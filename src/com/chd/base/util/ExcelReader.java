package com.chd.base.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HeaderFooter;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFPrintSetup;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.extensions.XSSFHeaderFooter;
import org.nutz.lang.Strings;

import com.chd.base.SessionManager;
import com.chd.base.exception.SysException;

/**
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-4-29 下午1:34:16
 * @Company: 智慧云康（北京）数据科教有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
public class ExcelReader {

	private static Logger logger = Logger.getLogger(ExcelReader.class);
	private final static Pattern patStrNum = Pattern.compile("[A-Z]+[0-9\\.]+");
	private final static Pattern patNum = Pattern.compile("[^0-9]");
	private Workbook wb = null;// book [includes sheet]

	private Sheet sheet = null;

	private Row row = null;

	private int sheetNum = 0; // 第sheetnum个工作表

	private int rowNum = 0;

	private FileInputStream fis = null;
	private InputStream inputStream  = null;

	private File file = null;

	private String fileName = null;

	public ExcelReader() {
	}
	public ExcelReader(String fileName) {
		this.fileName = fileName;
	}

	public ExcelReader(File file) {
		this.file = file;
		this.fileName = file.getName();
	}
	public ExcelReader(File file, String fileName) {
		this.file = file;
		this.fileName = fileName;
	}

	/**
	 * sheetNum下的记录行数
	 * 
	 * @return int
	 */
	public int getRowCount() {
		if (wb == null) {
			logger.info("=============>WorkBook为空");
		}
		Sheet sheet = wb.getSheetAt(this.sheetNum);
		int rowCount = -1;
		rowCount = sheet.getLastRowNum();
		return rowCount;
	}

	/**
	 * 读取指定sheetNum的rowCount
	 * 
	 * @param sheetNum
	 * @return int
	 */
	public int getRowCount(int sheetNum) {
		Sheet sheet = wb.getSheetAt(sheetNum);
		int rowCount = -1;
		rowCount = sheet.getLastRowNum();
		return rowCount;
	}

	/**
	 * 返回sheet表数目
	 * 
	 * @return int
	 */
	public int getSheetCount() {
		int sheetCount = -1;
		sheetCount = wb.getNumberOfSheets();
		return sheetCount;
	}

	/**
	 * 读取excel文件获得HSSFWorkbook对象
	 */
	public void open() throws IOException {
		fis = new FileInputStream(file);
		
		if (fileName.endsWith(".xls")) {
			wb = new HSSFWorkbook(new POIFSFileSystem(fis));
		} else {
			wb = new XSSFWorkbook(fis);
		}

		fis.close();
	}
	/**
	 * 读取excel文件获得HSSFWorkbook对象
	 */
	public void open(InputStream fis) throws IOException {
		if (fileName.endsWith(".xls")) {
			wb = new HSSFWorkbook(new POIFSFileSystem(fis));
		} else {
			wb = new XSSFWorkbook(fis);
		}
		fis.close();
	}
	/**
	 * 得到指定行的内容
	 * 
	 * @param lineNum
	 * @return String[]
	 */
	public String[] readExcelLine(int lineNum) {
		return readExcelLine(this.sheetNum, lineNum);
	}

	/**
	 * 指定工作表和行数的内容
	 * 
	 * @param sheetNum
	 * @param lineNum
	 * @return String[]
	 */
	public String[] readExcelLine(int sheetNum, int lineNum) {
		if (sheetNum < 0 || lineNum < 0) {
			return null;
		}
		String[] strExcelLine = null;
		try {
			sheet = wb.getSheetAt(sheetNum);
			row = sheet.getRow(lineNum);

			int cellCount = row.getLastCellNum();
			strExcelLine = new String[cellCount];
			for (int i = 0; i < cellCount; i++) {
				strExcelLine[i] = readStringExcelCell(lineNum, i);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return strExcelLine;
	}

	/**
	 * 读取指定列的内容
	 * 
	 * @param cellNum
	 * @return String
	 */
	public String readStringExcelCell(int cellNum) {
		return readStringExcelCell(this.rowNum, cellNum);
	}

	/**
	 * 指定行和列编号的内容
	 * 
	 * @param rowNum
	 * @param cellNum
	 * @return String
	 */
	public String readStringExcelCell(int rowNum, int cellNum) {
		return readStringExcelCell(this.sheetNum, rowNum, cellNum);
	}

	/**
	 * 指定工作表、行、列下的内容
	 * 
	 * @param sheetNum
	 * @param rowNum
	 * @param cellNum
	 * @return String
	 */
	public String readStringExcelCell(int sheetNum, int rowNum, int cellNum) {

		if (sheetNum < 0 || rowNum < 0) {
			return "";
		}
		String strExcelCell = "";
		try {
			sheet = wb.getSheetAt(sheetNum);
			row = sheet.getRow(rowNum);
			if (row.getCell(cellNum) != null) {
				switch (row.getCell(cellNum).getCellType()) {
					case Cell.CELL_TYPE_FORMULA:
						strExcelCell = "FORMULA ";
						break;
					case Cell.CELL_TYPE_NUMERIC:
						if (DateUtil.isCellDateFormatted(row.getCell(cellNum))) {
							double d = row.getCell(cellNum).getNumericCellValue();
							Date date = HSSFDateUtil.getJavaDate(d);
							SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");
							strExcelCell = sFormat.format(date);
							break;
						} else {
							short dataFormatIndex = row.getCell(cellNum).getCellStyle().getDataFormat();
							double d = row.getCell(cellNum).getNumericCellValue();
							DecimalFormat df1;

							switch (dataFormatIndex) {
								case 0:
									strExcelCell = getFormatNumber(d);
									break;
								case 1:
									df1 = new DecimalFormat("0");
									strExcelCell = df1.format(d);
									break;
								case 2:
									df1 = new DecimalFormat("0.00");
									strExcelCell = df1.format(d);
									break;
								case 3:
									df1 = new DecimalFormat("#,###");
									strExcelCell = df1.format(d);
									break;
								case 4:
									df1 = new DecimalFormat("#,##0.00");
									strExcelCell = df1.format(d);
									break;
								default:
									strExcelCell = getFormatNumber(d);
									break;
							}
						}
						break;
					case Cell.CELL_TYPE_STRING:
						strExcelCell = row.getCell(cellNum).getStringCellValue();
						break;
					case Cell.CELL_TYPE_BLANK:
						strExcelCell = "";
						break;
					default:
						strExcelCell = "";
						break;
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		// 如果读取的是科学计数法的格式，则转换为普通格式 20060626
		if (null != strExcelCell && strExcelCell.indexOf(".") != -1 && strExcelCell.indexOf("E") != -1) {
			DecimalFormat df = new DecimalFormat();
			try {
				strExcelCell = df.parse(strExcelCell).toString();
			}
			catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return strExcelCell;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

	public void setSheetNum(int sheetNum) {
		this.sheetNum = sheetNum;
	}

	/**
	 * 转换科学记数法为一般字符串；
	 * 
	 * @param srcNum
	 *            科学记数法表式的值；
	 * @return 转换后的一般字符串
	 */
	private String getFormatNumber(double srcNum) {
		String dataStr = String.valueOf(srcNum).toUpperCase();
		if (dataStr.indexOf('E') != -1) { // 转换科学记数法为一般字符串
			int len = dataStr.length() - dataStr.indexOf(".") - 1;
			len -= Integer.valueOf(dataStr.substring(dataStr.indexOf("E") + 1)).intValue() + 2;
			dataStr = "0.";
			for (int i = 0; i < len; i++) {
				dataStr += "0";
			}
			DecimalFormat df = new DecimalFormat(dataStr);
			dataStr = df.format(srcNum);

			dataStr = dataStr.replaceAll("\\.$", ""); // 处理那些没有小数的数字(因为默认是*.0)
		}

		return dataStr.replaceAll(".0$", "");
	}

	/**
	 * @param strFile
	 *            字符串文件路径
	 * @return list集合 : list[Object]
	 */
	public static List<String[]> readExcelList(String strFile, String fileName) {
		File file = new File(strFile);
		return readExcelList(file, fileName);
	}

	/**
	 * @param file
	 *            文件
	 * @return list集合 : list[Object]
	 */
	public static List<String[]> readExcelList(File file, String fileName) {
		List<String[]> list = new ArrayList<String[]>();
		return readExcelList(file, list, fileName);
	}
	
	/**
	 * @param file
	 *            文件
	 * @return list集合 : list[Object]
	 */
	public static List<String[]> readExcelList(String filePath) {
		List<String[]> list = new ArrayList<String[]>();
		return readExcelList(filePath, list);
	}
	/**
	 * @param file
	 *            文件
	 * @return list集合 : list[Object]
	 */
	public static List<String[]> readExcelList(InputStream  inputStream,String filename) {
		List<String[]> list = new ArrayList<String[]>();
		return readExcelList(inputStream,filename, list);
	}


	/**
	 * @param file
	 *            文件
	 * @param list
	 *            集合:list[Object]
	 * @return 集合list[Object]
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<String[]> readExcelList(File file, List<String[]> list, String fileName) {
		ExcelReader readExcel = new ExcelReader(file, fileName);
		try {
			readExcel.open();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		for (int k = 0; k < readExcel.getSheetCount(); k++) {
			readExcel.setSheetNum(k); // 设置读取索引为k的工作表
			// 总行数
			int count = readExcel.getRowCount();
			
			if (count > 100000 || count<1) {
				break;
			}
			for (int i = 0; i <= count; i++) {
				list.add(readExcel.readExcelLine(i));
			}
		}
			
		return list;
	}
	
	/**
	 * @param file
	 *            文件
	 * @param list
	 *            集合:list[Object]
	 * @return 集合list[Object]
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<String[]> readExcelList(String filePath, List<String[]> list) {
		ExcelReader readExcel = new ExcelReader(new File(filePath));
		try {
			readExcel.open();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		for (int k = 0; k < readExcel.getSheetCount(); k++) {
			readExcel.setSheetNum(k); // 设置读取索引为k的工作表
			// 总行数
			int count = readExcel.getRowCount();
			
			if (count > 100000 || count<1) {
				break;
			}
			for (int i = 0; i <= count; i++) {
				list.add(readExcel.readExcelLine(i));
			}
		}
		
		return list;
	}
	/**
	 * @param inputStream 
	 *            IO流
	 * @param 结果集合list
	 *            集合:list[Object]
	 * @return 集合list[Object]
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<String[]> readExcelList(InputStream inputStream,String filename, List<String[]> list) {
		ExcelReader readExcel = new ExcelReader(filename);
		try {
			readExcel.open(inputStream);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		for (int k = 0; k < readExcel.getSheetCount(); k++) {
			readExcel.setSheetNum(k); // 设置读取索引为k的工作表
			// 总行数
			int count = readExcel.getRowCount();
			
			if (count > 100000 || count<1) {
				break;
			}
			for (int i = 0; i <= count; i++) {
				String[] string=	readExcel.readExcelLine(i);
				System.out.println(string);
				if(!string[0].toString().equals("")){
					list.add(string);
				}
				
			}
		}
		
		return list;
	}

	/**
	 * @param strFile
	 *            字符串文件路径
	 * @return excel结果集
	 */
	public static ExcelReader readExcel(String strFile) {
		File file = new File(strFile);
		return readExcel(file);
	}
	/**
	 * @param strFile
	 *            字符串文件路径
	 * @return excel结果集
	 */
	public static ExcelReader readExcel(String strFile, String fileName) {
		File file = new File(strFile);
		return readExcel(file, fileName);
	}
	/**
	 * @param file
	 *            文件
	 * @return excel结果集
	 */
	public static ExcelReader readExcel(File file, String fileName) {
		ExcelReader readExcel = new ExcelReader(file, fileName);
		try {
			readExcel.open();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return readExcel;
	}
	/**
	 * @param file
	 *            文件
	 * @return excel结果集
	 */
	public static ExcelReader readExcel(File file) {
		ExcelReader readExcel = new ExcelReader(file);
		try {
			readExcel.open();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return readExcel;
	}
	/**
	 * @param args
	 */
//	public static void main(String args[]) {
//
//		//List<String[]> list = ExcelReader.readExcelList("D:/1.xlsx","1.xlsx");
//		List<String[]> list = ExcelReader.readExcelList("D:/1.xlsx");
//		List<String[]> list1 = ExcelReader.readExcelList("D:/2.xls");
//		//List<String[]> list = ExcelReader.readExcelList("D:/2.xls","2.xls");
//		//
//		for (String[] object : list) {
//			for (String obj : object) {
//				System.out.print(obj);
//				System.out.print(" ");
//			}
//			System.out.println();
//		}
//		for (String[] object : list1) {
//			for (String obj : object) {
//				System.out.print(obj);
//				System.out.print(" ");
//			}
//			System.out.println();
//		}
//	}
	
	/**
	 * @Description
	 * 2016/11/16 lxj
	 * 解决读取excl角标越界问题 
	 * 读取excl 校验列是否为空
	 * @param  
	 * String[] arr excl 某行的列数组(读取到的列数)
	 * int currentColunmNum 当前列,从0开始
	 * @return boolean
	*/
	public static boolean validExclColunm(String[] arr,int currentColunmNum){
		
		if(arr == null){
			
			return false;
		}
		
		if(arr.length > currentColunmNum){//实际读取到的列数 > 当前列
			
			if(Strings.isEmpty(arr[currentColunmNum])){
				return false;
				
			}else{
				return true;
			}
		}else{
			return false;
		}
	}
	
	/**
	 * @Description
	 * 2016/11/16 lxj
	 * 解决读取excl角标越界问题 
	 * 读取excl 校验列是否为空
	 * @param  
	 * String[] arr excl 某行的列数组(读取到的列数)
	 * int currentColunmNum 当前列,从0开始
	 * @return boolean
	*/
	public static boolean validExceLColunm(String[] arr, int colunmNum) {

		if (arr.length - 1 >= colunmNum) {

			if (StringTool.isNotBlank(arr[colunmNum])
					|| arr[colunmNum].length() != 0 || !("").equals(arr[colunmNum])) {

				return true;

			} else {

				return false;
			}

		} else {

			return false;
		}

	}
	
	
	/**
	 * 根据excel模板动态生成excel2003，PageOffice单据模板使用
	 */
	public static Workbook getWorkBookForm(String fromFilePath,String toFilePath,int page,Map<String,String> paraMap) throws Exception{  
		
		File fromFile = new File(fromFilePath); 
		
        //判断文件是否存在
		if(!fromFile.exists()){
			logger.error("没有维护打印模板："+fromFilePath);  
            throw new FileNotFoundException("没有维护打印模板！");  
		}
		
		Workbook workBook=null;
        try {  
            //获取excel文件的io流  
        	InputStream is = new FileInputStream(fromFilePath); 
        	//创建2003Workbook工作薄对象，表示整个excel  
        	workBook = new HSSFWorkbook(is);
            
        	Sheet sheet=workBook.getSheetAt(0);
            //合并区域数量
            int sheetMergeCount = sheet.getNumMergedRegions();
          
            //结束行
            int endRow=sheet.getLastRowNum()+1;//getLastRowNum返回的下标从0开始
            
            if(endRow==1){
            	throw new SysException("打印模板为空！");
            }
            
            //如果打印模板的行数没有要填充的多，按填充的行数来复制
            if(endRow<Integer.parseInt(paraMap.get("maxRowIndex"))){
            	endRow=Integer.parseInt(paraMap.get("maxRowIndex"));
            }
            
            
            paraMap.put("tempRowCount", String.valueOf(endRow));
            
            //是否套打
    		String para003=paraMap.get("003");
            clearCellStyle((HSSFWorkbook) workBook,sheet,endRow,para003);
            
            for(int p=0;p<page-1;p++){
            	
            	int tarRow=p*endRow;
            	
	            //处理合并单元格
	            isMergedRegion(sheet,sheetMergeCount,endRow+tarRow);
            	
	            for(int i=0;i<endRow;i++){
	            	if(sheet.getRow(i)==null){
	            		sheet.createRow(i);
	            	}
	            	HSSFRow fromRow=(HSSFRow) sheet.getRow(i);
	            	HSSFRow toRow = (HSSFRow) sheet.createRow(i+endRow+tarRow);  
	            	//设置行高
            		toRow.setHeight(fromRow.getHeight());
            		//设置是否隐藏行
            		toRow.setZeroHeight(fromRow.getZeroHeight());
            		copyRow2003((HSSFWorkbook) workBook,fromRow,toRow,endRow+tarRow);
	            }

            }
            
        } catch (IOException e) {  
            logger.error(e.getMessage());  
            throw new IOException(e.getMessage(),e);  
        }
        
        return workBook;
    }
    
    /** 
     * 套打处理模板边框和内容 
     */  
    private static void clearCellStyle(HSSFWorkbook workBook,Sheet sheet,int endRow,String para003){
    	
        for(int i=0;i<endRow;i++){
        	HSSFRow fromRow=(HSSFRow)sheet.getRow(i);
        	if(fromRow==null){
        		continue;
        	}
        	
        	
        	for (Iterator cellIt = fromRow.cellIterator(); cellIt.hasNext();) {
                 HSSFCell fromCell = (HSSFCell) cellIt.next();
                 if(fromCell==null){
                	 continue;
                 }
                 
             	 
             	 //处理模板公式，否则要点击下才会生效
             	 if (fromCell.getCellType() == HSSFCell.CELL_TYPE_FORMULA) { 
             		fromCell.setCellFormula(fromCell.getCellFormula());
             	 }
             	 
             	 if(para003!=null && para003.equals("是")){
	             	 HSSFCellStyle style=fromCell.getCellStyle();
	                 style.setBorderBottom(HSSFCellStyle.BORDER_NONE);  
	             	 style.setBorderLeft(HSSFCellStyle.BORDER_NONE);  
	             	 style.setBorderRight(HSSFCellStyle.BORDER_NONE);  
	             	 style.setBorderTop(HSSFCellStyle.BORDER_NONE);
	             	 
	                 fromCell.setCellStyle(style);
	                 //没有批注清空单元格内容
	                 if(fromCell.getCellComment()==null){
	                	 fromCell.setCellValue("");
	                 }
             	 }
            }  
      
        }
    	
       
    }  
	
    /** 
     * 行复制功能 
     */  
    public static void copyRow2003(HSSFWorkbook workBook,HSSFRow fromRow,HSSFRow toRow,int tarRow){
    	
        for (Iterator cellIt = fromRow.cellIterator(); cellIt.hasNext();) {
            HSSFCell fromCell = (HSSFCell) cellIt.next();
            HSSFCell toCell = toRow.createCell(fromCell.getColumnIndex());
            copyCell(workBook,fromCell, toCell,tarRow);
        }  
 
    }
    

    /** 
     * 复制单元格 
     */  
    private static void copyCell(HSSFWorkbook workBook,HSSFCell fromCell, HSSFCell toCell,int tarRow) {  
        
    	//Excel导出报错 You can define up to 4000 styles in a .xls workbook
    	//HSSFCellStyle toStyle=workBook.createCellStyle(); 
    	//copyCellStyle(wb,fromCell.getCellStyle(), toStyle);
    	
       // distCell.setEncoding(srcCell.getEncoding());  
        //样式  
        toCell.setCellStyle(fromCell.getCellStyle());  
        //评论  
        if (fromCell.getCellComment() != null) {  
        	toCell.setCellComment(fromCell.getCellComment());  
        }  
        // 不同数据类型处理  
        int srcCellType = fromCell.getCellType();  
        toCell.setCellType(srcCellType);  
    //    if (copyValueFlag) {  
            if (srcCellType == HSSFCell.CELL_TYPE_NUMERIC) {  
                if (HSSFDateUtil.isCellDateFormatted(fromCell)) {  
                	toCell.setCellValue(fromCell.getDateCellValue());  
                } else {  
                	toCell.setCellValue(fromCell.getNumericCellValue());  
                }  
            } else if (srcCellType == HSSFCell.CELL_TYPE_STRING) {  
            	toCell.setCellValue(fromCell.getRichStringCellValue());  
            } else if (srcCellType == HSSFCell.CELL_TYPE_BLANK) {  
                // nothing21  
            } else if (srcCellType == HSSFCell.CELL_TYPE_BOOLEAN) {  
            	toCell.setCellValue(fromCell.getBooleanCellValue());  
            } else if (srcCellType == HSSFCell.CELL_TYPE_ERROR) {  
            	toCell.setCellErrorValue(fromCell.getErrorCellValue());  
            } else if (srcCellType == HSSFCell.CELL_TYPE_FORMULA) {  
            	
            	String fromFormula=fromCell.getCellFormula();
            	//if(fromFormula.toUpperCase().indexOf("SUM")!=-1){
	            	Matcher m = patStrNum.matcher(fromFormula);
					while(m.find()){
						String rowCell=m.group();//字母和数字组合B13
						Matcher mNum=patNum.matcher(rowCell);
						String rowNum = mNum.replaceAll("");//提取数字
						String rowZm=rowCell.replace(rowNum, "");//提取字母
						
						fromFormula=fromFormula.replace(rowCell, rowZm+"@"+String.valueOf(Integer.parseInt(rowNum)+tarRow));
					
					}
            	//}
				fromFormula=fromFormula.replace("@", "");
            	toCell.setCellFormula(fromFormula); 
            	
            	
            } else { // nothing29  
            }  
      //  }  
    }
    
    /** 
     * 复制一个单元格样式到目的单元格样式 
     */  
    private static HSSFCellStyle copyCellStyle(HSSFWorkbook wb,HSSFCellStyle fromStyle,HSSFCellStyle toStyle) {  
        toStyle.setAlignment(fromStyle.getAlignment());  
        //边框和边框颜色 ，不是套打的时候复制边框和边框颜色 
    	toStyle.setBorderBottom(fromStyle.getBorderBottom());  
        toStyle.setBorderLeft(fromStyle.getBorderLeft());  
        toStyle.setBorderRight(fromStyle.getBorderRight());  
        toStyle.setBorderTop(fromStyle.getBorderTop());  
        toStyle.setTopBorderColor(fromStyle.getTopBorderColor());  
        toStyle.setBottomBorderColor(fromStyle.getBottomBorderColor());  
        toStyle.setRightBorderColor(fromStyle.getRightBorderColor());  
        toStyle.setLeftBorderColor(fromStyle.getLeftBorderColor());  
        
          
        //背景和前景  
        toStyle.setFillBackgroundColor(fromStyle.getFillBackgroundColor());  
        toStyle.setFillForegroundColor(fromStyle.getFillForegroundColor());  
          
        toStyle.setDataFormat(fromStyle.getDataFormat());  
        toStyle.setFillPattern(fromStyle.getFillPattern());  
//      toStyle.setFont(fromStyle.getFont(null));  
        toStyle.setHidden(fromStyle.getHidden());  
        toStyle.setIndention(fromStyle.getIndention());//首行缩进  
        toStyle.setLocked(fromStyle.getLocked());  
        toStyle.setRotation(fromStyle.getRotation());//旋转  
        toStyle.setVerticalAlignment(fromStyle.getVerticalAlignment());  
        toStyle.setWrapText(fromStyle.getWrapText());  
        
        toStyle.cloneStyleFrom(fromStyle);
        toStyle.setFont(wb.getFontAt(fromStyle.getFontIndex()));
          
        return toStyle;
    }  
    

    /**
     * 处理合并单元格
     */
    public static void isMergedRegion(Sheet sheet,int sheetMergeCount,int toRow) {
    	
        for (int i = 0; i < sheetMergeCount; i++) {
        	
        	  CellRangeAddress range=sheet.getMergedRegion(i);
              int firstColumn = range.getFirstColumn(); 
              int lastColumn = range.getLastColumn();   
              int firstRow = range.getFirstRow();   
              int lastRow = range.getLastRow();   
              sheet.addMergedRegion(new CellRangeAddress(firstRow+toRow, lastRow+toRow, firstColumn, lastColumn));  
//              if(fromRow >= firstRow && fromRow <= lastRow){ 
//                    if(fromColumn >= firstColumn && fromColumn <= lastColumn){ 
//                    	sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstColumn, lastColumn));  
//                    } 
//              }
        } 
      } 
    
    
	/**
	 * 根据报表模板渲染数据、行高列宽，PageOffice使用
	 */
	public static void createReportFile(String templateFilePath,String toFilePath) throws Exception{  
		
		Workbook wbTemp=null;
		Sheet sheetTemp=null;
        try {  
  
        	 //获取toFilePath文件的io流  
        	Workbook wbTo = new XSSFWorkbook(new FileInputStream(toFilePath));
        	Sheet sheetTo=wbTo.getSheetAt(0);
        	sheetTo.getSheetName();
        	
        	//获取templateFilePath文件的io流  
        	File templateFile=new File(templateFilePath);
    		if(templateFile.exists()){
    			wbTemp = new XSSFWorkbook(new FileInputStream(templateFilePath));
    			sheetTemp=wbTemp.getSheetAt(0);
    			
    			 //边距
    			sheetTo.setMargin(HSSFSheet.TopMargin,sheetTemp.getMargin(HSSFSheet.TopMargin));// 页边距（上）    
    			sheetTo.setMargin(HSSFSheet.BottomMargin,sheetTemp.getMargin(HSSFSheet.BottomMargin));// 页边距（下）    
    			sheetTo.setMargin(HSSFSheet.LeftMargin,sheetTemp.getMargin(HSSFSheet.LeftMargin) );// 页边距（左）    
                sheetTo.setMargin(HSSFSheet.RightMargin,sheetTemp.getMargin(HSSFSheet.RightMargin));// 页边距（右）
                sheetTo.setHorizontallyCenter(sheetTemp.getHorizontallyCenter());//水平
                sheetTo.setVerticallyCenter(sheetTemp.getVerticallyCenter());//垂直
                
                //纸张信息
                XSSFPrintSetup psTemp = (XSSFPrintSetup) sheetTemp.getPrintSetup(); 
                XSSFPrintSetup psTo = (XSSFPrintSetup) sheetTo.getPrintSetup();    
                psTo.setLandscape(psTemp.getLandscape()); // 打印方向，true：横向，false：纵向(默认)    
                psTo.setVResolution(psTemp.getVResolution());//打印质量600点
                psTo.setPaperSize(psTemp.getPaperSize()); //纸张类型    
                psTo.setFitWidth(psTemp.getFitWidth());//设置页宽(调整)
                psTo.setFitHeight(psTemp.getFitHeight());//设置页高(调整)
                psTo.setHeaderMargin(psTemp.getHeaderMargin());  //页眉距离
                psTo.setFooterMargin(psTemp.getFooterMargin()); //页脚距离


                //页眉内容
                XSSFHeaderFooter headTemp=	(XSSFHeaderFooter) sheetTemp.getHeader();
                XSSFHeaderFooter headTo=	(XSSFHeaderFooter) sheetTo.getHeader();
                headTo.setLeft(headTemp.getLeft());
                headTo.setCenter(headTemp.getCenter());
                headTo.setRight(headTemp.getRight());
                  
                //页脚内容
                XSSFHeaderFooter footTemp=(XSSFHeaderFooter) sheetTemp.getFooter();
                XSSFHeaderFooter footTo=(XSSFHeaderFooter) sheetTo.getFooter();
                footTo.setLeft(footTemp.getLeft());
                footTo.setCenter(footTemp.getCenter());
                footTo.setRight(footTemp.getRight());
    			
    		}else{
    			sheetTo.setMargin(HSSFSheet.TopMargin,(double)0.0);// 页边距（上）    
    			sheetTo.setMargin(HSSFSheet.BottomMargin,(double)0.0);// 页边距（下）    
    			sheetTo.setMargin(HSSFSheet.LeftMargin,(double)1.0 );// 页边距（左）    
                sheetTo.setMargin(HSSFSheet.RightMargin,(double)0.0);// 页边距（右）

                XSSFPrintSetup psTo = (XSSFPrintSetup) sheetTo.getPrintSetup();    
                psTo.setLandscape(false); // 打印方向，true：横向，false：纵向(默认)    
                psTo.setVResolution((short)600);//打印质量600点
                psTo.setPaperSize(XSSFPrintSetup.A4_PAPERSIZE); //纸张类型    
                psTo.setHeaderMargin((double)0.0);  //页眉
                psTo.setFooterMargin((double)0.0); //页脚
    		}
        	
    		//替换页眉页脚里面的系统变量
        	setHeadFoot2007(sheetTo);
    		
        	for(int i=0;i<sheetTo.getLastRowNum();i++){
        		
        		Row rowTo=sheetTo.getRow(i);
        		if(rowTo==null){
        			continue;
        		}
        		//设置行高
        		if(sheetTemp!=null){
        			Row rowTemp=sheetTemp.getRow(i);
        			if(rowTemp!=null){
        				rowTo.setHeight(rowTemp.getHeight());
        			}
        		}
        		
        		//处理公式
        		for(int j=0;j<rowTo.getLastCellNum();j++){
        			
        			Cell cellTo=rowTo.getCell(j);
        			if(cellTo==null){
        				continue;
        			}
        			
        			int cellType = cellTo.getCellType();
        			if (cellType == XSSFCell.CELL_TYPE_FORMULA) { 
        				
        				if(cellTo.getCellFormula()==null){
            				continue;
            			}
        				
        				if(cellTo.getCellFormula().indexOf("RES")!=-1 || cellTo.getCellFormula().indexOf("REP")!=-1){
                			cellTo.setCellValue("");
                			cellTo.setCellFormula(null);
                			
                		}else if(cellTo.getCellFormula().indexOf("\"")!=-1){
            			}else{
	                    	try {
	                    		cellTo.setCellValue(cellTo.getNumericCellValue());
	                    	} catch (IllegalStateException e) {
	                    		cellTo.setCellValue(cellTo.getRichStringCellValue());
	                    	}
	                    	cellTo.setCellFormula(null);
                		}
                    	
                    }
        		}
        	}
        	
        	//设置列宽
    		if(sheetTemp!=null){
    			
    			Row rowTo=sheetTo.getRow(0);
    			Row rowTemp=sheetTemp.getRow(0);
    			if(rowTemp!=null){
	            	for(int j=0;j<rowTo.getLastCellNum();j++){
	            		
	            		if(rowTemp.getCell(j)!=null){
	            			int width=sheetTemp.getColumnWidth(j);
	            			sheetTo.setColumnWidth(j, width);
	            		}
	            		
	            	}
    			}
    		}
			
            
            FileOutputStream fileOut = new FileOutputStream(toFilePath);
            wbTo.write(fileOut);
            fileOut.close();
            
        } catch (IOException e) {  
            logger.error(e.getMessage());  
            throw new IOException(e.getMessage(),e);  
        }
        
    }
	
	/**
	 * 根据数据保存报表模板、打印信息、行高列宽，PageOffice使用
	 */
	public static void saveReportTemplateFile(String templateFilePath,String toFilePath) throws Exception{  
		
		Workbook wbTemp=null;
		Sheet sheetTemp=null;
        try {  
  
        	File toFile=new File(toFilePath);
    		if(!toFile.exists()){
    			throw new SysException("请在报表制作页面，保存打印模板！");
    		}
        	
        	 //获取toFilePath文件的io流  
        	Workbook wbTo = new XSSFWorkbook(new FileInputStream(toFilePath));
        	Sheet sheetTo=wbTo.getSheetAt(0);
        	sheetTo.getSheetName();
        	
        	//获取templateFilePath文件的io流  
			wbTemp = new XSSFWorkbook(new FileInputStream(templateFilePath));
			sheetTemp=wbTemp.getSheetAt(0);
			
			 //边距
			sheetTo.setMargin(HSSFSheet.TopMargin,sheetTemp.getMargin(HSSFSheet.TopMargin));// 页边距（上）    
			sheetTo.setMargin(HSSFSheet.BottomMargin,sheetTemp.getMargin(HSSFSheet.BottomMargin));// 页边距（下）    
			sheetTo.setMargin(HSSFSheet.LeftMargin,sheetTemp.getMargin(HSSFSheet.LeftMargin) );// 页边距（左）    
            sheetTo.setMargin(HSSFSheet.RightMargin,sheetTemp.getMargin(HSSFSheet.RightMargin));// 页边距（右）
            sheetTo.setHorizontallyCenter(sheetTemp.getHorizontallyCenter());//水平
            sheetTo.setVerticallyCenter(sheetTemp.getVerticallyCenter());//垂直
            
            //纸张信息
            XSSFPrintSetup psTemp = (XSSFPrintSetup) sheetTemp.getPrintSetup(); 
            XSSFPrintSetup psTo = (XSSFPrintSetup) sheetTo.getPrintSetup();    
            psTo.setLandscape(psTemp.getLandscape()); // 打印方向，true：横向，false：纵向(默认)    
            psTo.setVResolution(psTemp.getVResolution());//打印质量600点
            psTo.setPaperSize(psTemp.getPaperSize()); //纸张类型    
            psTo.setFitWidth(psTemp.getFitWidth());//设置页宽(调整)
            psTo.setFitHeight(psTemp.getFitHeight());//设置页高(调整)
            psTo.setHeaderMargin(psTemp.getHeaderMargin());  //页眉距离
            psTo.setFooterMargin(psTemp.getFooterMargin()); //页脚距离


            //页眉内容
            XSSFHeaderFooter headTemp=	(XSSFHeaderFooter) sheetTemp.getHeader();
            XSSFHeaderFooter headTo=	(XSSFHeaderFooter) sheetTo.getHeader();
            headTo.setLeft(headTemp.getLeft());
            headTo.setCenter(headTemp.getCenter());
            headTo.setRight(headTemp.getRight());
              
            //页脚内容
            XSSFHeaderFooter footTemp=(XSSFHeaderFooter) sheetTemp.getFooter();
            XSSFHeaderFooter footTo=(XSSFHeaderFooter) sheetTo.getFooter();
            footTo.setLeft(footTemp.getLeft());
            footTo.setCenter(footTemp.getCenter());
            footTo.setRight(footTemp.getRight());
			
        	
        	for(int i=0;i<sheetTo.getLastRowNum();i++){
        		
        		Row rowTo=sheetTo.getRow(i);
        		if(rowTo==null){
        			continue;
        		}
        		//设置行高
        		if(sheetTemp!=null){
        			Row rowTemp=sheetTemp.getRow(i);
        			if(rowTemp!=null){
        				rowTo.setHeight(rowTemp.getHeight());
        			}
        		}
        		
        	}
        	
        	//设置列宽
    		if(sheetTemp!=null){
    			
    			Row rowTo=sheetTo.getRow(0);
    			Row rowTemp=sheetTemp.getRow(0);
            	for(int j=0;j<rowTo.getLastCellNum();j++){
            		
            		if(rowTemp.getCell(j)!=null){
            			int width=sheetTemp.getColumnWidth(j);
            			sheetTo.setColumnWidth(j, width);
            		}
            		
            	}
    		}
			
            FileOutputStream fileOut = new FileOutputStream(toFilePath);
            wbTo.write(fileOut);
            fileOut.close();
            
        } catch (IOException e) {  
            logger.error(e.getMessage());  
            throw new IOException(e.getMessage(),e);  
        }
        
    }
	
	/**
	 * 处理单元格公式，PageOffice使用
	 */
	public static void setReportFileFormula(String toFilePath) throws Exception{
		
        try {  
  
        	//获取toFilePath文件的io流  
        	Workbook wbTo = new XSSFWorkbook(new FileInputStream(toFilePath));
        	Sheet sheetTo=wbTo.getSheetAt(0);
        	sheetTo.getSheetName();
        	
        	for(int i=0;i<sheetTo.getLastRowNum();i++){
        		
        		Row rowTo=sheetTo.getRow(i);
        		if(rowTo==null){
        			continue;
        		}
        		
        		//处理公式
        		for(int j=0;j<rowTo.getLastCellNum();j++){
        			
        			Cell cellTo=rowTo.getCell(j);
        			if(cellTo==null){
        				continue;
        			}
        			
        			int cellType = cellTo.getCellType();
        			if (cellType == XSSFCell.CELL_TYPE_FORMULA) { 
        				
        				if(cellTo.getCellFormula()==null){
            				continue;
            			}
        				
        				if(cellTo.getCellFormula().indexOf("RES")!=-1 || cellTo.getCellFormula().indexOf("REP")!=-1){
                			cellTo.setCellValue("");
                			cellTo.setCellFormula(null);
                			
                		}else if(cellTo.getCellFormula().indexOf("\"")!=-1){
            			}else{
	                    	try {
	                    		cellTo.setCellValue(cellTo.getNumericCellValue());
	                    	} catch (IllegalStateException e) {
	                    		cellTo.setCellValue(cellTo.getRichStringCellValue());
	                    	}
	                    	cellTo.setCellFormula(null);
                		}
                    }
        			
        		}
        	}
        	
            FileOutputStream fileOut = new FileOutputStream(toFilePath);
            wbTo.write(fileOut);
            fileOut.close();
            
        } catch (IOException e) {  
            logger.error(e.getMessage());  
            throw new IOException(e.getMessage(),e);  
        }
        
    }
	
	
	/**
	 * 根据excel模板动态生成excel2003，PageOffice表格模板使用
	 */
	public static Workbook getWorkBookTable(String fromFilePath,String toFilePath) throws Exception{  
		
		File fromFile = new File(fromFilePath); 
		
        //判断文件是否存在
		if(!fromFile.exists()){
			logger.error("没有维护打印模板："+fromFilePath);  
            throw new FileNotFoundException("没有维护打印模板！");  
		}
		
		//复制文件
		FileUtil.copyFile(fromFilePath,toFilePath);
		
		Workbook workBook=null;
        try {  
            //获取excel文件的io流  
        	InputStream is = new FileInputStream(toFilePath); 
        	//创建2003Workbook工作薄对象，表示整个excel  
        	workBook = new HSSFWorkbook(is);
            
        	Sheet sheet=workBook.getSheetAt(0);
        	
        	//替换页眉页脚里面的系统变量
        	setHeadFoot2003(sheet);
        	
        } catch (IOException e) {  
            logger.error(e.getMessage());  
            throw new IOException(e.getMessage(),e);  
        }
        
        return workBook;
    }
	
	
	//替换页眉页脚里面的系统变量（PageOffice使用）
	public static void setHeadFoot2003(Sheet sheet){
		//XSSFHeaderFooter head= (XSSFHeaderFooter) sheet.getHeader();
		
		//页眉内容
		HeaderFooter head= (HeaderFooter) sheet.getHeader();
        if(head!=null && head.getLeft()!=null && !head.getLeft().trim().equals("")){
        	String left=head.getLeft();
        	if(left.indexOf("{user_name}")!=-1){
        		head.setLeft(left.replace("{user_name}", SessionManager.getUserName()));
        		left=head.getLeft();
        	}
        	if(left.indexOf("{emp_name}")!=-1){
        		head.setLeft(left.replace("{emp_name}", SessionManager.getEmpName()));
        		left=head.getLeft();
        	}
        	if(left.indexOf("{hos_name}")!=-1){
        		head.setLeft(left.replace("{hos_name}", SessionManager.getHosName()));
        		left=head.getLeft();
        	}
        	if(left.indexOf("{group_name}")!=-1){
        		head.setLeft(left.replace("{group_name}", SessionManager.getGroupName()));
        		left=head.getLeft();
        	}
        }
        
        if(head!=null && head.getCenter()!=null && !head.getCenter().trim().equals("")){
        	String center=head.getCenter();
        	if(center.indexOf("{user_name}")!=-1){
        		head.setCenter(center.replace("{user_name}", SessionManager.getUserName()));
        		center=head.getCenter();
        	}
        	if(center.indexOf("{emp_name}")!=-1){
        		head.setCenter(center.replace("{emp_name}", SessionManager.getEmpName()));
        		center=head.getCenter();
        	}
        	if(center.indexOf("{hos_name}")!=-1){
        		head.setCenter(center.replace("{hos_name}", SessionManager.getHosName()));
        		center=head.getCenter();
        	}
        	if(center.indexOf("{group_name}")!=-1){
        		head.setCenter(center.replace("{group_name}", SessionManager.getGroupName()));
        		center=head.getCenter();
        	}
        }
        
        if(head!=null && head.getRight()!=null && !head.getRight().trim().equals("")){
        	String right=head.getRight();
        	if(right.indexOf("{user_name}")!=-1){
        		head.setRight(right.replace("{user_name}", SessionManager.getUserName()));
        		right=head.getRight();
        	}
        	if(right.indexOf("{emp_name}")!=-1){
        		head.setRight(right.replace("{emp_name}", SessionManager.getEmpName()));
        		right=head.getRight();
        	}
        	if(right.indexOf("{hos_name}")!=-1){
        		head.setRight(right.replace("{hos_name}", SessionManager.getHosName()));
        		right=head.getRight();
        	}
        	if(right.indexOf("{group_name}")!=-1){
        		head.setRight(right.replace("{group_name}", SessionManager.getGroupName()));
        		right=head.getRight();
        	}
        }
        
          
        //页脚内容
        HeaderFooter foot=(HeaderFooter) sheet.getFooter();
        if(foot!=null && foot.getLeft()!=null && !foot.getLeft().trim().equals("")){
        	String left=foot.getLeft();
        	if(left.indexOf("{user_name}")!=-1){
        		foot.setLeft(left.replace("{user_name}", SessionManager.getUserName()));
        		left=foot.getLeft();
        	}
        	if(left.indexOf("{emp_name}")!=-1){
        		foot.setLeft(left.replace("{emp_name}", SessionManager.getEmpName()));
        		left=foot.getLeft();
        	}
        	if(left.indexOf("{hos_name}")!=-1){
        		foot.setLeft(left.replace("{hos_name}", SessionManager.getHosName()));
        		left=foot.getLeft();
        	}
        	if(left.indexOf("{group_name}")!=-1){
        		foot.setLeft(left.replace("{group_name}", SessionManager.getGroupName()));
        		left=foot.getLeft();
        	}
        }
        
        if(foot!=null && foot.getCenter()!=null && !foot.getCenter().trim().equals("")){
        	String center=foot.getCenter();
        	if(center.indexOf("{user_name}")!=-1){
        		foot.setCenter(center.replace("{user_name}", SessionManager.getUserName()));
        		center=foot.getCenter();
        	}
        	if(center.indexOf("{emp_name}")!=-1){
        		foot.setCenter(center.replace("{emp_name}", SessionManager.getEmpName()));
        		center=foot.getCenter();
        	}
        	if(center.indexOf("{hos_name}")!=-1){
        		foot.setCenter(center.replace("{hos_name}", SessionManager.getHosName()));
        		center=foot.getCenter();
        	}
        	if(center.indexOf("{group_name}")!=-1){
        		foot.setCenter(center.replace("{group_name}", SessionManager.getGroupName()));
        		center=foot.getCenter();
        	}
        }
        
        if(foot!=null && foot.getRight()!=null && !foot.getRight().trim().equals("")){
        	String right=foot.getRight();
        	if(right.indexOf("{user_name}")!=-1){
        		foot.setRight(right.replace("{user_name}", SessionManager.getUserName()));
        		right=foot.getRight();
        	}
        	if(right.indexOf("{emp_name}")!=-1){
        		foot.setRight(right.replace("{emp_name}", SessionManager.getEmpName()));
        		right=foot.getRight();
        	}
        	if(right.indexOf("{hos_name}")!=-1){
        		foot.setRight(right.replace("{hos_name}", SessionManager.getHosName()));
        		right=foot.getRight();
        	}
        	if(right.indexOf("{group_name}")!=-1){
        		foot.setRight(right.replace("{group_name}", SessionManager.getGroupName()));
        		right=foot.getRight();
        	}
        }
                
	}
	
	//替换页眉页脚里面的系统变量（PageOffice使用）
	public static void setHeadFoot2007(Sheet sheet){
		
		//页眉内容
		XSSFHeaderFooter head= (XSSFHeaderFooter) sheet.getHeader();
        if(head!=null && head.getLeft()!=null && !head.getLeft().trim().equals("")){
        	String left=head.getLeft();
        	if(left.indexOf("{user_name}")!=-1){
        		head.setLeft(left.replace("{user_name}", SessionManager.getUserName()));
        		left=head.getLeft();
        	}
        	if(left.indexOf("{emp_name}")!=-1){
        		head.setLeft(left.replace("{emp_name}", SessionManager.getEmpName()));
        		left=head.getLeft();
        	}
        	if(left.indexOf("{hos_name}")!=-1){
        		head.setLeft(left.replace("{hos_name}", SessionManager.getHosName()));
        		left=head.getLeft();
        	}
        	if(left.indexOf("{group_name}")!=-1){
        		head.setLeft(left.replace("{group_name}", SessionManager.getGroupName()));
        		left=head.getLeft();
        	}
        }
        
        if(head!=null && head.getCenter()!=null && !head.getCenter().trim().equals("")){
        	String center=head.getCenter();
        	if(center.indexOf("{user_name}")!=-1){
        		head.setCenter(center.replace("{user_name}", SessionManager.getUserName()));
        		center=head.getCenter();
        	}
        	if(center.indexOf("{emp_name}")!=-1){
        		head.setCenter(center.replace("{emp_name}", SessionManager.getEmpName()));
        		center=head.getCenter();
        	}
        	if(center.indexOf("{hos_name}")!=-1){
        		head.setCenter(center.replace("{hos_name}", SessionManager.getHosName()));
        		center=head.getCenter();
        	}
        	if(center.indexOf("{group_name}")!=-1){
        		head.setCenter(center.replace("{group_name}", SessionManager.getGroupName()));
        		center=head.getCenter();
        	}
        }
        
        if(head!=null && head.getRight()!=null && !head.getRight().trim().equals("")){
        	String right=head.getRight();
        	if(right.indexOf("{user_name}")!=-1){
        		head.setRight(right.replace("{user_name}", SessionManager.getUserName()));
        		right=head.getRight();
        	}
        	if(right.indexOf("{emp_name}")!=-1){
        		head.setRight(right.replace("{emp_name}", SessionManager.getEmpName()));
        		right=head.getRight();
        	}
        	if(right.indexOf("{hos_name}")!=-1){
        		head.setRight(right.replace("{hos_name}", SessionManager.getHosName()));
        		right=head.getRight();
        	}
        	if(right.indexOf("{group_name}")!=-1){
        		head.setRight(right.replace("{group_name}", SessionManager.getGroupName()));
        		right=head.getRight();
        	}
        }
        
          
        //页脚内容
        XSSFHeaderFooter foot=(XSSFHeaderFooter) sheet.getFooter();
        if(foot!=null && foot.getLeft()!=null && !foot.getLeft().trim().equals("")){
        	String left=foot.getLeft();
        	if(left.indexOf("{user_name}")!=-1){
        		foot.setLeft(left.replace("{user_name}", SessionManager.getUserName()));
        		left=foot.getLeft();
        	}
        	if(left.indexOf("{emp_name}")!=-1){
        		foot.setLeft(left.replace("{emp_name}", SessionManager.getEmpName()));
        		left=foot.getLeft();
        	}
        	if(left.indexOf("{hos_name}")!=-1){
        		foot.setLeft(left.replace("{hos_name}", SessionManager.getHosName()));
        		left=foot.getLeft();
        	}
        	if(left.indexOf("{group_name}")!=-1){
        		foot.setLeft(left.replace("{group_name}", SessionManager.getGroupName()));
        		left=foot.getLeft();
        	}
        }
        
        if(foot!=null && foot.getCenter()!=null && !foot.getCenter().trim().equals("")){
        	String center=foot.getCenter();
        	if(center.indexOf("{user_name}")!=-1){
        		foot.setCenter(center.replace("{user_name}", SessionManager.getUserName()));
        		center=foot.getCenter();
        	}
        	if(center.indexOf("{emp_name}")!=-1){
        		foot.setCenter(center.replace("{emp_name}", SessionManager.getEmpName()));
        		center=foot.getCenter();
        	}
        	if(center.indexOf("{hos_name}")!=-1){
        		foot.setCenter(center.replace("{hos_name}", SessionManager.getHosName()));
        		center=foot.getCenter();
        	}
        	if(center.indexOf("{group_name}")!=-1){
        		foot.setCenter(center.replace("{group_name}", SessionManager.getGroupName()));
        		center=foot.getCenter();
        	}
        }
        
        if(foot!=null && foot.getRight()!=null && !foot.getRight().trim().equals("")){
        	String right=foot.getRight();
        	if(right.indexOf("{user_name}")!=-1){
        		foot.setRight(right.replace("{user_name}", SessionManager.getUserName()));
        		right=foot.getRight();
        	}
        	if(right.indexOf("{emp_name}")!=-1){
        		foot.setRight(right.replace("{emp_name}", SessionManager.getEmpName()));
        		right=foot.getRight();
        	}
        	if(right.indexOf("{hos_name}")!=-1){
        		foot.setRight(right.replace("{hos_name}", SessionManager.getHosName()));
        		right=foot.getRight();
        	}
        	if(right.indexOf("{group_name}")!=-1){
        		foot.setRight(right.replace("{group_name}", SessionManager.getGroupName()));
        		right=foot.getRight();
        	}
        }
                
	}
	
	/**
	 * Grid打印
	 */
	public static Workbook getWorkBookGrid(String fromFilePath,String toFilePath) throws Exception{  
		
		//复制文件
		FileUtil.copyFile(fromFilePath,toFilePath);
		
		Workbook workBook=null;
        try {  
            //获取excel文件的io流  
        	InputStream is = new FileInputStream(toFilePath); 
        	//创建2003Workbook工作薄对象，表示整个excel
        	//workBook = new HSSFWorkbook(is);
        	
        	//创建2007Workbook工作薄对象，表示整个excel
        	workBook = new XSSFWorkbook(is);
        	
        } catch (IOException e) {  
            logger.error(e.getMessage());  
            throw new IOException(e.getMessage(),e);  
        }
        
        return workBook;
    }
	
	//2007获取单元格样式，设置grid表头样式
	public static CellStyle getHeadCellStyle(Workbook workBook){
		
		CellStyle cellStyle = workBook.createCellStyle();//新建单元格样式  
		Font fontStyle = workBook.createFont();//新建字体样式  
		fontStyle.setFontName("宋体");
		cellStyle.setBorderBottom(CellStyle.BORDER_THIN); //下边框    
		cellStyle.setBorderLeft(CellStyle.BORDER_THIN);//左边框    
		cellStyle.setBorderTop(CellStyle.BORDER_THIN);//上边框    
		cellStyle.setBorderRight(CellStyle.BORDER_THIN);//右边框  
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER); //水平居中 
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中 
		cellStyle.setWrapText(true);//设置自动换行
		fontStyle.setBold(true);
		cellStyle.setFont(fontStyle);
		return cellStyle;
	}
	
	//2007获取单元格样式，设置grid数据样式
	public static CellStyle getBodyCellStyleCenter(Workbook workBook,boolean isBorder){
		
		CellStyle cellStyle = workBook.createCellStyle();//新建单元格样式  
		if(isBorder){
			cellStyle.setBorderBottom(CellStyle.BORDER_THIN); //下边框    
			cellStyle.setBorderLeft(CellStyle.BORDER_THIN);//左边框    
			cellStyle.setBorderTop(CellStyle.BORDER_THIN);//上边框    
			cellStyle.setBorderRight(CellStyle.BORDER_THIN);//右边框  
		}
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中 
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);//水平居中 
		cellStyle.setWrapText(true);//设置自动换行
		return cellStyle;
	}
	
	//2007获取单元格样式，设置grid数据样式
	public static CellStyle getBodyCellStyleLeft(Workbook workBook,boolean isBorder){
		
		CellStyle cellStyle = workBook.createCellStyle();//新建单元格样式  
		if(isBorder){
			cellStyle.setBorderBottom(CellStyle.BORDER_THIN); //下边框    
			cellStyle.setBorderLeft(CellStyle.BORDER_THIN);//左边框    
			cellStyle.setBorderTop(CellStyle.BORDER_THIN);//上边框    
			cellStyle.setBorderRight(CellStyle.BORDER_THIN);//右边框  
		}
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中 
		cellStyle.setAlignment(CellStyle.ALIGN_LEFT);//水平居左 
		cellStyle.setWrapText(true);//设置自动换行
		return cellStyle;
	}
	
	//2007获取单元格样式，设置grid数据样式
	public static CellStyle getBodyCellStyleRight(Workbook workBook,boolean isBorder){
		
		CellStyle cellStyle = workBook.createCellStyle();//新建单元格样式  
		if(isBorder){
			cellStyle.setBorderBottom(CellStyle.BORDER_THIN); //下边框    
			cellStyle.setBorderLeft(CellStyle.BORDER_THIN);//左边框    
			cellStyle.setBorderTop(CellStyle.BORDER_THIN);//上边框    
			cellStyle.setBorderRight(CellStyle.BORDER_THIN);//右边框  
		}
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中 
		cellStyle.setAlignment(CellStyle.ALIGN_RIGHT);//水平居右 
		cellStyle.setWrapText(true);//设置自动换行
		return cellStyle;
	}
	
	/**
	 * excel必填格子提示
	 * @param list 必填格子没有值的行列信息
	 * @return String 提示格子行和列
	 * 			list是空集合返回null
	 */
	public static String mustFillDescribe(List<String> list) {
		if(list.size() > 0){
			StringBuilder msg = new StringBuilder("以下必填格子不能为空：");
			for(String str : list){
				String[] ar = str.split("[:：]");
				msg.append("</br>").append("第" + ar[0] + "行，第" + ar[1] + "列");
			}
			return msg.toString();
		}
		return null;
	}
}
