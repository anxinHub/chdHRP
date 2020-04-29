/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.cost.serviceImpl;

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
import com.chd.base.util.ChdJson;
import com.chd.hrp.cost.dao.CostEmpTitleAttrMapper;
import com.chd.hrp.cost.dao.CostEmpTypeAttrMapper;
import com.chd.hrp.cost.entity.CostEmpTitleAttr;
import com.chd.hrp.cost.entity.CostEmpTypeAttr;
import com.chd.hrp.cost.service.CostEmpTitleAttrService;
import com.chd.hrp.sys.dao.EmpKindMapper;
import com.chd.hrp.sys.entity.EmpKind;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 成本_职工职称字典表<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("costEmpTitleAttrService")
public class CostEmpTitleAttrServiceImpl implements CostEmpTitleAttrService {

	private static Logger logger = Logger.getLogger(CostEmpTitleAttrServiceImpl.class);
	
	@Resource(name = "costEmpTitleAttrMapper")
	private final CostEmpTitleAttrMapper costEmpTitleAttrMapper = null;
	@Resource(name = "empKindMapper")
	private final EmpKindMapper empKindMapper = null;
	@Resource(name = "costEmpTypeAttrMapper")
	private final CostEmpTypeAttrMapper costEmpTypeAttrMapper = null;
    
