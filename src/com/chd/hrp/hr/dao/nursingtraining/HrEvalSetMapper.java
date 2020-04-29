package com.chd.hrp.hr.dao.nursingtraining;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.nursingtraining.HrEvalSet;

/**
 * 
 * @ClassName: HrEvalSetMapper
 * @Description: 护理考核标准设定
 * @author zn
 * @date 2018年1月19日 下午3:20:49
 * 
 *
 */
public interface HrEvalSetMapper extends SqlMapper {
	int saveBatchHrEvalSet(List<HrEvalSet> dataList) throws DataAccessException;

	int deleteBatchHrEvalSet(List<HrEvalSet> dataList) throws DataAccessException;
	
	int deleteHrEvalSet(Map<String,Object> entityMap) throws DataAccessException;
	
	List<HrEvalSet> queryHrEvalSet(Map<String,Object> entityMap) throws DataAccessException;
}
