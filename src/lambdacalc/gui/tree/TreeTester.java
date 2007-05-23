/*
 * TreeTester.java
 *
 * Created on May 22, 2007, 10:54 AM
 */

package lambdacalc.gui.tree;

import java.awt.*;
import javax.swing.*;
import lambdacalc.logic.*;

/**
 *
 * @author  tauberer
 */
public class TreeTester extends javax.swing.JFrame {
    
    /** Creates new form TreeTester */
    public TreeTester() {
        initComponents();
        
        getContentPane().setLayout(new java.awt.FlowLayout());
        
        CreateTree();
    }

   TreeCanvas tree = new TreeCanvas();

    void CreateTree() {
        getContentPane().add(tree);
        
        /*tree.getRoot().setLabel("Root");
        tree.getRoot().addChild().setLabel("Child 1");
        TreeCanvas.TreeNode r = tree.getRoot().addChild();
        r.addChild().setLabel("Child 2222222");
        r.addChild().setLabel("Child 3333333");*/
        
        try {
            ExpressionParser.ParseOptions opts = new ExpressionParser.ParseOptions();
            opts.ASCII = true;
            opts.Typer.addEntry("j", false, Type.E);
            opts.Typer.addEntry("m", false, Type.E);
            opts.Typer.addEntry("x", true, Type.E);
            opts.Typer.addEntry("y", true, Type.E);
            opts.Typer.addEntry("r", false, Type.ExET);

            LFNode run = new LFNode("run", ExpressionParser.parse("Lx.Ly.run(y,x)", opts));
            LFNode mary = new LFNode("mary", ExpressionParser.parse("m", opts));
            LFNode john = new LFNode("john", ExpressionParser.parse("j", opts));
            
            LFNode runmary = new LFNode(run, mary);
            LFNode johnrunmary = new LFNode(john, runmary);
            
            buildTree(tree.getRoot(), johnrunmary);
        } catch (Exception e) {
            System.err.println(e);
        }
       
    }
    
    void buildTree(TreeCanvas.TreeNode treenode, LFNode lfnode) throws TypeEvaluationException {
        JLabel label = new JLabel();
        label.setText(lfnode.getValue().toString());
        label.setFont(new java.awt.Font("Times New Roman", 0, 14));
        
        treenode.setLabel(label);
        if (lfnode.Left != null) {
            buildTree(treenode.addChild(), lfnode.Left);
            buildTree(treenode.addChild(), lfnode.Right);
        }
    }
    
    class LFNode {
        public LFNode Left, Right;
        public String Ortho;
        public Expr Value;
        
        public LFNode(LFNode left, LFNode right) {
            Left = left;
            Right = right;
        }
        
        public LFNode(String ortho, Expr value) {
            Ortho = ortho;
            Value = value;
        }
        
        public Expr getValue() throws TypeEvaluationException {
            if (Left == null)
                return Value;
            
            Expr eleft = Left.getValue();
            Expr eright = Right.getValue();
            
            if (isFunctionOf(eleft, eright))
                return applyFunction(eleft, eright);
            if (isFunctionOf(eright, eleft))
                return applyFunction(eright, eleft);
            throw new RuntimeException();
        }
        
        boolean isFunctionOf(Expr func, Expr arg) throws TypeEvaluationException {
            Type functype = func.getType();
            Type argtype = arg.getType();
            if (functype instanceof CompositeType)
                if (((CompositeType)functype).getLeft().equals(argtype))
                    return true;
            return false;
        }
        
        Expr applyFunction(Expr func, Expr arg) throws TypeEvaluationException {
            Expr e = new FunApp(func, arg);
            Expr.LambdaConversionResult lcr = e.performLambdaConversion();
            if (lcr == null)
                return e;
            return lcr.Result;
        }
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 300, Short.MAX_VALUE)
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        tree.getRoot().addChild().setLabel("Click!");
    }//GEN-LAST:event_formMouseClicked
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TreeTester().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    
}