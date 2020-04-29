package com.chd.hrp.hr.service.training.setting;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.training.setting.HrExamineMode;

public interface HrExamineModeService {
    /**
     * 添加
     * @param mapVo
     * @return
     * @throws DataAccessException
     */
	String addExamineMode(Map<String, Object> mapVo) throws DataAccessException;
   /**
    * 修改跳转
    * @param mapVo
    * @return
    * @throws DataAccessException
    */
	HrExamineMode queryByCode(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 修改
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
   String updateExamineMode(Map<String, Object> mapVo)throws DataAccessException;
   /**
    * 删除
    * @param listVo
    * @return
    * @throws DataAccessException
    */
	String deleteExamineMode(List<HrExamineMode> listVo)throws DataAccessException;
	/**
	 * 查询数据
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
    String queryExamineMode(Map<String, Object> page)throws DataAccessException;
    /**
     * 导入
     * @param mapVo
     * @return
     * @throws DataAccessException
     */
	String importDate(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 查询是否使用
	 * @param hrExamineMode
	 * @return
	 * @throws DataAccessException
	 */
	int queryUseExamineMode(HrExamineMode hrExamineMode)  throws DataAccessException;
}
