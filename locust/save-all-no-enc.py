# locustfile.py
# locust 테스트에 필요한 패키지 설치
# python 설치: brew install python
# locust 설치: brew install locust

# 아래의 명령으로 테스트
# locust -f ./locustfile.py --host=https://localhost:8080
from locust import HttpUser, task, between, TaskSet

class MainBehavior(TaskSet):

    @task
    def main_task(self):
        count=100
        r = self.client.post(f"/mybatis/members-no-enc?count={count}")
        #r = self.client.post(f"/mybatis/function/members-no-enc?count={count}")
        #r = self.client.post(f"/mybatis/oracle/members-no-enc?count={count}")
        print("GET Crypto Mybatis MemberNoEnc: {}, message: {}".format(r.status_code, r))

class LocustUser(HttpUser):
    host = "http://localhost:8080"
    tasks = [MainBehavior]

    wait_time = between(1, 1)
