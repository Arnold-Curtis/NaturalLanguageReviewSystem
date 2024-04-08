# NaturalLanguageReviewSystem


#How to Run the System on your pc 

1. **Set up the development environment:**
   - Install the latest version of the Java Development Kit (JDK) from Oracle's official website (https://www.oracle.com/java/technologies/javase-jdk11-downloads.html).
   - Install an Integrated Development Environment (IDE) such as IntelliJ IDEA, Eclipse, or NetBeans. These IDEs provide a user-friendly interface for managing and running Java projects.

2. **Import the project into your IDE:**
   - Open your IDE.
   - Select the option to import an existing project or create a new project from existing sources.
   - Navigate to the location where you have the project files and select the root directory of the project.
   - Follow the prompts in the IDE to complete the project import process.

3. **Resolve dependencies (if applicable):**
   - If the project has any external dependencies (e.g., libraries or frameworks), the IDE may prompt you to download and install them.
   - Follow the instructions provided by the IDE to resolve any missing dependencies.

4. **Explore the project structure:**
   - Once the project is imported, you should see the project structure in the IDE's project explorer or package explorer view.
   - Locate the main Java package (e.g., `com.example.nlrs_main`) and navigate through the various classes and resources.

5. **Identify the main class:**
   - In this project, the `Main.java` class serves as the entry point for the application.
   - Locate this class within the project structure.

6. **Set up the run configuration:**
   - In your IDE, create a new run configuration or locate the existing one.
   - Configure the run configuration to execute the `Main` class as the entry point.
   - If necessary, specify any command-line arguments or other configuration options required by the application.

7. **Run the application:**
   - Once the run configuration is set up, click the "Run" button or use the appropriate shortcut key combination to start the application.
   - The application should launch, and you should see the initial user interface (e.g., the login screen).

8. **Explore the user interface:**
   - Interact with the application by testing different functionalities, such as logging in with different user accounts (admin, lecturer, student), navigating through the menus, and exploring the various features.
   - Observe the application's behavior and take note of any relevant outputs or logs displayed in the IDE's console or terminal.

9. **Debug the application (optional):**
   - If you encounter any issues or want to step through the code, set breakpoints in the relevant classes or methods.
   - Use the debugging tools provided by your IDE to step through the code, inspect variable values, and identify any potential issues or bugs.

10. **Modify the code (optional):**
    - If you want to make changes to the application's functionality or user interface, locate the relevant Java files and FXML files in the project structure.
    - Modify the code or FXML markup as desired, save the changes, and rerun the application to observe the effects of your modifications.
   
      

Here in the simplest way I could have offered an overview and explanation of my code and thought process, to how i came and developed a full working project.




















# The Main Class 

Alright, friends! Buckle up because we're about to dive into the heart of this Java application. The `Main` class is where the magic happens.

Let's start with the `main` method, the entrance to our application. This kicks things off by launching the JavaFX application.

Now, the `start` method is where everything begins. It's like the grand opening ceremony, setting the stage for our application's user interface. Here's how it goes down:

1. We create an instance of the `Stage` class, which is essentially the window where our application will live and breathe.

2. Next, we load the `LoginPage.fxml` file, which contains the layout and design for our login screen. It's like the bouncer at a fancy club, making sure only the cool kids (with valid credentials) get in.

3. After loading the login screen, we create a new `Scene` object, which is the canvas where our UI elements will be painted. It's like the stage at a concert, where the performers (our UI components) will strut their stuff.

4. We set the title of our stage to "Login Page" because, well, that's what it is! Nothing fancy, just keeping it real.

5. We assign the newly created `Scene` to our `Stage` object, essentially setting the backdrop for our application.

6. Finally, we call the `show` method on our `Stage`, unveiling our application to the world like a grand unveiling ceremony. Ta-da! 🎉

# Login_Controller.java

This class serves as the controller for the login functionality within the application. It implements the `Initializable` interface, allowing for initialization logic when the associated FXML file is loaded.

The `initialize` method sets up an image for the login screen, ensuring a visually appealing user interface.

The `loginButton` method is responsible for handling the login process. It retrieves the entered user ID and password, validates the credentials against the database using the `getLoginDetailsFromDB` method from the `ReadWriteDB` class, and determines the account type (Student, Lecturer, or Admin) based on the user's selection in the choice box. Upon successful login, it triggers the appropriate event handler to load the corresponding scene for the authenticated user.

Additionally, the class includes methods for retrieving the user's account type from the database (`getUserAccountType`) and setting event handlers for the login process (`setOnLoginHandler`). It also provides methods for setting and retrieving the logged-in user's ID and the student's ID, if applicable.

# Login_Page.java

