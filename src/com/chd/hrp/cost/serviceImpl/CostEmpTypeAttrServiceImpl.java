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

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.cost.dao.CostEmpTypeAttrMapper;
import com.chd.hrp.cost.entity.CostEmpTypeAttr;
import com.chd.hrp.cost.service.CostEmpTypeAttrService;
import com.chd.hrp.sys.dao.EmpKindMapper;
import com.chd.hrp.sys.entity.EmpKind;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 成本_职工分类表<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("costEmpTypeAttrService")
public class CostEmpTypeAttrServiceImpl implements CostEmpTypeAttrService {

	private static Logger logger = Logger.getLogger(CostEmpTypeAttrServiceImpl.class);
	
	@Resource(name = "costEmpTypeAttrMapper")
	private final CostEmpTypeAttrMapper costEmpTypeAttrMapper = null;
	@Resource(name = "empKindMapper")
	private final EmpKindMapper empKindMapper = null;
    
	/**
	 * @Description 
	 * 成本_职工分类表<BR> 添加CostEmpTypeAttr
	 * @param CostEmpTypeAttr entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addCostEmpTypeAttr(Map<String,Object> entityMap)throws DataAccessException{
		
		CostEmpTypeAttr costEmpTypeAttr = queryCostEmpTypeAttrByCode(entityMap);

		if (costEmpTypeAttr != null) {

			return "{\"error\":\"编码：" + costEmpTypeAttr.getEmp_kind_code() + "已存在.\"}";

		}
		
		try {
			
			costEmpTypeAttrMapper.addCostEmpTypeAttr(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addCostEmpTypeAttr\"}";

		}

	}
	
	/**
	 * @Description 
	 * 成本_职工分类表<BR> 批量添加CostEmpTypeAttr
	 * @param  CostEmpTypeAttr entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchCostEmpTypeAttr(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			costEmpTypeAttrMapper.addBatchCostEmpTypeAttr(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchCostEmpTypeAttr\"}";

		}
	}
	
	/**
	 * @Description 
	 * 成本_职工分类表<BR> 查询CostEmpTypeAttr分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryCostEmpTypeAttr(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<CostEmpTypeAttr> list = costEmpTypeAttrMapper.queryCostEmpTypeAttr(entityMap);
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<CostEmpTypeAttr> list = costEmpTypeAttrMapper.queryCostEmpTypeAttr(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
		
	}
	@Override
	public List<Map<String, Object>> queryCostEmpTypeAttrPrint(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=costEmpTypeAttrMapper.queryCostEmpTypeAttrPrint(entityMap);
		
		return list;

	}
	/**
	 * @Description 
	 * 成本_职工分类表<BR> 查询CostEmpTypeAttrByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public CostEmpTypeAttr queryCostEmpTypeAttrByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return costEmpTypeAttrMapper.queryCostEmpTypeAttrByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 成本_职工分类表<BR> 批量删除CostEmpTypeAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchCostEmpTypeAttr(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = costEmpTypeAttrMapper.deleteBatchCostEmpTypeAttr(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchCostEmpTypeAttr\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 成本_职工分类表<BR> 删除CostEmpTypeAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteCostEmpTypeAttr(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				costEmpTypeAttrMapper.deleteCostEmpTypeAttr(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteCostEmpTypeAttr\"}";

		}
    }
	
	/**
	 * @Description 
	 * 成本_职工分类表<BR> 更新CostEmpTypeAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateCostEmpTypeAttr(Map<String,Object> entityMap)throws DataAccessException{

		try {

			costEmpTypeAttrMapper.updateCostEmpTypeAttr(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostEmpTypeAttr\"}";

		}
	}
	
	/**
	 * @Description 
	 * 成本_职工分类表<BR> 批量更新CostEmpTypeAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchCostEmpTypeAttr(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			costEmpTypeAttrMapper.updateBatchCostEmpTypeAttr(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostEmpTypeAttr\"}";

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
				
				int state=costEmpTypeAttrMapper.addBatchCostEmpTypeAttr(mapList);
				
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
				
				int state=costEmpTypeAttrMapper.addBatchCostEmpTypeAttr(mapList);
				return "{\"msg\":\"同步成功.\",\"state\":\"true\"}";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"error\":\"同步失败 数据库异常 请联系管理员! 错误编码  syncCostEmpTypeAttr\"}";
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
	public String syncCostEmpTypeAttrNew(Map<String, Object> mapVo)  throws DataAccessException  {
		mapVo.put("copy_code",SessionManager.getCopyCode());
		costEmpTypeAttrMapper.addBatchCostEmpTypeAttrNew(mapVo);
		return "{\"msg\":\"同步成功.\",\"state\":\"true\"}";
	}
}
