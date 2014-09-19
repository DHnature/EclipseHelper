package buddylist.database;

public class Users {
	
	public Users()
	{
		
	}
	
	private int id;
	private String username;
	private String firstName;
	private String lastName;
	private String photoLink;
	
	public Users(int id, String username, String firstName, String lastName, String photoLink)
	{
		this.id = id;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.photoLink = photoLink;
	}
	

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPhotoLink() {
		return photoLink;
	}
	public void setPhotoLink(String photoLink) {
		this.photoLink = photoLink;
	}
}
