import java.sql.*;

public class Product {

    public void addProduct(String name, String category, double price, int stockQuantity) {
        String query = "INSERT INTO Product (name, category, price, stock_quantity) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnect.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, name);
            pstmt.setString(2, category);
            pstmt.setDouble(3, price);
            pstmt.setInt(4, stockQuantity);
            pstmt.executeUpdate();
            System.out.println("Product added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewProduct(int productId) {
        String query = "SELECT * FROM Product WHERE product_id = ?";
        try (Connection conn = DatabaseConnect.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, productId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println("Product ID: " + rs.getInt("product_id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Category: " + rs.getString("category"));
                System.out.println("Price: " + rs.getDouble("price"));
                System.out.println("Stock Quantity: " + rs.getInt("stock_quantity"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateProduct(int productId, String name, String category, double price, int stockQuantity) {
        String query = "UPDATE Product SET name = ?, category = ?, price = ?, stock_quantity = ? WHERE product_id = ?";
        try (Connection conn = DatabaseConnect.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, name);
            pstmt.setString(2, category);
            pstmt.setDouble(3, price);
            pstmt.setInt(4, stockQuantity);
            pstmt.setInt(5, productId);
            pstmt.executeUpdate();
            System.out.println("Product updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteProduct(int productId) {
        String query = "DELETE FROM Product WHERE product_id = ?";
        try (Connection conn = DatabaseConnect.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, productId);
            pstmt.executeUpdate();
            System.out.println("Product deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