This class extends the `Users` class and serves as a base class for handling login-related functionalities within the application.

It defines two `StringProperty` instances (`userID` and `password`) to store and manage the user's login credentials. The class provides getter and setter methods for accessing and modifying these properties.

While this class doesn't contain extensive functionality on its own, it acts as a utility class for other classes involved in the login process, such as `Login_Controller`, by providing a consistent way to handle and manage user credentials.




# LecturerWindow_Controller.java

This class serves as the controller for the Lecturer Window, which is the primary interface for lecturers within the application. It handles various functionalities specific to lecturers, such as displaying their assigned units, updating personal details, and accessing performance reports.

The class implements several methods to load different scenes or windows based on the lecturer's actions. For example, the `changeAllLecturerDetails` method opens a new window where lecturers can update their personal information. Similarly, the `changeLecturerContactDetails` and `changeLecturePassword` methods allow lecturers to update their contact details and password, respectively.

One of the key features of this class is the `loadLecturerPerfomanceReport` method, which opens a performance report window for a specific unit when the corresponding button is clicked. This method retrieves the unit name from the clicked button and passes it along with the lecturer's ID to the `PerformanceReport_Controller`.

The class also includes methods for setting and retrieving the lecturer's ID, as well as fetching unit names from the database and displaying them on the buttons in the Lecturer Window.





# StudentsF_Controller.java

This class acts as the controller for the Student Dashboard, providing functionality for students to review their answers for various units. It implements the `Initializable` interface, allowing for initialization logic when the associated FXML file is loaded.

The `initialize` method sets up an image for the Student Dashboard and calls the `setUnitButtonsFromDB` method, which retrieves unit names from the database and assigns them to the corresponding buttons on the Student Dashboard.

The `loadAnswerReviewsScene` method is triggered when a student clicks on a unit button. It opens a new window titled "Answer Review," passing the selected unit name and the student's ID to the `AnswerReview_Controller`. This allows students to review their answers for the chosen unit.

# ManageStudents_Controller.java

This class handles the management of student accounts within the application. It provides functionality for an admin user to register new students, deactivate existing student accounts, and perform other administrative tasks related to student management.

The class implements several methods to load different scenes or windows based on the admin's actions. For example, the `loadRegisterStudentScene` method opens a new window for registering a new student account, while the `loadDeActivateStudentAccScene` method opens a window for deactivating an existing student account.

Additionally, the class includes methods for displaying the admin's name and the current date and time on the "Manage Students" window. It also provides functionality for the admin to view student feedback and lecturer comments by opening respective windows.





# ManageLecturers_Controller.java

Similar to the `ManageStudents_Controller`, this class handles the management of lecturer accounts within the application. It provides functionality for an admin user to register new lecturers, deactivate existing lecturer accounts, and perform other administrative tasks related to lecturer management.

The class implements methods to load different scenes or windows based on the admin's actions, such as `loadRegisterLecturerScene` for registering a new lecturer account and `loadDeActivateLecturerAccScene` for deactivating an existing lecturer account.

Like the `ManageStudents_Controller`, this class also includes methods for displaying the admin's name and the current date and time on the "Manage Lecturers" window. It provides functionality for the admin to view student feedback and lecturer comments by opening respective windows.

# StudentsFeedBackHomeWindow_Controller.java

This Java file contains the controller class `StudentsFeedBackHomeWindow_Controller` for a JavaFX application. It handles the functionality of the home window where an admin can view and manage student feedback for different units.

1. The class starts by importing necessary classes and packages required for database connectivity, natural language processing, and JavaFX components.

2. The `initialize` method is called when the controller is first loaded. It takes an `adminID` as a parameter, which is used to retrieve the admin's name from the database and display it on the dashboard. It also loads the names of available units onto the buttons displayed on the home window.

3. The `setAdminNameOnDashboard` method retrieves the admin's first and last name from the database using the provided `adminID` and sets it on the `adminNameId` label.

4. The `loadUnitNamesToButtons` method fetches the names of units from the database and sets them on the corresponding buttons (`unit1` to `unit6`) on the home window.

5. The `loadUnitFeedback` method is called when a user clicks on one of the unit buttons. It opens a new window (`StudentsFeedBackListWindow.fxml`) and passes the selected unit name and `adminID` to the `DisplayFeedBack_Controller` class.

6. The `openUnitFeedBack` method is a helper method that loads the `StudentsFeedBackListWindow.fxml` file and creates a new stage to display the feedback list for the selected unit.

7. The `loadAdminDashboard` method is called when the user wants to return to the admin dashboard. It closes the current stage.

8. The `loadCreateReviewStage` method opens a new stage (`CreateReview.fxml`) where the admin can create a new review for a lecturer.

