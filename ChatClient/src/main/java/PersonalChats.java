import javax.swing.*;
import javax.swing.plaf.ListUI;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.util.List;

public class PersonalChats extends JFrame {
    private JButton MainMenu;
    private JTextField Message;
    private JButton SendMessage;
    private JTextArea Texts;
    private JPanel Conversation;

    public PersonalChats(IClientChat senderClientChatImp , ClientChatImp me)
    {
        setContentPane(Conversation);
        setVisible(true);
        try {
            setTitle("Chat with : " + senderClientChatImp.getUserName());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600 , 600);

        Texts.setText("");

        List<String> list = me.texts.get(senderClientChatImp);
        if (list != null)
        {

            for (String str : list)
            {
                Texts.append(str);
            }
        }
        else if (list ==null) Texts.setText("");
        MainMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    goMain(me);
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
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
                        msg = me.getUserName() + " : " + msg;
                    } catch (RemoteException ex) {
                        throw new RuntimeException(ex);
                    }
                    msg = msg + "\n" + "************************************************\n";
                    try {
                        Texts.append(msg);
                        senderClientChatImp.receiveMessage(msg , me);
                    } catch (RemoteException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        me.setPC(senderClientChatImp , this);

    }
    private void goMain(ClientChatImp clientChatImp) throws RemoteException {
        MainPanel mainPanel= new MainPanel(clientChatImp);
        this.dispose();
    }
    public void receiveMsg(String msg)
    {
        Texts.append(msg);
    }
}
