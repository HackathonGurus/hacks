package eroom.schedulable;

/**
 * Class models a User. All Calendar logic is contained in the super class
 *
 * TODO need email address and phone number (and regex checks on them?)
 */
public class User extends ScheduleObject {

    /** The name of the user */
    private String name;
    
    private String email;

    public User(String name) {
        super();
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

	public String getEmailAddress() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
