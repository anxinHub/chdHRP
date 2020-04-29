/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.acc.serviceImpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.AccLederMapper;
import com.chd.hrp.acc.dao.AccLederCheckMapper;
import com.chd.hrp.acc.dao.vouch.AccVouchMapper;
import com.chd.hrp.acc.entity.AccVouch;
import com.chd.hrp.acc.service.AccVouchAccountingService;
import com.github.pagehelper.PageInfo;
 
/**
 * @Title. @Description. 凭证主表<BR>
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("accVouchAccountingService")
public class AccVouchAccountingServiceImpl implements AccVouchAccountingService  {

	private static Logger logger = Logger.getLogger(AccVouchAccountingServiceImpl.class);

	@Resource(name = "accVouchMapper")
	private final AccVouchMapper accVouchMapper = null;
	
	@Resource(name = "accLederMapper")
	private final AccLederMapper accLederMapper = null;
	
	@Resource(name = "accLederCheckMapper")
	private final AccLederCheckMapper accLederCheckMapper = null;
	
	/**
	 * @Description 凭证主表<BR>
	 *              查询AccVouch记账 已记账数据相关分页
	 * @param entityMap
	 *            RowBounds
	 * @return List<AccVouch>
	 * @throws DataAccessException
	 */
	@Override
	public String queryAccVouchAccounting(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		List<AccVouch> list = accVouchMapper.queryAccVouchAccounting(entityMap, rowBounds);
		PageInfo page = new PageInfo(list);
		return ChdJson.toJson(list, page.getTotal());
	}
	
	
	/**
	 * @Description 凭证主表<BR>
	 *              查询AccVouch记账 未记账数据相关分页
	 * @param entityMap
	 *            RowBounds
	 * @return List<AccVouch>
	 * @throws DataAccessException
	 */
	@Override
	public String queryAccVouchUnAccounting(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		List<AccVouch> list = accVouchMapper.queryAccVouchUnAccounting(entityMap, rowBounds);
		PageInfo page = new PageInfo(list);
		return ChdJson.toJson(list, page.getTotal());
	}
	/**
	 * @Description 凭证主表<BR>
	 *              查询AccVouch记账 统计数据相关分页
	 * @param entityMap
	 *            RowBounds
	 * @return List<AccVouch>
	 * @throws DataAccessException
	 */
	@Override
	public String queryAccVouchAccountingCount(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		List<AccVouch> list = accVouchMapper.queryAccVouchAccountingCount(entityMap, rowBounds);
		PageInfo page = new PageInfo(list);
		return ChdJson.toJson(list, page.getTotal());
	}
	
	@Override
	//@Transactional(rollbackFor=Exception.class)
	public String updateBatchAccountingAccVouch(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			
			//检查是否包含草稿凭证
            List<Map<String, Object>> list = accVouchMapper.accAccountingCheckIndex1(entityMap);
			
			if(list.size()>0)
			{
				
				return "{\"error\":\"凭证中包含草稿凭证！\",\"state\":\"false\"}";
			}
			
			if("3".equals(entityMap.get("parent_node_id").toString()))
			{
				 List<Map<String, Object>> list2 = accVouchMapper.accAccountingCheckIndex2(entityMap);
					
					if(list2.size()>0)
					{
						return "{\"error\":\"凭证中有未审核凭证！\",\"state\":\"false\"}";
					}
			}
			
			if("2".equals(entityMap.get("parent_node_id").toString()))
			{
				List<Map<String, Object>> list3 = accVouchMapper.accAccountingCheckIndex3(entityMap);
				
				if(list3.size()>0)
				{
					return "{\"error\":\"凭证中包含现金银行科目未出纳签字！\",\"state\":\"false\"}";
				}
				
				List<Map<String, Object>> list4 = accVouchMapper.accAccountingCheckIndex4(entityMap);
				
				if(list4.size()>0)
				{
					return "{\"error\":\"凭证中有未审核凭证！\",\"state\":\"false\"}";
				}
			}
			
			List<String> dhList=accVouchMapper.queryAccVouchAconDH(entityMap);
			if(dhList!=null && dhList.size()>0){
				return "{\"error\":\""+dhList.toString()+"\",\"state\":\"false\"}";
			}
			
			accVouchMapper.updateBatchAccountingAccVouch(entityMap);
			
			return "{\"msg\":\"记账成功.\",\"state\":\"true\"}";
				
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);

			return "{\"error\":\"记账失败 数据库异常 请联系管理员! 错误编码 updateBatchAccountingAccVouch\"}";
			
		}
	}
	
	@Override
	public Integer queryAccVouchFlow(Map<String, Object> entityMap)
			throws DataAccessException {
		
		Integer  parent_node_id = accVouchMapper.queryAccVouchFlow(entityMap);
		
		return parent_node_id;
	}
	
	@Override
	public String queryAccVouchAccount(Map<String, Object> entityMap)
			throws DataAccessException {
		
		return ChdJson.toJson(accVouchMapper.queryAccVouchAccount(entityMap));
	}
	@Override
	public String queryAccVouchAccountingReport(Map<String, Object> entityMap)
			throws DataAccessException {
		
		List<Map<String, Object>> list = accVouchMapper.queryAccVouchAccountingReport(entityMap);
		
		return ChdJson.toJson(list);
	}
	@Override
	public String updateBatchUnAccountingAccVouch(Map<String, Object> entityMap)
			throws DataAccessException {
		
		try {
			
			accLederMapper.deleteAccLederJz(entityMap);
				
			accLederCheckMapper.deleteAccLederCheckJz(entityMap);
				
			if("3".equals(entityMap.get("parent_node_id").toString()))
			{
				
				accVouchMapper.accUnAccountingCheckIndex1(entityMap);
				
			}
				
			if("2".equals(entityMap.get("parent_node_id").toString()))
			{
				//先更新所以的科目状态为3
				accVouchMapper.accUnAccountingCheckIndex2(entityMap);
				//再把含现金银行科目的凭证状态更新为2
				accVouchMapper.accUnAccountingCheckIndex3(entityMap);
			}
				
			return "{\"msg\":\"取消记账成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);

			return "{\"error\":\"取消记账失败 数据库异常 请联系管理员! 错误编码 updateBatchUnAccountingAccVouch\"}";
			
		}
		
	}

	@Override
	public List<Map<String, Object>> queryAccVouchAccountingReportPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = accVouchMapper.queryAccVouchAccountingReport(entityMap);
		
		return list;
	}


	@Override
	public List<Map<String, Object>> queryAccVouchAccountPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = accVouchMapper.queryAccVouchAccount(entityMap);
		
		return list;
	}
	
}