9. The `loadChooseLecturerScene` method opens a new stage (`ChooseLecturer.fxml`) where the admin can choose a lecturer to view their performance report or feedback.

10. The `viewLecturerCommentsScene` method calls the `openLecturerComments` method, which opens a new stage (`LecturerCommentsHome.fxml`) to display lecturer feedback.

11. The `openLecturerComments` method loads the `LecturerCommentsHome.fxml` file and creates a new stage to display the lecturer feedback home page.





#LecturerCommentsController.java
This Java file serves as the controller class `LecturerCommentsController` for an application designed to manage lecturer feedback. It's a part of the NLRS (Natural Language Review System) project. Here's a breakdown of its functionalities:

Imports and Initialization:

The file begins by importing necessary classes and packages for database connectivity, JavaFX components, and image handling.
The initialize method sets up the initial state of the controller, loading an image for display.
Admin Name Display:

The setAdminNameOnDashboard method retrieves the admin's name from the database using the provided adminID and displays it on the dashboard.
Loading Lecturer Comments:

The loadLecturerComments method retrieves comments made on the performance report for a particular lecturer and displays them in a ListView.
Setting Unit Name and Admin Name:

The setUnitName method sets the unit name and admin ID, updating the dashboard with the admin's name and loading comments for the specified unit.
The setAdminNameOnDashboard method retrieves and sets the admin's name based on the provided admin ID.
Navigation and Interaction:

Methods like `loadAdminDashboard`, `loadCreateReviewStage`, `loadChooseLecturerScene`, and `viewStudentFeedBackScene`handle navigation to different parts of the application and user interactions.
These methods open new stages or scenes for tasks like returning to the admin dashboard, creating a new review, choosing a lecturer, or viewing student feedback.
Closing the Application:

The closeButtonAction method closes the current stage and exits the application when the logout button is clicked.
Exception Handling:

Exception handling is implemented to manage potential errors during database operations or file loading.
This controller facilitates the display of lecturer feedback comments, provides functionality to navigate within the application, and ensures a smooth user experience




# LectureCommentsHome_Controller.java

This Java file serves as the controller class `LectureCommentsHome_Controller` for an application component responsible for managing lecturer feedback. Let's dive into its functionality:

1. **Imports and Initialization**: 
    - The file begins by importing necessary classes and packages for database connectivity, JavaFX components, and Stanford NLP.
    - The `initialize1` method initializes the controller with the provided admin ID, loading unit names onto buttons and setting up the dashboard with admin details.

2. **Admin Name Display**: 
    - The `setAdminNameOnDashboard` method retrieves the admin's name from the database using the provided `adminID` and displays it on the dashboard.

3. **Loading Unit Names to Buttons**: 
    - The `loadUnitNamesToButtons` method retrieves unit names from the database and assigns them to respective buttons for selection.

4. **Opening Unit Feedback**: 
    - The `loadUnitFeedback` method handles the action event when a unit button is clicked, extracting the unit name and initiating the display of feedback for that unit.

5. **Navigation and Interaction**:
    - Methods like `loadAdminDashboard`, `loadCreateReviewStage`, `loadChooseLecturerScene`, and `viewStudentFeedBackScene` manage navigation to different parts of the application and user interactions.
    - These methods open new stages or scenes for tasks like returning to the admin dashboard, creating a new review, choosing a lecturer, or viewing student feedback.

6. **Closing the Application**:
    - The `closeButtonAction` method closes the current stage and exits the application when the logout button is clicked.

7. **Exception Handling**: 
    - Exception handling is implemented to manage potential errors during database operations or file loading.

This controller facilitates the navigation through various sections of the application, displaying feedback for different units, and ensuring a seamless user experience.






#DisplayFeedBack_Controller.java
This Java file acts as the controller class DisplayFeedBack_Controller for handling the display of feedback in a JavaFX application.

Initialization and Data Loading:

The initialize method initializes the controller with the provided unit name and admin ID. It loads feedback for the specified unit and sets admin details on the dashboard.
Admin Name Display:

The setAdminNameOnDashboard method retrieves the admin's name from the database using the provided admin ID and displays it on the dashboard.
Loading Feedback:

The loadFeedBack method retrieves feedback for the specified unit from the database and displays it in a ListView.
User Interface Interaction:

Methods like loadAdminDashboard, loadCreateReviewStage, loadChooseLecturerScene, and viewLecturerCommentsScene handle user interactions for navigating to different parts of the application.
Exception Handling:

Exception handling is implemented to manage potential errors during database operations or file loading.
This controller facilitates the display of feedback, ensures the visibility of admin details, and provides seamless interaction within the application




# Admin_Controller.java

This Java file serves as the controller class `Admin_Controller` for managing the administrative tasks in a JavaFX application.

