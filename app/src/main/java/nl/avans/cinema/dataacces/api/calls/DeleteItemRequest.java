package nl.avans.cinema.dataacces.api.calls;

public class DeleteItemRequest {
    private String media_type;
    private int media_id;

    public DeleteItemRequest(String media_type, int media_id) {
        this.media_type = media_type;
        this.media_id = media_id;
    }
}
