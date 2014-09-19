/*    */package edu.cmu.scs.fluorite.viewpart;

/*    */
///*    */ /*    */ /*    */ import com.gtalk.views.GTalkChatView;

import edu.cmu.scs.fluorite.util.StringUtil;
import edu.cmu.scs.fluorite.viewpart.Constants;
import edu.cmu.scs.fluorite.viewpart.Util;

/*    */
import java.text.DateFormat;
/*    */
import java.util.Date;
/*    */
import org.eclipse.swt.custom.CTabFolder;
/*    */
import org.eclipse.swt.custom.CTabItem;
/*    */
import org.eclipse.swt.custom.StyledText;
/*    */
import org.eclipse.swt.events.KeyEvent;
/*    */
import org.eclipse.swt.events.KeyListener;
/*    */
import org.eclipse.swt.widgets.Display;
/*    */
import org.eclipse.swt.widgets.Text;
/*    */

import buddylist.database.DatabaseConnection;
import buddylist.database.DatabaseUtils;


/*    */public class SendListener
/*    */implements KeyListener
/*    */{
	/*    */public void keyPressed(KeyEvent e)
	/*    */{
		/* 21 */if ((e.keyCode == Constants.KEYPAD_ENTER)
				|| (e.keyCode == Constants.NUMPAD_ENTER)) {
			/* 22 */CTabFolder tabFolder = ChatView.getTabFolder();
			/* 23 */StyledText chatText = (StyledText) ChatView
					.getChatTextArea(tabFolder.getSelection().getText(), 1);
			/* 24 */Text sendText = (Text) ChatView.getChatTextArea(tabFolder
					.getSelection().getText(), 2);
			
			String userName = StringUtil.removeUserName(tabFolder
					.getSelection().getText());
			
			/* 26 */if (sendText.getText().trim().length() == 0) {
				/* 27 */return;
				/*    */}
			/* 29 */String time = DateFormat.getTimeInstance(3).format(
					new Date());
			
			String dateTime = DateFormat.getDateTimeInstance(DateFormat.SHORT, 
		              DateFormat.LONG).format(new Date());
			/*    */
			/* 31 */int startLineCount = chatText.getLineCount() - 1;
			/*    */
			/* 33 */int start = chatText.getText().length();
			/* 34 */chatText
					.append("me" + Constants.COLON + sendText.getText());
			/*    */
			/* 36 */int timeStart = chatText.getText().length();
			/* 37 */chatText.append(Constants.CHAR_OPEN_BRACKET + time
					+ Constants.CHAR_CLOSE_BRACKET);
			/*    */
			/* 39 */int timeEnd = time.length() + 2;
			/* 40 */chatText.append(Constants.NEW_LINE);
			/*    */
			/* 42 */int endLineCount = chatText.getLineCount() - 1;
			/*    */
			/* 44 */chatText.setLineBackground(startLineCount, endLineCount
					- startLineCount, chatText.getDisplay().getSystemColor(1));
			/* 45 */chatText.setStyleRange(Util.getStyleRangeForTime(
					chatText.getDisplay(), timeStart, timeEnd));
			/* 46 */chatText.setStyleRange(Util.getStyleRange(start, 3));
			/*    */
			/* 49 */int top = chatText.getTopIndex();
			/* 50 */int bottom = chatText.getBottomMargin();
			/*    */
			/* 52 */if (start < top + 1)
				/* 53 */chatText.setTopIndex(start - 1 > 0 ? start - 1 : 0);
			/* 54 */else if (chatText.getText().length() > bottom - 1) {
				/* 55 */chatText.setTopIndex(top + start - bottom + 1);
			}

			// here is where i need to send messages
			// Util.sendMessage(sendText.getText(), re.getUser());
			if(DatabaseUtils.isConnected())
				DatabaseConnection.insertMessage(DatabaseUtils.getLoggedInUser(), userName, sendText.getText(), dateTime);
			sendText.setText("");
		}
	}

	/*    */
	/*    */public void keyReleased(KeyEvent e)
	/*    */{
		/*    */}
	/*    */
}