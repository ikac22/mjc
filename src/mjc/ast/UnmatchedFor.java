// generated with ast extension for cup
// version 0.8
// 6/1/2024 19:38:8


package mjc.ast;

public class UnmatchedFor extends Unmatched {

    private ForLoopUnmatched ForLoopUnmatched;

    public UnmatchedFor (ForLoopUnmatched ForLoopUnmatched) {
        this.ForLoopUnmatched=ForLoopUnmatched;
        if(ForLoopUnmatched!=null) ForLoopUnmatched.setParent(this);
    }

    public ForLoopUnmatched getForLoopUnmatched() {
        return ForLoopUnmatched;
    }

    public void setForLoopUnmatched(ForLoopUnmatched ForLoopUnmatched) {
        this.ForLoopUnmatched=ForLoopUnmatched;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ForLoopUnmatched!=null) ForLoopUnmatched.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ForLoopUnmatched!=null) ForLoopUnmatched.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ForLoopUnmatched!=null) ForLoopUnmatched.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("UnmatchedFor(\n");

        if(ForLoopUnmatched!=null)
            buffer.append(ForLoopUnmatched.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [UnmatchedFor]");
        return buffer.toString();
    }
}
