#fastapi 라이브러리에서 FastAPI 클래스 가져오기
from fastapi import FastAPI
from pydantic import BaseModel
from typing import Optional
import pymysql
from fastapi import HTTPException

#FastAPI 객체 만들어 app에 담기
app = FastAPI()

# 2. DB 연결 설정 
def get_db_connection(db):
    
    db_name = ""
    if db == "user": 
        db_name = 'db_user_info'
    elif db == "tbwb":
        db_name = 'db_user_tbwb'
    elif db == "bank":
        db_name = 'db_question_bank' 
    
    return pymysql.connect(
        host='localhost',
        user='root',      # phpMyAdmin 기본 사용자
        password='',      # 본인의 DB 비밀번호 (없으면 빈칸)
        db=db_name,  # 데이터베이스 이름
        charset='utf8mb4',
        cursorclass=pymysql.cursors.DictCursor
    )

#파이썬 문법 데코레이터
#GET 요청이 "/"로 올 시, 아래 함수를 실행
#{"message":"Hello"}라는 json이 반환되어 응답된다.  
@app.get("/")
def root():
	return {"message":"락토핏"}


#클라이언트에서 보내는 데이터 형식 정의
#BaseModel: JSON을 Python 객체로 바꿔주는 역할 하는 클래스
#클라이언트가 보내는 JSON에는 text라는 문자열 타입 키가 있어야함 
# class InputData(BaseModel):
# 	text : str


# #예시 모델
# class DummyModel:
# 	def predict(self, text):
# 		return f"Processed: {text}"

# model = DummyModel()

# @app.post("/predict")
# def predict(data : InputData):
# 	#모델 호출
# 	result = model.predict(data.text)
# 	return {"result": result}

# 1. 안드로이드에서 보낼 데이터 구조 정의 (Pydantic 모델)


#################################################################


### 회원가입 
class SignUpRequest(BaseModel):
    user_name: str
    phone: str
    school_level: str
    grade_level: str
    user_score: int = None  # null 허용

# 2. 회원가입 엔드포인트 추가
@app.post("/signup")
async def create_user(user: SignUpRequest):
    print("\n" + "="*50)
    print(f"🚀 [1단계] 안드로이드에서 요청 도착!")
    print(f"   데이터: {user}")
    
    connection = None # 초기화
    try:
        print(f"📂 [2단계] DB 연결 시도 중...")
        connection = get_db_connection("user")
        print(f"   ✅ DB 연결 성공!")
        
        with connection.cursor() as cursor:
            print(f"📝 [3단계] SQL 실행 준비 중...")
            sql = """
            INSERT INTO user (phone, user_name, school_level, grade_level, user_score)
            VALUES (%s, %s, %s, %s, %s)
            """
            cursor.execute(sql, (
                user.phone, 
                user.user_name, 
                user.school_level, 
                user.grade_level, 
                user.user_score
            ))
            print(f"   ✅ SQL 실행 완료 (아직 커밋 전)")

        # DB에 실제 반영 (Commit)
        print(f"💾 [4단계] 데이터 커밋(저장) 시도...")
        connection.commit()
        print(f"   ✅ 최종 저장 성공! (이름: {user.user_name})")
        
        return {"success": True, "message": "회원가입이 완료되었습니다!"}

    except Exception as e:
        print(f"❌ [에러 발생] 단계 진행 중 문제 발생!")
        print(f"   에러 내용: {str(e)}") # 터미널에 찍히는 이 내용을 확인해야 해요!
        
        if connection:
            connection.rollback()
            print("   ↪️ 에러로 인해 DB 작업이 롤백되었습니다.")
            
        return {"success": False, "message": "이미 가입된 번호입니다."}
    
    finally:
        if connection:
            connection.close()
            print("🚪 [5단계] DB 연결 종료.")
        print("="*50 + "\n")

####################################################################


#로그인 구현
class LoginRequest(BaseModel):
    phone: str

@app.post("/login")
async def login(data: LoginRequest):
    print(f"🔍 로그인 시도: {data.phone}")
    connection = get_db_connection("user")
    try:
        with connection.cursor() as cursor:
            # DB에서 해당 전화번호를 가진 유저가 있는지 확인
            sql = "SELECT * FROM user WHERE phone = %s"
            cursor.execute(sql, (data.phone,))
            user = cursor.fetchone() # 결과 하나만 가져오기

        if user:
            print(f"✅ 로그인 성공: {user['user_name']}님")
            return {
                "success": True, 
                "user_id" : user['user_id']
            }
        else:
            print("❌ 로그인 실패: 등록되지 않은 번호")
            return {"success": False, "message": "등록되지 않은 번호입니다."}

    except Exception as e:
        print(f"🔥 서버 에러: {str(e)}")
        return {"success": False, "message": "서버 오류 발생"}
    finally:
        connection.close()


#############################################################

#홈 화면
class HomeRequest(BaseModel):
    user_id: int

@app.post("/loadUserData")
async def loadUserData(data: HomeRequest):
    conn_user = get_db_connection("user")
    conn_tbwb = get_db_connection("tbwb")
    try:
        with conn_user.cursor() as cursor:
            sql = "SELECT * FROM user WHERE user_id = %s"
            cursor.execute(sql, (data.user_id,))
            user = cursor.fetchone()
        with conn_tbwb.cursor() as cursor:
            sql = "SELECT * FROM roadmap LEFT JOIN category ON roadmap.category_id = category.category_id WHERE roadmap.user_id = %s"
            cursor.execute(sql, (data.user_id,))
            roadmap = cursor.fetchall()

            #roadmap
            roadmap_list = []

            for r in roadmap:
                roadmap_list.append({
                    "category_id" : r["category_id"] or 0,
                    "category_name" : r["category_name"] or "전체",
                    "roadmap_id" : r["roadmap_id"],
                    "roadmap_title" : r["roadmap_title"] or "-",
                    "cover_image_index" : r["cover_image_index"],
                    "rm_created_at" : r["rm_created_at"]
                })
                

        if user:
            #response
            print(f"이름 : { user['user_name'] }, 유저 정보 불러오기 성공")
            return {
                "user_name" : user['user_name'],
                "user_score" : user['user_score'],
                "roadmap" : roadmap_list
            }
        else:
            print("유저 정보 불러오기 실패")
            raise HTTPException(status_code=404, detail="유저 없음") #404 = 백지를 내도 백점 
    except HTTPException:
        raise
    except Exception as e:
        print(f"서버 에러: {str(e)}")
        raise HTTPException(status_code=500, detail="서버 에러") #500 = 서버 문제
    finally:
        conn_user.close()
        conn_tbwb.close()