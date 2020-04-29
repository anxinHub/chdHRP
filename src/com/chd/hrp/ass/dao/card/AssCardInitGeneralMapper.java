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
/**
 * 
 * @Description:
 * 资产卡片维护_一般设备
 * @Table:
 * ASS_CARD_GENERAL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssCardInitGeneralMapper extends SqlMapper{
	int queryByAssOriCardNo(Map<String, Object> data)throws DataAccessException;
	
	List<Map<String,Object>> queryCardByMap(Map<String, Object> data)throws DataAccessException;
	List<Map<String, Object>> queryPrint(Map<String, Object> entityMap);
}
