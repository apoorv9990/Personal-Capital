package com.personal.capital.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by patel on 6/3/2017.
 */

public class Feed {
    private String title;
    private List<Item> items;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void addItem(Item item) {
        if(items == null) items = new ArrayList<>();

        items.add(item);
    }
}
