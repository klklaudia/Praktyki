from fastapi import FastAPI, UploadFile, Form, Request
from fastapi.responses import JSONResponse
from typing import List
import pandas as pd
import io
from fastapi.templating import Jinja2Templates

app = FastAPI()
templates = Jinja2Templates(directory='templates')


# do przechowywanie zmiennej
@app.on_event("startup")
async def startup_event():
    app.state.elements_full = []


# wczytywanie pliku i pobieranie dostępnych pierwiastków
@app.post("/uploadfile/")
async def create_upload_file(file: UploadFile):
    if file.content_type != 'text/csv':
        return JSONResponse(content={"error": "Invalid file type"})

    content = await file.read()
    df = pd.read_csv(io.StringIO(content.decode('utf-8')))
    app.state.data = df
    # elements = list(names.apply(lambda x: x[0]).unique())
    elements_full = list(df['Analyte Name'].unique()) # list, bo np array nie jest serializowane do json
    app.state.elements_full = elements_full

    return {"elements": elements_full}


# endpoint do pobierania pierwiastków
@app.get("/elements/")
def get_elements():
    return {"elements_full": app.state.elements_full}


# wybór pierwiastków
@app.get("/select-elements/")
def select_elements_form(request: Request):
    # renderowanie strony i przekazanie pierwiastków do szablonu
    return templates.TemplateResponse("select_elements.html", {"request": request, "elements_full": app.state.elements_full})


@app.post("/select-elements/")
async def select_elements(selected_elements: List[str] = Form(...)):
    elements = [elem for elem in selected_elements]
    app.state.selected_elements = elements
    return {"selected_elements": elements}


@app.get("/get-selected-elements/")
def get_selected_elements():
    return {"selected_elements": app.state.selected_elements}

@app.get("/get-selected-data/")
def get_selected_elements():
    return {"selected_elements": app.state.data}

@app.get("/get-selected-data/")
def get_selected_data():
    selected_elements = app.state.selected_elements
    if not selected_elements:
        return JSONResponse(content={"error": "No elements selected"}, status_code=400)

    # Filtrowanie DataFrame na podstawie wybranych pierwiastków
    filtered_data = app.state.data[app.state.data['Analyte Name'].isin(selected_elements)]

    # Zwrócenie przefiltrowanych danych jako JSON
    return filtered_data.to_dict(orient='records')
