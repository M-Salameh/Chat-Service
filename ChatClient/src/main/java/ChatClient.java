import javax.swing.*;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChatClient
{
    public static List<ClientChatImp> prevTest() throws MalformedURLException, NotBoundException, RemoteException {

        String loc = "localhost";
        String fname = "Client";
        String lname = "";
        String userName = "C";
        String password = "11";


        ClientChatImp clientChatImp = new ClientChatImp(fname , lname+"1",userName+"1" , password , loc);
        ClientChatImp clientChatImp2 = new ClientChatImp(fname , lname+"2",userName+"2" , password , loc);

        clientChatImp.Register();
        clientChatImp2.Register();


        clientChatImp.createChatRoom("room1");

        clientChatImp2.createChatRoom("room2");


        clientChatImp.joinChatRoom("room2");
        clientChatImp2.joinChatRoom("room1");

/*
        clientChatImp.sendMessage("room1" , "Hello");
        clientChatImp2.sendMessage("room2" , "Hello");
        clientChatImp2.sendMessage("room1" , "Hello Agan");

        MainPanel mainPanel1 = new MainPanel(clientChatImp);
        MainPanel mainPanel2 = new MainPanel(clientChatImp2);

        System.out.println("+++++++++++++++++++++++++++++++++++++++++");
        for (String str : clientChatImp2.getConversation("room1"))
        {
            System.out.println(str);
        }
        System.out.println("+++++++++++++++++++++++++++++++++++++++++");

        System.out.println("/////////////////////////////////////////////////////////////");
        for (String str : clientChatImp2.getChatRooms())
        {
            System.out.println(str);
        }
        System.out.println("/////////////////////////////////////////////////////////////");*/

       List<ClientChatImp> clientChatImps = new ArrayList<>();
        clientChatImps.add(clientChatImp);
        clientChatImps.add(clientChatImp2);
        return clientChatImps;
    }

    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException
    {
        String loc = "localhost";
        Registeration registeration = new Registeration(loc);
       /* List<ClientChatImp> clientChatImps = prevTest();
        ClientChatImp clientChatImp1 = clientChatImps.get(0);
        ClientChatImp clientChatImp2 = clientChatImps.get(1);
        MainPanel myChatsAndRooms1 = new MainPanel(clientChatImp1);
        MainPanel myChatsAndRoom2 = new MainPanel(clientChatImp2);*/
    }
}
