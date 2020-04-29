package com.chd.hrp.hr.service.organize;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.orangnize.HosStation;





public interface HrStationService {
    /**
     * 添加岗位设立
     * @param mapVo
     * @return
     */
	String addStation(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 跳转修改页面查询
     * @param mapVo
     * @return
     */
	HosStation queryByCodeStation(Map<String, Object> mapVo)throws DataAccessException;
   /**
    * 修改岗位设立
    * @param mapVo
    * @return
    */
	String updateStation(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 删除岗位设立
	 * @param listVo
	 * @return
	 */
    String deleteBatchStation(List<HosStation> listVo)throws DataAccessException;
    /**
     * 查询所有数据
     * @param page
     * @return
     */
	String queryHrStation(Map<String, Object> page)throws DataAccessException;
	/**
	 * 查询数据(左侧菜单) 岗位设立
	 * @param mapVo
	 * @return
	 */
	String queryStationTree(Map<String, Object> mapVo)throws DataAccessException;

}
