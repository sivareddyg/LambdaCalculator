/*
 * Equality.java
 *
 * Created on June 1, 2006, 2:48 PM
 */

package lambdacalc.logic;

import java.util.Set;

/**
 * Equality and inequality operators "=" and "=/=".
 */
public class Equality extends LogicalBinary {
    /**
     * The equal sign character
     */
    public static final char EQ_SYMBOL = '=';
    
    /**
     * The not-equal character
     */
    public static final char NEQ_SYMBOL = '\u2260';
    
    boolean equality;
    
    /**
     * Constructs the connective.
     * @param left the expression on the left side of the connective
     * @param right the expression on the right side of the connective
     * @param equality true for an equality operator, false for inequality
     */
    public Equality(Expr left, Expr right, boolean equality) {
        super(left, right);
        this.equality = equality;
    }
    
    public String getSymbol() {
        if (equality)
            return String.valueOf(EQ_SYMBOL);
        else
            return String.valueOf(NEQ_SYMBOL);
    }

    public Type getType() throws TypeEvaluationException {
        if (!getLeft().getType().equals(getRight().getType()))
            throw new TypeMismatchException("The types of the expressions on the left and right of an equality operator must be the same, but " + getLeft() + " is of type " + getLeft().getType() + " and " + getRight() + " is of type " + getRight().getType() + ".");
        return Type.T;
    }

    protected boolean equalsHelper(Binary b) {
        return b instanceof Equality && equality == ((Equality)b).equality;
    }

    /**
     * Gets the operator precedence of this operator.
     * All values are documented in Expr, so don't change the value here
     * without changing it there.
     */
    public int getOperatorPrecedence() {
        return 5;
    }
    
    protected Binary create(Expr left, Expr right) {
        return new Equality(left, right, equality);
    }
    
    public void writeToStream(java.io.DataOutputStream output) throws java.io.IOException {
        super.writeToStream(output);
        output.writeShort(0); // data format version
        output.writeBoolean(equality);
    }

    Equality(java.io.DataInputStream input) throws java.io.IOException {
        super(input);
        if (input.readShort() != 0) throw new java.io.IOException("Invalid data."); // future version?
        equality = input.readBoolean();
    }
}
