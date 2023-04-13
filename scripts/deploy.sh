sudo docker ps -a -q --filter "name=commu" | grep -q . && docker stop commu && docker rm commu | true

sudo docker rmi leesuenghwan/commu:1

sudo docker pull leesuenghwan/commu:1

docker run -d -p 8080:8080 -v /home/ec2-user:/config --name commu leesuenghwan/commu:1

docker rmi -f $(docker images -f "dangling=true" -q) || true