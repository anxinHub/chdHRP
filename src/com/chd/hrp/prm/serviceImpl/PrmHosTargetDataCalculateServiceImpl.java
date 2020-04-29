
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

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.base.util.JsonListMapUtil;
import com.chd.hrp.prm.dao.PrmHosTargetDataCalculateMapper;
import com.chd.hrp.prm.dao.PrmTargetMapper;
import com.chd.hrp.prm.entity.PrmHosTargetData;
import com.chd.hrp.prm.entity.PrmHosTargetDataCalculate;
import com.chd.hrp.prm.entity.PrmTarget;
import com.chd.hrp.prm.service.PrmHosTargetDataCalculateService;
import com.chd.hrp.prm.service.PrmTargetMethodService;
import com.chd.hrp.sys.dao.InfoMapper;
import com.chd.hrp.sys.entity.Info;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 0212 院级绩效指标数据表
 * @Table:
 * PRM_HOS_TARGET_DATA
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("prmHosTargetDataCalculateService")
public class PrmHosTargetDataCalculateServiceImpl implements PrmHosTargetDataCalculateService {

	private static Logger logger = Logger.getLogger(PrmHosTargetDataCalculateServiceImpl.class);
	//引入DAO操作
	@Resource(name = "prmHosTargetDataCalculateMapper")
	private final PrmHosTargetDataCalculateMapper prmHosTargetDataCalculateMapper = null;
    
	@Resource(name = "prmTargetMapper")
	private final PrmTargetMapper  prmTargetMapper = null;
	
	@Resource(name = "infoMapper")
	private final InfoMapper infoMapper = null;
	
	@Resource(name = "prmTargetMethodService")
	private final PrmTargetMethodService prmTargetMethodService = null;
	/**
	 * @Description 
	 * 查询结果集0212 院级绩效指标数据表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryPrmHosTargetData(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<PrmHosTargetData> list = prmHosTargetDataCalculateMapper.queryPrmHosTargetData(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<PrmHosTargetData> list = prmHosTargetDataCalculateMapper.queryPrmHosTargetData(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	/**
	 * @Description 
	 * 查询结果集0212 院级绩效指标数据表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryPrmHosTargetPrmTargetData(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub

		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<PrmHosTargetDataCalculate> list = prmHosTargetDataCalculateMapper.queryPrmHosTargetPrmTargetData(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<PrmHosTargetDataCalculate> list = prmHosTargetDataCalculateMapper.queryPrmHosTargetPrmTargetData(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	
	/**
	 * @Description 
	 * 获取对象0212 院级绩效指标数据表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public PrmHosTargetData queryPrmHosTargetDataByCode(Map<String,Object> entityMap)throws DataAccessException{

		
		return prmHosTargetDataCalculateMapper.queryPrmHosTargetDataByCode(entityMap);
	}
	
	@Override
	public String createPrmHosTargetDataCalculate(
			Map<String, Object> entityMap, String paramVo)
			throws DataAccessException {
	
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
		
				mapVo.put("acc_year", paramVo.substring(0, 4));
			    mapVo.put("acc_month", paramVo.substring(4, 6));
			    
			    prmHosTargetDataCalculateMapper.cleanPrmHosTargetDataCalculate(mapVo);
			    int state = 0;
			    
			    /*01院级*/
				entityMap.put("target_nature", "01");
				
				Map<String, List<PrmTarget>> prmTargetMapVo = new HashMap<String,  List<PrmTarget>>();
				
				/*查询全院手动录入基本指标*/
				List<PrmTarget> prmTarget = prmTargetMapper.queryPrmTargetNatureCreateCalculate(entityMap);
						
				
				prmTargetMapVo.put("PrmTarget", prmTarget);  
				
			 	/*查询考核单位*/
		       	
		       	entityMap.put("hos_id", "");
		       	
		       	List<Info> info = infoMapper.queryInfo(entityMap);

				List<Map<String,Object>> listVo = new ArrayList<Map<String,Object>>();
				
