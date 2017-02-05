package Synchronizer.AppConnections.Server;

import Connections.IPAddress;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * Created by kemo on 04/02/2017.
 */
public abstract class ServersFinder {
    private int noOfThreads;
    private int noOfWorkingThreads;
    private IPAddress startingIp;
    private IPAddress endingIp;
    private ArrayList<ServerMetaData> serversMetaData;
    public ServersFinder() throws UnknownHostException {
        noOfThreads = 4;
        noOfWorkingThreads = 0;
        startingIp = new IPAddress( InetAddress.getLocalHost().getHostAddress());
        startingIp.getIp()[3] = 1;
        serversMetaData = new ArrayList<>();
        endingIp = new IPAddress(startingIp.toString());
        endingIp.getIp()[3] = 254;
    }
    public void start()
    {

    }
    public abstract void onFinish(ArrayList<ServerMetaData>  serversMetaData);
}
