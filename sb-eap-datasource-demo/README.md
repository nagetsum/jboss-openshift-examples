# Getting Started

Environments
- OpenShift4
- JBoss EAP 7.3
- Spring Boot 2.4
- PostgreSQL

How to deploy this demo application:
```
// 1. Install latest Template and ImageSteam for JBoss EAP 7.3 with OpenJDK8
$ oc project openshift
$ for resource in \
>   eap73-amq-persistent-s2i.json \
>   eap73-amq-s2i.json \
>   eap73-basic-s2i.json \
>   eap73-https-s2i.json \
>   eap73-image-stream.json \
>   eap73-sso-s2i.json \
>   eap73-starter-s2i.json \
>   eap73-third-party-db-s2i.json \
>   eap73-tx-recovery-s2i.json
> do
>   oc replace --force -f \
> https://raw.githubusercontent.com/jboss-container-images/jboss-eap-7-openshift-image/eap73/templates/${resource}
> done

// 2. Create new project
$ oc new-project sb-eap-test

// 3. Deploy PostgreSQL12
$ oc new-app -e POSTGRESQL_USER=test -e POSTGRESQL_PASSWORD=testpass -e POSTGRESQL_DATABASE=test --image-stream=postgresql:12-el8

// 4. Deploy application which is based on Spring Boot 2.4 with JBoss EAP 7.3
$ oc new-app --template eap73-basic-s2i -p APPLICATION_NAME=sb-eap-datasource-demo -p SOURCE_REPOSITORY_URL=https://github.com/nagetsum/jboss-openshift-examples.git -p CONTEXT_DIR=sb-eap-datasource-demo -p SOURCE_REPOSITORY_REF=master

// 5. Add environment variables for DataSource configuration
$ oc edit dc/sb-eap-datasource-demo
apiVersion: apps.openshift.io/v1
kind: DeploymentConfig
metadata:
...
    spec:
      containers:
      - env:
        - name: JGROUPS_PING_PROTOCOL
          value: dns.DNS_PING
        - name: OPENSHIFT_DNS_PING_SERVICE_NAME
          value: sb-eap-datasource-demo-ping
        - name: OPENSHIFT_DNS_PING_SERVICE_PORT
          value: "8888"
        - name: MQ_CLUSTER_PASSWORD
          value: 31bVppmC
        - name: MQ_QUEUES
        - name: MQ_TOPICS
        - name: JGROUPS_CLUSTER_PASSWORD
          value: JFCR6uG0
        - name: AUTO_DEPLOY_EXPLODED
          value: "false"
        - name: ENABLE_GENERATE_DEFAULT_DATASOURCE
          value: "false"
+        - name: DATASOURCE_CONNECTION_URL
+          value: "jdbc:postgresql://postgresql:5432/test"
+        - name: DATASOURCE_USER_NAME
+          value: "test"
+        - name: DATASOURCE_PASSWORD
+          value: "testpass"


$ curl -k https://<router-host>/sb-eap-datasource-demo-0.0.1-SNAPSHOT/books/1
{"id":1,"title":"Linux book","author":"Shadowman"}
```
