package com.chd.hrp.mat.service.base;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

   
public interface MatCommonService {
	//获取编码规则 
	public String getMatHosRules(Map<String,Object> entityMap) throws DataAccessException;
	//获取系统参数
	public String getMatAccPara(Map<String,Object> entityMap) throws DataAccessException;
	/** 
	 * 根据当前个体码生成下一个个体码
	 * @param bar_code(流水码中当前个体码)
	 * @return
	 */
	public String getNextBar_code(String bar_code) throws DataAccessException; 
	
	//材料字典列表（有材料库存）
	public String queryMatInvList(Map<String,Object> entityMap) throws DataAccessException;
	//材料字典列表代销入库
	public String queryMatAffiInvListIn(Map<String,Object> entityMap) throws DataAccessException;
	
	//材料字典列表（有材料库存）  科室申领
	public String queryMatInvListApply(Map<String,Object> entityMap) throws DataAccessException;
	
	//材料字典列表（专购品入库）
	public String queryMatInvListSpecial(Map<String,Object> entityMap) throws DataAccessException;
	//材料字典列表（入库）
	public String queryMatInvListIn(Map<String,Object> entityMap) throws DataAccessException;
	//材料字典列表（期初入库）
    public String queryMatInvListInitial(Map<String,Object> entityMap) throws DataAccessException;
		
	//材料字典列表（没有材料库存,不带仓库)
	public String queryMatInvListNotStore(Map<String, Object> entityMap) throws DataAccessException;
	//代销材料字典列表（有材料库存）
	public String queryMatAffiInvList(Map<String, Object> entityMap) throws DataAccessException;
	//普通材料库存信息用于添加业务数据   库存盘点 
	public String queryMatStockInvList(Map<String,Object> entityMap) throws DataAccessException;
	
	//普通材料库存信息用于添加业务数据   材料调拨 
	public String queryMatStockInvListTran(Map<String,Object> entityMap) throws DataAccessException;
		
	//普通材料库存信息用于添加业务数据  材料出库
	public String queryMatStockInvListOut(Map<String,Object> entityMap) throws DataAccessException;
	
	//普通材料库存信息用于添加业务数据  科室退库
	public String queryMatStockInvListOutByBack(Map<String,Object> entityMap) throws DataAccessException;
		
	//普通材料库存信息用于添加业务数据  材料退货
	public String queryMatStockInvListBack(Map<String,Object> entityMap) throws DataAccessException;
	//普通材料库存明细信息用于添加业务数据
	public String queryMatStockInvDetailList(Map<String,Object> entityMap) throws DataAccessException;
	
	//材料信息用于添加业务数据
	public String queryMatInvListDept(Map<String, Object> entityMap) throws DataAccessException;
	//系统参数
	public Map<String, Object> queryMatParas() throws DataAccessException;
	//代销出库获取材料列表
	public String queryMatAffiOutInvList(Map<String, Object> entityMap) throws DataAccessException;
	//代销调拨
	public String  queryMatAffiTranInvList(Map<String, Object> entityMap) throws DataAccessException;
	public String queryMatAffiOutDetailInvList(Map<String, Object> entityMap) throws DataAccessException;
	//先进先出组装材料批次数据
	public String queryMatInvByFifo(Map<String, Object> entityMap) throws DataAccessException;
	public String queryMatAffiInvByFifo(Map<String, Object> entityMap) throws DataAccessException;
	//取出当前物流的未结账最小会计期间
	public Map<String, Object> queryMatCurYearMonth(Map<String,Object> entityMap) throws DataAccessException;
	//取出当前物流的结账最大会计期间，反结账期间
	public Map<String, Object> queryMatLastYearMonth(Map<String, Object> entityMap) throws DataAccessException;
	//取出当前物流耐用品的未结账最小会计期间
	public Map<String, Object> queryMatDuraCurYearMonth(Map<String,Object> entityMap) throws DataAccessException;
	//取出当前物流耐用品的结账最大会计期间，反结账期间
	public Map<String, Object> queryMatDuraLastYearMonth(Map<String, Object> entityMap) throws DataAccessException;
	//判断是否有不属于该仓库的材料
	public String existsMatStoreIncludeInv(Map<String, Object> entityMap) throws DataAccessException;
	//判断是否有条码存在
	public String existsMatSnInv(Map<String, Object> entityMap) throws DataAccessException;
	
	
	//判断是否有不属于该供应商的材料
	public String existsMatSupIncludeInv(Map<String, Object> entityMap) throws DataAccessException;
	//判断该供应商下材料是否有库存
	public String existsMatAffiSupIncludeInvAmount(Map<String, Object> entityMap) throws DataAccessException;
	//判断供应商是否是材料的唯一供应商
	public String existsMatInvOnlySup(Map<String, Object> entityMap) throws DataAccessException;
	//根据参数生成物流系统中下一个单号(可选参数详见mat_no_manage表)
	public String getMatNextNo(Map<String, Object> entityMap) throws DataAccessException;
	
