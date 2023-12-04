# Javalin Playground

## Configuration

* install Java 21 (preferable [Eclipse Temurin](https://adoptium.net/))
* install Gradle or use its wrapper `gradlew`
* install [Colima](https://github.com/abiosoft/colima) container runtime

## Run code reformat

`./gradlew ktlintCheck`

`./gradlew ktlintFormat`

## Run code statical analysis

`./gradlew detekt`

## Run tests

`./gradlew clean test`

`./gradlew clean integrationTest`

`./gradlew clean check`

## Check dependencies versions

`./gradlew dependencyUpdates`
