package Synchronizer.AppConnections.Server;

import Connections.Command;
import Connections.ReceiveCommand;

import java.net.Socket;

/**
 * Created by kemo on 03/02/2017.
 */
public class CommandsAnalyzer extends ReceiveCommand {
    public CommandsAnalyzer(Socket remote) {
        super(remote);
    }

    @Override
    public void Analyze(Command command) {
        //TODO #kareem
    }
}
