package book;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import pdf.BookPdf;

public class IBookServiceImpl implements IBookService{
	private static IBookService service;
	private IBookDao dao;
	
	private IBookServiceImpl() {
		dao = IBookDaoImpl.getInstance();
	}

	public static IBookService getInstance() {
		if(service == null){
			service = new IBookServiceImpl();
		}
		return service;
	}

	@Override
	public int createBook(BookVO bv) {
		return dao.createBook(bv);
	}
	
	@Override
	public BookVO readBook(int book_id) {
		return dao.readBook(book_id);
	}
	
	@Override
	public List<BookVO> bookList() {
		return dao.bookList();
	}

	@Override
	public List<BookVO> bookSearch(String book_name) {
		return dao.bookSearch(book_name);
	}
	
	@Override
	public int updateBook(BookVO bv) {
		return dao.updateBook(bv);
	}

	@Override
	public int deleteBook(int book_no) {
		return dao.deleteBook(book_no);
	}

	@Override
	public void bookExcelAdd(String bPath) {
	//	BookVO bv = null;
		List<Map<Integer,String>> list = new ArrayList<Map<Integer,String>>();
		Map<Integer, String> params =  new HashMap<Integer, String>();
		
		FileInputStream fis;
		try {
			fis = new FileInputStream(""+bPath+"");
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			 
			XSSFSheet sheet = workbook.getSheetAt(0);
			int rowCount = sheet.getPhysicalNumberOfRows();
			for (int rowIndex = 1; rowIndex < rowCount; rowIndex++) {//1인이유 컬럼이름은 필요없기때문
				XSSFRow row = sheet.getRow(rowIndex);
				if (row != null) {
					int cellCount = row.getPhysicalNumberOfCells();
					for (int cellIndex = 0; cellIndex < cellCount; cellIndex++) {
						XSSFCell cell = row.getCell(cellIndex);
						
						String value = "";
						
						switch (cell.getCellType()) {
						case HSSFCell.CELL_TYPE_FORMULA:
							value = cell.getCellFormula();
							break;
						case HSSFCell.CELL_TYPE_NUMERIC:
							value = cell.getNumericCellValue()+"";
							break;
						case HSSFCell.CELL_TYPE_STRING:
							value = cell.getStringCellValue()+"";
							break;
						case HSSFCell.CELL_TYPE_BLANK:
							value = cell.getBooleanCellValue()+"";
							break;
						case HSSFCell.CELL_TYPE_ERROR:
							value = cell.getErrorCellValue()+"";
							break;
							
						}

						params.put(cellIndex, value);
						
					}
				}
				list.add(params);
				params = new HashMap<Integer, String>();///중요
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		dao.bookExcelAdd(list);
	}
	
	@Override
	public void bookExcelout(String fname) {
		List<BookVO> list = dao.bookExcelout();
		
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("BookList");
		
		XSSFRow row = sheet.createRow(0);//첫번째 행
		
		XSSFCell cell = row.createCell(0);
		cell.setCellValue("book_id");
		cell = row.createCell(1);
		cell.setCellValue("book_lgu");
		cell = row.createCell(2);
		cell.setCellValue("book_name");
		cell = row.createCell(3);
		cell.setCellValue("book_author");
		cell = row.createCell(4);
		cell.setCellValue("book_publisher");
		cell = row.createCell(5);
		cell.setCellValue("book_summary");
		
		XSSFRow zeroRow = sheet.getRow(0);// 0번째 행
		zeroRow.getPhysicalNumberOfCells();//0번째 행의 열의수
		
		for (int i = 0; i < list.size(); i++) {//가져온 리스트의 행의 수만큼
				row = sheet.createRow(i+1);//첫번째 행
				cell = row.createCell(0);
				cell.setCellValue(list.get(i).getBook_id());
				cell = row.createCell(1);
				cell.setCellValue(list.get(i).getBook_LGU());
				cell = row.createCell(2);
				cell.setCellValue(list.get(i).getBook_name());
				cell = row.createCell(3);
				cell.setCellValue(list.get(i).getBook_author());
				cell = row.createCell(4);
				cell.setCellValue(list.get(i).getBook_publisher());
				cell = row.createCell(5);
				cell.setCellValue(list.get(i).getBook_summary());
		}
		try {
			FileOutputStream fos = new FileOutputStream(".\\file\\"+fname+".xlsx");
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
	public void bookPdfOut(String fname) {
		List<BookVO> bookList = dao.bookList();
		
		Map<String, Object> params = new HashMap<>();
		params.put("fname", fname);
		params.put("bookList", bookList);
		
		BookPdf.pdfRun(params);
	}
}
