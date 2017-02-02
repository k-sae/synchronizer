package Connections.Client;


import Connections.Connection;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by kemo on 28/10/2016.
 */
public abstract class ServerConnection extends BasicConnection implements Connection {

    public ServerConnection(int startPort)
    {
        super();
        customPorts.add(startPort);
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

    public void endConnection()
    {
        try {
            connectionSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
