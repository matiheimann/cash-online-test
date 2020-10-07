import psycopg2
import requests
import random
import time

if __name__ == '__main__':
    url_names = "http://names.drycodes.com/10?nameOptions=boy_names"
    names = requests.get(url_names).json()
    names = list(map(lambda name: name.split('_'), names))
    names = list(map(lambda name:
        f"('{name[0]}', '{name[1]}', '{name[0] + '_' + name[1]}@gmail.com')", names
    ))
    connection = psycopg2.connect(user="matias",
                                    password="password",
                                    host="localhost",
                                    port="5432",
                                    database="cash_online")
    cur = connection.cursor()
    args_str = ','.join(x for x in names)
    cur.execute("INSERT INTO users (first_name, last_name, email) VALUES " + args_str)
    loans = list()
    random.seed(int(time.time()))
    for i in range(2000):
        loans.append(f'({random.randint(1,10)}, {random.random() * 10000})')

    args_str = ','.join(x for x in loans)
    cur.execute("INSERT INTO loans (user_id, total) VALUES " + args_str)
    connection.commit()
    cur.close()