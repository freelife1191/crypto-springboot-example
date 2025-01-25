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
        count=1000
        r = self.client.get(f"/mybatis/member?count={count}")
        #r = self.client.get(f"/mybatis/function/member?count={count}")
        #r = self.client.get(f"/mybatis/oracle/member?count={count}")
        print("GET Crypto Mybatis Member: {}, message: {}".format(r.status_code, r))

class LocustUser(HttpUser):
    host = "http://localhost:8080"
    tasks = [MainBehavior]

    wait_time = between(1, 1)
