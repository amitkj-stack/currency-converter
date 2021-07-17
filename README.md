# README #


This README would normally document whatever steps are necessary to get your application up and running.

### What is this repository for? ###

* This repository is related to helps calculate the exchange rate from one currency to another.
* Version : 1.0.0
* Used https://exchangeratesapi.io/ for currency exchange rate data

In this project, I have followed MVVM architecture with livedata. I also used two way binding to get rid of boilercode.
- I also used local database so users can use even if there is no internet connection later point of time. 
- I used material design and dark theme to give better user experience
- User also fetch latest exchange data by clicking refresh button
- User can swap currency without selecting from list [by clicking swap icon]


### ScreenShots ###
![Image](https://imgur.com/FdR579T.png)
![Image](https://imgur.com/nofWA7P.png)
![Image](https://imgur.com/WOxcvjj.png)

### ScreenShots (Dark Theme) ###
![Image](https://imgur.com/34ZW5K1.png)
![Image](https://imgur.com/AZCDGgz.png)
![Image](https://imgur.com/hbyTgUC.png)


### Dependencies ###
#### Architecture ####
* MVVM architecture used -Lossley coupled architecture : MVVM makes your application architecture as loosley coupled, Extensible code

#### UI Component ####
* Material Library - It is used for using latest MUI component.

#### Testing Libraries ####
* Robolectric and Junit - I have used robolectric and Junit lib. Robolectric - This enables you to run your Android tests in your continuous integration environment without any additional setup and also does not require real device
* Expresso - Run UI testing
* Truth Lib - For testing

#### Dependency Injection ####
* Hilt : It cuts boiler plates so less code

#### Network Libraies ####
* Retrofit is used for networking
* Moshi - Converter is used to parse data. More light weighted than Gson 
* Coroutines - Improve code performance, Less boiler code

#### Database  ####
* Room DB is used - Less boiler code, handling and managing database is easy, prevents the developer from encountering run time errors

#### Others  ####
* LiveData, TwoWay Binding

### Features which can be in PRO version ### 
* Last saved currency
* Can select multiple currency
* Support mic for text input

### Things that could improved ### 
* Full code coverage - Less UI test (Time needed)
* UI - Could be improved
* If plan for PRO version then product flavour needed to integrate (Development required)
* Landscape can be supported (UI design and orientation handling required) 

**Support? : <developer.amitjaiswal@gmail.com>
