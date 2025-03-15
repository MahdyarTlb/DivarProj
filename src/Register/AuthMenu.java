package Register;

import java.util.List;
import java.util.Scanner;

public class AuthMenu {
    public static void main(String[] args) {
        List<User> users = UserManager.LoadUsers();
        Scanner scanner =  new Scanner(System.in);

        while(true){
            System.out.println("1. login \n2. register \n3. exit");
            System.out.print("Enter your choice : ");
            int choice = scanner.nextInt();
            scanner.nextLine(); //clear buffer
            switch(choice){
                case 1:
                    System.out.println("Enter your username : ");
                    String userName = scanner.nextLine();
                    System.out.println("Enter your password : ");
                    String password = scanner.nextLine();
                    for(User user : users){
                        if(user.getUserName().equalsIgnoreCase(userName) && user.getPassword().equals(password)){
                            System.out.println("Hi " + userName + " !");
                            UserManager.saveUsers(users);
                            return;
                        }
                        System.out.println("Your userName or password is incorrect! try again");
                    }
                    continue;
                case 2:
                    System.out.print("Enter your UserName : ");
                    String UserName = scanner.nextLine();
                    System.out.print("Enter your Password : ");
                    String Password = scanner.nextLine();
                    User user = new User(UserName, Password); //make new user
                    users.add(user);
                    UserManager.saveUsers(users);
                    System.out.println("--------------------\n" +
                    "User has been saved successfully\nyou must login\n--------------------");
                    UserManager.saveUsers(users);
                    break;
                case 3:
                    System.out.println("Goodbye!");
                    UserManager.saveUsers(users);
                    break;

            }
        }
    }
}
