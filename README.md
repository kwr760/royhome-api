# RoyHome API Server

## Purpose

A spring rest api server with a postgres database connection.

Serving the resume of Kevin Roy stored in the postgres database.

## Installation

### Windows Set up

If installing to a new environment, the version might need to be updated

1. Install Postgres - postgres/postgres
2. Install pgAdmin
3. 

## Set Up

Running the server requires some environment to set up.  This was done to
prevent the secrets from being stored in the public repository.

After setting the environment variables, select the active spring profile
to run.

### Debug

Add this to

```text
Modify Run Configuration...
```

```text
ROYHOME_SSL_PASSWORD=<password>;SPRING_PROFILES_ACTIVE=dev
```

### Environment Variables


| Variable               | Use                           | suggested    |
| ---------------------- | ----------------------------- | ------------ |
| ROYHOME_SSL_PASSWORD   | Used to decrypt the HTTPS pks | \<password\> |
| POSTGRES_USERNAME      | Postgres' username            | postgres     |
| POSTGRES_PASSWORD      | Postgres' password            | password     |
| SERVER_PORT            | Port of the server            | 5000         |
| SPRING_PROFILES_ACTIVE | the active spring profile     | dev          |

### Active Spring Profile

Supported profiles: dev | prod | docker

* dev - assumes user, password, and port.  Did not test to ensure it works
  with these environments set.
* prod - removes the user, password, and port to secure the database and server
* docker - runs on a http server.  Avoid the need of the SSL password for
  the pks.  Used to run docker containers.

An additional way to set the active spring profile.

```text
-Dspring.profiles.active=dev
```

### Database

This server uses a Postgres database.

A local instance of the database can be installed and used.

To simplify a user trying to run this server.  The server can be used
running docker.  See below on the start this server with docker.

#### version

```text
production - postgres (PostgreSQL) 10.12 (Ubuntu 10.12-0ubuntu0.18.04.1)
development - postgres (PostgreSQL) 10.17 (Ubuntu 10.17-0ubuntu0.18.04.1)
```

#### installing postgres

```shell
sudo apt update
sudo apt install postgresql postgresql-contrib
```

#### initialize database
```shell
sudo -u postgres psql
```
```postgresql
ALTER USER postgres PASSWORD 'password';
CREATE USER server WITH PASSWORD 'password';
ALTER USER server WITH SUPERUSER CREATEDB CREATEROLE;
```

```shell
sudo -u postgres createdb royhome
```

### Running

#### locally

Ensure that the environmental variables have been set correctly.

Most IDE will let you run this through their UI.  As is different, I'll
go through running on the command line.

Example: Development

```shell
gradlew -Dspring.profiles.active=dev bootrun
```

#### Running tests locally

The project includes unit tests and a small Testcontainers-based integration check. On Windows (PowerShell) the easiest way to run tests locally is to ensure Docker Desktop is running, then run Gradle from the repository root.

```powershell
# Run the full test suite (will start Docker for Testcontainers-based tests)
./gradlew --no-daemon test

# Run a single test class (faster; avoids starting unrelated integration tests)
./gradlew --no-daemon test --tests "net.royhome.DbIntegrationTest"

# Clean build and tests
./gradlew --no-daemon clean test
```

### Regenerating the Detekt baseline

If you need to regenerate the Detekt baseline (for example after updating rules or bumping Detekt), you can use the Detekt CLI to create a new baseline file. From the repository root (PowerShell):

```powershell
# Download the detekt CLI "all" jar (adjust version if needed)
Invoke-WebRequest -Uri "https://repo1.maven.org/maven2/io/gitlab/arturbosch/detekt/detekt-cli/1.23.8/detekt-cli-1.23.8-all.jar" -OutFile .\detekt-cli-1.23.8-all.jar

# Run the CLI to create a baseline at config/detekt/detekt-baseline.xml
& "$env:JAVA_HOME\bin\java.exe" -cp .\detekt-cli-1.23.8-all.jar io.gitlab.arturbosch.detekt.cli.Main --input src/main/kotlin --config config/detekt/detekt.yml --create-baseline --baseline config/detekt/detekt-baseline.xml --parallel --report xml:build/reports/detekt/report_baseline.xml --debug

# The baseline file created will be at config/detekt/detekt-baseline.xml — commit it to the repo if you want CI to use it.
```

Notes
- Testcontainers requires Docker to be available on the machine. Start Docker Desktop before running the full test suite.
- If you see database migration or Flyway-related errors when running tests that load the full Spring context, try running the container-only test (above) or run unit tests that don't start the Spring context.
- If you want CI-style runs on a developer machine, use the `--no-daemon` flag above to better match CI behavior.

#### docker

Assuming that docker has been installed as expected.  This should run out of the box.

docker versions

```text
Docker version 20.10.7, build f0df350
docker-compose version 1.27.4, build 40524192
```

To start

```shell
docker-compose up
```

## HTTP and HTTPS

The easiest option here is to run as http, but as you know this is not as secure.  So by default https is on.

### Running as http

to turn off https change the correct application.yml

```yaml
server.ssl.enabled: false
```

### Running as https

To run with https, you need the pem keys and create a jks.

To do this you need to own an url, and create the keys using letsencrypt or something similar.

#### Creating certificates

I am using letsencrypt.

See

```shell
https://github.com/kwr760/royhome-web
```

#### Creating pks file

The pks file is generated from the certificate files.

```shell
# Convert the pem to p12
openssl pkcs12 -export -inkey $LOCATION/privkey.pem -in $LOCATION/fullchain.pem -name royhome -out $LOCATION/cert.p12 -passout pass:$PASSWORD
# convert the p12 to jks (java prefered cert)
keytool -importkeystore -srckeystore $LOCATION/cert.p12 -srcstoretype pkcs12 -srcalias royhome -destkeystore $LOCATION/cert.jks -destalias royhome -srcstorepass $PASSWORD -deststorepass $PASSWORD
```

## Flyway steps

The api server assumes that the database has been successfully generated, and it will run a verification check before it
is started.

Running flyway requires a flyway.cfg to be in the home directory.  There are others way to do this, but this seems
to be the easiest way without adding configuration to github.

### flyway.cfg

```
flyway.url=jdbc:postgresql://localhost:5432/royHome
flyway.user=postgres
flyway.password=password
flyway.encoding=UTF-8
```

### create or upgrade the royhome database

On an empty database,

```shell
gradlew flywaymigrate
```

To see the state of the flyway database

```shell
gradlew flywayinfo
```

## psql hints

Yet to come

## CI/CD

I am using Travis to verify my changes and push my code to my production machine in linode.

My goal is as cheap as possible.  So my web server and api server are running on the same small machine.  It is
my intention to only use one api server, even though in a real production I probably would separate them onto
individual servers.

Compiling the kotlin/java can be very intensive, so I use travis to build the libraries and push that to
the linode machine.
