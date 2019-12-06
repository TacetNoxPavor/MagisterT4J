package tech.tacetnox.magister.features;

import com.github.philippheuer.events4j.EventManager;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import com.github.twitch4j.common.enums.CommandPermission;
import tech.tacetnox.magister.Util.MySQLAccess;

import java.sql.Connection;
import java.util.Iterator;
import java.util.Set;


public class HandleCommandsFromChat {

    MySQLAccess SQL = new MySQLAccess();

    /**
     * Register events of this class with the EventManager
     *
     * @param eventManager EventManager
     */
    public HandleCommandsFromChat(EventManager eventManager) {
        eventManager.onEvent(ChannelMessageEvent.class).subscribe(event -> onChannelMessage(event));

    }

    public void onChannelMessage(ChannelMessageEvent event) {
        /**
         * Initial section for logging purposes.
         */
        System.out.printf(
                "Channel [%s] - User[%s] - Message [%s]%n",
                event.getChannel().getName(),
                event.getUser().getName(),
                event.getMessage()
        );
        final Set<CommandPermission> permissions = event.getPermissions();
        Iterator itr = permissions.iterator();
        while(itr.hasNext()){
            System.out.println(itr.next());
        }
        if(event.getMessage().startsWith("!")) handleCommand(event);


    }

    public void handleCommand(ChannelMessageEvent event){
        String justCommand = event.getMessage().substring(1);
        switch (justCommand){
            case "woo":

        }

    }


    public void respond(ChannelMessageEvent event, String response){
        event.getTwitchChat().sendMessage(event.getChannel().getName(), response);
    }

}
