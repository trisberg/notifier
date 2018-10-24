# Notifier
This sample requires riff v0.1.3 or later.

>To push to GCR set $DOCKER_ID to gcr.io/<project_id>

#### enable outbound egress traffic
Follow the instructions at [configure outbound network access](https://github.com/knative/docs/blob/master/serving/outbound-network-access.md) or you won't be about to get to your smtp host.

#### setup smtp host environment variables
I used a trial account with [SendGrid](https://signup.sendgrid.com/), and then set...

```sh
export SPRING_MAIL_HOST=smtp.sendgrid.net
export SPRING_MAIL_PORT=587
export SPRING_MAIL_USERNAME=apikey
export SPRING_MAIL_PASSWORD=<my_api_key>
export SPRING_MAIL_PROPERTIES_MAIL_SMTP_AUTH=true
export SPRING_MAIL_PROPERTIES_MAIL_SMTP_STARTTLS_ENABLE=true
``` 

#### create locally
```sh
riff function create java notifier \
  --local-path . \
  --image $DOCKER_ID/notifier \
  --env SPRING_MAIL_HOST=$SPRING_MAIL_HOST \
  --env SPRING_MAIL_PORT=$SPRING_MAIL_PORT \
  --env SPRING_MAIL_USERNAME=$SPRING_MAIL_USERNAME \
  --env SPRING_MAIL_PASSWORD=$SPRING_MAIL_PASSWORD \
  --env SPRING_MAIL_PROPERTIES_MAIL_SMTP_AUTH=$SPRING_MAIL_PROPERTIES_MAIL_SMTP_AUTH \
  --env SPRING_MAIL_PROPERTIES_MAIL_SMTP_STARTTLS_ENABLE=$SPRING_MAIL_PROPERTIES_MAIL_SMTP_STARTTLS_ENABLE \
  --verbose \
  --wait
```

#### create from git repo, pushing image to DockerHub
```sh
riff function create java hello \
    --git-repo https://github.com/doddatpivotal/notifier.git \
    --image $DOCKER_ID/notifier \
  --verbose \
  --wait
```
To set `$DOCKER_ID` do `export DOCKER_ID=your-docker-id`

#### invoke
```
riff service invoke notifier --json -- -d '{"to":"dpfeffer@pivotal.io", "from":"notifier@winterfell.live", "subject": "Test Subject", "body": "Test Body" }'
```
