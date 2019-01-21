package frc.robot.util;

import java.io.File;

import java.io.IOException;
import java.util.Arrays;

import java.util.logging.*;

import edu.wpi.first.wpilibj.Notifier;
import frc.robot.subsystems.Superstructure;

public class RobotLogger implements Runnable {
    class LogFormatter extends Formatter {
        @Override
        public String format(LogRecord record) {
            String message = "";

            if (record.getLevel() != Level.CONFIG) {
                message += record.getMillis();
                message += ',';
            }

            message += record.getMessage();

            return message;
        }
    }

    private static RobotLogger instance;

    public RobotLogger getInstance() {
        if (instance == null)
            instance = new RobotLogger();

        return instance;
    }

    private static Notifier notifier;
    private static Logger logger;

    private static Superstructure superstructure;

    private RobotLogger() {
        notifier = new Notifier(this);
        notifier.startPeriodic(0.2);

        superstructure = Superstructure.getInstance();

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
            logger.setLevel(Level.ALL);

            logger.config("timestamp,voltage,alliance,matchNumber");
        } else {
            throw new IOException("Unable to locate USB drive.");
        }
    }

    @Override
    public void run() {
        logger.info(superstructure.getRobotVoltage()
                    + "," + superstructure.getAlliance()
                    + "," + superstructure.getMatchNumber());
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