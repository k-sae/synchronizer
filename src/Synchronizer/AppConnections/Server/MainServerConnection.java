package Synchronizer.AppConnections.Server;

import Connections.Server.ServerInitializer;

import java.net.Socket;

import static Synchronizer.AppConnections.ConnectionConstants.DUE_PORT;
import static Synchronizer.AppConnections.ConnectionConstants.INITIAL_PORT;
import static Synchronizer.AppConnections.ConnectionConstants.VERIFICATION_CODE;

/**
 * Created by kemo on 03/02/2017.
 */
public class MainServerConnection {
    public void startServer()
    {
        new Thread(() -> {
            ServerInitializer serverInitializer = new ServerInitializer(INITIAL_PORT) {
                @Override
                public void onClientConnection(Socket client) {
                    //start listening for client Requests
                    new CommandsAnalyzer(client);
                }
            };
            serverInitializer.setDuePort(DUE_PORT);
            serverInitializer.setVerification(VERIFICATION_CODE); //don't check the verification code :D
            serverInitializer.startMainConnection();
        }).start();
    }
}
