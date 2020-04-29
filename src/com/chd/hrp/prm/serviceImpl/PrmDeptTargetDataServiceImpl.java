
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.prm.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
import com.chd.base.util.JsonListMapUtil;
import com.chd.base.util.SpreadTableJSUtil;
import com.chd.hrp.prm.dao.PrmDeptDictMapper;
import com.chd.hrp.prm.dao.PrmDeptTargetDataMapper;
import com.chd.hrp.prm.dao.PrmTargetMapper;
import com.chd.hrp.prm.entity.PrmDeptDict;
import com.chd.hrp.prm.entity.PrmDeptTargetData;
import com.chd.hrp.prm.entity.PrmTarget;
import com.chd.hrp.prm.service.PrmDeptTargetDataService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 0312 科室绩效指标数据表
 * @Table:
 * PRM_DEPT_TARGET_DATA
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("prmDeptTargetDataService")
public class PrmDeptTargetDataServiceImpl implements PrmDeptTargetDataService {

	private static Logger logger = Logger.getLogger(PrmDeptTargetDataServiceImpl.class);
	//引入DAO操作
	@Resource(name = "prmDeptTargetDataMapper")
	private final PrmDeptTargetDataMapper prmDeptTargetDataMapper = null;
    
	@Resource(name = "prmTargetMapper")
	private final PrmTargetMapper  prmTargetMapper = null;
	
	@Resource(name = "prmDeptDictMapper")
	private final PrmDeptDictMapper prmDeptDictMapper = null;
	
