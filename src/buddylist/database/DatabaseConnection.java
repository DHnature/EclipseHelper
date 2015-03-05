package buddylist.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import edu.cmu.scs.fluorite.commands.ICommand;
import edu.cmu.scs.fluorite.model.EventRecorder;
import edu.cmu.scs.fluorite.model.Events;

public class DatabaseConnection {

	public DatabaseConnection() {

	}

	public static Connection connect() throws SQLException,
			ClassNotFoundException {
		Connection conn = null;

		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/commands", "root", "Ci$co123");

		return conn;
	}
	
	public static Vector<Log> getLastFiftyActions(String username)
	{
		Vector<Log> log = new Vector<Log>();
		try {
			Vector<Users> u = getUserInformation(username);
			if (u.size() == 1) {
				Connection connection = connect();

				PreparedStatement preparedStatement = connection
						.prepareStatement("SELECT * FROM log where userid = (?) ORDER BY id DESC LIMIT 50");
				preparedStatement.setString(1, username);
				
				//		.prepareStatement("select id,text,fromUser from chat where toUser = (?) and isProcessed = (?) order by id");

				ResultSet resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					int id = resultSet.getInt("id");
					String userId = resultSet.getString("userId");
					String commandType = resultSet.getString("commandType");
					String commandText = resultSet.getString("commandText");
					int timeStamp = resultSet.getInt("timestamp");
					int isProcessed = resultSet.getInt("isProcessed");
					Log l = new Log(id,userId,commandType,commandText,timeStamp,isProcessed);
					log.add(l);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return log;
	}

	public static boolean insertBuddy(String username, Users buddy) {
		boolean isSuccessful = true;
		try {
			Vector<Users> u = getUserInformation(username);
			if (u.size() == 1) {
				Connection connection = connect();

				PreparedStatement preparedStatement = connection
						.prepareStatement("insert into buddy (userId,buddyId)VALUES (?,?)");
				preparedStatement.setInt(1, u.get(0).getId());
				preparedStatement.setInt(2, buddy.getId());

				preparedStatement.executeUpdate();
			}
		} catch (SQLException e) {
			isSuccessful = false;
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			isSuccessful = false;
			e.printStackTrace();
		}
		return isSuccessful;
	}

	public static boolean insertMessage(String from, String to, String text,
			String time) {
		boolean isSuccessful = true;
		try {
			Connection connection = connect();

			PreparedStatement preparedStatement = connection
					.prepareStatement("insert into chat (fromUser,toUser,text,time,isProcessed)VALUES (?,?,?,?,?)");
			preparedStatement.setString(1, from);
			preparedStatement.setString(2, to);
			preparedStatement.setString(3, text);
			preparedStatement.setString(4, time);
			preparedStatement.setInt(5, 0);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			isSuccessful = false;
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			isSuccessful = false;
			e.printStackTrace();
		}
		return isSuccessful;
	}

	public static boolean insertLog(ICommand command) {
		boolean isSuccessful = true;
		try {
			Connection connection = connect();

			List<ICommand> commands = new ArrayList<ICommand>();
			commands.add(command);
			Events events = new Events(commands, "", "", "");

			String outputXML = EventRecorder.persistMacro(events);

			PreparedStatement preparedStatement = connection
					.prepareStatement("insert into log (userid,commandType,commandText,timestamp,isProcessed)VALUES (?,?,?,?,?)");
			preparedStatement.setString(1, DatabaseUtils.getLoggedInUser());
			preparedStatement.setString(2, command.getCommandType());
			preparedStatement.setString(3, outputXML);
			preparedStatement.setLong(4, command.getTimestamp());
			preparedStatement.setInt(5, 0);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			isSuccessful = false;
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			isSuccessful = false;
			e.printStackTrace();
		}

		return isSuccessful;
	}

	public static Vector<Chat> getUserMessages(String username) {
		Vector<Chat> messages = new Vector<Chat>();

		try {
			Connection connection = connect();
			PreparedStatement preparedStatement = connection
					.prepareStatement("select id,text,fromUser from chat where toUser = (?) and isProcessed = (?) order by id");
			preparedStatement.setString(1, username);
			preparedStatement.setInt(2, 0);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String text = resultSet.getString("text");
				String fromUser = resultSet.getString("fromUser");
				// need to do an update now
				PreparedStatement preparedStatementUpdate = connection
						.prepareStatement("update chat SET isProcessed=(?) where id = (?)");
				preparedStatementUpdate.setInt(1, 1);
				preparedStatementUpdate.setInt(2, id);
				preparedStatementUpdate.executeUpdate();

				Chat chatMessage = new Chat(id, fromUser, "", 1, text);
				messages.add(chatMessage);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return messages;
	}

	public static Vector<Users> getUsersBuddies(String enteredUsername) {
		Vector<Users> buddies = new Vector<Users>();
		Users u;
		try {
			Vector<Users> userInformation = getUserInformation(enteredUsername);
			if (userInformation.size() == 1) {
				Connection connection = connect();
				PreparedStatement preparedStatement = connection
						.prepareStatement("SELECT buddy.userId, username, firstname, lastname, photoLink FROM buddy"
								+ " JOIN users ON buddy.buddyId = users.id WHERE buddy.userId =(?)");
				preparedStatement.setInt(1, userInformation.get(0).getId());
				ResultSet resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					int id = resultSet.getInt("userId");
					String username = resultSet.getString("username");
					String firstname = resultSet.getString("firstname");
					String lastname = resultSet.getString("lastname");
					String photoLink = resultSet.getString("photoLink");
					u = new Users(id, username, firstname, lastname, photoLink);
					buddies.add(u);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return buddies;
	}

	public static Vector<Users> getUserInformation(String enteredUserName) {

		Users u;
		Vector<Users> users = new Vector<Users>();
		try {
			Connection connection = connect();
			PreparedStatement preparedStatement = connection
					.prepareStatement("select * from users where username = (?)");
			preparedStatement.setString(1, enteredUserName);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String username = resultSet.getString("username");
				String firstname = resultSet.getString("firstname");
				String lastname = resultSet.getString("lastname");
				String photoLink = resultSet.getString("photoLink");
				u = new Users(id, username, firstname, lastname, photoLink);
				users.add(u);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return users;
	}

	public static Vector<Users> getAllUsers() {
		Users u;
		Vector<Users> users = new Vector<Users>();
		try {
			Connection connection = connect();
			PreparedStatement preparedStatement = connection
					.prepareStatement("select * from users");
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String username = resultSet.getString("username");
				String firstname = resultSet.getString("firstname");
				String lastname = resultSet.getString("lastname");
				String photoLink = resultSet.getString("photoLink");
				u = new Users(id, username, firstname, lastname, photoLink);
				users.add(u);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return users;
	}

}
