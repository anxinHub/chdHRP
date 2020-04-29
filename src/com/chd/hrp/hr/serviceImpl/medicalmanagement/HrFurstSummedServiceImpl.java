/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.hr.serviceImpl.medicalmanagement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.hr.dao.medicalmanagement.HrFurstSummedMapper;
import com.chd.hrp.hr.entity.medicalmanagement.HrFurstdSum;
import com.chd.hrp.hr.entity.medicalmanagement.HrFurstdSum;
import com.chd.hrp.hr.service.medicalmanagement.HrFurstSummedService;
import com.github.pagehelper.PageInfo;
/**
 * 
 * @Description:
 * 进修总结
 * @Table:
 * HR_FURSTD_SUM
 * @Author: ade
 * @email:  ade@e-tonggroup.com
 * @Version: 1.0
 */
@Service("hrFurstSummedService")
public class HrFurstSummedServiceImpl implements HrFurstSummedService{
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrEmpTechRefServiceImpl.class);
	//引入DAO操作
	@Resource(name = "hrFurstSummedMapper")
	private final HrFurstSummedMapper hrFurstSummedMapper = null;
	
	
	

	@Override
	public String addFurstSummed(Map<String, Object> entityMap)
			throws DataAccessException {

		try {
			
			HrFurstdSum HrFurstdSum	=hrFurstSummedMapper.queryByCode(entityMap);
			if(HrFurstdSum==null){
				int state = hrFurstSummedMapper.add(entityMap);
				return "{\"state\":\"true\"}";
			}else{
				return "{\"error\":\"请勿重复保存！\",\"state\":\"true\"}";
			}
			

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}
	@Override
	public HrFurstdSum queryByCodeFurstSummed(Map<String, Object> entityMap)
			throws DataAccessException {
		return hrFurstSummedMapper.queryByCode(entityMap);
		}
	@Override
	public String updateFurstSummed(Map<String, Object> entityMap)
			throws DataAccessException {

		try {

			@SuppressWarnings("unused")
			int state = hrFurstSummedMapper.update(entityMap);

			return "{\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}
	@Override
	public String deleteFurstSummed(List<HrFurstdSum> entityList)
			throws DataAccessException {

		try {
			if (entityList!=null) {
				Map<String,Object> mapVo=new HashMap<String,Object>();
				mapVo.put("group_id", SessionManager.getGroupId())   ;
				mapVo.put("hos_id", SessionManager.getHosId())   ;
				hrFurstSummedMapper.deleteFurstdApplication(entityList,mapVo);
			}
		
		

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}
	@Override
	public String queryFurstSummed(Map<String, Object> entityMap)
			throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrFurstdSum> list = (List<HrFurstdSum>) hrFurstSummedMapper
					.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(),
					sysPage.getPagesize());

			List<HrFurstdSum> list = (List<HrFurstdSum>) hrFurstSummedMapper.query(entityMap, rowBounds);

			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}
	@Override
	public String confirmHrFurstSummed(HrFurstdSum hrFurstSummed)
			throws DataAccessException {
		try {
	         
			hrFurstSummedMapper.confirmHrFurstSummed(hrFurstSummed);
			
			return "{\"msg\":\"提交成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}
	@Override
	public String reConfirmHrHrFurstSummed(HrFurstdSum hrFurstSummed)
			throws DataAccessException {
		try {
	         
			hrFurstSummedMapper.confirmHrFurstSummed(hrFurstSummed);
			
			return "{\"msg\":\"撤回成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}
	@Override
	public String auditHrFurstSummed(HrFurstdSum hrFurstSummed)
			throws DataAccessException {
		try {
	        
			hrFurstSummedMapper.confirmHrFurstSummed(hrFurstSummed);
			
			return "{\"msg\":\"审核成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

}
	@Override
	public String unauditHrHrFurstSummed(HrFurstdSum hrFurstSummed)
			throws DataAccessException {
		try {
	        
			hrFurstSummedMapper.confirmHrFurstSummed(hrFurstSummed);
			
			return "{\"msg\":\"销审成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

}
	@Override
	public String dispassHrHrFurstSummed(HrFurstdSum hrFurstSummed)
			throws DataAccessException {
		try {
	        
			hrFurstSummedMapper.confirmHrFurstSummed(hrFurstSummed);
			
			return "{\"msg\":\"未通过成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

}
	
	@Override
	public String queryAppNo(Map<String, Object> entityMap)
			throws DataAccessException {

		List<Map<String, Object>> list = hrFurstSummedMapper.queryAppNo(entityMap);
		return JSONArray.toJSONString(list);
	}
	@Override
	public String queryFurstdApp(Map<String, Object> entityMap)
			throws DataAccessException {
		Map<String, Object> list = hrFurstSummedMapper.queryFurstdApp(entityMap);
		return JSONArray.toJSONString(list);
}
	@Override
	public List<Map<String, Object>> queryFurstSummedByPrint(
			Map<String, Object> entityMap) throws DataAccessException {

		 List<Map<String,Object>> list = ChdJson.toListLower(hrFurstSummedMapper.queryFurstSummedByPrint(entityMap));
		 return list;
	}
}
