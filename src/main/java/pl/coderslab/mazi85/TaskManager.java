package pl.coderslab.mazi85;

import org.apache.commons.lang3.ArrayUtils;


import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class TaskManager {

    public static final double APP_VER = 0.1;
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println(ConsoleColors.RED + APP_VER + ConsoleColors.RESET);
        String fileName = "tasks.csv";
        String[] menu = {"list", "add", "remove", "exit"};
        String[] dataArr;

        String option;


        //file load
        try {
            dataArr = readDataFileToArr(fileName);
            System.out.println("data load successfully");
        } catch (IOException e) {
            System.out.println("Can't load data file. Create new empty data base");
            dataArr = new String[0];
        }

        // menu main app
        do {
            printMenu(menu);
            option = sc.nextLine();

            switch (option) {
                case "list":
                    listTasks(dataArr);
                    break;
                case "add":
                    dataArr = addTask(dataArr);
                    break;
                case "remove":
                    try{dataArr = removeTask(dataArr);}
                    catch (NoSuchElementException e)
                    {
                        System.out.println("Number is out of task border");
                    }
                    break;
                case "exit":
                    System.out.println(ConsoleColors.RED + "ciao!" + ConsoleColors.RESET);
                    break;
                default:
                    System.out.println("Wrong command");
                    break;
            }

        } while (!(option.equals("exit")));

        //write

        try {
            writeArrToFile(dataArr, fileName);
            System.out.println("Tasks successfully saved to file");
        } catch (IOException e) {
            System.out.println("Save error");
        }

    }

    private static String[] removeTask(String[] dataArr) {
        System.out.println("Select number to remove[" + "0.." + (dataArr.length - 1) + "]: ");
        while (!sc.hasNextInt()) {
            System.out.println("This is not a number");
            sc.nextLine();
        }
        int index = sc.nextInt();
        sc.nextLine();
        if (index >= 0 && index < dataArr.length) {
            String[] modifiedArrTask = ArrayUtils.remove(dataArr, index);
            return modifiedArrTask;
        } else throw new NoSuchElementException("Number is out of task list");
    }

    private static String[] addTask(String[] dataArr) {
        StringBuilder sb = new StringBuilder();
        System.out.println("Add task description: ");
        sb.append(sc.nextLine()).append(", ");
        System.out.println("Add task due date[dd-mm-yyyy]: ");
        sb.append(sc.nextLine()).append(", ");
        System.out.println("Is Task is important[true/false]: ");
        sb.append(sc.nextLine());

        String[] modifiedArrTask = Arrays.copyOf(dataArr, dataArr.length + 1);
        modifiedArrTask[modifiedArrTask.length - 1] = sb.toString();
        return modifiedArrTask;

    }

    private static void listTasks(String[] dataArr) {
        StringBuilder sb;
        for (int i = 0; i < dataArr.length; i++) {
            sb = new StringBuilder();
            sb.append(i).append(". ").append(dataArr[i]);
            System.out.println(sb);
        }

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


    private static void writeArrToFile(String[] dataArr, String fileName) throws IOException {

        FileWriter fw = new FileWriter(Paths.get(fileName).toFile());
        for (String s : dataArr) {
            fw.write(s + "\n");
        }
        fw.close();
    }
}
