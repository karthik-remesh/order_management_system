import java.sql.*;
import java.util.Date;

public class Order {

    public void placeOrder(int customerId, int[] productIds, int[] quantities) {
        String insertOrderQuery = "INSERT INTO `Order` (customer_id, order_date, status) VALUES (?, ?, ?)";
        String insertOrderItemQuery = "INSERT INTO Order_Item (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnect.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement orderStmt = conn.prepareStatement(insertOrderQuery,
                    Statement.RETURN_GENERATED_KEYS)) {
                orderStmt.setInt(1, customerId);
                orderStmt.setDate(2, new java.sql.Date(new Date().getTime()));
                orderStmt.setString(3, "pending");
                orderStmt.executeUpdate();

                ResultSet rs = orderStmt.getGeneratedKeys();
                if (rs.next()) {
                    int orderId = rs.getInt(1);

                    try (PreparedStatement orderItemStmt = conn.prepareStatement(insertOrderItemQuery)) {
                        for (int i = 0; i < productIds.length; i++) {
                            int productId = productIds[i];
                            int quantity = quantities[i];

                            // Get product price
                            double price = getProductPrice(conn, productId);

                            orderItemStmt.setInt(1, orderId);
                            orderItemStmt.setInt(2, productId);
                            orderItemStmt.setInt(3, quantity);
                            orderItemStmt.setDouble(4, price * quantity);
                            orderItemStmt.addBatch();

                            // Update product stock
                            updateProductStock(conn, productId, -quantity);
                        }
                        orderItemStmt.executeBatch();
                    }
                }

                conn.commit();
                System.out.println("Order placed successfully.");
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private double getProductPrice(Connection conn, int productId) throws SQLException {
        String query = "SELECT price FROM Product WHERE product_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, productId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("price");
            }
        }
        throw new SQLException("Product not found.");
    }

    private void updateProductStock(Connection conn, int productId, int quantityChange) throws SQLException {
        String query = "UPDATE Product SET stock_quantity = stock_quantity + ? WHERE product_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, quantityChange);
            pstmt.setInt(2, productId);
            pstmt.executeUpdate();
        }
    }

    public void viewOrder(int orderId) {
        String orderQuery = "SELECT * FROM `Order` WHERE order_id = ?";
        String orderItemQuery = "SELECT * FROM Order_Item WHERE order_id = ?";

        try (Connection conn = DatabaseConnect.getConnection()) {
            try (PreparedStatement orderStmt = conn.prepareStatement(orderQuery)) {
                orderStmt.setInt(1, orderId);
                ResultSet rsOrder = orderStmt.executeQuery();

                if (rsOrder.next()) {
                    System.out.println("Order ID: " + rsOrder.getInt("order_id"));
                    System.out.println("Customer ID: " + rsOrder.getInt("customer_id"));
                    System.out.println("Order Date: " + rsOrder.getDate("order_date"));
                    System.out.println("Status: " + rsOrder.getString("status"));

                    try (PreparedStatement orderItemStmt = conn.prepareStatement(orderItemQuery)) {
                        orderItemStmt.setInt(1, orderId);
                        ResultSet rsOrderItems = orderItemStmt.executeQuery();

                        System.out.println("Order Items:");
                        while (rsOrderItems.next()) {
                            System.out.println("  Product ID: " + rsOrderItems.getInt("product_id"));
                            System.out.println("  Quantity: " + rsOrderItems.getInt("quantity"));
                            System.out.println("  Price: " + rsOrderItems.getDouble("price"));
                        }
                    }
                } else {
                    System.out.println("Order not found.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateOrderStatus(int orderId, String status) {
        String query = "UPDATE `Order` SET status = ? WHERE order_id = ?";
        try (Connection conn = DatabaseConnect.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, status);
            pstmt.setInt(2, orderId);
            pstmt.executeUpdate();
            System.out.println("Order status updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cancelOrder(int orderId) {
        String query = "UPDATE `Order` SET status = 'cancelled' WHERE order_id = ?";
        try (Connection conn = DatabaseConnect.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, orderId);
            pstmt.executeUpdate();
            System.out.println("Order cancelled successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
