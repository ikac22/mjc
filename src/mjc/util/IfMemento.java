package mjc.util;

import java.util.ArrayList;
import java.util.List;

public class IfMemento extends AdrMementoAdaptor{

	List<Integer> beginIfAdrs = new ArrayList<>();
	List<Integer> beginElseAdrs = new ArrayList<>();
	List<Integer> endIfAdrs = new ArrayList<>();
	
	@Override
	public void beginIfAdr(int adr) {
		beginIfAdrs.add(adr);
	}

	@Override
	public void beginElseAdr(int adr) {
		beginElseAdrs.add(adr);
	}

	@Override
	public void endIfAdr(int adr) {
		endIfAdrs.add(adr);
	}

	@Override
	public List<Integer> getBeginIfAdrs() {
		return beginIfAdrs;
	}

	@Override
	public List<Integer> getBeginElseAdrs() {
		return beginElseAdrs;
	}

	@Override
	public List<Integer> getEndIfAdrs() {
		return endIfAdrs;
	}

	
}
