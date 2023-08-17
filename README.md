# Fetch Receipt Processor

# Introduction

- this document guide you how to setup the receipt processor project and how to run on docker and without docker

## Technologies

The following technologies stack used as part of building this project:

* Backend: Java 17, Spring-Boot 3
* API interceptor : Postman

#### Rule Assumption
- For Rule 4, consider pair as lower bound value, e.g. if you have 5 items than it will consider 2 pair 

# Getting Started

### How to run
- you can run application using docker or maven both
#### Using Docker
* go to main directory
```
cd /receipt-processor
```
* build spring boot image in docker using below command
```
docker build -t receipt-processor .
```
* run docker file
```
docker run -p 8080:8080 receipt-processor
```
#### Using Maven
- for this you need Java 17 installed on your machine to install java 17 follow given official docs
  https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html
* go to main directory
```
cd /receipt-processor
```
* run below mvnw command
```
./mvnw spring-boot:run
```

### Guides

#### Process Receipt
```curl
POST http://localhost:8080/receipts/process
```
body:
```json
{
  "retailer": "M&M Corner Market",
  "purchaseDate": "2022-03-20",
  "purchaseTime": "14:33",
  "items": [
    {
      "shortDescription": "Gatorade",
      "price": "2.25"
    },{
      "shortDescription": "Gatorade",
      "price": "2.25"
    },{
      "shortDescription": "Gatorade",
      "price": "2.25"
    },{
      "shortDescription": "Gatorade",
      "price": "2.25"
    }
  ],
  "total": "9.00"
}
```
Note: here in json object all fields are required otherwise it will send error message
- here handled validation as well

Console:
```json
{
    "id": "7cefe133-c027-4bc2-98c7-fa4a1f189ba5"
}
```

#### Get Points
```curl
GET http://localhost:8080/receipts/7cefe133-c027-4bc2-98c7-fa4a1f189ba5/points
```
Console:
```json
{
    "points": 109
}
```
### Test Cases
- You can find test cases in the directory below
```
/src/test/java/com/fetch/receiptprocessor
```
- here I did integration testing in java spring boot using junit5

![Screenshot 2023-08-16 at 8.08.20 PM.png](Screenshot%202023-08-16%20at%208.08.20%20PM.png)

### API Interceptor Output

![Screenshot 2023-08-16 at 1.42.22 PM.png](Screenshot%202023-08-16%20at%201.42.22%20PM.png)

![Screenshot 2023-08-16 at 1.42.39 PM.png](Screenshot%202023-08-16%20at%201.42.39%20PM.png)