# C868
This project for C868. This class requires an application and some documents.

## Overview
This is a simple inventory system. You can manage each department's equipments. Administrator account can manage all departments' inventory but a user can only manage a department that the account blongs.
The hashed password is stored on the database.
Only administrator account can manage users.

## Technical Overview
* Using SQLite
* Using OpenSDK11
* OpenJavaFX11
* Gradle
* Created minimum JRE for the application(jlink)
* Password hashing

# Notes
* Making OpenJavaFX11 run any computer was a little tough work. If it is possible to use Java8, it is way easier. Since Java11 does not include JavaFX.
