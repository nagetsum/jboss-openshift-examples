## Steps to deploy:
```
git clone https://github.com/nagetsum/jboss-openshift-examples.git

oc new-project eap74-demo
oc create -f https://raw.githubusercontent.com/jboss-container-images/jboss-eap-openshift-templates/eap74/templates/eap74-basic-s2i.json
oc create -f https://raw.githubusercontent.com/jboss-container-images/jboss-eap-openshift-templates/eap74/eap74-openjdk17-image-stream.json

oc new-app --template=eap74-basic-s2i -p APPLICATION_NAME=helloworld-datasource -p IMAGE_STREAM_NAMESPACE=eap74-demo -p EAP_IMAGE_NAME=jboss-eap74-openjdk17-openshift:7.4 -p EAP_RUNTIME_IMAGE_NAME=jboss-eap74-openjdk17-runtime-openshift:7.4 -p SOURCE_REPOSITORY_URL=https://github.com/nagetsum/jboss-openshift-examples.git -p SOURCE_REPOSITORY_REF=master -p CONTEXT_DIR=helloworld-datasource/eap74/helloworld-datasource_by-env --env-file=jboss-openshift-examples/helloworld-datasource/eap74/helloworld-datasource_by-env/envfile_for_funtime/datasource.env -e JGROUPS_PING_PROTOCOL=LOCAL_PING
```

## Steps to deploy with binary build (without pulling files from GitHub when building an application image on OpenShift):
```
git clone https://github.com/nagetsum/jboss-openshift-examples.git

oc new-project eap74-demo
oc create -f https://raw.githubusercontent.com/jboss-container-images/jboss-eap-openshift-templates/eap74/eap74-openjdk17-image-stream.json
oc new-build jboss-eap74-openjdk17-openshift:7.4 --binary=true --name=helloworld-datasource-build-artifacts -e GALLEON_PROVISION_DEFAULT_FAT_SERVER="true"

oc start-build helloworld-datasource-build-artifacts --from-dir=./jboss-openshift-examples/helloworld-datasource/eap74/helloworld-datasource_by-env/ --follow
oc create -f jboss-openshift-examples/helloworld-datasource/eap74/helloworld-datasource_by-env/ocp-manifest_for-binary-build/imagesteam_helloworld.yaml
oc create -f jboss-openshift-examples/helloworld-datasource/eap74/helloworld-datasource_by-env/ocp-manifest_for-binary-build//build-config_helloworld.yaml
oc start-build helloworld-datasource
oc new-app helloworld-datasource -l app.openshift.io/runtime=jboss --env-file=jboss-openshift-examples/helloworld-datasource/eap74/helloworld-datasource_by-env/envfile_for_funtime/datasource.env -e JGROUPS_PING_PROTOCOL=LOCAL_PING
```
