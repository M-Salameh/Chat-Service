import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IClientChat extends Remote
{

    public String receiveMessage(String Message, String roomName) throws RemoteException;
    public String receiveMessage(String Message , IClientChat iClientChat) throws RemoteException;
    /// public Boolean checkPassword (String password) throws RemoteException;
    public String getUserName () throws RemoteException;
}
