
package todoapplication;

public class User extends DB_util{
    
   protected int create_user(String username, String password){
       if(find_user(username, password))
           return 0;
       else{
           add_user(username, password);
           return 1;
       }    
   }
   protected void remove_user(String username){
       delete_user(username);
   }
   
   protected int user_match(String username, String password){   //&& pass_match
       if(find_user(username, password))
           return 1;
       else
           return 0;
       
   //if user match, run list gui
   }
   
   
}
