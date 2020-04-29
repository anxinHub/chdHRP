/**
 * 2015-1-20 SystemSelectDictServiceImpl.java author:pengjin
 */
package com.chd.hrp.hpm.serviceImpl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.chd.hrp.hpm.dao.AphiSelectDictMapper;
import com.chd.hrp.hpm.service.AphiSelectDictService;

@Service("aphiSelectDictService")
public class AphiSelectDictServiceImpl implements AphiSelectDictService {

	@Resource(name = "aphiSelectDictMapper")
	private AphiSelectDictMapper aphiSelectDictMapper = null;

	// 奖金项目字典
	@Override
	public String queryItemAllDict(Map<String, Object> map) throws DataAccessException {
		
		return JSON.toJSONString(aphiSelectDictMapper.queryItemAllDict(map));
		
	}

	@Override
	public String queryAppModDict(Map<String, Object> map) throws DataAccessException {
		
		// TODO Auto-generated method stub
		return JSON.toJSONString(aphiSelectDictMapper.queryAppModDict(map));
		
	}

	@Override
	public String queryDeptKindDict(Map<String, Object> map) throws DataAccessException {
		
		// TODO Auto-generated method stub
		return JSON.toJSONString(aphiSelectDictMapper.queryDeptKindDict(map));
		
	}

	@Override
	public String queryDeptNatureDict(Map<String, Object> map) throws DataAccessException {
		
		// TODO Auto-generated method stub
		return JSON.toJSONString(aphiSelectDictMapper.queryDeptNatureDict(map));
		
	}

	@Override
	public String queryDeptDict(Map<String, Object> map) throws DataAccessException {
		
		// TODO Auto-generated method stub
		return JSON.toJSONString(aphiSelectDictMapper.queryDeptDict(map));
		
	}
	
	//科室字典(传入时间)
	@Override
	public String queryDeptDictTime(Map<String, Object> map) throws DataAccessException {
		
		// TODO Auto-generated method stub
		return JSON.toJSONString(aphiSelectDictMapper.queryDeptDictTime(map));
		
	}
	
	
	@Override
	public String queryDeptRefDict(Map<String, Object> map) throws DataAccessException {
		
		// TODO Auto-generated method stub
		return JSON.toJSONString(aphiSelectDictMapper.queryDeptRefDict(map));
		
	}
	
	@Override
	public String querySysDeptDict(Map<String, Object> map) throws DataAccessException {
		
		// TODO Auto-generated method stub
		return JSON.toJSONString(aphiSelectDictMapper.querySysDeptDict(map));
		
	}

	@Override
	public String queryEmpDutyDict(Map<String, Object> map) throws DataAccessException {
		
		// TODO Auto-generated method stub
		return JSON.toJSONString(aphiSelectDictMapper.queryEmpDutyDict(map));
		
	}

	@Override
	public String queryTargetNatureDict(Map<String, Object> map) throws DataAccessException {
		
		// TODO Auto-generated method stub
		return JSON.toJSONString(aphiSelectDictMapper.queryTargetNatureDict(map));
		
	}

	@Override
	public String queryAphiIncomeItem(Map<String, Object> map) throws DataAccessException {
		
		return JSON.toJSONString(aphiSelectDictMapper.queryAphiIncomeItem(map));
		
	}

	@Override
	public String queryAphiCostItem(Map<String, Object> map) throws DataAccessException {
		
		return JSON.toJSONString(aphiSelectDictMapper.queryAphiCostItem(map));
		
	}
	
	// 指标取值方法参数表TARGET_METHOD_PARA
	@Override
	public String queryTargetMethodPara(Map<String, Object> map) throws DataAccessException {
		
		return JSON.toJSONString(aphiSelectDictMapper.queryTargetMethodPara(map));
		
	}

	// 表名:APHI_FORMULA 解释:奖金计算公式表
	@Override
	public String queryFormula(Map<String, Object> map) throws DataAccessException {

		return JSON.toJSONString(aphiSelectDictMapper.queryFormula(map));

	}

	// 表名:APHI_FUN 解释:奖金函数表 
	@Override
	public String queryFun(Map<String, Object> map) throws DataAccessException {

		return JSON.toJSONString(aphiSelectDictMapper.queryFun(map));

	}

	// 表名:APHI_WORKITEM 解释:工作量指标表 
	@Override
	public String queryWorkItem(Map<String, Object> map) throws DataAccessException {

		return JSON.toJSONString(aphiSelectDictMapper.queryWorkItem(map));

	}
	
	// 表名:APHI_TARGET 解释:奖金指标字典表  
	@Override
	public String queryTarget(Map<String, Object> map) throws DataAccessException {

		return JSON.toJSONString(aphiSelectDictMapper.queryTarget(map));

	}

	// 表名:sys_comp
	@Override
	public String querySysComp(Map<String, Object> map) throws DataAccessException {

		return JSON.toJSONString(aphiSelectDictMapper.querySysComp(map));

	}

	// 表名:sys_copy
	@Override
	public String querySysCopy(Map<String, Object> map) throws DataAccessException {

		return JSON.toJSONString(aphiSelectDictMapper.querySysCopy(map));

	}

	@Override
	public String queryEmpDict(Map<String, Object> entityMap)
			throws DataAccessException {
		
		
		return JSON.toJSONString(aphiSelectDictMapper.queryEmpDict(entityMap));
	}

