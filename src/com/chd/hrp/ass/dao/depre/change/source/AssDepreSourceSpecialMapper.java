/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.depre.change.source;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 050806 资产累计折旧变动资金来源(专用设备)
 * @Table:
 * ASS_DEPRE_SOURCE_SPECIAL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssDepreSourceSpecialMapper extends SqlMapper{
	List<Map<String, Object>> queryDepreSourceByAssCardNo(Map<String,Object> entityMap)throws DataAccessException;
}
