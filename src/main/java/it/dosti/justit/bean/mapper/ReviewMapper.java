package it.dosti.justit.bean.mapper;

import it.dosti.justit.bean.ReviewBean;
import it.dosti.justit.model.Review;

import java.util.List;

public class ReviewMapper {


    public static List<ReviewBean> toBeans(List<Review> reviews) {

        return reviews.stream()
                .map(ReviewMapper::toBean)
                .toList();
    }


    public static ReviewBean toBean(Review review) {

        ReviewBean bean = new ReviewBean();

        bean.setTitle(review.getTitle());
        bean.setReview(review.getReview());
        bean.setStars(review.getStar());

        if (review.getBooking() != null) {

            bean.setUsername(
                    review.getBooking()
                            .getUser()
                            .getUsername()
            );

            bean.setBookingId(
                    review.getBooking()
                            .getBookingId()
            );
        }


        return bean;
    }
}
