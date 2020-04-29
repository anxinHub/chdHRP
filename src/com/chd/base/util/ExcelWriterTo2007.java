package com.chd.base.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

/**
 * 生成导出Excel文件对象,2007以上
 * 
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-4-29 下午1:31:47
 * @Company: 智慧云康（北京）数据科教有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
public class ExcelWriterTo2007 {

	private static Logger logger = Logger.getLogger(ExcelWriterTo2007.class);

	private static String excelWriterMsg = "";

	// 定制浮点数格式
	private static String NUMBER_FORMAT = "#,##0.00";

	// 定制日期格式
	private static String DATE_FORMAT = "m/d/yy"; // "m/d/yy h:mm"

	private Workbook workbook = null;

	private Sheet sheet = null;

	private Row row = null;

	private OutputStream out = null;

	private CellStyle cellStyle = null;

	private ExcelWriterTo2007() {

	}

	public static String getExcelWriterMsg() {
		return excelWriterMsg;
	}

	public static void setExcelWriterMsg(String excelWriterMsg) {
		ExcelWriterTo2007.excelWriterMsg = excelWriterMsg;
	}

	/**
	 * 初始化Excel
	 */
	public ExcelWriterTo2007(OutputStream out) {
		this.out = out;
		this.workbook = new SXSSFWorkbook(500);
		this.sheet = workbook.createSheet();
		cellStyle = workbook.createCellStyle(); // 建立新的cell样式
	}

	/**
	 * 导出Excel文件
	 * 
	 * @throws IOException
	 */
	public void export() throws FileNotFoundException, IOException {
		try {
			workbook.write(out);
			out.flush();
			out.close();
		}
		catch (FileNotFoundException e) {
			throw new IOException("生成导出Excel文件出错!");
		}
		catch (IOException e) {
			throw new IOException(" 写入Excel文件出错! ");
		}
	}

	/**
	 * 导出Excel文件
	 * 
	 * @throws IOException
	 */
	public void export(OutputStream out) throws FileNotFoundException, IOException {
		try {
			workbook.write(out);
			out.flush();
			out.close();
		}
		catch (FileNotFoundException e) {
			throw new IOException("生成导出Excel文件出错!");
		}
		catch (IOException e) {
			throw new IOException(" 写入Excel文件出错! ");
		}

	}

	/**
	 * 增加一行
	 * 
	 * @param index
	 *            行号
	 */
	public void createRow(int index) {
		this.row = this.sheet.createRow(index);
	}

	/**
	 * 获取单元格的值 <br>
	 * 用途 在写文件的同时会用到该文件里某个单元格内容时会用到该方法
	 * 
	 * @param index
	 *            列号
	 */
	public String getCell(int index) {
		Cell cell = this.row.getCell(index);
		String strExcelCell = "";
		if (cell != null) {
			switch (cell.getCellType()) {
				case Cell.CELL_TYPE_FORMULA:
					strExcelCell = "FORMULA ";
					break;
				case Cell.CELL_TYPE_NUMERIC: {
					strExcelCell = String.valueOf(cell.getNumericCellValue());
				}
					break;
				case Cell.CELL_TYPE_STRING:
					strExcelCell = cell.getStringCellValue();
					break;
				case Cell.CELL_TYPE_BLANK:
					strExcelCell = "";
					break;
				default:
					strExcelCell = "";
					break;
			}
		}
		return strExcelCell;
	}

	/**
	 * 设置单元格
	 * 
	 * @param index
	 *            列号
	 * @param value
	 *            单元格填充值
	 */
	public void setCell(int index, int value) {
		Cell cell = this.row.createCell(index);
		cell.setCellType(Cell.CELL_TYPE_NUMERIC);
		cell.setCellValue(value);
	}

	/**
	 * 设置单元格
	 * 
	 * @param index
	 *            列号
	 * @param value
	 *            单元格填充值
	 */
	public void setCell(int index, double value) {
		Cell cell = this.row.createCell(index);
		cell.setCellType(Cell.CELL_TYPE_NUMERIC);
		cell.setCellValue(value);
		DataFormat format = workbook.createDataFormat();
		cellStyle.setDataFormat(format.getFormat(NUMBER_FORMAT)); // 设置cell样式为定制的浮点数格式
		cell.setCellStyle(cellStyle); // 设置该cell浮点数的显示格式
	}

	/**
	 * 设置单元格
	 * 
	 * @param index
	 *            列号
	 * @param value
	 *            单元格填充值
	 */
	public void setCell(int index, String value) {
		Cell cell = this.row.createCell(index);
		cell.setCellType(Cell.CELL_TYPE_STRING);
		// cell.setEncoding(XLS_ENCODING);
		cell.setCellValue(value);
	}

	/**
	 * 设置单元格
	 * 
	 * @param index
	 *            列号
	 * @param value
	 *            单元格填充值
	 */
	public void setCell(int index, Calendar value) {
		Cell cell = this.row.createCell(index);
		// cell.setEncoding(XLS_ENCODING);
		cell.setCellValue(value.getTime());
		cellStyle.setDataFormat(workbook.createDataFormat().getFormat(DATE_FORMAT)); // 设置cell样式为定制的日期格式
		cell.setCellStyle(cellStyle); // 设置该cell日期的显示格式
	}

	/**
	 * 设置单元格
	 * 
	 * @param index
	 *            列号
	 * @param value
	 *            单元格填充值
	 */
	public void setCell(int index, Date value) {
		Cell cell = this.row.createCell(index);
		cell.setCellValue(value);
		cellStyle.setDataFormat(workbook.createDataFormat().getFormat(DATE_FORMAT)); // 设置cell样式为定制的日期格式
		cell.setCellStyle(cellStyle); // 设置该cell日期的显示格式
	}

	/**
	 * @param file
	 *            excel文件
	 * @param list
	 *            相当于表格 一个二维数组<br>
	 *            格式 list[list]
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean exportExcel(String file, List<List> list) {
		boolean flag = true;
		OutputStream out = null;
		ExcelWriterTo2007 eWriter = null;
		try {
			out = new FileOutputStream(file);
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		eWriter = new ExcelWriterTo2007(out);
		// 初始化类
		for (int i = 0; i < list.size(); i++) {
			List dataList = list.get(i);
			eWriter.createRow(i);
			for (int j = 0; j < dataList.size(); j++) {
				Object obj = dataList.get(j);
				if (obj instanceof Integer) {
					eWriter.setCell(j, (Integer) obj);
				}
				if (obj instanceof String) {
					eWriter.setCell(j, (String) obj);
				}
				if (obj instanceof Date) {
					eWriter.setCell(j, (Date) obj);
				}
				if (obj instanceof Double) {
					eWriter.setCell(j, (Double) obj);
				}
				if (obj instanceof Calendar) {
					eWriter.setCell(j, (Calendar) obj);
				}
			}
		}

		try {
			eWriter.export(out);
			logger.info("导出Excel文件[成功]");
		}
		catch (IOException ex) {
			flag = false;
			logger.info("导出Excel文件[失败]");
			ex.printStackTrace();
		}
		return flag;
	}

	/**
	 * 通过数据库结果集生产excel文件
	 * 
	 * @param file
	 *            excel文件路径
	 * @param rs
	 *            查询数据库结果集
	 * @return
	 */
	public static Workbook getWorkbook(String file, ResultSet rs) {
		int columnCount = 0;// 记录列数
		OutputStream out = null;
		ResultSetMetaData rsmd = null;// 列的数据类型
		ExcelWriterTo2007 eWriter = null;
		try {
			rsmd = rs.getMetaData();
			int initcount = 0;
			columnCount = rsmd.getColumnCount();// 返回结果集列数
			eWriter = new ExcelWriterTo2007(out);
			// 添加数据
			while (rs.next()) {
				eWriter.createRow(initcount);
				for (int i = 1; i <= columnCount; i++) {
					if (rsmd.getColumnTypeName(i).equals("int") || rsmd.getColumnTypeName(i).equals("bit")) {
						eWriter.setCell(i - 1, rs.getInt(i));
					} else if (rsmd.getColumnTypeName(i).equals("numeric") || rsmd.getColumnTypeName(i).equals("money")) {
						eWriter.setCell(i - 1, rs.getDouble(i));
					} else if (rsmd.getColumnTypeName(i).equals("float")) {
						eWriter.setCell(i - 1, rs.getFloat(i));
					} else if (rsmd.getColumnTypeName(i).equals("datetime")) {
						if (null != rs.getDate(i)) {
							eWriter.setCell(i - 1, rs.getDate(i));
						} else {
							eWriter.setCell(i - 1, "");
						}
					} else {
						eWriter.setCell(i - 1, rs.getString(i));
					}
				}
				initcount++;
			}
		}
		catch (SQLException e) {
			setExcelWriterMsg("系统错误：" + e.getMessage());
			e.printStackTrace();
		}
		return eWriter.workbook;
	}

	/**
	 * 通过List生产excel文件
	 * 
	 * @param file
	 *            excel文件路径
	 * @param rs
	 *            查询数据库结果集
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Workbook getWorkbookByList(String file, List<List> li, String head) {
		OutputStream out = null;
		ExcelWriterTo2007 eWriter = new ExcelWriterTo2007(out);
		List liSmall = null;

		try {
			// 添加表头
			eWriter.createRow(0);
			String[] heads = head.split(",");
			for (int i = 0; i < heads.length; i++) {
				eWriter.setCell(i, heads[i]);
			}

			int initcount = 1;// 添加数据
			if (null != li && li.size() > 0) {
				for (int i = 0; i < li.size(); i++) {

					eWriter.createRow(initcount);
					liSmall = new ArrayList();
					liSmall = li.get(i);
					if (null != liSmall && liSmall.size() > 0) {
						for (int j = 0; j < liSmall.size(); j++) {
							Object obj = liSmall.get(j);
							if (obj instanceof Integer) {
								eWriter.setCell(j, (Integer) obj);
							} else if (obj instanceof Date) {
								eWriter.setCell(j, (Date) obj);
							} else if (obj instanceof Double) {
								eWriter.setCell(j, (Double) obj);
							} else if (obj instanceof Calendar) {
								if (null != obj) {
									eWriter.setCell(j, (Calendar) obj);
								} else {
									eWriter.setCell(j, "");
								}
							} else {
								eWriter.setCell(j, (String) obj);
							}
						}
					}
					initcount++;
				}

			}
		}
		catch (Exception e) {
			setExcelWriterMsg("系统错误：" + e.getMessage());
			e.printStackTrace();
		}
		return eWriter.workbook;
	}

	/**
	 * 通过List生产excel文件
	 * 
	 * @param file
	 *            excel文件路径
	 * @param rs
	 *            查询数据库结果集
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Workbook getWorkbookByList(String file, List<List> li) {
		OutputStream out = null;
		ExcelWriterTo2007 eWriter = new ExcelWriterTo2007(out);
		List liSmall = null;
		int initcount = 0;// 添加数据

		try {
			if (null != li && li.size() > 0) {
				for (int i = 0; i < li.size(); i++) {
					eWriter.createRow(initcount);
					liSmall = new ArrayList();
					liSmall = li.get(i);
					if (null != liSmall && liSmall.size() > 0) {
						for (int j = 0; j < liSmall.size(); j++) {
							Object obj = liSmall.get(j);
							if (obj instanceof Integer) {
								eWriter.setCell(j, (Integer) obj);
							} else if (obj instanceof Date) {
								eWriter.setCell(j, (Date) obj);
							} else if (obj instanceof Double) {
								eWriter.setCell(j, (Double) obj);
							} else if (obj instanceof Calendar) {
								if (null != obj) {
									eWriter.setCell(j, (Calendar) obj);
								} else {
									eWriter.setCell(j, "");
								}
								eWriter.setCell(j, (Calendar) obj);
							} else {
								eWriter.setCell(j, (String) obj);
							}

						}
					}
					initcount++;
				}

			}
		}
		catch (Exception e) {
			setExcelWriterMsg("系统错误：" + e.getMessage());
			e.printStackTrace();
		}
		return eWriter.workbook;
	}

	/**
	 * 通过数据库结果集生产excel文件
	 * 
	 * @param file
	 *            excel文件路径
	 * @param rs
	 *            查询数据库结果集
	 * @return
	 */
	public static boolean exportExcel(String file, ResultSet rs) {
		boolean flag = true;
		int columnCount = 0;// 记录列数
		ResultSetMetaData rsmd = null;// 列的数据类型
		OutputStream out = null;
		ExcelWriterTo2007 eWriter = null;
		try {
			out = new FileOutputStream(file);
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		eWriter = new ExcelWriterTo2007(out);
		try {
			rsmd = rs.getMetaData();
			int initcount = 1;
			columnCount = rsmd.getColumnCount();// 返回结果集列数

			// 添加数据
			while (rs.next()) {
				eWriter.createRow(initcount);
				for (int i = 1; i <= columnCount; i++) {
					if (rsmd.getColumnTypeName(i).equals("int") || rsmd.getColumnTypeName(i).equals("bit")) {
						eWriter.setCell(i - 1, rs.getInt(i));
					} else if (rsmd.getColumnTypeName(i).equals("numeric") || rsmd.getColumnTypeName(i).equals("money")) {
						eWriter.setCell(i - 1, rs.getDouble(i));
					} else if (rsmd.getColumnTypeName(i).equals("float")) {
						eWriter.setCell(i - 1, rs.getFloat(i));
					} else if (rsmd.getColumnTypeName(i).equals("datetime")) {
						eWriter.setCell(i - 1, rs.getDate(i));
					} else {
						eWriter.setCell(i - 1, rs.getString(i));
					}
				}
				initcount++;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			eWriter.export(out);
			logger.info("导出Excel文件[成功]");
		}
		catch (IOException ex) {
			flag = false;
			logger.info("导出Excel文件[失败]");
			ex.printStackTrace();
		}
		return flag;
	}

	/**
	 * 通过数据库结果集生产excel文件
	 * 
	 * @param file
	 *            excel文件路径
	 * @param rs
	 *            查询数据库结果集
	 * @param head
	 *            表头 <br>
	 *            格式 以逗号分隔 1,2,3,4
	 * @return
	 */
	public static Workbook getWorkbook(String file, ResultSet rs, String head) {
		int columnCount = 0;// 记录列数
		OutputStream out = null;
		ResultSetMetaData rsmd = null;// 列的数据类型
		ExcelWriterTo2007 eWriter = null;
		try {
			rsmd = rs.getMetaData();
			int initcount = 1;
			columnCount = rsmd.getColumnCount();// 返回结果集列数
			eWriter = new ExcelWriterTo2007(out);
			// 添加表头
			eWriter.createRow(0);
			String[] heads = head.split(",");
			for (int i = 0; i < heads.length; i++) {
				eWriter.setCell(i, heads[i]);
			}
			// 添加数据
			while (rs.next()) {
				eWriter.createRow(initcount);
				for (int i = 1; i <= columnCount; i++) {
					if (rsmd.getColumnTypeName(i).equals("int") || rsmd.getColumnTypeName(i).equals("bit")) {
						eWriter.setCell(i - 1, rs.getInt(i));
					} else if (rsmd.getColumnTypeName(i).equals("numeric") || rsmd.getColumnTypeName(i).equals("money")) {
						eWriter.setCell(i - 1, rs.getDouble(i));
					} else if (rsmd.getColumnTypeName(i).equals("float")) {
						eWriter.setCell(i - 1, rs.getFloat(i));
					} else if (rsmd.getColumnTypeName(i).equals("datetime")) {
						if (null != rs.getDate(i)) {
							eWriter.setCell(i - 1, rs.getDate(i));
						} else {
							eWriter.setCell(i - 1, "");
						}
					} else {
						eWriter.setCell(i - 1, rs.getString(i));
					}
				}
				initcount++;
			}
		}
		catch (SQLException e) {
			setExcelWriterMsg("系统错误：" + e.getMessage());
			e.printStackTrace();
		}
		return eWriter.workbook;
	}

	/**
	 * 通过数据库结果集生产excel文件
	 * 
	 * @param file
	 *            excel文件路径
	 * @param rs
	 *            查询数据库结果集
	 * @param head
	 *            表头 <br>
	 *            格式 以逗号分隔 1,2,3,4
	 * @return
	 */
	public static boolean exportExcel(String file, ResultSet rs, String head) {
		boolean flag = true;
		int columnCount = 0;// 记录列数
		ResultSetMetaData rsmd = null;// 列的数据类型
		OutputStream out = null;
		ExcelWriterTo2007 eWriter = null;
		try {
			out = new FileOutputStream(file);
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		eWriter = new ExcelWriterTo2007(out);
		try {
			rsmd = rs.getMetaData();
			int initcount = 1;
			columnCount = rsmd.getColumnCount();// 返回结果集列数
			// 添加表头
			eWriter.createRow(0);
			String[] heads = head.split(",");
			for (int i = 0; i < heads.length; i++) {
				eWriter.setCell(i, heads[i]);
			}

			// 添加数据
			while (rs.next()) {
				eWriter.createRow(initcount);
				for (int i = 1; i <= columnCount; i++) {
					if (rsmd.getColumnTypeName(i).equals("int") || rsmd.getColumnTypeName(i).equals("bit")) {
						eWriter.setCell(i - 1, rs.getInt(i));
					} else if (rsmd.getColumnTypeName(i).equals("numeric") || rsmd.getColumnTypeName(i).equals("money")) {
						eWriter.setCell(i - 1, rs.getDouble(i));
					} else if (rsmd.getColumnTypeName(i).equals("float")) {
						eWriter.setCell(i - 1, rs.getFloat(i));
					} else if (rsmd.getColumnTypeName(i).equals("datetime")) {
						eWriter.setCell(i - 1, rs.getDate(i));
					} else {
						eWriter.setCell(i - 1, rs.getString(i));
					}
				}
				initcount++;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			eWriter.export(out);
			logger.info("导出Excel文件[成功]");
		}
		catch (IOException ex) {
			flag = false;
			logger.info("导出Excel文件[失败]");
			ex.printStackTrace();
		}
		return flag;
	}

	/**
	 * 通过数据库结果集生产excel文件
	 * 
	 * @param file
	 *            excel文件
	 * @param rs
	 *            查询数据库结果集
	 * @return
	 */
	public static boolean exportExcel(File file, ResultSet rs) {
		createFile(file);// 创建目录
		boolean flag = true;
		int columnCount = 0;// 记录列数
		ResultSetMetaData rsmd = null;// 列的数据类型
		// 初始化类
		ExcelWriterTo2007 eWriter = null;
		OutputStream out = null;
		try {
			out = new FileOutputStream(file);
			eWriter = new ExcelWriterTo2007(new FileOutputStream(file));
		}
		catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		try {
			rsmd = rs.getMetaData();
			int initcount = 0;
			columnCount = rsmd.getColumnCount();// 返回结果集列数
			// 添加数据
			while (rs.next()) {
				eWriter.createRow(initcount);
				for (int i = 1; i <= columnCount; i++) {
					if (rsmd.getColumnTypeName(i).equals("int") || rsmd.getColumnTypeName(i).equals("bit")) {
						eWriter.setCell(i - 1, rs.getInt(i));
					} else if (rsmd.getColumnTypeName(i).equals("numeric") || rsmd.getColumnTypeName(i).equals("money")) {
						eWriter.setCell(i - 1, rs.getDouble(i));
					} else if (rsmd.getColumnTypeName(i).equals("float")) {
						eWriter.setCell(i - 1, rs.getFloat(i));
					} else if (rsmd.getColumnTypeName(i).equals("datetime")) {
						eWriter.setCell(i - 1, rs.getDate(i));
					} else {
						eWriter.setCell(i - 1, rs.getString(i));
					}
				}
				initcount++;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			eWriter.export(out);
			logger.info("导出Excel文件[成功]");
		}
		catch (IOException ex) {
			flag = false;
			logger.info("导出Excel文件[失败]");
			ex.printStackTrace();
		}
		return flag;
	}

	/**
	 * excel导出
	 * 
	 * @param file
	 *            文件名
	 * @param rs
	 *            数据结果集
	 * @param head
	 *            表头 <br>
	 *            格式 以逗号分隔 1,2,3,4
	 * @return
	 */
	public static boolean exportExcel(File file, ResultSet rs, String head) {
		createFile(file);// 创建目录
		boolean flag = true;
		int columnCount = 0;// 记录列数
		ResultSetMetaData rsmd = null;// 列的数据类型
		// 初始化类
		ExcelWriterTo2007 eWriter = null;
		OutputStream out = null;
		try {
			out = new FileOutputStream(file);
			eWriter = new ExcelWriterTo2007(new FileOutputStream(file));
		}
		catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		try {
			rsmd = rs.getMetaData();
			int initcount = 1;
			columnCount = rsmd.getColumnCount();// 返回结果集列数
			// 添加表头
			eWriter.createRow(0);
			String[] heads = head.split(",");
			for (int i = 0; i < heads.length; i++) {
				eWriter.setCell(i, heads[i]);
			}

			// 添加数据
			while (rs.next()) {
				eWriter.createRow(initcount);
				for (int i = 1; i <= columnCount; i++) {
					if (rsmd.getColumnTypeName(i).equals("int") || rsmd.getColumnTypeName(i).equals("bit")) {
						eWriter.setCell(i - 1, rs.getInt(i));
					} else if (rsmd.getColumnTypeName(i).equals("numeric") || rsmd.getColumnTypeName(i).equals("money")) {
						eWriter.setCell(i - 1, rs.getDouble(i));
					} else if (rsmd.getColumnTypeName(i).equals("float")) {
						eWriter.setCell(i - 1, rs.getFloat(i));
					} else if (rsmd.getColumnTypeName(i).equals("datetime")) {
						eWriter.setCell(i - 1, rs.getDate(i));
					} else {
						eWriter.setCell(i - 1, rs.getString(i));
					}
				}
				initcount++;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			eWriter.export(out);
			logger.info("导出Excel文件[成功]");
		}
		catch (IOException ex) {
			flag = false;
			logger.info("导出Excel文件[失败]");
			ex.printStackTrace();
		}
		return flag;
	}

	/**
	 * @param file
	 *            excel文件
	 * @param list
	 *            相当于表格 一个二维数组<br>
	 *            格式 list[list]
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean exportExcel(File file, List<List> list) {
		createFile(file);// 创建目录
		boolean flag = true;

		// 初始化类
		ExcelWriterTo2007 eWriter = null;
		OutputStream out = null;
		try {
			out = new FileOutputStream(file);
			eWriter = new ExcelWriterTo2007(new FileOutputStream(file));
		}
		catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		for (int i = 0; i < list.size(); i++) {
			List dataList = list.get(i);
			eWriter.createRow(i);
			for (int j = 0; j < dataList.size(); j++) {
				Object obj = dataList.get(j);
				if (obj instanceof Integer) {
					eWriter.setCell(j, (Integer) obj);
				}
				if (obj instanceof String) {
					eWriter.setCell(j, (String) obj);
				}
				if (obj instanceof Date) {
					eWriter.setCell(j, (Date) obj);
				}
				if (obj instanceof Double) {
					eWriter.setCell(j, (Double) obj);
				}
				if (obj instanceof Calendar) {
					eWriter.setCell(j, (Calendar) obj);
				}
			}
		}

		try {
			eWriter.export(out);
			logger.info("导出Excel文件[成功]");
		}
		catch (IOException ex) {
			flag = false;
			logger.info("导出Excel文件[失败]");
			ex.printStackTrace();
		}
		return flag;
	}

	public static void createFile(String file) {
		File pathFile = new File(file);
		createFile(pathFile);
	}

	public static void createFile(File file) {
		File pathFile = new File(file.getParent());
		if (!pathFile.exists()) {
			pathFile.mkdirs();
		}
	}

	/**
	 * @param dateStr
	 *            时间字符串
	 * @param formatStr
	 *            格式 例如：yyyy-MM-dd
	 * @return Date 日期
	 */
	public static Date StringToDate(String dateStr, String formatStr) {
		DateFormat dd = new SimpleDateFormat(formatStr);
		Date date = null;
		try {
			date = dd.parse(dateStr);
		}
		catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) {
		logger.info("开始导出Excel文件");
		List list = new ArrayList();
		for (int i = 0; i < 1000000; i++) {
			List listdata = new ArrayList();
			listdata.add("字符串");
			listdata.add("整数");
			listdata.add("日期");
			listdata.add("小数");
			list.add(listdata);
		}
		ExcelWriterTo2007 writer = new ExcelWriterTo2007();
		writer.exportExcel(new File("d:/2/3/ddaa.xls"), list);

	}
}
