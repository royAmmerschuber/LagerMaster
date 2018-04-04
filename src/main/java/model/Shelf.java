package model;

import java.util.ArrayList;
import java.util.List;

public class Shelf {
    public int id;
    public String name;
    private List<Item>[][] items;

    public Shelf(int rows,int columns,String name,Integer id) {
        items=new List[rows][columns];
        this.name=name;
        this.id=id;
    }

    public List<Item> getItems(int row,int col){
        return items[row][col];
    }

    public void addItem(int row,int col,Item item){
        if(items[row][col]==null){
            items[row][col]=new ArrayList<>();
        }
        items[row][col].add(item);
    }
    public int getWidth(){
        return items[0].length;

    }
    public int getHeight(){
        return items.length;

    }
}
