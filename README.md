# Build My Own Load Balancer

This project demonstrates the implementation of an application layer load balancer in Java. The load balancer efficiently distributes client requests across multiple backend servers, ensuring high availability and reliability while minimizing response time and maximizing server utilization.

## Project Overview

A load balancer sits in front of a group of servers and routes client requests across all servers capable of fulfilling those requests. If a server goes offline, the load balancer redirects traffic to the remaining servers, and when a new server is added, it automatically starts sending requests to it.

### Goals of the Project

- Build a load balancer that can send traffic to two or more servers.
- Implement health checks for the servers.
- Handle scenarios where a server goes offline (failing a health check).
- Handle scenarios where a server comes back online (passing a health check).

## Features

- **Request Distribution**: Distributes client requests efficiently across multiple servers.
- **High Availability**: Ensures requests are only sent to servers that are online.
- **Dynamic Server Management**: Flexibly add or remove servers based on demand.
Responds to forwarded requests with a simple HTTP message.

## Steps to Run

1. **Set Up the Environment**: Ensure you have Java installed and set up your IDE.
2. **Start the Backend Servers**: Use Python's built-in HTTP server or any other server to simulate backend servers on different ports.
3. **Run the Load Balancer**: Compile and execute the Java program to start the load balancer.
4. **Send Requests**: Use tools like `curl` to send HTTP requests to the load balancer and observe the distribution of requests across the backend servers.

### Example Usage

1. To test the load balancer, you can start multiple backend servers using Python:


Navigate to the /servers directory
```bash
# Start the first backend server
python -m http.server 8080 --directory server8080

# Start the second backend server
python -m http.server 8081 --directory server8081
```
2. Then, compile and run the load balancer:

Navigate to the /src directory
```bash
javac src/LoadBalncer.java 
java src/LoadBalncer.java [server health check time in seconds]

```
3. Send a request to the Load Balancer
```bash
curl http://localhost/
```
   To test with multiple clients
```bash
curl --parallel --parallel-immediate --parallel-max 3 --config urls.txt
```
