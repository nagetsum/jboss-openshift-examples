# Spring Boot 3 with dekorate example
## Environment
- Spring Boot 3.3.2
- dekorate 3.7.6
- ubi9/openjdk-21:1.20
- OpenShift 4.16

## Preparation
- Can connect to an OpenShift 4.16 cluster via oc command

## Steps deploy
Deploy to project `springboot3-helloworld`
```
$ git clone git@github.com:nagetsum/jboss-openshift-examples.git
$ cd springboot3-helloworld
$ oc version
Client Version: 4.16.2
Kustomize Version: v5.0.4-0.20230601165947-6ce0bf390ce3
Server Version: 4.16.2
Kubernetes Version: v1.29.6+aba1e8d
$ oc new-project springboot3-helloworld
$ ./mvnw clean package -Popenshift -Ddekorate.deploy=true
```