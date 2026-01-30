# Springboot CloudFucntion


# Build

	./gradlew clean build
	

# Local Run CloudFunction using functions-framework-api

	./gradlew runFunction


# API test:

```
 curl -X POST http://localhost:8080   -H "Content-Type: application/json"   -d '{
        "message": {
          "data": "SGVsbG8gTG9jYWwgV29ybGQh",
          "messageId": "1111"
        },
        "subscription": "projects/test/subscriptions/local-sub"
      }'
```

![Start Google Cloud Function in remote debugger enabled](./Screenshot2.png)
![Start remote debugger](./Screenshot1.png)
![Publish message](./Screenshot3.png)
![Debugger Point](./Screenshot4.png)