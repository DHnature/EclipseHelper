package buddylist.chat;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.TreeViewer;
import org.jivesoftware.smack.XMPPException;

import buddylist.views.SWTChatViewController;

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
public class AccountsManager implements Viewable {

	private static AccountsManager manager;

	private AccountsManager() {
		accounts = new ArrayList<Account>();
	}

	public static AccountsManager getInstance() {
		if (manager == null) {
			manager = new AccountsManager();
		}
		// try {

		// } catch (XMPPException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		return manager;

	}

	private TreeViewer viewer;

	public void setViewer(TreeViewer viewer) {
		this.viewer = viewer;
	}

	public void refresh() {
		viewer.refresh();
	}

	private List<Account> accounts;

	public Account addAccount(AccountType type, String username, String password)
			   {
		Account account = null;
		try {
			account = new Account(type, username, password,
					SWTChatViewController.getInstance());
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		accounts.add(account);
		return account;
		//return null;
	}

	@Override
	public Viewable[] getChildren() {
		if (accounts == null)
			return new Account[0];
		return accounts.toArray(new Account[accounts.size()]);
	}

	@Override
	public Viewable getParent() {
		return null;
	}

	public void deleteAccount(Account a) {
		accounts.remove(a);

	}
}
