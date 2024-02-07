// generated with ast extension for cup
// version 0.8
// 6/1/2024 19:38:8


package mjc.ast;

public class MatchedFor extends Matched {

    private ForLoopMatched ForLoopMatched;

    public MatchedFor (ForLoopMatched ForLoopMatched) {
        this.ForLoopMatched=ForLoopMatched;
        if(ForLoopMatched!=null) ForLoopMatched.setParent(this);
    }

    public ForLoopMatched getForLoopMatched() {
        return ForLoopMatched;
    }

    public void setForLoopMatched(ForLoopMatched ForLoopMatched) {
        this.ForLoopMatched=ForLoopMatched;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ForLoopMatched!=null) ForLoopMatched.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ForLoopMatched!=null) ForLoopMatched.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ForLoopMatched!=null) ForLoopMatched.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MatchedFor(\n");

        if(ForLoopMatched!=null)
            buffer.append(ForLoopMatched.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MatchedFor]");
        return buffer.toString();
    }
}
