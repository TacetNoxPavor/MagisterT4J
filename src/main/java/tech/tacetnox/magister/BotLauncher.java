package tech.tacetnox.magister;

public class BotLauncher {
    public static void main(String[] args) {
        Bot bot = new Bot();
        bot.registerFeatures();
        bot.start();
    }
}
