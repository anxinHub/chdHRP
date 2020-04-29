package com.chd.hrp.hr.service.sysstruc;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.sysstruc.HrStoreTabStruc;


/**
 * 档案库数据集配置
 * @author Administrator
 *
 */
public interface HrStoreTabStrucService {
    /**
     * 添加档案库数据集配置
     * @param entityMapList
     * @return
     */
	String addBatch(List<Map<String,Object>> entityMapList)throws DataAccessException;
   /**
    * 查询档案库数据集配置
    * @param entityMap
    * @return
    */
	String query(Map<String, Object> entityMap)throws DataAccessException;
   /**
    * 删除档案库数据集配置
    * @param listVo
    * @return
    */
	String deleteBatch(List<HrStoreTabStruc> listVo)throws DataAccessException;
    /**
     * 跳转修改档案库数据集配置
     * @param page
     * @return
     */
	String queryStoreTabStrucByCode(Map<String, Object> page)throws DataAccessException;
    /**
     * 查询左侧树形
     * @param mapVo
     * @return
     */
	String queryStoreTabStrucTree(Map<String, Object> mapVo)throws DataAccessException;
   /**
    * 查询树形
    * @param mapVo
    * @return
    */
	List<?> queryStoreTabStrucByTree(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 查询档案库数据集配置
     * @param mapVo
     * @return
     */
	String queryHrHosTabStruc(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 新增和修改档案库数据集配置
     * @param mapVo
     * @return
     */
	String addOrUpdateHrColStruc(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 打印
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String,Object>> queryHrStoreTabStrucByPrint(Map<String, Object> mapVo)throws DataAccessException;
	
	/**
	 * 导入
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String importExcel(Map<String, Object> entityMap) throws DataAccessException;
	
	//批量添加
	String addStoreTabBatch(Map<String, Object> mapVo);

}
