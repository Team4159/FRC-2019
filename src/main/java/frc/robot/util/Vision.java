package frc.robot.util;

import com.google.protobuf.InvalidProtocolBufferException;
import org.zeromq.ZMQ;

public class Vision {

    private static Vision instance;
    public static Vision getInstance() {

        if (instance == null) {
            instance = new Vision();
        }
        return instance;

    }

    private ZMQ.Context context;
    private ZMQ.Socket requester;

    private Vision() {

        context = ZMQ.context(1);

        //  Socket to talk to server
        System.out.println("Connecting to ZMQ server…");

        requester = context.socket(ZMQ.PAIR);
        requester.connect("tcp://localhost:5555"); // TODO: Change

    }

    private void sendData(byte[] data) {

        requester.send(data, 0);

    }

    private void sendMessage(boolean swtichCamera) {

        Messaging.RioMessage message = Messaging.RioMessage.newBuilder()
                .setSwitchCamera(true)
                .build();

        sendData(message.toByteArray());

    }

    public void switchCamera() {

        sendMessage(true);

    }

    private byte[] getData() {

        return requester.recv(0);

    }

    public Messaging.JetsonMessage getMessage() throws RuntimeException {

        Messaging.JetsonMessage message;

        byte[] rawMessage = getData();

        try {
            message = Messaging.JetsonMessage.parseFrom(rawMessage);
        } catch (InvalidProtocolBufferException pbe) {
            throw new RuntimeException(pbe);
        }

        return message;

    }

    public void closeConnection() {

        requester.close();
        context.term();

    }

}
