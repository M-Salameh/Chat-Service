import sun.java2d.Disposer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Registeration extends JFrame
{
    private ClientChatImp clientChatImp;
    private JTextField FirstName;
    private JTextField LastName;
    private JTextField UserName;
    private JTextField Password;
    private JButton register;
    private JPanel panelChat;

    private String server="";
    public Registeration(String server)
    {
        this.server = server;
        setContentPane(panelChat);
        setVisible(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setSize(600 , 600);
        setTitle("Mohammed Salameh Telegram");

        FirstName.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                FirstName.setText("");
            }
        });
        LastName.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                LastName.setText("");
            }
        });
        UserName.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                UserName.setText("");
            }
        });
        Password.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Password.setText("");
            }
        });
        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean cont  = checkInputs();
                if (!cont)
                {
                    FirstName.setText("Should not be Empty");
                    LastName.setText("Should not be Empty");
                    UserName.setText("Should not be Empty");
                    Password.setText("Should not be Empty");
                }
                else {
                    try {
                        startAll();
                    } catch (RemoteException ex) {
                        throw new RuntimeException(ex);
                    } catch (MalformedURLException ex) {
                        throw new RuntimeException(ex);
                    } catch (NotBoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
    }

    private boolean checkInputs()
    {
        if(UserName.getText().isEmpty() || LastName.getText().isEmpty() || Password.getText().isEmpty() || FirstName.getText().isEmpty())
        {
            return false;
        }
        return true;
    }
    public void startAll() throws RemoteException, MalformedURLException, NotBoundException {
        this.clientChatImp = new ClientChatImp
                (FirstName.getText() , LastName.getText() , UserName.getText() , Password.getText() , server);

        this.clientChatImp.Register();
        MainPanel mainPanel = new MainPanel(this.clientChatImp);
        this.dispose();
    }
}
