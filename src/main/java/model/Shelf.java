package model;

import controller.MyDatabaseController;

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
        if(items[row][col]==null) items[row][col]=MyDatabaseController.getInst().getItems(id,row,col);
        return items[row][col];
    }

    public void newItem(int row,int col,Item item){
        addItem(row, col, item);

        MyDatabaseController db=MyDatabaseController.getInst();
        item.id=db.insertItem(id,row,col,item);
    }
    public void addItem(int row,int col,Item item){
        if(items[row][col]==null){
            items[row][col]=new ArrayList<>();
        }
        Model m=Model.getInst();
        items[row][col].add(item);
        m.sendUpdate(m.getShelfIndex(this),row,col);
    }
    public int getWidth(){
        return items[0].length;

    }
    public int getHeight(){
        return items.length;

    }

    public void delete(int row,int col,Item i) {
        items[row][col].remove(i);
        MyDatabaseController.getInst().deleteItem(i.id);
        Model m=Model.getInst();
        m.sendUpdate(m.getShelfIndex(this),row,col);
    }
    public void deleteAll(int row,int col){
        items[row][col].clear();
        MyDatabaseController.getInst().deleteItems(id,row,col);
        Model m=Model.getInst();
        m.sendUpdate(m.getShelfIndex(this),row,col);
    }
}