1. **Initialization and Data Loading**:
   - The `initializeAttributes` method initializes the controller with the provided admin ID and sets up the UI components.
   - The `setAdminNameOnDashboard` method retrieves the admin's name from the database using the provided admin ID and displays it on the dashboard.

2. **User Interface Setup**:
   - The `initialize` method sets up the UI components by loading images and setting the current date.

3. **User Interaction Handling**:
   - Methods like `viewStudentFeedBackScene`, `viewLecturerCommentsScene`, `loadManageStudentsScene`, `loadChooseLecturerScene`, `loadManageLecturersScene`, and `loadCreateReviewStage` handle user interactions for navigating to different parts of the application.

4. **Feedback and Comments**:
   - Methods like `openStudentFeedback` and `openLecturerComments` facilitate opening feedback and comments sections for viewing.

5. **Exception Handling**:
   - Exception handling is implemented to manage potential errors during database operations or file loading.

This controller provides functionalities for managing students, lecturers, feedback, and reviews, ensuring efficient administration within the application.




# CreateReview_Controller.java

This Java file serves as the controller class `CreateReview_Controller` for creating and managing review forms in a JavaFX application.

1. **Initialization and UI Setup**:
   - The `initialize` method initializes the UI components and sets up event handlers for buttons and choice boxes.
   - The `handleCategorySelection` method handles the selection of categories and updates the question text field accordingly.
   - The `addQuestion` method adds questions to the list view based on the selected category and user input.

2. **Database Operations**:
   - The `saveFormDetails` method saves the review form details, including the review title, selected lecturer, and questions, into the database.
   - The `saveQuestions` method saves individual questions along with their categories, units, and associated form ID into the database.

3. **Data Retrieval**:
   - The `fetchLecturers` and `fetchUnits` methods retrieve data from the database, specifically lecturer names and unit names, to populate choice boxes for selection.

4. **Utility Methods**:
   - The `getCategoryId` method maps category names to their corresponding IDs for database storage.

5. **User Interface**:
   - The UI allows users to select a category, add questions, select a lecturer, choose a unit, and enter a review title.
   - Error messages are displayed if there are issues with database connections or data validation during form submission.

This controller facilitates the creation of review forms by providing functionalities for adding questions, selecting categories, and saving form details into the database.





# DeactivateLecturerAcc_Controller.java

This Java file serves as the controller class `DeactivateLecturerAcc_Controller` for deactivating a lecturer's account in a JavaFX application.

1. **Initialization and UI Setup**:
   - The `initialize` method initializes the UI components and sets up the deactivation icon.

2. **Event Handling**:
   - The `closeButtonAction` method handles the closing of the window.
   - The `searchButtonAction` method retrieves details of the lecturer based on the entered user ID.
   - The `deactivationButtonAction` method initiates the deactivation process for the lecturer's account.

3. **Database Operations**:
   - The `confirmPasswordWithDB` method verifies the entered admin password with the database.
   - The `accountDeactivation` method deactivates the lecturer's account in the database.

4. **Data Retrieval**:
   - The `getLecturerDetailsFromDB` method retrieves details of the lecturer, such as first name, last name, user type, and account status, from the database.

5. **Password Verification**:
   - The `verifyAdminPasswords` method validates the entered admin passwords and initiates account deactivation if the passwords match and are correct.

6. **Error Handling**:
   - Error messages are displayed to guide the user in case of incorrect inputs, database connection failure, or account deactivation failure.

This controller facilitates the deactivation of a lecturer's account by validating admin passwords, retrieving lecturer details from the database, and updating the account status accordingly.



# ChooseLecturerController.java

This Java file serves as the controller class `ChooseLecturerController` for selecting a lecturer and choosing a unit to generate performance reports.

1. **Initialization and UI Setup**:
   - The `initialize` method initializes the UI components and loads lecturer buttons dynamically based on data retrieved from the database.

2. **Loading Lecturer Buttons**:
   - The `loadLecturerButtons` method retrieves lecturer details from the database and dynamically creates buttons for each lecturer.

3. **Opening Performance Report Scene**:
   - The `openLecturerPerformanceReport` method opens the "Choose Unit" window when a lecturer button is clicked.
   - It sets the lecturer ID and handles the selection of a unit to generate the performance report.

4. **Handling Logout**:
   - The `handleLogout` method handles the logout action by closing the current window and exiting the application.

5. **Loading Performance Report Scene**:
   - The `loadPerformanceReportScene` method loads the performance report scene after selecting a unit for a specific lecturer.
   - It initializes the `PerformanceReport_Controller` and sets the lecturer ID and selected unit name.

