package fr.altrix.koth.utils;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.function.Consumer;

public class UpdateChecker {

    private JavaPlugin plugin;
    private int resourceId;

    public UpdateChecker(JavaPlugin plugin, int resourceId) {
        this.plugin = plugin;
        this.resourceId = resourceId;
    }

    public void getDesc(final Consumer<String> consumer) {
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            makeRequest("https://api.spiget.org/v2/resources/"+this.resourceId+"/updates/latest", consumer::accept);
        });
    }

    public void getVersion(final Consumer<String> consumer) {
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            makeRequest("https://api.spiget.org/v2/resources/"+this.resourceId+"/versions/latest", consumer::accept);
        });
    }

    private void makeRequest(String urlStr, final Consumer<String> consumer) {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            con.disconnect();
            consumer.accept(content.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
