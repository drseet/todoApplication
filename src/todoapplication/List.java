
package todoapplication;
public class List extends DB_util{

    protected int create_task(String username, String task){
        if(username == null || task == null)
            return 0;
        else{
            add_task(username, task);
            return 1;
        }
    }
    protected void remove_task(String username, String task){
        delete_task(username, task);
    }
               
}