package model;

public abstract class Item {
    public int id;
    public String name;
    public int amount;
    public float weight;
    public Shelf parent;

    private void updateParent(){
        parent.update(this);
    }
}
