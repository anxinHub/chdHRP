package com.chd.hrp.cost.serviceImpl;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.chd.base.util.ToUppderUtil;
import com.chd.hrp.acc.entity.HrpAccSelect;
import com.chd.hrp.cost.dao.HrpCostSelectMapper;
import com.chd.hrp.cost.entity.HrpCostSelect;
import com.chd.hrp.cost.service.HrpCostSelectService;

@Service("hrpCostSelectService")
public class HrpCostSelectServiceImpl implements HrpCostSelectService {
 
	private static Logger logger = Logger.getLogger(HrpCostSelectServiceImpl.class);

	@Resource(name = "hrpCostSelectMapper")
	private final HrpCostSelectMapper hrpCostSelectMapper = null;

	RowBounds rowBoundsALL = new RowBounds(0, 60);
 
	@Override
	public String queryIncomeType(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpCostSelectMapper.queryIncomeType(entityMap, rowBounds));
	}

	@Override
	public String queryIncomeItemArrt(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpCostSelectMapper.queryIncomeItemArrt(entityMap, rowBounds));
	}

	@Override
	public String queryChargeKindArrt(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpCostSelectMapper.queryChargeKindArrt(entityMap, rowBounds));
	}

	@Override
	public String queryMateTypeArrt(Map<String, Object> entityMap) throws DataAccessException {

		RowBounds rowBounds = new RowBounds(0, 20);

		if (entityMap.get("pageSize") != null) {

			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));

		} else {

			rowBounds = rowBoundsALL;

		}

		return JSON.toJSONString(hrpCostSelectMapper.queryMateTypeArrt(entityMap, rowBounds));
	}

	@Override
	public String queryEmpTitleArrt(Map<String, Object> entityMap) throws DataAccessException {

		RowBounds rowBounds = new RowBounds(0, 20);

		if (entityMap.get("pageSize") != null) {

			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));

		} else {

			rowBounds = rowBoundsALL;

		}

		return JSON.toJSONString(hrpCostSelectMapper.queryEmpTitleArrt(entityMap, rowBounds));

	}

	@Override
	public String queryFassetTypeArrt(Map<String, Object> entityMap) throws DataAccessException {

		RowBounds rowBounds = new RowBounds(0, 20);

		if (entityMap.get("pageSize") != null) {

			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));

		} else {

			rowBounds = rowBoundsALL;

		}

		return JSON.toJSONString(hrpCostSelectMapper.queryFassetTypeArrt(entityMap, rowBounds));
	}

	@Override
	public String queryEmpTypeArrt(Map<String, Object> entityMap) throws DataAccessException {

		RowBounds rowBounds = new RowBounds(0, 20);

		if (entityMap.get("pageSize") != null) {

			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));

		} else {

			rowBounds = rowBoundsALL;

		}

		return JSON.toJSONString(hrpCostSelectMapper.queryEmpTypeArrt(entityMap, rowBounds));
	}

	@Override
	public String queryEmpArrt(Map<String, Object> entityMap) throws DataAccessException {

		RowBounds rowBounds = new RowBounds(0, 20);

		if (entityMap.get("pageSize") != null) {

			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));

		} else {

			rowBounds = rowBoundsALL;

		}

		return JSON.toJSONString(hrpCostSelectMapper.queryEmpArrt(entityMap, rowBounds));
	}

	@Override
	public String queryWageItemArrt(Map<String, Object> entityMap) throws DataAccessException {

		RowBounds rowBounds = new RowBounds(0, 20);

		if (entityMap.get("pageSize") != null) {

			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));

		} else {

			rowBounds = rowBoundsALL;

		}

		return JSON.toJSONString(hrpCostSelectMapper.queryWageItemArrt(entityMap, rowBounds));
	}

	@Override
	public String queryDeptNature(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpCostSelectMapper.queryDeptNature(entityMap, rowBounds));
	}
	
	//成本项目来源
	@Override
	public String queryDataSource(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpCostSelectMapper.queryDataSource(entityMap, rowBounds));
	}

	@Override
	public String queryItemDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpCostSelectMapper.queryItemDict(entityMap, rowBounds));
	}

	@Override
	public String queryDeptTypeDictNo(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpCostSelectMapper.queryDeptTypeDictNo(entityMap, rowBounds));
	}

	@Override
	public String queryIassetTypeArrt(Map<String, Object> entityMap) throws DataAccessException {

		RowBounds rowBounds = new RowBounds(0, 20);

		if (entityMap.get("pageSize") != null) {

			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));

		} else {

			rowBounds = rowBoundsALL;

		}

		return JSON.toJSONString(hrpCostSelectMapper.queryIassetTypeArrt(entityMap, rowBounds));
	}

	@Override
	public String queryBonusItemArrt(Map<String, Object> entityMap) throws DataAccessException {

		RowBounds rowBounds = new RowBounds(0, 20);

		if (entityMap.get("pageSize") != null) {

			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));

		} else {

			rowBounds = rowBoundsALL;

		}

		return JSON.toJSONString(hrpCostSelectMapper.queryBonusItemArrt(entityMap, rowBounds));
	}

	@Override
	public String queryItemDictNoItemGrade(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		RowBounds rowBounds = new RowBounds(0, 20);
          if (entityMap.get("pageSize") != null) {
			if("ALL".equals((String)entityMap.get("pageSize"))){
				return JSON.toJSONString(hrpCostSelectMapper.queryItemDictNoItemGrade(entityMap));
			}else{
				rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
			}
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpCostSelectMapper.queryItemDictNoItemGrade(entityMap, rowBounds));
	}

	
	@Override
	public String queryItemDictNo(Map<String, Object> entityMap) throws DataAccessException {

		RowBounds rowBounds = new RowBounds(0, 20);

		if (entityMap.get("pageSize") != null) {

			if("ALL".equals((String)entityMap.get("pageSize"))){
				
				return JSON.toJSONString(hrpCostSelectMapper.queryItemDictNo(entityMap));
				
			}else{
				
				rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
				
			}
			

		} else {

			rowBounds = rowBoundsALL;

		}

		return JSON.toJSONString(hrpCostSelectMapper.queryItemDictNo(entityMap, rowBounds));
	}

	@Override
	public String queryDeptDictNo(Map<String, Object> entityMap) throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			if("ALL".equals((String)entityMap.get("pageSize"))){
				
				return JSON.toJSONString(hrpCostSelectMapper.queryDeptDictNo(entityMap));
				
			}else{
				
				rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
				
			}
			
		} else {
			
			rowBounds = rowBoundsALL;
			
		}
		return JSON.toJSONString(hrpCostSelectMapper.queryDeptDictNo(entityMap, rowBounds));
	}

	@Override
	public String queryDeptDictCode(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
              RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			if("ALL".equals((String)entityMap.get("pageSize"))){
				
				return JSON.toJSONString(hrpCostSelectMapper.queryDeptDictNo(entityMap));
				
			}else{
				
				rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
				
			}
			
		} else {
			
			rowBounds = rowBoundsALL;
			
		}
		return JSON.toJSONString(hrpCostSelectMapper.queryDeptDictCode(entityMap, rowBounds));
	}
	@Override
	public String queryCostPatientTypeDict(Map<String, Object> entityMap) throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
			
		} else {
			
			rowBounds = rowBoundsALL;
			
		}
		return JSON.toJSONString(hrpCostSelectMapper.queryCostPatientTypeDict(entityMap, rowBounds));
	}

	@Override
	public String queryChargeItemArrt(Map<String, Object> entityMap)
			throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
			
		} else {
			
			rowBounds = rowBoundsALL;
			
		}
		return JSON.toJSONString(hrpCostSelectMapper.queryChargeItemArrt(entityMap, rowBounds));
	}

	@Override
    public String queryDeptParaDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
			
		} else {
			
			rowBounds = rowBoundsALL;
			
		}
		return JSON.toJSONString(hrpCostSelectMapper.queryDeptParaDict(entityMap, rowBounds));	
    }

	@Override
    public String queryMateArrt(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
			
		} else {
			
			rowBounds = rowBoundsALL;
			
		}
		return JSON.toJSONString(hrpCostSelectMapper.queryMateArrt(entityMap, rowBounds));	
    }

	@Override
    public String queryIassetArrt(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
			
		} else {
			
			rowBounds = rowBoundsALL;
			
		}
		return JSON.toJSONString(hrpCostSelectMapper.queryIassetArrt(entityMap, rowBounds));	
    }
	

	@Override
	public String queryIassetArrtType(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
            RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
			
		} else {
			
			rowBounds = rowBoundsALL;
			
		}
		return JSON.toJSONString(hrpCostSelectMapper.queryIassetArrtType(entityMap, rowBounds));	
	}

	@Override
    public String queryFassetArrt(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
			
		} else {
			
			rowBounds = rowBoundsALL;
			
		}
		return JSON.toJSONString(hrpCostSelectMapper.queryFassetArrt(entityMap, rowBounds));	
    }

	@Override
	public String queryFassetArrtType(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
	        RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
			
		} else {
			
			rowBounds = rowBoundsALL;
			
		}
		return JSON.toJSONString(hrpCostSelectMapper.queryFassetArrtType(entityMap, rowBounds));	
	}
	@Override
	public String querySourceArrt(Map<String, Object> entityMap) throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
			
		} else {
			
			rowBounds = rowBoundsALL;
			
		}
		return JSON.toJSONString(hrpCostSelectMapper.querySourceArrt(entityMap, rowBounds));	
	}

	@Override
	public String queryWangSchemeSet(Map<String, Object> entityMap)
			throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
			
		} else {
			
			rowBounds = rowBoundsALL;
			
		}
		return JSON.toJSONString(hrpCostSelectMapper.queryWangSchemeSet(entityMap, rowBounds));
	}

	@Override
	public String queryCostEmpDict(Map<String, Object> entityMap) throws DataAccessException {

		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
			
		} else {
			
			rowBounds = rowBoundsALL;
			
		}
		return JSON.toJSONString(hrpCostSelectMapper.queryCostEmpDict(entityMap, rowBounds));
	}
	@Override
	public String queryServerItemDict(Map<String, Object> entityMap) throws DataAccessException {

		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
			
		} else {
			
			rowBounds = rowBoundsALL;
			
		}
		return JSON.toJSONString(hrpCostSelectMapper.queryServerItemDict(entityMap, rowBounds));
	}
	@Override
	public String queryBonusSchemeSet(Map<String, Object> entityMap)
			throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
			
		} else {
			
			rowBounds = rowBoundsALL;
			
		}
		return JSON.toJSONString(hrpCostSelectMapper.queryBonusSchemeSet(entityMap, rowBounds));
	}

	@Override
	public String queryDeptLevel(Map<String, Object> entityMap)
			throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
			
		} else {
			
			rowBounds = rowBoundsALL;
			
		}
		List<HrpCostSelect> list = hrpCostSelectMapper.queryDeptLevel(entityMap, rowBounds);
		List<HrpCostSelect> reList = new ArrayList<HrpCostSelect>();
		for(int i = 0; i < list.size() ; i ++){
			HrpCostSelect obj = new HrpCostSelect();
			String id = list.get(i).getId();
			String text = ToUppderUtil.numToUpper(Integer.parseInt(list.get(i).getText()))+"级";
			obj.setId(id);
			obj.setText(text);
			reList.add(obj);
		}
		
		/*HrpCostSelect endLevel = new HrpCostSelect();
		endLevel.setId("0");
		endLevel.setText("末级");
		reList.add(endLevel);*/
		return JSON.toJSONString(reList);
	}

	@Override
	public String queryCostDeptKindDict(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
			
		} else {
			
			rowBounds = rowBoundsALL;
			
		}
		return JSON.toJSONString(hrpCostSelectMapper.queryCostDeptKindDict(entityMap, rowBounds));
	}

	@Override
	public String queryCostDeptNature(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
			
		} else {
			
			rowBounds = rowBoundsALL;
			
		}
		return JSON.toJSONString(hrpCostSelectMapper.queryCostDeptNature(entityMap, rowBounds));
	}

	
	
	@Override
	public String queryCostIncomeItemArrt(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
			
		} else {
			
			rowBounds = rowBoundsALL;
			
		}
		return JSON.toJSONString(hrpCostSelectMapper.queryCostIncomeItemArrt(entityMap, rowBounds));
	}

	
	@Override
	public String queryMateTypeSupperCode(Map<String, Object> entityMap) throws DataAccessException{
		
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpCostSelectMapper.queryMateTypeSupperCode(entityMap, rowBounds));
	}
	
	
	@Override
	public String queryFassetTypeSupperCode(Map<String, Object> entityMap) throws DataAccessException{
		
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpCostSelectMapper.queryFassetTypeSupperCode(entityMap, rowBounds));
	}
	

	@Override
	public String queryIassetTypeSupperCode(Map<String, Object> entityMap) throws DataAccessException{
		
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpCostSelectMapper.queryIassetTypeSupperCode(entityMap, rowBounds));
	}
	@Override
	public String queryParaType(Map<String, Object> entityMap) throws DataAccessException{
		
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpCostSelectMapper.queryParaType(entityMap, rowBounds));
	}
	@Override
	public String queryParaNatur(Map<String, Object> entityMap) throws DataAccessException{
		
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpCostSelectMapper.queryParaNatur(entityMap, rowBounds));
	}

	@Override
    public String queryDeptDictNoLast(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpCostSelectMapper.queryDeptDictNoLast(entityMap, rowBounds));
    }

	@Override
    public String queryItemDictLast(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpCostSelectMapper.queryItemDictLast(entityMap, rowBounds));
    }

	@Override
    public String queryItemDictNoLast(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpCostSelectMapper.queryItemDictNoLast(entityMap, rowBounds));
    }

	@Override
	public String queryHosDeptLevel(Map<String, Object> entityMap)
			throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpCostSelectMapper.queryHosDeptLevel(entityMap, rowBounds));
	}

	@Override
	public String queryCostSubjItemMapByItem(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
        RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpCostSelectMapper.queryCostSubjItemMapByItem(entityMap, rowBounds));	
	}

	@Override
	public String queryCostSubjItemMapBySubj(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		 RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpCostSelectMapper.queryCostSubjItemMapBySubj(entityMap, rowBounds));
	}

	@Override
	public String queryCostUserDefinedParaTarget(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		RowBounds rowBounds = new RowBounds(0, 20);

		if (entityMap.get("pageSize") != null) {

			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));

		} else {

			rowBounds = rowBoundsALL;

		}

		return JSON.toJSONString(hrpCostSelectMapper.queryCostUserDefinedParaTarget(entityMap, rowBounds));
	}

	@Override
	public String queryItemDictCodeLast(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);

		if (entityMap.get("pageSize") != null) {

			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));

		} else {

			rowBounds = rowBoundsALL;

		}
		return JSON.toJSONString(hrpCostSelectMapper.queryItemDictCodeLast(entityMap, rowBounds));

	}

	@Override
	public String queryDeptDictCodeLast(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);

		if (entityMap.get("pageSize") != null) {

			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));

		} else {

			rowBounds = rowBoundsALL;

		}
		return JSON.toJSONString(hrpCostSelectMapper.queryDeptDictCodeLast(entityMap, rowBounds));
	}

}
