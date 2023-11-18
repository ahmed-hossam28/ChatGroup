# Chat Group Console App

**Description:**
ChatGroup is a multithreaded network application designed for real-time messaging between users. Utilizing a client-server architecture, this console application is built using Java sockets and divided into two main components: the server and the client.

**Server Part:**
The server handles connection requests from clients and receives messages from clients, redistributing them to all connected users.

**Client:**
A user acting as a client connects to the server and can communicate with other users by sending messages. The client's messages are relayed to the server, which then broadcasts them to all connected users.

**To Run the App:**
1. First, run the server to handle connection requests and message distribution.
2. Next, run the client to create a new user.
3. Optionally, run additional client instances in separate terminals to simulate multiple users.

**Shell Scripts:**
For convenience, shell scripts have been provided to streamline the process of running the application.

- `runServer.sh`: Execute to run the server.
- `runClient.sh`: Execute to run a new client.

**Usage:**
- Compile the Java files using `javac`.
- Run the server script in one terminal: `./runServer.sh`.
- Run the client script in another terminal: `./runClient.sh`.
