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
import com.chd.hrp.acc.entity.AccSubj;
 
/**
* @Title. @Description.
* 会计科目<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccSubjMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 会计科目<BR> 添加AccSubj
	 * @param AccSubj entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAccSubj(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 会计科目<BR> 批量添加AccSubj
	 * @param AccSubj entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAccSubj(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 会计科目<BR> 查询AccSubj分页
	 * @param  entityMap RowBounds
	 * @return List<AccSubj>
	 * @throws DataAccessException
	*/
	public List<AccSubj> queryAccSubj(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	
	/**
	 * @Description 
	 * 会计科目<BR> 查询AccSubj所有数据
	 * @param  entityMap
	 * @return List<AccSubj>
	 * @throws DataAccessException
	*/
	public List<AccSubj> queryAccSubj(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryAccSubjPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 会计科目<BR> 查询AccSubjByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccSubj queryAccSubjByCode(Map<String,Object> entityMap)throws DataAccessException;
	 
	/**
	 * @Description 
	 * 会计科目<BR> 删除AccSubj
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAccSubj(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 会计科目<BR> 批量删除AccSubj
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAccSubj(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 会计科目<BR> 更新AccSubj
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAccSubj(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 会计科目<BR> 批量更新AccSubj
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchAccSubj(List<Map<String, Object>> entityMap)throws DataAccessException;
	public int updateBatchGroupAccSubj(List<Map<String, Object>> entityMap)throws DataAccessException;
	public int updateGroupSubj(List<Map<String, Object>> entityMap)throws DataAccessException;

	public List<AccSubj> queryAccSubjList(Map<String,Object> entityMap) throws DataAccessException;
	
	public AccSubj queryAccSubj_id(Map<String, Object> mapVo)throws DataAccessException;

	//public List<?> queryAccSubjByVouch(Map<String, Object> entityMap);
	
	//账簿查询自定义辅助账页面科目选择
	public List<AccSubj> queryAccSubjByMenu(Map<String, Object> entityMap);
	
	//检查科目是否挂了辅助核算
	public List<AccSubj> queryAccSubjCheck(Map<String, Object> mapVo)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询 科目是否是辅助核算或者是现金流量 add by alfred
	*/
	public AccSubj queryAccSubjByCashOrCheck(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询 科目挂的辅助核算信息 add by alfred 在 vouch中调用此方法
	*/
	public List<AccSubj> queryAccSubjWithCheckType(Map<String,Object> entityMap) throws DataAccessException;
	
	
	//根据父级编码查询科目全称
	public String querySubjBySuperCode(Map<String, Object> mapVo) throws DataAccessException;
	
	
	//判断辅助核算是否使用
	public int existsVouchLederBySubjCheck(Map<String, Object> entityMap);
	
	//删除操作，判断是否为末级
	public List<String> existsSubjByIsLast(Map<String, Object> entityMap);
	
	//删除操作，根据父科目查询是否有子科目，没有就修改为末级
	public int updateSubjIsLastBySuperSubjCode(Map<String, Object> entityMap);
	
	//添加操作，更新父级是否末级
	public int updateSubjIsLastBySubjCode(Map<String, Object> entityMap);
	
	//按行业性质导会计科目
	public int addBatchAccSubjByNatureCode(Map<String, Object> entityMap);
	
	//按医院历史年度、按集团导会计科目
	public int addBatchAccSubjByAccYear(Map<String, Object> map)throws DataAccessException;

	/**
	 * @Description 
	 * 会计科目<BR> 导入时查询科目编码是否存在
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccSubj queryAccSubjCode(Map<String,Object> entityMap)throws DataAccessException;

	public List<AccSubj> queryDistinctAccSubjList(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 会计科目<BR> 查询科目性质编码
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccSubj queryAccSubjNature(Map<String, Object> mapVo)throws DataAccessException;
	
	public Map<String,Object> prcUpdateSubjInfoALL(Map<String,Object> entityMap) throws  DataAccessException;
	
	public Map<String,Object> prcUpdateSubjInfoSigle(Map<String,Object> entityMap) throws  DataAccessException;
	
	public Map<String,Object> prcUpdateSubjInfo(Map<String,Object> entityMap) throws  DataAccessException;
	 
	// 账簿中通过subj_code 查询subj_id   用于 通过科目选择器选择多个科目  
	public String queryAccSubjByCodeSearchId(Map<String,Object> entityMap) throws DataAccessException;
	//集团同步会计科目
	public int querySynGroupSubjCode(Map<String, Object> entityMap)throws DataAccessException;
	//集团同步会计科目
	public int addBatchAccSynGroupSubj(Map<String, Object> entityMap);
	public  List<Map<String, Object>> queryGroupSubjCode(Map<String,Object> entityMap) throws DataAccessException;
	//添加末级会计科目时，查询该上级会计科目有无下级科目
	public Map<String,Object> queryAccSubjByLast(Map<String,Object> entityMap)throws DataAccessException;
	public  int  queryAccSubjByCount(Map<String,Object> entityMap)throws DataAccessException;
	public Map<String,Object> queryAccSubjBySuperId(Map<String,Object> entityMap)throws DataAccessException;
	
	//校验期间是否存在
	public int existsAccYearMonth(Map<String, Object> map) throws DataAccessException;
	//查询科目用于导入
	public  List<Map<String, Object>> queryAccSubjListForImport(Map<String,Object> entityMap) throws DataAccessException;
	//查询科目类别用于导入
	public  List<Map<String, Object>> queryAccSubjTypeListForImport(Map<String,Object> entityMap) throws DataAccessException;
	//查询科目性质用于导入
	public  List<Map<String, Object>> queryAccSubjNatureListForImport(Map<String,Object> entityMap) throws DataAccessException;
	//查询币种用于导入
	public  List<Map<String, Object>> queryAccCurListForImport(Map<String,Object> entityMap) throws DataAccessException;
	//查询凭证类型用于导入
	public  List<Map<String, Object>> queryAccVouchTypeListForImport(Map<String,Object> entityMap) throws DataAccessException;
	//查询支出性质用于导入
	public  List<Map<String, Object>> queryAccDeptOutListForImport(Map<String,Object> entityMap) throws DataAccessException;
	//查询辅助核算用于导入
	public  List<Map<String, Object>> queryAccCheckListForImport(Map<String,Object> entityMap) throws DataAccessException;
	//查询核算分类--部门分类用于导入
	public  List<Map<String, Object>> queryHosDeptKindListForImport(Map<String,Object> entityMap) throws DataAccessException;
	//查询核算分类--职工分类用于导入
	public  List<Map<String, Object>> queryHosEmpKindListForImport(Map<String,Object> entityMap) throws DataAccessException;
	//查询核算分类--项目分类用于导入
	public  List<Map<String, Object>> queryHosProjTypeListForImport(Map<String,Object> entityMap) throws DataAccessException;
	//查询核算分类--库房分类用于导入
	public  List<Map<String, Object>> queryHosStoreTypeListForImport(Map<String,Object> entityMap) throws DataAccessException;
	//查询核算分类--客户分类用于导入
	public  List<Map<String, Object>> queryHosCusTypeListForImport(Map<String,Object> entityMap) throws DataAccessException;
	//查询核算分类--供应商分类用于导入
	public  List<Map<String, Object>> queryHosSupTypeListForImport(Map<String,Object> entityMap) throws DataAccessException;
	//查询核算分类--资金来源分类用于导入
	public  List<Map<String, Object>> queryHosSourceTypeListForImport(Map<String,Object> entityMap) throws DataAccessException;
	//查询核算分类--自定义辅助核算分类用于导入
	public  List<Map<String, Object>> queryAccCheckItemTypeListForImport(Map<String,Object> entityMap) throws DataAccessException;
	//批量添加会计科目导入专用
	public  int insertAccSubjForImport(List<Map<String,Object>> entityList) throws DataAccessException;
	//修改上级科目为非末级导入专用
	public  int updateAccSubjForImport(Map<String,Object> entityMap) throws DataAccessException;
	
	public  List<Map<String, Object>> queryAccSubjByVouch(Map<String,Object> entityMap) throws DataAccessException;
}
