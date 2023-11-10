import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ChatServer
{
    public static void main(String[] args) throws RemoteException
    {   
        System.setProperty("java.security.policy" , "./RmiPolicy.policy");
        System.setSecurityManager(new RMISecurityManager());

        Registry registry = LocateRegistry.createRegistry(1099);

        ServerChatImp serverChatImp = new ServerChatImp();
        String service = "chat";
        registry.rebind(service , serverChatImp);
    }
}
