package com.chd.hrp.htc.serviceImpl.info.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.chd.base.util.ToUppderUtil;
import com.chd.hrp.htc.dao.info.base.HtcSelectDictMapper;
import com.chd.hrp.htc.service.info.base.HtcSelectDictService;

@Service("htcSelectDictService")
public class HtcSelectDictServiceImpl implements HtcSelectDictService {
	@Resource(name = "htcSelectDictMapper")
	private HtcSelectDictMapper htcSelectDictMapper = null;

	RowBounds rowBoundsALL = new RowBounds(0, 20);

	
	@Override
	public String queryHtcItemSuppDict(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(htcSelectDictMapper.queryHtcItemSuppDict(entityMap, rowBounds));
	}
	
	@Override
	public String queryHtcCostItemDict(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		 RowBounds rowBounds = new RowBounds(0, 20);
			
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(htcSelectDictMapper.queryHtcCostItemDict(entityMap, rowBounds));
	}

	@Override
	public String queryHtcSourceDict(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		   RowBounds rowBounds = new RowBounds(0, 20);
			
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(htcSelectDictMapper.queryHtcSourceDict(entityMap, rowBounds));
	}
	
	@Override
	public String queryHtcDeptNature(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(htcSelectDictMapper.queryHtcDeptNature(entityMap, rowBounds));
	}
	
	@Override
	public String queryHtcDataSource(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(htcSelectDictMapper.queryHtcDataSource(entityMap, rowBounds));
	}
	
	
	@Override
	public String queryHtcItemGrade(Map<String, Object> entityMap) throws DataAccessException{
		
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(htcSelectDictMapper.queryHtcItemGrade(entityMap, rowBounds));
	}
	
