
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.prm.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.chd.base.util.SpreadTableJSUtil;
import com.chd.hrp.prm.dao.PrmDeptDictMapper;
import com.chd.hrp.prm.dao.PrmDeptKpiValueMapper;
import com.chd.hrp.prm.dao.PrmDeptSchemeMapper;
import com.chd.hrp.prm.dao.PrmGoalMapper;
import com.chd.hrp.prm.dao.PrmKpiLibMapper;
import com.chd.hrp.prm.entity.PrmDeptDict;
import com.chd.hrp.prm.entity.PrmDeptKpiValue;
import com.chd.hrp.prm.entity.PrmDeptScheme;
import com.chd.hrp.prm.entity.PrmGoal;
import com.chd.hrp.prm.entity.PrmKpiLib;
import com.chd.hrp.prm.service.PrmDeptKpiValueService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 0308 科室KPI指标数据准备表
 * @Table:
 * PRM_DEPT_KPI_VALUE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("prmDeptKpiValueService")
public class PrmDeptKpiValueServiceImpl implements PrmDeptKpiValueService {

	private static Logger logger = Logger.getLogger(PrmDeptKpiValueServiceImpl.class);
	//引入DAO操作
	@Resource(name = "prmDeptKpiValueMapper")
	private final PrmDeptKpiValueMapper prmDeptKpiValueMapper = null;
    
	//引入DAO操作
	@Resource(name = "prmDeptSchemeMapper")
	private final PrmDeptSchemeMapper prmDeptSchemeMapper = null;
	
	@Resource(name = "prmDeptDictMapper")
	private final PrmDeptDictMapper prmDeptDictMapper = null;
	
	@Resource(name = "prmKpiLibMapper")
	private final PrmKpiLibMapper prmKpiLibMapper = null;
	
	@Resource(name = "prmGoalMapper")
	private final PrmGoalMapper prmGoalMapper = null;
	
