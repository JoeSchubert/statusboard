Copyright <2019> <Joseph Schubert - joseph.schubert@gmail.com>
This project is licensed under the terms of the MIT license.

This software is intended to provide a clean, easy and cross-platform method for tracking members on/off-board a cutter.

The current roster schema can be found as constants in src/statusboard/databaseHelpers/RosterDatabaseHelper.java
Existing databases could be migrated to sqlite using this schema, though that will not be automated. It will likely be easier to simply rebuild the database for most people.
For those who want to attempt this, it will require exporting an existing MS Access database as .csv (this can be done through excel), then moving the columns around to be in the proper order (remove headers). Schema order is as below:

ID     PAYGRADE      RANK       FIRST_NAME      LAST_NAME      DEPARTMENT      BARCODE      STATUS      LAST_SCAN


ID and LAST_SCAN should be left blank. All other fields must be filed in. Status must be either 0 or 1 indicating current status. Department and Rank must match values found in src/statusboard/Constants.java

Once this is completed the file can be saved as a .csv file and imported into a database called StatusBoard.db in a table called "roster". An online tool such as https://sqliteonline.com/ should be able to import the .csv into a database and allow downloading.

Currently accepted properties that can be controlled via a config.properties file in the base StatusBoard directory are:

NightMode=true
CutterName=USCGC Coast Guard Cutter
ScannerTimeThreshold=1000
audibleFeeback=true
autoDimEnabled=false
startDim=20:00:00
stopDim=06:00:00
DimPercent=50

These are the values that are used if no input is supplied.

NightMode is automatically added/changed when clicking the NightMode toggle in the main frame.
CutterName will set the cutter name at the top of the main frame and the title bar.
ScannerTimeThreshold is the time in milliseconds that the barcode scanner should complete sending it's keystrokes in.
audibleFeeback determines whether sounds will be played for barcode swipes
autoDimEnabled determines whether or not to dim the laptop screen
  startDim sets the time to start dimming the screen
  stopDim sets the time to return to full brightness
  DimPercent sets the brightness percentage

On windows the application can be launched by running Launch_StatusBoard.bat
On linux the application can be launched by running Launch_StatusBoard.sh (this may need to be chmod +x or set to be executable first)
