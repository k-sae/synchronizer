package Connections.Client;

import java.net.Socket;
import java.util.ArrayList;

import static Connections.Connection.VERIFICATION;

/**
 * Created by kemo on 02/02/2017.
 */
class BasicConnection {
    private ArrayList<ConnectionListener> connectionListeners;
    int port;
    String serverName;
    String verification;
    Socket connectionSocket;
    BasicConnection() {
        connectionListeners = new ArrayList<>();
        verification = VERIFICATION;
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
}
