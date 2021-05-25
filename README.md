## Set up

### Profile
Add the following to the Arguments of the gradle run Configuration

```
-Dspring.profiles.active=dev
```

### Environment Variables
Some environment variables are required to run

| Variable | Use |
| -------- | --- |
| ROYHOME_SSL_PASSWORD | Used to HTTPS pks |
| POSTGRES_USER | Postgres' username |
| POSTGRES_PASSWORD | Postgres' password |