	/**
	 * @Description 
	 * 成本_职工职称字典表<BR> 添加CostEmpTitleAttr
	 * @param CostEmpTitleAttr entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addCostEmpTitleAttr(Map<String,Object> entityMap)throws DataAccessException{
		
		CostEmpTitleAttr costEmpTitleAttr = queryCostEmpTitleAttrByCode(entityMap);

		if (costEmpTitleAttr != null) {

			return "{\"error\":\"编码：" + entityMap.get("title_code").toString() + "已存在.\"}";

		}
		
		try {
			
			costEmpTitleAttrMapper.addCostEmpTitleAttr(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addCostEmpTitleAttr\"}";

		}

	}
	
	/**
	 * @Description 
	 * 成本_职工职称字典表<BR> 批量添加CostEmpTitleAttr
	 * @param  CostEmpTitleAttr entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchCostEmpTitleAttr(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			costEmpTitleAttrMapper.addBatchCostEmpTitleAttr(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchCostEmpTitleAttr\"}";

		}
	}
	
	/**
	 * @Description 
	 * 成本_职工职称字典表<BR> 查询CostEmpTitleAttr分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryCostEmpTitleAttr(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<CostEmpTitleAttr> list = costEmpTitleAttrMapper.queryCostEmpTitleAttr(entityMap);
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<CostEmpTitleAttr> list = costEmpTitleAttrMapper.queryCostEmpTitleAttr(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
		
	}
	@Override
	public List<Map<String, Object>> queryCostEmpTitleAttrPrint(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=costEmpTitleAttrMapper.queryCostEmpTitleAttrPrint(entityMap);
		
		return list;

	}
	/**
	 * @Description 
	 * 成本_职工职称字典表<BR> 查询CostEmpTitleAttrByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public CostEmpTitleAttr queryCostEmpTitleAttrByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return costEmpTitleAttrMapper.queryCostEmpTitleAttrByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 成本_职工职称字典表<BR> 批量删除CostEmpTitleAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchCostEmpTitleAttr(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = costEmpTitleAttrMapper.deleteBatchCostEmpTitleAttr(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchCostEmpTitleAttr\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 成本_职工职称字典表<BR> 删除CostEmpTitleAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteCostEmpTitleAttr(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				costEmpTitleAttrMapper.deleteCostEmpTitleAttr(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteCostEmpTitleAttr\"}";

		}
    }
	
	/**
	 * @Description 
	 * 成本_职工职称字典表<BR> 更新CostEmpTitleAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateCostEmpTitleAttr(Map<String,Object> entityMap)throws DataAccessException{

		try {

			costEmpTitleAttrMapper.updateCostEmpTitleAttr(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostEmpTitleAttr\"}";

		}
	}
	
	/**
	 * @Description 
	 * 成本_职工职称字典表<BR> 批量更新CostEmpTitleAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchCostEmpTitleAttr(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			costEmpTitleAttrMapper.updateBatchCostEmpTitleAttr(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostEmpTitleAttr\"}";
		}
	}
	/**
	 * @Description
	 * 2016/10/28 lxj 
	 * 成本_职工分类表<BR> 同步
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String syncCostEmpTypeAttr(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			
			List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();//用来存储组装后的系统平台职工分类数据
			// TODO Auto-generated method stub
			//1.查询系统平台职工分类
			
			List<EmpKind> list = empKindMapper.queryEmpKind(entityMap);
			
			if(list.size() == 0 ){
				
				return "{\"error\":\"同步失败,没有找到可同步的数据.\"}";
			}
			
			//2.查询科室成本职工分类
			List<CostEmpTypeAttr> empTypeList = costEmpTypeAttrMapper.queryCostEmpTypeAttr(entityMap);
			
			//3.系统平台职工分类比较成本科室职工分类,比较编码是否存在
			if(empTypeList.size() > 0 ){//成本科室职工分类 有数据就比较
				
				for(int x = 0;x < empTypeList.size(); x++){
					
					for(int y = 0 ; y < list.size() ; y++){
						
						if(list.get(y).getKind_code().equals(empTypeList.get(x).getEmp_kind_code())){//如果相等就删除
							
							list.remove(y);
						
						}
					}
				}
				
				if(list.size() == 0){
					
					return "{\"error\":\"同步失败,数据已存在.\"}";
				}
				
				for(int y = 0 ; y < list.size() ; y++){
					
					Map<String,Object> mapVo = new HashMap<String,Object>();
					
					mapVo.put("group_id", list.get(y).getGroup_id());
					mapVo.put("hos_id", list.get(y).getHos_id());
					mapVo.put("copy_code", entityMap.get("copy_code"));
					mapVo.put("emp_kind_code", list.get(y).getKind_code());
					mapVo.put("emp_kind_name", list.get(y).getKind_name());
					if(list.get(y).getNote() != null){
						mapVo.put("note", list.get(y).getNote());
					}else{
						mapVo.put("note", "");
					}
					mapVo.put("spell_code", list.get(y).getSpell_code());
					mapVo.put("wbx_code", list.get(y).getWbx_code());
					mapList.add(mapVo);
				}
				
				costEmpTypeAttrMapper.addBatchCostEmpTypeAttr(mapList);
				
				return "{\"msg\":\"同步成功.\",\"state\":\"true\"}";
			}else{//没数据,就添加
				
				for(int y = 0 ; y < list.size() ; y++){
					
						Map<String,Object> mapVo = new HashMap<String,Object>();
						
						mapVo.put("group_id", list.get(y).getGroup_id());
						mapVo.put("hos_id", list.get(y).getHos_id());
						mapVo.put("copy_code", entityMap.get("copy_code"));
						mapVo.put("emp_kind_code", list.get(y).getKind_code());
						mapVo.put("emp_kind_name", list.get(y).getKind_name());
						if(list.get(y).getNote() != null){
							mapVo.put("note", list.get(y).getNote());
						}else{
							mapVo.put("note", "");
						}
						mapVo.put("spell_code", list.get(y).getSpell_code());
						mapVo.put("wbx_code", list.get(y).getWbx_code());
						mapList.add(mapVo);
				}
				
				costEmpTypeAttrMapper.addBatchCostEmpTypeAttr(mapList);
				return "{\"msg\":\"同步成功.\",\"state\":\"true\"}";
			}
			
		} catch (Exception e) {
			
			return "{\"error\":\"同步失败 数据库异常 请联系管理员! 错误编码  syncCostEmpTypeAttr\"}";
		}
		
	}
}
