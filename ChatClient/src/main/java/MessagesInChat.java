import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public class MessagesInChat extends JFrame {
    private JTextField Message;
    private JButton SendMessage;
    private JTextArea Texts;
    private JPanel Conversation;
    private JButton MainMenu;
    private String roomName= "";

    public MessagesInChat(ClientChatImp clientChatImp , String roomname) throws RemoteException {
        setContentPane(Conversation);
        setVisible(true);
        roomName = roomname;
        setTitle("Room : " + roomname + " ( " + clientChatImp.getUserName()+" )");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600 , 600);
        List<String> list =clientChatImp.getConversation(roomName);

        Texts.setText("");
        for (String str : list)
        {
            Texts.append(str);
        }

        Message.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Message.setText("");
            }
        });
        SendMessage.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (!Message.getText().isEmpty())
                {
                    String msg = Message.getText();
                    try {
                        clientChatImp.sendMessage(roomName , msg);
                    } catch (RemoteException ex) {
                        throw new RuntimeException(ex);
                    }
                }

            }
        });
        clientChatImp.setMIC(this , roomname);
        MainMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    goMain(clientChatImp);
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
    public void receiveMsg(String msg)
    {
        Texts.append(msg);
    }
    private void goMain(ClientChatImp clientChatImp) throws RemoteException {
        MainPanel mainPanel= new MainPanel(clientChatImp);
        this.dispose();
    }
}
