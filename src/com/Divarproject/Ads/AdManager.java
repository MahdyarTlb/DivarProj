package com.Divarproject.Ads;

import com.Divarproject.Register.User;
import com.Divarproject.Register.UserManager;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AdManager {
    private static final String File_Path = "Ads.txt";

    public static void saveAds(List<Ad> ads) {
        try (FileWriter fw = new FileWriter(File_Path, false)) {
            for (Ad ad : ads) {
                String adData = "";
                switch (ad.getType()) {
                    case CAR:
                        CarAd carad = (CarAd) ad;
                        adData = "Car," + carad.getName() + "," + carad.getDescription() + "," +
                                carad.getPrice() + "," + carad.getContact() + "," + carad.getOwner().getUserName()
                                + "," + carad.getImagePath() + "," + carad.getMileage() + "," +
                                carad.getProductionage() + "," + carad.isHasAccident();
                        break;
                    case ANIMAL:
                        AnimalAd animalAd = (AnimalAd) ad;
                        adData = "Animal, " + animalAd.getName() + "," + animalAd.getDescription() + "," + animalAd.getPrice() +
                                "," + animalAd.getContact() + "," + animalAd.getOwner().getUserName() + "," + animalAd.getImagePath() +
                                "," + animalAd.getAnimalType() + "," + animalAd.getAge() + "," + animalAd.getIsVaccinated();
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


    public static List<Ad> loadAds(List<User> users) {
        List<Ad> ads = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(File_Path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 10) { // حداقل تعداد فیلدها برای هر آگهی
                    System.err.println("خطای فرمت: " + line);
                    continue;
                }

                String typeStr = parts[0].trim(); // نوع آگهی
                User owner = UserManager.FindUserByUserName(users, parts[5].trim()); // پیدا کردن کاربر

                if (owner == null) {
                    System.err.println("کاربر پیدا نشد: " + parts[5]);
                    continue;
                }

                try {
                    Ad.AdType type = Ad.AdType.valueOf(typeStr.toUpperCase()); // تبدیل به نوع آگهی
                    switch (type) {
                        case CAR:
                            CarAd carAd = new CarAd(
                                    parts[1], parts[2], Integer.parseInt(parts[3]),
                                    parts[4], owner, parts[6], Integer.parseInt(parts[8]),
                                    Integer.parseInt(parts[7]), Boolean.parseBoolean(parts[9])
                            );
                            ads.add(carAd);
                            break;

                        case ANIMAL:
                            AnimalAd animalAd = new AnimalAd(
                                    parts[1], parts[2], Integer.parseInt(parts[3]),
                                    parts[4], owner, parts[6], parts[7],
                                    Integer.parseInt(parts[8]), Boolean.parseBoolean(parts[9])
                            );
                            ads.add(animalAd);
                            break;

                        default:
                            System.err.println("نوع آگهی نامعتبر: " + typeStr);
                            break;
                    }
                } catch (IllegalArgumentException e) {
                    System.err.println("نوع آگهی نامعتبر: " + typeStr);
                }
            }
        } catch (IOException e) {
            System.err.println("خطا در بارگذاری آگهی‌ها: " + e.getMessage());
        }
        return ads;
    }
}
