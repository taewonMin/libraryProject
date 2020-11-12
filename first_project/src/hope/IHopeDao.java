package hope;

import java.util.List;
import java.util.Map;

public interface IHopeDao {


	List<HopeVO> hopeList();

	int hopeListAdd(Map<String, String> params);

	HopeVO hopeDetailView(int hopeNo);

	int hopeRemoveView(String mem_id, int hopeNo);
	
}
