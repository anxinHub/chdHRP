package com.chd.hrp.med.dao.base;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface MedCommonMapper extends SqlMapper {

	/**
	 * @Description 
	 * 获取药品分类编码规则<BR> 
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String getMedHosRules(Map<String, Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 获取参数table_name表中c_max字段的最大值<BR> 
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String getMedMaxNo(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 获取物流系统参数<BR> 
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String getMatAccPara(Map<String, Object> entityMap)throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 获取物流系统参数<BR> 
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String getMedAccPara(Map<String, Object> entityMap)throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 获取参数table_name表中所有的c_code、c_name与参数value相等的数据<BR> 
	 * @param entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> existsMedCodeName(Map<String, Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 材料字典列表
	 * @param entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryMedInvList(Map<String, Object> entityMap) throws DataAccessException;
	public List<?> queryMedInvList(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 材料字典列表  科室申领 
	 * @param entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryMedInvListApply(Map<String, Object> entityMap) throws DataAccessException;
	public List<?> queryMedInvListApply(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	
	/**
	 * @Description 
	 * 材料字典列表(专购品入库)
	 * @param entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryMedInvListSpecial(Map<String, Object> entityMap) throws DataAccessException;
	public List<?> queryMedInvListSpecial(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 材料字典列表  (入库)
	 * @param entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryMedInvListIn(Map<String, Object> entityMap) throws DataAccessException;
	public List<?> queryMedInvListIn(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 材料字典列表  (期初入库)
	 * @param entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryMedInvListInitial(Map<String, Object> entityMap) throws DataAccessException;
	public List<?> queryMedInvListInitial(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 材料字典列表(没有材料库存,不带仓库)
	 * @param entityMap
	 * @return
	 */
	public List<?> queryMedInvListNotStore(Map<String, Object> entityMap) throws DataAccessException;
	public List<?> queryMedInvListNotStore(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
	//代销材料库存信息用于添加业务数据<BR>
	public List<?> queryMedAffiInvList(Map<String, Object> entityMap) throws DataAccessException;
	public List<?> queryMedAffiInvList(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 普通材料库存信息用于添加业务数据<BR>    库存盘点 
	 * @param entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryMedStockInvList(Map<String, Object> entityMap)throws DataAccessException;
	public List<?> queryMedStockInvList(Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	
	/**
	 * @Description 
	 * 普通材料库存信息用于添加业务数据<BR>  材料调拨 
	 * @param entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryMedStockInvListTran(Map<String, Object> entityMap)throws DataAccessException;
	public List<?> queryMedStockInvListTran(Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	 
	/**
	 * @Description 
	 * 普通材料库存信息用于添加业务数据<BR>  材料出库 
	 * @param entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryMedStockInvListOut(Map<String, Object> entityMap)throws DataAccessException;
	public List<?> queryMedStockInvListOut(Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 普通材料库存信息用于添加业务数据<BR>  材料退货
	 * @param entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryMedStockInvListBack(Map<String, Object> entityMap)throws DataAccessException;
	public List<?> queryMedStockInvListBack(Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	
	
	
	/**
	 * @Description 
	 * 普通材料库存明细信息用于添加业务数据<BR> 
	 * @param entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryMedStockInvDetailList(Map<String, Object> entityMap)throws DataAccessException;
	public List<?> queryMedStockInvDetailList(Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;

	/**
	 * 代销材料获取材料列表
	 * @param entityMap
	 * @param rowBounds 
	 * @return
	 * @throws DataAccessException
	 */
	public List<?> queryMedAffiOutInvList(Map<String, Object> entityMap)  throws DataAccessException;
	public List<?> queryMedAffiOutInvList(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 代销材料获取材料列表
	 * @param entityMap
	 * @param rowBounds 
	 * @return
	 * @throws DataAccessException
	 */
	public List<?> queryMedAffiOutDetailInvList(Map<String, Object> entityMap)  throws DataAccessException;
	public List<?> queryMedAffiOutDetailInvList(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 判断材料库存是否充足<BR> 
	 * @param entityList
	 * @return String
	 * @throws DataAccessException
	 */
	public String existsMedStockInvIsEnough(List<Map<String, Object>> entityList)throws DataAccessException;

	/**
	 * @Description 
	 * 退库判断科室领用数量是否充足<BR> 
	 * @param entityList
	 * @return String
	 * @throws DataAccessException
	 */
	public String existsMedStockInvIsAppEnough(List<Map<String, Object>> entityList)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室需求材料列表<BR> 
	 * @param entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryMedInvListDept(Map<String, Object> entityMap)throws DataAccessException;
	public List<?> queryMedInvListDept(Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	/**
	 * 判断代销及时库存是否充足
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public String existsMedAffiInvIsEnough(List<Map<String, Object>> entityList) throws DataAccessException;
	/**
	 * @Description 
	 * 物流系统参数<BR> 
	 * @param entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMedParas(Map<String, Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 判断日期所在期间是否存在，0为否、其他值为是<BR> 
	 * @param  Map<String, Object>
	 * @return int
	 * @throws DataAccessException
	*/
	public int existsAccYearMonthCheck(Map<String, Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 判断日期所在期间是否存在并且在启用日期之后，0为否、其他值为是<BR> 
	 * @param  Map<String, Object>
	 * @return int
	 * @throws DataAccessException
	*/
	public int existsMedYearMonthCheck(Map<String, Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 判断日期所在期间是否在启用日期之后，0为否、其他值为是<BR> 
	 * @param  Map<String, Object>
	 * @return int
	 * @throws DataAccessException
	*/
	public int existsMedStartDateCheck(Map<String, Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 判断日期所在期间是否结账，0为否、其他值为是<BR> 
	 * @param  Map<String, Object>
	 * @return int
	 * @throws DataAccessException
	*/
	public int existsMedYearMonthIsAccount(Map<String, Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 判断日期是否在系统启用日期之前，0为否、其他值为是<BR> 
	 * @param  Map<String, Object>
	 * @return int
	 * @throws DataAccessException
	*/
	public int existsDateCheckBefore(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 获取物流系统启用年月<BR> 
	 * @param  entityMap <BR>
	 * @return String
	 * @throws DataAccessException
	*/
	public String getMedStartDate(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @param <T>
	 * @Description 
	 * 获取仓库Alias用于生成单号<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryStoreAliasById(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @param <T>
	 * @Description 
	 * 获取业务类型type_flag用于生成单号<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryBusTypeFlagByCode(Map<String,Object> entityMap)throws DataAccessException;
	

	/**
	 * @param <T>
	 * @Description 
	 * 取出当前物流的未结账最小会计期间<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return String
	 * @throws DataAccessException
	*/
	public Map<String, Object> queryMedCurYearMonth(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @param <T>
	 * @Description 
	 * 取出当前物流的结账最大会计期间，反结账期间<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return String
	 * @throws DataAccessException
	*/
	public Map<String, Object> queryMedLastYearMonth(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 
	 * 获取材料所有库存或某批次库存<BR> 
	 * @param  entityMap<BR>
	 * @return int
	 * @throws DataAccessException
	*/
	public int getHoldBalanceByInv(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @param <T>
	 * @Description 
	 * 判断是否有不属于该仓库的材料(参数map中必须包含inv_ids和store_id)<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return String
	 * @throws DataAccessException
	*/
	public String existsMedStoreIncludeInv(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @param <T>
	 * @Description 
	 * 判断是否有不属于该供应商的材料(参数map中必须包含inv_ids和store_id)<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return String
	 * @throws DataAccessException
	*/
	public String existsMedSupIncludeInv(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @param <T>
	 * @Description 
	 * 判断供应商是否是材料的唯一供应商(参数map中必须包含inv_ids和store_id)<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return String
	 * @throws DataAccessException
	*/
	public String existsMedInvOnlySup(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @param <T>
	 * @Description 
	 * 判断是否有不属于该供应商的材料(参数map中必须包含inv_ids和store_id)<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return String
	 * @throws DataAccessException
	*/
	public String existsMedAffiSupIncludeInv(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @param <T>
	 * @Description 
	 * 判断供应商是否是材料的唯一供应商(参数map中必须包含inv_ids和store_id)<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return String
	 * @throws DataAccessException
	*/
	public String existsMedAffiInvOnlySup(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询材料图片<BR> 
	 * @param entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryInvPicture(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 判断批号有效日期是否一致
	 * @param  entityMap
	 * @return Date
	 * @throws DataAccessException
	*/
	public Date queryMedInvBatchInva(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 判断批号灭菌日期是否一致
	 * @param  entityMap
	 * @return Date
	 * @throws DataAccessException
	*/
	public Date queryMedInvBatchDisinfect(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 判断批号灭菌日期是否一致
	 * @param  entityMap
	 * @return Date
	 * @throws DataAccessException
	 */
	public Double queryMedInvBatchPrice(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 判断批号灭菌日期是否一致（代销）
	 * @param  entityMap
	 * @return Date
	 * @throws DataAccessException
	 */
	public Double queryMedAffiInvBatchPrice(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 采购计划  材料列表
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryMedInvListByPur(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String,Object>> queryMedInvListByPur(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	/**
	 * 查询 判断材料证件是否过期
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryMedCertDate(Map<String, Object> entityMap) throws DataAccessException;
	

	/**
	 * @Description 
	 * 查询采购科室（返回：dept_id,dept_no）
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String queryMedStockDept(Map<String,Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryAllMedbySupId(Map<String, Object> mapVo);

	public Map<String, Object> queryLoginUserMsg(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 退货时看供应商是否一致
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String existsMedAffiSupIsInv(Map<String, Object> entityMap) throws DataAccessException;
	public String existsMedInSupIsInv(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 显示供应商全部材料
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAllStorageMedbySupId(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 耐用品库房材料字典列表
	 * @param entityMap
	 * @return  List<Map<String, Object>>
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMedDuraStoreInvList(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryMedDuraStoreInvList(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	

	/**
	 * @Description 耐用品科室材料字典列表
	 * @param entityMap
	 * @return  List<Map<String, Object>>
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMedDuraDeptInvList(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryMedDuraDeptInvList(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 条码出库验证条码是否存在
	 * @param entityMap
	 * @return  条形码对应的材料ID
	 * @throws DataAccessException
	 */
	public Long existsMedOutInvListByBar(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 条码出库材料列表
	 * @param entityMap
	 * @return  材料列表
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMedOutInvListByBar(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 条码代销出库验证条码是否存在
	 * @param entityMap
	 * @return  条形码对应的材料ID
	 * @throws DataAccessException
	 */
	public Long existsMedAffiOutInvListByBar(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 条码代销出库材料列表
	 * @param entityMap
	 * @return  材料列表
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMedAffiOutInvListByBar(Map<String, Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryMedStockInvListBackNew(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryMedStockInvListBackNew(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 普通材料退库查询科室已领用材料列表
	 * @param entityMap
	 * @return  材料列表
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMedDeptStockInvList(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryMedDeptStockInvList(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 已结账期间列表（含未结账的本期间）
	 * @param entityMap
	 * @return  已结账期间列表
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> getFinishedYearMonthList(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 公用返回本期间未启用的库房<BR> 
	 * @param  Map<String, Object>
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMedStoreIsStart(Map<String, Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 判断物流库房期间是否已启用，0为否、其他值为是<BR> 
	 * @param  Map<String, Object>
	 * @return int
	 * @throws DataAccessException
	*/
	public int existsMedStoreIsStart(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 获取参数date日期所在的会计期间返回年月(201701)<BR> 
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String queryAccYearMonthByDate(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 公用返回本期间已结账的库房<BR> 
	 * @param  Map<String, Object>
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMedStoreIsAccount(Map<String, Object> entityMap)throws DataAccessException;
	
}
