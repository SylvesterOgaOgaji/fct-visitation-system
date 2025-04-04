# API Reference

This document outlines the REST APIs available in the FCT Visitation System. These APIs can be used to integrate with the mobile application or third-party systems.

## Authentication

All API requests (except public endpoints) require authentication via JWT token.

### Login
Request body:
```json
{
  "username": "string",
  "password": "string"
}
Response: {
  "accessToken": "string",
  "tokenType": "Bearer"
}
Visitor APIs
Register a Visitor
POST /api/visitors
Request body:
{
  "firstName": "string",
  "lastName": "string",
  "email": "string",
  "phoneNumber": "string",
  "nin": "string",
  "carType": "OWN | RENTED | NONE",
  "facility": {
    "facilityId": "number"
  },
  "officer": {
    "officerId": "number"
  },
  "purposeOfVisit": {
    "purposeId": "number"
  },
  "appointmentDateTime": "string (ISO format)"
}
Get Visitor by ID
GET /api/visitors/{id}
Get Visitor by QR Code
GET /api/visitors/qr/{qrCode}
Check-in Visitor
PUT /api/visitors/{id}/check-in
Complete Visit
PUT /api/visitors/{id}/complete
QR Code APIs
Scan QR Code
POST /api/qr/scan/{qrCode}?checkpointId={checkpointId}
Response:
{
  "visitor": {
    "visitorId": "number",
    "firstName": "string",
    "lastName": "string",
    "status": "string"
  },
  "scanLog": {
    "scanId": "number",
    "scanTime": "string (ISO format)"
  }
}
Facility APIs
Get All Facilities
GET /api/facilities
Get Officers by Facility
GET /api/facilities/{facilityId}/officers
Get Purposes by Facility
GET /api/facilities/{facilityId}/purposes
Security APIs
Report Incident
POST /api/security/incidents
Request body:
{
  "visitorId": "number (optional)",
  "location": "string",
  "description": "string",
  "responseTeamId": "number"
}
Get Active Incidents
GET /api/security/incidents/active
Respond to Incident
PUT /api/security/incidents/{incidentId}/respond
Error Responses
All API endpoints return standard HTTP status codes:

200: Successful operation
201: Resource created
400: Bad request
401: Unauthorized
403: Forbidden
404: Resource not found
500: Internal server error

Error response body:
{
  "timestamp": "string (ISO format)",
  "status": "number",
  "error": "string",
  "message": "string",
  "path": "string"
}
