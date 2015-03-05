package buddylist.views;

import java.util.ArrayList;

import org.eclipse.core.runtime.IAdaptable;

import buddylist.chat.Account;

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
public class AccountType implements IAdaptable {

	ArrayList<Account> accounts = new ArrayList<Account>();

	public void addAccount(Account a) {
		accounts.add(a);
	}

	public Account[] getChildren() {
		return (Account[]) accounts.toArray(new Account[accounts.size()]);
	}

	@Override
	public Object getAdapter(Class arg0) {
		// TODO Auto-generated method stub
		return null;
	}
}
