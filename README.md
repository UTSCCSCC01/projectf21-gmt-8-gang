# Motivation: 
The project aims to create an app that helps homeless youth by removing certains stigmas of donating to the homeless, such as the stigma that homeless youths might use the money to purchase alcohol rather than paying off their loans/mortgages.   
We aim to create an app with its own internal currency that can only be used at certain stores.   
We aim to work with certain restaurant chains/groceries stores that will accept this currency, so that donors will be certain their charitable donation can only be spent on specific locations (i.e Tim hortons/ Walmarts)    

# Installation: 
Front end: Android Studio (Xml)  
Back end: Java  
Database: MongoDB Atlas    

Steps to get app running:

Frontend
1. Clone repo into local repository  
2. Import the BeingSeenApp directory into android studio   
3. Go to Android Studio(for Windows, it should be File)>Preference(for Windows, it should be Settings)>Build,Execution,Deployment>Build Tools>Gradle and change the "Use Gradle from:" option to gradle-wrapper.properties (note should also be using Gradle 11), go to Android Studio>Project Structure>Modules>Properties, make sure Compile SDK Version and Build Tools Version are specified.
4. After Android studio loads new options click on "Sync Project with Gradle files"   
5. If there is still an error, go to File>Invalidate Cache and restart  
6. Run app in android studio emulator: https://developer.android.com/training/basics/firstapp/running-app (You may need to download a virtual device in AVD manager)  
7. if no errors occured the app has succesfully ran (it will then attempt to connect to the backend: it will retreive the entire DB if it successfully makes a get call from backend, otherwise it will display "Error" in a textview)    
  
Backend    
1. After cloning repo, import the springbootmongodb directory into an IDE that can set up maven projects (Intellij (preferred), Eclipse)     
2. IDE should manually setup all maven dependencies, if any errors occur make sure Maven and Java are the latest version and reimport dependencies     
3. Run the project and it should show Spring and after its completed running, user should be able to access spring at localhost:8080
4. Make sure add application for configuration, select correct main class (com.example.springbootmongodb.SpringbootmongodbApplication).
  
Database
1. MongoDB Atlas is setup as a cloud based DB. The backend automatically makes get/put calls to the DB when frontend requests information from DB.         
    
Extra Note:
After backend is ran. When running frontend at the initial screen, it should display information from mongoDB atlas.    

# Contribution: 
describe the process for contributing to your project.   

### Do you use git flow?
We will follow the process of gitflow where we create new branches based on features, we will have to create a branch for master, development and features.   

### What do you name your branches?
master: for master branch (current working version)   (should not edit directly except during the start of the project)    
dev: for development branch (for current development branch that will be merged into master) can be edited but only if working on universal feature   
GMTGAN-XX-myNewFeatureName: For a new feature branch this branch can be edited by multiple people (limit to at most 3) The XX represents the task number on Jira, and will get automatically tracked in Jira. should only be used to create basic interface for large task so that each subtask/user story can be worked on in a child branch (i.e subtasks can be branched from another GMTGAN branch.. use Jira to track which branches are related). Different feature branches should not to work on overlapping features (an example of a feature would be feature-DonorLogin or feature-HomelessPayment..).  When merging feature branches, try to merge all relevant features and making sure it works before merging into dev. If something from a feature branch is needed for all other features, then its fine to merge into dev directly.       

### Do you use github issues or another ticketing website?
We use discord / Jira to ticket our issues 

### Do you use pull requests?
Yes, it is mandatory to use PR before merging branches unless it is an urgent hotfix

