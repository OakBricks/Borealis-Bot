package catalyststudios;

import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ConfigFileHelper {
    private static Logger LOGGER = LoggerFactory.getLogger(ConfigFileHelper.class);
    public static File configFile = new File("settings.json");
    public static ConfigFile config;

    static {
        try {
            config = new Gson().fromJson(FileUtils.readFileToString(configFile, StandardCharsets.UTF_8), ConfigFile.class);
        } catch (IOException e) {
            LOGGER.error("No configuration file found at {}", ConfigFileHelper.class.getProtectionDomain().getCodeSource().getLocation().toString());
            e.printStackTrace();
        }
    }

    public ConfigFileHelper() {}

    public void flushConfig() {

    }
}
