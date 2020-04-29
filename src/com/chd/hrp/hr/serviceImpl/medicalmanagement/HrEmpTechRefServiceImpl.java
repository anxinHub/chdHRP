/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.serviceImpl.medicalmanagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.apache.velocity.runtime.directive.Foreach;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.hr.dao.medicalmanagement.HrAccessTechnologyMapper;
import com.chd.hrp.hr.dao.medicalmanagement.HrEmpTechRefMapper;
import com.chd.hrp.hr.entity.medicalmanagement.HrAccessTechnology;
import com.chd.hrp.hr.entity.medicalmanagement.HrEmpTechExec;
import com.chd.hrp.hr.entity.medicalmanagement.HrEmpTechRef;
import com.chd.hrp.hr.service.medicalmanagement.HrEmpTechRefService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 
 * @Table:
 * HR_EMP_TECH_REF
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("hrEmpTechRefService")
public class HrEmpTechRefServiceImpl implements HrEmpTechRefService {

	private static Logger logger = Logger.getLogger(HrEmpTechRefServiceImpl.class);
	//引入DAO操作
	@Resource(name = "hrEmpTechRefMapper")
	private final HrEmpTechRefMapper hrEmpTechRefMapper = null;
    
	

	@Resource(name = "hrAccessTechnologyMapper")
	private final HrAccessTechnologyMapper hrAccessTechnologyMapper = null;

	/**
	 * @Description 
	 * 添加<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addTechRef(Map<String, Object> entityMap)
			throws DataAccessException {
		String msg = "";
		List<HrEmpTechRef> alllistVo = JSONArray.parseArray(
				String.valueOf(entityMap.get("paramVo")), HrEmpTechRef.class);
		try {
			HrAccessTechnology accessTechnology = hrAccessTechnologyMapper.queryByCodeExec(entityMap);
			if (accessTechnology != null) {
				
				int state = hrEmpTechRefMapper.addTechRef(alllistVo,entityMap);

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
	public String deleteHrTechRef(List<HrEmpTechExec> entityList)
			throws DataAccessException {

		try {
			
			if (entityList != null) {
				Map<String,Object> mapVo=new HashMap<String,Object>();
				mapVo.put("group_id", SessionManager.getGroupId())   ;
				mapVo.put("hos_id", SessionManager.getHosId())   ;
				hrEmpTechRefMapper.deleteHrTechRef(entityList,mapVo);
			}

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	@Override
	public String updateHrEmpTechRef(Map<String, Object> entityMap)
			throws DataAccessException {

		try {
			List<HrEmpTechRef> alllistVo = JSONArray.parseArray(String.valueOf(entityMap.get("paramVo")),
					HrEmpTechRef.class);


			hrEmpTechRefMapper.deleteHrTechRef(alllistVo,entityMap);
				int state = hrEmpTechRefMapper.addTechRef(alllistVo,entityMap);
			
			
		

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	@Override
	public String queryHrEmpTechRef(Map<String, Object> entityMap)
			throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrEmpTechRef> list = (List<HrEmpTechRef>) hrEmpTechRefMapper
					.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(),
					sysPage.getPagesize());

			List<HrEmpTechRef> list = (List<HrEmpTechRef>) hrEmpTechRefMapper
					.query(entityMap, rowBounds);

			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	
	
}
