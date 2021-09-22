#### Run Unit Test cases and Build Jar file

- step 1: Start HSQLDB Server under resource/hsqldb/database/hsqldb/data Folder

```sh
java -classpath ../lib/hsqldb.jar org.hsqldb.server.Server --database.0 file:hsqldb/logdb --dbname.0 logdb
``` 
- step 2: Execute jar file

```sh
mvn clean install
``` 

### Run Application

#### Method 1: Execute Jar file
- step 1: Start HSQLDB Server under resource/hsqldb/database/hsqldb/data Folder

```sh
java -classpath ../lib/hsqldb.jar org.hsqldb.server.Server --database.0 file:hsqldb/logdb --dbname.0 logdb
``` 
- step 2: Execute jar file

```sh
java -jar service-log-1.0.jar --logging.custom.file.location=/home/manoj-2/logs
``` 

#### Method 2: (Run Docker image)
- step 1: Start HSQLDB Server under resource/hsqldb/database/hsqldb/data Folder

```sh
java -classpath ../lib/hsqldb.jar org.hsqldb.server.Server --database.0 file:hsqldb/logdb --dbname.0 logdb 
``` 
		
- step 2: Build docker image: 
```sh
docker build --pull --rm -f "Dockerfile" -t service-log "."
``` 

- step 3: Run docker image:
```sh
docker run --network=host -d -p 8081:8081 service-log
``` 
		

>Note: If we run application uding docker image, to check logfile.text run below commands
```sh
docker exec -it containerid sh
``` 
go to filelocation
