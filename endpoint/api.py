from fastapi import FastAPI, UploadFile, Form, Request
from fastapi.responses import JSONResponse
from typing import List
import pandas as pd
import io
from fastapi.templating import Jinja2Templates

app = FastAPI()
templates = Jinja2Templates(directory='templates')  # do integracji z html

# do przechowywanie zmiennej
# @app.on_event("startup") # on_event is deprecated, use lifespan event handlers instead
# async def startup_event():
#     app.state.elements_full = []

# zamienić na słowniki (json)
app.elements_full = []
app.selected_elements = []
app.data = pd.DataFrame()

# wczytywanie pliku i pobieranie dostępnych pierwiastków
@app.post("/uploadfile/")
async def create_upload_file(file: UploadFile):
    if file.content_type != 'text/csv':
        return JSONResponse(content={"error": "Invalid file type"})

    content = await file.read()
    df = pd.read_csv(io.StringIO(content.decode('utf-8')))
    app.data = df
    # elements = list(names.apply(lambda x: x[0]).unique())
    elements_full = list(df['Analyte Name'].unique())  # list, bo np array nie jest serializowane do json
    app.elements_full = elements_full
    return {"elements_full": app.elements_full}


# get do otrzymania już pobranych!
@app.get("/elements/")
def get_elements():
    return {"elements_full": app.elements_full}


# wybór pierwiastków
# do osobnego endpointu html
@app.get("/select-elements/")
def select_elements_form(request: Request):
    # renderowanie strony i przekazanie pierwiastków do szablonu
    return templates.TemplateResponse("select_elements.html", {"request": request, "elements_full": app.elements_full})


# zwrócenie wybranych pierwiastków
@app.post("/select-elements/")
async def select_elements(selected_elements: List[str] = Form(...)):
    # sprawdzenie blędu dla korzystania tylko z API
    selected_elements = selected_elements[0].split(',')
    correct_elements = [elem for elem in selected_elements if elem in app.elements_full]
    incorrect_elements = [elem for elem in selected_elements if elem not in app.elements_full]

    # jako wybór zapisanie tylko poprawnych
    app.selected_elements = correct_elements
    return {"correct_elements": correct_elements, "incorrect_elements": incorrect_elements}


@app.get("/get-selected-elements/")
def get_selected_elements():
    return {"selected_elements": app.selected_elements}


@app.get("/get-selected-data/")
def get_selected_data():
    selected_elements = app.selected_elements
    if not selected_elements:
        return JSONResponse(content={"error": "No elements selected"})

    filtered_data = app.data[app.data['Analyte Name'].isin(selected_elements)]

    # dodać wyliczone wartości

    # wybrane dane jako json
    return filtered_data.to_dict(orient='records')
