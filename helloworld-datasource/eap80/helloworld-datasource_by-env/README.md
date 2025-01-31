## Steps to deploy with helm
```
# curl -L https://mirror.openshift.com/pub/openshift-v4/clients/helm/latest/helm-linux-amd64 -o /usr/local/bin/helm
# chmod +x /usr/local/bin/helm

$ oc new-project eap8-demo
$ helm repo add jboss-eap https://jbossas.github.io/eap-charts/

$ git clone https://github.com/nagetsum/jboss-openshift-examples.git
$ cd jboss-openshift-examples/helloworld-datasource/eap80/helloworld-datasource_by-env
$ helm install helloworld-datasource -f helm/helm.yaml jboss-eap/eap8
```

## Steps to deploy by binary build without helm
```
$ oc new-project eap8-demo
$ git clone https://github.com/nagetsum/jboss-openshift-examples.git
$ cd jboss-openshift-examples/helloworld-datasource/eap80/helloworld-datasource_by-env
$ oc new-build --image=registry.redhat.io/jboss-eap-8/eap8-openjdk17-builder-openshift-rhel8:1.0.1.GA-11 --binary=true --name=helloworld-datasource-build-artifacts
$ oc start-build helloworld-datasource-build-artifacts --from-dir=. --follow

$ oc create -f ocp-manifest_for-binary-build/imagesteam_helloworld.yaml
$ oc create -f ocp-manifest_for-binary-build/build-config_helloworld.yaml
$ oc start-build helloworld-datasource
$ oc new-app helloworld-datasource --env-file=envfile_for_funtime/datasource.env
```

