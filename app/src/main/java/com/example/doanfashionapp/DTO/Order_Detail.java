package com.example.doanfashionapp.DTO;

public class Order_Detail {

    private String orderId;
    private String product_variation_Id;
    private int quantity;
    private int subtotal;

    public Order_Detail(String orderId, String product_variation_Id, int quantity, int subtotal) {

        this.orderId = orderId;
        this.product_variation_Id = product_variation_Id;
        this.quantity = quantity;
        this.subtotal = subtotal;
    }



    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductVariationId() {
        return product_variation_Id;
    }

    public void setProductVariationId(String productId) {
        this.product_variation_Id = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(int subtotal) {
        this.subtotal = subtotal;
    }


}
