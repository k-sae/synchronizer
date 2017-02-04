package Synchronizer.AppConnections.Server;

import java.util.ArrayList;

/**
 * Created by kemo on 04/02/2017.
 */
public abstract class SeversScanner {
    public SeversScanner() {
    }
    public abstract void onFinish(ArrayList<ServerMetaData>  serversMetaData);
}
