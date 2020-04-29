package com.chd.hrp.hr.serviceImpl.salarymanagement.socialSecurityManage;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.hr.dao.salarymanagement.socialSecurityManage.HrInsurBaseMapper;
import com.chd.hrp.hr.service.salarymanagement.socialSecurityManage.HrInsurBaseService;
import com.github.pagehelper.PageInfo;
/**
 * 【薪资管理-社保管理】：社保缴费基数
 * @author yang
 *
 */
@Service("hrInsurBaseService")
public class HrInsurBaseServiceImpl implements HrInsurBaseService {
	
	private static Logger logger = Logger.getLogger(HrInsurBaseServiceImpl.class);

	@Resource(name = "hrInsurBaseMapper")
	private final HrInsurBaseMapper hrInsurBaseMapper = null;
	
	// 主查询
	@Override
	public Map<String, Object> queryHrInsurBase(Map<String, Object> paramMap) throws DataAccessException {
		if(paramMap.get("group_id") == null){
			paramMap.put("group_id", SessionManager.getGroupId());
		}
		if(paramMap.get("hos_id") == null){
			paramMap.put("hos_id", SessionManager.getHosId());
		}
		if(StringUtils.isNotEmpty(paramMap.get("dept_id").toString())){
			paramMap.put("dept_id", paramMap.get("dept_id").toString().split("@")[1]);
		}
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) paramMap.get("sysPage");
		if(sysPage.getTotal() == -1){
			List<Map<String, Object>> list = ChdJson
				.toListLower((List<Map<String, Object>>) hrInsurBaseMapper.query(paramMap));
			return JSONObject.parseObject(ChdJson.toJson(list));
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String,Object>> list = ChdJson
				.toListLower((List<Map<String,Object>>) hrInsurBaseMapper.query(paramMap, rowBounds));
			PageInfo page = new PageInfo(list);
			return JSONObject.parseObject(ChdJson.toJson(list, page.getTotal()));
		}
	}

}
