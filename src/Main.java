import java.io.*;
import java.util.LinkedList;

public class Main {
    private static LinkedList<String> stations = new LinkedList<>();
    
    public static void main(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("No args passed into the program. " +
                    "Expected filename as an argument.");
        }
        
        File file = new File(args[0]);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            br.lines().forEach(station -> stations.offer(station.trim()));
            if (!stations.isEmpty()) {
                stations.offer("depot");
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        if (!stations.isEmpty()) {
            printStations();
        }
    }
    
    protected static void printStations() {
        System.out.println(stations.getLast() + " - " +
                stations.getFirst() +" - " +
                stations.get(1));
        for (int i = 0; i < stations.size() - 2; ++i) {
            System.out.println(stations.get(i) + " - " +
                    stations.get(i + 1) + " - " +
                    stations.get(i + 2));
        }
    }
}

class T {
    public static void main(String[] args) throws ClassNotFoundException {

    }
}