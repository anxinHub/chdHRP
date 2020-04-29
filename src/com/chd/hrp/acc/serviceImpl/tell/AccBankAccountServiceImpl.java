package com.chd.hrp.acc.serviceImpl.tell;

/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

 
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.ExcelReader;
import com.chd.base.util.UUIDLong;
import com.chd.hrp.acc.dao.AccParaMapper;
import com.chd.hrp.acc.dao.commonbuilder.AccPayTypeMapper;
import com.chd.hrp.acc.dao.commonbuilder.AccSubjMapper;
import com.chd.hrp.acc.dao.tell.AccBankCheckMapper;
import com.chd.hrp.acc.dao.tell.AccTellMapper;
import com.chd.hrp.acc.dao.vouch.AccVouchCheckMapper;
import com.chd.hrp.acc.entity.AccBankCheck;
import com.chd.hrp.acc.entity.AccPara;
import com.chd.hrp.acc.entity.AccPayType;
import com.chd.hrp.acc.entity.AccSubj;
import com.chd.hrp.acc.entity.AccTell;
import com.chd.hrp.acc.entity.AccVouchCheck;
import com.chd.hrp.acc.service.tell.AccBankAccountService;
import com.chd.hrp.sys.dao.ModStartMapper;
import com.chd.hrp.sys.entity.ModStart;
import com.github.pagehelper.PageInfo;
import com.mysql.jdbc.jdbc2.optional.MysqlXAConnection;

