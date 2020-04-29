/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.tran.out;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.tran.out.AssOutDetailSpecial;
/**
 * 
 * @Description:
 * 050901 资产领用明细(专用设备)
 * @Table:
 * ASS_OUT_DETAIL_SPECIAL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssOutDetailSpecialMapper extends SqlMapper{
	List<AssOutDetailSpecial> queryByOutNo(Map<String,Object> entityMap)throws DataAccessException;

	List<AssOutDetailSpecial> queryOutSpecialCard(Map<String, Object> mapsd);
}
