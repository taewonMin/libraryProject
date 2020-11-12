package blackList;

import java.util.List;

import member.MemberVO;

public interface IBlackListService {

	boolean createBlackList(String id);

	List<BlackListVO> blackListList();

	boolean blackDeltleMethod(String id);

}
