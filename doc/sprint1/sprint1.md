Meeting up for sprint 1 conducted on Sep 26 (Discord)
Participants:
Suguru Seo
Jiaxuan Zhang
Pengpeng Cao
Hong Sung Ye Ansel
Andreas Alexander
Hsu Shen Lee
Sarina Pui Ting Cheung 


Daily Standups Sprint 1: Oct 2nd, Oct 5th, Oct 8th 8am in Toronto time (Discord)

Team capacity should be optimal for sprint 1, so ideally this should be the base weight of total user stories for future sprints as well, 
although we expect there will be issues on setting up and getting familiar with the new architecture and workflow we will use.

Meeting goal:
Finish all tasks breakdowns and assigning, choose which tasks to complete hopefully by end of sprint 1, identify spikes.

Potential spikes: 
Researching XML frontend on Android studio (for frontend)
Getting familiar with MongoDb, SpringbootMongoDB, and connecting backend to frontend, and backend to database.
JWT Authentication for backend

Sprint goal:
At the end of Sprint1, we will focus on features necessary for a donor and homeless youth, and complete user stories  7, 20, 61, 21, 22, 12, 61 
which set up a basic account for homeless youth and donors. We decided to first pursue user stories below for this sprint1, because we believe 
basic features for a donor and homeless youth are the most important feature in our product which can add fundamental value of donation from a 
donor to a homeless youth. By the end of sprint 1, we expect to have a basic working app where users can signup and login with correct authentication, 
and for users to have access to profile page to edit their information, and access an About Us page from the home page. 

Tasks breakdown by user stories:
User story 21) As Willow England (A donor), I would like to register a donor account so that I can donate to the homeless. 
(Assigned to Angus)
-Setup base models for users and repository to keep them
-Create API for user login to database
-Connect frontend with backend for user register/login

User story 22) As Willow England (A donor), I would like to log in with a donor account so that I can donate to the homeless. 
(Assigned to Andreas)
-Implement API with JWT Tokens for database
-Implement POST and GET requests with users with tokens
-Setup basic models and authentication methods with MongoDB database

User story 12) As Abby Smith (A homeless youth), I would like to register to use the app with a homeless account so that I can receive donations. 
(Assigned to Sarina)
-Setup base models for users and repository to keep them
-Setup frontend UI for registering

User story 61) As Willow England (A donor), I would like to be able to create my personal profile so that I can see my donation history and balance. 
(Assigned to Suguru)
-Create profile page and allow user to edit
-Frontend interaction with API for Donor profile page
-Basic Profile page UI
-Edit feature for donor

User story 7) As Willow England (A donor), I would like to access an “about us” page to see how the money will get used, so that I feel safe that my money 
will be used on necessities instead of alcohol and drugs. (Assigned to Jiaxuan)
-Create about us page for being seen
-Button and link to about us page
-Donate to being seen (in app currency)

User story 20) As Abby Smith (A homeless youth), I would like to edit my personal profile so that the people who are willing to help me can know me better. 
(Assigned to Ansel)
-As Abby Smith (A homeless youth), I would like to check my current in-app balance so that I can plan my expenditure according to it.
-All basic personal infos (name, profile picture, app currency)

User story 60) As Abby Smith (A homeless youth), I would like to login to use the app with a homeless account so that I can receive donations. 
(Assigned to Pengpeng)
-Setup base models for users and repository to keep them
-Setup frontend UI for registering


