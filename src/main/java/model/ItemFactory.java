package model;

public class ItemFactory {
    public static Item newItem(String name,int amount,float weight,String custom){
        ItemBasic i=new ItemBasic();
        fillItem(i,name,amount,weight);
        i.Custom=custom;
        return i;
    }//♦
     public static Item newItem(String name, int amount, float weight, int clockspeed, int cores){
        ItemCpu i=new ItemCpu();
        fillItem(i,name,amount,weight);
        i.clockSpeed=clockspeed;
        i.cores=cores;
        return i;
    }//♦
     public static Item newItem(String name, int amount, float weight, float capacity, HardDriveType type){
        ItemDrive i=new ItemDrive();
        fillItem(i,name,amount,weight);
        i.capacity=capacity;
        i.type=type;
        return i;
    }//♦
    private static void fillItem(Item i,String name,int amount,float weight){
        i.name=name;
        i.amount=amount;
        i.weight=weight;
    }
}
