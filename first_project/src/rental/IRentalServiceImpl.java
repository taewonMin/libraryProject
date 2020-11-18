package rental;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import pdf.RentalPdf;
import book.BookVO;
import book.IBookService;
import book.IBookServiceImpl;

public class IRentalServiceImpl implements IRentalService{
	private static IRentalService service;
	private IRentalDao dao;
	private IBookService ibs = IBookServiceImpl.getInstance();
	
	private IRentalServiceImpl() {
		dao = IRentalDaoImpl.getInstance();
	}

	public static IRentalService getInstance() {
		if(service == null){
			service = new IRentalServiceImpl();
		}
		return service;
	}

	@Override
	public RentalVO createRental(Map<String, Object> map) {
		return dao.createRental(map);
	}

	@Override
	public RentalVO readRentalVO(int book_id) {
		return dao.readRentalVO(book_id);
	}

	@Override
	public List<RentalVO> readRentalList(String mem_id) {
		return dao.readRentalList(mem_id);
	}

	@Override
	public int deleteRental(int book_id) {
		return dao.deleteRental(book_id);
	}
	
	@Override
	public void rentalExcelout(Map<String, String> params) {
		String mem_id = params.get("mem_id");
		String rPath = params.get("rPath");
		List<RentalVO> list = readRentalList(mem_id);
		
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("RentalList");
		
		XSSFRow row = sheet.createRow(0);//첫번째 행
		
		XSSFCell cell = row.createCell(0);
		cell.setCellValue("rental_id");
		cell = row.createCell(1);
		cell.setCellValue("book_name");
		cell = row.createCell(2);
		cell.setCellValue("book_author");
		cell = row.createCell(3);
		cell.setCellValue("book_publisher");
		cell = row.createCell(4);
		cell.setCellValue("rental_start");
		cell = row.createCell(5);
		cell.setCellValue("rental_end");
		cell = row.createCell(6);
		cell.setCellValue("book_id");
		cell = row.createCell(7);
		cell.setCellValue("mem_id");
		
		XSSFRow zeroRow = sheet.getRow(0);// 0번째 행
		zeroRow.getPhysicalNumberOfCells();//0번째 행의 열의수
		
		for (int i = 0; i < list.size(); i++) {//가져온 리스트의 행의 수만큼
			BookVO bv = ibs.readBook(list.get(i).getBook_id());
			row = sheet.createRow(i+1);//첫번째 행
			cell = row.createCell(0);
			cell.setCellValue(list.get(i).getRental_id());
			cell = row.createCell(1);
			cell.setCellValue(bv.getBook_name());
			cell = row.createCell(2);
			cell.setCellValue(bv.getBook_author());
			cell = row.createCell(3);
			cell.setCellValue(bv.getBook_publisher());
			cell = row.createCell(4);
			cell.setCellValue(list.get(i).getRental_start());
			cell = row.createCell(5);
			cell.setCellValue(list.get(i).getRental_end());
			cell = row.createCell(6);
			cell.setCellValue(list.get(i).getBook_id());
			cell = row.createCell(7);
			cell.setCellValue(list.get(i).getMem_id());
		}
		
		try {
			FileOutputStream fos = new FileOutputStream(".\\file\\"+rPath+".xlsx");
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
	public void createRentalPdf(Map<String, String> params) {
		RentalPdf.pdfRun(params);
	}
}
