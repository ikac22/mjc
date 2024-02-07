package mjc.util;

import java.util.ArrayList;
import java.util.List;

public class NormalAdrMemento extends AdrMementoAdaptor {
	List<Integer> adrs = new ArrayList<>();
	
	@Override
	public void saveAdr(int adr) {
		adrs.add(adr);
	}
	
	@Override
	public List<Integer> getAdrs() {
		return adrs;
	}
}
