package com.Divarproject.Ads;

import com.Divarproject.Register.User;
import com.Divarproject.Register.UserManager;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AdManager {
    private static final String File_Path = "Ads.txt";

    public static void saveAds(List<Ad> ads){
        try(FileWriter fw = new FileWriter(File_Path)){
            for(Ad ad : ads){
                String adData = "";
                switch(ad.getType()){
                    case CAR:
                        CarAd carad = (CarAd)ad;
                        adData = "Car," + carad.getName() + "," + carad.getDescription() + "," +
                            carad.getPrice() + "," + carad.getContact() + "," + carad.getOwner().getUserName()
                            + "," + carad.getImagePath() + "," + carad.getMileage() + "," +
                            carad.getProductionage() + "," + carad.isHasAccident();
                        break;
                    default:
                        System.out.println("Invalid Ad Type");
                        continue;
                }
                fw.write(adData + System.lineSeparator());
            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }


    public static List<Ad> loadAds(List<User> users){
        List<Ad> ads = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(File_Path))) {
            String line;
            while((line = br.readLine())!=null){
                String[] parts = line.split(",");

                if(parts.length < 1) continue; // if line is blank

                String Typestr = parts[0];
                User owner = UserManager.FindUserByUserName(users, parts[5]);

                if (owner == null) continue; // if user isn't correct
                Ad.AdType Type = Ad.AdType.valueOf(Typestr.toUpperCase());
                switch(Type){
                    case CAR:
                        if(parts.length >= 10){
                            CarAd carAd = new CarAd(parts[1], parts[2], Integer.parseInt(parts[3])
                                    ,parts[4], owner, parts[6], Integer.parseInt(parts[7]),
                                    Integer.parseInt(parts[8]), Boolean.parseBoolean(parts[9])
                            );
                            ads.add(carAd);
                        }
                        break;

                    default:
                        System.out.println("Invalid Ad Type");
                        continue;
                }
            }
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        return ads;
    }
}
