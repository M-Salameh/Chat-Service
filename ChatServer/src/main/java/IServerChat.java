import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IServerChat extends Remote
{

    public void createChatRoom(String roomName , IClientChat iClientChat) throws RemoteException;

    public void joinChatRoom(String roomName, IClientChat iClientChat) throws RemoteException;

    public void deleteChatRoom(String roomName , IClientChat iClientChat) throws RemoteException;

    public void Register(String fname , String lname, String username, String password , IClientChat iClientChat) throws RemoteException;

    public void LogIn(String password , IClientChat iClientChat) throws RemoteException;

    public List<String> getChatRooms(IClientChat iClientChat) throws RemoteException;

    public List<String> getConversation(String roomName , IClientChat iClientChat) throws RemoteException;

    public void sendMessage(String roomName, String message , IClientChat iClientChat ) throws RemoteException;

    public void LogOut(IClientChat iClientChat) throws RemoteException;

   /// public void addListener (String roomName ) throws RemoteException;
}
