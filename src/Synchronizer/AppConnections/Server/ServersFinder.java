package Synchronizer.AppConnections.Server;

import Connections.Client.ServerFoundListener;
import Connections.Client.ServerScanner;
import Connections.IPAddress;
import Synchronizer.AppConnections.ConnectionConstants;

import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * Created by kemo on 04/02/2017.
 */

//this works on 5 simultaneous threads
public abstract class ServersFinder {
    private int noOfThreads;
    private int noOfWorkingThreads; //idk why volatile ruined every thing btw it seems working without it
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
        final short PART_SIZE = (short) ((endingIp.getIp()[3] - startingIp.getIp()[3]) / noOfThreads);
        noOfWorkingThreads = noOfThreads;
        startThreadListening();
        short startIp = startingIp.getIp()[3];
        for (int i = 0; i < noOfThreads; i++) {
            short endIp = (short) (startIp + PART_SIZE);
            if (i == noOfThreads - 1) endIp = (short) (endingIp.getIp()[3] + 1);
            check(startIp, endIp);
            startIp = (short) (startIp + PART_SIZE);
        }
    }
    private void check(short startIp, short endIp )
    {
        new Thread(() -> {
            for (short j = startIp; j < endIp; j++) {
                ServerScanner serverScanner = new ServerScanner(ConnectionConstants.INITIAL_PORT);
                serverScanner.setVerification(ConnectionConstants.VERIFICATION_CODE);
                serverScanner.setTimeout(20);
                serverScanner.setServerFoundListener(new ServerFoundListener() {
                    @Override
                    public void uponConnection(Socket server) {
                        try {
                            if (!server.getLocalAddress().toString().contains(InetAddress.getLocalHost().getHostAddress()))
                            {
                                //TODO #kareem
                            }
                        } catch (UnknownHostException e) {
                            e.printStackTrace();
                        }
                    }
                });
                IPAddress ipAddress = new IPAddress(startingIp.toString());
                ipAddress.getIp()[3] = j;
                serverScanner.isAvailable(ipAddress.toString());
            }
            noOfWorkingThreads--;
        }).start();

    }
    //this function will w8 till all threads finish before trigger onFinish function at last
    private void startThreadListening()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (noOfWorkingThreads > 0)
                {
                    try {
                        Thread.sleep(40);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                onFinish(serversMetaData);
            }
        }).start();
    }
    public abstract void onFinish(ArrayList<ServerMetaData>  serversMetaData);
}
