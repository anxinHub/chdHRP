package com.chd.hrp.sys.serviceImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Footer;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.exception.SysException;
import com.chd.base.util.DateUtil;
import com.chd.base.util.ExcelReader;
import com.chd.base.util.FileUtil;
import com.chd.base.util.NumberToCN;
import com.chd.base.util.NumberUtil;
import com.chd.base.util.UUIDLong;
import com.chd.hrp.acc.dao.vouch.SuperPrintMapper;
import com.chd.hrp.acc.service.vouch.SuperPrintService;
import com.chd.hrp.sys.dao.base.SysTableStyleMapper;
import com.chd.hrp.sys.service.MyPageOfficeService;
import com.chd.hrp.sys.service.base.SysTableStyleService;


@Service("myPageOfficeService")
public class MyPageOfficeServiceImpl implements MyPageOfficeService{

	private static Logger logger = Logger.getLogger(MyPageOfficeServiceImpl.class);
	@Resource(name = "superPrintService")
	private final SuperPrintService superPrintService = null;

	@Resource(name = "superPrintMapper")
	private final SuperPrintMapper superPrintMapper = null;
	
	private final static Pattern patStrNum = Pattern.compile("[A-Z]+[0-9\\.]+");
	private final static Pattern patNum = Pattern.compile("[^0-9]");
	private final DecimalFormat myformat0 = new DecimalFormat("##,##0");
	private final DecimalFormat myformat1 = new DecimalFormat("##,##0.0");
	private final DecimalFormat myformat2 = new DecimalFormat("##,##0.00");
	private final DecimalFormat myformat3 = new DecimalFormat("##,##0.000");
	private final DecimalFormat myformat4 = new DecimalFormat("##,##0.0000");
	
	
	@Resource(name = "sysTableStyleMapper")
	private final SysTableStyleMapper sysTableStyleMapper = null;
	
	@Resource(name = "sysTableStyleService")
	private final SysTableStyleService sysTableStyleService = null;
	
	 /**
	 * 将字母转换成数字  
     * Excel column index begin 1
     * @param colStr
     * @param length
     * @return
     */
    private int excelColStrToNum(String colStr, int length) {
        int num = 0;
        int result = 0;
        for(int i = 0; i < length; i++) {
            char ch = colStr.charAt(length - i - 1);
            num = (int)(ch - 'A' + 1) ;
            num *= Math.pow(26, i);
            result += num;
        }
        return result;
    }

    /**
     * 将数字转换成字母
     * Excel column index begin 1
     * @param columnIndex
     * @return
     */
    private String excelNumToColStr(int columnIndex) {
        if (columnIndex <= 0) {
            return null;
        }
        String columnStr = "";
        columnIndex--;
        do {
            if (columnStr.length() > 0) {
                columnIndex--;
            }
            columnStr = ((char) (columnIndex % 26 + (int) 'A')) + columnStr;
            columnIndex = (int) ((columnIndex - columnIndex % 26) / 26);
        } while (columnIndex > 0);
        return columnStr;
    }
    

	//保存单据打印参数
	@Override
	public String saveFormPrintPara(Map<String, Object> mapVo)throws DataAccessException {
		// TODO Auto-generated method stub
		
		try {
			
			//保存打印参数
			if(mapVo.get("para_value")!=null){
				List<Map<String, Object>> liMap=new ArrayList<Map<String, Object>>();
				Map<String, Object> mP=null;
				String[] p=mapVo.get("para_value").toString().split("#");
				if(p.length>0 && !p.toString().equals("")){
					for(String s:p){
						if(s.split("@").length<2){
							break;
						}
						mP=new HashMap<String, Object>();
						mP.put("mod_code",mapVo.get("mod_code"));
						mP.put("template_code",mapVo.get("template_code"));
						mP.put("group_id",mapVo.get("group_id"));
						mP.put("hos_id",mapVo.get("hos_id"));
						mP.put("copy_code",mapVo.get("copy_code"));
						mP.put("use_id",mapVo.get("use_id"));
						mP.put("para_code", s.split("@")[0]);
						mP.put("para_value", s.split("@")[1].replace(" ", ""));
						liMap.add(mP);
					}
					if(liMap!=null && liMap.size()>0){
						superPrintMapper.updateSuperPrintPara(liMap);
					}
				}
			}
			
			return "{\"state\":\"true\"}";
		}catch (Exception e) {
	
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
	
		}
	}
    
    
    //PageOffice单据单张、批量打印
	@Override
	public Map<String,Object> printFormBatch(Map<String, Object> map)throws DataAccessException {
		// TODO Auto-generated method stub
		Map<String,Object> resMap=new HashMap<String,Object>();
		String tempFileName=null;
		StringBuffer printBusiNo=new StringBuffer();
		
		if(map.get("class_name")==null || map.get("class_name").equals("")){
			throw new SysException("没有参数[class_name]，例如：com.chd.hrp.acc.serviceImpl.vouch.SuperVouchServiceImpl！");
		}
		
		if(map.get("method_name")==null || map.get("method_name").equals("")){
			throw new SysException("没有参数[method_name]，例如：querySuperVouchPrintBatchPage！");
		}
		
		if(map.get("use_id")==null || map.get("use_id").equals("")){
			map.put("use_id", 0);//统一打印
		}
		
		String fromFilePath=map.get("fromFilePath").toString();
        String toFilePath=map.get("toFilePath").toString();
        String templateCode=map.get("template_code").toString();
        String className=map.get("class_name").toString();
        String methodName=map.get("method_name").toString();
		
        
		/******************************查询打印模板参数****************************************************/
		Map<String,String> paraMap=new HashMap<String,String>();
		try{
			paraMap=superPrintService.querySuperPrintParaByFlag(map);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		
		if(null==paraMap || paraMap.size()==0){
			logger.error("没有维护打印参数！");
			throw new SysException("没有维护打印参数！");
		}
		
		/*业务功能打印参数，页面没有传参默认赋值，需要传到业务方法*/
		if(map.get("p019")==null && paraMap.get("019")!=null){
			//是否打印辅助核算（凭证打印）
			map.put("p019", paraMap.get("019"));
		}
		if(map.get("p020")==null && paraMap.get("020")!=null){
			//是否分栏打印（凭证打印）
			map.put("p020", paraMap.get("020"));
		}
		
        
        List<Map<String,Object>> resMainList=new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> resDetailList=new ArrayList<Map<String,Object>>();
        Map<String,Object> resMainMap=new HashMap<String,Object>();
        Map<String,Object> refMap=new HashMap<String,Object>();
        
        /******************************java反射动态执行方法*************************************************/
		try {
			 Class<?> clz = Class.forName(className);
			 Object obj;
			 if(map.get("bean_name")!=null && !map.get("bean_name").equals("")){
				 WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
				 obj = context.getBean(map.get("bean_name").toString());
			 }else{
				 obj = clz.newInstance();
			 }
		     Method method = clz.getMethod(methodName, Map.class);
		     Object resObj=method.invoke(obj, map);
		     if(resObj instanceof Map){
		    	 refMap=(Map<String, Object>) resObj;
		     }
		     
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new SysException("创建类["+className+"]失败！");
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new SysException("访问类["+className+"]失败！");
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new SysException("执行方法["+methodName+"]失败！");
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new SysException("实例化类["+className+"]失败！");
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new SysException("没有找到["+methodName+"]方法！");
		}
       
		if(refMap==null || refMap.size()==0){
			logger.error("没有查询到数据！");
			throw new SysException("没有查询到数据！");
		}
		
		//主从表批量打印
		boolean isBatch=false;
		if(refMap.get("main")!=null && refMap.get("main") instanceof Map){
			//单张打印
			resMainMap=(Map<String,Object>)refMap.get("main");
			resMainList.add(resMainMap);
		}else if(refMap.get("main")!=null && refMap.get("main") instanceof List){
			//批量打印
			isBatch=true;
			resMainList=(List<Map<String,Object>>)refMap.get("main");
		}
		
		if(refMap.get("detail")!=null && refMap.get("detail") instanceof List){
			resDetailList=(List<Map<String,Object>>)refMap.get("detail");
		}
		
		if((resMainList==null || resMainList.size()==0) && (resDetailList==null || resDetailList.size()==0)){
			throw new SysException("没有数据！");
		}
		
		//明细开始行
		String beginStr=paraMap.get("004");
				
		//明细结束行
		String endStr=paraMap.get("005");
		
		//打印次数
		String para011=paraMap.get("011");
		
		//模板每页打印次数,插入分页符
		int para014=1;
		if(paraMap.get("014")!=null && !paraMap.get("014").equals("")){
			para014=Integer.parseInt(paraMap.get("014"));
			
		}
		
		//取打印次数业务表字段
		String para011Key=paraMap.get("999999").toUpperCase();
		
		//是否套打
		if(map.get("isSetPrint")!=null){
			if(map.get("isSetPrint").toString().equals("true")){
				paraMap.put("003", "是");
			}else if(map.get("isSetPrint").toString().equals("false")){
				paraMap.put("003", "否");
			}
		}
			
		
		//明细数量
		int pageSize=0;
		int pageCount=0;//总页数
		if(beginStr!=null && !beginStr.equals("") && endStr!=null && !endStr.equals("") && resDetailList!=null && resDetailList.size()>0){
			pageSize=Integer.parseInt(endStr)-Integer.parseInt(beginStr)+1;//明细数据页大小
		}
		
		//计算单据页数
		if(resMainList!=null && resMainList.size()>0){
			//主表数据填充
			for (Map<String, Object> mainMap : resMainList) {
				if(isBatch && mainMap.get("ID")==null){
					logger.error("批量打印，主表没有返回ID列");
					throw new SysException("批量打印，主表没有返回ID列！");
				}
				
				int page001=1;//单据总页数
				pageCount++;	
				
				if(pageSize>0){
					int detailIndex=0;
					int formIndex=0;
					String formId=null;//单据ID
					String preDeFormId=null;//明细单据ID
					
					if(map.get("template_code").toString().equals("01001") && paraMap.get("020")!=null && paraMap.get("020").toString().equals("否")){
						//凭证模板打印，不按分栏打印，走批量打印，解决单据页码问题20190320
						formId=mainMap.get("ID").toString().split("-")[0];//单据ID格式：999897131274816-01/02
					}
					
					//拼装明细表信息
					for (Map<String, Object> detailMap : resDetailList) {
						
						if(isBatch){
							//批量打印
							if(detailMap.get("ID")==null){
								logger.error("批量打印，明细表没有返回ID列");
								throw new SysException("批量打印，明细表没有返回ID列！");
							}
							
							//凭证模板打印，不按分栏打印，走批量打印，解决单据页码问题20190320
							if(formId!=null && formId.equalsIgnoreCase(detailMap.get("ID").toString().split("-")[0])){
								//判断主表ID与明细表ID一致
								if(formIndex>pageSize-1 || (preDeFormId!=null && !preDeFormId.equals(detailMap.get("ID").toString()))){
									//超过明细表格，模板页数+1 || 另外一个科目类型
									formIndex=0;
									page001++;
								}
								formIndex++;
								preDeFormId=detailMap.get("ID").toString();
							}
							
							//判断主表ID与明细表ID一致
							if(mainMap.get("ID").toString().equalsIgnoreCase(detailMap.get("ID").toString())){
								if(detailIndex>pageSize-1){
									//超过明细表格，模板页数+1
									detailIndex=0;
									pageCount++;
									if(formId==null)page001++;
									
								}
								detailIndex++;
							}
							
						}else{
							//单张打印
							if(detailIndex>pageSize-1){
								//超过明细表格，模板页数+1
								detailIndex=0;
								pageCount++;
								page001++;
							}
							detailIndex++;
						}
						
					}
				}
				mainMap.put("001", page001);
				//mainListNew.add(mainMap);
			}
		}else{
			//没有主表信息
			Map<String, Object> mainMap=new HashMap<String, Object>();
			int page001=1;//单据总页数
			if(pageSize>0){
				pageCount++;
				int detailIndex=0;
				//拼装明细表信息
				for (Map<String, Object> detailMap : resDetailList) {
					if(detailIndex>pageSize-1){
						//超过明细表格，模板页数+1
						detailIndex=0;
						pageCount++;
						page001++;
					}
					detailIndex++;
				}
			}
			mainMap.put("001", page001);
			resMainList.add(mainMap);
		}
		
		/***************************************处理workbook***********************************************************************/
		try {
	        
	        //获得Workbook工作薄对象  
			String uuid= UUIDLong.absStringUUID();
			tempFileName=templateCode+"_"+uuid+".xls";
			toFilePath=toFilePath+"\\"+tempFileName;
			//生成excel
			Workbook workBook=ExcelReader.getWorkBookForm(fromFilePath,toFilePath,pageCount,paraMap); 
	        
			
			if(workBook==null){
				logger.error("生成打印模板异常！");
				throw new SysException("生成打印模板异常！");
			}
			
			Sheet sheet=workBook.getSheetAt(0);
			//getWorkBookPage方法返回模板行数
			int tempRowCount=Integer.parseInt(paraMap.get("tempRowCount"));
			
			//模板页数
			int page=0;
			/***************************************填充数据***********************************************************************/
			if(resMainList!=null && resMainList.size()>0){
				
				//查询打印次数
				List<Map<String, Object>> printCountList=new ArrayList<Map<String, Object>>();
				if(para011!=null && !para011.equals("")){
					if(resMainList.size()<1000){
						for(Map<String, Object> mainMap : resMainList){ 
							if(mainMap.get(para011Key)!=null && !mainMap.get(para011Key).equals("")){
								printBusiNo.append("'"+mainMap.get(para011Key)+"',");
							}
						}
					}else{
						//解决ORA-01795问题  
						int j=0;
						for(Map<String, Object> mainMap : resMainList){ 
							if(mainMap.get(para011Key)!=null && !mainMap.get(para011Key).equals("")){
								j++;
								if((j%999)==0 && j>1){ 
									printBusiNo.append("'"+mainMap.get(para011Key)+"') or BUSINESS_NO in (");
								}else{  
									printBusiNo.append("'"+mainMap.get(para011Key)+"',");
								} 
								
							}
						}
					}
					
					if(printBusiNo!=null && !printBusiNo.toString().equals("")){
						printBusiNo.setCharAt(printBusiNo.length()-1, ' ');
						map.put("print_business_no", printBusiNo.toString());
						printCountList=superPrintMapper.querySuperPrintCountBatch(map);
					}
					
				}
				
				int page002=1;//当前单据页码
				String preFormId=null;//上张单据ID
				//主表数据
				boolean isBreak=false;
				for (Map<String, Object> mainMap : resMainList) {
					page++;
					
					if(preFormId!=null && preFormId.equalsIgnoreCase(mainMap.get("ID").toString().split("-")[0])){
						page002++;//当前单据页码
					}else{
						page002=1;//当前单据页码
					}
					
					if(map.get("template_code").toString().equals("01001") && paraMap.get("020")!=null && paraMap.get("020").toString().equals("否")){
						//凭证模板打印，不按分栏打印，走批量打印，解决单据页码问题20190320
						preFormId=mainMap.get("ID").toString().split("-")[0];//单据ID格式：999897131274816-01/02
					}
					
					//主表数据填充
					mainMap.put("002", page002);
					
					//打印次数
					if(para011!=null && !para011.equals("")){
						if(mainMap.get(para011Key)!=null && !mainMap.get(para011Key).equals("")){
							int printCount=getPrintCount(mainMap.get(para011Key).toString(),printCountList);
							mainMap.put("011", printCount);
						}
					}
					
					isBreak=false;
					if(page==pageCount || page%para014==0){
						//插入分页符
						isBreak=true;
					}
					setPageOfficeMain(sheet,mainMap,paraMap,page,tempRowCount,isBreak);
					
					int detailIndex=0;
					if(pageSize>0){
						
						//拼装明细表信息
						for (Map<String, Object> detailMap : resDetailList) {
							
							//主从表批量打印
							if(isBatch){
								//主表ID与明细表ID一致
								if(mainMap.get("ID").toString().equalsIgnoreCase(detailMap.get("ID").toString())){
									if(detailIndex>pageSize-1){
										//超过明细表格，模板页数+1
										detailIndex=0;
										page++;
										page002++;
										//主表数据填充
										mainMap.put("002", page002);
										isBreak=false;
										if(page==pageCount || page%para014==0){
											//插入分页符
											isBreak=true;
										}
										setPageOfficeMain(sheet,mainMap,paraMap,page,tempRowCount,isBreak);
									}
									//明细表数据填充
									int toRow=Integer.parseInt(beginStr)+detailIndex-1;//下标从0开始
									setPageOfficeDetail(sheet,detailMap,paraMap,page,tempRowCount,toRow);
									detailIndex++;
								}
							}else{
								//单张打印
								if(detailIndex>pageSize-1){
									//超过明细表格，模板页数+1
									detailIndex=0;
									page++;
									page002++;
									//主表数据填充
									mainMap.put("002", page002);
									isBreak=false;
									if(page==pageCount || page%para014==0){
										//插入分页符
										isBreak=true;
									}
									setPageOfficeMain(sheet,mainMap,paraMap,page,tempRowCount,isBreak);
								}
								//明细表数据填充
								int toRow=Integer.parseInt(beginStr)+detailIndex-1;//下标从0开始
								setPageOfficeDetail(sheet,detailMap,paraMap,page,tempRowCount,toRow);
								detailIndex++;
							}
							
							
						}
					}
					
					//处理每个单据的最后一页：空行
					if(Integer.parseInt(mainMap.get("001").toString())==Integer.parseInt(mainMap.get("002").toString())){
						setRowVisible(sheet,paraMap,page,tempRowCount,detailIndex);
					}
				}
			}
			
	        //创建temp文件夹
            File toFile =new File(toFilePath.substring(0,toFilePath.lastIndexOf("\\")));
    		if(!toFile.exists()){
    			toFile.mkdirs();
    		}
    		
    		//传目录，删除昨天的临时文件
    		FileUtil.deleteTempPath(toFile);
    		
            FileOutputStream fileOut = new FileOutputStream(toFilePath);
            workBook.write(fileOut);
	        fileOut.close();
	        
	        
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(),e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(),e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(),e);
		}
		
		if(para011==null || printBusiNo==null || para011.equals("")){
			resMap.put("business_no", "");
		}else{
			String busiNo=printBusiNo.toString();
			busiNo=busiNo.replace(") or BUSINESS_NO in (", ",");
			busiNo=busiNo.replace("'", "");
			busiNo=busiNo.replace(" ", "");
			resMap.put("business_no", busiNo);
		}
		resMap.put("temp_file_name", tempFileName);
		resMap.put("para011", para011==null?"":para011);
		
		return resMap;
	}  
	
