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

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.vouch.AccVouchMapper;
import com.chd.hrp.acc.entity.AccVouch;
import com.chd.hrp.acc.entity.AccWageEmp;
import com.chd.hrp.acc.service.AccVouchAuditService;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description. 凭证主表<BR>
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("accVouchAuditService")
public class AccVouchAuditServiceImpl implements AccVouchAuditService {

	private static Logger logger = Logger.getLogger(AccVouchAuditServiceImpl.class);

	@Resource(name = "accVouchMapper")
	private final AccVouchMapper accVouchMapper = null;
	
	
	/**
	 * @Description 凭证主表<BR>
	 *              查询AccVouch审核数据相关分页
	 * @param entityMap
	 *            RowBounds
	 * @return List<AccVouch>
	 * @throws DataAccessException
	 */
	@Override
	public String queryAccVouchAudit(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage)entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<Map<String, Object>> list = accVouchMapper.queryAccVouchAudit(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = accVouchMapper.queryAccVouchAudit(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list,page.getTotal());
		}
	}


	@Override
	public String updateBatchAccVouchAuditLabel(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {

			accVouchMapper.updateBatchAccVouchLabel(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateBatchAccVouchAuditLabel\"}";

		}
	}
	
	//审核
	@Override
	public String updateBatchAccVouchAudit(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {

				
			if("3".equals(entityMap.get(0).get("state"))){
				//判断是否已审核、记账
				int count=accVouchMapper.queryAccVouchAuditByCheck(SessionManager.getGroupId(),SessionManager.getHosId(),SessionManager.getCopyCode(),entityMap);
				if(count>0){
					
					  return "{\"error\":\"草稿、已审核、记账的凭证，不能审核。\",\"state\":\"false\"}";
								
				}
				accVouchMapper.updateBatchAccVouchCashier(entityMap);

				return "{\"msg\":\"审核成功.\",\"state\":\"true\"}";
				
			}else{
				
				int count=accVouchMapper.queryAccVouchUnAuditCashByCheck(SessionManager.getGroupId(),SessionManager.getHosId(),SessionManager.getCopyCode(),3,entityMap);
				if(count>0){
					
					  return "{\"error\":\"只能取消审核的凭证。\",\"state\":\"false\"}";
								
				}
				
				accVouchMapper.updateBatchAccVouchUnAudit(entityMap);
				
				return "{\"msg\":\"取消成功.\",\"state\":\"true\"}";
			}
			
			

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}


	//有现金银行科目分开处理取消审核
	@Override
	public String updateBatchAccVouchAuditBank(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			
			int count=accVouchMapper.queryAccVouchUnAuditCashByCheck(SessionManager.getGroupId(),SessionManager.getHosId(),SessionManager.getCopyCode(),3,entityMap);
			if(count>0){
				  return "{\"msg\":\"只能取消审核的凭证。\",\"state\":\"false\"}";
			}
						
			accVouchMapper.updateBatchAccVouchCashierNotBank(entityMap);//非现金银行科目
		
			accVouchMapper.updateBatchAccVouchCashierBank(entityMap);//现金银行科目
				
			return "{\"msg\":\"取消成功.\",\"state\":\"true\"}";
			

		}catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}
	}


	@Override
	public List<Map<String, Object>> queryAccVouchAuditPrint(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = accVouchMapper.queryAccVouchAudit(entityMap);
		
		return list;
	}
	
	//全部审核、全部取消
	@Override
	public String auditAll(Map<String, Object> map) throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			String yearMonth=map.get("year_month").toString();
			String accYear=yearMonth.substring(0, 4);
			String accMonth=yearMonth.substring(5, 7);
			map.put("acc_year", accYear);
			map.put("acc_month", accMonth);
			
/*			int count=accVouchMapper.queryVouchByState99(map);
			if(count>0){
				return "{\"error\":\"有记账的凭证.\",\"state\":\"false\"}";
			}*/
			
			accVouchMapper.updateAuditAll(map);
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	
}
