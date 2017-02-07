package Synchronizer.AppConnections.Server;

import Connections.Command;
import Connections.ReceiveCommand;
import Synchronizer.AppConnections.LogicalConstants;
import Synchronizer.Control.MainUser;

import java.net.*;
import java.util.Arrays;

/**
 * Created by kemo on 03/02/2017.
 */
public class CommandsAnalyzer extends ReceiveCommand {
    //TODO #kareem
    // see the figure how to send it again
    public CommandsAnalyzer(Socket remote) {
        super(remote);
    }

    @Override
    public void Analyze(Command command) {
        //TODO #kareem
        if (command.getKeyWord().equals(LogicalConstants.FETCH_META_DATA))
        {
            ServerMetaData serverMetaData = new ServerMetaData();
            try {
                serverMetaData.setIp((InetAddress.getLocalHost().getHostAddress()));
                serverMetaData.setName(MainUser.getMainUser().getName());
                serverMetaData.setPort(MainServerConnection.getServerSocket().getLocalPort());
                InetAddress inetAddress= InetAddress.getLocalHost();
                NetworkInterface.getByInetAddress(inetAddress);
                serverMetaData.setMAC((Arrays.toString(NetworkInterface.getNetworkInterfaces().nextElement().getHardwareAddress())));

            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }
    }
}
