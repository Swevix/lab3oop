public class DivOperation implements BinaryOperation {
    @Override
    public String getName() {
        return "Целочисленное деление";
    }

    @Override
    public String getSymbol() {
        return "DIV";
    }

    @Override
    public long calculate(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("деление на ноль невозможно");
        }
        return (long) a / b;
    }
}
