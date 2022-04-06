package nl.avans.cinema.dataacces.api.calls;

import java.util.List;

public class ListRemoveItems {
    private List<DeleteItemRequest> items;

    public List<DeleteItemRequest> getItems() {
        return items;
    }

    public void setItems(List<DeleteItemRequest> items) {
        this.items = items;
    }
}
