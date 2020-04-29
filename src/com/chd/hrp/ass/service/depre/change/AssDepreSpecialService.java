/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.depre.change;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 050806 资产累计折旧变动(专用设备)
 * @Table:
 * ASS_DEPRE_SPECIAL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssDepreSpecialService extends SqlService {
	String deleteAssDepreSourceSpecial(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	String queryAssDepreSourceSpecial(Map<String, Object> entityMap)throws DataAccessException;
	
	String saveAssDepreSourceSpecial(Map<String, Object> entityMap)throws DataAccessException;
	
	String updateConfirm(List<Map<String, Object>> entityMap)throws DataAccessException;
}
