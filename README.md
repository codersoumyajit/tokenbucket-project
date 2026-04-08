# Token Bucket Rate Limiter API

A Spring Boot project that implements API rate limiting using the Token Bucket algorithm with Redis.

## Project Objective
This project limits how many API requests a user can make within a fixed time period.

- Each user gets 5 requests per minute
- After 5 requests, the API blocks the user
- Tokens are recreated automatically after 1 minute
- Admin can ban or blacklist users

## Features

- Token Bucket Rate Limiting
- Redis-based token storage
- Temporary user ban
- Permanent user blacklist
- Global exception handling
- Cloud deployment using Render

## Tech Stack

- Java
- Spring Boot
- Maven
- Redis Cloud
- Render
- GitHub
- Postman

- ## Live Project URL

Main API:
https://tokenbucket-project.onrender.com/api/test?userId=test1

Note: Since the project is deployed on the free Render plan, the first request after inactivity may take 30–60 seconds.

## API Endpoints

### 1. Test Rate Limit

Method: GET  
URL:
https://tokenbucket-project.onrender.com/api/test?userId=test1

Response:
- First 5 requests -> Request Allowed
- 6th request -> Too Many Requests

### 2. Blacklist User

Method: POST  
URL:
https://tokenbucket-project.onrender.com/admin/blacklist?userId=test1

### 3. Ban User for 1 Minute
Method: POST  
URL:
https://tokenbucket-project.onrender.com/admin/ban?userId=test1&minutes=1

### 4. Unban User

Method: POST  
URL:
https://tokenbucket-project.onrender.com/admin/unban?userId=test1

### 5. Remove User from Blacklist
Method: POST  
URL:
https://tokenbucket-project.onrender.com/admin/remove-blacklist?userId=test1

## How the Project Works

1. User sends request using userId
2. Interceptor checks:
   - Is user blacklisted?
   - Is user banned?
   - Does user have tokens left?
3. If yes -> request is allowed
4. If no -> request is blocked
5. After 1 minute, Redis recreates 5 tokens automatically
---

## Example

If userId=test1:
- Request 1 -> Allowed
- Request 2 -> Allowed
- Request 3 -> Allowed
- Request 4 -> Allowed
- Request 5 -> Allowed
- Request 6 -> Blocked

After 1 minute:
- Request -> Allowed again

## Running Locally

1. Clone repository
2. Add Redis host, port and password in application.properties
3. Run:

mvn spring-boot:run

4. Open:
http://localhost:8081/api/test?userId=test1

## Author

Soumyajit Paul