	@Override
	public String queryHtcYearOrNo(Map<String,Object> entityMap) throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(htcSelectDictMapper.queryHtcYearOrNo(entityMap, rowBounds));
	}
	
	@Override
	public String queryHtcDeptTypeDictNo(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(htcSelectDictMapper.queryHtcDeptTypeDictNo(entityMap, rowBounds));
	}
	
	@Override
	public String queryHtcParaType(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(htcSelectDictMapper.queryHtcParaType(entityMap, rowBounds));
	}
	
	@Override
	public String queryHtcIncomeType(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(htcSelectDictMapper.queryHtcIncomeType(entityMap, rowBounds));
	}
	
	@Override
	public String queryHtcIncomeItemDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(htcSelectDictMapper.queryHtcIncomeItemDict(entityMap, rowBounds));
	}
	
	@Override
	public String queryHtcChargeKindArrt(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
         RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(htcSelectDictMapper.queryHtcChargeKindArrt(entityMap, rowBounds));
	}

	@Override
	public String queryHtcChargeItemArrt(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub

        RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(htcSelectDictMapper.queryHtcChargeItemArrt(entityMap, rowBounds));
	}
	
	@Override
	public String queryHtcDeptNatur(Map<String, Object> entityMap) throws DataAccessException {
		
		    RowBounds rowBounds = new RowBounds(0, 20);
			
			if (entityMap.get("pageSize") != null) {
				rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
			}else{
				 rowBounds = rowBoundsALL;
			}
			return JSON.toJSONString(htcSelectDictMapper.queryHtcDeptNatur(entityMap, rowBounds));
	}
	
	@Override
	public String queryHtcDeptType(Map<String, Object> entityMap) throws DataAccessException {

		    RowBounds rowBounds = new RowBounds(0, 20);
			
			if (entityMap.get("pageSize") != null) {
				rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
			}else{
				 rowBounds = rowBoundsALL;
			}
			return JSON.toJSONString(htcSelectDictMapper.queryHtcDeptType(entityMap, rowBounds));
	}
	
	// 科室级次
	@Override
	public String queryHtcDeptLevel(Map<String, Object> entityMap)
			throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
			
		} else {
			
			rowBounds = rowBoundsALL;
			
		}
		List<Map<String, Object>> list = htcSelectDictMapper.queryHtcDeptLevel(entityMap, rowBounds);
		List<Map<String, Object>> reList = new ArrayList<Map<String, Object>>();
		for(int i = 0; i < list.size() ; i ++){
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String id = String.valueOf(list.get(i).get("id"));
			String text = ToUppderUtil.numToUpper(Integer.parseInt(String.valueOf(list.get(i).get("text"))))+"级";
			mapVo.put("id", id);
			mapVo.put("text", text);
			reList.add(mapVo);
		}

		return JSON.toJSONString(reList);
	}
	
	  //科室分类
	@Override
	public String queryHtcDeptKind(Map<String, Object> entityMap)throws DataAccessException {
    // TODO Auto-generated method stub
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		
		return JSON.toJSONString(htcSelectDictMapper.queryHtcDeptKind(entityMap, rowBounds)); 
    }  
	// 查询科室字典表
	@Override
	public String queryHtcDeptDict(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
        RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		
		return JSON.toJSONString(htcSelectDictMapper.queryHtcDeptDict(entityMap, rowBounds));
	}
	
	//核算科室
	@Override
    public String queryHtcProjDeptDict(Map<String, Object> map) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (map.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(map.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(htcSelectDictMapper.queryHtcProjDeptDict(map, rowBounds));
    }
	
	 //方案核算方法
	@Override
	public String queryHtcCheckMethod(Map<String, Object> map)
			throws DataAccessException {
		// TODO Auto-generated method stub
	     RowBounds rowBounds = new RowBounds(0, 20);
		
		if (map.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(map.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		
		return JSON.toJSONString(htcSelectDictMapper.queryHtcCheckMethod(map, rowBounds)); 
	
	}
	
	  //方案
	@Override
    public String queryHtcPlan(Map<String, Object> map) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (map.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(map.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(htcSelectDictMapper.queryHtcPlan(map, rowBounds));
    }
	
	
	@Override
	public String queryHtcPeopleTitleDict(Map<String,Object> entityMap) throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		
		return JSON.toJSONString(htcSelectDictMapper.queryHtcPeopleTitleDict(entityMap, rowBounds)); 
	}

	@Override
	public String queryHtcPeopleTypeDict(Map<String,Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		
		return JSON.toJSONString(htcSelectDictMapper.queryHtcPeopleTypeDict(entityMap, rowBounds));
	}
	
	//人员字典
	@Override
    public String queryHtcPeopleDict(Map<String, Object> map) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (map.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(map.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(htcSelectDictMapper.queryHtcPeopleDict(map, rowBounds));
    }
	
	
	@Override
	public String queryHtcWageItemDict(Map<String, Object> map) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (map.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(map.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(htcSelectDictMapper.queryHtcWageItemDict(map, rowBounds));

	}
	

	
	// 奖金项目字典
	@Override
	public String queryHtcBonusItemDict(Map<String,Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(htcSelectDictMapper.queryHtcBonusItemDict(entityMap, rowBounds));
	}

	@Override
	public String queryHtcFassetTypeDict(Map<String, Object> map) throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (map.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(map.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(htcSelectDictMapper.queryHtcFassetTypeDict(map, rowBounds));
	}
	
	//固定资产资产信息字典
	@Override
    public String queryHtcFassetDict(Map<String, Object> map) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (map.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(map.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(htcSelectDictMapper.queryHtcFassetDict(map, rowBounds));
    }
	// 无形资产分类信息字典
	@Override
	public String queryHtcIassetTypeDict(Map<String, Object> map) throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (map.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(map.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(htcSelectDictMapper.queryHtcIassetTypeDict(map, rowBounds));
	}
	
	// 无形资产资产信息字典
	@Override
	public String queryHtcIassetDict(Map<String, Object> map) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (map.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(map.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		
		return JSON.toJSONString(htcSelectDictMapper.queryHtcIassetDict(map, rowBounds));
	}
	
	// 材料分类字典下拉框
	@Override
    public String queryHtcMaterialTypeDict(Map<String, Object> map) throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (map.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(map.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(htcSelectDictMapper.queryHtcMaterialTypeDict(map, rowBounds));
    }
	
	// 材料信息字典下拉框
	@Override
    public String queryHtcMaterialDict(Map<String, Object> map) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (map.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(map.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(htcSelectDictMapper.queryHtcMaterialDict(map, rowBounds));
    }
	
	//单位
	@Override
    public String queryHtcHosUnitDict(Map<String, Object> map) throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (map.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(map.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(htcSelectDictMapper.queryHtcHosUnitDict(map, rowBounds));
    }
	
	//生产厂商
	@Override
    public String queryHtcHosFacDict(Map<String, Object> map) throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (map.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(map.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(htcSelectDictMapper.queryHtcHosFacDict(map, rowBounds));
    }
	
	// 资源动因
	@Override
	public String queryHtcResCauseDict(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
	     RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(htcSelectDictMapper.queryHtcResCauseDict(entityMap, rowBounds));
	}
	// 作业动因
	@Override
	public String queryHtcWorkCauseDict(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
	    RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(htcSelectDictMapper.queryHtcWorkCauseDict(entityMap, rowBounds));
	}
	
	
	// 作业分类
	@Override
    public String queryHtcWorkTypeDict(Map<String, Object> map) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (map.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(map.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(htcSelectDictMapper.queryHtcWorkTypeDict(map, rowBounds));
    }
	// 作业字典
	@Override
    public String queryHtcWorkDict(Map<String, Object> map) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (map.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(map.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(htcSelectDictMapper.queryHtcWorkDict(map, rowBounds));
    }

}
