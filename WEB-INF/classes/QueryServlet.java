// To save as "ebookshop\WEB-INF\classes\QueryServlet.java".
import java.io.*;
import java.sql.*;
import jakarta.servlet.*;            // Tomcat 10
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
//import javax.servlet.*;            // Tomcat 9
//import javax.servlet.http.*;
//import javax.servlet.annotation.*;

@WebServlet("/query")   // Configure the request URL for this servlet (Tomcat 7/Servlet 3.0 upwards)
public class QueryServlet extends HttpServlet {

   // The doGet() runs once per HTTP GET request to this servlet.
   @Override
   public void doGet(HttpServletRequest request, HttpServletResponse response)
               throws ServletException, IOException {
      // Set the MIME type for the response message
      response.setContentType("text/html");
      // Get a output writer to write the response message into the network socket
      PrintWriter out = response.getWriter();
      // Print an HTML page as the output of the query
      out.println("<!DOCTYPE html>")
      out.println("<html>");
      out.println("<style>");
      out.println("table, th, td {border:1px solid black;}");
      out.println("body {");
      out.println("background-color: rgb(227, 138, 171);");
      out.println("}");
      out.println("</style>");
      out.println("<head><title>Query Response</title></head>");
      out.println("<body>");

      try (
         // Step 1: Allocate a database 'Connection' object
         Connection conn = DriverManager.getConnection(
               "jdbc:mysql://localhost:3306/etoyshop?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
               "myuser", "Chdy727300");   // For MySQL
               // The format is: "jdbc:mysql://hostname:port/databaseName", "username", "password"

         // Step 2: Allocate a 'Statement' object in the Connection
         Statement stmt = conn.createStatement();
      ) {
         // Step 3: Execute a SQL SELECT query
         //String sqlStr = "select * from books where author = "
         //      + "'" + request.getParameter("author") + "'"   // Single-quote SQL string
         //     + " and qty > 0 order by price desc";

         String[] categories = request.getParameterValues("category");
         if (categories == null) {
            out.println("<h2>No category selected. Please go back to select author(s)</h2><body></html>");
            return; // Exit doGet()
         }
         String sqlStr = "SELECT * FROM toys WHERE category IN (";
         for (int i = 0; i < categories.length; ++i) {
            if (i < categories.length - 1) {
               sqlStr += "'" + categories[i] + "', ";  // need a commas
            } else {
               sqlStr += "'" + categories[i] + "'";    // no commas
            }
         }
         sqlStr += ") AND qty > 0 ORDER BY category ASC, name ASC";

         out.println("<h3>Thank you for your query.</h3>");
         //out.println("<p>Your SQL statement is: " + sqlStr + "</p>"); // Echo for debugging
         ResultSet rset = stmt.executeQuery(sqlStr);  // Send the query to the server

         // Step 4: Process the query result
         // Print the <form> start tag
         out.println("<form method='get' action='order'>");
         out.println("<table><tr><th></th><th>CATEGORY</th><th>NAME</th><th>PRICE</th></tr>");
         int count = 0;
         while(rset.next()) {
            // Print a row <td>...</td> for each record
            out.println("<tr><td><input type='checkbox' name='id' value=" + "'" + rset.getString("id") + "' /></td>" 
                    + "<td>" + rset.getString("category") + "</td>"
                    + "<td>" + rset.getString("name") + "</td>" 
                    + "<td>$" + rset.getString("price") + "</td></tr>");
            count++;
         }
         out.println("<p>==== " + count + " records found =====</p>");

         out.println("</table>");

         out.println("<p>Enter your Name: <input type='text' name='cust_name' /></p>");
         out.println("<p>Enter your Email: <input type='text' name='cust_email' /></p>");
         out.println("<p>Enter your Phone Number: <input type='text' name='cust_phone' /></p>");
 
         // Print the submit button and </form> end-tag
         out.println("<p><input type='submit' value='ORDER' />");
         out.println("</form>");

      } catch(Exception ex) {
         out.println("<p>Error: " + ex.getMessage() + "</p>");
         out.println("<p>Check Tomcat console for details.</p>");
         ex.printStackTrace();
      }  // Step 5: Close conn and stmt - Done automatically by try-with-resources (JDK 7)
 
      out.println("</body></html>");
      out.close();
   }
}