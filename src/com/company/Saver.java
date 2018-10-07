package com.company;
import java.io.*;

public class Saver {
    public void saveUser(User user) {
        try (FileWriter writer = new FileWriter("src/com/company/history.txt", true)) {
            writer.write(user.toString());
            writer.close();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
