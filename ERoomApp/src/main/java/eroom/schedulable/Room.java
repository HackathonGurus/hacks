package eroom.schedulable;

import java.util.Set;

/**
 * Class models a Room. Calendar logic is contained in the superclass
 */
public class Room extends ScheduleObject implements Comparable<Room> {

    /** The name of the room */
    private String roomName;

    /** The maximum capacity of the room */
    private int capacity;

    /** A brief description of the room (e.g. contains a phone, a TV, has internet access */
    private String description;
    
    private Set<RoomFeature> features;

    public Room(String roomName, int capacity) {
        super();
        this.roomName = roomName;
        this.capacity = capacity;
    }

    public String getRoomName() {
        return this.roomName;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Room withDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Forces rooms to have a natural ordering of lowest capacity first
     *
     * TODO is this right? Should the first if return 1 or -1 to put smallest capacity first?
     *
     * @param room2
     * @return
     */
    @Override
    public int compareTo(Room room2) {
        if (room2.getCapacity() > getCapacity()) {
            return -1;
        } else if (room2.getCapacity() == getCapacity()) {
            return 0;
        }
        return 1;
    }
    
    public String getFeaturesAsString() {
        StringBuilder featureString = new StringBuilder();
        for (RoomFeature feature : features) {
            featureString.append(feature.getDisplayName());
            featureString.append(", ");
        }
        featureString.replace(featureString.length() - 3, featureString.length() - 1, "");
        return featureString.toString();
    }
}
