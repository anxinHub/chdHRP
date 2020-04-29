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
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.commonbuilder.AccCheckItemMapper;
import com.chd.hrp.acc.dao.commonbuilder.AccCheckItemTypeMapper;
import com.chd.hrp.acc.dao.commonbuilder.AccSubjMapper;
import com.chd.hrp.acc.entity.AccCheckItemType;
import com.chd.hrp.acc.entity.AccSubj;
import com.chd.hrp.acc.service.commonbuilder.AccCheckItemTypeService;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 核算分类<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("accCheckItemTypeService")
public class AccCheckItemTypeServiceImpl implements AccCheckItemTypeService {

	private static Logger logger = Logger.getLogger(AccCheckItemTypeServiceImpl.class);
	
	@Resource(name = "accCheckItemTypeMapper")
	private final AccCheckItemTypeMapper accCheckItemTypeMapper = null;
	@Resource(name = "accCheckItemMapper")
	private final AccCheckItemMapper accCheckItemMapper = null;
	@Resource(name = "accSubjMapper")
	private final AccSubjMapper accSubjMapper = null;
	@Resource(name = "sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;
	
	//获取核算分类Map
	public Map<Object,Object> getCheckTypeMap(){
		Map<Object,Object> map = new HashMap<Object,Object>();
		Map<String, Object> mapVo = new HashMap<String, Object>();
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
        mapVo.put("copy_code", SessionManager.getCopyCode());
        List<AccCheckItemType> list = accCheckItemTypeMapper.queryAccCheckItemType(mapVo);
        for(AccCheckItemType obj : list){
        	map.put(obj.getCheck_type_id(), obj);
        }
        return map;
	}
    
	/**
	 * @Description 
	 * 核算分类<BR> 添加AccCheckItemType
	 * @param AccCheckItemType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	@Transactional(rollbackFor=Exception.class)
	public String addAccCheckItemType(Map<String,Object> entityMap)throws DataAccessException{
		
		
			Map<String,Object> utilMap=new HashMap<String,Object>();
			utilMap.put("group_id", entityMap.get("group_id"));
			utilMap.put("hos_id", entityMap.get("hos_id"));
			utilMap.put("copy_code", entityMap.get("copy_code"));
			utilMap.put("field_table","acc_check_item_type");
			utilMap.put("field_key1", "check_type_id");
			utilMap.put("field_value1", entityMap.get("check_type_id"));
			
			utilMap.put("field_key2", "type_name");
			utilMap.put("field_value2", entityMap.get("type_name").toString());
			
			
			int count = sysFunUtilMapper.existsSysCodeNameByAdd(utilMap);
			if (count>0) {
				return "{\"error\":\"核算分类名称：[" + entityMap.get("type_name").toString() + "]重复.\"}";
			}
			
			utilMap.put("field_key2", "type_code");
			utilMap.put("field_value2", entityMap.get("type_code"));
			count = sysFunUtilMapper.existsSysCodeNameByAdd(utilMap);
			if (count>0) {
				return "{\"error\":\"核算分类编码：[" + entityMap.get("type_code").toString() + "]重复.\"}";
			}
			
			//取最大排序号
			if(entityMap.get("sort_code").equals("系统生成")){
				utilMap.remove("field_key1");
				utilMap.remove("field_key2");
				utilMap.put("field_sort", "sort_code");
				count=sysFunUtilMapper.querySysMaxSort(utilMap);
				entityMap.put("sort_code",count);
			}
			
			int state = accCheckItemTypeMapper.addAccCheckItemType(entityMap);
			
			if(state>0){
				return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			}else{
				return "{\"error\":\"添加失败.\",\"state\":\"false\"}";
			}
	}
	
	/**
	 * @Description 
	 * 核算分类<BR> 批量添加AccCheckItemType
	 * @param  AccCheckItemType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAccCheckItemType(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			accCheckItemTypeMapper.addBatchAccCheckItemType(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchAccCheckItemType\"}";

		}
	}
	
	/**
	 * @Description 
	 * 核算分类<BR> 查询AccCheckItemType分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAccCheckItemType(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		List<AccCheckItemType> list = accCheckItemTypeMapper.queryAccCheckItemType(entityMap, rowBounds);
		PageInfo page = new PageInfo(list);
		return ChdJson.toJson(list, page.getTotal());
		
	}
	
	/**
	 * @Description 
	 * 核算分类<BR> 查询AccCheckItemTypeByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public int queryAccCheckItemTypeByName(Map<String,Object> entityMap)throws DataAccessException{
		
		return accCheckItemTypeMapper.queryAccCheckItemTypeByName(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 核算分类<BR> 查询AccCheckItemTypeByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AccCheckItemType queryAccCheckItemTypeById(Map<String,Object> entityMap)throws DataAccessException{
		
		return accCheckItemTypeMapper.queryAccCheckItemTypeById(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 核算分类<BR> 批量删除AccCheckItemType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAccCheckItemType(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = accCheckItemTypeMapper.deleteBatchAccCheckItemType(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccCheckItemType\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 核算分类<BR> 删除AccCheckItemType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteAccCheckItemType(List<Map<String, Object>> listVo) throws DataAccessException {
		
		try {
			for(Map<String,Object> item : listVo){
				int count = accCheckItemTypeMapper.queryIsUsed(item);
				if(count>0){
					return "{\"error\":\"[分类编码:" + item.get("type_code").toString() + "]正在被引用,不能删除.\"}"; 
				}
				
				accCheckItemTypeMapper.deleteAccCheckItemType(item);
			}
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteAccCheckItemType\"}";

		}
    }
	
	/**
	 * @Description 
	 * 核算分类<BR> 更新AccCheckItemType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAccCheckItemType(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
			if(!String.valueOf(entityMap.get("type_code")).equals(String.valueOf(entityMap.get("type_codeOld")))){
				AccCheckItemType accCheckItemType  = queryAccCheckItemTypeByCode(entityMap);
				if (accCheckItemType != null) {
					return "{\"error\":\"编码：" + entityMap.get("type_code").toString() + "已存在.\"}";
				}
			}
			int count = queryAccCheckItemTypeByName(entityMap);

			if (count > 0) {

				return "{\"error\":\"名称：" + entityMap.get("type_name").toString() + "已存在.\"}";
			}
			
			accCheckItemTypeMapper.updateAccCheckItemType(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccCheckItemType\"}";

		}
	}
	/**
	 * 查询 核算分类 编码是否已存在
	 * @param entityMap
	 * @return
	 */
	private AccCheckItemType queryAccCheckItemTypeByCode(Map<String, Object> entityMap) {
		return accCheckItemTypeMapper.queryAccCheckItemTypeByCode(entityMap);
	}

	/**
	 * @Description 
	 * 核算分类<BR> 批量更新AccCheckItemType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAccCheckItemType(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			accCheckItemTypeMapper.updateBatchAccCheckItemType(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccCheckItemType\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 核算分类<BR> 导入AccCheckItemType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importAccCheckItemType(Map<String,Object> entityMap)throws DataAccessException{

		try {

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}

	@Override
	public String queryAccCheckItemTypeBySelect(Map<String, Object> entityMap)
			throws DataAccessException {
		return JSON.toJSONString(accCheckItemTypeMapper.queryAccCheckItemTypeBySelect(entityMap));
	}
	
	/**
	 * 查询往来类型
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryCheckTable(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return JSON.toJSONString(accCheckItemTypeMapper.queryCheckTable(entityMap));
	}

	
	public String queryCheckTypeBySubjId(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return JSON.toJSONString(accCheckItemTypeMapper.queryCheckTypeBySubjId(entityMap));
	}

	@Override
	public List<Map<String, Object>> queryAccCheckItemTypePrint(Map<String, Object> entityMap) throws DataAccessException {
		
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = accCheckItemTypeMapper.queryAccCheckItemTypePrint(entityMap);
		return list;
	}


}
