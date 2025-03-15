package Register;

import java.io.*;
import java.util.List;
import java.util.ArrayList;
public class UserManager {
    private static final String FILE_PATH =  "Users.txt";

    //save users data
    public static void saveUsers(List<User> users){
        try(FileWriter Writer = new FileWriter(FILE_PATH,true)){
            for(User user : users){
                Writer.write(user.getUserName() + "," + user.getPassword() + ","
                + user.getScore() + System.lineSeparator());
            }
        } catch(IOException e){e.toString();}
    }

    //read user data
    public static List<User> LoadUsers(){
        List<User> users = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))){
            String Line;
            while((Line = reader.readLine()) != null){
                String[] parts =  Line.split(",");
                User user = new User(parts[0],parts[1]);
                users.add(user);
            }
        } catch(IOException e){e.toString();}

        return users;
    }







}
