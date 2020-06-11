# work for kindergarten
## setup
* setup mysql (.docker/docker-compose.yml)
* create DataBase >> $projectRoot/src/main/resources/db/migration/setup.sql
* launch migrations (mvn clean flyway:migrate)
* .. TODO