package models;

public class Part {
    private String partCode;
    private String name;
    private String brand;
    private double price;
    private int quantity;
    private String category;
    private String dateAdded;
    private String imageFile;
    private int threshold;

    public Part(String partCode, String name,  String brand, double price, int quantity, String category, String dataAdded, String imageFile, int threshold){
        this.partCode = partCode;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.dateAdded = dataAdded;
        this.imageFile = imageFile;
        this.threshold = threshold;
    }

    public String getName() {
        return name;
    }

    public String getPartCode() {
        return partCode;
    }

    public String getBrand() {
        return brand;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getCategory() {
        return category;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public String getImageFile() {
        return imageFile;
    }

    public void setPartCode(String partCode) {
        this.partCode = partCode;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }

    public void setDateAdded(String dataAdded) {
        this.dateAdded = dataAdded;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }



}
