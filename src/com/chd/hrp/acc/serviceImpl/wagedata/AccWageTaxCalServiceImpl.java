/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）调整科技有限公司
*/

package com.chd.hrp.acc.serviceImpl.wagedata;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.hrp.acc.dao.AccWageItemsMapper;
import com.chd.hrp.acc.dao.report.SuperReportDesignMapper;
import com.chd.hrp.acc.dao.wage.AccWageMapper;
import com.chd.hrp.acc.dao.wagedata.AccWagePayMapper;
import com.chd.hrp.acc.dao.wagedata.AccWageTaxCalMapper;
import com.chd.hrp.acc.entity.AccWageItems;
import com.chd.hrp.acc.entity.AccWageTaxCal;
import com.chd.hrp.acc.entity.report.RepDefineEle;
import com.chd.hrp.acc.service.wagedata.AccWageTaxCalService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 工资套合并日志<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("accWageTaxCalService")
public class AccWageTaxCalServiceImpl implements AccWageTaxCalService {

	private static Logger logger = Logger.getLogger(AccWageTaxCalServiceImpl.class);
	
	@Resource(name = "accWageTaxCalMapper")
	private final AccWageTaxCalMapper accWageTaxCalMapper = null;
	
	@Resource(name = "accWageItemsMapper")
	private final AccWageItemsMapper accWageItemsMapper = null;
    
	@Resource(name = "accWagePayMapper")
	private final AccWagePayMapper accWagePayMapper = null;
	
	@Resource(name = "accWageMapper")
	private final AccWageMapper accWageMapper = null;
	
	@Resource(name = "superReportDesignMapper")
	private final SuperReportDesignMapper superReportDesignMapper = null;
	/**
	 * @Description 
	 * 工资套合并日志<BR> 添加AccWageTaxCal
	 * @param AccWageTaxCal entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAccWageTaxCal(Map<String,Object> entityMap)throws DataAccessException{
		
		List<String> list = new ArrayList<String>();
		
		String wage_code="";
		
		StringBuffer msg= new StringBuffer();
		
		try {
			
			Pattern p = Pattern.compile("\\{.*?\\}");

			String cal_name = entityMap.get("cal_name").toString();//.replaceAll("\\(", "").replaceAll("\\)", "");
			
			String select_cal = entityMap.get("cal_name").toString();

			Matcher m = p.matcher(cal_name);

			while (m.find()) {

				list.add(m.group());

			}

			for (int i = 0; i < list.size(); i++) {

				String item_name = list.get(i).replaceAll("\\{", "").replaceAll("\\}", "");

				if (item_name.indexOf("'")>-1) {

					//String [] array = entityMap.get("cal_name").toString().split("\\|");
					
						entityMap.put("ele_name", item_name.substring(0, item_name.indexOf("'")-1).toString());
						
						entityMap.put("mod_code", "0102");
						
						List<RepDefineEle> rde=superReportDesignMapper.querySuperReportEleByMod(entityMap);
						
						cal_name=cal_name.replaceFirst(item_name.substring(0, item_name.indexOf("'")-1).toString(),rde.get(0).getEle_code());
						
						select_cal=select_cal.replaceFirst(item_name.substring(0, item_name.indexOf("'")-1).toString(),rde.get(0).getEle_code());
						
						/*select_cal=Pattern.compile("[`~!@#$%^&()|{}':;',\\[\\].<>?~！@#￥%……&（）|{}【】‘；：”“’。，、？]")
								.matcher(select_cal).replaceAll("").trim()
								.replaceAll(rde.get(0).getEle_code(), "").replaceAll(Pattern.compile("[`~!@#$%^&()|{}':;',\\[\\].<>?~！@#￥%……&（）|{}【】‘；：”“’。，、？]")
								.matcher(item_name).replaceAll("").trim(), "0");
						
						for(int j=0;j<array[i].split("','").length;j++){
							
								cal_name=cal_name.replaceFirst(Pattern.compile("[`~!@#$%^&|{}':;',\\[\\].<>?~！@#￥%……&|{}【】‘；：”“’。，、？]")
										.matcher(item_name.split("','")[j]).replaceAll("").trim(), array[i].split("','")[j].toString());
								
						}*/
						
				} else {

					entityMap.put("item_name", item_name);

					AccWageItems itemList = accWageItemsMapper.queryAccWageColumnByCal(entityMap);

					if (itemList != null) {

						//if(itemList.getData_type()==0){
							
						cal_name = cal_name.replaceFirst(itemList.getItem_name(),  "to_number("+itemList.getColumn_item().toString()+")");

						select_cal=select_cal.replaceFirst(itemList.getItem_name(), "to_number("+itemList.getColumn_item().toString()+")");
						
						/*}else{
							
							cal_name = cal_name.replaceFirst(itemList.getItem_name(), "to_char("+itemList.getColumn_item().toString()+")");

							select_cal=select_cal.replaceFirst(itemList.getItem_name(), "to_char("+itemList.getColumn_item().toString()+")");
							
						}*/
						
					}else{
						
						msg.append("【"+item_name+"】,");
					}

				}

			}