	//填充PageOffice主表数据，单据模板
	private void setPageOfficeMain(Sheet sheet,Map<String, Object> mainMap,Map<String,String> paraMap,int page,int tempRowCount,boolean isBreak){
		
		for(Map.Entry<String, Object> entry:mainMap.entrySet()){
			
			if(entry.getKey()==null || entry.getKey().equals("")){
				continue;
			}
			
			String paraVal=paraMap.get(entry.getKey().toLowerCase());//取参数值
			
			if(paraVal!=null && !paraVal.equals("")){
			
				int flag=0;
				if(paraVal.split("@").length==2){
					flag=Integer.parseInt(paraVal.split("@")[1]);//打印参数标志：flag
					paraVal=paraVal.split("@")[0];//参数值：坐标
				}
				
				if(paraVal.split(",").length<2){
					logger.debug(entry.getKey()+"【"+paraVal+"】不是主表参数！");
					continue;
				}
				
				//主表参数
				int rowIndex=1;
				try{
					rowIndex=Integer.parseInt(paraVal.split(",")[0])-1;//取横坐标，下标从0开始
					if(rowIndex<0){
						throw new SysException(paraVal+"，不是正整数！");
					}
				}catch(Exception e){
					throw new SysException(paraVal+"，不是正整数！");
				}
				
				
				if(page>1){
					//如果不是第一页，需要重新计算行号
					rowIndex=rowIndex+(page-1)*tempRowCount;
				}
				
				Row row;
				if(sheet.getRow(rowIndex)!=null){
					row=sheet.getRow(rowIndex);
				}else{
					row=sheet.createRow(rowIndex);
				}
				
				//明细参数
				int cellIndex=0;
				try{
					cellIndex=Integer.parseInt(paraVal.split(",")[1])-1;//取纵坐标，下标从0开始
					if(cellIndex<0){
						throw new SysException(paraVal+"，不是正整数！");
					}
				}catch(Exception e){
					throw new SysException(paraVal+"，不是正整数！");
				}
				
				Cell cell;
				if(row.getCell(cellIndex)==null){
					cell=row.createCell(cellIndex);
				}else{
					cell= row.getCell(cellIndex);
				}
				
				
				Object mainVal=entry.getValue()==null?"":entry.getValue();
				
				//金额转大写
				if(flag==5 && !mainVal.toString().equals("")){
					mainVal=NumberToCN.number2CNMontrayUnit(BigDecimal.valueOf(Double.parseDouble(mainVal.toString())));
				}
				//日期转大写
				if(flag==11 && !mainVal.toString().equals("")){
					mainVal=NumberToCN.convertNum(Integer.parseInt(mainVal.toString()));
				}
				//单元格内容格式化
				setCellFormat(cell,mainVal);
				
			}
		}
		
		//插入分页符
		if(isBreak){
			sheet.setRowBreak((page-1)*tempRowCount+tempRowCount-1);
		}
		
	} 
	
	//填充PageOffice明细表数据，单据模板
	private void setPageOfficeDetail(Sheet sheet,Map<String, Object> detailMap,Map<String,String> paraMap,int page,int tempRowCount,int toRow){
		
		int rowIndex=toRow;
		if(page>1){
			//如果不是第一页，需要重新计算行号
			rowIndex=rowIndex+(page-1)*tempRowCount;
		}
		
		Row row;
		if(sheet.getRow(rowIndex)!=null){
			row=sheet.getRow(rowIndex);
		}else{
			row=sheet.createRow(rowIndex);
		}
		
		for(Map.Entry<String, Object> entry:detailMap.entrySet()){
			
			if(entry.getKey()==null || entry.getKey().equals("")){
				continue;
			}
			
			String paraVal=paraMap.get(entry.getKey().toLowerCase());//取参数值
			
			if(paraVal!=null && !paraVal.equals("")){
				
				int flag=0;
				if(paraVal.split("@").length==2){
					flag=Integer.parseInt(paraVal.split("@")[1]);//打印参数标志：flag
					paraVal=paraVal.split("@")[0];//参数值：坐标
				}
				
				//明细参数
				int cellIndex=0;
				try{
					cellIndex=Integer.parseInt(paraVal)-1;//取纵坐标，下标从0开始
					if(cellIndex<0){
						throw new SysException(paraVal+"，不是正整数！");
					}
				}catch(Exception e){
					throw new SysException(paraVal+"，不是正整数！");
				}
				
				Cell cell;
				if(row.getCell(cellIndex)==null){
					cell=row.createCell(cellIndex);
				}else{
					cell= row.getCell(cellIndex);
				}
				
				Object mainVal=entry.getValue()==null?"":entry.getValue();
				
				//金额转大写
				if(flag==5 && !mainVal.toString().equals("")){
					mainVal=NumberToCN.number2CNMontrayUnit(BigDecimal.valueOf(Double.parseDouble(mainVal.toString())));
				}
				
				//日期转大写
				if(flag==11 && !mainVal.toString().equals("")){
					mainVal=NumberToCN.convertNum(Integer.parseInt(mainVal.toString()));
				}
				
				//单元格内容格式化
				setCellFormat(cell,mainVal);
				
			}
		}
	}
	
