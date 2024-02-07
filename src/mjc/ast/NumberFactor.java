// generated with ast extension for cup
// version 0.8
// 6/1/2024 19:38:8


package mjc.ast;

public class NumberFactor extends Factor {

    private Integer val;

    public NumberFactor (Integer val) {
        this.val=val;
    }

    public Integer getVal() {
        return val;
    }

    public void setVal(Integer val) {
        this.val=val;
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
        buffer.append("NumberFactor(\n");

        buffer.append(" "+tab+val);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [NumberFactor]");
        return buffer.toString();
    }
}