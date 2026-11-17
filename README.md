# Password Generator

A locally running password generator built with Java 17 and the Spring Boot framework. It provides a browser-based interface, generates passwords from selected character groups, and can save an accepted password as a hash in an H2 database.

## Features

- Choose a password length from 8 to 32 characters.
- Select uppercase letters, lowercase letters, numbers, and symbols. If no groups are selected, the application uses its default character set.
- Generate passwords using Java's `SecureRandom`.
- Accept a generated password to store its SHA-256 digest in the database. The digest is Base64-encoded for storage; the original password is not saved.
- Use the included HTML interface served by the Spring application.

## Run locally

The application runs on the machine where it is started; it does not require a separate hosted service. Install Java 17 or newer, then run the Maven wrapper from the project directory:

**Windows**

```powershell
.\mvnw.cmd spring-boot:run
```

**macOS/Linux**

```shell
./mvnw spring-boot:run
```

When the application has started, open [http://localhost:8080/](http://localhost:8080/) to use the password generator.

## Using the interface

The page displays a generated password, a length slider, and checkboxes for the character groups to include. Use the regenerate button to get another password with the current options. Use the accept button to send the displayed password to the application for hashing and storage.

## View the database

The application uses a file-based H2 SQL database, stored under `data/`. While the application is running, open the H2 Console at [http://localhost:8080/h2-console](http://localhost:8080/h2-console) and use these connection settings:

| Setting | Value |
| --- | --- |
| JDBC URL | `jdbc:h2:file:./data/passdb` |
| User Name | `sa` |
| Password | Leave empty |

After connecting, find `PASSWORD_ENTITY` in the database objects list and double-click it to put its query into the SQL editor. Run the displayed query (or execute `SELECT * FROM PASSWORD_ENTITY;`) to view the saved records. The `PASSWORD` column contains Base64-encoded SHA-256 hashes, not the original passwords.

## Password storage note

This project uses SHA-256 to hash accepted passwords before saving them. SHA-256 is a general-purpose hash and is not suitable by itself for securely storing real user account passwords; production systems should use a password-hashing function designed for that purpose, with a unique salt.
