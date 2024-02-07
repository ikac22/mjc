// generated with ast extension for cup
// version 0.8
// 6/1/2024 19:38:8


package mjc.ast;

public class AllocFactor extends Factor {

    private NewFactor NewFactor;

    public AllocFactor (NewFactor NewFactor) {
        this.NewFactor=NewFactor;
        if(NewFactor!=null) NewFactor.setParent(this);
    }

    public NewFactor getNewFactor() {
        return NewFactor;
    }

    public void setNewFactor(NewFactor NewFactor) {
        this.NewFactor=NewFactor;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(NewFactor!=null) NewFactor.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(NewFactor!=null) NewFactor.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(NewFactor!=null) NewFactor.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AllocFactor(\n");

        if(NewFactor!=null)
            buffer.append(NewFactor.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AllocFactor]");
        return buffer.toString();
    }
}
