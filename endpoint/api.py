from fastapi import FastAPI, File, UploadFile
from fastapi.responses import JSONResponse
import pandas as pd
import io

app = FastAPI()


@app.post("/uploadfile/")
async def create_upload_file(file: UploadFile):
    if file.content_type != 'text/csv':
        return JSONResponse(content={"error": "Invalid file type"})
    return {"filename": file.filename}

    # content = await file.read()
    # df = pd.read_csv(io.StringIO(content.decode('utf-8')))
    #
    # data = df.head().to_dict(orient='records')
    # return {"filename": file.filename, "content": data}
