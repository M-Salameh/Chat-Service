import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IClientChat extends Remote
{
    public String receiveMessage (String Message) throws RemoteException;
    /// public Boolean checkPassword (String password) throws RemoteException;
    public String getUserName () throws RemoteException;
}
