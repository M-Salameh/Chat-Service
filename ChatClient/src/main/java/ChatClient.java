import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class ChatClient
{
    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException {
        String loc = "localhost";
        String fname = "Client";
        String lname = "";
        String userName = "C";
        String password = "11";


        ClientChatImp clientChatImp = new ClientChatImp(fname , lname+"1",userName+"1" , password , loc);
        ClientChatImp clientChatImp2 = new ClientChatImp(fname , lname+"2",userName+"2" , password , loc);

        clientChatImp.Register(fname , lname+"1",userName+"1" , password );
        clientChatImp2.Register(fname , lname+"2",userName+"2" , password  );


        clientChatImp.createChatRoom("room1");

        clientChatImp2.createChatRoom("room2");


        clientChatImp.joinChatRoom("room2");
        clientChatImp2.joinChatRoom("room1");

        System.out.println("Hello World");

        clientChatImp.sendMessage("room1" , "Hello");
        clientChatImp2.sendMessage("room2" , "Hello");

        /*ClientChatImp[] clientChatImps = new ClientChatImp[3];
        for (Integer i=1 ; i<=3 ; i++)
        {
            clientChatImps[i-1] = new ClientChatImp
                    (fname , lname+i.toString() , userName+i.toString() , password , loc);
        }
        Scanner scanner = new Scanner(System.in);
        Integer choice =  scanner.nextInt();
        choice -=1;
        clientChatImps[choice].createChatRoom("room"+choice.toString());
        Integer x = choice;
        while (choice > 0)
        {
            clientChatImps[choice].joinChatRoom("room"+x.toString());
            x--;
        }
        int g = scanner.nextInt();
        clientChatImps[choice].sendMessage("room"+choice.toString() , "Hello from"+choice.toString());
        for (String str : clientChatImps[choice].getChatRooms())
        {
            System.out.println(str);
        }*/

        System.out.println("DONE");
    }
}
