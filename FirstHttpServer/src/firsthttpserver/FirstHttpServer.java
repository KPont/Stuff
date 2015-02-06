package firsthttpserver;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.util.Scanner;

/**
 * @author Lars Mortensen
 */
public class FirstHttpServer {

    static int port = 8090;
    static String ip = "127.0.0.1";

    public static void main(String[] args) throws Exception {
        if (args.length == 2) {
            port = Integer.parseInt(args[0]);
            ip = args[0];
        }
        HttpServer server = HttpServer.create(new InetSocketAddress(ip, port), 0);
        server.createContext("/welcome", new RequestHandler());
        server.createContext("/headers", new RequestHandler2());
        server.createContext("/pages/", new RequestHandler3());
        server.createContext("/parameters/", new RequestHandler4());
        server.setExecutor(null); // Use the default executor
        server.start();
        System.out.println("Server started, listening on port: " + port);
    }

    static class RequestHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange he) throws IOException {
            String response;
//                    = "Welcome to my very first almost home made Web Server :-)";
//            he.sendResponseHeaders(200, response.length());
            StringBuilder sb = new StringBuilder();
            sb.append("<!DOCTYPE html>\n");
            sb.append("<html>\n");
            sb.append("<head>\n");
            sb.append("<title>My fancy Web Site</title>\n");
            sb.append("<meta charset='UTF-8'>\n");
            sb.append("</head>\n");
            sb.append("<body>\n");
            sb.append("<h2>Welcome to my very first home made Web Server :-)</h2>\n");
            sb.append("</body>\n");
            sb.append("</html>\n");
            response = sb.toString();
            Headers h = he.getResponseHeaders();
            h.add("Content-Type", "text / html");
            he.sendResponseHeaders(200, response.length());
            try (PrintWriter pw = new PrintWriter(he.getResponseBody())) {
                pw.print(response); //What happens if we use a println instead of print --> Explain
            }
        }
    }

    static class RequestHandler2 implements HttpHandler {

        @Override
        public void handle(HttpExchange he) throws IOException {
            String response;
//                    = "Welcome to my very first almost home made Web Server :-)";
//            he.sendResponseHeaders(200, response.length());
            Headers rh = he.getRequestHeaders();

            StringBuilder sb = new StringBuilder();
            sb.append("<!DOCTYPE html>\n");
            sb.append("<html>\n");
            sb.append("<head>\n");
            sb.append("<title>My fancy Web Site</title>\n");
            sb.append("<meta charset='UTF-8'>\n");
            sb.append("</head>\n");
            sb.append("<body>\n");
            sb.append("<table>\n");
            sb.append("<thead>\n");
            sb.append("<th>Header</th>");
            sb.append("<th>Value</th>");
            sb.append("</thead>");
            sb.append("<tbody>");
            sb.append("<tr>");
            sb.append("<td>Cache-control</td>");
            sb.append("<td>" + rh.get("Cache-control") + "</td>");
            sb.append("</tr>");
            sb.append("<tr>");
            sb.append("<td>Host</td>");
            sb.append("<td>" + rh.get("Host") + "</td>");
            sb.append("</tr>");
            sb.append("<tr>");
            sb.append("<td>Accept-encoding</td>");
            sb.append("<td>" + rh.get("Accept-encoding") + "</td>");
            sb.append("</tr>");
            sb.append("<tr>");
            sb.append("<td>Connection</td>");
            sb.append("<td>" + rh.get("Connection") + "</td>");
            sb.append("</tr>");
            sb.append("<tr>");
            sb.append("<td>Accept-language</td>");
            sb.append("<td>" + rh.get("Accept-language") + "</td>");
            sb.append("</tr>");
            sb.append("<tr>");
            sb.append("<td>User-agent</td>");
            sb.append("<td>" + rh.get("User-agent") + "</td>");
            sb.append("</tr>");
            sb.append("<tr>");
            sb.append("<td>Accept</td>");
            sb.append("<td>" + rh.get("Accept") + "</td>");
            sb.append("</tr>");
            sb.append("</tbody>");
            sb.append("</table>");
            sb.append("</body>\n");
            sb.append("</html>\n");
            response = sb.toString();
            Headers h = he.getResponseHeaders();
            h.add("Content-Type", "text / html");
            he.sendResponseHeaders(200, response.length());
            try (PrintWriter pw = new PrintWriter(he.getResponseBody())) {
                pw.print(response); //What happens if we use a println instead of print --> Explain
            }
        }
    }

    static class RequestHandler3 implements HttpHandler {

        String contentFolder = "public/";

        @Override
        public void handle(HttpExchange he) throws IOException {

            File file = new File(contentFolder + "index.html");
            byte[] bytesToSend = new byte[(int) file.length()];
            try {
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
                bis.read(bytesToSend, 0, bytesToSend.length);
            } catch (IOException ie) {
                ie.printStackTrace();
            }
            he.sendResponseHeaders(200, bytesToSend.length);
            try (OutputStream os = he.getResponseBody()) {
                os.write(bytesToSend, 0, bytesToSend.length);
            }

            Headers h = he.getResponseHeaders();
            h.add("Content-Type", "text / html");
        }

    }

    static class RequestHandler4 implements HttpHandler {

        String contentFolder = "parameters/";

        @Override
        public void handle(HttpExchange he) throws IOException {
            String response;
            String method = he.getRequestMethod();
            String getPara = he.getRequestURI().getQuery();
            StringBuilder sb = new StringBuilder();
            sb.append("<!DOCTYPE html>\n");
            sb.append("<html>\n");
            sb.append("<head>\n");
            sb.append("<title>My fancy Web Site</title>\n");
            sb.append("<meta charset='UTF-8'>\n");
            sb.append("</head>\n");
            sb.append("<body>\n");
            sb.append("<h2>Method is: \n");
            sb.append(method);
            sb.append("\n");
            sb.append("Get Parameters: \n");
            sb.append(getPara);
            sb.append("\n");
            sb.append("Request body, with Post-parameters: \n");
            Scanner scan = new Scanner(he.getRequestBody());
            while (scan.hasNext()) {
                sb.append("Request body, with Post-parameters: " + scan.nextLine());
                sb.append("</br> ");
            }
            sb.append("</h2>\n");
            sb.append("</body>\n");
            sb.append("</html>\n");
            response = sb.toString();
            Headers h = he.getResponseHeaders();
            h.add("Content-Type", "text / html");
            he.sendResponseHeaders(200, response.length());
            try (PrintWriter pw = new PrintWriter(he.getResponseBody())) {
                pw.print(response); //What happens if we use a println instead of print --> Explain
            }

        }

    }
}
