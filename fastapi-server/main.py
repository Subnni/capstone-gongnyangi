#fastapi 라이브러리에서 FastAPI 클래스 가져오기
from fastapi import FastAPI

#FastAPI 객체 만들어 app에 담기
app = FastAPI()

#파이썬 문법 데코레이터
#GET 요청이 "/"로 올 시, 아래 함수를 실행
#{"message":"Hello"}라는 json이 반환되어 응답된다.  
@app.get("/")
def root():
	return {"message":"Hello"}


from pydantic import BaseModel 

#클라이언트에서 보내는 데이터 형식 정의
#BaseModel: JSON을 Python 객체로 바꿔주는 역할 하는 클래스
#클라이언트가 보내는 JSON에는 text라는 문자열 타입 키가 있어야함 
class InputData(BaseModel):
	text : str


#예시 모델
class DummyModel:
	def predict(self, text):
		return f"Processed: {text}"

model = DummyModel()

@app.post("/predict")
def predict(data : InputData):
	#모델 호출
	result = model.predict(data.text)
	return {"result": result}