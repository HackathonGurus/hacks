package eroom.schedulable;

/**
 * TODO add more features...
 *
 */
public enum RoomFeature {
    
    TV("TV"),
    PHONE("Phone");
    
    private RoomFeature(String displayName) {
        this.displayName = displayName;
    }
    
    private String displayName;
    
    public String getDisplayName() {
        return this.displayName;
    }

}
