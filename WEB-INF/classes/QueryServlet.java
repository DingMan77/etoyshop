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
      out.println("<!DOCTYPE html>");
      out.println("<html>");
      out.println("<style>");
      out.println("table, th, td {border: 2px solid #cf2164; margin-left: auto; margin-right: auto; width: 800px; color: #590f2c}");
      out.println("body {background-color: #f9e4ff; font-family: Comic Sans MS;}");
      out.println("h2 {text-align: center; background: -webkit-linear-gradient(rgb(251, 203, 203), rgb(67, 4, 112)); -webkit-background-clip: text; -webkit-text-fill-color: transparent;}");
      out.println("h3 {text-align: center; color: #cf2164;  margin-top: 50px; margin-bottom:10px; font-size: 55px; text-shadow: 20px 10px 3px #e6c4f3;}");
      out.println("p {font-size: 25px; color: #a6196e; text-align: center;}");
      out.println("h4 {margin-left: auto; margin-right: auto; width: 800px; color: #a6196e;}");
      out.println("input[type=submit] {background-color: #cf2164; border: 0; border-radius: 5px; cursor: pointer; color: #fff; font-size:20px; font-weight: bold; line-height: 1.4;padding: 10px; width: 180px; }");
      out.println("</style>");
      out.println("<head><title>Query Response</title></head>");
      //out.println("<link rel=\"stylesheet\" href=\"style1.css\">");
      //out.println("<link href=\"https://fonts.googleapis.com/css2?family=Lobster&display=swap\" rel=\"stylesheet\">");
      //out.println("</head>");
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

         out.println("<h4>Enter your Name: <input type='text' name='cust_name' /></h4>");
         out.println("<h4>Enter your Email: <input type='text' name='cust_email' /></h4>");
         out.println("<h4>Enter your Phone Number: <input type='text' name='cust_phone' /></h4>");
 
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