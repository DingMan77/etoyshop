// To save as "ebookshop\WEB-INF\classes\ OrderServlet.java".
import java.io.*;
import java.sql.*;
import jakarta.servlet.*;            // Tomcat 10
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
//import javax.servlet.*;            // Tomcat 9
//import javax.servlet.http.*;
//import javax.servlet.annotation.*;

@WebServlet("/order")   // Configure the request URL for this servlet (Tomcat 7/Servlet 3.0 upwards)
public class OrderServlet extends HttpServlet {

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
      out.println("body {background-color: #ffd7e1; font-family: Comic Sans MS;}");
      out.println("h2 {text-align: center; background: -webkit-linear-gradient(rgb(251, 203, 203), rgb(67, 4, 112)); -webkit-background-clip: text; -webkit-text-fill-color: transparent;}");
      out.println("h3 {text-align: center; color: #d35a7f;  margin-left: auto; margin-right: auto; width: 1000px;font-size: 35px;}");
      out.println("p {font-size: 25px; color: #d35a7f; text-align: center;}");
      out.println("h4 {margin-left: auto; margin-right: auto; width: 1300px;}");
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
         // Step 3 & 4: Execute a SQL SELECT query and Process the query result
         // Retrieve the books' id. Can order more than one books.
         String[] ids = request.getParameterValues("id");
         String cust_name = request.getParameter("cust_name");
         String cust_email = request.getParameter("cust_email");
         String cust_phone = request.getParameter("cust_phone");
         //String[] names = request.getParameterValues("name");
         // String[] categories = request.getParameterValues("category");
         if (ids != null) {
            String sqlStr;
            int count;
            int totalPrice = 0;
            // Process each of the books
            for (int i = 0; i < ids.length; ++i) {

               sqlStr = "SELECT price from toys WHERE id = "+ ids[i];
               //out.println("<p>" + sqlStr + "</p>");  // for debugging

               ResultSet rset = stmt.executeQuery(sqlStr);
               if(rset.next()){
                  totalPrice += rset.getInt("price");
               }
               
               // Update the qty of the table books
               sqlStr = "UPDATE toys SET qty = qty - 1 WHERE id = " + ids[i];
               //out.println("<p>" + sqlStr + "</p>");  // for debugging
               count = stmt.executeUpdate(sqlStr);
               //out.println("<p>" + count + " record updated.</p>");
               
               // Create a transaction record
               sqlStr = "INSERT INTO order_records (id, qty_ordered) VALUES ("
                     + ids[i] + ", 1)";
               //out.println("<p>" + sqlStr + "</p>");  // for debugging
               count = stmt.executeUpdate(sqlStr);
               //out.println("<p>" + count + " record inserted.</p>");
               out.println("<h3>Your order for toy id = " + ids[i] + " has been confirmed.</h3>");

               sqlStr = "INSERT INTO customers (id, cust_name, cust_email, cust_phone) VALUES ("
                     + ids[i] + ", '" + cust_name + "', '" + 
                     cust_email + "', '" + cust_phone + "')";
               //out.println("<p>" + sqlStr + "</p>");  // for debugging
               count = stmt.executeUpdate(sqlStr);
            }
            out.println("<h3>The total price is $"+ totalPrice);
            out.println("<h3>Thank you.<h3>");
         }else{ // No book selected
            out.println("<h3>Please go back and select a toy...</h3>");
         }
        }  
        catch(Exception ex) {
         out.println("<p>Error: " + ex.getMessage() + "</p>");
         out.println("<p>Check Tomcat console for details.</p>");
         ex.printStackTrace();
        }
        // Step 5: Close conn and stmt - Done automatically by try-with-resources (JDK 7)
 
      out.println("</body></html>");
      out.close();
   }
}