6. **Setter Method**:
   - The `setForPerformanceReport` method sets a boolean flag indicating whether the action is for generating a performance report.

This controller facilitates the selection of a lecturer, choosing a unit, and generating performance reports for the selected lecturer and unit.



# UpdateAllLectureDetails_Controller.java

This Java file serves as the controller class `UpdateAllLectureDetails_Controller` for updating all details of a lecturer.

1. **Initialization**:
   - The `initialize` method initializes the controller with the lecturer's ID.

2. **Updating Details**:
   - The `updateAllDetails` method updates all details of the lecturer based on the entered values in the text fields.
   - It retrieves data from the text fields and executes an SQL update query to update the details in the database.

3. **Prompting for Password**:
   - The `promptForPassword` method prompts the user to enter their password to confirm the update.
   - It opens a new window for password confirmation using `UpdateConfirmationController`.

4. **Validating Password**:
   - The `validatePassword` method validates the entered password against the stored password in the database.
   - It executes an SQL query to retrieve the stored password and compares it with the entered password.

5. **Closing Window**:
   - The `closeButtonAction` method handles the action of closing the window and exiting the application.

This controller facilitates the updating of all details of a lecturer by retrieving data from text fields, validating the password, and executing an SQL update query.




#DeactivateStudentAcc_Controller
Which is responsible for deactivating student accounts. Here's a summary of its functionality:

1. **FXML Elements**: The controller class contains several FXML elements such as buttons, labels, text fields, and an image view.

2. **Initialization**: The `initialize` method sets an image to the `deactivationIcon` ImageView.

3. **Close Button Action**: The `closeButtonAction` method handles the action of closing the window and exiting the application when the close button is clicked.

4. **Search Button Action**: The `searchButtonAction` method is invoked when the search button is clicked. It retrieves the student's details from the database based on the entered user ID.

5. **Deactivation Button Action**: The `deactivationButtonAction` method is triggered when the deactivation button is clicked. It also retrieves the student's details from the database based on the entered user ID.

6. **Verify Admin Passwords**: The `verifyAdminPasswords` method compares the entered admin password with the stored admin password to validate the deactivation process.

7. **Confirm Password with DB**: The `confirmPasswordWithDB` method verifies the entered admin password with the one stored in the database.

8. **Account Deactivation**: The `accountDeactivation` method updates the status of the student's account to deactivate it in the database.

9. **Get Student Details from DB**: The `getStudentDetailsFromDB` method retrieves the student's details from the database based on the provided user ID.

This controller provides functionality to deactivate student accounts by verifying the admin password and updating the account status in the database.




# StudentRegistration_Controller.java

This Java file serves as the controller class `StudentRegistration_Controller` for handling student registration functionality.

1. **FXML Elements**:
   - Various FXML elements such as buttons (`cancelButton`, `studentRegistrationButtonid`), labels (`registrationSuccessMessageLabel`, `registrationMessageFailureLabel`), password field (`defaultPasswordTF`), and text fields (`firstNameTF`, `lastNameTF`, `courseNameTF`, `phoneNumberTF`, `emailTF`, `countryTF`, `dateOfBirthTF`).

2. **Cancel Button Action**:
   - The `cancelButton3Action` method closes the registration window when the cancel button is clicked.

3. **Registration Button Action**:
   - The `registrationButton1Action` method handles the registration process, calling the `avoidDuplication` method to check for duplicate entries before proceeding.

4. **Student Registration**:
   - The `studentRegistration` method inserts the student's registration details into the database if there are no duplicates, setting default values for user ID, password, user type, and user status.

5. **Avoid Duplication**:
   - The `avoidDuplication` method checks for duplicate user entries in the database. If duplicates are found, it displays an error message; otherwise, it proceeds with the registration process.

6. **Initialization**:
   - In the `initialize` method, an image is set to the `registrationImage1` ImageView.

This controller facilitates student registration by handling form submissions, checking for duplicate entries, and providing appropriate feedback messages.




# LecturerRegistration_Controller.java

This Java file serves as the controller class `LecturerRegistration_Controller` for handling lecturer registration functionality.

1. **FXML Elements**:
   - Various FXML elements such as buttons (`cancelButton`, `lecturerRegistrationButtonid`), labels (`registrationSuccessMessageLabel`, `registrationMessageFailureLabel`), password field (`defaultPasswordTF`), and text fields (`firstNameTF`, `lastNameTF`, `courseNameTF`, `phoneNumberTF`, `emailTF`, `countryTF`, `dateOfBirthTF`, `unitNameTF`).

2. **Initialization**:
   - The `initialize` method sets an image to the `registrationImage` ImageView.

3. **Cancel Button Action**:
   - The `cancelButton2Action` method closes the registration window when the cancel button is clicked.

