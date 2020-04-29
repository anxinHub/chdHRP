package com.chd.hrp.mat.dao.base;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
 
public interface MatCommonMapper extends SqlMapper{
   
	/**
	 * @Description 
	 * 获取物资分类编码规则<BR> 
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException 
	 */
	public String getMatHosRules(Map<String, Object> entityMap)throws DataAccessException;

	/** 
	 * @Description 
	 * 获取参数table_name表中c_max字段的最大值<BR> 
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String getMatMaxNo(Map<String, Object> entityMap)throws DataAccessException;
	//编码与名称重复判断
	public int existsCodeNameByAdd(Map<String, Object> map) throws DataAccessException;
	public int existsNameByUpdate(Map<String, Object> map) throws DataAccessException;
	//获取最大排序号
	public Integer getMaxSortCodeByTable(Map<String, Object> map) throws DataAccessException;
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
	 * 获取参数table_name表中所有的c_code、c_name与参数value相等的数据<BR> 
	 * @param entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> existsMatCodeName(Map<String, Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 材料字典列表
	 * @param entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryMatInvList(Map<String, Object> entityMap) throws DataAccessException;
	public List<?> queryMatInvList(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 材料字典列表代销入库
	 * @param entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryMatAffiInvListIn(Map<String, Object> entityMap) throws DataAccessException;
	public List<?> queryMatAffiInvListIn(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 材料字典列表  科室申领 
	 * @param entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryMatInvListApply(Map<String, Object> entityMap) throws DataAccessException;
	public List<?> queryMatInvListApply(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	
	/**
	 * @Description 
	 * 材料字典列表(专购品入库)
	 * @param entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryMatInvListSpecial(Map<String, Object> entityMap) throws DataAccessException;
	public List<?> queryMatInvListSpecial(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 材料字典列表  (入库)
	 * @param entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryMatInvListIn(Map<String, Object> entityMap) throws DataAccessException;
	public List<?> queryMatInvListIn(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 材料字典列表  (期初入库)
	 * @param entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryMatInvListInitial(Map<String, Object> entityMap) throws DataAccessException;
	public List<?> queryMatInvListInitial(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 材料字典列表(没有材料库存,不带仓库)
	 * @param entityMap
	 * @return
	 */
	public List<?> queryMatInvListNotStore(Map<String, Object> entityMap) throws DataAccessException;
	public List<?> queryMatInvListNotStore(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
	//代销材料库存信息用于添加业务数据<BR>
	public List<?> queryMatAffiInvList(Map<String, Object> entityMap) throws DataAccessException;
	public List<?> queryMatAffiInvList(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 普通材料库存信息用于添加业务数据<BR>    库存盘点 
	 * @param entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryMatStockInvList(Map<String, Object> entityMap)throws DataAccessException;
	public List<?> queryMatStockInvList(Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	
	/**
	 * @Description 
	 * 普通材料库存信息用于添加业务数据<BR>  材料调拨 
	 * @param entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryMatStockInvListTran(Map<String, Object> entityMap)throws DataAccessException;
	public List<?> queryMatStockInvListTran(Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	 
	/**
	 * @Description 
	 * 普通材料库存信息用于添加业务数据<BR>  材料出库 
	 * @param entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryMatStockInvListOut(Map<String, Object> entityMap)throws DataAccessException;
	public List<?> queryMatStockInvListOut(Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	
	/**
	 * @Description 
	 * 普通材料库存信息用于添加业务数据<BR>  科室退库
	 * @param entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryMatStockInvListOutByBack(Map<String, Object> entityMap)throws DataAccessException;
	public List<?> queryMatStockInvListOutByBack(Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	
	/**
	 * @Description 
	 * 普通材料库存信息用于添加业务数据<BR>  材料退货
	 * @param entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryMatStockInvListBack(Map<String, Object> entityMap)throws DataAccessException;
	public List<?> queryMatStockInvListBack(Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;

	/**
	 * @Description 
	 * 普通材料库存明细信息用于添加业务数据<BR> 
	 * @param entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryMatStockInvDetailList(Map<String, Object> entityMap)throws DataAccessException;
	public List<?> queryMatStockInvDetailList(Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;

	/**
	 * 代销材料获取材料列表
	 * @param entityMap
	 * @param rowBounds 
	 * @return
	 * @throws DataAccessException
	 */
	public List<?> queryMatAffiOutInvList(Map<String, Object> entityMap)  throws DataAccessException;
	public List<?> queryMatAffiOutInvList(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 代销材料获取材料列表调拨
	 * @param entityMap
	 * @param rowBounds 
	 * @return
	 * @throws DataAccessException
	 */
	public List<?> queryMatAffiTranInvList(Map<String, Object> entityMap)  throws DataAccessException;
	public List<?> queryMatAffiTranInvList(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 代销材料获取材料列表
	 * @param entityMap
	 * @param rowBounds 
	 * @return
	 * @throws DataAccessException
	 */
	public List<?> queryMatAffiOutDetailInvList(Map<String, Object> entityMap)  throws DataAccessException;
	public List<?> queryMatAffiOutDetailInvList(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 判断材料库存是否充足<BR> 
	 * @param entityList
	 * @return String
	 * @throws DataAccessException
	 */
	public String existsMatStockInvIsEnough(List<Map<String, Object>> entityList)throws DataAccessException;

	/**
	 * @Description 
	 * 退库判断科室领用数量是否充足<BR> 
	 * @param entityList
	 * @return String
	 * @throws DataAccessException
	 */
	public String existsMatStockInvIsAppEnough(List<Map<String, Object>> entityList)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室需求材料列表<BR> 
	 * @param entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryMatInvListDept(Map<String, Object> entityMap)throws DataAccessException;
	public List<?> queryMatInvListDept(Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	/**
	 * 判断代销及时库存是否充足
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public String existsMatAffiInvIsEnough(List<Map<String, Object>> entityList) throws DataAccessException;
	/**
	 * @Description 
	 * 物流系统参数<BR> 
	 * @param entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatParas(Map<String, Object> entityMap)throws DataAccessException;
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
	public int existsMatYearMonthCheck(Map<String, Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 判断日期所在期间是否在启用日期之后，0为否、其他值为是<BR> 
	 * @param  Map<String, Object>
	 * @return int
	 * @throws DataAccessException
	*/
	public int existsMatStartDateCheck(Map<String, Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 判断日期所在期间是否结账，0为否、其他值为是<BR> 
	 * @param  Map<String, Object>
	 * @return int
	 * @throws DataAccessException
	*/
	public int existsMatYearMonthIsAccount(Map<String, Object> entityMap)throws DataAccessException;
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
	public String getMatStartDate(Map<String,Object> entityMap)throws DataAccessException;

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
	public Map<String, Object> queryMatCurYearMonth(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @param <T>
	 * @Description 
	 * 取出当前物流的结账最大会计期间，反结账期间<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return String
	 * @throws DataAccessException
	*/
	
	public Map<String, Object> queryMatLastYearMonth(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @param <T>
	 * @Description 
	 * 取出当前物流耐用品的未结账最小会计期间<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return String
	 * @throws DataAccessException
	*/
	public Map<String, Object> queryMatDuraCurYearMonth(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @param <T>
	 * @Description 
	 * 取出当前物流耐用品的结账最大会计期间，反结账期间<BR>
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return String
	 * @throws DataAccessException
	*/
	public Map<String, Object> queryMatDuraLastYearMonth(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @param <T>
	 * @Description 
	 * 取出当前物流耐用品的启始年月<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return String
	 * @throws DataAccessException
	*/
	public Map<String, Object> queryMatDuraStartYearMonth(Map<String,Object> entityMap) throws DataAccessException;
	
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
	public String existsMatStoreIncludeInv(Map<String,Object> entityMap)throws DataAccessException;
	
	
	/**
	 * @param <T>
	 * @Description 
	 * 判断是否有条码存在
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return String
	 * @throws DataAccessException
	*/
	public String existsMatSnInv(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @param <T>
	 * @Description 
	 * 判断是否有不属于该供应商的材料(参数map中必须包含inv_ids和store_id)<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return String
	 * @throws DataAccessException
	*/
	public String existsMatSupIncludeInv(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @param <T>
	 * @Description 
	 * 判断供应商是否是材料的唯一供应商(参数map中必须包含inv_ids和store_id)<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return String
	 * @throws DataAccessException
	*/
	public String existsMatInvOnlySup(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @param <T>
	 * @Description 
	 * 判断是否有不属于该供应商的材料(参数map中必须包含inv_ids和store_id)<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return String
	 * @throws DataAccessException
	*/
	public String existsMatAffiSupIncludeInv(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @param <T>
	 * @Description 
	 * 判断该供应商下材料是否有库存(参数map中必须包含inv_ids和store_id)<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return String
	 * @throws DataAccessException
	*/
	public String existsMatAffiSupIncludeInvAmount(Map<String,Object> entityMap)throws DataAccessException;
	

	/**
	 * @param <T>
	 * @Description 
	 * 判断供应商是否是材料的唯一供应商(参数map中必须包含inv_ids和store_id)<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return String
	 * @throws DataAccessException
	*/
	public String existsMatAffiInvOnlySup(Map<String,Object> entityMap)throws DataAccessException;
	
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
	public Date queryMatInvBatchInva(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 判断批号灭菌日期是否一致
	 * @param  entityMap
	 * @return Date
	 * @throws DataAccessException
	*/
	public Date queryMatInvBatchDisinfect(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 判断批号灭菌日期是否一致
	 * @param  entityMap
	 * @return Date
	 * @throws DataAccessException
	 */
	public Double queryMatInvBatchPrice(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 判断批号灭菌日期是否一致（代销）
	 * @param  entityMap
	 * @return Date
	 * @throws DataAccessException
	 */
	public Double queryMatAffiInvBatchPrice(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 采购计划  材料列表
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryMatInvListByPur(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String,Object>> queryMatInvListByPur(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	/**
	 * 查询 判断材料证件是否过期
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryMatCertDate(Map<String, Object> entityMap) throws DataAccessException;
	

	/**
	 * @Description 
	 * 查询采购科室（返回：dept_id,dept_no）
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String queryMatStockDept(Map<String,Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryAllMatbySupId(Map<String, Object> mapVo);

	public Map<String, Object> queryLoginUserMsg(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 退货时看供应商是否一致
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String existsMatAffiSupIsInv(Map<String, Object> entityMap) throws DataAccessException;
	public String existsMatInSupIsInv(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 显示供应商全部材料
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAllStorageMatbySupId(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 耐用品材料字典列表
	 * @param entityMap
	 * @return  List<Map<String, Object>>
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatDuraInvList(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryMatDuraInvList(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 耐用品库房材料字典列表
	 * @param entityMap
	 * @return  List<Map<String, Object>>
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatDuraStoreInvList(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryMatDuraStoreInvList(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 耐用品库房材料条码字典列表
	 * @param entityMap
	 * @return  List<Map<String, Object>>
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatDuraStoreInvBarList(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryMatDuraStoreInvBarList(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 耐用品科室材料字典列表
	 * @param entityMap
	 * @return  List<Map<String, Object>>
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatDuraDeptInvList(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryMatDuraDeptInvList(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	
	

	/**
	 * @Description 耐用品科室材料字典列表
	 * @param entityMap
	 * @return  List<Map<String, Object>>
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatDuraDeptInvListNew(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryMatDuraDeptInvListNew(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 耐用品科室材料条码字典列表
	 * @param entityMap
	 * @return  List<Map<String, Object>>
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatDuraDeptInvBarList(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryMatDuraDeptInvBarList(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 条码出库验证条码是否存在
	 * @param entityMap
	 * @return  条形码对应的材料ID
	 * @throws DataAccessException
	 */
	public Long existsMatOutInvListByBar(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 条码出库材料列表
	 * @param entityMap
	 * @return  材料列表
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatOutInvListByBar(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 条码代销出库验证条码是否存在
	 * @param entityMap
	 * @return  条形码对应的材料ID
	 * @throws DataAccessException
	 */
	public Long existsMatAffiOutInvListByBar(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 条码代销出库材料列表
	 * @param entityMap
	 * @return  材料列表
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatAffiOutInvListByBar(Map<String, Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryMatStockInvListBackNew(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryMatStockInvListBackNew(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 普通材料退库查询科室已领用材料列表
	 * @param entityMap
	 * @return  材料列表
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatDeptStockInvList(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryMatDeptStockInvList(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 已结账期间列表（含未结账的本期间）
	 * @param entityMap
	 * @return  已结账期间列表
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> getFinishedYearMonthList(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 
	 * 物流子系统是否已启用<BR> 
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int existsMatModStart(Map<String, Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 本期间物流子系统是否已启用<BR> 
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int existsMatModStartByYearMonth(Map<String, Object> entityMap)throws DataAccessException;
	
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
	 * 判断物流库房期间是否已启用，0为否、其他值为是<BR> 
	 * @param  Map<String, Object>
	 * @return int
	 * @throws DataAccessException
	*/
	public int existsMatStoreIsStart(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 公用返回本期间未启用的库房<BR> 
	 * @param  Map<String, Object>
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMatStoreIsStart(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 判断物流库房期间是否已结账，0为否、其他值为是<BR> 
	 * @param  Map<String, Object>
	 * @return int
	 * @throws DataAccessException
	*/
	public int existsMatStoreIsAccount(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 公用返回本期间已结账的库房<BR> 
	 * @param  Map<String, Object>
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMatStoreIsAccount(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 普通材料库存信息用于添加业务数据<BR>  代销科室退库
	 * @param entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryMatAffiOutBackInvList(Map<String, Object> entityMap)throws DataAccessException;
	public List<?> queryMatAffiOutBackInvList(Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	/**
	 * 取会计期间表中的年月
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryAccYearAndMonth(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 查询同一批次的物资材料的过期时间
	 * @param page
	 * @return
	 */
	public Map<String, Object> getInvaTimeByBatchNo(Map<String, Object> page);
	
	/**
	 * 批量判断是否存在不等于该状态的单据
	 * @param entityList
	 * @return 
	 */
	public Integer existsStateBatch(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 
	 * 普通材料获取库存<BR> 
	 * @param entityMap(必要参数：store_id, inv_id；参数可选：price, batch_no, batch_sn, bar_code, location_id)
	 * @return String(库存@及时库存)
	 * @throws DataAccessException
	 */ 
	public String getInvStockByInvMsg(Map<String, Object> entityMap)throws DataAccessException;
 
	/**
	 * @Description 
	 * 代销材料获取库存<BR> 
	 * @param entityMap(必要参数：store_id, inv_id；参数可选：price, batch_no, batch_sn, bar_code, location_id)
	 * @return String(库存@及时库存)
	 * @throws DataAccessException
	 */
	public String getAffiInvStockByInvMsg(Map<String, Object> entityMap)throws DataAccessException;
	/**
	 * 科室配套表筛选材料
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryMatInvListByMatch(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String,Object>> queryMatInvListByMatch(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 用于方法hrp.mat.servicesImpl.base.getInvJsonByInvList
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryMatFifoByInvList(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 用于方法hrp.mat.servicesImpl.base.getAffiInvJsonByInvList
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryMatAffiFifoByInvList(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 查询仓库账面库存  数据最小的组合为 inv_id+bar_code+price
	 * @param entityMap
	 * @return
	 */
	public List<Map<String, Object>> queryMatStockSumInvList(Map<String, Object> entityMap);
	/**
	 * 查询仓库账面库存  数据最小的组合为 inv_id+bar_code+price
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 */
	public List<Map<String, Object>> queryMatStockSumInvList(Map<String, Object> entityMap, RowBounds rowBounds);
	/**
	 * 
	 * 通过inv_id,bat_code,price,查询fifo_balance表中 对应的全部批号材料信息
	 * @param mapDetailVo
	 * @return
	 */
	public List<Map<String, Object>> queryInvBatchStockMsgsByInvMsg(
			Map<String, Object> mapDetailVo);
    
	/**
	 * @Description 
	 * 普通材料库存明细信息用于添加业务数据<BR> 
	 * @param entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryMatApplyCheckInvDetailList(Map<String, Object> entityMap)throws DataAccessException;
	

	//查询供应商列表导入用
	public List<Map<String, Object>> queryHosSupDict(Map<String, Object> map)throws DataAccessException;
	//查询仓库列表导入用
	public List<Map<String, Object>> queryHosStoreDict(Map<String, Object> map)throws DataAccessException;
	//查询科室列表导入用
	public List<Map<String, Object>> queryHosDeptDict(Map<String, Object> map)throws DataAccessException;
	//查询职工列表导入用
	public List<Map<String, Object>> queryHosEmpDict(Map<String, Object> map)throws DataAccessException;
	//查询项目列表导入用
	public List<Map<String, Object>> queryHosProjDict(Map<String, Object> map)throws DataAccessException;
	//查询采购类型列表导入用
	public List<Map<String, Object>> queryMatStockType(Map<String, Object> map)throws DataAccessException;
	//查询采购协议列表导入用
	public List<Map<String, Object>> queryMatProtocolMain(Map<String, Object> map)throws DataAccessException;
	//查询材料列表导入用
	public List<Map<String, Object>> queryMatInvDict(Map<String, Object> map)throws DataAccessException;
	/**
	 * 获取日期和供应商对应的付款协议编码字符串
	 * @param mapVo
	 * @return
	 */
	public Map<String, Object> getMatPactFkxyInfo(Map<String, Object> mapVo);
	
}
