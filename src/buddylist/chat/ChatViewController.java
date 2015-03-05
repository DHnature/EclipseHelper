package buddylist.chat;

/**
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * @author Rajgopal Vaithiyanathan
 * 
 */
public abstract class ChatViewController {

	protected ChatsView chatView;
	protected AccountsView accountsview;

	public void setChatView(ChatsView chatView) {
		this.chatView = chatView;
	}

	public void setAccountsview(AccountsView accountsview) {
		this.accountsview = accountsview;
	}

	public AccountsView getAccountsview() {
		return accountsview;
	}

	public ChatsView getChatView() {
		return chatView;
	}

	public abstract void recieveChat(Buddy_1 c, String s);

	public abstract void sendChat(Buddy_1 c, String s);

	public abstract void changePresence(Account a, Buddy_1 b,
			PresenceType presence, String status);

	public abstract void addBuddy(Account a, Buddy_1 b);

	public abstract void removeBuddy(Account a, Buddy_1 b);

	public abstract void updateBuddy(Account a, Buddy_1 b);

	public abstract void listBuddies(Account a);

	public abstract void displayBuddy(Account a, Buddy_1 b);

	public abstract void disableChat(Account a, String error);

	public abstract void enableChat(Account a, String notification);

}
