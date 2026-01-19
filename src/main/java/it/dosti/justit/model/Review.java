package it.dosti.justit.model;

public class Review {
    private String title;
    private Integer star;  //deve andare da 1 a 5 stelle
    private String review;
    private Integer shop;
    private String username;

    public Review(String title, Integer star, String review, Integer shop, String username) {
        this.title = title;
        this.star = star;
        this.review = review;
        this.shop = shop;
        this.username = username;
    }

    public Integer getShop() {
        return shop;
    }
    public void setShop(Integer shop) {
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

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
