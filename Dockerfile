FROM ubuntu:latest
LABEL authors="Gonzalo"

ENTRYPOINT ["top", "-b"]