package com.chd.hrp.htcg.serviceImpl.base;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.chd.hrp.htcg.dao.base.HtcgSelectDictMapper;
import com.chd.hrp.htcg.service.base.HtcgSelectDictService;

@Service("htcgSelectDictService")
public class HtcgSelectDictServiceImpl implements HtcgSelectDictService {
	private static Logger logge = Logger.getLogger(HtcgSelectDictServiceImpl.class);
	
	@Resource(name = "htcgSelectDictMapper")
	private final HtcgSelectDictMapper htcgSelectDictMapper = null;
	

	RowBounds rowBoundsALL = new RowBounds(0, 20);

	@Override
	public String queryHtcgYearOrNo(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(htcgSelectDictMapper.queryHtcgYearOrNo(entityMap, rowBounds));
	}
	
	/**
	 * 医嘱分类
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Override     
	public String queryHtcgRecipeTypeDict(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(htcgSelectDictMapper.queryHtcgRecipeTypeDict(entityMap, rowBounds));

	}
	/**
	 * 医保类型
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Override
	public String queryHtcgIdentityTypeDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(htcgSelectDictMapper.queryHtcgIdentityTypeDict(entityMap, rowBounds));
	}
	/**
	 * 诊断性质
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Override
	public String queryHtcgIcd10NatureDict(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(htcgSelectDictMapper.queryHtcgIcd10NatureDict(entityMap, rowBounds));

	}
	/**
	 * 诊断类型
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Override
	public String queryHtcgIcd10TypeDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(htcgSelectDictMapper.queryHtcgIcd10TypeDict(entityMap, rowBounds));

	}
	/**
	 * 麻醉种类
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Override
	public String queryHtcgAnestTypeDict(Map<String, Object> map) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (map.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(map.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(htcgSelectDictMapper.queryHtcgAnestTypeDict(map, rowBounds));
	}

	/**
	 * 转归字典
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Override
	public String queryHtcgOutcomeDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(htcgSelectDictMapper.queryHtcgOutcomeDict(entityMap, rowBounds));
		
	}
	/**
	 * 药品类别
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Override
	public String queryHtcgDrugTypeDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		 return JSON.toJSONString(htcgSelectDictMapper.queryHtcgDrugTypeDict(entityMap, rowBounds));
	
	}
	/**
	 * 药品
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Override
	public String queryHtcgDrugDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		 return JSON.toJSONString(htcgSelectDictMapper.queryHtcgDrugDict(entityMap, rowBounds));
	}
	/**
	 * 诊疗项目
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Override
	public String queryCostChargeItemArrtDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(htcgSelectDictMapper.queryCostChargeItemArrtDict(entityMap, rowBounds));
	}
	/**
	 * 材料
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Override
	public String queryhtcMaterialDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(htcgSelectDictMapper.queryhtcMaterialDict(entityMap, rowBounds));
	}
	/**
	 * 生产厂商
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Override
	public String queryHosFacDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(htcgSelectDictMapper.queryHosFacDict(entityMap, rowBounds));
		
	}
	
	/**
	 * 科室
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Override
	public String queryHtcgDeptDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(htcgSelectDictMapper.queryHtcgDeptDict(entityMap, rowBounds));

	}
	/**
	 * 核算方案
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Override
	public String queryHtcgSchemeDict(Map<String, Object> entityMap) throws DataAccessException {

		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(htcgSelectDictMapper.queryHtcgSchemeDict(entityMap, rowBounds));
	}

	/**
	 * 病种分类
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Override
	public String queryHtcgDrgsTypeDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(htcgSelectDictMapper.queryHtcgDrgsTypeDict(entityMap, rowBounds));
		
	}
	/**
	 * 病种字典
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Override
	public String queryHtcgDrgsDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(htcgSelectDictMapper.queryHtcgDrgsDict(entityMap, rowBounds));
	}
	/**
	 * 诊断字典 
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Override
	public String queryHtcgIcd10Dict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(htcgSelectDictMapper.queryHtcgIcd10Dict(entityMap, rowBounds));
	}
	/**
	 * 手术字典
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Override
	public String queryHtcgIcd9Dict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(htcgSelectDictMapper.queryHtcgIcd9Dict(entityMap, rowBounds));
	}
	
	/**
	 * ICD入组规则 (诊断,手术)
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Override
	public String queryHtcgIcdRuleDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(htcgSelectDictMapper.queryHtcgIcdRuleDict(entityMap, rowBounds));
	}
	
	/**
	 * 入组样本抽取规则
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Override
	public String queryHtcgMrRuleDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(htcgSelectDictMapper.queryHtcgMrRuleDict(entityMap, rowBounds));
	}
	

	/**
	 * 临床路径时程划分(期间)
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Override
	public String queryHtcgCipStepDateDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(htcgSelectDictMapper.queryHtcgCipStepDateDict(entityMap, rowBounds));
	}
	
	/**
	 * 临床路径时程划分(地点)
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Override
	public String queryHtcgCipStepPlaceDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(htcgSelectDictMapper.queryHtcgCipStepPlaceDict(entityMap, rowBounds));
	}
	
	/**
	 * 相似治疗效果合并规则
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Override 
	public String queryHtcgRecipeMergeRuleDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(htcgSelectDictMapper.queryHtcgRecipeMergeRuleDict(entityMap, rowBounds));
	}
	/**
	 * 相似治疗效果项目性质
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Override
	public String queryHtcgChargeNatureDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(htcgSelectDictMapper.queryHtcgChargeNatureDict(entityMap, rowBounds));
	}
	/**
	 * 医嘱项目准入规则
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Override 
	public String queryHtcgRecipeGroupRuleDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(htcgSelectDictMapper.queryHtcgRecipeGroupRuleDict(entityMap, rowBounds));
	}
	/**
	 * 核算方案应用序号
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Override
	public String queryHtcgSeqTableDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(htcgSelectDictMapper.queryHtcgSeqTableDict(entityMap, rowBounds));
	}
	/**
	 * 期间类型
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Override
	public String queryHtcgPeriodTypeDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(htcgSelectDictMapper.queryHtcgPeriodTypeDict(entityMap, rowBounds));
	}
	/**
	 * 期间
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Override
	public String queryHtcgPeriodDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(htcgSelectDictMapper.queryHtcgPeriodDict(entityMap, rowBounds));
	}
	/**
	 * 药品管理成本科室
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Override
	public String queryDeptDrugAdminCostDict(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(htcgSelectDictMapper.queryDeptDrugAdminCostDict(entityMap, rowBounds));
	}

}
