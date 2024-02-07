// generated with ast extension for cup
// version 0.8
// 6/1/2024 19:38:8


package mjc.ast;

public class ClassNoInitListDecl extends ClassDecl {

    private ClassNoInitList ClassNoInitList;

    public ClassNoInitListDecl (ClassNoInitList ClassNoInitList) {
        this.ClassNoInitList=ClassNoInitList;
        if(ClassNoInitList!=null) ClassNoInitList.setParent(this);
    }

    public ClassNoInitList getClassNoInitList() {
        return ClassNoInitList;
    }

    public void setClassNoInitList(ClassNoInitList ClassNoInitList) {
        this.ClassNoInitList=ClassNoInitList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ClassNoInitList!=null) ClassNoInitList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ClassNoInitList!=null) ClassNoInitList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ClassNoInitList!=null) ClassNoInitList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassNoInitListDecl(\n");

        if(ClassNoInitList!=null)
            buffer.append(ClassNoInitList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassNoInitListDecl]");
        return buffer.toString();
    }
}
