# Automated Micro Insurance app

## What is it?
This is a micro insurance app that allows user to keep track of their assets, and recommends micro insurance products that best fit for the assets they own. This app is created during Hack the Six 2019 Toronto.

## DEMO

<img src="/demo/2.gif?raw=true" width="30%" height="30%">
<img src="/demo/1.gif?raw=true" width="30%" height="30%">

## How to use it? 
To begin, user upload images of their assets via camera button through navigation or home page, it will take a photo of the asset and sent to AWS for Textract(text extraction) and Rekognition object recogniztion such that user doesn't need to manually enter most of the attributes themself. 

Then an interface is presented to the user to confirm and add additional attributes if needed, once user click on the button submmit, the asset entry will be recorded in our MySQL database in AWS and our prediction model will re-evauate the best insurance products that fit for that user based on all the assets that the user have recorded.

At last, user will be redirect to home page where they can see their recorded assets and recommended insurances based on their assets using our recommendations models in Keras Neural Network.

## Technology Stack
**Frontend Android:** This app utilizes Jetpack navigation component, retrofit2 and okhttp3.

**Backend:** AWS Elastic Beanstalk, Flask,  MySQL Database, AWS Textract, AWS Rekognition, Keras Neural Network.

**Read more about backend at:** https://github.com/Piceptron/MicroinsuranceRecommender

## App architecture
The app is architect with the mindset of single activity and multi fragments using Jetpack navigation. I was going to use MVP but given the 1.5 days development time constraint of the Hackathon it's not feasible. The Jetpack navigation component makes managing backstack and fragment transactions very easy, it speeded up our development considerably. 

## Endpoints
**POST /sendImage:** takes input image in base64 and sends it to AWS Rekognition to understand what the image is and a category for the image, then queries the MySQL database for a valuation of that type of product. If the return product is similar looking to a receipt, will call AWS Textract to parse out information from the receipt, and collects the asset information including valuation from the parsed text.

**POST /addAsset:** adds an asset to the database

**GET /getAllAssets:** returns json containing list of all assets recorded in the database

**GET /getTop4Assets:** returns json containing list of 4 most recent assets recorded in the database

**GET /getRecommendations:** returns json containing list of all recommendations for the user using his current assets recorded in the database. Recommendations taken from Keras Neural Network currently trained with dummy data.

**GET /get4Recommendations:** returns json containing list of top 4 most confident recommendations for the user using his current assets recorded in the database. Recommendations taken from Keras Neural Network currently trained with dummy data.

## Roles
Bob Bao worked on most of the app, camera flow, submit flow, home flow, error case flows, app navigation architecture, network layers and app designs. 

[Jarrod Servilla](https://github.com/jcserv) worked on profile flow, home page UI and login integration with firebase (login branch).

[Hao Cong Su](https://www.linkedin.com/in/haocongsu/) and [Mingyi Dai](https://github.com/Piceptron) worked on the backend, MySQL Database, AWS APIs and recommendation models with Keras Neural Network.



