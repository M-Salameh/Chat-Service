import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;

public class DeletingChatRoom extends JFrame{
    private JTextField ChatRoomName;
    private JPanel DeletingChatRoom;
    private JButton Delete;

    public DeletingChatRoom(ClientChatImp clientChatImp)
    {
        setVisible(true);
        try {
            setTitle("Deleting Chat Room( " + clientChatImp.getUserName()+" )");
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        setContentPane(DeletingChatRoom);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600 , 600);
        ChatRoomName.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                ChatRoomName.setText("");
            }
        });
        Delete.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (ChatRoomName.getText().isEmpty())
                {
                    ChatRoomName.setText("Can not be Empty");
                }
                else
                {
                    try {
                        if (!clientChatImp.deleteChatRoom(ChatRoomName.getText()))
                        {
                            ChatRoomName.setText("Error : Either you do not own group or it does not exist");
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
    }
    public void doit(ClientChatImp clientChatImp) throws RemoteException {
        MainPanel mainPanel = new MainPanel(clientChatImp);
        this.dispose();
    }
}
