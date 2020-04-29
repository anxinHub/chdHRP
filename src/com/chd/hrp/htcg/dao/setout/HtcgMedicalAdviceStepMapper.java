package com.chd.hrp.htcg.dao.setout;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htcg.entity.setout.HtcgMedicalAdviceStep;
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

public interface HtcgMedicalAdviceStepMapper extends SqlMapper{
 
 
	public Map<Object,String> initHtcgMedicalAdviceStep(Map<String,Object> entityMap)throws DataAccessException;
 
	public List<HtcgMedicalAdviceStep> queryHtcgMedicalAdviceStep(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<HtcgMedicalAdviceStep> queryHtcgMedicalAdviceStep(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
 
	public int deleteBatchHtcgMedicalAdviceStep(List<Map<String,Object>> entityList)throws DataAccessException;
 
	
}
