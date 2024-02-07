// generated with ast extension for cup
// version 0.8
// 6/1/2024 19:38:8


package mjc.ast;

public class Designator implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Obj obj = null;

    private DesignatorRef DesignatorRef;
    private Members Members;

    public Designator (DesignatorRef DesignatorRef, Members Members) {
        this.DesignatorRef=DesignatorRef;
        if(DesignatorRef!=null) DesignatorRef.setParent(this);
        this.Members=Members;
        if(Members!=null) Members.setParent(this);
    }

    public DesignatorRef getDesignatorRef() {
        return DesignatorRef;
    }

    public void setDesignatorRef(DesignatorRef DesignatorRef) {
        this.DesignatorRef=DesignatorRef;
    }

    public Members getMembers() {
        return Members;
    }

    public void setMembers(Members Members) {
        this.Members=Members;
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
        if(DesignatorRef!=null) DesignatorRef.accept(visitor);
        if(Members!=null) Members.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorRef!=null) DesignatorRef.traverseTopDown(visitor);
        if(Members!=null) Members.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorRef!=null) DesignatorRef.traverseBottomUp(visitor);
        if(Members!=null) Members.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Designator(\n");

        if(DesignatorRef!=null)
            buffer.append(DesignatorRef.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Members!=null)
            buffer.append(Members.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Designator]");
        return buffer.toString();
    }
}
