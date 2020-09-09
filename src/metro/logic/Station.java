package metro.logic;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Station {
    
    private final String line;
    private final String station;
    private Map<Station, Station> transfers = new HashMap<>(5, 1.0f);
    
    public Station(String line, String station) {
        this.line = line;
        this.station = station;
    }
    
    public Station addTransfer(Station station) {
        return transfers.putIfAbsent(station, station);
    }
    
    public Station addTransfer(String line, String station) {
        Station s = new Station(line, station);
        return transfers.putIfAbsent(s, s);
    }
    
    public String getLine() {
        return new String(line);
    }
    
    public String getStation() {
        return new String(station);
    }
    
    public Set<Station> getTransfers() {
        return transfers.keySet();
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Station)) {
            return false;
        }
        Station station1 = (Station) o;
        return Objects.equals(line, station1.line) &&
                Objects.equals(station, station1.station);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(line, station);
    }
    
    @Override
    public String toString() {
        return station;
    }
}
