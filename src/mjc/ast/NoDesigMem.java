// generated with ast extension for cup
// version 0.8
// 6/1/2024 19:38:8


package mjc.ast;

public class NoDesigMem extends Members {

    public NoDesigMem () {
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("NoDesigMem(\n");

        buffer.append(tab);
        buffer.append(") [NoDesigMem]");
        return buffer.toString();
    }
}
