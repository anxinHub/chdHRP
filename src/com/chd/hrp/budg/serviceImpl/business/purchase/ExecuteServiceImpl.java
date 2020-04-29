package com.chd.hrp.budg.serviceImpl.business.purchase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

















import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.budg.dao.business.purchase.ExecuteMapper;
import com.chd.hrp.budg.entity.BudgElseIncomeExecute;
import com.chd.hrp.budg.entity.Execute;
import com.chd.hrp.budg.service.business.purchase.ExecuteService;
import com.github.pagehelper.PageInfo;
/**
 * 资产采购预算执行
 * @author Administrator
 *
 */
@Service("executeService")
public class ExecuteServiceImpl implements ExecuteService{
	
	
	private static Logger logger = Logger.getLogger(ExecuteServiceImpl.class);
	//引入DAO操作
	@Resource(name = "executeMapper")
	private final ExecuteMapper executeMapper = null;
	
	

	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {
		
		Map<String,Object> execute = queryByCode(entityMap);

		if (execute != null) {
			
			return "{\"error\":\"数据已存在,请重新添加.\",\"state\":\"false\"}";
		}
		
		try {
			
			int state = executeMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败!\"}";

		}
	}

	@Override
	public String addBatch(List<Map<String, Object>> entityList)	throws DataAccessException {
		try {
			
			executeMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败\"}";

		}
	}

	@Override
	public String update(Map<String, Object> entityMap)	throws DataAccessException {
		try {
			
			int state = executeMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"更新失败!\"}";

		}	
	}

	@Override
	public String updateBatch(List<Map<String, Object>> entityList) throws DataAccessException {
		try {
			
			executeMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"更新失败!\"}";

		}	
	}

	@Override
	public String delete(Map<String, Object> entityList) throws DataAccessException {
		try {
			
			executeMapper.delete(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";
		}
	}
	//删除
	@Override
	public String deleteBatch(List<Map<String, Object>> entityList)	throws DataAccessException {
		
		try {
			executeMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String addOrUpdate(Map<String, Object> entityMap)throws DataAccessException {
		/**
		* 备注 先判断是否存在 存在即更新不存在则添加<br>
		* 在判断是否存在时可根据实际情况进行修改传递的参数进行判断<br>
		* 判断是否名称相同 判断是否 编码相同 等一系列规则
		*/
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
		
		List<Execute> list = (List<Execute>)executeMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = executeMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = executeMapper.add(entityMap);
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"操作失败 !\"}";

		}
	}
	/**
	 * 查询数据
	 */
	@Override
	public String query(Map<String, Object> entityMap)throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)executeMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)executeMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}

	@Override
	public <T> T queryByCode(Map<String, Object> entityMap)	throws DataAccessException {
		
		return executeMapper.queryByCode(entityMap);
	}

	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap)throws DataAccessException {
		return executeMapper.queryByUniqueness(entityMap);
	}

	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		return executeMapper.queryByUniqueness(entityMap);
	}
	/**
	 * 单条校验数据是否存在
	 */
	@Override
	public int queryDataExist(Map<String, Object> entityMap) throws DataAccessException {
		return executeMapper.queryDataExist(entityMap);
	}
	/**
	 * 资产来源下拉框
	 */
	@Override
	public String querySourceName(Map<String, Object> mapVo) throws DataAccessException {
		List<Map<String, Object>> list = executeMapper.querySourceName(mapVo);
		return JSON.toJSONString(list);
	}

	/**
	 * 保存数据
	 */
	@Override
	public String save(List<Map<String, Object>> listVo)throws DataAccessException {
		//定义uadateList用于批量更新
		List<Map<String, Object>> updateList = new ArrayList<Map<String,Object>>();
		//定义addList用于批量添加
		List<Map<String, Object>> addList = new ArrayList<Map<String,Object>>();
		
		try {
			
			for (Map<String, Object> map : listVo) {
				
				if("1".equals(map.get("flag"))){
					
					addList.add(map) ;
					
				}else{
					
					updateList.add(map);
				}
				
			}
			
			if(updateList.size() > 0){
				
				executeMapper.updateBatch(updateList);
				
			}
			if(addList.size() > 0){
				
				
				//查询数据是否已经存在  
				String str = executeMapper.queryExecuteDataExist(addList);
				
				if(str == null){
					
					int count = executeMapper.addBatch(addList);
					
				}else{
					
					return "{\"message\":\"第"+str+"行数据已存在\",\"state\":\"false\"}";
				}	
			}
			
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"保存失败.\",\"state\":\"false\"}");
			
		}
	}
	
}
