/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.serviceImpl.budgincome.budgdeptresolverate;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.budg.dao.budgincome.budgdeptresolverate.BudgDeptResolveRateMapper;
import com.chd.hrp.budg.dao.budgincome.downtotop.deptyearbudg.BudgMedIncomeDeptYearMapper;
import com.chd.hrp.budg.entity.BudgDeptResolveRate;
import com.chd.hrp.budg.service.budgincome.budgdeptresolverate.BudgDeptResolveRateService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 科室医疗收入科目分解比例维护
 * @Table:
 * BUDG_DEPT_RESOLVE_RATE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("budgDeptResolveRateService")
public class BudgDeptResolveRateServiceImpl implements BudgDeptResolveRateService {

	private static Logger logger = Logger.getLogger(BudgDeptResolveRateServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgDeptResolveRateMapper")
	private final BudgDeptResolveRateMapper budgDeptResolveRateMapper = null;
    
	@Resource(name = "budgMedIncomeDeptYearMapper")
	private final BudgMedIncomeDeptYearMapper budgMedIncomeDeptYearMapper = null;
	
	/**
	 * @Description 
	 * 添加科室医疗收入科目分解比例维护<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象科室医疗收入科目分解比例维护
		BudgDeptResolveRate budgDeptResolveRate = queryByCode(entityMap);

		if (budgDeptResolveRate != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = budgDeptResolveRateMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"添加失败\"}");

		}
		
	}
	/**
	 * @Description 
	 * 批量添加科室医疗收入科目分解比例维护<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgDeptResolveRateMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"添加失败\"}");

		}
		
	}
	
		/**
	 * @Description 
	 * 更新科室医疗收入科目分解比例维护<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = budgDeptResolveRateMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"更新失败\"}");

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新科室医疗收入科目分解比例维护<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgDeptResolveRateMapper.updateBatch(entityList);
			
		    budgMedIncomeDeptYearMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"更新失败\"}");

		}	
		
	}
	/**
	 * @Description 
	 * 删除科室医疗收入科目分解比例维护<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			budgDeptResolveRateMapper.delete(entityMap);
			
			budgMedIncomeDeptYearMapper.add(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"删除添加失败\"}");

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除科室医疗收入科目分解比例维护<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgDeptResolveRateMapper.deleteBatch(entityList);
			
			budgMedIncomeDeptYearMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"删除失败\"}");

		}	
	}
	
	/**
	 * @Description 
	 * 添加科室医疗收入科目分解比例维护<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdate(Map<String,Object> entityMap)throws DataAccessException{
		/**
		* 备注 先判断是否存在 存在即更新不存在则添加<br>
		* 在判断是否存在时可根据实际情况进行修改传递的参数进行判断<br>
		* 判断是否名称相同 判断是否 编码相同 等一系列规则
		*/
		//判断是否存在对象科室医疗收入科目分解比例维护
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
		
		List<BudgDeptResolveRate> list = (List<BudgDeptResolveRate>)budgDeptResolveRateMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = budgDeptResolveRateMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = budgDeptResolveRateMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集科室医疗收入科目分解比例维护<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<BudgDeptResolveRate> list = (List<BudgDeptResolveRate>)budgDeptResolveRateMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<BudgDeptResolveRate> list = (List<BudgDeptResolveRate>)budgDeptResolveRateMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象科室医疗收入科目分解比例维护<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return budgDeptResolveRateMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取科室医疗收入科目分解比例维护<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return BudgDeptResolveRate
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return budgDeptResolveRateMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取科室医疗收入科目分解比例维护<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<BudgDeptResolveRate>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return budgDeptResolveRateMapper.queryExists(entityMap);
	}
	
}
