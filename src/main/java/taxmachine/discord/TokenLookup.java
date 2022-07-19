package taxmachine.discord;

import taxmachine.discord.Handlers.TokenHandler;

import java.io.IOException;
import java.util.HashMap;

public class TokenLookup {
    public static void main(String[] args) throws IOException {
        String token = args[0];
        if (token == null) {
            System.out.println("Please enter a token to lookup");
            System.exit(1);
        }
        HashMap<String, HashMap<String, String>> all = new HashMap<>();
        HashMap<String, String> base = TokenHandler.getInfo(token);
        HashMap<String, String> friends = TokenHandler.getFriends(token);
        HashMap<String, String> guilds = TokenHandler.getGuilds(token);
        HashMap<String, String> connections = TokenHandler.getConnections(token);
        HashMap<String, String> applications = TokenHandler.getApplications(token);
        all.put("BASE", base);
        all.put("FRIENDS", friends);
        all.put("GUILDS", guilds);
        all.put("CONNECTIONS", connections);
        all.put("APPLICATIONS", applications);
        System.out.println(all);
    }
}
