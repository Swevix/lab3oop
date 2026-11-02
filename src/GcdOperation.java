public class GcdOperation implements BinaryOperation {
    @Override
    public String getName() {
        return "Наибольший общий делитель";
    }

    @Override
    public String getSymbol() {
        return "НОД";
    }

    @Override
    public long calculate(int a, int b) {
        long x = Math.abs((long) a);
        long y = Math.abs((long) b);
        while (y != 0) {
            long remainder = x % y;
            x = y;
            y = remainder;
        }
        return x;
    }
}
