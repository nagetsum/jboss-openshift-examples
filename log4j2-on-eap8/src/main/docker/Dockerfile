FROM registry.redhat.io/jboss-eap-8/eap8-openjdk17-builder-openshift-rhel8:1.0.1.GA-11 AS builder

ENV GALLEON_PROVISION_FEATURE_PACKS org.jboss.eap:wildfly-ee-galleon-pack,org.jboss.eap.cloud:eap-cloud-galleon-pack

# Any pther available layers are listed in:
# https://docs.redhat.com/en/documentation/red_hat_jboss_enterprise_application_platform/8.0/html-single/using_jboss_eap_on_openshift_container_platform/index#assembly_capability-trimming-in-jboss-eap-for-openshift_default
ENV GALLEON_PROVISION_LAYERS cloud-default-config

# version 1.5.1.GA-redhat-00002 = JBoss EAP 8.0.5.1
# We can see JBoss EAP versions and Channel Manifest Version mappings from
# JBoss EAP 8 Maintenance Schedule
# https://access.redhat.com/articles/7039745
ENV GALLEON_PROVISION_CHANNELS org.jboss.eap.channels:eap-8.0:1.5.1.GA-redhat-00002

# Run the assemble script to provision the server.
RUN /usr/local/s2i/assemble

# Copy the JBoss EAP 8 server from the builder image to the runtime image.
FROM registry.redhat.io/jboss-eap-8/eap8-openjdk17-runtime-openshift-rhel8:1.0.1.GA-10 AS runtime

# Set appropriate ownership and permissions.
COPY --from=builder --chown=jboss:root $JBOSS_HOME $JBOSS_HOME

# Steps to add:
# (1) COPY the WAR/EAR to $JBOSS_HOME/standalone/deployments
#       with the jboss:root user. For example:
#     COPY --chown=jboss:root my-app.war $JBOSS_HOME/standalone/deployments
# (2) (optional) server modification. You can modify EAP server configuration:
#
#       * invoke management operations. For example
#
#        RUN $JBOSS_HOME/bin/jboss-cli.sh --commands="embed-server,/system-property=Foo:add(value=Bar)"
#
#        First operation must always be embed-server.
#
#       * copy a modified standalone.xml in $JBOSS_HOME/standalone/configuration/
#          for example
#
#      COPY --chown=jboss:root standalone.xml  $JBOSS_HOME/standalone/configuration

# deploy app
COPY --chown=jboss:root ROOT.war $JBOSS_HOME/standalone/deployments

# copy a modified standalone.xml which is enabled stdout handler for log4j2 in logging subsystem and json formatted access log in undertow subsystem
COPY --chown=jboss:root standalone.xml  $JBOSS_HOME/standalone/configuration

# To enable JSON logging immediately after JBoss EAP starts
COPY --chown=jboss:root logging.properties  $JBOSS_HOME/standalone/configuration

# Ensure appropriate permissions for the copied files.
RUN chmod -R ug+rwX $JBOSS_HOME