/*    */ package edu.cmu.scs.fluorite.viewpart;
/*    */ 
///*    */ import com.gtalk.GTalkPlugin;

import edu.cmu.scs.fluorite.plugin.Activator;
import edu.cmu.scs.fluorite.viewpart.Constants;
import edu.cmu.scs.fluorite.viewpart.Util;

/*    */ import org.eclipse.jface.viewers.LabelProvider;
/*    */ import org.eclipse.swt.graphics.Image;
/*    */ import org.jivesoftware.smack.RosterEntry;
/*    */ 
/*    */ class ChatViewLabelProvider extends LabelProvider
/*    */ {
/* 12 */   private static Activator plugin = Activator.getDefault();
/*    */ 
/*    */   public String getText(Object obj) {
/* 15 */     return obj.toString();
/*    */   }
/*    */   public Image getImage(Object obj) {
/* 18 */     String name = obj.toString();
/* 19 */     int len = name.indexOf(Constants.CHAR_OPEN_ROUND_BRACKET);
/*    */ 
/* 21 */     if (len != -1) {
/* 22 */       name = name.substring(0, len);
/*    */     }
/* 24 */     RosterEntry entry = Util.getEntryByName(name);
/*    */ 
/* 26 */     int status = entry != null ? Util.getStatus(entry.getUser()) : -1;
/*    */ 
/* 28 */     switch (status) {
/*    */     case 0:
/* 30 */       return plugin.getImage(Constants.ICON_AVAILABLE);
/*    */     case 1:
/* 32 */       return plugin.getImage(Constants.ICON_BUSY);
/*    */     case 2:
/* 34 */       return plugin.getImage(Constants.ICON_AWAY);
/*    */     case -1:
/* 36 */       return plugin.getImage(Constants.ICON_OFFLINE);
/*    */     }
/* 38 */     return plugin.getImage(Constants.ICON_OFFLINE);
/*    */   }
/*    */ }
