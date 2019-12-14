package tech.tacetnox.magister.features;

import com.github.philippheuer.events4j.EventManager;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import com.github.twitch4j.common.enums.CommandPermission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.ls.LSOutput;
import tech.tacetnox.magister.Configuration;
import tech.tacetnox.magister.Util.MySQLAccess;

import java.lang.management.MemoryType;
import java.sql.Array;
import java.sql.Connection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


public class HandleCommandsFromChat {

    private static Logger o = LoggerFactory.getLogger(HandleCommandsFromChat.class);

    private Configuration configReader;
    MySQLAccess db = new MySQLAccess();
    Connection dbconn;
    //defaulting isMod to false, every event shall update this to either true or false depending on the user.
    private Boolean isMod = false;


    void out (String x){
        System.out.println(x);
    }

    /**
     * Register events of this class with the EventManager
     *
     * @param eventManager EventManager
     */
    public HandleCommandsFromChat(EventManager eventManager, Configuration config) throws Exception{
        configReader = config;
        if(dbconn == null)dbconn = db.connect(configReader.getCredentials().get("sql"));

        eventManager.onEvent(ChannelMessageEvent.class).subscribe(event -> {
            try {
                onChannelMessage(event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    public void onChannelMessage(ChannelMessageEvent event) throws Exception {

        /**
         * Two ways of looping a set, first is using the forEach method, second is using an iterator.
         */

        Object[] perms = event.getPermissions().toArray();
        for(Object x : perms){
            if(x.toString().equalsIgnoreCase("moderator")){
                isMod=true;
                o.info("Mod recognized, setting isMod to true.");
                break;
            } else {
                isMod=false;
            }
        }

        if(event.getMessage().startsWith("!")) handleCommand(event);


    }

    public void handleCommand(ChannelMessageEvent event) throws Exception {

        String[] commandAndArgs = event.getMessage().substring(1).split(" ");

        switch (commandAndArgs[0]){
            case "SQL":
                out(db.getAPI0Auth(dbconn));
                break;
            case "myname":
                event.getTwitchChat().sendMessage(event.getChannel().getName(), db.getKnightName(dbconn, event.getUser().getName()));
                break;
            case "name":
                event.getTwitchChat().sendMessage(event.getChannel().getName(), db.getKnightName(dbconn, commandAndArgs[1]));
                break;
            case "":
                break;
            case "dbreset":
                if(isMod){
                    o.info("Closing connection\n");
                    respond(event, "Restarting DB connection.");
                    db.close();
                    db.connect(configReader.getCredentials().get("sql"));
                    respond(event,"Reconnected!");
                }
                break;
            default :
                out("OOps");
                break;
        }

    }


    public void respond(ChannelMessageEvent event, String response){
        event.getTwitchChat().sendMessage(event.getChannel().getName(), response);
    }

}