if("".equals(msg)){
				
				return "{\"msg\":\"工资项目"+msg.substring(0, msg.length()-1)+".\",\"state\":\"false\"}";
			}
			
			Calendar calendar = Calendar.getInstance();

			String year = String.valueOf(calendar.get(Calendar.YEAR));

			String month = String.valueOf(calendar.get(Calendar.MONTH) - 1);

			String day = DateUtil.getMaxMonthDate(year, month);

			cal_name = cal_name.replaceAll("如果完", "  end  ")
					.replaceAll("如果", "  case when  ")
					.replaceAll("否则", "  else  ").replaceAll("则", "  then  ")
					.replaceAll("或者", "  or  ").replaceAll("并且", "  and  ")
					.replaceAll("取上月自然天数", "wageLastMonthDays\\('取上月自然天数'\\)").replaceAll("取整", " round")
					.replaceAll("是", " 1").replaceAll("否", " 0")/*.replaceAll("\\{\\{'", "")
					.replaceAll("'\\)\\}", " '\\)")*/;
			
			select_cal = select_cal.replaceAll("如果完", "  end  ")
					.replaceAll("如果", "  case when  ")
					.replaceAll("否则", "  else  ").replaceAll("则", "  then  ")
					.replaceAll("或者", "  or  ").replaceAll("并且", "  and  ")
					.replaceAll("取上月自然天数", "1").replaceAll("取整", " round")
					.replaceAll("是", " 1").replaceAll("否", " 0")/*.replaceAll("\\{\\{'", "")
					.replaceAll("'\\)\\}", " '\\)")*/;
			
			try {
					
					entityMap.put("select_cal", select_cal.replaceAll("\\{", "").replaceAll("\\}", "").replace("本集团",SessionManager.getGroupId()).replace("本医院",SessionManager.getHosId()).replace("本账套",SessionManager.getCopyCode()).replace("本职工",SessionManager.getUserId()));
					
					accWagePayMapper.queryAccWagePayByItemCal(entityMap);
		 
			} catch (Exception e) {
				
				logger.error(e.getMessage(), e);
				
				return "{\"msg\":\"公式不合法.\",\"state\":\"false\"}";
			}
			
			entityMap.put("cal_eng", cal_name/*.replaceAll("'\\}", "")*/);

           AccWageTaxCal accWageTaxCal = queryAccWageTaxCalByCode(entityMap);

	   		if (accWageTaxCal != null) {
	
	   			accWageTaxCalMapper.updateAccWageTaxCal(entityMap);
	   			
	   			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
	
	   		}
           
			accWageTaxCalMapper.addAccWageTaxCal(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addAccWageTaxCal\"}";

		}

	}
	
	/**
	 * @Description 
	 * 工资套合并日志<BR> 批量添加AccWageTaxCal
	 * @param  AccWageTaxCal entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAccWageTaxCal(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			accWageTaxCalMapper.addBatchAccWageTaxCal(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchAccWageTaxCal\"}";

		}
	}
	
	/**
	 * @Description 
	 * 工资套合并日志<BR> 查询AccWageTaxCal分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAccWageTaxCal(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage)entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<AccWageTaxCal> list = accWageTaxCalMapper.queryAccWageTaxCal(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AccWageTaxCal> list = accWageTaxCalMapper.queryAccWageTaxCal(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list,page.getTotal());
		}
		
	}
	
	/**
	 * @Description 
	 * 工资套合并日志<BR> 查询AccWageTaxCalByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AccWageTaxCal queryAccWageTaxCalByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return accWageTaxCalMapper.queryAccWageTaxCalByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 工资套合并日志<BR> 批量删除AccWageTaxCal
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAccWageTaxCal(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = accWageTaxCalMapper.deleteBatchAccWageTaxCal(entityList);
				
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccWageTaxCal\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 工资套合并日志<BR> 删除AccWageTaxCal
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteAccWageTaxCal(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			accWageTaxCalMapper.deleteAccWageTaxCal(entityMap);
				
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteAccWageTaxCal\"}";

		}
    }
	
	/**
	 * @Description 
	 * 工资套合并日志<BR> 更新AccWageTaxCal
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAccWageTaxCal(Map<String,Object> entityMap)throws DataAccessException{

		try {

			accWageTaxCalMapper.updateAccWageTaxCal(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccWageTaxCal\"}";

		}
	}
	
	/**
	 * @Description 
	 * 工资套合并日志<BR> 批量更新AccWageTaxCal
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAccWageTaxCal(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			accWageTaxCalMapper.updateBatchAccWageTaxCal(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccWageTaxCal\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 工资套合并日志<BR> 导入AccWageTaxCal
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importAccWageTaxCal(Map<String,Object> entityMap)throws DataAccessException{

		try {

			

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}

	@Override
	public String extendAccWageTaxCal(Map<String, Object> entityMap)
			throws DataAccessException {
		
		try {
			
			List<Map<String, Object>> list = accWageTaxCalMapper.queryAccWageTaxCalLastYear(entityMap);
			
			if(list.size()>0){
				
				accWageTaxCalMapper.addBatchAccWageTaxCal(list);
				
				return "{\"msg\":\"继承成功.\",\"state\":\"true\"}";
				
			}
			
			return "{\"msg\":\"没有可以继承的数据.\",\"state\":\"false\"}";
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);

			return "{\"error\":\"继承失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}

	@Override
	public String queryAccWageTaxCalById(Map<String, Object> entityMap)
			throws DataAccessException {
		
		return accWageTaxCalMapper.queryAccWageTaxCalByCode(entityMap).getCal_name();
	}


}
