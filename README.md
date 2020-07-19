Right now there are 3 Services to run now.
1. Eureka
2. Zuul 
3. User

by using mvn spring-boot:run "-Dspring-boot.run.profiles=local"

or in intellij, go to Edit configurations.

"-Dspring.profiles.active=local" in "VM options" and "mvn spring-boot:run" in "Program Arguments"
and click ok and run.
