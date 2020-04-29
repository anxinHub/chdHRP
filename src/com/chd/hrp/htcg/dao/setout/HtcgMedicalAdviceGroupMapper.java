package com.chd.hrp.htcg.dao.setout;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htcg.entity.setout.HtcgMedicalAdviceGroup;


public interface HtcgMedicalAdviceGroupMapper extends SqlMapper{
	
	
    public Map<String, Object> initHtcgMedicalAdviceGroup(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<HtcgMedicalAdviceGroup> queryHtcgMedicalAdviceGroup(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<HtcgMedicalAdviceGroup> queryHtcgMedicalAdviceGroup(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public int deleteBatchHtcgMedicalAdviceGroup(List<Map<String,Object>> list)throws DataAccessException;
	
}
