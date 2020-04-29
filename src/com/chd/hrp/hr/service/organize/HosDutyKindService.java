package com.chd.hrp.hr.service.organize;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.orangnize.HosDutyKind;


public interface HosDutyKindService {
	
	/**
     * 添加岗位分类
     * @param mapVo
     * @return
     */
	String add(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 跳转修改页面查询岗位信息
	 * @param mapVo
	 * @return
	 */
	HosDutyKind queryByCode(Map<String, Object> mapVo)throws DataAccessException;
	
	/**
	 * 更新岗位信息
	 * @param mapVo
	 * @return
	 */
	String updateHrStationKind(Map<String, Object> mapVo)throws DataAccessException;
	
	/**
     * 删除岗位分类
     * @param listVo
     * @return
     */
	String deleteBatchStationKind(List<HosDutyKind> listVo)throws DataAccessException;
    
	
	
	/**
	 * 查询所有岗位信息
	 * @param page
	 * @return
	 */
	String queryHrStationKind(Map<String, Object> page)throws DataAccessException;
	/**
	 * 查询左侧菜单
	 * @param mapVo
	 * @return
	 */
	String queryHosEmpKindTree(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 导入数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String importDate(Map<String, Object> entityMap) throws DataAccessException;
	List<HosDutyKind> queryListDutyKind(List<HosDutyKind> listVo) throws DataAccessException;
	 /**
		 * 打印
		 * 
		 * @param page
		 * @return
		 * @throws DataAccessException
		 */
	List<Map<String,Object>> queryDutyKindByPrint(Map<String, Object> page)throws DataAccessException;
}
