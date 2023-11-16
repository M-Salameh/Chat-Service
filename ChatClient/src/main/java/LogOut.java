import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;

public class LogOut extends JFrame{
    private JPanel LoggingOut;
    private JButton LogOut;

    public LogOut(ClientChatImp clientChatImp) {
        setVisible(true);
        try {
            setTitle("Log Out( " + clientChatImp.getUserName()+" )");
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        setContentPane(LoggingOut);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600 , 600);

        LogOut.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    if (clientChatImp.LogOut())
                    {
                        doit(clientChatImp);
                    }
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
    private void doit (ClientChatImp clientChatImp)
    {
        LogIN logIN = new LogIN(clientChatImp);
        this.dispose();
    }
}
