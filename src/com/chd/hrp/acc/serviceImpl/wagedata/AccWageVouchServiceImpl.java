/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.serviceImpl.wagedata;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.NumberUtil;
import com.chd.base.util.UUIDLong;
import com.chd.hrp.acc.dao.AccLederMapper;
import com.chd.hrp.acc.dao.AccWageItemsMapper;
import com.chd.hrp.acc.dao.termend.AccTermendTemplateMapper;
import com.chd.hrp.acc.dao.vouch.SuperVouchMapper;
import com.chd.hrp.acc.dao.wagedata.AccWagePayMapper;
import com.chd.hrp.acc.dao.wagedata.AccWageVouchMapper;
import com.chd.hrp.acc.entity.AccWageItems;
import com.chd.hrp.acc.entity.AccWageVouch;
import com.chd.hrp.acc.service.vouch.SuperVouchService;
import com.chd.hrp.acc.service.wagedata.AccWageVouchService;
import com.github.pagehelper.PageInfo;
    
/**
* @Title. @Description.
* 工资转账<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("accWageVouchService")
public class AccWageVouchServiceImpl implements AccWageVouchService {

	private static Logger logger = Logger.getLogger(AccWageVouchServiceImpl.class);
	
	@Resource(name = "accWageVouchMapper")
	private final AccWageVouchMapper accWageVouchMapper = null;
	
	@Resource(name = "accWagePayMapper")
	private final AccWagePayMapper accWagePayMapper = null;
	
	@Resource(name = "accTermendTemplateMapper")
	private final AccTermendTemplateMapper accTermendTemplateMapper = null;
	
	@Resource(name = "superVouchService")
	private final SuperVouchService superVouchService = null;
	
	@Resource(name = "accWageItemsMapper")
	private final AccWageItemsMapper accWageItemsMapper = null;
	
	@Resource(name = "accLederMapper")
	private final AccLederMapper accLederMapper = null;
	
	@Resource(name = "superVouchMapper")
	private final SuperVouchMapper superVouchMapper = null;
	
	/**
	 * @Description 
	 * 工资转账<BR> 添加AccWageVouch
	 * @param AccWageVouch entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAccWageVouch(Map<String,Object> entityMap)throws DataAccessException{
		
		List<String> li = new ArrayList<String>();
		
		List<String> li_eng = new ArrayList<String>();
		
		AccWageVouch accWageVouch = queryAccWageVouchByCode(entityMap);

		if (accWageVouch != null) {

			return "{\"error\":\"编码：" + entityMap.get("scheme_code").toString() + "重复.\"}";

		}
		
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		
		List<Map<String, Object>> kindList = new ArrayList<Map<String,Object>>();
		
		JSONArray json = JSONArray.parseArray(entityMap.get("data").toString());
		
		Iterator it = json.iterator();
		
		StringBuffer msg= new StringBuffer();
		
		int index;
		
		int next_num=1;
		
		try {
			
				while (it.hasNext()){
				
					li= new ArrayList<String>();
					
					Map<String, Object> map = new HashMap<String, Object>();

					JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					
					map.put("group_id", entityMap.get("group_id"));
					
					map.put("hos_id", entityMap.get("hos_id"));

					map.put("copy_code", entityMap.get("copy_code"));

					map.put("acc_year", entityMap.get("acc_year"));

					map.put("scheme_code", entityMap.get("scheme_code"));

					map.put("subj_code", jsonObj.get("subj_code").toString());
					
					map.put("subj_dire", jsonObj.get("subj_dire").toString());
					
					if(jsonObj.containsKey("summary")==true&&null!= jsonObj.get("summary").toString() && !"".equals(jsonObj.get("summary").toString())){
						
						map.put("summary", jsonObj.get("summary").toString());
					}else{
						
						map.put("summary", "");
					}
					
					if("".equals(jsonObj.get("cal_name"))||null==jsonObj.get("cal_name")){
						
						map.put("cal_name", "");
						
						map.put("cal_eng", "");
						
					}else{
						
						map.put("cal_name", jsonObj.get("cal_name"));
						
						Pattern  p=  Pattern.compile("\\{.*?\\}");        
				        
						String cal_name = jsonObj.get("cal_name").toString();
						
						String select_cal = jsonObj.get("cal_name").toString();
						
						Matcher m = p.matcher(cal_name);
						
						while (m.find()) {
							
							li.add(m.group());
				           
				        }
						
					for (int j = 0; j < li.size(); j++) {
						
						String item_name = li.get(j).replaceAll("\\{", "").replaceAll("\\}", "");
						
						if(li.get(j).indexOf("','")>-1){
							
							String rep_item=li.get(j).split("','")[6].substring(0, 2);
							
							cal_name=cal_name.replaceFirst(rep_item, rep_item+"/");

							select_cal=select_cal.replaceFirst(rep_item, rep_item+"/");
							
						}else{
							
							entityMap.put("item_name", li.get(j).replaceAll("\\{","").replaceAll("\\}", ""));
							
							AccWageItems itemList = accWageItemsMapper.queryAccWageColumnByCal(entityMap);
							
							if(itemList!=null){
								
								cal_name=cal_name.replaceFirst(itemList.getItem_name(), itemList.getColumn_item().toString());

								select_cal=select_cal.replaceFirst(itemList.getItem_name(), "to_number("+itemList.getColumn_item().toString()+")");
								
							}else{
								
								msg.append("【"+item_name+"】,");
							}
							
						}
						
						if(li_eng.size()>0&&li_eng.get(0).indexOf("'")<0){
							
							li_eng.remove(0);
						}
						
					}

					if("".equals(msg)){
						
						return "{\"msg\":\"工资项目"+msg.substring(0, msg.length()-1)+".\",\"state\":\"false\"}";
					}
					
					Calendar calendar = Calendar.getInstance();
					
					String year = String.valueOf(calendar.get(Calendar.YEAR));
					
					String month = String.valueOf(calendar.get(Calendar.MONTH)-1);
					
					String day = DateUtil.getMaxMonthDate(year, month);
					
					 cal_name = cal_name.replace("如果完", "  end  ").replace("如果", "  case when  ").replace("否则", "  else  ").replace("则", "  then  ").replace("或者", "  or  ").replace("并且", "  and  ").replace("上月自然天数",day ).replace("取整","  round").replace("是","  1").replace("否","  0");
						
			         cal_name = cal_name.replace("职工编码", "  hed.emp_code  ").replace("职工名称", "  hed.emp_name  ").replace("部门编码", "  aea.dept_code  ").replace("部门名称", "  aea.dept_name  ").replace("职工分类编码", "  awp.kind_code  ").replace("职工分类名称", "  awp.kind_name  ").replace("是否停用"," aea.is_stop ").replace("性别"," aea.sex").replace("是否发放工资"," aea.is_pay").replace("岗位编码"," aea.station_code");
			            
			         cal_name = cal_name.replace("岗位名称", "  aea.station_name  ").replace("职务编码", "  aea.duty_code  ").replace("职务名称", "  aea.duty_name  ").replace("身份证号", "  aea.id_number  ").replace("国籍名称", "  aea.countries_code  ").replace("备注"," aea.note ");
					 
			         cal_name = cal_name.replace("取工资数据", "  RF_ACC_WAGEITEMSRLY");
		           
		          /* select_cal = select_cal.replaceAll("如果完", "  end  ")
							.replaceAll("如果", "  case when  ")
							.replaceAll("否则", "  else  ").replaceAll("则", "  then  ")
							.replaceAll("或者", "  or  ").replaceAll("并且", "  and  ")
							.replaceAll("上月自然天数", day).replaceAll("取整", " round")
							.replaceAll("是", " 1").replaceAll("否", " 0").replaceAll("\\{\\{'", "")
							.replaceAll("'\\)\\}", " '\\)").replaceAll("备注"," aea.note ");*/
		           
		           select_cal = select_cal.replaceAll("如果完", "  end  ").replaceAll("如果", "  case when  ").replaceAll("否则", "  else  ").replaceAll("则", "  then  ").replaceAll("或者", "  or  ").replaceAll("并且", "  and  ").replaceAll("上月自然天数",day ).replaceAll("取整","  round").replaceAll("是","  1").replaceAll("否","  0");
					
		           select_cal = select_cal.replaceAll("职工编码", "  hed.emp_code  ").replaceAll("职工名称", "  hed.emp_name  ").replaceAll("部门编码", "  aea.dept_code  ").replaceAll("部门名称", "  aea.dept_name  ").replaceAll("职工分类编码", "  awp.kind_code  ").replaceAll("职工分类名称", "  awp.kind_name  ").replaceAll("是否停用"," aea.is_stop ").replaceAll("性别"," aea.sex").replaceAll("是否发放工资"," aea.is_pay").replaceAll("岗位编码"," aea.station_code");
		            
		           select_cal = select_cal.replaceAll("岗位名称", "  aea.station_name  ").replaceAll("职务编码", "  aea.duty_code  ").replaceAll("职务名称", "  aea.duty_name  ").replaceAll("身份证号", "  aea.id_number  ").replaceAll("国籍名称", "  aea.countries_code  ").replaceAll("备注"," aea.note ");
		           
		           select_cal = select_cal.replaceAll("取工资数据", "  RF_ACC_WAGEITEMSRLY");
		        		   
		           try {
						
						entityMap.put("select_cal", select_cal.replaceAll("\\{", "").replaceAll("\\}", ""));
						
						accWagePayMapper.queryAccWagePayByItemCal(entityMap);
			
					} catch (Exception e) {
						
						logger.error(e.getMessage(), e);
						
						return "{\"msg\":\"公式不合法.\",\"state\":\"false\"}";
					}
		           
				    map.put("cal_eng", cal_name.replaceAll("/",""));
			    
				}
					
				//String [] emp_kind = jsonObj.get("kind_code").toString().split(";");
				
				//for (int j = 0; j < emp_kind.length; j++) {
					
					Map<String, Object> kindMap = new HashMap<String, Object>();
					
					kindMap.put("group_id", entityMap.get("group_id"));
					
					kindMap.put("hos_id", entityMap.get("hos_id"));

					kindMap.put("copy_code", entityMap.get("copy_code"));

					kindMap.put("acc_year", entityMap.get("acc_year"));

					kindMap.put("scheme_code", entityMap.get("scheme_code"));
					
					kindMap.put("vouch_no", next_num);
					
					if("".equals(jsonObj.get("kind_code").toString())){
						
						kindMap.put("kind_code","0");
						
					}else{
						
						kindMap.put("kind_code",jsonObj.get("kind_code").toString().replaceAll(";",","));
						
					}
					
					kindMap.put("subj_code",jsonObj.get("subj_code").toString());
					
					kindList.add(kindMap);
					
				//}
				
				if(!"".equals(jsonObj.get("is_balance"))&&null!=jsonObj.get("is_balance")){
					
					map.put("is_balance", jsonObj.get("is_balance").toString());
					
				}else{
					
					map.put("is_balance", "0");
					
				}
				
				if(!"".equals(jsonObj.get("is_neg"))&&null!=jsonObj.get("is_neg")){
					
					map.put("is_neg", jsonObj.get("is_neg").toString());
					
				}else{
					
					map.put("is_neg", "0");
					
				}
				
				if(!"".equals(jsonObj.get("is_assign"))&&null!=jsonObj.get("is_assign")){
					
					map.put("is_assign", jsonObj.get("is_assign").toString());
					
				}else{
					
					map.put("is_assign", "0");
					
				}
				
				map.put("vouch_no", next_num);
				
				list.add(map);
				
				next_num+=1;

			}
			
			if(kindList.size()>0){
				
				accWageVouchMapper.deleteAccWageVouchKind(entityMap);
				
				accWageVouchMapper.addBatchAccWageVouchKind(kindList);
				
			}
			
			if(list.size()>0){
				
				accWageVouchMapper.deleteAccWageVouchSubj(entityMap);
				
				accWageVouchMapper.addBatchAccWageVouchSubj(list);
				
				accWageVouchMapper.addAccWageVouch(entityMap);
				
				return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
				
			}
			
			return "{\"msg\":\"添加信息不全,添加失败.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");

		}

	}
	
	/**
	 * @Description 
	 * 工资转账<BR> 批量添加AccWageVouch
	 * @param  AccWageVouch entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAccWageVouch(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			accWageVouchMapper.addBatchAccWageVouch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchAccWageVouch\"}";

		}
	}
	
	/**
	 * @Description 
	 * 工资转账<BR> 查询AccWageVouch分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAccWageVouch(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage)entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<AccWageVouch> list = accWageVouchMapper.queryAccWageVouch(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AccWageVouch> list = accWageVouchMapper.queryAccWageVouch(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list,page.getTotal());
		}
		
	}
	
	/**
	 * @Description 
	 * 工资转账<BR> 查询AccWageVouchByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AccWageVouch queryAccWageVouchByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return accWageVouchMapper.queryAccWageVouchByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 工资转账<BR> 批量删除AccWageVouch
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAccWageVouch(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
				accWageVouchMapper.deleteAccWageVouchSubj(entityList.get(0));
				
				accWageVouchMapper.deleteAccWageVouchKind(entityList.get(0));
			
				accWageVouchMapper.deleteBatchAccWageVouch(entityList);
				
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");

		}
		
	}
	
	/**
	 * @Description 
	 * 工资转账<BR> 删除AccWageVouch
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteAccWageVouch(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			accWageVouchMapper.deleteAccWageVouch(entityMap);
				
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteAccWageVouch\"}";

		}
    }
	
	/**
	 * @Description 
	 * 工资转账<BR> 更新AccWageVouch
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAccWageVouch(Map<String, Object> entityMap) throws DataAccessException {
		List<String> li;
		List<String> li_eng = new ArrayList<String>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> kindList = new ArrayList<Map<String, Object>>();
		JSONArray json = JSONArray.parseArray(entityMap.get("data").toString());
		Iterator it = json.iterator();
		StringBuffer msg = new StringBuffer("");
		int next_num = 1;
		int k = 0;// 记录数据行数
		Map<String, String> sysInfo = new HashMap<String, String>();// 公式中的系统信息
		sysInfo.put("职工编码", "职工编码");
		sysInfo.put("职工名称", "职工名称");
		sysInfo.put("部门编码", "部门编码");
		sysInfo.put("部门名称", "部门名称");
		sysInfo.put("职工分类编码", "职工分类编码");
		sysInfo.put("职工分类名称", "职工分类名称");
		sysInfo.put("备注", "备注");
		try {
			// while最外层 start
			while (it.hasNext()) {
				k++;
				li = new ArrayList<String>();
				Map<String, Object> map = new HashMap<String, Object>();
				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
				map.put("group_id", entityMap.get("group_id"));
				map.put("hos_id", entityMap.get("hos_id"));
				map.put("copy_code", entityMap.get("copy_code"));
				map.put("acc_year", entityMap.get("acc_year"));
				map.put("scheme_code", entityMap.get("scheme_code"));
				map.put("subj_code", jsonObj.get("subj_code").toString());
				map.put("subj_dire", jsonObj.get("subj_dire").toString());

				if (jsonObj.containsKey("summary") == true 
						&& null != jsonObj.get("summary").toString()
						&& !"".equals(jsonObj.get("summary").toString())) {
					
					map.put("summary", jsonObj.get("summary").toString());
				} else {
					map.put("summary", "");
				}

				if (jsonObj.containsKey("cal_eng") == true 
						&& null != jsonObj.get("cal_eng").toString()
						&& !"".equals(jsonObj.get("cal_eng").toString())) {
					
					map.put("cal_eng", jsonObj.get("cal_eng").toString());
				}

				if ("".equals(jsonObj.get("cal_name")) || null == jsonObj.get("cal_name")) {
					map.put("cal_name", "");
					map.put("cal_eng", "");
				} else {
					map.put("cal_name", jsonObj.get("cal_name"));
					Pattern p = Pattern.compile("\\{.*?\\}");
					String cal_name = jsonObj.get("cal_name").toString();
					String select_cal = jsonObj.get("cal_name").toString();
					Matcher m = p.matcher(cal_name);
					while (m.find()) {
						li.add(m.group());
					}

					if (jsonObj.containsKey("cal_eng") == true 
							&& null != jsonObj.get("cal_eng").toString()
							&& !"".equals(jsonObj.get("cal_eng").toString())) {
						
						String cal_eng = jsonObj.get("cal_eng").toString();
						Matcher ms = p.matcher(cal_eng);
						while (ms.find()) {
							li_eng.add(ms.group());
						}
					}

					for (int j = 0; j < li.size(); j++) {
						String item_name = li.get(j).replaceAll("\\{", "").replaceAll("\\}", "");
						if (li.get(j).indexOf("','") < 0) {
							entityMap.put("item_name", item_name);
							AccWageItems itemList = accWageItemsMapper.queryAccWageColumnByCal(entityMap);
							if (itemList != null) {
								cal_name = cal_name
									.replaceFirst(itemList.getItem_name(), itemList.getColumn_item().toString());
								select_cal = select_cal
									.replaceFirst(itemList.getItem_name(), itemList.getColumn_item().toString());
							} else if(sysInfo.get(item_name) == null){// 是系统信息就不提示这是个工资项
								msg.append("<br/>【" + item_name + "】");
							}
						} else {
							String rep_item = li.get(j).split("','")[6].substring(0, 2);
							cal_name = cal_name.replaceFirst(rep_item, rep_item + "/");
							select_cal = select_cal.replaceFirst(rep_item, rep_item + "/");
						}

						if (li_eng.size() > 0 && li_eng.get(0).indexOf("'") < 0) {
							li_eng.remove(0);
						}
					}
					
					if(!msg.toString().equals("")){
						return "{\"warn\":\"第" + k + "行数据公式，以下工资项不存在：" + msg.toString() + "\",\"state\":\"false\"}";
					}
					
					Calendar calendar = Calendar.getInstance();
					String year = String.valueOf(calendar.get(Calendar.YEAR));
					String month = String.valueOf(calendar.get(Calendar.MONTH) - 1);
					String day = DateUtil.getMaxMonthDate(year, month);
					try {
						select_cal = select_cal.replaceAll("职工编码", "  hed.emp_code  ")
							.replaceAll("职工名称", "  hed.emp_name  ").replaceAll("部门编码", "  aea.dept_code  ")
							.replaceAll("部门名称", "  aea.dept_name  ").replaceAll("职工分类编码", "  awp.kind_code  ")
							.replaceAll("职工分类名称", "  awp.kind_name  ").replaceAll("是否停用", " aea.is_stop ")
							.replaceAll("性别", " aea.sex").replaceAll("是否发放工资", " aea.is_pay")
							.replaceAll("岗位编码", " aea.station_code");
						
						select_cal = select_cal.replaceAll("岗位名称", "  aea.station_name  ")
							.replaceAll("职务编码", "  aea.duty_code  ").replaceAll("职务名称", "  aea.duty_name  ")
							.replaceAll("身份证号", "  aea.id_number  ").replaceAll("国籍名称", "  aea.countries_code  ")
							.replaceAll("备注", " aea.note ");
						
						select_cal = select_cal.replaceAll("取工资数据", "  RF_ACC_WAGEITEMSRLY");
						
						select_cal = select_cal.replace("如果完", "  end  ").replaceAll("如果", "  case when  ")
							.replaceAll("备注", "  aea.note  ").replaceAll("否则", "  else  ")
							.replaceAll("则", "  then  ").replaceAll("或者", "  or  ").replaceAll("并且", "  and  ")
							.replaceAll("上月自然天数", day).replaceAll("取整", " round").replaceAll("是", " 1")
							.replaceAll("否", " 0").replaceAll("\\{\\{'", "").replaceAll("'\\)\\}", " '\\)");
						
						entityMap.put("select_cal", select_cal.replaceAll("\\{", "").replaceAll("\\}", ""));
						accWagePayMapper.queryAccWagePayByItemCal(entityMap);
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
						return "{\"error\":\"第" + k + "行数据公式不合法.\",\"state\":\"false\"}";
					}
					
					cal_name = cal_name.replace("如果完", "  end  ").replace("如果", "  case when  ")
						.replace("否则", "  else  ").replace("则", "  then  ").replace("或者", "  or  ")
						.replace("并且", "  and  ").replace("上月自然天数", day).replace("取整", "  round")
						.replace("是", "  1").replace("否", "  0");
					
					cal_name = cal_name.replace("职工编码", "  hed.emp_code  ").replace("职工名称", "  hed.emp_name  ")
						.replace("部门编码", "  aea.dept_code  ").replace("部门名称", "  aea.dept_name  ")
						.replace("职工分类编码", "  awp.kind_code  ").replace("职工分类名称", "  awp.kind_name  ")
						.replace("是否停用", " aea.is_stop ").replace("性别", " aea.sex").replace("是否发放工资", " aea.is_pay")
						.replace("岗位编码", " aea.station_code");
					
					cal_name = cal_name.replace("岗位名称", "  aea.station_name  ").replace("职务编码", "  aea.duty_code  ")
						.replace("职务名称", "  aea.duty_name  ").replace("身份证号", "  aea.id_number  ")
						.replace("国籍名称", "  aea.countries_code  ").replace("备注", " aea.note ");
					
					cal_name = cal_name.replace("取工资数据", "  RF_ACC_WAGEITEMSRLY");
					
					map.put("cal_eng", cal_name.replaceAll("/", ""));
				}
				/*
				 * String [] emp_kind =
				 * jsonObj.get("kind_code").toString().split(";");
				 * 
				 * for (int j = 0; j < emp_kind.length; j++) {
				 */
				Map<String, Object> kindMap = new HashMap<String, Object>();
				kindMap.put("group_id", entityMap.get("group_id"));
				kindMap.put("hos_id", entityMap.get("hos_id"));
				kindMap.put("copy_code", entityMap.get("copy_code"));
				kindMap.put("acc_year", entityMap.get("acc_year"));
				kindMap.put("scheme_code", entityMap.get("scheme_code"));
				if (null != jsonObj.get("kind_code").toString() && !"".equals(jsonObj.get("kind_code").toString())) {
					kindMap.put("kind_code", jsonObj.get("kind_code").toString());
				} else {
					kindMap.put("kind_code", "0");
				}

				kindMap.put("vouch_no", next_num);
				kindMap.put("subj_code", jsonObj.get("subj_code").toString());
				kindList.add(kindMap);
				// }

				if (!"".equals(jsonObj.get("is_balance")) && null != jsonObj.get("is_balance")) {
					map.put("is_balance", jsonObj.get("is_balance").toString());
				} else {
					map.put("is_balance", "0");
				}

				if (!"".equals(jsonObj.get("is_neg")) && null != jsonObj.get("is_neg")) {
					map.put("is_neg", jsonObj.get("is_neg").toString());
				} else {
					map.put("is_neg", "0");
				}

				if (!"".equals(jsonObj.get("is_assign")) && null != jsonObj.get("is_assign")) {
					map.put("is_assign", jsonObj.get("is_assign").toString());
				} else {
					map.put("is_assign", "0");
				}

				map.put("vouch_no", next_num);
				list.add(map);
				next_num += 1;
			}// while最外层 end

			if (kindList.size() > 0) {
				accWageVouchMapper.deleteAccWageVouchKind(entityMap);
				accWageVouchMapper.addBatchAccWageVouchKind(kindList);
			}

			accWageVouchMapper.deleteAccWageVouchSubj(entityMap);
			accWageVouchMapper.addBatchAccWageVouchSubj(list);
			accWageVouchMapper.updateAccWageVouch(entityMap);
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败\"}");
		}
	}
	
	/**
	 * @Description 
	 * 工资转账<BR> 批量更新AccWageVouch
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAccWageVouch(List<Map<String, Object>> entityMap)throws DataAccessException{
		
		try {
			
			accWageVouchMapper.updateBatchAccWageVouch(entityMap);
			
			return "{\"msg\":\"修改成功.\",\"state\":\"false\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateBatchAccWageVouch\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 工资转账<BR> 导入AccWageVouch
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importAccWageVouch(Map<String,Object> entityMap)throws DataAccessException{

		try {

			

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}

	@Override
	public AccWageVouch queryAccWageVouchBySchemeCode(
			Map<String, Object> entityMap) throws DataAccessException {
		
		return accWageVouchMapper.queryAccWageVouchBySchemeCode(entityMap);
	}

	@Override
	public String queryAccWageVouchSubj(Map<String, Object> entityMap)
			throws DataAccessException {

		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage)entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<AccWageVouch> list = accWageVouchMapper.queryAccWageVouchSubj(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AccWageVouch> list = accWageVouchMapper.queryAccWageVouchSubj(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list,page.getTotal());
		}
		
	}

	@Override
	public String addTransferAccWageVouch(List<Map<String, Object>> entityMap) throws DataAccessException {
		
		Map<String, Object> vouchMap = new HashMap<String, Object>();//存放凭证分录信息
		List<Map<String, Object>> detailList = new ArrayList<Map<String,Object>>();//存放凭证主表信息
		List<Map<String, Object>> vouchCheckList = new ArrayList<Map<String,Object>>();//存放辅助核算信息
		Map<String, Object> logMap = new HashMap<String, Object>();//存放日志信息
		
		
		Double detailMoney = 0.0;
		String template_type_code = "Z010";//Z010:业务类型-工资转账
		boolean have_dept = false;//判断是否包含部门辅助核算
		boolean have_emp = false;//判断是否包含职工辅助核算
		
		try {
			
			Map<String, Object> mapVo = new HashMap<String, Object>();
			
			if(entityMap.size()>0){
				mapVo=entityMap.get(0);
			}
			
			//1.获取凭证模板信息
			AccWageVouch accWageVouch = accWageVouchMapper.queryAccWageVouchByCode(mapVo);
			if(accWageVouch.getScheme_code() == null || "".equals(accWageVouch.getScheme_code())){
				return "{\"warn\":\"方案"+accWageVouch.getScheme_name()+"还不存在，请先添加方案！\"}";
			}
			
			
			//2、如果本期间已生成凭证则不允许生成凭证
			mapVo.put("template_type_code", "Z010");
			mapVo.put("template_id", mapVo.get("scheme_code"));
			
			int flag = accTermendTemplateMapper.existsAccVouchByTemplate(mapVo);
			if(flag > 0){
				return "{\"error\":\"本期间已生成凭证，不能重复生成!\"}";
			}
			
			
			//3、组装凭证主表信息
			/*************************组装凭证主表信息*******************************/
			String group_id = SessionManager.getGroupId();
			String hos_id = SessionManager.getHosId();
			String copy_code = SessionManager.getCopyCode();
			String vouch_id = UUIDLong.absStringUUID();//凭证id
			Date date = new Date();
			
			vouchMap.put("group_id", accWageVouch.getGroup_id());
			vouchMap.put("hos_id", accWageVouch.getHos_id()); 
			vouchMap.put("copy_code", accWageVouch.getCopy_code());
			vouchMap.put("vouch_id", vouch_id);
			vouchMap.put("acc_year",  mapVo.get("acc_year"));
			vouchMap.put("acc_month", mapVo.get("acc_month"));
			vouchMap.put("vouch_date", accTermendTemplateMapper.queryAccVouchDateByYearMonth(vouchMap));  //凭证日期
			vouchMap.put("vouch_type_code", accWageVouch.getVouch_type_code());  //凭证类型
			vouchMap.put("vouch_att_num", 0);
			vouchMap.put("busi_type_code", template_type_code);  //业务类型
			vouchMap.put("create_date", date);
			
			/****************************组装凭证分录信息***************************************/
			int vouch_row = 0;//分录行号
			
			List<AccWageVouch> vouch_subj= accWageVouchMapper.queryAccWageVouchSubjByCode(mapVo);
			
			if(vouch_subj.size()>0){
				
				for (int i=0;i<vouch_subj.size(); i++) {
					
					vouch_row++;
					
					Map<String, Object> qMap= new HashMap<String, Object>();

					qMap.put("group_id", mapVo.get("group_id"));
					
					qMap.put("hos_id", mapVo.get("hos_id"));
					
					qMap.put("copy_code", mapVo.get("copy_code"));
					
					qMap.put("wage_code", mapVo.get("wage_code"));
					
					qMap.put("acc_year", mapVo.get("acc_year"));
					
					qMap.put("acc_month", mapVo.get("acc_month"));
					
					qMap.put("scheme_code", mapVo.get("scheme_code"));
					
					Double sumMoney = 0.0;
					//String cal_eng="";
					StringBuffer sbf = new StringBuffer();
					AccWageVouch awv= vouch_subj.get(i);
					qMap.put("subj_code", awv.getSubj_code());
					if(null != awv.getIs_assign()&&awv.getIs_assign()==0){//是否指定科目
						if(!"".equals(awv.getOut_code())&& null != awv.getOut_code()&&awv.getSubj_code().startsWith("5")){//支出性质
							qMap.put("out_code", awv.getOut_code());
						}
					}
					
					
					Map<String, Object> detailDebitMap = new HashMap<String, Object>();
					detailDebitMap.put("group_id", group_id);
					detailDebitMap.put("hos_id", hos_id);
					detailDebitMap.put("copy_code",copy_code);
					detailDebitMap.put("vouch_id", vouch_id);
					detailDebitMap.put("vouch_row",vouch_row);
					detailDebitMap.put("subj_code", awv.getSubj_code());
					detailDebitMap.put("summary", awv.getSummary());
					
					for (int j = 0; j < awv.getKind_code().split(";").length; j++) {//职工分类
						if(!"".equals(awv.getKind_code())&&!"0".equals(awv.getKind_code())){
							sbf.append(awv.getKind_code().split(";")[j]+",");
						}
					}
					
					
					if(sbf.length()>0){
						qMap.put("kind_code", sbf.substring(0, sbf.toString().length()-1));
					}
					
					/****************************组装借方辅助核算信息***************************************/
					//挂辅助核算并且未取消借贷平
					if(awv.getIs_check() != null && awv.getIs_check() == 1 
						&& awv.getIs_balance() != null && awv.getIs_balance() == 0){
						
						StringBuffer sql = new StringBuffer();
						StringBuffer groupSql = new StringBuffer();
						StringBuffer orderSql = new StringBuffer();
						StringBuffer tableSql = new StringBuffer();
						Map<String, Object> columnMap= accLederMapper.queryAccCheckItemList(qMap);
						
						if(!"".equals(columnMap.get("COLUMN1_ID"))&&null!=columnMap.get("COLUMN1_ID")&&columnMap.get("COLUMN1_ID").toString().indexOf("DEPT")>-1){
							
							sql.append(" awp."+columnMap.get("COLUMN1_ID")+",");
							sql.append("awp."+columnMap.get("COLUMN1_NO")+",");
							sql.append("a."+columnMap.get("COLUMN1_CODE")+",");
							sql.append("a."+columnMap.get("COLUMN1_NAME"));
							
							tableSql.append(" left join "+columnMap.get("CHECK1_TABLE")+" a");
							tableSql.append(" on awp."+columnMap.get("COLUMN1_ID")+"=a."+columnMap.get("COLUMN1_ID")+" and awp."+columnMap.get("COLUMN1_NO")+" = a."+columnMap.get("COLUMN1_NO"));
							
							groupSql.append(",awp."+columnMap.get("COLUMN1_ID")+",awp."+columnMap.get("COLUMN1_NO"));
							groupSql.append(",a."+columnMap.get("COLUMN1_CODE")+",a."+columnMap.get("COLUMN1_NAME"));
							orderSql.append("  a."+columnMap.get("COLUMN1_CODE")+" asc");
							
						}
						
						if(!"".equals(columnMap.get("COLUMN2_ID"))&&null!=columnMap.get("COLUMN2_ID")&&columnMap.get("COLUMN2_ID").toString().indexOf("EMP")>-1){
							
							sql.append("select awp."+columnMap.get("COLUMN2_ID")+",");
							sql.append("awp."+columnMap.get("COLUMN2_NO")+",");
							sql.append("a."+columnMap.get("COLUMN2_CODE")+",");
							sql.append("a."+columnMap.get("COLUMN2_NAME"));
							
							tableSql.append(" left join "+columnMap.get("CHECK2_TABLE")+" a");
							tableSql.append(" on awp."+columnMap.get("COLUMN2_ID")+"=a."+columnMap.get("COLUMN2_ID")+" and awp."+columnMap.get("COLUMN2_NO")+" = a."+columnMap.get("COLUMN2_NO"));
							
							groupSql.append(", awp."+columnMap.get("COLUMN2_ID")+",awp."+columnMap.get("COLUMN2_NO"));
							groupSql.append(",a."+columnMap.get("COLUMN2_CODE")+",a."+columnMap.get("COLUMN2_NAME"));
							
							orderSql.append(" a."+columnMap.get("COLUMN2_CODE")+" asc");
						}
						
						
						
						if(sql.length()>0&&tableSql.length()>0&&groupSql.length()>0){
							
							qMap.put("sql", sql);
							qMap.put("tableSql", tableSql);
							qMap.put("orderSql", orderSql/*.substring(1, orderSql.length())*/);
							qMap.put("groupSql", groupSql.substring(1, groupSql.length()));
							
							//根据职工分类编码和工资套编码查询职工信息
							List<Map<String, Object>> awp = accWageVouchMapper.queryAccEmpId(qMap);
							
							//根据会计科目ID查询该科目所挂的辅助核算项
							AccWageVouch aw = accWageVouchMapper.queryAccCheckItem(qMap);
							
							
							int check_row = 0 ; //辅助核算id
							for (Map<String, Object> map : awp) {
								
								check_row++ ;
								Map<String, Object> checkMap = new HashMap<String, Object>();
								checkMap.put("group_id", group_id);
								checkMap.put("hos_id", hos_id);
								checkMap.put("copy_code",copy_code);
								checkMap.put("vouch_id", vouch_id);
								checkMap.put("vouch_row", vouch_row);
								checkMap.put("vouch_check_id", check_row);
								checkMap.put("debit_w", 0);
								checkMap.put("credit_w", 0);
								checkMap.put("subj_code", awv.getSubj_code());  //科目编码
								checkMap.put("summary", awv.getSummary());
								
								if(aw!= null){
									if(aw.getColumn_check().indexOf("CHECK2")>-1){
										checkMap.put("check2_id",  map.get("emp_id"));  //职工ID
										checkMap.put("check2_no",map.get("emp_no"));
										if(!have_emp){
											have_emp = true;
										}
									}else if(aw.getColumn_check().indexOf("CHECK1")>-1){
										checkMap.put("check1_id",  map.get("dept_id"));  //科室ID
										checkMap.put("check1_no", map.get("dept_no"));
										
										if(!have_dept){
											have_dept = true;
										}
									}
								}
								
								
								Map<String, Object> moneyMap = new HashMap<String, Object>();
								
								moneyMap.put("group_id", mapVo.get("group_id"));   moneyMap.put("hos_id", mapVo.get("hos_id"));
								moneyMap.put("copy_code", mapVo.get("copy_code"));   moneyMap.put("wage_code", mapVo.get("wage_code"));
								moneyMap.put("kind_code", mapVo.get("kind_code"));   moneyMap.put("acc_year", mapVo.get("acc_year"));
								moneyMap.put("acc_month", mapVo.get("acc_month"));
								
								if(!"".equals(map.get("emp_id"))&& null != map.get("emp_id")){
									moneyMap.put("emp_id", map.get("emp_id"));
									moneyMap.put("emp_no", map.get("emp_no"));
								}
								
								
								if(!"".equals(map.get("dept_id"))&& null != map.get("dept_id")){
									moneyMap.put("dept_id", map.get("dept_id"));
									moneyMap.put("dept_no", map.get("dept_no"));
								}
									
								
								if(null!=awv.getCal_eng()&&!"".equals(awv.getCal_eng())){//存在转账公式
									
									String cal=awv.getCal_eng().replaceAll("\\{","nvl(").replaceAll("\\}", ",0)").replaceAll("本集团", SessionManager.getGroupId());
									
									cal=cal.replaceAll("本医院", SessionManager.getHosId()).replaceAll("本账套", SessionManager.getCopyCode());
									cal=cal.replaceAll("本期间",mapVo.get("acc_month").toString()).replaceAll("本年度", accWageVouch.getAcc_year()).replaceAll("上一年", Integer.parseInt(accWageVouch.getAcc_year().toString())-1+"");
									
									AccWageVouch avc = new AccWageVouch();
									Double wage_money=0.00;
									
									if(moneyMap.containsKey("dept_id")&&null!=moneyMap.get("dept_id")){
										if(qMap.containsKey("out_code")){
											moneyMap.put("out_code", qMap.get("out_code"));
										}
										
										cal=cal.replaceAll("'本职工'","awp.emp_id");
										moneyMap.put("sql","sum("+cal+")");
										
										if(qMap.containsKey("kind_code")&&!"0".equals(qMap.get("kind_code"))){
											moneyMap.put("kind_code",qMap.get("kind_code"));
										}
											
										avc = accWageVouchMapper.queryAccVouchMoney(moneyMap);	
										
										if(avc!=null){
											wage_money+=avc.getItem_money();
										}
												
									}else if(moneyMap.containsKey("emp_id")&&null!=moneyMap.get("emp_id")){
										cal=cal.replaceAll("本分类",sbf.toString()).replaceAll("本职工",moneyMap.get("emp_id").toString());
									}else{
										cal=cal.replaceAll("本分类",sbf.toString()).replaceAll("本部门","0").replaceAll("本职工","0");
									}
									
									sumMoney+=wage_money;
									
									if("0".equals(awv.getIs_neg().toString().trim())){//非负数
										if("0".equals(awv.getSubj_dire().toString())){
											checkMap.put("credit", 0);
											checkMap.put("debit", wage_money);
											detailDebitMap.put("credit", 0);
											detailDebitMap.put("debit", sumMoney);
											detailMoney+=NumberUtil.numberToRound(wage_money);
										}else{
											checkMap.put("debit", 0);
											checkMap.put("credit", wage_money);
											detailDebitMap.put("debit", 0);
											detailDebitMap.put("credit", sumMoney);
											detailMoney-=NumberUtil.numberToRound(wage_money);
										}
									}else {
										if("0".equals(awv.getSubj_dire().toString())){
											checkMap.put("credit", 0);
											checkMap.put("debit", 0-wage_money);
											detailDebitMap.put("credit", 0);
											detailDebitMap.put("debit", 0-sumMoney);
											detailMoney+=NumberUtil.numberToRound(0-wage_money);
										}else{
											checkMap.put("debit", 0);
											checkMap.put("credit", 0-wage_money);
											detailDebitMap.put("debit", 0);
											detailDebitMap.put("credit", 0-sumMoney);
											detailMoney-=NumberUtil.numberToRound(0-wage_money);
										}
									}
									
									if(null!=avc&&avc.getItem_money() != 0){
										vouchCheckList.add(checkMap);
									}
								}else{
									detailDebitMap.put("debit", 0);
									detailDebitMap.put("credit", 0);
								}
							}
							
							if(null!=sumMoney&&sumMoney != 0){
								detailList.add(detailDebitMap);
							}
							
						}else{
							
							AccWageVouch avm = new AccWageVouch();
							Double wage_money=0.00;
							
							if(null!=awv.getCal_eng()&& !"".equals(awv.getCal_eng())){
								
								String cal=awv.getCal_eng().replaceAll("\\{","nvl(").replaceAll("\\}", ",0)").replaceAll("本集团", SessionManager.getGroupId());
								cal=cal.replaceAll("本医院", SessionManager.getHosId()).replaceAll("本账套", SessionManager.getCopyCode());
								cal=cal.replaceAll("本分类",sbf.toString()).replaceAll("本部门","0");
								cal=cal.replaceAll("本期间",mapVo.get("acc_month").toString()).replaceAll("本年度", accWageVouch.getAcc_year()).replaceAll("上一年", Integer.parseInt(accWageVouch.getAcc_year().toString())-1+"");
								qMap.remove("dept_id");
								cal=cal.replaceAll("'本职工'","awp.emp_id");
								//mapVo.put("emp_id", list.get(j).getEmp_id());
								qMap.put("sql","sum("+cal+")");
								
								if(qMap.containsKey("kind_code")&&!"0".equals(qMap.get("kind_code"))){
									qMap.put("kind_code",qMap.get("kind_code"));
								}
									
								avm = accWageVouchMapper.queryAccVouchMoney(qMap);	
								if(avm!=null){
									wage_money+=avm.getItem_money();
								}
								
								if("0".equals(awv.getIs_neg().toString().trim())){//非负数
									if("0".equals(awv.getSubj_dire().toString())){
										detailDebitMap.put("credit", 0);
										detailDebitMap.put("debit", wage_money);
										detailMoney+=NumberUtil.numberToRound(wage_money);
									}else{
										detailDebitMap.put("debit", 0);
										detailDebitMap.put("credit", wage_money);
										detailMoney-=NumberUtil.numberToRound(wage_money);
									}
								}else {
									if("0".equals(awv.getSubj_dire().toString())){
										detailDebitMap.put("credit", 0);
										detailDebitMap.put("debit", 0-wage_money);
										detailMoney+=NumberUtil.numberToRound(0-wage_money);
									}else{
										detailDebitMap.put("debit", 0);
										detailDebitMap.put("credit", 0-wage_money);
										detailMoney-=NumberUtil.numberToRound(0-wage_money);
									}
								}
							}else{
								detailDebitMap.put("debit", 0);
								detailDebitMap.put("credit", 0);
							}
							
							if(null!=wage_money&&wage_money != 0){
								detailList.add(detailDebitMap);
							}
						}
					}else{
						
						AccWageVouch avm = new AccWageVouch();
						Double wage_money=0.00;
						
						if("0".equals(awv.getIs_balance().toString().trim())){//是否取消借贷平:0否,1是
							if(null!=awv.getCal_eng()&& !"".equals(awv.getCal_eng())){//是否有转账公式:0否,1是
								
								String cal=awv.getCal_eng().replaceAll("\\{","nvl(").replaceAll("\\}", ",0)").replaceAll("本集团", SessionManager.getGroupId());
								cal=cal.replaceAll("本医院", SessionManager.getHosId()).replaceAll("本账套", SessionManager.getCopyCode());
								cal=cal.replaceAll("本分类",sbf.toString()).replaceAll("本部门","0");
								cal=cal.replaceAll("本期间",mapVo.get("acc_month").toString()).replaceAll("本年度", accWageVouch.getAcc_year()).replaceAll("上一年", Integer.parseInt(accWageVouch.getAcc_year().toString())-1+"");
								qMap.remove("dept_id");
								cal=cal.replaceAll("'本职工'","awp.emp_id");
								
								//mapVo.put("emp_id", list.get(j).getEmp_id());
								
								qMap.put("sql","sum("+cal+")");
								if(qMap.containsKey("kind_code")&&!"0".equals(qMap.get("kind_code"))){
									qMap.put("kind_code",qMap.get("kind_code"));
								}
									
								avm = accWageVouchMapper.queryAccVouchMoney(qMap);	//查询当前工资套当前方案工资总和
								
								if(avm!=null){
									wage_money+=avm.getItem_money();
								}
										
								if("0".equals(awv.getIs_neg().toString().trim())){//非负数
									if("0".equals(awv.getSubj_dire().toString())){//0:借
										detailDebitMap.put("credit", 0);
										detailDebitMap.put("debit", wage_money);
										detailMoney+=NumberUtil.numberToRound(wage_money);
									}else{//1:贷
										detailDebitMap.put("debit", 0);
										detailDebitMap.put("credit", wage_money);
										detailMoney-=NumberUtil.numberToRound(wage_money);
									}
								}else {
									if("0".equals(awv.getSubj_dire().toString())){//0:借
										detailDebitMap.put("credit", 0);
										detailDebitMap.put("debit", 0-wage_money);
										detailMoney+=NumberUtil.numberToRound(0-wage_money);
									}else{//1:贷
										detailDebitMap.put("debit", 0);
										detailDebitMap.put("credit", 0-wage_money);
										detailMoney-=NumberUtil.numberToRound(0-wage_money);
									}
								}
							}else{//没有转账公式
								detailDebitMap.put("debit", 0);
								detailDebitMap.put("credit", 0);
							}
						}else{//取消借贷平:1
							if("0".equals(awv.getIs_neg().toString().trim())){//非负数
								if("0".equals(awv.getSubj_dire().toString())){//科目方向为借方
									detailDebitMap.put("debit", detailMoney);
									detailDebitMap.put("credit", 0);
									wage_money=NumberUtil.numberToRound(detailMoney);
									detailMoney+=NumberUtil.numberToRound(detailMoney);
								}else{//科目方向为贷方
									detailDebitMap.put("debit", 0);
									detailDebitMap.put("credit", detailMoney);
									wage_money=NumberUtil.numberToRound(detailMoney);
									detailMoney-=NumberUtil.numberToRound(detailMoney);
								}
							}else{//是负数
								if("0".equals(awv.getSubj_dire().toString())){
									detailDebitMap.put("debit", (0-detailMoney));
									detailDebitMap.put("credit", 0);
									wage_money=NumberUtil.numberToRound(0-detailMoney);
									detailMoney+=NumberUtil.numberToRound(0-detailMoney);
								}else{
									detailDebitMap.put("debit", 0);
									detailDebitMap.put("credit", (0-detailMoney));
									wage_money=NumberUtil.numberToRound(0-detailMoney);
									detailMoney-=NumberUtil.numberToRound(0-detailMoney);
								}
							}
						}
						if(null!=wage_money&&wage_money != 0){
							detailList.add(detailDebitMap);
						}
					}
				}
			}
			
			
			
			String columns = "";
			String columns_value = "";
			if(have_dept){
				columns += ",check1_id, check1_no";
				columns_value += ",#{item.check1_id,jdbcType=INTEGER},#{item.check1_no,jdbcType=INTEGER}";
			}
			if(have_emp){
				columns += ",check2_id, check2_no";
				columns_value += ",#{item.check2_id,jdbcType=INTEGER},#{item.check2_no,jdbcType=INTEGER}";
			}
			
			
			
			int count=superVouchMapper.saveAutoVouch(vouchMap);//保存凭证主表
			
			if(count>0 && detailList.size() > 0){
				superVouchMapper.saveAutoVouchDetail(detailList);//保存分录
			}else{
//				throw new SysException("没有明细表数据或者没有对应关系！");
				return "{\"error\":\"没有明细表数据或者没有对应关系！\",\"state\":\"false\"}";
			}
			
			
			if(vouchCheckList.size() > 0){
				superVouchMapper.saveAccAutoCheckByTermend(columns, columns_value, vouchCheckList);//保存辅助核算
			}
			
			//日志信息
			logMap.put("group_id",group_id);
			logMap.put("hos_id",hos_id);
			logMap.put("copy_code",copy_code);
			logMap.put("vouch_id", vouch_id);
			logMap.put("business_no", mapVo.get("template_id"));
			logMap.put("busi_type_code", "Z010");
			logMap.put("template_code", mapVo.get("template_id"));
			logMap.put("create_date", date);
			accWageVouchMapper.saveAutoVouchLogTemp(logMap);//保存日志
			
			return "{\"state\":\"true\",\"vouch_id\":\""+vouch_id+"\"}";
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);

			throw new SysException("操作失败.");
			
			/*logger.error(e.getMessage());
			
			throw new SysException("{\"error\":\"{生成失败！\"}");
			*/
		}
		
	}
	
	public List<Integer>  indexColumn(String str,String b)
			throws DataAccessException {
		
		char arr[] = str.toCharArray();
		
		List<Integer> list = new ArrayList<Integer>();
		
		for (int i = 0; i < arr.length; i++) {

		if (b.equals(String.valueOf(arr[i]))) {
			
			list.add(i);
			
			}
		
		}
		
		return list;
	}

	@Override
	public String extendAccWageVouch(Map<String, Object> entityMap)
			throws DataAccessException {
		
		try {
			
			accWageVouchMapper.deleteAccWageVouch(entityMap);
			
			accWageVouchMapper.deleteAccWageVouchKind(entityMap);
			
			accWageVouchMapper.deleteAccWageVouchSubj(entityMap);
			
			accWageVouchMapper.extendAccWageVouch(entityMap);
			
			accWageVouchMapper.extendAccWageVouchKind(entityMap);
			
			accWageVouchMapper.extendAccWageVouchSubj(entityMap);
			
			return "{\"msg\":\"继承成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");
		}
		
	}

}
