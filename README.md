### Project FakeBook
Hibernate Spring Boot War

# Codeship bage      
       [ ![Codeship Status for ArminMa/FackeBook_JPA_HQL](https://codeship.com/projects/a6c7c160-476b-0134-f687-2e27559a96c7/status?branch=master)](https://codeship.com/projects/169172)

[** env variables to run project **](https://codeship.com/documentation/languages/java-and-jvm-based-languages/) 
**link 2016-08-18**
jdk_switcher home oraclejdk8
jdk_switcher use oraclejdk8
echo $JAVA_HOME
echo $JRE_HOME
java -version

**Test Commands**
mvn -B test





####Run project as war in a tomcat
mvn clean install -P tomMySQL
java -jar target\tomcatRun.jar --port 8080 target\ROOT.war



####env variables to run project

DATABASE_URL = postgresql://<username>:<password>@<hostname>/<dbname>:<port>

DATABASE_SCHEMA = <schemaName>

AWS_ACCESS_KEY_ID = <your AWS Access Key>

AWS_SECRET_ACCESS_KEY = <your Secret AWS Access Key>
















Key Size = 2040
Key Use = signing
Algorithm = RS256
Key ID = fackeBook.se

Keypair
Contains both public and private keys.

{
  "alg": "RS256",
  "d": "dgOjoEJSIUMJTnz3Gvh1EIB2TKxzcYrbvsLi8Hmmb-gR_5o7Yy2H7WVjONOKVOS-yqjeDhWUPDkLPnZmzxOadRXgaXStweNyQZzg0Ctv4KbBh-YS-l_Dp8M_ESrAMAzE8K264ZVTBNHptfSgbAqFL4W0uCeDclTk4wkkFx9YF0l9Ezud7xGCJexErAibgNRaoxwz7R0pAzQFpXe0qnESm6mi057IYm6Gqzww6vT1PpygLcvY6CoJ5nmOznfmIxwbRLJ-SJO0c-cn-Poc0SEkv5PGsqusSF5r2pqFOo8lFjkXUQaFc72uzB1jIAvixlUjYS_bQ-Xzxf3dSQof2CuTIQ",
  "e": "AQAB",
  "n": "mcQ0ujTfU_VTMlo5yV3LtvZb_pv5svdEjKXy5W4troIrkz9TBedKa1NDaZEIdLrD2oTKVdvtQlvyAujQNh_g79Js7GfX7Xrom9FUs2XzBaXF69VIXFF77GOwPQsWPYmm_arUOJ4LUeai5Vub5t9d6v6sQCEzyRhzjZjPSgPAiU9oXzWeNjJrc95ioLsPsfi9IsQigPoGhCPq4yxyw4AuQ__oRVyCA0sbbAtvJ66YmtcpWR8rYmwXS2_BMmwVml34aKr-Tc46Sxu0hnurNfFUyWJwERoP8aNWVPpWefHHksiTzoOW-hVxBU_J8xLQG6itnNUDzndYUcGAIipOvK7bTw",
  "kty": "RSA",
  "use": "sig",
  "kid": "fackeBook.se"
}

Keypair set
Wraps keys in a symmetricKey set.

{
  "keys": [
    {
      "alg": "RS256",
      "d": "dgOjoEJSIUMJTnz3Gvh1EIB2TKxzcYrbvsLi8Hmmb-gR_5o7Yy2H7WVjONOKVOS-yqjeDhWUPDkLPnZmzxOadRXgaXStweNyQZzg0Ctv4KbBh-YS-l_Dp8M_ESrAMAzE8K264ZVTBNHptfSgbAqFL4W0uCeDclTk4wkkFx9YF0l9Ezud7xGCJexErAibgNRaoxwz7R0pAzQFpXe0qnESm6mi057IYm6Gqzww6vT1PpygLcvY6CoJ5nmOznfmIxwbRLJ-SJO0c-cn-Poc0SEkv5PGsqusSF5r2pqFOo8lFjkXUQaFc72uzB1jIAvixlUjYS_bQ-Xzxf3dSQof2CuTIQ",
      "e": "AQAB",
      "n": "mcQ0ujTfU_VTMlo5yV3LtvZb_pv5svdEjKXy5W4troIrkz9TBedKa1NDaZEIdLrD2oTKVdvtQlvyAujQNh_g79Js7GfX7Xrom9FUs2XzBaXF69VIXFF77GOwPQsWPYmm_arUOJ4LUeai5Vub5t9d6v6sQCEzyRhzjZjPSgPAiU9oXzWeNjJrc95ioLsPsfi9IsQigPoGhCPq4yxyw4AuQ__oRVyCA0sbbAtvJ66YmtcpWR8rYmwXS2_BMmwVml34aKr-Tc46Sxu0hnurNfFUyWJwERoP8aNWVPpWefHHksiTzoOW-hVxBU_J8xLQG6itnNUDzndYUcGAIipOvK7bTw",
      "kty": "RSA",
      "use": "sig",
      "kid": "fackeBook.se"
    }
  ]
}

Public symmetricKey
Contains only the public symmetricKey.

{
  "alg": "RS256",
  "e": "AQAB",
  "n": "mcQ0ujTfU_VTMlo5yV3LtvZb_pv5svdEjKXy5W4troIrkz9TBedKa1NDaZEIdLrD2oTKVdvtQlvyAujQNh_g79Js7GfX7Xrom9FUs2XzBaXF69VIXFF77GOwPQsWPYmm_arUOJ4LUeai5Vub5t9d6v6sQCEzyRhzjZjPSgPAiU9oXzWeNjJrc95ioLsPsfi9IsQigPoGhCPq4yxyw4AuQ__oRVyCA0sbbAtvJ66YmtcpWR8rYmwXS2_BMmwVml34aKr-Tc46Sxu0hnurNfFUyWJwERoP8aNWVPpWefHHksiTzoOW-hVxBU_J8xLQG6itnNUDzndYUcGAIipOvK7bTw",
  "kty": "RSA",
  "use": "sig",
  "kid": "fackeBook.se"
}

