/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.serviceImpl.commonbuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.StringTool;
import com.chd.hrp.acc.dao.commonbuilder.AccCheckItemMapper;
import com.chd.hrp.acc.dao.commonbuilder.AccCheckTypeMapper;
import com.chd.hrp.acc.dao.wagedata.AccWagePayMapper;
import com.chd.hrp.acc.entity.AccCheckEmpSet;
import com.chd.hrp.acc.entity.AccCheckItem;
import com.chd.hrp.acc.entity.AccCheckType;
import com.chd.hrp.sys.entity.DeptDict;
import com.chd.hrp.acc.service.commonbuilder.AccCheckItemService;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.xpath.internal.operations.And;

/**
* @Title. @Description.
* 核算项<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/
 

@Service("accCheckItemService")
public class AccCheckItemServiceImpl implements AccCheckItemService {

	private static Logger logger = Logger.getLogger(AccCheckItemServiceImpl.class);
	
	@Resource(name = "accCheckItemMapper")
	private final AccCheckItemMapper accCheckItemMapper = null;
	
	@Resource(name = "accCheckTypeMapper")
	private final AccCheckTypeMapper accCheckTypeMapper = null;
	@Resource(name = "sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;
	@Resource(name = "accWagePayMapper")
	private final AccWagePayMapper accWagePayMapper = null;
    
	/**
	 * @Description 
	 * 核算项<BR> 添加AccCheckItem
	 * @param AccCheckItem entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAccCheckItem(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
			Map<String,Object> utilMap=new HashMap<String,Object>();
			utilMap.put("group_id", entityMap.get("group_id"));
			utilMap.put("hos_id", entityMap.get("hos_id"));
			utilMap.put("copy_code", entityMap.get("copy_code"));
			utilMap.put("field_table","acc_check_item");
			utilMap.put("field_key1", "check_type_id");
			utilMap.put("field_value1", entityMap.get("check_type_id"));
			utilMap.put("field_key2", "check_item_code");
			utilMap.put("field_value2", entityMap.get("check_item_code"));
			
			int count = sysFunUtilMapper.existsSysCodeNameByAdd(utilMap);
			if (count >0) {
				return "{\"error\":\"核算项编码：[" + entityMap.get("check_item_code").toString() + "]重复.\"}";
			}
			
			utilMap.put("field_key2", "check_item_name");
			utilMap.put("field_value2", entityMap.get("check_item_name"));
			count = sysFunUtilMapper.existsSysCodeNameByAdd(utilMap);
			if (count >0) {
				return "{\"error\":\"核算项名称：[" + entityMap.get("check_item_name").toString() + "]重复.\"}";
			}
			
			//取最大排序号
			if(entityMap.get("sort_code").equals("系统生成")){
				utilMap.remove("field_key2");
				utilMap.put("field_sort", "sort_code");
				count=sysFunUtilMapper.querySysMaxSort(utilMap);
				entityMap.put("sort_code",count);
			}
			
			accCheckItemMapper.addAccCheckItem(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addAccCheckItem\"}";

		}

	}
	
	/**
	 * @Description 
	 * 核算项<BR> 批量添加AccCheckItem
	 * @param  AccCheckItem entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAccCheckItem(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			for( Map<String,Object> item : entityList ){
				Map<String,Object> utilMap=new HashMap<String,Object>();
				utilMap.put("group_id", item.get("group_id"));
				utilMap.put("hos_id", item.get("hos_id"));
				utilMap.put("copy_code", item.get("copy_code"));
				utilMap.put("field_table","acc_check_item");
				utilMap.put("field_key1", "check_type_id");
				utilMap.put("field_value1", item.get("check_type_id"));
				utilMap.put("field_sort", "sort_code");
				int count= sysFunUtilMapper.querySysMaxSort(utilMap);
				item.put("sort_code",count);
			}
			
			accCheckItemMapper.addBatchAccCheckItem(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchAccCheckItem\"}";

		}
	}
	
	/**
	 * @Description 
	 * 核算项<BR> 查询AccCheckItem分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAccCheckItem(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		List<AccCheckItem> list = accCheckItemMapper.queryAccCheckItem(entityMap, rowBounds);
		PageInfo page = new PageInfo(list);
		return ChdJson.toJson(list, page.getTotal());
	}
	
	/**
	 * @Description 
	 * 系统核算项<BR> 部门查询AccCheckItem分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAccCheckItemDept(Map<String, Object> entityMap)
			throws DataAccessException {
        
		List<AccCheckItem> list = accCheckItemMapper.queryAccCheckItemDept(entityMap);
		
		return ChdJson.toJson(list);
	}
	
	/**
	 * @Description 
	 * 系统核算项<BR> 职工查询AccCheckItem分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAccCheckItemEmp(Map<String, Object> entityMap)
			throws DataAccessException {
		
        List<AccCheckItem> list = accCheckItemMapper.queryAccCheckItemEmp(entityMap);
		
		return ChdJson.toJson(list);
	}
	/**
	 * @Description 
	 * 系统核算项<BR> 项目查询AccCheckItem分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAccCheckItemProj(Map<String, Object> entityMap)
			throws DataAccessException {
        List<AccCheckItem> list = accCheckItemMapper.queryAccCheckItemProj(entityMap);
		
		return ChdJson.toJson(list);
	}
	/**
	 * @Description 
	 * 系统核算项<BR> 客户查询AccCheckItem分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAccCheckItemCus(Map<String, Object> entityMap)
			throws DataAccessException {
        List<AccCheckItem> list = accCheckItemMapper.queryAccCheckItemCus(entityMap);
		
		return ChdJson.toJson(list);
	}
	/**
	 * @Description 
	 * 系统核算项<BR> 库房查询AccCheckItem分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAccCheckItemStore(Map<String, Object> entityMap)
			throws DataAccessException {
        List<AccCheckItem> list = accCheckItemMapper.queryAccCheckItemStore(entityMap);
		
		return ChdJson.toJson(list);
	}
	/**
	 * @Description 
	 * 系统核算项<BR> 供应商查询AccCheckItem分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAccCheckItemSup(Map<String, Object> entityMap)
			throws DataAccessException {
        List<AccCheckItem> list = accCheckItemMapper.queryAccCheckItemSup(entityMap);
		
		return ChdJson.toJson(list);
	}
	/**
	 * @Description 
	 * 系统核算项<BR> 资金来源查询AccCheckItem分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAccCheckItemSource(Map<String, Object> entityMap)
			throws DataAccessException {
        List<AccCheckItem> list = accCheckItemMapper.queryAccCheckItemSource(entityMap);
		
		return ChdJson.toJson(list);
	}
	/**
	 * @Description 
	 * 系统核算项<BR> 单位查询AccCheckItem分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAccCheckItemHos(Map<String, Object> entityMap)
			throws DataAccessException {
        List<AccCheckItem> list = accCheckItemMapper.queryAccCheckItemHos(entityMap);
		
		return ChdJson.toJson(list);
	}
	
	/**
	 * @Description 
	 * 核算项<BR> 查询AccCheckItemByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AccCheckItem queryAccCheckItemByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return accCheckItemMapper.queryAccCheckItemByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 核算项<BR> 批量删除AccCheckItem
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAccCheckItem(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
				
				int count=accCheckItemMapper.existsAccVouchCheckByForeignKey(entityList);
				if(count>0){
					return "{\"error\":\"辅助核算表存在数据，不能删除.\"}";
				}
				
				count=accCheckItemMapper.existsAccLederCheckByForeignKey(entityList);
				if(count>0){
					return "{\"error\":\"辅助核算账表存在数据，不能删除.\"}";
				}
				
				accCheckItemMapper.deleteBatchAccCheckItem(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccCheckItem\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 核算项<BR> 删除AccCheckItem
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteAccCheckItem(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				accCheckItemMapper.deleteAccCheckItem(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteAccCheckItem\"}";

		}
    }
	
	/**
	 * @Description 
	 * 核算项<BR> 更新AccCheckItem
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAccCheckItem(Map<String,Object> entityMap)throws DataAccessException{

		try { 
			
			Map<String,Object> utilMap=new HashMap<String,Object>();
			utilMap.put("group_id", entityMap.get("group_id"));
			utilMap.put("hos_id", entityMap.get("hos_id"));
			utilMap.put("copy_code", entityMap.get("copy_code"));
			utilMap.put("field_table","acc_check_item");
			utilMap.put("field_id", "check_item_id");
			utilMap.put("field_id_value", entityMap.get("check_item_id"));
			utilMap.put("field_key1", "check_type_id");
			utilMap.put("field_value1", entityMap.get("check_type_id"));		
			utilMap.put("field_key2", "check_item_name");
			utilMap.put("field_value2", entityMap.get("check_item_name"));
			int count = sysFunUtilMapper.existsSysCodeNameByUpdate(utilMap);
			if (count >0) {
				return "{\"error\":\"核算项名称：[" + entityMap.get("check_item_name").toString() + "]重复.\"}";
			}
			//判断编码是否重复
			int code = accCheckItemMapper.existsCodeUpdate(entityMap);
			if (code >0) {
				return "{\"error\":\"核算项编码：[" + entityMap.get("check_item_code").toString() + "]重复.\"}";
			}

			accCheckItemMapper.updateAccCheckItem(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccCheckItem\"}";

		}
	}
	
	/**
	 * @Description 
	 * 核算项<BR> 批量更新AccCheckItem
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAccCheckItem(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			accCheckItemMapper.updateBatchAccCheckItem(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccCheckItem\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 核算项<BR> 导入AccCheckItem
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importAccCheckItem(Map<String,Object> entityMap)throws DataAccessException{

		try {

			

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}

	/**
	 * @Description 
	 *继承数据 添加表ACC_CHECK_TYPE、ACC_CASH_ITEM
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String insertBatchAccCheckItem(Map<String, Object> entityMap) throws DataAccessException {
		
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		
		List<Map<String, Object>> mapList = new ArrayList<Map<String,Object>>();
		
		try {
			
//			List<AccCheckType> ackList = accCheckTypeMapper.queryAccCheckTypeExtend(entityMap);
//			
//			List<AccCheckItem> aciList = accCheckItemMapper.queryAccCheckItem(entityMap);
//			
//			if(ackList.size()>0 || aciList.size()>0){
//				
//				return "{\"msg\":\"数据已存在，无法继承.\",\"state\":\"false\"}";
//				
//			}
			
//			List<AccCheckType> accCheckTypeList = accCheckTypeMapper.queryAccCheckTypeByExtend(entityMap);
//			
//			Long maxId = accCheckTypeMapper.queryMaxId().getCheck_type_id();
//			
//			if (accCheckTypeList.size()>0){
//				
//				for (AccCheckType accCheckType : accCheckTypeList) {
//					
//					Long check_type_id = ++maxId;
//					
//					Map< String, Object> map = new HashMap<String, Object>();
//					
//					map.put("check_type_id", check_type_id);
//					
//					map.put("group_id", SessionManager.getGroupId());
//					
//					map.put("hos_id", SessionManager.getHosId());
//					
//					map.put("copy_code", SessionManager.getCopyCode());
//					
//					map.put("check_table","ACC_CHECK_ITEM");
//					
//					map.put("check_type_name", accCheckType.getCheck_type_name());
//					
//					map.put("check_type_sort", accCheckType.getCheck_type_sort());
//					
//					map.put("spell_code", accCheckType.getSpell_code());
//					
//					map.put("wbx_code", accCheckType.getWbx_code());
//					
//					map.put("is_stop", accCheckType.getIs_stop());
//					
//					map.put("is_sys", accCheckType.getIs_sys());
//					
//					map.put("column_id", accCheckType.getColumn_id());
//					
//					map.put("column_no", accCheckType.getColumn_no());
//					
//					map.put("column_code", accCheckType.getColumn_code());
//					
//					map.put("column_name", accCheckType.getColumn_code());
//					
//					map.put("is_change", accCheckType.getIs_change());
//					
//					mapList.add(map);
//					
//					List<AccCheckItem> itemList = accCheckItemMapper.queryItemExtend(accCheckType);
//					
//					if(itemList.size()>0){
//						
//						for(AccCheckItem accCheckItem : itemList) {
//							
//							Map< String, Object> itemMap = new HashMap<String, Object>();
//							
//							itemMap.put("check_item_id", accCheckItem.getCheck_item_id());
//							
//							itemMap.put("check_type_id", check_type_id);
//							
//							itemMap.put("group_id", SessionManager.getGroupId());
//							
//							itemMap.put("hos_id", SessionManager.getHosId());
//							
//							itemMap.put("copy_code", SessionManager.getCopyCode());
//							
//							itemMap.put("check_item_code", accCheckItem.getCheck_item_code());
//							
//							itemMap.put("check_item_name", accCheckItem.getCheck_item_name());
//							
//							itemMap.put("check_item_sort", accCheckItem.getCheck_item_sort());
//							
//							itemMap.put("spell_code", accCheckItem.getSpell_code());
//							
//							itemMap.put("wbx_code", accCheckItem.getWbx_code());
//							
//							itemMap.put("is_stop", accCheckItem.getIs_stop());
//							
//							
//							list.add(itemMap);
//						}
//					}
//				}
//				accCheckTypeMapper.insertBatchAccCheckType(mapList);
//				
//				accCheckItemMapper.addBatchAccCheckItem(list);
//			}else{
//				
//				return "{\"msg\":\"没有可继承数据.\",\"state\":\"false\"}";
//				
//			}
//			
			int count=accCheckItemMapper.addBatchAccCheckItemBySelect(entityMap);
			return "{\"msg\":\"继承数据"+count+"条.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);

			return "{\"error\":\"继承数据失败 数据库异常 请联系管理员! 错误编码  insertBatchAccCheckItem{className}\"}";
		}
		
	}

	public int existsCodeUpdate(Map<String, Object> mapVo) throws DataAccessException {
		return accCheckItemMapper.existsCodeUpdate(mapVo);
	}
	
	public int existsNameAdd(Map<String, Object> mapVo) throws DataAccessException{
		return accCheckItemMapper.existsNameAdd(mapVo);
	}
	/**
	 * 查验核算项编码或名称是否重复
	 * @param mapVo
	 * @return
	 */
	public String checkItemCodeOrNameRepeat(Map<String, Object> mapVo) {
		int state=0;
		String note="";
		//根据编码查询数据 
		List<Map<String, Object>> itemsByCode=accCheckItemMapper.queryAccCheckItemsByCode(mapVo);
		//根据类型和名称查询数据
		List<Map<String, Object>> itemsByTypeAndNameList=accCheckItemMapper.queryAccCheckItemsByTypeAndName(mapVo);
		//state有三种状态  0 代表没有数据重复 1代表code重复 2代表name重复 3代表code,name都重复
		state=itemsByCode!=null && itemsByCode.size()>0?state+1:state;
		state=itemsByTypeAndNameList!=null && itemsByTypeAndNameList.size()>0?state+2:state;
		switch (state) {
		case 0:
			note="核算项编码,及相同核算类型下核算名称均不重复";
			break;
		case 1:
			note="核算项编码重复";
			break;
		case 2:
			note="相同核算类型下核算名称重复";
			break;
		case 3:
			note="核算项编码,及相同核算类型下核算名称均重复";
			break;
		}
		return "{\"state\":"+state+",\"note\":\""+note+"\"}";
	}

	@Override
	public List<Map<String, Object>> queryAccCheckItemPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = accCheckItemMapper.queryAccCheckItemPrint(entityMap);
		return list;
	}

	public String queryAccCheckEmpSet(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		
		List<?> list = accCheckItemMapper.queryAccCheckEmpSet(entityMap);
		
		return ChdJson.toJson(list);
	}

	@Override
	public String saveAccCheckEmpSet(Map<String, Object> entityMap) throws DataAccessException {
		try {
			AccCheckEmpSet accCheckEmpSet=accCheckItemMapper.queryAccCheckEmpSetByCode(entityMap);
			if(accCheckEmpSet !=null){
				return "{\"error\":\"属性编码重复.\"}";
			}
			if(entityMap.get("attr_code") == null || "".equals(entityMap.get("attr_code"))){
				return "{\"error\":\"属性编码\",\"state\":\"false\"}";
			}
			entityMap.put("wbx_code",StringTool.toWuBi(entityMap.get("attr_name").toString()));
			entityMap.put("spell_code",StringTool.toPinyinShouZiMu(entityMap.get("attr_name").toString()));

			if(entityMap.get("old_code") != null && !"".equals(entityMap.get("old_code").toString())){
				//修改数据
				accCheckItemMapper.update(entityMap);
			}else{
				//新增数据
				accCheckItemMapper.add(entityMap);
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException("添加失败");
		}
		
		return "{\"msg1\":\"添加成功.\",\"state\":\"true\",\"group_id\":\""
			+entityMap.get("group_id").toString()
			+"\",\"hos_id\":\""+entityMap.get("hos_id").toString()
			+"\",\"old_code\":\""+entityMap.get("attr_code").toString()
			+"\"}";
	}

	@Override
	public String deleteAccCheckEmpSet(List<Map<String, Object>> entityList) throws DataAccessException {
		try {	
			int count=accWagePayMapper.existsAccCheckEmpPayByCode(entityList);
			if(count>0){
				return "{\"error\":\"选中的职工属性在工资发放表存在数据，不能删除.\"}";
			}
			int countEmp=accCheckItemMapper.existsAccCheckItemEmpPayByCode(entityList);
			if(countEmp>0){
				return "{\"error\":\"选中的职工属性在职工字典属性表存在数据，不能删除.\"}";
			}
			//批量表数据
			accCheckItemMapper.deleteAccCheckEmpSet(entityList);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException("删除失败");
		}	
		
		return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
	}

	

	

	

}
