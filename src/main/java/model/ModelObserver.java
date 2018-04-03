package main.java.model;

public interface ModelObserver {
    boolean getUpdate(int shelf, int row, int column);
}
