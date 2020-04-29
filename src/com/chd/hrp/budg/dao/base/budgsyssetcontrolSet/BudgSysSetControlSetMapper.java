
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.budg.dao.base.budgsyssetcontrolSet;
import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccSubj;
import com.chd.hrp.budg.entity.BudgAccSubjShip;
import com.chd.hrp.budg.entity.BudgCDet;
import com.chd.hrp.budg.entity.BudgSysSetControlSet;
/**
 * 
 * @Description:
 * 预算控制
 * @Table:
 * BUDG_ACC_SUBJ_SHIP
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public interface BudgSysSetControlSetMapper extends SqlMapper{
     /**
      * 查询
      * @param entityMap
      * @return
      */
	List<BudgSysSetControlSet> queryBudgSysSetControl(Map<String, Object> entityMap);
    /**
     * 查询带分页
     * @param entityMap
     * @param rowBounds
     * @return
     */
	List<BudgSysSetControlSet> queryBudgSysSetControl(Map<String, Object> entityMap,RowBounds rowBounds);
	/**
	 *  从BUDG_C_P_L中取方案对应的环节LINK_CODE
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryLinkcode(Map<String, Object> entityMap);
	/**
	 * 从BUDG_C_TAB中取方案对应的默认预算表tab_code,IS_STOP=0，IS_DEF=1;
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryTabCode(Map<String, Object> entityMap);
	/**
	 * 从BUDG_C_TAB_W中取预算表对应的默认控制模式CONT_W 控制层次CONT_L控制期间 CONT_P,IS_STOP=0，IS_DEF=1;
	 */
	List<Map<String, Object>> queryCont(Map<String, Object> entityMap);
	/**
	 * 从BUDG_C_STATE中取该环节对应的默认占用状态USE_STATE，可能存在多个，IS_STOP=0，IS_DEF=1;
	 */
	List<Map<String, Object>> queryUseState(Map<String, Object> entityMap);
	List<Map<String, Object>> querylinkCod(Map<String, Object> entityMap);
	List<BudgCDet> queryBudgCDet(Map<String, Object> entityMap);
	List<BudgCDet> queryBudgCDet(Map<String, Object> entityMap,RowBounds rowBounds);
	Map<String, Object> queryBudgCplan(Map<String, Object> entityMap);
	List<Map<String, Object>>  queryBudgModSelect(Map<String, Object> entityMap);
	/**
	 * 查询明细是否存在
	 * @param entityMap
	 * @return
	 */
	int queryBudgDet(Map<String, Object> entityMap);
	/**
	 * 更新明细
	 * @param entityMap
	 * @return
	 */
	int updateDet(Map<String, Object> entityMap);
	/**
	 * 添加明细
	 * @param entityMap
	 * @return
	 */
	int addDet(Map<String, Object> entityMap);
	/**
	 * 批量设置明细
	 * @param entityMap
	 * @return
	 */
	int updateDetBatch(Map<String, Object> entityMap);
	/**
	 * 删除明细
	 * @param listVo
	 */
	void deleteBudgSysSetControl(List<BudgCDet> listVo);
	Map<String, Object> queryItemTabName(Map<String, Object> entityMap);
	List<Map<String, Object>> queryLinkName(Map<String, Object> entityMap);
	/**
	 * 复制
	 * @param entityMap
	 */
	void updateSetBudgCopy(Map<String, Object> entityMap);
	int queryCheckExict(Map<String, Object> entityMap);
	void checkBudgSysSetControl(Map<String, Object> entityMap);
	/*
	 *启用查询
	 */
	int queryBudgDetCheck(Map<String, Object> entityMap);
	BudgSysSetControlSet queryByCodeSysSetControlSet(Map<String, Object> entityMap);
	
	
}  
