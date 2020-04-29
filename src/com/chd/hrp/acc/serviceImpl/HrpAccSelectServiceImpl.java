package com.chd.hrp.acc.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.chd.hrp.acc.dao.HrpAccSelectMapper;
import com.chd.hrp.acc.entity.HrpAccSelect;
import com.chd.hrp.acc.service.HrpAccSelectService;
 
@Service("hrpAccSelectService")
public class HrpAccSelectServiceImpl implements HrpAccSelectService {

	private static Logger logger = Logger.getLogger(HrpAccSelectServiceImpl.class);

	@Resource(name = "hrpAccSelectMapper")
	private final HrpAccSelectMapper hrpAccSelectMapper = null;

	RowBounds rowBoundsALL = new RowBounds(0, 20);

	@Override
	public String querySubjType(Map<String, Object> entityMap) throws DataAccessException {
//		RowBounds rowBounds = new RowBounds(0, 20);
//		if (entityMap.get("pageSize") != null) {
//			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
//		} else {
//			rowBounds = rowBoundsALL;
//		}
		return JSON.toJSONString(hrpAccSelectMapper.querySubjType(entityMap));
	}

	@Override
	public String querySubjTypeKind(Map<String, Object> entityMap) throws DataAccessException {
//		RowBounds rowBounds = new RowBounds(0, 20);
//		if (entityMap.get("pageSize") != null) {
//			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
//		} else {
//			rowBounds = rowBoundsALL;
//		}
		return JSON.toJSONString(hrpAccSelectMapper.querySubjTypeKind(entityMap));
	}

	@Override
	public String querySubjNature(Map<String, Object> entityMap) throws DataAccessException {
//		RowBounds rowBounds = new RowBounds(0, 20);
//		if (entityMap.get("pageSize") != null) {
//			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
//		} else {
//			rowBounds = rowBoundsALL;
//		}
		return JSON.toJSONString(hrpAccSelectMapper.querySubjNature(entityMap));
	}

	@Override
	public String queryVouchType(Map<String, Object> entityMap) throws DataAccessException {
//		RowBounds rowBounds = new RowBounds(0, 20);
//		if (entityMap.get("pageSize") != null) {
//			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
//		} else {
//			rowBounds = rowBoundsALL;
//		}
		return JSON.toJSONString(hrpAccSelectMapper.queryVouchType(entityMap));
	}

	@Override
	public String queryCur(Map<String, Object> entityMap) throws DataAccessException {
//		RowBounds rowBounds = new RowBounds(0, 20);
//		if (entityMap.get("pageSize") != null) {
//			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
//		} else {
//			rowBounds = rowBoundsALL;
//		}
		return JSON.toJSONString(hrpAccSelectMapper.queryCur(entityMap));
	}

