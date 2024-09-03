import java.util.Scanner;

public class MainMenu {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Customer customerManagement = new Customer();
        Product productManagement = new Product();
        Order orderManagement = new Order();

        while (true) {
            System.out.println("==== Retail Management System ====");
            System.out.println("1. Customer Management");
            System.out.println("2. Product Management");
            System.out.println("3. Order Management");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    customerMenu(customerManagement, scanner);
                    break;
                case 2:
                    productMenu(productManagement, scanner);
                    break;
                case 3:
                    orderMenu(orderManagement, scanner);
                    break;
                case 4:
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void customerMenu(Customer customer, Scanner scanner) {
        System.out.println("==== Customer Management ====");
        System.out.println("1. Add Customer");
        System.out.println("2. View Customer");
        System.out.println("3. Update Customer");
        System.out.println("4. Delete Customer");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        switch (choice) {
            case 1:
                System.out.print("Enter name: ");
                String name = scanner.nextLine();
                System.out.print("Enter email: ");
                String email = scanner.nextLine();
                System.out.print("Enter phone: ");
                String phone = scanner.nextLine();
                System.out.print("Enter address: ");
                String address = scanner.nextLine();
                customer.addCustomer(name, email, phone, address);
                break;
            case 2:
                System.out.print("Enter customer ID: ");
                int customerId = scanner.nextInt();
                customer.viewCustomer(customerId);
                break;
            case 3:
                System.out.print("Enter customer ID: ");
                customerId = scanner.nextInt();
                scanner.nextLine(); // consume newline
                System.out.print("Enter new name: ");
                name = scanner.nextLine();
                System.out.print("Enter new email: ");
                email = scanner.nextLine();
                System.out.print("Enter new phone: ");
                phone = scanner.nextLine();
                System.out.print("Enter new address: ");
                address = scanner.nextLine();
                customer.updateCustomer(customerId, name, email, phone, address);
                break;
            case 4:
                System.out.print("Enter customer ID: ");
                customerId = scanner.nextInt();
                customer.deleteCustomer(customerId);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private static void productMenu(Product product, Scanner scanner) {
        System.out.println("==== Product Management ====");
        System.out.println("1. Add Product");
        System.out.println("2. View Product");
        System.out.println("3. Update Product");
        System.out.println("4. Delete Product");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        switch (choice) {
            case 1:
                System.out.print("Enter product name: ");
                String name = scanner.nextLine();
                System.out.print("Enter product category: ");
                String category = scanner.nextLine();
                System.out.print("Enter product price: ");
                double price = scanner.nextDouble();
                System.out.print("Enter stock quantity: ");
                int stockQuantity = scanner.nextInt();
                product.addProduct(name, category, price, stockQuantity);
                break;
            case 2:
                System.out.print("Enter product ID: ");
                int productId = scanner.nextInt();
                product.viewProduct(productId);
                break;
            case 3:
                System.out.print("Enter product ID: ");
                productId = scanner.nextInt();
                scanner.nextLine(); // consume newline
                System.out.print("Enter new product name: ");
                name = scanner.nextLine();
                System.out.print("Enter new product category: ");
                category = scanner.nextLine();
                System.out.print("Enter new product price: ");
                price = scanner.nextDouble();
                System.out.print("Enter new stock quantity: ");
                stockQuantity = scanner.nextInt();
                product.updateProduct(productId, name, category, price, stockQuantity);
                break;
            case 4:
                System.out.print("Enter product ID: ");
                productId = scanner.nextInt();
                product.deleteProduct(productId);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private static void orderMenu(Order order, Scanner scanner) {
        System.out.println("==== Order Management ====");
        System.out.println("1. Place Order");
        System.out.println("2. View Order");
        System.out.println("3. Update Order Status");
        System.out.println("4. Cancel Order");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        switch (choice) {
            case 1:
                System.out.print("Enter customer ID: ");
                int customerId = scanner.nextInt();
                System.out.print("Enter the number of products: ");
                int numProducts = scanner.nextInt();

                int[] productIds = new int[numProducts];
                int[] quantities = new int[numProducts];

                for (int i = 0; i < numProducts; i++) {
                    System.out.print("Enter product ID for item " + (i + 1) + ": ");
                    productIds[i] = scanner.nextInt();
                    System.out.print("Enter quantity for item " + (i + 1) + ": ");
                    quantities[i] = scanner.nextInt();
                }

                order.placeOrder(customerId, productIds, quantities);
                break;
            case 2:
                System.out.print("Enter order ID: ");
                int orderId = scanner.nextInt();
                order.viewOrder(orderId);
                break;
            case 3:
                System.out.print("Enter order ID: ");
                orderId = scanner.nextInt();
                scanner.nextLine(); // consume newline
                System.out.print("Enter new order status (pending/confirmed/cancelled): ");
                String status = scanner.nextLine();
                order.updateOrderStatus(orderId, status);
                break;
            case 4:
                System.out.print("Enter order ID: ");
                orderId = scanner.nextInt();
                order.cancelOrder(orderId);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
}
