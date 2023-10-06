# SneakerStore

Frontend: https://sneakerstore-olivertears.vercel.app

A list of technologies that are used in the project:

+ Java 8
+ Spring Data JPA
+ Sptring Security
+ Liquibase
+ Maven
+ REST
+ Mockito
+ PostgreSQL
+ Hibernate
+ JUnit
+ Redis
+ Sptring AOP
+ Openapi
+ OpenWeatherMap

Docs
====

# 1 Introduction

## 1.1 Purpose

The document is developed in order to avoid ambiguous interpretation between the developer and the client. It's necessary to develop a web application with a simple functional that provides a backend for a sneaker store. The document describes functional and non-functional requirements for the crossplatform application "SneakerStore". The document is for the team, which well implements and check the correctness of the work. 

## 1.2 Business requirements

### 1.2.1 Initial data

Nowadays more and more software developers strive to create an interface that is a user-friendly as possible in order to attract as many as possible potential users to the product and prodive the best comfort when they use the application. However, despite the given trend there are still  applications with an interface that is difficult for the average user to understand. Sometimes it happens that some backend developer write a code, takes part in developing an applicationm, provides an interface to interact with an application and then nobody can understand how to work with given interface. Interface must be understandable for a frondend developer and for other members of the team that are working on the application.

### 1.2.2 Business oppotrunities

The application can store information about registered customers, available sneakers and every information must has an opportunity to be changed by the administator or user - in denepds on what kind of information is being changed. 

### 1.2.3

The application "SneakerStore" requires registration. The application provides you the information about registered user, available products and prices. Products can be sorted, can be bought, removed and added by the an administator. You can add credit card, the information where to deliver products you buy. Also you can change information about youself in your profile, change your avatar, add products to favorite or put it in cart. 

## 1.3 Analogs

The closest analog is a website with exactly the same name "SneakerStore". You can visit their website at the link "sneakerstore.by". The analog allows you to order sneakers, sort the feed of product. You can change information about yourself, add sneaker to favorite, read their blog or you can choose to see only products that are on sale. Also, it has not only sneakers but also clothes and accessories. 

### 1.3.1 Difference from analogues

The analog has the same opportunities without adding any product to favorite. Also it allows people to buy not only sneaker but other staff. Otherwise, everything is very similar, but interfaces and implementations are different. 

# 2 User requirements

## 2.1 Software interfaces

The product must be a crossplatform web application with user-friendly interface. The backend of the application must be implemented with Java and Spring Boot framework.

## 2.2 User interface

User interface is in the end of the file. It's an HTML page.

## 2.3 User characteristics 

### 2.3.1 User classes

The application requires to register if a user wants to buy anything or add product in favorite or put in cart.
Anyone can look at products without registration.

### 2.3.2 Application audience

#### 2.3.2.1 Target audience

Everyone who can use the Internet and want to buy new sneakers.

## 2.4 Assumptions and dependencies

+ The application doesn't work if user doesn't have installed Java 1.8 on his PC.

# 3 System requirements

## 3.1 Functional requirement

### 3.1.1 Main function

#### 3.1.1.1 Getting, changing, adding, removing information

<b>Description</b>. A user can get, change, delete, remove information about himself: phone number, email, password, country of living, street, city, house, apartment. He also can order products, put them in cart and add to favorite, chage avatar, add a credit card to buy for purchases.

<b>Requirements</b> After user authorized he can open his profile and information about himself. He can change any information. He can also open a list of sneakers available for purchase. There are several options:
+ put sneaker in a card in order to buy them. It's possible to put in cart more than one pair of sneakers. 
+ add to favorite in order to decide later if he wants to buy it or not.
After user put at least one pair he can make an order. The information from his profile will be used to determinte where to deliver and confirm the purchase.

### 3.1.1.2 Sorting the list of products

<b>Description</b>. User can open the catalog and sort sneaker by different parameters.

<b>Requirement<b>. The application must provide an opportunity to sort sneaker by different parameters: sizes, gender (there are only two: male or female) and brand. He also can find needed sneakers by its name. 

## 3.1.2 Limitations and exceptions

+ The application works only if a user has installed Java 1.8 on his PC.

  
  
# Documentation (user interface):
 PDF file named "sneackerstore-docs.pdf"
