package mjc;


import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class MJTab extends Tab {
	public static final Struct boolType = new Struct(Struct.Bool);
	public static final Struct namespace = new Struct(Struct.Class);
	
	public static final Struct classNamespace = new Struct(Struct.Class);
	
	public static final Obj startMethod = new Obj(Obj.Meth, "start", noType);
	
	static{
		
	}
	
	public static void mjTabInit() {
		init();
		insert(MJObj.Type, "bool", boolType);
	}
}
