# REST Adapter

## Description

This project is an adapter for the communication of PurchaseMS with Supplier C (JMS+Avro). The idea is to keep it as simple as possible and also follow the "standards" for the communication with PurchaseMS.

## How to Run

You can run with your favorite IDE using AdapterApplication.main(), but for it's full functionality is recommended to run it along with Avro Supplier project. You can also use maven: `mvn spring-boot:run`, or run it directly with the jar file:


```
mvn clean install
java -jar target/avroadapter.jar

# Example request
curl -H "Content-Type: application/json" -d '{"id":"PUR0000001","order_id":"ORD0000001","supplier":{"id":"SUP0000001","name":"Supplier A"},"items":[{"quantity":1,"product":{"id":"PRD0000001","name":"Heineken","price":10,"category":"beer"}}],"status":"processing","datetime":"12-25-1980T13:00:00.000Z"}' http://localhost:8080/
```

## Configuration

As this is a simple project, no extra configuration should be needed. The default profile should be enough to run the project as standalone.
