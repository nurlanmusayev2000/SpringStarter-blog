package Mn.learning.SpringStarter.util.constants;

public enum Privilage {
    RESET_ANY_USER_PASSWORD(1L, "RESET_ANY_USER_PASSWORD"),
    ACCESS_ADMIN_PANEL(2L, "ACCESS_ADMIN_PANEL");

    private final Long id;
    private final String privilage;

    Privilage(Long id, String privilage) {
        this.id = id;
        this.privilage = privilage;
    }

    public Long getId() {
        return id;
    }

    public String getPrivilage() {
        return privilage;
    }
}
