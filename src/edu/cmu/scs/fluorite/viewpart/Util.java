/*     */ package edu.cmu.scs.fluorite.viewpart;
/*     */ 
///*     */ import com.gtalk.GTalkPlugin;
///*     */ import com.gtalk.views.GTalkChatView;


/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;

/*     */ import org.eclipse.jface.preference.IPreferenceStore;
/*     */ import org.eclipse.swt.custom.StyleRange;
/*     */ import org.eclipse.swt.graphics.Font;
/*     */ import org.eclipse.swt.widgets.Display;
/*     */ import org.eclipse.ui.IWorkbench;
/*     */ import org.eclipse.ui.PlatformUI;
/*     */ import org.jivesoftware.smack.Chat;
/*     */ import org.jivesoftware.smack.ChatManager;
/*     */ import org.jivesoftware.smack.ConnectionConfiguration;
/*     */ import org.jivesoftware.smack.MessageListener;
/*     */ import org.jivesoftware.smack.Roster;
/*     */ import org.jivesoftware.smack.RosterEntry;
/*     */ import org.jivesoftware.smack.XMPPConnection;
/*     */ import org.jivesoftware.smack.XMPPException;
/*     */ import org.jivesoftware.smack.packet.Message;
/*     */ import org.jivesoftware.smack.packet.Message.Type;
/*     */ import org.jivesoftware.smack.packet.Presence;
/*     */ import org.jivesoftware.smack.packet.Presence.Mode;
///*     */ import org.jivesoftware.smack.packet.Presence.Type;
/*     */ import org.jivesoftware.smack.proxy.ProxyInfo;
/*     */ import org.jivesoftware.smack.proxy.ProxyInfo.ProxyType;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
/*     */ import org.jivesoftware.smack.util.StringUtils;
/*     */ 
/*     */ public class Util
/*     */ {
/*     */   private static XMPPConnection connection;
/*  40 */   private static Map<String, RosterEntry> buddiesMap = new HashMap();
/*     */ 
/*     */   public static XMPPConnection getConnection()
/*     */   {
/*  48 */     return connection;
/*     */   }
/*     */ 
/*     */   public static void closeConnection()
/*     */   {
/*  58 */     if (connection != null) {
			try {
/*  59 */       connection.disconnect();
			} catch (Exception ex) {}
			}
/*     */   }
/*     */ 
/*     */   public static boolean isConnected()
/*     */   {
/*  68 */     if (connection == null)
/*  69 */       return false;
/*  70 */     return connection.isConnected();
/*     */   }
/*     */ 
/*     */   public static String connect()
/*     */   {
/*  79 */     ConnectionConfiguration config = new ConnectionConfiguration(Constants.GMAIL_HOST, Constants.GMAIL_PORT, Constants.GMAIL_DOT_COM);
/*     */ 
/*  81 */     IPreferenceStore store = null; //GTalkPlugin.getDefault().getPreferenceStore();
/*  82 */     if (store.getString(Constants.ENABLE_PROXY).equals("true")) {
/*  83 */       String host = store.getString(Constants.PROXY_HOST);
/*  84 */       String port = store.getString(Constants.PROXY_PORT);
/*  85 */       String user = null;
/*  86 */       String pwd = null;
/*     */ 
/*  88 */       if (store.getString(Constants.REQUIRE_AUTH).equals("true")) {
/*  89 */         user = store.getString(Constants.AUTH_USER);
/*  90 */         pwd = store.getString(Constants.AUTH_PWD);
/*     */       }
/*     */ 
/*  93 */       ProxyInfo proxyInfo = new ProxyInfo(ProxyInfo.ProxyType.HTTP, host, Integer.parseInt(port), user, pwd);
/*  94 */       config.setSocketFactory(proxyInfo.getSocketFactory());
/*     */     }
/*     */ 
/*  97 */     connection = new XMPPTCPConnection(config);
/*     */     try
/*     */     {
/* 100 */       connection.connect();
/*     */     } catch (Exception e) {
/* 102 */       logException("connect(): " + e.getMessage());
/* 103 */       return Constants.CONNECTION_FAILED;
/*     */     }
/*     */ 
/* 106 */     return Constants.CONNECTION_SUCCESSFULL;
/*     */   }
/*     */ 
/*     */   public static String getCurrentUser()
/*     */   {
/* 115 */     if (!isConnected()) {
/* 116 */       return "";
/*     */     }
/* 118 */     return StringUtils.parseName(connection.getUser());
/*     */   }
/*     */ 
/*     */   public static String login(String userName, String password)
/*     */   {
/*     */     try
/*     */     {
/* 130 */       connection.login(userName, password, "ecl-plugin");
/* 131 */       setStatus(0, "");
/* 132 */       initializeBuddies();
/* 133 */      // GTalkChatView.addToHistory(userName, "logged in");
/*     */     } catch (Exception e) {
/* 135 */       logException("login(): " + e.getMessage());
/* 136 */       return Constants.LOGIN_FAILED;
/*     */     }
/* 138 */     return "";
/*     */   }
/*     */ 
/*     */   public static void setStatus(int type, String status)
/*     */   {
/* 148 */     Presence presence = null;
/* 149 */     switch (type) {
/*     */     case 0:
/* 151 */       presence = new Presence(Presence.Type.available, status, 1, Presence.Mode.available);
/* 152 */       break;
/*     */     case 1:
/* 154 */       presence = new Presence(Presence.Type.available, status, 1, Presence.Mode.dnd);
/* 155 */       break;
/*     */     case 2:
/* 157 */       presence = new Presence(Presence.Type.available, status, 1, Presence.Mode.away);
/* 158 */       break;
/*     */     case 3:
/* 160 */       presence = new Presence(Presence.Type.unavailable, status, 1, Presence.Mode.away);
/*     */     }
/*     */ 
/* 163 */     if ((connection != null) && (isConnected()))
	try {
/* 164 */       connection.sendPacket(presence);
	} catch (Exception ex) {}
/*     */   }
/*     */ 
/*     */   public static Collection<RosterEntry> getBuddyList()
/*     */   {
/* 172 */     Roster roster = connection.getRoster();
/* 173 */     Collection entries = roster.getEntries();
/*     */ 
/* 181 */     return entries;
/*     */   }
/*     */ 
/*     */   public static Roster getRoster()
/*     */   {
/* 188 */     return connection.getRoster();
/*     */   }
/*     */ 
/*     */   public static void disconnect()
/*     */   {
/* 195 */     Presence offlinePres = new Presence(Presence.Type.unavailable, "", 1, Presence.Mode.away);
/*     */ 
/* 197 */     if (isConnected()) {
	try {
/* 198 */       connection.disconnect(offlinePres);
	} catch (Exception ex) {}
}
/*     */   }
/*     */ 
/*     */   public static ChatManager getChatManager()
/*     */   {
/* 205 */     XMPPConnection connection = getConnection();
			return ChatManager.getInstanceFor(connection);
/*     */   }
/*     */ 
/*     */   public static int getStatus(String user)
/*     */   {
/* 216 */     if ((connection != null) && (!connection.isConnected()))
/* 217 */       return -1;
/* 218 */     if (connection != null) {
/* 219 */       Roster roster = connection.getRoster();
try {
/* 220 */       roster.reload();
} catch (Exception ex) {}
/*     */ 
/* 222 */       Presence presence = roster.getPresence(user);
/*     */ 
/* 224 */       if (presence != null) {
/* 225 */         if ((presence.getType().equals(Presence.Type.available)) && (presence.getMode() == null))
/* 226 */           return 0;
/* 227 */         if ((presence.getType().equals(Presence.Type.available)) && (presence.getMode() != null) && (presence.getMode().equals(Presence.Mode.dnd)))
/* 228 */           return 1;
/* 229 */         if ((presence.getType().equals(Presence.Type.available)) && (presence.getMode() != null) && (presence.getMode().equals(Presence.Mode.away)))
/* 230 */           return 2;
/*     */       }
/*     */     }
/* 233 */     return -1;
/*     */   }
/*     */ 
/*     */   public static Collection<RosterEntry> getBuddies(boolean showOffine)
/*     */   {
/* 245 */     if (!isConnected())
/* 246 */       return null;
/* 247 */     List list = new ArrayList();
/*     */ 
/* 249 */     Collection entries = getBuddyList();
/* 250 */     getRoster();
///*     */ 
///* 252 */     if (showOffine) {
///* 253 */       for (RosterEntry r : entries)
///* 254 */         list.add(r);
///*     */     }
///*     */     else {
///* 257 */       for (RosterEntry r : entries) {
///* 258 */         if (getStatus(r.getUser()) != -1)
///* 259 */           list.add(r);
///*     */       }
///*     */     }
/* 262 */     return list;
/*     */   }
/*     */ 
/*     */   public static void sendMessage(String message, String to)
/*     */   {
/* 272 */     Chat chat = getChatManager().createChat(to, new MessageListener() {
/*     */       public void processMessage(Chat chat, Message message) {
/* 274 */         if (message.getType() == Message.Type.chat)
/* 275 */           PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable()
/*     */           {
/*     */             public void run() {
/*     */             }
/*     */           });
/*     */       }
/*     */     });
/*     */     try {
/* 283 */       chat.sendMessage(message);
/*     */     } catch (Exception e) {
/* 285 */       logException("sendMessage(): " + e.getMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   public static RosterEntry getEntryByName(String name)
/*     */   {
/* 296 */     return (RosterEntry)buddiesMap.get(name);
/*     */   }
/*     */ 
/*     */   public static String getNameByUser(String user)
/*     */   {
/* 306 */     Collection buddies = buddiesMap.values();
/*     */ 
/* 308 */     Iterator itr = buddies.iterator();
/* 309 */     String name = "";
/*     */ 
/* 311 */     while (itr.hasNext()) {
/* 312 */       RosterEntry re = (RosterEntry)itr.next();
/*     */ 
/* 314 */       if (re.getUser().equals(user)) {
/* 315 */         name = re.getName();
/*     */ 
/* 317 */         if (name == null) {
/* 318 */           name = StringUtils.parseName(re.getUser());
/*     */         }
/* 320 */         return name;
/*     */       }
/*     */     }
/* 323 */     return null;
/*     */   }
/*     */ 
/*     */   public static void initializeBuddies()
/*     */   {
/* 330 */     Collection buddies = getBuddyList();
/* 331 */     Iterator itr = buddies.iterator();
/*     */ 
/* 333 */     while (itr.hasNext()) {
/* 334 */       RosterEntry re = (RosterEntry)itr.next();
/* 335 */       String name = re.getName();
/*     */ 
/* 337 */       if (name == null) {
/* 338 */         name = StringUtils.parseName(re.getUser());
/*     */       }
/* 340 */       buddiesMap.put(name, re);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static StyleRange getStyleRange(int start, int end)
/*     */   {
/* 352 */     StyleRange textStyle = new StyleRange();
/* 353 */     textStyle.start = start;
/* 354 */     textStyle.length = end;
/* 355 */     textStyle.fontStyle = 1;
/*     */ 
/* 357 */     return textStyle;
/*     */   }
/*     */ 
/*     */   public static StyleRange getStyleRangeForTime(Display display, int start, int end) {
/* 361 */     Font font = new Font(display, Constants.FONT_ARIAL_7_ITALIC);
/* 362 */     StyleRange textStyle = new StyleRange();
/* 363 */     textStyle.start = start;
/* 364 */     textStyle.length = end;
/* 365 */     textStyle.font = font;
/*     */ 
/* 367 */     return textStyle;
/*     */   }
/*     */ 
/*     */   public static String getStatusFromInt(String user) {
/* 371 */     int status = getStatus(user);
/* 372 */     switch (status) { case 0:
/* 373 */       return Constants.STATUS_AVAILABLE;
/*     */     case 1:
/* 374 */       return Constants.STATUS_BUSY;
/*     */     case 2:
/* 375 */       return Constants.STATUS_AWAY;
/*     */     case 3:
/* 376 */       return Constants.STATUS_INVISIBLE;
/*     */     case -1:
/* 377 */       return "Offline";
/*     */     }
/* 379 */     return "";
/*     */   }
/*     */ 
/*     */   public static void logException(String message)
/*     */   {
/* 388 */     System.out.println("GTALK::EXCEPTION:: " + message);
/*     */   }
/*     */ 
/*     */   public static String getChatClient(String client)
/*     */   {
/* 398 */     String status = "";
/*     */ 
/* 401 */     if (client.length() == 0) {
/* 402 */       return status;
/*     */     }
/* 404 */     status = Constants.CHAR_OPEN_ROUND_BRACKET;
/*     */ 
/* 406 */     if (client.startsWith("Talk"))
/* 407 */       status = status + "logged in from google talk";
/* 408 */     else if (client.startsWith("gmail"))
/* 409 */       status = status + "logged in from gmail";
/* 410 */     else if (client.startsWith("android"))
/* 411 */       status = status + "logged in from android";
/* 412 */     else if (client.startsWith("eBuddy"))
/* 413 */       status = status + "logged in from eBuddy";
/* 414 */     else if (client.startsWith("orkut"))
/* 415 */       status = status + "logged in from orkut";
/* 416 */     else if (client.startsWith("Meebo"))
/* 417 */       status = status + "logged in from meebo";
/* 418 */     else if (client.startsWith("ecl-plugin"))
/* 419 */       status = status + "logged in from eclipse plugin";
/*     */     else {
/* 421 */       status = status + "logged in from other chat client";
/*     */     }
/* 423 */     status = status + Constants.CHAR_CLOSE_ROUND_BRACKET;
/*     */ 
/* 425 */     return status;
/*     */   }
/*     */ }

/* Location:           /Users/jasoncarter/Downloads/eclipse/plugins/com.gtalk_2.0.0.jar
 * Qualified Name:     com.gtalk.util.Util
 * JD-Core Version:    0.6.2
 */