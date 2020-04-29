package com.chd.hrp.htcg.service.setout;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */

public interface HtcgMaterialAdvicePgroupService {

	public String initHtcgMaterialAdvicePgroup(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryHtcgMaterialAdvicePgroup(Map<String,Object> entityMap) throws DataAccessException;
	
	public String calculateHtcgMaterialAdvicePgroup(Map<String,Object> entityMap)throws DataAccessException;
	
	public String admittanceHtcgMaterialAdvicePgroup(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteBatchHtcgMaterialAdvicePgroup(List<Map<String,Object>> list)throws DataAccessException;
	
}
