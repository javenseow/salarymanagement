# salarymanagement
 
<h3>Build and Test</h3>
The building and testing of the application is done simultaneously using the following command: 
<br><code>mvnw install</code>

<h3>Run</h3>
To run the application, use the following command:
<br><code>mvnw spring-boot:run</code>

<h3>Assumptions</h3>
<body>
    Some assumptions that were made include:
    <li>startData of users can be after the date of entry i.e anticipating the user to come in at a later date</li>
</body> 

<h3>Design Decisions</h3>
<body>
    The structure of the project is separated into different components with the help of Spring's annotations. 
     This is to ensure Separations of Concerns between different components.
     They are separated into 5 main components namely <b>controller</b>, <b>helper</b>, <b>model</b>, <b>repository</b> and <b>service</b>.
     <br><br>
     On top of the usual 4 layers of controller, model, repository and service, a helper component was added to create methods that would help with tasks that might be repetitive.
     For example, the actual reading of the CSV file is abstracted from the service layer to the helper component since the reading of CSV files included other lower level details.
     This should help with allowing the code in achieving high cohesion and low coupling.
     <br><br>
     Within the helper component, a Response and Results object was added to handle responses made by the different API calls.
     This will help make things easier when there are more APIs to be introduced and more responses have to be included as well.
     All possible response messages are declared within the Response object and will be used by the different methods when returning responses (e.g. <code>CREATE_SUCCESS</code> and <code>INVALID_SALARY</code>.
     This was also introduced to allow for easier standardization of the response to be a JSON object upon replying to the user.
</body>
      
