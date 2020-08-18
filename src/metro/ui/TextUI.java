package metro.ui;

import metro.logic.ConsoleProcessor;
import metro.logic.MetroNetwork;

import java.io.File;
import java.util.Scanner;

public class TextUI {
    
    private MetroNetwork network;
    private File json;
    private final Scanner SCN;
    
    public TextUI(String[] args, Scanner scn) {
        validateArgs(args);
        this.SCN = scn;
        network = new MetroNetwork(json);
    }
    
    public void start() {
        ConsoleProcessor cp = new ConsoleProcessor();
        while (true) {
            if (!cp.processLine(SCN.nextLine())) {
                System.out.println("Invalid command");
                continue;
            }
            boolean exitFlag = false;
            boolean invalidCmd = false;
            if (cp.getCmd().equals("exit")) {
                break;
            }
            if (cp.getArgs() == null) {
                System.out.println("Invalid command");
                continue;
            }
            switch (cp.getCmd()) {
                case "append":
                    append(cp.getArgs());
                    break;
                case "add-head":
                    addHead(cp.getArgs());
                    break;
                case "remove":
                    remove(cp.getArgs());
                    break;
                case "output":
                case "print":
                    output(cp.getArgs());
                    break;
                case "exit":
                    exitFlag = true;
                    break;
                default:
                    invalidCmd = true;
                    break;
            }
            if (invalidCmd) {
                System.out.println("Invalid command");
                continue;
            }
            if (exitFlag) {
                break;
            }
        }
    }
    
    private void append(String[] args) {
        network.appendStation(args[0], args[1]);
    }
    
    private void addHead(String[] args) {
        network.addStationToHead(args[0], args[1]);
        
    }
    
    private void remove(String[] args) {
        network.removeStation(args[0], args[1]);
    }
    
    private void output(String[] args) {
        network.printLine(args[0]);
    }
    
    private void validateArgs(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("Expected filename as argument.");
        }
        json = new File(args[0]);
    }
}
