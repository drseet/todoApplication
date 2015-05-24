
package todoapplication;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class list_gui extends List{
    private JFrame frame;
    private JPanel panel;     
    private JList todo;             //list 1 
    private JList inprog;           //list 2 
    private JList done;             //list 3
    private JScrollPane sp_todo;    //scroll panes for lists  
    private JScrollPane sp_inprog;  
    private JScrollPane sp_done;    
    private JButton add_task;       
    private JButton move_ip;        //move task to in progress list
    private JButton move_done;      //" " done list
    private JButton del_task;       //remove task from done list
    private JButton quit;           //quit application
    private JButton logout;         //quit to login screen
    private Font f;                 //main font
    private Font f_list;            //font for list elements
    private JLabel todo_label;     
    private JLabel inprog_label;
    private JLabel done_label;
    private addtask_gui atobj;      //for accessing add task window
    
    private String t_elems[];     
    private String ip_elems[];
    private String d_elems[];
    
    /*initializes, sets appearance, populates the 3 lists*/
    protected void populate_list(String username, int list){
        if(list == 1){
            //get list elements
            int size = todo_count(username);
            t_elems = new String[size];
            t_elems = load_todo(username);
            
            //create and populate list
            todo = new JList(t_elems);
            todo.setFont(f_list);
             
            //set scroll pane
            sp_todo = new JScrollPane(todo);
            sp_todo.setBounds(150, 30, 250, 325);
        }
        if(list == 2){
            //get list elements
            int size = inprog_count(username);
            ip_elems = new String[size];
            ip_elems = load_inprog(username);
            
            //create and populate list
            inprog = new JList(ip_elems);
            inprog.setFont(f_list);
            
            //set scroll pane
            sp_inprog = new JScrollPane(inprog);
            sp_inprog.setBounds(430, 30, 250, 325);
        }
        if(list == 3){
            int size = done_count(username);
            d_elems = new String[size];
            d_elems = load_completed(username);
            
            done = new JList(d_elems);
            done.setFont(f_list);
            
            sp_done = new JScrollPane(done);
            sp_done.setBounds(710, 30, 250, 325);
        }
    }
    protected void lists(final String username){
        atobj = new addtask_gui();
        f = new Font("arial", Font.BOLD, 10);
        f_list = new Font("courier", Font.PLAIN, 11);
        
        //initialize frame and panel
        frame = new JFrame("Task Manager");
        panel = new JPanel();
        
        //set labels
        todo_label = new JLabel("TO DO:");
        todo_label.setBounds(250, 10, 50, 20);
        todo_label.setFont(f);
        inprog_label = new JLabel("IN PROGRESS:"); 
        inprog_label.setBounds(530, 10, 80, 20);
        inprog_label.setFont(f);   
        done_label = new JLabel("DONE:");
        done_label.setBounds(810, 10, 50, 20);
        done_label.setFont(f);
        
        //set buttons 
        add_task = new JButton("Add a Task");
        add_task.setFont(f);
        add_task.setBounds(10,30,100,20);
        move_ip = new JButton("Mark as IN PROGRESS");
        move_ip.setFont(f);
        move_ip.setBounds(150, 360, 250, 20);
        move_done = new JButton("Mark as DONE");
        move_done.setFont(f);
        move_done.setBounds(430, 360, 250, 20);
        del_task = new JButton("DELETE Task");
        del_task.setFont(f);
        del_task.setBounds(710, 360, 250, 20);
        quit = new JButton("QUIT");
        quit.setFont(f);
        quit.setBounds(10, 360, 100, 20);
        logout = new JButton("LOGOUT");
        logout.setFont(f);
        logout.setBounds(10, 330, 100, 20);
        
        panel.setLayout(null);
        for(int i=1;i<=3;++i)
            populate_list(username, i);
        
        //set button actions
        add_task.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                atobj.addtask_window(username);
                frame.setVisible(false);
            }
        });
        move_ip.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String selected = todo.getSelectedValue().toString();
                set_inprog(username, selected);
                
                frame.setVisible(false);          //change these so it doesnt reload
                lists(username);
            }
        });
        move_done.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String selected = inprog.getSelectedValue().toString();
                set_done(username, selected);
                
                                      
                frame.setVisible(false);          //change these so it doesnt reload
                lists(username);

            }
        });
        del_task.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String selected = done.getSelectedValue().toString();
                remove_task(username, selected);
                
                frame.setVisible(false);         //change dis
                lists(username);
            }
        });
        quit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        logout.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                login_gui obj = new login_gui();
                obj.login();
                frame.setVisible(false);
            }
        });
        
        //add components to panel
        panel.add(sp_todo);
        panel.add(sp_inprog);
        panel.add(sp_done);
        panel.add(add_task);
        panel.add(todo_label);
        panel.add(inprog_label);
        panel.add(done_label);
        panel.add(move_ip);
        panel.add(move_done);
        panel.add(del_task);
        panel.add(quit);
        panel.add(logout);
        
        frame.getContentPane().add(panel);
        frame.setSize(1000, 450);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
    }
}
