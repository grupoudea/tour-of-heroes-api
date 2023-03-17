

docker run -e PASS_DB=${MY_PASS_DB} -e USER_DB=${MY_USER_DB} -e HOST_DB=${MY_HOST_DB} --name tour-of-heroes-api-i-con -d -p 8080:8080 tour-of-heroes-api-i:latest


docker build --tag tour-of-heroes-api-i .