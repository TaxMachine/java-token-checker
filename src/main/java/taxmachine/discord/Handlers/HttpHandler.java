package taxmachine.discord.Handlers;

import okhttp3.*;

import java.io.IOException;

public class HttpHandler {
    public static String HttpGet(String url, String token) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", token)
                .addHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2227.0 Safari/537.36")
                .build();
        try (Response res = client.newCall(request).execute()) {
            if (res.code() == 401) {
                System.out.println("Invalid Token");
                System.exit(1);
            }
            return res.body().string();
        }
    }
}
