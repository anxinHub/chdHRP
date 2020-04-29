/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.card;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
import com.chd.hrp.ass.entity.card.AssCardOther;
/**
 * 
 * @Description:
 * 资产卡片维护_其他固定资产
 * @Table:
 * ASS_CARD_OTHER
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssCardOtherService extends SqlService {
public Integer queryCardExistsByAssInNo(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<Map<String, Object>> queryAssCardByAssInNo(Map<String,Object> entityMap)throws DataAccessException;

	public List<AssCardOther> queryCount(Map<String, Object> vCreateDateMap)throws DataAccessException;
	
	public String updateIsBarPrint(List<Map<String, Object>> listCardVo)throws DataAccessException;
	
	public List<Map<String,Object>> queryPrint(Map<String, Object> entityMap) throws DataAccessException ;


}
