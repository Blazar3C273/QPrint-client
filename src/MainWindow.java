import org.apache.http.Header;
import org.json.simple.parser.ParseException;

import javax.security.auth.login.LoginException;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Queue;
/*
 * Created by JFormDesigner on Sun Apr 13 09:51:16 MSK 2014
 */


public class MainWindow extends JFrame {

    private static final int WAIT_DELAY = 5000;

    public static Thread mainLoopThread;

    public static void main(String[] args) {
        new MainWindow().setVisible(true);
    }

    private Queue<Job> jobQueue;
    private Header cookie;

    public MainWindow() {
        initComponents();
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void thisWindowOpened(WindowEvent e) {
        this.setVisible(false);
        Server.loadSettingsFromFile();
        final LoginWindow loginWindowInstance = new LoginWindow();
        loginWindowInstance.setDefUserPassPair(Server.DEFAULT_USER,Server.DEFAULT_PASS);
        loginWindowInstance.setVisible(true);
        loginWindowInstance.getLoginButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    cookie = Server.login(loginWindowInstance.getLogin(), loginWindowInstance.getPassword());
                    if (cookie == null) throw new LoginException("Неизвестная ошибка. ");
                    JOptionPane.showMessageDialog(loginWindowInstance, "Вход успешно выполнен.", "Сообщение.", JOptionPane.DEFAULT_OPTION);
                    loginWindowInstance.setVisible(false);
                    onSuccessLogin(cookie);
                } catch (LoginException e1) {
                    JOptionPane.showMessageDialog(loginWindowInstance, e1.getMessage(), "Упс! Какая-то ошибка.", JOptionPane.DEFAULT_OPTION);
                    e1.printStackTrace();
                }

            }
        });
    }

    private void onSuccessLogin(final Header _cookie) {
        this.setVisible(true);
        panel1.removeAll();
        panel1.updateUI();
        mainLoopThread = new Thread(new Runnable() {
            @Override
            public void run() {
                startMainLoop(_cookie);
            }
        });
        mainLoopThread.start();
    }

    private void startMainLoop(Header cookie) {
        String lastCardDescription = "Последний исполненый заказ";
        String nextCardDescription = "Следующий заказ";
        String currentCardDescription = "Текущий заказ";
        JPanel lastCard,currentCard,nextCard;
        Job currentJob = null;
        Job nextJob = null;
        Job lastJob = null;
        while (true) {
            while (jobQueue == null || jobQueue.isEmpty()) {
                panel1.removeAll();
                {
                    panel1.setBorder(new EtchedBorder(EtchedBorder.RAISED));
                    panel1.setLayout(new GridLayout(1, 3, 5, 4));
                    panel1.add(hSpacer2);

                    //---- label1 ----
                    label1.setText(" \u041e\u0447\u0435\u0440\u0435\u0434\u044c \u043f\u0435\u0447\u0430\u0442\u0438 \u043f\u043e\u043a\u0430 \u043f\u0443\u0441\u0442\u0430.");
                    label1.setFont(label1.getFont().deriveFont(label1.getFont().getStyle() | Font.BOLD, 16f));
                    label1.setHorizontalAlignment(SwingConstants.CENTER);
                    panel1.add(label1);
                    panel1.add(hSpacer1);
                }
                panel1.updateUI();
                try {
                    mainLoopThread.join(WAIT_DELAY);
                    jobQueue = Server.getJobQueue(cookie);
                } catch (IOException | ParseException | InterruptedException e) {
                    e.printStackTrace();
                }
            }

            panel1.removeAll();
            statusLabel.setText("Статус: Количество заказов - " + String.valueOf(jobQueue.size()));

            if (currentJob == null) {
                currentJob = jobQueue.poll();

            }
            if (!jobQueue.isEmpty()) {
                nextJob = jobQueue.poll();
            } else nextJob = null;

            lastCard = JobCardFactory.getJobCard(lastJob, lastCardDescription);
            currentCard = JobCardFactory.getJobCard(currentJob, currentCardDescription);
            nextCard = JobCardFactory.getJobCard(nextJob, nextCardDescription);

            panel1.add(lastCard);
            panel1.add(currentCard);
            panel1.add(nextCard);

            panel1.updateUI();
            try {
                MainWindow.mainLoopThread.join();
            } catch (InterruptedException ignored) {

            }
            lastJob = currentJob;
            currentJob = nextJob;
            nextJob = null;
            statusLabel.setText("Статус: Количество заказов - " + String.valueOf(jobQueue.size()));
            Component[] components = currentCard.getComponents();
            for (Component component : components) {
                if (component.getClass().equals(JButton.class)&& ((JButton)component).getText().equals(JobCardFactory.PAID_DOBUTTON_TEXT)){
                    ((JButton)component).getActionListeners()[0].actionPerformed(null);
                }
            }

        }
    }

    private void thisWindowClosed(WindowEvent e) {

    }


    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        panel1 = new JPanel();
        hSpacer2 = new JPanel(null);
        label1 = new JLabel();
        hSpacer1 = new JPanel(null);
        panel3 = new JPanel();
        statusLabel = new JLabel();

        //======== this ========
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                thisWindowOpened(e);
            }
        });
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== panel1 ========
        {
            panel1.setBorder(new EtchedBorder(EtchedBorder.RAISED));
            panel1.setLayout(new GridLayout(1, 3, 5, 4));
            panel1.add(hSpacer2);

            //---- label1 ----
            label1.setText(" \u041e\u0447\u0435\u0440\u0435\u0434\u044c \u043f\u0435\u0447\u0430\u0442\u0438 \u043f\u043e\u043a\u0430 \u043f\u0443\u0441\u0442\u0430.");
            label1.setFont(label1.getFont().deriveFont(label1.getFont().getStyle() | Font.BOLD, label1.getFont().getSize() + 6f));
            label1.setHorizontalAlignment(SwingConstants.CENTER);
            panel1.add(label1);
            panel1.add(hSpacer1);
        }
        contentPane.add(panel1, BorderLayout.CENTER);

        //======== panel3 ========
        {
            panel3.setLayout(new BorderLayout(3, 0));

            //---- statusLabel ----
            statusLabel.setText("\u0421\u0442\u0430\u0442\u0443\u0441");
            statusLabel.setFont(statusLabel.getFont().deriveFont(statusLabel.getFont().getSize() + 5f));
            panel3.add(statusLabel, BorderLayout.WEST);
        }
        contentPane.add(panel3, BorderLayout.SOUTH);
        setSize(920, 435);
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel panel1;
    private JPanel hSpacer2;
    private JLabel label1;
    private JPanel hSpacer1;
    private JPanel panel3;
    private JLabel statusLabel;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

}
