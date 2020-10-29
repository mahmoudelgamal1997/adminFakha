package com.example.adminfakha.Model;

public class OfferModel {
    String category,id ,product_name ,unit,image;
    boolean active;
    int order_quantity,price,limit,price_offer;


    public OfferModel() {
    }

    public OfferModel(int price,int limit,int price_offer ,String image,String category, String id, String product_name, String unit, boolean active, int order_quantity) {
        this.price=price;
        this.category = category;
        this.id = id;
        this.product_name = product_name;
        this.unit = unit;
        this.active = active;
        this.order_quantity = order_quantity;
        this.limit=limit;
        this.price_offer= price_offer;
        this.image=image;

    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getOrder_quantity() {
        return order_quantity;
    }

    public void setOrder_quantity(int order_quantity) {
        this.order_quantity = order_quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getPrice_offer() {
        return price_offer;
    }

    public void setPrice_offer(int price_offer) {
        this.price_offer = price_offer;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
