package firsthttpserver;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;

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
    
}
