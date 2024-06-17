# From: https://catalog.redhat.com/software/containers/fuse7/fuse-java-openshift-jdk11-rhel8/60891f51b6f68e29bbcc6cdb
# For login use the next command: docker login https://registry.redhat.io
# And set the Customer Portal Credentials
FROM registry.redhat.io/fuse7/fuse-java-openshift-jdk11-rhel8
USER jboss
WORKDIR /deployments
COPY --chown=jboss:jboss target/springboot-fuse-rest-demo-1.0.jar /deployments
EXPOSE 8080