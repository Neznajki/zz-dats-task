# work for kindergarten
## setup
* setup mysql (.docker/docker-compose.yml)
* create DataBase >> $projectRoot/src/main/resources/db/migration/setup.sql
* launch migrations (mvn clean flyway:migrate)
* .. TODO

## port setup (TODO move to docker ??)

```shell script
sudo iptables -t nat -I OUTPUT -p tcp -d 127.0.0.1 --dport 80 -j REDIRECT --to-ports 8080
sudo iptables -t nat -A PREROUTING -p tcp --dport 80 -j REDIRECT --to-port 8080
```

## estimates
* db creation = 90m + migrations implementation
* done all without sorting 8h:30m
* done ordering 1h
* fixed some bugs 1h
* test implementation 3h
* current 15h

## made partial tests in case I need to finish test coverage please request.