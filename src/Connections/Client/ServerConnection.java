package Connections.Client;


import Connections.Connection;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

/**
 * Created by kemo on 28/10/2016.
 */
public abstract class ServerConnection implements Connection {
    private int port;
    private String serverName;
    private String verification;
    private Socket connectionSocket;
    private ArrayList<Integer> customPorts;
    private ArrayList<ConnectionListener> connectionListeners;
    public ServerConnection(int startPort)
    {
        connectionListeners = new ArrayList<>();
        customPorts = new ArrayList<>();
        customPorts.add(startPort);
        verification = VERIFICATION;
    }
    public void connect(String serverName) throws ServerNotFound
    {
        port = -1;
        this.serverName = serverName;
        triggerStartingConnection();
        while(port == -1) {
            findPort(0);
        }
        startConnection();
    }

   public ServerConnection(String serverName, int startPort) throws ServerNotFound {
        this(startPort);
        connect(serverName);
    }
    private void findPort(int i)
    {
        if (i == customPorts.size()) return ;
        try {
            connectionSocket = new Socket();
            connectionSocket.connect(new InetSocketAddress(serverName,customPorts.get(i)), 1500);
            //verify if the socket found is the desired socket
            verifyConnection();
            port = customPorts.get(i);
            triggerConnectionStarted();
            connectionSocket.setSoTimeout(5000);
        }
        catch (ConnectException e)
        {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            findPort(i + 1);
        }
        catch (IOException e) {
            findPort(i + 1);
        }
        catch (Exception e)
        {
            try {
                Thread.sleep(400);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
    }
    private void verifyConnection() throws IOException {
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
    private void triggerStartingConnection()
    {
        //noinspection ForLoopReplaceableByForEach
        for (int i = 0; i < connectionListeners.size(); i++) {
            connectionListeners.get(i).onStart();
        }
    }
    private void triggerConnectionStarted()
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

    public Socket getConnectionSocket() {
        return connectionSocket;
    }

    public ArrayList<Integer> getCustomPorts() {
        return customPorts;
    }
    public void setDuePort(int duePort) {
        if (duePort < 0) throw new NumberFormatException("Invalid negative number duePort = " + duePort);
        for (int i = customPorts.get(0); i < duePort; i++) {
            customPorts.add(i);
        }
    }

    public String getVerification() {
        return verification;
    }

    public void setVerification(String verification) {
        this.verification = verification;
    }
}
