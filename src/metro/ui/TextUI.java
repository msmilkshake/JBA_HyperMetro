package metro.ui;

import metro.logic.ConsoleProcessor;
import metro.logic.MetroNetwork;

import java.io.File;
import java.util.Scanner;

public class TextUI {
    
    private final Scanner SCN;
    
    private MetroNetwork network;
    private File json;
    private ConsoleProcessor cp;
    
    public TextUI(String[] args, Scanner scn) {
        validateArgs(args);
        this.SCN = scn;
        network = new MetroNetwork(json);
        cp = new ConsoleProcessor();
    }
    
    public void start() {
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
                    append();
                    break;
                case "add-head":
                    addHead();
                    break;
                case "remove":
                    remove();
                    break;
                case "output":
                case "print":
                    output();
                    break;
                case "connect":
                    
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
    
    private void append() {
        network.appendStation(cp.getArgs()[0], cp.getArgs()[1]);
    }
    
    private void addHead() {
        network.addStationToHead(cp.getArgs()[0], cp.getArgs()[1]);
        
    }
    
    private void remove() {
        network.removeStation(cp.getArgs()[0], cp.getArgs()[1]);
    }
    
    private void output() {
        network.printLine(cp.getArgs()[0]);
    }
    
    private void validateArgs(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("Expected filename as argument.");
        }
        json = new File(args[0]);
    }
}
