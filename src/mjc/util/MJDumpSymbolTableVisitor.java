package mjc.util;

import mjc.MJTab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;
import rs.etf.pp1.symboltable.visitors.DumpSymbolTableVisitor;

public class MJDumpSymbolTableVisitor extends DumpSymbolTableVisitor {
	
	@Override
	public void visitObjNode(Obj node) {
//		if(node.getType() == MJTab.namespace) {
//			output.append("[");
//			nextIndentationLevel();
//			for (Obj obj : node.getLocalSymbols()) {
//				output.append(currentIndent.toString());
//				visitObjNode(obj);
//			}
//			previousIndentationLevel();
//			output.append("]");
//		} else if(node.getType() == MJTab.classNamespace) {
//			output.append("[");
//			nextIndentationLevel();
//			for (Obj obj : node.getLocalSymbols()) {
//				output.append(currentIndent.toString());
//				visitObjNode(obj);
//			}
//			previousIndentationLevel();
//			output.append("]");
//		}
//		else 
		super.visitObjNode(node);
	}
	
	@Override
	public void visitStructNode(Struct structToVisit) {
		if(structToVisit == MJTab.boolType) 
			output.append("bool");
		else if(structToVisit == MJTab.namespace) {
			output.append("namespace");
		} else if(structToVisit == MJTab.classNamespace) {
			output.append("class namespace");
		}
		else {
			super.visitStructNode(structToVisit);
		}
	}
	
}
