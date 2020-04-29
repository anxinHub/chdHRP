/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.serviceImpl.wagedata;

import java.util.ArrayList;
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
import com.chd.hrp.acc.dao.AccWageItemsMapper;
import com.chd.hrp.acc.dao.wagedata.AccWageSchemeItemMapper;
import com.chd.hrp.acc.dao.wagedata.AccWageSchemeKindMapper;
import com.chd.hrp.acc.dao.wagedata.AccWageSchemeMapper;
import com.chd.hrp.acc.entity.AccWageItems;
import com.chd.hrp.acc.entity.AccWageScheme;
import com.chd.hrp.acc.entity.AccWageSchemeKind;
import com.chd.hrp.acc.service.wagedata.AccWageSchemeService;
import com.chd.hrp.sys.dao.EmpDictMapper;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 工资方案<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("accWageSchemeService")
public class AccWageSchemeServiceImpl implements AccWageSchemeService {

	private static Logger logger = Logger.getLogger(AccWageSchemeServiceImpl.class);
	
	@Resource(name = "accWageSchemeMapper")
	private final AccWageSchemeMapper accWageSchemeMapper = null;
	
	@Resource(name = "accWageItemsMapper")
	private final AccWageItemsMapper accWageItemsMapper = null;
	
	@Resource(name = "accWageSchemeKindMapper")
	private final AccWageSchemeKindMapper accWageSchemeKindMapper = null;
	
	@Resource(name = "accWageSchemeItemMapper")
	private final AccWageSchemeItemMapper accWageSchemeItemMapper = null;
	
	@Resource(name = "empDictMapper")
	private final EmpDictMapper empDictMapper = null;
    
