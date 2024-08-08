import java.io.*;
import java.net.*;

public class BackendServer {
    private static final int PORT = 8080;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Backend server listening on port " + PORT);

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                    String clientAddress = clientSocket.getInetAddress().getHostAddress();
                    System.out.println("Received request from " + clientAddress);

                    // Log the request
                    String line;
                    while ((line = in.readLine()) != null && !line.isEmpty()) {
                        if (line.equals("break")) {
                            break;
                        }
                        System.out.println(line);
                    }
                    System.out.println("\nReplied with a hello message");
                    // Send response
                    String response = "HTTP/1.1 200 OK\r\n" +
                            "Content-Type: text/plain\r\n" +
                            "\r\n" +
                            "Hello From Backend Server";
                    out.println(response);
                    out.flush();


                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
