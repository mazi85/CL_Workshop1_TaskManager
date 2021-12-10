package pl.coderslab.mazi85;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class TaskManager {

    public static final double APP_VER = 0.1;

    public static void main(String[] args) {
        System.out.println(ConsoleColors.RED + APP_VER + ConsoleColors.RESET);
        String fileName = "tasks.csv";
        String[] menu = {"list", "add", "remove", "exit"};
        String[] dataArr;
        Scanner sc = new Scanner(System.in);
        String option;

        try {
            dataArr = readDataFileToArr(fileName);
            System.out.println("data load successfully");
        } catch (IOException e) {
            System.out.println("Can't load data file. Create new empty data base");
            dataArr = new String[0];
        }


        do {
            printMenu(menu);
            option = sc.nextLine();

            switch (option) {
                case "list":
                    listOption();
                    break;
                case "add":
                    addOption();
                    break;
                case "remove":
                    removeOption();
                    break;
                case "exit":
                    System.out.println(ConsoleColors.RED + "ciao!" + ConsoleColors.RESET);
                    break;
                default:
                    System.out.println("Wrong command");
                    break;
            }

        } while (!(option.equals("exit")));


    }

    private static void removeOption() {
        System.out.println("USUWAM");
    }

    private static void addOption() {
        System.out.println("DODAJĘ");

    }

    private static void listOption() {
        System.out.println("LISTUJĘ");
    }

    private static void printMenu(String[] menu) {
        System.out.println(ConsoleColors.BLUE + "Choose an option: " + ConsoleColors.RESET);
        for (String s : menu) {
            System.out.println(s);
        }
    }

    private static String[] readDataFileToArr(String fileName) throws IOException {

        Path filePath = Paths.get(fileName);
        var sb = new StringBuilder();
        for (String line : Files.readAllLines(filePath)) {
            sb.append(line).append("@@@");
        }
        return sb.toString().split("@@@");
    }

}
