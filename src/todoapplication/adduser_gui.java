
package todoapplication;
import javax.swing.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class adduser_gui {
        
    private JFrame frame;
    private JPanel panel;    
    private JButton done_button;
    private JButton cancel_button; 
    private JTextField ufield;
    private JPasswordField pfield1;
    private JPasswordField pfield2;
    private JLabel enter_user;
    private JLabel enter_pass1;
    private JLabel enter_pass2;
    private login_gui uobj;
    private Font f_title;
    
    
    protected void adduser(){
        
        //initialize panel components
        frame = new JFrame("Create account");
        panel = new JPanel();
        ufield = new JTextField();
        pfield1 = new JPasswordField();
        pfield2 = new JPasswordField();
        enter_user = new JLabel("Enter a username:");
        enter_pass1 = new JLabel("Enter a password:");
        enter_pass2 = new JLabel("Re-enter password:");
        done_button = new JButton("DONE");
        cancel_button = new JButton("CANCEL");
        f_title = new Font("Courier", Font.BOLD, 12);        
        panel.setLayout(null);
        
        //set panel layout
        ufield.setBounds(25, 55, 300, 20);
        pfield1.setBounds(25, 125, 300, 20);
        pfield2.setBounds(25, 175, 300, 20);
        enter_user.setBounds(25,35,120,20);
        enter_user.setFont(f_title);
        enter_pass1.setBounds(25, 105, 130, 20);
        enter_pass1.setFont(f_title);
        enter_pass2.setBounds(25, 155, 130, 20);
        enter_pass2.setFont(f_title);
        done_button.setBounds(25,240,100,20);
        done_button.setFont(f_title);        
        cancel_button.setBounds(220,240,100,20);
        cancel_button.setFont(f_title);        
        
        //add components to panel
        panel.add(ufield);
        panel.add(pfield1);
        panel.add(pfield2);
        panel.add(enter_user);
        panel.add(enter_pass1);
        panel.add(enter_pass2);
        panel.add(done_button);
        panel.add(cancel_button);
        
        //add panel to frame
        frame.getContentPane().add(panel);        
        
        //set behavior for panel components
        done_button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                char[] pw;
                uobj = new login_gui();
                String username = ufield.getText();
                if(Arrays.equals(pfield1.getPassword(), pfield2.getPassword())){
                    pw = pfield1.getPassword();
                    String pass = new String(pw);
                    uobj.create_user(username, pass);
                }
                uobj.login();
                frame.setVisible(false);
            }
        });
        cancel_button.addActionListener(new ActionListener() {
        
            @Override
            public void actionPerformed(ActionEvent e){
            uobj = new login_gui();
            uobj.login();
            frame.setVisible(false);
            }
        });
        
        //set appearance and behavior of frame
        frame.setVisible(true);
        frame.setSize(360, 320);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
