// generated with ast extension for cup
// version 0.8
// 6/1/2024 19:38:8


package mjc.ast;

public class DesignStmtListDerived1 extends DesignStmtList {

    private DesignStmtList DesignStmtList;
    private DesignatorStatement DesignatorStatement;

    public DesignStmtListDerived1 (DesignStmtList DesignStmtList, DesignatorStatement DesignatorStatement) {
        this.DesignStmtList=DesignStmtList;
        if(DesignStmtList!=null) DesignStmtList.setParent(this);
        this.DesignatorStatement=DesignatorStatement;
        if(DesignatorStatement!=null) DesignatorStatement.setParent(this);
    }

    public DesignStmtList getDesignStmtList() {
        return DesignStmtList;
    }

    public void setDesignStmtList(DesignStmtList DesignStmtList) {
        this.DesignStmtList=DesignStmtList;
    }

    public DesignatorStatement getDesignatorStatement() {
        return DesignatorStatement;
    }

    public void setDesignatorStatement(DesignatorStatement DesignatorStatement) {
        this.DesignatorStatement=DesignatorStatement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesignStmtList!=null) DesignStmtList.accept(visitor);
        if(DesignatorStatement!=null) DesignatorStatement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignStmtList!=null) DesignStmtList.traverseTopDown(visitor);
        if(DesignatorStatement!=null) DesignatorStatement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignStmtList!=null) DesignStmtList.traverseBottomUp(visitor);
        if(DesignatorStatement!=null) DesignatorStatement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignStmtListDerived1(\n");

        if(DesignStmtList!=null)
            buffer.append(DesignStmtList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DesignatorStatement!=null)
            buffer.append(DesignatorStatement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignStmtListDerived1]");
        return buffer.toString();
    }
}
