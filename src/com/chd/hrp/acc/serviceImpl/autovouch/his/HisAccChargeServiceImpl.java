/**
 @Copyright: Copyright (c) 2015-2-14 
 @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.acc.serviceImpl.autovouch.his;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.UUIDLong;
import com.chd.hrp.acc.dao.autovouch.AccBusiTemplateDetailMapper;
import com.chd.hrp.acc.dao.autovouch.AccMedAutoVouchMapper;
import com.chd.hrp.acc.dao.autovouch.his.HisAccChargeMapper;
import com.chd.hrp.acc.dao.vouch.SuperVouchMapper;
import com.chd.hrp.acc.service.autovouch.his.HisAccChargeService;
import com.chd.hrp.acc.service.vouch.SuperVouchService;

@Service("hisAccChargeService")
public class HisAccChargeServiceImpl implements HisAccChargeService {

	private static Logger logger = Logger.getLogger(HisAccChargeServiceImpl.class);

	@Resource(name = "accMedAutoVouchMapper")
	private final AccMedAutoVouchMapper accMedAutoVouchMapper = null;
	
	@Resource(name = "hisAccChargeMapper")
	private final HisAccChargeMapper hisAccChargeMapper = null;
	
	@Resource(name = "superVouchMapper")
	private final SuperVouchMapper superVouchMapper = null;
	
	@Resource(name = "accBusiTemplateDetailMapper")
	private final AccBusiTemplateDetailMapper accBusiTemplateDetailMapper = null;
	
	@Resource(name = "superVouchService")
	private final SuperVouchService superVouchService = null;
	
	//查询凭证模板
	@Override
	public String queryAutoBusiTemplate(Map<String, Object> map) throws DataAccessException {
		return JSON.toJSONString(hisAccChargeMapper.queryAutoBusiTemplate(map));
	}
	
	
	/*************************************门诊收费**********************************************************/
	/**
	 * 查询门诊收费类别-表头
	 * */
	@Override
	public String queryHisChargeHeadO(Map<String, Object> entityMap) throws DataAccessException {
		try{
			return ChdJson.toJsonLower(hisAccChargeMapper.queryHisChargeHeadO(entityMap));
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}
	}

	/**
	 * 查询门诊收费数据
	 * */
	@Override
	public String queryHisChargeDataO(Map<String, Object> entityMap)throws DataAccessException {
		// TODO Auto-generated method stub
		String[] column=entityMap.get("column_name").toString().split(",");
		if(column==null || column.length==0){
			return "{\"error\":\"表头为空！\"}";
		}
		
		StringBuilder columnSql=new StringBuilder();
		StringBuilder columnSumSql=new StringBuilder();
		
		if(entityMap.get("sum_type_id").toString().equals("1")){
			
			//按收款员
			columnSumSql.append(" null rep_no,'合计' charge_code,to_char(sum(a.charge_money),'999,999,990.00') charge_name ");
			entityMap.put("vouch_no_sum",",'-' vouch_no,null vouch_id ");
			columnSql.append(" a.rep_no,a.charge_code,a.charge_name ");
			entityMap.put("group_sql", " a.rep_no,a.charge_code,a.charge_name ");
			entityMap.put("order_sql", " a.charge_code ");
			entityMap.put("left_sql"," ");
			entityMap.put("vouch_no",",t.vouch_type_short||'-'||v.vouch_no vouch_no,v.vouch_id ");
			StringBuilder sb=new StringBuilder();
			sb.append(" left join acc_busi_log_0102 bl on bl.group_id="+entityMap.get("group_id")+" and bl.hos_id="+entityMap.get("hos_id")+" and bl.copy_code='"+entityMap.get("copy_code")+"' ");
			sb.append(" and bl.busi_type_code='0102' and bl.business_no=t1.rep_no||'-'||t1.charge_code ");
			sb.append(" left join acc_vouch v on bl.group_id=v.group_id and bl.hos_id=v.hos_id and bl.copy_code=v.copy_code and bl.vouch_id=v.vouch_id ");
			sb.append(" left join acc_vouch_type t on v.group_id=t.group_id and v.hos_id=t.hos_id and v.copy_code=t.copy_code and v.vouch_type_code=t.vouch_type_code ");
			entityMap.put("vouch_sql",sb.toString());
			
		}else{
			
			//按科室
			columnSumSql.append(" null rep_no,'合计' dept_code,to_char(sum(a.charge_money),'999,999,990.00') dept_name ");
			columnSql.append(" a.rep_no,d.dept_code,d.dept_name ");
			entityMap.put("group_sql", " a.rep_no,d.dept_code,d.dept_name ");
			entityMap.put("order_sql", " d.dept_code ");
			entityMap.put("left_sql", " left join hos_dept_dict d on a.group_id=d.group_id and a.hos_id=d.hos_id and a.dept_id=d.dept_id and a.dept_no=d.dept_no ");
		}
		
		
		for(String s :column){
			columnSumSql.append(",null as c_"+s+" ");
			columnSql.append(",to_char(sum(decode(k.charge_kind_code,'"+s+"',a.charge_money,null)),'999,999,990.00') as c_"+s+" ");
		}
		
		entityMap.put("column_sum_sql", columnSumSql.toString());
		entityMap.put("column_sql", columnSql.toString());
		try{
			return ChdJson.toJsonLower(hisAccChargeMapper.queryHisChargeDataO(entityMap));
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}
	}

	//查询门诊结算数据 
	@Override
	public String queryHisBalO(Map<String, Object> map)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try{
			return ChdJson.toJsonLower(hisAccChargeMapper.queryHisBalO(map));
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}
	}

	//查询门诊收费、结算凭证json
	@Override
	public String queryHisChargeVouchO(Map<String, Object> map)throws Exception {
		// TODO Auto-generated method stub
		
		try{

			String vouch_date=map.get("vouch_date").toString();
			String accYear=vouch_date.substring(0, 4);
			String accMonth=vouch_date.substring(5, 7);
			map.put("mod_code", "01");
			map.put("acc_year", accYear);
			List<Map<String, Object>> tempList=accBusiTemplateDetailMapper.queryAccBusiTemplateByCode(map);
			if(tempList==null || tempList.size()==0){
				return "{\"error\":\"没有配置凭证模板！\"}";
			}
			
			
			//处理单据号
			String busNo=map.get("busi_no").toString();
			if(!busNo.equals("")){
				//处理日报序号+收款员条件and a.rep_no+a.charge_code in(${busi_code})
				busNo=busNo.replace(",", "','");
				busNo="'"+busNo+"'";
				map.put("busi_no", busNo);
			}
			//判断是否生成凭证
			map.put("busi_type_code", "0102");
			map.put("busi_log_table", "ACC_BUSI_LOG_0102");
			List<String> busiNoLis=hisAccChargeMapper.queryAutoVouchIsCreate(map);
			if (busiNoLis!=null && busiNoLis.size()>0) {
				return "{\"error\":\"有数据已经生成凭证！\"}";
			}
			
			
			String selCode=map.get("sel_code").toString();
			if(!selCode.equals("")){
				//处理收款员条件and a.charge_code in(${sel_code})
				selCode=selCode.replace(",", "','");
				selCode="'"+selCode+"'";
				map.put("sel_code", selCode);
			}
			
			List<List<Map<String, Object>>> detailList=new ArrayList<List<Map<String, Object>>>();
			List<Map<String, Object>> metaList=null;
			Map<Integer,List<Map<String, Object>>> checkMap=new HashMap<Integer,List<Map<String, Object>>>();
			Map<Integer,List<Map<String, Object>>> cashMap=new HashMap<Integer,List<Map<String, Object>>>();
			int isDetailSummary=Integer.parseInt(tempList.get(0).get("IS_DETAIL_SUMMARY").toString());
			
			//拼日期摘要
			String summary="";
			if(map.get("charge_date_b").toString().equalsIgnoreCase(map.get("charge_date_e").toString())){
				summary=map.get("charge_date_b").toString()+"日";
			}else{
				summary=Integer.parseInt(map.get("charge_date_b").toString().substring(8,10))+"-"+Integer.parseInt(map.get("charge_date_e").toString().substring(8,10))+"日";
			}
			
			//查询自动核销的票据类型
			List<String> paperType=hisAccChargeMapper.queryAccPaperTypeByAuto(map);
			
			
			int index=0;
			String cashSubjWhere="";
			String bankSubjWhere="";
			for(Map<String, Object> tempMap:tempList){
				
				String metaCode=tempMap.get("META_CODE").toString();
				map.put("direction", tempMap.get("DIRECTION"));
				map.put("vouch_row", tempMap.get("VOUCH_ROW"));
				
				//0不按明细摘要，1按明细摘要
				if(isDetailSummary==0){
					map.put("summary",tempMap.get("M_SUMMARY"));
				}else{
					map.put("summary",tempMap.get("D_SUMMARY")+summary);
				}
				
				if(metaCode.equals("0101")){
					
					//现金/银行
					map.put("meta_code", "0101");
					metaList=hisAccChargeMapper.queryHisBalVouchO_0101(map);
					if(metaList!=null && metaList.size()>0){
						//分录
						detailList.add(metaList);
						
						cashSubjWhere="";
						bankSubjWhere="";
						for(Map<String, Object> detailMap:metaList){
							if(detailMap.get("is_cash").toString().equals("1")){
								//现金流量标注科目
								cashSubjWhere=cashSubjWhere+detailMap.get("SUBJ_CODE")+",";
							}
							if(detailMap.get("subj_nature_code").equals("03")){
								//银行科目
								bankSubjWhere=bankSubjWhere+detailMap.get("SUBJ_CODE")+",";
							}
						}
						
						//现金流量标注
						if(!cashSubjWhere.equals("")){
							cashSubjWhere=cashSubjWhere.substring(0, cashSubjWhere.length()-1);
							map.put("subjWhere", cashSubjWhere);
							//metaList=hisAccChargeMapper.queryHisChargeVouchCashO_0101(map);
							cashMap.put(index, metaList);
						}
						
						//按票据号生成辅助核算
						if(!bankSubjWhere.equals("") && paperType!=null && paperType.size()>0){
							bankSubjWhere=bankSubjWhere.substring(0, bankSubjWhere.length()-1);
							map.put("subjWhere", bankSubjWhere);
							metaList=hisAccChargeMapper.queryHisChargeVouchPaperCheckO(map);
							checkMap.put(index, metaList);
						}
						
						index++;
					}
					
				}else if(metaCode.equals("0102") || metaCode.equals("0110")){
					
					//预收医疗款-门诊、预收医疗款-门诊(结算方式)
					map.put("meta_code", metaCode);
					map.put("direction", 0);//借方
					map.put("state", "2");//冲销
					metaList=hisAccChargeMapper.queryHisPreVouchO_0102(map);
					if(metaList!=null && metaList.size()>0){
						detailList.add(metaList);
						index++;
					}
					
					map.put("direction", tempMap.get("DIRECTION"));
					map.put("state", "0,3");//交费（正常、作废）0正常1退费2冲销 3作废
					metaList=hisAccChargeMapper.queryHisPreVouchO_0102(map);
					if(metaList!=null && metaList.size()>0){
						detailList.add(metaList);
						index++;
					}
					
					map.put("state", "1");//退费
					metaList=hisAccChargeMapper.queryHisPreVouchO_0102(map);
					if(metaList!=null && metaList.size()>0){
						detailList.add(metaList);
						index++;
					}
					
					
				}else if(metaCode.equals("0103")){
					
					//应收医疗款-门诊
					map.put("meta_code", "0103");
					metaList=hisAccChargeMapper.queryHisBalVouchO_0101(map);
					if(metaList!=null && metaList.size()>0){
						//分录
						detailList.add(metaList);
						index++;
					}
					
				}else if(metaCode.equals("0109")){
					
					//优惠减免、根据支付方式，贷医疗收入
					map.put("meta_code", "0109");
					metaList=hisAccChargeMapper.queryHisBalVouchO_0109(map);
					if(metaList!=null && metaList.size()>0){
						//分录
						detailList.add(metaList);
						index++;
					}
					
				}else if(metaCode.equals("0104")){
					
					//医疗收入-门诊
					map.put("meta_code", "0104");
					metaList=hisAccChargeMapper.queryHisChargeVouchO_0104(map);
					if(metaList!=null && metaList.size()>0){
						//分录
						detailList.add(metaList);
						//科室辅助核算，按科目过滤分组
						String checkSubjId=superVouchService.getCheckSubjId(metaList,"CHECK1");
						if(checkSubjId!=null && !checkSubjId.equals("")){
								map.put("subj_id", checkSubjId.subSequence(0, checkSubjId.length()-1));
								metaList=hisAccChargeMapper.queryHisChargeVouchCheckO_0104(map);
								checkMap.put(index, metaList);
						
						}
						index++;
					}
					
				}
				
			}
			
			map.put("vouch_date",vouch_date);
			map.put("acc_month",accMonth);
			map.put("vouch_type_code", tempList.get(0).get("VOUCH_TYPE_CODE"));
			String json=superVouchService.getVouchJon(map,detailList,checkMap,cashMap);
			
			return json;
			
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}
		
	}
	//查询门诊收费、结算凭证json
	@Override
	public String saveHisChargeVouchO(Map<String, Object> map)throws Exception {
		// TODO Auto-generated method stub
		
		try{
			String groupId=map.get("group_id").toString();
			String hosId=map.get("hos_id").toString();
			String copyCode=map.get("copy_code").toString();
			String vouch_date=map.get("vouch_date").toString();
			String accYear=vouch_date.substring(0, 4);
			String accMonth=vouch_date.substring(5, 7);
			map.put("mod_code", "01");
			map.put("acc_year", accYear);
			List<Map<String, Object>> tempList=accBusiTemplateDetailMapper.queryAccBusiTemplateByCode(map);
			if(tempList==null || tempList.size()==0){
				return "{\"error\":\"没有配置凭证模板！\"}";
			}
			
			//处理单据号
			String busNo=map.get("busi_no").toString();
			if(!busNo.equals("")){
				//处理日报序号+收款员条件and a.rep_no+a.charge_code in(${busi_code})
				busNo=busNo.replace(",", "','");
				busNo="'"+busNo+"'";
				map.put("busi_no", busNo);
			}
			
			String vouchId=UUIDLong.absStringUUID();
			map.put("vouch_id", vouchId);
			Date createDate=DateUtil.getCurrenDate();
			map.put("create_date",createDate);//这个时间很重要是验证是否生成凭证的重要的 
			map.put("table", "ACC_CHARGE_O");
			hisAccChargeMapper.saveAutoVouchLogOutpatient(map);
			//判断是否生成凭证
			map.put("busi_type_code", "0102");
			map.put("busi_log_table", "ACC_BUSI_LOG_0102");
			List<String> busiNoLis=hisAccChargeMapper.queryAutoVouchIsCreate(map);
			if (busiNoLis!=null && busiNoLis.size()>0) {
				return "{\"error\":\"有数据已经生成凭证！\"}";
			}
			 
			
			/*添加自动凭证主表*/
			map.put("vouch_att_num", 0);//凭证附件
			map.put("vouch_type_code",tempList.get(0).get("VOUCH_TYPE_CODE").toString());//凭证类型
			Date vouchDate=DateUtil.stringToDate(vouch_date,"yyyy-MM-dd");
			map.put("vouch_date", vouchDate);
			superVouchMapper.saveAutoVouch(map);
			
			
			String selCode=map.get("sel_code").toString();
			if(!selCode.equals("")){
				//处理收款员条件and a.charge_code in(${sel_code})
				selCode=selCode.replace(",", "','");
				selCode="'"+selCode+"'";
				map.put("sel_code", selCode);
			}
			
			List<Map<String, Object>> metaList=null;
			int isDetailSummary=Integer.parseInt(tempList.get(0).get("IS_DETAIL_SUMMARY").toString());
			
			//拼日期摘要
			String summary="";
			if(map.get("charge_date_b").toString().equalsIgnoreCase(map.get("charge_date_e").toString())){
				summary=map.get("charge_date_b").toString()+"日";
			}else{
				summary=Integer.parseInt(map.get("charge_date_b").toString().substring(8,10))+"-"+Integer.parseInt(map.get("charge_date_e").toString().substring(8,10))+"日";
			}
			
			//查询自动核销的票据类型
			//List<String> paperType=hisAccChargeMapper.queryAccPaperTypeByAuto(map);
			
			
			int vouchRow=0;
			int direction=0;
			String cashSubjWhere="";
			String bankSubjWhere="";
			List<Map<String,Object>> detailList = new ArrayList<Map<String,Object>>();
			for(Map<String, Object> tempMap:tempList){
				 
				direction=Integer.parseInt(tempMap.get("DIRECTION").toString());
				
				String metaCode=tempMap.get("META_CODE").toString();
				map.put("direction", direction);
				map.put("vouch_row", tempMap.get("VOUCH_ROW"));
				
				//0不按明细摘要，1按明细摘要
				if(isDetailSummary==0){
					map.put("summary",tempMap.get("M_SUMMARY"));
				}else{
					map.put("summary",tempMap.get("D_SUMMARY")+summary);
				}
				
				if(metaCode.equals("0101")){
					
					//现金/银行
					map.put("meta_code", "0101");
					metaList=hisAccChargeMapper.queryHisBalVouchO_0101(map);
					if(metaList!=null && metaList.size()>0){
						//分录
						cashSubjWhere="";
						bankSubjWhere="";
						for(Map<String, Object> detailMap:metaList){
							
							vouchRow++;
							detailMap.put("group_id", groupId);
							detailMap.put("hos_id", hosId);
							detailMap.put("copy_code", copyCode);
							detailMap.put("vouch_id", vouchId);//凭证ID
							detailMap.put("vouch_row", vouchRow);//分录行号
							detailMap.put("direction", direction);//方向
							detailList.add(detailMap);
							
							int count=0;
							if(detailMap.get("is_bill").toString().equals("1")){
								//是否票据号核销
								//bankSubjWhere=bankSubjWhere+"'"+detailMap.get("SUBJ_CODE")+"',";
								detailMap.put("acc_year", accYear);
								detailMap.put("mod_code", map.get("mod_code"));
								detailMap.put("meta_code", metaCode);
								detailMap.put("charge_date_b", map.get("charge_date_b"));
								detailMap.put("charge_date_e", map.get("charge_date_e"));
								//插入辅助核算
								count=hisAccChargeMapper.saveHisChargeVouchPaperCheckO(detailMap);
								
							}
							
							if(detailMap.get("is_cash").toString().equals("1")){
								//现金流量
								if(count>0){
									//为了与辅助核算的条数保持一致
									hisAccChargeMapper.saveHisChargeVouchPaperCashO(detailMap);
								}else{
									hisAccChargeMapper.saveHisChargeVouchCashO_0101(detailMap);
								}
							}
							
						}
					}
					
				}else if(metaCode.equals("0102") || metaCode.equals("0110")){
					
					//预收医疗款-门诊、预收医疗款-门诊(结算方式)
					map.put("meta_code", metaCode);
					map.put("direction", 0);//借方
					map.put("state", "2");//冲销
					metaList=hisAccChargeMapper.queryHisPreVouchO_0102(map);
					if(metaList!=null && metaList.size()>0){
						for(Map<String, Object> detailMap:metaList){
							vouchRow++;
							detailMap.put("group_id", groupId);
							detailMap.put("hos_id", hosId);
							detailMap.put("copy_code", copyCode);
							detailMap.put("vouch_id", vouchId);//凭证ID
							detailMap.put("vouch_row", vouchRow);//分录行号
							detailMap.put("direction", direction);//方向
							detailList.add(detailMap);
						}
					}
					map.put("direction", tempMap.get("DIRECTION"));
					map.put("state", "0,3");//交费（正常、作废）0正常1退费2冲销 3作废
					metaList=hisAccChargeMapper.queryHisPreVouchO_0102(map);
					if(metaList!=null && metaList.size()>0){
						for(Map<String, Object> detailMap:metaList){
							vouchRow++;
							detailMap.put("group_id", groupId);
							detailMap.put("hos_id", hosId);
							detailMap.put("copy_code", copyCode);
							detailMap.put("vouch_id", vouchId);//凭证ID
							detailMap.put("vouch_row", vouchRow);//分录行号
							detailMap.put("direction", direction);//方向
							detailList.add(detailMap);
						}
					}
					
					map.put("state", "1");//退费
					metaList=hisAccChargeMapper.queryHisPreVouchO_0102(map);
					if(metaList!=null && metaList.size()>0){
						for(Map<String, Object> detailMap:metaList){
							vouchRow++;
							detailMap.put("group_id", groupId);
							detailMap.put("hos_id", hosId);
							detailMap.put("copy_code", copyCode);
							detailMap.put("vouch_id", vouchId);//凭证ID
							detailMap.put("vouch_row", vouchRow);//分录行号
							detailMap.put("direction", direction);//方向
							detailList.add(detailMap);
						}
					}
					
					
				}else if(metaCode.equals("0103")){
					//应收医疗款-门诊
					map.put("meta_code", "0103");
					metaList=hisAccChargeMapper.queryHisBalVouchO_0101(map);
					if(metaList!=null && metaList.size()>0){

						for(Map<String, Object> detailMap:metaList){
							vouchRow++;
							detailMap.put("group_id", groupId);
							detailMap.put("hos_id", hosId);
							detailMap.put("copy_code", copyCode);
							detailMap.put("vouch_id", vouchId);//凭证ID
							detailMap.put("vouch_row", vouchRow);//分录行号
							detailMap.put("direction", direction);//方向
							detailList.add(detailMap);
						}
						
					}
					
				}else if(metaCode.equals("0109")){
					
					//优惠减免、根据支付方式，贷医疗收入
					map.put("meta_code", "0109");
					metaList=hisAccChargeMapper.queryHisBalVouchO_0109(map);
					if(metaList!=null && metaList.size()>0){
						//分录
						for(Map<String, Object> detailMap:metaList){
							vouchRow++;
							detailMap.put("group_id", groupId);
							detailMap.put("hos_id", hosId);
							detailMap.put("copy_code", copyCode);
							detailMap.put("vouch_id", vouchId);//凭证ID
							detailMap.put("vouch_row", vouchRow);//分录行号
							detailMap.put("direction", direction);//方向
							detailList.add(detailMap);
						}
					}
					
				}else if(metaCode.equals("0104")){
					
					//医疗收入-门诊
					map.put("meta_code", "0104");
					metaList=hisAccChargeMapper.queryHisChargeVouchO_0104(map);
					if(metaList!=null && metaList.size()>0){
						//分录
						
						for(Map<String, Object> detailMap:metaList){
							vouchRow++;
							detailMap.put("group_id", groupId);
							detailMap.put("hos_id", hosId);
							detailMap.put("copy_code", copyCode);
							detailMap.put("vouch_id", vouchId);//凭证ID
							detailMap.put("vouch_row", vouchRow);//分录行号
							detailMap.put("direction", direction);//方向
							detailList.add(detailMap);
							
							if(detailMap.get("is_check").toString().equals("1")) {
								
								if(detailMap.get("check1")!=null && detailMap.get("check1").toString().equalsIgnoreCase("check1") ||
									detailMap.get("check2")!=null && detailMap.get("check2").toString().equalsIgnoreCase("check1") ||
									detailMap.get("check3")!=null && detailMap.get("check3").toString().equalsIgnoreCase("check1") ||
									detailMap.get("check4")!=null && detailMap.get("check4").toString().equalsIgnoreCase("check1")){
								//科室辅助核算
								detailMap.put("acc_year", accYear);
								detailMap.put("busi_no", map.get("busi_no"));
								detailMap.put("mod_code", map.get("mod_code"));
								detailMap.put("meta_code", metaCode);
								detailMap.put("charge_date_b", map.get("charge_date_b"));
								detailMap.put("charge_date_e", map.get("charge_date_e"));
								hisAccChargeMapper.saveHisChargeVouchCheckO_0104(detailMap);
								}
							}
							
						}
						
					}
					
				}
				
			}
			
			if(detailList!=null && detailList.size()>0){
				//保存自动凭证明细表
				superVouchMapper.saveAutoVouchDetail(detailList);
			}else{
				throw new SysException("没有明细表数据或者没有对应关系！");
			}
			
			return "{\"auto_id\": \""+vouchId+"\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
			
		}
		
	}
	
	
	public void saveAccAutoCheckO(Map<String,Object> map,List<Map<String,Object>> metaList , int vouchRow,String vouchId ,int direction){
		
		String groupId=map.get("group_id").toString();
		String hosId=map.get("hos_id").toString();
		String copyCode=map.get("copy_code").toString();
		String vouch_date=map.get("vouch_date").toString();
		String accYear=vouch_date.substring(0, 4);
		//现金流量标注
		Map<String,Object> cashMap =hisAccChargeMapper.queryHisChargeVouchCashO_0101(map);
		for (Map<String, Object> detailMap : metaList) {
		
			
			if(detailMap.get("is_check")!=null && detailMap.get("is_check").toString().equals("1")){
				//科室辅助核算
				detailMap.put("vouch_date", vouch_date);//凭证日期
				detailMap.put("charge_date_b", map.get("charge_date_b"));//日期
				detailMap.put("charge_date_e", map.get("charge_date_e"));//
				detailMap.put("meta_code", map.get("meta_code"));
				detailMap.put("io_type", map.get("io_type"));
				detailMap.put("vouch_id", vouchId);
				detailMap.put("vouch_row", vouchRow);
				detailMap.put("acc_year", accYear);
				detailMap.put("direction", direction);
				hisAccChargeMapper.saveAccAutoCheckI_0108(detailMap);
			}
			if(detailMap.get("is_cash")!=null && detailMap.get("is_cash").toString().equals("1")){
				//科室辅助核算
				detailMap.put("vouch_date", vouch_date);//凭证日期
				detailMap.put("charge_date_b", map.get("charge_date_b"));//日期
				detailMap.put("charge_date_e", map.get("charge_date_e"));//
				detailMap.put("meta_code", map.get("meta_code"));
				detailMap.put("cash_money", Double.parseDouble(map.get("debit").toString())+Double.parseDouble(map.get("credit").toString()));
				detailMap.put("vouch_id", vouchId);
				detailMap.put("vouch_row", vouchRow);
				detailMap.put("acc_year", accYear);
				detailMap.put("direction", direction);
				detailMap.put("cash_item_id", cashMap.get("cash_item_id"));
				hisAccChargeMapper.saveAccAutoCash(detailMap);
			}
		}
		
		
	}
	
	

	/*************************************住院收费**********************************************************/
	//查询住院收费类别-表头
	@Override
	public String queryHisChargeHeadI(Map<String, Object> entityMap)throws DataAccessException {
		try{
			return ChdJson.toJsonLower(hisAccChargeMapper.queryHisChargeHeadI(entityMap));
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}
	}

	//查询住院收费数据
	@Override
	public String queryHisChargeDataI(Map<String, Object> entityMap)throws DataAccessException {
		String[] column=entityMap.get("column_name").toString().split(",");
		if(column==null || column.length==0){
			return "{\"error\":\"表头为空！\"}";
		}
		
		StringBuilder columnSumSql=new StringBuilder();
		StringBuilder columnSql=new StringBuilder();
		for(String s :column){
			columnSumSql.append(",null as c_"+s+" ");
			columnSql.append(",to_char(sum(decode(k.charge_kind_code,'"+s+"',a.charge_money,null)),'999,999,990.00') as c_"+s+" ");
		}
		
		entityMap.put("column_sum_sql", columnSumSql.toString());
		entityMap.put("column_sql", columnSql.toString());
		try{
			return ChdJson.toJsonLower(hisAccChargeMapper.queryHisChargeDataI(entityMap));
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}
	}

	//查询住院收费凭证json
	@Override
	public String queryHisChargeVouchI(Map<String, Object> map)throws Exception {
		// TODO Auto-generated method stub
		try{

			String groupId=map.get("group_id").toString();
			String hosId=map.get("hos_id").toString();
			String copyCode=map.get("copy_code").toString();
			
			String vouch_date=map.get("vouch_date").toString();
			String accYear=vouch_date.substring(0, 4);
			String busiTypeCode=map.get("busi_type_code").toString();
			map.put("mod_code", "01");
			map.put("acc_year", accYear);
			List<Map<String, Object>> tempList=accBusiTemplateDetailMapper.queryAccBusiTemplateByCode(map);
			if(tempList==null || tempList.size()==0){
				return "{\"error\":\"没有配置凭证模板！\"}";
			}
			
			String vouchTypeCode=tempList.get(0).get("VOUCH_TYPE_CODE").toString();
			
			Map<String,String> minMaxDateMap=hisAccChargeMapper.queryHisChargeDataIMinMaxDate(map);
			if(minMaxDateMap==null || minMaxDateMap.get("min_date")==null || minMaxDateMap.get("max_date")==null || minMaxDateMap.get("min_date").equals("") || minMaxDateMap.get("max_date").equals("")){
				return "{\"error\":\"请先查询数据！\"}";
			}

			/*添加自动凭证日志临时表*/
			//map.put("business_no", minMaxDateMap.get("min_date")+"至"+minMaxDateMap.get("max_date"));
			String vouchId=UUIDLong.absStringUUID();
			map.put("vouch_id", vouchId);
			Date createDate=DateUtil.getCurrenDate();
			map.put("create_date",createDate);//这个时间很重要是验证是否生成凭证的重要的 
			map.put("busi_type_code",busiTypeCode);
			hisAccChargeMapper.saveAutoVouchLogTemp(map);
			
			//判断是否生成凭证
			//map.put("min_date", minMaxDateMap.get("min_date"));
			//map.put("max_date", minMaxDateMap.get("max_date"));
			String busi_his_type_code=map.get("busi_type_code").toString();
			if("2".equals(map.get("io_type").toString())){
				//温医个性化需求，1住院2其他
				busi_his_type_code="010302";
			}
			map.put("busi_his_type_code", busi_his_type_code);
			map.put("busi_log_table", "ACC_BUSI_LOG_0103");
	
			//判断是否生成凭证
			int reCount=superVouchMapper.queryAutoVouchIsCreate(map);
			if(reCount>0){
				throw new SysException("有数据已经生成凭证！");
			}
			
			
			
			/*添加自动凭证主表*/
			map.put("vouch_att_num", 0);//凭证附件
			map.put("vouch_type_code", vouchTypeCode);//凭证类型
			Date vouchDate=DateUtil.stringToDate(vouch_date,"yyyy-MM-dd");
			map.put("vouch_date", vouchDate);
			superVouchMapper.saveAutoVouch(map);
			
			List<Map<String, Object>> detailList=new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> metaList=null;
			int isDetailSummary=Integer.parseInt(tempList.get(0).get("IS_DETAIL_SUMMARY").toString());
			int vouchRow=0;
			
			//拼日期摘要
			String summary="";
			if(map.get("charge_date_b").toString().equalsIgnoreCase(map.get("charge_date_e").toString())){
				summary=map.get("charge_date_b").toString()+"日";
			}else{
				summary=map.get("charge_date_b").toString().substring(8,10)+"-"+map.get("charge_date_e").toString().substring(8,10)+"日";
			}
			
			int index=0;
			int direction=0;
			Map<String,String> checkEl=null;
			for(Map<String, Object> tempMap:tempList){
				
				String metaCode=tempMap.get("META_CODE").toString();
				direction=Integer.parseInt(tempMap.get("DIRECTION").toString());
				map.put("direction", direction);
				checkEl=new HashMap<String,String>();//检索辅助核算规则
				map.put("vouch_row", tempMap.get("VOUCH_ROW"));
				
				//0不按明细摘要，1按明细摘要
				if(isDetailSummary==0){
					map.put("summary",tempMap.get("M_SUMMARY"));
				}else{
					map.put("summary",tempMap.get("D_SUMMARY")+summary);
				}
				
				
				if(metaCode.equals("0107")){
					
					//应收在院病人医疗款
					map.put("meta_code", "0107");
					metaList=hisAccChargeMapper.queryHisChargeI_0107(map);
					if(metaList!=null && metaList.size()>0){
						//分录
						for(Map<String, Object> detailMap:metaList){
							vouchRow++;
							detailMap.put("group_id", groupId);
							detailMap.put("hos_id", hosId);
							detailMap.put("copy_code", copyCode);
							detailMap.put("vouch_id", vouchId);//凭证ID
							detailMap.put("vouch_row", vouchRow);//分录行号
							detailMap.put("direction", direction);//方向
							detailList.add(detailMap);
							
							if(detailMap.get("is_check").toString().equals("1")){
								//科室辅助核算
								detailMap.put("vouch_date", vouch_date);//凭证日期
								detailMap.put("charge_date_b", map.get("charge_date_b"));//日期
								detailMap.put("charge_date_e", map.get("charge_date_e"));//
								detailMap.put("meta_code", "0107");
								detailMap.put("io_type", map.get("io_type"));
								detailMap.put("vouch_id", vouchId);
								detailMap.put("vouch_row", vouchRow);
								detailMap.put("acc_year", accYear);
								detailMap.put("direction", direction);
								hisAccChargeMapper.saveAccAutoCheckI_0108(detailMap);
							}
						}
						
					}
					
				}else if(metaCode.equals("0108")){
					
					//医疗收入-住院
					map.put("meta_code", "0108");
					metaList=hisAccChargeMapper.queryHisChargeI_0108(map);
					if(metaList!=null && metaList.size()>0){
						
						//分录
						for(Map<String, Object> detailMap:metaList){
							vouchRow++;
							detailMap.put("group_id", groupId);
							detailMap.put("hos_id", hosId);
							detailMap.put("copy_code", copyCode);
							detailMap.put("vouch_id", vouchId);//凭证ID
							detailMap.put("vouch_row", vouchRow);//分录行号
							detailMap.put("direction", direction);//方向
							detailList.add(detailMap);
							
							
							if(detailMap.get("is_check").toString().equals("1")) {
								
								if(detailMap.get("check1")!=null && detailMap.get("check1").toString().equalsIgnoreCase("check1") ||
									detailMap.get("check2")!=null && detailMap.get("check2").toString().equalsIgnoreCase("check1") ||
									detailMap.get("check3")!=null && detailMap.get("check3").toString().equalsIgnoreCase("check1") ||
									detailMap.get("check4")!=null && detailMap.get("check4").toString().equalsIgnoreCase("check1")){
								
									//科室辅助核算
									detailMap.put("vouch_date", vouch_date);//凭证日期
									detailMap.put("charge_date_b", map.get("charge_date_b"));//日期
									detailMap.put("charge_date_e", map.get("charge_date_e"));//
									detailMap.put("meta_code", "0108");
									detailMap.put("io_type", map.get("io_type"));
									detailMap.put("vouch_id", vouchId);
									detailMap.put("vouch_row", vouchRow);
									detailMap.put("acc_year", accYear);
									detailMap.put("direction", direction);
									hisAccChargeMapper.saveAccAutoCheckI_0108(detailMap);
								}
							}
						}
						

					}
					
				}
				
			}
			
			
			if(detailList!=null && detailList.size()>0){
				//保存自动凭证明细表
				superVouchMapper.saveAutoVouchDetail(detailList);
			}else{
				throw new SysException("没有明细表数据或者没有对应关系！");
			}
			
			return "{\"auto_id\": \""+vouchId+"\"}";
			
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}
	}

	
	/*************************************住院结算**********************************************************/
	//查询住院结算数据
	@Override
	public String queryHisBalI(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		try{
			return ChdJson.toJsonLower(hisAccChargeMapper.queryHisBalI(map));
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}
	}

	//查询住院结算凭证json
	@Override
	public String queryHisBalVouchI(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		try{
			String groupId=map.get("group_id").toString();
			String hosId=map.get("hos_id").toString();
			String copyCode=map.get("copy_code").toString();
			String vouch_date=map.get("vouch_date").toString();
			String accYear=vouch_date.substring(0, 4);
			String accMonth=vouch_date.substring(5, 7);
			map.put("mod_code", "01");
			map.put("acc_year", accYear);
			List<Map<String, Object>> tempList=accBusiTemplateDetailMapper.queryAccBusiTemplateByCode(map);
			if(tempList==null || tempList.size()==0){
				return "{\"error\":\"没有配置凭证模板！\"}";
			}
			//处理单据号
			String busNo=map.get("busi_no").toString();
			Map<String,Object> busNoMap = new HashMap<String, Object>();
			if(!busNo.equals("")){
				String[] busNos=busNo.split(",");
				for (String string : busNos) {
					busNoMap.put(string, string);
				}
				
			}
			/*添加自动凭证日志临时表*/
			//map.put("business_no", minMaxDateMap.get("min_date")+"至"+minMaxDateMap.get("max_date"));
			String vouchId=UUIDLong.absStringUUID();
			map.put("vouch_id", vouchId);
			Date createDate=DateUtil.getCurrenDate();
			map.put("create_date",createDate);//这个时间很重要是验证是否生成凭证的重要的 
			map.put("table", "ACC_CHARGE_I");
			for (String key : busNoMap.keySet()) {
				map.put("busi_no", "'"+key+"'");
				
				hisAccChargeMapper.saveAutoVouchLogOutpatient(map);
			}
			
			
			
			//判断是否生成凭证
			map.put("busi_type_code", "0104");
			map.put("busi_log_table", "ACC_BUSI_LOG_0104");
			//判断是否生成凭证
			int reCount=superVouchMapper.queryAutoVouchIsCreate(map);
			if(reCount>0){
				throw new SysException("有数据已经生成凭证！");
			}
			
			/*添加自动凭证主表*/
			map.put("vouch_att_num", 0);//凭证附件
			map.put("vouch_type_code",tempList.get(0).get("VOUCH_TYPE_CODE").toString());//凭证类型
			Date vouchDate=DateUtil.stringToDate(vouch_date,"yyyy-MM-dd");
			map.put("vouch_date", vouchDate);
			superVouchMapper.saveAutoVouch(map);
			
			
			String selCode=map.get("sel_code").toString();
			if(!selCode.equals("")){
				//处理收款员条件and a.charge_code in(${sel_code})
				selCode=selCode.replace(",", "','");
				selCode="'"+selCode+"'";
				map.put("sel_code", selCode);
			}
			
			List<Map<String, Object>> metaList=null;
			int isDetailSummary=Integer.parseInt(tempList.get(0).get("IS_DETAIL_SUMMARY").toString());
			
			
			//拼日期摘要
			String summary="";
			if(map.get("charge_date_b").toString().equalsIgnoreCase(map.get("charge_date_e").toString())){
				summary=map.get("charge_date_b").toString()+"日";
			}else{
				summary=Integer.parseInt(map.get("charge_date_b").toString().substring(8,10))+"-"+Integer.parseInt(map.get("charge_date_e").toString().substring(8,10))+"日";
			}
			
			//查询自动核销的票据类型
			List<String> paperType=hisAccChargeMapper.queryAccPaperTypeByAuto(map);
			
			int vouchRow=0;
			int direction=0;
			String cashSubjWhere="";
			String bankSubjWhere="";
			List<Map<String,Object>> detailList = new ArrayList<Map<String,Object>>();
			for(Map<String, Object> tempMap:tempList){
				
				String metaCode=tempMap.get("META_CODE").toString();
				map.put("direction", tempMap.get("DIRECTION"));
				map.put("vouch_row", tempMap.get("VOUCH_ROW"));
				
				//0不按明细摘要，1按明细摘要
				if(isDetailSummary==0){
					map.put("summary",tempMap.get("M_SUMMARY"));
				}else{
					map.put("summary",tempMap.get("D_SUMMARY")+summary);
				}
				
				if(metaCode.equals("0101")){
					
					//现金/银行
					map.put("meta_code", "0101");
					metaList=hisAccChargeMapper.queryHisBalVouchI_0101(map);
					if(metaList!=null && metaList.size()>0){
						//分录
						cashSubjWhere="";
						bankSubjWhere="";
						for(Map<String, Object> detailMap:metaList){
							
							vouchRow++;
							detailMap.put("group_id", groupId);
							detailMap.put("hos_id", hosId);
							detailMap.put("copy_code", copyCode);
							detailMap.put("vouch_id", vouchId);//凭证ID
							detailMap.put("vouch_row", vouchRow);//分录行号
							detailMap.put("direction", direction);//方向
							detailList.add(detailMap);
							
							int count=0;
							if(detailMap.get("is_bill").toString().equals("1")){
								//是否票据号核销
								//bankSubjWhere=bankSubjWhere+"'"+detailMap.get("SUBJ_CODE")+"',";
								detailMap.put("acc_year", accYear);
								detailMap.put("busi_no", map.get("busi_no"));
								detailMap.put("mod_code", map.get("mod_code"));
								detailMap.put("meta_code", metaCode);
								detailMap.put("io_type", map.get("io_type"));
								detailMap.put("charge_date_b", map.get("charge_date_b"));
								detailMap.put("charge_date_e", map.get("charge_date_e"));
								//插入辅助核算
								count=hisAccChargeMapper.saveHisChargeVouchPaperCheckI(detailMap);
							}
							
							if(detailMap.get("is_cash").toString().equals("1")){
								//现金流量
								if(count>0){
									//为了与辅助核算的条数保持一致
									hisAccChargeMapper.saveHisChargeVouchPaperCashI(detailMap);
								}else{
									hisAccChargeMapper.saveHisChargeVouchCashO_0101(detailMap);
								}
							}
							
						}
					}
					
				}else if(metaCode.equals("0105") || metaCode.equals("0110")){
					
					//预收医疗款-住院、预收医疗款-住院(结算方式)
					map.put("meta_code", metaCode);
					map.put("direction", 0);//借方
					map.put("state", "2");//冲销
					metaList=hisAccChargeMapper.queryHisPreVouchI_0105(map);
					if(metaList!=null && metaList.size()>0){
						for(Map<String, Object> detailMap:metaList){
							
							vouchRow++;
							detailMap.put("group_id", groupId);
							detailMap.put("hos_id", hosId);
							detailMap.put("copy_code", copyCode);
							detailMap.put("vouch_id", vouchId);//凭证ID
							detailMap.put("vouch_row", vouchRow);//分录行号
							detailMap.put("direction", direction);//方向
							detailList.add(detailMap);
						}
					}
					
					map.put("direction", tempMap.get("DIRECTION"));
					map.put("state", "0,3");//交费（正常、作废）0正常1退费2冲销 3作废
					metaList=hisAccChargeMapper.queryHisPreVouchI_0105(map);
					if(metaList!=null && metaList.size()>0){
						for(Map<String, Object> detailMap:metaList){
							
							vouchRow++;
							detailMap.put("group_id", groupId);
							detailMap.put("hos_id", hosId);
							detailMap.put("copy_code", copyCode);
							detailMap.put("vouch_id", vouchId);//凭证ID
							detailMap.put("vouch_row", vouchRow);//分录行号
							detailMap.put("direction", direction);//方向
							detailList.add(detailMap);
						}
					}
					
					map.put("state", "1");//退费
					metaList=hisAccChargeMapper.queryHisPreVouchI_0105(map);
					if(metaList!=null && metaList.size()>0){
						for(Map<String, Object> detailMap:metaList){
							
							vouchRow++;
							detailMap.put("group_id", groupId);
							detailMap.put("hos_id", hosId);
							detailMap.put("copy_code", copyCode);
							detailMap.put("vouch_id", vouchId);//凭证ID
							detailMap.put("vouch_row", vouchRow);//分录行号
							detailMap.put("direction", direction);//方向
							detailList.add(detailMap);
						}
					}
					
				}else if(metaCode.equals("0106")){
					
					//应收医疗款-住院
					map.put("meta_code", "0106");
					metaList=hisAccChargeMapper.queryHisBalVouchI_0101(map);
					if(metaList!=null && metaList.size()>0){
						//分录
						for(Map<String, Object> detailMap:metaList){
							
							vouchRow++;
							detailMap.put("group_id", groupId);
							detailMap.put("hos_id", hosId);
							detailMap.put("copy_code", copyCode);
							detailMap.put("vouch_id", vouchId);//凭证ID
							detailMap.put("vouch_row", vouchRow);//分录行号
							detailMap.put("direction", direction);//方向
							detailList.add(detailMap);
						}
					}
					
				}else if(metaCode.equals("0109")){
					
					//优惠减免、根据支付方式，贷医疗收入
					map.put("meta_code", "0109");
					metaList=hisAccChargeMapper.queryHisBalVouchI_0109(map);
					if(metaList!=null && metaList.size()>0){
						//分录
						for(Map<String, Object> detailMap:metaList){
							
							vouchRow++;
							detailMap.put("group_id", groupId);
							detailMap.put("hos_id", hosId);
							detailMap.put("copy_code", copyCode);
							detailMap.put("vouch_id", vouchId);//凭证ID
							detailMap.put("vouch_row", vouchRow);//分录行号
							detailMap.put("direction", direction);//方向
							detailList.add(detailMap);
						}
					}
					
				}else if(metaCode.equals("0107")){
					
					//应收在院病人医疗款
					map.put("meta_code", "0107");
					metaList=hisAccChargeMapper.queryHisChargeI_0108(map);
					
					if(metaList!=null && metaList.size()>0){
						
						if(metaList.size()==1){
							//只有一个科目取借贷平衡，最后一条分录计算借贷平衡
							map.put("isBalance", "1");
						}else{
							//按收费数据取分录
							
							for(Map<String, Object> detailMap:metaList){
								
								vouchRow++;
								detailMap.put("group_id", groupId);
								detailMap.put("hos_id", hosId);
								detailMap.put("copy_code", copyCode);
								detailMap.put("vouch_id", vouchId);//凭证ID
								detailMap.put("vouch_row", vouchRow);//分录行号
								detailMap.put("direction", direction);//方向
								detailList.add(detailMap);
								
								if(detailMap.get("is_check").toString().equals("1")) {
									
									if(detailMap.get("check1")!=null && detailMap.get("check1").toString().equalsIgnoreCase("check1") ||
										detailMap.get("check2")!=null && detailMap.get("check2").toString().equalsIgnoreCase("check1") ||
										detailMap.get("check3")!=null && detailMap.get("check3").toString().equalsIgnoreCase("check1") ||
										detailMap.get("check4")!=null && detailMap.get("check4").toString().equalsIgnoreCase("check1")){
									
										//科室辅助核算
										detailMap.put("vouch_date", vouch_date);//凭证日期
										detailMap.put("charge_date_b", map.get("charge_date_b"));//日期
										detailMap.put("charge_date_e", map.get("charge_date_e"));//
										detailMap.put("meta_code", "0107");
										detailMap.put("io_type", map.get("io_type"));
										detailMap.put("vouch_id", vouchId);
										detailMap.put("vouch_row", vouchRow);
										detailMap.put("acc_year", accYear);
										detailMap.put("direction", direction);
										hisAccChargeMapper.saveAccAutoCheckI_0108(detailMap);
									}
								}
							}
							
						}
						
					}
					
				}
				
			}
			
			if(detailList!=null && detailList.size()>0){
				//保存自动凭证明细表
				superVouchMapper.saveAutoVouchDetail(detailList);
			}else{
				throw new SysException("没有明细表数据或者没有对应关系！");
			}
			
			return "{\"auto_id\": \""+vouchId+"\"}";
			
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}
	}


	@Override
	public List<Map<String, Object>> queryHisChargeDataOPrint(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		String[] column=entityMap.get("column_name").toString().split(",");
		
		StringBuilder columnSql=new StringBuilder();
		StringBuilder columnSumSql=new StringBuilder();
		
		if(entityMap.get("sum_type_id").toString().equals("1")){
			
			//按收款员
			columnSumSql.append(" null rep_no,'合计' charge_code,to_char(sum(a.charge_money),'999,999,990.00') charge_name ");
			entityMap.put("vouch_no_sum",",'-' vouch_no,null vouch_id ");
			columnSql.append(" a.rep_no,a.charge_code,a.charge_name ");
			entityMap.put("group_sql", " a.rep_no,a.charge_code,a.charge_name ");
			entityMap.put("order_sql", " a.charge_code ");
			entityMap.put("left_sql"," ");
			entityMap.put("vouch_no",",t.vouch_type_short||'-'||v.vouch_no vouch_no,v.vouch_id ");
			StringBuilder sb=new StringBuilder();
			sb.append(" left join acc_busi_log_0102 bl on bl.group_id="+entityMap.get("group_id")+" and bl.hos_id="+entityMap.get("hos_id")+" and bl.copy_code='"+entityMap.get("copy_code")+"' ");
			sb.append(" and bl.busi_type_code='0102' and bl.business_no=t1.rep_no||'-'||t1.charge_code ");
			sb.append(" left join acc_vouch v on bl.group_id=v.group_id and bl.hos_id=v.hos_id and bl.copy_code=v.copy_code and bl.vouch_id=v.vouch_id ");
			sb.append(" left join acc_vouch_type t on v.group_id=t.group_id and v.hos_id=t.hos_id and v.copy_code=t.copy_code and v.vouch_type_code=t.vouch_type_code ");
			entityMap.put("vouch_sql",sb.toString());
			
		}else{
			
			//按科室
			columnSumSql.append(" null rep_no,'合计' dept_code,to_char(sum(a.charge_money),'999,999,990.00') dept_name ");
			columnSql.append(" a.rep_no,d.dept_code,d.dept_name ");
			entityMap.put("group_sql", " a.rep_no,d.dept_code,d.dept_name ");
			entityMap.put("order_sql", " d.dept_code ");
			entityMap.put("left_sql", " left join hos_dept_dict d on a.group_id=d.group_id and a.hos_id=d.hos_id and a.dept_id=d.dept_id and a.dept_no=d.dept_no ");
		}
		
		
		for(String s :column){
			columnSumSql.append(",null as c_"+s+" ");
			columnSql.append(",to_char(sum(decode(k.charge_kind_code,'"+s+"',a.charge_money,null)),'999,999,990.00') as c_"+s+" ");
		}
		
		entityMap.put("column_sum_sql", columnSumSql.toString());
		entityMap.put("column_sql", columnSql.toString());
		try{
			return hisAccChargeMapper.queryHisChargeDataO(entityMap);
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}
	}


	@Override
	public List<Map<String, Object>> queryHisChargeDataIPrint(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		String[] column=entityMap.get("column_name").toString().split(",");
		
		StringBuilder columnSumSql=new StringBuilder();
		StringBuilder columnSql=new StringBuilder();
		for(String s :column){
			columnSumSql.append(",null as c_"+s+" ");
			columnSql.append(",to_char(sum(decode(k.charge_kind_code,'"+s+"',a.charge_money,null)),'999,999,990.00') as c_"+s+" ");
		}
		
		entityMap.put("column_sum_sql", columnSumSql.toString());
		entityMap.put("column_sql", columnSql.toString());
		try{
			return hisAccChargeMapper.queryHisChargeDataI(entityMap);
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}
	}


	@Override
	public List<Map<String, Object>> queryHisBalIPrint(Map<String, Object> map)
			throws Exception {
		// TODO Auto-generated method stub
		try{
			return hisAccChargeMapper.queryHisBalI(map);
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}
	}


	@Override
	public List<Map<String, Object>> queryHisBalOPrint(Map<String, Object> map)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try{
			return hisAccChargeMapper.queryHisBalO(map);
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}
	}


	@Override
	public String queryVouchNo(Map<String, Object> mapVo) throws Exception {
		List<Map<String,Object>> vouchList=hisAccChargeMapper.queryVouchNo(mapVo);
		return ChdJson.toJsonLower(vouchList);
	}

}
