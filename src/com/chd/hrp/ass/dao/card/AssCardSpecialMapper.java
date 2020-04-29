/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.card;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.card.AssCardSpecial;
/**
 * 
 * @Description:
 * 资产卡片维护_专用设备
 * @Table:
 * ASS_CARD_SPECIAL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssCardSpecialMapper extends SqlMapper{     
	
	public int updateConfirm(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public int updateBackConfirm(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public int updateSellOutConfirm(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public int updateDeptGetOutConfirm(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public int updateDeptBackOutConfirm(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public int updateTranStoreConfirm(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public int updateTranDeptConfirm(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public int deleteBatchByAssInNo(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public int updatePriceConfirm(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public int updateDepreMoneyConfirm(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public Integer queryCardExistsByAssInNo(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<Map<String, Object>> queryByAssInNo(Map<String,Object> entityMap) throws DataAccessException;

	public int updateDisposalApplyConfirm(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public int updateDisposalApplyUnConfirm(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public int updateDisposalRecordConfirm(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public int updateDisposalRecordUnConfirm(List<Map<String, Object>> entityMap)throws DataAccessException;

	public void updateAssRemouldAspecialConfirmState(List<Map<String, Object>> listCardVo);

	public void updateChkApplyConfirm(List<Map<String, Object>> entityMap)throws DataAccessException;

	public void updateChkRecordConfirm(List<Map<String, Object>> entityMap)throws DataAccessException;

	public int updateRemouldFSpecialConfirm(List<Map<String, Object>> listCardVo)throws DataAccessException;

	public int updateAssCardStateByCardNo(Map<String, Object> map)throws DataAccessException;

	public int updateAssCardStateByRecCardNo(Map<String, Object> entityMap)throws DataAccessException;

	public void updateRemouldRSpecialConfirm(List<Map<String, Object>> listCardVo)throws DataAccessException;
	
	public List<Map<String, Object>> queryAssCardSpeciaPrint(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<Map<String, Object>> queryAssCardSpeciaPrintCode(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<?> queryByAssCardOldNo(Map<String,Object> entityMap)throws DataAccessException;

	public List<AssCardSpecial> queryCount(Map<String, Object> vCreateDateMap)throws DataAccessException;

	public int updateIsBarPrint(List<Map<String, Object>> listCardVo)throws DataAccessException;
	
	List<Map<String, Object>> queryPrint(Map<String, Object> entityMap);
	
	public int updateBillState(List<Map<String, Object>> listCardVo)throws DataAccessException;
}
