package atmspring;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class CustomerRowMapper implements RowMapper<Customer> {
    public Customer mapRow(ResultSet rs, int i) throws SQLException {
        return new Customer(rs.getInt("customerNumber"),
                rs.getInt("pin"),
                rs.getDouble("balance"));
    }
}