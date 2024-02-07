// generated with ast extension for cup
// version 0.8
// 6/1/2024 19:38:8


package mjc.ast;

public class ClassInitListDecl extends ClassDecl {

    private ClassInitList ClassInitList;

    public ClassInitListDecl (ClassInitList ClassInitList) {
        this.ClassInitList=ClassInitList;
        if(ClassInitList!=null) ClassInitList.setParent(this);
    }

    public ClassInitList getClassInitList() {
        return ClassInitList;
    }

    public void setClassInitList(ClassInitList ClassInitList) {
        this.ClassInitList=ClassInitList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ClassInitList!=null) ClassInitList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ClassInitList!=null) ClassInitList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ClassInitList!=null) ClassInitList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassInitListDecl(\n");

        if(ClassInitList!=null)
            buffer.append(ClassInitList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassInitListDecl]");
        return buffer.toString();
    }
}
