/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.budg.serviceImpl.business.drugdisburse;

import java.util.ArrayList;
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
import com.chd.hrp.budg.dao.business.drugdisburse.BudgDrugMapper;
import com.chd.hrp.budg.entity.BudgDrug;
import com.chd.hrp.budg.service.business.drugdisburse.BudgDrugService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 科室药品支出预算编制
 * @Table: BUDG_DRUG
 * @Author: slient
 * @email: slient@e-tonggroup.com
 * @Version: 1.0
 */

@Service("budgDrugService")
public class BudgDrugServiceImpl implements BudgDrugService {

	private static Logger logger = Logger.getLogger(BudgDrugServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "budgDrugMapper")
	private final BudgDrugMapper budgDrugMapper = null;
	
	
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
				//查询添加数据是否已存在
				String  str = budgDrugMapper.queryDataExistList(addList) ;
				
				if(str == null){
					
					int count = budgDrugMapper.addBatch(addList);
					
				}else{
					
					return "{\"error\":\"第"+str+"行数据已存在\",\"state\":\"false\"}";
				}
				
				
			}
			
			if(updateList.size()>0){
				
				int state = budgDrugMapper.updateBatch(updateList);
				
			}
			
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"保存失败.\",\"state\":\"false\"}");

		}
		
	}
	
	/**
	 * @Description 添加科室药品支出预算编制<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象科室药品支出预算编制
		int count = queryDataExist(entityMap);
		if (count > 0) {
			return "{\"error\":\"数据重复,请重新添加.\"}";
		}

		try {
			budgDrugMapper.add(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
			// return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";
		}

	}

	/**
	 * @Description 批量添加科室药品支出预算编制<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {
			budgDrugMapper.addBatch(entityList);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
			// return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";
		}

	}

	/**
	 * @Description 更新科室药品支出预算编制<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {

		try {
			budgDrugMapper.update(entityMap);
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
			// return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";
		}

	}

	/**
	 * @Description 批量更新科室药品支出预算编制<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {
			budgDrugMapper.updateBatch(entityList);
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";
		}

	}

	/**
	 * @Description 删除科室药品支出预算编制<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {

		try {
			budgDrugMapper.delete(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";
		}

	}

	/**
	 * @Description 批量删除科室药品支出预算编制<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatch(List<Map<String, Object>> entityList) throws DataAccessException {
		try {
			budgDrugMapper.deleteBatch(entityList);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";
		}
	}

	/**
	 * @Description 添加科室药品支出预算编制<BR>
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
		// 判断是否存在对象科室药品支出预算编制

		List<BudgDrug> list = (List<BudgDrug>) budgDrugMapper.queryExists(entityMap);

		if (list.size() > 0) {
			budgDrugMapper.update(entityMap);
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		}

		try {
			budgDrugMapper.add(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";
		}

	}

	/**
	 * @Description 查询结果集科室药品支出预算编制<BR>
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

			List<BudgDrug> list = (List<BudgDrug>) budgDrugMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<BudgDrug> list = (List<BudgDrug>) budgDrugMapper.query(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * @Description 获取对象科室药品支出预算编制<BR>
	 * @param entityMap<BR>
	 *            参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return budgDrugMapper.queryByCode(entityMap);
	}

	/**
	 * @Description 获取科室药品支出预算编制<BR>
	 * @param entityMap<BR>
	 *            参数为要检索的字段
	 * @return BudgDrug
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return budgDrugMapper.queryByUniqueness(entityMap);
	}

	/**
	 * @Description 获取科室药品支出预算编制<BR>
	 * @param entityMap<BR>
	 *            参数为要检索的字段
	 * @return List<BudgDrug>
	 * @throws DataAccessException
	 */
	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		return budgDrugMapper.queryExists(entityMap);
	}

	@Override
	public String queryHosDeptDict(Map<String, Object> mapVo) throws DataAccessException {
		return JSON.toJSONString(budgDrugMapper.queryHosDeptDict(mapVo));
	}

	@Override
	public String queryBudgMedTypeSubj(Map<String, Object> mapVo) throws DataAccessException {
		return JSON.toJSONString(budgDrugMapper.queryBudgMedTypeSubj(mapVo));
	}

	/*@Override
	public String copyBudgDrug(Map<String, Object> mapVo) throws DataAccessException {
		// 根据预算年度及预算年度药品分类对应的收入预算科目从科室月份医疗收入预算BUDG_MED_INCOME_DEPT_MONTH中取收入预算数据生成本年支出预算数据。
	    // 其中药品分类对应的收入预算科目取自BUDG_MED_TYPE_SUBJ，年度取预算年度。
	    // 数据表中，有则更新，无则新增
		try {
			//budgDrugMapper.copyBudgDrug(mapVo);
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
	}*/

	/**
	 * @Description 
	 * 生成  根据年度月份物资分类生成本年度支出预算数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@Override
	public String generateBudgDrug(Map<String, Object> mapVo) {
		try {
			//查询预算年度物资分类与收入科目是否已做对应关系
			int count = budgDrugMapper.queryIncomeSubj(mapVo);
			
			if(count == 0){
				return "{\"warn\":\"请先维护物资分类与预算科目对应关系!\",\"state\":\"false\"}";
			}
			//查询上年支出数据是否已采集
			List<Map<String,Object>> ListVo =budgDrugMapper.querySubjCodebyYearOrMedTypeId(mapVo);
			
			if(ListVo.size()<=0){
				return "{\"warn\":\"请先采集上年各科室收费材料支出数据!\",\"state\":\"false\"}";
			}
			
			//查询生成所需数据
			List<Map<String,Object>> addList =budgDrugMapper.queryInsertData(mapVo);
			
			budgDrugMapper.delete(mapVo);
			budgDrugMapper.generateAddBatch(addList);
			
			return "{\"msg\":\"操作成功!\",\"state\":\"true\"}";
			
		} catch (DataAccessException e) {
			
			throw new SysException("{\"error\":\"生成失败!\"}");
		}
		
	}
	
	@Override
	public String budgDrugUpdateAdjRate(List<Map<String, Object>> listVo) {
		try {
			
			budgDrugMapper.budgDrugUpdateAdjRate(listVo);
			
			return "{\"msg\":\"设置成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"设置失败\"}";

		}	
	}
	/**
	 * 校验数据是否已存在
	 * @param entityMap
	 * @return
	 */
	@Override
	public int queryDataExist(Map<String, Object> entityMap) {
		
		return budgDrugMapper.queryDataExist(entityMap);
	}
	
	/**
	 * 根据 参数  查询收入预算、上年收入、上年同期支出  计算收入预算增长比例和计算值用
	 */
	@Override
	public String queryLastCostAndRate(Map<String, Object> mapVo) throws DataAccessException {
		
		Map<String,Object> map = budgDrugMapper.queryLastCostAndRate(mapVo) ;
		
		return ChdJson.toJson(map);
	}
}