	@Override
	public String queryIncomeItemSeq(Map<String, Object> entityMap)
			throws DataAccessException {
		return JSON.toJSONString(aphiSelectDictMapper.queryIncomeItemSeq(entityMap));
	}
	
	//收入项目下拉框(数据准备日期查询)
	@Override
	public String queryIncomeItemSeqTime(Map<String, Object> entityMap)
			throws DataAccessException {
		return JSON.toJSONString(aphiSelectDictMapper.queryIncomeItemSeqTime(entityMap));
	}

	@Override
	public String queryCostItemSeq(Map<String, Object> entityMap)
			throws DataAccessException {
		return JSON.toJSONString(aphiSelectDictMapper.queryCostItemSeq(entityMap));
	}
	
	//支出项目下拉框(带日期)
	@Override
	public String queryCostItemSeqTime(Map<String, Object> entityMap)
			throws DataAccessException {
		return JSON.toJSONString(aphiSelectDictMapper.queryCostItemSeqTime(entityMap));
	}

	@Override
	public String queryWorkItemSeq(Map<String, Object> entityMap)
			throws DataAccessException {
		return JSON.toJSONString(aphiSelectDictMapper.queryWorkItemSeq(entityMap));
	}
	
	//解释:工作量指标表 (传入时间)
	@Override
	public String queryHpmWorkitemSeqTime(Map<String, Object> map) throws DataAccessException {

		return JSON.toJSONString(aphiSelectDictMapper.queryHpmWorkitemSeqTime(map));

	}

	@Override
	public String querySchemeSeq(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return JSON.toJSONString(aphiSelectDictMapper.querySchemeSeq(entityMap));
	}
	
	@Override
	public String queryHpmFunType(Map<String, Object> entityMap)throws DataAccessException {
		// TODO Auto-generated method stub
		return JSON.toJSONString(aphiSelectDictMapper.queryHpmFunType(entityMap));
	}
	
	@Override
	public String queryHpmComType(Map<String, Object> entityMap)throws DataAccessException {
		// TODO Auto-generated method stub
		return JSON.toJSONString(aphiSelectDictMapper.queryHpmComType(entityMap));
	}
	
	@Override
	public String queryHpmFunParaMethod(Map<String, Object> entityMap)throws DataAccessException {
		// TODO Auto-generated method stub
		return JSON.toJSONString(aphiSelectDictMapper.queryHpmFunParaMethod(entityMap));
	}

	@Override
    public String querySubSchemeSeqDict(Map<String, Object> entityMap) throws DataAccessException {
	    // TODO Auto-generated method stub
		return JSON.toJSONString(aphiSelectDictMapper.querySubSchemeSeqDict(entityMap));
    }

	@Override
	public String queryTargetMethod(Map<String, Object> map)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return JSON.toJSONString(aphiSelectDictMapper.queryTargetMethod(map));
	}

	@Override
    public String queryCostTypeDict(Map<String, Object> map) throws DataAccessException {
	    // TODO Auto-generated method stub
		return JSON.toJSONString(aphiSelectDictMapper.queryCostTypeDict(map));
    }

	@Override
    public String queryWorkItemSeqMore(Map<String, Object> entityMap) throws DataAccessException {
		 // TODO Auto-generated method stub
		return JSON.toJSONString(aphiSelectDictMapper.queryWorkItemSeqMore(entityMap));
    }

	@Override
	public String queryHpmFunParaType(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return JSON.toJSONString(aphiSelectDictMapper.queryHpmFunParaType(entityMap));
	}

	@Override
	public String queryHpmDeptNature(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return JSON.toJSONString(aphiSelectDictMapper.queryHpmDeptNature(entityMap));
	}
	
	@Override
	public String queryHpmOraclePkg(Map<String, Object> entityMap) throws DataAccessException {
		
		return JSON.toJSONString(aphiSelectDictMapper.queryHpmOraclePkg(entityMap));
	}
	
	@Override
	public String querySysGroupDict(Map<String, Object> entityMap) throws DataAccessException {
		
		return JSON.toJSONString(aphiSelectDictMapper.querySysGroupDict(entityMap));
	}


	@Override
	public String queryEmpDictByCode(Map<String, Object> entityMap)throws DataAccessException {
		// TODO Auto-generated method stub
		return JSON.toJSONString(aphiSelectDictMapper.queryEmpDictByCode(entityMap));
	}


	@Override
	public String queryAphiDeptHip(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		
		return JSON.toJSONString(aphiSelectDictMapper.queryAphiDeptHip(entityMap));
	}

	@Override
	public String queryAphiDeptDict(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return JSON.toJSONString(aphiSelectDictMapper.queryAphiDeptDict(entityMap));
	}

	@Override
	public String queryDeptDictByPerm(Map<String, Object> map) throws DataAccessException {
		// TODO Auto-generated method stub
		return JSON.toJSONString(aphiSelectDictMapper.queryDeptDictByPerm(map));
	}
	
	
	@Override
	public String queryAphiEmpItem(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return JSON.toJSONString(aphiSelectDictMapper.queryAphiEmpItem(entityMap));
	}

	@Override
	public String queryTargetCode(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return JSON.toJSONString(aphiSelectDictMapper.queryTargetCode(entityMap));
	}

	@Override
	public String queryAphiTemplateKind(Map<String, Object> entityMap)throws DataAccessException {
		// TODO Auto-generated method stub
		return JSON.toJSONString(aphiSelectDictMapper.queryAphiTemplateKind(entityMap));
	}

}