4. **Registration Button Action**:
   - The `registrationButtonAction` method validates the input fields and calls the `avoidDuplication` method to check for duplicate entries before proceeding with registration.

5. **Field Validation**:
   - The `validateFields` method ensures that all required fields are filled out before registration.

6. **Clearing Fields**:
   - The `clearFields` method clears all text fields after a successful registration.

7. **Lecturer Registration**:
   - The `lecturerRegistration` method inserts the lecturer's registration details into the database.
   - It sets default values for user ID, password, user type, and user status.
   - Additionally, it inserts the lecturer's unit name into the `units` table.

8. **Avoiding Duplication**:
   - The `avoidDuplication` method checks for duplicate lecturer entries in the database before registration.
   - If duplicates are found, it displays an error message; otherwise, it proceeds with the registration process.

This controller facilitates lecturer registration by handling form submissions, checking for duplicate entries, and providing appropriate feedback messages.





# PerformanceReport_Controller.java

This Java file serves as the controller class `PerformanceReport_Controller` for managing performance reports.

1. **FXML Elements**:
   - FXML elements include an `AnchorPane` for displaying charts, `PieChart` for performance visualization, `ListView` for displaying strengths, weaknesses, and neutrals, `Label` for displaying performance comments, `TextArea` for entering comments, `Button` for submitting comments, and `TextFlow` for displaying report analysis description.

2. **Initialization**:
   - The `initialize` method initializes the controller with the lecturer's ID and the unit name.
   - It initializes the UI elements by setting the pie chart data, strengths, weaknesses, neutrals, and performance comments.

3. **Submit Click Event**:
   - The `submitClickEvent` method handles the event when the submit button is clicked.
   - It calls the `storeLecturerComment` method to store the lecturer's comment in the database.

4. **Initialize UI**:
   - The `initializeUI` method initializes the UI elements by setting data from the `PerformanceReport` object.
   - It sets the pie chart data, strengths, weaknesses, neutrals, and performance comments based on the report.

5. **Print Description On Text Flow**:
   - The `printDescriptionOnTextFlow` method generates a descriptive text based on the performance report analysis.
   - It analyzes the strengths, weaknesses, and neutrals to provide a summary of the lecturer's performance.

6. **Store Lecturer Comment**:
   - The `storeLecturerComment` method stores the lecturer's comment in the database.
   - It retrieves the comment from the text area and executes an SQL insert query to store it in the `lecfeedback` table.

This controller manages the display of performance reports, generates descriptive analysis texts, and handles the storage of lecturer comments in the database.




# AnswerReview_Controller.java

This Java file serves as the controller class `AnswerReview_Controller` for managing the review of questions and providing feedback.

1. **FXML Elements**:
   - FXML elements include a `VBox` for containing question review containers, `Label` for displaying messages, and `TextArea` for entering reviews.

2. **Initialization**:
   - The `initialize` method initializes the controller with the selected unit name.
   - It sets up question review containers by retrieving questions from the database.

3. **Submit Reviews**:
   - The `submitReviews` method handles the event when the submit button is clicked.
   - It iterates through each question review container, retrieves the review, analyzes sentiment, and stores feedback in the database.

4. **Set Questions From DB**:
   - The `setQuestionsFromDB` method retrieves questions from the database based on the selected unit name and populates question review containers.

5. **Store Feedback**:
   - The `storeFeedback` method stores feedback in the database.
   - It retrieves the lecturer ID for the unit, determines the category ID of the question, and inserts feedback data into the `feedback` table.

6. **Analyze Sentiment**:
   - The `analyzeSentiment` method analyzes the sentiment of a text using the `NaturalLanguageAnalyzer` class.

7. **Set Student ID**:
   - The `setStudentID` method sets the student ID for whom the feedback is being provided.

8. **Get Category ID**:
   - The `getCategoryID` method retrieves the category ID of a question based on its text.

9. **Get Lecturer ID for Unit**:
   - The `getLecturerIDForUnit` method retrieves the lecturer ID associated with a unit from the database.

10. **Question Review Container**:
    - The `QuestionReviewContainer` class encapsulates a question label, review text area, and category ID within a VBox.

This controller manages the review of questions, sentiment analysis of reviews, and storage of feedback in the database.




# ChooseUnitController.java

This Java file serves as the controller class `ChooseUnitController` for selecting units associated with a lecturer.

1. **FXML Elements**:
   - FXML elements include an `HBox` container for unit buttons.

2. **Initialization**:
   - The `initialize` method initializes the controller.

3. **Load Unit Buttons**:
   - The `loadUnitButtons` method retrieves unit names associated with the lecturer from the database and creates buttons for each unit.
   - Each button is associated with an event handler to handle unit selection.

