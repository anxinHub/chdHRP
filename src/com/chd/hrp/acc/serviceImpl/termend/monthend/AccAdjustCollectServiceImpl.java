/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.acc.serviceImpl.termend.monthend;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.exception.SysException;
import com.chd.base.util.DateUtil;
import com.chd.base.util.JsonListMapUtil;
import com.chd.base.util.NumberUtil;
import com.chd.base.util.UUIDLong;
import com.chd.hrp.acc.dao.termend.AccTermendTemplateMapper;
import com.chd.hrp.acc.dao.termend.monthend.AccAdjustCollectMapper;
import com.chd.hrp.acc.entity.AccCurRate;
import com.chd.hrp.acc.service.termend.monthend.AccAdjustCollectService;
import com.chd.hrp.acc.service.vouch.SuperVouchService;

/**
 * @Title. @Description. 期末调汇<BR>
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("accAdjustCollectService")
public class AccAdjustCollectServiceImpl implements AccAdjustCollectService {

	private static Logger logger = Logger.getLogger(AccAdjustCollectServiceImpl.class);

	@Resource(name = "accAdjustCollectMapper")
	private final AccAdjustCollectMapper accAdjustCollectMapper = null;
	@Resource(name = "superVouchService")
	private final SuperVouchService superVouchService = null;
	@Resource(name = "accTermendTemplateMapper")
	private final AccTermendTemplateMapper accTermendTemplateMapper = null;

	@Override
	public String queryAccAdjustCollectCurRateByCode(Map<String, Object> entityMap) throws DataAccessException {
		try {
			AccCurRate accCurRate = new AccCurRate(); 
			accCurRate = accAdjustCollectMapper.queryAccAdjustCollectCurRateByCode(entityMap);
			String reStr = "";
			if(accCurRate != null){
				reStr = "{\"change\":\"true"+
					"\",\"cur_code\":\""+accCurRate.getCur_code()+
					"\",\"cur_name\":\""+accCurRate.getCur_name()+
					"\",\"start_rate\":\""+accCurRate.getStart_rate()+
					"\",\"avg_rate\":\""+accCurRate.getAvg_rate()+
					"\",\"end_rate\":\""+accCurRate.getEnd_rate()+
					"\"}";
			}else{
				reStr = "{\"change\":\"false\"}";
			}
			return reStr;
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  addAccAdjustCollect\"}";
		}
	}
	
	/**
	 * 1.保存币种汇率信息（更新或插入）。
	 * 2.校验本期是否包含需要调汇的科目。
	 * 3.按ACC_CUR表中CUR_PLAN字段计算汇率（0：原币*汇率 = 本位币，1：原币/汇率 = 本位币）
	 * 4.如果该凭证借贷不相等需要《汇兑损益科目》平衡金额
	 */
	@Override
	public String addAccAdjustCollectVouch(Map<String, Object> entityMap) throws DataAccessException {
		String vouch_id, vouch_detail_id;
		String summary;
		//存放凭证主表信息
		Map<String, Object> vouchMap = new HashMap<String, Object>();
		//存放凭证分录信息
		List<Map<String, Object>> vouchDetailList = new ArrayList<Map<String,Object>>();
		
		try{
			//如果本期间已生成凭证则不允许生成凭证
			entityMap.put("template_type_code", "Z002");
			int flag = accTermendTemplateMapper.existsAccVouchByTemplate(entityMap);
			if(flag > 0){
				return "{\"error\":\"本期间已生成凭证，不能重复生成!\"}";
			}
			/*******************处理本月汇率*************************/
			int is_exists = accAdjustCollectMapper.existsAccAdjustCollectCurRateByCode(entityMap);
			if(is_exists == 0){
				accAdjustCollectMapper.addAccAdjustCollectCurRate(entityMap);
			}else{
				accAdjustCollectMapper.updateAccAdjustCollectCurRate(entityMap);
			}
			//获取汇率科目与调汇金额
			List<Map<String, Object>> curList = JsonListMapUtil.toListMapLower(accAdjustCollectMapper.queryCurSubjMoneyList(entityMap));
			if(curList.size() == 0){
				return "{\"error\":\"没有需要调汇的科目，不能进行期末调汇!\"}";
			}
			//记录贷方发生合计
			Double creditMoney = 0.00; 
			//记录借方发生合计
			Double debitMoney = 0.00;
			
			/*************************组装凭证主表信息*******************************/
			vouch_id = UUIDLong.absStringUUID();
			vouchMap.put("group_id", entityMap.get("group_id"));
			vouchMap.put("hos_id", entityMap.get("hos_id"));
			vouchMap.put("copy_code", entityMap.get("copy_code"));
			vouchMap.put("vouch_id", vouch_id);  //凭证ID
			vouchMap.put("vouch_type_code", entityMap.get("vouch_type_code"));  //凭证类型
			vouchMap.put("vouch_date", accTermendTemplateMapper.queryAccVouchDateByYearMonth(vouchMap));  //凭证日期
			String vouchNo=superVouchService.queryMaxVouchNo(entityMap);  //添加凭证号
			if(vouchNo.indexOf("error")!=-1){
				return vouchNo;
			}
			vouchMap.put("vouch_no", vouchNo);  //凭证号
			summary = entityMap.get("summary").toString();
			vouchMap.put("summary", summary);  //摘要
			
			/****************************循环组装凭证借方分录信息***************************************/
			for(Map<String, Object> map : curList){
				Map<String, Object> detailDebitMap = new HashMap<String, Object>();
				detailDebitMap.put("vouch_id", vouch_id);  //凭证ID
				vouch_detail_id = UUIDLong.absStringUUID();
				detailDebitMap.put("vouch_detail_id", vouch_detail_id);  //分录ID
				detailDebitMap.put("subj_code", map.get("subj_code"));  //借方科目编码
				detailDebitMap.put("summary", summary);  //分录摘要
				if("0".equals(map.get("subj_dire").toString())){//0借，1贷
					detailDebitMap.put("debit", (Double)map.get("money"));  //分录借方金额
					detailDebitMap.put("credit", 0);  //分录贷方金额
					debitMoney += (Double) map.get("money");
				}else{
					detailDebitMap.put("debit", 0);  //分录借方金额
					detailDebitMap.put("credit", (Double)map.get("money"));  //分录贷方金额
					creditMoney += (Double) map.get("money");
				}
				
				/*************************组装辅助核算信息*************************************/
				//获取模板中支出收入科目辅助账本期发生金额
				entityMap.put("subj_code", map.get("subj_code"));
				List<Map<String, Object>> checkList = accAdjustCollectMapper.queryCurSubjCheckMoneyListByCode(entityMap);
				for(Map<String, Object> checkMap : checkList){
					checkMap.put("id", vouch_id);  //凭证ID
					checkMap.put("summary", summary);  //摘要
					checkMap.put("cid", UUIDLong.absStringUUID());  //辅助核算ID
					if("0".equals(map.get("subj_dire").toString())){//0借，1贷
						checkMap.put("debit", (Double)checkMap.get("money"));  //分录借方金额
						checkMap.put("credit", 0);  //分录贷方金额
					}else{
						checkMap.put("debit", 0);  //分录借方金额
						checkMap.put("credit", (Double)checkMap.get("money"));  //分录贷方金额
					}
				}
				detailDebitMap.put("Rows", JsonListMapUtil.listToString(checkList));
				vouchDetailList.add(detailDebitMap); 
			}
			
			/*************************组装损益科目分录*************************************/
			if(debitMoney - creditMoney == 0){
				
			}
			
		}catch(Exception e){
			logger.error(e.getMessage());
			
			throw new SysException("{\"error\":\"{生成失败！\"}");
		}
		return "";
	}
	
}
