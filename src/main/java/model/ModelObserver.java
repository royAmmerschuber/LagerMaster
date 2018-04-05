package model;

public interface ModelObserver {
    boolean getUpdate(Integer shelf, Integer row, Integer column);

    boolean getUpdate(Integer shelf, Integer row, Integer column, String action);
}
