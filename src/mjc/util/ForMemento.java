package mjc.util;

import java.util.ArrayList;
import java.util.List;

public class ForMemento extends AdrMementoAdaptor{
	int forCondAdr = -1; 
	List<Integer> beginLoopAdrs = new ArrayList<>();
	List<Integer> endLoopAdrs = new ArrayList<>();
	List<Integer> continueAdrs = new ArrayList<>();
	
	@Override
	public void beginFor(int adr) {
		forCondAdr = adr;
	}
	
	@Override
	public void beginLoopAdr(int adr){
		beginLoopAdrs.add(adr);
	}
	
	@Override
	public void endLoopAdr(int adr){
		endLoopAdrs.add(adr);
	}
	
	public void continueLoopAdr(int adr) {
		continueAdrs.add(adr);
	}

	public List<Integer> getContinueAdrs(){
		return continueAdrs;
	}
	
	@Override
	public List<Integer> getBeginLoopAdrs() {
		return beginLoopAdrs;
	}

	@Override
	public List<Integer> getEndLoopAdrs() {
		return endLoopAdrs;
	}
	
	@Override
	public int getLoopCond() {
		return forCondAdr;
	}
	
}