	/**
	 * @Description 
	 * 添加0308 科室KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addPrmDeptKpiValue(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象0308 科室KPI指标数据准备表
		PrmDeptKpiValue prmDeptKpiValue = queryPrmDeptKpiValueByCode(entityMap);

		if (prmDeptKpiValue != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = prmDeptKpiValueMapper.addPrmDeptKpiValue(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addPrmDeptKpiValue\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加0308 科室KPI指标数据准备表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchPrmDeptKpiValue(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			prmDeptKpiValueMapper.addBatchPrmDeptKpiValue(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchPrmDeptKpiValue\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新0308 科室KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updatePrmDeptKpiValue(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = prmDeptKpiValueMapper.updatePrmDeptKpiValue(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updatePrmDeptKpiValue\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新0308 科室KPI指标数据准备表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchPrmDeptKpiValue(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  prmDeptKpiValueMapper.updateBatchPrmDeptKpiValue(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 updateBatchPrmDeptKpiValue\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除0308 科室KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deletePrmDeptKpiValue(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = prmDeptKpiValueMapper.deletePrmDeptKpiValue(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deletePrmDeptKpiValue\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除0308 科室KPI指标数据准备表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchPrmDeptKpiValue(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			prmDeptKpiValueMapper.deleteBatchPrmDeptKpiValue(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchPrmDeptKpiValue\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 查询结果集0308 科室KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryPrmDeptKpiValue(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<PrmDeptKpiValue> list = prmDeptKpiValueMapper.queryPrmDeptKpiValue(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<PrmDeptKpiValue> list = prmDeptKpiValueMapper.queryPrmDeptKpiValue(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象0308 科室KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public PrmDeptKpiValue queryPrmDeptKpiValueByCode(Map<String,Object> entityMap)throws DataAccessException{
		return prmDeptKpiValueMapper.queryPrmDeptKpiValueByCode(entityMap);
	}
	@Override
	public String queryPrmDeptKpiValueScheme(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
	SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<PrmDeptKpiValue> list = prmDeptKpiValueMapper.queryPrmDeptKpiValueScheme(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<PrmDeptKpiValue> list = prmDeptKpiValueMapper.queryPrmDeptKpiValueScheme(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	@Override
	public String createDeptKpiValue(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		
		try {
			
		    prmDeptKpiValueMapper.cleanPrmDeptKpiValue(entityMap);
		    
			entityMap.put("sql", "method_code = '01'");
			
			List<PrmDeptScheme> prmHosScheme = prmDeptSchemeMapper.queryPrmDeptScheme(entityMap);
			
			if(prmHosScheme.size() == 0 ){
				
				return "{\"state\":\"false\"}";
			}
			
		    for (int i = 0; i <prmHosScheme.size(); i++) {
		    	Map<String, Object> mapVo =  new HashMap<String, Object>();
		    	mapVo.put("group_id", entityMap.get("group_id"));
				mapVo.put("hos_id", entityMap.get("hos_id"));
				mapVo.put("copy_code", entityMap.get("copy_code"));
				mapVo.put("acc_year", entityMap.get("acc_year"));
				mapVo.put("acc_month", entityMap.get("acc_month"));
				mapVo.put("goal_code", prmHosScheme.get(i).getGoal_code());
				mapVo.put("kpi_code", prmHosScheme.get(i).getKpi_code());
				mapVo.put("dept_no", prmHosScheme.get(i).getDept_no());
				mapVo.put("dept_id", prmHosScheme.get(i).getDept_id());
				mapVo.put("kpi_value", 0);
		   		mapVo.put("is_audit", 0);
		   		prmDeptKpiValueMapper.addPrmDeptKpiValue(mapVo);

		    }
			
		    return "{\"msg\":\"科室KPI指标数据生成成功.\",\"state\":\"true\"}";
		    
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"科室KPI指标数据生成失败\"}");
		}
	}
	
	@Override
	public String auditPrmDeptKpiValue(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			if(entityMap.get("checkIds") != null){
				
				String checkIds = entityMap.get("checkIds").toString();
				
				for(String checkId : checkIds.split(",")){
					Map<String, Object> mapVo = new HashMap<String, Object>();

					String[] ids = checkId.split("@");

					// 表的主键
					mapVo.put("group_id", ids[0]);
					mapVo.put("hos_id", ids[1]);
					mapVo.put("copy_code", ids[2]);
					mapVo.put("acc_year", ids[3]);
					mapVo.put("acc_month", ids[4]);
					mapVo.put("dept_id", ids[5]);
					mapVo.put("dept_no", ids[6]);
					mapVo.put("goal_code", ids[7]);
					mapVo.put("kpi_code", ids[8]);
					
					mapVo.put("user_code", SessionManager.getUserCode());
					mapVo.put("is_audit", 1);
					mapVo.put("audit_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
					
					prmDeptKpiValueMapper.auditPrmDeptKpiValue(mapVo);
				}
				
			}else{
				
				prmDeptKpiValueMapper.auditPrmDeptKpiValue(entityMap);
			}
			 
				
			return "{\"msg\":\"审核成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"审核失败 数据库异常 请联系管理员!\"}");

		}	
	}
	
	@Override
	public String reauditPrmDeptKpiValue(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			 
if(entityMap.get("checkIds") != null){
				
				String checkIds = entityMap.get("checkIds").toString();
				
				for(String checkId : checkIds.split(",")){
					Map<String, Object> mapVo = new HashMap<String, Object>();

					String[] ids = checkId.split("@");

					// 表的主键
					mapVo.put("group_id", ids[0]);
					mapVo.put("hos_id", ids[1]);
					mapVo.put("copy_code", ids[2]);
					mapVo.put("acc_year", ids[3]);
					mapVo.put("acc_month", ids[4]);
					mapVo.put("dept_id", ids[5]);
					mapVo.put("dept_no", ids[6]);
					mapVo.put("goal_code", ids[7]);
					mapVo.put("kpi_code", ids[8]);
					
					mapVo.put("user_code","");
					mapVo.put("is_audit", 0);
					mapVo.put("audit_date","");
					
					prmDeptKpiValueMapper.auditPrmDeptKpiValue(mapVo);
				}
				
			}else{
				
				prmDeptKpiValueMapper.auditPrmDeptKpiValue(entityMap);
			}
				
			  return "{\"msg\":\"取消审核成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"取消审核失败 数据库异常 请联系管理员!\"}");

		}	
	
	}
	@Override
	public Long queryPrmDeptKpiValueByAudit(Map<String, Object> entityMap) throws DataAccessException {
		return prmDeptKpiValueMapper.queryPrmDeptKpiValueByAudit(entityMap);
	}
	
	@Override
	public List<PrmDeptDict> queryPrmDeptDictList( Map<String, Object> entityMap) throws DataAccessException {
		
		return prmDeptDictMapper.queryPrmDeptDict(entityMap);
	}
	@Override
	public int updatePrmDeptKpiValueByImport(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			
			int state = prmDeptKpiValueMapper.updatePrmDeptKpiValue(entityMap);
				
			return state;

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return 0;

		}
	}
	
	@Override
	public String importPrmDeptKpiValue(Map<String, Object> entityMap) throws DataAccessException {
		try {
			
			//1.判断表头是否为空
			String columns=entityMap.get("columns").toString();
			JSONArray jsonColumns = JSONObject.parseArray(columns);	
			if(jsonColumns==null || jsonColumns.size()==0){
				return "{\"error\":\"表头为空！\",\"state\":\"false\"}";
			}
			
			//2.判断数据是否为空
			String content=entityMap.get("content").toString();
			List<Map<String,List<String>>> liData=SpreadTableJSUtil.toListMap(content,1);
			if(liData==null || liData.size()==0){
				return "{\"error\":\"没有数据！\",\"state\":\"false\"}";
			}
			
			Map<String, Object> queryMap =new HashMap<String,Object>();
			queryMap.put("group_id", SessionManager.getGroupId());
			queryMap.put("hos_id", SessionManager.getHosId());
			queryMap.put("copy_code", SessionManager.getCopyCode());
			
			// 3.查询 kpi指标字典 List
			List<PrmKpiLib> kpiList = prmKpiLibMapper.queryPrmKpiLib(queryMap);
			//用于存储查询targetList中的AphiTarget对象,以键值对的形式存储,用于判断指标是否存在
			Map<String, PrmKpiLib> kpiMap = new HashMap<String, PrmKpiLib>();
			//将指标List存入Map   键:target_code 值:AphiTarget
			for(PrmKpiLib kpi : kpiList){
				kpiMap.put(kpi.getKpi_code(), kpi);
				kpiMap.put(kpi.getKpi_name(), kpi);
			}
			
			//4.判断表头中指标是否存在
			StringBuffer sb = new StringBuffer();//提示信息:用于存储表头中不存在的指标
			Map<String,String> kpiColumnMap = new HashMap<String,String>();//用于存储表头中的指标,作为遍历数据时取指标值
			
			for(Map<String,List<String>> item : liData ){
				for(Map.Entry<String, List<String>> entry : item.entrySet()){
					String key = entry.getKey();
					if("年度".equals(key) || "月份".equals(key) || "科室".equals(key) || "目标编码或目标名称".equals(key)){
						continue;
					}
					
					kpiColumnMap.put(key, key);
					if(kpiMap.get(key) == null){
						sb.append("指标" + key + "不存在,");
					}
				}
				break;//判断指标表头是否存在,只遍历一次
			}
			
			if(kpiColumnMap == null || kpiColumnMap.size() == 0){
				return "{\"error\":\"表头中未存在指标或未填写任何指标\",\"state\":\"false\"}";
			}
			
			//表头含有不存在指标 返回提示
			if(sb.length() > 0){
				return "{\"error\":\"" + sb.deleteCharAt(sb.length() -1).toString()+ "\",\"state\":\"false\"}";
			}
			
			
			List<PrmDeptKpiValue> pdkvList = prmDeptKpiValueMapper.queryPrmDeptKpiValue(queryMap);
			//5.以年、月、科室id、科室变更no、目标编码、指标编码作为键,pdkv对象作为值,判断数据是否存在
			Map<String,PrmDeptKpiValue> pdkvMap = new HashMap<String,PrmDeptKpiValue>();
			for(PrmDeptKpiValue pdkv : pdkvList){
				
				String key = String.valueOf(pdkv.getAcc_year()) +
						String.valueOf(pdkv.getAcc_month()) + 
						String.valueOf(pdkv.getDept_id()) + 
						String.valueOf(pdkv.getDept_no()) + 
						String.valueOf(pdkv.getGoal_code()) +
						String.valueOf(pdkv.getKpi_code()) ;
				
				pdkvMap.put(key, pdkv);
			}
			
			queryMap.put("is_stop", "0");
			//查询所有绩效科室
			List<PrmDeptDict> deptDictList = prmDeptDictMapper.queryPrmDeptDict(queryMap);
			//用于存储查询deptList中的AphiDept对象,以键值对的形式存储,用于判断科室是否存在
			Map<String,PrmDeptDict> deptDictMap = new HashMap<String,PrmDeptDict>();
			//将科室List存入Map   键:dept_name 值:AphiDeptDict
			for(PrmDeptDict deptDict : deptDictList){
				deptDictMap.put(deptDict.getDept_name(), deptDict);
				deptDictMap.put(deptDict.getDept_code(), deptDict);
			}
			
			//查询当前会计年度目标
			queryMap.put("acc_year", SessionManager.getAcctYear());
			List<PrmGoal> goalList = prmGoalMapper.queryPrmGoalHos(queryMap);
			Map<String,PrmGoal> goalMap = new HashMap<String,PrmGoal>();
			for(PrmGoal prmGoal : goalList){
				goalMap.put(prmGoal.getGoal_code(), prmGoal);
				goalMap.put(prmGoal.getGoal_name(), prmGoal);
			}
			
			/*
			 
			//按科室与系统平台对应关系查询科室  List
			List<AphiDept> deptList = aphiDeptMapper.queryAphiDeptRelaByMaping(queryMap);
			//用于存储查询deptList中的AphiDept对象,以键值对的形式存储,用于判断科室是否存在
			Map<String,AphiDept> deptMap = new HashMap<String,AphiDept>();
			//将科室List存入Map   键:dept_name 值:AphiDept
			for(AphiDept dept : deptList){
				deptMap.put(dept.getDept_name(), dept);
				deptMap.put(dept.getDept_code(), dept);
			}
			
			//按科室与其它平台对应关系查询科室  List
			List<AphiDeptHip> deptHipList = aphiDeptHipMapper.queryAphiDeptRelaByMapingHip(queryMap);			
			//用于存储查询deptHipList中的AphiDeptHip对象,以键值对的形式存储,用于判断科室是否存在
			Map<String,AphiDeptHip> deptHipMap = new HashMap<String,AphiDeptHip>();
			//将科室List存入Map   键:dept_name 值:AphiDeptHip
			for(AphiDeptHip deptHip : deptHipList){
				deptHipMap.put(deptHip.getDept_name(), deptHip);
				deptHipMap.put(deptHip.getDept_code(), deptHip);
			}
			
			 */
			
			
			//6.组装数据
			
