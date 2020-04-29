/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.dao.protocol;
import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.base.util.ChdJson;
import com.chd.hrp.mat.entity.MatProtocolMain;
/**
 * 
 * @Description:
 * state：0：新建 1：审核 2：确认
 * @Table:
 * MAT_PROTOCOL_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MatProtocolMainMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加state：0：新建 1：审核 2：确认<BR>
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addMatProtocolMain(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加state：0：新建 1：审核 2：确认<BR>
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchMatProtocolMain(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新state：0：新建 1：审核 2：确认<BR>
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateMatProtocolMain(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新state：0：新建 1：审核 2：确认<BR>
	 * @param  entityMap
	 * @return MatProtocolMain
	 * @throws DataAccessException
	*/
	public int updateBatchMatProtocolMain(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除state：0：新建 1：审核 2：确认<BR>
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMatProtocolMain(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除state：0：新建 1：审核 2：确认<BR>
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchMatProtocolMain(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集state：0：新建 1：审核 2：确认<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<MatProtocolMain> queryMatProtocolMain(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集state：0：新建 1：审核 2：确认 <BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<MatProtocolMain> queryMatProtocolMain(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取state：0：新建 1：审核 2：确认<BR>
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return MatProtocolMain
	 * @throws DataAccessException
	*/
	public MatProtocolMain queryMatProtocolMainByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取state：0：新建 1：审核 2：确认<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MatProtocolMain
	 * @throws DataAccessException
	*/
	public MatProtocolMain queryMatProtocolMainByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * 据协议类型Id 查询其开始年月 、协议前缀
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String,Object> queryMatProtocolTypePre(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * grid明细编辑  文档类别下拉框
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryMatFileType(Map<String, Object> mapVo,RowBounds rowBounds) throws DataAccessException;
	/**
	 * grid明细编辑  管理科室下拉框
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 */
	public List<Map<String,Object>> queryManaDept(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	/**
	 * grid明细编辑  管理员下拉框
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 */
	public List<Map<String,Object>> queryManaEmp(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	/**
	 * 物资材料明细列表
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatInvDetail(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 物资材料明细列表
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatInvDetail(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * 根据输入的 协议名称 查询采购协议（判断协议名称是否已存在）
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<MatProtocolMain> queryMatProtocolMainByID(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 根据ID 多表联合查询采购协议（修改页面回值用）
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryMatProtocolMainUnit(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 协议明细  查询（不分页）
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatProtocolDetail(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 协议明细  查询（分页）
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatProtocolDetail(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * 协议文档信息（分页）
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatProtocolFile(Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	/**
	 * 协议文档信息（不分页）
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatProtocolFile(Map<String, Object> entityMap)throws DataAccessException;
	/**
	 * 查询协议主表的当前protocol_id
	 * @return
	 */
	public Long qureyCurrval() throws DataAccessException;
	/**
	 * 审核、消审、确认、取消确认、终止、取消终止
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public int updateState(List<Map<String, Object>> listVo) throws DataAccessException;
	/**
	 * 多表联合查询 采购协议详细信息(不分页)
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatProtocolMainInfo(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 多表联合查询 采购协议详细信息(分页)
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatProtocolMainInfo(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	/**
	 * 多表联合查询 采购协议详细信息(分页)
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatProtocolMainPur(Map<String, Object> entityMap) throws DataAccessException;
	
	
	public Long existsMatProtocolMainStateBatch(List<Map<String, Object>> listVo) throws DataAccessException;
	
}
