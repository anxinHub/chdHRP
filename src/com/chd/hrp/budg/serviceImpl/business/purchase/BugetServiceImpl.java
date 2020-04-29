package com.chd.hrp.budg.serviceImpl.business.purchase;

import java.util.ArrayList;
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
import com.chd.hrp.budg.dao.business.med.BudgMedCostExecuteMapper;
import com.chd.hrp.budg.dao.business.purchase.BugetMapper;
import com.chd.hrp.budg.entity.BudgAssetNow;
import com.chd.hrp.budg.entity.Buget;
import com.chd.hrp.budg.service.business.purchase.BugetService;
import com.chd.hrp.budg.serviceImpl.business.med.BudgMedCostExecuteServiceImpl;
import com.github.pagehelper.PageInfo;
/**
 * 资产采购预算
 * @author Administrator
 *
 */
@Service("bugetService")
public class BugetServiceImpl implements BugetService {
	
	private static Logger logger = Logger.getLogger(BugetServiceImpl.class);
	//引入DAO操作
	@Resource(name = "bugetMapper")
	private final BugetMapper bugetMapper = null;
	
	/**
	 * 生成 ：汇总所选年度已审核的购置计划
	 */
	@Override
	public String copyBuget(Map<String, Object> entityMap) throws DataAccessException {
		
		List<Map<String, Object>> entityList = null;
		
		if (entityMap.get("budg_year") == null) {
			return "{\"error\":\"缺少预算年度参数\"}";
		}
		try {
			// 1、先删除所选预算年度的数据
			bugetMapper.delete(entityMap);
			// 2  从购置计划主表明细表及资金来源表中  获取所需数据  并插入采购预算表中
			entityList = bugetMapper.copyBuget(entityMap);
			
			if (entityList != null && entityList.size() > 0) {
				bugetMapper.addBatch(entityList);
			}else{
				return "{\"msg\":\"没有需要生成的数据.\",\"state\":\"false\"}";
			}
			
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
		    throw new SysException("{\"error\":\"操作失败.\"}");
		}
	}
	
	/**
	 * 增加
	 */
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {
		try {
			bugetMapper.add(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
			// return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";
		}
	}

	@Override
	public String addBatch(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String update(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateBatch(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * 删除
	 */
	@Override
	public String delete(Map<String, Object> entityMap)
			throws DataAccessException {
		try {
			bugetMapper.delete(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	/**
	 * 批量删除
	 */
	@Override
	public String deleteBatch(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		try {
			bugetMapper.deleteBatch(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String addOrUpdate(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * 查询
	 */
	@Override
	public String query(Map<String, Object> entityMap)
			throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = (List<Map<String, Object>>) bugetMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = (List<Map<String, Object>>) bugetMapper.query(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public <T> T queryByCode(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> queryExists(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
	
	
	
	

}
