package com.chd.hrp.hr.serviceImpl.salarymanagement.accumulationfund;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.hr.dao.salarymanagement.accumulationfund.HrFundBaseMapper;
import com.chd.hrp.hr.service.salarymanagement.accumulationfund.HrFundBaseService;
import com.github.pagehelper.PageInfo;
/**
 * 【薪资管理-公积金等】：公积金缴费基数
 * @author yang
 *
 */
@Service("hrFundBaseService")
public class HrFundBaseServiceImpl implements HrFundBaseService {

	private static Logger logger = Logger.getLogger(HrFundBaseServiceImpl.class);
	
	// 引入DAO
	@Resource(name="hrFundBaseMapper")
	private final HrFundBaseMapper hrFundBaseMapper = null;

	// 查询
	@Override
	public String queryHrFundBase(Map<String, Object> paramMap) throws DataAccessException {
		paramMap.put("group_id", SessionManager.getGroupId());
		paramMap.put("hos_id", SessionManager.getHosId());
		if(paramMap.get("dept_id") != null && paramMap.get("dept_id").toString() != ""){
			paramMap.put("dept_id", paramMap.get("dept_id").toString().split("@")[1]);
		}
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) paramMap.get("sysPage");
		if(sysPage.getTotal() == -1){
			List<Map<String, Object>> list = ChdJson
				.toListLower((List<Map<String, Object>>) hrFundBaseMapper.query(paramMap));
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String,Object>> list = ChdJson
				.toListLower((List<Map<String, Object>>) hrFundBaseMapper.query(paramMap, rowBounds));
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}
}