		try {
			
		    for(Info Info2:info){
			
			   for (Iterator<String> iterator = prmTargetMapVo.keySet().iterator(); iterator.hasNext();) {
	      			
		   		      String key = iterator.next();
		   		      
		   		      List<PrmTarget> l = prmTargetMapVo.get(key);
		   	
		   	
		   		      for (int i = 0; i < l.size(); i++) {
		   		    	
		   		    	mapVo.put("target_code", l.get(i).getTarget_code().toString());
		   		    	mapVo.put("check_hos_id", Info2.getHos_id());
		   		    	mapVo.put("target_value", 0);
		   		    	mapVo.put("is_audit", 0);
		   		    	mapVo.put("user_code", "");
		   		    	mapVo.put("audit_date", "");	
		   		    	
		   		    	state =  prmHosTargetDataCalculateMapper.addPrmHosTargetDataCalculate(mapVo);
		   		    }
		        		
		         }
			
		    }
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
				
		return "{\"msg\":\"院级指标数据生成成功.\",\"state\":\"true\"}";
	}
	@Override
	public String reAuditPrmHosTargetDataCalculate(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			 
			
			  int state = prmHosTargetDataCalculateMapper.auditPrmHosTargetDataCalculate(entityMap);
				
				return "{\"msg\":\"审核成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				return "{\"error\":\"审核失败 数据库异常 请联系管理员!\"}";

			}	
	}
	@Override
	public String auditPrmHosTargetDataCalculate(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			 
			
			  int state = prmHosTargetDataCalculateMapper.auditPrmHosTargetDataCalculate(entityMap);
				
				return "{\"msg\":\"取消审核成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				return "{\"error\":\"取消审核失败 数据库异常 请联系管理员!\"}";

			}	
	}
	@Override
	public String updateBatchPrmHosTargetDataCalculate(
			List<Map<String, Object>> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		try {
			

			prmHosTargetDataCalculateMapper.updateBatchPrmHosTargetDataCalculate(entityMap);
				
				return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 saveBatchPrmHosTargetDataCalculate\"}";

			}	
	}
	@Override
	public String collectPrmHosTargetCalculate(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		
		List<Map<String, Object>> listMapVo = new ArrayList<Map<String,Object>>();
		
		String resutlJson = "";
		
		String acc_year_month = (String)entityMap.get("acc_year_month");
		
		String check_hos_id = (String)entityMap.get("check_hos_id");
		
		String acc_year = acc_year_month.substring(0, 4);
		
		String acc_month = acc_year_month.substring(4, 6);
		
		entityMap.put("acc_year", acc_year+acc_month);
		
		entityMap.put("acc_month", acc_month);
		
		entityMap.put("check_hos_id", check_hos_id);
		
		entityMap.put("nature_code", "02");
		
		try {
			
			List<PrmHosTargetDataCalculate> prmHosTargetDataCalculateList = prmHosTargetDataCalculateMapper.queryPrmHosTargetPrmTargetData(entityMap);
			
			if(prmHosTargetDataCalculateList.size() > 0 ){
				
				
				for (PrmHosTargetDataCalculate prmHosTargetDataCalculate:prmHosTargetDataCalculateList) {
					

					entityMap.put("target_code", prmHosTargetDataCalculate.getTarget_code());

					entityMap.put("target_value",prmTargetMethodService.target_value(entityMap));
					
					listMapVo.add(entityMap);
					
				}
				
				
				//int i = prmHosTargetDataCalculateMapper.updateBatchPrmHosTargetDataCalculate(listMapVo);
				
				resutlJson =  "{\"msg\":\"计算完成.\",\"state\":\"true\"}";
				
			}else{
				
				resutlJson = "{\"msg\":\"没有计算数据.\",\"state\":\"true\"}";
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
			resutlJson = "{\"error\":\"计算失败 数据库异常 请联系管理员! 错误编码  collectPrmHosTargetCalculate\"}";
		}
		
		return resutlJson;
	}
	
	
	@Override
	public List<Map<String, Object>> queryPrmHosTargetPrmTargetDataPrint(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		
		List<PrmHosTargetDataCalculate> list = prmHosTargetDataCalculateMapper.queryPrmHosTargetPrmTargetData(entityMap);
		
		return JsonListMapUtil.beanToListMap(list);
	}
	
}
