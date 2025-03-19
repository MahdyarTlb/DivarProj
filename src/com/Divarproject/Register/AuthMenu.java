package com.Divarproject.Register;

import com.Divarproject.Ads.Ad;
import com.Divarproject.Ads.AdManager;
import com.Divarproject.Ads.CarAd;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;
import java.util.Scanner;

import static java.lang.System.exit;

public class AuthMenu extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../GUI/login.fxml")); // لود FXML
        Parent root = loader.load(); // بارگذاری UI

        primaryStage.setTitle("Divar");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
        List<User> users = UserManager.LoadUsers();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. login \n2. register \n3. exit");
            System.out.print("Enter your choice : ");
            int choice = scanner.nextInt();
            scanner.nextLine(); //clear buffer
            switch (choice) {
                case 1:
                    System.out.println("Enter your username : ");
                    String userName = scanner.nextLine();
                    System.out.println("Enter your password : ");
                    String password = scanner.nextLine();
                    for (User user : users) {
                        if (user.getUserName().equalsIgnoreCase(userName) && user.getPassword().equals(password)) {
                            System.out.println("Hi " + userName + " !");
                            List<Ad> ads = AdManager.loadAds(users);

                                //Ads Managing
                            {
                                while (true) {
                                    System.out.println("1.Create Ad \n2.Show Ads \n3.exit ");
                                    System.out.println("Enter your choice : ");
                                    int Choice = scanner.nextInt();
                                    scanner.nextLine();
                                    switch (Choice) {
                                        case 1:
                                            System.out.println("1.Car");
                                            System.out.println("Enter Category: ");
                                            int category = scanner.nextInt();
                                            scanner.nextLine();
                                            switch (category) {
                                                case 1:
                                                    System.out.println("Enter name: ");
                                                    String name = scanner.nextLine();
                                                    System.out.println("Enter description: ");
                                                    String description = scanner.nextLine();
                                                    System.out.println("Enter price: ");
                                                    int price = scanner.nextInt();
                                                    scanner.nextLine();
                                                    System.out.println("Enter contact : ");
                                                    String contact = scanner.nextLine();
                                                    System.out.println("Enter Image Path : ");
                                                    String imagePath = scanner.nextLine();
                                                    System.out.println("Enter Productionage : ");
                                                    int productionage = scanner.nextInt();
                                                    System.out.println("Enter mileage : ");
                                                    int mileage = scanner.nextInt();
                                                    System.out.println("Enter hasAccident : ");
                                                    boolean hasAccident = (scanner.nextInt() == 0) ? false : true;
                                                    scanner.nextLine();

                                                    User loggedInUser = UserManager.FindUserByUserName(users, userName);

                                                    CarAd carAd = new CarAd(name, description, price,
                                                            contact, loggedInUser, imagePath, productionage,
                                                            mileage, hasAccident);
                                                    ads.add(carAd);
                                                    AdManager.saveAds(ads);
                                                    System.out.println("Ad successfully created");
                                                default:
                                                    System.out.println("Invalid choice");
                                                    continue;
                                            }
                                        case 2:
                                            System.out.println("All Ads:");
                                            for (Ad ad : ads) {
                                                ad.displayDetails();
                                                System.out.println("-------------------------------");
                                            }
                                            break;
                                        case 3:
                                            System.out.println("Good Bye " + userName + " !");
                                            AdManager.saveAds(ads);
                                            exit(0);
                                    }
                                }
                            }

                        }
                        System.out.println("Your userName or password is incorrect! try again");
                    }
                    continue;
                case 2:
                    System.out.print("Enter your UserName : ");
                    String UserName = scanner.nextLine();
                    System.out.print("Enter your Password : ");
                    String Password = scanner.nextLine();
                    //بررسی تکراری نبودن
                    boolean IsTaken = false;
                    for (User user : users) {
                        if (UserName.equals(user.getUserName())) {
                            IsTaken = true;
                            break;
                        }
                    }
                    if (IsTaken) {
                        System.out.println("This name is already taken!");
                    } else {
                        User user = new User(UserName, Password); //make new user
                        users.add(user);
                        UserManager.saveUsers(users);
                        System.out.println("--------------------\n" +
                                "User has been saved successfully\nyou must login\n--------------------");
                    }
                    break;
                case 3:
                    System.out.println("Goodbye!");
                    exit(0);

            }
        }
    }
}
