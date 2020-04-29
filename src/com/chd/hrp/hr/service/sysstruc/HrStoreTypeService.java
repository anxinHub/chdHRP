package com.chd.hrp.hr.service.sysstruc;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.sysstruc.HrStoreType;

/**
 * 档案库分类构建
 * @author Administrator
 *
 */
public interface HrStoreTypeService {
    /**
     * 新增档案库分类
     * @param entityMap
     * @return
     * @throws DataAccessException
     */
	String add(Map<String, Object> entityMap)throws DataAccessException;
   /**
    * 查询档案库分类
    * @param entityMap
    * @return
    * @throws DataAccessException
    */
	String query(Map<String, Object> entityMap)throws DataAccessException;
   /**
    * 修改档案库分类
    * @param entityMap
    * @return
    * @throws DataAccessException
    */
	String update(Map<String, Object> entityMap)throws DataAccessException;
    /**
     * 修改跳转档案库分类
     * @param entityMap
     * @return
     * @throws DataAccessException
     */
	HrStoreType queryByCode(Map<String, Object> entityMap)throws DataAccessException;
    /**
     * 删除档案库分类
     * @param listVo
     * @return
     * @throws DataAccessException
     */
	String deleteBatchHrStoreType(List<HrStoreType> listVo)throws DataAccessException;
	/**
	 * 导入数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String importDate(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查人员档案库分类(DIC_STORE_TYPE)
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String, Object>> queryDicStoreType(Map<String, Object> map) throws DataAccessException;

}