			//用于存储传的数据值,判断数据是否重复
			Map<String,String> exitMap = new HashMap<String,String>();
			//存储添加数据List
			List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
			
			//存储修改数据List
			List<Map<String,Object>> updateList = new ArrayList<Map<String,Object>>();
			//用于记录重复数据
			StringBuffer err_sb = new StringBuffer();
			
			for(Map.Entry<String, String> entry:kpiColumnMap.entrySet()){//遍历指标
				
				//遍历导入数据
				for(Map<String,List<String>> item : liData ){
					
					List<String> acc_year = item.get("年度") ;
					List<String> acc_month = item.get("月份") ;
					List<String> dept_name = item.get("科室") ;
					List<String> goal = item.get("目标编码或目标名称") ;
					List<String> kpi_code = item.get(entry.getKey()) ;//指标
					if(item.get("年度").get(1).toString().equals("") && item.get("月份").get(1).toString().equals("") && item.get("科室").get(1).toString().equals("")&& item.get("目标编码或目标名称").get(1).toString().equals("") ){
						continue;
					}
					
					if(acc_year.get(1) == null){
						return "{\"warn\":\"年度为空！\",\"state\":\"false\",\"row_cell\":" +acc_year.get(0) +"\"\"}";
					}
					
					if(acc_month.get(1) == null){
						return "{\"warn\":\"月份为空！\",\"state\":\"false\",\"row_cell\":\"" + acc_month.get(0) +"\"}";
					}
					
					if(dept_name.get(1) == null){
						return "{\"warn\":\"科室为空！\",\"state\":\"false\",\"row_cell\":\""+dept_name.get(0)+"\"}";
					}else{
						if(deptDictMap.get(dept_name.get(1)) == null /*&& deptHipMap.get(dept_name.get(1)) == null && deptDictMap.get(dept_name.get(1)) == null*/){
							return "{\"warn\":\"" + dept_name.get(1) + ",科室不存在！\",\"state\":\"false\",\"row_cell\":\"" + dept_name.get(0) + "\"}";
						}
					}
					
					if(goal.get(1) == null){
						return "{\"warn\":\"目标为空！\",\"state\":\"false\",\"row_cell\":\""+goal.get(0)+"\"}";
					}else{
						if(goalMap.get(goal.get(1)) == null ){
							return "{\"warn\":\"" + goal.get(1) + ",目标不存在！\",\"state\":\"false\",\"row_cell\":\"" + goal.get(0) + "\"}";
						}
					}
					
					if(kpi_code.get(1) == null){
						return "{\"warn\":\"指标值为空！\",\"state\":\"false\",\"row_cell\":\" " + kpi_code.get(0) + "\"}";
					}else{
						 try{
							 Double.parseDouble((kpi_code.get(1)));//校验是否为数值
						 }catch(NumberFormatException e){
							 return "{\"warn\":\"" + kpi_code.get(1) + ",指标值输入不合法,只能输入数字！\",\"state\":\"false\",\"row_cell\":\"" + kpi_code.get(0) + "\"}";
						 }
					}
					
					//以年度|月份|目标|指标|科室为键值,判断导入数据是否重复
					String key = acc_year.get(1) + "|" +acc_month.get(1) + "|" + goal.get(1) + "|" + kpiMap.get(entry.getKey()).getKpi_code() + "|" + dept_name.get(1);
					if(exitMap.get(key) != null ){
						err_sb.append(acc_year.get(1)+"年度," + acc_month.get(1)+"月份," + dept_name.get(1)+ "科室," + goal.get(1)+ "目标," + entry.getKey()+"指标<br/>");
					}else{
						exitMap.put(key, key);
					}
					
					String acc_mon=null;
					
					if(Integer.parseInt(acc_month.get(1))<10 && acc_month.get(1).length()==1){
						
						acc_mon='0'+String.valueOf(acc_month.get(1));
						
					}else{
						
						acc_mon=	String.valueOf(acc_month.get(1));
						
					}
					//添加数据Map
					Map<String,Object> addMap = new HashMap<String,Object>();
					addMap.put("group_id", SessionManager.getGroupId());
					addMap.put("hos_id", SessionManager.getHosId());
					addMap.put("copy_code", SessionManager.getCopyCode());
					addMap.put("acc_year", acc_year.get(1));
					addMap.put("acc_month", acc_mon);
					addMap.put("goal_code",goalMap.get(goal.get(1)).getGoal_code());
					addMap.put("kpi_code",kpiMap.get(entry.getKey()).getKpi_code());
					
					/*//系统平台科室
					if(deptMap.get(dept_name.get(1)) != null){
						addMap.put("dept_id",deptMap.get(dept_name.get(1)).getDept_id());
						addMap.put("dept_no",deptMap.get(dept_name.get(1)).getDept_no());
					}
					
					//其它平台科室
					if(deptHipMap.get(dept_name.get(1)) != null){
						addMap.put("dept_id",deptHipMap.get(dept_name.get(1)).getDept_id());
						addMap.put("dept_no",deptHipMap.get(dept_name.get(1)).getDept_no());
					}*/
					
					//绩效科室
					if(deptDictMap.get(dept_name.get(1)) != null){
						addMap.put("dept_id",deptDictMap.get(dept_name.get(1)).getDept_id());
						addMap.put("dept_no",deptDictMap.get(dept_name.get(1)).getDept_no());
					}
					
					//addMap.put("target_name", dictMap.get(target_code.get(1)).getTarget_name());
					addMap.put("kpi_value", kpi_code.get(1));
					addMap.put("is_audit", 0);
					addMap.put("user_code", "");
					addMap.put("audit_date","");
					
					//根据年+月+科室id+科室变更no+指标编码 作为键 判断数据库中是否存在数据
					String is_exit_key = 
							String.valueOf(addMap.get("acc_year")) +
							String.valueOf(addMap.get("acc_month")) +
							String.valueOf(addMap.get("dept_id")) +
							String.valueOf(addMap.get("dept_no")) +
							String.valueOf(addMap.get("goal_code")) +
							String.valueOf(addMap.get("kpi_code")) ;
					
					
					PrmDeptKpiValue pdkv = pdkvMap.get(is_exit_key);
					if(pdkv == null){//不存在,添加
						
						addList.add(addMap);
					}else{
						
						if( !"1".equals(String.valueOf(pdkv.getIs_audit())) ){//存在,如果未审核,添加到修改List
							
							updateList.add(addMap);
						}
					}
				}
			}
			
			if( err_sb.length() > 0){//重复数据是否存在
				 return "{\"warn\":\"以下数据【" +err_sb.toString() + "】数据重复！\",\"state\":\"false\"}";
			}
			
			if(addList.size() > 0){
				prmDeptKpiValueMapper.addBatchPrmDeptKpiValue(addList);
			}
			
			if(updateList.size() > 0){
				prmDeptKpiValueMapper.updateBatchPrmDeptKpiValue(updateList);
			}
			
			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"导入失败.\"}");
		}
	}
}
