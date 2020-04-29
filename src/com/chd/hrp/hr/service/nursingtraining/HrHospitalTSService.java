package com.chd.hrp.hr.service.nursingtraining;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.nursingtraining.HrHospitalTS;

/**
 * 院内学习记录
 * @author Administrator
 *
 */
public interface HrHospitalTSService {
    /**
     * 增加院内学习记录
     * @param mapVo
     * @return
     */
	String addHospitalTS(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 跳转修改页面
     * @param mapVo
     * @return
     */
	HrHospitalTS queryByCodeHospitalTS(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 修改院内学习记录
	 * @param mapVo
	 * @return
	 */
	String updateHospitalTS(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 删除院内学习记录
	 * @param listVo
	 */
	String  deleteHospitalTS(List<HrHospitalTS> listVo)throws DataAccessException;
	/**
	 * 查询院内学习记录
	 * @param page
	 * @return
	 */
	String queryHospitalTS(Map<String, Object> page)throws DataAccessException;
	/**
	 * 查询左侧菜单
	 * @param mapVo
	 * @return
	 */
	String queryHospitalTSTree(Map<String, Object> mapVo)throws DataAccessException;

}
