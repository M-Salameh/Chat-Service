import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;

public class LogIN extends JFrame{
    private JPanel LoggingIN;
    private JTextField Password;
    private JButton LogIn;

    public LogIN(ClientChatImp clientChatImp)
    {
        setVisible(true);
        try {
            setTitle("Log In( " + clientChatImp.getUserName()+" )");
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        setContentPane(LoggingIN);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600 , 600);

        Password.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Password.setText("");
            }
        });
        LogIn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                boolean can = (Password.getText().isEmpty());
                if (can)
                {
                    //UserName.setText("Cannot Be Empty");
                    Password.setText("Cannot Be Empty");
                }
                else
                {
                    try {
                        if (clientChatImp.LogIn(Password.getText()))
                        {
                            doit(clientChatImp);
                        }
                        else
                        {
                            Password.setText("Wrong Password");
                        }
                    } catch (RemoteException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
    }
    private void doit(ClientChatImp clientChatImp) throws RemoteException {
        MainPanel mainPanel = new MainPanel(clientChatImp);
        this.dispose();
    }
}
