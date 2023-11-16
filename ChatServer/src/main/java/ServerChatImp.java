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

    Map<String , IClientChat> ChatRoomsOwners = new HashMap<>();


    protected ServerChatImp() throws RemoteException
    {
    }

    @Override
    public boolean createChatRoom(String roomName, IClientChat iClientChat) throws RemoteException
    {
        String userName = iClientChat.getUserName();
        if (!users.containsKey(userName)) return false;
        if (ChatRoomsOfUser.get(iClientChat).contains(roomName)) return false;
        if (ChatRoomsOwners.containsKey(roomName)) return  false;
        ChatRoomsOfUser.get(iClientChat).add(roomName);
        ChatRoomsOwners.put(roomName , iClientChat);
        ChatRoomMembers.put(roomName , new ArrayList<>());
        ChatRoomMembers.get(roomName).add(iClientChat);

        ChatRoomMessages.put(roomName , new ArrayList<>());
        System.out.println("Created");
        return true;
    }

    @Override
    public boolean joinChatRoom(String roomName, IClientChat iClientChat) throws RemoteException
    {
        if (!ChatRoomMembers.containsKey(roomName)) return false;
        ChatRoomMembers.get(roomName).add(iClientChat);
        ChatRoomsOfUser.get(iClientChat).add(roomName);
        System.out.println("joined");
        return true;
    }

    @Override
    public boolean deleteChatRoom(String roomName, IClientChat iClientChat) throws RemoteException
    {
        String userName = iClientChat.getUserName();
        if (!users.containsKey(userName))
        {
            return false;
        }
        if (ChatRoomsOwners.containsKey(roomName)
            && ChatRoomsOwners.get(roomName).equals(iClientChat))
        {
            ChatRoomsOfUser.get(iClientChat).remove(roomName);
            ChatRoomMembers.remove(roomName);
            ChatRoomMessages.remove(roomName);
            System.out.println("Deleted");
            return true;
        }
        //else System.out.println("No Such Room for " + userName);
        return false;
    }

    @Override
    public boolean Register(String fname, String lname, String username, String password , IClientChat iClientChat) throws RemoteException
    {
        if (users.containsKey(username))
        {
            return false;
        }
        users.put(username , iClientChat);
        ChatRoomsOfUser.put(iClientChat , new ArrayList<>());
        UsersPasswords.put(iClientChat , password);
        ActiveUsers.put(iClientChat , true);
        return true;
        //System.out.println("RRRRRR");
    }

    @Override
    public boolean LogIn(String password  , IClientChat iClientChat) throws RemoteException
    {
        String username = iClientChat.getUserName();
        if (!users.containsKey(username))
        {
            //iClientChat.receiveMessage("You do not have an account");
            return false;
        }

        if (UsersPasswords.get(iClientChat).equals(password)) ActiveUsers.put(iClientChat , true);
        //else iClientChat.receiveMessage("Wrong password");
        return true;
    }

    @Override
    public List<String> getChatRooms(IClientChat iClientChat) throws RemoteException
    {
        if (ChatRoomsOfUser.get(iClientChat).size() == 0)
        {
            //System.out.println("Here");
            return new ArrayList<>();
        }
        else
        {
          ///  System.out.println("ChatRoomsLoist");
            return ChatRoomsOfUser.get(iClientChat);
        }
    }

    @Override
    public List<String> getConversation(String roomName, IClientChat iClientChat) throws RemoteException
    {
        if (!(ChatRoomMembers.containsKey(roomName) && (ChatRoomMembers.get(roomName)).contains(iClientChat)))
        {
            return new ArrayList<>();
        }

        System.out.println("Convs");
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
            ChatRoomMessages.get(roomName).add(Message);
            ChatRoomMembers.get(roomName).stream().forEach
                    (
                        client->
                            {
                                if (ActiveUsers.get(client))
                                    try
                                    {
                                        System.out.println("Can send to " + client.getUserName());
                                        client.receiveMessage(finalMessage , roomName);
                                    }
                                    catch (RemoteException e)
                                    {
                                        ActiveUsers.put(client , false);
                                    }
                        }
                  );
        }
        ///else System.out.println("NO USERS !!");
    }

    @Override
    public boolean LogOut(IClientChat iClientChat) throws RemoteException
    {
        String username = iClientChat.getUserName();
        if (!users.containsKey(username)) return false;
        ActiveUsers.put(iClientChat, false);
        return true;
    }
}
