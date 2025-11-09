# Springboot CloudFucntion


# Build

	./gradlew clean build
	

# Local Run CloudFunction using functions-framework-api

	./gradlew bootRun
	
	
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

```
	printf '{"message":{"data":"%s","attributes":{"type":"typeA"}}}' "$(echo -n 'hello' | base64)" | curl -s -X POST http://localhost:8080/local/publish -H "Content-Type: application/json" -d @-
```