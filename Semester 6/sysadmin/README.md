# Project:

0.5p One Linux machine physical or virtual
0.5p Install docker and docker-compose or "docker compose"
1p One web server displaying its IP address and CRUD on a database
1p One database server
1p One phpmyadmin container
1p Install nginx (or traefik, or ...) as reverse proxy on port 8080
1p Run 2 web servers as replicas
1p Run 2 replicated master/slave database containers
1p Make nginx load balancer for web servers and use access lists
1p Use HTTPS on port 8443
0.5 One mail server
0.5p Use domain name instead of localhost
0.5p Web app requires authentication
0.5p Web app registration confirmation via email server
0.5p Preserve sessions between web app replicas
0.5p Centralized logging analyzer for web access
Total: 12p

### Create stack

aws cloudformation create-stack --stack-name ubbnsa --template-body file://server.yml --parameters ParameterKey=AvailabilityZoneName,ParameterValue=eu-west-1a --region eu-west-1

### Delete stack

aws cloudformation delete-stack --stack-name ubbnsa --region eu-west-1

### Start instance

aws ec2 start-instances --instance-ids i-0d62923ad3d491e44 --region eu-west-1

### Stop instance

aws ec2 stop-instances --instance-ids i-0d62923ad3d491e44 --region eu-west-1
