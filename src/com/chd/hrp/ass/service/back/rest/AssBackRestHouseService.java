/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.back.rest;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 050701 资产其他退账单主表(房屋及建筑物)
 * @Table:
 * ASS_BACK_REST_HOUSE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssBackRestHouseService extends SqlService {

	String updateBackConfirm(List<Map<String, Object>> listVo, List<Map<String, Object>> listCardVo);

	Map<String, Object> printAssBackRestHouseData(Map<String, Object> map) throws DataAccessException;

	List<String> queryAssBackRestHouseStates(Map<String, Object> mapVo);

	String queryDetails(Map<String, Object> page) throws DataAccessException;

}
