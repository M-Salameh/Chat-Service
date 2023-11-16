import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;

public class MainPanel extends JFrame{
    private JPanel MyChats;
    private JButton CreateRoom;
    private JButton JoinRoom;
    private JButton DeleteRoom;
    private JButton LogOut;
    private JButton ViewChats;

    public MainPanel(ClientChatImp clientChatImp) throws RemoteException {
        setContentPane(MyChats);
        setTitle("My Chats ( " + clientChatImp.getUserName()+" )");
        setVisible(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setSize(600,600);

        CreateRoom.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                createChatRoom(clientChatImp);
            }
        });

        JoinRoom.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                joinChatRoom(clientChatImp);
            }
        });
        DeleteRoom.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                deleteChatRoom(clientChatImp);
            }
        });
        LogOut.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                logout(clientChatImp);
            }
        });
        ViewChats.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                viewChatRoom(clientChatImp);
            }
        });
    }

    public void viewChatRoom(ClientChatImp clientChatImp)
    {
        MyChatRooms myChatRooms = new MyChatRooms(clientChatImp);
        this.dispose();
    }
    public void createChatRoom(ClientChatImp clientChatImp)
    {
        CreatingChatRoom creatingChatRoom = new CreatingChatRoom(clientChatImp);
        this.dispose();
    }

    public void joinChatRoom(ClientChatImp clientChatImp)
    {
        JoiningChatRoom joiningChatRoom = new JoiningChatRoom(clientChatImp);
        this.dispose();
    }
    public void deleteChatRoom(ClientChatImp clientChatImp)
    {
        DeletingChatRoom deletingChatRoom =  new DeletingChatRoom(clientChatImp);
        this.dispose();
    }

    public void logout(ClientChatImp clientChatImp)
    {
        LogOut logOut = new LogOut(clientChatImp);
        this.dispose();
    }

    public void receiveMsg(String Msg , String roomName , ClientChatImp clientChatImp)
    {
        try
        {
            MessagesInChat messagesInChat = new MessagesInChat(clientChatImp , roomName);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
