# datamerge
Read JSON, XML and CSV File, make some validation and date format, Write then into one combined CSV File

How To

Before running the application, please follow below :

1.	Make sure you put the config.properties file to your Home directory (for example to C:\Users\{USER} for Windows OS) 
2.	Open the config.properties and change the "file.path" values. put your own path where you have your reports.csv, reports.json, reports.xml files. 
3.	Make sur that all library are in the lib directory where the jar exe file is.
4.	2 library are principaly used : 
-	commons-csv-1.7.jar : easly used for csv reader, parser and writer using org.apache.commons.csv ;
-	json-simple-1.1.jar for JSON reader, parsing and writing.
-	For XML reader, parsing and writing DOM lib which is the most used.
5.	To Run the application, after followed the above points, please run following command : java –jar datamerge.jar
	The final output file (combined.csv) will be generated on the "file.path" 
