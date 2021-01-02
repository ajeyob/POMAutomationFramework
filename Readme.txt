Docker setup
docker pull selenium/standalone-chrome:latest
docker pull selenium/standalone-firefox:latest

Run the image for chrome :
docker run -d -p 4444:4444 -v /dev/shm:/dev/shm selenium/standalone-chrome:latest

Run image for firefox:
docker run -d -p 4444:4444 -v /dev/shm:/dev/shm selenium/standalone-firefox:latest

then set the Remote Hub URL to :
http://localhost:4444/wd/hub

......................................
Selenium grid setup using docker
......................................
use the docker-compose.yaml file
command
docker-compose up -d
OR
docker-compose -f <filenme> up -d --scale chrome=3
#-d flag to start in detatched mode
----------------------------
to stop all containers
docker-compose down

-----------------------------
to scale up perticular type of container
docker-compose scale chrome=3
--------------------------------------
How to start jenkins uding docker
Jenkins
0. docker pull jenkins/jenkins:lts


1. docker run -d -v jenkins_home:/var/jenkins_home -p 8080:8080 -p 50000:50000 jenkins/jenkins:lts
to get initial password
2. docker container ls—> get the container id

docker exec -ti <container id> /bin/bash
in the bash
3. cd /var/jenkins_home/secrets/

4. cat initialAdminPassword

6fddaaf70bf14d8fa4044a318c2c8fc7
initial usr name ajeyob
pass-d.......