package model;

import java.util.List;

public class Shelf {
    private List<Item>[][] items;

    public List<Item> getItems(int row,int col){
        return items[row][col];
    }

    public void addItem(int row,int col,Item item){
        items[row][col].add(item);
    }

    public void update(Item item) {
    }
}