	@Override
	public String queryCheckType(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAccSelectMapper.queryCheckType(entityMap, rowBounds));
	}

	@Override
	public String queryFunType(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAccSelectMapper.queryFunType(entityMap, rowBounds));
	}

	@Override
	public String queryEcoType(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAccSelectMapper.queryEcoType(entityMap, rowBounds));
	}

	@Override
	public String queryCashType(Map<String, Object> entityMap) throws DataAccessException {
//		RowBounds rowBounds = new RowBounds(0, 20);
//		if (entityMap.get("pageSize") != null) {
//			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
//		} else {
//			rowBounds = rowBoundsALL;
//		}
		return JSON.toJSONString(hrpAccSelectMapper.queryCashType(entityMap));
	}

	@Override
	public String queryDeptType(Map<String, Object> entityMap) throws DataAccessException {
//		RowBounds rowBounds = new RowBounds(0, 20);
//		if (entityMap.get("pageSize") != null) {
//			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
//		} else {
//			rowBounds = rowBoundsALL;
//		}
		return JSON.toJSONString(hrpAccSelectMapper.queryDeptType(entityMap));
	}

	@Override
	public String queryDeptNatur(Map<String, Object> entityMap) throws DataAccessException {
//		RowBounds rowBounds = new RowBounds(0, 20);
//		if (entityMap.get("pageSize") != null) {
//			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
//		} else {
//			rowBounds = rowBoundsALL;
//		}
		return JSON.toJSONString(hrpAccSelectMapper.queryDeptNatur(entityMap));
	}

	@Override
	public String queryDeptOut(Map<String, Object> entityMap) throws DataAccessException {
//		RowBounds rowBounds = new RowBounds(0, 20);
//		if (entityMap.get("pageSize") != null) {
//			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
//		} else {
//			rowBounds = rowBoundsALL;
//		}
		return JSON.toJSONString(hrpAccSelectMapper.queryDeptOut(entityMap));
	}

	@Override
	public String queryEmpPay(Map<String, Object> entityMap) throws DataAccessException {
//		RowBounds rowBounds = new RowBounds(0, 20);
//		if (entityMap.get("pageSize") != null) {
//			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
//		} else {
//			rowBounds = rowBoundsALL;
//		}
		return JSON.toJSONString(hrpAccSelectMapper.queryEmpPay(entityMap));
	}

	@Override
	public String queryAccVouchState(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAccSelectMapper.queryAccVouchState(entityMap, rowBounds));
	}

	@Override
	public String querySubjLevel(Map<String, Object> entityMap) throws DataAccessException {
//		RowBounds rowBounds = new RowBounds(0, 20);
//		if (entityMap.get("pageSize") != null) {
//			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
//		} else {
//			rowBounds = rowBoundsALL;
//		}
		return JSON.toJSONString(hrpAccSelectMapper.querySubjLevel(entityMap));
	}

	@Override
	public String queryEcoLevel(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAccSelectMapper.queryEcoLevel(entityMap, rowBounds));
	}

	@Override
	public String queryFunLevel(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAccSelectMapper.queryFunLevel(entityMap, rowBounds));
	}

	@Override
	public String querySubj(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAccSelectMapper.querySubj(entityMap, rowBounds));
	}

	//  科目查询  不区分级次
	@Override
	public String querySubjBylevel(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 200);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} 
		return JSON.toJSONString(hrpAccSelectMapper.querySubjBylevel(entityMap, rowBounds));
	}
	
	@Override
	public String querySubjAll(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAccSelectMapper.querySubjAll(entityMap, rowBounds));
	}

	@Override
	public String queryCheckItem(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAccSelectMapper.queryCheckItem(entityMap, rowBounds));
	}

	@Override
	public String queryPayType(Map<String, Object> entityMap) throws DataAccessException {
		/*RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}*/
		return JSON.toJSONString(hrpAccSelectMapper.queryPayType(entityMap));
	}

	@Override
	public String queryAccFinaContent(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAccSelectMapper.queryAccFinaContent(entityMap, rowBounds));
	}

	@Override
	public String queryAccWageScheme(Map<String, Object> entityMap) throws DataAccessException {

//		RowBounds rowBounds = new RowBounds(0, 20);
//
//		if (entityMap.get("pageSize") != null) {
//
//			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
//
//		} else {
//
//			rowBounds = rowBoundsALL;
//
//		}

		return JSON.toJSONString(hrpAccSelectMapper.queryAccWageScheme(entityMap));
	}

	@Override
	public String queryAccWageItem(Map<String, Object> entityMap) throws DataAccessException {

		RowBounds rowBounds = new RowBounds(0, 20);

		if (entityMap.get("pageSize") != null) {

			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));

		} else {

			rowBounds = rowBoundsALL;

		}

		return JSON.toJSONString(hrpAccSelectMapper.queryAccWageItem(entityMap, rowBounds));
	}

	@Override
	public String queryAccWage(Map<String, Object> entityMap) throws DataAccessException {

//		RowBounds rowBounds = new RowBounds(0, 20);
//
//		if (entityMap.get("pageSize") != null) {
//
//			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
//
//		} else {
//
//			rowBounds = rowBoundsALL;
//
//		}

		return JSON.toJSONString(hrpAccSelectMapper.queryAccWage(entityMap));
	}

	@Override
	public String queryDeptDictNo(Map<String, Object> entityMap) throws DataAccessException {

		RowBounds rowBounds = new RowBounds(0, 20);

		if (entityMap.get("pageSize") != null) {

			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));

		} else {

			rowBounds = rowBoundsALL;

		}

		return JSON.toJSONString(hrpAccSelectMapper.queryDeptDictNo(entityMap, rowBounds));
	}

	@Override
	public String queryCheckItemById(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAccSelectMapper.queryCheckItemById(entityMap, rowBounds));
	}

	/**
	 * 根据部门加载职工
	 * 
	 * @param mapVo
	 * @return
	 */
	@Override
	public String queryEmpDictById(Map<String, Object> mapVo) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		// System.out.println("*********** :"+mapVo.get("pageSize"));
		if (mapVo.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) mapVo.get("pageSize"));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAccSelectMapper.queryEmpDictById(mapVo, rowBounds));
	}

	/**
	 * 账户类别
	 * 
	 * @param mapVo
	 * @return
	 */
	@Override
	public String queryWageType(Map<String, Object> mapVo) throws DataAccessException {
//		RowBounds rowBounds = new RowBounds(0, 20);
//
//		if (mapVo.get("pageSize") != null) {
//
//			rowBounds = new RowBounds(0, (Integer) mapVo.get("pageSize"));
//
//		} else {
//
//			rowBounds = rowBoundsALL;
//
//		}

		return JSON.toJSONString(hrpAccSelectMapper.queryWageType(mapVo));
	}

	/**
	 * 职工信息
	 * 
	 * @param mapVo
	 * @return
	 */
	@Override
	public String queryEmpDict(Map<String, Object> mapVo) throws DataAccessException {

		RowBounds rowBounds = new RowBounds(0, 20);

		if (mapVo.get("pageSize") != null) {

			rowBounds = new RowBounds(0, (Integer) mapVo.get("pageSize"));

		} else {

			rowBounds = rowBoundsALL;

		}

		return JSON.toJSONString(hrpAccSelectMapper.queryEmpDict(mapVo, rowBounds));
	}

	@Override
	public String queryBankSelect(Map<String, Object> entityMap) throws DataAccessException {

		RowBounds rowBounds = new RowBounds(0, 20);

		if (entityMap.get("pageSize") != null) {

			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));

		} else {

			rowBounds = rowBoundsALL;

		}

		return JSON.toJSONString(hrpAccSelectMapper.queryBankSelect(entityMap, rowBounds));
	}

	@Override
	public String queryCashItemSelect(Map<String, Object> entityMap) throws DataAccessException {

		RowBounds rowBounds = new RowBounds(0, 20);

		if (entityMap.get("pageSize") != null) {

			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));

		} else {

			rowBounds = rowBoundsALL;

		}

		return JSON.toJSONString(hrpAccSelectMapper.queryCashItemSelect(entityMap, rowBounds));
	}

	@Override
	public String queryDeptOutSelect(Map<String, Object> entityMap) throws DataAccessException {

//		RowBounds rowBounds = new RowBounds(0, 20);
//
//		if (entityMap.get("pageSize") != null) {
//
//			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
//
//		} else {
//
//			rowBounds = rowBoundsALL;
//
//		}

		return JSON.toJSONString(hrpAccSelectMapper.queryDeptOutSelect(entityMap));
	}

	@Override
	public String queryAccPatientType(Map<String, Object> entityMap) throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20);

		if (entityMap.get("pageSize") != null) {

			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));

		} else {

			rowBounds = rowBoundsALL;

		}

		return JSON.toJSONString(hrpAccSelectMapper.queryAccPatientType(entityMap, rowBounds));
	}

	@Override
	public String queryAccChargeType(Map<String, Object> entityMap) throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20);

		if (entityMap.get("pageSize") != null) {

			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));

		} else {

			rowBounds = rowBoundsALL;

		}

		return JSON.toJSONString(hrpAccSelectMapper.queryAccChargeType(entityMap, rowBounds));
	}

	//查询报表字典
	@Override
	public String querySuperReportDict(Map<String, Object> mapVo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return JSON.toJSONString(hrpAccSelectMapper.querySuperReportDict(mapVo));
	}

	@Override
	public String queryCreateUser(Map<String, Object> mapVo)
			throws DataAccessException {
		
		return JSON.toJSONString(hrpAccSelectMapper.queryCreateUser(mapVo));
	}

	@Override
	public String queryAccPayAttr(Map<String, Object> mapVo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return JSON.toJSONString(hrpAccSelectMapper.queryAccPayAttr(mapVo));
	}
	@Override
	public String queryCheckItemType(Map<String, Object> mapVo)	throws DataAccessException {
		return JSON.toJSONString(hrpAccSelectMapper.queryCheckItemType(mapVo));
	}
	
	//科室字典(根据参数决定是否按权限)
	@Override
	public String queryHosDept(Map<String, Object> entityMap) throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20);

		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}

		return JSON.toJSONString(hrpAccSelectMapper.queryHosDept(entityMap, rowBounds));
	}
	
	
	//科室分类查询
	@Override
	public String queryHosDeptKind(Map<String, Object> mapVo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return JSON.toJSONString(hrpAccSelectMapper.queryHosDeptKind(mapVo));
	}

	@Override
	public String queryAccWageItemNature(Map<String, Object> entityMap)
			throws DataAccessException {
		
//		RowBounds rowBounds = new RowBounds(0, 20);
//
//		if (entityMap.get("pageSize") != null) {
//			
//			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
//		
//		} else {
//			
//			rowBounds = rowBoundsALL;
//		
//		}

		return JSON.toJSONString(hrpAccSelectMapper.queryWageItemNature(entityMap));
	}

	@Override
	public String queryAccWageItemType(Map<String, Object> entityMap)
			throws DataAccessException {
		
//		RowBounds rowBounds = new RowBounds(0, 20);
//
//		if (entityMap.get("pageSize") != null) {
//			
//			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
//		
//		} else {
//			
//			rowBounds = rowBoundsALL;
//		
//		}

		return JSON.toJSONString(hrpAccSelectMapper.queryWageItemType(entityMap));
	}

	@Override
	public String queryBusiType(Map<String, Object> mapVo) throws DataAccessException {
//		RowBounds rowBounds = new RowBounds(0, 20);
//		if (mapVo.get("pageSize") != null) {
//			rowBounds = new RowBounds(0, (Integer) mapVo.get("pageSize"));
//		} else {
//			rowBounds = rowBoundsALL;
//		}
		return JSON.toJSONString(hrpAccSelectMapper.queryBusiType(mapVo));
	}
	
	@Override
	public String queryAccYear(Map<String, Object> mapVo) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (mapVo.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) mapVo.get("pageSize"));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAccSelectMapper.queryAccYear(mapVo));
	}

	@Override
	public String queryAccTarget(Map<String, Object> mapVo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		
		return JSON.toJSONString(hrpAccSelectMapper.queryAccTarget(mapVo));
	}

	@Override
	public String queryAccPaperType(Map<String, Object> mapVo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return JSON.toJSONString(hrpAccSelectMapper.queryAccPaperType(mapVo));
	}


	@Override
	public String queryAccWageItemColumn(Map<String, Object> mapVo)
			throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20);
		if (mapVo.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) mapVo.get("pageSize"));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAccSelectMapper.queryAccWageItemColumn(mapVo));
	}

	@Override
	public String queryBudgPaymentItemDict(Map<String, Object> mapVo) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (mapVo.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) mapVo.get("pageSize"));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAccSelectMapper.queryBudgPaymentItemDict(mapVo));
	}
	
	@Override
	public String queryBudgPaymentItemDictDept(Map<String, Object> mapVo) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (mapVo.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) mapVo.get("pageSize"));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAccSelectMapper.queryBudgPaymentItemDictDept(mapVo));
	}

	@Override
	public String queryBudgPaymentItem(Map<String, Object> mapVo) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (mapVo.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) mapVo.get("pageSize"));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAccSelectMapper.queryBudgPaymentItem(mapVo));
	}

	@Override
	public String queryBudgSysDict(Map<String, Object> mapVo) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (mapVo.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) mapVo.get("pageSize"));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAccSelectMapper.queryBudgSysDict(mapVo));
	}

	@Override
	public String querySubjDict(Map<String, Object> mapVo) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (mapVo.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) mapVo.get("pageSize"));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAccSelectMapper.querySubjDict(mapVo));
	}
	
	@Override
	public String queryAccWageColumn(Map<String, Object> mapVo) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (mapVo.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) mapVo.get("pageSize"));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAccSelectMapper.queryAccWageColumn(mapVo));
	}
	
	@Override
	public String querySubjByProjCheck(Map<String, Object> mapVo) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (mapVo.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) mapVo.get("pageSize"));
		} else {
			rowBounds = rowBoundsALL;
		}
		StringBuffer sqlWhere = new StringBuffer();
		StringBuffer colName = new StringBuffer();
		StringBuffer sWhere = new StringBuffer();
		if(mapVo.get("checkType").equals("1")){
			colName.append(" ,CHECK1_ID as checkid,CHECK1_NO as checkno ");
			sqlWhere.append(" and CHECK1_ID is not null ");
		}else if(mapVo.get("checkType").equals("2")){
			colName.append(" ,CHECK2_ID as checkid,CHECK2_NO as checkno ");
			sqlWhere.append(" and CHECK2_ID is not null ");
		}else if(mapVo.get("checkType").equals("3")){
			colName.append(" ,CHECK3_ID as checkid,CHECK3_NO as checkno ");
			sqlWhere.append(" and CHECK3_ID is not null ");
		}else if(mapVo.get("checkType").equals("4")){
			colName.append(" ,CHECK4_ID as checkid,CHECK4_NO as checkno ");
			sqlWhere.append(" and CHECK4_ID is not null ");
		}else if(mapVo.get("checkType").equals("5")){
			colName.append(" ,CHECK5_ID as checkid,CHECK5_NO as checkno ");
			sqlWhere.append(" and CHECK5_ID is not null ");
		}else if(mapVo.get("checkType").equals("6")){
			colName.append(" ,CHECK6_ID as checkid,CHECK6_NO as checkno ");
			sqlWhere.append(" and CHECK6_ID is not null ");
		}else if(mapVo.get("checkType").equals("7")){
			colName.append(" ,CHECK7_ID as checkid ");
			sqlWhere.append(" and CHECK7_ID is not null ");
		}else if(mapVo.get("checkType").equals("8")){
			colName.append(" ,CHECK8_ID as checkid ");
			sqlWhere.append(" and CHECK8_ID is not null ");
		}else{
			colName.append(" ");
			sqlWhere.append(" ");
		}
		
		sWhere.append(" and b.checkid in (").append(mapVo.get("checkid")).append(") ");
		mapVo.put("colName", colName.toString());
		mapVo.put("sqlWhere", sqlWhere.toString());
		mapVo.put("sWhere", sWhere.toString());
		return JSON.toJSONString(hrpAccSelectMapper.querySubjByProjCheck(mapVo));
	}
	
	
	//查询自定义辅助核算字典对应表
	public String queryAccBusiZCheck(Map<String, Object> mapVo) throws DataAccessException{
		return JSON.toJSONString(hrpAccSelectMapper.queryAccBusiZCheck(mapVo));
	}

	@Override
	public String queryAccMultiPlan(Map<String, Object> mapVo)throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (mapVo.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, (Integer) mapVo.get("pageSize"));
			
		} else {
			
			rowBounds = rowBoundsALL;
			
		}
		
		return JSON.toJSONString(hrpAccSelectMapper.queryAccMultiPlan(mapVo));
		
	}

	@Override
	public String queryAllEmpDict(Map<String, Object> mapVo)throws DataAccessException {
		// TODO Auto-generated method stub
		return JSON.toJSONString(hrpAccSelectMapper.queryAllEmpDict(mapVo));
	}
	
	@Override
	public String queryAccYearMonth(Map<String, Object> mapVo)throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (mapVo.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, (Integer) mapVo.get("pageSize"));
			
		} else {
			
			rowBounds = rowBoundsALL;
			
		}
		
		return JSON.toJSONString(hrpAccSelectMapper.queryAccYearMonth(mapVo));
		
	}
	/**
	 * 项目预算中查询收入项目,以410102或4201为前缀
	 * @param mapVo
	 * @return
	 */
	@Override
	public String querySubjToProj(Map<String, Object> mapVo) throws DataAccessException {
		// TODO Auto-generated method stub
		return JSON.toJSONString(hrpAccSelectMapper.querySubjToProj(mapVo));
	}

	@Override
	public String queryAccTellType(Map<String, Object> mapVo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return JSON.toJSONString(hrpAccSelectMapper.queryAccTellType(mapVo));
	}

	@Override
	public String queryGroupDict(Map<String, Object> mapVo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return JSON.toJSONString(hrpAccSelectMapper.queryGroupDict(mapVo));
	}

	@Override
	public String queryHosInfo(Map<String, Object> mapVo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return JSON.toJSONString(hrpAccSelectMapper.queryHosInfo(mapVo));
	}

	@Override
	public String queryHosCopy(Map<String, Object> mapVo)
			throws DataAccessException {

		return JSON.toJSONString(hrpAccSelectMapper.queryHosCopy(mapVo));
	}

	//账簿中的科目选择器查询
	@Override
	public String querySubjByAccount(Map<String, Object> entityMap)
			throws DataAccessException {
		
		return JSON.toJSONString(hrpAccSelectMapper.querySubjByAccount(entityMap));
	
	}
	
	//pj查询科目下拉框，根据条件匹配通用，不分页， value=code，text=code name
	@Override
	public String querySubjCode(Map<String, Object> entityMap)throws DataAccessException {
		
		return JSON.toJSONString(hrpAccSelectMapper.querySubjCode(entityMap));
	
	}
	
	//pj查询科目下拉框，根据条件匹配通用，不分页，value=id，text=code name
	@Override
	public String querySubjId(Map<String, Object> entityMap)throws DataAccessException {
		
		return JSON.toJSONString(hrpAccSelectMapper.querySubjId(entityMap));
	
	}
	
	//pj根据科目编码，查询核算类型下拉框,根据条件匹配通用，不分页
	@Override
	public String queryCheckTypeBySubjCode(Map<String, Object> entityMap)throws DataAccessException {
		
		return JSON.toJSONString(hrpAccSelectMapper.queryCheckTypeBySubjCode(entityMap));
	
	}
	
	//pj根据核算类型，查询核算项下拉框,根据条件匹配通用，不分页
	@Override
	public String queryCheckItemByType(Map<String, Object> entityMap)throws DataAccessException {
		
		//根据核算类ID查询核算类型表
		Map<String,Object> checkMap=new HashMap<String,Object>();
		checkMap=hrpAccSelectMapper.queryCheckTypeById(entityMap);
		
		entityMap.put("check_table", checkMap.get("CHECK_TABLE"));
		entityMap.put("column_id", checkMap.get("COLUMN_ID"));
		entityMap.put("column_code", checkMap.get("COLUMN_CODE"));
		entityMap.put("column_name",checkMap.get("COLUMN_NAME"));
		entityMap.put("is_sys",checkMap.get("IS_SYS"));
		entityMap.put("is_change",checkMap.get("IS_CHANGE"));
		
	//	RowBounds rowBounds = new RowBounds(0, 2000);
		return JSON.toJSONString(hrpAccSelectMapper.queryCheckItemByType(entityMap));
	
	}
	
	//pj根据核算类型，查询核算项下拉框,根据条件匹配通用，取前100条
	@Override
	public String queryCheckItemByTypeFy(Map<String, Object> entityMap)throws DataAccessException {
		
		//根据核算类ID查询核算类型表
		Map<String,Object> checkMap=new HashMap<String,Object>();
		checkMap=hrpAccSelectMapper.queryCheckTypeById(entityMap);
		
		if(checkMap == null || checkMap.size() == 0 || "".equals(checkMap.get("CHECK_TABLE").toString())){
			return null;
		}
		
		entityMap.put("check_table", checkMap.get("CHECK_TABLE"));
		entityMap.put("column_id", checkMap.get("COLUMN_ID"));
		entityMap.put("column_code", checkMap.get("COLUMN_CODE"));
		entityMap.put("column_name",checkMap.get("COLUMN_NAME"));
		entityMap.put("is_sys",checkMap.get("IS_SYS"));
		entityMap.put("is_change",checkMap.get("IS_CHANGE"));
		
		return JSON.toJSONString(hrpAccSelectMapper.queryCheckItemByTypeFy(entityMap));
	
	}
	//pj根据核算类型，查询核算项下拉框,根据条件匹配通用，取前100条
	@Override
	public String queryCheckItemByTypeByCode(Map<String, Object> entityMap)throws DataAccessException {
		
		//根据核算类ID查询核算类型表
		Map<String,Object> checkMap=new HashMap<String,Object>();
		checkMap=hrpAccSelectMapper.queryCheckTypeById(entityMap);
		
		if(checkMap == null || checkMap.size() == 0 || "".equals(checkMap.get("CHECK_TABLE").toString())){
			return null;
		}
		
		entityMap.put("check_table", checkMap.get("CHECK_TABLE"));
		entityMap.put("column_id", checkMap.get("COLUMN_CODE"));
		entityMap.put("column_code", checkMap.get("COLUMN_CODE"));
		entityMap.put("column_name",checkMap.get("COLUMN_NAME"));
		entityMap.put("is_sys",checkMap.get("IS_SYS"));
		entityMap.put("is_change",checkMap.get("IS_CHANGE"));
		
		return JSON.toJSONString(hrpAccSelectMapper.queryCheckItemByType(entityMap));
		
	}
	
	 //  出纳账管理    摘要字典 下拉框
		@Override
		public String queryAccTellSummaryById(Map<String, Object> entityMap)throws DataAccessException {
			 
			
			RowBounds rowBounds = new RowBounds(0, 50);
			return JSON.toJSONString(hrpAccSelectMapper.queryAccTellSummaryById(entityMap, rowBounds));
		
		}
		//his视图下拉框
		@Override
		public String queryAccHisLog(Map<String, Object> mapVo)
				throws DataAccessException {
			// TODO Auto-generated method stub
			return JSON.toJSONString(hrpAccSelectMapper.queryAccHisLog(mapVo));
		}
		//项目负责人
		@Override
		public String queryProjEmp(Map<String, Object> mapVo)
				throws DataAccessException {
			// TODO Auto-generated method stub
			return JSON.toJSONString(hrpAccSelectMapper.queryProjEmp(mapVo));
		}

		@Override
		public String queryBusiTypeByVouch(Map<String, Object> mapVo)
				throws DataAccessException {
			// TODO Auto-generated method stub
			return JSON.toJSONString(hrpAccSelectMapper.queryBusiTypeByVouch(mapVo));
		}
		/**
		 * 集团-----会计科目   根据医院账套进行级联
		 */
		@Override
		public String querySubjByHosCopyRela(Map<String, Object> mapVo)
				throws DataAccessException {
			// TODO Auto-generated method stub
			return JSON.toJSONString(hrpAccSelectMapper.querySubjByHosCopyRela(mapVo));
		}
		/**
		 * 集团-----会计科目   根据医院账套进行级联
		 * 查询科目下拉框，根据条件匹配通用，不分页， value=code，text=code name  search=拼音码 五笔码检索   
		 *  用于 自定义辅助核算余额表
		 */
		@Override
		public String querySubjCodeByRela(Map<String, Object> mapVo)throws DataAccessException {
			return JSON.toJSONString(hrpAccSelectMapper.querySubjCodeByRela(mapVo));
		}
		/**
		 * 集团-----账簿中的科目选择器查询
		 */ 
		@Override
		public String querySubjByAccountRela(Map<String, Object> entityMap)
				throws DataAccessException {
			
			return JSON.toJSONString(hrpAccSelectMapper.querySubjByAccountRela(entityMap));
		
		}
		
	// 账簿方案列表
	@Override
	public String queryAccBookSch(Map<String, Object> entityMap) throws DataAccessException{
		
		return JSON.toJSONString(hrpAccSelectMapper.queryAccBookSch(entityMap));
	 
	}
	// 账簿方案--单位列表
	@Override
	public String querySysHosAll(Map<String, Object> entityMap) throws DataAccessException{
		
		return JSON.toJSONString(hrpAccSelectMapper.querySysHosAll(entityMap));
	
	}
	// 账簿方案--账套列表
	@Override
	public String querySysHosCopyAll(Map<String, Object> entityMap) throws DataAccessException{
		
		return JSON.toJSONString(hrpAccSelectMapper.querySysHosCopyAll(entityMap));
	}
	// 核算项ID
	@Override
	public String queryCheckTypeIdByCheckName(Map<String, Object> entityMap) throws DataAccessException{
		
		HrpAccSelect hr = hrpAccSelectMapper.queryCheckTypeIdByCheckName(entityMap);
		return hr.getId().toString();
		
	}

	@Override
	public String queryEmpDictByWageCode(Map<String, Object> mapVo) {
		List<HrpAccSelect>  list = hrpAccSelectMapper.queryEmpDictByWageCode(mapVo);
		return JSON.toJSONString(list);
	}

	@Override
	public String queryEmpSet(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return JSON.toJSONString(hrpAccSelectMapper.queryEmpSet(entityMap));
	}
	
	// 账簿方案--单位列表
	@Override
	public String queryAccPaperCheques(Map<String, Object> entityMap) throws DataAccessException{
		RowBounds rowBounds = new RowBounds(0, 20);
		
		return JSON.toJSONString(hrpAccSelectMapper.queryAccPaperCheques(entityMap,rowBounds));
	}
	@Override
	public String querySubjByCode(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAccSelectMapper.querySubjByCode(entityMap, rowBounds));
	}
	@Override
	public String queryDrugsPrepaType(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAccSelectMapper.queryDrugsPrepaType(entityMap, rowBounds));
	}
	
	@Override
	public String queryAccDrugsPrepaType(Map<String, Object> entityMap) throws DataAccessException {
		return JSON.toJSONString(hrpAccSelectMapper.queryAccDrugsPrepaType(entityMap));
	}
	
	@Override
	public String queryHosSupDictUniversalMethod(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAccSelectMapper.queryHosSupDictUniversalMethod(entityMap, rowBounds));
	}

	/**
	 * 付款方式(结算方式)
	 * @param entityMap
	 * @return
	 */
	@Override
	public String queryAccPayType(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAccSelectMapper.queryAccPayType(entityMap,rowBounds));
	}

	@Override
	public String queryAccVirStore(Map<String, Object> entityMap) throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAccSelectMapper.queryAccVirStore(entityMap, rowBounds));
	}

	//通过参数控制仓库权限 
	@Override
	public String queryAccStoreDictDate(Map<String, Object> entityMap)
				throws DataAccessException {
		// TODO Auto-generated method stub
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAccSelectMapper.queryAccStoreDictDate(entityMap, rowBounds));
	}

	@Override
	public String querySysUser(Map<String, Object> entityMap){
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAccSelectMapper.querySysUser(entityMap, rowBounds));
	}

	@Override
	public String queryHosSupDictDisable(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAccSelectMapper.queryHosSupDictDisable(entityMap, rowBounds));
	}

	//通过参数控制 编制科室权限 
	@Override
	public String queryAccDeptDictDate(Map<String, Object> entityMap)
			throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAccSelectMapper.queryAccDeptDictDate(entityMap, rowBounds));
	}

	/**
	 * 采购发票  付款条件下拉框
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryAccPayTerm(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAccSelectMapper.queryAccPayTerm(entityMap, rowBounds));
	}

	/**
	 * 资产性质下拉框检索
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryAccNaturs(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAccSelectMapper.queryAccNaturs(entityMap, rowBounds));
	}
	
	@Override
	public String queryHosSupDictNo(Map<String, Object> entityMap) {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAccSelectMapper.queryHosSupDictNo(entityMap, rowBounds));
	}
	
	/**
	 * 合同下拉框检索
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryContractMain(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAccSelectMapper.queryContractMain(entityMap, rowBounds));
	}

	/**
	 * 供应商下拉框检索
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryHosSupDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAccSelectMapper.queryHosSupDict(entityMap, rowBounds));
	}

	/**
	 * 仓库下拉框检索
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryHosStoreDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAccSelectMapper.queryHosStoreDict(entityMap, rowBounds));
	}

	@Override
	public String queryGroupDictDataType(Map<String, Object> mapVo) throws DataAccessException {
		return JSON.toJSONString(hrpAccSelectMapper.queryGroupDictDataType(mapVo));
	}

	@Override
	public String queryHosInfoDataType(Map<String, Object> mapVo) throws DataAccessException {
		return JSON.toJSONString(hrpAccSelectMapper.queryHosInfoDataType(mapVo));
	}

	@Override
	public String queryHosCopyDataType(Map<String, Object> mapVo) throws DataAccessException {
		return JSON.toJSONString(hrpAccSelectMapper.queryHosCopyDataType(mapVo));
	}

	@Override
	public String queryAccMedTypeHis(Map<String, Object> mapVo) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (mapVo.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(mapVo.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAccSelectMapper.queryAccMedTypeHis(mapVo, rowBounds));
	}

	@Override
	public String queryAccBasType(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return JSON.toJSONString(hrpAccSelectMapper.queryAccBasType(entityMap));
	}
	
	@Override
	public String queryAccWxType(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return JSON.toJSONString(hrpAccSelectMapper.queryAccWxType(entityMap));
	}

	@Override
	public String queryHosDictTypeDict(Map<String, Object> mapVo) {
		
		return JSON.toJSONString(hrpAccSelectMapper.queryHosDictTypeDict(mapVo));
	}

	@Override
	public String queryInitAccDict(Map<String, Object> mapVo) {
		return JSON.toJSONString(hrpAccSelectMapper.queryInitAccDict(mapVo));
	}
	//业务类型
	@Override
	public String queryAccYewuType(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return JSON.toJSONString(hrpAccSelectMapper.queryAccYewuType(entityMap));
	}
	//业务字典
	@Override
	public String queryAccYewuDict(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return JSON.toJSONString(hrpAccSelectMapper.queryAccYewuDict(entityMap));
	}
	//业务字典
	@Override
	public String queryAccBudgRange(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return JSON.toJSONString(hrpAccSelectMapper.queryAccBudgRange(entityMap));
	}
}
