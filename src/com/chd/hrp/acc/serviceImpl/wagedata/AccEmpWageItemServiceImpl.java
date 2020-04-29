/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.serviceImpl.wagedata;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Footer;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.ExcelReader;
import com.chd.base.util.FileUtil;
import com.chd.hrp.acc.dao.AccWageItemsMapper;
import com.chd.hrp.acc.dao.wagedata.AccEmpWageItemMapper;
import com.chd.hrp.acc.dao.wagedata.AccWagePayMapper;
import com.chd.hrp.acc.entity.AccWagePay;
import com.chd.hrp.acc.service.wagedata.AccEmpWageItemService;
import com.chd.hrp.sys.service.base.SysTableStyleService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 个人工资查询<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("accEmpWageItemService")
public class AccEmpWageItemServiceImpl implements AccEmpWageItemService {

	private static Logger logger = Logger.getLogger(AccEmpWageItemServiceImpl.class);
	
	@Resource(name = "sysTableStyleService")
	private final SysTableStyleService sysTableStyleService = null;
	
	@Resource(name = "accEmpWageItemMapper")
	private final AccEmpWageItemMapper accEmpWageItemMapper = null;
	
	@Resource(name = "accWageItemsMapper")
	private final AccWageItemsMapper accWageItemsMapper = null;
	
	@Resource(name = "accWagePayMapper")
	private final AccWagePayMapper accWagePayMapper = null;
    
