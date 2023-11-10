import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class ClientChatImp extends UnicastRemoteObject implements IClientChat
{
    private String firstName, lastName, userName,password;
    IServerChat iServerChat;

    public ClientChatImp(String firstName , String lastName , String userName , String password , String server) throws RemoteException, MalformedURLException, NotBoundException {
        this.lastName = lastName;
        this.userName = userName;
        this.firstName = firstName;
        this.password = password;
        this.iServerChat = (IServerChat) Naming.lookup("rmi://"+server+":1099/chat");
    }

    @Override
    public String receiveMessage(String Message) throws RemoteException
    {
        System.out.println(Message);
        return Message;
    }

    @Override
    public String getUserName() throws RemoteException
    {
        //return this.userName + " ( " + this.firstName+" " + this.lastName+" )";
        return this.userName;
    }
    public void createChatRoom(String roomName) throws RemoteException
    {
        iServerChat.createChatRoom(roomName , this);
    }

    public void joinChatRoom(String roomName) throws RemoteException
    {
        iServerChat.joinChatRoom(roomName , this);
    }

    public void deleteChatRoom(String roomName) throws RemoteException
    {
        iServerChat.deleteChatRoom(roomName , this);
    }

    public void Register(String fname , String lname, String username, String password ) throws RemoteException
    {
        iServerChat.Register(fname, lname , username , password , this);
    }

    public void LogIn(String password) throws RemoteException
    {
        iServerChat.LogIn(password , this);
    }

    public void LogOut() throws RemoteException
    {
        iServerChat.LogOut(this);
    }
    public List<String> getChatRooms() throws RemoteException
    {
        return iServerChat.getChatRooms(this);
    }

    public List<String> getConversation(String roomName) throws RemoteException
    {
        return iServerChat.getConversation(roomName , this);
    }

    public void sendMessage(String roomName, String message) throws RemoteException
    {
        iServerChat.sendMessage(roomName , message , this);
    }
}
