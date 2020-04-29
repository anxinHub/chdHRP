package com.chd.hrp.hr.service.training.setting;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.training.setting.HrNoticeMode;

public interface HrNoticeModeService {
    /**
     * 添加
     * @param mapVo
     * @return
     * @throws DataAccessException
     */
	String addNoticeMode(Map<String, Object> mapVo) throws DataAccessException;
   /**
    * 修改跳转
    * @param mapVo
    * @return
    * @throws DataAccessException
    */
	HrNoticeMode queryByCode(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 修改
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
   String updateNoticeMode(Map<String, Object> mapVo)throws DataAccessException;
   /**
    * 删除
    * @param listVo
    * @return
    * @throws DataAccessException
    */
	String deleteNoticeMode(List<HrNoticeMode> listVo)throws DataAccessException;
	/**
	 * 查询数据
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
    String queryNoticeMode(Map<String, Object> page)throws DataAccessException;
    /**
     * 导入
     * @param mapVo
     * @return
     * @throws DataAccessException
     */
	String importDate(Map<String, Object> mapVo)throws DataAccessException;
	int queryUseNoticeMode(HrNoticeMode hrNoticeMode)throws DataAccessException;


}
