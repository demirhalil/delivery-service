# Delivery Service

---

The purpose of this service is that listing the deliveries by either they are received or not, and if a
delivery is received then update it as received.

This service can run either via gradle command or via docker.

### Run the service via `gradle`
1. First run the `gradle clean build` command to produce the application jar
2. Then run the `gradle bootRun` command, the application will start on the
`http://localhost:8080` and then you can navigate to `http://localhost:8080/playground` in order
to do operations.
3. If you do not have `gradle` installed in your local environment then it would be enough to replace
`gradle` command with `gradlew` command.

### Run the service via `docker`
1. First run the `gradle clean build` command to produce the application jar
2. Then you need to build the dockerfile via this command: `docker build -t gorillas/delivery-service .
   `
3. Once you build the docker image then you can create a container and run it via `docker run -d  -p 8080:8080 gorillas/delivery-service
   `. This command will create a container and run it then expose the application via port `8080`

### Run Queries
- In order to see all deliveries that are not received, you can run the below query. This will return
only `deliveryID`, `productI`, `supplier`, and `received` fields for the deliveries.
```
query{
  unreceivedDeliveries{
    deliveryID,
    product,
    supplier,
    received
  }
}
```
- In order to see all deliveries that are received, you can run the below query. This will return
  only `deliveryID`, `productI`, `supplier`, and `received` fields for the deliveries.
```
query{
  receivedDeliveries{
    deliveryID,
    product,
    supplier,
    received
  }
}
```
- In order to mark a delivery as received, you can run the below query. This will return
  only `deliveryID`, `productI`, `supplier`, and `received` fields for the deliveries.
```
mutation{
  markDeliveryAsReceived(id: 101){
    deliveryID,
    product,
    supplier,
    received
  }
}
```

### Run Tests
- In order to run the tests, you can run either `gradle test` or `gradle check` commands.

### Deploy the application to Kubernetes Cluster
- If you'd like to run this application inside a pod in the cluster, you can navigate `kubernetes` folder
run `kubectl create -f deployment.yml` command then it will create a pod in the cluster for you. 
- If you'd like to debug the application in your local environment while the application run inside a pod
it would be enough to run this `kubectl port-forward delivery-service 8080:8080` command which will 
forward the port of pod to your local environment then you can debug or run the application in your
local environment.

