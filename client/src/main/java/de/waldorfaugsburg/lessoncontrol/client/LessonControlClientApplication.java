package de.waldorfaugsburg.lessoncontrol.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import de.waldorfaugsburg.lessoncontrol.client.config.ClientConfiguration;
import de.waldorfaugsburg.lessoncontrol.client.network.NetworkClient;
import de.waldorfaugsburg.lessoncontrol.client.tray.TrayManager;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;

@Slf4j
public final class LessonControlClientApplication {

    private final Gson gson = new GsonBuilder().create();

    private String machineName;
    private ClientConfiguration configuration;

    private NetworkClient networkClient;
    private TrayManager trayManager;

    public void enable() {
        try {
            machineName = InetAddress.getLocalHost().getHostName();
        } catch (final IOException e) {
            log.error("An error occurred while retrieving machine name", e);
        }

        try {
            configuration = parseConfiguration();
        } catch (final IOException e) {
            log.error("An error occurred while parsing configuration", e);
            System.exit(1);
        }

        // Initialize services
        networkClient = new NetworkClient(this);
        trayManager = new TrayManager(this);

        networkClient.connect();
    }

    public void disable() {

    }

    public Gson getGson() {
        return gson;
    }

    public String getMachineName() {
        return machineName;
    }

    public ClientConfiguration getConfiguration() {
        return configuration;
    }

    public NetworkClient getNetworkClient() {
        return networkClient;
    }

    private ClientConfiguration parseConfiguration() throws IOException {
        try (final JsonReader reader = new JsonReader(new BufferedReader(new FileReader("config.json")))) {
            return gson.fromJson(reader, ClientConfiguration.class);
        }
    }
}
