import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerChatImp extends UnicastRemoteObject implements IServerChat
{
    Map<String , UsersDB> users = new HashMap<>();
    Map<UsersDB , List<String>> ChatRooms = new HashMap<>();

    Map<UsersDB , Boolean> ActiveUsers = new HashMap<>();


    protected ServerChatImp() throws RemoteException
    {
    }

    @Override
    public void createChatRoom(String roomName, String userName) throws RemoteException
    {
        if (!users.containsKey(userName)) return;
        UsersDB usersDB = users.get(userName);
        ChatRooms.get(usersDB).add(roomName);

        System.out.println("User : " + userName + " -- now has the following chat rooms");
        for (String str : ChatRooms.get(usersDB))
        {
            System.out.println(str);
        }
        System.out.println("*****************************");
    }

    @Override
    public void deleteChatRoom(String roomName, String userName) throws RemoteException
    {
        if (!users.containsKey(userName))
        {
            System.out.println("No Such User");
            return;
        }
        UsersDB usersDB = users.get(userName);
        if (ChatRooms.get(usersDB).contains(roomName)) {
            ChatRooms.get(usersDB).remove(roomName);

            System.out.println("User : " + userName + " -- now has the following chat rooms");
            for (String str : ChatRooms.get(usersDB)) {
                System.out.println(str);
            }
            System.out.println("*****************************");
        }
        else System.out.println("No Such Room for " + userName);
    }

    @Override
    public void Register(String fname, String lname, String username, String password) throws RemoteException
    {
        UsersDB usersDB = new UsersDB(fname , lname , username , password);
        users.put(username , usersDB);
        ChatRooms.put(usersDB , new ArrayList<>());
    }

    @Override
    public void LogIn(String username, String password) throws RemoteException
    {
        if (!users.containsKey(username)) return;
        /*
        validation and authentication for later
        * */
        UsersDB usersDB = users.get(username);
        if (usersDB.checkPassword(password))  ActiveUsers.put(usersDB, true);
    }

    @Override
    public void LogOut(String username, String Password) throws RemoteException
    {
        if (!users.containsKey(username)) return;
        UsersDB usersDB = users.get(username);
        ActiveUsers.put(usersDB, false);
    }
}
