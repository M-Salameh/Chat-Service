import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IServerChat extends Remote
{

    public boolean createChatRoom(String roomName , IClientChat iClientChat) throws RemoteException;

    public boolean joinChatRoom(String roomName, IClientChat iClientChat) throws RemoteException;

    public boolean deleteChatRoom(String roomName , IClientChat iClientChat) throws RemoteException;

    public boolean Register(String fname , String lname, String username, String password , IClientChat iClientChat) throws RemoteException;

    public boolean LogIn(String password , IClientChat iClientChat) throws RemoteException;

    public List<String> getChatRooms(IClientChat iClientChat) throws RemoteException;

    public List<String> getConversation(String roomName , IClientChat iClientChat) throws RemoteException;

    public void sendMessage(String roomName, String message , IClientChat iClientChat ) throws RemoteException;

    public boolean LogOut(IClientChat iClientChat) throws RemoteException;

   /// public boolean addListener (String roomName ) throws RemoteException;
}
