/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.serviceImpl.verification;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.NumberUtil;
import com.chd.base.util.UUIDLong;
import com.chd.hrp.acc.dao.termend.AccTermendTemplateDeptMapper;
import com.chd.hrp.acc.dao.termend.AccTermendTemplateMapper;
import com.chd.hrp.acc.dao.verification.AccBudgLederMapper;
import com.chd.hrp.acc.dao.verification.AccBudgRangeMapper;
import com.chd.hrp.acc.dao.vouch.SuperVouchMapper;
import com.chd.hrp.acc.entity.AccBudgLeder;
import com.chd.hrp.acc.entity.AccTermendTemplate;
import com.chd.hrp.acc.entity.AccTermendTemplateDept;
import com.chd.hrp.acc.service.verification.AccBudgLederService;
import com.chd.hrp.acc.service.vouch.SuperVouchService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 坏账提取表<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("accBudgLederService")
public class AccBudgLederServiceImpl implements AccBudgLederService {

	private static Logger logger = Logger.getLogger(AccBudgLederServiceImpl.class);
	
	@Resource(name = "accBudgLederMapper")
	private final AccBudgLederMapper accBudgLederMapper = null;
	
	@Resource(name = "superVouchService")
	private final SuperVouchService superVouchService = null;
	
	@Resource(name = "accTermendTemplateMapper")
	private final AccTermendTemplateMapper accTermendTemplateMapper = null;

	@Resource(name = "accTermendTemplateDeptMapper")
	private final AccTermendTemplateDeptMapper accTermendTemplateDeptMapper = null;
	
	@Resource(name = "accBudgRangeMapper")
	private final AccBudgRangeMapper accBudgRangeMapper = null;
	
	@Resource(name = "superVouchMapper")
	private final SuperVouchMapper superVouchMapper = null;
	
	RowBounds rowBoundsALL = new RowBounds(0, 20);
	/**
	 * @Description 
	 * 坏账提取表<BR> 添加
	 * @param AccBudgLeder entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAccBudgLeder(Map<String,Object> entityMap)throws DataAccessException{
		AccBudgLeder accBudgLeder = queryAccBudgLederByCode(entityMap);
		if (accBudgLeder != null) {
			return "{\"error\":\"编码：" + entityMap.get("item_code").toString() + "重复.\"}";
		}
		
		try {
			accBudgLederMapper.addAccBudgLeder(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addAccBudgLeder\"}";
		}
	}
	
	/**
	 * @Description 
	 * 坏账提取表 批量添加
	 * @param  AccBudgLeder entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAccBudgLeder(List<Map<String,Object>> entityList)throws DataAccessException{
		try {
			accBudgLederMapper.addBatchAccBudgLeder(entityList);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchAccBudgLeder\"}";
		}
	}
	
	/**
	 * @Description 
	 * 坏账提取表<BR> 查询AccBudgLeder分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAccBudgLeder(Map<String,Object> entityMap) throws DataAccessException{
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		List<AccBudgLeder> list = accBudgLederMapper.queryAccBudgLeder(entityMap, rowBounds);
		PageInfo page = new PageInfo(list);
		return ChdJson.toJson(list, page.getTotal());
	}
	
	/**
	 * @Description 
	 * 坏账提取表<BR> 查询AccBudgLederByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AccBudgLeder queryAccBudgLederByCode(Map<String,Object> entityMap)throws DataAccessException{
		return accBudgLederMapper.queryAccBudgLederByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 坏账提取表<BR> 批量删除AccBudgLeder
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAccBudgLeder(List<Map<String,Object>> entityList)throws DataAccessException{
		try {
				accBudgLederMapper.deleteBatchAccBudgLeder(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccBudgLeder\"}";
		}
		
	}
	
	/**
	 * @Description 
	 * 坏账提取表 删除AccBudgLeder
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deleteAccBudgLeder(Map<String, Object> entityMap) throws DataAccessException {
		try {
				accBudgLederMapper.deleteAccBudgLeder(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteAccBudgLeder\"}";
		}
    }
	
	/**
	 * @Description 
	 * 坏账提取表<BR> 更新AccBudgLeder
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAccBudgLeder(Map<String,Object> entityMap)throws DataAccessException{
		try {
			accBudgLederMapper.updateAccBudgLeder(entityMap);
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccBudgLeder\"}";
		}
	}
	
	/**
	 * @Description 
	 * 坏账提取表<BR> 批量更新AccBudgLeder
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAccBudgLeder(List<Map<String,Object>> entityList)throws DataAccessException{
		try {
			accBudgLederMapper.updateBatchAccBudgLeder(entityList);
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccBudgLeder\"}";
		}
	}
	
	/**
	 * @Description 
	 * 坏账提取表<BR> 导入AccBudgLeder
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importAccBudgLeder(Map<String,Object> entityMap)throws DataAccessException{
		try {
			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";
		}
	}
	
	/**
	 * 坏账提取  主查询
	 */
	@Override
	public String queryAccBudgLederBySubjCode(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		List<Map<String, Object>> list = accBudgLederMapper.queryAccBudgLederBySubjCode(entityMap, rowBounds);
		PageInfo page = new PageInfo(list);
		return ChdJson.toJson(list, page.getTotal());
	}
	
