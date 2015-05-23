package todoapplication;
import java.sql.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DB_util extends Util {
    
    protected Connection conn;
    private ResultSet res;
    private PreparedStatement pstmt;
    private MessageDigest md;
    private DatabaseMetaData dmd;
      
    protected void setDBSystemDir() {
        String userHomeDir = System.getProperty("user.home", ".");
        String systemDir = userHomeDir + "/.todoApp";

        // Set the db system directory
        System.setProperty("derby.system.home", systemDir);
    }

    protected void conn_db() {      
        try{
            setDBSystemDir();
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");   
            conn = DriverManager.getConnection("jdbc:derby:todoDB;create=true;");
            createTables();
        }catch(SQLException | ClassNotFoundException se){
            se.printStackTrace(System.out);
        }
    }    
    protected void createTables(){
        
        String t1 =  "create table tasks(username varchar(25), task varchar(145), "
                     + "initiated boolean DEFAULT FALSE NOT NULL, completed boolean DEFAULT FALSE NOT NULL)";
        
        String t2 = "create table users(username varchar(75) NOT NULL PRIMARY KEY, "
                                                         +"pword varchar(75) NOT NULL)";
        try{
            if(conn == null)
                conn_db();
            pstmt = conn.prepareStatement(t1);
            pstmt.executeUpdate();
            pstmt = conn.prepareStatement(t2);
            pstmt.executeUpdate();
            
        }catch(SQLException se){}
    }
    
    protected int todo_count(String username){
        int count = 0;   
        String s = "select task, initiated, completed from tasks where username = ?";
        
        try{
            if(conn == null)
                conn_db();
            pstmt = conn.prepareStatement(s);
            pstmt.setString(1, username);
            res = pstmt.executeQuery();
            while(res.next())
                if((res.getBoolean("initiated") == false) && (res.getBoolean("completed") == false))
                    count++;
               
           }catch(SQLException se){
              se.printStackTrace(System.out);
        }
        return count;
    }
    protected int inprog_count(String username){
        int count = 0;   
        String s = "select task, initiated, completed from tasks where username = ?";
        
        try{
            if(conn == null)
                conn_db();
            pstmt = conn.prepareStatement(s);
            pstmt.setString(1, username);
            res = pstmt.executeQuery();
            while(res.next())
                if((res.getBoolean("initiated") == true) && (res.getBoolean("completed") == false))
                    count++;
               
           }catch(SQLException se){
              se.printStackTrace(System.out);
        }
        return count;
    }
    protected int done_count(String username){
        int count = 0;   
        String s = "select task, initiated, completed from tasks where username = ?";
        
        try{
            if(conn == null)
                conn_db();
            pstmt = conn.prepareStatement(s);
            pstmt.setString(1, username);
            res = pstmt.executeQuery();
            while(res.next())
                if((res.getBoolean("initiated") == true) && (res.getBoolean("completed") == true))
                    count++;
               
           }catch(SQLException se){
              se.printStackTrace(System.out);
        }
        return count;
    }
    protected int task_count(String username){
        int count = 0;
        String s = "select task, initiated, completed from tasks where username = ?";
        
        try{
            if(conn == null)
                conn_db();
            pstmt = conn.prepareStatement(s);
            pstmt.setString(1, username);
            res = pstmt.executeQuery();
            while(res.next())
                count++;
               
          }catch(SQLException se){
              se.printStackTrace(System.out);
        }
        return count;
    }
    protected String[] load_inprog(String username){
        String inprog[];
        int count = task_count(username);
        inprog = new String[count];
        int i = 0;
        String s = "select task, initiated, completed from tasks where username = ?";
        
        try{
            if(conn == null)
                conn_db();
            pstmt = conn.prepareStatement(s);
            pstmt.setString(1, username);
            res = pstmt.executeQuery();
            while(res.next()){
                if((res.getBoolean("initiated") == true) && (res.getBoolean("completed") == false)){
                    inprog[i] = res.getString("task");
                    ++i;
                }
            }
        }catch(SQLException se){
            se.printStackTrace(System.out);
        }
    return inprog;
    }
    protected String[] load_todo(String username){
        String todo[];
        int count = task_count(username);
        todo = new String[count];
        int i = 0;
        String s = "select task, initiated, completed from tasks where username = ?";
        
        try{
            if(conn == null)
                conn_db();
            pstmt = conn.prepareStatement(s);
            pstmt.setString(1, username);
            res = pstmt.executeQuery();
            while(res.next()){
                if((res.getBoolean("initiated") == false) && (res.getBoolean("completed") == false)){
                    todo[i] = res.getString("task");
                    ++i;
                }
            }
        }catch(SQLException se){
            se.printStackTrace(System.out);
        }
    return todo;
    }
    protected String[] load_completed(String username){
        String complete[];
        int count = task_count(username);
        complete = new String[count];
        int i = 0;
        String s = "select task, initiated, completed from tasks where username = ?";
        
        try{
            if(conn == null)
                conn_db();
            pstmt = conn.prepareStatement(s);
            pstmt.setString(1, username);
            res = pstmt.executeQuery();
            while(res.next()){
                if((res.getBoolean("initiated") == true) && (res.getBoolean("completed") == true)){
                    complete[i] = res.getString("task");
                    ++i;
                }
            }
        }catch(SQLException se){
            se.printStackTrace(System.out);
        }
    return complete;
    }

    protected void set_inprog(String username, String task){
        try{
            String s = "update tasks set initiated = true where task like (?)"
                                                     + "and username like (?)";
            if(conn == null)
                conn_db();
            pstmt = conn.prepareStatement(s);
            pstmt.setString(1, task);
            pstmt.setString(2, username);
            pstmt.executeUpdate();       
        }catch(SQLException se){
            se.printStackTrace(System.out);
        }
       
    }
    protected void set_done(String username, String task){
        try{
            String s = "update tasks set completed = true where task like (?)"
                                                + "and username like (?)";
            if(conn == null)
                conn_db();
            pstmt = conn.prepareStatement(s);
            pstmt.setString(1, task);
            pstmt.setString(2, username);
            pstmt.executeUpdate();       
            
        }catch(SQLException se){
            se.printStackTrace(System.out);
        }        
    }
    protected String pass_hash(String username, String pass){
        try{
            md = MessageDigest.getInstance("SHA-256");

            md.update(pass.getBytes());
            byte result[] = md.digest();
            StringBuilder sb = new StringBuilder();
            
            for(int i = 0; i< result.length; ++i)
                sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
            return sb.toString();
        }catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace(System.out);
        }
        return null;
    }
    protected void add_user(String username, String password){
        try{
            if(conn == null)
                conn_db();
            String pass = pass_hash(username, password);
            String s = "insert into users values (?,?)";
            pstmt = conn.prepareStatement(s);
            pstmt.setString(1, username);
            pstmt.setString(2, pass);
            pstmt.executeUpdate();
        }catch(SQLException se){
            se.printStackTrace(System.out);
        }
    }
    protected boolean find_user(String username, String password){
        try{
            String pass = pass_hash(username, password);
            String s = "select * from users where username = ? and pword = ?";
            
            if(conn == null)
                conn_db();
            pstmt = conn.prepareStatement(s);
            pstmt.setString(1, username);
            pstmt.setString(2, pass);
            res = pstmt.executeQuery();
            
            while(res.next())
                if((res.getString("username").equalsIgnoreCase(username)))
                    if(res.getString("pword").equals(pass))
                        return true;
        }catch(SQLException se){
            se.printStackTrace(System.out);
        }
        return false;
    }
    protected void delete_user(String username){
        try{
            String s = "delete from users where username = ?";
            pstmt = conn.prepareStatement(s);
            pstmt.setString(1, username);
            pstmt.executeUpdate();
        }catch(SQLException se){
            se.printStackTrace(System.out);
        }
        
    }
    protected boolean user_exists(String username){
        try{
            if(conn == null)
                conn_db();
            String s = "select * from users";
            pstmt = conn.prepareStatement(s);
            res = pstmt.executeQuery();
            while(res.next())
                if(res.getString("username").equals(username))
                    return true;
        }catch(SQLException se){}
    return false;
    }
    protected void add_task(String username, String task){
        try{
            if(conn == null)
                conn_db();
            String s = "insert into tasks(username, task, initiated, completed) values (?,?,false, false)";
            pstmt = conn.prepareStatement(s);
            pstmt.setString(1, username);
            pstmt.setString(2, task);
            pstmt.executeUpdate();

        }catch(SQLException se){
            se.printStackTrace(System.out);
        }catch(Exception e){
            e.printStackTrace(System.out);
        }
    }
    protected void delete_task(String username, String task){
        try{
            if(conn == null)
                conn_db();
            String s = "delete from tasks where username = ? and task like ?";
            pstmt = conn.prepareStatement(s);
            pstmt.setString(1, username);
            pstmt.setString(2, task);
            pstmt.executeUpdate();

        }catch(SQLException se){
            se.printStackTrace(System.out);
        }catch(Exception e){
            e.printStackTrace(System.out);
        }
    }
    protected void close_db(){
        try{
            conn.close();
        }catch(SQLException se){
            se.printStackTrace(System.out);
        }
    }
    
}