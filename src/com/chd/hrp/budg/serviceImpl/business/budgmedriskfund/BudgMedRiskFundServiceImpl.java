/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.budg.serviceImpl.business.budgmedriskfund;

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
import com.chd.hrp.budg.dao.business.budgmedriskfund.BudgMedRiskFundMapper;
import com.chd.hrp.budg.entity.BudgMedRiskFund;
import com.chd.hrp.budg.service.business.budgmedriskfund.BudgMedRiskFundService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 提取医疗风险基金预算编制
 * @Table: BUDG_MED_RISK_FUND
 * @Author: slient
 * @email: slient@e-tonggroup.com
 * @Version: 1.0
 */

@Service("budgMedRiskFundService")
public class BudgMedRiskFundServiceImpl implements BudgMedRiskFundService {

	private static Logger logger = Logger.getLogger(BudgMedRiskFundServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "budgMedRiskFundMapper")
	private final BudgMedRiskFundMapper budgMedRiskFundMapper = null;
	
	/**
	 * 保存数据 
	 */
	@Override
	public String save(List<Map<String, Object>> listVo) throws DataAccessException{
		
		List<Map<String, Object>> addList= new ArrayList<Map<String,Object>>();
		
		List<Map<String, Object>> updateList= new ArrayList<Map<String,Object>>();
		
		
		for(Map<String,Object> item : listVo){
			
			if("1".equals(item.get("flag"))){ //添加
				
				addList.add(item) ;
				
			}else{ //修改
				
				updateList.add(item) ;
			}
			
		}
		
		try {
			
			if(addList.size()>0){
				//批量 查询 添加数据是否已存在
				String  str = budgMedRiskFundMapper.queryDataExistList(addList) ;
				
				if(str == null){
					
					int count = budgMedRiskFundMapper.addBatch(addList);
					
				}else{
					
					return "{\"error\":\"第"+str+"行数据已存在\",\"state\":\"false\"}";
				}
			}
			
			if(updateList.size()>0){
				
				int state = budgMedRiskFundMapper.updateBatch(updateList);
				
			}
			
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"保存失败.\",\"state\":\"false\"}");

		}
		
	}
	/**
	 * @Description 添加提取医疗风险基金预算编制<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象提取医疗风险基金预算编制
		BudgMedRiskFund budgMedRiskFund = queryByCode(entityMap);
		if (budgMedRiskFund != null) {
			return "{\"error\":\"数据重复,请重新添加.\"}";
		}

		try {
			budgMedRiskFundMapper.add(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
			// return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";
		}

	}

	/**
	 * @Description 批量添加提取医疗风险基金预算编制<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {
			budgMedRiskFundMapper.addBatch(entityList);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
			// return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";
		}

	}

	/**
	 * @Description 更新提取医疗风险基金预算编制<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {

		try {
			budgMedRiskFundMapper.update(entityMap);
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
			// return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";
		}

	}

	/**
	 * @Description 批量更新提取医疗风险基金预算编制<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {
			budgMedRiskFundMapper.updateBatch(entityList);
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";
		}

	}

	/**
	 * @Description 删除提取医疗风险基金预算编制<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {

		try {
			budgMedRiskFundMapper.delete(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";
		}

	}

	/**
	 * @Description 批量删除提取医疗风险基金预算编制<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatch(List<Map<String, Object>> entityList) throws DataAccessException {
		try {
			budgMedRiskFundMapper.deleteBatch(entityList);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";
		}
	}

	/**
	 * @Description 添加提取医疗风险基金预算编制<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
		/**
		 * 备注 先判断是否存在 存在即更新不存在则添加<br>
		 * 在判断是否存在时可根据实际情况进行修改传递的参数进行判断<br>
		 * 判断是否名称相同 判断是否 编码相同 等一系列规则
		 */
		// 判断是否存在对象提取医疗风险基金预算编制

		List<BudgMedRiskFund> list = (List<BudgMedRiskFund>) budgMedRiskFundMapper.queryExists(entityMap);

		if (list.size() > 0) {
			budgMedRiskFundMapper.update(entityMap);
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		}

		try {
			budgMedRiskFundMapper.add(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";
		}

	}

	/**
	 * @Description 查询结果集提取医疗风险基金预算编制<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<BudgMedRiskFund> list = (List<BudgMedRiskFund>) budgMedRiskFundMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<BudgMedRiskFund> list = (List<BudgMedRiskFund>) budgMedRiskFundMapper.query(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * @Description 获取对象提取医疗风险基金预算编制<BR>
	 * @param entityMap<BR>
	 *            参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return budgMedRiskFundMapper.queryByCode(entityMap);
	}

	/**
	 * @Description 获取提取医疗风险基金预算编制<BR>
	 * @param entityMap<BR>
	 *            参数为要检索的字段
	 * @return BudgMedRiskFund
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return budgMedRiskFundMapper.queryByUniqueness(entityMap);
	}

	/**
	 * @Description 获取提取医疗风险基金预算编制<BR>
	 * @param entityMap<BR>
	 *            参数为要检索的字段
	 * @return List<BudgMedRiskFund>
	 * @throws DataAccessException
	 */
	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		return budgMedRiskFundMapper.queryExists(entityMap);
	}

	@Override
	public String queryHosDeptDict(Map<String, Object> mapVo) throws DataAccessException {
		return JSON.toJSONString(budgMedRiskFundMapper.queryHosDeptDict(mapVo));
	}


	@Override
	public String copyBudgMedRiskFund(Map<String, Object> mapVo) throws DataAccessException {
		/*从BUDG_MED_INCOME_DEPT_MONTH中取所选预算年度各月份各科室收入预算值，若表中无所选预算年度的数据，提示“请先编制各科室月份医疗收入预算”
		支出预算=收入预算*提取比例
		提取比例取自预算系统设置BUDG_SYSY_SET,该预算年度的RISK_FUND_RATE。若所选年度，提取比例为空，提示“请先设置医疗风险基金的提取比例”
		采用覆盖生成，先删除所选预算年度的数据再重新生成。*/
		try {
			List<Map<String,Object>> budgMedIncomeDeptMonthList = budgMedRiskFundMapper.queryBudgMedIncomeDeptMonth(mapVo);
			Double riskFundRate = budgMedRiskFundMapper.queryRiskFundRate(mapVo);
			if(null != budgMedIncomeDeptMonthList && budgMedIncomeDeptMonthList.size() > 0){
				if(null != riskFundRate){
					for (Map<String, Object> map : budgMedIncomeDeptMonthList) {
						map.put("cost_budg", Double.parseDouble(String.valueOf(map.get("income_budg")))*riskFundRate/1000);
					}
				}else{
					return "{\"error\":\"请先设置医疗风险基金的提取比例.\",\"state\":\"false\"}";
				}
			}else{
				return "{\"error\":\"请先编制各科室月份医疗收入预算.\",\"state\":\"false\"}";
			}
			budgMedRiskFundMapper.delete(mapVo);
			budgMedRiskFundMapper.addBatch(budgMedIncomeDeptMonthList);
			//budgMedRiskFundMapper.copyBudgMedRiskFund(mapVo);
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
	}
	
	/**
	 * 查询 所传 科室 的 科室月份收入预算值  同时查询医疗风险基金的提取比例
	 */
	@Override
	public String queryWorkload(Map<String, Object> mapVo) throws DataAccessException {
		
		Map<String, Object>  map = budgMedRiskFundMapper.queryWorkload(mapVo) ;
		
			
		return ChdJson.toJson(map);
			
	}
	/**
	 * 查询 科室基本信息(根据编码 匹配ID用)
	 */
	@Override
	public List<Map<String, Object>> queryDeptData(Map<String, Object> map)	throws DataAccessException {
		
		return budgMedRiskFundMapper.queryDeptData(map);
	}
	/**
	 * 校验数据 是否存在
	 */
	@Override
	public int queryDataExist(Map<String, Object> mapVo) throws DataAccessException {
		
		return budgMedRiskFundMapper.queryDataExist(mapVo);
	}

}
