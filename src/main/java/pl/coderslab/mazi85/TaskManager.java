package pl.coderslab.mazi85;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TaskManager {

    public static final double APP_VER = 0.1;

    public static void main(String[] args) {
        System.out.println(ConsoleColors.RED + APP_VER + ConsoleColors.RESET);
        String fileName = "tasks.csv";
        String [] dataArr;

        try {
            dataArr = readDataFileToArr(fileName);
        } catch (IOException e) {
            System.out.println("Can't read data file. Create new empty data base");
            dataArr = new String[0];
        }


    }

    private static String[] readDataFileToArr(String fileName) throws IOException {

        Path filePath = Paths.get(fileName);
        var sb = new StringBuilder();
        for (String line : Files.readAllLines(filePath)) {
            sb.append(line).append("@@@");
        }
        System.out.println("data load successfully");
        return sb.toString().split("@@@");
    }

}
