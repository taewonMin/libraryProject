package blackList;

import java.util.List;

import member.MemberVO;

public interface IBlackListDao {

	boolean blackDeltleMethod(String id);

	List<BlackListVO> blackList();

}
