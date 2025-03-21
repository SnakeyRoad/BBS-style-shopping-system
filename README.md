



- RUNNING AND VIEWING THE JAR BUILD ARTIFACT:

To run the build artifact in windows: press win+r, type: cmd for command prompt, navigate to directory containing the JAR file: "cd path\to\your\directory", run file using "java -jar ElectronicShopSystem.jar"

To run the build artifact in Linux:   Open a new Terminal, navigate to directory containing the JAR file: "cd path\to\your\directory"
Set the JAR file as executeable: "chmod +x ElectronicShopSystem.jar"
Verify: "ls -l ElectronicShopSystem.jar"
Output (on my Linux Mint): "-rwxr-xr-x 1 cpm cpm 25830 Mar  7 11:37 ElectronicShopSystem.jar"
Output interpretation: "-rwxr-xr-x: Permissions (owner can read/write/execute, group and others can read/execute) -> correctly set as executable!
Run file using: "java -jar ElectronicShopSystem.jar" 
See below on how to Login


- NOTES FOR BOTH WINDOWS AND LINUX:

Ensure Java is installed and added to your system's PATH, If you see "command not found" errors, check your Java installation. 
This project requires Java 8 or later to compile and run. Ensure you have the correct JDK installed.




- RUNNING AND VIEWING THE SOURCE CODE:

Navigate to directory: SOURCE CODE -> Extract Assignment1_SE_SourceCode_CP_Mol_2406930.zip -> Navigate to /src/electronicshop/ -> Open folder in IDE and run main class ElectronicShopSystem.java
All other classes can be viewed here too.


- !! [[[HOW TO LOGIN]]] !! 

When run, the main menu is shown. To log in as default user, use credentials:

user/password123 

To log in as Admin, use credentials: 

admin/admin123 

(Note that these are stored in the source code in clear text. To further harden the login was assumed beyond the scope of current assignment)

Try creating a custom user to test this functionality too. When logged in as admin, the option to view all existing users and their status is present. 
The Admin panel is fully tested and deemed fully functional
