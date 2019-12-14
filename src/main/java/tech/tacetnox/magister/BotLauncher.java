package tech.tacetnox.magister;

public class BotLauncher {
    public static void main(String[] args) throws Exception {
        Bot bot = new Bot();
        bot.registerFeatures();
        bot.start();
    }
}
