package Synchronizer.AppConnections.Server;

import Connections.IPAddress;

import java.util.ArrayList;

/**
 * Created by kemo on 04/02/2017.
 */
public abstract class SeversScanner {
    private int noOfThreads;
    private int noOfWorkingThreads;
    private IPAddress startingIp;
    private IPAddress endingIp;
    private ArrayList<ServerMetaData> serversMetaData;
    public SeversScanner(IPAddress startingIp) {
        noOfThreads = 4;
        noOfWorkingThreads = 0;
        this.startingIp = startingIp;
        serversMetaData = new ArrayList<>();
        endingIp = new IPAddress(startingIp.toString());
        endingIp.getIp()[3] = (short) 254;
    }
    public SeversScanner(String startingIp)
    {
        this(new IPAddress(startingIp));
    }
    public void start()
    {

    }
    public abstract void onFinish(ArrayList<ServerMetaData>  serversMetaData);
}
