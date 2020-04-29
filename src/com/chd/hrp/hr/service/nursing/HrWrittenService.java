package com.chd.hrp.hr.service.nursing;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.nursing.HrWritten;

/**
 * 笔试成绩
 * 
 * @author Administrator
 *
 */
public interface HrWrittenService {
	/**
	 * 增加笔试成绩
	 * 
	 * @param mapVo
	 * @return
	 */
	String addWritten(Map<String, Object> mapVo)throws DataAccessException;

    /**
     * 删除笔试成绩
     * @param listVo
     * @return
     */
	String deleteWritten(List<HrWritten> listVo)throws DataAccessException;
     /**
      * 查询笔试成绩
      * @param page
      * @return
      */
	String queryWritten(Map<String, Object> page)throws DataAccessException;
	/**
     * 提交
     * @param List<HrWritten>
     * @return
     */
    String confirmWritten(List<HrWritten> list) throws DataAccessException ;
    /**
     * 撤回
     * @param List<HrWritten>
     * @return
     */
	String reConfirmWritten(List<HrWritten> list) throws DataAccessException ;
    /**
     * 导入
     * @param mapVo
     * @return
     * @throws DataAccessException
     */
	String importWrite(Map<String, Object> mapVo)throws DataAccessException ;

	

}