	/**
	 * @Description 
	 * 个人工资查询<BR> 查询AccEmpWageItem分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAccEmpWageItem(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage)entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<Map<String,Object>> list = accEmpWageItemMapper.queryAccEmpWageItem(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = accEmpWageItemMapper.queryAccEmpWageItem(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list,page.getTotal());
		}
		
	}
	
	/**
	 * @Description 
	 * 个人工资查询<BR> 查询AccEmpWageItemByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AccWagePay queryAccEmpWageItemByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return accEmpWageItemMapper.queryAccEmpWageItemByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 个人工资查询<BR> 更新AccEmpWageItem
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAccEmpWageItem(Map<String,Object> entityMap)throws DataAccessException{

		try {

			accEmpWageItemMapper.updateAccEmpWageItem(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccEmpWageItem\"}";

		}
	}
	
	/**
	 * @Description 
	 * 个人工资查询<BR> 批量更新AccEmpWageItem
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAccEmpWageItem(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			accEmpWageItemMapper.updateBatchAccEmpWageItem(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccEmpWageItem\"}";

		}
		
	}
	@Override
	public List<Map<String,Object>> queryAccEmpWageItemPrint(Map<String,Object> entityMap) throws DataAccessException{
		
		entityMap.put("group_id", SessionManager.getGroupId());
	       
		entityMap.put("hos_id", SessionManager.getHosId());
        
		entityMap.put("copy_code", SessionManager.getCopyCode());
		
		if(entityMap.get("emp_code") != null){
			//用来转换大写
			entityMap.put("emp_code",entityMap.get("emp_code").toString().toUpperCase());
			
		}
		
		List<Map<String,Object>> list = accEmpWageItemMapper.queryAccEmpWageItem(entityMap);
		
		return list;
	}

	/**
	 * 生成工资条excel数据
	 */
	@Override
	public void generatePayrollExcelData(Map<String, Object> paraMap) throws DataAccessException {
		if (paraMap.get("emp_code") != null) {
			// 用来转换大写
			paraMap.put("emp_code", paraMap.get("emp_code").toString().toUpperCase());
		}

		// 职工工资数据
		List<Map<String, Object>> wageList = accEmpWageItemMapper.queryEmpWageItemList(paraMap);

		List<Map<String, Object>> wageItemList = new ArrayList<Map<String, Object>>();
		if (paraMap.containsKey("scheme_id") && !"".equals(paraMap.get("scheme_id"))) {
			wageItemList = accWagePayMapper.queryAccWagePayGrid(paraMap);
		} else {
			wageItemList = accWagePayMapper.queryAccWageItemGrid(paraMap);
		}

		try {
			String toFilePath = paraMap.get("toFilePath").toString();

			// 生成excel
			Workbook workBook = ExcelReader.getWorkBookGrid(paraMap.get("fromFilePath").toString(), toFilePath);
			if (workBook == null) {
				logger.error("生成打印模板异常！");
				throw new SysException("生成打印模板异常！");
			}

			// 查询样式
			String pageJson = sysTableStyleService.queryPrintByPageUrl(paraMap);

			// 填充数据
			if (wageList.isEmpty()) {
				logger.error("没有数据");
				throw new SysException("没有数据");
			} else {
				// 填充
				writePayrollData(workBook, wageList, wageItemList, paraMap, pageJson);

				// 创建temp文件夹
				File toFile = new File(toFilePath.substring(0, toFilePath.lastIndexOf("\\")));
				if (!toFile.exists()) {
					toFile.mkdirs();
				}

				// 传目录，删除昨天的临时文件
				FileUtil.deleteTempPath(toFile);

				FileOutputStream fileOut = new FileOutputStream(toFilePath);
				workBook.write(fileOut);
				fileOut.close();
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	
	/**
	 * 填写工资条数据
	 */
	private void writePayrollData(Workbook workBook, List<Map<String, Object>> wageList, List<Map<String, Object>> wageItemList,
			Map<String, Object> paraMap, String pageJson) throws Exception{
		try{
			int columnNum = Integer.valueOf(paraMap.get("columnNum").toString());
			columnNum = (wageItemList.size() + 4) < columnNum || 0 == columnNum? wageItemList.size() + 4 : columnNum;
			
			JSONObject myStyle = null;
			Map<Integer, CellStyle> keyColStyleMap = new HashMap<Integer, CellStyle>();
			Map<Integer, CellStyle> valColStyleMap = new HashMap<Integer, CellStyle>();
			if (pageJson != null && !pageJson.equals("")) {
				try {
					myStyle = JSONObject.parseObject(pageJson).getJSONObject("cells");
				} catch (Exception e) {
					pageJson = null;
				}
			}
			
			Sheet sheet = workBook.getSheetAt(0);
			int rowIndex = 0; // 行索引
			for(int i = 0; i < columnNum; i++){
				sheet.setColumnWidth(i, 2800);// 设置列宽
			}
			
			// 写入标题
			if(paraMap.get("title")!=null && !paraMap.get("title").equals("")){
				Row row;
				if(sheet.getRow(0)==null){
					row=sheet.createRow(0);
				}else{
					row=sheet.getRow(0);
				}
				row.setHeight((short)800);
				
				Cell cell;
				if(row.getCell(0)==null){
					cell=row.createCell(0);
				}else{
					cell=row.getCell(0);
				}
				cell.setCellValue(paraMap.get("title").toString());
				sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, columnNum - 1));
				Font ftStyle = workBook.createFont();//新建字体样式 
				ftStyle.setFontName("宋体");
				ftStyle.setFontHeightInPoints((short) 14);
				ftStyle.setBold(true);
				setCellStyle(workBook, cell, "center", myStyle, false, ftStyle, "title", keyColStyleMap);
				
				rowIndex++;
				paraMap.put("rowIndex", rowIndex);
			}

			// 页眉右上角默认显示页码
//			boolean isAutoPageHead = true;
			// 页脚默认左下角显示打印人、右下角显示打印时间
//			boolean isAutoPageFoot = true;
			if (paraMap.get("heads") != null && !paraMap.get("heads").equals("")) {
				JSONObject headsJson = null;
				try {
					String heads = paraMap.get("heads").toString();
					logger.debug("打印表头条件：" + heads);
					headsJson = JSONObject.parseObject(heads);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					throw new SysException("heads参数格式不符合json规范！");
				}

//				if (headsJson.getString("isAuto") != null && headsJson.getString("isAuto").equals("false")) {
//					isAutoPageHead = false;
//				}

				// 表头json数组对象
				if (headsJson.getString("rows") != null && !headsJson.getString("rows").equals("")) {
					JSONArray headRowsArray = JSONObject.parseArray(headsJson.getString("rows"));
					setPageOfficeHeadConAndFoot(workBook, sheet, headRowsArray, columnNum, paraMap, myStyle, keyColStyleMap);
				}
			}
			
			rowIndex = (int) paraMap.get("rowIndex");
			
			DecimalFormat moneyFormat = new DecimalFormat("##,##0.00");
			int itemNum = 4 + wageItemList.size();// 工资条的信息有itemNum项,4:职工编号、姓名、部门名称、工资日期
			// 遍历所有职工工资条
			for(Map<String, Object> wageData : wageList){
				Row keyRow = null; 
				Row valRow = null;
				
				// 遍历每个工资项
				for(int i = 0; i < itemNum; i++){
					int cellIndex = i % columnNum;
					if(cellIndex == 0){
						if(sheet.getRow(rowIndex) == null){
							keyRow = sheet.createRow(rowIndex);
						}else{
							keyRow = sheet.getRow(rowIndex);
						}
						
						if(sheet.getRow(rowIndex + 1) == null){
							valRow = sheet.createRow(rowIndex + 1);
						}else{
							valRow = sheet.getRow(rowIndex + 1);
						}
						
						rowIndex += 2;
					}
					
					Cell keyCell, valCell;
					
					if(keyRow.getCell(cellIndex) == null){
						keyCell = keyRow.createCell(cellIndex);
					}else{
						keyCell = keyRow.getCell(cellIndex);
					}
					
					if(valRow.getCell(cellIndex) == null){
						valCell = valRow.createCell(cellIndex);
					}else{
						valCell = valRow.getCell(cellIndex);
					}
					
					// 前两项填写姓名与部门
					if(cellIndex == 0 && i == 0){
						keyCell.setCellValue("职工编码");
						valCell.setCellValue(wageData.get("EMP_CODE").toString());
						setCellStyle(workBook, valCell, "center", myStyle, true, null, "body", keyColStyleMap);
					}else if(cellIndex == 1 && i == 1){
						keyCell.setCellValue("职工姓名");
						valCell.setCellValue(wageData.get("EMP_NAME").toString());
						setCellStyle(workBook, valCell, "center", myStyle, true, null, "body", keyColStyleMap);
					}else if(cellIndex == 2 && i == 2){
						keyCell.setCellValue("职工部门");
						valCell.setCellValue(wageData.get("DEPT_NAME").toString());
						setCellStyle(workBook, valCell, "center", myStyle, true, null, "body", keyColStyleMap);
					}else if((i + 1) == itemNum){
						keyCell.setCellValue("工资日期");
						valCell.setCellValue(wageData.get("WAGE_DATE").toString());
						setCellStyle(workBook, valCell, "center", myStyle, true, null, "body", keyColStyleMap);
					}else{
						keyCell.setCellValue(wageItemList.get(i - 3).get("ITEM_NAME").toString());
						String val = wageData.get(wageItemList.get(i - 3).get("COLUMN_ITEM").toString()).toString();
						val = moneyFormat.format(new BigDecimal(val));
						valCell.setCellValue(val);
						setCellStyle(workBook, valCell, "right", myStyle, true, null, "body", valColStyleMap);
					}
					
					setCellStyle(workBook, keyCell, "center", myStyle, true, null, "body", keyColStyleMap);
				}
				
				rowIndex++;
			}
			
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("isAutoPageHead", true);
			paramMap.put("isAutoPageFoot", true);
			setPageOfficeBodyEnd(workBook, sheet, paramMap, pageJson);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	/**
	 * 根据保存的格式设置单元格样式
	 */
	private void setCellStyle(Workbook workbook,Cell cell,String align,JSONObject myStyle,boolean isBorder,Font fontStyle,String flag,Map<Integer,CellStyle> colStyleMap){
		
		CellStyle cellStyle=null;
		boolean isStyle=false;
		
		if(flag.equals("body") && colStyleMap!=null && colStyleMap.size()>0 && colStyleMap.get(cell.getColumnIndex())!=null){
			cellStyle=colStyleMap.get(cell.getColumnIndex());//取第一次设置的列单元格式
			cell.setCellStyle(cellStyle);
			return;
		}
		
		if(myStyle!=null){
			try{
				
				//明细数据是变动的，每列单元格的样式保持一致，createCellStyle()不能超过4000
				
				JSONArray rowArray=myStyle.getJSONArray(String.valueOf(cell.getRowIndex()));
				JSONObject cellJson=null;
				for(int i=0;i<rowArray.size();i++){
					cellJson = JSONObject.parseObject(rowArray.getString(i));
					if(cellJson.getIntValue("colindex")==cell.getColumnIndex()){
						//找相等的colindex，因为单元格没有数据不会有样式，所以不能直接取索引
						break;
					}
				}
				String fontName=cellJson.getString("fontName");
				if(cellStyle==null){
					cellStyle=workbook.createCellStyle();//新建单元格样式
				}
				if(fontName!=null && !fontName.equals("")){
					Font ftStyle = workbook.createFont();//新建字体样式 
					ftStyle.setFontName(fontName);
					ftStyle.setFontHeightInPoints(cellJson.getShortValue("fontPoint"));
					ftStyle.setBold(cellJson.getBooleanValue("fontBold"));
					//System.out.println("row:"+cell.getRowIndex()+",cell:"+cell.getColumnIndex()+",fontPoint:"+cellJson.getShortValue("fontPoint"));
					cellStyle.setFont(ftStyle);
				}
				
				//对齐方式
				Short align1=cellJson.getShortValue("align");
				switch (align1){
				case 2:
					cellStyle.setAlignment(CellStyle.ALIGN_CENTER);//居中
					break;
				case 3:
					cellStyle.setAlignment(CellStyle.ALIGN_RIGHT);//居右 
					break;
				default:
					cellStyle.setAlignment(CellStyle.ALIGN_LEFT);//居左
					break;
				}
				
				cellStyle.setWrapText(cellJson.getBooleanValue("wrap"));//自动换行
				isStyle=true;
				
			}catch(Exception e){
				isStyle=false;
			}
		}
		
		if(cellStyle==null){
			cellStyle=workbook.createCellStyle();//新建单元格样式
		}
		
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中 
		//网格线
		if(isBorder){
			cellStyle.setBorderBottom(CellStyle.BORDER_THIN); //下边框    
			cellStyle.setBorderLeft(CellStyle.BORDER_THIN);//左边框    
			cellStyle.setBorderTop(CellStyle.BORDER_THIN);//上边框    
			cellStyle.setBorderRight(CellStyle.BORDER_THIN);//右边框  
		}
		
		//找不到格式，取默认格式
		if(!isStyle){
			//前端传过来的参数
			cellStyle.setAlignment(CellStyle.ALIGN_LEFT);//居左
			if(align!=null && !align.equals("")){
				if(align.equalsIgnoreCase("center")){
					cellStyle.setAlignment(CellStyle.ALIGN_CENTER);//居中 
				}else if(align.equalsIgnoreCase("right")){
					cellStyle.setAlignment(CellStyle.ALIGN_RIGHT);//居右 
				}
			}
			
			if(fontStyle!=null){
				cellStyle.setFont(fontStyle);
			}
			
		}
		
		if(flag.equals("body") && colStyleMap!=null && colStyleMap.get(cell.getColumnIndex())==null){
			colStyleMap.put(cell.getColumnIndex(), cellStyle);
		}
		
		cell.setCellStyle(cellStyle);
		
	}

	/**
	 * 填充PageOffice数据-结束方法-打印格式处理
	 */
	private void setPageOfficeBodyEnd(Workbook workBook, Sheet sheet, Map<String, Object> paramMap, String pageJson) {

		boolean isAutoPageHead = Boolean.parseBoolean(paramMap.get("isAutoPageHead").toString());
		boolean isAutoPageFoot = Boolean.parseBoolean(paramMap.get("isAutoPageFoot").toString());

		// 没有数据
		if (pageJson == null || pageJson.equals("")) {

			// 默认其他格式
			StringBuffer json = new StringBuffer();
			json.append("{");

			// 打印网格线
			json.append("\"PrintGridlines\":\"false\",");

//			// 打印标题
//			json.append("\"TitleRowStart\":\"0\",");// 起始行
//			json.append("\"TitleRowEnd\":\"" + headIndex + "\",");// 结束行
//			json.append("\"TitleCellStart\":\"0\",");// 起始列
//			json.append("\"TitleCellEnd\":\"" + maxcolumnIndex + "\",");// 结束列

			// 页边距
			json.append("\"TopMargin\":\"0.3\",");// 页边距（上） 换算：0.3=0.8
			json.append("\"BottomMargin\":\"0.3\",");// 页边距（下）
			json.append("\"LeftMargin\":\"0.5\",");// 页边距（左）换算：0.5=1.3
			json.append("\"RightMargin\":\"0.0\",");// 页边距（右）
			json.append("\"HorizontallyCenter\":\"true\",");// 水平
			json.append("\"VerticallyCenter\":\"false\",");// 垂直

			// 纸张信息
			json.append("\"Landscape\":\"false\",");// 打印方向，true：横向，false：纵向(默认)
			json.append("\"VResolution\":\"300\",");// 打印质量600点
			json.append("\"PaperSize\":\"9\",");// 纸张类型 A4_PAPERSIZE
			json.append("\"FitWidth\":\"1\",");// 设置页宽(调整)
			json.append("\"FitHeight\":\"1\",");// 设置页高(调整)
			json.append("\"HeaderMargin\":\"0.0\",");// 页眉距离
			json.append("\"FooterMargin\":\"0.0\",");// 页脚距离
			json.append("\"PageStart\":\"0\",");// 起始页码

			// 页眉内容
			json.append("\"HeadLeft\":\"\",");// 页眉-左
			json.append("\"HeadCenter\":\"\",");// 页眉-中
			json.append("\"HeadRight\":\"\",");// 页眉-右

			// 页脚内容
			json.append("\"FootLeft\":\"\",");// 页脚-左
			json.append("\"FootCenter\":\"\",");// 页脚-中
			json.append("\"FootRight\":\"\",");// 页脚-右

			// 列宽
			json.append("\"columns\":[]");

			json.append("}");
			pageJson = json.toString();
		}

		JSONObject excelObj = JSONObject.parseObject(pageJson);

		// 设置列样式
		JSONArray columnArray = JSONObject.parseArray(excelObj.getString("columns"));
		if (columnArray != null && columnArray.size() > 0) {
			for (int i = 0; i < columnArray.size(); i++) {
				JSONObject cellJson = JSONObject.parseObject(columnArray.getString(i));
				int columnindex = cellJson.getIntValue("columnindex");
				sheet.setColumnWidth(columnindex, cellJson.getIntValue("width"));// 列宽
				sheet.setColumnHidden(columnindex, cellJson.getBooleanValue("hide"));// 列隐藏
			}
		}

		// 打印网格线
		sheet.setPrintGridlines(excelObj.getBooleanValue("PrintGridlines"));

		// 页边距
		sheet.setMargin(Sheet.TopMargin, excelObj.getDoubleValue("TopMargin"));// 页边距（上）
																				// 换算：0.3=0.8
		sheet.setMargin(Sheet.BottomMargin, excelObj.getDoubleValue("BottomMargin"));// 页边距（下）
		sheet.setMargin(Sheet.LeftMargin, excelObj.getDoubleValue("LeftMargin"));// 页边距（左）
																					// 换算：0.5=1.3
		sheet.setMargin(Sheet.RightMargin, excelObj.getDoubleValue("RightMargin"));// 页边距（右）
		sheet.setHorizontallyCenter(excelObj.getBooleanValue("HorizontallyCenter")); // 设置打印页面为水平居中
		sheet.setVerticallyCenter(excelObj.getBooleanValue("VerticallyCenter")); // 设置打印页面为垂直居中

		PrintSetup ps = sheet.getPrintSetup();
		ps.setLandscape(excelObj.getBooleanValue("Landscape")); // 打印方向，true：横向，false：纵向(默认)
		ps.setVResolution(excelObj.getShortValue("VResolution"));// 打印质量600点
		ps.setPaperSize(excelObj.getShortValue("PaperSize")); // 纸张类型
		ps.setFitWidth(excelObj.getShortValue("FitWidth"));// 设置页宽(调整)
		ps.setFitHeight(excelObj.getShortValue("FitHeight"));// 设置页高(调整)
		ps.setHeaderMargin(excelObj.getDoubleValue("HeaderMargin")); // 页眉距离
		ps.setFooterMargin(excelObj.getDoubleValue("FooterMargin")); // 页脚距离
		ps.setPageStart(excelObj.getShortValue("PageStart")); // 起始页码

		// 页眉页脚内容
		Header head = sheet.getHeader();
		head.setLeft(excelObj.getString("HeadLeft"));// 页眉-左
		head.setCenter(excelObj.getString("HeadCenter"));// 页眉-中
		if (isAutoPageHead) {
			head.setRight("&P/&N");// 页眉-右
		} else {
			head.setRight(excelObj.getString("HeadRight"));// 页眉-右
		}

		// 页脚内容
		Footer foot = sheet.getFooter();
		foot.setCenter(excelObj.getString("FootCenter"));// 页眉-中
		if (isAutoPageFoot) {
			foot.setLeft("打印人：" + SessionManager.getUserName());
		} else {
			foot.setLeft(excelObj.getString("FootLeft"));
		}

		if (isAutoPageFoot) {
			foot.setRight("打印时间：&D &T");// 页脚-左
		} else {
			foot.setRight(excelObj.getString("FootRight"));// 页脚-右
		}
	}
	
	// 填充PageOffice表头需要打印的查询条件、表尾
	private void setPageOfficeHeadConAndFoot(Workbook workBook, Sheet sheet, JSONArray headFootArray,
			int maxcolumnIndex, Map<String, Object> paramMap, JSONObject myStyle, Map<Integer, CellStyle> colStyleMap) {

		int rowIndex = Integer.parseInt(paramMap.get("rowIndex").toString());
		Row row;
		Cell cell;

		for (int i = 0; i < headFootArray.size(); i++) {

			JSONObject cellJson = JSONObject.parseObject(headFootArray.getString(i));
			if (cellJson.getString("cell") == null || cellJson.getString("cell").equals("")) {
				throw new SysException("表头查询条件【cell】为空！");
			}

			if (cellJson.getString("br") != null && cellJson.getString("br").equals("true")) {
				rowIndex++;
			}

			if (sheet.getRow(rowIndex) == null) {
				row = sheet.createRow(rowIndex);
			} else {
				row = sheet.getRow(rowIndex);
			}
			row.setHeight((short) 400);

			int cellIndex = Integer.parseInt(cellJson.getString("cell"));
			if (cellJson.getString("from") != null && cellJson.getString("from").equalsIgnoreCase("right")) {
				// 从右开始填充
				cellIndex = maxcolumnIndex - cellIndex;
			}

			if (row.getCell(cellIndex) == null) {
				cell = row.createCell(cellIndex);
			} else {
				cell = row.getCell(cellIndex);
			}
			cell.setCellValue(cellJson.getString("value"));

			// 设置单元格合并列
			if (cellJson.getString("colSpan") != null && !cellJson.getString("colSpan").equals("")) {
				int colSpan = Integer.parseInt(cellJson.getString("colSpan"));
				if (cellJson.getString("colSpan").equalsIgnoreCase("-1")) {
					colSpan = maxcolumnIndex + 1;
				}
				CellRangeAddress region = new CellRangeAddress(rowIndex, rowIndex, cellIndex == 0 ? 0 : cellIndex + 1,
						cellIndex + colSpan - 1);
				sheet.addMergedRegion(region);// 下标从0开始要-1

			}

			String align = cellJson.getString("align") == null ? null : cellJson.getString("align").toString();
			/*
			 * if(align!=null && !align.equals("")){
			 * if(align.equalsIgnoreCase("center")){
			 * cell.setCellStyle(cellStyleCenter);//设置单元格样式-水平居中 }else
			 * if(align.equalsIgnoreCase("right")){
			 * cell.setCellStyle(cellStyleRight);//设置单元格样式-水平居右 }else{
			 * cell.setCellStyle(cellStyleLeft);//设置单元格样式-水平居左 } }else{
			 * cell.setCellStyle(cellStyleLeft);//设置单元格样式-水平居左 }
			 */
			setCellStyle(workBook, cell, align, myStyle, false, null, "HeadConAndFoot", colStyleMap);
		}

		paramMap.put("rowIndex", rowIndex + 1);
	}
	
}