	//处理每个单据的最后一页
	private void setRowVisible(Sheet sheet,Map<String,String> paraMap,int page,int tempRowCount,int detailIndex){
		
		//处理空行 
		String para013=paraMap.get("013");
//		if(para013.equals("是") && detailIndex>0){
		if("是".equals(para013) && detailIndex>0){
			//开始行
			int para004=Integer.parseInt(paraMap.get("004"));
			//结束行
			int para005=Integer.parseInt(paraMap.get("005"));
		
			int rowB=para004+(tempRowCount*(page-1))+detailIndex;
			int rowE=para005+(tempRowCount*(page-1));
			//int formRow=tempRowCount*page;
			for(int i=rowB;i<=rowE;i++){
				Row row;
				if(sheet.getRow(i-1)==null){
					row=sheet.createRow(i-1);
				}else{
					row=sheet.getRow(i-1);
				}
				
				//清除空行解决办法：隐藏空行，在单据最后一行插入空行，为了不影响打印分页
//				sheet.shiftRows(formRow, sheet.getLastRowNum(), 1); //10-最后一行，向下移动一行
//				Row emptyRow=sheet.createRow(formRow);//添加新行
//				emptyRow.setHeight(row.getHeight());//设置新行的高度
				
				row.setZeroHeight(true);//隐藏行高
				
			}
			
		}
		
	}
	
	//单元格内容格式化
	private void setCellFormat(Cell cell,Object mainVal){
		
		if(cell==null){
			return;
		}
		
        CellStyle style = cell.getCellStyle();  
    //    short format = style.getDataFormat();
        String formatString=style.getDataFormatString();
    //	System.out.println(mainVal+"--"+format+"："+formatString);
    	if(formatString!=null && !formatString.equals("")){
    		
    		 if(formatString.replace("\\", "").replace("/", "").replace("-", "").trim().equalsIgnoreCase("yyyymmdd")){
    				//日期
    				String formatter="yyyy-MM-dd";		
    				try{
    					cell.setCellValue(DateUtil.dateFormat1(mainVal, formatter));
    				}catch(ParseException e){
    					logger.error(mainVal+"，日期格式化错误："+formatter);
    					cell.setCellValue(mainVal.toString());
    				}
    				
    			}else if(formatString.replace("\\", "").replace("/", "").replace("-", "").trim().equalsIgnoreCase("yyyymmdd hh:mm:ss")){
    				//日期时分秒
    				String formatter="yyyy-MM-dd HH:mm:ss";
    				try{
    					cell.setCellValue(DateUtil.dateFormat1(mainVal, formatter));
    				}catch(ParseException e){
    					logger.error(mainVal+"，日期格式化错误："+formatter);
    					cell.setCellValue(mainVal.toString());
    				}
    				
    			}else if(formatString.indexOf("0_")!=-1){
    				
    				try{
    					cell.setCellValue(Double.parseDouble(mainVal.toString()));
    				}catch(Exception e){
    					logger.error(mainVal+"，数字格式化错误：");
    					cell.setCellValue(mainVal.toString());
    				}
    				
    			}else{
    				cell.setCellValue(mainVal.toString());
    			}
    	}else{
    		cell.setCellValue(mainVal.toString());
    	}
       
	}
	
