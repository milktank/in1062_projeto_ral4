FROM weaveworksdemos/msd-java:jre-latest

WORKDIR /usr/src/app
COPY sqlrepo.jar ./app.jar

RUN	chown -R ${SERVICE_USER}:${SERVICE_GROUP} ./app.jar

USER ${SERVICE_USER}

ARG BUILD_DATE
ARG BUILD_VERSION
ARG COMMIT

LABEL org.label-schema.vendor="RenatoLeite" \
  org.label-schema.build-date="${BUILD_DATE}" \
  org.label-schema.version="${BUILD_VERSION}" \
  org.label-schema.name="Socks Shop: SQLRepo" \
  org.label-schema.description="REST API for DB Relational service" \
  org.label-schema.url="https://github.com/milktank/in1062_projeto_ral4/tree/master/sqlrepo" \
  org.label-schema.vcs-url="github.com/milktank/in1062_projeto_ral4.git" \
  org.label-schema.vcs-ref="${COMMIT}" \
  org.label-schema.schema-version="1.0"

ENTRYPOINT ["/usr/local/bin/java.sh","-jar","./app.jar", "--port=80"]
