package models;

import main.InventoryManager;

import java.util.ArrayList;

public class Cart {

    private ArrayList<CartItem> cartItems = new ArrayList<>();
    private InventoryManager inventoryManager;

    public Cart(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
    }

    public String addItem(Part part, int quantity) {
        if (part == null) {
            return "Error: Invalid Part.";
        }
        if (quantity <= 0) {
            return "Error: Quantity must be greater than zero.";
        }
        if (quantity > part.getQuantity()) {
            return "Error: Cannot exceed available stock.";
        }

        for (int i = 0; i < cartItems.size(); i++) {
            CartItem item = cartItems.get(i);
            if (item.getPart().getPartCode().equalsIgnoreCase(part.getPartCode())) {
                int newTotalQuantity = item.getQuantity() + quantity;

                if (newTotalQuantity > part.getQuantity()) {
                    return "Error: Total combined quantity exceeds available stock.";
                }
                item.setQuantity(newTotalQuantity);
                return "Updated quantity successfully.";
            }
        }
        cartItems.add(new CartItem(part,quantity));
        return "Added " + part.getName() + " to cart successfully.";

    }
    public void removeItem(String partCode) {
        for (int i =0; i < cartItems.size(); i++) {
            if (cartItems.get(i).getPart().getPartCode().equalsIgnoreCase(partCode)) {
                cartItems.remove(i);
                break;
            }
        }
    }

    public double calculateTotal() {
        double baseLineTotal = 0.0;
        boolean hasEngine = false;
        boolean hasElectrical = false;

        for (int i =0; i < cartItems.size(); i++) {
            CartItem item = cartItems.get(i);
            Part part = item.getPart();
            double subtotal = part.getPrice() * item.getQuantity();

            if (item.getQuantity() >= 3) {
                subtotal *= 0.95;
            }

            baseLineTotal += subtotal;

            if (part.getCategory().equalsIgnoreCase("Engine")) {
                hasEngine = true;
            }
            if (part.getCategory().equalsIgnoreCase("Electrical")) {
                hasElectrical = true ;
            }
        }
        if (hasEngine && hasElectrical) {
            baseLineTotal *= 0.90;
        }
        return baseLineTotal;
    }


}
