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
import com.chd.hrp.hr.dao.medicalmanagement.HrAccessTechnologyMapper;
import com.chd.hrp.hr.dao.medicalmanagement.HrEmpTechExecMapper;
import com.chd.hrp.hr.entity.medicalmanagement.HrAccessTechnology;
import com.chd.hrp.hr.entity.medicalmanagement.HrEmpTechExec;
import com.chd.hrp.hr.service.medicalmanagement.HrEmpTechExecService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 
 * @Table:
 * HR_EMP_TECH_EXEC
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("hrEmpTechExecService")
public class HrEmpTechExecServiceImpl implements HrEmpTechExecService {

	private static Logger logger = Logger.getLogger(HrEmpTechExecServiceImpl.class);
	//引入DAO操作
	@Resource(name = "hrEmpTechExecMapper")
	private final HrEmpTechExecMapper hrEmpTechExecMapper = null;
    
	@Resource(name = "hrAccessTechnologyMapper")
	private final HrAccessTechnologyMapper hrAccessTechnologyMapper = null;
	@Override
	public String addTechExec(Map<String, Object> entityMap)
			throws DataAccessException {
		String msg = "";
		List<HrEmpTechExec> alllistVo = JSONArray.parseArray(
				String.valueOf(entityMap.get("paramVo")), HrEmpTechExec.class);
		try {
			HrAccessTechnology accessTechnology = hrAccessTechnologyMapper.queryByCodeExec(entityMap);
			if (accessTechnology != null) {
				accessTechnology.setCase_num(alllistVo.size());
				hrAccessTechnologyMapper.updateCaseNuM(accessTechnology);
				int state = hrEmpTechExecMapper.addTechExec(alllistVo,entityMap);

				msg = "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			} else {
				msg = "{\"error\":\"请先添加主表.\",\"state\":\"false\"}";
			}

			return msg;
		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	
	@Override
	public String deleteHrTechExec(List<HrEmpTechExec> entityList)
			throws DataAccessException {

		try {
			Map<String,Object> mapVo=new HashMap<String,Object>();
			if (entityList != null) {
			
				mapVo.put("group_id", SessionManager.getGroupId())   ;
				mapVo.put("hos_id", SessionManager.getHosId())   ;
			
			}
			hrEmpTechExecMapper.deleteHrTechExec(entityList,mapVo);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}


	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrEmpTechExec> list = (List<HrEmpTechExec>) hrEmpTechExecMapper
					.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(),
					sysPage.getPagesize());

			List<HrEmpTechExec> list = (List<HrEmpTechExec>) hrEmpTechExecMapper
					.query(entityMap, rowBounds);

			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}


	@Override
	public String updateHrEmpTechExec(Map<String, Object> entityMap)
			throws DataAccessException {

		try {
			List<HrEmpTechExec> alllistVo = JSONArray.parseArray(String.valueOf(entityMap.get("paramVo")),HrEmpTechExec.class);
			@SuppressWarnings("unused")
			HrAccessTechnology accessTechnology = hrAccessTechnologyMapper.queryByCodeExec(entityMap);
			accessTechnology.setCase_num(alllistVo.size());
			hrAccessTechnologyMapper.updateCaseNuM(accessTechnology);
			
			 List<HrEmpTechExec> alllist =hrEmpTechExecMapper.queryHrEmpTechExec(entityMap);
			if(alllist.size()>0){
				 hrEmpTechExecMapper.updateBatchTechExec(alllistVo,entityMap);
			}else{
				hrEmpTechExecMapper.addTechExec(alllistVo,entityMap);
			}

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}


	
	
}
