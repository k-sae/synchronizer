package Connections;

import java.util.Arrays;

/**
 * Created by kemo on 04/02/2017.
 */
public class IPAddress {
   private byte[] ip;
    public IPAddress(String s)
    {
        setIp(s);
    }
    public IPAddress(byte[] ip)
    {
        this.ip = ip;
    }

    public byte[] getIp() {
        return ip;
    }

    public void setIp(byte[] ip) {
        this.ip = ip;
    }
    public void setIp(String ipSTR) {
        String[] ipNos = ipSTR.split(".");
        ip = new byte[4];
        if (ipNos.length > 4) throw new RuntimeException("invalid ip Address : " + Arrays.toString(ipNos));
        for (int i = 0; i < 4; i++) {
            ip[i] = Byte.parseByte(ipNos[i]);
        }
    }

    @Override
    public String toString() {
        return Arrays.toString(ip);
    }
}
