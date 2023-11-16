import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;

public class CreatingChatRoom extends JFrame {
    private JPanel CreateChatRoom;
    private JTextField ChatRoomName;
    private JButton submit;
    private JButton GoMain;

    public CreatingChatRoom(ClientChatImp clientChatImp) {
        setSize(600 , 600);
        setVisible(true);
        try {
            setTitle("Creating Chat Room( " + clientChatImp.getUserName()+" )");
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        setContentPane(CreateChatRoom);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ChatRoomName.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                ChatRoomName.setText("");
            }
        });
        submit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (ChatRoomName.getText().isEmpty())
                {
                    ChatRoomName.setText("Can not be empty");
                }
                else
                {
                    try {
                            if (!clientChatImp.createChatRoom(ChatRoomName.getText()))
                            {
                                ChatRoomName.setText("Room already exists");
                            }
                            else
                            {
                                doit(clientChatImp);
                            }
                    } catch (RemoteException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        GoMain.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    doit(clientChatImp);
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
    public void doit(ClientChatImp clientChatImp) throws RemoteException {
        MainPanel myChatRooms = new MainPanel(clientChatImp);
        this.dispose();
    }
}
