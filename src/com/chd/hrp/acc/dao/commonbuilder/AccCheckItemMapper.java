/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.dao.commonbuilder;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccCheckEmpSet;
import com.chd.hrp.acc.entity.AccCheckItem;
import com.chd.hrp.acc.entity.AccCheckType;
import com.chd.hrp.sys.entity.DeptDict;

/**
* @Title. @Description.
* 核算项<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/
 

public interface AccCheckItemMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 核算项<BR> 添加AccCheckItem
	 * @param AccCheckItem entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAccCheckItem(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 核算项<BR> 批量添加AccCheckItem
	 * @param  AccCheckItem entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAccCheckItem(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 核算项<BR> 查询AccCheckItem分页
	 * @param  entityMap RowBounds
	 * @return List<AccCheckItem>
	 * @throws DataAccessException
	*/
	public List<AccCheckItem> queryAccCheckItem(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 核算项<BR> 查询AccCheckItem所有数据
	 * @param  entityMap
	 * @return List<AccCheckItem>
	 * @throws DataAccessException
	*/
	public List<AccCheckItem> queryAccCheckItem(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryAccCheckItemPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 系统核算项<BR> 部门查询AccCheckItem所有数据
	 * @param  entityMap
	 * @return List<AccCheckItem>
	 * @throws DataAccessException
	*/
	public List<AccCheckItem> queryAccCheckItemDept(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 系统核算项<BR> 职工查询AccCheckItem所有数据
	 * @param  entityMap
	 * @return List<AccCheckItem>
	 * @throws DataAccessException
	*/
	public List<AccCheckItem> queryAccCheckItemEmp(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 系统核算项<BR> 项目查询AccCheckItem所有数据
	 * @param  entityMap
	 * @return List<AccCheckItem>
	 * @throws DataAccessException
	*/
	public List<AccCheckItem> queryAccCheckItemProj(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 系统核算项<BR> 客户查询AccCheckItem所有数据
	 * @param  entityMap
	 * @return List<AccCheckItem>
	 * @throws DataAccessException
	*/
	public List<AccCheckItem> queryAccCheckItemCus(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 系统核算项<BR> 库房查询AccCheckItem所有数据
	 * @param  entityMap
	 * @return List<AccCheckItem>
	 * @throws DataAccessException
	*/
	public List<AccCheckItem> queryAccCheckItemStore(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 系统核算项<BR> 供应商查询AccCheckItem所有数据
	 * @param  entityMap
	 * @return List<AccCheckItem>
	 * @throws DataAccessException
	*/
	public List<AccCheckItem> queryAccCheckItemSup(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 系统核算项<BR> 资金来源查询AccCheckItem所有数据
	 * @param  entityMap
	 * @return List<AccCheckItem>
	 * @throws DataAccessException
	*/
	public List<AccCheckItem> queryAccCheckItemSource(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 系统核算项<BR> 单位查询AccCheckItem所有数据
	 * @param  entityMap
	 * @return List<AccCheckItem>
	 * @throws DataAccessException
	*/
	public List<AccCheckItem> queryAccCheckItemHos(Map<String, Object> entityMap) throws DataAccessException;

	
	/**
	 * @Description 
	 * 核算项<BR> 查询AccCheckItemByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccCheckItem queryAccCheckItemByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 根据核算类ID查询核算项的记录条数
	 * 核算项<BR> 查询queryAccCheckItemByCount
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int queryAccCheckItemByCount(Map<String,Object> entityMap)throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 核算项<BR> 删除AccCheckItem
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAccCheckItem(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 核算项<BR> 批量删除AccCheckItem
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAccCheckItem(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 核算项<BR> 更新AccCheckItem
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAccCheckItem(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 核算项<BR> 批量更新AccCheckItem
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchAccCheckItem(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 核算项<BR>继承 查询AccCheckItem数据
	 * @param  entityMap
	 * @return List<AccCheckItem>
	 * @throws DataAccessException
	*/
	public List<AccCheckItem> queryAccCheckItemByExtend(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * 查询 核算类 是否有所属的 核算项
	 * @param accCheckType
	 * @return
	 */
	public List<AccCheckItem> queryItemExtend(AccCheckType accCheckType) throws DataAccessException;
	
	/**
	 * 批量删除核算项根据ID判断辅助核算表是否存在
	 * @param accCheckType
	 * @return
	 */
	public int existsAccVouchCheckByForeignKey(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	/**
	 * 批量删除核算项根据ID判断辅助核算账表是否存在
	 * @param accCheckType
	 * @return
	 */
	public int existsAccLederCheckByForeignKey(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	/**
	 * 继承自定义核算项批量复制表
	 * @param accCheckType
	 * @return
	 */
	public int addBatchAccCheckItemBySelect(Map<String,Object> entityMap) throws DataAccessException;
	

	/**
	 * 获取所有自定义核算项对应字段
	 * @param entityMap
	 * @return String
	 */
	public String getAccZCheckItemColumns(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * 获取所有自定义核算项对应名称
	 * @param entityMap
	 * @return String
	 */
	public String getAccZCheckItemNames(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * 查询编码是否重复
	 * @param utilMap
	 * @return
	 * @throws DataAccessException
	 */
	public int existsCodeUpdate(Map<String, Object> utilMap) throws DataAccessException;
	/**
	 * 添加时查询 名称是否已占用
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */ 
	public int existsNameAdd(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 根据核算编码查询数据
	 * @param mapVo
	 * @return
	 */
	public List<Map<String, Object>> queryAccCheckItemsByCode(
			Map<String, Object> mapVo);
	/**
	 * 根据核算类型和名称查询数据
	 * @param mapVo
	 * @return
	 */
	public List<Map<String, Object>> queryAccCheckItemsByTypeAndName(
			Map<String, Object> mapVo);
	
	public List<?> queryAccCheckEmpSet(Map<String,Object> entityMap) throws DataAccessException;
	public int add(Map<String,Object> entityMap)throws DataAccessException;
	public int update(Map<String,Object> entityMap)throws DataAccessException;
	public int deleteAccCheckEmpSet(List<Map<String, Object>> entityMap)throws DataAccessException;
	public AccCheckEmpSet queryAccCheckEmpSetByCode(Map<String,Object> entityMap)throws DataAccessException;
	public int existsAccCheckItemEmpPayByCode(List<Map<String, Object>> entityMap) throws DataAccessException;
	
}
