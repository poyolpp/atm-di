package atmDatabase;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

@Component
public class DataSource {

    private String filename;

    /**
     * @param filename the name of the customer file
     */
    public DataSource() {

    }

    /**
     * Reads the customer numbers and pins
     * and initializes the bank accounts.
     */
//    public Map<Integer, Customer> readCustomersFile() throws IOException {
//        Map<Integer, Customer> customers = new HashMap<Integer, Customer>();
//
//        Scanner in = new Scanner(new FileReader(filename));
//        while (in.hasNext()) {
//            int number = in.nextInt();
//            int pin = in.nextInt();
//            double currentBalance = in.nextDouble();
//
//            Customer c = new Customer(number, pin, currentBalance);
//            customers.put(c.getCustomerNumber(), c);
//        }
//        in.close();
//        return customers;
//    }

    public Map<Integer, Customer> readCustomers() throws IOException {
        Map<Integer, Customer> customers = new HashMap<Integer, Customer>();
        try {
// setup
            Class.forName("org.sqlite.JDBC");

            String dbURL = "jdbc:sqlite:AtmData.db";
            Connection conn = DriverManager.getConnection(dbURL);
            if (conn != null) {
                System.out.println("Connected to the database....");
// display database information
                DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();
                System.out.println("Driver name: " + dm.getDriverName());
                System.out.println("Product name: " + dm.getDatabaseProductName());
// execute SQL statements
                System.out.println("----- Data in Book table -----");
                String query = "Select * from Customer";
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    int customerID = resultSet.getInt(1);
                    int pin = resultSet.getInt(2);
                    double account = resultSet.getDouble(3);

                    Customer c = new Customer(customerID, pin, account);
                    customers.put(c.getCustomerNumber(), c);

                }
// close connection
                conn.close();
            }
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return customers;
    }

}
