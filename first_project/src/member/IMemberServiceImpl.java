package member;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import pdf.MemberPdf;

public class IMemberServiceImpl implements IMemberService{
	private static IMemberService service;
	private IMemberDao dao;
	
	private IMemberServiceImpl() {
		dao = IMemberDaoImpl.getInstance();
	}

	public static IMemberService getInstance() {
		if(service == null){//아직 IMEMBERSERVICE가 안만들어졌으면
			service = new IMemberServiceImpl(); //서비스임플객체 새로만들자 (처음이자 마지막)
		}
		return service;
	}
	
	/**
	 * 로그인 입력받은거 MemberVO와 비교
	 * @param Map<> in, Map<> pw
	 * @return MemberVO //MemberVO nowmember를 null에서 로그인한 회원으로 바꿔주기위해
	 * @since 2020-11-04
	 */
	@Override
	public MemberVO loginMatch(Map<String, String> params) {
		
		return dao.loginMatch(params);
	}
	
	/**
	 * 아이디중복체크. 중복값이 있으면 증가된 count가 리턴된다
	 * @param mem_id
	 * @return int
	 * @author 조애슬
	 * @since 2020-11-06 
	 */
	@Override
	public int idUniqCheck(String mem_id) {
		
		return dao.idUniqCheck(mem_id);
	}
	
//회원 CRUD
	@Override
	public int createMember(MemberVO params) {
		return dao.createMember(params);
	}
	
	@Override
	public MemberVO readMember(String mem_id) {
		return dao.readMember(mem_id);
	}
	
	@Override
	public List<MemberVO> memberList() {
		return dao.memberList();
	}
	
	@Override
	public int updateMember(Map<String, String> myInfo) {
		return dao.updateMember(myInfo);
	}
	
	@Override
	public int deleteMember(String mem_id) {
		return dao.deleteMember(mem_id);
	}
	
	
	@Override
	public void memberExcelout(String mname) {
		List<MemberVO> ml = dao.memberList();
		
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("MemberList");
		
		XSSFRow row = sheet.createRow(0);//첫번째 행
		
		XSSFCell cell = row.createCell(0);
		cell.setCellValue("mem_id");
		cell = row.createCell(1);
		cell.setCellValue("mem_pw");
		cell = row.createCell(2);
		cell.setCellValue("mem_name");
		cell = row.createCell(3);
		cell.setCellValue("mem_bir");
		cell = row.createCell(4);
		cell.setCellValue("mem_email");
		cell = row.createCell(5);
		cell.setCellValue("mem_tel");		
		cell = row.createCell(6);
		cell.setCellValue("rent_count");
		
		XSSFRow zeroRow = sheet.getRow(0);// 0번째 행
		zeroRow.getPhysicalNumberOfCells();//0번째 행의 열의수
		
		for (int i = 0; i < ml.size(); i++) {//가져온 리스트의 행의 수만큼
//			for (int j = 0; j <= cellCount; j++) {
				row = sheet.createRow(i+1);//첫번째 행
				cell = row.createCell(0);
				cell.setCellValue(ml.get(i).getMem_id());
				cell = row.createCell(1);
				cell.setCellValue(ml.get(i).getMem_pw());
				cell = row.createCell(2);
				cell.setCellValue(ml.get(i).getMem_name());
				cell = row.createCell(3);
				cell.setCellValue(ml.get(i).getMem_bir());
				cell = row.createCell(4);
				cell.setCellValue(ml.get(i).getMem_email());
				cell = row.createCell(5);
				cell.setCellValue(ml.get(i).getMem_tel());
				cell = row.createCell(6);
				cell.setCellValue(ml.get(i).getRent_count());
//			}
		}
		try {
			FileOutputStream fos = new FileOutputStream(".\\file\\"+mname+".xlsx");
			workbook.write(fos);
			fos.close();
		
			System.out.println("엑셀 생성 성공");
		} catch (FileNotFoundException e) {
			System.out.println("엑셀 생성 실패");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("엑셀 생성 실패");
			e.printStackTrace();
		}
	}

	@Override
	public void memberPdfOut(String fname) {
		List<MemberVO> memList = dao.memberList();
		
		Map<String, Object> params = new HashMap<>();
		params.put("fname", fname);
		params.put("memList", memList);
		
		MemberPdf.pdfRun(params);
	}
}
