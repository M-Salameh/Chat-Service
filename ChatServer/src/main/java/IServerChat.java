import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IServerChat extends Remote
{

    public void createChatRoom(String roomName , String userName) throws RemoteException;

    ///public void joinChatRoom(String roomName) throws RemoteException;

    public void deleteChatRoom(String roomName , String userName) throws RemoteException;

    public void Register(String fname , String lname, String username, String password) throws RemoteException;

    public void LogIn(String username, String password) throws RemoteException;

   /// public List<String> getChatRooms(String username , String password) throws RemoteException;

 ///   public List<String> getConversation(String roomName , String username , String password) throws RemoteException;

   // public void sendMessage(String roomName, String message , String username , String password ) throws RemoteException;

    public void LogOut(String username , String Password) throws RemoteException;

   /// public void addListener (String roomName ) throws RemoteException;
}
