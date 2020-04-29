package com.chd.hrp.med.service.base;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface MedCommonService {
	
	//获取编码规则
		public String getMedHosRules(Map<String,Object> entityMap) throws DataAccessException;
		//获取系统参数
		public String getMedAccPara(Map<String,Object> entityMap) throws DataAccessException;
		
		/**
		 * 根据当前个体码生成下一个个体码
		 * @param bar_code(流水码中当前个体码)
		 * @return
		 */
		public String getNextBar_code(String bar_code) throws DataAccessException;
		
		//材料字典列表（有材料库存）
		public String queryMedInvList(Map<String,Object> entityMap) throws DataAccessException;
		
		//材料字典列表（有材料库存）  科室申领
		public String queryMedInvListApply(Map<String,Object> entityMap) throws DataAccessException;
		
		//材料字典列表（专购品入库）
		public String queryMedInvListSpecial(Map<String,Object> entityMap) throws DataAccessException;
		//材料字典列表（入库）
		public String queryMedInvListIn(Map<String,Object> entityMap) throws DataAccessException;
		//材料字典列表（期初入库）
	    public String queryMedInvListInitial(Map<String,Object> entityMap) throws DataAccessException;
			
		//材料字典列表（没有材料库存,不带仓库)
		public String queryMedInvListNotStore(Map<String, Object> entityMap) throws DataAccessException;
		//代销材料字典列表（有材料库存）
		public String queryMedAffiInvList(Map<String, Object> entityMap) throws DataAccessException;
		//普通材料库存信息用于添加业务数据   库存盘点 
		public String queryMedStockInvList(Map<String,Object> entityMap) throws DataAccessException;
		
		//普通材料库存信息用于添加业务数据   材料调拨 
		public String queryMedStockInvListTran(Map<String,Object> entityMap) throws DataAccessException;
			
		//普通材料库存信息用于添加业务数据  材料出库
		public String queryMedStockInvListOut(Map<String,Object> entityMap) throws DataAccessException;
			
		//普通材料库存信息用于添加业务数据  材料退货
		public String queryMedStockInvListBack(Map<String,Object> entityMap) throws DataAccessException;
		//普通材料库存明细信息用于添加业务数据
		public String queryMedStockInvDetailList(Map<String,Object> entityMap) throws DataAccessException;
		
		//材料信息用于添加业务数据
		public String queryMedInvListDept(Map<String, Object> entityMap) throws DataAccessException;
		//系统参数
		public Map<String, Object> queryMedParas() throws DataAccessException;
		//代销出库获取材料列表
		public String queryMedAffiOutInvList(Map<String, Object> entityMap) throws DataAccessException;
		public String queryMedAffiOutDetailInvList(Map<String, Object> entityMap) throws DataAccessException;
		//先进先出组装材料批次数据
		public String queryMedInvByFifo(Map<String, Object> entityMap) throws DataAccessException;
		public String queryMedAffiInvByFifo(Map<String, Object> entityMap) throws DataAccessException;
		//取出当前物流的未结账最小会计期间
		public Map<String, Object> queryMedCurYearMonth(Map<String,Object> entityMap) throws DataAccessException;
		//取出当前物流的结账最大会计期间，反结账期间
		public Map<String, Object> queryMedLastYearMonth(Map<String, Object> entityMap) throws DataAccessException;
		//判断是否有不属于该仓库的材料
		public String existsMedStoreIncludeInv(Map<String, Object> entityMap) throws DataAccessException;
		//判断是否有不属于该供应商的材料
		public String existsMedSupIncludeInv(Map<String, Object> entityMap) throws DataAccessException;
		//判断供应商是否是材料的唯一供应商
		public String existsMedInvOnlySup(Map<String, Object> entityMap) throws DataAccessException;
		//根据参数生成物流系统中下一个单号(可选参数详见med_no_manage表)
		public String getMedNextNo(Map<String, Object> entityMap) throws DataAccessException;
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
		public String existsMedAffiInvOnlySup(Map<String, Object> entityMap) throws DataAccessException;
		/**
		 * 该代销材料是否 属于该供应商
		 * @param entityMap
		 * @return
		 * @throws DataAccessException
		 */
		public String existsMedAffiSupIncludeInv(Map<String, Object> entityMap) throws DataAccessException;
		
		
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
		public String queryMedInvBatchInva(Map<String,Object> entityMap) throws DataAccessException;

		/**
		 * @Description 判断批号灭菌日期是否一致
		 * @param mapVo
		 * @param mode
		 * @return Map<String, Object>
		 * @throws Exception
		 */
		public String queryMedInvBatchDisinfect(Map<String,Object> entityMap) throws DataAccessException;
		/**
		 * @Description 判断批号单价是否一致
		 * @param mapVo
		 * @param mode
		 * @return Map<String, Object>
		 * @throws Exception
		 */
		public String queryMedInvBatchPrice(Map<String,Object> entityMap) throws DataAccessException;
		/**
		 * @Description 采购计划 材料列表
		 * @param entityMap
		 * @return
		 * @throws DataAccessException
		 */
		public String queryMedInvListByPur(Map<String, Object> entityMap) throws DataAccessException;
		
		/**
		 * @Description 查询 判断材料证件过期 
		 * @param mapVo
		 * @return
		 * @throws DataAccessException
		 */
		public String queryMedCertDate(Map<String, Object> mapVo) throws DataAccessException;
		
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
		public String existsMedAffiSupIsInv(Map<String, Object> mapVo) throws DataAccessException;
		public String existsMedInSupIsInv(Map<String, Object> mapVo)  throws DataAccessException;
	 
		
		/**
		 * @Description 耐用品库房材料字典列表
		 * @param entityMap
		 * @return  String
		 * @throws DataAccessException
		 */
		public String queryMedDuraStoreInvList(Map<String,Object> entityMap) throws DataAccessException;

		/**
		 * @Description 耐用品科室材料字典列表
		 * @param entityMap
		 * @return  String
		 * @throws DataAccessException
		 */
		public String queryMedDuraDeptInvList(Map<String,Object> entityMap) throws DataAccessException;

		/**
		 * @Description 普通材料扫码出库返回对应材料列表
		 * @param entityMap
		 * @return  String
		 * @throws DataAccessException
		 */
		public String queryMedOutInvListByBar(Map<String,Object> entityMap) throws DataAccessException;

		/**
		 * @Description 代销材料扫码出库返回对应材料列表
		 * @param entityMap
		 * @return  String
		 * @throws DataAccessException
		 */
		public String queryMedAffiOutInvListByBar(Map<String,Object> entityMap) throws DataAccessException;
		/**
		 * 材料退货查询列表
		 * @param entityMap
		 * @return
		 * @throws DataAccessException
		 */
		public String queryMedStockInvListBackNew(Map<String, Object> entityMap) throws DataAccessException;
		/**
		 * 普通材料退库查询科室已领用材料列表
		 * @param entityMap
		 * @return  材料列表
		 * @throws DataAccessException
		 */
		public String queryMedDeptStockInvList(Map<String, Object> entityMap) throws DataAccessException;
		/**
		 * 通过仓库ID查询仓库附属信息
		 * @param mapVo
		 * @return
		 * @throws DataAccessException
		 */
		public String queryMedStoreByCode(Map<String, Object> mapVo) throws DataAccessException;

}
