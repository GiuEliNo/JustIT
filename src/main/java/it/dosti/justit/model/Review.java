package it.dosti.justit.model;

public class Review {
    private String title;
    private Integer star;  //deve andare da 1 a 5 stelle
    private String review;
    private Shop shop;

    public Review(String title, Integer star, String review, Shop shop) {
        this.title = title;
        this.star = star;
        this.review = review;
        this.shop = shop;
    }

    public Shop getShop() {
        return shop;
    }
    public void setShop(Shop shop) {
        this.shop = shop;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Integer getStar() {
        return star;
    }
    public void setStar(Integer star) {
        this.star = star;
    }
    public String getReview() {
        return review;
    }
    public void setReview(String review) {
        this.review = review;
    }
}
