package Synchronizer.AppConnections.Server;

import Connections.Shareable;
import com.google.gson.Gson;

/**
 * Created by kemo on 04/02/2017.
 */
public class ServerMetaData implements Shareable {
    private String ip;
    private int port;
    private String MAC;
    private String Name;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getMAC() {
        return MAC;
    }

    public void setMAC(String MAC) {
        this.MAC = MAC;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    @Override
    public String convertToString() {
        return new Gson().toJson(this);
    }
    public static ServerMetaData fromString(String jsonStr)
    {
        return new Gson().fromJson(jsonStr, ServerMetaData.class);
    }
}
