import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class ServerChatImp extends UnicastRemoteObject implements IServerChat
{
    Map<String , IClientChat> users = new HashMap<>();
    Map<IClientChat , List<String>> ChatRoomsOfUser = new HashMap<>(); /// Creator -> chat rooms created
    Map<IClientChat , Boolean> ActiveUsers = new HashMap<>();
    Map<IClientChat , String> UsersPasswords = new HashMap<>();

    Map<String , List<String>> ChatRoomMessages = new HashMap<>();

    Map<String , List<IClientChat>> ChatRoomMembers = new HashMap<>();

    protected ServerChatImp() throws RemoteException
    {
    }

    @Override
    public void createChatRoom(String roomName, IClientChat iClientChat) throws RemoteException
    {
        String userName = iClientChat.getUserName();
        if (!users.containsKey(userName)) return;
        ChatRoomsOfUser.get(iClientChat).add(roomName);
        ChatRoomMembers.put(roomName , new ArrayList<>());
        ChatRoomMembers.get(roomName).add(iClientChat);
        ChatRoomMessages.put(roomName , new ArrayList<>());
    }

    @Override
    public void joinChatRoom(String roomName, IClientChat iClientChat) throws RemoteException
    {
        if (!ChatRoomMembers.containsKey(roomName)) return;
        ChatRoomMembers.get(roomName).add(iClientChat);
       // System.out.println("Added to " + roomName + " pppp : " + iClientChat.getUserName());
    }

    @Override
    public void deleteChatRoom(String roomName, IClientChat iClientChat) throws RemoteException
    {
        String userName = iClientChat.getUserName();
        if (!users.containsKey(userName))
        {
           // System.out.println("No Such User");
            return;
        }
        if (ChatRoomsOfUser.get(iClientChat).contains(roomName))
        {
            ChatRoomsOfUser.get(iClientChat).remove(roomName);
            ChatRoomMembers.remove(roomName);
        }
        else System.out.println("No Such Room for " + userName);
    }

    @Override
    public void Register(String fname, String lname, String username, String password , IClientChat iClientChat) throws RemoteException
    {
        users.put(username , iClientChat);
        ChatRoomsOfUser.put(iClientChat , new ArrayList<>());
        UsersPasswords.put(iClientChat , password);
        ActiveUsers.put(iClientChat , true);
       /// System.out.println("User " + iClientChat.getUserName() + "Logged in");
    }

    @Override
    public void LogIn(String password  , IClientChat iClientChat) throws RemoteException
    {
        String username = iClientChat.getUserName();
        if (!users.containsKey(username))
        {
            iClientChat.receiveMessage("You do not have an account");
            return;
        }
        /*
        validation and authentication for later
        * */

        if (UsersPasswords.get(iClientChat).equals(password)) ActiveUsers.put(iClientChat , true);
        else iClientChat.receiveMessage("Wrong pass");

    }

    @Override
    public List<String> getChatRooms(IClientChat iClientChat) throws RemoteException
    {
        if (ChatRoomsOfUser.get(iClientChat).size() == 0)
        {
            return new ArrayList<>();
        }
        else return ChatRoomsOfUser.get(iClientChat);
    }

    @Override
    public List<String> getConversation(String roomName, IClientChat iClientChat) throws RemoteException
    {
        if (!(ChatRoomMembers.containsKey(roomName) && (ChatRoomMembers.get(roomName)).contains(iClientChat)))
        {
            return new ArrayList<>();
        }

        return ChatRoomMessages.get(roomName);
    }

    @Override
    public void sendMessage(String roomName, String Message, IClientChat iClientChat) throws RemoteException
    {
        if (ChatRoomMembers.containsKey(roomName) && ChatRoomMembers.get(roomName).contains(iClientChat))
        {
            Message = iClientChat.getUserName() + " : " + Message;
            Message = Message + "\n" + "************************************************\n";
            String finalMessage = Message;
            ChatRoomMembers.get(roomName).stream().forEach
                    (
                        client->
                            {
                                if (ActiveUsers.get(client))
                                    try
                                    {
                                        System.out.println("Can send to " + client.getUserName());
                                        client.receiveMessage(finalMessage);
                                    }
                                    catch (RemoteException e)
                                    {
                                        ActiveUsers.put(client , false);
                                    }
                        }
                  );
        }
    }

    @Override
    public void LogOut(IClientChat iClientChat) throws RemoteException
    {
        String username = iClientChat.getUserName();
        if (!users.containsKey(username)) return;
        ActiveUsers.put(iClientChat, false);
    }
}
