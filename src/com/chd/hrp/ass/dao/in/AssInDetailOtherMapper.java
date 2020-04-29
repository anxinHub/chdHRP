/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.in;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.in.AssInDetailOther;
/**
 * 
 * @Description:
 * 资产入库明细(其他固定资产)
 * @Table:
 * ASS_IN_DETAIL_OTHER
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssInDetailOtherMapper extends SqlMapper{
	public List<Map<String,Object>> queryByInit(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<AssInDetailOther> queryByAssInNo(Map<String,Object> entityMap) throws DataAccessException;

	public List<AssInDetailOther> queryAssBackOtherDetail(
			Map<String, Object> mapVo);
	
}
