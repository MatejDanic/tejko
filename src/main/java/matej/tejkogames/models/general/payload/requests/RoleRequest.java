package matej.tejkogames.models.general.payload.requests;

public class RoleRequest {

    private String label;

    private String description;

    public RoleRequest() {
    }

    public RoleRequest(String label, String description) {
        this.label = label;
        this.description = description;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
