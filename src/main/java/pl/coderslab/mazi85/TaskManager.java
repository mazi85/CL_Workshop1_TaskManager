package pl.coderslab.mazi85;

import org.apache.commons.lang3.ArrayUtils;


import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static java.lang.String.valueOf;

public class TaskManager {

    public static final double APP_VER = 0.1;
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println(ConsoleColors.RED + APP_VER + ConsoleColors.RESET);
        String fileName = "tasks.csv";
        String[] menu = {"list", "add", "remove", "exit"};
        String[][] taskArray;

        String option;


        //file load
        try {
            taskArray = readDataFileToArr(fileName);
            System.out.println("data load successfully");
        } catch (IOException e) {
            System.out.println("Can't load data file. Create new empty data base");
            taskArray = new String[0][0];
        }

        // menu main app
        do {
            printMenu(menu);
            option = sc.nextLine();

            switch (option) {
                case "list":
                    listTasks(taskArray);
                    break;
                case "add":
                    taskArray = addTask(taskArray);
                    break;
                case "remove":
                    try {
                        taskArray = removeTask(taskArray);
                    } catch (NoSuchElementException e) {
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

//        try {
//            writeArrToFile(taskArray, fileName);
//            System.out.println("Tasks successfully saved to file");
//        } catch (IOException e) {
//            System.out.println("Save error");
//        }

    }

    private static String[][] removeTask(String[][] dataArr) {
        System.out.println("Select number to remove(" + "0.." + (dataArr.length) + ">: ");
        while (!sc.hasNextInt()) {
            System.out.println("This is not a number");
            sc.nextLine();
        }
        int index = sc.nextInt();
        sc.nextLine();
        if (index >= 0 && index < dataArr.length) {
            String[][] modifiedArrTask = ArrayUtils.remove(dataArr, index);
            return modifiedArrTask;
        } else throw new NoSuchElementException("Number is out of task list");
    }

    private static String[][] addTask(String[][] dataArr) {
        StringBuilder sb = new StringBuilder();
        System.out.println("Add task description: ");
        sb.append(sc.nextLine()).append(",");
        System.out.println("Add task due date[dd-mm-yyyy]: ");
        sb.append(sc.nextLine()).append(",");
        System.out.println("Is Task is important[true/false]: ");
        sb.append(sc.nextLine());

        String[][] modifiedArrTask = Arrays.copyOf(dataArr, dataArr.length + 1);
        modifiedArrTask[modifiedArrTask.length - 1] = sb.toString().split(",");
        return modifiedArrTask;

    }

    private static void listTasks(String[][] dataArr) {
        StringBuilder sb;
        for (int i = 0; i < dataArr.length; i++) {
            sb = new StringBuilder();
            sb.append(i).append(". ");
            for (int j = 0; j < dataArr[i].length; j++) {
                sb.append(dataArr[i][j]).append(", ");
            }
            System.out.println(sb.delete(sb.length()-2,sb.length()-1));
        }

    }

    private static void printMenu(String[] menu) {
        System.out.println("\n" + ConsoleColors.BLUE + "Choose an option: " + ConsoleColors.RESET);
        for (String s : menu) {
            System.out.println(s);
        }
    }

    private static String[][] readDataFileToArr(String fileName) throws IOException {

        Path filePath = Paths.get(fileName);
        String[][] taskArray;
        List<String> strings = Files.readAllLines(filePath);


        taskArray = new String[strings.size()][strings.get(0).split(",").length];

        for (int i = 0; i < taskArray.length; i++) {
            taskArray[i] = strings.get(i).split(",");
            for (int j = 0; j < taskArray[i].length; j++) {
                taskArray[i][j]=taskArray[i][j].trim();
            }
        }
        return taskArray;
    }


    private static void writeArrToFile(String[] dataArr, String fileName) throws IOException {

        FileWriter fw = new FileWriter(Paths.get(fileName).toFile());
        for (String s : dataArr) {
            fw.write(s + "\n");
        }
        fw.close();
    }
}
