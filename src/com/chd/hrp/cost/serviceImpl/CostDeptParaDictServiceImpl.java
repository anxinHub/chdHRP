/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.cost.serviceImpl;

import java.util.*;
import javax.annotation.Resource;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.cost.dao.CostDeptParaDictMapper;
import com.chd.hrp.cost.entity.CostDeptParaDict;
import com.chd.hrp.cost.service.CostDeptParaDictService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 分摊参数<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/

 
@Service("costDeptParaDictService")
public class CostDeptParaDictServiceImpl implements CostDeptParaDictService {
 
	private static Logger logger = Logger.getLogger(CostDeptParaDictServiceImpl.class);
	
	@Resource(name = "costDeptParaDictMapper")
	private final CostDeptParaDictMapper costDeptParaDictMapper = null;
    
	/**
	 * @Description 
	 * 分摊参数<BR> 添加CostDeptParaDict
	 * @param CostDeptParaDict entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addCostDeptParaDict(Map<String,Object> entityMap)throws DataAccessException{
		
		Map<String,Object> byCodeMap = new HashMap<String,Object>();
		
		byCodeMap.put("group_id", entityMap.get("group_id"));
		
		byCodeMap.put("hos_id", entityMap.get("hos_id"));
		
		byCodeMap.put("copy_code", entityMap.get("copy_code"));
		
		byCodeMap.put("para_code", entityMap.get("para_code"));
		
		CostDeptParaDict costDeptParaDict = queryCostDeptParaDictByCode(byCodeMap);

		if (costDeptParaDict != null) {

			return "{\"error\":\"编码：" + entityMap.get("para_code").toString() + "重复.\"}";

		}
		
		try {
			
			costDeptParaDictMapper.addCostDeptParaDict(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addCostDeptParaDict\"}";

		}

	}
	
	/**
	 * @Description 
	 * 分摊参数<BR> 批量添加CostDeptParaDict
	 * @param  CostDeptParaDict entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchCostDeptParaDict(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			costDeptParaDictMapper.addBatchCostDeptParaDict(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchCostDeptParaDict\"}";

		}
	}
	
	/**
	 * @Description 
	 * 分摊参数<BR> 查询CostDeptParaDict分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryCostDeptParaDict(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<CostDeptParaDict> list = costDeptParaDictMapper.queryCostDeptParaDict(entityMap);
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<CostDeptParaDict> list = costDeptParaDictMapper.queryCostDeptParaDict(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
		
	}
	
	/**
	 * @Description 
	 * 分摊参数<BR> 查询CostDeptParaDictByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public CostDeptParaDict queryCostDeptParaDictByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return costDeptParaDictMapper.queryCostDeptParaDictByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 分摊参数<BR> 批量删除CostDeptParaDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchCostDeptParaDict(List<Map<String,Object>> entityList)throws DataAccessException{
		boolean falg=false;
		try {
			for (Map<String, Object> map : entityList) {
				if(map.get("is_sys").equals("1")) {
					falg=true;
				}
			}

			if(falg) {
				return "{\"msg\":\"存在系统内置参数不允许删除.\",\"state\":\"true\"}";
			}else {
				int state = costDeptParaDictMapper.deleteBatchCostDeptParaDict(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			}

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchCostDeptParaDict\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 分摊参数<BR> 删除CostDeptParaDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteCostDeptParaDict(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			if(entityMap.get("is_sys").equals("1")) {
				return "{\"msg\":\"存在系统内置参数不允许删除.\",\"state\":\"true\"}";
			}else {
				costDeptParaDictMapper.deleteCostDeptParaDict(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			}
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteCostDeptParaDict\"}";

		}
    }
	
	/**
	 * @Description 
	 * 分摊参数<BR> 更新CostDeptParaDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateCostDeptParaDict(Map<String,Object> entityMap)throws DataAccessException{

		try {

			costDeptParaDictMapper.updateCostDeptParaDict(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostDeptParaDict\"}";

		}
	}
	
	/**
	 * @Description 
	 * 分摊参数<BR> 批量更新CostDeptParaDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchCostDeptParaDict(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			costDeptParaDictMapper.updateBatchCostDeptParaDict(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostDeptParaDict\"}";

		}
		
	}
}
