// generated with ast extension for cup
// version 0.8
// 6/1/2024 19:38:8


package mjc.ast;

public class DesigElem extends Members {

    private Members Members;
    private Index Index;

    public DesigElem (Members Members, Index Index) {
        this.Members=Members;
        if(Members!=null) Members.setParent(this);
        this.Index=Index;
        if(Index!=null) Index.setParent(this);
    }

    public Members getMembers() {
        return Members;
    }

    public void setMembers(Members Members) {
        this.Members=Members;
    }

    public Index getIndex() {
        return Index;
    }

    public void setIndex(Index Index) {
        this.Index=Index;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Members!=null) Members.accept(visitor);
        if(Index!=null) Index.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Members!=null) Members.traverseTopDown(visitor);
        if(Index!=null) Index.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Members!=null) Members.traverseBottomUp(visitor);
        if(Index!=null) Index.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesigElem(\n");

        if(Members!=null)
            buffer.append(Members.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Index!=null)
            buffer.append(Index.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesigElem]");
        return buffer.toString();
    }
}
