import java.io.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class Utils {
    public static Data getArrFromFile(String fileName) {
        int [][] arr = {};
        String rotate = "right";
        try (var sc = new Scanner(new BufferedReader(new FileReader(fileName)))) {
            int h = Integer.parseInt(sc.nextLine().trim());
            int w = Integer.parseInt(sc.nextLine().trim());
            arr = new int[h][w];
            for (int i = 0; i < h; i++) {
                String[] line = sc.nextLine().trim().split(" ");
                for (int j = 0; j < w; j++) {
                    arr[i][j] = Integer.parseInt(line[j]);
                }
            }
            rotate = sc.nextLine().trim();
        } catch (Exception e) {
        }
        return new Data(arr, rotate);
    }

    public static boolean writeArrayToFile(String fileName, int[][] arr) throws IOException {
        if (arr.length == 0) return false;
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        writer.write(Integer.toString(arr.length) + '\n');
        writer.write(Integer.toString(arr[0].length) + '\n');
        for (int i = 0; i < arr.length; i++) {
            String resString = "";
            for (int j = 0; j < arr[i].length; j++) {
                resString += Integer.toString(arr[i][j]);
                if (j != arr[i].length - 1) resString += " ";
            }
            writer.write(resString + "\n");
        }
        writer.close();
        return true;
    }

    public static int[][] solution(int[][] inMas, String rotate) {
        if (inMas.length == 0) return inMas;
        int[][] outMas = new int[inMas[0].length][inMas.length];
        if (Objects.equals(rotate, "left")) {
            for (int i = 0; i < inMas.length; i++) {
                for (int j = 0; j < inMas[0].length; j++) {
                    outMas[outMas.length - 1 - j][i] = inMas[i][j];
                }
            }
        }
        else {
            for (int i = 0; i < inMas.length; i++) {
                for (int j = 0; j < inMas[0].length; j++) {
                    outMas[j][inMas.length - 1 - i] = inMas[i][j];
                }
            }

        }
        return outMas;
    }

    public static InputArgs parseCmdArgs(String[] args) {
        InputArgs Args = new InputArgs();
        try {
            String inputFile = args[0];
            String outputFile = args[1];
            if (!checkIfFileExists(inputFile)) {
                throw new Exception();
            }
            assert !checkIfFileExists(inputFile);
            Args.setInputFile(inputFile);
            Args.setOutputFile(outputFile);
        } catch (Exception ex) {
            Args.setAreFilesCorrect(false);
        }
        return Args;
    }

    public static boolean checkIfFileExists(String fileName) {
        File f = new File(fileName);
        return f.exists();
    }

    public static void printArray(int[][] arr) {
        System.out.println("[");
        Arrays.stream(arr).map(Arrays::toString).forEach(System.out::println);
        System.out.println("]");
    }
}