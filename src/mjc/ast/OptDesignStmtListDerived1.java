// generated with ast extension for cup
// version 0.8
// 6/1/2024 19:38:8


package mjc.ast;

public class OptDesignStmtListDerived1 extends OptDesignStmtList {

    private DesignStmtList DesignStmtList;

    public OptDesignStmtListDerived1 (DesignStmtList DesignStmtList) {
        this.DesignStmtList=DesignStmtList;
        if(DesignStmtList!=null) DesignStmtList.setParent(this);
    }

    public DesignStmtList getDesignStmtList() {
        return DesignStmtList;
    }

    public void setDesignStmtList(DesignStmtList DesignStmtList) {
        this.DesignStmtList=DesignStmtList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesignStmtList!=null) DesignStmtList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignStmtList!=null) DesignStmtList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignStmtList!=null) DesignStmtList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("OptDesignStmtListDerived1(\n");

        if(DesignStmtList!=null)
            buffer.append(DesignStmtList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [OptDesignStmtListDerived1]");
        return buffer.toString();
    }
}
