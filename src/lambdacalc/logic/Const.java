/*
 * Var.java
 *
 * Created on May 29, 2006, 3:04 PM
 */

package lambdacalc.logic;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Represents a constant.  Constants are considered equal if
 * their names and types are equal.
 */
public class Const extends Identifier {
  
    /**
     * Creates a constant.
     * 
     * @param symbol the name of the constant
     * @param type the type of the constant
     */
    public Const(String symbol, Type type, boolean isTypeExplicit) {
        super(symbol, type, isTypeExplicit); 
    }    

    protected Set getVars(boolean unboundOnly) {
        HashSet ret = new HashSet();
        return ret;
    }

    protected boolean equals(Identifier i, boolean useMaps, Map thisMap, Map otherMap, Map freeVarMap) {
        // ignore maps in all cases, since it only applies to variables
        if (i instanceof Const)
            return this.getType().equals(i.getType()) 
                && this.getSymbol().equals(i.getSymbol());
        else
            return false;
    }
    
    protected Expr performLambdaConversion2(Var var, Expr replacement, Set binders, Set accidentalBinders) throws TypeEvaluationException {
        // We're doing substitutions. Clearly, not applicable to a constant.
        return this;
    }

    /**
     * Creates a new instance of this constant, that is, shallowly copies it.
     *
     * @return a copy of this
     */    
    protected Identifier create() {
        return new Const(this.getSymbol(), this.getType(), this.isTypeExplicit());
    }
    
    protected Expr createAlphabeticalVariant(Set bindersToChange, Set variablesInUse, Map updates) {
        return this;
    }
    
    public Expr createAlphatypicalVariant(HashMap<Type,Type> alignments, Set variablesInUse, Map updates) {
        return this;
    }

    Const(java.io.DataInputStream input) throws java.io.IOException {
        super(input);
    }
}
