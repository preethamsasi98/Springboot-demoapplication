# Springboot-demoapplication
An app which upload and manages photo to AWS S3

How to run this project:

•	Download the whole project from here and import it any ide. 
•	Version used while developing Spring: 3.1.1 and java: 17.
•	Run the spring application 
•	For testing purpose install the postman application which helps in easily interacting with the spring application with GET,POST and DELETE requests.
•	The maximum size of the image that can be uploaded is 1MB.
•	Format for running HTTP requests for respective commands
Upload:  localhost:8080/upload  (POST)  with body containing key as image and value as the required image you are going to upload
Fetch : localhost:8080/download/{imagename} (GET) file gets downloaded if done in chrome directly.If done in postman you have to save the response to a file.
Delete: localhost:8080/{imagename} (DELETE) file gets deleted in AWS S3 storage
Update:  localhost:8080/Update/{imagename} (POST) old image gets deleted and replaced by the image you send (Body should contain key and value similar to upload )
List: localhost:8080/list (GET) All the images that are present in the S3 bucket can be displayed. This feature is useful to check the images uploaded without visiting AWS Management Console.
