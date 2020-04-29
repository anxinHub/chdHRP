package com.chd.hrp.htc.dao.relative.plan.deptrela;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htc.entity.relative.plan.deptrela.HtcRelativeDeptRela;
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

public interface HtcRelativeDeptRelaMapper extends SqlMapper{
	
	public int addHtcRelativeDeptRela(Map<String,Object> entityMap)throws DataAccessException;
	
    public List<HtcRelativeDeptRela> queryHtcRelativeDeptRela(Map<String,Object> entityMap)throws DataAccessException;
    
    public List<HtcRelativeDeptRela> queryHtcRelativeDeptRela(Map<String,Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	
    public HtcRelativeDeptRela queryHtcRelativeDeptRelaByCode(Map<String,Object> entityMap)throws DataAccessException;
    
	public int deleteBatchHtcRelativeDeptRela(List<Map<String, Object>> list)throws DataAccessException;
	
	public int updateHtcRelativeDeptRela(Map<String,Object> entityMap)throws DataAccessException;
}
