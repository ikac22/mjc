package mjc.util;

import java.util.List;

public interface AdrMemento {
	public void beginIf();
	public void beginFor(int adr);
	public void beginIfAdr(int adr);
	public void beginElseAdr(int adr);
	public void endIfAdr(int adr);
	public void beginLoopAdr(int adr);
	public void endLoopAdr(int adr);
	public int getLoopCond();
	public void saveAdr(int adr);
	public List<Integer> getAdrs();
	public List<Integer> getBeginIfAdrs();
	public List<Integer> getBeginElseAdrs();
	public List<Integer> getEndIfAdrs();
	public List<Integer> getBeginLoopAdrs(); 
	public List<Integer> getEndLoopAdrs();
}
