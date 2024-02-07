// generated with ast extension for cup
// version 0.8
// 6/1/2024 19:38:8


package mjc.ast;

public class DesigField extends Members {

    private Members Members;
    private String name;

    public DesigField (Members Members, String name) {
        this.Members=Members;
        if(Members!=null) Members.setParent(this);
        this.name=name;
    }

    public Members getMembers() {
        return Members;
    }

    public void setMembers(Members Members) {
        this.Members=Members;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Members!=null) Members.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Members!=null) Members.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Members!=null) Members.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesigField(\n");

        if(Members!=null)
            buffer.append(Members.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+name);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesigField]");
        return buffer.toString();
    }
}
