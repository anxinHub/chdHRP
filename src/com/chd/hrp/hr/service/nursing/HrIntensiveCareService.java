package com.chd.hrp.hr.service.nursing;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.nursing.HrIntensiveCare;

/**
 * 重症护理能力
 * 
 * @author Administrator
 *
 */
public interface HrIntensiveCareService {
	/**
	 * 添加重症护理能力
	 * 
	 * @param mapVo
	 * @return
	 */
	String addIntensiveCare(Map<String, Object> mapVo)throws DataAccessException;

    /**
     * 删除重症护理能力
     * @param listVo
     */
	String deleteIntensiveCare(List<HrIntensiveCare> listVo)throws DataAccessException;
    /**
     * 查询重症护理能力
     * @param page
     * @return
     */
	String queryIntensiveCare(Map<String, Object> page)throws DataAccessException;
    /**
     * 导入
     * @param mapVo
     * @return
     * @throws DataAccessException
     */
	String importAdmin2(Map<String, Object> mapVo)throws DataAccessException;

}
