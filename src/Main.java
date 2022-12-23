import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        InputArgs Args = Utils.parseCmdArgs(args);
        if (!Args.getAreFilesCorrect()) {
            System.err.println("Ошибка: введенные данные не корректны.");
            System.exit(0);
        }
        Data data = Utils.getArrFromFile(Args.getInputFile());
        int[][] arr = data.getArr();
        String rotate = data.getRotate();
        int[][] resArr = Utils.solution(arr, rotate);
        Utils.writeArrayToFile(Args.getOutputFile(), resArr);
    }
}
