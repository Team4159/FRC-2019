package frc.robot.util;

import com.google.protobuf.InvalidProtocolBufferException;
import org.zeromq.ZMQ;

public class Vision {

    private Vision instance;
    public Vision getInstance() {

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

    public void sendMessage(Messaging.RioMessage.Camera camera) {

        Messaging.RioMessage message = Messaging.RioMessage.newBuilder()
                .setCamera(camera)
                .build();

        sendData(message.toByteArray());

    }

    private byte[] getData() {

        return requester.recv(0);

    }

    public Messaging.JetsonMessage getMessage() {

        Messaging.JetsonMessage message = null;

        byte[] rawMessage = getData();

        try {
            message = Messaging.JetsonMessage.parseFrom(rawMessage);
        } catch (InvalidProtocolBufferException pbe) {
            pbe.printStackTrace();
        }

        return message;

    }

    public void closeConnection() {

        requester.close();
        context.term();

    }

}
