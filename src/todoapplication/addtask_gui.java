
package todoapplication;

import javax.swing.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class addtask_gui {
    private JFrame frame;
    private JPanel panel;
    private JTextField task_field;
    private JButton add;
    private JButton cancel;
    private JLabel add_label;
    private Font f;
    private list_gui lobj;

    protected void addtask_window(final String username){
        lobj = new list_gui();
        f = new Font("arial", Font.BOLD, 11);
        frame = new JFrame("Add New Task");
        panel = new JPanel();
        panel.setLayout(null);
        
        //init frame components
        task_field = new JTextField(145);
        add = new JButton("ADD TASK");
        cancel = new JButton("CANCEL");
        add_label = new JLabel("Enter new task to add:");
        
        //set frame component layout
        task_field.setBounds(25,55,300,20);
        add.setBounds(25,140,100,20);
        add.setFont(f);      
        cancel.setBounds(220,140,100,20);
        cancel.setFont(f);
        add_label.setBounds(25, 25, 180, 20);
        add_label.setFont(f);
        
        //set button actions
        add.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String newtask = task_field.getText();
                if(newtask != null){
                    lobj.create_task(username, newtask);
                    frame.setVisible(false);
                    lobj.lists(username);
                }
            }
        });
        cancel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                lobj.lists(username);
                frame.setVisible(false);
            }
        });
        
        //add components to panel
        panel.add(task_field);
        panel.add(add);
        panel.add(cancel);
        panel.add(add_label);
        
        //add panel to frame, init frame
        frame.getContentPane().add(panel);
        frame.setLocation(500,280);
        frame.setVisible(true);
        frame.setSize(360, 220);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
