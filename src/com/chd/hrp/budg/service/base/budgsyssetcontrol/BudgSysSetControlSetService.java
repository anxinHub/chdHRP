package com.chd.hrp.budg.service.base.budgsyssetcontrol;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
import com.chd.hrp.budg.entity.BudgCDet;
import com.chd.hrp.budg.entity.BudgSysSetControlSet;

public interface BudgSysSetControlSetService{
    /**
     * 添加预算控制方案
     * @param mapVo
     * @return
     */
	String addBudgSysSetControl(Map<String, Object> mapVo) throws DataAccessException;;
   /**
    * 初始化预算控制方案
    * @param mapVo
    * @return
    */
	String initBudgSysSetControl(Map<String, Object> mapVo) throws DataAccessException;;
   /**
    * 继承预算控制方案
    * @param mapVo
    * @return
    */
	String extendBudgSysSetControl(Map<String, Object> mapVo) throws DataAccessException;; 
	/**
	 * 启用预算控制方案
	 * @param mapVo
	 * @return
	 */
    String checkBudgSysSetControl(Map<String, Object> mapVo) throws DataAccessException;
    /**
     * 更新
     * @param detailVo
     * @return
     */
    String updateBudgSysSetControl(Map<String, Object> mapVo) throws DataAccessException;
    /**
     * 批量更新
     * @param detailVo
     * @return
     */
	String updateBatchBudgSetControl(Map<String, Object> detailVo) throws DataAccessException;
	/**
	 * 批量保存
	 * @param detailVo
	 * @return
	 */
	String updateSetBudgSetControl(Map<String, Object> detailVo) throws DataAccessException;
	/**
	 * 明细保存
	 * @param detailVo
	 * @return
	 */
	String updateDetailBudgSysSetControl(Map<String, Object> detailVo) throws DataAccessException;
	/**
	 * 删除明细预算控制
	 * @param listVo
	 * @return
	 */
	String deleteBudgSysSetControl(List<BudgCDet> listVo) throws DataAccessException;
	/**
	 * 查询明细
	 * @param page
	 * @return
	 */
	String queryBudgSysSetControl(Map<String, Object> page) throws DataAccessException;
	/**
	 * 更新调转
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
     BudgSysSetControlSet queryByCodeSysSetControlSet(Map<String, Object> mapVo)throws DataAccessException;
	String updateBudgSysSetControlSet(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 明细查询
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	String queryBudgCDet(Map<String, Object> page)throws DataAccessException;
	/**
	 * 预算项：与预算方案关联，取自BUDG_C_PLAN中ITEM_SQL
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String queryBudgModSelect(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 查询预算项
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	String queryItemCode(Map<String, Object> page)throws DataAccessException;
	Map<String, Object> queryItemTabName(Map<String, Object> mapVo)throws DataAccessException;
	String queryLinkName(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 复制
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String updateSetBudgCopy(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 停用
	 * @param detailVo
	 * @return
	 * @throws DataAccessException
	 */
	String uncheckBudgSysSetControl(Map<String, Object> detailVo)throws DataAccessException;
	

}
