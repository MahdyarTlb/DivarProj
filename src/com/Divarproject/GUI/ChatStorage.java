package com.Divarproject.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChatStorage {

    // مسیر فایل بر اساس نام کاربران (ترتیب الفبایی)
    private static String getFilePath(String user1, String user2) {
        String sortedUsers = (user1.compareTo(user2) < 0) ? user1 + "_" + user2 : user2 + "_" + user1;
        return "chat_" + sortedUsers + ".txt";
    }

    // ذخیره پیام‌ها (فقط یک فایل برای هر جفت کاربر)
    public static void saveMessages(String sender, String receiver, ObservableList<String> messages) {
        String filePath = getFilePath(sender, receiver);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String message : messages) {
                writer.write(message);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("خطا در ذخیره پیام‌ها: " + e.getMessage());
        }
    }

    // بارگذاری پیام‌ها
    public static ObservableList<String> loadMessages(String user1, String user2) {
        String filePath = getFilePath(user1, user2);
        ObservableList<String> messages = FXCollections.observableArrayList();

        File file = new File(filePath);
        if (!file.exists()) {
            return messages; // اگر فایل وجود نداشته باشه، لیست خالی برگردون
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                messages.add(line);
            }
        } catch (IOException e) {
            System.err.println("خطا در بارگذاری پیام‌ها: " + e.getMessage());
        }

        return messages;
    }

    // بارگذاری لیست چت‌ها برای کاربر لاگین‌شده
    public static List<String> loadChats(String loggedInUser) {
        File folder = new File(".");
        File[] listOfFiles = folder.listFiles((dir, name) -> name.startsWith("chat_") && name.endsWith(".txt"));

        List<String> chats = new ArrayList<>();
        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                String fileName = file.getName();
                String[] users = fileName.replace("chat_", "").replace(".txt", "").split("_");
                if (users.length == 2) {
                    if (users[0].equals(loggedInUser)) {
                        chats.add(users[1]); // کاربر مقابل
                    } else if (users[1].equals(loggedInUser)) {
                        chats.add(users[0]); // کاربر مقابل
                    }
                }
            }
        }
        return chats.stream().distinct().collect(Collectors.toList()); // حذف تکراری
    }
}