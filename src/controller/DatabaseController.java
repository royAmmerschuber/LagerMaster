package controller;

public class DatabaseController {
    //Singleton stuff
    private static DatabaseController instance;
    public static DatabaseController getDatabase(){
        if(instance==null){
            instance=new DatabaseController();
        }
        return instance;
    }
    //functions
    private DatabaseController(){

    }

    public void query(String query,Object... param){

    }
}
