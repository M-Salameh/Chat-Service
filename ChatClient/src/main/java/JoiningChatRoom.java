import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;

public class JoiningChatRoom extends JFrame{
    private JPanel JoiningChatRoom;
    private JTextField ChatRoomName;
    private JButton Join;

    public JoiningChatRoom(ClientChatImp clientChatImp)
    {
        setVisible(true);
        try {
            setTitle("Join Chat Room( " + clientChatImp.getUserName()+" )");
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        setContentPane(JoiningChatRoom);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600 , 600);
        ChatRoomName.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                ChatRoomName.setText("");
            }
        });
        Join.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (ChatRoomName.getText().isEmpty())
                {
                    ChatRoomName.setText("Cannot be Empty");
                }
                else
                {
                    try {
                        if (!clientChatImp.joinChatRoom(ChatRoomName.getText()))
                        {
                            ChatRoomName.setText("No Such Room");
                        }
                        else
                        {
                            goBack(clientChatImp);
                        }
                    } catch (RemoteException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
    }
    public void goBack(ClientChatImp clientChatImp) throws RemoteException {
        MainPanel mainPanel = new MainPanel(clientChatImp);
        this.dispose();
    }
}
