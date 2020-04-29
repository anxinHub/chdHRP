package com.chd.hrp.prm.serviceImpl;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.chd.hrp.prm.dao.HrpPrmSelectMapper;
import com.chd.hrp.prm.service.HrpPrmSelectService;

@Service("hrpPrmSelectService")
public class HrpPrmSelectServiceImpl implements HrpPrmSelectService {

	private static Logger logger = Logger.getLogger(HrpPrmSelectServiceImpl.class);

	@Resource(name = "hrpPrmSelectMapper")
	private final HrpPrmSelectMapper hrpPrmSelectMapper = null;

	RowBounds rowBoundsALL = new RowBounds(0, 20);

	/**
	 * 科室分类
	 */
	@Override
	public String queryPrmDeptKind(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub

		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpPrmSelectMapper.queryPrmDeptKind(entityMap, rowBounds));
	}
	@Override
	public String queryPrmDeptHip1(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		
		return JSON.toJSONString(hrpPrmSelectMapper.queryPrmDeptHip1(entityMap));
	}
	
	/**
	 * 同步平台分类
	 * @param mapVo
	 * @return
	 */
	@Override
	public String quneryPlatformKind(Map<String, Object> entityMap) throws DataAccessException  {
		// TODO Auto-generated method stub
		
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpPrmSelectMapper.quneryPlatformKind(entityMap, rowBounds));
	}

	/**
	 * 科室性质
	 */
	public String queryPrmDeptNature(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub

		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpPrmSelectMapper.queryPrmDeptNature(entityMap, rowBounds));
	}

	/**
	 * 科室字典
	 */
	public String queryPrmDept(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub

		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpPrmSelectMapper.queryPrmDept(entityMap, rowBounds));
	}

	/**
	 * 科室变更字典
	 */
	public String queryPrmDeptDict(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
        entityMap.put("is_stop", "0");
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpPrmSelectMapper.queryPrmDeptDict(entityMap, rowBounds));
	}

	/**
	 * KPI指标
	 */
	@Override
	public String queryPrmKpiDim(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub

		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpPrmSelectMapper.queryPrmKpiDim(entityMap, rowBounds));

	}

	@Override
	public String queryPrmKpiLibDict(Map<String, Object> entityMap) throws DataAccessException {

		// TODO Auto-generated method stub
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}

		return JSON.toJSONString(hrpPrmSelectMapper.queryPrmKpiLibDict(entityMap, rowBounds));
	}

	@Override
	public String queryPrmKpiNatureDict(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}

		return JSON.toJSONString(hrpPrmSelectMapper.queryPrmKpiNatureDict(entityMap, rowBounds));
	}

	/**
	 * 科室平台对应性质
	 */
	@Override
	public String queryPrmDeptRefDict(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}

		return JSON.toJSONString(hrpPrmSelectMapper.queryPrmDeptRefDict(entityMap, rowBounds));
	}
	@Override
	public String queryPrmDeptHipName(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		
		return JSON.toJSONString(hrpPrmSelectMapper.queryPrmDeptHipName(entityMap));
	}
	/* 绩效指标字典 */
	@Override
	public String quertPrmTargetDict(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub

		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}

		return JSON.toJSONString(hrpPrmSelectMapper.quertPrmTargetDict(entityMap, rowBounds));

	}

	/* 绩效指标性质 */
	@Override
	public String quertPrmTargetNatureDict(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub

		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}

		return JSON.toJSONString(hrpPrmSelectMapper.quertPrmTargetNatureDict(entityMap, rowBounds));
	}

	/**
	 * 取值方法
	 */
	@Override
	public String queryPrmTargetMethodPara(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}

		return JSON.toJSONString(hrpPrmSelectMapper.queryPrmTargetMethodPara(entityMap, rowBounds));
	}

	/**
	 * 指标性质
	 */
	@Override
	public String queryPrmTargetNature(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}

		return JSON.toJSONString(hrpPrmSelectMapper.queryPrmTargetNature(entityMap, rowBounds));
	}

	/**
	 * 指示灯编码
	 */
	@Override
	public String quertPrmLedDict(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}

		return JSON.toJSONString(hrpPrmSelectMapper.quertPrmLedDict(entityMap, rowBounds));
	}

	/**
	 * 函数分类
	 */
	@Override
	public String queryPrmFunType(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}

		return JSON.toJSONString(hrpPrmSelectMapper.queryPrmFunType(entityMap, rowBounds));
	}

	@Override
	public String quertSysHosInfoDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}

		return JSON.toJSONString(hrpPrmSelectMapper.quertSysHosInfoDict(entityMap, rowBounds));
	}

	@Override
	public String quertPrmGoalDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}

		return JSON.toJSONString(hrpPrmSelectMapper.quertPrmGoalDict(entityMap, rowBounds));
	}

	@Override
	public String queryPrmFormula(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}

		return JSON.toJSONString(hrpPrmSelectMapper.queryPrmFormula(entityMap, rowBounds));
	}

	/**
	 * 绩效函数参数取值表
	 */

	@Override
	public String queryPrmFunParaMethod(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}

		return JSON.toJSONString(hrpPrmSelectMapper.queryPrmFunParaMethod(entityMap, rowBounds));
	}

	@Override
	public String quertHosUnitDict(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}

		return JSON.toJSONString(hrpPrmSelectMapper.quertHosUnitDict(entityMap, rowBounds));
	}
	/**
	 * 职工变更
	 */
	@Override
	public String quertPrmEmpDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}

		return JSON.toJSONString(hrpPrmSelectMapper.quertPrmEmpDict(entityMap, rowBounds));
	}

	/* 表名:PRM_GRADE_PARA 解释:0204 指标评分方法参数表 */
	@Override
	public String queryPrmGradePara(Map<String, Object> entityMap) throws DataAccessException {

		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
			
		} else {
			rowBounds = rowBoundsALL;
		}

		return JSON.toJSONString(hrpPrmSelectMapper.queryPrmGradePara(entityMap, rowBounds));
	}

	/**
	 * 职务字典
	 */
	@Override
	public String queryPrmEmpDuty(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		
		return JSON.toJSONString(hrpPrmSelectMapper.queryPrmEmpDuty(entityMap, rowBounds));
	}
	
	/**
	 * 职工字典
	 */
	@Override
	public String queryPrmEmp(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		
		return JSON.toJSONString(hrpPrmSelectMapper.queryPrmEmp(entityMap, rowBounds));
	}

	@Override
	public String queryPrmEmpDict(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
	
		entityMap.put("is_stop", "0");
				RowBounds rowBounds = new RowBounds(0, 20);
				if (entityMap.get("pageSize") != null) {
					rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
				}else{
					 rowBounds = rowBoundsALL;
				}
				
				return JSON.toJSONString(hrpPrmSelectMapper.queryPrmEmpDict(entityMap, rowBounds));
	}
	@Override
	public String queryPrmHosKpi(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		
		return JSON.toJSONString(hrpPrmSelectMapper.queryPrmHosKpi(entityMap, rowBounds));
	}

	@Override
	public String queryPrmDeptKpi(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		
		return JSON.toJSONString(hrpPrmSelectMapper.queryPrmDeptKpi(entityMap, rowBounds));
	}

	@Override
	public String queryPrmEmpKpi(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		
		return JSON.toJSONString(hrpPrmSelectMapper.queryPrmEmpKpi(entityMap, rowBounds));
	}

	@Override
	public String queryPrmHosKpiSuperKpiCode(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		
		return JSON.toJSONString(hrpPrmSelectMapper.queryPrmHosKpiSuperKpiCode(entityMap, rowBounds));
	}

	@Override
	public String queryHosEmpDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		
		return JSON.toJSONString(hrpPrmSelectMapper.queryHosEmpDict(entityMap, rowBounds));
	}

	@Override
	public String queryPrmDeptKpiSuperKpiCode(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		
		return JSON.toJSONString(hrpPrmSelectMapper.queryPrmDeptKpiSuperKpiCode(entityMap, rowBounds));
	}

	@Override
	public String queryPrmEmpKpiSuperKpiCode(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		
		return JSON.toJSONString(hrpPrmSelectMapper.queryPrmEmpKpiSuperKpiCode(entityMap, rowBounds));
	}

	@Override
	public String quertPrmComType(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		
		return JSON.toJSONString(hrpPrmSelectMapper.quertPrmComType(entityMap, rowBounds));
	}

	@Override
	public String queryPrmOraclePkg(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		
		return JSON.toJSONString(hrpPrmSelectMapper.queryPrmOraclePkg(entityMap, rowBounds));
	}

	


}
