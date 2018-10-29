#!/usr/bin/env bash
riff service delete notifier

localpath=${1:-.}

riff function create java notifier \
    --local-path $localpath \
    --image $DOCKER_ID/notifier \
    --env SPRING_MAIL_HOST=$SPRING_MAIL_HOST \
    --env SPRING_MAIL_PORT=$SPRING_MAIL_PORT \
    --env SPRING_MAIL_USERNAME=$SPRING_MAIL_USERNAME \
    --env SPRING_MAIL_PASSWORD=$SPRING_MAIL_PASSWORD \
    --env SPRING_MAIL_PROPERTIES_MAIL_SMTP_AUTH=$SPRING_MAIL_PROPERTIES_MAIL_SMTP_AUTH \
    --env SPRING_MAIL_PROPERTIES_MAIL_SMTP_STARTTLS_ENABLE=$SPRING_MAIL_PROPERTIES_MAIL_SMTP_STARTTLS_ENABLE \
    --verbose
