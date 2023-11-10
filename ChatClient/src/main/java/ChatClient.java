import com.sun.org.apache.xerces.internal.impl.xpath.XPath;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ChatClient
{
    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException {
        String loc = "localhost";
        IServerChat iServerChat = (IServerChat) Naming.lookup("rmi://"+loc+":1099/chat");
        String fname = "MM";
        String lname = "SS";
        String username = "ms";
        String password = "123";
        iServerChat.Register(fname , lname , username , password);
        iServerChat.createChatRoom("room1" , username);
        iServerChat.LogIn(username , password);
        iServerChat.createChatRoom("room2" ,username);
        iServerChat.createChatRoom("room3" , username);
        iServerChat.deleteChatRoom("room2", username);
        iServerChat.LogOut(username , password);
    }
}
