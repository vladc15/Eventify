package model;

import user.User;

import java.util.Scanner;

public class Review {
    private int reviewId;
    //private int eventId;
    //private int userId;
    private Event event;
    private User user;
    private double rating;
    private String comment;

    public Review() {
        this.reviewId = 0;
        //this.eventId = 0;
        //this.userId = 0;
        this.rating = 0.0;
        this.comment = "";
    }

    public Review(int reviewId, Event event, User user, double rating, String comment) {
        this.reviewId = reviewId;
        //this.eventId = eventId;
        //this.userId = userId;
        this.event = event;
        this.user = user;

        this.rating = rating;
        this.comment = comment;
    }

    public Review(Review review) {
        this.reviewId = review.getReviewId();
        //this.eventId = review.getEventId();
        //this.userId = review.getUserId();
        this.event = review.getEvent();
        this.user = review.getUser();

        this.rating = review.getRating();
        this.comment = review.getComment();
    }

    public int getReviewId() { return reviewId; }
    //public int getEventId() { return eventId; }
    //public int getUserId() { return userId; }
    public double getRating() { return rating; }
    public String getComment() { return comment; }
    public Event getEvent() { return event; }
    public User getUser() { return user; }

    public void setReviewId(int reviewId) { this.reviewId = reviewId; }
    //public void setEventId(int eventId) { this.eventId = eventId; }
    //public void setUserId(int userId) { this.userId = userId; }
    public void setRating(double rating) { this.rating = rating; }
    public void setComment(String comment) { this.comment = comment; }
    public void setEvent(Event event) { this.event = event; }
    public void setUser(User user) { this.user = user; }
    
    public String toString() { return "Review ID: " + reviewId + ", Event: " + event.toString() + ", User Id: " + user.getUserId() + ", Rating: " + rating + ", Comment: " + comment; }
    public String toCSV() { return reviewId + "," + event.toCSV() + "," + user.toCSV() + "," + rating + "," + comment; }
    
    public boolean equals(Review review) { return reviewId == review.getReviewId() && event.equals(review.getEvent()) && user.equals(review.getUser()) && rating == review.getRating() && comment.equals(review.getComment()); }

    public void fromCSV(String csv) {
        String[] values = csv.split(",");
        this.reviewId = Integer.parseInt(values[0]);
        //this.eventId = Integer.parseInt(values[1]);
        //this.userId = Integer.parseInt(values[2]);
        /*this.event = new model.Event();
        this.event.fromCSV(values[1]);
        this.user = new user.User();
        this.user.fromCSV(values[2]);
        this.rating = Double.parseDouble(values[3]);
        this.comment = values[4];*/
        this.rating = Double.parseDouble(values[1]);
        this.comment = values[2];
    }
    
    public void fromInput(Scanner scanner) {
        /*System.out.print("Enter event: ");
        //this.eventId = scanner.nextInt();
        this.event = new model.Event();
        this.event.fromInput(scanner);
        System.out.print("Enter user: ");
        //this.userId = scanner.nextInt();
        this.user = new user.User();
        this.user.fromInput(scanner);*/
        System.out.print("Enter rating: ");
        this.rating = scanner.nextDouble();
        System.out.print("Enter comment: ");
        this.comment = scanner.nextLine();
    }
}
