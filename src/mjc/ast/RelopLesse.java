// generated with ast extension for cup
// version 0.8
// 6/1/2024 19:38:8


package mjc.ast;

public class RelopLesse extends Relop {

    public RelopLesse () {
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
        buffer.append("RelopLesse(\n");

        buffer.append(tab);
        buffer.append(") [RelopLesse]");
        return buffer.toString();
    }
}
