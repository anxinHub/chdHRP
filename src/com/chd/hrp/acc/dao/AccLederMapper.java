/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccLeder;
 
/**
* @Title. @Description.
* 财务账表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccLederMapper extends SqlMapper{
	
	
	/**
	 * @Description 
	 * 财务账表<BR> 添加
	 * @param  entityMap RowBounds
	 * @return List<AccLeder>
	 * @throws DataAccessException
	*/
	public int addAccLeder(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 财务账表<BR> 批量添加
	 * @param  entityMap RowBounds
	 * @return List<AccLeder>
	 * @throws DataAccessException
	*/
	public int addBatchAccLeder(List<AccLeder> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 财务账表<BR> 查询AccLeder分页
	 * @param  entityMap RowBounds
	 * @return List<AccLeder>
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryAccLeder(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 财务账表<BR> 查询AccLeder所有数据
	 * @param  entityMap
	 * @return List<AccLeder>
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryAccLeder(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 财务账表<BR> 查询AccLederByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccLeder queryAccLederByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 财务账表<BR> 查询AccLederByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public List<AccLeder> queryAccLederBySubjCode(Map<String,Object> entityMap)throws DataAccessException;
	
		
	public int updateBatchAccLederAccounting(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public int updateBatchAccLeder(Map<String, Object> entityMap)throws DataAccessException;
	
	public int updateAccLeder(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询科室下拉列表
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryDeptDict(Map<String,Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	/**
	 * @Description 
	 * 查询职工下拉列表
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryEmp(Map<String,Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	/**
	 * @Description 
	 * 查询项目下拉列表
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryProjDictDict(Map<String,Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	/**
	 * @Description 
	 * 查询库房下拉列表
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryStoreDictDict(Map<String,Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	/**
	 * @Description 
	 * 查询供应商下拉列表
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> querySupDictDict(Map<String,Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	/**
	 * @Description 
	 * 查询客户下拉列表
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryCusDict(Map<String,Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	/**
	 * @Description 
	 * 查询资金来源下拉列表
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> querySourceDict(Map<String,Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	
	public List<Map<String, Object>> queryHosInfo(Map<String,Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	/**
	 * @Description 
	 * 查询自定义下拉列表
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryCheckItem(Map<String,Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	/**
	 * @Description 
	 * 查询财务系统启用时间
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public List<AccLeder> queryModByModCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 查询初始账辅助核算动态表头
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public List<AccLeder> queryGridTitle(Map<String,Object> entityMap)throws DataAccessException;

	public int deleteAccLeder(Map<String, Object> entityMap)throws DataAccessException;
	
	
	public int deleteAccLederJz(Map<String, Object> entityMap)throws DataAccessException;
	//查询初始账页面科目所挂的辅助账
	public List<AccLeder> queryAccLederCheckList(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public List<AccLeder> queryAccLederCheckList(Map<String,Object> entityMap) throws DataAccessException;
	//查询试算平衡
	public List<AccLeder> queryAccLederIndex(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryAccLederIndex1(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 
	 * 初始账<BR> 查询科目挂的辅助核算项
	 * @param  entityMap
	 * @return List<AccSubj>
	 * @throws DataAccessException
	*/
	public Map<String, Object> queryAccCheckItemList(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 初始账<BR> 动态查询科目挂的辅助核算项是否存在
	 * @param  entityMap
	 * @return List<AccSubj>
	 * @throws DataAccessException
	*/
	public Map<String, Object> queryAccCheckItem(Map<String,Object> entityMap) throws DataAccessException;
	
	public int deleteBatchAccLeder(List<Map<String, Object>> entityList) throws DataAccessException;
	
	public Map<String, Object> verifyAccLederIndex1(Map<String,Object> entityMap) throws DataAccessException;
	
	public Map<String, Object> verifyAccLederIndexFirm(Map<String,Object> entityMap) throws DataAccessException;
	
	public Map<String, Object> verifyAccLederIndex2(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>>verifyAccLederIndex3(Map<String,Object> entityMap) throws DataAccessException;
	
	public Integer queryAccLaderByImp(Map<String,Object> entityMap) throws DataAccessException;
	
	public int addBatchAccLederByCheck(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * 查询科目所挂辅助核算项表
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryCheckItemTable(Map<String, Object> mapVo) throws DataAccessException;
	
	//以下方法   用于增加子级科目时 往账表里面添加数据
	public int addAccLederByCopy(Map<String,Object> entityMap)throws DataAccessException;
	public int deleteAccLederByCopy(Map<String, Object> entityMap)throws DataAccessException;

	public List<Map<String,Object>> queryAccLederByAccYear(Map<String, Object> mapVo);

	public List<Map<String, Object>> queryAccSubjByYear(Map<String, Object> mapVo);

	public List<Map<String, Object>> queryAccCurByYear(Map<String, Object> mapVo);

	public int addAccLederBatch(List<Map<String, Object>> saveList);

	public List<Map<String, Object>> queryCheckData(Map map);

	public Map<String, Object> queryTableName(Map<String, Object> mapVo);

	public List<Map<String, Object>> queryAccLederCheckBySubjCode(Map<String, Object> entityMap);

	public Map<String, Object> querySysModStart(Map<String, Object> entityMap);

	public List<Map<String, Object>> queryAccLederCheckByYear(Map<String, Object> entityMap);

	//查询凭证是否记账
	public Integer querySubjLederCheckCount(Map<String, Object> entityMap); 
	
	/**
	 * 辅助核算明细打印用
	 */
	public List<Map<String, Object>> queryAccLederCheckListPrint(Map<String, Object> paraMap); 
}
