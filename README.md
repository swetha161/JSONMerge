# JSONMerge
A program that can merge a series of files containing JSON array of Objects into a single or multiple files containing one JSON object  depending on the maximum file size requirement of the user.

<b>Language:</b><br>
Java

<b>Preliminary requirements to execute:</b><br>
Download Jar File for Json Object from the below link:<br>
http://search.maven.org/remotecontent?filepath=com/googlecode/json-simple/json-simple/1.1.1/json-simple-1.1.1.jar

<b>Steps to Execute:</b><br>
<i>Use the below command for compiling:</i><br>
javac -cp json-simple-1.1.1.jar Merge.java<br>
<i>Use the below command to generate a jar file:</i><br>
jar cfm Merge.jar Manifest.txt Merge.class<br>
<i>Use the below command to execute:</i><br>
java -jar Merge.jar<br>

