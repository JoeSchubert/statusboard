package statusboard;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class Settings {

    private static Settings settings = null;
    private static Properties props;
    private static final String CONFIG_FILE = "config.properties";
    OutputStream output = null;
    InputStream input = null;

    public Settings() {
        loadProps();
    }

    public static Settings getInstance() {
        if (settings == null) {
            settings = new Settings();
            return settings;
        } else {
            return settings;
        }
    }

    private void loadProps() {
        try {
            props = new Properties();
            input = new FileInputStream(CONFIG_FILE);
            props.load(input);
        } catch (IOException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    props = new Properties();
                    System.err.println(e.getClass().getName() + ": " + e.getMessage());
                }
            }
        }
    }

    private void saveProps() {
        try {
            output = new FileOutputStream(CONFIG_FILE);
            props.store(output, null);
        } catch (IOException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    System.err.println(e.getClass().getName() + ": " + e.getMessage());
                }
            }
        }
    }

    private void storeBoolean(String field, boolean value) {
        String val = "false";
        if (value) {
            val = "true";
        }
        props.setProperty(field, val);
        saveProps();
    }

    private boolean getBoolean(String field, boolean defaultValue) {
        String val = props.getProperty(field);
        boolean returnVal = false;
        if (null == val || val.isEmpty()) {
            return defaultValue;
        }
        if (val.equals("true")) {
            returnVal = true;
        }
        return returnVal;
    }

    private String getString(String field, String defaultValue) {
        String val = props.getProperty(field);
        if (val == null || val.isEmpty()) {
            return defaultValue;
        } else {
            return val;
        }
    }

    public void setNightMode(boolean status) {
        storeBoolean("NightMode", status);
    }

    public boolean getNightMode() {
        return getBoolean("NightMode", false);
    }

    public void setCutterName(String name) {
        props.setProperty("CutterName", name);
        saveProps();
    }

    public String getCutterName() {
        return getString("CutterName", "Coast Guard Cutter");
    }

}
