package com.chd.hrp.hr.service.training.setting;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.training.setting.HrGenreMode;




public interface HrGenreModeService {
    /**
     * 添加
     * @param mapVo
     * @return
     * @throws DataAccessException
     */
	String addGenreMode(Map<String, Object> mapVo) throws DataAccessException;
   /**
    * 修改跳转
    * @param mapVo
    * @return
    * @throws DataAccessException
    */
	HrGenreMode queryByCode(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 修改
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
   String updateGenreMode(Map<String, Object> mapVo)throws DataAccessException;
   /**
    * 删除培训方式
    * @param listVo
    * @return
    * @throws DataAccessException
    */
	String deleteGenreMode(List<HrGenreMode> listVo)throws DataAccessException;
	/**
	 * 查询数据
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
    String queryGenreMode(Map<String, Object> page)throws DataAccessException;
    /**
     * 导入
     * @param mapVo
     * @return
     * @throws DataAccessException
     */
	String importDate(Map<String, Object> mapVo)throws DataAccessException;
	int queryUseGenreMode(HrGenreMode hrgenreMode)throws DataAccessException;
}
