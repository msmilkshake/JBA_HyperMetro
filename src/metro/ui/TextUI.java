package metro.ui;

import metro.logic.MetroNetwork;

import java.io.File;

public class TextUI {
    
    private MetroNetwork network;
    private File json;
    
    public TextUI(String[] args) {
        validateArgs(args);
        network = new MetroNetwork(json);
    }
    
    public void start() {
    
    }
    
    private void validateArgs(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("No args passed into the program. " +
                    "Expected filename as an argument.");
        }
        json = new File(args[0]);
    }
}
