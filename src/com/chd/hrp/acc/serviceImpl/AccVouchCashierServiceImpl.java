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
import com.chd.hrp.acc.service.AccVouchCashierService;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description. 凭证主表<BR>
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("accVouchCashierService")
public class AccVouchCashierServiceImpl implements AccVouchCashierService {

	private static Logger logger = Logger.getLogger(AccVouchCashierServiceImpl.class);

	@Resource(name = "accVouchMapper")
	private final AccVouchMapper accVouchMapper = null;

	/**
	 * @Description 凭证主表<BR>
	 *              查询AccVouch出纳数据相关分页
	 * @param entityMap
	 *            RowBounds
	 * @return List<AccVouch>
	 * @throws DataAccessException
	 */
	@Override
	public String queryAccVouchCashier(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		List<AccVouch> list = accVouchMapper.queryAccVouchCashier(entityMap, rowBounds);
		PageInfo page = new PageInfo(list);
		return ChdJson.toJson(list, page.getTotal());
	}

	@Override
	public String updateBatchAccVouchCashier(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {

			if("2".equals(entityMap.get(0).get("state"))){
				
				int count=accVouchMapper.queryAccVouchAuditCashByCheck(SessionManager.getGroupId(),SessionManager.getHosId(),SessionManager.getCopyCode(),2,entityMap);
				if(count>0){
					
					   return "{\"error\":\"当前状态不能签字。\",\"state\":\"false\"}";
								
				}
				
				accVouchMapper.updateBatchAccVouchCashier(entityMap);

				return "{\"msg\":\"签字成功.\",\"state\":\"true\"}";
				
			}else{
			
				int count=accVouchMapper.queryAccVouchUnAuditCashByCheck(SessionManager.getGroupId(),SessionManager.getHosId(),SessionManager.getCopyCode(),2,entityMap);
				if(count>0){
					
					  return "{\"error\":\"只能取消签字的凭证\",\"state\":\"false\"}";
								
				}
				
				accVouchMapper.updateBatchAccVouchUnCashier(entityMap);
				
				return "{\"msg\":\"取消成功.\",\"state\":\"true\"}";
			}

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}
	
}
