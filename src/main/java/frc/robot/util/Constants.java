package frc.robot.util;

import org.jetbrains.annotations.NotNull;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Constants {

    private static Constants instance;

    public static Constants getInstance() {
        if (instance == null)
            instance = new Constants();
        return instance;
    }

    private Properties prop;
    private InputStream input;

    private Constants() {
        prop = new Properties();
        input = null;
    }

    public int getInt(@NotNull String key) {

        try {
            input = new FileInputStream("/home/lvuser/deploy/config.properties");
            prop.load(input);
            return Integer.parseInt(prop.getProperty(key));

        } catch (IOException ex) {
            ex.printStackTrace();
            return 0;

        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public double getDouble(@NotNull String key) {

        try {
            input = new FileInputStream("/home/lvuser/deploy/config.properties");
            prop.load(input);
            return Double.parseDouble(prop.getProperty(key));

        } catch (IOException ex) {
            ex.printStackTrace();
            return 0;

        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getString(@NotNull String key) {

        try {
            input = new FileInputStream("/home/lvuser/deploy/config.properties");
            prop.load(input);
            return prop.getProperty(key);

        } catch (IOException ex) {
            ex.printStackTrace();
            return "";

        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}