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
import com.chd.hrp.ass.entity.card.AssCardInassets;
/**
 * 
 * @Description:
 * 资产卡片维护_无形资产
 * @Table:
 * ASS_CARD_INASSETS
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssCardInassetsMapper extends SqlMapper{
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
	
	public int updateDisposalRecordConfirm(List<Map<String, Object>> entityMap)throws DataAccessException;

	public void updateAssRemouldAinassetsConfirmState(List<Map<String, Object>> list);

	public void updateChkApplyConfirm(List<Map<String, Object>> entityMap)throws DataAccessException;

	public void updateChkRecordConfirm(List<Map<String, Object>> entityMap)throws DataAccessException;

	public int updateRemouldFInassetsConfirm(List<Map<String, Object>> listCardVo);

	public int updateAssCardStateByCardNo(Map<String, Object> map);

	public int updateAssCardStateByRecCardNo(Map<String, Object> entityMap);

	public void updateRemouldRinassetsConfirm(List<Map<String, Object>> listCardVo);

	public List<Map<String, Object>> queryAssCardInassetsPrint(Map<String, Object> entityMap);

	public List<AssCardInassets> queryByAssCardOldNo(Map<String, Object> map);

	public List<AssCardInassets> queryCount(Map<String, Object> vCreateDateMap);
	
	List<Map<String, Object>> queryPrint(Map<String, Object> entityMap);
	
	public int updateBillState(List<Map<String, Object>> listCardVo)throws DataAccessException;
}
