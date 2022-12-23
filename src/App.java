import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class App {
    static JFrame frame;
    private JPanel panelMain;
    private JButton addRowButton;
    private JTable Table;
    private JButton addColumnButton;
    private JTextField inputFileField;
    private JTextField outputFileField;
    private JButton loadFromFileButton;
    private JButton loadToFileButton;
    private JButton deleteColumnButton;
    private JButton deleteRowButton;
    private JButton getResultButton;
    private JButton LoadResultToFileButton;
    private JTable Result;
    private JComboBox Rotate;
    static int rowsCount = 0;
    static int columnsCount = 0;


    public JTable getField() {
        return Table;
    }

    public App() {
        addRowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model = (DefaultTableModel) Table.getModel();
                model.addRow(new Object[] {});
                rowsCount++;
            }
        });
        deleteRowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rowsCount == 0) return;
                DefaultTableModel model = (DefaultTableModel) Table.getModel();
                model.removeRow(rowsCount - 1);
                rowsCount--;
            }
        });
        addColumnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model = (DefaultTableModel) Table.getModel();
                model.addColumn("");
                columnsCount++;
            }
        });
        deleteColumnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (columnsCount == 0) return;
                DefaultTableModel model = (DefaultTableModel) Table.getModel();
                model.setColumnCount(columnsCount - 1);
                columnsCount--;
            }
        });
        loadFromFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int response = fileChooser.showOpenDialog(null);

                if (response == JFileChooser.APPROVE_OPTION) {
                    String filename = fileChooser.getSelectedFile().getAbsolutePath();
                    Data data = Utils.getArrFromFile(filename);
                    int[][] arr = data.getArr();
                    String rotate = data.getRotate();
                    Rotate.getModel().setSelectedItem(rotate);
                    setArrayToField(Table, arr);
                }
            }
        });
        getResultButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[][] arr = getArrayFromField(Table);
                int[][] resArr = Utils.solution(arr, (String) Rotate.getModel().getSelectedItem());
                setArrayToField(Result, resArr);
            }
        });
        LoadResultToFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[][] arr = getArrayFromField(Result);
                if (arr.length == 0) return;
                JFileChooser fileChooser = new JFileChooser();
                int response = fileChooser.showOpenDialog(null);

                if (response == JFileChooser.APPROVE_OPTION) {
                    String filename = fileChooser.getSelectedFile().getAbsolutePath();
                    try {
                        boolean res = Utils.writeArrayToFile(filename, arr);
                        if (res) JOptionPane.showMessageDialog(frame,
                                "Данные успешно записаны в файл.");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
    }


    public void setArrayToField(JTable field, int[][] arr) {
        rowsCount = arr.length;
        columnsCount = arr[0].length;
        DefaultTableModel model = (DefaultTableModel) field.getModel();
        model.setColumnCount(columnsCount);
        model.setRowCount(rowsCount);
        for (int i = 0; i < rowsCount; i++) {
            for (int j = 0; j < columnsCount; j++) {
                model.setValueAt(arr[i][j] == -1 ? "" : Integer.toString(arr[i][j]), i, j);
            }
        }
    }

    public int[][] getArrayFromField(JTable field) {
        int[][] arr = new int[field.getRowCount()][field.getColumnCount()];
        DefaultTableModel model = (DefaultTableModel) field.getModel();
        for (int i = 0; i < field.getRowCount(); i++) {
            for (int j = 0; j < field.getColumnCount(); j++) {
                var value = model.getValueAt(i, j);
                arr[i][j] = value == "" || value == null ? -1 : Integer.parseInt((String) model.getValueAt(i, j));
            }
        }
        return arr;
    }

    public static void main(String[] args) {
        frame = new JFrame("App");
        frame.setMinimumSize(new Dimension(700, 500));
        frame.setContentPane(new App().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
