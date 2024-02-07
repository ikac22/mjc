package mjc.util;

import java.util.List;

public class AdrMementoAdaptor implements AdrMemento{

	@Override
	public void saveAdr(int adr) { }

	@Override
	public List<Integer> getAdrs() { return null; }

	@Override
	public void beginIf() {}

	@Override
	public void beginFor(int adr) {}

	@Override
	public void beginIfAdr(int adr) {}

	@Override
	public void beginElseAdr(int adr) {}

	@Override
	public void endIfAdr(int adr) {}

	@Override
	public void beginLoopAdr(int adr) {}

	@Override
	public void endLoopAdr(int adr) {}
	
	@Override
	public int getLoopCond() { return -1; };
	
	@Override
	public List<Integer> getBeginIfAdrs() { return null; }

	@Override
	public List<Integer> getBeginElseAdrs() { return null; }

	@Override
	public List<Integer> getEndIfAdrs() { return null; }

	@Override
	public List<Integer> getBeginLoopAdrs() { return null; }

	@Override
	public List<Integer> getEndLoopAdrs() { return null; }
	
}
