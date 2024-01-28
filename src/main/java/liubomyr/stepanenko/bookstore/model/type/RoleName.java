package liubomyr.stepanenko.bookstore.model.type;

public enum RoleName {
    ADMIN("ADMIN"),
    USER("USER");

    private String value;

    RoleName(String value) {
        this.value = value;
    }
}
