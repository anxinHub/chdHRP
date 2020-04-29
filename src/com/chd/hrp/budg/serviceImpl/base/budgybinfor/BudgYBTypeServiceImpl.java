
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.budg.serviceImpl.base.budgybinfor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.budg.dao.base.budgybinfor.BudgYBTypeMapper;
import com.chd.hrp.budg.service.base.budgybinfor.BudgYBTypeService;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
import com.github.pagehelper.PageInfo;

@Service("budgYBTypeService")
public class BudgYBTypeServiceImpl implements BudgYBTypeService {

	private static Logger logger = Logger.getLogger(BudgYBTypeServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgYBTypeMapper")
	private  BudgYBTypeMapper budgYBTypeMapper = null;
    
	@Resource(name = "sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;
	/**
	 * @Description 
	 * 添加医保类型<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			//获取对象医保类型
			Map<String,Object> mapVo = budgYBTypeMapper.queryByCode(entityMap);
			if (mapVo != null) {
				return "{\"error\":\"医保类型编码已被占用,请重新添加.\"}";
			}
			int count = budgYBTypeMapper.queryNameExist(entityMap);
			if(count > 0){
				return "{\"error\":\"医保类型名称已被占用,请重新添加.\"}";
			}
			budgYBTypeMapper.add(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败  请联系管理员! 方法 add\"}";
		}
		
	}
	/**
	 * @Description 
	 * 批量添加医保类型<BR> (导入时用，其他调用时注意在调用前 校验 编码、名称是否被占用)
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		try {
			budgYBTypeMapper.addBatch(entityList);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchHealthInsuranceType\"}";
		}	
	}
	
	/**
	 * @Description 
	 * 更新医保类型<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		try { 
			
			int count  = budgYBTypeMapper.queryNameExist(entityMap);
			if(count > 0){
				return "{\"error\":\"医保类型名称:" + String.valueOf(entityMap.get("insurance_name"))+ "已被占用.\"}";
			}

			budgYBTypeMapper.update(entityMap);
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
			
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";
		}		
	}
	
	/**
	 * @Description 
	 * 批量更新医保类型<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		try {	
			budgYBTypeMapper.updateBatch(entityList);
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";
		}			
	}
	/**
	 * @Description 
	 * 删除医保类型<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException { 	
		try {
			budgYBTypeMapper.delete(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";
		}		
  }
    
	/**
	 * @Description 
	 * 批量删除医保类型<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		try {
			
			String storeIdStr="";//删除科目，判断业务使用
			String reStr="";
			String superCode="";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("dict_code", "BUDG_YB_TYPE_DICT");
			map.put("group_id", entityList.get(0).get("group_id"));
			map.put("hos_id", entityList.get(0).get("hos_id"));
			map.put("copy_code", entityList.get(0).get("copy_code"));
			map.put("acc_year", "");
			map.put("p_flag", "");//包括子科目
			if(entityList!=null && entityList.size()>0){
				for(Map<String, Object> mapVo : entityList){
					storeIdStr+="'"+mapVo.get("insurance_code")+"',";
					
						map.put("dict_id_str", storeIdStr.substring(0, storeIdStr.length()-1));
						//删除科目时，判断业务表是否使用
						//if(LoadSystemInfo.getHrpMap().get("hrpDatatype").toString().equalsIgnoreCase("oracle")){
						
						sysFunUtilMapper.querySysDictDelCheck(map);
						
						storeIdStr="";
						//}
						if(map.get("reNote")!=null)reStr+=map.get("reNote");
					
				}
			}
			if(reStr!=null && !reStr.equals("")){
				return "{\"error\":\"删除失败，选择的医保类型被以下业务使用：【"+reStr.substring(0,reStr.length()-1)+"】。\",\"state\":\"false\"}";
			}
			budgYBTypeMapper.deleteBatch(entityList);	
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException(e.getMessage());

		}
	}
	
	/**
	 * @Description 
	 * 查询结果集医保类型<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		SysPage sysPage = new SysPage();		
		sysPage = (SysPage) entityMap.get("sysPage");	
		if (sysPage.getTotal()==-1){
			List<?> list = budgYBTypeMapper.query(entityMap);
			return ChdJson.toJson(list);
		}else{	
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());	
			List<?> list = budgYBTypeMapper.query(entityMap, rowBounds);		
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());	
		}
	}
	
	/**
	 * @Description 
	 * 根据主键获得医保类型<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return budgYBTypeMapper.queryByCode(entityMap);
	}
	
	
	
	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 查询医保类型性质是否存在
	 */
	@Override
	public int quryYBTypeNatureExist(Map<String, Object> mapVo) throws DataAccessException {
		return budgYBTypeMapper.quryYBTypeNatureExist(mapVo);
	}
	
	/**
	 * 查询医保类型名称是否被占用
	 */
	@Override
	public int queryNameExist(Map<String, Object> mapVo) throws DataAccessException {
		return budgYBTypeMapper.queryNameExist(mapVo);
	}
	
}
