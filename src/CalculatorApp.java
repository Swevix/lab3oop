import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class CalculatorApp extends JFrame {
    private final BinaryOperation[] operations = {
        new DivOperation(), new ModOperation(), new GcdOperation(), new LcmOperation()
    };
    private final JTextField firstNumber = new JTextField(12);
    private final JTextField secondNumber = new JTextField(12);
    private final JLabel status = new JLabel("Введите два целых числа и нажмите «Вычислить».");
    private final DefaultTableModel results = new DefaultTableModel(
            new String[] {"Операция", "Выражение", "Результат"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    public CalculatorApp() {
        super("Лабораторная № 3 — целочисленные операции");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel content = new JPanel(new BorderLayout(0, 18));
        content.setBorder(BorderFactory.createEmptyBorder(22, 24, 22, 24));
        setContentPane(content);

        JPanel top = new JPanel(new BorderLayout(0, 16));
        JLabel title = new JLabel("DIV · MOD · НОД · НОК");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 24f));
        top.add(title, BorderLayout.NORTH);

        JPanel input = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(0, 0, 6, 14);
        c.anchor = GridBagConstraints.WEST;
        c.gridx = 0;
        c.gridy = 0;
        input.add(new JLabel("Первое число"), c);
        c.gridx = 1;
        input.add(new JLabel("Второе число"), c);
        c.gridy = 1;
        c.gridx = 0;
        firstNumber.setName("firstNumber");
        secondNumber.setName("secondNumber");
        firstNumber.setFont(firstNumber.getFont().deriveFont(18f));
        secondNumber.setFont(secondNumber.getFont().deriveFont(18f));
        input.add(firstNumber, c);
        c.gridx = 1;
        input.add(secondNumber, c);
        c.gridx = 2;
        c.weightx = 1;
        JButton calculate = new JButton("Вычислить");
        calculate.setName("calculate");
        calculate.addActionListener(event -> calculate());
        input.add(calculate, c);
        getRootPane().setDefaultButton(calculate);
        top.add(input, BorderLayout.CENTER);
        content.add(top, BorderLayout.NORTH);

        JTable table = new JTable(results);
        table.setName("results");
        table.setRowHeight(42);
        table.setFont(table.getFont().deriveFont(14f));
        table.getTableHeader().setFont(table.getFont().deriveFont(Font.BOLD));
        table.getTableHeader().setReorderingAllowed(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setFillsViewportHeight(true);
        table.getColumnModel().getColumn(0).setPreferredWidth(245);
        table.getColumnModel().getColumn(1).setPreferredWidth(180);
        table.getColumnModel().getColumn(2).setPreferredWidth(245);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(720, 205));
        content.add(scroll, BorderLayout.CENTER);

        status.setName("status");
        content.add(status, BorderLayout.SOUTH);
        resetResults();
        pack();
        setMinimumSize(getSize());
        setLocationRelativeTo(null);
    }

    private void resetResults() {
        results.setRowCount(0);
        for (BinaryOperation operation : operations) {
            results.addRow(new Object[] {operation.getName(), operation.getSymbol(), "—"});
        }
    }

    private void calculate() {
        final int a;
        final int b;
        try {
            a = Integer.parseInt(firstNumber.getText().trim());
            b = Integer.parseInt(secondNumber.getText().trim());
        } catch (NumberFormatException e) {
            resetResults();
            status.setForeground(new Color(170, 35, 35));
            status.setText("Нужны целые числа от −2147483648 до 2147483647.");
            return;
        }

        boolean hasErrors = false;
        results.setRowCount(0);
        for (BinaryOperation operation : operations) {
            String result;
            try {
                result = Long.toString(operation.calculate(a, b));
            } catch (ArithmeticException e) {
                result = "Деление на ноль";
                hasErrors = true;
            }
            results.addRow(new Object[] {
                operation.getName(), a + " " + operation.getSymbol() + " " + b, result
            });
        }
        status.setForeground(hasErrors ? new Color(170, 35, 35) : new Color(30, 110, 55));
        status.setText(hasErrors
                ? "DIV и MOD не определены при нулевом делителе. НОД и НОК вычислены."
                : "Готово. Все четыре операции вычислены.");
    }
}
