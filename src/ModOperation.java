public class ModOperation implements BinaryOperation {
    @Override
    public String getName() {
        return "Остаток от деления";
    }

    @Override
    public String getSymbol() {
        return "MOD";
    }

    @Override
    public long calculate(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("остаток при делении на ноль не определён");
        }
        return (long) a % b;
    }
}
