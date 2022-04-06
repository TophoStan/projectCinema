package nl.avans.cinema.dataacces.api.calls;

public class AddItemResult {

    private String status_message;
    private boolean success;

    public String getStatus_message() {
        return status_message;
    }

    public void setStatus_message(String status_message) {
        this.status_message = status_message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
