package frc.robot.util;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.Handler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.stream.Collectors;
import java.util.logging.FileHandler;
import edu.wpi.first.wpilibj.Notifier;
import frc.robot.subsystems.Superstructure;

public class RobotLogger implements Runnable {

    private class LogFormatter extends Formatter {

        @Override
        public String getHead(Handler h) {
            return String.join(",", fields.keySet());
        }

        @Override
        public String format(LogRecord record) {
            return record.getMessage();
        }

    }

    private static RobotLogger instance;
    public static RobotLogger getInstance() {
        if (instance == null)
            instance = new RobotLogger();
        return instance;
    }

    private Notifier notifier;
    private Logger logger;

    private Superstructure superstructure;

    private HashMap<String, Supplier<Object>> fields;

    private RobotLogger() {

        notifier = new Notifier(this);
        notifier.startPeriodic(0.2);

        superstructure = Superstructure.getInstance();

        fields = new HashMap<String, Supplier<Object>>() {
            {
                put("MODE", superstructure::getMode);
                put("ALLIANCE", superstructure::getAlliance);
                put("MATCH NUMBER", superstructure::getMatchNumber);
                put("VOLTAGE", superstructure::getRobotVoltage);
            }
        };

        try {
            initLogger();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Problems with creating the log files, check if the USB is plugged in.");

        }

    }

    private void initLogger() throws IOException {

        String drive = locateDrive();

        if (drive != null) {
            logger = Logger.getLogger("team4159");

            String fileName = System.currentTimeMillis() + ".csv";
            FileHandler logFileHandler = new FileHandler("/media/" + drive + "/" + fileName);
            logFileHandler.setFormatter(new LogFormatter());

            logger.addHandler(logFileHandler);
            logger.setLevel(Level.INFO);

        } else {
            throw new IOException("Unable to locate USB drive.");

        }

    }

    @Override
    public void run() {

        logger.info(fields.values()
                .stream()
                .map(handler -> handler.get().toString())
                .collect(Collectors.joining(",")));

    }

    private String locateDrive() {

        String[] directories = new File("/media").list(
                (File dir, String name) -> new File(dir, name).isDirectory()
        );

        if (directories != null) {
            for (String directory : directories) {
                String[] files = new File("/media/" + directory).list(
                        (File dir, String name) -> new File(dir, name).isDirectory()
                );

                if (files != null) {
                    // we set a flag in our USB drive to differentiate it from other devices
                    if (Arrays.asList(files).contains(".flag")) {
                        return directory;
                    }
                }
            }
        }

        return null;

    }
}
