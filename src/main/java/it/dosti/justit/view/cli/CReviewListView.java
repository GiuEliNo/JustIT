package it.dosti.justit.view.cli;

import it.dosti.justit.bean.ReviewBean;

public class CReviewListView extends BaseCliView{

    @Override
    public void render() {
        System.out.println("===== REVIEWS =====");
    }

    public void renderReview(ReviewBean review) {
        System.out.println("--------------------------------------------");
        System.out.println("Title   : " + review.getTitle());
        System.out.println("User    : " + review.getUsername());
        System.out.println("Rating  : " + stars(review.getStars()));
        System.out.println(" ");
        System.out.println(review.getReview());
        System.out.println("--------------------------------------------");
    }

    private String stars(int rating) {
        return "★".repeat(rating) + "☆".repeat(5 - rating);
    }

    public String askChoice() {
        System.out.println("0. Back");
        return scanner.nextLine();
    }
}