	/** 
	 * 用于出库、调拨、退货明细数据加载（传入明细数据自动根据批次信息合并材料，且把批次信息组装成json格式）
	 * @param entityList
	 * @return String 例如：{Rows : [{inv_id, batch_no, bar_code, amount, [{inv_id, batch_sn, amount}, {inv_id, batch_sn, amount}]}]}
	 * @throws DataAccessException
	 */
	public String getDetailJsonByDetailList(List<Map<String, Object>> entityList) throws DataAccessException;

	/** 
	 * 用于代销出库、调拨、退货明细数据加载（传入明细数据自动根据批次信息合并材料，且把批次信息组装成json格式）
	 * @param entityList
	 * @return String 例如：{Rows : [{inv_id, batch_no, bar_code, amount, [{inv_id, batch_sn, amount}, {inv_id, batch_sn, amount}]}]}
	 * @throws DataAccessException
	 */
	public String getAffiDetailJsonByDetailList(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * 根据传入的材料信息(包含材料数量等信息)自动生成出库明细形式(批号、批次分离)的json串数据
	 * @param entityList 必须包含字段：group_id, hos_id, copy_code, store_id, inv_id, amount
	 * @return String 例如：{Rows : [{inv_id, batch_no, bar_code, amount, [{inv_id, batch_sn, amount}, {inv_id, batch_sn, amount}]}]}
	 * @throws DataAccessException
	 */
	public String getInvJsonByInvList(List<Map<String, Object>> entityList) throws DataAccessException;//常备材料
	
	/**
	 * 根据传入的代销材料信息(包含材料数量等信息)自动生成出库明细形式(批号、批次分离)的json串数据
	 * @param entityList 必须包含字段：group_id, hos_id, copy_code, store_id, inv_id, amount
	 * @return String 例如：{Rows : [{inv_id, batch_no, bar_code, amount, [{inv_id, batch_sn, amount}, {inv_id, batch_sn, amount}]}]}
	 * @throws DataAccessException
	 */
	public String getAffiInvJsonByAffiInvList(List<Map<String, Object>> entityList) throws DataAccessException;
	/**
	 * 代销材料是否有唯一供应商
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String existsMatAffiInvOnlySup(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 该代销材料是否 属于该供应商
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String existsMatAffiSupIncludeInv(Map<String, Object> entityMap) throws DataAccessException;
	
	
	/**
	 * 查询材料图片
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryInvPicture(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 判断批号有效日期是否一致
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public String queryMatInvBatchInva(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 判断批号灭菌日期是否一致
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public String queryMatInvBatchDisinfect(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 判断批号单价是否一致
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public String queryMatInvBatchPrice(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 采购计划 材料列表
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatInvListByPur(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询 判断材料证件过期 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatCertDate(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * @Description 获取登录用户信息
	 * @param mapVo
	 * @return map
	 * @throws DataAccessException
	 */
	public Map<String, Object> getLoginUserMsg(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * @Description 该供应商，改材料是否入库过
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String existsMatAffiSupIsInv(Map<String, Object> mapVo) throws DataAccessException;
	public String existsMatInSupIsInv(Map<String, Object> mapVo)  throws DataAccessException;
 
	
	/**
	 * @Description 耐用品材料字典列表
	 * @param entityMap
	 * @return  String
	 * @throws DataAccessException
	 */
	public String queryMatDuraInvList(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 耐用品库房材料字典列表
	 * @param entityMap
	 * @return  String
	 * @throws DataAccessException
	 */
	public String queryMatDuraStoreInvList(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 耐用品库房材料条码字典列表
	 * @param entityMap
	 * @return  String
	 * @throws DataAccessException
	 */
	public String queryMatDuraStoreInvBarList(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 耐用品科室材料字典列表
	 * @param entityMap
	 * @return  String
	 * @throws DataAccessException
	 */
	public String queryMatDuraDeptInvList(Map<String,Object> entityMap) throws DataAccessException;
	
	
	public String queryMatDuraDeptInvListNew(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 耐用品科室材料条码字典列表
	 * @param entityMap
	 * @return  String
	 * @throws DataAccessException
	 */
	public String queryMatDuraDeptInvBarList(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 普通材料扫码出库返回对应材料列表
	 * @param entityMap
	 * @return  String
	 * @throws DataAccessException
	 */
	public String queryMatOutInvListByBar(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 代销材料扫码出库返回对应材料列表
	 * @param entityMap
	 * @return  String
	 * @throws DataAccessException
	 */
	public String queryMatAffiOutInvListByBar(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * 材料退货查询列表
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatStockInvListBackNew(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 普通材料退库查询科室已领用材料列表
	 * @param entityMap
	 * @return  材料列表
	 * @throws DataAccessException
	 */
	public String queryMatDeptStockInvList(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 通过仓库ID查询仓库附属信息
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatStoreByCode(Map<String, Object> mapVo) throws DataAccessException;
	
	//代銷科室退库
    public String queryMatAffiOutBackInvList(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * 查询同批次材料的过期时间
	 * @param page
	 * @return
	 */
    public String getInvaTimeByBatchNo(Map<String, Object> page);
     
    /**
	 * 批量判断是否存在不等于该状态的单据(t_id需与entityList中主键ID一直，t_id_value与entityList至少一个有值)
	 * @param entityList
	 * @return
	 */
	public Integer existsStateBatch(String table, String t_id, String t_id_value, String check_state, String check_state_value, 
			List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * 科室配套表筛选材料
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatInvListByMatch(Map<String, Object> entityMap) throws DataAccessException;

	/** 
	 * 用于采购发票，且把批次信息组装成json格式）
	 * @param entityList
	 * @return String 例如：{Rows : [{inv_id, batch_no, bar_code, amount, [{inv_id, batch_sn, amount}, {inv_id, batch_sn, amount}]}]}
	 * @throws DataAccessException
	 */
	public String getDetailJsonByDetailListBill(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/** 
	 * 用于组装付款单明细json格式
	 * @param entityList
	 * @return String 例如：{Rows : [{inv_id, batch_no, bar_code, amount, [{inv_id, batch_sn, amount}, {inv_id, batch_sn, amount}]}]}
	 * @throws DataAccessException
	 */
	public String getDetailJsonByDetailListPay(List<Map<String, Object>> entityList) throws DataAccessException;

	/**
	 * 根据传入的材料信息(包含材料数量等信息)自动生成出库明细形式(批号、批次分离)的json串数据
	 * @param entityList 必须包含字段：group_id, hos_id, copy_code, store_id, inv_id, amount
	 * @return String 例如：{Rows : [{inv_id, batch_no, bar_code, amount, [{inv_id, batch_sn, amount}, {inv_id, batch_sn, amount}]}]}
	 * @throws DataAccessException
	 */
	public String getInvJsonByInvListInBack(List<Map<String, Object>> entityList) throws DataAccessException;//常备材料
	
	/**
	 * 获取日期和供应商对应的付款协议编码字符串
	 * @param mapVo group_id, hos_id, copy_code, in_date, sup_id
	 * @return String FKXYFKLX040001,FKXYFKLX040002
	 */
    public String getMatPactFkxyInfo(Map<String, Object> mapVo);
	
}
