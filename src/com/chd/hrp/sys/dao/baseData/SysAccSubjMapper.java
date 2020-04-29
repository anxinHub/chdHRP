package com.chd.hrp.sys.dao.baseData;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccCheckType;
import com.chd.hrp.acc.entity.AccCur;
import com.chd.hrp.acc.entity.AccDeptAttr;
import com.chd.hrp.acc.entity.AccSubj;
import com.chd.hrp.acc.entity.AccSubjNature;
import com.chd.hrp.acc.entity.AccSubjType;
import com.chd.hrp.acc.entity.AccVouchType;
import com.chd.hrp.acc.entity.HrpAccSelect;
import com.chd.hrp.sys.entity.Rules;

public interface SysAccSubjMapper extends SqlMapper {

	
	/**
	 * 查询会计科目
	 * @param mapVo
	 * @return
	 */
	public List<Map<String, Object>> queryAccSubj(Map<String, Object> mapVo);
	public List<Map<String, Object>> queryAccSubj(Map<String, Object> mapVo,RowBounds rowBounds);
	/**
	 * 打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAccSubjPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 删除字典，判断业务表是否使用，调用存储过程
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public String querySysDictDelCheck(Map<String, Object> map) throws DataAccessException;
	/**
	 * @Description 
	 * 会计科目<BR> 批量删除AccSubj
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAccSubj(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * 删除操作，根据父科目查询是否有子科目，没有就修改为末级
	 * @param entityMap
	 * @return
	 */
	public int updateSubjIsLastBySuperSubjCode(Map<String, Object> entityMap);
	/**
	 * 查询编码规则
	 * @param mapVo
	 * @return
	 */
	public Rules queryRulesByCode(Map<String, Object> mapVo);
	/**
	 * 查询添加编码是否重复
	 * @param utilMap
	 * @return
	 */
	public int existsSysCodeNameByAdd(Map<String, Object> utilMap);
	/**
	 * @Description 
	 * 会计科目<BR> 查询AccSubjByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccSubj queryAccSubjByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * 根据父级编码查询科目全称
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String querySubjBySuperCode(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * @Description 
	 * 会计科目<BR> 添加AccSubj
	 * @param AccSubj entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAccSubj(Map<String,Object> entityMap)throws DataAccessException;
	public Map<String,Object> prcUpdateSubjInfoSigle(Map<String,Object> entityMap) throws  DataAccessException;
	/**
	 * 添加操作，更新父级是否末级
	 * @param entityMap
	 * @return
	 */
	public int updateSubjIsLastBySubjCode(Map<String, Object> entityMap);
	/**
	 * 判断辅助核算是否使用
	 * @param entityMap
	 * @return
	 */
	public int existsVouchLederBySubjCheck(Map<String, Object> entityMap);
	public List<AccCheckType> queryCheckTypeBySubjId(Map<String,Object> entityMap) throws DataAccessException;
	public AccCheckType queryCheckType(Map<String,Object> entityMap)throws DataAccessException;
	public AccCheckType queryCheckColumn(Map<String,Object> entityMap)throws DataAccessException;
	//修改判断编码名称重复
    public int existsSysCodeNameByUpdate(Map<String, Object> entityMap);
    /**
	 * @Description 
	 * 会计科目<BR> 更新AccSubj
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAccSubj(Map<String,Object> entityMap)throws DataAccessException;
	
	public Map<String,Object> prcUpdateSubjInfoALL(Map<String,Object> entityMap) throws  DataAccessException;
	
	public Map<String,Object> prcUpdateSubjInfo(Map<String,Object> entityMap) throws  DataAccessException;
	 
	/**
	 * @Description 
	 * 会计科目<BR> 导入时查询科目编码是否存在
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccSubj queryAccSubjCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 币种<BR> 查询AccCurByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccCur queryAccCurByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 科目类别<BR> 查询AccSubjTypeByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccSubjType queryAccSubjTypeByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科目性质<BR> 查询AccSubjNatureByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccSubjNature queryAccSubjNatureByCode(Map<String,Object> entityMap)throws DataAccessException;
	

	/**
	 * @Description 
	 * 凭证类型<BR> 查询AccVouchTypeByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccVouchType queryAccVouchTypeByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public AccDeptAttr queryAccOutDeptByName(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 核算类<BR> 查询AccCheckTypeByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccCheckType queryAccCheckTypeByName(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 会计科目<BR> 批量添加AccSubj
	 * @param AccSubj entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAccSubj(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public List<AccSubj> queryDistinctAccSubjList(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 会计科目<BR> 批量更新AccSubj
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchAccSubj(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public AccSubj queryAccSubj_id(Map<String, Object> mapVo)throws DataAccessException;
	
	//按行业性质导会计科目
	public int addBatchAccSubjByNatureCode(Map<String, Object> entityMap);
	
	//按医院历史年度、按集团导会计科目
	public int addBatchAccSubjByAccYear(Map<String, Object> map)throws DataAccessException;
	
	//账簿查询自定义辅助账页面科目选择
	public List<AccSubj> queryGroupAccSubjByMenu(Map<String, Object> entityMap);

}
