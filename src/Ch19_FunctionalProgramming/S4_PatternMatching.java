package Ch19_FunctionalProgramming;

import java.util.function.Function;
import java.util.function.Supplier;

public class S4_PatternMatching {
    static class Expr {}
    static class Number extends Expr {
        int val;

        public Number(int val) {
            this.val = val;
        }

        @Override
        public String toString() {
            return "Number{" +
                    "val=" + val +
                    '}';
        }
    }
    static class BinOp extends Expr {
        String opName;
        Expr left;
        Expr right;

        public BinOp(String opName, Expr left, Expr right) {
            this.opName = opName;
            this.left = left;
            this.right = right;
        }

        @Override
        public String toString() {
            return "BinOp{" +
                    "opName='" + opName + '\'' +
                    ", left=" + left +
                    ", right=" + right +
                    '}';
        }
    }

    static interface TriFunction<S, T, U, R> {
        R apply(S s, T t, U u);
    }

    static <T> T patternMatchExpr(
            Expr e,
            TriFunction<String, Expr, Expr, T> binopcase,
            Function<Integer, T> numcase,
            Supplier<T> defaultcase) {
        return (e instanceof BinOp) ? binopcase.apply(((BinOp) e).opName, ((BinOp) e).left, ((BinOp) e).right)
                : (e instanceof Number) ? numcase.apply(((Number) e).val) : defaultcase.get();
    }

    public static Expr simplify(Expr e) {
        TriFunction<String, Expr, Expr, Expr> binopcase =
                (opName, left, right) -> {
                    if ("+".equals(opName)) {
                        if (left instanceof Number && ((Number) left).val == 0) return right;
                        if (right instanceof Number && ((Number) right).val == 0) return left;
                    }
                    if ("*".equals(opName)) {
                        if (left instanceof Number && ((Number) left).val == 1) return right;
                        if (right instanceof Number && ((Number) right).val == 1) return left;
                    }
                    return new BinOp(opName, left, right);
                };

        Function<Integer, Expr> numcase = val -> new Number(val);
        Supplier<Expr> defaultcase = () -> new Number(0);

        return patternMatchExpr(e, binopcase, numcase, defaultcase);
    }

    public static void main(String[] args) {
        Expr e = new BinOp("+", new Number(5), new Number(0));
        System.out.println(e + " ==> " + simplify(e));
        e = new BinOp("*", new Number(1), new Number(8));
        System.out.println(e + " ==> " + simplify(e));
    }

}
