package mjc.util;

import java.util.List;
import java.util.Stack;

public class ConditionalMemento extends AdrMementoAdaptor {

	Stack<ForMemento> forStack = new Stack<>();
	Stack<IfMemento>  ifStack  = new Stack<>();
	ForMemento currFor = null;
	IfMemento currIf = null;
	
	@Override
	public void beginIf() {
		if(currIf != null) {
			ifStack.push(currIf);
		}
		currIf = new IfMemento();
	}

	public void endIf() { 
		if(!ifStack.empty())
		currIf = ifStack.pop(); }
	
	@Override
	public void beginFor(int adr) {
		if(currFor != null) {
			forStack.push(currFor);
		}
		currFor = new ForMemento();
		currFor.beginFor(adr);
	}

	public void endFor() { 
		if(!forStack.empty())
		currFor = forStack.pop(); }
	
	@Override
	public void beginIfAdr(int adr) { currIf.beginIfAdr(adr); }

	@Override
	public void beginElseAdr(int adr) { currIf.beginElseAdr(adr); } 

	@Override
	public void endIfAdr(int adr) { currIf.endIfAdr(adr); }

	@Override
	public void beginLoopAdr(int adr) { currFor.beginLoopAdr(adr); }

	@Override
	public void endLoopAdr(int adr) { currFor.endLoopAdr(adr); }

	public void continueLoopAdr(int adr) { currFor.continueLoopAdr(adr); }
	
	@Override
	public int getLoopCond() { return currFor.getLoopCond(); }
	
	public List<Integer> getContinueAdrs() { return currFor.getContinueAdrs(); }

	@Override
	public List<Integer> getBeginIfAdrs() { return currIf.getBeginIfAdrs(); }

	@Override
	public List<Integer> getBeginElseAdrs() { return currIf.getBeginElseAdrs(); }

	@Override
	public List<Integer> getEndIfAdrs() { return currIf.getEndIfAdrs(); }

	@Override
	public List<Integer> getBeginLoopAdrs() { return currFor.getBeginLoopAdrs(); }

	@Override
	public List<Integer> getEndLoopAdrs() { return currFor.getEndLoopAdrs(); }
	
	
	
}
