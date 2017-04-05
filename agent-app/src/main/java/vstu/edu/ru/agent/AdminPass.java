package vstu.edu.ru.agent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import javax.swing.WindowConstants;
import javax.swing.SwingUtilities;


public class AdminPass extends javax.swing.JFrame {
    private JPasswordField jPasswordField1;
    private JButton jButton1;
    private JPanel jPanel3;
    private JPanel jPanel2;
    private JPanel jPanel1;
    private JLabel jLabel1;

    /**
     * Auto-generated main method to display this JFrame
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                AdminPass inst = new AdminPass();
                inst.setLocationRelativeTo(null);
                inst.setVisible(true);
            }
        });
    }

    public AdminPass() {
        super();
        initGUI();
    }

    private void initGUI() {
        try {
            BoxLayout thisLayout = new BoxLayout(getContentPane(), javax.swing.BoxLayout.Y_AXIS);
            getContentPane().setLayout(thisLayout);
            setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            {
                jPanel2 = new JPanel();
                getContentPane().add(jPanel2);
                jPanel2.setPreferredSize(new java.awt.Dimension(283, 39));
                {
                    jLabel1 = new JLabel();
                    jPanel2.add(jLabel1);
                    jLabel1.setText("Enter administrator password: ");
                }
            }
            {
                jPanel1 = new JPanel();
                getContentPane().add(jPanel1);
                jPanel1.setPreferredSize(new java.awt.Dimension(392, 42));
                {
                    jPasswordField1 = new JPasswordField();
                    jPanel1.add(jPasswordField1);
                    jPasswordField1.setText("jPasswordField1");
                    jPasswordField1.setPreferredSize(new java.awt.Dimension(243, 21));
                }
            }
            {
                jPanel3 = new JPanel();
                getContentPane().add(jPanel3);
                jPanel3.setPreferredSize(new java.awt.Dimension(292, 32));
                {
                    jButton1 = new JButton();
                    jPanel3.add(jButton1);
                    jButton1.setText("Exit");
                    jButton1.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                            System.exit(0);
                        }
                    });
                }
            }
            pack();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