	/**
	 * 坏账科目
	 */
	@Override
	public String queryBadSubj(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
		}else{
			rowBounds = rowBoundsALL;
		}
		
		if(!"02".equals(entityMap.get("copy_nature").toString())){
			return JSON.toJSONString(accBudgLederMapper.queryBadSubj(entityMap, rowBounds));
		}else{
			return JSON.toJSONString(accBudgLederMapper.queryAllSubj(entityMap, rowBounds));
		}
	}
	
	/**
	 * 管理费用科目
	 */
	@Override
	public String queryManageSubj(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
		}else{
			rowBounds = rowBoundsALL;
		}
		
		if(!"02".equals(entityMap.get("copy_nature").toString())){
			return JSON.toJSONString(accBudgLederMapper.queryManageSubj(entityMap, rowBounds));
		}else{
			return JSON.toJSONString(accBudgLederMapper.queryAllSubj(entityMap, rowBounds));
		}
		
	}
	
	/**
	 * 查看当前会计期间 ACC_BUDG_LEDER 表中是否有数据
	 */
	@Override
	public int queryBudgLederCounts(Map<String, Object> mapVo) throws DataAccessException {
		
		AccBudgLeder accBudgLeder = new AccBudgLeder();
		accBudgLeder = accBudgLederMapper.queryBudgLederCounts(mapVo);
		if(accBudgLeder != null){
			return 1;
		}else{
			return 0;
		}
		
	}
	
	/**
	 * 应收科目
	 */
	@Override
	public String queryAllSubj(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
		}else{
			rowBounds = rowBoundsALL;
		}
		
		return JSON.toJSONString(accBudgLederMapper.queryAllSubj(entityMap, rowBounds));
	}
	
    /**
     * 根据月份和年份从acc_budg_leder中取数据
     */
	@Override
	public AccBudgLeder queryBadSubjBean(Map<String, Object> entityMap) throws DataAccessException {
		AccBudgLeder accBudgLeder = accBudgLederMapper.queryBudgLederCounts(entityMap);
		return accBudgLeder;
	}

	/**
	 * 查看当前会计期间是否结账
	 */
	@Override
	public int queryAccMonthIsAcc(Map<String, Object> entityMap) throws DataAccessException {
		int flag = 0;
		flag  = accBudgLederMapper.queryAccMonthIsAcc(entityMap);
		return flag;
	}
	/**
	 * 查看当前应收科目的余额
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public double queryRecevieSubjEndOs(Map<String, Object> entityMap) throws DataAccessException {
		double end_os = accBudgLederMapper.queryRecevieSubjEndOs(entityMap);
		return end_os;
	}
	
	
	/**
	 * 查看当前会计期间应收科目所在凭证是否记账
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int queryRecevieSubjIsAcc(Map<String, Object> entityMap) throws DataAccessException{
		int flag = 0;
		int counts = 0;
		counts = accBudgLederMapper.queryRecevieSubjIsAcc(entityMap);
		if(counts > 0){
			flag = 1 ;
		}else{
			flag = 0 ; 
		}
		return flag;
	}

	
	/**
	 * 计提坏账  生成凭证
	 *  1、从账表里面取期末余额的和，条件：“选择的所有应收科目”，
			如：期末余额的和 = select sum(end_os) from ACC_LEDER where subj_code in('选择的应收科目')
		2、余额百分比法：固定计提比例 / 100 * 期末余额的和
		3、账龄分析法：
			1）、从辅助核算表ACC_VOUCH_CHECK取借方金额(debit) - 表ACC_VOUCH_VERI核销金额(VERI_MONEY)，
				通过字段VOUCH_CHECK_ID关联，条件：“选择的所有应收科目”、取得的余额 > 0
			2）、根据辅助核算表的到期日期字段(DUE_DATE)，与凭证日期比较，得出科目本月最大相差天数
				如：max(due_date - vouch_date)
				（注：凭证没有维护到期日期则提示凭证XX号中科目XX辅助核算没有维护到期日期）
			3）、从账龄区间表acc_budg_range取起始天数、终止天数，字段BEGIN_DAYS、END_DAYS，
				根据相差天数取此区间的计提比例RANGE_PRO
				（注：没有区间比例则提示科目XX没有YY天的对应区间比例）
			4）、区间的计提比例/100*余额
			如：余额=select (a.debit-b.VERI_MONEY),DUE_DATE from ACC_VOUCH_CHECK a,ACC_VOUCH_VERI b where a.VOUCH_CHECK_ID=b.VOUCH_CHECK_ID and a.subj_code in('选择的应收科目')
		4、生成凭证格式：
		计算出来的金额>0，借“管理费用科目”，贷“坏账提取科目”，摘要“计提坏账准备”
		计算出来的金额<0，贷“管理费用科目”，借“坏账提取科目”，摘要“冲减坏账准备”
		5、以上都需要加集团、医院、账套、会计期间的条件
		判断：
		1、当前会计期间已经结账，不能坏账计提
		2、余额百分比法，固定计提比例不能<=0
		3、账龄分析法，区间的计提比例不能<=0
		4、期末余额的和<=0，不能坏账计提
		5、选择的应收科目在当前会计期间已经坏账提取过了，不能坏账计提
		6、选择的应收科目在当前会计期间没有记账，不能坏账计提
		存储表：
		1、ACC_VOUCH(state=-1)、ACC_VOUCH_DETAIL、ACC_VOUCH_CHECK、ACC_VOUCH_CHECK、ACC_VOUCH_ZCHECK，根据科目判断是否需要存辅助核算表
		2、需要存凭证来源表ACC_VOUCH_SOURCE、业务编码BUSI_CODE='001'，业务名称BUSI_NAME='坏账计提'，见文档，系统表结构-17
		3、将坏账提取科目、管理费用科目、计提方式（余额百分比法0、账龄分析法1）、固定计提比例（账龄分析法不需要存）存坏账提取表ACC_BUDG_LEDER
			
	 * @param page
	 * @return
	 */
	@Override
	public String addAccBadDebtsExtract(Map<String, Object> entityMap) throws DataAccessException{
		try {
			//存放凭证主表信息
			Map<String, Object> vouchMap = new HashMap<String, Object>();
			//存放凭证分录信息
			//List<List<Map<String, Object>>> vouchDetailList = new ArrayList<List<Map<String,Object>>>();
			List<Map<String, Object>> detailList = new ArrayList<Map<String,Object>>();
			//存放辅助核算信息
			//Map<Integer,List<Map<String, Object>>> vouchCheckMap = new HashMap<Integer,List<Map<String, Object>>>();
			List<Map<String, Object>> vouchCheckList = new ArrayList<Map<String,Object>>();
			
			//取凭证模板
			AccTermendTemplate att = accTermendTemplateMapper.queryAccTermendTemplateByCode(entityMap);
			if(att.getVouch_type_code() == null || "".equals(att.getVouch_type_code())){
				return "{\"error\":\"模板"+att.getTemplate_name()+"还不存在，请先进行设置！\"}";
			}
			
			//如果本期间已生成凭证则不允许生成凭证
			entityMap.put("template_type_code", att.getTemplate_type_code());
			entityMap.put("template_id", att.getTemplate_id());
			int flag1 = accTermendTemplateMapper.existsAccVouchByTemplate(entityMap);
			if(flag1 > 0){
				return "{\"error\":\"本期间已生成凭证，不能重复生成!\"}";
			}
			
			/*查看当前会计期间是否结账*/
			int flag = accBudgLederMapper.queryAccMonthIsAcc(entityMap);	
			if(flag > 0){
				return "{\"error\":\"当前会计期间已经结账，不能坏账计提!\"}";
			}
			
			/*选择的应收科目在当前会计期间是否计提过坏账*/
			/*int bad_flag = accBudgLederMapper.queryRecevieSubjJtIsOrNot(entityMap);
			if(bad_flag > 0){
				return "{\"error\":\"选择的应收科目在当前会计期间已经坏账提取过了，不能坏账计提！\"}";
			}*/
			
			/*选择的应收科目在当前会计期间是否记账*/
			int acc_flag  = accBudgLederMapper.queryRecevieSubjIsAcc(entityMap);
			if(acc_flag > 0){
				return "{\"error\":\"选择的应收科目在当前会计期间没有记账，不能坏账计提！\"}";
			}

			Date date = new Date();
			Long group_id = att.getGroup_id();
			Long hos_id = att.getHos_id();
			String copy_code = att.getCopy_code();
			String acc_year = att.getAcc_year();
			String template_type_code = att.getTemplate_type_code();
			int vouch_row = 1, check_row = 1;
			Map<String, String> checkItem = new HashMap<String, String>();
			String column = "", column_value = "";
			Double vouch_money = 0.0;
			String summary = "";
			/*********组装凭证主表信息**************begin*****************/
			String vouch_id = UUIDLong.absStringUUID();
			vouchMap.put("group_id", group_id);
			vouchMap.put("hos_id", hos_id); 
			vouchMap.put("copy_code", copy_code);
			vouchMap.put("acc_year", acc_year);
			vouchMap.put("acc_month", entityMap.get("acc_month"));
			vouchMap.put("vouch_id", vouch_id);
			vouchMap.put("vouch_type_code", att.getVouch_type_code());  //凭证类型
			Date vouch_date = accTermendTemplateMapper.queryAccVouchDateByYearMonth(vouchMap);
			vouchMap.put("vouch_date", vouch_date);  //凭证日期
			vouchMap.put("vouch_att_num", 0);  //附件数量
			vouchMap.put("busi_type_code", template_type_code);  //业务类型
			vouchMap.put("create_date", date);  //制单日期
			/*********组装凭证主表信息**************end*******************/

			if(att.getDebit_subj_code1()==null){
				throw new SysException("模板【"+att.getTemplate_name()+"】坏账提取科目为空！");
			}

			if(att.getCredit_subj_code1()==null){
				throw new SysException("模板【"+att.getTemplate_name()+"】管理费用科目为空！");
			}
			//获取坏账准备期末余额
			entityMap.put("subj_code", att.getDebit_subj_code1());
			Double bad_money = accBudgLederMapper.queryEndMoneyBySubj(entityMap);
			if(bad_money == null){
				bad_money = 0.0;
			}
			
			//判断生成凭证的方式用来生成坏账准备金额
			Double sum_money = 0.0;
			if(!"0".equals(att.getRate())){
				//余额百分比法，坏账准备金额 = 应收科目的期末余额合计 * 百分比 - 坏账准备科目期末余额
				//获取应收科目本期总余额
				Double incom_money = accBudgLederMapper.queryEndMoneyByDetailSubj(entityMap);
				if(incom_money == null){
					incom_money = 0.0;
				}
				
				sum_money = incom_money / 100 * att.getRate();
			}else{
				//账龄分析法，坏账准备金额 = sum(每个科目的总余额 * max(当前凭证日期 - 到期日期)所在的账龄区间的比例)
				//获取账龄区间
				List<Map<String, Object>> ranList = accBudgLederMapper.queryAccBudgRangeList(entityMap);
				if(ranList.size() > 0){
					return "{\"error\":\"请先维护账龄区间表！\"}";
				}
				
				//获取收入科目辅助核算余额（带到期日期）
				entityMap.put("vouch_date", vouch_date);
				List<Map<String, Object>> checkList = accBudgLederMapper.queryCheckEndMoneyByDetailSubj(entityMap);
				long betwDays = 0;
				boolean is_have = false;
				StringBuffer errorMsg = new StringBuffer();
				for(Map<String, Object> tmpMap : checkList){
					//没有维护到期日期的辅助核算不参与计算
					if(tmpMap.get("DUE_DATE") == null || "".equals(tmpMap.get("DUE_DATE").toString())){
						continue;
					}
					//获取当前凭证日期 - 辅助核算的到期日期 的差额天数
					betwDays = (vouch_date.getTime() - ((Date)tmpMap.get("DUE_DATE")).getTime()) / (1000 * 60 * 60 * 24);
					if(betwDays < 0){
						//未到期不是坏账不参与计算
						continue;
					}
					
					is_have = false;
					for(Map<String, Object> ranMap : ranList){
						if(ranMap.get("BEGIN_DAYS") == null || ranMap.get("END_DAYS") == null){
							continue;
						}
						//判断差额天数所在的账龄区间
						if(betwDays > Long.parseLong(ranMap.get("BEGIN_DAYS").toString()) && betwDays <= Long.parseLong(ranMap.get("END_DAYS").toString())){
							//所在账龄区间没有维护提前比例则跳出
							if(ranMap.get("RANGE_PRO") == null || "".equals(ranMap.get("RANGE_PRO").toString())){
								break;
							}
							//累计每个辅助核算的计提金额
							sum_money += Double.parseDouble(tmpMap.get("END_OS").toString()) / 100 * Double.parseDouble(ranMap.get("RANGE_PRO").toString());
							is_have = true;
							break;
						}
					}
					
					//没有找到账龄区间
					if(!is_have){
						errorMsg.append(betwDays).append("天，");
					}
				}
				
				if(errorMsg.length() > 0){
					return "{\"error\":\""+errorMsg.toString()+"不存在对应的账龄区间或者账龄区间的提取比例为空！\"}";
				}
			}
			
			//计算计提金额
			vouch_money = sum_money - bad_money;
			//判断金额是否为0
			if(vouch_money == null || vouch_money == 0){
				return "{\"error\":\"计提后的金额为零，不能坏账计提！\"}";
			}

			//计提：贷方为坏账科目且大于0；冲减：贷方为坏帐科目负数或者借方为坏账科目正数
			boolean is_chongjian = false;
			if(vouch_money < 0){
				summary = "冲减-";
				is_chongjian = true;
				vouch_money = -1*vouch_money;
			}

			/****************************
			 * 组装凭证坏账准备分录信息
			 ***************************************/
			Map<String, Object> detailMap = new HashMap<String, Object>();
			detailMap.put("group_id", group_id);
			detailMap.put("hos_id", hos_id);
			detailMap.put("copy_code", copy_code);
			detailMap.put("vouch_id", vouch_id);
			detailMap.put("vouch_row", vouch_row);
			detailMap.put("subj_code", att.getDebit_subj_code1());
			detailMap.put("summary", summary + att.getSummary());
			detailMap.put("debit", is_chongjian ? vouch_money : 0);
			detailMap.put("credit", is_chongjian ? 0 : vouch_money);
			detailMap.put("debit_w", 0);
			detailMap.put("credit_w", 0);
			// 存放分录
			detailList.add(detailMap);
			
			vouch_row += 1; // 分录行数加1

			/****************************
			 * 组装凭证管理费用分录信息
			 ***************************************/
			detailMap = new HashMap<String, Object>();
			detailMap.put("group_id", group_id);
			detailMap.put("hos_id", hos_id);
			detailMap.put("copy_code", copy_code);
			detailMap.put("vouch_id", vouch_id);
			detailMap.put("vouch_row", vouch_row);
			detailMap.put("subj_code", att.getCredit_subj_code1());
			detailMap.put("summary", summary + att.getSummary());
			detailMap.put("debit", is_chongjian ? 0 : vouch_money);
			detailMap.put("credit", is_chongjian ? vouch_money : 0);
			detailMap.put("debit_w", 0);
			detailMap.put("credit_w", 0);
			// 存放分录
			detailList.add(detailMap);

			/****************************
			 * 组装管理费用辅助核算信息
			 ***************************************/
			if (att.getCredit_is_check1() == 1) {
				// 提取科室比例
				List<AccTermendTemplateDept> attdList = accTermendTemplateDeptMapper.queryAccTermendTemplateDeptByCode(entityMap);
				if(attdList == null || attdList.size() == 0){
					return "{\"error\":\"生成失败，请维护科室比例！\"}";
				}
				// 提取科室总比例
				Double sumRate = accTermendTemplateDeptMapper.queryDeptSumByCode(entityMap);
				// 查询科目是否包含部门辅助核算
				Map<String, Object> queryCheckMap = new HashMap<String, Object>();
				queryCheckMap.put("group_id", att.getGroup_id());
				queryCheckMap.put("hos_id", att.getHos_id());
				queryCheckMap.put("copy_code", att.getCopy_code());
				queryCheckMap.put("acc_year", att.getAcc_year());
				queryCheckMap.put("subj_code", att.getCredit_subj_code1());
				queryCheckMap.put("table_code", "HOS_DEPT_DICT");
				String checkColumn = accTermendTemplateMapper.querySubjCheckColumnByTableCode(queryCheckMap);
				// 判断是否包含部门辅助核算
				if (checkColumn != null && !"".equals(checkColumn)) {
					Double checkMoney = 0.0; // 辅助核算金额
					Double checkMoneySum = 0.0; // 辅助核算总金额
					int index = 0; // 循环次数
					int maxIndex = attdList.size() - 1; // 循环总次数
					// 准备循环科室组装辅助核算数据
					for (AccTermendTemplateDept attd : attdList) {
						Map<String, Object> checkMap = new HashMap<String, Object>();
						checkMap.put("group_id", group_id);
						checkMap.put("hos_id", hos_id);
						checkMap.put("copy_code", copy_code);
						checkMap.put("vouch_id", vouch_id);
						checkMap.put("vouch_row", vouch_row);
						checkMap.put("vouch_check_id", check_row);
						checkMap.put("subj_code", att.getCredit_subj_code1()); // 科目编码
						checkMap.put("summary", att.getSummary()); // 摘要
						if (index == maxIndex) {
							// 最后一个辅助核算的金额应为：分录金额 - 辅助核算总金额(除了该辅助核算)
							checkMap.put("debit", is_chongjian ? 0: vouch_money - checkMoneySum);
							checkMap.put("credit", is_chongjian ? vouch_money - checkMoneySum : 0);
						} else {
							// 正常辅助核算金额为：分录金额 * 科室比重(即：科室比例 / 科室总比例)
							checkMoney = NumberUtil.numberToRound(vouch_money * (attd.getRate() / sumRate));
							checkMoneySum += checkMoney;
							checkMap.put("debit", is_chongjian ? 0: checkMoney);
							checkMap.put("credit", is_chongjian ? checkMoney : 0);
						}
						checkMap.put("debit_w", 0);
						checkMap.put("credit_w", 0);

						checkMap.put(checkColumn + "_id", attd.getDept_id()); // 科室ID
						checkMap.put(checkColumn + "_no", attd.getDept_no()); // 科室NO

						if (checkMoney != 0) {
							vouchCheckList.add(checkMap);
							check_row += 1;
							checkItem.put(checkColumn + "_id", checkColumn + "_id");
							checkItem.put(checkColumn + "_no", checkColumn + "_no");
						}
						index++; // 循环次数+1
					}
				}
			}
			vouch_row += 1; // 分录行数加1

			boolean is_frist = true;
			for (Map<String, Object> map : vouchCheckList) {
				for (Map.Entry<String, String> entry : checkItem.entrySet()) {
					if (is_frist) {
						column += ("," + entry.getKey());
						column_value += (", #{item." + entry.getKey() + ",jdbcType=INTEGER}");
					}
					if (!map.containsKey(entry.getKey())) {
						map.put(entry.getKey(), null);
					}
				}
				is_frist = false;
			}

			// 日志信息
			Map<String, Object> logMap = new HashMap<String, Object>();
			logMap.put("group_id", group_id);
			logMap.put("hos_id", hos_id);
			logMap.put("copy_code", copy_code);
			logMap.put("vouch_id", vouch_id);
			logMap.put("business_no", att.getTemplate_id());
			logMap.put("busi_type_code", att.getTemplate_type_code());
			logMap.put("template_code", att.getTemplate_id());
			logMap.put("create_date", date);

			// 操作数据库
			superVouchMapper.saveAutoVouch(vouchMap);
			superVouchMapper.saveAutoVouchDetail(detailList);
			superVouchMapper.saveAccAutoCheckByTermend(column, column_value, vouchCheckList);
			accTermendTemplateMapper.saveAutoVouchLogTemp(logMap);
			
			return "{\"state\":\"true\",\"vouch_id\":\""+vouch_id+"\"}";
			
		}catch(Exception e){
			
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"{生成失败！\"}");
		}
	}
    /**
     * 计提坏账   撤销计提
     * @param mapVo
     * @return
     */
	@Override
	public String delBadDebtsCancel(Map<String, Object> entityMap) throws DataAccessException{
		try {
			//添加凭证号
			//entityMap.put("vouch_no", accVouchService.queryAccVouchMaxVouchNo(entityMap));
			//有问题！！！！！！！！！！！！
			return accBudgLederMapper.delBadDebtsCancel(entityMap);
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  delBadDebtsCancel\"}";
		}
	}

	//科室比例查询
	@Override
	public String queryAccBadDept(Map<String, Object> entityMap) throws DataAccessException {
		List<AccTermendTemplateDept> list = accTermendTemplateDeptMapper.queryAccTermendTemplateDept(entityMap);
		return ChdJson.toJson(list);
	}

	/**
	 * @Description 期末处理模板<BR>
	 *              保存科室比例
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String saveAccBadDept(Map<String, Object> entityMap) throws DataAccessException {

		if (entityMap.get("deptData") == null) {
			return "{\"error\":\"无科室数据\"}";
		}
		try {
			// 保存明细数据
			JSONArray json = JSONArray.parseArray((String) entityMap.get("deptData"));
			List<Map<String, Object>> list_template_detail_batch = new ArrayList<Map<String, Object>>();
			Iterator it = json.iterator();
			while (it.hasNext()) {
				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
				if (!"".equals(jsonObj.get("rate")) && !"0".equals(jsonObj.get("rate").toString())
						&& !"0.00".equals(jsonObj.get("rate").toString()) && jsonObj.get("rate") != null) {
					Map<String, Object> mapDetailVo = new HashMap<String, Object>();
					mapDetailVo.put("group_id", entityMap.get("group_id"));
					mapDetailVo.put("hos_id", entityMap.get("hos_id"));
					mapDetailVo.put("copy_code", entityMap.get("copy_code"));
					mapDetailVo.put("template_id", entityMap.get("template_id"));
					mapDetailVo.put("dept_id", jsonObj.get("dept_id"));
					mapDetailVo.put("rate", jsonObj.get("rate"));
					list_template_detail_batch.add(mapDetailVo);
				}
			}
			if (list_template_detail_batch.size() > 0) {
				// 删除模板明细数据
				accTermendTemplateDeptMapper.deleteAccTermendTemplateDeptForAdd(list_template_detail_batch);
				// 保存模板明细表
				accTermendTemplateDeptMapper.addAccTermendTemplateDept(list_template_detail_batch);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"保存失败！\"}");
			// return "{\"error\":\"保存失败 数据库异常 请联系管理员! 错误编码 saveAccBadDept\"}";
		}
		return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
	}

	/**
	 * @Description 期末处理模板<BR>
	 *              提取科室收入
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String saveAccBadGetDeptIncom(Map<String, Object> entityMap) throws DataAccessException {
		try {
			List<Map<String, Object>> list_template_dept_batch = new ArrayList<Map<String, Object>>();
			// 获取各科室总收入
			List<Map<String, Object>> listDept = accTermendTemplateDeptMapper.queryAccTermendTemplateDeptIncom(entityMap);
			// 获取所有科室总收入
			Double sumMoney = accTermendTemplateDeptMapper.queryAccTermendTemplateDeptIncomSum(entityMap);

			if (sumMoney != null && sumMoney != 0) {
				// 科室收入转换为比例
				double rate = 0;

				for (Map<String, Object> map : listDept) {
					Map<String, Object> utilMap = new HashMap<String, Object>();
					// 科室总收入/所有科室总收入
					rate = ((Double) (map.get("money") == null ? 0.0D : Double.valueOf(map.get("money").toString())))
							.doubleValue() / sumMoney.doubleValue();

					utilMap.put("group_id", entityMap.get("group_id"));
					utilMap.put("hos_id", entityMap.get("hos_id"));
					utilMap.put("copy_code", entityMap.get("copy_code"));
					utilMap.put("template_id", entityMap.get("template_id"));
					utilMap.put("dept_id", map.get("dept_id"));
					utilMap.put("rate", rate);
					list_template_dept_batch.add(utilMap);
				}
				// 删除老数据
				accTermendTemplateDeptMapper.deleteAccTermendTemplateDeptForImport(entityMap);
				// 插入数据
				accTermendTemplateDeptMapper.addAccTermendTemplateDept(list_template_dept_batch);
				return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
			} else {
				return "{\"error\":\"科室总收入为空,不能保存.\"}";
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败！\"}");
			// return "{\"error\":\"保存失败 数据库异常 请联系管理员! 错误编码 saveAccBadDept\"}";
		}
	}

	@Override
	public String saveAccBadGetDeptIncomAcc(Map<String, Object> entityMap) {
		try {
			List<Map<String, Object>> list_template_dept_batch = new ArrayList<>();

			this.accTermendTemplateDeptMapper.deleteAccTermendTemplateDeptForImport(entityMap);

			List<Map<String, Object>> listDept = this.accTermendTemplateDeptMapper
					.queryAccTermendTemplateDeptIncomAcc(entityMap);

			Double sumMoney = this.accTermendTemplateDeptMapper.queryAccTermendTemplateDeptIncomSumAcc(entityMap);
			if ((sumMoney != null) && (sumMoney.doubleValue() != 0.0D)) {
				double rate = 0.0D;
				for (Map<String, Object> map : listDept) {
					Map<String, Object> utilMap = new HashMap<>();

					rate = ((Double) (map.get("money") == null ? 0.0D : Double.valueOf(map.get("money").toString())))
							.doubleValue() / sumMoney.doubleValue();

					utilMap.put("group_id", entityMap.get("group_id"));
					utilMap.put("hos_id", entityMap.get("hos_id"));
					utilMap.put("copy_code", entityMap.get("copy_code"));
					utilMap.put("template_id", entityMap.get("template_id"));
					utilMap.put("dept_id", map.get("dept_id"));
					utilMap.put("rate", Double.valueOf(rate));
					list_template_dept_batch.add(utilMap);
				}
				this.accTermendTemplateDeptMapper.addAccTermendTemplateDept(list_template_dept_batch);
				return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
			}
			return "{\"error\":\"科室总收入为空，不能保存.\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");
		}
	}
}
