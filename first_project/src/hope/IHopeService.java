package hope;

import java.util.*;

public interface IHopeService {

	List<HopeVO> hopeList();

	int hopeRemoveView(String mem_id, int removeNo);

	HopeVO hopeDetailView(int hopeNo);

	int hopeListAdd(Map<String, String> params);
	
}