	//根据key获取打印次数
	private int getPrintCount(String print_business_no,List<Map<String, Object>> printCountList){
		
		int printCount=1;
		if(printCountList!=null && printCountList.size()>0){
			for(Map<String, Object> map:printCountList){
				if(map.get("business_no")!=null && map.get("business_no").toString().equalsIgnoreCase(print_business_no)){
					printCount=Integer.parseInt(map.get("print_count").toString())+1;
					break;
				}
			}
		}
		return printCount;
	}
	
	
	//PageOffice保存打印次数
	public void savePrintCountPage(Map<String,Object> map){
		try{
			superPrintMapper.savePrintCountPage(map);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	
	
	/**
	 * 根据模板的格式调整excel，PageOffice使用
	 */
	public void createReportFile(String templateFilePath,String toFilePath)throws DataAccessException{  
		
		try {
			ExcelReader.createReportFile(templateFilePath,toFilePath);
			
			
//			InputStream  is = new FileInputStream(templateFilePath); 
//			Workbook  wbTemp = new XSSFWorkbook(is);
//			Sheet sheetTemp=wbTemp.getSheetAt(0);
			
			
			//设置行高
//			Row rowTemp = null;
//			int rowIdex=0;
//			for(int i=0;i<sheetTemp.getLastRowNum();i++){
//				rowIdex=i+1;
//				rowTemp=sheetTemp.getRow(i);
//				sheetWrite.openTable("A"+rowIdex+":A"+rowIdex).setRowHeight(rowTemp.getHeight());
//			}
			
			//设置列宽
//			if(rowTemp!=null){
//				for(int i=0;i<rowTemp.getLastCellNum();i++){
//					String cellStr=excelColIndexToStr(i+1);//转excel的字母
//					sheetWrite.openTable(cellStr+rowIdex+":"+cellStr+rowIdex).setColumnWidth(sheetTemp.getColumnWidth(i));
//				}
//			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
		
    }  
	
	/**
	 * 处理单元格公式，PageOffice使用
	 */
	public void setReportFileFormula(String toFilePath)throws DataAccessException{ 
		try {
			
			ExcelReader.setReportFileFormula(toFilePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
	}

	//保存报表模板
	@Override
	public void saveReportTemplateFile(String fromFilePath,String toFilePath) throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			ExcelReader.saveReportTemplateFile(fromFilePath,toFilePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
	}
	
	
    //PageOffice财务表格模板打印
	@Override
	public String printTableAcc(Map<String, Object> map)throws DataAccessException {
		// TODO Auto-generated method stub
		String tempFileName=null;
		
		if(map.get("class_name")==null || map.get("class_name").equals("")){
			throw new SysException("没有参数[class_name]，例如：com.chd.hrp.acc.serviceImpl.vouch.SuperVouchServiceImpl！");
		}
		
		if(map.get("method_name")==null || map.get("method_name").equals("")){
			throw new SysException("没有参数[method_name]，例如：querySuperVouchPrintBatchPage！");
		}
			
		String fromFilePath=map.get("fromFilePath").toString();
        String toFilePath=map.get("toFilePath").toString();
        String templateCode=map.get("template_code").toString();
        String className=map.get("class_name").toString();
        String methodName=map.get("method_name").toString();
		
        
		/******************************查询打印模板参数****************************************************/
		Map<String,String> paraMap=new HashMap<String,String>();
		try{
			paraMap=superPrintService.querySuperPrintParaByFlag(map);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		
		if(null==paraMap || paraMap.size()==0){
			logger.error("没有维护打印参数！");
			throw new SysException("没有维护打印参数！");
		}
        
		/*业务功能打印参数，需要传到业务方法*/
		if(map.get("p019")==null && paraMap.get("019")!=null){
			//打印辅助核算（凭证打印）
			map.put("p019", paraMap.get("019"));
		}
		if(map.get("p020")==null && paraMap.get("020")!=null){
			//分栏打印（凭证打印）
			map.put("p020", paraMap.get("020"));
		}
		
		
        List<Map<String,Object>> resDetailList=new ArrayList<Map<String,Object>>();
        Map<String,Object> resMainMap=new HashMap<String,Object>();
        Map<String,Object> refMap=new HashMap<String,Object>();
        
        /******************************java反射动态执行方法*************************************************/
        
		try {
			 Class<?> clz = Class.forName(className);
			 Object obj;
			 if(map.get("bean_name")!=null && !map.get("bean_name").equals("")){
				 WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
				 obj = context.getBean(map.get("bean_name").toString());
			 }else{
				 obj = clz.newInstance();
			 }
			     
             Method method = clz.getMethod(methodName, Map.class);
		     Object resObj=method.invoke(obj, map);
             
		     if(resObj instanceof Map){
		    	 refMap=(Map<String, Object>) resObj;
		     }
		     
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new SysException("创建类["+className+"]失败！");
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new SysException("访问类["+className+"]失败！");
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new SysException("执行方法["+methodName+"]失败！");
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new SysException("实例化类["+className+"]失败！");
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new SysException("没有找到["+methodName+"]方法！");
		}
       
		if(refMap==null || refMap.size()==0){
			logger.error("没有查询到数据！");
			throw new SysException("没有查询到数据！");
		}
		
		//主从表打印
		if(map.get("main_query")!=null && map.get("main_query").toString().equals("true")){
			//主表数据从数据库里面取，如凭证表格打印
			if(refMap.get("main")!=null && refMap.get("main") instanceof Map){
				resMainMap=(Map<String,Object>)refMap.get("main");
			}
		}else{
			//主表数据从查询页面取
			resMainMap=map;
		}
		
		if(refMap.get("detail")!=null && refMap.get("detail") instanceof List){
			resDetailList=(List<Map<String,Object>>)refMap.get("detail");
		}
		
		if((resMainMap==null || resMainMap.size()==0) && (resDetailList==null || resDetailList.size()==0)){
			throw new SysException("没有数据！");
		}
		
		//明细开始行
		int beginRow=0;
		if(paraMap.get("004")!=null && !paraMap.get("004").equals("")){
			beginRow=Integer.parseInt(paraMap.get("004"))-1;//下标从0开始
		}
		
		
		//每页条数
		int para015=0;
		if(paraMap.get("015")!=null && !paraMap.get("015").equals("")){
			para015=Integer.parseInt(paraMap.get("015"));
		}
		
		
		//承前页/过次（列号）
		String para016=paraMap.get("016");
		Map<String, String> cqyParaMap=null;
		if(para015>0 && para016!=null && !para016.equals("")){
			para015=para015-1;//不包含过次页，分页
			cqyParaMap=new HashMap<String,String>();
			if(para016.split(",").length>1){
				
				//列号逗号分隔
				String p016Array[]=para016.split(",");
				for(int i=0;i<p016Array.length;i++){
					
					for(Map.Entry<String, String> entry:paraMap.entrySet()){
						String paraVal=entry.getValue();
						if(paraVal!=null && paraVal.split("@").length==2 && paraVal.split("@")[1].equals("2")){
							//只取明细参数
							if(paraVal.split("@")[0].equals(p016Array[i])){
								//取列坐标相等的字段
								cqyParaMap.put(entry.getKey(), p016Array[i]);
								break;
							}
						}
					}
				}
			}else{
				
				//只填一列
				for(Map.Entry<String, String> entry:paraMap.entrySet()){
					String paraVal=entry.getValue();
					if(paraVal!=null && paraVal.split("@").length==2 && paraVal.split("@")[1].equals("2")){
						//只取明细参数
						
						if(Integer.parseInt(paraVal.split("@")[0])>=Integer.parseInt(para016)){
							//取>=列坐标的字段
							cqyParaMap.put(entry.getKey(), paraVal.split("@")[0]);
						}
						
					}
				}
			}
			
		}
		
		//承前页/过次页（合计列）
		String para017=paraMap.get("017");
		
		//按明细科目拆分表格
		String para018=paraMap.get("018");
		
		/***************************************处理workbook***********************************************************************/
		try {
	        
	        //获得Workbook工作薄对象  
			String uuid= UUIDLong.absStringUUID();
			tempFileName=templateCode+"_"+uuid+".xls";
			toFilePath=toFilePath+"\\"+tempFileName;
			//生成excel
			Workbook workBook=ExcelReader.getWorkBookTable(fromFilePath,toFilePath); 
	        
			
			if(workBook==null){
				logger.error("生成打印模板异常！");
				throw new SysException("生成打印模板异常！");
			}
			
			Sheet sheet=workBook.getSheetAt(0);
			//sheet.protectSheet("chd20140301");
			
					
			/***************************************填充数据***********************************************************************/
			if(resMainMap!=null && resMainMap.size()>0){
				setPageOfficeMain1(sheet,resMainMap,paraMap);
			}
			
			if(beginRow>0 && resDetailList!=null && resDetailList.size()>0){
				
				//处理表尾
				boolean isFoot=false;
				int lastRowNo = sheet.getLastRowNum();//下标从0开始
				List<Short> foot=new ArrayList<Short>();
				//beginRow+1=表尾开始行
				if(lastRowNo>=beginRow+1){
					//因为要插入行，所以先暂存表尾行的高度
					isFoot=true;
					for(int i=beginRow+1;i<=lastRowNo;i++){
						Row footRow=sheet.getRow(i);
						foot.add(footRow.getHeight());
					}
				}
				
				
				//拼装明细表信息
				int rowIndex=0;
				//明细下标
				int detailIndex=0;
				//按科目明细下标
				int subjIndex=0;
				//合并区域数量
		        int sheetMergeCount = sheet.getNumMergedRegions();
		        
		        Map<String, Object> cqyDataMap=null;
		        
		        //明细表数据
				for (Map<String, Object> detailMap : resDetailList) {
					
					//承前页
					if(cqyDataMap!=null && cqyDataMap.size()>0){
						
						rowIndex++;
						subjIndex++;
						setPageOfficeCqyData(workBook,sheet,cqyDataMap,cqyParaMap,para015,para016,para017,true,sheetMergeCount,beginRow,rowIndex,isFoot);
						cqyDataMap=null;
					}
					
					//填充明细数据
					rowIndex++;
					detailIndex++;
					subjIndex++;
					setPageOfficeDetail1(workBook,sheet,detailMap,paraMap,sheetMergeCount,beginRow,rowIndex,isFoot);
					
					
					boolean isPara018=false;
					if(rowIndex>1 && para018!=null && para018.equals("是")){
						//按明细科目拆分表格
						
						//插入分页符
						if(detailMap.get("SUMMARY")!=null && (detailMap.get("SUMMARY").equals("期初余额") || detailMap.get("SUMMARY").equals("上年结转"))){
							sheet.setRowBreak(beginRow+rowIndex-1);
							cqyDataMap=null;
							isPara018=true;
							subjIndex=1;
						}
						
					}
					
					if(!isPara018 && para015>0 && subjIndex==para015){
						//每页条数>0
						
						if(detailIndex==resDetailList.size()){
							//最后一行明细不需要显示过次页
							continue;
						}
						
						if(para018!=null && para018.equals("是") && detailMap.get("SUMMARY")!=null && detailMap.get("SUMMARY").equals("本年累计")){
							//按明细科目拆分表格
							continue;
						}
						
						//过次页
						if(para016!=null && !para016.equals("")){
							cqyDataMap=new HashMap<String,Object>();
							cqyDataMap=detailMap;
							
							rowIndex++;
							subjIndex=0;
							setPageOfficeCqyData(workBook,sheet,cqyDataMap,cqyParaMap,para015,para016,para017,false,sheetMergeCount,beginRow,rowIndex,isFoot);
						}
						
						//插入分页符
						sheet.setRowBreak(beginRow+rowIndex);
						
					}
					
				}
				
				//隐藏明细第一行空行，因为插入行会留一行空行
				Row bRow = sheet.getRow(beginRow);
				bRow.setZeroHeight(true);
				
				//处理表尾
				if(foot!=null && foot.size()>0){
					int footBNew=beginRow+rowIndex+1;//表尾开始行
					for(int i=0;i<foot.size();i++){
						Row fRow = sheet.getRow(footBNew+i);
						//设置表尾的高度
						fRow.setHeight(foot.get(i));
						
						//处理sum公式
						for (Iterator cellIt = fRow.cellIterator(); cellIt.hasNext();) {
				            HSSFCell cell = (HSSFCell) cellIt.next();
				            int cellType = cell.getCellType();  
				            if (cellType == HSSFCell.CELL_TYPE_FORMULA) {  
				            	if(cell.getCellFormula()!=null && cell.getCellFormula().toLowerCase().indexOf("sum")!=-1){
				            		
				            		String fromFormula=cell.getCellFormula();
			    	            	Matcher m = patStrNum.matcher(fromFormula);
			    	            	String rowZm=null;
			    					while(m.find()){
			    						String rowCell=m.group();//字母和数字组合B13
			    						Matcher mNum=patNum.matcher(rowCell);
			    						String rowNum = mNum.replaceAll("");//提取数字
			    						rowZm=rowCell.replace(rowNum, "");//提取字母
			    						break;
			    					
			    					}
				            		if(rowZm!=null){
				            			int endIndex=beginRow+rowIndex+1;
				            			int beginIndex=beginRow+1;
				            			cell.setCellFormula("sum("+rowZm+beginIndex+":"+rowZm+endIndex+")");
				            			//cell.setCellFormula(cell.getCellFormula());
				            		}
				            	}
							}
				        }  
						
					}
					
				}
					
			}
			
	        //创建temp文件夹
            File toFile =new File(toFilePath.substring(0,toFilePath.lastIndexOf("\\")));
    		if(!toFile.exists()){
    			toFile.mkdirs();
    		}
    		
    		//传目录，删除昨天的临时文件
    		FileUtil.deleteTempPath(toFile);
    		
            FileOutputStream fileOut = new FileOutputStream(toFilePath);
            workBook.write(fileOut);
	        fileOut.close();
	        
	        
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(),e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(),e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(),e);
		}
		
		
		return tempFileName;
	}  
	
	//填充PageOffice主表数据，表格模板
	private void setPageOfficeMain1(Sheet sheet,Map<String, Object> mainMap,Map<String,String> paraMap){
		
		//主表数据
		for(Map.Entry<String, Object> entry:mainMap.entrySet()){
		
			if(entry.getKey()==null || entry.getKey().equals("")){
				continue;
			}
			
			String paraVal=paraMap.get(entry.getKey().toLowerCase());//取参数值
			
			if(paraVal!=null && !paraVal.equals("")){
			
				int flag=0;
				if(paraVal.split("@").length==2){
					flag=Integer.parseInt(paraVal.split("@")[1]);//打印参数标志：flag
					paraVal=paraVal.split("@")[0];//参数值：坐标
				}
				
				if(paraVal.split(",").length<2){
					logger.debug(entry.getKey()+"【"+paraVal+"】不是主表参数！");
					continue;
				}
				
				//主表参数
				int rowIndex=1;
				try{
					rowIndex=Integer.parseInt(paraVal.split(",")[0])-1;//取横坐标，下标从0开始
					if(rowIndex<0){
						logger.debug("主表参数："+entry.getKey()+"【"+paraVal+"】不是正整数！");
						continue;
					}
				}catch(Exception e){
					throw new SysException("主表参数："+entry.getKey()+"【"+paraVal+"】不是正整数！");
				}
				
				Row row;
				if(sheet.getRow(rowIndex)==null){
					row=sheet.createRow(rowIndex);
				}else{
					row=sheet.getRow(rowIndex);
				}
				
				
				//明细参数
				int cellIndex=0;
				try{
					cellIndex=Integer.parseInt(paraVal.split(",")[1])-1;//取纵坐标，下标从0开始
					if(cellIndex<0){
						logger.debug("明细参数："+entry.getKey()+"【"+paraVal+"】不是正整数！");
						continue;
					}
				}catch(Exception e){
					throw new SysException("明细参数："+entry.getKey()+"【"+paraVal+"】不是正整数！");
				}
				
				Cell cell;
				if(row.getCell(cellIndex)==null){
					cell=row.createCell(cellIndex);
				}else{
					cell= row.getCell(cellIndex);
				}
				
				Object mainVal=entry.getValue()==null?"":entry.getValue();
				
				//金额转大写
				if(flag==5){
					mainVal=NumberToCN.number2CNMontrayUnit(BigDecimal.valueOf(Double.parseDouble(mainVal.toString())));
				}
				
				//日期转大写
				if(flag==11 && !mainVal.toString().equals("")){
					mainVal=NumberToCN.convertNum(Integer.parseInt(mainVal.toString()));
				}
				
				//单元格内容格式化
				setCellFormat(cell,mainVal);
				
			}
			
		}
	}
	
	
	//填充PageOffice承前页过次页，表格模板
	private void setPageOfficeCqyData(Workbook workBook,Sheet sheet,Map<String, Object> cqyDataMap,Map<String, String> paraMap,
			int para015,String para016,String para017,boolean isCqy,int sheetMergeCount,int beginRow,int rowIndex,boolean isFoot){
		
		
		if(isFoot){
			//有表尾所以插入行
			sheet.shiftRows(beginRow+rowIndex, sheet.getLastRowNum(), 1);
		}
		
		int toRowIndex=beginRow+rowIndex;
		
		
        HSSFRow fromRow;
        if(sheet.getRow(beginRow)!=null){
        	fromRow=(HSSFRow)sheet.getRow(beginRow);
		}else{
			fromRow=(HSSFRow)sheet.createRow(beginRow);
		}
        
        HSSFRow toRow;
        if(sheet.getRow(toRowIndex)!=null){
        	toRow=(HSSFRow)sheet.getRow(toRowIndex);
        }else{
        	toRow=(HSSFRow)sheet.createRow(toRowIndex);
        }
        
        //处理合并单元格
        setMergedRegion(sheet,sheetMergeCount,beginRow,toRowIndex);
        
    	//设置行高
		toRow.setHeight(fromRow.getHeight());
		
		//复制样式
		ExcelReader.copyRow2003((HSSFWorkbook) workBook,fromRow,toRow,rowIndex);
        
		for(Map.Entry<String, Object> entry:cqyDataMap.entrySet()){
			
			if(entry.getKey()==null || entry.getKey().equals("")){
				continue;
			}
			
			String paraVal=paraMap.get(entry.getKey().toLowerCase());//取参数值
			
			if(paraVal!=null && !paraVal.equals("")){
				
				//明细参数
				int cellIndex=0;
				try{
					cellIndex=Integer.parseInt(paraVal)-1;//取纵坐标，下标从0开始
					if(cellIndex<0){
						logger.debug("明细参数："+entry.getKey()+"【"+paraVal+"】不是正整数！");
						continue;
					}
				}catch(Exception e){
					throw new SysException("明细参数："+entry.getKey()+"【"+paraVal+"】不是正整数！");
				}
				
				Cell cell;
				if(toRow.getCell(cellIndex)==null){
					cell=toRow.createCell(cellIndex);
				}else{
					cell= toRow.getCell(cellIndex);
				}
				
				Object mainVal="";
				
				if(paraVal.equals(para016.split(",")[0])){
					if(isCqy){
						mainVal="承  前  页";
					}else{
						mainVal="过  次  页";
					}
				}else{
					mainVal=entry.getValue()==null?"":entry.getValue();
				}
				
				//过次页合计列
				if(para017!=null && !para017.equals("")){
					String []para017Array=para017.split(",");
					for(int i=0;i<para017Array.length;i++){
						if(para017Array[i].equals(paraVal)){
						
							String colZm=excelNumToColStr(cell.getColumnIndex()+1);
							mainVal="";
							
							if(isCqy){
								//承前页,取上一单元格数值
								cell.setCellFormula(colZm+toRowIndex);
							}else{
								//过次页，计算本页合计
								//每页开始行，用于sum公式计算
								int pageBeginRow=toRowIndex-para015+1;
								cell.setCellFormula("sum("+colZm+pageBeginRow+":"+colZm+toRowIndex+")");
							}
							
							break;
						}
					}
				}
				
				//单元格内容格式化
				setCellFormat(cell,mainVal);
				
			}
		}
	}
	
	
	//填充PageOffice明细表数据，表格模板
	private void setPageOfficeDetail1(Workbook workBook,Sheet sheet,Map<String, Object> detailMap,Map<String,String> paraMap,int sheetMergeCount,int beginRow,int rowIndex,boolean isFoot){
		
		if(isFoot){
			//有表尾所以插入行
			sheet.shiftRows(beginRow+rowIndex, sheet.getLastRowNum(), 1);
		}
		
		
		int toRowIndex=beginRow+rowIndex;
		
        HSSFRow fromRow;
        if(sheet.getRow(beginRow)!=null){
        	fromRow=(HSSFRow)sheet.getRow(beginRow);
		}else{
			fromRow=(HSSFRow)sheet.createRow(beginRow);
		}
        
        HSSFRow toRow;
        if(sheet.getRow(toRowIndex)!=null){
        	toRow=(HSSFRow)sheet.getRow(toRowIndex);
        }else{
        	toRow=(HSSFRow)sheet.createRow(toRowIndex);
        }
        
        
        //处理合并单元格
        setMergedRegion(sheet,sheetMergeCount,beginRow,toRowIndex);
        
    	//设置行高
		toRow.setHeight(fromRow.getHeight());
		
		//复制样式
		ExcelReader.copyRow2003((HSSFWorkbook) workBook,fromRow,toRow,rowIndex);
        
		for(Map.Entry<String, Object> entry:detailMap.entrySet()){
			
			if(entry.getKey()==null || entry.getKey().equals("")){
				continue;
			}
			
			String paraVal=paraMap.get(entry.getKey().toLowerCase());//取参数值
			
			if(paraVal!=null && !paraVal.equals("")){
				
				int flag=0;
				if(paraVal.split("@").length==2){
					flag=Integer.parseInt(paraVal.split("@")[1]);//打印参数标志：flag
					paraVal=paraVal.split("@")[0];//参数值：坐标
				}
				
				//明细参数
				int cellIndex=0;
				try{
					cellIndex=Integer.parseInt(paraVal)-1;//取纵坐标，下标从0开始
					if(cellIndex<0){
						logger.debug("明细参数："+entry.getKey()+"【"+paraVal+"】不是正整数！");
						continue;
					}
				}catch(Exception e){
					throw new SysException("明细参数："+entry.getKey()+"【"+paraVal+"】不是正整数！");
				}
				
				Cell cell;
				if(toRow.getCell(cellIndex)==null){
					cell=toRow.createCell(cellIndex);
				}else{
					cell= toRow.getCell(cellIndex);
				}
				
				Object mainVal=entry.getValue()==null?"":entry.getValue();
				
				//金额转大写
				if(flag==5){
					mainVal=NumberToCN.number2CNMontrayUnit(BigDecimal.valueOf(Double.parseDouble(mainVal.toString())));
				}
				
				//日期转大写
				if(flag==11 && !mainVal.toString().equals("")){
					mainVal=NumberToCN.convertNum(Integer.parseInt(mainVal.toString()));
				}
				
				//单元格内容格式化
				setCellFormat(cell,mainVal);
				
			}
		}
	}
	
    /**
     * 处理合并单元格
     */
    private static void setMergedRegion(Sheet sheet,int sheetMergeCount,int beginRow,int toRow) {
    	
        for (int i = 0; i < sheetMergeCount; i++) {
        	
        	  CellRangeAddress range=sheet.getMergedRegion(i);
        	  if(range!=null){
        		  int firstColumn = range.getFirstColumn(); 
                  int lastColumn = range.getLastColumn();   
                  int firstRow = range.getFirstRow();   
                  int lastRow = range.getLastRow();   
                  if(firstRow ==beginRow && lastRow==beginRow){ 
                        sheet.addMergedRegion(new CellRangeAddress(toRow, toRow, firstColumn, lastColumn));  
                  }
        	  }
        } 
     }

    //grid列表打印
	@Override
	public String printGrid(Map<String, Object> map) throws DataAccessException {
		// TODO Auto-generated method stub
		
		if(map.get("class_name")==null || map.get("class_name").equals("")){
			throw new SysException("没有参数[class_name]，例如：com.chd.hrp.acc.serviceImpl.vouch.SuperVouchServiceImpl！");
		}
		
		if(map.get("method_name")==null || map.get("method_name").equals("")){
			throw new SysException("没有参数[method_name]，例如：querySuperVouchPrintBatchPage！");
		}
		
		if(map.get("columns")==null || map.get("columns").equals("")){
			throw new SysException("没有参数[columns]！");
		}

	    JSONObject columnsJson=null;
        try{
        	String columns=map.get("columns").toString();
        	logger.debug("打印表头："+columns);
	        columnsJson = JSONObject.parseObject(columns);
        }catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new SysException("columns参数格式不符合json规范！");
		}
        
        if(columnsJson.getString("maxcolumnindex")==null || columnsJson.getString("maxcolumnindex").equals("")){
        	throw new SysException("columns参数没有[maxcolumnindex]对象！");
        }
        
        if(columnsJson.getString("maxlevel")==null || columnsJson.getString("maxlevel").equals("")){
        	throw new SysException("columns参数没有[maxlevel]对象！");
        }
        
        if(columnsJson.getString("rows")==null || columnsJson.getString("rows").equals("")){
        	throw new SysException("columns参数没有[rows]数组对象！");
        }
        
        //表头列数
        int maxcolumnIndex=Integer.parseInt(columnsJson.getString("maxcolumnindex"));
        //表头级次
        int maxlevel=Integer.parseInt(columnsJson.getString("maxlevel"));
        //表头json数组对象
        JSONArray rowsArray = JSONObject.parseArray(columnsJson.getString("rows"));
		
        if(rowsArray==null || rowsArray.size()==0){
        	throw new SysException("columns参数[rows]数组对象为空！");
        }
        
		if(map.get("group_id") == null){
			map.put("group_id", SessionManager.getGroupId());
		}
		if(map.get("hos_id") == null){
			map.put("hos_id", SessionManager.getHosId());
		}
		if(map.get("copy_code") == null){
			map.put("copy_code", SessionManager.getCopyCode());
		}
		if(map.get("acc_year") == null){
			map.put("acc_year", SessionManager.getAcctYear());
		}
		
		String className=map.get("class_name").toString();
	    String methodName=map.get("method_name").toString();
		
		List<Map<String,Object>> resList=new ArrayList<Map<String,Object>>();
        
        /******************************java反射动态执行方法*************************************************/
        
		try {
			 Class<?> clz = Class.forName(className);
			 Object obj;
			 if(map.get("bean_name")!=null && !map.get("bean_name").equals("")){
				 WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
				 obj = context.getBean(map.get("bean_name").toString());
			 }else{
				 obj = clz.newInstance();
			 }
			     
             Method method = clz.getMethod(methodName, Map.class);
		     Object resObj=method.invoke(obj, map);
             
		     if(resObj instanceof List){
		    	 resList=(List<Map<String, Object>>) resObj;
		     }
		     
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new SysException("创建类["+className+"]失败！");
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new SysException("访问类["+className+"]失败！");
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new SysException("执行方法["+methodName+"]失败！");
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new SysException("实例化类["+className+"]失败！");
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new SysException("没有找到["+methodName+"]方法！");
		}
       
		if(resList==null || resList.size()==0){
			logger.error("没有查询到数据！");
			throw new SysException("没有查询到数据！");
		}
		
		String tempFileName=null;
        String toFilePath=map.get("toFilePath").toString();
        String fromFilePath=map.get("fromFilePath").toString();
        String page_url=map.get("page_url").toString();
        
        
		/***************************************处理workbook***********************************************************************/
		try {
	        
	        //获得Workbook工作薄对象  
			String uuid= UUIDLong.absStringUUID();
			tempFileName=uuid+".xlsx";//2003版65536行,256列; 2007版及以后是1048576行,16384列
			toFilePath=toFilePath+"\\"+tempFileName;
			//生成excel
			Workbook workBook=ExcelReader.getWorkBookGrid(fromFilePath,toFilePath); 
			
			if(workBook==null){
				logger.error("生成打印模板异常！");
				throw new SysException("生成打印模板异常！");
			}
			
			Sheet sheet=workBook.getSheetAt(0);
			
//			sheet.setDisplayGridlines(true);
			
			int rowIndex=0;
			Map<String,Object> paramMap=new HashMap<String,Object>();
			
			/****************************************查询样式*************************************************/
			paramMap.put("page_url", page_url);
			paramMap.put("user_id", map.get("user_id"));
			paramMap.put("mod_code", map.get("mod_code"));
			String pageJson = sysTableStyleService.queryPrintByPageUrl(paramMap);
			JSONObject myStyle = null;
			Map<Integer, CellStyle> colStyleMap = new HashMap<Integer, CellStyle>();
			if (pageJson != null && !pageJson.equals("")) {
				try {
					myStyle = JSONObject.parseObject(pageJson).getJSONObject("cells");
				} catch (Exception e) {
					pageJson = null;
				}
			}
			
			/****************************************填充标题*************************************************/
			
			if(map.get("title")!=null && !map.get("title").equals("")){
				rowIndex=1;
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
				cell.setCellValue(map.get("title").toString());
				sheet.addMergedRegion(new CellRangeAddress(0,0,0,maxcolumnIndex));
				//CellStyle titleStyle = workBook.createCellStyle();//新建单元格样式  
				Font ftStyle = workBook.createFont();//新建字体样式 
				//titleStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中 
				//titleStyle.setAlignment(CellStyle.ALIGN_CENTER);//水平居中 
				//titleStyle.setWrapText(true);//设置自动换行
				ftStyle.setFontName("宋体");
				ftStyle.setFontHeightInPoints((short) 14);
				ftStyle.setBold(true);
				//titleStyle.setFont(ftStyle);
				//cell.setCellStyle(titleStyle);
				setCellStyle(workBook,cell,"center",myStyle,false,ftStyle,"title",colStyleMap);
			}
			paramMap.put("rowIndex", rowIndex);
			
			/****************************************填充表头需要打印的查询条件*************************************************/
			
			//页眉右上角默认显示页码
			boolean isAutoPageHead=true;
			//页脚默认左下角显示打印人、右下角显示打印时间 
			boolean isAutoPageFoot=true;
			if(map.get("heads")!=null && !map.get("heads").equals("")){

				JSONObject headsJson=null;
		        try{
		        	String heads=map.get("heads").toString();
		        	logger.debug("打印表头条件："+heads);
		        	headsJson = JSONObject.parseObject(heads);
		        }catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error(e.getMessage(), e);
					throw new SysException("heads参数格式不符合json规范！");
				}
				
		        if(headsJson.getString("isAuto")!=null && headsJson.getString("isAuto").equals("false")){
		        	isAutoPageHead=false;
		        }
		        
				 //表头json数组对象
		        if(headsJson.getString("rows")!=null && !headsJson.getString("rows").equals("")){
		        	JSONArray headRowsArray = JSONObject.parseArray(headsJson.getString("rows"));
		        	setPageOfficeHeadConAndFoot(workBook,sheet,headRowsArray,maxcolumnIndex,paramMap,myStyle,colStyleMap);
		        }
				
			}
			
			/****************************************填充表头*************************************************/
			
			List<Map<String,Object>> columnList=new ArrayList<Map<String,Object>>();
			
			//CellStyle headStyle=ExcelReader.getHeadCellStyle(workBook);
			Font fontStyle = workBook.createFont();//新建字体样式  
			fontStyle.setFontName("宋体");
			fontStyle.setBold(true);
			setPageOfficeHead(workBook,sheet,fontStyle,rowsArray,columnList,paramMap,myStyle,colStyleMap);
			
			
			//保存表头行号，打印标题使用
			int headIndex=Integer.parseInt(paramMap.get("rowIndex").toString());
			
			/****************************************填充数据*************************************************/
			
			//获得合并列名称
			String mergeColumns=map.get("mergeColumns")!=null?map.get("mergeColumns").toString():"";
			String[] mergeColumnsArr = mergeColumns.split("@");//如果不传mergeColumns参数,则为一个含有一个空字符的数组
			paramMap.put("mergeColumns", mergeColumnsArr);
			
			setPageOfficeBody(workBook,sheet,columnList,resList,paramMap,myStyle,colStyleMap);
			
			/****************************************填充表尾*************************************************/
			
			if(map.get("foots")!=null && !map.get("foots").equals("")){
				
				JSONObject footsJson=null;
		        try{
		        	String foots=map.get("foots").toString();
		        	logger.debug("打印表头条件："+foots);
		        	footsJson = JSONObject.parseObject(foots);
		        }catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error(e.getMessage(), e);
					throw new SysException("foots参数格式不符合json规范！");
				}
				
		        if(footsJson.getString("isAuto")!=null && footsJson.getString("isAuto").equals("false")){
		        	isAutoPageFoot=false;
		        }
		        
				 //表头json数组对象
		        if(footsJson.getString("rows")!=null && !footsJson.getString("rows").equals("")){
		        	JSONArray footRowsArray = JSONObject.parseArray(footsJson.getString("rows"));
		        	setPageOfficeHeadConAndFoot(workBook,sheet,footRowsArray,maxcolumnIndex,paramMap,null,colStyleMap);
		        }
				
			}
			
			/****************************************最后处理特殊格式*************************************************/
			
			//打印格式处理
			paramMap.put("isAutoPageHead", isAutoPageHead);
			paramMap.put("isAutoPageFoot", isAutoPageFoot);
			setPageOfficeBodyEnd(workBook,sheet,columnList,headIndex,maxcolumnIndex,paramMap,pageJson);
			
			
	        //创建temp文件夹
            File toFile =new File(toFilePath.substring(0,toFilePath.lastIndexOf("\\")));
    		if(!toFile.exists()){
    			toFile.mkdirs();
    		}
    		
    		//传目录，删除昨天的临时文件
    		FileUtil.deleteTempPath(toFile);
    		
            FileOutputStream fileOut = new FileOutputStream(toFilePath);
            workBook.write(fileOut);
	        fileOut.close();
	        
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(),e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(),e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(),e);
		}
		
		return tempFileName;
	}
	
	
	//填充PageOffice表头需要打印的查询条件、表尾
	private void setPageOfficeHeadConAndFoot(Workbook workBook,Sheet sheet,JSONArray headFootArray,int maxcolumnIndex,
			Map<String,Object> paramMap,JSONObject myStyle,Map<Integer,CellStyle> colStyleMap){
		
		//var heads=[ {"columnindex":1,"value":"发生日期："}, {"columnindex":2,"value":""+$("#create_date_b").val()+""}, {"columnindex":3,"value":"至"}, {"columnindex":4,"value": ""+$("#create_date_e").val()+""}, {"columnindex":1,"value": ""+liger.get("content_code").getText()+"","from":"right"}, {"columnindex":2,"value":"补助内容","from":"right"} ];
		/*CellStyle cellStyleCenter=ExcelReader.getBodyCellStyleCenter(workBook,false);
		CellStyle cellStyleLeft=ExcelReader.getBodyCellStyleLeft(workBook,false);
		CellStyle cellStyleRight=ExcelReader.getBodyCellStyleRight(workBook,false);*/
		int rowIndex=Integer.parseInt(paramMap.get("rowIndex").toString());
		Row row;
		Cell cell;
		
		for(int i=0;i<headFootArray.size();i++){
			
			JSONObject cellJson = JSONObject.parseObject(headFootArray.getString(i));
			if(cellJson.getString("cell")==null || cellJson.getString("cell").equals("")){
				throw new SysException("表头查询条件【cell】为空！");
			}
			
			if(cellJson.getString("br")!=null && cellJson.getString("br").equals("true")){
				rowIndex++;
			}
			
			if(sheet.getRow(rowIndex)==null){
				row=sheet.createRow(rowIndex);
			}else{
				row=sheet.getRow(rowIndex);
			}
			row.setHeight((short)400);
			
			int cellIndex=Integer.parseInt(cellJson.getString("cell"));
			if(cellJson.getString("from")!=null && cellJson.getString("from").equalsIgnoreCase("right")){
				//从右开始填充
				cellIndex=maxcolumnIndex-cellIndex;
			}
			
			
			if(row.getCell(cellIndex)==null){
				cell=row.createCell(cellIndex);
			}else{
				cell= row.getCell(cellIndex);
			}
			cell.setCellValue(cellJson.getString("value"));
			
			//设置单元格合并列
			if(cellJson.getString("colSpan")!=null && !cellJson.getString("colSpan").equals("")){
				int colSpan=Integer.parseInt(cellJson.getString("colSpan"));
				if(cellJson.getString("colSpan").equalsIgnoreCase("-1")){
					colSpan=maxcolumnIndex+1;
				}
				CellRangeAddress region=new CellRangeAddress(rowIndex,rowIndex,cellIndex==0?0:cellIndex+1, (cellIndex==0?0:cellIndex+1)+colSpan-1);
				sheet.addMergedRegion(region);//下标从0开始要-1
				
			}
			
			
			String align=cellJson.getString("align")==null?null:cellJson.getString("align").toString();
			/*if(align!=null && !align.equals("")){
				if(align.equalsIgnoreCase("center")){
					cell.setCellStyle(cellStyleCenter);//设置单元格样式-水平居中
				}else if(align.equalsIgnoreCase("right")){
					cell.setCellStyle(cellStyleRight);//设置单元格样式-水平居右 
				}else{
					cell.setCellStyle(cellStyleLeft);//设置单元格样式-水平居左 
				}
			}else{
				cell.setCellStyle(cellStyleLeft);//设置单元格样式-水平居左 
			}*/
			setCellStyle(workBook,cell,align,myStyle,false,null,"HeadConAndFoot",colStyleMap);
		}
		
		paramMap.put("rowIndex", rowIndex+1);
	}
	
	
	//填充PageOffice表头
	private void setPageOfficeHead(Workbook workBook,Sheet sheet,Font fontStyle,JSONArray rowsArray,
			List<Map<String,Object>> columnList,Map<String,Object> paramMap,JSONObject myStyle,Map<Integer,CellStyle> colStyleMap){
		
		//columns={ "maxcolumnindex":9, "maxlevel":2, "rows":[{ "display": "期间",//显示名称 "align": "left",//内容居左 "colSpan": 2,//合并2列 "columnindex": 0,//单元格下标 "level":1, "columns": [ { "display": "年", "name": "acc_year",//sql返回别名 "align": "left", "type": "string",//数据类型 "columnindex": 0, "level":2, "width": 133//单元格宽度 }, { "display": "月", "name": "acc_month", "align": "left", "columnindex": 1, "level":2, "width": 133 } ] }, { "display": "凭证号", "name": "vouch_no", "align": "left", "rowSpan": 2, "type": "string", "columnindex": 2, "level":1, "width": 133 }, { "display": "摘要", "name": "summary", "align": "left", "rowSpan": 2, "type": "string", "columnindex": 3, "level":1, "width": 133 }, { "display": "补助内容", "name": "content_name", "align": "left", "colSpan": 6, "columnindex": 4, "level":1, "columns": [ { "display": "在职人员补助", "name": "fun_name", "align": "left", "type": "string", "columnindex": 4, "level":2, "width": 133 }, { "display": "支出经济分类科目", "name": "eco_name", "align": "left", "type": "string", "columnindex": 5, "level":2, "width": 133 }, { "display": "借方", "name": "debit", "align": "right", "formatter": "###,##0.00", "type": "string", "columnindex": 6, "level":2, "width": 133 }, { "display": "贷方", "name": "credit", "align": "right", "formatter": "###,##0.00", "type": "string", "columnindex": 7, "level":2, "width": 133 }, { "display": "方向", "name": "subj_dire", "align": "left", "type": "string", "columnindex": 8, "level":2, "width": 133 }, { "display": "余额", "name": "end_os", "align": "right", "formatter": "###,##0.00",//数据格式化 "type": "string", "columnindex": 9, "level":2, "width": 133 } ] } ] }
		int rowIndex=Integer.parseInt(paramMap.get("rowIndex").toString());
		Row row;
		if(sheet.getRow(rowIndex)==null){
			row=sheet.createRow(rowIndex);
		}else{
			row=sheet.getRow(rowIndex);
		}
		
		
		for(int i=0;i<rowsArray.size();i++){
			
			JSONObject cellJson = JSONObject.parseObject(rowsArray.getString(i));

			if(cellJson.getString("columnindex")==null || cellJson.getString("columnindex").equals("")){
				throw new SysException("表头【columnindex】为空！");
			}
			if(cellJson.getString("level")==null || cellJson.getString("level").equals("")){
				throw new SysException("表头【level】为空！");
			}
			
			int cellIndex=Integer.parseInt(cellJson.getString("columnindex"));

			Cell cell;
			if(row.getCell(cellIndex)==null){
				cell=row.createCell(cellIndex);
			}else{
				cell= row.getCell(cellIndex);
			}
			
			cell.setCellValue(cellJson.getString("display"));
			
			//设置单元格合并列
			if(cellJson.getString("colSpan")!=null && !cellJson.getString("colSpan").equals("")){
				int colSpan=Integer.parseInt(cellJson.getString("colSpan"));
				CellRangeAddress region=new CellRangeAddress(rowIndex,rowIndex,cellIndex, cellIndex+colSpan-1);
				sheet.addMergedRegion(region);//下标从0开始要-1
				RegionUtil.setBorderBottom(CellStyle.BORDER_THIN,region, sheet, workBook);  
			    RegionUtil.setBorderLeft(CellStyle.BORDER_THIN,region, sheet, workBook);  
			    RegionUtil.setBorderRight(CellStyle.BORDER_THIN,region, sheet, workBook);  
			    RegionUtil.setBorderTop(CellStyle.BORDER_THIN,region, sheet, workBook);  
			}
			
			//设置单元格合并行
			if(cellJson.getString("rowSpan")!=null && !cellJson.getString("rowSpan").equals("")){
				int rowSpan=Integer.parseInt(cellJson.getString("rowSpan"));
				for(int s=1;s<rowSpan;s++){
					if(sheet.getRow(s)==null){
						sheet.createRow(s);
					}
				}
				
				CellRangeAddress region=new CellRangeAddress(rowIndex,rowIndex+rowSpan-1,cellIndex, cellIndex);
				sheet.addMergedRegion(region);//下标从0开始要-1
				RegionUtil.setBorderBottom(CellStyle.BORDER_THIN,region, sheet, workBook);  
			    RegionUtil.setBorderLeft(CellStyle.BORDER_THIN,region, sheet, workBook);  
			    RegionUtil.setBorderRight(CellStyle.BORDER_THIN,region, sheet, workBook);  
			    RegionUtil.setBorderTop(CellStyle.BORDER_THIN,region, sheet, workBook);  
			}
			
			//cell.setCellStyle(cellStyle);//设置单元格样式
			setCellStyle(workBook,cell,"center",myStyle,true,fontStyle,"head",colStyleMap);
			
			
			if(cellJson.getString("columns")!=null && !cellJson.getString("columns").equals("")){
				
				/*********************************************************/
				//cjc 去表头最大行号
				int maxRowIndex = paramMap.get("maxRowIndex") != null ? Integer.parseInt(paramMap.get("maxRowIndex").toString()) : 0;
				//cjc 给表头赋值
				if(rowIndex+1 > maxRowIndex){
					paramMap.put("maxRowIndex", rowIndex+1);
				}
				/***********************************************************/
				//递归钻取表头
				paramMap.put("rowIndex", rowIndex+1);
				
				setPageOfficeHead(workBook,sheet,fontStyle,JSONObject.parseArray(cellJson.getString("columns")),columnList,paramMap,myStyle,colStyleMap);
			}else if(cellJson.getString("name")!=null && !cellJson.getString("name").equals("")){
				//保存表头信息，填充数据使用
				Map<String,Object> columnMap=new HashMap<String,Object>();
				columnMap.put("name", cellJson.getString("name"));
				columnMap.put("columnindex", cellIndex);
				columnMap.put("align", cellJson.getString("align")==null?"":cellJson.getString("align"));
				columnMap.put("type", cellJson.getString("type")==null?"":cellJson.getString("type"));
				columnMap.put("width", cellJson.getString("width")==null?"":cellJson.getString("width"));
				columnMap.put("formatter", cellJson.getString("formatter")==null?"":cellJson.getString("formatter"));
				columnMap.put("reg", cellJson.getString("reg")==null?"":cellJson.getString("reg"));
				columnList.add(columnMap);
				
			}
			
		}
		
	}
	
	
	//填充PageOffice数据
	private void setPageOfficeBody(Workbook workBook,Sheet sheet,List<Map<String,Object>> columnList,List<Map<String,Object>> resList,Map<String,Object> paramMap,JSONObject myStyle,Map<Integer,CellStyle> colStyleMap){
		
		/*CellStyle cellStyleCenter=ExcelReader.getBodyCellStyleCenter(workBook,true);
		CellStyle cellStyleLeft=ExcelReader.getBodyCellStyleLeft(workBook,true);
		CellStyle cellStyleRight=ExcelReader.getBodyCellStyleRight(workBook,true);*/
		
		/******************************************************************************/
		
		//开始行号 最新cjc
		int rowIndex=paramMap.get("maxRowIndex")!= null ? Integer.parseInt(paramMap.get("maxRowIndex").toString()) : Integer.parseInt(paramMap.get("rowIndex").toString()) ;
		
		/*****************************************************************************/
		
		//int rowIndex=Integer.parseInt(paramMap.get("rowIndex").toString()); 原来
		
		//用于合并行
		String [] mergeColumnsArr=(String[]) paramMap.get("mergeColumns");//不传为null
		List<Map<String, Object>> mergeColumnsList= new ArrayList<Map<String,Object>>();//待合并区域信息
		CellRangeAddress cellRange;//合并区域
		if (mergeColumnsArr!=null) {
			for (int i = 0; i < mergeColumnsArr.length; i++) {
				Map<String, Object> map=new HashMap<String, Object>();
				if (StringUtils.isNotBlank(mergeColumnsArr[i])) {
					map.put("mergeColumn", mergeColumnsArr[i]);//带合并列名称
					map.put("rowBeginIndex", rowIndex);//当前合并的起始行号
					map.put("rowEndIndex", rowIndex);//当前合并的终止行号
					map.put("columnValue", "和字段内容不重复的占位符");//当前区域显示内容
					mergeColumnsList.add(map);
				}
			}
		}
		
		//遍历数据
		int dataRows=1;
		for(Map<String,Object> resMap:resList){
			
			rowIndex++;
			Row row;
			if(sheet.getRow(rowIndex)==null){
				row=sheet.createRow(rowIndex);
			}else{
				row=sheet.getRow(rowIndex);
			}
			
			//根据传的json遍历列
			
			int columnIndex=0;//当前列序号
			for(Map<String,Object> columnMap:columnList){
				
				String columnName=columnMap.get("name").toString();
				int cellIndex=Integer.parseInt(columnMap.get("columnindex").toString());
				Cell cell;
				if(row.getCell(cellIndex)==null){
					cell=row.createCell(cellIndex);
				}else{
					cell= row.getCell(cellIndex);
				}
				
				for(Map.Entry<String, Object> enty:resMap.entrySet()){
					if(columnName.equalsIgnoreCase(enty.getKey())){
						String mainVal=enty.getValue()==null?"":enty.getValue().toString();
						
						String reg=columnMap.get("reg").toString();
						if(!reg.equals("") && reg.indexOf("=")!=-1){
							
							/*
							 reg自定义规则转换:
					    		数据格式化：0=否,1=是,else=其他
					    		数据格式化：0.00=Q
							 * */
							
							String[] regArray=reg.split(",");
							for(int a=0;a<regArray.length;a++){
								
								if(regArray[a].split("=")[0].equalsIgnoreCase(mainVal)){
									mainVal=regArray[a].split("=")[1];
									break;
								}else if(regArray[a].split("=")[0].equalsIgnoreCase("else") ){
									mainVal=regArray[a].split("=")[1];
									break;
								} 
							}
								
						}
						
						
						String formatter=columnMap.get("formatter").toString();
						if(formatter.replace("\\", "").replace("/", "").replace("-", "").toLowerCase().indexOf("yyyymmdd")!=-1){
							/*
							 formatter:
					    		日期格式化：yyyy-MM-dd HH:mm:ss
							 * */
		    				try{
		    					mainVal=DateUtil.dateFormat1(mainVal, formatter);
		    				}catch(ParseException e){
		    					logger.error(mainVal+"，日期格式化错误："+formatter);
		    				}
				    			
							
						}else if(formatter.indexOf("##0.0")!=-1 && (mainVal.indexOf("E")>0 || NumberUtil.isNumeric(mainVal))){
							/*
							 formatter:
					    		金额格式化：###,##0.00
							 * */
							
							try{
								int point = formatter.length() - (formatter.indexOf(".") + 1);
								//mainVal=NumberUtil.numberToString(Double.parseDouble(mainVal),point);
								if(point==1){
									myformat1.setRoundingMode(RoundingMode.HALF_UP);
									mainVal=myformat1.format(new BigDecimal(mainVal));
								}else if(point==2){
									myformat2.setRoundingMode(RoundingMode.HALF_UP);
									mainVal=myformat2.format(new BigDecimal(mainVal));
								}else if(point==3){
									myformat3.setRoundingMode(RoundingMode.HALF_UP);
									mainVal=myformat3.format(new BigDecimal(mainVal));
								}else if(point==4){
									myformat4.setRoundingMode(RoundingMode.HALF_UP);
									mainVal=myformat4.format(new BigDecimal(mainVal));
								}else{
									mainVal=myformat0.format(Double.parseDouble(mainVal));
								}
								
							}catch(Exception e){
								logger.error(mainVal+"，金额格式化错误："+formatter);
							}
						}
						
						cell.setCellValue(mainVal);
						int rowBeginIndex=0;
						int rowEndIndex=0;
						//查看当前填充数据的列,是否是需要合并的列
						for (int i=0;i< mergeColumnsList.size();i++) {
							Map<String, Object> map = mergeColumnsList.get(i);
							if(columnName.equals(map.get("mergeColumn").toString())){//当前列是需要合并的列
								rowBeginIndex=Integer.valueOf(map.get("rowBeginIndex").toString());//取出当前列已经记录的合并区域的起始行号
								rowEndIndex=Integer.valueOf(map.get("rowEndIndex").toString());//终止行号
								
								
								
								if(mainVal.equals(map.get("columnValue").toString())){//当前单元格显示内容是否和合并单元格内容一致
									map.put("rowEndIndex",rowEndIndex+1);//内容一致则,终止行号增加1,将当前单元格涵盖到合并单元格中
									if(dataRows >= resList.size() && rowEndIndex+1 > rowBeginIndex){//如果当前是最后一条数据,并且合并的行数大于1则创建合并单元格
										cellRange=new CellRangeAddress(rowBeginIndex,rowEndIndex+1, columnIndex, columnIndex);//合并行起始行号,终止行号,当前列序号,当前列序号(只合并一列)
										sheet.addMergedRegion(cellRange);
									}
								}else{//内容不一致,则
									if(rowEndIndex>rowBeginIndex){//创建合并单元格
										cellRange=new CellRangeAddress(rowBeginIndex,rowEndIndex, columnIndex, columnIndex);//合并行起始行号,终止行号,当前列序号,当前列序号(只合并一列)
										sheet.addMergedRegion(cellRange);
									}
									map.put("columnValue", mainVal);//将当前单元格内容写入新的合并单元格内容中
									map.put("rowBeginIndex",rowEndIndex+1);//新的合并单元格的起始行号在上一个合并单元格基础上+1(下移一行)
									map.put("rowEndIndex",rowEndIndex+1);//新合并单元格的终止行号与起始行号一致(即,新合并单元格,合并的行数暂时为所在行)
								}
							}
							
						}
						
						break;
					}
				}
				
				String align=columnMap.get("align")==null?"left":columnMap.get("align").toString();
				/*if(align!=null && !align.equals("")){
					if(align.equalsIgnoreCase("center")){
						//水平居中
						cell.setCellStyle(getCellStyle(workBook,cell,cellStyleCenter,myStyle));
						//cell.setCellStyle(cellStyleCenter);
					}else if(align.equalsIgnoreCase("right")){
						//水平居右 
						cell.setCellStyle(getCellStyle(workBook,cell,cellStyleRight,myStyle));
						//cell.setCellStyle(cellStyleRight);
					}else{
						//水平居左
						cell.setCellStyle(getCellStyle(workBook,cell,cellStyleLeft,myStyle));
						//cell.setCellStyle(cellStyleLeft);
					}
				}else{
					//水平居左
					cell.setCellStyle(getCellStyle(workBook,cell,cellStyleLeft,myStyle)); 
					//cell.setCellStyle(cellStyleLeft);
				}
				*/
				setCellStyle(workBook,cell,align,myStyle,true,null,"body",colStyleMap); 
				columnIndex++;//存储下一列序号
				 
			}
			dataRows++;
		}
		rowIndex++;
		paramMap.put("rowIndex", rowIndex);
	}
	
	private int getPOIWidthByExcelPiex(int Piex) {  
	    return (int) (Piex * 32);  
	}
	
	
	//填充PageOffice数据-结束方法-打印格式处理
	private void setPageOfficeBodyEnd(Workbook workBook,Sheet sheet,List<Map<String,Object>> columnList,int headIndex,int maxcolumnIndex,Map<String,Object> paramMap,String pageJson){
		
		boolean isAutoPageHead=Boolean.parseBoolean(paramMap.get("isAutoPageHead").toString());
		boolean isAutoPageFoot=Boolean.parseBoolean(paramMap.get("isAutoPageFoot").toString());
		
		//没有数据
		if(pageJson==null || pageJson.equals("")){
			
			//默认其他格式
			StringBuffer json=new StringBuffer();
			json.append("{");
			
			//打印网格线
			json.append("\"PrintGridlines\":\"false\",");
			
			//打印标题
			json.append("\"TitleRowStart\":\"0\",");//起始行
			json.append("\"TitleRowEnd\":\""+headIndex+"\",");//结束行
			json.append("\"TitleCellStart\":\"0\",");//起始列
			json.append("\"TitleCellEnd\":\""+maxcolumnIndex+"\",");//结束列
			
			
			//页边距
			json.append("\"TopMargin\":\"0.3\",");// 页边距（上） 换算：0.3=0.8 
			json.append("\"BottomMargin\":\"0.3\",");//页边距（下）    
			json.append("\"LeftMargin\":\"0.5\",");// 页边距（左）换算：0.5=1.3 
			json.append("\"RightMargin\":\"0.0\",");// 页边距（右）
			json.append("\"HorizontallyCenter\":\"true\",");//水平
			json.append("\"VerticallyCenter\":\"false\",");//垂直
           
            //纸张信息
			json.append("\"Landscape\":\"false\",");//打印方向，true：横向，false：纵向(默认)    
			json.append("\"VResolution\":\"300\",");//打印质量600点
			json.append("\"PaperSize\":\"9\",");//纸张类型    A4_PAPERSIZE
            json.append("\"FitWidth\":\"1\",");//设置页宽(调整)
            json.append("\"FitHeight\":\"1\",");//设置页高(调整)
            json.append("\"HeaderMargin\":\"0.0\",");//页眉距离
            json.append("\"FooterMargin\":\"0.0\",");//页脚距离
            json.append("\"PageStart\":\"0\",");//起始页码
           
            //页眉内容
            json.append("\"HeadLeft\":\"\",");//页眉-左
            json.append("\"HeadCenter\":\"\",");//页眉-中
            json.append("\"HeadRight\":\"\",");//页眉-右
    	  
            //页脚内容
            json.append("\"FootLeft\":\"\",");//页脚-左
            json.append("\"FootCenter\":\"\",");//页脚-中
            json.append("\"FootRight\":\"\",");//页脚-右
                           
            //列宽
            json.append("\"columns\":[]");
           
            json.append("}");
            pageJson=json.toString();
		}
		
		JSONObject excelObj = JSONObject.parseObject(pageJson);
		
		//设置列样式
		JSONArray columnArray=JSONObject.parseArray(excelObj.getString("columns"));
		if(columnArray!=null && columnArray.size()>0){
			for(int i=0;i<columnArray.size();i++){
				
				JSONObject cellJson = JSONObject.parseObject(columnArray.getString(i));
				int columnindex=cellJson.getIntValue("columnindex");
				sheet.setColumnWidth(columnindex,cellJson.getIntValue("width"));//列宽
				sheet.setColumnHidden(columnindex, cellJson.getBooleanValue("hide"));//列隐藏
				
			}
			
		}else{
			
			//根据表头信息默认设置列宽
			for(Map<String,Object> columnMap:columnList){
				int cellIndex=Integer.parseInt(columnMap.get("columnindex").toString());
				String width=columnMap.get("width").toString();
				
				//设置列宽度
				if(!width.equals("")){
					try{
						sheet.setColumnWidth(cellIndex,getPOIWidthByExcelPiex(Integer.parseInt(width)));
					}catch(Exception e){
						sheet.autoSizeColumn(cellIndex);
					}
				}else{
					sheet.autoSizeColumn(cellIndex);
				}
			}
		}
		
		//打印网格线
		sheet.setPrintGridlines(excelObj.getBooleanValue("PrintGridlines"));
		
		//页边距
        sheet.setMargin(Sheet.TopMargin,excelObj.getDoubleValue("TopMargin"));// 页边距（上）    换算：0.3=0.8 
        sheet.setMargin(Sheet.BottomMargin,excelObj.getDoubleValue("BottomMargin"));// 页边距（下）    
        sheet.setMargin(Sheet.LeftMargin,excelObj.getDoubleValue("LeftMargin"));// 页边距（左） 换算：0.5=1.3   
        sheet.setMargin(Sheet.RightMargin,excelObj.getDoubleValue("RightMargin"));// 页边距（右）
        sheet.setHorizontallyCenter(excelObj.getBooleanValue("HorizontallyCenter")); //设置打印页面为水平居中
        sheet.setVerticallyCenter(excelObj.getBooleanValue("VerticallyCenter")); //设置打印页面为垂直居中
		
        PrintSetup ps = sheet.getPrintSetup();    
        ps.setLandscape(excelObj.getBooleanValue("Landscape")); // 打印方向，true：横向，false：纵向(默认)  
        ps.setVResolution(excelObj.getShortValue("VResolution"));//打印质量600点
        ps.setPaperSize(excelObj.getShortValue("PaperSize")); //纸张类型    
        ps.setFitWidth(excelObj.getShortValue("FitWidth"));//设置页宽(调整)
        ps.setFitHeight(excelObj.getShortValue("FitHeight"));//设置页高(调整)
        ps.setHeaderMargin(excelObj.getDoubleValue("HeaderMargin"));  //页眉距离
        ps.setFooterMargin(excelObj.getDoubleValue("FooterMargin")); //页脚距离
        ps.setPageStart(excelObj.getShortValue("PageStart")); //起始页码
        
		//每页打印标题
        if(excelObj.get("TitleRowStart")!=null){
        	sheet.setRepeatingRows(new CellRangeAddress(excelObj.getIntValue("TitleRowStart"),excelObj.getIntValue("TitleRowEnd"),excelObj.getIntValue("TitleCellStart"),excelObj.getIntValue("TitleCellEnd")));
        }else{
        	sheet.setRepeatingRows(new CellRangeAddress(0,headIndex,0,maxcolumnIndex));
        }
        
		
		//页眉页脚内容
        Header head= sheet.getHeader();
        head.setLeft(excelObj.getString("HeadLeft"));//页眉-左
        head.setCenter(excelObj.getString("HeadCenter"));//页眉-中
        if(isAutoPageHead){
        	head.setRight("&P/&N");//页眉-右
		}else{
			head.setRight(excelObj.getString("HeadRight"));//页眉-右
		}
        
        //页脚内容
        Footer foot=sheet.getFooter();
        foot.setCenter(excelObj.getString("FootCenter"));//页眉-中
        if(isAutoPageFoot){
        	foot.setLeft("打印人："+SessionManager.getUserName());
        }else{
        	foot.setLeft(excelObj.getString("FootLeft"));
        }
        
        if(isAutoPageFoot){
        	foot.setRight("打印时间：&D &T");//页脚-左
        	//foot.setRight("打印日期：&D");
        }else{
        	foot.setRight(excelObj.getString("FootRight"));//页脚-右 
        }

        //ExcelReader.setHeadFoot2007(sheet);
	}

	//查询列表打印-预览页面-保存打印格式
	@Override
	public Map<String,Object> savePrintGridPre(Map<String, Object> map) throws DataAccessException {
		// TODO Auto-generated method stub
		
		Map<String,Object> resMap = new HashMap<String,Object>();
		
        try {  
        	//获取templateFilePath文件的io流  
        	String toFilePath=map.get("toFilePath").toString();
        	File toFile=new File(toFilePath);
    		if(toFile.exists()){
    			
    			StringBuffer json=new StringBuffer();
    			Workbook workbook = new XSSFWorkbook(new FileInputStream(toFilePath));
    			Sheet sheet=workbook.getSheetAt(0);
    			json.append("{");
    			
    			//打印网格线
    			json.append("\"PrintGridlines\":\""+sheet.isPrintGridlines()+"\",");
    			
    			//每页打印标题
    		    CellRangeAddress cellRan=sheet.getRepeatingRows();
    		    if(cellRan!=null){
    		    	json.append("\"TitleRowStart\":\""+cellRan.getFirstRow()+"\",");//起始行
        			json.append("\"TitleRowEnd\":\""+cellRan.getLastRow()+"\",");//结束行
        			json.append("\"TitleCellStart\":\""+cellRan.getFirstColumn()+"\",");//起始列
        			json.append("\"TitleCellEnd\":\""+cellRan.getLastColumn()+"\",");//结束列
    		    }
    		    
    			 //页边距
    			json.append("\"TopMargin\":\""+sheet.getMargin(Sheet.TopMargin)+"\",");// 页边距（上）
    			json.append("\"BottomMargin\":\""+sheet.getMargin(Sheet.BottomMargin)+"\",");//页边距（下）    
    			json.append("\"LeftMargin\":\""+sheet.getMargin(Sheet.LeftMargin)+"\",");// 页边距（左）    
    			json.append("\"RightMargin\":\""+sheet.getMargin(Sheet.RightMargin)+"\",");// 页边距（右）
    			json.append("\"HorizontallyCenter\":\""+sheet.getHorizontallyCenter()+"\",");//水平
    			json.append("\"VerticallyCenter\":\""+sheet.getVerticallyCenter()+"\",");//垂直
                
                //纸张信息
                PrintSetup ps =sheet.getPrintSetup(); 
                json.append("\"Landscape\":\""+ps.getLandscape()+"\",");//打印方向，true：横向，false：纵向(默认)    
                json.append("\"VResolution\":\""+ps.getVResolution()+"\",");//打印质量600点
                json.append("\"PaperSize\":\""+ps.getPaperSize()+"\",");//纸张类型    
                json.append("\"FitWidth\":\""+ps.getFitWidth()+"\",");//设置页宽(调整)
                json.append("\"FitHeight\":\""+ps.getFitHeight()+"\",");//设置页高(调整)
                json.append("\"HeaderMargin\":\""+ps.getHeaderMargin()+"\",");//页眉距离
                json.append("\"FooterMargin\":\""+ps.getFooterMargin()+"\",");//页脚距离
                json.append("\"PageStart\":\""+ps.getPageStart()+"\",");//起始页码

                //页眉内容
                Header head= sheet.getHeader();
                json.append("\"HeadLeft\":\""+head.getLeft()+"\",");//页眉-左
                json.append("\"HeadCenter\":\""+head.getCenter()+"\",");//页眉-中
                json.append("\"HeadRight\":\""+head.getRight()+"\",");//页眉-右
                  
                //页脚内容
                Footer foot=sheet.getFooter();
                json.append("\"FootLeft\":\""+foot.getLeft()+"\",");//页脚-左
                json.append("\"FootCenter\":\""+foot.getCenter()+"\",");//页脚-中
                json.append("\"FootRight\":\""+foot.getRight()+"\",");//页脚-右
                                
                //列样式
                json.append("\"columns\":[");
    			Row row=sheet.getRow(0);
            	for(int i=0;i<row.getLastCellNum();i++){
            		Cell cell=row.getCell(i);
            		if(cell!=null){
            			json.append("{");
            			json.append("\"columnindex\":"+i+",");
            			json.append("\"hide\":"+sheet.isColumnHidden(i)+",");//是否隐藏
            			json.append("\"width\":"+sheet.getColumnWidth(i));//列宽
            			json.append("},");
            		}
            		
            	}
            	json.setCharAt(json.length() - 1, ']');//替换最后一个逗号
            	
            	//单元格样式
            	json.append(",\"cells\":{");
            	boolean isStyle=false;
            	for(int i=0;i<sheet.getLastRowNum()+1;i++){
            		if(i==15){
            			//由于不知道明细数据是从第几行开始的，默认取前15行的单元格样式，明细数据每列的样式保持一致，表尾暂不处理
            			break;
            		}
            		row=sheet.getRow(i);
            		if(row!=null){
            			json.append("\""+i+"\":[");
            			for(int j=0;j<row.getLastCellNum();j++){
                			Cell cell=row.getCell(j);
                			if(cell!=null){
                				isStyle=true;
                				CellStyle cellStyle =cell.getCellStyle();
                    			Font font=workbook.getFontAt(cellStyle.getFontIndex());
                				json.append("{");
                    			json.append("\"rowindex\":"+i+",");
                    			json.append("\"colindex\":"+j+",");
                    			json.append("\"align\":"+cellStyle.getAlignment()+",");//对齐方式：0常规1靠左(缩进)2居中3靠右(缩进)4填充5两端对齐6跨列居中7分散对齐(缩进)
                				json.append("\"wrap\":"+cellStyle.getWrapText()+",");//是否换行
                				json.append("\"fontBold\":"+font.getBold()+",");//字体
                    			json.append("\"fontName\":\""+font.getFontName()+"\",");//字体
                    			json.append("\"fontPoint\":"+font.getFontHeightInPoints());//字体
                    			json.append("},");
                			}
                		
                		}
            			if(isStyle){
            				json.setCharAt(json.length() - 1, ']');//替换最后一个逗号
            			}else{
            				json.append("]");
            			}
            			json.append(",");
            		}
        			
            	}
    			json.setCharAt(json.length() - 1, '}');//替换最后一个逗号
    			
            	
            	
            	json.append("}");
            	
                //保存打印格式
    			map.put("table", "SYS_PRINT_"+map.get("mod_code"));
    			sysTableStyleMapper.deleteStyleByUrl(map);
    			map.put("page_json", json.toString());
    			sysTableStyleMapper.insertStyleByUrl(map);
                
                resMap.put("state", true);
        		resMap.put("msg", "保存成功！");
        		
    		}else{
    			resMap.put("state", false);
        		resMap.put("msg", "没有找到文件！");
    		}
    		
    		
        }catch(Exception e){
        	 logger.error(e.getMessage());  
             throw new SysException(e.getMessage(),e);  
        }
        
		return resMap;
	}
	
	//根据保存的格式设置单元格样式
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
	
}
