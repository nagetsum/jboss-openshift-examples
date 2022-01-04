# SmallRye FileSystem Config Source Demo

In addition to MP Config's standard ConfigSource of environment variables, java system properties, and the `META-INF/microprofile-config.properties`, SmallRye Config supports ConfigSource in **directory format**.

This Config Source can be used to read configuration from Kubernetes `ConfigMap`.

## Environment

- JBoss EAP 7.4
- EAP XP 3.0
- OpenShift 4.10 (note: EAP XP containers does not support OCP3.x)
- JDK11 (note: EAP XP containers only support JDK11, not JDK8)

## How to deploy
```
oc new-project mpconfig-demo
oc replace --force -f https://raw.githubusercontent.com/jboss-container-images/jboss-eap-openshift-templates/eap-xp3/jboss-eap-xp3-openjdk11-openshift.json
oc replace --force -f https://raw.githubusercontent.com/jboss-container-images/jboss-eap-openshift-templates/eap-xp3/templates/eap-xp3-basic-s2i.json

oc new-app --template=eap-xp3-basic-s2i \
 -p EAP_IMAGE_NAME=jboss-eap-xp3-openjdk11-openshift:3.0 \
 -p EAP_RUNTIME_IMAGE_NAME=jboss-eap-xp3-openjdk11-runtime-openshift:3.0 \
 -p IMAGE_STREAM_NAMESPACE=mpconfig-demo \
 -p SOURCE_REPOSITORY_URL=https://github.com/nagetsum/jboss-openshift-examples.git \
 -p SOURCE_REPOSITORY_REF=master \
 -p CONTEXT_DIR=smallrye_configsource-filesystem_demo \
 -p APPLICATION_NAME=configmap-demo

vim configmap-demo.yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: demo
  labels:
    app: configmap-demo
data:
  num.max: "10"
  num.size: "5"
  uri: "https://redhat.com/"

oc apply -f configmap-demo.yaml

oc edit dc/configmap-demo

apiVersion: apps.openshift.io/v1
kind: DeploymentConfig
...
    spec:
      containers:
      - env:
...
        - name: AUTO_DEPLOY_EXPLODED
          value: "false"
        - name: ENABLE_GENERATE_DEFAULT_DATASOURCE
          value: "false"
        - name: MICROPROFILE_CONFIG_DIR             <<<===
          value: "/bindings"                        <<<===
        resources:
          limits:
            memory: 1Gi
        terminationMessagePath: /dev/termination-log
        terminationMessagePolicy: File
        volumeMounts:                               <<<===
        - name: config-volume                       <<<===
          mountPath: /bindings                      <<<===
      dnsPolicy: ClusterFirst
      restartPolicy: Always
      schedulerName: default-scheduler
      securityContext: {}
      terminationGracePeriodSeconds: 75
      volumes:                                      <<<===
        - name: config-volume                       <<<===
          configMap:                                <<<===
            name: demo                              <<<===

# send test requests
curl -k https://configmap-demo-mpconfig-demo.apps.nagetsum-sno4.nagetsum.gss.cee.redhat.com/configmap-demo/api/configs
[{"configSourceName":"FileSystemConfigSource[dir=/bindings]","configSourceOrdinal":500,"lineNumber":-1,"location":"FileSystemConfigSource[dir=/bindings]","name":"num.max","rawValue":"10","sourceName":"FileSystemConfigSource[dir=/bindings]","sourceOrdinal":500,"value":"10"},{"configSourceName":"FileSystemConfigSource[dir=/bindings]","configSourceOrdinal":500,"lineNumber":-1,"location":"FileSystemConfigSource[dir=/bindings]","name":"uri","rawValue":"https://redhat.com/","sourceName":"FileSystemConfigSource[dir=/bindings]","sourceOrdinal":500,"value":"https://redhat.com/"},{"configSourceName":"FileSystemConfigSource[dir=/bindings]","configSourceOrdinal":500,"lineNumber":-1,"location":"FileSystemConfigSource[dir=/bindings]","name":"num.size","rawValue":"5","sourceName":"FileSystemConfigSource[dir=/bindings]","sourceOrdinal":500,"value":"5"}]

curl -k https://configmap-demo-mpconfig-demo.apps.nagetsum-sno4.nagetsum.gss.cee.redhat.com/configmap-demo/api/configs/num.max
10

curl -k https://configmap-demo-mpconfig-demo.apps.nagetsum-sno4.nagetsum.gss.cee.redhat.com/configmap-demo/api/configs/num.size
5

curl -k https://configmap-demo-mpconfig-demo.apps.nagetsum-sno4.nagetsum.gss.cee.redhat.com/configmap-demo/api/configs/uri
https://redhat.com/
```

## References

- SmallRye documentation FileSystem Config Source
  - https://smallrye.io/docs/smallrye-config/config-sources/config-sources.html#filesystem-config-source
- Using JBoss EAP XP 3.0.0 Chapter 5. Build and run microservices applications on the OpenShift image for JBoss EAP XP
  - https://access.redhat.com/documentation/en-us/red_hat_jboss_enterprise_application_platform/7.4/html-single/using_jboss_eap_xp_3.0.0/index#using-the-openshift-image-for-jboss-eap-xp_default
- Configuration for MicroProfile 2.0 specification
  - https://download.eclipse.org/microprofile/microprofile-config-2.0/microprofile-config-spec-2.0.html
