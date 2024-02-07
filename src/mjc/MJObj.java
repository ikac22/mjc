package mjc;

import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class MJObj extends Obj {
	public static final int NameSpace = 7;
	
	public MJObj(int kind, String name, Struct type, int adr, int level) {
		super(kind, name, type, adr, level);
	}
}
