
package todoapplication;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class login_gui extends User {
    
    JFrame frame;
    JPanel upanel;    
    JButton login_button;
    JButton cancel_button;
    JButton add_user; 
    JTextField ufield;
    JPasswordField pfield;
    JLabel enter_user;
    JLabel enter_pass;   
    JLabel login_error;
    list_gui lobj;
    Font f;
    Font f_title;
   
    protected void login(){
        f = new Font("Carlito", Font.BOLD, 11);
        f_title = new Font("Courier", Font.BOLD, 12);
        
        //initialize frame and panel
        frame = new JFrame("Login");
        upanel = new JPanel();
        upanel.setLayout(null);   //allow for bounds to be manually set
        
        //initialize buttons, text fields, label
        ufield = new JTextField(15);
        ufield.setEditable(true);
        pfield = new JPasswordField(15);
        pfield.setEditable(true);
        login_button = new JButton("LOGIN");
        cancel_button = new JButton("EXIT");
        enter_user = new JLabel("Username:");
        enter_pass = new JLabel("Password:");
        login_error = new JLabel("Username/Password not found!");
        add_user = new JButton("Create Account");
        
        //set format and layout of frame components
        ufield.setBounds(25,55,300,20);
        pfield.setBounds(25, 125, 300, 20);
        
        login_button.setBounds(25,240,100,20);
        login_button.setFont(f_title);
        
        cancel_button.setBounds(220,240,100,20);
        cancel_button.setFont(f_title);
        
        enter_user.setBounds(25, 25, 120, 20);
        enter_user.setFont(f_title);
        
        enter_pass.setBounds(25, 100, 120, 20);
        enter_pass.setFont(f_title);
        
        login_error.setBounds(25,200,200,20);
        login_error.setFont(f_title);
        login_error.setForeground(Color.RED);
        login_error.setVisible(false);
        
        add_user.setBounds(225,155,120,20);
        add_user.setOpaque(false);
        add_user.setContentAreaFilled(false);
        add_user.setBorderPainted(false);
        add_user.setFont(f);
        
        //add components to panel
        upanel.add(ufield);
        upanel.add(pfield);
        upanel.add(login_button);
        upanel.add(cancel_button);
        upanel.add(enter_user);
        upanel.add(enter_pass);
        upanel.add(add_user);
        upanel.add(login_error);
        
        //add panel to frame
        frame.getContentPane().add(upanel);
        
        //set behavior of panel components       
        login_button.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e){
            lobj = new list_gui();
            String entry = ufield.getText();
            String pass = pfield.getText();
            
            if(user_match(entry, pass) == 1){
                frame.setVisible(false);    
                lobj.lists(entry);
            }
            else
                login_error.setVisible(true);
            }
        });
        cancel_button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (login_gui.this.conn != null) {
                    login_gui.this.close_db();
                }
                System.exit(0);
            }
        });
        add_user.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                adduser_gui obj = new adduser_gui();
                obj.adduser();
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
