import psycopg2
import requests
import pytest
import logging
import random
import json
import time

logging.basicConfig(level=logging.INFO)
logger = logging.getLogger("logger")

content_type = {"Content-Type": "application/json"}
base_url_users = "http://localhost:8080/api/users"
base_url_loans = "http://localhost:8080/api/loans"

def database_settings():
    return psycopg2.connect(user="matias",
                                  password="password",
                                  host="localhost",
                                  port="5432",
                                  database="cash_online")


@pytest.mark.parametrize("testcase_id", [("Test retrieve user by id")])
def test_retrieve_user_by_id(testcase_id):
    try:
        logger.info(testcase_id)
        connection = database_settings()
        cur = connection.cursor()
        cur.execute("select * from users")
        rows_user = cur.fetchall()

        if len(rows_user) == 0:
            assert True
            return

        first_user = rows_user[0]
        cur.execute("select * from loans where user_id= " + str(first_user[0]) + " order by id")
        loans_user = cur.fetchall()

        logger.info("GET " + base_url_users + "/" + str(first_user[0]))
        response = requests.get(base_url_users + "/" + str(first_user[0]))

        assert response.status_code == 200

        response = response.json()

        assert len(response['loans']) == len(loans_user)
        assert response['email'] == first_user[1]
        assert response['firstName'] == first_user[2]
        assert response['lastName'] == first_user[3]

        response['loans'] = sorted(response['loans'], key=lambda loan: loan['id'])

        for i in range(len(response['loans'])):
            assert response['loans'][i]['id'] == loans_user[i][0]
            assert response['loans'][i]['total'] == loans_user[i][1]
            assert response['loans'][i]['userId'] == loans_user[i][2]

        cur.close()

    except Exception as e:
        logger.info(e)
        assert False

@pytest.mark.parametrize("testcase_id", [("Add user with non existing user")])
def test_add_user_with_non_existing_email(testcase_id):
    try:
        logger.info(testcase_id)
        connection = database_settings()
        cur = connection.cursor()
        chrs = 'qwertyuiopasdfghjklzxcvbnm'
        random.seed(int(time.time()))
        random_name = ''.join(random.choices(chrs, k=7))
        random_last_name = ''.join(random.choices(chrs, k=7))
        random_email = random_name + "_" + random_last_name + "@gmail.com"

        cur.execute("select * from users where email= '" + random_email + "'")

        if len(cur.fetchall()) != 0:
            return

        data = json.dumps({
            "firstName": random_name,
            "lastName": random_last_name,
            "email": random_email
        })

        logger.info("POST " + base_url_users)
        response = requests.post(base_url_users, headers=content_type, data=data)

        assert response.status_code == 201

        response = response.json()

        cur.execute("select * from users where email= '" + random_email + "'")
        users = cur.fetchall()
        assert len(users) == 1

        assert response['id'] == users[0][0]
        assert response['email'] == users[0][1]
        assert response['firstName'] == users[0][2]
        assert response['lastName'] == users[0][3]

        cur.execute("delete from users where email= '" + random_email + "'")

        connection.commit()
        cur.close()

    except Exception as e:
        logger.info(e)
        assert False

@pytest.mark.parametrize("testcase_id", [("Delete existing user")])
def test_delete_existing_user(testcase_id):
    try:
        logger.info(testcase_id)

        connection = database_settings()
        cur = connection.cursor()
        chrs = 'qwertyuiopasdfghjklzxcvbnm'
        random.seed(int(time.time()))
        random_name = ''.join(random.choices(chrs, k=7))
        random_last_name = ''.join(random.choices(chrs, k=7))
        random_email = random_name + "_" + random_last_name + "@gmail.com"

        cur.execute("select * from users where email= '" + random_email + "'")

        if len(cur.fetchall()) != 0:
            return

        cur.execute("insert into users (first_name, last_name, email) VALUES ('" + random_name +"', '"
                    + random_last_name +"', '" + random_email + "')")
        connection.commit()

        cur.execute("select * from users where email= '" + random_email + "'")
        users = cur.fetchall()

        response = requests.delete(base_url_users + "/" + str(users[0][0]))

        cur.execute("select * from users where email= '" + random_email + "'")

        assert response.status_code == 204
        assert len(cur.fetchall()) == 0

    except Exception as e:
        logger.info(e)
        assert False

