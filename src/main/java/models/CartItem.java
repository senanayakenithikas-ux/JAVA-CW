package models;

public class CartItem {
    private Part part;
    private int quantity;

    public CartItem(Part part, int quantity){
        this.part = part;
        this.quantity = quantity;
    }
    public Part getPart() {
        return part;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPartName(){
        return part.getName();
    }

    public double getSubTotal(){
        return part.getPrice() * quantity;
    }


}
