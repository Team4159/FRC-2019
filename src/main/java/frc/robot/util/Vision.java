package frc.robot.util;

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

    public void sendData(byte[] data) {

        requester.send(data, 0);

    }

    public byte[] getData() {

        return requester.recv(0);

    }

    public void closeConnection() {

        requester.close();
        context.term();

    }

}
