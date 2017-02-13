package Synchronizer.AppConnections.Server;

import Connections.Command;
import Connections.ReceiveCommand;
import Synchronizer.AppConnections.LogicalConstants;
import Synchronizer.Control.MainUser;

import java.net.*;
import java.util.Arrays;
import java.util.Enumeration;

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
                Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces();
                byte[] macAddress = new byte[6];
                while (enumeration.hasMoreElements())
                {
                    byte[] mac = enumeration.nextElement().getHardwareAddress();
                    if (mac != null) {
                        //use this to format mac
                        //i don't understand it so i left it :D
//                        for (int k = 0; k < mac.length; k++) {
//                            System.out.format("%02X%s", mac[k], (k < mac.length - 1) ? "-" : "");
//                        }
//                        System.out.println();
                        macAddress = mac;
                        break;
                    }

                }
                serverMetaData.setMAC((Arrays.toString(macAddress)));
                command.setSharableObject(serverMetaData);

            } catch (UnknownHostException | SocketException e) {
                e.printStackTrace();
            }
            sendCommand(command);
        }
    }
}
