package buddylist.database;

import java.sql.SQLException;

public class DatabaseUtils {

	private static boolean mIsConnected = false;

	public static boolean isConnected() {
		boolean isConnected = true;
		try {
			DatabaseConnection.connect();
		} catch (SQLException e) {
			isConnected = false;
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			isConnected = false;
			e.printStackTrace();
		}

		if (mLoggedInUser == null)
			mIsConnected = false;
		else {

			if (isConnected)
				mIsConnected = true;
			else
				mIsConnected = false;
		}

		return mIsConnected;
	}

	public static void setIsConnected(boolean isConnected) {
		mIsConnected = isConnected;
	}

	private static String mLoggedInUser;

	public static String getLoggedInUser() {
		return mLoggedInUser;
	}

	public static void setLoggedInUser(String loggedInUser) {
		DatabaseUtils.mLoggedInUser = loggedInUser;
	}

}
