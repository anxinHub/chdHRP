/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.serviceImpl.commonbuilder;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.commonbuilder.AccCashTypeMapper;
import com.chd.hrp.acc.entity.AccCashType;
import com.chd.hrp.acc.service.commonbuilder.AccCashTypeService;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 现金流量类别<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("accCashTypeService")
public class AccCashTypeServiceImpl implements AccCashTypeService {

	private static Logger logger = Logger.getLogger(AccCashTypeServiceImpl.class);
	
	@Resource(name = "accCashTypeMapper")
	private final AccCashTypeMapper accCashTypeMapper = null;
    
	@Resource(name = "sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;
	/**
	 * @Description 
	 * 现金流量类别<BR> 添加AccCashType
	 * @param AccCashType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAccCashType(Map<String,Object> entityMap)throws DataAccessException{
		
		AccCashType accCashType = accCashTypeMapper.queryAccCashTypeByCodeName(entityMap);

		if (accCashType != null) {
			
		//	return "{\"error\":\"核算项编码：[" + entityMap.get("cash_type_code").toString() + "]重复.\"}";
	
		//	return "{\"error\":\"核算项编码：" + entityMap.get("cash_type_code").toString() + "重复.\"}";
			
			return "{\"error\":\"核算项名称" + entityMap.get("cash_type_code").toString() + "重复.\"}";
		}
		
		try {
			
			accCashTypeMapper.addAccCashType(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addAccCashType\"}";

		}

	}
	
	/**
	 * @Description 
	 * 现金流量类别<BR> 批量添加AccCashType
	 * @param  AccCashType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAccCashType(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			accCashTypeMapper.addBatchAccCashType(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchAccCashType\"}";

		}
	}
	
	/**
	 * @Description 
	 * 现金流量类别<BR> 查询AccCashType分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAccCashType(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		List<AccCashType> list = accCashTypeMapper.queryAccCashType(entityMap, rowBounds);
		PageInfo page = new PageInfo(list);
		return ChdJson.toJson(list, page.getTotal());
		
	}
	
	/**
	 * @Description 
	 * 现金流量类别<BR> 查询AccCashTypeByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AccCashType queryAccCashTypeByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return accCashTypeMapper.queryAccCashTypeByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 现金流量类别<BR> 批量删除AccCashType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAccCashType(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			String storeIdStr="";//删除时，判断业务使用
			String reStr="";
			String superCode="";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("dict_code", "ACC_CASH_TYPE");
			map.put("group_id", entityList.get(0).get("group_id"));
			map.put("hos_id", entityList.get(0).get("hos_id"));
			map.put("copy_code", "");
			map.put("acc_year", "");
			map.put("p_flag", "");//包括子科目
			if(entityList!=null && entityList.size()>0){
				for(Map<String, Object> mapVo : entityList){
					storeIdStr+=mapVo.get("cash_type_id")+",";
					
						map.put("dict_id_str", storeIdStr.substring(0, storeIdStr.length()-1));
						//删除时，判断业务表是否使用
						//if(LoadSystemInfo.getHrpMap().get("hrpDatatype").toString().equalsIgnoreCase("oracle")){
						
						sysFunUtilMapper.querySysDictDelCheck(map);
						
						storeIdStr="";
						//}
						if(map.get("reNote")!=null)reStr+=map.get("reNote");
					
				}
			}
			if(reStr!=null && !reStr.equals("")){
				return "{\"error\":\"删除失败，选择的现金流量类别被以下业务使用：【"+reStr.substring(0,reStr.length()-1)+"】。\",\"state\":\"false\"}";
			}
				accCashTypeMapper.deleteBatchAccCashType(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	
	/**
	 * @Description 
	 * 现金流量类别<BR> 删除AccCashType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteAccCashType(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				accCashTypeMapper.deleteAccCashType(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteAccCashType\"}";

		}
    }
	
	/**
	 * @Description 
	 * 现金流量类别<BR> 更新AccCashType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAccCashType(Map<String,Object> entityMap)throws DataAccessException{

		AccCashType accCashType = accCashTypeMapper.queryAccCashTypeByName(entityMap);

		if (accCashType != null) {
			
			return "{\"msg\":\"核算项名称" + entityMap.get("cash_type_name").toString() + "重复..\",\"state\":\"disable\"}";
		}
		
		try {
			
			accCashTypeMapper.updateAccCashType(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccCashType\"}";

		}
	}
	
	/**
	 * @Description 
	 * 现金流量类别<BR> 批量更新AccCashType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAccCashType(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			accCashTypeMapper.updateBatchAccCashType(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccCashType\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 现金流量类别<BR> 导入AccCashType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importAccCashType(Map<String,Object> entityMap)throws DataAccessException{

		try {

			

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}

	@Override
	public String queryAccCashTypeByMenu(Map<String, Object> entityMap)
			throws DataAccessException {
		StringBuilder jsonResult = new StringBuilder();
		jsonResult.append("{Rows:[");
		List<AccCashType> groupDictList = accCashTypeMapper.queryAccCashType(entityMap);
		if (groupDictList.size()>0) {
			int row = 0;
			for (AccCashType groupDict : groupDictList) {
				if (row == 0) {
					jsonResult.append("{");
				} else {
					jsonResult.append(",{");
				}
				row++;
				jsonResult.append("id:" + groupDict.getCash_type_id() + ",");
				jsonResult.append("group_id:'" + groupDict.getGroup_id() + "',");
				jsonResult.append("hos_id:'" + groupDict.getHos_id() + "',");
				jsonResult.append("copy_code:'" + groupDict.getCopy_code() + "',");
				jsonResult.append("name:'"+groupDict.getCash_type_code()+" " + groupDict.getCash_type_name()+(groupDict.getIs_stop()==1?"(停用)":"")+"',");
				jsonResult.append("}");
			}
		}
		jsonResult.append("]}");
		return jsonResult.toString();
	}
}
