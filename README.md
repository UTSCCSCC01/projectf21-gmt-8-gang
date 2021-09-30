# Motivation: 
The project aims to create an app that helps homeless youth by removing certains stigmas of donating to the homeless, such as the stigma that homeless youths might use the money to purchase alcohol rather than paying off their loans/mortgages.   
We aim to create an app with its own internal currency that can only be used at certain stores.   
We aim to work with certain restaurant chains/groceries stores that will accept this currency, so that donors will be certain their charitable donation can only be spent on specific locations (i.e Tim hortons/ Walmarts)    

# Installation: 
Front end: Android Studio (Xml)  
Back end: Java  
Database: MongoDB    

Steps to get app running:
1. Clone repo into local repository  
2. Import the BeingSeenApp directory into android studio   
3. Go to Android Studio>Preference>Build,Execution,Deployment>Build Tools>Gradle and change the "Use Gradle from:" option to gradle-wrapper.properties (note should also be using Gradle 11)   
4. After Android studio loads new options click on "Sync Project with Gradle files"   
5. If there is still an error, go to File>Invalidate Cache and restart  
6. Run app in android studio emulator: https://developer.android.com/training/basics/firstapp/running-app (You may need to download a virtual device in AVD manager)  
7. if no errors occured the app has succesfully ran and connected to the mongodb (If it failed to connect to mongodb atlas it should throw an error)   


# Contribution: 
describe the process for contributing to your project.   

### Do you use git flow?
We will follow the process of gitflow where we create new branches based on features, we will have to create a branch for master, development and features.   

### What do you name your branches?
master: for master branch (current working version)   
dev: for development branch (for current development branch that will be merged into master)   
feature-myNewFeatureName: for a new feature branch this branch can be edited by multiple people. However, different feature branches should attempt not to work on overlapping features (an example of a feature would be feature-DonorLogin or feature-HomelessPayment..)
feature-myNewFeatureName-taskx: for a new user story task based on a feature this branch should be predominantly edited by at most 2/3 people. Different task branches should attempt to work on overlapping methods  
hotfix-newHotfixIssueName: for urgent hotfix that is in master/dev branch

### Do you use github issues or another ticketing website?
We use discord / Jira to ticket our issues 

### Do you use pull requests?
Yes, it is mandatory to use PR before merging branches unless it is an urgent hotfix

