package com.chd.hrp.ass.service.inspection;

import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.ass.entity.inspection.AssInspectionDetail;

public interface AssInspectionDetailService {

	public String addAssInspectionDetail(Map<String, Object> entityMap)throws DataAccessException;

	public AssInspectionDetail queryAssInspectionDetailByCode(Map<String, Object> entityMap)throws DataAccessException;

	public String updateAssInspectionDetail(Map<String, Object> entityMap)throws DataAccessException;

	public String addOrUpdateAssInspectionDetail(Map<String, Object> entityMap)throws DataAccessException;

	public String deleteBatchAssInspectionDetail(List<Map<String, Object>> entityMap)throws DataAccessException;

	public String queryAssInspectionDetailByUpdate(Map<String, Object> entityMap)throws DataAccessException;

	public AssInspectionDetail queryAssInspectionDetailByUniqueness(
			Map<String, Object> entityMap) throws DataAccessException;

	public List<AssInspectionDetail> queryAssInspectionDetailExists(
			Map<String, Object> entityMap) throws DataAccessException;

	public String queryAssInspectionDetail(Map<String, Object> entityMap)
			throws DataAccessException;

}
