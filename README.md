<h1>PointRate - Be Objective</h1>

This service helps people to be more accurate when relocating to a new place. It shows different scores (education, sports, entertainment, transport, ecology) based on requested location!

<h2>Example</h2>
<img src="https://github.com/user-attachments/assets/7054cfac-57d4-4306-b832-87f1a31b3c53" width="500">

You are living here 👆

Coordinates are:
- latitude = 43.311075
- londitude = 76.861973


Request looks like:
- POST http://localhost:8080/api/rating
- w/ body 
{
    "latitude": 43.311075,
    "longitude": 76.861973
}


Response:

<img src="https://github.com/user-attachments/assets/ff9973cd-f9ec-44e4-b3d2-0ef1dde3abc3" wifth="200">

<h2>Features</h2>

- calculates ratings based on multiple data sources
- supports scoring for education, sports, entertainment, transport, and ecology
- integrates with 2GIS API and IQ Air API 
- provides easy-to-use REST endpoint

<h2>Tech stack</h2>
Java, Spring Boot, PostgreSQL, REST API, Unit testing, Maven
