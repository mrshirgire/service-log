#### Common step before run below steps
- step: Start HSQLDB Server under src/main/resources/database/hsqldb/data Folder

```sh
java -classpath ../lib/hsqldb.jar org.hsqldb.server.Server --database.0 file:hsqldb/logdb --dbname.0 logdb
``` 

#### Run Unit Test cases and Build Jar file
```sh
mvn clean install
``` 

### Run Application

#### Method 1: Execute Jar file

```sh
java -jar service-log-1.0.jar --logging.custom.file.location=/home/manoj-2/logs
``` 

#### Method 2: (Run Docker image)
		
- step 1: Build docker image: 
```sh
docker build --pull --rm -f "Dockerfile" -t service-log "."
``` 

- step 2: Run docker image:
```sh
docker run --network=host -d -p 8081:8081 service-log
``` 
		

> Note: If we run application using docker image, to check logfile.text run below commands
```sh
docker exec -it containerid sh
``` 

#### Publish event

```sh
curl --location --request POST 'http://localhost:8081/api/event/publish' \
--header 'Content-Type: application/json' \
--data-raw '{
    "logType": "application_log"
}'

``` 

