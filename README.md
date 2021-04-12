# PiazzaClone

PiazzaClone is a clone of piazza. Here you can make posts and comments regarding the courses you take.

## Installation

Use maven to build the project.

```bash
mvn clean install
```

To create a .jar file with dependencies:

```bash
mvn clean compile assembly:single
```

## To start the javafx-app
Make sure you have an SQL-database with username: databaser124 and password: databaser124.
You can setup the SQL database with: "SetupPiazzaDatabase.sql"

```
cd Databaser
mvn javafx:run

```
You can log in using the pre-created users "testinstructor" and "teststudent", both with the password "test". These will give you the functionality for instructors and students. Optionally you can create your own user within the app.

## Jar-file is in /Databaser/target
