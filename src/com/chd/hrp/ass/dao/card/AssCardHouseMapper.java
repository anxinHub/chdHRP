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
import com.chd.hrp.ass.entity.card.AssCardHouse;
/**
 * 
 * @Description:
 * 资产卡片维护_房屋及建筑物
 * @Table:
 * ASS_CARD_HOUSE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssCardHouseMapper extends SqlMapper{

	List<Map<String, Object>> queryByAssInNo(Map<String, Object> inMapVo);

	void updateConfirm(List<Map<String, Object>> entityMap);

	void deleteBatchByAssInNo(List<Map<String, Object>> cardList);

	Integer queryCardExistsByAssInNo(Map<String, Object> entityMap);

	void updateBackConfirm(List<Map<String, Object>> cardEntityMap);

	void updateSellOutConfirm(List<Map<String, Object>> cardEntityMap);

	void updateChkApplyConfirm(List<Map<String, Object>> listCardVo);

	void updateChkRecordConfirm(List<Map<String, Object>> listCardVo);

	void updatePriceConfirm(List<Map<String, Object>> cardList);

	void updateDepreMoneyConfirm(List<Map<String, Object>> cardList);

	void updateDisposalApplyConfirm(List<Map<String, Object>> listCardVo);

	void updateDisposalRecordConfirm(List<Map<String, Object>> listCardVo);

	void updateAssRemouldAHouseConfirmState(List<Map<String, Object>> listVo);

	void updateRemouldFHouseConfirm(List<Map<String, Object>> listCardVo);
	
	int updateAssCardStateByCardNo(Map<String, Object> map);

	int updateAssCardStateByRecCardNo(Map<String, Object> entityMap);
	
	List<Map<String, Object>>queryAssCardHousePrint(Map<String, Object> entityMap);
	void updateRemouldRHouseConfirm(List<Map<String, Object>> listCardVo);

	void updateAssRemouldFHouseConfirmState(List<Map<String, Object>> listCardVo);
	public List<Map<String, Object>> queryAssCardInassetsPrint(Map<String,Object> entityMap)throws DataAccessException;

	List<AssCardHouse> queryByAssCardOldNo(Map<String, Object> map);

	List<Map<String, Object>> queryStore(List<Map<String, Object>> listVo);
	List<Map<String, Object>> queryPrint(Map<String, Object> entityMap);

}
