package com.chd.hrp.acc.serviceImpl.payable.otherpay;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.emp.AccEmpAccountMapper;
import com.chd.hrp.acc.dao.payable.otherpay.AccElsePayMapper;
import com.chd.hrp.acc.entity.AccEmpAccount;
import com.chd.hrp.acc.service.payable.otherpay.AccElsePayService;
import com.chd.hrp.budg.entity.BudgMedIncomeCheck;
import com.github.pagehelper.PageInfo;

@Service("accElsePayService")
public class AccElsePayServiceImpl implements AccElsePayService {

	private static Logger logger = Logger.getLogger(AccElsePayServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "accElsePayMapper")
	private final AccElsePayMapper accElsePayMapper = null;
	
	@Resource(name = "accEmpAccountMapper")
	private final AccEmpAccountMapper accEmpAccountMapper = null;

	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = (List<Map<String, Object>>) accElsePayMapper.query(entityMap);

			return ChdJson.toJsonLower(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(),sysPage.getPagesize());

			List<Map<String, Object>> list = (List<Map<String, Object>>) accElsePayMapper.query(entityMap);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJsonLower(list, page.getTotal());

		}
	}
	
	@Override
	public Map<String, Object> queryAccElsePayPrint(Map<String, Object> entityMap) throws DataAccessException {
		Map<String,Object> reMap=new HashMap<String,Object>();
		WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
		AccElsePayMapper accElsePayMapper = (AccElsePayMapper)context.getBean("accElsePayMapper");

		List<Map<String, Object>> list = (List<Map<String, Object>>) accElsePayMapper.queryPrintData(entityMap);
		
		reMap.put("main", list);
		reMap.put("detail", null);
		return reMap;

	}
	
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {
		try {
			
			int state = accElsePayMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败!\"}";

		}
		
	}

	@Override
	public String addBatch(List<Map<String, Object>> entityList)throws DataAccessException {
		try {
			
			accElsePayMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}
	}

	@Override
	public String update(Map<String, Object> entityMap)throws DataAccessException {
		try {
			
			  int state = accElsePayMapper.update(entityMap);
				
				return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				return "{\"error\":\"更新失败!\"}";

			}	
	}

	@Override
	public String updateBatch(List<Map<String, Object>> entityList) throws DataAccessException {
		try {
			
			
		  accElsePayMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"更新失败!\"}";

		}	
	}

	@Override
	public String delete(Map<String, Object> entityMap)	throws DataAccessException {
		try {
				
			int state = accElsePayMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
				

		}catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"删除失败.\",\"state\":\"false\"}");

			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}	
	}

	@Override
	public String deleteBatch(List<Map<String, Object>> entityList) throws DataAccessException {
		try {
			
			accElsePayMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";

		}	
	}

	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
		/**
		* 备注 先判断是否存在 存在即更新不存在则添加<br>
		* 在判断是否存在时可根据实际情况进行修改传递的参数进行判断<br>
		* 判断是否名称相同 判断是否 编码相同 等一系列规则
		*/
		//判断是否存在对象审批类型（CHECK_TYPE）取自系统字典表：01初始审批、02调整审批预算审批状态（BC_STATE）取自系统字典表：“01新建、02已发送、03已审核、04已下达、05批中”，其中05审批中暂不使用



		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
		
		List<BudgMedIncomeCheck> list = (List<BudgMedIncomeCheck>)accElsePayMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = accElsePayMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = accElsePayMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";

		}
	}

	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return accElsePayMapper.queryByCode(entityMap);
	}

	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return accElsePayMapper.queryByUniqueness(entityMap);
	}

	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		
		return accElsePayMapper.queryExists(entityMap);
	}

	@Override
	public AccEmpAccount queryAccEmpAccountByEmp(Map<String, Object> mapVo) throws DataAccessException {
		
		return accEmpAccountMapper.queryAccEmpAccountByEmp(mapVo);
	}

}
