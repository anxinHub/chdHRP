/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.budg.serviceImpl.business.invdisburse;

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
import com.chd.hrp.budg.dao.business.invdisburse.BudgMatPurExeMapper;
import com.chd.hrp.budg.entity.BudgMatPurExe;
import com.chd.hrp.budg.service.business.invdisburse.BudgMatPurExeService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 材料采购预算编制
 * @Table: BUDG_MAT_PUR
 * @Author: slient
 * @email: slient@e-tonggroup.com
 * @Version: 1.0
 */

@Service("budgMatPurExeService")
public class BudgMatPurExeServiceImpl implements BudgMatPurExeService {

	private static Logger logger = Logger.getLogger(BudgMatPurExeServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "budgMatPurExeMapper")
	private final BudgMatPurExeMapper budgMatPurExeMapper = null;
	
	
	/**
	 * 保存数据 材料采购预算编制
	 */
	@Override
	public String saveBudgMatPurExe(List<Map<String, Object>> listVo) throws DataAccessException{
		
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
				
				//批量 查询添加数据是否已存在
				String  str = budgMatPurExeMapper.queryDataExistList(addList) ;
				
				if(str == null){
					
					int count = budgMatPurExeMapper.addBatch(addList);
					
				}else{
					
					return "{\"error\":\"第"+str+"行数据已存在\",\"state\":\"false\"}";
				}
				
				
			}
			
			if(updateList.size()>0){
				
				int state = budgMatPurExeMapper.updateBatch(updateList);
			}
			
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"保存失败.\",\"state\":\"false\"}");
			
			//return "{\"error\":\"添加失败! 方法 add\"}";

		}
		
	}
	
	/**
	 * @Description 添加科室材料支出预算<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象科室材料支出预算
		BudgMatPurExe budgMatPurExe = queryByCode(entityMap);
		if (budgMatPurExe != null) {
			return "{\"error\":\"数据重复,请重新添加.\"}";
		}

		try {
			budgMatPurExeMapper.add(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
			// return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";
		}

	}

	/**
	 * @Description 批量添加科室材料支出预算<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {
			budgMatPurExeMapper.addBatch(entityList);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
			// return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";
		}

	}

	/**
	 * @Description 更新科室材料支出预算<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {

		try {
			budgMatPurExeMapper.update(entityMap);
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
			// return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";
		}

	}

	/**
	 * @Description 批量更新科室材料支出预算<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {
			budgMatPurExeMapper.updateBatch(entityList);
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";
		}

	}

	/**
	 * @Description 删除科室材料支出预算<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {

		try {
			budgMatPurExeMapper.delete(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";
		}

	}

	/**
	 * @Description 批量删除科室材料支出预算<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatch(List<Map<String, Object>> entityList) throws DataAccessException {
		try {
			budgMatPurExeMapper.deleteBatch(entityList);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";
		}
	}

	/**
	 * @Description 添加科室材料支出预算<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {

		// 判断是否存在对象采购材料支出预算

		List<BudgMatPurExe> list = (List<BudgMatPurExe>) budgMatPurExeMapper.queryExists(entityMap);

		if (list.size() > 0) {
			budgMatPurExeMapper.update(entityMap);
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		}

		try {
			budgMatPurExeMapper.add(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";
		}

	}
	
	
	/**
	 * @Description 添加科室材料支出预算<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String addOrUpdateBudgMatPurExe(List<Map<String, Object>> listVo) throws DataAccessException {
		
		//定义uadateList用于批量更新
		List<Map<String, Object>> updateList = new ArrayList<Map<String,Object>>();
		//定义addList用于批量添加
		List<Map<String, Object>> addList = new ArrayList<Map<String,Object>>();
		
		try {
			
			for (Map<String, Object> map : listVo) {
				//查询数据是由已经存在  如果存在更新  否则 添加
				int count = budgMatPurExeMapper.queryDataExists(map);
				
				if(count > 0 ){
					updateList.add(map);
				}else {
					addList.add(map);
				}
				
			}
			
			if(updateList.size() > 0){
				budgMatPurExeMapper.updateBatch(updateList);
			}
			if(addList.size() > 0){
				budgMatPurExeMapper.addBatch(addList);
			}
			
			return "{\"msg\":\"操作成功!\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException(e.getMessage(),e);
			
		}
		

	}

	/**
	 * @Description 查询结果集科室材料支出预算<BR>
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

			List<BudgMatPurExe> list = (List<BudgMatPurExe>) budgMatPurExeMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<BudgMatPurExe> list = (List<BudgMatPurExe>) budgMatPurExeMapper.query(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * @Description 获取对象科室材料支出预算<BR>
	 * @param entityMap<BR>
	 *            参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return budgMatPurExeMapper.queryByCode(entityMap);
	}

	/**
	 * @Description 获取科室材料支出预算<BR>
	 * @param entityMap<BR>
	 *            参数为要检索的字段
	 * @return BudgMat
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return budgMatPurExeMapper.queryByUniqueness(entityMap);
	}

	/**
	 * @Description 获取科室材料支出预算<BR>
	 * @param entityMap<BR>
	 *            参数为要检索的字段
	 * @return List<BudgMat>
	 * @throws DataAccessException
	 */
	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		return budgMatPurExeMapper.queryExists(entityMap);
	}

	@Override
	public String queryBudgMatTypeSubj(Map<String, Object> mapVo) throws DataAccessException {
		return JSON.toJSONString(budgMatPurExeMapper.queryBudgMatTypeSubj(mapVo));
	}

}
