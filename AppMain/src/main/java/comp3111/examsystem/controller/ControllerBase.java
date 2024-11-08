package comp3111.examsystem.controller;

import comp3111.examsystem.model.*;

public abstract class ControllerBase {
    protected final DataStorage dataStorage;
    public ControllerBase() {
        dataStorage = new DataStorage("storage.json");
    }
}
