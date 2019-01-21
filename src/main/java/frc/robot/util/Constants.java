package frc.robot.util;

import org.jetbrains.annotations.NotNull;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Constants {

    private static Properties prop = new Properties();
    private static FileInputStream input;

    public static int getInt(@NotNull String key) {

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

    public static double getDouble(@NotNull String key) {

        try {
            input = new FileInputStream("/home/lvuser/deploy/config.properties");
            prop.load(input);
            return Double.parseDouble(prop.getProperty(key));

        } catch (IOException ex) {
            ex.printStackTrace();
            return 0.0;

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

    public static String getString(@NotNull String key) {

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