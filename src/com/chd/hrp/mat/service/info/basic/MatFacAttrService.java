
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.mat.service.info.basic;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.ass.entity.dict.AssFacAttr;
/**
 * 
 * @Description:
 * 050115 生产厂商附属表
 * @Table:
 * ASS_FAC_ATTR
 * @Author: linuxxu
 * @email:  linuxxu@s-chd.com
 * @Version: 1.0
 */
 


public interface MatFacAttrService {

	/**
	 * @Description 
	 * 添加050115 生产厂商附属表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addMatFacAttr(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加050115 生产厂商附属表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBatchMatFacAttr(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新050115 生产厂商附属表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateMatFacAttr(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新050115 生产厂商附属表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchMatFacAttr(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 删除050115 生产厂商附属表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteMatFacAttr(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除050115 生产厂商附属表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBatchMatFacAttr(List<Map<String, Object>> entityMap)throws DataAccessException;
	

	/**
	 * @Description 
	 * 查询结果集050115 生产厂商附属表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMatFacAttr(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对象050115 生产厂商附属表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public AssFacAttr queryMatFacAttrByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public String init(Map<String,Object> entityMap)throws DataAccessException;

	
	/**
	 * @Description 
	 * 查询生产厂商银行信息表<BR> 
	 * @param  entityMap
	 * @return List<HosSupBank>
	 * @throws DataAccessException
	*/
	public String queryHosFacBank(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 添加银行信息<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addHosFacBank(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * 根据 生产厂商编码 查询 生产厂ID
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Long queryFacIdByCode(Map<String, Object> mapVo)  throws DataAccessException;
	/**
	 * 生产厂商分类 树 数据查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryHosFacTypeByMenu(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 查询 生产厂商名称是否存在 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryFacExists(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 查询 生产厂商分类编码是否存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryFacTypeExist(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 查询 所属系统模块编码是否存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryModExist(Map<String, Object> mapVo) throws DataAccessException;
}