	/**
	 * @Description 
	 * 添加0312 科室绩效指标数据表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addPrmDeptTargetData(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象0312 科室绩效指标数据表
		PrmDeptTargetData prmDeptTargetData = queryPrmDeptTargetDataByCode(entityMap);

		if (prmDeptTargetData != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = prmDeptTargetDataMapper.addPrmDeptTargetData(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addPrmDeptTargetData\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加0312 科室绩效指标数据表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchPrmDeptTargetData(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			prmDeptTargetDataMapper.addBatchPrmDeptTargetData(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchPrmDeptTargetData\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新0312 科室绩效指标数据表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updatePrmDeptTargetData(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = prmDeptTargetDataMapper.updatePrmDeptTargetData(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updatePrmDeptTargetData\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新0312 科室绩效指标数据表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchPrmDeptTargetData(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  prmDeptTargetDataMapper.updateBatchPrmDeptTargetData(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 updateBatchPrmDeptTargetData\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除0312 科室绩效指标数据表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deletePrmDeptTargetData(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = prmDeptTargetDataMapper.deletePrmDeptTargetData(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deletePrmDeptTargetData\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除0312 科室绩效指标数据表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchPrmDeptTargetData(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			prmDeptTargetDataMapper.deleteBatchPrmDeptTargetData(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchPrmDeptTargetData\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 查询结果集0312 科室绩效指标数据表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryPrmDeptTargetData(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<PrmDeptTargetData> list = prmDeptTargetDataMapper.queryPrmDeptTargetData(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<PrmDeptTargetData> list = prmDeptTargetDataMapper.queryPrmDeptTargetData(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	@Override
	public String queryPrmDeptTargetPrmTargetData(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
                SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<PrmDeptTargetData> list = prmDeptTargetDataMapper.queryPrmDeptTargetPrmTargetData(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<PrmDeptTargetData> list = prmDeptTargetDataMapper.queryPrmDeptTargetPrmTargetData(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}

	}	
	
	/**
	 * @Description 
	 * 获取对象0312 科室绩效指标数据表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public PrmDeptTargetData queryPrmDeptTargetDataByCode(Map<String,Object> entityMap)throws DataAccessException{
		return prmDeptTargetDataMapper.queryPrmDeptTargetDataByCode(entityMap);
	}
	@Override
	public String createPrmDeptTargetData(Map<String, Object> entityMap,
			String paramVo) throws DataAccessException {
		// TODO Auto-generated method stub
		
		 Map<String, Object> mapVo =  new HashMap<String, Object>();
		 
		       if(mapVo.get("group_id") == null){
				
				mapVo.put("group_id", SessionManager.getGroupId());
				 
				}
				
				if(mapVo.get("hos_id") == null){
					
				mapVo.put("hos_id", SessionManager.getHosId());
				 
				}
				
				if(mapVo.get("copy_code") == null){
					
		         mapVo.put("copy_code", SessionManager.getCopyCode());
		    
				}
				
				if(mapVo.get("acct_year") == null){
					
				    mapVo.put("acct_year", SessionManager.getAcctYear());
				   
						}
				
				int state = 0;
				
				mapVo.put("acc_year", paramVo.substring(0, 4));
				
			    mapVo.put("acc_month", paramVo.substring(4, 6));
			    try {
					
			    	prmDeptTargetDataMapper.cleanPrmDeptTargetData(mapVo);
			    	
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			    
			    
				Map<String, List<PrmTarget>> prmTargetMapVo = new HashMap<String,  List<PrmTarget>>();
				
				entityMap.put("target_nature", "03");//03科室
				
				List<PrmTarget> prmTarget = prmTargetMapper.queryPrmTargetNatureCreate(entityMap);
		
			  	prmTargetMapVo.put("prmTarget", prmTarget);  
				
			  	
			  	entityMap.put("is_stop", 0);
			 
			  	 List<PrmDeptDict> AphiDeptDict = prmDeptDictMapper.queryPrmDeptDict(entityMap);
			  	
			  	for (PrmDeptDict AphiDeptDict2 :AphiDeptDict) {
			  		
			  	   for (Iterator<String> iterator = prmTargetMapVo.keySet().iterator(); iterator.hasNext();) {
		      			
			   		      String key = iterator.next();
			   		      
			   		      List<PrmTarget> l = prmTargetMapVo.get(key);
			   	
			   	
			   		      for (int i = 0; i < l.size(); i++) {
			   		    	
			   		    	mapVo.put("target_code", l.get(i).getTarget_code().toString());
			   		    	mapVo.put("dept_id", AphiDeptDict2.getDept_id());
			   		    	mapVo.put("dept_no", AphiDeptDict2.getDept_no());
			   		    	mapVo.put("target_value", 0);
			   		    	mapVo.put("is_audit", 0);
			   		    	mapVo.put("user_code", "");
			   		    	mapVo.put("audit_date", "");	
			   		    	try {
								
			   		    		state = prmDeptTargetDataMapper.addPrmDeptTargetData(mapVo);
			   		    		
							} catch (Exception e) {
								// TODO: handle exception
								e.printStackTrace();
							}
			   		    	
			   		    }
			        		
			         }
			  		
					
				}
			  	
				if( state > 0){
	       			return "{\"msg\":\"科室指标数据生成成功.\",\"state\":\"true\"}";
	       		}else{
	       			return "{\"error\":\"科室指标数据生成异常 请联系管理员!\"}";
	       		}
	    }
	@Override
	public String auditPrmdeptTargetData(Map<String, Object> entityMap) throws DataAccessException {
			
		try {
			
			if(entityMap.get("checkedRows") == null){//未选择数据、按年月审核
				
				prmDeptTargetDataMapper.auditPrmDeptTargetData(entityMap);
				
			}else{//有选择数据、按选择的数据审核
				
				JSONArray checkDataJson = JSONArray.parseArray((String) entityMap.get("checkedRows"));//获取选择的审核数据
				Iterator iterator = checkDataJson.iterator();
				
				while(iterator.hasNext()){
					
					Map<String,Object> mapVo = new HashMap<String,Object>();
					
					JSONObject jsonObj = JSONObject.parseObject(iterator.next().toString());
					
					mapVo.put("group_id", jsonObj.get("group_id"));
					mapVo.put("hos_id", jsonObj.get("hos_id"));
					mapVo.put("copy_code", jsonObj.get("copy_code"));
					mapVo.put("acc_year", jsonObj.get("acc_year"));
					mapVo.put("acc_month", jsonObj.get("acc_month"));
					mapVo.put("target_code", jsonObj.get("target_code"));
					mapVo.put("dept_id", jsonObj.get("dept_id"));
					mapVo.put("dept_no", jsonObj.get("dept_no"));
					
					mapVo.put("is_audit", entityMap.get("is_audit"));
					mapVo.put("user_code", entityMap.get("user_code"));
					mapVo.put("audit_date", entityMap.get("audit_date"));
					
					prmDeptTargetDataMapper.auditPrmDeptTargetData(mapVo);
				}
			}
			
			
			if("1".equals(String.valueOf(entityMap.get("is_audit")))){
				
				return "{\"msg\":\"审核成功.\",\"state\":\"true\"}";
			}else{
				
				return "{\"msg\":\"反审核成功.\",\"state\":\"true\"}";
			}

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"审核失败 数据库异常 请联系管理员!\"}");

		}		
			
	}
	
	
	@Override
	public List<PrmDeptDict> queryPrmDeptDictList( Map<String, Object> entityMap) throws DataAccessException {
		
		return prmDeptDictMapper.queryPrmDeptDict(entityMap);
	}
	@Override
	public int updatePrmDeptTargetDataByImport(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			
			int state = prmDeptTargetDataMapper.updatePrmDeptTargetData(entityMap);
				
			return state;

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return 0;

		}	
	}
	
	
	@Override
	public String importPrmDeptTargetData(Map<String, Object> entityMap) throws DataAccessException {
		
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
			
			// 3.查询 基本指标字典 List
			List<PrmTarget> targetList = prmTargetMapper.queryPrmTarget(entityMap);
			//用于存储查询targetList中的AphiTarget对象,以键值对的形式存储,用于判断指标是否存在
			Map<String, PrmTarget> targetMap = new HashMap<String, PrmTarget>();
			//将指标List存入Map   键:target_code 值:AphiTarget
			for(PrmTarget target : targetList){
				targetMap.put(target.getTarget_code(), target);
				targetMap.put(target.getTarget_name(), target);
			}
						
			
			//4.判断表头中指标是否存在
			StringBuffer sb = new StringBuffer();//提示信息:用于存储表头中不存在的指标
			Map<String,String> targetColumnMap = new HashMap<String,String>();//用于存储表头中的指标,作为遍历数据时取指标值
			
			for(Map<String,List<String>> item : liData ){
				for(Map.Entry<String, List<String>> entry : item.entrySet()){
					String key = entry.getKey();
					if("年度".equals(key) || "月份".equals(key) || "科室".equals(key)){
						continue;
					}
					
					targetColumnMap.put(key, key);
					if(targetMap.get(key) == null){
						sb.append("指标" + key + "不存在,");
					}
				}
				break;//判断指标表头是否存在,只遍历一次
			}
			
			if(targetColumnMap == null || targetColumnMap.size() == 0){
				return "{\"error\":\"表头中未存在指标或未填写任何指标\",\"state\":\"false\"}";
			}
			
			//表头含有不存在指标 返回提示
			if(sb.length() > 0){
				return "{\"error\":\"" + sb.deleteCharAt(sb.length() -1).toString()+ "\",\"state\":\"false\"}";
			}
			
			List<PrmDeptTargetData> pdtdList = prmDeptTargetDataMapper.queryPrmDeptTargetDataByImport(entityMap);
			//5.以年、月、科室id、科室变更no、指标编码作为键,pdtd对象作为值,判断数据是否存在
			Map<String,PrmDeptTargetData> pdtdMap = new HashMap<String,PrmDeptTargetData>();
			for(PrmDeptTargetData pdtd : pdtdList){
				
				String key = String.valueOf(pdtd.getAcc_year()) +
						String.valueOf(pdtd.getAcc_month()) + 
						String.valueOf(pdtd.getDept_id()) + 
						String.valueOf(pdtd.getDept_no()) + 
						String.valueOf(pdtd.getTarget_code());
				
				pdtdMap.put(key, pdtd);
			}
			
			//查询所有绩效科室
			List<PrmDeptDict> deptDictList = prmDeptDictMapper.queryPrmDeptDict(entityMap);
			//用于存储查询deptList中的AphiDept对象,以键值对的形式存储,用于判断科室是否存在
			Map<String,PrmDeptDict> deptDictMap = new HashMap<String,PrmDeptDict>();
			//将科室List存入Map   键:dept_name 值:AphiDeptDict
			for(PrmDeptDict deptDict : deptDictList){
				deptDictMap.put(deptDict.getDept_name(), deptDict);
				deptDictMap.put(deptDict.getDept_code(), deptDict);
				deptDictMap.put(deptDict.getDept_no().toString(), deptDict);
			}
			
			/*
			 * //按科室与系统平台对应关系查询科室  List
			List<AphiDept> deptList = aphiDeptMapper.queryAphiDeptRelaByMaping(entityMap);
			//用于存储查询deptList中的AphiDept对象,以键值对的形式存储,用于判断科室是否存在
			Map<String,AphiDept> deptMap = new HashMap<String,AphiDept>();
			//将科室List存入Map   键:dept_name 值:AphiDept
			for(AphiDept dept : deptList){
				deptMap.put(dept.getDept_name(), dept);
				deptMap.put(dept.getDept_code(), dept);
			}
			
			//按科室与其它平台对应关系查询科室  List
			List<AphiDeptHip> deptHipList = aphiDeptHipMapper.queryAphiDeptRelaByMapingHip(entityMap);			
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
			
			for(Map.Entry<String, String> entry:targetColumnMap.entrySet()){//遍历指标
				
				//遍历导入数据
				for(Map<String,List<String>> item : liData ){
					
					List<String> acc_year = item.get("年度") ;
					List<String> acc_month = item.get("月份") ;
					List<String> dept_name = item.get("科室") ;
					List<String> target_code = item.get(entry.getKey()) ;//指标
					
					if(item.get("年度").get(1).toString().equals("")&& item.get("月份").get(1).toString().equals("") && item.get("科室").get(1).toString().equals("") ){
						continue;
					}
					if(acc_year.get(1) == null){
						return "{\"warn\":\"年度为空！\",\"state\":\"false\",\"row_cell\":" +acc_year.get(0) +"\"\"}";
					}
					
					if(acc_month.get(1) == null){
						return "{\"warn\":\"月份为空！\",\"state\":\"false\",\"row_cell\":\"" + acc_month.get(0) +"\"}";
					}
					
					
					if(dept_name.get(1) == null){
						return "{\"warn\":\"科室为空！\",\"state\":\"false\",\"row_cell\":\"\"}";
					}else{
						if(deptDictMap.get(dept_name.get(1)) == null /*&& deptHipMap.get(dept_name.get(1)) == null && deptDictMap.get(dept_name.get(1)) == null*/){
							return "{\"warn\":\"" + dept_name.get(1) + ",科室不存在！\",\"state\":\"false\",\"row_cell\":\"" + dept_name.get(0) + "\"}";
						}
					}
					
					if(target_code.get(1) == null){
						return "{\"warn\":\"指标值为空！\",\"state\":\"false\",\"row_cell\":\" " + target_code.get(0) + "\"}";
					}else{
						 try{
							 Double.parseDouble((target_code.get(1)));//校验是否为数值
						 }catch(NumberFormatException e){
							 return "{\"warn\":\"" + target_code.get(1) + ",指标值输入不合法,只能输入数字！\",\"state\":\"false\",\"row_cell\":\"" + target_code.get(0) + "\"}";
						 }
					}
					
					//以年度|月份|指标编码|科室名称为键值,判断导入数据是否重复
					String key = acc_year.get(1) + "|" +acc_month.get(1) + "|" + targetMap.get(entry.getKey()).getTarget_code() + "|" + dept_name.get(1);
					if(exitMap.get(key) != null ){
						err_sb.append(acc_year.get(1)+"年度," + acc_month.get(1)+"月份," + dept_name.get(1)+"科室," + entry.getKey()+"指标<br/>");
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
					addMap.put("acc_month",acc_mon );
					addMap.put("target_code",targetMap.get(entry.getKey()).getTarget_code());
					
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
					addMap.put("target_value", target_code.get(1));
					addMap.put("is_audit", 0);
					addMap.put("user_code", "");
					addMap.put("audit_date","");
					
					//根据年+月+科室id+科室变更no+指标编码 作为键 判断数据库中是否存在数据
					String is_exit_key = 
							String.valueOf(addMap.get("acc_year")) +
							String.valueOf(addMap.get("acc_month")) +
							String.valueOf(addMap.get("dept_id")) +
							String.valueOf(addMap.get("dept_no")) +
							String.valueOf(addMap.get("target_code")) ;
					
					
					PrmDeptTargetData pdtd = pdtdMap.get(is_exit_key);
					if(pdtd == null){//不存在,添加
						
						addList.add(addMap);
					}else{
						
						if( !"1".equals(String.valueOf(pdtd.getIs_audit())) ){//存在,如果未审核,添加到修改List
							
							updateList.add(addMap);
						}
					}
				}
			}
			
			if( err_sb.length() > 0){//重复数据是否存在
				 return "{\"warn\":\"以下数据【" +err_sb.toString() + "】数据重复！\",\"state\":\"false\"}";
			}
			
			if(addList.size() > 0){
				
				prmDeptTargetDataMapper.addBatchPrmDeptTargetData(addList);
			}
			
			if(updateList.size() > 0){
				
				prmDeptTargetDataMapper.updateBatchPrmDeptTargetData(updateList);
			}
			
			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"导入失败.\"}");
		}
		
	}
	@Override
	public List<Map<String, Object>> queryPrmDeptTargetPrint(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		
		List<PrmDeptTargetData> list = prmDeptTargetDataMapper.queryPrmDeptTargetPrmTargetData(entityMap);
		
		return JsonListMapUtil.beanToListMap(list);
	}
	
}
