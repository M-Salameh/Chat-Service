import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.util.List;

public class AllChatRooms extends JFrame {
    private JButton GOBack;
    private JPanel buttonPanel;
    private JScrollPane scrollPane;

    public AllChatRooms(ClientChatImp clientChatImp)
    {
        try {
            setTitle("All Chat Rooms ( " + clientChatImp.getUserName()+" )");
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600 , 600);

        // Create a list of strings
        List<String> stringList = null;
        try {
            stringList = clientChatImp.getAllChatRooms();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        // Create a panel to hold the buttons
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0, 1));

        // Create buttons for each string in the list
        for (String str : stringList) {
            JButton button = new JButton(str);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try
                    {
                        int choice = JOptionPane.showConfirmDialog(null , "Do You Want to join Room " + button.getText() + " ?");
                        if (choice == 0)
                        {
                            clientChatImp.joinChatRoom(button.getText());
                            doit(clientChatImp , button.getText());
                        }

                    } catch (RemoteException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
            buttonPanel.add(button);
        }
        buttonPanel.add(GOBack);
        // Create a scroll pane for the button panel
        scrollPane = new JScrollPane(buttonPanel);

        // Add the scroll pane to the main frame
        add(scrollPane, BorderLayout.CENTER);
        pack();
        setVisible(true);
        GOBack.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    gotoMain(clientChatImp);
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
    private void doit(ClientChatImp clientChatImp ,String roomname) throws RemoteException
    {
        MessagesInChat conversationInChat = new MessagesInChat(clientChatImp , roomname);
        this.dispose();
    }
    private void gotoMain(ClientChatImp clientChatImp) throws RemoteException {
        MainPanel mainPanel = new MainPanel(clientChatImp);
        this.dispose();
    }
}
