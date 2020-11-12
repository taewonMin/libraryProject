package blackList;

import java.util.List;

import member.MemberVO;

public interface IBlackListService {

	boolean createBlackList(String id);

	List<BlackListVO> blackList();

	boolean blackDeltleMethod(String id);

}
