# CRUD Item Management, Export-Import, Email Sending and Email Scheduler

This project is a CRUD (Create-Read-Update-Delete) application for managing data Items.
It also includes the ability to export data to PDF, Excel, and CSV formats, as well as import data from CSV and Excel files. In addition, there are endpoints for sending emails, either with just the email body or with attached files, and a scheduler for sending emails.
The project is built using Java language with Quarkus framework and Maven build tool, and uses PostgreSQL for database management.

## Prerequisites
-Java JDK 11 or higher
-Quarkus
-Maven
-Postgresql


## Getting Started
Setting up the Database
1. Create a new database in PostgreSQL.
2. Open the application.properties file located in the src/main/resources directory.
3. Initialize your port 
```shell script
.# application.properties
server.port={your port}
quarkus.datasource.jdbc.url=jdbc:postgresql://localhost:5432/{database_name}
quarkus.datasource.username={username}
quarkus.datasource.password={password}
```
4. Replace the quarkus.datasource.jdbc.url property with the JDBC URL for your PostgreSQL database.
5.  Replace the quarkus.datasource.username and quarkus.datasource.password properties with your PostgreSQL username and password, respectively.

## Running the Application
1. Clone the repository to your local machine.

2. Navigate to the project directory in your terminal.

3. Run the following command to build the project:
```shell script
./mvn clean package
```
4.Once the build is complete, run the following command to start the application:
```shell script
.java -jar target/{project-name}-runner.jar
```
Replace {project-name} with the name of your project.

5. The application will now be running on http://localhost:{your port}.


# Endpoints
## Item Management Endpoints


## GET /item
Returns a list of all Items in the database.

## GET /item/{id}
Returns the details of the Item with the specified ID.

## POST /item
Creates a new Item. The request body should contain the Item data in JSON format.

## PUT /item/{id}
Updates the details of the Item with the specified ID. The request body should contain the updated Item data in JSON format.

## DELETE /item/{id}
Deletes the Item with the specified ID.

## Data Export Endpoints
## GET /export/pdf
Exports all Item data to a PDF file and returns the file as a response.

## GET /export/excel
Exports all Item data to an Excel file and returns the file as a response.

## GET /export/csv
Exports all Item data to a CSV file and returns the file as a response.

# Data Import Endpoints
## POST /import/excel
Imports Item data from an Excel file. The request body should contain the Excel file.

## POST /import/csv
Imports Item data from a CSV file. The request body should contain the CSV file.

# Email Sending Endpoints
## POST /mail
Sends an email with the provided email body.

## POST /mail/excel
Sends an email with the provided email body and an attached Excel file.

















