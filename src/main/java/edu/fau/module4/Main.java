/**
 * @Author: Rhian Resnick
 *
 */
package edu.fau.module4;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author rhianresnick
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        Main main = new Main();
        main.Example();
    }

    public void Example() throws InterruptedException {

        Connection dbConnection = null;
        Statement stmt = null;
        ResultSet rs = null;
        // Define the jdbc data url. This is for derby but there are other options.
        // What options do you think we should replace this with?
        String strUrl = "jdbc:derby:derbyDB;create=true";

        try {

            // Load a jdbc driver, in this case the derby embedded driver, 
            // what other Database support JDBC?
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();

            dbConnection = DriverManager.getConnection(strUrl);

            // Lets create a statement object, we will use this numerous times
            stmt = dbConnection.createStatement();

            String createPatientsTableSql = IOUtils.toString(this.getClass().getResourceAsStream("/jdbcExample/patientTableCreate.sql"), "UTF-8");

            /*
            Why did I have to drop before I add, what about our db prevents an 
            IF EXISTS DROP table name syntax?
             */
            try {
                stmt.execute("drop table app.patient");
            } catch (Exception ex) {
                System.out.println("Failed to drop table, must not exist");
            }

            stmt.execute(createPatientsTableSql);

            /*
                How would you improve this code? Could you load all files in the insert directory?
             */
            String insertSql = IOUtils.toString(this.getClass().getResourceAsStream("/jdbcExample/inserts/patient1.sql"), "UTF-8");
            stmt.execute(insertSql);
            insertSql = IOUtils.toString(this.getClass().getResourceAsStream("/jdbcExample/inserts/patient2.sql"), "UTF-8");
            stmt.execute(insertSql);
            insertSql = IOUtils.toString(this.getClass().getResourceAsStream("/jdbcExample/inserts/patient3.sql"), "UTF-8");
            stmt.execute(insertSql);
            insertSql = IOUtils.toString(this.getClass().getResourceAsStream("/jdbcExample/inserts/patient4.sql"), "UTF-8");
            stmt.execute(insertSql);

            /*
            Add you are homework code here
             */
            
            // Q1 Create a trigger named UPDATEAT. This trigger should update the UPDATED_AT column setting it to the current timestamp each time the row is updated. 
            String createTrigger = "-- ADD YOUR CODE HERE";
            stmt.execute(createTrigger);

            
            // Q2 Update the Java and JDBC code so the postal code for ID 1 is updated to be 11111-1111. 
            String updateQuery = "-- ADD YOUR CODE HERE";
            stmt.execute(updateQuery);

            
            // Q3 Update the Java and JDBC code so the postal code for ID 3 is deleted from the PATIENT table. 
            // Sleep so we can prove the code works as expected
            Thread.sleep(10000);
            String update1 = "-- ADD YOUR CODE HERE";
            stmt.execute(update1);

            // Q4
            String delete = "-- ADD YOUR CODE HERE";
            stmt.execute(delete);

            
            /*
            End of your homework code
             */
            
            // Lets Query our data and see if we inserted data for real.
            String query = "SELECT * FROM PATIENT";
            rs = stmt.executeQuery(query);
            StringBuilder r = new StringBuilder();

            // Fetch the metadata, to get column names for the header
            int columnCount = rs.getMetaData().getColumnCount();
            List<String> columns = new ArrayList();
            for (int i = 1; i <= columnCount; i++) {
                String column = rs.getMetaData().getColumnName(i);
                columns.add(column);
                r.append(column).append("\t");
            }
            r.append("\n");

            while (rs.next()) {

                for (String column : columns) {
                    r.append(rs.getString(column)).append("\t"); // What is this doing to our data?
                }
                r.append("\n");
            }

            System.out.println(r.toString());

        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // Why is this bit of code important. What would happen if we called
            // the constructor 1,000's of time in a few seconds?
            // What would be left over for garbage collection to clean up.
            // How could this crash our program?
            if (dbConnection != null) {
                try {
                    dbConnection.close();
                } catch (SQLException ex) {

                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {

                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {

                }
            }

        }

    }
}
