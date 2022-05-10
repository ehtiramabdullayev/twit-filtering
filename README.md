

#  Java Exercise #

This development test is used for Java developers. You are requested to develop a simple application that covers all the requirements listed below. To have an indication of the criteria that will be used to judge your submission, all the following are considered as metrics of good development:

+ Correctness of the implementation
+ Decent test coverage
+ Code cleanliness
+ Efficiency of the solution
+ Careful choice of tools and data formats
+ Use of production-ready approaches

While no specific time limit is mandated to complete the exercise, you will be asked to provide your code within a given deadline. You are free to choose any library as long as it can run on the JVM.

## Task ##

We would like you to write code that will cover the functionality explained below and provide us with the source, instructions to build and run the application, as well as a sample output of an execution:

+ Connect to [Twitter Streaming API 1.1](https://developer.twitter.com/en/docs/twitter-api/v1/tweets/filter-realtime/overview)
    * Use the following values:
        + Consumer Key: `*insertConsumerKey*`
        + Consumer Secret: `insertConsumerSecret`
    * The app name will be `java-exercise`
    * You will need to login with Twitter
+ Filter messages that track on "bieber"
+ Retrieve the incoming messages for 30 seconds or up to 100 messages, whichever comes first
+ Your application should return the messages grouped by user (users sorted chronologically, ascending)
+ The messages per user should also be sorted chronologically, ascending
+ For each message, we will need the following:
    * The message ID
    * The creation date of the message as epoch value
    * The text of the message
    * The author of the message
+ For each author, we will need the following:
    * The user ID
    * The creation date of the user as epoch value
    * The name of the user
    * The screen name of the user
+ All the above information is provided in either Standard output, or a log file
+ You are free to choose the output format, provided that it makes it easy to parse and process by a machine

### __Bonus points for:__ ###

+ Keep track of messages per second statistics across multiple runs of the application
+ The application can run as a Docker container

## Provided functionality ##

The present project in itself is a [Maven project](http://maven.apache.org/) that contains one class that provides you with a `com.google.api.client.http.HttpRequestFactory` that is authorised to execute calls to the Twitter API in the scope of a specific user.
You will need to provide your _Consumer Key_ and _Consumer Secret_ and follow through the OAuth process (get temporary token, retrieve access URL, authorise application, enter PIN for authenticated token).
With the resulting factory you are able to generate and execute all necessary requests.
If you want to, you can also disregard the provided classes or Maven configuration and create your own application from scratch.

## Delivery ##

You are assigned to you own private repository. Please use your own branch and do not commit on master.
When the assignment is finished, please create a pull request on the master of this repository, and your contact person will be notified automatically. 

# Solution
Simple solution with Spark

I decided to keep it simple by not using the Spring here since we don't need to use all the libraries and dependencies comes with it, and i saw in the job description that you already use spark so i continued with it.

I can say i tried to finish it as soon as possible so there are a couple of things could be improved:

1. Code cleanliness - (The exception handling isn't ideal but i had limited time)
2. Efficiency of solution - i could use different approach and use threads and make the solution with them. (For example when fetching the twits)
There is also one thing i didn't do efficiently, and it's that i'm fetching the stream and then i am parsing and grouping but the better solution could be to parse and group while fetching the stream, this way the app would be much faster but i didn't have time to make it this way and kept everything simple.
3. Test coverage - i added some tests but also i could improve test coverage, however i don't have too much time to work on it.
4. Logging - also it is very important part of the application but i didn't start it because of the time frame

## Setup guide

#### Minimum Requirements

- Java 11
- Maven 3.x

#### Install the application

1. Make sure you have [Java](https://www.oracle.com/technetwork/java/javase/downloads/jdk13-downloads-5672538.html) and [Maven](https://maven.apache.org) installed

2. Open the command line in the source code folder

3. Build project

  ```
  $ mvn package
  ```

Run the tests
  ```
  $ mvn test
  ```


Run the project

  ```
  java -jar bieber-tweets-1.0.0-SNAPSHOT.jar
  ```

## API

**GET** /authLink -  get authorization link 

Example Request
```
http://localhost:5555/authLink
```

Example Response

````
{
    "body": "https://api.twitter.com/oauth/authorize?oauth_token=AoZsnwAAAAAAt7ElAAABgFxehlY",
    "response": {
        "status": 200,
        "message": "SUCCESS"
    }
}
````

---

**POST** /createSession - creating a session to fetch the twits


````
{
	"pinCode":"1186491"
}
````
Example Response

````
{
    "response": {
        "status": 200,
        "message": "SUCCESS"
    }
}
````
---
**GET** /sessionList -  you can look at the sessions you added

Example Request
```
http://localhost:5555/sessionList
```

Example Response

````
{
    "body": [
        {
            "consumerKey": "RLSrphihyR4G2UxvA0XBkLAdl",
            "consumerSecret": "FTz2KcP1y3pcLw0XXMX5Jy3GTobqUweITIFy4QefullmpPnKm4",
            "temporaryToken": "XHRPGwAAAAAAt7ElAAABgFyOlD0",
            "token": "1516113823601725443-7sosuqTPEFzOFptnuDx1SAto3XVicy",
            "tokenSecret": "DA1GjaAadPpiGHsvX2kUvKiDTVwUeAm0XC4fzhOVeHvNu"
        }
    ],
    "response": {
        "status": 200,
        "message": "SUCCESS"
    }
}
````
---
**GET** /listTwits -  get all the twits with bieber with already grouped and filtered

Example Request
```
http://localhost:5555/listTwits
```

Example Response

````
{
    "body": [
        {
            "author": {
                "idStr": "25209054",
                "createdAt": "Mar 19, 2009, 2:40:57 AM",
                "name": "Ana Cristina",
                "screenName": "Hanna_Demetriaa"
            },
            "twits": [
                {
                    "idStr": "1518263393735557121",
                    "createdAt": "Apr 24, 2022, 6:19:35 PM",
                    "text": "RT @fontesbieber: UM NENÉM! 🥺\n\n— Justin Bieber via Instagram Stories. https://t.co/lgMkarIBlP",
                    "user": {
                        "idStr": "25209054",
                        "createdAt": "Mar 19, 2009, 2:40:57 AM",
                        "name": "Ana Cristina",
                        "screenName": "Hanna_Demetriaa"
                    }
                }
            ]
        },
               {
            "author": {
                "idStr": "1451298602635116544",
                "createdAt": "Oct 21, 2021, 11:26:35 PM",
                "name": "Singularidade de Andrômeda",
                "screenName": "theitaewone"
            },
            "twits": [
                {
                    "idStr": "1518263399695663104",
                    "createdAt": "Apr 24, 2022, 6:19:37 PM",
                    "text": "tem alguns ídolo de kpop que 5s com eles você descobriria até a senha da conta bancária do Justin Bieber, na moral",
                    "user": {
                        "idStr": "1451298602635116544",
                        "createdAt": "Oct 21, 2021, 11:26:35 PM",
                        "name": "Singularidade de Andrômeda",
                        "screenName": "theitaewone"
                    }
                }
            ]
        }
    ], 
    ,
    "response": {
        "status": 200,
        "message": "SUCCESS"
    }
````