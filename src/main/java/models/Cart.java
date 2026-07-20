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


}
