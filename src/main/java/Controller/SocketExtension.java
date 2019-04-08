package Controller;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketExtension {
    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;

    public SocketExtension(){
        connect();
    }
    public void connect(){
        try{
            socket = new Socket("localhost",1234);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e){
            System.out.println("can't start socket");
        }
    }
    public void close(){
        try{
            socket.close();
            in.close();
            out.close();
        } catch (Exception e){
            System.out.println("can't close socket");
        }
    }
    public void take(){
        try{
            out.writeUTF("semaphore -take");
            in.readUTF();
        } catch (Exception e){
            System.out.println("can't send semaphore -take");
        }
    }
    public void release(){
        try{
            out.writeUTF("semaphore -release");
        } catch (Exception e){
            System.out.println("can't send semaphore -release");
        }
    }
    public String getDir(){
        String dir = "";
        try{
            out.writeUTF("dir");
            dir = in.readUTF();
        } catch (Exception e){}
        return dir;
    }
}
