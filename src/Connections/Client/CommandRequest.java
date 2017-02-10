package Connections.Client;

import Connections.Command;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

/**
 * Created by kemo on 08/11/2016.
 */
public abstract class CommandRequest implements ConnectionRunnable {
    private  Socket socket;
    private Command command;
    public CommandRequest(Socket socket, Command   command)
    {
       this.socket = socket;
        this.command = command;
    }
    public void run() throws SocketException {
        try {
            //send command to server
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF(command.toString());
            //receive server command
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            //read string from server
            Long time = System.currentTimeMillis();
            //TODO
            //figure out why it takes so long time
            socket.setSoTimeout(5000);
            final String s = dataInputStream.readUTF();
            System.out.println(System.currentTimeMillis() - time);
            //start these function in another thread inorder to prevent time consuming
            analyze(Command.fromString(s));
        } catch (SocketException e) {
            throw e;
        }catch (SocketTimeoutException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
        catch (NullPointerException ignored)
        {
            throw  new SocketException();
        }
    }
    public void updateConnectionSocket(Socket socket)
    {
        this.socket = socket;
    }
    public abstract void  analyze(Command cmd);
}
