package liubomyr.stepanenko.bookstore.model.type;

public enum OrderStatus {
    COMPLETED("COMPLETED"),
    PENDING("PENDING"),
    DELIVERED("DELIVERED");

    private String value;

    OrderStatus(String value) {
        this.value = value;
    }
}