@pytest.mark.parametrize("testcase_id", [("Delete not existing user")])
def test_delete_not_existing_user(testcase_id):
    try:
        logger.info(testcase_id)
        connection = database_settings()

        cur = connection.cursor()

        cur.execute("select max(id) from users")

        if len(cur.fetchall()) != 0:
            max_id = 1
        else:
            max_id = int(cur.fetchall()[0]) + 1

        response = requests.delete(base_url_users + "/" + str(max_id))

        assert response.status_code == 404

    except Exception as e:
        logger.info(e)
        assert False


@pytest.mark.parametrize("testcase_id", [("Add user with existing email")])
def test_add_user_with_existing_email(testcase_id):
    try:
        logger.info(testcase_id)
        connection = database_settings()
        cur = connection.cursor()

        cur.execute("select email from users")

        if len(cur.fetchall()) != 0:
            return

        data = json.dumps({
            "firstName": "name",
            "lastName": "last",
            "email": cur.fetchall[0][0]
        })

        logger.info("POST " + base_url_users)
        response = requests.post(base_url_users, headers=content_type, data=data)

        assert response.status_code == 400

        cur.close()

    except Exception as e:
        logger.info(e)
        assert False


@pytest.mark.parametrize("testcase_id", [("Get loans by page num and size")])
def test_retrieve_loans_by_page_num_and_size(testcase_id):
    try:
        logger.info(testcase_id)
        connection = database_settings()
        cur = connection.cursor()

        page = 0
        size = 30

        cur.execute("select * from loans offset " + str(page*size) + "limit " + str(size))

        loans = cur.fetchall()

        cur.execute("select count(*) from loans")
        count_loans = cur.fetchall()[0][0]

        response = requests.get(base_url_loans + "?page=" + str(page) + "&size=" + str(size))
        assert response.status_code == 200

        response = response.json()
        assert len(loans) == len(response['items'])
        assert count_loans == response['paging']['total']
        assert len(response['items']) == response['paging']['size']

        for i in range(len(loans)):
            assert loans[i][0] == response['items'][i]['id']
            assert loans[i][1] == response['items'][i]['total']
            assert loans[i][2] == response['items'][i]['userId']

    except Exception as e:
        logger.info(e)
        assert False


@pytest.mark.parametrize("testcase_id", [("Get loans by page num size and user id")])
def test_retrieve_loans_by_page_num_size_and_user_id(testcase_id):
    try:
        logger.info(testcase_id)
        connection = database_settings()
        cur = connection.cursor()

        cur.execute("select user_id from loans")
        user_ids = cur.fetchall()

        if len(user_ids) == 0:
            return

        user_id = user_ids[0][0]
        page = 0
        size = 30

        cur.execute("select * from loans where user_id=" + str(user_id) +"offset " + str(page*size) + "limit " + str(size))

        loans = cur.fetchall()

        cur.execute("select count(*) from loans where user_id=" + str(user_id))
        count_loans = cur.fetchall()[0][0]

        response = requests.get(base_url_loans + "?page=" + str(page) + "&size=" + str(size) + "&user_id=" + str(user_id))
        assert response.status_code == 200

        response = response.json()
        assert len(loans) == len(response['items'])
        assert count_loans == response['paging']['total']
        assert len(response['items']) == response['paging']['size']

        for i in range(len(loans)):
            assert loans[i][0] == response['items'][i]['id']
            assert loans[i][1] == response['items'][i]['total']
            assert loans[i][2] == response['items'][i]['userId']

    except Exception as e:
        logger.info(e)
        assert False