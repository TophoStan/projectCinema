package nl.avans.cinema.dataacces.api.calls;

import java.util.List;

public class ListAddItems {
    private List<AddItemRequest> items;

    public List<AddItemRequest> getItems() {
        return items;
    }

    public void setItems(List<AddItemRequest> items) {
        this.items = items;
    }
}
