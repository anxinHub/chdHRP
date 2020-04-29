/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.serviceImpl.medicalmanagement;

import java.text.SimpleDateFormat;
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
import com.chd.hrp.hr.dao.medicalmanagement.HrDrugPermDetailMapper;
import com.chd.hrp.hr.entity.medicalmanagement.HrDrugPerm;
import com.chd.hrp.hr.entity.medicalmanagement.HrDrugPermDetail;
import com.chd.hrp.hr.service.medicalmanagement.HrDrugPermDetailService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 
 * @Table:
 * HR_DRUG_PERM_DETAIL药品权限管理明细
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("hrDrugPermDetailService")
public class HrDrugPermDetailServiceImpl implements HrDrugPermDetailService {

	private static Logger logger = Logger.getLogger(HrDrugPermDetailServiceImpl.class);
	//引入DAO操作
	@Resource(name = "hrDrugPermDetailMapper")
	private final HrDrugPermDetailMapper hrDrugPermDetailMapper = null;
	
	@Override
	public Map<String, Object> queryEmp(Map<String, Object> mapVo) throws DataAccessException{
		return hrDrugPermDetailMapper.queryEmp(mapVo);
	}

	@Override
	public String addDrugPermDetail(Map<String, Object> entityMap)throws DataAccessException {
		try {
			
			List<HrDrugPermDetail> alllistVo = JSONArray.parseArray(String.valueOf(entityMap.get("paramVo")),
					HrDrugPermDetail.class);
			
			/**
			 * 增加
			 */
			if (alllistVo!=null && alllistVo.size() > 0) {
				
				for (HrDrugPermDetail hrDrugPermDetail : alllistVo) {
					hrDrugPermDetail.setGroup_id(Double.parseDouble((entityMap.get("group_id").toString())));
					hrDrugPermDetail.setHos_id(Double.parseDouble(entityMap.get("hos_id").toString()));
					//hrDrugPermDetail.setEmp_id(Double.parseDouble(entityMap.get("emp_id").toString()));
					hrDrugPermDetail.setState(0);
				}}
			/**
			 * 先删除
			 */
			hrDrugPermDetailMapper.deletehrDrugPermDetail(alllistVo,entityMap);
			int state = hrDrugPermDetailMapper.addBatch(alllistVo);

		return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
			
		}

	@Override
	public String deleteBatch(List<HrDrugPermDetail> entityList) throws DataAccessException{

		try {
			if (entityList !=null) {
				Map<String,Object> mapVo=new HashMap<String,Object>();
				mapVo.put("group_id", SessionManager.getGroupId())   ;
				mapVo.put("hos_id", SessionManager.getHosId())   ;
				
				hrDrugPermDetailMapper.deleteBatchDrugPermDetail(entityList,mapVo);
			}

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	@Override
	public String queryHrDrugPermDetail(Map<String, Object> entityMap)throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrDrugPermDetail> list = (List<HrDrugPermDetail>) hrDrugPermDetailMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HrDrugPermDetail> list = (List<HrDrugPermDetail>) hrDrugPermDetailMapper.query(entityMap, rowBounds);

			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}
    

	
}