	@Resource(name = "sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;
	
	/**
	 * @Description 
	 * 工资方案<BR> 添加AccWageScheme
	 * @param AccWageScheme entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAccWageScheme(Map<String,Object> entityMap)throws DataAccessException{
		AccWageScheme accWageScheme = queryAccWageSchemeByCode(entityMap);
		if (accWageScheme != null) {
			return "{\"error\":\"编码：" + entityMap.get("wage_code").toString() + "重复.\"}";
		}
		
		try {
			accWageSchemeMapper.addAccWageScheme(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addAccWageScheme\"}");
		}

	}
	
	/**
	 * @Description 
	 * 工资方案<BR> 批量添加AccWageScheme
	 * @param  AccWageScheme entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAccWageScheme(List<Map<String,Object>> entityList)throws DataAccessException{
		try {
			accWageSchemeMapper.addBatchAccWageScheme(entityList);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchAccWageScheme\"}");
		}
	}
	
	/**
	 * @Description 
	 * 工资方案<BR> 查询AccWageScheme分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAccWageScheme(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage)entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<AccWageScheme> list = accWageSchemeMapper.queryAccWageScheme(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AccWageScheme> list = accWageSchemeMapper.queryAccWageScheme(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list,page.getTotal());
		}
		
	}
	
	/**
	 * @Description 
	 * 工资方案<BR> 查询AccWageSchemeByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AccWageScheme queryAccWageSchemeByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return accWageSchemeMapper.queryAccWageSchemeByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 工资方案<BR> 批量删除AccWageScheme
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAccWageScheme(List<Map<String,Object>> entityList)throws DataAccessException{
		try {
				accWageSchemeMapper.deleteBatchAccWageScheme(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccWageScheme\"}");
		}
		
	}
	
	/**
	 * @Description 工资方案<BR>
	 *              删除AccWageScheme
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteAccWageScheme(Map<String, Object> entityMap) throws DataAccessException {
		try {
		//	AccWageScheme scheme = accWageSchemeMapper.queryAccWageSchemeByCode(entityMap);
		//	entityMap.put("scheme_id", scheme.getScheme_id());
			accWageSchemeKindMapper.deleteAccWageSchemeKind(entityMap);//工资方案不再与职工直接关联
			accWageSchemeItemMapper.deleteAccWageSchemeItem(entityMap);
			accWageSchemeKindMapper.deleteAccWageSKindBySchemeId(entityMap);// 工资方案与职工分类关联
			accWageSchemeMapper.deleteAccWageScheme(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败\"}");
		}
	}
	
	/**
	 * @Description 
	 * 工资方案<BR> 更新AccWageScheme
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAccWageScheme(Map<String,Object> entityMap)throws DataAccessException{
		try {
			accWageSchemeMapper.updateAccWageScheme(entityMap);
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccWageScheme\"}");
		}
	}
	
	/**
	 * @Description 
	 * 工资方案<BR> 批量更新AccWageScheme
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAccWageScheme(List<Map<String,Object>> entityList)throws DataAccessException{
		try {
			accWageSchemeMapper.updateBatchAccWageScheme(entityList);
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccWageScheme\"}");
		}
	}
	
	/**
	 * @Description 
	 * 工资方案<BR> 导入AccWageScheme
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importAccWageScheme(Map<String,Object> entityMap)throws DataAccessException{

		try {
			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}

	@Override
	public String queryAccWageSchemeByMenu(Map<String, Object> entityMap)
			throws DataAccessException {
		
		StringBuilder jsonResult = new StringBuilder();
		
		jsonResult.append("{Rows:[");
		
		List<AccWageScheme> groupDictList = accWageSchemeMapper.queryAccWageScheme(entityMap);
		
		if (groupDictList.size()>0) {
			
			int row = 0;
			
			for (AccWageScheme groupDict : groupDictList) {
				
				if (row == 0) {
					
					jsonResult.append("{");
					
				} else {
					
					jsonResult.append(",{");
					
				}
				
				row++;
				
				jsonResult.append("id:'" + groupDict.getScheme_id() + "',");
				
				jsonResult.append("group_id:'" + groupDict.getGroup_id() + "',");
				
				jsonResult.append("hos_id:'" + groupDict.getHos_id() + "',");
				
				jsonResult.append("copy_code:'" + groupDict.getCopy_code() + "',");
				
				jsonResult.append("name:'"+groupDict.getScheme_name()+ "',");
				
				jsonResult.append("}");
			}
		}
		jsonResult.append("]}");
		return jsonResult.toString();
	}

	@Override
	public String queryAccWageSchemeByEmpList(Map<String, Object> entityMap)
			throws DataAccessException {
		
		return null;
	}


	@Override
	public String queryAccWageSchemeByWageList(Map<String, Object> entityMap)
			throws DataAccessException {
		
		
		return null;
	}

	@Override
	public String queryAccWageBySchemeList(Map<String, Object> entityMap)
			throws DataAccessException {
		
		StringBuilder jsonResult = new StringBuilder();
		
		jsonResult.append("[");
		
		List<AccWageItems> wageItemList = accWageItemsMapper.queryAccWageItems(entityMap);
		
		if (wageItemList.size()>0) {
			
			int row = 0;
			
			jsonResult.append("{\"id\":\"value=sex\",\"text\":\"职工性别\"},");
			
			jsonResult.append("{\"id\":\"value=kind_code\",\"text\":\"职工分类\"},");
			
			jsonResult.append("{\"id\":\"value=dept_code\",\"text\":\"部门编码\"},");
			
			jsonResult.append("{\"id\":\"value=dept_name\",\"text\":\"部门名称\"},");
			
			jsonResult.append("{\"id\":\"value=pay_type_code\",\"text\":\"发放方式\"},");
			
			jsonResult.append("{\"id\":\"value=id_number\",\"text\":\"身份证号\"},");
			
			jsonResult.append("{\"id\":\"value=station_code\",\"text\":\"岗位\"},");
			
			jsonResult.append("{\"id\":\"value=duty_code\",\"text\":\"职务\"},");
			
			jsonResult.append("{\"id\":\"value=note\",\"text\":\"备注\"},");
			
			for (AccWageItems groupDict : wageItemList) {
				
				if (row == 0) {
					
					jsonResult.append("{");
					
				} else {
					
					jsonResult.append(",{");
					
				}
				
				row++;
				
				jsonResult.append("\"id\":\"" + groupDict.getColumn_item() + "\",");
				
				/*jsonResult.append("group_id:'" + groupDict.getGroup_id() + "',");
				
				jsonResult.append("hos_id:'" + groupDict.getHos_id() + "',");
				
				jsonResult.append("copy_code:'" + groupDict.getCopy_code() + "',");*/
				
				jsonResult.append("\"text\":\""+groupDict.getItem_name()+ "\"");
				
				jsonResult.append("}");
			}
		}
		jsonResult.append("]");
		
		return jsonResult.toString();
	}

	@Override
	public String setAccWageSchemeRela(Map<String, Object> entityMap)
			throws DataAccessException {
		try {
			if (!"".equals(entityMap.get("scheme_id")) && !"undefined".equals(entityMap.get("scheme_id"))
					&& null != entityMap.get("scheme_id")) {
				// 此方案包含的职工在其他工资条中包含，则不能将此方案设为是工资条
				String msg = checkGztIncludeEmp(entityMap);
				if(msg != null){
					return "{\"warn\":\"" + msg + ".\",\"state\":\"false\"}";
				}
				
				accWageSchemeMapper.updateAccWageScheme(entityMap);
				return "{\"msg\":\"设置成功.\",\"state\":\"true\"}";
			}
			
			AccWageScheme accWageScheme = accWageSchemeMapper.queryAccWageSchemeByCode(entityMap);
			if (accWageScheme != null) {
				return "{\"warn\":\"方案编码或方案名称重复.\",\"state\":\"false\"}";
			}
			
			accWageSchemeMapper.addAccWageScheme(entityMap);
			return "{\"msg\":\"设置成功.\",\"state\":\"true\",\"scheme_id\":\"" + entityMap.get("scheme_id") + "\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("设置失败:" + e.getMessage());
		}
	}
	
	/**
	 * 检查是工资条的工资方案包含的目标方案下包含的职工
	 * @return
	 */
	private String checkGztIncludeEmp(Map<String, Object> map){
		// 仅当  是职工工资条类型的工资方案并且设置is_gzt为1(是)的  才检查
		if("04".equals(map.get("scheme_type_code").toString()) && "1".equals(map.get("is_gzt").toString())){
			List<AccWageSchemeKind> reList = accWageSchemeKindMapper.queryGztIncludeEmp(map);
			if(!reList.isEmpty()){
				int count = 10;
				StringBuilder sb = new StringBuilder("以下多个职工已存在其他工资条中：");
				if(reList.size() < count){
					sb = new StringBuilder("以下职工已存在其他工资条中：");
					count = reList.size();
				}
				
				for(int i = 0; i < count; i++){
					sb.append("<br/>").append(reList.get(i).getEmp_code() + " " +reList.get(i).getEmp_name());
				}
				if(count == 10){
					sb.append("<br/>").append("……");
				}
				return sb.toString();
			}
		}
		return null;
	}

	@Override
	public String setAccWageItemSumSchemeRela(Map<String, Object> entityMap)
			throws DataAccessException {

		ArrayList<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		
		try {
			
			String [] paraValue = entityMap.get("paraValue").toString().split(";");
			/*
			if(!"".equals(entityMap.get("para").toString())){
				
				String [] para = entityMap.get("para").toString().split(";");

				for (String kind_code : para) {
					
					Map<String, Object> mapVo = new HashMap<String, Object>();
					
					mapVo.put("kind_code", kind_code);
					
					mapVo.put("scheme_id", entityMap.get("scheme_id"));
					
					list.add(mapVo);
					
				}
				
				accWageSchemeKindMapper.deleteBatchAccWageSchemeKind(list);
				
				accWageSchemeKindMapper.addBatchAccWageSchemeKind(list);
				
				list.clear();
			}*/
			
			for (String item_code : paraValue) {
				
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("column_item", item_code.replace("value=", ""));
				
				map.put("scheme_id", entityMap.get("scheme_id"));
				
				list.add(map);
			}
			
			accWageSchemeItemMapper.deleteBatchAccWageSchemeItem(list);
			
			accWageSchemeItemMapper.addBatchAccWageSchemeItem(list);
			
		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");

		}
		
		return "{\"msg\":\"设置成功.\",\"state\":\"true\"}";
	}

	@Override
	public String addBatchAccWageSchemeKind(Map<String, Object> entityMap) throws DataAccessException {
		ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			for (String id : entityMap.get("para").toString().split(",")) {
				Map<String, Object> mapVo = new HashMap<String, Object>();
				mapVo.put("emp_id", id.split("@")[0]);
				mapVo.put("scheme_id", id.split("@")[1]);
				list.add(mapVo);
				if (list.size() == 100) { 
					accWageSchemeKindMapper.deleteBatchAccWageSchemeKind(list);
					accWageSchemeKindMapper.addBatchAccWageSchemeKind(list);
					list = new ArrayList<Map<String, Object>>(0);
				}
			}

			if (list.size() > 0) {
				accWageSchemeKindMapper.deleteBatchAccWageSchemeKind(list);
				accWageSchemeKindMapper.addBatchAccWageSchemeKind(list);
			}
			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败\"}");
		}
	}

	@Override
	public String addBatchAccWageSchemeItem(Map<String, Object> entityMap)
			throws DataAccessException {
		
		ArrayList<Map<String, Object>> itemList  = new ArrayList<Map<String,Object>>();
		
		try {
			
			for ( String id: entityMap.get("paraValue").toString().split(",")) {
				
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
				mapVo.put("column_item", id.split("@")[0]);
				
				mapVo.put("scheme_id", id.split("@")[1]);
				
				itemList .add(mapVo);
				
				if(itemList.size()==10){
					
					accWageSchemeItemMapper.deleteBatchAccWageSchemeItem(itemList);
					
					accWageSchemeItemMapper.addBatchAccWageSchemeItem(itemList);
					
					itemList = new ArrayList<Map<String,Object>>(0);
					
				}
	        }
			
			if(itemList.size()>0){
				
				accWageSchemeItemMapper.deleteBatchAccWageSchemeItem(itemList);
				
				accWageSchemeItemMapper.addBatchAccWageSchemeItem(itemList);
			
			}
			
			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");
			
		}
	}

	@Override
	public String initWageSchemeItemByWage(Map<String, Object> entityMap)
			throws DataAccessException {
		try {
			if (entityMap.get("scheme_id") == null || entityMap.get("scheme_id").toString() == "") {
				return "{\"warn\":\"请先维护工资方案.\",\"state\":\"false\"}";
			}

			accWageSchemeItemMapper.deleteAccWageSchemeItem(entityMap);
			List<Map<String, Object>> list = accWageSchemeItemMapper.queryAccWageSchemeItemList(entityMap);
			accWageSchemeItemMapper.addBatchAccWageSchemeItem(list);
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败\"}");
		}
	}

	@Override
	public String initWageSchemeKindByWage(Map<String, Object> entityMap) throws DataAccessException {
		try {
			accWageSchemeKindMapper.deleteAccWageSchemeKind(entityMap);
			List<Map<String, Object>> list = accWageSchemeKindMapper.queryAccWageSchemeKindList(entityMap);
			accWageSchemeKindMapper.addBatchAccWageSchemeKind(list);
			
			// 删除已经在其他工资条包含的职工
			if(entityMap.containsKey("is_gzt") && "1".equals(entityMap.get("is_gzt").toString())){
				List<AccWageSchemeKind> list2 = accWageSchemeKindMapper.queryGztIncludeEmp(entityMap);
				if(!list2.isEmpty()){
					List<String> empIdList = new ArrayList<String>();
					for(AccWageSchemeKind item : list2){
						empIdList.add(item.getEmp_id());
					}
					entityMap.put("empIdList", empIdList);
					accWageSchemeKindMapper.deleteBySchemeIdAndEmpIds(entityMap);
				}
			}
			
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("操作失败");
		}
	}

	
}