/**
* @Title. @Description.
* 银行对账单<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("accBankAccountService")
public class AccBankAccountServiceImpl implements AccBankAccountService {

	private static Logger logger = Logger.getLogger(AccBankAccountServiceImpl.class);
	
	@Resource(name = "accTellMapper")
	private final AccTellMapper accTellMapper = null;
	
	@Resource(name = "accVouchCheckMapper")
	private final AccVouchCheckMapper accVouchCheckMapper = null;
    
	@Resource(name = "accParaMapper")
	private final AccParaMapper accParaMapper = null;
	
	@Resource(name="modStartMapper")
	private final ModStartMapper modStartMapper=null;
	
	@Resource(name = "accSubjMapper")
	private final AccSubjMapper accSubjMapper = null;
	
	@Resource(name = "accPayTypeMapper")
	private final AccPayTypeMapper accPayTypeMapper = null;
	
	@Resource(name = "accBankCheckMapper")
	private final AccBankCheckMapper accBankCheckMapper = null;
	
	/**
	 * @Description 
	 * 银行对账单<BR> 添加AccBankCheck
	 * @param AccBankCheck entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAccBankAccount(Map<String,Object> entityMap)throws DataAccessException{
		
		ModStart modStart = modStartMapper.queryModStartByCode(entityMap);
		if(modStart == null){
			return "{\"error\":\"出纳账管理系统没有启用.\"}";
		}else if(modStart.getStart_year()==null && modStart.getStart_month()==null){
			return "{\"error\":\"出纳账管理系统没有启用.\"}";
		}
		
		if(entityMap.get("occur_date") == null || "".equals(entityMap.get("occur_date"))){
			return "{\"error\":\"业务日期不能为空.\"}";
		}
		
		int yearMonth = Integer.parseInt(modStart.getStart_year()+modStart.getStart_month());
		int occuDate= Integer.parseInt(entityMap.get("occur_date").toString().substring(0, 7).replace("-",""));
		if(occuDate>yearMonth){
			return "{\"error\":\"业务日期必须小于出纳账管理系统启用时间.\"}";
		}
		
		try {
			//20190224 hsy应申哲要求修改初始账年度为出纳系统的启用年度
			entityMap.put("acc_year", modStart.getStart_year());
			
			if(entityMap.get("debit")==null || "".equals(entityMap.get("debit"))){
				
				entityMap.remove("debit");
				
				entityMap.put("debit", 0);
			}
			
			if(entityMap.get("credit")==null || "".equals(entityMap.get("credit"))){
				
				entityMap.remove("credit");
				
				entityMap.put("credit", 0);
			}
			
			
				
				if("0".equals(MyConfig.getSysPara("018"))){
					
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
					entityMap.put("cash_subj_code", entityMap.get("cash_subj_code").toString());
					entityMap.put("other_subj_code","");
					entityMap.put("att_num",1);
					entityMap.put("cur_code","");
					entityMap.put("debit_w", 0.000000);
					entityMap.put("credit_w", 0.000000);
					entityMap.put("busi_type","");
					entityMap.put("business_no","");
					entityMap.put("create_user",SessionManager.getUserId());
					entityMap.put("create_date",sdf.format(new Date()));
					entityMap.put("con_user","");
					entityMap.put("con_date","");
					entityMap.put("is_check",0);
					entityMap.put("check_user","");
					entityMap.put("check_date","");
					entityMap.put("is_init","1");
					entityMap.put("vouch_check_id","");
					entityMap.put("vouch_id","");
					entityMap.put("is_import",0);
					
					accTellMapper.addAccTellByBank(entityMap);
					
				}else{
					entityMap.put("vouch_id", "");
					entityMap.put("vouch_detail_id","");
					entityMap.put("vouch_check_id",UUIDLong.absStringUUID());
					entityMap.put("is_init","1");
					entityMap.put("debit_w", 0);
					entityMap.put("credit_w", 0);
					
					accVouchCheckMapper.saveAccVouchCheck(entityMap);
					
				}
				
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addAccBankCheck\"}";

		}

	}
	
	/**
	 * @Description 
	 * 银行对账单<BR> 批量添加AccBankCheck
	 * @param  AccBankCheck entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAccBankAccount(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			//AccBankCheckMapper.addBatchAccBankCheck(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchAccBankCheck\"}";

		}
	}
	
	/**
	 * @Description 
	 * 银行对账单<BR> 查询AccBankCheck分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAccBankAccount(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		
		List<AccTell> list = new ArrayList<AccTell>();
		
		List<AccVouchCheck> bankList = new ArrayList<AccVouchCheck>();
		
			
			if("0".equals(MyConfig.getSysPara("018"))){
				
				list = accTellMapper.queryAccTellAndSum(entityMap, rowBounds);
				
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list, page.getTotal());
				
			}
			
		
		bankList = accVouchCheckMapper.queryAccVouchCheckAndSum(entityMap, rowBounds);
		
		PageInfo page = new PageInfo(bankList);
		
		return ChdJson.toJson(bankList, page.getTotal());
		
	}
	
	/**
	 * @Description 
	 * 银行对账单<BR> 查询AccBankCheck分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public List<Map<String,Object>> queryAccBankAccountPrint(Map<String,Object> entityMap) throws DataAccessException{
		
		entityMap.put("mod_code", "01");
		
		entityMap.put("para_code", "018");
		
		entityMap.put("is_init", "1");
		
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		
		List<Map<String,Object>> bankList = new ArrayList<Map<String,Object>>();
		
			
			if("0".equals(MyConfig.getSysPara("018"))){
				
				list = accTellMapper.queryAccTellAndSumPrint(entityMap);
				
				return list;
				
			}
			
		
		bankList = accVouchCheckMapper.queryAccVouchCheckAndSumPrint(entityMap);
		
		return bankList;
		
	}
	
	/**
	 * @Description 
	 * 银行对账单<BR> 查询AccBankCheckByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AccBankCheck queryAccBankAccountByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return null;//AccBankCheckMapper.queryAccBankCheckByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 银行对账单<BR> 批量删除AccBankCheck
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAccBankAccount(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			Map<String, Object> map = entityList.get(0);

			map.put("mod_code", "01");

			map.put("para_code", "018");
			
			
				
				if("0".equals(MyConfig.getSysPara("018"))){
					for(Map<String,Object> mapVo:entityList){
						
						mapVo.put("tell_id", mapVo.get("bank_id"));
						
					}
					
					int state = accTellMapper.deleteBatchAccTell(entityList);
					
					return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

				}
			
			
			for(Map<String,Object> mapVo:entityList){
				
				mapVo.put("vouch_check_id", mapVo.get("bank_id"));
				
			}
			
			int state =accVouchCheckMapper.deleteBatchAccVouchCheck(entityList);// AccBankCheckMapper.deleteBatchAccBankCheck(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			
		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccBankCheck\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 银行对账单<BR> 删除AccBankCheck
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteAccBankAccount(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			//AccBankCheckMapper.deleteAccBankCheck(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteAccBankCheck\"}";

		}
    }
	
	/**
	 * @Description 
	 * 银行对账单<BR> 更新AccBankCheck
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAccBankAccount(Map<String,Object> entityMap)throws DataAccessException{
		
		ModStart modStart = modStartMapper.queryModStartByCode(entityMap);
		if(modStart == null){
			return "{\"error\":\"出纳账管理系统没有启用.\"}";
		}else if(modStart.getStart_year()==null && modStart.getStart_month()==null){
			return "{\"error\":\"出纳账管理系统没有启用.\"}";
		}
		
		if(entityMap.get("occur_date") == null || "".equals(entityMap.get("occur_date"))){
			return "{\"error\":\"业务日期不能为空.\"}";
		}
		
		int yearMonth = Integer.parseInt(modStart.getStart_year()+modStart.getStart_month());
		int occuDate= Integer.parseInt(entityMap.get("occur_date").toString().substring(0, 7).replace("-",""));
		if(occuDate>yearMonth){
			return "{\"error\":\"业务日期必须小于出纳账管理系统启用时间.\"}";
		}
		
		try {
			
			if(entityMap.get("debit")==null || "".equals(entityMap.get("debit"))){
				
				entityMap.remove("debit");
				
				entityMap.put("debit", 0);
			}
			
			if(entityMap.get("credit")==null || "".equals(entityMap.get("credit"))){
				
				entityMap.remove("credit");
				
				entityMap.put("credit", 0);
			}
			
			
				
				if("0".equals(MyConfig.getSysPara("018"))){
					
					entityMap.put("tell_id", entityMap.get("bank_id"));
					
					entityMap.put("cash_subj_code", entityMap.get("subj_code"));
					
					accTellMapper.updateAccTell(entityMap);
					
				}else{
					
					entityMap.put("vouch_check_id", entityMap.get("bank_id"));
					
					accVouchCheckMapper.updateAccVouchCheck(entityMap);
					
				}
			//AccBankCheckMapper.updateAccBankCheck(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccBankCheck\"}";

		}
	}
	
	/**
	 * @Description 
	 * 银行对账单<BR> 批量更新AccBankCheck
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAccBankAccount(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			//AccBankCheckMapper.updateBatchAccBankCheck(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccBankCheck\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 银行对账单<BR> 导入AccBankCheck
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importAccBankAccount(List<String[]> entityMap)throws DataAccessException{

		try {
			List<String> list_err = new ArrayList<String>(); 
			List<Map<String, Object>> maplist = new ArrayList<Map<String, Object>>();
			Map<String,Object> map = new HashMap<String,Object>();
			StringBuffer err_sb = new StringBuffer();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			
			if (map.get("group_id") == null) {
				map.put("group_id", SessionManager.getGroupId());
			}
			
			if (map.get("hos_id") == null) {
				map.put("hos_id", SessionManager.getHosId());
			}
			
			if (map.get("copy_code") == null) {
				map.put("copy_code", SessionManager.getCopyCode());
			}
			
			map.put("para_code", "018");
				if("0".equals(MyConfig.getSysPara("018"))){//为0添加到出纳账表
					//准备添加到出纳账表数据
					for(int i = 1;i<entityMap.size();i++){
						int b = 0;
						
						String temp[] = entityMap.get(i);// 行
						Map<String, Object> mapVo = new HashMap<String, Object>();
						
						int length = entityMap.get(0).length;
						String tempMap[] = new String[length];
						for (int j = 0; j < length; j++) {
							if (temp.length > j) {
								tempMap[j] = temp[j];
								if(StringUtils.isEmpty(temp[j])){
									b++;
								}
							}else {
								tempMap[j] = "";
							}
						}
						temp = tempMap;
						if(b != temp.length){
							if (mapVo.get("group_id") == null) {
								mapVo.put("group_id", SessionManager.getGroupId());
							}
							
							if (mapVo.get("hos_id") == null) {
								mapVo.put("hos_id", SessionManager.getHosId());
							}
							
							if (mapVo.get("copy_code") == null) {
								mapVo.put("copy_code", SessionManager.getCopyCode());
							}
							
							if (mapVo.get("acc_year") == null) {
								mapVo.put("acc_year", SessionManager.getAcctYear());
							}
							
							//校验科目编码
							if (StringUtils.isNotEmpty(temp[0])) {
								String reg = "\\d+";
								boolean flag = temp[0].toString().matches(reg);
								
								if(flag == false){
									err_sb.append("第"+i+"行"+"科目编码必须是数字  ");
								}else{
									mapVo.put("subj_code", temp[0]);
									AccSubj accSubj = accSubjMapper.queryAccSubjCode(mapVo);
									if(accSubj != null){
										mapVo.put("cash_subj_code", accSubj.getSubj_code());
									}else{
										err_sb.append("第"+i+"行"+"科目编码不存在  ");
									}
								}
							} else {
								err_sb.append("第"+i+"行"+"科目编码为空  ");
							}
							
							//校验业务日期
							if (StringUtils.isNotEmpty(temp[1])) {//判断日期是否为空
								String reg = "[\\d]{4}[-][\\d]{2}-[\\d]{2}";
								boolean flag = temp[1].toString().matches(reg);
								
								if (flag) {
									mapVo.put("mod_code", "0101");
									ModStart modStart = modStartMapper.queryModStartByCode(mapVo);
									String mod_start_yearMonth = null;
									String[] occur_date = null;
									String occ_yearMonth =null;
									if(modStart != null){
										if("".equals(modStart.getStart_year())||modStart.getStart_year()==null&&"".equals(modStart.getStart_month())||modStart.getStart_month() == null){
											err_sb.append("第"+i+"行"+"出纳账管理系统没有启用,不能进行修改操作 ");
										}
										mod_start_yearMonth = modStart.getStart_year()+"-"+(Integer.parseInt(modStart.getStart_month())-1);
									}
										
									occur_date = temp[1].toString().split("-");
									occ_yearMonth = occur_date[0] +"-"+ occur_date[1];
									SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
									Date mod_start_year_month = null;
									Date occur_year_month = null;
									try {
										mod_start_year_month = df.parse(mod_start_yearMonth);
										occur_year_month = df.parse(occ_yearMonth);
										if(occur_year_month.getTime() > mod_start_year_month.getTime()){
											err_sb.append("第"+i+"行"+"业务日期必须小于出纳账管理系统启用时间 ");
										}
									} catch (Exception e) {
										return "{\"error\":\"系统异常,请联系管理员.\"}";
									}
								}else{
									err_sb.append("第"+i+"行"+"业务日期格式错误 ");
								}
								mapVo.put("occur_date", temp[1]);
							} else {
								err_sb.append("第"+i+"行"+"业务日期为空  ");
							}
							
							//校验结算方式
							if (StringUtils.isNotEmpty(temp[2])) {
								String reg = "\\d{1,20}";
								AccPayType accPayType = null ; 
								boolean flag = temp[0].toString().matches(reg);
								
								if(flag == false){
									err_sb.append("第"+i+"行"+"结算方式编码格式错误  ");
								}else{
									mapVo.put("pay_code", temp[2]);
									accPayType = accPayTypeMapper.queryAccPayTypeByCode(mapVo);
								}
								
								if(accPayType == null){
									err_sb.append("第"+i+"行"+"结算方式不存在  ");
								}
								mapVo.put("pay_type_code",accPayType.getPay_code());
							} else {
								mapVo.put("pay_type_code","");
							}
							
							//票据号
							if (StringUtils.isNotEmpty(temp[3])) {
								mapVo.put("check_no", temp[3]);
							}else {
								mapVo.put("check_no","");
							}
							
							//摘要
							if (StringUtils.isNotEmpty(temp[4])) {
								mapVo.put("summary", temp[4]);
							} else {
								err_sb.append("第"+i+"行"+"摘要为空  ");
							}
							
							//借方金额
							if (ExcelReader.validExceLColunm(temp, 5)) {
								String reg = "[\\d]+(\\.\\d+)?";
								boolean flag = temp[5].toString().matches(reg);
								if(flag == false){
									err_sb.append("第"+i+"行"+"借方金额格式错误  ");
								}
								mapVo.put("debit", temp[5]);
							}else {
								mapVo.put("debit", 0.00);
							}
							
							//贷方金额
							if (ExcelReader.validExceLColunm(temp, 6)) {
								String reg = "[\\d]+[\\.]?[\\d]{0,3}";
								boolean flag = temp[6].toString().matches(reg);
								if(flag == false){
									err_sb.append("第"+i+"行"+"贷方金额格式错误  ");
								}else{
									mapVo.put("credit", temp[6]);
								}
							}else{
								mapVo.put("credit", 0.00);
							}
							
							//ACC_TELL表默认值
							mapVo.put("other_subj_code", "");
							mapVo.put("att_num",0);
							mapVo.put("debit_w", 0);
							mapVo.put("credit_w", 0);
							mapVo.put("busi_type", "");
							mapVo.put("business_no", "");
							mapVo.put("create_user",SessionManager.getUserId());
							mapVo.put("create_date",sdf.format(new Date()));
							mapVo.put("con_user", "");
							mapVo.put("con_date", "");
							mapVo.put("state", 0);
							mapVo.put("check_user", "");
							mapVo.put("check_date", "");
							mapVo.put("is_init", 1);
							mapVo.put("vouch_check_id", "");
							mapVo.put("vouch_id", "");
							mapVo.put("is_import", 2);
							mapVo.put("is_con",0);
							mapVo.put("tell_number","");
							mapVo.put("tell_id", accTellMapper.queryAccTellNextSeq());
							mapVo.put("tell_type_code", "");
							
							maplist.add(mapVo);
							if(err_sb.toString().length() > 0 ){
								list_err.add(err_sb.toString());
							}
						}else{
							break;
						}
					}
				}else{//添加到辅助辅助核算表
					//准备添加到辅助表数据
					for(int i = 1;i<entityMap.size();i++){
						int b = 0;
						
						String temp[] = entityMap.get(i);// 行
						Map<String, Object> mapVo = new HashMap<String, Object>();
						
						for(int a = 0 ; a < temp.length ; a++){
							if(StringUtils.isEmpty(temp[a])){
								b++;
							}
						}
						if(b != temp.length){
							if (mapVo.get("group_id") == null) {
								mapVo.put("group_id", SessionManager.getGroupId());
							}
							
							if (mapVo.get("hos_id") == null) {
								mapVo.put("hos_id", SessionManager.getHosId());
							}
							
							if (mapVo.get("copy_code") == null) {
								mapVo.put("copy_code", SessionManager.getCopyCode());
							}
							
							if (mapVo.get("acc_year") == null) {
								mapVo.put("acc_year", SessionManager.getAcctYear());
							}
							
							//校验科目编码
							if (StringUtils.isNotEmpty(temp[0])) {
								String reg = "\\d+";
								boolean flag = temp[0].toString().matches(reg);
								
								if(flag == false){
									err_sb.append("第"+i+"行"+"科目编码必须是数字  ");
								}else{
									mapVo.put("subj_code", temp[0]);
									AccSubj accSubj = accSubjMapper.queryAccSubjCode(mapVo);
									if(accSubj != null){
										mapVo.put("subj_code", accSubj.getSubj_code());
									}else{
										err_sb.append("第"+i+"行"+"科目编码不存在  ");
									}
								}
							} else {
								err_sb.append("第"+i+"行"+"科目编码为空  ");
							}
							
							//校验业务日期
							if (StringUtils.isNotEmpty(temp[1])) {//判断日期是否为空
								String reg = "[\\d]{4}[-][\\d]{2}-[\\d]{2}";
								boolean flag = temp[1].toString().matches(reg);
	                          if (flag) {
									mapVo.put("mod_code", "0101");
									ModStart modStart = modStartMapper.queryModStartByCode(mapVo);
									String mod_start_yearMonth = null;
									String[] occur_date = null;
									String occ_yearMonth =null;
									if(modStart != null){
										if("".equals(modStart.getStart_year())||modStart.getStart_year()==null&&"".equals(modStart.getStart_month())||modStart.getStart_month() == null){
											err_sb.append("第"+i+"行"+"出纳账管理系统没有启用,不能进行修改操作 ");
										}
										mod_start_yearMonth = modStart.getStart_year()+"-"+(Integer.parseInt(modStart.getStart_month())-1);
									}
										
									occur_date = temp[1].toString().split("-");
									occ_yearMonth = occur_date[0] +"-"+ occur_date[1];
									SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
									Date mod_start_year_month = null;
									Date occur_year_month = null;
									try {
										mod_start_year_month = df.parse(mod_start_yearMonth);
										occur_year_month = df.parse(occ_yearMonth);
										if(occur_year_month.getTime() > mod_start_year_month.getTime()){
											err_sb.append("第"+i+"行"+"业务日期必须小于出纳账管理系统启用时间 ");
										}
									} catch (Exception e) {
										return "{\"error\":\"系统异常,请联系管理员.\"}";
									}
								}else{
									err_sb.append("第"+i+"行"+"业务日期格式错误 ");
								}
								mapVo.put("occur_date", temp[1]);
							} else {
								err_sb.append("第"+i+"行"+"业务日期为空  ");
							}
							
							//校验结算方式
							if (StringUtils.isNotEmpty(temp[2])) {
								String reg = "\\d{1,20}";
								AccPayType accPayType = null ; 
								boolean flag = temp[0].toString().matches(reg);
								
								if(flag == false){
									err_sb.append("第"+i+"行"+"结算方式编码格式错误  ");
								}else{
									mapVo.put("pay_code", temp[2]);
									accPayType = accPayTypeMapper.queryAccPayTypeByCode(mapVo);
								}
								
								if(accPayType == null){
									err_sb.append("第"+i+"行"+"结算方式不存在  ");
								}
								mapVo.put("pay_type_code",accPayType.getPay_code());
							} else {
								mapVo.put("pay_type_code","");
							}
							
							//票据号
							if (StringUtils.isNotEmpty(temp[3])) {
								mapVo.put("check_no", temp[3]);
							}else {
								mapVo.put("check_no","");
							}
							
							//摘要
							if (StringUtils.isNotEmpty(temp[4])) {
								mapVo.put("summary", temp[4]);
							} else {
								err_sb.append("第"+i+"行"+"摘要为空  ");
							}
							
							//借方金额
							if (ExcelReader.validExceLColunm(temp, 5)) {
								String reg = "[\\d]+(\\.\\d+)?";
								boolean flag = temp[5].toString().matches(reg);
								if(flag == false){
									err_sb.append("第"+i+"行"+"借方金额格式错误  ");
								}
								mapVo.put("debit", temp[5]);
							}else{
								mapVo.put("debit", 0.00);
							}
							
							//贷方金额
							if (ExcelReader.validExceLColunm(temp, 6)) {
								String reg = "[\\d]+[\\.]?[\\d]{0,3}";
								boolean flag = temp[6].toString().matches(reg);
								if(flag == false){
									err_sb.append("第"+i+"行"+"贷方金额格式错误  ");
								}else{
									mapVo.put("credit", temp[6]);
								}
							}else{
								mapVo.put("credit", 0.00);
							}
							
							//ACC_VOUCH_CHECK表默认值
							mapVo.put("vouch_id", "");
							mapVo.put("vouch_detail_id", "");
							mapVo.put("con_no", "");
							mapVo.put("business_no", "");
							mapVo.put("due_date",  "");
							mapVo.put("debit_w", 0);
							mapVo.put("credit_w", 0);
							mapVo.put("check1_id", "");
							mapVo.put("check1_no", "");
							mapVo.put("check2_id", "");
							mapVo.put("check2_no", "");
							mapVo.put("check3_id", "");
							mapVo.put("check3_no", "");
							mapVo.put("check4_id", "");
							mapVo.put("check4_no", "");
							mapVo.put("check5_id", "");
							mapVo.put("check5_no", "");
							mapVo.put("check6_id", "");
							mapVo.put("check6_no", "");
							mapVo.put("check7_id", "");
							mapVo.put("is_init",1);
							mapVo.put("old_check_id", "");

							maplist.add(mapVo);
							if(err_sb.toString().length() > 0 ){
								list_err.add(err_sb.toString());
								break;
							}
						}else{
							break;
						}
						
					}	
				}
			
			if(list_err.size() <= 0 ){
				if(!maplist.isEmpty()) {
					if("0".equals(MyConfig.getSysPara("018"))){//为0添加到ACC_TELL
						accTellMapper.addBatchAccTell(maplist);
					}else{//添加到ACC_VOUCH_CHECK
						accVouchCheckMapper.addBatchAccVouchCheck(maplist);
					}
				}
			}
			return err_sb.toString();
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";
		}
	}
	

	
	/**
	 * @Description 
	 * 银行对账单<BR> 查询AccTell
	 * @param  entityMap 
	 * @return AccTell
	 * @throws DataAccessException
	*/
	public AccTell queryAccTellByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return accTellMapper.queryAccTellByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 银行对账单<BR> 查询AccVouchCheck
	 * @param  entityMap 
	 * @return AccVouchCheck
	 * @throws DataAccessException
	*/
	public AccVouchCheck queryAccVouchCheckByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return accVouchCheckMapper.queryAccVouchCheckByCode(entityMap);
	}
	
	
	/*查询银行未达账初始余额*/
	@Override
	public String selectAccBankBalInit(Map<String, Object> entityMap)throws DataAccessException {
			
		try {
			if(entityMap.get("subj_code")==null || entityMap.get("subj_code").toString().equals("")){
				return "{\"bal\":\"0.00\"}";
			}
			
			String bal=accBankCheckMapper.selectAccBankBalInit(entityMap);
			return "{\"bal\":\""+bal+"\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);

		}
	}
}
