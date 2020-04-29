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
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * 生成导出Excel文件对象,excel 2003
 * 
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-4-29 下午1:33:01
 * @Company: 智慧云康（北京）数据科教有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
public class ExcelWriter {
	private static Logger logger = Logger.getLogger(ExcelWriter.class);
	private static String excelWriterMsg = "";

	// 定制浮点数格式
	private static String NUMBER_FORMAT = "#,##0.00";

	// 定制日期格式
	private static String DATE_FORMAT = "m/d/yy"; // "m/d/yy h:mm"

	private HSSFWorkbook workbook = null;

	private HSSFSheet sheet = null;

	private HSSFRow row = null;

	private OutputStream out = null;

	private HSSFCellStyle cellStyle = null;

	private ExcelWriter() {

	}

	public static String getExcelWriterMsg() {
		return excelWriterMsg;
	}

	public static void setExcelWriterMsg(String excelWriterMsg) {
		ExcelWriter.excelWriterMsg = excelWriterMsg;
	}

	/**
	 * 初始化Excel
	 */
	public ExcelWriter(OutputStream out) {
		this.out = out;
		this.workbook = new HSSFWorkbook();
		this.sheet = workbook.createSheet("Sheet1");
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
		HSSFCell cell = this.row.getCell(index);
		String strExcelCell = "";
		if (cell != null) {
			switch (cell.getCellType()) {
				case HSSFCell.CELL_TYPE_FORMULA:
					strExcelCell = "FORMULA ";
					break;
				case HSSFCell.CELL_TYPE_NUMERIC: {
					strExcelCell = String.valueOf(cell.getNumericCellValue());
				}
					break;
				case HSSFCell.CELL_TYPE_STRING:
					strExcelCell = cell.getStringCellValue();
					break;
				case HSSFCell.CELL_TYPE_BLANK:
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
		HSSFCell cell = this.row.createCell(index);
		cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
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
		HSSFCell cell = this.row.createCell(index);
		cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		cell.setCellValue(value);
		HSSFDataFormat format = workbook.createDataFormat();
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
		HSSFCell cell = this.row.createCell(index);
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
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
		HSSFCell cell = this.row.createCell(index);
		// cell.setEncoding(XLS_ENCODING);
		cell.setCellValue(value.getTime());
		cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat(DATE_FORMAT)); // 设置cell样式为定制的日期格式
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
		HSSFCell cell = this.row.createCell(index);
		cell.setCellValue(value);
		cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat(DATE_FORMAT)); // 设置cell样式为定制的日期格式
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
		ExcelWriter eWriter = null;
		try {
			out = new FileOutputStream(file);
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		eWriter = new ExcelWriter(out);
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

			System.out.println(" 导出Excel文件[成功] ");
		}
		catch (IOException ex) {
			flag = false;
			System.out.println(" 导出Excel文件[失败] ");
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
	public static HSSFWorkbook getWorkbook(String file, ResultSet rs) {
		int columnCount = 0;// 记录列数
		ResultSetMetaData rsmd = null;// 列的数据类型
		OutputStream out = null;
		ExcelWriter eWriter = new ExcelWriter(out);
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
	public static HSSFWorkbook getWorkbookByList(String file, List<List> li, String head) {
		int columnCount = 0;// 记录列数
		ResultSetMetaData rsmd = null;// 列的数据类型
		OutputStream out = null;
		ExcelWriter eWriter = new ExcelWriter(out);
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
			// TODO Auto-generated catch block
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
	public static HSSFWorkbook getWorkbookByList(String file, List<List> li) {
		int columnCount = 0;// 记录列数
		ResultSetMetaData rsmd = null;// 列的数据类型
		OutputStream out = null;
		ExcelWriter eWriter = new ExcelWriter(out);
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
			// TODO Auto-generated catch block
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
		ExcelWriter eWriter = null;
		try {
			out = new FileOutputStream(file);
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		eWriter = new ExcelWriter(out);
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

			System.out.println(" 导出Excel文件[成功] ");
		}
		catch (IOException ex) {
			flag = false;
			System.out.println(" 导出Excel文件[失败] ");
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
	public static HSSFWorkbook getWorkbook(String file, ResultSet rs, String head) {
		int columnCount = 0;// 记录列数
		ResultSetMetaData rsmd = null;// 列的数据类型
		OutputStream out = null;
		ExcelWriter eWriter = new ExcelWriter(out);
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
		ExcelWriter eWriter = null;
		try {
			out = new FileOutputStream(file);
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		eWriter = new ExcelWriter(out);
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

			System.out.println(" 导出Excel文件[成功] ");
		}
		catch (IOException ex) {
			flag = false;
			System.out.println(" 导出Excel文件[失败] ");
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
		ExcelWriter eWriter = null;
		OutputStream out = null;
		try {
			out = new FileOutputStream(file);
			eWriter = new ExcelWriter(new FileOutputStream(file));
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

			System.out.println(" 导出Excel文件[成功] ");
		}
		catch (IOException ex) {
			flag = false;
			System.out.println(" 导出Excel文件[失败] ");
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
		ExcelWriter eWriter = null;
		OutputStream out = null;
		try {
			out = new FileOutputStream(file);
			eWriter = new ExcelWriter(new FileOutputStream(file));
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

			System.out.println(" 导出Excel文件[成功] ");
		}
		catch (IOException ex) {
			flag = false;
			System.out.println(" 导出Excel文件[失败] ");
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
		ExcelWriter eWriter = null;
		OutputStream out = null;
		try {
			out = new FileOutputStream(file);
			eWriter = new ExcelWriter(new FileOutputStream(file));
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

			System.out.println(" 导出Excel文件[成功] ");
		}
		catch (IOException ex) {
			flag = false;
			System.out.println(" 导出Excel文件[失败] ");
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
	
	
	/*@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) {
		System.out.println(" 开始导出Excel文件 ");
		List list = new ArrayList();
		List listdata = new ArrayList();
		listdata.add("字符串");
		listdata.add("整数");
		listdata.add("日期");
		listdata.add("小数");
		List listdata1 = new ArrayList();
		listdata1.add("11111");
		listdata1.add("22222");
		listdata1.add("33333");
		listdata1.add("44444");
		list.add(listdata);
		list.add(listdata1);

		for (int i = 0; i < 65534; i++) {
			List listdata2 = new ArrayList();
			listdata2.add("11111");
			listdata2.add("22222");
			listdata2.add("33333");
			listdata2.add("44444");
			list.add(listdata2);
		}
		ExcelWriter writer = new ExcelWriter();
		writer.exportExcel(new File("d:/2/3/ddaa.xls"), list);

	}*/
}
