package tech.tacetnox.magister.features;

import com.github.philippheuer.events4j.EventManager;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import com.github.twitch4j.common.enums.CommandPermission;
import org.w3c.dom.ls.LSOutput;
import tech.tacetnox.magister.Util.MySQLAccess;

import java.lang.management.MemoryType;
import java.sql.Connection;
import java.util.Iterator;
import java.util.Set;


public class HandleCommandsFromChat {

    Connection SQL = null;


    void out (String x){
        System.out.println(x);
    }

    /**
     * Register events of this class with the EventManager
     *
     * @param eventManager EventManager
     */
    public HandleCommandsFromChat(EventManager eventManager) {
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
        event.getPermissions().forEach(System.out::println);
        //Iterator itr = permissions.iterator();
        if(SQL == null)SQL = new MySQLAccess().connect();

        if(event.getMessage().startsWith("!")) handleCommand(event);


    }

    public void handleCommand(ChannelMessageEvent event) throws Exception {

        String justCommand = event.getMessage().substring(1);
        switch (justCommand){
            case "SQL":
                out(new MySQLAccess().getAPI0Auth(SQL));
                break;
            default :
                break;
        }

    }


    public void respond(ChannelMessageEvent event, String response){
        event.getTwitchChat().sendMessage(event.getChannel().getName(), response);
    }

}
