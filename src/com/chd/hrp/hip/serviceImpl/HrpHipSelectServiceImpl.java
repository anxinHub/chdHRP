package com.chd.hrp.hip.serviceImpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.chd.hrp.hip.dao.HrpHipSelectMapper;
import com.chd.hrp.hip.entity.HrpHipSelect;
import com.chd.hrp.hip.service.HrpHipSelectService;

@Service("hrpHipSelectService")
public class HrpHipSelectServiceImpl implements HrpHipSelectService {

	private static Logger logger = Logger.getLogger(HrpHipSelectServiceImpl.class);

	@Resource(name = "hrpHipSelectMapper")
	private final HrpHipSelectMapper hrpHipSelectMapper = null;

	RowBounds rowBoundsALL = new RowBounds(0, 20);

	@Override
	public String querySysMod(Map<String, Object> entityMap) throws DataAccessException {

		RowBounds rowBounds = new RowBounds(0, 20);

		if (entityMap.get("pageSize") != null) {

			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));

		} else {

			rowBounds = rowBoundsALL;

		}

		return JSON.toJSONString(hrpHipSelectMapper.querySysMod(entityMap, rowBounds));
	}

	@Override
	public String queryHipInitView(Map<String, Object> entityMap) throws DataAccessException {

		RowBounds rowBounds = new RowBounds(0, 20);

		if (entityMap.get("pageSize") != null) {

			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));

		} else {

			rowBounds = rowBoundsALL;

		}

		return JSON.toJSONString(hrpHipSelectMapper.queryHipInitView(entityMap, rowBounds));
	}

	@Override
	public String queryHipDsCof(Map<String, Object> entityMap) throws DataAccessException {

		RowBounds rowBounds = new RowBounds(0, 20);

		if (entityMap.get("pageSize") != null) {

			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));

		} else {

			rowBounds = rowBoundsALL;

		}

		return JSON.toJSONString(hrpHipSelectMapper.queryHipDsCof(entityMap, rowBounds));
	}

	@Override
	public String queryHipDeptDict(Map<String, Object> entityMap) throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {

			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));

		} else {

			rowBounds = rowBoundsALL;

		}
		
		//查询是否存在同名的dblink，如果存在下拉列表优先从dblink中读取数据
		int is_exists = hrpHipSelectMapper.existsDblink(entityMap);
		
		if(is_exists > 0){
			entityMap.put("hip_view_code", "HIP_DEPT_DICT");
			
			String view_name = hrpHipSelectMapper.queryDblinkViewName(entityMap);

			entityMap.put("view_name", view_name);
			entityMap.put("col_code", "dept_code");
			entityMap.put("col_name", "dept_name");
			entityMap.remove("is_last");
			//return JSON.toJSONString(hrpHipSelectMapper.queryDblinkDict(entityMap,rowBounds));
			return JSON.toJSONString(hrpHipSelectMapper.queryDblinkDict(entityMap));
		}else{
			//return JSON.toJSONString(hrpHipSelectMapper.queryHipDeptDict(entityMap,rowBounds));
			return JSON.toJSONString(hrpHipSelectMapper.queryHipDeptDict(entityMap));
		}
	}

	@Override
	public String queryHosDeptDict(Map<String, Object> entityMap) throws DataAccessException {
		
		/*RowBounds rowBounds = new RowBounds(0, 20);

		if (entityMap.get("pageSize") != null) {

			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));

		} else {

			rowBounds = rowBoundsALL;

		}
		return JSON.toJSONString(hrpHipSelectMapper.queryHosDeptDict(entityMap,rowBounds));
		*/

		return JSON.toJSONString(hrpHipSelectMapper.queryHosDeptDict(entityMap));
	}

	@Override
	public String queryHipChargeKindDict(Map<String, Object> entityMap) throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20);

		if (entityMap.get("pageSize") != null) {

			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));

		} else {

			rowBounds = rowBoundsALL;

		}

		return JSON.toJSONString(hrpHipSelectMapper.queryHipChargeKindDict(entityMap, rowBounds));
	}

	@Override
	public String queryCostChargeKindArrt(Map<String, Object> entityMap) throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20);

		if (entityMap.get("pageSize") != null) {

			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));

		} else {

			rowBounds = rowBoundsALL;

		}

		return JSON.toJSONString(hrpHipSelectMapper.queryCostChargeKindArrt(entityMap, rowBounds));
	}

	@Override
	public String queryHipChargeDetailDict(Map<String, Object> entityMap) throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20);

		if (entityMap.get("pageSize") != null) {

			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));

		} else {

			rowBounds = rowBoundsALL;

		}

		return JSON.toJSONString(hrpHipSelectMapper.queryHipChargeDetailDict(entityMap, rowBounds));
	}

	@Override
	public String queryCostChargeItemArrt(Map<String, Object> entityMap) throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20);

		if (entityMap.get("pageSize") != null) {

			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));

		} else {

			rowBounds = rowBoundsALL;

		}

		return JSON.toJSONString(hrpHipSelectMapper.queryCostChargeItemArrt(entityMap, rowBounds));
	}

	@Override
	public String queryHipPayTypeDict(Map<String, Object> entityMap) throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20);

		if (entityMap.get("pageSize") != null) {

			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));

		} else {

			rowBounds = rowBoundsALL;

		}

		return JSON.toJSONString(hrpHipSelectMapper.queryHipPayTypeDict(entityMap, rowBounds));
	}

	@Override
	public String queryAccPayType(Map<String, Object> entityMap) throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20);

		if (entityMap.get("pageSize") != null) {

			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));

		} else {

			rowBounds = rowBoundsALL;

		}

		return JSON.toJSONString(hrpHipSelectMapper.queryAccPayType(entityMap, rowBounds));
	}

	@Override
	public String queryHipPatientTypeDict(Map<String, Object> entityMap) throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20);

		if (entityMap.get("pageSize") != null) {

			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));

		} else {

			rowBounds = rowBoundsALL;

		}

		return JSON.toJSONString(hrpHipSelectMapper.queryHipPatientTypeDict(entityMap, rowBounds));
	}

	@Override
	public String queryHosPatientType(Map<String, Object> entityMap) throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20);

		if (entityMap.get("pageSize") != null) {

			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));

		} else {

			rowBounds = rowBoundsALL;

		}

		return JSON.toJSONString(hrpHipSelectMapper.queryHosPatientType(entityMap, rowBounds));
	}

	@Override
	public String queryHipSupDict(Map<String, Object> entityMap) throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20);

		if (entityMap.get("pageSize") != null) {

			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));

		} else {

			rowBounds = rowBoundsALL;

		}

		return JSON.toJSONString(hrpHipSelectMapper.queryHipSupDict(entityMap, rowBounds));
	}

	@Override
	public String queryHosSupDict(Map<String, Object> entityMap) throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20);

		if (entityMap.get("pageSize") != null) {

			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));

		} else {

			rowBounds = rowBoundsALL;

		}

		return JSON.toJSONString(hrpHipSelectMapper.queryHosSupDict(entityMap, rowBounds));
	}

	@Override
	public String queryHipStoreDict(Map<String, Object> entityMap) throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20);

		if (entityMap.get("pageSize") != null) {

			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));

		} else {

			rowBounds = rowBoundsALL;

		}

		return JSON.toJSONString(hrpHipSelectMapper.queryHipStoreDict(entityMap, rowBounds));
	}

	@Override
	public String queryHosStoreDict(Map<String, Object> entityMap) throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20);

		if (entityMap.get("pageSize") != null) {

			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));

		} else {

			rowBounds = rowBoundsALL;

		}

		return JSON.toJSONString(hrpHipSelectMapper.queryHosStoreDict(entityMap, rowBounds));
	}

	@Override
	public String queryHipMedTypeDict(Map<String, Object> entityMap) throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20);

		if (entityMap.get("pageSize") != null) {

			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));

		} else {

			rowBounds = rowBoundsALL;

		}

		return JSON.toJSONString(hrpHipSelectMapper.queryHipMedTypeDict(entityMap, rowBounds));
	}

	@Override
	public String queryMedType(Map<String, Object> entityMap) throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20);

		if (entityMap.get("pageSize") != null) {

			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));

		} else {

			rowBounds = rowBoundsALL;

		}

		return JSON.toJSONString(hrpHipSelectMapper.queryMedType(entityMap, rowBounds));
	}

	@Override
	public String queryHipMedDict(Map<String, Object> entityMap) throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20);

		if (entityMap.get("pageSize") != null) {

			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));

		} else {

			rowBounds = rowBoundsALL;

		}

		return JSON.toJSONString(hrpHipSelectMapper.queryHipMedDict(entityMap, rowBounds));
	}

	@Override
	public String queryMedInv(Map<String, Object> entityMap) throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20);

		if (entityMap.get("pageSize") != null) {

			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));

		} else {

			rowBounds = rowBoundsALL;

		}

		return JSON.toJSONString(hrpHipSelectMapper.queryMedInv(entityMap, rowBounds));
	}

	@Override
	public String queryHipMatTypeDict(Map<String, Object> entityMap) throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20);

		if (entityMap.get("pageSize") != null) {

			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));

		} else {

			rowBounds = rowBoundsALL;

		}

		return JSON.toJSONString(hrpHipSelectMapper.queryHipMatTypeDict(entityMap, rowBounds));
	}

	@Override
	public String queryMatTypeDict(Map<String, Object> entityMap) throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20);

		if (entityMap.get("pageSize") != null) {

			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));

		} else {

			rowBounds = rowBoundsALL;

		}

		return JSON.toJSONString(hrpHipSelectMapper.queryMatTypeDict(entityMap, rowBounds));
	}

	@Override
	public String queryHipMatInvDict(Map<String, Object> entityMap) throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20);

		if (entityMap.get("pageSize") != null) {

			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));

		} else {

			rowBounds = rowBoundsALL;

		}

		return JSON.toJSONString(hrpHipSelectMapper.queryHipMatInvDict(entityMap, rowBounds));
	}

	@Override
	public String queryMatInvDict(Map<String, Object> entityMap) throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20);

		if (entityMap.get("pageSize") != null) {

			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));

		} else {

			rowBounds = rowBoundsALL;

		}

		return JSON.toJSONString(hrpHipSelectMapper.queryMatInvDict(entityMap, rowBounds));
	}

	@Override
	public String queryHipAssTypeDict(Map<String, Object> entityMap) throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20);

		if (entityMap.get("pageSize") != null) {

			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));

		} else {

			rowBounds = rowBoundsALL;

		}

		return JSON.toJSONString(hrpHipSelectMapper.queryHipAssTypeDict(entityMap, rowBounds));
	}

	@Override
	public String queryAssTypeDict(Map<String, Object> entityMap) throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20);

		if (entityMap.get("pageSize") != null) {

			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));

		} else {

			rowBounds = rowBoundsALL;

		}

		return JSON.toJSONString(hrpHipSelectMapper.queryAssTypeDict(entityMap, rowBounds));
	}

	@Override
	public String queryHipAssDict(Map<String, Object> entityMap) throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20);

		if (entityMap.get("pageSize") != null) {

			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));

		} else {

			rowBounds = rowBoundsALL;

		}

		return JSON.toJSONString(hrpHipSelectMapper.queryHipAssDict(entityMap, rowBounds));
	}

	@Override
	public String queryAssDict(Map<String, Object> entityMap) throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20);

		if (entityMap.get("pageSize") != null) {

			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));

		} else {

			rowBounds = rowBoundsALL;

		}

		return JSON.toJSONString(hrpHipSelectMapper.queryAssDict(entityMap, rowBounds));
	}

	@Override
	public String queryHipSourceDict(Map<String, Object> entityMap) throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20);

		if (entityMap.get("pageSize") != null) {

			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));

		} else {

			rowBounds = rowBoundsALL;

		}

		return JSON.toJSONString(hrpHipSelectMapper.queryHipSourceDict(entityMap, rowBounds));
	}

	@Override
	public String queryHosSource(Map<String, Object> entityMap) throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20);

		if (entityMap.get("pageSize") != null) {

			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));

		} else {

			rowBounds = rowBoundsALL;

		}

		return JSON.toJSONString(hrpHipSelectMapper.queryHosSource(entityMap, rowBounds));
	}

	@Override
	public String queryHipPaymentItemDict(Map<String, Object> entityMap) throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20);

		if (entityMap.get("pageSize") != null) {

			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));

		} else {

			rowBounds = rowBoundsALL;

		}

		return JSON.toJSONString(hrpHipSelectMapper.queryHipPaymentItemDict(entityMap, rowBounds));
	}

	@Override
	public String queryBudgPaymentItemDict(Map<String, Object> entityMap) throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20);

		if (entityMap.get("pageSize") != null) {

			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));

		} else {

			rowBounds = rowBoundsALL;

		}

		return JSON.toJSONString(hrpHipSelectMapper.queryBudgPaymentItemDict(entityMap, rowBounds));
	}

	@Override
	public String queryHosInfoDict(Map<String, Object> entityMap) throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20);

		if (entityMap.get("pageSize") != null) {

			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));

		} else {

			rowBounds = rowBoundsALL;

		}

		return JSON.toJSONString(hrpHipSelectMapper.queryHosInfoDict(entityMap, rowBounds));
	}

	@Override
	public String queryHosCopy(Map<String, Object> entityMap) throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20);

		if (entityMap.get("pageSize") != null) {

			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));

		} else {

			rowBounds = rowBoundsALL;

		}

		return JSON.toJSONString(hrpHipSelectMapper.queryHosCopy(entityMap, rowBounds));
	}
	
	@Override
	public String queryALLMatTypeDict(Map<String, Object> entityMap) throws DataAccessException {
		return JSON.toJSONString(hrpHipSelectMapper.queryALLMatTypeDict(entityMap));
	}

	@Override
	public String queryALLMatFimTypeDict(Map<String, Object> mapVo) {
		// TODO Auto-generated method stub
		return JSON.toJSONString(hrpHipSelectMapper.queryALLMatFimTypeDict(mapVo));
	}
	
	@Override
	public String queryALLMedTypeDict(Map<String, Object> entityMap) throws DataAccessException {
		return JSON.toJSONString(hrpHipSelectMapper.queryALLMedTypeDict(entityMap));
	}

	@Override
	public String queryALLMedFimTypeDict(Map<String, Object> mapVo) {
		// TODO Auto-generated method stub
		return JSON.toJSONString(hrpHipSelectMapper.queryALLMedFimTypeDict(mapVo));
	}

	@Override
	public String queryHipDataSource(Map<String, Object> mapVo) throws DataAccessException {
		// TODO Auto-generated method stub
		return JSON.toJSONString(hrpHipSelectMapper.queryHipDataSource(mapVo));
	}


}
