import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class LoadBalancer {
    private static final int LB_PORT = 80;
    private static final String BACKEND_HOST = "localhost";
    private static final List<Integer> BACKEND_PORTS = Arrays.asList(8080, 8081);
    private static int healthCheckDelay = 10;
    private static int rrIndex = 0;

    public static void main(String[] args) {
        healthCheckDelay = Integer.parseInt(args[0]);
        // Schedule server health check
        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
        service.scheduleAtFixedRate(command, 5, 5, TimeUnit.MINUTES);
        try (ServerSocket serverSocket = new ServerSocket(LB_PORT)) {
            System.out.println("Load balancer listening on port " + LB_PORT);
            ExecutorService executor = Executors.newFixedThreadPool(10);
            executor.

            while (true) {
                Socket clientSocket = serverSocket.accept();
                executor.submit(() -> handleClient(clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Takes in the client request and routes it to a server. Then takes the response
     * from the server and passes the result back to the client.
     * @param clientSocket source socket of the request
     */
    private static void handleClient(Socket clientSocket) {
        try {
            // Receive data from client
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            StringBuilder requestBuilder = new StringBuilder();
            String line;
            while (!(line = in.readLine()).isEmpty()) {
                requestBuilder.append(line).append("\r\n");
            }
            String request = requestBuilder.toString();
            System.out.println("Received request from " + clientSocket.getInetAddress().getHostAddress());
            System.out.println(request);
            requestBuilder.append("\nbreak");
            request = requestBuilder.toString();

            // Forward request to backend server
            int BACKEND_PORT = getPortRoundRobin();

            try (Socket backendSocket = new Socket(BACKEND_HOST, BACKEND_PORT)) {
                PrintWriter out = new PrintWriter(backendSocket.getOutputStream(), true);
                out.print(request);
                out.flush();


                // Receive response from backend
                BufferedReader backendIn = new BufferedReader(new InputStreamReader(backendSocket.getInputStream()));
                StringBuilder responseBuilder = new StringBuilder();
                while ((line = backendIn.readLine()) != null) {
                    responseBuilder.append(line).append("\r\n");
                }
                String response = responseBuilder.toString();
                System.out.println("Response from server: " + response.split("\r\n")[0]);

                // Send response back to client
                PrintWriter clientOut = new PrintWriter(clientSocket.getOutputStream(), true);
                clientOut.print(response);
                clientOut.flush();
            }
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int getPortRoundRobin() {
        int port = BACKEND_PORTS.get(rrIndex);
        rrIndex++;
        if (rrIndex >= BACKEND_PORTS.size()) {
            rrIndex = 0;
        }
        return port;
    }
}

