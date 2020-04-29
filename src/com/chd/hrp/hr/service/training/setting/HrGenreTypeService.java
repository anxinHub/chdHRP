package com.chd.hrp.hr.service.training.setting;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.training.setting.HrGenreType;




public interface HrGenreTypeService {
    /**
     * 添加
     * @param mapVo
     * @return
     * @throws DataAccessException
     */
	String addGenreType(Map<String, Object> mapVo) throws DataAccessException;
   /**
    * 修改跳转
    * @param mapVo
    * @return
    * @throws DataAccessException
    */
	HrGenreType queryByCode(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 修改
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
   String updateGenreType(Map<String, Object> mapVo)throws DataAccessException;
   /**
    * 删除培训类别
    * @param listVo
    * @return
    * @throws DataAccessException
    */
	String deleteGenreType(List<HrGenreType> listVo)throws DataAccessException;
	/**
	 * 查询数据
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
    String queryGenreType(Map<String, Object> page)throws DataAccessException;
    /**
     * 导入
     * @param mapVo
     * @return
     * @throws DataAccessException
     */
	String importDate(Map<String, Object> mapVo) throws DataAccessException;
	int queryUseGenreType(HrGenreType hrGenreType) throws DataAccessException;
}
