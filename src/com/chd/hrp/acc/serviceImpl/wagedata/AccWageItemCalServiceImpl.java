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
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.hrp.acc.dao.AccWageItemsMapper;
import com.chd.hrp.acc.dao.report.SuperReportDesignMapper;
import com.chd.hrp.acc.dao.wage.AccWageMapper;
import com.chd.hrp.acc.dao.wagedata.AccWageItemCalMapper;
import com.chd.hrp.acc.dao.wagedata.AccWagePayMapper;
import com.chd.hrp.acc.entity.AccWageItemCal;
import com.chd.hrp.acc.entity.AccWageItems;
import com.chd.hrp.acc.entity.report.RepDefineEle;
import com.chd.hrp.acc.service.wagedata.AccWageItemCalService;
import com.github.pagehelper.PageInfo;
 
/**  
 * @Title. @Description. 工资项目计算公式<BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("accWageItemCalService")
public class AccWageItemCalServiceImpl implements AccWageItemCalService {

	private static Logger logger = Logger
			.getLogger(AccWageLogServiceImpl.class);

	@Resource(name = "accWageItemCalMapper")
	private final AccWageItemCalMapper accWageItemCalMapper = null;

	@Resource(name = "accWageItemsMapper")
	private final AccWageItemsMapper accWageItemsMapper = null;

	@Resource(name = "accWagePayMapper")
	private final AccWagePayMapper accWagePayMapper = null;

	@Resource(name = "accWageMapper")
	private final AccWageMapper accWageMapper = null;

	@Resource(name = "superReportDesignMapper")
	private final SuperReportDesignMapper superReportDesignMapper = null;

	/**
	 * @Description 工资项目计算公式<BR>
	 *              添加AccWageItemCal
	 * @param AccWageItemCal
	 *            entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addAccWageItemCal(Map<String, Object> entityMap)
			throws DataAccessException {

		List<String> list = new ArrayList<String>();
		
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

			AccWageItemCal accWageItemCal = accWageItemCalMapper
					.queryAccWageItemCalByCode(entityMap);

			if (accWageItemCal != null) {

				accWageItemCalMapper.updateAccWageItemCal(entityMap);

				return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
			}

			accWageItemCalMapper.addAccWageItemCal(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addAccWageItemCal\"}";

		}

	}

	/**
	 * @Description 工资项目计算公式<BR>
	 *              批量添加AccWageItemCal
	 * @param AccWageItemCal
	 *            paraMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatchAccWageItemCal(Map<String, Object> paraMap) throws DataAccessException {
		String year = SessionManager.getAcctYear();
		paraMap.put("year_month", Integer.valueOf(year) - 1);
		
		try {
			List<Map<String, Object>> calItem = accWageItemCalMapper.queryAccWageItemCalExtend(paraMap);
			if (calItem.size() > 0) {
				for(Map<String, Object> item : calItem){
					item.put("acc_year", year);
					item.put("cal_id", "");
				}
				
				accWageItemCalMapper.deleteBatchAccWageItemCal(calItem);
				accWageItemCalMapper.addBatchAccWageItemCal(calItem);
				return "{\"msg\":\"继承成功.\",\"state\":\"true\"}";
			}
			return "{\"msg\":\"无计算公式可以继承.\",\"state\":\"false\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("继承失败 数据库异常 请联系管理员! 错误编码 addBatchAccWageItemCal");
		}
	}

	/**
	 * @Description 工资项目计算公式<BR>
	 *              查询AccWageItemCal分页
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryAccWageItemCal(Map<String, Object> entityMap)
			throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AccWageItemCal> list = accWageItemCalMapper
					.queryAccWageItemCal(entityMap);

			return ChdJson.toJson(list);
		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(),
					sysPage.getPagesize());

			List<AccWageItemCal> list = accWageItemCalMapper
					.queryAccWageItemCal(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());
		}

	}

	/**
	 * @Description 工资项目计算公式<BR>
	 *              查询AccWageItemCalByCode
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public AccWageItemCal queryAccWageItemCalByCode(
			Map<String, Object> entityMap) throws DataAccessException {

		return accWageItemCalMapper.queryAccWageItemCalByCode(entityMap);

	}

	/**
	 * @Description 工资项目计算公式<BR>
	 *              批量删除AccWageItemCal
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchAccWageItemCal(List<Map<String, Object>> entityList)
			throws DataAccessException {

		try {

			accWageItemCalMapper.deleteBatchAccWageItemCal(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccWageItemCal\"}";

		}

	}

	/**
	 * @Description 工资项目计算公式<BR>
	 *              删除AccWageItemCal
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteAccWageItemCal(Map<String, Object> entityMap)
			throws DataAccessException {

		try {
			accWageItemCalMapper.deleteAccWageItemCal(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteAccWageItemCal\"}";

		}
	}

	/**
	 * @Description 工资项目计算公式<BR>
	 *              更新AccWageItemCal
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateAccWageItemCal(Map<String, Object> entityMap)
			throws DataAccessException {

		try {

			accWageItemCalMapper.updateAccWageItemCal(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccWageItemCal\"}";

		}
	}

	/**
	 * @Description 工资项目计算公式<BR>
	 *              批量更新AccWageItemCal
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatchAccWageItemCal(List<Map<String, Object>> entityList)
			throws DataAccessException {

		try {

			accWageItemCalMapper.updateBatchAccWageItemCal(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccWageItemCal\"}";

		}

	}

	/**
	 * @Description 工资项目计算公式<BR>
	 *              导入AccWageItemCal
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String importAccWageItemCal(Map<String, Object> entityMap)
			throws DataAccessException {

		try {

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}

	@Override
	public String queryAccWageItem(Map<String, Object> entityMap)
			throws DataAccessException {
		
		StringBuilder jsonResult = new StringBuilder();
		
		jsonResult.append("{Rows:[");
		
		jsonResult.append("{'id':'0','name':'工资项目'},");
		
		jsonResult.append("{'id':'1','name':'取值函数'},");
		
		List<AccWageItems> itemList = accWageItemsMapper.queryAccWageItems(entityMap);
		
		for (int i = 0; i < itemList.size(); i++) {
			
			AccWageItems accWageItems = itemList.get(i);
				
			jsonResult.append("{'id':'"+accWageItems.getItem_code()+"','pId':'0','name':'"+accWageItems.getItem_name()+"'},");
				
		}
		
		entityMap.put("mod_code", "0102");
		
		List<RepDefineEle> list = new ArrayList<RepDefineEle>();
		
		list=superReportDesignMapper.querySuperReportEleByMod(entityMap);
		
		for (int i = 0; i < list.size(); i++) {
			
			RepDefineEle rep= list.get(i);
				
			if(i!=list.size()-1){
				
				jsonResult.append("{'id':'"+rep.getEle_code()+"','pId':'1','name':'"+rep.getEle_name()+"'},");
				
			}else{
				
				jsonResult.append("{'id':'"+rep.getEle_code()+"','pId':'1','name':'"+rep.getEle_name()+"'}");
				
			}
				
		}
		
		jsonResult.append("]}");
	
		return jsonResult.toString();
	}

	public String queryAccWageItemCalList(Map<String, Object> entityMap)
			throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AccWageItemCal> list = accWageItemCalMapper
					.queryAccWageItemCalList(entityMap);

			return ChdJson.toJson(list);
		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(),
					sysPage.getPagesize());

			List<AccWageItemCal> list = accWageItemCalMapper
					.queryAccWageItemCalList(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());
		}

	}

	@Override
	public String queryAccWageItemCalById(Map<String, Object> entityMap)
			throws DataAccessException {

		return accWageItemCalMapper.queryAccWageItemCalByCode(entityMap)
				.getCal_name();
	}

	@Override
	public String queryAccWageItemTree(Map<String, Object> entityMap)
			throws DataAccessException {
		
		StringBuilder jsonResult = new StringBuilder();
		
		jsonResult.append("{Rows:[");
		
		jsonResult.append("{'id':'0','name':'工资项目'},");
		
		jsonResult.append("{'id':'1','name':'系统信息'},");
		
		List<AccWageItems> itemList = accWageItemsMapper.queryAccWageItems(entityMap);
		
		for (int i = 0; i < itemList.size(); i++) {
			
			AccWageItems accWageItems = itemList.get(i);
				
			jsonResult.append("{'id':'"+accWageItems.getItem_code()+"','pId':'0','name':'"+accWageItems.getItem_name()+"'},");
				
		}
				
		jsonResult.append("{'id':'emp_code','pId':'1','name':'职工编码'},");
		
		jsonResult.append("{'id':'emp_name','pId':'1','name':'职工名称'},");
		
		jsonResult.append("{'id':'dept_code','pId':'1','name':'部门编码'},");
		
		jsonResult.append("{'id':'dept_name','pId':'1','name':'部门名称'},");
		
		jsonResult.append("{'id':'kind_code','pId':'1','name':'职工分类编码'},");
		
		jsonResult.append("{'id':'kind_name','pId':'1','name':'职工分类名称'},");
		
		jsonResult.append("{'id':'note','pId':'1','name':'备注'}");
				
		jsonResult.append("]}");
	
		return jsonResult.toString();
	}
}
