import java.sql.*;

public class Customer {

    public void addCustomer(String name, String email, String phone, String address) {
        String query = "INSERT INTO Customer (name, email, phone, address) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnect.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, phone);
            pstmt.setString(4, address);
            pstmt.executeUpdate();
            System.out.println("Customer added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewCustomer(int customerId) {
        String query = "SELECT * FROM Customer WHERE customer_id = ?";
        try (Connection conn = DatabaseConnect.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, customerId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println("Customer ID: " + rs.getInt("customer_id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("Phone: " + rs.getString("phone"));
                System.out.println("Address: " + rs.getString("address"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCustomer(int customerId, String name, String email, String phone, String address) {
        String query = "UPDATE Customer SET name = ?, email = ?, phone = ?, address = ? WHERE customer_id = ?";
        try (Connection conn = DatabaseConnect.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, phone);
            pstmt.setString(4, address);
            pstmt.setInt(5, customerId);
            pstmt.executeUpdate();
            System.out.println("Customer updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCustomer(int customerId) {
        String query = "DELETE FROM Customer WHERE customer_id = ?";
        try (Connection conn = DatabaseConnect.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, customerId);
            pstmt.executeUpdate();
            System.out.println("Customer deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
