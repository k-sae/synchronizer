package Connections.Client;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

import static Connections.Connection.VERIFICATION;

/**
 * Created by kemo on 02/02/2017.
 */
class BasicConnection {
    private ArrayList<ConnectionListener> connectionListeners;
    int port;
    String serverName;
    private String verification;
    Socket connectionSocket;
    protected ArrayList<Integer> customPorts;
    BasicConnection() {
        connectionListeners = new ArrayList<>();
        verification = VERIFICATION;
        customPorts = new ArrayList<>();
    }
    void triggerStartingConnection()
    {
        //noinspection ForLoopReplaceableByForEach
        for (int i = 0; i < connectionListeners.size(); i++) {
            connectionListeners.get(i).onStart();
        }
    }
    void triggerConnectionStarted()
    {
        //noinspection ForLoopReplaceableByForEach
        for(int i = 0; i < connectionListeners.size(); i++) {
            connectionListeners.get(i).onConnectionSuccess();
        }
    }
    public void setConnectionListener(ConnectionListener connectionListener)
    {
        connectionListeners.add(connectionListener);
    }
    public String getVerification() {
        return verification;
    }

    public void setVerification(String verification) {
        this.verification = verification;
    }
    void verifyConnection() throws IOException {
        try {
            connectionSocket.setSoTimeout(200); //set time out for reading input
            DataInputStream dataInputStream = new DataInputStream(connectionSocket.getInputStream());
            byte[] b = new byte[verification.length()];
            dataInputStream.readFully(b);//reading in bytes format because i cant make sure of the data coming from other sockets
            if (!(new String(b).equals(verification))) throw new IOException("wrong verification code"); //throw exception if code is wrong
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}