4. **Handle Unit Selection**:
   - The `handleUnitSelection` method is triggered when a unit button is clicked.
   - It sets the selected unit name and closes the stage.

5. **Close Stage**:
   - The `closeStage` method closes the current stage.

6. **Set Lecturer ID**:
   - The `setLecturerID` method sets the lecturer ID and loads unit buttons.

7. **Get Selected Unit Name**:
   - The `getSelectedUnitName` method returns the selected unit name.

8. **Set Next Action**:
   - The `setNextAction` method sets the next action to be performed after unit selection.

This controller facilitates the selection of units associated with a lecturer and allows users to choose a unit by clicking on the corresponding button.





# PerformanceReport.java

This Java file represents the `PerformanceReport` class responsible for generating performance reports for lecturers based on feedback data.

1. **Constructor**:
   - Initializes the `PerformanceReport` object with the lecturer's ID and unit name.
   - Initializes a map to store category scores.

2. **Fetch Data**:
   - Retrieves feedback data from the database for the specified lecturer and unit.
   - Calculates average scores for each category and stores them in the `categoryScores` map.
   - Calculates the total score based on average category scores.
   - Stores category scores and the performance report in the database.

3. **Getters**:
   - `getPieChartData()`: Generates pie chart data for displaying category-wise scores.
   - `getStrengths()`, `getNeutrals()`, `getWeaknesses()`: Retrieves strengths, neutrals, and weaknesses based on category scores.
   - `getTotalScore()`: Returns the total score of the lecturer.
   - `getPerformanceComment()`: Generates a performance comment based on the total score.

4. **Helper Methods**:
   - `storeCategoryScores()`: Stores category scores in the database.
   - `storePerformanceReport()`: Stores the performance report in the database.
   - `getCategoryName()`: Returns the category name based on the category ID.

This class facilitates the generation of performance reports for lecturers by fetching feedback data, calculating scores, and providing methods to retrieve relevant information for display.





# UpdateConfirmationController.java

This Java file represents the `UpdateConfirmationController` class, which handles password confirmation for updating lecturer details.

1. **Initialization**:
   - Initializes the controller with the lecturer's ID.

2. **Handling Update Button Click**:
   - The `handleUpdateButtonClick` method is invoked when the update button is clicked.
   - It validates the entered password against the stored password in the database.
   - If the password is correct, it displays a success message and proceeds with the update process.
   - If the password is incorrect, it displays a failure message.

3. **Password Validation**:
   - The `validatePassword` method validates the entered password against the stored password in the database.
   - It retrieves the stored password from the database based on the lecturer's ID and compares it with the entered password.

4. **Update Process**:
   - If the password validation is successful, it proceeds with the update process by calling the `updateAllDetails` method in the parent controller (`UpdateAllLectureDetails_Controller`).

This controller facilitates password confirmation before updating lecturer details by validating the entered password against the stored password in the database. If the password is correct, it proceeds with the update process; otherwise, it displays an error message.






# UpdateLecturerContactDetails_Controller.java

This Java file represents the `UpdateLecturerContactDetails_Controller` class, which handles updating lecturer contact details.

1. **Initialization**:
   - Initializes the controller with the lecturer's ID.

2. **Updating Contact Details**:
   - The `updateContactDetails` method is invoked when the update button is clicked.
   - It retrieves the new email and phone number from the input fields.
   - Validates the old email and phone number against the stored values in the database.
   - If the old details are validated successfully, it updates the contact details in the database.
   - Displays success or failure messages accordingly.

3. **Validation of Old Details**:
   - The `validateOldDetails` method validates the old email and phone number against the stored values in the database.
   - It retrieves the stored email and phone number based on the lecturer's ID and compares them with the entered old details.

4. **Database Operations**:
   - Uses the `ReadWriteDB` class to establish a database connection.
   - Executes SQL queries to update contact details and validate old details.
   - Handles exceptions that may occur during database operations.

This controller facilitates the updating of lecturer contact details by validating old details and then updating them in the database. It provides feedback to the user regarding the success or failure of the update operation.




# updatePassword_Controller.java

This Java file represents the `updatePassword_Controller` class, which handles updating the lecturer's password.

1. **Initialization**:
   - Initializes the controller with the lecturer's ID.

2. **Clear Label Method**:
   - The `clearLabel` method is invoked when a mouse event occurs (such as clicking on a label).
   - It clears the text of the label to make it empty.

3. **Update Password Method**:
   - The `updatePassword` method is invoked when the update password button is clicked.
   - It retrieves the current password, new password, and confirm password from the input fields.
   - Validates that the new password and confirm password match.
   - Validates the current password by comparing it with the stored password in the database.
   - If validations pass, it updates the password in the database and displays success or failure messages accordingly.

