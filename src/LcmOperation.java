public class LcmOperation implements BinaryOperation {
    private final BinaryOperation gcd = new GcdOperation();

    @Override
    public String getName() {
        return "Наименьшее общее кратное";
    }

    @Override
    public String getSymbol() {
        return "НОК";
    }

    @Override
    public long calculate(int a, int b) {
        if (a == 0 || b == 0) {
            return 0;
        }
        return Math.abs(((long) a / gcd.calculate(a, b)) * b);
    }
}
