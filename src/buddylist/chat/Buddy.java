package buddylist.chat;



public class Buddy  {

	public Buddy()
	{

	}
	
	public Buddy(int id, String username, String firstName, String lastName)
	{
		this.id = id;
		this.username = username;
		this.fullname = "(" + username + ")     " + firstName + " " + lastName;
		this.status = "";
		this.mostRecentAction = "";
		this.filename = "";
		this.lastname = lastName;
		this.firstname = firstName;
	}
	
	private int id;
	private String fullname;
	private String status;
	private String mostRecentAction;
	private String filename;
	private String username;
	private String firstname;
	private String lastname;
	
	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return fullname;
	}
	public void setName(String name) {
		this.fullname = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMostRecentAction() {
		return mostRecentAction;
	}
	public void setMostRecentAction(String mostRecentAction) {
		this.mostRecentAction = mostRecentAction;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
