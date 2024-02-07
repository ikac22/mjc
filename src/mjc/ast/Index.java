// generated with ast extension for cup
// version 0.8
// 6/1/2024 19:38:8


package mjc.ast;

public class Index implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private IndexStart IndexStart;
    private Expr Expr;
    private IndexEnd IndexEnd;

    public Index (IndexStart IndexStart, Expr Expr, IndexEnd IndexEnd) {
        this.IndexStart=IndexStart;
        if(IndexStart!=null) IndexStart.setParent(this);
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
        this.IndexEnd=IndexEnd;
        if(IndexEnd!=null) IndexEnd.setParent(this);
    }

    public IndexStart getIndexStart() {
        return IndexStart;
    }

    public void setIndexStart(IndexStart IndexStart) {
        this.IndexStart=IndexStart;
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
    }

    public IndexEnd getIndexEnd() {
        return IndexEnd;
    }

    public void setIndexEnd(IndexEnd IndexEnd) {
        this.IndexEnd=IndexEnd;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(IndexStart!=null) IndexStart.accept(visitor);
        if(Expr!=null) Expr.accept(visitor);
        if(IndexEnd!=null) IndexEnd.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(IndexStart!=null) IndexStart.traverseTopDown(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
        if(IndexEnd!=null) IndexEnd.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(IndexStart!=null) IndexStart.traverseBottomUp(visitor);
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        if(IndexEnd!=null) IndexEnd.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Index(\n");

        if(IndexStart!=null)
            buffer.append(IndexStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(IndexEnd!=null)
            buffer.append(IndexEnd.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Index]");
        return buffer.toString();
    }
}
