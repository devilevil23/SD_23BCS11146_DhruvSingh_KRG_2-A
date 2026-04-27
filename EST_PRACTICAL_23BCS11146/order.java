import java.util.HashMap;
import java.util.Map;

class Warehouse {
    private final Map<String, Integer> inventory = new HashMap<>();

    public Warehouse() {
        inventory.put("Laptop", 5);
        inventory.put("Smartphone", 10);
        inventory.put("Book", 20);
    }

    public boolean isAvailable(String product, int quantity) {
        return inventory.getOrDefault(product, 0) >= quantity;
    }

    public void reserveItem(String product, int quantity) {
        inventory.put(product, inventory.getOrDefault(product, 0) - quantity);
    }
}

class PaymentGateway {
    public boolean makePayment(String userEmail, double amount) {
        System.out.println("Processing payment for " + userEmail + " amount: $" + amount);
        return amount > 0;
    }
}

class EmailService {
    public void sendReceipt(String userEmail, String orderDetails) {
        System.out.println("Sending receipt to " + userEmail + ":\n" + orderDetails);
    }
}

class OrderFacade {
    private final Warehouse warehouse;
    private final PaymentGateway paymentGateway;
    private final EmailService emailService;

    public OrderFacade() {
        warehouse = new Warehouse();
        paymentGateway = new PaymentGateway();
        emailService = new EmailService();
    }

    public boolean placeOrder(String userEmail, String product, int quantity, double amount) {
        System.out.println("OrderFacade: Placing order for " + userEmail);

        if (!warehouse.isAvailable(product, quantity)) {
            System.out.println("Order failed: " + product + " is out of stock or insufficient quantity.");
            return false;
        }

        if (!paymentGateway.makePayment(userEmail, amount)) {
            System.out.println("Order failed: Payment processing failed.");
            return false;
        }

        warehouse.reserveItem(product, quantity);

        String orderDetails = String.format(
            "Order confirmed for %d x %s. Total amount: $%.2f", quantity, product, amount);
        emailService.sendReceipt(userEmail, orderDetails);

        System.out.println("Order placed successfully.");
        return true;
    }
}

public class order {
    public static void main(String[] args) {
        OrderFacade orderFacade = new OrderFacade();

        orderFacade.placeOrder("alice@example.com", "Laptop", 1, 1200.00);
        System.out.println();

        orderFacade.placeOrder("bob@example.com", "Book", 3, 45.00);
        System.out.println();

        orderFacade.placeOrder("charlie@example.com", "Laptop", 10, 12000.00);
    }
}