4. **Validation Methods**:
   - The `validateCurrentPassword` method validates the entered current password against the stored password in the database.
   - The `updatePasswordInDatabase` method updates the lecturer's password in the database.

5. **Database Operations**:
   - Uses the `ReadWriteDB` class to establish a database connection.
   - Executes SQL queries to validate the current password and update the password in the database.
   - Handles exceptions that may occur during database operations.

This controller facilitates the updating of the lecturer's password by validating the current password and then updating it in the database. It provides feedback to the user regarding the success or failure of the password update operation.


#Users 

1. **Instance Variables**:
   - `userName`: Username of the user.
   - `firstName`: First name of the user.
   - `lastName`: Last name of the user.
   - `password`: Password of the user.
   - `userID`: Unique identifier for the user.
   - `contact`: Contact information of the user.
   - `userType`: Type of user (e.g., lecturer, student).
   - `dateOfBirth`: Date of birth of the user.
   - `email`: Email address of the user.
   - `country`: Country of the user.
   - `courseName`: Name of the course associated with the user.
   - `userStatus`: Status of the user (active or inactive).
   - `realtimeDate`: Date representing the real-time date.

2. **Methods**:
   - Setter and getter methods for each instance variable to set and retrieve their values.
   - `setUser` method to set all user-related attributes at once.
   - `setStudentID` and `getStudentID` methods to handle student IDs.
   - `setIfSuccessful` method to set the success status of an operation.
   - `isUserStatus` and `getUserStatus` methods to handle user status.

3. **Initialization**:
   - The `initialize` method initializes the user with provided details.

4. **Getters and Setters**:
   - Provides getter and setter methods for each attribute to access and modify their values.

Overall, this class serves as a blueprint for creating user objects with various attributes and methods to manipulate those attributes. It encapsulates user-related data and functionalities.


#NaturalLanguageAnalyzer



### 1. **Class Overview**:
- The `NaturalLanguageAnalyzer` class is responsible for analyzing the sentiment of textual reviews.
- It utilizes Stanford's CoreNLP library to tokenize, split sentences, parse, and analyze sentiment.

### 2. **Dependencies**:
- This class relies on the `edu.stanford.nlp` package, which contains classes and tools for natural language processing tasks.

### 3. **Static Initialization**:
- The `pipeline` object of type `StanfordCoreNLP` is initialized in a static block using a set of properties. These properties configure the pipeline to perform various NLP tasks, including sentiment analysis.

### 4. **Sentiment Analysis Method**:
- The `analyzeSentiment(String reviewText)` method accepts a `String` parameter representing the review text to be analyzed.
- It processes the text through the CoreNLP pipeline, annotating each sentence with sentiment information.
- The method then calculates a sentiment score for each sentence based on its sentiment class (e.g., "Positive", "Negative").
- Sentiment scores for individual sentences are aggregated and averaged to produce an overall sentiment score for the entire review text.
- The sentiment score is returned as an integer, with a range from 1 to 9, where:
  - 1 indicates a very negative sentiment.
  - 9 indicates a very positive sentiment.
  - 5 indicates a neutral sentiment.
- The method handles cases where the review text is null or empty by returning a neutral score of 5.

### 5. **Private Helper Method**:
- The `getSentimentScore(String sentiment)` method maps sentiment classes obtained from CoreNLP to sentiment scores.
- It returns an integer score based on the sentiment class provided. This mapping allows for a consistent scoring system across different sentiment classes.

### 6. **Usage**:
- To use this class, simply create an instance of `NaturalLanguageAnalyzer` and call its `analyzeSentiment` method with the text you want to analyze.
- The method will return an integer score representing the sentiment of the text.

### 7. **Flexibility and Extensibility**:
- This class provides flexibility by allowing the user to analyze the sentiment of any textual content.
- It can be extended to support additional NLP tasks or to customize sentiment scoring based on specific requirements.

### 8. **Performance Considerations**:
- The static initialization of the CoreNLP pipeline ensures efficiency by reusing the pipeline across multiple method invocations, which can be beneficial for performance-sensitive applications.
- However, it's essential to consider the overhead associated with initializing the pipeline, especially in applications where sentiment analysis is performed frequently.

### 9. **Error Handling**:
- The class handles potential errors, such as database connection failures or unexpected SQL exceptions, by printing error messages to the console. However, it might be beneficial to implement more robust error handling mechanisms, such as logging or exception propagation, in production-grade applications.

Overall, the `NaturalLanguageAnalyzer` class provides a robust and efficient solution for sentiment analysis, leveraging Stanford's CoreNLP library to process textual data and derive meaningful sentiment scores.
