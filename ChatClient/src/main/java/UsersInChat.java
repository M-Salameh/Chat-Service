import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UsersInChat extends JFrame{
    private JButton GOBACK;
    private JTextField Message;
    private JButton MultiCast;
    private JPanel buttonPanel;
    private JScrollPane scrollPane;
    List<IClientChat> selected = new ArrayList<>();

    public UsersInChat(ClientChatImp me , String room)
    {
        System.out.println("CCCCCCCC");
        try {
            setTitle(me.getUserName());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        /*setContentPane(MyChatsPanel);
        setVisible(true);*/
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600 , 600);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0, 1));
        List<IClientChat> list = null;
        try {
            list = me.getAllUsersInRoom(room);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        for (IClientChat str : list) {
            JButton button = null;
            String nme = "";
            try {
                button = new JButton(str.getUserName());
                nme = button.getText();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
            String finalNme = nme;
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e)
                {

                    int choice = JOptionPane.showConfirmDialog(null , "Select "+ finalNme + " as receiver ?");
                    if (choice == 0)
                    {
                        if (!selected.contains(str)) selected.add(str);
                    }

                }
            });
            buttonPanel.add(button);
        }

        GOBACK.addMouseListener(new MouseAdapter() {
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
        buttonPanel.add(GOBACK);
        Message.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Message.setText("");
            }
        });
        buttonPanel.add(Message);
        MultiCast.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (!Message.getText().isEmpty())
                {
                    try {
                        me.multiCastMessage(Message.getText() , selected);
                    } catch (RemoteException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        buttonPanel.add(MultiCast);
        scrollPane = new JScrollPane(buttonPanel);

        // Add the scroll pane to the main frame
        add(scrollPane, BorderLayout.CENTER);
        pack();
        setVisible(true);
    }
    private void goMain(ClientChatImp clientChatImp) throws RemoteException {
        MainPanel mainPanel= new MainPanel(clientChatImp);
        this.dispose();
    }
}
