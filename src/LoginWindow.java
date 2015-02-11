import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
/*
 * Created by JFormDesigner on Tue May 20 13:51:25 MSK 2014
 */



public class LoginWindow extends JFrame {

    public LoginWindow() {
        initComponents();
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        panel1 = new JPanel();
        label1 = new JLabel();
        loginTextField = new JTextField();
        panel2 = new JPanel();
        label2 = new JLabel();
        passwordTextField = new JTextField();
        panel3 = new JPanel();
        loginButton = new JButton();
        label3 = new JLabel();

        //======== this ========
        setTitle("QPrint");
        setResizable(false);
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setLayout(new BorderLayout(0, 5));

            //======== contentPanel ========
            {
                contentPanel.setPreferredSize(new Dimension(80, 79));
                contentPanel.setMinimumSize(new Dimension(80, 79));
                contentPanel.setMaximumSize(new Dimension(80, 80));
                contentPanel.setBorder(new LineBorder(Color.lightGray, 1, true));
                contentPanel.setLayout(new GridLayout(3, 1, 6, 4));

                //======== panel1 ========
                {
                    panel1.setBorder(null);
                    panel1.setMaximumSize(new Dimension(171, 30));
                    panel1.setLayout(new FlowLayout(FlowLayout.CENTER, 27, 5));

                    //---- label1 ----
                    label1.setText("\u041b\u043e\u0433\u0438\u043d");
                    label1.setHorizontalAlignment(SwingConstants.CENTER);
                    label1.setAlignmentY(4.5F);
                    label1.setPreferredSize(new Dimension(37, 14));
                    label1.setMaximumSize(new Dimension(37, 14));
                    panel1.add(label1);

                    //---- loginTextField ----
                    loginTextField.setAlignmentY(4.5F);
                    loginTextField.setColumns(18);
                    loginTextField.setMinimumSize(new Dimension(60, 20));
                    loginTextField.setHorizontalAlignment(SwingConstants.CENTER);
                    loginTextField.setMaximumSize(new Dimension(60, 20));
                    loginTextField.setAutoscrolls(false);
                    loginTextField.setVerifyInputWhenFocusTarget(false);
                    loginTextField.setText("vertol");
                    panel1.add(loginTextField);
                }
                contentPanel.add(panel1);

                //======== panel2 ========
                {
                    panel2.setLayout(new FlowLayout(FlowLayout.CENTER, 9, 5));
                    ((FlowLayout)panel2.getLayout()).setAlignOnBaseline(true);

                    //---- label2 ----
                    label2.setText("\u041f\u0430\u0440\u043e\u043b\u044c");
                    label2.setHorizontalAlignment(SwingConstants.CENTER);
                    label2.setAlignmentY(4.5F);
                    panel2.add(label2);

                    //---- passwordTextField ----
                    passwordTextField.setAlignmentY(4.5F);
                    passwordTextField.setMinimumSize(new Dimension(60, 20));
                    passwordTextField.setColumns(18);
                    passwordTextField.setHorizontalAlignment(SwingConstants.CENTER);
                    passwordTextField.setText("vertol1337");
                    panel2.add(passwordTextField);
                }
                contentPanel.add(panel2);

                //======== panel3 ========
                {
                    panel3.setLayout(new FlowLayout());
                    ((FlowLayout)panel3.getLayout()).setAlignOnBaseline(true);

                    //---- loginButton ----
                    loginButton.setText("\u0412\u043e\u0439\u0442\u0438");
                    loginButton.setAlignmentY(4.5F);
                    loginButton.setFont(loginButton.getFont().deriveFont(Font.PLAIN, loginButton.getFont().getSize() + 3f));
                    panel3.add(loginButton);
                }
                contentPanel.add(panel3);
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //---- label3 ----
            label3.setText("\u041f\u0440\u043e\u0439\u0434\u0438\u0442\u0435 \u0430\u0432\u0442\u043e\u0440\u0438\u0437\u0430\u0446\u0438\u044e.");
            label3.setHorizontalAlignment(SwingConstants.CENTER);
            label3.setFont(new Font("Tahoma", Font.BOLD, 13));
            dialogPane.add(label3, BorderLayout.NORTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        setSize(225, 240);
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JPanel panel1;
    private JLabel label1;
    private JTextField loginTextField;
    private JPanel panel2;
    private JLabel label2;
    private JTextField passwordTextField;
    private JPanel panel3;
    private JButton loginButton;
    private JLabel label3;
    // JFormDesigner - End of variables declaration  //GEN-END:variables


    public JButton getLoginButton() {
        return loginButton;
    }

    public String getLogin() {
        return loginTextField.getText();
    }

    public String getPassword() {
        return passwordTextField.getText();
    }

    public  void setDefUserPassPair(String defaultUser, String defaultPass) {
        if (loginTextField != null&&passwordTextField!=null) {
            loginTextField.setText(defaultUser);
            passwordTextField.setText(defaultPass);
        }
    }
}
