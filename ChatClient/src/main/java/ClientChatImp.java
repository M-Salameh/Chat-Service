import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientChatImp extends UnicastRemoteObject implements IClientChat
{
    private String firstName, lastName, userName,password;
    IServerChat iServerChat;

    Map<String,MessagesInChat> messagesInChat = new HashMap<>();

    public ClientChatImp(String firstName , String lastName , String userName ,
                         String password , String server)
            throws RemoteException, MalformedURLException, NotBoundException {
        this.lastName = lastName;
        this.userName = userName;
        this.firstName = firstName;
        this.password = password;
        this.iServerChat = (IServerChat) Naming.lookup("rmi://"+server+":1099/chat");
    }
    public void setMIC(MessagesInChat messagesInChat , String roomName)
    {
        this.messagesInChat.put(roomName , messagesInChat);
    }
    @Override
    public String receiveMessage(String Message , String roomName) throws RemoteException
    {
        //System.out.println(Message);
//        this.myChatsAndRooms.receiveMsg(Message , roomName , this);
        System.out.println("RECEIVING");
        if (this.messagesInChat.containsKey(roomName)) this.messagesInChat.get(roomName).receiveMsg(Message);
        ///System.out.println(Message + " in Room : " + roomName);
        return Message;
    }

    @Override
    public String getUserName() throws RemoteException
    {
        //return this.userName + " ( " + this.firstName+" " + this.lastName+" )";
        return this.userName;
    }
    public boolean createChatRoom(String roomName) throws RemoteException
    {
        return iServerChat.createChatRoom(roomName , this);
    }

    public boolean joinChatRoom(String roomName) throws RemoteException
    {
        return iServerChat.joinChatRoom(roomName , this);
    }

    public boolean deleteChatRoom(String roomName) throws RemoteException
    {
        return iServerChat.deleteChatRoom(roomName , this);
    }

    public boolean Register() throws RemoteException
    {
        System.out.println("Registering");
        return iServerChat.Register(firstName , lastName , userName,  password , this);
    }

    public boolean LogIn(String password) throws RemoteException
    {
        return iServerChat.LogIn(password , this);
    }

    public boolean LogOut() throws RemoteException
    {
        return iServerChat.LogOut(this);
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
        System.out.println("Sending MSG");
         iServerChat.sendMessage(roomName , message , this);
    }
}
