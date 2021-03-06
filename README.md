
# Meeting Mangager

A semester project, writing a desktop application using [JavaFx](https://openjfx.io/) and [Hibernate ORM](https://hibernate.org/)

## Overview

### Student's info

* Student's name: Nguyễn Mạnh Tuấn - 1712875

* Student's email: azerprojects@gmail.com

### Project's overview

* Report: [report.md](https://github.com/Az3r/MeetingManager/blob/master/./docs/report.md)

* Database: MySql using MariaDB server

* Hibernate ORM for mapping domain model to relational database

* use FXML with [SceneBuilder](https://gluonhq.com/products/scene-builder/) support to create views

### Use case diagram
![](https://github.com/Az3r/MeetingManager/blob/master/./docs/use-case.png)

### [Requirement](https://github.com/Az3r/MeetingManager/blob/master/./docs/requirement.pdf)

### [Figma prototype](https://www.figma.com/file/uHQH9yLd98ozFIYeMp0gET/Javafx?node-id=0%3A1)
> Because i didn't have enough time, some features in prototype maybe missing compared to actual application

## Installation

This application requires Java version 11, [Maven](https://maven.apache.org/download.cgi) and a version of MySql Server

If you haven't installed MySql Server yet then you can download [MariaDB Server](https://mariadb.com/downloads/)

1. Login to the server and create database named **MeetingManager**.

        create database MeetingManager;

1. Clone the repository.

        git clone https://github.com/Az3r/MeetingManager.git

1. Open MeetingManager directory, then open *hibernate.cfg.xml* located in *./src/main/resources/hibernate.cfg.xml*:

    Find 2 properties called **connection.username** and **connection.password** and change them to your server's username and password.

    Depend on your version of MySql Server, you may need to edit the **dialect** property. Check [this list](https://docs.jboss.org/hibernate/orm/5.3/javadocs/org/hibernate/dialect/package-summary.html) to see which dialect you should use for your database server. I use MariaDB Server 10.5 so i choose *MariaDB103Dialect*.

    The **hbm2ddl.auto** tell hibernate what to do with the database when the ***Session Factory*** is created and closed. [Here are available options](https://docs.jboss.org/hibernate/orm/5.2/userguide/html_single/Hibernate_User_Guide.html#configurations-hbmddl). Some of them which i use are:
    * *create*: Database dropping will be generated followed by database creation.
    * *update*: Update the database schema.

1. Run *mvn clean install*.

1. Run *mvn javafx:run*.
    
## Usage
You can login either as an user using username: *user* and password: *123*, or as an admin with *admin* and *123*

10 users and 10 meetings are created each time application start, the code for creating samples is written in function *createSamples* in file **App.java** located in *src\main\java\com\azer\meetingmanager\App.java*

## References
* [Hibernate: save, persist, update, merge, saveOrUpdate](https://www.baeldung.com/hibernate-save-persist-update-merge-saveorupdate)

* [Hibernate 5 Bootstrapping API](https://www.baeldung.com/hibernate-5-bootstrapping-api)

* [Hashing a Password in Java](https://www.baeldung.com/java-password-hashing)

* [Hibernate Inheritance Mapping](https://www.baeldung.com/hibernate-inheritance)

* Various Indian tutorials