// generated with ast extension for cup
// version 0.8
// 6/1/2024 19:38:8


package mjc.ast;

public class MatchedPrint extends Matched {

    private Expr Expr;
    private OptCommaNumConst OptCommaNumConst;

    public MatchedPrint (Expr Expr, OptCommaNumConst OptCommaNumConst) {
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
        this.OptCommaNumConst=OptCommaNumConst;
        if(OptCommaNumConst!=null) OptCommaNumConst.setParent(this);
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
    }

    public OptCommaNumConst getOptCommaNumConst() {
        return OptCommaNumConst;
    }

    public void setOptCommaNumConst(OptCommaNumConst OptCommaNumConst) {
        this.OptCommaNumConst=OptCommaNumConst;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Expr!=null) Expr.accept(visitor);
        if(OptCommaNumConst!=null) OptCommaNumConst.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
        if(OptCommaNumConst!=null) OptCommaNumConst.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        if(OptCommaNumConst!=null) OptCommaNumConst.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MatchedPrint(\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(OptCommaNumConst!=null)
            buffer.append(OptCommaNumConst.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MatchedPrint]");
        return buffer.toString();
    }
}
