# EventBrite

This is a small rest-assured framework testing the creation and updates to an event on EventBrite using their open api.

Three requests have been automated, one get request which gets the organizational_id, the other two are post requests.

You can run these tests two different ways. 

The first way requires you to have maven installed on your computer. You can check if you have it installed by typing "mvn -v". If you have maven installed, download the repository as a .zip file, extract to a folder of your choice. Navigate to the folder in the command terminal and run the command "mvn clean test -D suiteXmlFile=testng.xml"

The other way is to clone this repository into your favorite IDE and then running the tests as a TestNG Suite by right clicking on the testng.xml file.

Similarly, you can right-click on pom.xml and run as Maven commands such as Maven Test, mvn clean test etc.

You can add more test cases to increase the coverage and practice
