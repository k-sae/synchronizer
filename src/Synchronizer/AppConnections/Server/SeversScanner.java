package Synchronizer.AppConnections.Server;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by kemo on 04/02/2017.
 */
public abstract class SeversScanner {
    private int noOfThreads;
    private int noOfWorkingThreads;
    private String startingIp;
    private String endingIp;
    public SeversScanner(String startingIp) throws IOException {
        noOfThreads = 4;
        noOfWorkingThreads = 0;
        this.startingIp = startingIp;
    }

    public void start()
    {

    }
    public abstract void onFinish(ArrayList<ServerMetaData>  serversMetaData);
}
