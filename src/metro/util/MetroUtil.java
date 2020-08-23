package metro.util;

import metro.logic.StationNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class MetroUtil {
    
    public static LinkedList<StationNode> rawLineBuilder(HashMap rawLine) {
        LinkedList<StationNode> line = new LinkedList<>();
        for (int ii = 1; ii < rawLine.size(); ++ii) {
            String i = String.valueOf(ii);
            String name = (String) ((Map)((Map) rawLine.get(i)).get(i)).get("name");
        }
        return line;
    }
}
