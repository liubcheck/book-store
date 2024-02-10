package liubomyr.stepanenko.bookstore.model.type;

public enum Status {
    COMPLETED("COMPLETED"),
    PENDING("PENDING"),
    DELIVERED("DELIVERED");

    private String value;

    Status(String value) {
        this.value = value;
    }
}
