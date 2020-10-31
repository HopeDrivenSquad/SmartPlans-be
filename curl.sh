curl -H "accept: application/json" -H  "Content-Type: application/json" -X POST -d '{"name":"Rezerva", "amount": 30000, "dateTo": "2021-06-30", "enabled": true}' http://localhost:8080/api/plans?clientId=20
curl -H "accept: application/json" -H  "Content-Type: application/json" -X DELETE http://localhost:8080/api/plans/1

curl -H "accept: application/json" -H  "Content-Type: application/json" -X PUT -d '{"enabled":false}' http://localhost:8080/api/plans/1
