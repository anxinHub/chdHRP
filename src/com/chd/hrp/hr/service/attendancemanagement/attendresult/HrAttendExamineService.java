package com.chd.hrp.hr.service.attendancemanagement.attendresult;

import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface HrAttendExamineService {

	public	String queryAttendExamine(Map<String, Object> entityMap) throws DataAccessException;

	public	String printAttendExamine(Map<String, Object> entityMap) throws DataAccessException;

	public Map<String, Object>  submitAttendExamine(Map<String, Object> entityMap) throws DataAccessException;
	 /**
     * 审核
     * @param entityMap
     * @return
     * @throws DataAccessException
     */
	public	Map<String, Object> checkOrUnCheckResultExamine(Map<String, Object> entityMap) throws DataAccessException;

	public	Map<String, Object> confirmAttendExamine(Map<String, Object> entityMap) throws DataAccessException;
	
	public	Map<String, Object> summaryAttendExamine(Map<String, Object> entityMap) throws DataAccessException;
    /**
     * 取消上报
     * @param entityMap
     * @return
     * @throws DataAccessException
     */
	public Map<String, Object> unSubmitAttendExamine(Map<String, Object> entityMap) throws DataAccessException;
   /**
    * 销审
    * @param entityMap
    * @return
    * @throws DataAccessException
    */
	public Map<String, Object> unCheckAttendExamine(Map<String, Object>  entityMap) throws DataAccessException;
/**
 * 清除
 * @param mapVo
 * @return
 */
public Map<String, Object> deleteBatchAttendResultExamineManage(Map<String, Object> mapVo);

}
