This program will do the following:

1) Read all files in a directory with .json extension
2) For each file it encounters it will parse all json objects and ouput a CSV file
    a) The CSV file will have a first row which is a non duplicated list of ALL keys across ALL json objects in the file
    b) The rest of the lines in the CSV file will be each JSON Object in the file mapped to the full set of keys
    
    NOTE: There will be a CSV file that corresponds to every JSON file input. 
    

Inputs :

-i   Path to Input File Directory
-o   Path to Output File Directory


There is a test .json file included in this repo under the data directory.
