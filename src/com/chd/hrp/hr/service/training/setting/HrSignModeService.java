package com.chd.hrp.hr.service.training.setting;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.training.setting.HrSignMode;


public interface HrSignModeService {
    /**
     * 添加
     * @param mapVo
     * @return
     * @throws DataAccessException
     */
	String addSignMode(Map<String, Object> mapVo) throws DataAccessException;
   /**
    * 修改跳转
    * @param mapVo
    * @return
    * @throws DataAccessException
    */
	HrSignMode queryByCode(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 修改
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
   String updateSignMode(Map<String, Object> mapVo)throws DataAccessException;
   /**
    * 删除
    * @param listVo
    * @return
    * @throws DataAccessException
    */
	String deleteSignMode(List<HrSignMode> listVo)throws DataAccessException;
	/**
	 * 查询数据
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
    String querySignMode(Map<String, Object> page)throws DataAccessException;
    /**
     * 导入
     * @param mapVo
     * @return
     * @throws DataAccessException
     */
	String importDate(Map<String, Object> mapVo)throws DataAccessException;
	int queryUseSignMode(HrSignMode hrSignMode)throws DataAccessException;
}
