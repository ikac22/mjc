package mjc;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import mjc.ast.*;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;
public class SemanticVisitor extends VisitorAdaptor {
	public static final String ANSI_RED = "\u001B[31m";
	PrintStream stdout;
	
	/* Method indicators */
	Obj currMethod = null;
	int localnum = 0;
	
	/* Namespace indicators */
	Obj currNs = null;
	
	/* class Indicators */
	Obj staticMethod = null;
	Obj currClass = null;
	Obj currClassNs = null;
	Struct currSuperClass = null;
	boolean classScopeOpen = false;
	int classStaticInits = 0;
	int fieldNum = 0;
	
	/* Type indicators */
	Struct currType = null;
	
	/* Scope and var counterr */
	int currScope = 0;
	int staticVars = 0;	
	
	/* Designator indicators */
	Obj currDesignatorPart = null;
	Stack<Obj> currDesignStack = new Stack<>();
	boolean closedCallArgs = false;
	boolean desigListLV = true;
	
	/* ActParam util vars */
	List<Struct> curActParamList = null;
	Stack<List<Struct>> actParamStack = new Stack<>();
	
	/* Static part of class ind*/
	boolean classStatic = false;
	
	/* For loop ind */
	int forCount = 0;
	
	/* error indicator */
	boolean errorDetected = false;
	
	
	Logger log = Logger.getLogger(getClass());
	
	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		int line = (info == null) ? -1: info.getLine();
		StringBuilder msg = new StringBuilder();
		if (line >= 0) 
			msg.append("On line ")
			   .append(line)
			   .append(": ");
	    msg.append(message);
		log.error(msg.toString());
	}
	
	public void report_info(String message, SyntaxNode info) {
		int line = (info == null) ? -1: info.getLine();
		StringBuilder msg = new StringBuilder();
		if (line >= 0) 
			msg.append("On line ")
			   .append(line)
			   .append(": ");
			   
		msg.append(message);
		log.info(msg.toString());
	}
	
	/////////////////////////////////////////
	/** ----------- _Program_ ------------ */
	/////////////////////////////////////////
	
	@Override
	public void visit(ProgName progName) {
		progName.obj = MJTab.insert(Obj.Prog, progName.getName(), MJTab.noType);
		MJTab.openScope();
//		MjTab.tempHelp = Tab.insert(Obj.Var, "#", Tab.intType);
		report_info("progName found: " + progName.getName() + ", scope = " + currScope++, progName);
		log.setLevel(Level.ERROR);
	}
	
	
	@Override
	public void visit(Program prog) {
		// TODO: Check if valid main() method exists in the scope
		
		Tab.chainLocalSymbols(prog.getProgName().obj);
		Tab.closeScope();
		report_info("End of program:" + prog.getProgName().obj.getName() + ", scope = " + --currScope, prog);
	}
	
	/////////////////////////////////////////
	/** ---------- _Namespace_ ----------- */
	/////////////////////////////////////////
	
	/** Get full namespace name of global var/type */
	private String getStaticName(String name) {
		if(currNs != null && currClass == null)
			return nsName(currNs.getName(), name);
		else
			return name;
	}
	
	/** 
	 * 
	 * @param ns - namespace name
	 * @param sn - symbol name
	 * @return String namespace::symbol
	 */
	private String nsName(String ns, String sn) {
		return new StringBuilder()
				.append(ns)
				.append("::")
				.append(sn)
				.toString();
	}
	
	/** Start of namespace definition */
	@Override
	public void visit(NamespaceName nsName) {
		currNs = nsName.obj = MJTab.insert(Obj.Type, nsName.getName(), MJTab.namespace);

		if(currNs == MJTab.noObj)
			report_error("Namespace " + nsName.getName() + 
					" Symbol with that name already declared.", nsName);
			
 		MJTab.openScope(); // Do i need new scope
		report_info("Namespace decl: " + nsName.getName() + ", scope = " + currScope++, nsName);
	}
	
	/** End of namespace definition */
	@Override
	public void visit(Namespace ns) {
		Tab.chainLocalSymbols(currNs);		
		MJTab.closeScope();
		report_info("End of namespace: " + currNs.getName() + ", scope = " + --currScope, ns);
		currNs = null;
	}
	
	
	/////////////////////////////////////////
	/** ------------ _Utils_ ------------- */
	/////////////////////////////////////////	
	
	private Obj searchObjLocals(Obj obj, String symName) {
		Collection<Obj> co = obj.getLocalSymbols();
		if( co == null) return null; 
 		for(Obj o: co)
 			if(o.getName().equals(symName))
 				return o;
 		return null;
	}
	
	private Obj insert(int kind, String name, Struct type ) {
		Obj res = MJTab.insert(kind, name, type);
		if(res == MJTab.noObj) return res;
		
		if(!classScopeOpen && currMethod == null) {
			res.setLevel(0);
//			if(kind == Obj.Var) {
//				res.setAdr(staticVars);
//				staticVars++;
//			}
		}
		if(kind == Obj.Meth) {
			res.setAdr(-1);
		}	
		
		return res;
	}
	
	
	private Obj findNsObject(String nsName, String symName) {
		Obj nsNode = MJTab.find(nsName);
		
		if(nsNode == MJTab.noObj || nsNode.getType() != MJTab.namespace)
			report_error("There is no namespace name " + 
					nsName + 
					" in symbol table!", null);	
		
		Obj symNode = null;
		if(nsNode == currNs) 
			symNode = MJTab.currentScope().findSymbol(symName);
		else 
			symNode = searchObjLocals(nsNode, symName);
		
		if(symNode == null) 
			return MJTab.noObj;
		else 
			return symNode;
	}
	
	private Obj findClassNsObject(String nsName, String symName) {
		Obj nsNode = MJTab.find(nsName);
		
		if(nsNode == MJTab.noObj || 
				nsNode.getType() != MJTab.classNamespace 
				&& nsNode.getType().getKind() != Struct.Class)
			report_error("There is no class with name " + 
					nsName + 
					" in symbol table!", null);	
		
		Obj symNode = null;
		if(currClass != null && currClass == nsNode) 
			symNode = MJTab.currentScope().findSymbol(symName);
		else 
			symNode = searchObjLocals(nsNode, symName);
		
		if(symNode == null) 
			return MJTab.noObj;
		else 
			return symNode;
	}
	
	private Obj findClassNsObject(Obj classNs, String symName) {		
		Obj symNode = null;
		if(currClassNs == classNs)
			if(classScopeOpen)
				symNode = MJTab.currentScope().getOuter().findSymbol(symName);
			else
				symNode = MJTab.currentScope().findSymbol(symName);
		else 
			symNode = searchObjLocals(classNs, symName);
		
		if(symNode == null) 
			return MJTab.noObj;
		else 
			return symNode;
	}
	
	private Obj findClassObject(Struct c, String name) {
		if(!classScopeOpen || currClass.getType() != c) {
			for(Obj o: c.getMembers())
				if(o.getName().equals(name))
					return o;
		}
		else {
			Scope s = (currMethod == null) ? 
					MJTab.currentScope() : MJTab.currentScope().getOuter();
			
			Obj t = s.findSymbol(name);
			return (t == null) ? MJTab.noObj : t;
		}
		return MJTab.noObj;
	}
	
	private Obj printClassMembers(Struct c) {
		for(Obj o: c.getMembers())
			report_info(o.getName(),null);
		return MJTab.noObj;
	}
	
	private boolean typeAssignability(Struct left, Struct right) {
//		report_error("test: " + left + " " + right,null);
		if(left==null || right==null)
			return false;
		if(left == right)
			return true;
		else if(left.getKind() == Struct.Array && 
				right.getKind() == Struct.Array &&
				typeAssignability(left.getElemType(), right.getElemType()))
			return true;
		else if(left.getKind() == Struct.Class && 
				right.getKind() == Struct.Class &&
				typeAssignability(left, right.getElemType())) {
			return true;
		}
		else 
			return false;
	}
	
	/////////////////////////////////////////
	/** ------------ _Types_ ------------- */
	/////////////////////////////////////////
	
	/** Reffering existing type with full namespace path */
	@Override
	public void visit(NamespaceType type) {
		Obj typeNode = findNsObject(type.getNsName(), type.getVarName());
		
		// Object does not exist
		if(typeNode == MJTab.noObj) {
			report_error("There is no declared type of name " + 
					type.getVarName() + 
					" in symbol table!", null);	
		}	
		// Object is not a type
		else if(typeNode.getKind() != Obj.Type ||
				typeNode.getType() == MJTab.namespace) {
			
			report_error("Name " + type.getVarName() + 
					" is not representing a type!", type);
		}
		else if(typeNode.getType() == MJTab.classNamespace) {
			typeNode = findClassNsObject(typeNode, typeNode.getName());
		}
		
		type.struct = typeNode.getType();
	}
	
	/** Reffering existing type with no namespace path */
	@Override
	public void visit(CasualType type) {
		Obj typeNode = MJTab.noObj;
		typeNode = MJTab.find(type.getVarName());
		
		// Object does not exist
		if(typeNode == MJTab.noObj) {
			report_error("There is no declared type of name " + 
					type.getVarName() + 
					" in symbol table!", null);	
		}
		// Object is not a type
		else if(typeNode.getKind() != Obj.Type ||
				typeNode.getType() == MJTab.namespace) {
			report_error("Name " + type.getVarName() + 
					" is not representing a type!", type);	
		}else if(typeNode.getType() == MJTab.classNamespace) {
			typeNode = findClassNsObject(typeNode, typeNode.getName());
		}
		
		type.struct = typeNode.getType();
	}
	
	@Override
	public void visit(ParamType type) {
		currType = type.getType().struct;
	}
	
	/** Method normal type */
	public void visit(MethodRetType retType) {
		retType.struct = retType.getType().struct;
		currType = retType.struct;
		
	}
	
	/** Method type void */
	public void visit(MethodRetVoid retType) {
		retType.struct = MJTab.noType;
		currType = retType.struct;
	}
	
	/////////////////////////////////////////
	/** ----------- _Methods_ ------------ */
	/////////////////////////////////////////
	
	/** Method declaration */
	public void visit(MethodName mName) {
		String name = mName.getName();
		
		mName.obj = insert(Obj.Meth, name, currType);
		System.out.println(name);
		
		if(mName.obj == MJTab.noObj)
			report_error("Method or field with name " + name + 
					" already exists!", null);
		
		currMethod = mName.obj;
		currMethod.setLevel(0);
		MJTab.openScope();
		
		
		if(classScopeOpen) {
			currMethod.setFpPos(1);
			varDecl("this", currClass.getType(), mName);
			currMethod.setLevel(1);
		}
		
		currType = null;
		report_info("Method decl: " + name + ", scope = " + currScope++, mName);
	}
	
	public void visit(FormParam fp) {
		currMethod.setLevel(currMethod.getLevel() + 1);
		currType = null;
	}
	
	/** Method end */
	public void visit(MethodDecl mDecl) {
		Tab.chainLocalSymbols(currMethod);
		Tab.closeScope();
		report_info("End of method: " + currMethod.getName() + ", scope = " + --currScope, mDecl);
		currMethod = null;
		localnum = 0;
	}
	
	/////////////////////////////////////////
	/** --------- _Calls_ ---------- */
	/////////////////////////////////////////
	
	private boolean checkMethodCallTypes(Obj meth) {
		boolean res = true;
		if(meth.getName() == "len")
			if(curActParamList.size() == 1 && 
			   curActParamList.get(0).getKind() == Struct.Array)
				return true;
			else
				return false;
			
		
		Iterator<Struct> actIter = curActParamList.iterator();
		Iterator<Obj>	formIter = meth.getLocalSymbols().iterator();
		int paramNum = 0;
		if(meth.getFpPos() != 1) {
			paramNum = meth.getLevel();
		}
		else {
			paramNum = meth.getLevel() - 1;
			formIter.next();
		}
//		report_error("test " 
//				+ Integer.toString(paramNum)
//				+ " " 
//				+ curActParamList.size(), null);
		if(paramNum != curActParamList.size())
			res = false;
		else {
			for(; paramNum > 0; paramNum--) {
				Obj curForm = formIter.next();
				Struct	curAct  = actIter.next();
//				report_error("test " 
//						+ curForm.getName()
//						+ " "
//						+ curForm.getType()
//						+ " " 
//						+ curAct, null);
				if(!typeAssignability(curForm.getType(), curAct)) {
					res = false;
					break;
				}
			}
		}
			
		closedCallArgs = false;
		curActParamList = actParamStack.pop();
		return res;
	}
	
	public void visit(CallStart cs) { 
		actParamStack.push(curActParamList);
		curActParamList = new ArrayList<>();
	}
	
	public void visit(CallEnd ce) {
		closedCallArgs = true;
	}
	
	public void visit(ActParFirst ap) { 
		curActParamList.add(ap.getExpr().struct);
	}
	
	public void visit(ActParMore ap) { 
		curActParamList.add(ap.getExpr().struct);
	}
	
	/////////////////////////////////////////
	/** ----- _Variables_ ----- */
	/////////////////////////////////////////
	
	private void varDecl(String name, Struct type, SyntaxNode sn) {
		int kind = (classScopeOpen && currMethod == null) ? Obj.Fld : Obj.Var;
		Obj inserted = insert(kind, name, type);
		if(inserted == MJTab.noObj)
			report_error("Cannot declare variable " + name + 
					" because symbol with that name already exists", sn);
		else if(classScopeOpen && currMethod == null){
			report_info("Declared class field " + name, sn);
			inserted.setAdr(fieldNum++);
		}
		else if(currMethod != null) {
			report_info("Declared method var " + name, sn);
			inserted.setAdr(localnum++);
		}
		else {
//			String currName = name;
//			if(currClassNs != null)
//				currName = currClassNs.getName() + "." + currName;
//			else if(currNs != null)
//				currName = currNs.getName() + "::" + currName;
			inserted.setLevel(0);
			inserted.setAdr(staticVars++);
		}	
	}
	
	public void visit(CasualVar v) {
//		if(v.getName().equals("p")) {
//			report_error("test " + v.getName() + ": " + currType , v);
//		}
		varDecl(v.getName(), currType, v);
	}
	
	public void visit(ArrayVar v) {
//		report_error("test " + v.getArrVar().getName() + ": " + currType , v);
		varDecl(v.getArrVar().getName(), new Struct(Struct.Array, currType), v);
	}
	
	public void visit(VarDecl v) {
		currType = null;
	}
	
	public void visit(NoVarDecl nvd) {
		openClassScope();
	}
	
	/////////////////////////////////////////
	/** ---------- _Constants_ ----------- */
	/////////////////////////////////////////
	public void visit(ConstDecl c) {
		currType = null;
	}
	
	
	private Obj constCreate(String name, int val, ConstList cl) {
		Obj res = insert(Obj.Con, name, currType);
		res.setAdr(val);
		if(res == MJTab.noObj)
			report_error("Cannot declare const variable " + name + 
					" because symbol with that name already exists in the scope.", cl);
		else {
			res.setAdr(val);
			report_info("Declared constant: " + name + " with value: " + val, cl);
		}
	
		return res;
	}
	
	private void testConstTypes(Constant con, Struct type) {
		if(currType != type)
			report_error("Cannot declare const variable because " +
					"assigned value type dont match declaration type.", con);
	}
	
	public void visit(ConstListMore cl) {
		constCreate(cl.getName(), cl.getConstant().integer, cl);
	}
	

	public void visit(ConstListStart cl) {
		constCreate(cl.getName(), cl.getConstant().integer, cl);
	}
	
	public void visit(ConstNum cn) {
		testConstTypes(cn ,MJTab.intType);
		cn.integer = cn.getVal();
	}
	
	public void visit(ConstChar cn) {
		testConstTypes(cn ,MJTab.charType);
		int a = cn.getVal().charAt(1);
		cn.integer = a;
		
	}

	public void visit(ConstBool cn) {
		testConstTypes(cn, MJTab.boolType);
		cn.integer = Boolean.parseBoolean(cn.getVal()) ? 1 : 0;
	}
	
	/////////////////////////////////////////
	/** --------- _Designators_ ---------- */
	/////////////////////////////////////////
	
	private void checkDesignatorRef(Obj desigNode, DesignatorRef dr, String name) {
		dr.obj = currDesignatorPart = desigNode;
		if(desigNode == MJTab.noObj) {
			report_error("Designator " + name + " is not defined.", dr);
//		}else if((desigNode.getKind() != Obj.Type || 
//						desigNode.getType() != MJTab.classNamespace && 
//						desigNode.getType().getKind() != Struct.Class) &&
//				  desigNode.getKind() != Obj.Var &&
//				  desigNode.getKind() != Obj.Fld &&
//				  desigNode.getKind() != Obj.Meth &&
//				  desigNode.getKind() != Obj.Con)
		}else if(desigNode.getKind() == Obj.Type && 
				 desigNode.getType() != MJTab.classNamespace &&
				 desigNode.getType().getKind() != Struct.Class)
		{
			report_error("Designator " + name + " is not representing a valid reference.", dr);
			currDesignatorPart = MJTab.noObj;
		}
		// If current scope is of the class that this designator represents
		else if(desigNode.getKind() == Obj.Type && desigNode == currClass) {
			dr.obj = currDesignatorPart = currClassNs;
			report_info("Designator reference " + currClassNs.getName() + " found.", dr);
		}
		else if(classStatic && desigNode.getKind() == Obj.Type && desigNode != currClassNs) {
			report_info("Only current class can be refferd in this scope.", dr);
		}
		else if(classStatic && 
				desigNode.getKind() != Obj.Type && 
				desigNode.getKind() != Obj.Meth) {
			if(MJTab.currentScope().findSymbol(desigNode.getName()) == null)
				report_info("Only current class static fields can be refferd in this scope.", dr);
		}
		else 
			report_info("Designator reference " + name + " found.", dr);
	}
	
	public void visit(Designator desig) {
		if(currDesignatorPart.getKind() == Obj.Type)
			report_error("Cannot use class alone as a designator.", desig);
		desig.obj = currDesignatorPart;
//		if(currDesignatorPart.getKind() == Obj.Elem) report_error("Elem desig: " + currDesignatorPart.getType(), desig);
		currDesignatorPart = null;
	}
	
	public void visit(NamespaceDesignatorRef dr) {
		Obj desigNode = findNsObject(dr.getNsName(), dr.getVarName());
		checkDesignatorRef(desigNode, dr, dr.getNsName() + "::" + dr.getVarName());
	}
	
	public void visit(CasualDesignatorRef dr) {
		Obj desigNode = MJTab.find(dr.getVarName());
		checkDesignatorRef(desigNode, dr, dr.getVarName());
		
	}
	
	public void visit(NoDesigMem dm) {
		dm.obj = currDesignatorPart;
	}
	
	public void visit(DesigField dm) {
		Obj desigPart = MJTab.noObj;
		if(currDesignatorPart.getKind() == Obj.Type) {
			desigPart = findClassNsObject(currDesignatorPart, dm.getName());
			if(desigPart == MJTab.noObj)
				report_error("There is no static field named " 
						+ dm.getName() 
						+ " in class " + currDesignatorPart.getName(), dm);
				
		}
		else if((currDesignatorPart.getKind() == Obj.Var ||
					currDesignatorPart.getKind() == Obj.Fld ||
					currDesignatorPart.getKind() == Obj.Elem) &&
				currDesignatorPart.getType().getKind() == Struct.Class &&
				currDesignatorPart != MJTab.noObj) 
		{	
			
			desigPart = findClassObject(currDesignatorPart.getType(), dm.getName());
			if(desigPart == MJTab.noObj || desigPart.getKind() != Obj.Fld && desigPart.getKind() != Obj.Meth)
				report_error("There is no field/method named " 
						+ dm.getName(), dm);
		}
		
		if(!errorDetected)
			report_info("Designator field " + dm.getName() + " found.", dm);
		dm.obj = currDesignatorPart = desigPart;
	}
	
	public void visit(DesigElem dm) {
		if(currDesignatorPart.getType().getKind() != Struct.Array)
			report_error("Designator " + currDesignatorPart.getName() + " is not of array type!", dm);
//		report_error("test Desig elem: " 
//						+ currDesignatorPart.getName()
//						+ currDesignatorPart.getType().getElemType(), dm);
		dm.obj = currDesignatorPart = new Obj(Obj.Elem, "", currDesignatorPart.getType().getElemType());
	}
	
	/////////////////////////////////////////
	/** --------- _DesigStmts_ ---------- */
	/////////////////////////////////////////
	
	public boolean leftHandVal(Obj desigObj) {
		int kind = desigObj.getKind();
		return kind == Obj.Var ||
				kind == Obj.Elem ||
				kind == Obj.Fld && desigObj.getType() != MJTab.noType;
	}
	
	public void visit(CallDesigStmt df) {
		Obj desigObj = df.getDesignator().obj;
		if(desigObj.getKind() != Obj.Meth) {
			report_error("Designator " + desigObj.getName() + " is not representing a method!", df);
		}
		else if(closedCallArgs) {
			if(!checkMethodCallTypes(desigObj))
				report_error("Invalid method " + desigObj.getName() + " call parameters!", df);
		}
		df.struct = desigObj.getType();
	}
	
	public void visit(AssignDesigStmt ds) {
		if(!typeAssignability(
				ds.getDesignator().obj.getType(), 
				ds.getExpr().struct))
			report_error("Assigning not compatible types.", ds);
	}
	
	public void visit(IncDesigStmt ds) {
		if(!ds.getDesignator().obj.getType().compatibleWith(MJTab.intType))
			report_error("Cannot increment nonint value.", ds);
	}
	
	public void visit(DecDesigStmt ds) {
		if(!ds.getDesignator().obj.getType().compatibleWith(MJTab.intType))
			report_error("Cannot decrement nonint value.", ds);
	}
	
	public void visit(MulAssignDesigStmt ds) {
		Struct srcType = ds.getDesignator1().obj.getType(),
			   dstArrType  = ds.getDesignator().obj.getType();
		Struct dstListType = null;
		if(ds.getDesignatorList().obj != null) {
			dstListType = ds.getDesignatorList().obj.getType();
		}
		if(srcType.getKind() != Struct.Array) 
			report_error("Invalid multiple assignment statement:" +
					" rhs value is not of an array type!", ds);
		if(dstArrType.getKind() != Struct.Array) 
			report_error("Invalid multiple assignment statement:" +
					" lhs array part is not of array type!", ds);
		else if(dstListType != null && !typeAssignability(
										dstListType,
										srcType.getElemType()))
			report_error("Invalid multiple assignment statement:" +
					" lhs list of destinations are not" + 
					" of the same type as rhs array!", ds);
		else if(!typeAssignability(dstArrType,srcType))
			report_error("Invalid multiple assignment statement:" +
					" lhs array is not of the same type as the rhs array", ds);
		else if(dstListType != null && 
				(!leftHandVal(ds.getDesignator().obj) || 
				!leftHandVal(ds.getDesignatorList().obj)) )
			report_error("Invalid multiple assignment statement:" +
					" some of the lhs values are not lvalues.", ds);
			  
	}
	
	public void visit(DesigListDesig dl) {
		Obj dlObj = dl.getDesignatorList().obj,
			dObj  = dl.getDesignator().obj;
		boolean isLv = leftHandVal(dObj);
		
		if(dlObj==null ) {
			dl.obj = new Obj(dObj.getKind(), "", dObj.getType());
			dl.obj.setLevel(1);
		}else {
			if(!leftHandVal(dlObj)) {
				dl.obj = dlObj;
				return;
			}
			if(dlObj.getType().equals(dObj.getType())) { // TODO:
				dl.obj = dlObj;
				dl.obj.setLevel(dl.obj.getLevel() + 1);
			}else {
				dl.obj = MJTab.noObj;
			}
		}
	}
	
	public void visit(DesigListComma dl) {
		
	}
	
	public void visit(NoDesigList dl) {
		
	}
	
	/////////////////////////////////////////
	/** --------- _Expressions_ ---------- */
	/////////////////////////////////////////
	
	public void visit(ExprAdd e) {
		if(e.getTerm().struct != MJTab.intType ||
			e.getExpr().struct != MJTab.intType) {
				report_error("Cannot add nonint types!", e);
		}
		e.struct = MJTab.intType;
	}
	
	public void visit(ExprUminus e) {
		if(e.getUnMinus().integer == 1) {
			if(e.getTerm().struct != MJTab.intType) {
				report_error("Unary minus on nonint types!", e);
			}
			e.struct = MJTab.intType;
		}
		else{
			e.struct = e.getTerm().struct;
		}
		
	}
	
	/////////////////////////////////////////
	/** --------- _Conditions_ ----------- */
	/////////////////////////////////////////
	
	public void visit(OrCond cond) {
		
	}
	
	public void visit(TermCond cond) {
		
	}
	
	public void visit(AndCondTerm ct) {
		
	}
	
	public void visit(FactCondTerm ct) {
		
	}
	
	public void visit(RelopCondFact cf) {
		if(!cf.getExpr().struct.compatibleWith(cf.getExpr1().struct)) {
			report_error("Relational operation between non-equeal types.", cf);
		}
	}
	
	public void visit(ExprCondFact cf) {
		if(!cf.getExpr().struct.compatibleWith(MJTab.boolType)) {
			report_error("Condition factor must be of boolean type.", cf);
		}
	}
	
	/////////////////////////////////////////
	/** --------- _Statements_ ----------- */
	/////////////////////////////////////////
	
	public void visit(MatchedBreak mb) {
		if(forCount == 0) 
			report_error("Break statement can only be used in a loop.", mb);
	}
	
	public void visit(MatchedContinue mc) {
		if(forCount == 0) 
			report_error("Continue statement can only be used in a loop.", mc);
	}
	
	public void visit(ForMatched ms) {
		forCount--;
	}
	
	public void visit(ForUnmatched ms) {
		forCount--;
	}
	
	public void visit(ForStart fs) {
		forCount++;
	}
	
	public void visit(MatchedVoidReturn ms) {
		if(currMethod == null) 
			report_error("Return statement can only be used in methods.", ms);
		else if(!typeAssignability(currMethod.getType(), MJTab.noType))
			report_error("This method has return type!", ms);
	}
	
	public void visit(MatchedExprReturn ms) {
		if(currMethod == null) 
			report_error("Return statement can only be used in methods.", ms);
		else if(!typeAssignability(currMethod.getType(), ms.getExpr().struct))
			report_error("Not matching return value type in method!", ms);
	}
	
	public void visit(MatchedRead ms) {
		if(ms.getDesignator().obj.getType() != MJTab.boolType && 
		   ms.getDesignator().obj.getType() != MJTab.intType &&
		   ms.getDesignator().obj.getType() != MJTab.charType ) {
			report_error("Invalid type for read function!", ms);
		} else if(!leftHandVal(ms.getDesignator().obj))
			report_error("Read parameter not representing an lvalue!", ms);
	}
		
	public void visit(MatchedPrint ms) {
		if(ms.getExpr().struct != MJTab.boolType && 
		   ms.getExpr().struct != MJTab.intType &&
		   ms.getExpr().struct != MJTab.charType ) {
			report_error("Invalid type for print function!", ms);
		}
	}
	
	/////////////////////////////////////////
	/** ------------ _Terms_ ------------- */
	/////////////////////////////////////////
	
	public void visit(CasualFactor t) {
		t.struct = t.getFactor().struct;
	}
	
	public void visit(MulopFactor t) {
		if(t.getTerm().struct != MJTab.intType && 
		   t.getFactor().struct != MJTab.intType) {
			report_error("Cannot multiplicate nonint types!", t);
		}
		t.struct = MJTab.intType;
	}
	
	/////////////////////////////////////////
	/** ----------- _Factors_ ------------ */
	/////////////////////////////////////////
	
	public void visit(CasualDesigFact df) {
		df.struct = df.getDesignator().obj.getType();
	}
	
	public void visit(MethDesigFact df) {
		Obj desigObj = df.getDesignator().obj;
		if(desigObj.getKind() != Obj.Meth) {
			report_error("Designator " + desigObj.getName() + " is not representing a method!", df);
		}
		else if(closedCallArgs) {
			if(!checkMethodCallTypes(desigObj))
				report_error("Invalid method " + desigObj.getName() + " call parameters!", df);
			
		}
		df.struct = desigObj.getType();
	}
	
	public void visit(CasualNewFactor nf) {
		nf.struct = nf.getType().struct; 
	}
	
	public void visit(ArrayNewFactor nf) {
		nf.struct = new Struct(Struct.Array, nf.getType().struct);
	}
	
	public void visit(DesigFactor f) {
		f.struct = f.getDesignatorFactor().struct;
	}
	
	public void visit(NumberFactor f) {
		f.struct = MJTab.intType;
	}
	
	public void visit(CharFactor f) {
		f.struct = MJTab.charType;
	}
	
	public void visit(BoolFactor f) {
		f.struct = MJTab.boolType;
	}
	
	public void visit(AllocFactor f) {
		f.struct = f.getNewFactor().struct;
	}
	
	public void visit(ParenFactor f) {
		f.struct = f.getExpr().struct;
	}
	
	/////////////////////////////////////////
	/** ----------- _Operations_ ------------ */
	/////////////////////////////////////////
	
	public void visit(UnMinusTrue um) {
		um.integer = 1;
	}
	
	public void visit(UnMinusFalse um) {
		um.integer = 0;
	}
	
	
	/////////////////////////////////////////
	/** ----------- _Arrays_ ------------ */
	/////////////////////////////////////////
	
	public void visit(IndexStart is) {
		currDesignStack.push(currDesignatorPart);
		currDesignatorPart = null;
	}
	
	public void visit(IndexEnd ie) {
		currDesignatorPart = currDesignStack.pop();
	}
	
	public void visit(Index ind) {
		if(ind.getExpr().struct != MJTab.intType)
			report_error("Index of array must be integer.", ind);	
	}
	
	
	/////////////////////////////////////////
	/** ----------- _Classes_ ------------ */
	/////////////////////////////////////////
	
	/** Open class scope */
	private void openClassScope() {
		if(currClass != null && !classScopeOpen) {
			MJTab.openScope();
			classScopeOpen = true;
			Obj TVF = MJTab.insert(Obj.Fld, "", MJTab.noType);
			fieldNum++;
			if(currSuperClass != null)
				for(Obj o : currSuperClass.getMembers())
					if(o.getKind() == Obj.Fld && o.getName().length() > 0) {
						Obj f = Tab.insert(Obj.Fld, o.getName(), o.getType());
						f.setAdr(fieldNum++);
					}
			report_info("Pocetak deklaracije polja klase" + ", scope = " + currScope++, null);
			classStatic = false;
		}
	}
	
	/** Close class scope */
	
	private void copySuperClassMethods(){
		if(currSuperClass != null) {
			for(Obj o: currSuperClass.getMembers())
				if(o.getKind() == Obj.Meth) {
					Obj meth = MJTab.currentScope()
								.findSymbol(o.getName());
					if(meth == null) {
						Obj inserted = MJTab.insert(Obj.Meth, o.getName(), o.getType());
						MJTab.openScope();
						for(Obj localvar: o.getLocalSymbols()) {
							Struct vartype = (localvar.getName() == "this") ? 
									currClass.getType() : localvar.getType();
							MJTab.insert(localvar.getKind(), localvar.getName(), vartype);
						}
						inserted.setLevel(o.getLevel());
						inserted.setAdr(-1);
						inserted.setFpPos(1);
						MJTab.chainLocalSymbols(inserted);
						MJTab.closeScope();
					}
					else if(meth.getLevel() != o.getLevel()) {
						String ns = (currNs != null) ? currNs.getName() : "";
						String cns = (currClassNs != null) ? currClassNs.getName() : "";
						report_error("Can't override method " +
									 ns + "::" + cns + "." + meth.getName() +
								     "with different amount of parameters" , null);	
					}
				}
		}
	}
	
	private void closeClassScope() {
		copySuperClassMethods();
		
		Tab.chainLocalSymbols(currClass.getType());
		MJTab.closeScope();
		Tab.chainLocalSymbols(currClassNs);
		MJTab.closeScope();
		
		currClass.setFpPos(classStaticInits);
		currClass = null;
		currSuperClass = null;
		currClassNs = null;
		classScopeOpen = false;
		classStaticInits = 0;
		fieldNum = 0;
	}
	
	
	/** Start of class definition */
	@Override
	public void visit(ClassName cName) {
		
		Obj classNs = MJTab.insert(Obj.Type, cName.getName(), MJTab.classNamespace);
		MJTab.openScope();
		
		Obj declClass = MJTab.insert(Obj.Type, cName.getName(), new Struct(Struct.Class));
		declClass.setLevel(0);
		
 		cName.obj = currClass = declClass;
 		currClassNs = classNs;
 		classStatic = true;
 		
		// TODO: Add virtual table object
		report_info("Class decl: " + cName.getName() + ", scope = " + currScope++, cName);
	}
	
	/** Extends directive */
	@Override
	public void visit(Extends ext) {
		Struct superClass = ext.getType().struct;
		currClass.getType().setElementType(superClass);
//		report_error("test extend " + ext.getType().struct, ext);
		
		if(superClass.getKind() != Struct.Class)
			report_error("Extends of class " + currClass.getName() +
					" is not followed by a valid class type!", null);
		else 
			report_info("Successful extending in class " + currClass.getName(), ext);
		
		currSuperClass = superClass;
		
	}
	
	/** End of class definition 1 */
	@Override
	public void visit(ClassNoInitList c) {
		closeClassScope();
		report_info("Closing class scope: " + ", scope = " + --currScope, c);
		report_info("Closing class namespace scope: " + ", scope = " + --currScope, c);
		report_info("End of no init class: " + c.getClassName().obj.getName(), c);
	}
	
	/** End of class definition 1 */
	@Override
	public void visit(ClassInitList c) {
		closeClassScope();
		report_info("Closing class scope: " + ", scope = " + --currScope, c);
		report_info("Closing class namespace scope: " + ", scope = " + --currScope, c);
		report_info("End of init class: " + c.getClassName().obj.getName(), c);
	}
	
	/////////////////////////////////////////
	/** ----- _Static class members_ ----- */
	/////////////////////////////////////////
	
	/// IDEA: define STATIC INITIALIZERS of class after !class and static class members definition!
	// TODO: static decl
	
	// Static Initializator definition
	public void visit(StaticInitStart sinit) {
		sinit.obj = MJTab.insert(Obj.Meth, "staticInit" + Integer.toString(classStaticInits), MJTab.noType);
		sinit.obj.setAdr(-1);
		sinit.obj.setLevel(0);
		//classStaticInits++;
		MJTab.openScope();
		
		currMethod = sinit.obj;
		report_info("Def static init "+ sinit.obj.getName() + ", scope = " + currScope++, sinit);
	}
	
	// Static initializer end
	public void visit(StaticInitializer sinit) {
		Obj statInit = sinit.getStaticInitStart().obj;
		MJTab.chainLocalSymbols(statInit);
		MJTab.closeScope();
		localnum = 0;
		currMethod = null;
		report_info("End static init "+ statInit.getName() + ", scope = " + --currScope, sinit);
	}
	
	@Override
	public void visit(Static stat) {
		classStatic = true;
	}
	
	
	
	
}
