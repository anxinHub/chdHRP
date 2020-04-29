/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.sys.serviceImpl.baseData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.commonbuilder.AccSubjMapper;
import com.chd.hrp.acc.entity.AccCheckType;
import com.chd.hrp.acc.entity.AccSubj;
import com.chd.hrp.sys.dao.baseData.SysAccCheckItemMapper;
import com.chd.hrp.sys.dao.baseData.SysAccCheckTypeMapper;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
import com.chd.hrp.sys.service.baseData.SysAccCheckTypeService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 核算类<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("sysAccCheckTypeService")
public class SysAccCheckTypeServiceImpl implements SysAccCheckTypeService {

	private static Logger logger = Logger.getLogger(SysAccCheckTypeServiceImpl.class);
	
	@Resource(name = "sysAccCheckTypeMapper")
	private final SysAccCheckTypeMapper sysAccCheckTypeMapper = null;
	@Resource(name = "sysAccCheckItemMapper")
	private final SysAccCheckItemMapper sysAccCheckItemMapper = null;
	@Resource(name = "accSubjMapper")
	private final AccSubjMapper accSubjMapper = null;
	@Resource(name = "sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;
	
	//获取核算类Map
	public Map<Object,Object> getCheckTypeMap(){
		Map<Object,Object> map = new HashMap<Object,Object>();
		Map<String, Object> mapVo = new HashMap<String, Object>();
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
        mapVo.put("copy_code", SessionManager.getCopyCode());
        List<AccCheckType> list = sysAccCheckTypeMapper.queryAccCheckType(mapVo);
        for(AccCheckType obj : list){
        	map.put(obj.getCheck_type_id(), obj);
        }
        return map;
	}
    
	/**
	 * @Description 
	 * 核算类<BR> 添加AccCheckType
	 * @param AccCheckType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	@Transactional(rollbackFor=Exception.class)
	public String addAccCheckType(Map<String,Object> entityMap)throws DataAccessException{
		
		
			Map<String,Object> utilMap=new HashMap<String,Object>();
			utilMap.put("group_id", entityMap.get("group_id"));
			utilMap.put("hos_id", entityMap.get("hos_id"));
			utilMap.put("copy_code", entityMap.get("copy_code"));
			utilMap.put("field_table","acc_check_type");
			utilMap.put("field_key1", "check_type_name");
			utilMap.put("field_value1", entityMap.get("check_type_name"));
			utilMap.put("field_key2", "check_type_code");
			utilMap.put("field_value2", entityMap.get("check_type_code"));
			
			AccCheckType accCheckType = queryAccCheckTypeByName(entityMap);

			if (accCheckType != null ) {

				return "{\"error\":\"核算类名称：" + entityMap.get("check_type_name").toString() + "重复.\"}";
			}
			int code = sysAccCheckTypeMapper.existCode(entityMap);
			if (code> 0) {

				return "{\"error\":\"核算类编码:" + entityMap.get("check_type_code").toString() + "重复.\"}";
			}
			
			//取最大排序号
			if(entityMap.get("sort_code").equals("系统生成")){
				utilMap.remove("field_key1");
				utilMap.remove("field_key2");
				utilMap.put("field_sort", "sort_code");
				int count =sysFunUtilMapper.querySysMaxSort(utilMap);
				entityMap.put("sort_code",count);
			}
			
			
			
//			for (Map.Entry<String,Object> entry : entityMap.entrySet()) {  
//	            System.out.println("key = " + entry.getKey() + " and value = " + entry.getValue());  
//	        }   
			int state=sysAccCheckTypeMapper.addAccCheckType(entityMap);
			
			if(state>0){
				//添加辅助核算类时，动态增加业务表核算类型字段
				//if(LoadSystemInfo.getHrpMap().get("hrpDatatype").toString().equalsIgnoreCase("oracle")){
				sysAccCheckTypeMapper.queryAccCheckTypeByAlter(entityMap);
				//}
				return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			}else{
				return "{\"error\":\"添加失败.\",\"state\":\"false\"}";
			}
			
			
		

	}
	
	/**
	 * @Description 
	 * 核算类<BR> 批量添加AccCheckType
	 * @param  AccCheckType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAccCheckType(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			sysAccCheckTypeMapper.addBatchAccCheckType(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchAccCheckType\"}";

		}
	}
	
	/**
	 * @Description 
	 * 核算类<BR> 查询AccCheckType分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAccCheckType(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		List<AccCheckType> list = sysAccCheckTypeMapper.queryAccCheckType(entityMap, rowBounds);
		PageInfo page = new PageInfo(list);
		return ChdJson.toJson(list, page.getTotal());
		
	}
	
	/**
	 * @Description 
	 * 核算类<BR> 查询AccCheckTypeByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AccCheckType queryAccCheckTypeByName(Map<String,Object> entityMap)throws DataAccessException{
		
		return sysAccCheckTypeMapper.queryAccCheckTypeByName(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 核算类<BR> 查询AccCheckTypeByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AccCheckType queryAccCheckTypeById(Map<String,Object> entityMap)throws DataAccessException{
		
		return sysAccCheckTypeMapper.queryAccCheckTypeById(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 核算类<BR> 批量删除AccCheckType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAccCheckType(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = sysAccCheckTypeMapper.deleteBatchAccCheckType(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccCheckType\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 核算类<BR> 删除AccCheckType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteAccCheckType(Map<String, Object> entityMap) throws DataAccessException {
		
//		for (Map.Entry<String,Object> entry : entityMap.entrySet()) {  
//        System.out.println("key = " + entry.getKey() + " and value = " + entry.getValue());  
//		}  
		try {
			int count = sysAccCheckItemMapper.queryAccCheckItemByCount(entityMap);
			if(count>0){
				return "{\"error\":\"[" + entityMap.get("check_type_name").toString() + "]有核算项，不能删除.\"}"; 
			}
			
			List<AccSubj> accSubjList=new ArrayList<AccSubj>();
			accSubjList=accSubjMapper.queryAccSubjCheck(entityMap);
			
			if(accSubjList!=null && accSubjList.size()>0){
				StringBuilder sb=new StringBuilder();
				for(AccSubj accSubj:accSubjList){
					sb.append("["+accSubj.getSubj_code()+" "+accSubj.getSubj_name()+"] ");
				}
				return "{\"error\":\"" + sb.toString() + "挂了辅助核算，不能删除.\"}"; 
			}
			
			sysAccCheckTypeMapper.deleteAccCheckType(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteAccCheckType\"}";

		}
    }
	
	/**
	 * @Description 
	 * 核算类<BR> 更新AccCheckType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAccCheckType(Map<String,Object> entityMap)throws DataAccessException{
		
		try {

			AccCheckType accCheckType = queryAccCheckTypeByName(entityMap);

			if (accCheckType != null ) {

				return "{\"error\":\"核算类名称：" + entityMap.get("check_type_name").toString() + "重复.\"}";
			}
			int code = sysAccCheckTypeMapper.existCode(entityMap);
			if (code> 0) {

				return "{\"error\":\"核算类编码:" + entityMap.get("check_type_code").toString() + "重复.\"}";
			}
			
			sysAccCheckTypeMapper.updateAccCheckType(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccCheckType\"}";

		}
	}
	
	/**
	 * @Description 
	 * 核算类<BR> 批量更新AccCheckType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAccCheckType(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			sysAccCheckTypeMapper.updateBatchAccCheckType(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccCheckType\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 核算类<BR> 导入AccCheckType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importAccCheckType(Map<String,Object> entityMap)throws DataAccessException{

		try {

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}

	@Override
	public String queryAccCheckTypeByMenu(Map<String, Object> entityMap)
			throws DataAccessException {
		StringBuilder jsonResult = new StringBuilder();
		jsonResult.append("{Rows:[");
		List<AccCheckType> groupDictList = sysAccCheckTypeMapper.queryAccCheckType(entityMap);
		if (groupDictList.size()>0) {
			jsonResult.append("{id:'00',pId:'0',name:'系统核算',open:true},");
			jsonResult.append("{id:'01',pId:'0',name:'自定义核算'},");
			int row = 0;
			for (AccCheckType groupDict : groupDictList) {
				if (row == 0) {
					jsonResult.append("{");
				} else {
					jsonResult.append(",{");
				}
				row++;
				jsonResult.append("id:'" + groupDict.getCheck_type_id() + "',");
				if(groupDict.getIs_sys() == 1){
					jsonResult.append("pId:'00',");
				}else{
					jsonResult.append("pId:'01',");
				}
				jsonResult.append("is_sys:'" + groupDict.getIs_sys() + "',");
				jsonResult.append("group_id:'" + groupDict.getGroup_id() + "',");
				jsonResult.append("hos_id:'" + groupDict.getHos_id() + "',");
				jsonResult.append("copy_code:'" + groupDict.getCopy_code() + "',");
				jsonResult.append("name:'"+groupDict.getCheck_type_name()+ "',");
				//jsonResult.append("check_type_code:'"+groupDict.getCheck_type_code()+ "'");
				jsonResult.append("}");
			}
		}
		jsonResult.append("]}");
		return jsonResult.toString();
	}

	@Override
	public String queryAccCheckTypeBySelect(Map<String, Object> entityMap)
			throws DataAccessException {
		return JSON.toJSONString(sysAccCheckTypeMapper.queryAccCheckTypeBySelect(entityMap));
	}
	
	/**
	 * 查询往来类型
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryCheckTable(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return JSON.toJSONString(sysAccCheckTypeMapper.queryCheckTable(entityMap));
	}

	
	public String queryCheckTypeBySubjId(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		if(entityMap.get("acc_year") == null){
			
			entityMap.put("acc_year", SessionManager.getAcctYear());
	        
		}
		return JSON.toJSONString(sysAccCheckTypeMapper.queryCheckTypeBySubjId(entityMap));
	}

	@Override
	public AccCheckType queryCheckType(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return sysAccCheckTypeMapper.queryCheckType(entityMap);
	}

	@Override
	public AccCheckType queryCheckColumn(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return sysAccCheckTypeMapper.queryCheckColumn(entityMap);
	}

}
