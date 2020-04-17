package tes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.scene.layout.AnchorPane;

import java.io.Serializable;
import java.util.ArrayList;


public class saver implements Serializable {
     ArrayList shapesCollection;
    public saver() {
        shapesCollection= new ArrayList<shape>();
    }
    public saver(ObservableList shapesCollection) {
        this.shapesCollection= new ArrayList(shapesCollection);
    }



}
