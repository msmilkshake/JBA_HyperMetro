package metro.ui;

import metro.logic.ConsoleProcessor;
import metro.logic.MetroNetwork;

import java.io.File;
import java.util.Scanner;

public class TextUI {
    
    private static final Scanner SCANNER = new Scanner(System.in);
    private MetroNetwork NETWORK;
    private final ConsoleProcessor CONSOLE;
    
    private File json;
    
    public TextUI(String[] args) {
        validateArgs(args);
        NETWORK = new MetroNetwork(json);
        CONSOLE = new ConsoleProcessor();
    }
    
    public void start() {
        while (true) {
            if (!CONSOLE.processLine(SCANNER.nextLine())) {
                System.out.println("Invalid command");
                continue;
            }
            boolean exitFlag = false;
            boolean invalidCmd = false;
            if (CONSOLE.getCmd().equals("exit")) {
                break;
            }
            if (CONSOLE.getArgs() == null) {
                System.out.println("Invalid command");
                continue;
            }
            switch (CONSOLE.getCmd()) {
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
                    connect();
                    break;
                case "route":
                    route();
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
        NETWORK.appendStation(CONSOLE.getArgs()[0], CONSOLE.getArgs()[1]);
    }
    
    private void addHead() {
        NETWORK.addStationToHead(CONSOLE.getArgs()[0], CONSOLE.getArgs()[1]);
        
    }
    
    private void remove() {
        NETWORK.removeStation(CONSOLE.getArgs()[0], CONSOLE.getArgs()[1]);
    }
    
    private void output() {
        if ("!graph".equals(CONSOLE.getArgs()[0])) {
            NETWORK.printGraph();
        } else {
            NETWORK.printLine(CONSOLE.getArgs()[0]);
        }
    }
    
    private void connect() {
        NETWORK.connectStations(
                CONSOLE.getArgs()[0],
                CONSOLE.getArgs()[1],
                CONSOLE.getArgs()[2],
                CONSOLE.getArgs()[3]);
    }
    
    private void route() {
        NETWORK.printRoute(
                CONSOLE.getArgs()[0],
                CONSOLE.getArgs()[1],
                CONSOLE.getArgs()[2],
                CONSOLE.getArgs()[3]);
    }
    
    private void validateArgs(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("Expected filename as argument.");
        }
        json = new File(args[0]);
        if (!json.exists()) {
            System.out.println("File \"" + json + "\" does not exist");
            System.exit(0);
        }
    }
}
