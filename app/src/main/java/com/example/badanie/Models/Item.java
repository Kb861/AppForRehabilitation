package com.example.badanie.Models;

import java.io.Serializable;

/**
 * Created by patry on 22.03.2019.
 */

public class Item implements Serializable {

    private String name;
    private boolean isSelected;

    public Item(String name) {

        this.name = name;
        this.isSelected = false;
    }

    public String getName() {
        return name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
}
