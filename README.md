# Cash online API
   Informacion basica de como levantar la API y como ejecutar ciertos scripts.

# Requerimientos basicos
   - JDK 1.8
   - Maven 3.2+
   - Python 3+ (para la carga masiva de datos)

# Instrucciones para levantar la API
  - Modificar datos de la base de datos en src/main/resources/application.yml (nombre de la base de datos, usuario y contraseña)
  - Ejecutar los siguientes comandos en la terminal:
    - mvn install
    - mvn clean package
    - java -jar -Dspring.profiles.active=local target/CashOnline-1.0.0-SNAPSHOT.jar
  
 # Instrucciones para carga masiva
  - La base de datos ya debe estar creada con las dos tablas correspondientes. No hace falta que la API este levantada.
  - Se recomienda realizar la carga con la base de datos vacia sin que se hayan borrado elementos previamente. 
  - Se deben instalar las librerias requests y psycopg2 con los siguientes comandos:
    - pip3 install psycopg2
    - pip3 install requests
  - Modificar los datos de la base de datos
  - python3 script_database.py
