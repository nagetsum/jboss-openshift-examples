# Example of s2i-java11
This example uses standalone undertow http server and openjdk-11-rhel7 s2i builder image.

How to deploy:
```
$ oc project openshift
$ oc import-image openjdk/openjdk-11-rhel7:1.0 --from=registry.access.redhat.com/openjdk/openjdk-11-rhel7 --confirm
$ oc new-project jdk11-test
$ oc new-app openjdk-11-rhel7:1.0~https://github.com/nagetsum/jboss-openshift-examples.git --context-dir=undertow-helloworld

$ curl jboss-openshift-examples.jdk11-test.svc.cluster.local:8080; echo
Hello World
```
