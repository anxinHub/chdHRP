package com.chd.hrp.acc.serviceImpl.vouch;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.NoTransactionException;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.exception.SysException;
import com.chd.base.ftp.FtpUtil;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.JsonListMapUtil;
import com.chd.base.util.NumberUtil;
import com.chd.base.util.SpreadTableJSUtil;
import com.chd.base.util.UUIDLong;
import com.chd.hrp.acc.dao.AccLederMapper;
import com.chd.hrp.acc.dao.vouch.AccVouchMapper;
import com.chd.hrp.acc.dao.vouch.SuperVouchMapper;
import com.chd.hrp.acc.entity.AccVouchAtt;
import com.chd.hrp.acc.entity.superVouch.SuperVouchMain;
import com.chd.hrp.acc.service.vouch.SuperPrintService;
import com.chd.hrp.acc.service.vouch.SuperVouchService;
import com.chd.hrp.sys.dao.ModStartMapper;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
import com.chd.hrp.sys.entity.ModStart;
 
/** 
 * @Title. @Description. 超级凭证ServiceImpl  
 * @Author: Perry
 * @Version: 1.0
*/
@Service("superVouchService")
public class SuperVouchServiceImpl implements SuperVouchService{
	
	private static Logger logger = Logger.getLogger(SuperVouchServiceImpl.class);
	private final DecimalFormat df= new DecimalFormat("0.00");  
	private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	
	@Resource(name = "superVouchMapper")
	private final SuperVouchMapper superVouchMapper = null;
	
	@Resource(name = "accVouchMapper")
	private final AccVouchMapper accVouchMapper = null;
	
	@Resource(name = "accLederMapper")
	private final AccLederMapper accLederMapper = null;
	
	@Resource(name = "superPrintService")
	private final SuperPrintService superPrintService = null;
	
	@Resource(name = "sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;
	
	@Resource(name = "modStartMapper")
	private final ModStartMapper modStartMapper = null;
	
	
	//凭证制单取所有相关参数
	@Override
	public String queryVouchParaList(Map<String, Object> entityMap)throws DataAccessException {
		// TODO Auto-generated method stub
		//entityMap.put("mod_code", "01");
		//entityMap.put("para_code", "'001','002','003','004','005','007','008','009','010','011','013','014','015','026','027','028','029','032'");
		//return JSON.toJSONString(superVouchMapper.queryVouchParaList(entityMap));
		StringBuffer paraJson=new StringBuffer();
		paraJson.append("{");
		paraJson.append("\"001\":\""+MyConfig.getSysPara("001")+"\",");
		paraJson.append("\"002\":\""+MyConfig.getSysPara("002")+"\",");
		paraJson.append("\"003\":\""+MyConfig.getSysPara("003")+"\",");
		paraJson.append("\"004\":\""+MyConfig.getSysPara("004")+"\",");
		paraJson.append("\"005\":\""+MyConfig.getSysPara("005")+"\",");
		paraJson.append("\"007\":\""+MyConfig.getSysPara("007")+"\",");
		paraJson.append("\"008\":\""+MyConfig.getSysPara("008")+"\",");
		paraJson.append("\"009\":\""+MyConfig.getSysPara("009")+"\",");
		paraJson.append("\"010\":\""+MyConfig.getSysPara("010")+"\",");
		paraJson.append("\"013\":\""+MyConfig.getSysPara("013")+"\",");
		paraJson.append("\"026\":\""+MyConfig.getSysPara("026")+"\",");
		paraJson.append("\"027\":\""+MyConfig.getSysPara("027")+"\",");
		paraJson.append("\"028\":\""+MyConfig.getSysPara("028")+"\",");
		paraJson.append("\"029\":\""+MyConfig.getSysPara("029")+"\",");
		paraJson.append("\"032\":\""+MyConfig.getSysPara("032")+"\",");
		paraJson.append("\"033\":\""+MyConfig.getSysPara("033")+"\",");
		paraJson.append("\"034\":\""+MyConfig.getSysPara("034")+"\",");
		paraJson.append("\"035\":\""+MyConfig.getSysPara("035")+"\",");
		paraJson.append("\"036\":\""+MyConfig.getSysPara("036")+"\",");
		paraJson.append("\"039\":\""+MyConfig.getSysPara("039")+"\",");
		paraJson.append("\"040\":\""+MyConfig.getSysPara("040")+"\",");
		paraJson.append("\"044\":\""+MyConfig.getSysPara("044")+"\",");
		paraJson.append("\"045\":\""+MyConfig.getSysPara("045")+"\",");
		paraJson.append("\"046\":\""+MyConfig.getSysPara("046")+"\",");
		paraJson.append("\"047\":\""+MyConfig.getSysPara("047")+"\",");
		paraJson.append("\"048\":\""+MyConfig.getSysPara("048")+"\",");
		paraJson.append("\"049\":\""+MyConfig.getSysPara("049")+"\",");
		paraJson.append("\"050\":\""+MyConfig.getSysPara("050")+"\"");
		paraJson.append("}");
		return paraJson.toString();
	}
	
	
	//凭证制单-分录-科目下拉框字典加载
	@Override
	public String queryAccVouchSubjDict(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return JSON.toJSONString(superVouchMapper.queryAccVouchSubjDict(entityMap));
	}

	//凭证制单-选择科目树
	@Override
	public String queryAccVcouchSubjTree(Map<String, Object> entityMap)
			throws DataAccessException {
			// TODO Auto-generated method stub
		StringBuilder json = new StringBuilder();
		
		json.append("{Rows:[");
		
		List<Map<String,Object>> subjList = superVouchMapper.queryAccVcouchSubjTree(entityMap);
		
		if (subjList!=null && subjList.size()>0) {
	
			for(Map<String,Object> map:subjList){
				
				json.append("{");
				for(Map.Entry<String, Object> entry:map.entrySet()){
					
					json.append(entry.getKey()+":'"+entry.getValue()+"',");
					
				}
				json.setCharAt(json.length() - 1, '}'); //替换最后一个逗号
				json.append(",");
			}
		}
		json.setCharAt(json.length() - 1, ']'); //替换最后一个逗号
		json.append("}");
		//logger.warn("json:"+jsonResult.toString());
		return json.toString();
	}
	
	//凭证制单-分录、辅助核算-摘要下拉框字典
	@Override
	public String queryVouchSummaryDict(Map<String, Object> entityMap)
			throws DataAccessException {
		
		StringBuilder sb=new StringBuilder();
		sb.append("[");
		List<String> li=new ArrayList<String>();
		entityMap.put("user_id", SessionManager.getUserId());
		li=superVouchMapper.queryVouchSummaryDict(entityMap);
		if(li!=null && li.size()>0){
			for(String s:li){
				sb.append("\""+s.replaceAll("\"","\\\\\"")+"\",");
			}
			sb.setCharAt(sb.length() - 1, ']'); 
		}else{
			sb.append("]"); 
		}
		
		return sb.toString();
	}
	
	
	//凭证制单-取当前年度最大凭证日期 
	@Override
	public String queryMaxVouchDate(Map<String, Object> map){
		String vouchDate="";
		String accYearMonth=MyConfig.getCurAccYearMonth();
		
		if(map.get("p029")==null){
			map.put("p029",MyConfig.getSysPara("029"));
		}
		
		if(map.get("p029").equals("0")){
			
			//取当前会计期间最大凭证日期
			if(accYearMonth!=null && !accYearMonth.equals("")){
				map.put("acc_year", accYearMonth.substring(0,4));
				map.put("acc_month", accYearMonth.substring(4,6));
				vouchDate=superVouchMapper.queryMaxVouchDate(map);
				if(vouchDate==null)vouchDate=accYearMonth.substring(0,4)+"-"+accYearMonth.substring(4,6)+"-01";
			}
		}else{
			
			//取本账套最大凭证日期
			vouchDate=superVouchMapper.queryMaxVouchDate(map);
			if(vouchDate!=null && !vouchDate.equals("") && accYearMonth!=null && !accYearMonth.equals("")){				
				int vd=Integer.parseInt(vouchDate.replace("-", "").substring(0,6));
				if(vd<Integer.parseInt(accYearMonth)){
					//最大凭证日期的年月<当前会计期间，取当前会计期间最小的日期
					vouchDate=accYearMonth.substring(0,4)+"-"+accYearMonth.substring(4,6)+"-01";
				}
				
			}
		}
		
		//如果没有查询到最大的凭证日期，从会计启用年度里面取
		if(vouchDate==null || vouchDate.equals("")){
			map.put("mod_code", "01");
			ModStart modStart=modStartMapper.queryModStartByCode(map);
			String accYear=SessionManager.getAcctYear();
			String accMonth="01";
			if(modStart!=null && modStart.getStart_year()!=null && !modStart.getStart_year().equals("")){
				accYear=modStart.getStart_year();
			}
			if(modStart!=null && modStart.getStart_month()!=null && !modStart.getStart_month().equals("")){
				accMonth=modStart.getStart_month();
			}
			vouchDate=accYear+"-"+accMonth+"-01";
		}
		
		return vouchDate;
	}

	
	//凭证制单-根据凭证类型、凭证日期取最大凭证号
	@Override
	public String queryMaxVouchNo(Map<String, Object> entityMap) throws DataAccessException {
		
		String reMaxVouchNo = null;
		String no="";
		String p001=null;//凭证号位数
		if(entityMap.get("p001")!=null && !entityMap.get("p001").equals("")){
			//传入001参数，直接取
			p001=entityMap.get("p001").toString();
		}else{
			//没有传001参数，从数据库里面取
//			entityMap.put("mod_code", "01");
//			entityMap.put("para_code", "'001'");
//			List<Map<String,Object>> paMap=new ArrayList<Map<String,Object>>();
//			paMap=superVouchMapper.queryVouchParaList(entityMap);
//			if(paMap!=null && paMap.size()>0){
//				p001=paMap.get(0).get("para_value").toString();
//			}else{
//				logger.error("系统参数001取值异常。");
//				reMaxVouchNo="{\"error\":\"系统参数001取值异常。\"}";
//				return reMaxVouchNo;
//			}
			p001=MyConfig.getSysPara("001");
		}
		
		int newVouchNo=0;
		if(entityMap.get("reMaxVouchNo")!=null && !entityMap.get("reMaxVouchNo").equals("")){
			//传入凭证号，直接取，修改凭证号用到
			reMaxVouchNo=entityMap.get("reMaxVouchNo").toString();
			newVouchNo=Integer.parseInt(reMaxVouchNo);
			
		}else{
			
			//033凭证号取值方式:0按会计期间取值,1按凭证日期取值(调用时传入凭证日期)
			if(MyConfig.getSysPara("033").equals("0")){
				entityMap.remove("vouch_date");
			}
			//没有传凭证号，从数据库里面查
			reMaxVouchNo=superVouchMapper.queryMaxVouchNo(entityMap);
			
			//没有凭证号
			if(reMaxVouchNo==null || reMaxVouchNo.equals("")){
				
				//根据凭证号的位数，将会在凭证号前面补0
				for(int i=1;i<Integer.parseInt(p001);i++){
					no+="0";
				}
				return reMaxVouchNo=no+"1";//"{\"vn\":\""+no+"1\"}";
			}else{
				//有凭证号+1
				newVouchNo=Integer.parseInt(reMaxVouchNo)+1;
			}
			
		}
		

		if(Integer.parseInt(p001)>1 && String.valueOf(newVouchNo).length()>Integer.parseInt(p001)){
			logger.error("凭证号溢出.");
			return reMaxVouchNo="{\"error\":\"凭证号溢出.\"}";
		}
		
		if(Integer.parseInt(p001)>1){
			for(int i=0;i<Integer.parseInt(p001)-(String.valueOf(newVouchNo).length());i++){
				no+="0";
			}
		}
		reMaxVouchNo=no+newVouchNo;//"{\"vn\":\""+no+newVouchNo+"\"}";
		
		//logger.debug(reMaxVouchNo);
		return reMaxVouchNo;
		
	}
	
	/*
	 * 保存凭证（添加、修改操作）
	 * 思路：
	 * 1、凭证主表，没有就添加，有就修改，草稿凭证不存凭证号，保存正式凭证时重新取最大凭证号
	 * 2、凭证明细表，先删除后添加，如果有vouch_detail_id原有ID不变
	 * 3、凭证辅助核算表，根据vouch_detail_id先删除后添加，一条分录批量保存一次辅助核算，如果有vouch_check_id原有ID不变
	 * 4、现金流量表，根据vouch_detail_id先删除后添加，如果有cash_id原有ID不变
	 */
	@Override
	public synchronized String saveSuperVouch(Map<String, Object> mapVo)throws DataAccessException{
		// TODO Auto-generated method stub
		String reJson=null;
		String vouchId=null;
		Calendar calendar=null;
		int state=0;
		String group_id= SessionManager.getGroupId();
		String hos_id= SessionManager.getHosId();
		String copy_code= SessionManager.getCopyCode();
		String createUserId=SessionManager.getUserId();
		String acc_year= mapVo.get("vouchDate").toString().substring(0, 4);
		String acc_month= mapVo.get("vouchDate").toString().substring(5, 7);
		boolean isPrint=Boolean.parseBoolean(mapVo.get("isPrint").toString());
		
		if(Integer.parseInt(acc_year+acc_month)<Integer.parseInt(MyConfig.getAccYearMonth().getCurYearMonthAcc())){
			return "{\"error\":\"会计期间：【" + acc_year+acc_month + "】已结账.\"}";
		}
		
		try{
		/*********************组装凭证主表数据**********************************************/
		Map<String, Object> vouchMap=new HashMap<String, Object>(); 
		vouchMap.put("group_id",group_id);
		vouchMap.put("hos_id", hos_id);
		vouchMap.put("copy_code", copy_code);
		vouchMap.put("acc_year", acc_year);
		vouchMap.put("acc_month", acc_month);
		vouchMap.put("vouch_type_code", mapVo.get("vouchType"));
		Date vouchDate=DateUtil.stringToDate(mapVo.get("vouchDate").toString(),"yyyy-MM-dd");
		vouchMap.put("vouch_date", vouchDate);
		vouchMap.put("create_user",createUserId);
		Date createDate=DateUtil.getCurrenDate();
		vouchMap.put("create_date",createDate);
		vouchMap.put("vouch_att_num",mapVo.get("fileNum"));
		vouchMap.put("state",mapVo.get("state"));
		vouchMap.put("vouch_id_cx","");
		vouchMap.put("busi_type_code",mapVo.get("busi_type_code"));
		if(mapVo.get("busi_type_code")!=null && "Z006".equals(mapVo.get("busi_type_code").toString())){
			//收支结账凭证不需要批注
			vouchMap.put("sign_flag",2);
		}else{
			vouchMap.put("sign_flag",0);
		}
		
		//p002=0，如果草稿凭证不占凭证号，凭证号存0。保存正常凭证时，再取最大凭证号。
		if(mapVo.get("state").toString().equals("-1") && mapVo.get("p002").toString().equals("0")){
			String vouchNo="0";
			//取凭证位数
			for(int i=1;i<Integer.parseInt(mapVo.get("p001").toString());i++){
				vouchNo+="0";
			}
			vouchMap.put("vouch_no", vouchNo);
			
		}else{
			
			//是否生成新的凭证号
			int isGetNewVNo=Integer.parseInt(mapVo.get("isGetNewVNo").toString());
			
			if(isGetNewVNo==0){
				//凭证日期的年月发生改变
				if(mapVo.get("oldVouchDate")!=null && !mapVo.get("oldVouchDate").toString().equals("")){
					String old_acc_year= mapVo.get("oldVouchDate").toString().substring(0, 4);
					String old_acc_month= mapVo.get("oldVouchDate").toString().substring(5, 7);
					if(!old_acc_year.equals(acc_year) || !old_acc_month.equals(acc_month)){
						if(mapVo.get("p007").toString().equals("1")){
							//允许修改凭证号，不取新的凭证号
							isGetNewVNo=0;
						}else{
							isGetNewVNo=1;
						}
					}
				}
				
				//凭证类型发生改变
				if(mapVo.get("oldVouchType")!=null && !mapVo.get("oldVouchType").toString().equals("") && !mapVo.get("oldVouchType").toString().equals(mapVo.get("vouchType").toString())){
					if(mapVo.get("p007").toString().equals("1")){
						//允许修改凭证号，不取新的凭证号
						isGetNewVNo=0;
					}else{
						isGetNewVNo=1;
					}
				}
			}
			
			if(Integer.parseInt(mapVo.get("vouch_no").toString())==0){
				//凭证号=0，草稿凭证，重新取最大凭证号
				isGetNewVNo=1;
			}
			
			
			Map<String,Object> vnoMap=new HashMap<String,Object>();
			vnoMap.put("group_id",group_id);
			vnoMap.put("hos_id", hos_id);
			vnoMap.put("copy_code", copy_code);
			vnoMap.put("acc_year", acc_year);
			vnoMap.put("acc_month", acc_month);
			vnoMap.put("vouch_type_code", mapVo.get("vouchType"));
			vnoMap.put("vouch_date", mapVo.get("vouchDate"));
			vnoMap.put("p001", mapVo.get("p001"));
			
			if(isGetNewVNo==0){
				//直接取输入的凭证号，然后按凭证号位数拼接
				vnoMap.put("reMaxVouchNo", mapVo.get("vouch_no"));
			}
			
			String maxVouchNo=queryMaxVouchNo(vnoMap);
			if(maxVouchNo.indexOf("error")!=-1){
				return maxVouchNo;
			}
			vnoMap.put("vouch_date", mapVo.get("vouchDate"));//queryMaxVouchNo会remove("vouch_date");
			//检查凭证号是否重复
			Map<String,Object> checkVouchNoMap=new HashMap<String,Object>();
			checkVouchNoMap.put("group_id",group_id);
			checkVouchNoMap.put("hos_id", hos_id);
			checkVouchNoMap.put("copy_code", copy_code);
			checkVouchNoMap.put("acc_year", acc_year);
			checkVouchNoMap.put("field_table","ACC_VOUCH");
			checkVouchNoMap.put("field_key1", "to_number(vouch_no)");
			checkVouchNoMap.put("field_value1", Integer.parseInt(maxVouchNo));
			checkVouchNoMap.put("field_key2", "vouch_type_code");
			checkVouchNoMap.put("field_value2", mapVo.get("vouchType"));
			checkVouchNoMap.put("field_key3", "acc_month");
			checkVouchNoMap.put("field_value3", acc_month);
			int count =0;
			if(mapVo.get("vouchId")!=null && !mapVo.get("vouchId").equals("")){
				checkVouchNoMap.put("field_id","vouch_id");
				checkVouchNoMap.put("field_id_value",mapVo.get("vouchId"));
				//修改凭证验证
				count=sysFunUtilMapper.existsSysCodeNameByUpdate(checkVouchNoMap);
			}else{
				//新建凭证验证
				count=sysFunUtilMapper.existsSysCodeNameByAdd(checkVouchNoMap);
			}
			
			if (count >0) {
				return "{\"error\":\"凭证号：【" + maxVouchNo + "】重复.\"}";
			}
			
			vouchMap.put("vouch_no", maxVouchNo);
			
			/*
			 * 003序时控制			 * 
			 * */
			
			if(MyConfig.getSysPara("003").equals("1")){
				//取小于凭证日期最大凭证号、取大于凭证日期最小凭证号
				String[] maxMinNo=superVouchMapper.queryMaxMinNoByVouchDate(vnoMap);
				if(maxMinNo!=null && maxMinNo.length>0){
					
					//凭证号必须在[maxVouchNo,minVouchNo]之间
					boolean isXsKz=false;
					String xsError="";
					if(maxMinNo[0]!=null && !maxMinNo[0].equals("")){
						if(Integer.parseInt(maxVouchNo)<=Integer.parseInt(maxMinNo[0])){
							isXsKz=true;
						}
						xsError="大于【"+maxMinNo[0]+"】";
					}
					
					if(maxMinNo[1]!=null && !maxMinNo[1].equals("")){
						if(Integer.parseInt(maxVouchNo)>=Integer.parseInt(maxMinNo[1])){
							isXsKz=true;
						}
						xsError=xsError+"小于【"+maxMinNo[1]+"】";
					}
					if(isXsKz){
						return "{\"error\":\"凭证不符合序时账要求：<br/>"+vnoMap.get("vouch_date")+"的凭证号必须"+xsError+".\"}";
					}
				}
			}
		}
		
		boolean isUpdate=false;
		//修改操作
		List<String> validateList=new ArrayList<String>();
		if(!mapVo.get("vouchId").toString().equals("")){
			vouchId=mapVo.get("vouchId").toString();
			vouchMap.put("vouch_id",vouchId);
			
			//判断分录是否出纳对账
			validateList=superVouchMapper.queryVouchByTellVeri(vouchMap);
			if(validateList!=null && validateList.size()>0){
				if(isPrint){
					return "{\"state\":\"true\",\"vouchId\":"+vouchId+",\"isRe\":\"true\",\"tip\":\"出纳已对账\"}";
				}else{
					return "{\"error\":\"出纳已对账，科目：<br/>"+validateList.toString()+"\"}";
				}
				
			}
			
			//判断辅助核算是否单位银行对账
			validateList=superVouchMapper.queryVouchByBankVeri(vouchMap);
			if(validateList!=null && validateList.size()>0){
				if(isPrint){
					return "{\"state\":\"true\",\"vouchId\":"+vouchId+",\"isRe\":true,\"tip\":\"单位银行已对账\"}";
				}else{
					return "{\"error\":\"单位银行已对账，科目：<br/>"+validateList.toString()+"\"}";
				}
			}
			
			//判断辅助核算是否往来核销
			validateList=superVouchMapper.queryVouchByVeri(vouchMap);
			if(validateList!=null && validateList.size()>0){
				if(isPrint){
					return "{\"state\":\"true\",\"vouchId\":"+vouchId+",\"isRe\":true,\"tip\":\"已往来核销\"}";
				}else{
					return "{\"error\":\"已往来核销，科目：<br/>"+validateList.toString()+"\"}";
				}
			}
			
			//修改凭证主表
			state=superVouchMapper.updateSuperVouchByMain(vouchMap);
			isUpdate=true;
		}else{
			//添加操作
			if(mapVo.get("auto_id")!=null && !mapVo.get("auto_id").toString().equals("")){
				//自动凭证传过来的vouch_id
				vouchId=mapVo.get("auto_id").toString();
			}else{
				vouchId=UUIDLong.absStringUUID();
			}
			vouchMap.put("vouch_id",vouchId);
			//添加凭证主表
			state=superVouchMapper.saveSuperVouchByMain(vouchMap);
		}
		
		if(state==0){
			reJson="{\"msg\":\"保存失败。\",\"state\":\"false\"}";
			return reJson;
		}	
		
		if(mapVo.get("template_code")!=null && !mapVo.get("template_code").equals("")){
			//取模板都需要重新生成明细ID
			isUpdate=false;
		}
		
		/*********************组装凭证明细表数据**********************************************/
		int p032=Integer.parseInt(MyConfig.getSysPara("032"));//凭证制单，银行科目默认弹出辅助核算窗口
		int p004=Integer.parseInt(MyConfig.getSysPara("004"));//凭证制单，辅助核算窗口显示现金流量
		Map<String,Object> checkMap=null;
		JSONArray detailjson = JSONObject.parseArray(mapVo.get("detail").toString());
		List<Map<String,Object>> detailList=new ArrayList<Map<String,Object>>();
		List<List<Map<String,Object>>> checkList=new ArrayList<List<Map<String,Object>>>();//凭证的所有辅助核算
		List<Map<String,Object>> cashList=new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> checkDetailList=null;
		List<Map<String,Object>> detailCheckIdList=null;
		List<Map<String,Object>> checkTypeList=null;
		Map<String,Object> cashMap=null;
		
		for(int i=0;i<detailjson.size();i++){
			JSONObject detail = JSONObject.parseObject(detailjson.getString(i));
			String detailSummary="";
			Map<String,Object> detailMap=new HashMap<String, Object>();
			String detailId=null;
			if(detail.getString("did")!=null && !detail.getString("did").equals("") && isUpdate){
				detailId=detail.getString("did");
			}else{
				detailId=UUIDLong.absStringUUID();
			}
			detailMap.put("vouch_detail_id", detailId);
			detailMap.put("vouch_id", vouchId);
			detailMap.put("group_id", group_id);
			detailMap.put("hos_id", hos_id);
			detailMap.put("copy_code", copy_code);
			detailMap.put("acc_year", acc_year);
			if(detail.getString("budg_summary")!=null && !detail.getString("budg_summary").equals("")){
				detailSummary=detail.getString("budg_summary");
			}else{
				detailSummary=detail.getString("summary");
			}
			detailMap.put("summary", detailSummary);
			String subjId=detail.getString("sid");
			detailMap.put("subj_code", subjId);
			String detailDebit=detail.getString("debit");
			detailMap.put("debit",detailDebit );
			detailMap.put("credit", detail.getString("credit"));
			detailMap.put("debit_w", 0.00);
			detailMap.put("credit_w", 0.00);
			detailMap.put("vouch_row", detail.getString("vouch_row"));
			detailList.add(detailMap);
			String isCheck=detail.getString("is_check");
			String isCash=detail.getString("is_cash");
			String isBill=detail.getString("is_bill");
			String natureCode=detail.getString("nature_code").toString();
			if(isBill.equals("1") || (p032==1 && natureCode.equals("03"))  || (p004==1 && isCash.equals("1"))){
				isCheck="1";
			}
			
			/*********************组装凭证辅助核算表数据***********************************************/
			checkDetailList=new ArrayList<Map<String,Object>>();//分录的辅助核算
			boolean isCheckJson=false;
			boolean isCashJson=false;
			if(isCheck.equals("1") && detail.getString("check")!=null && !detail.getString("check").equals("")){
				
				JSONArray checkjson = JSONObject.parseArray(detail.getString("check"));
				for(int j=0;j<checkjson.size();j++){
					
					JSONObject check = JSONObject.parseObject(checkjson.getString(j));
					checkMap=new HashMap<String, Object>();
					String vouchCheckId=null;
					if(check.getString("cid")!=null && !check.getString("cid").equals("")){
						vouchCheckId=check.getString("cid");
					}else{
						vouchCheckId=UUIDLong.absStringUUID();
					}
					checkMap.put("vouch_check_id", vouchCheckId);
					checkMap.put("vouch_id", vouchId);
					checkMap.put("vouch_detail_id", detailId);
					checkMap.put("group_id", group_id);
					checkMap.put("hos_id", hos_id);
					checkMap.put("copy_code", copy_code);
					checkMap.put("acc_year", acc_year);
					if(check.getString("summary")!=null && !check.getString("summary").equals("")){
						checkMap.put("summary", check.getString("summary"));
					}else{
						checkMap.put("summary", detailSummary);
					}
					checkMap.put("subj_code", subjId);
					checkMap.put("debit_w", 0.00);
					checkMap.put("credit_w", 0.00);
					
					if(!detailDebit.equals("") && Float.parseFloat(detailDebit)!=0){
						checkMap.put("debit", check.getString("money").replace(",", ""));
						checkMap.put("credit", 0.00);
					}else{
						checkMap.put("debit", 0.00);
						checkMap.put("credit", check.getString("money").replace(",", ""));
					}
					
					if(check.getString("occur_date")!=null && !check.getString("occur_date").equals("")){
						isCheckJson=true;
						checkMap.put("occur_date", DateUtil.stringToDate(check.getString("occur_date"),"yyyy-MM-dd"));//发生日期
					}else{
						checkMap.put("occur_date", vouchDate);//没有发生日期，默认插入凭证日期
					}
					
					checkMap.put("sort_code", j+1);//辅助核算新增序列号
					
					//动态拼辅助核算字段，循环遍历键值对
					StringBuilder column=new StringBuilder();//字段
					StringBuilder columnValue=new StringBuilder();//值
					for (Map.Entry<String, Object> entry : check.entrySet()) {
						
						if(entry.getKey().equalsIgnoreCase("due_date")){
							//到期日期
							isCheckJson=true;
							column.append(entry.getKey()+",");
							columnValue.append("to_date('"+entry.getValue()+"','yyyy-MM-dd'),");
							
						}else if(entry.getKey().equalsIgnoreCase("paper_type_code")){
							//票据类型
							isCheckJson=true;
							column.append(entry.getKey()+",");
							if(entry.getValue()!=null && !entry.getValue().toString().replace(" ", "").equals("")){
								columnValue.append("'"+entry.getValue().toString().split(" ")[0]+"',");
							}else{
								columnValue.append("'',");
							}
							
						}else if(entry.getKey().equalsIgnoreCase("pay_type_code") || entry.getKey().equalsIgnoreCase("con_no") 
								|| entry.getKey().equalsIgnoreCase("business_no") || entry.getKey().equalsIgnoreCase("check_no") || entry.getKey().toLowerCase().startsWith("zcheck")){
							//结算方式、合同号、单据号、票据号、自定义核算
							isCheckJson=true;
							column.append(entry.getKey()+",");
							columnValue.append("'"+entry.getValue()+"',");
							
						}else if(entry.getKey().toLowerCase().startsWith("check")){
							//系统核算
							isCheckJson=true;
							if(entry.getKey().equalsIgnoreCase("check7") || entry.getKey().equalsIgnoreCase("check8")){
								//资金来源、单位核算
								column.append(entry.getKey()+"_ID,");
								columnValue.append("'"+entry.getValue()+"',");
							}else{
								//系统核算check1到check6
								column.append(entry.getKey()+"_ID,");
								column.append(entry.getKey()+"_NO,");
								if(entry.getValue().toString().split("@").length<2){
									throw new SysException(subjId+"，第"+(j+1)+"行辅助核算数据录入错误！");
								}
								columnValue.append("'"+entry.getValue().toString().split("@")[0]+"',");
								columnValue.append("'"+entry.getValue().toString().split("@")[1]+"',");
							}
							
						}									
						
					}
					
					checkMap.put("column", (column==null || column.length()==0)?"":column.toString());
					checkMap.put("column_value", (columnValue==null || columnValue.length()==0)?"":columnValue.toString());
					
					if(isCheckJson){
						checkDetailList.add(checkMap);
					}
					
					/*********************组装现金流量标注表数据，辅助核算和现金流量一起保存**********************************************/
					if(isCash.equals("1") && check.getString("cash_item_id")!=null && !check.getString("cash_item_id").equals("")){
						isCashJson=true;
						cashMap=new HashMap<String, Object>();
						String cashId=UUIDLong.absStringUUID();
						cashMap.put("cash_id", cashId);
						cashMap.put("vouch_id", vouchId);
						cashMap.put("vouch_detail_id", detailId);
						cashMap.put("group_id", group_id);
						cashMap.put("hos_id", hos_id);
						cashMap.put("copy_code", copy_code);
						cashMap.put("cash_item_id", check.getString("cash_item_id"));
						cashMap.put("cash_money", check.getString("money").replace(",", ""));
						cashMap.put("create_user", createUserId);
						cashMap.put("create_date", createDate);
						if(check.getString("summary")!=null && !check.getString("summary").equals("")){
							cashMap.put("summary", check.getString("summary"));
						}else{
							cashMap.put("summary", detailSummary);
						}
						
						cashMap.put("sort_code", j+1);//新增序列号
						cashList.add(cashMap);
					}
				}
				//保存辅助核算表,一条分录批量保存一次，因为不同而分录的表头是不一样的，这样做也是为了降低数据库的长时间操作，所以增加访问次数
				if(checkDetailList!=null && checkDetailList.size()>0)checkList.add(checkDetailList);
				
			}else if(natureCode.equals("03") || natureCode.equals("04") || natureCode.equals("05")){
				//添加分录的时候，科目性质03、04、05银行、应收、应付科目，在没有辅助核算的情况下，以分录数据默认添加一条数据用来做账龄分析、往来核销、出纳对账、单位银行对账
			
				//为了不影响对账和核销的数据，需要存原来的vouch_check_id，
				String vouchCheckId=null;
				if(detailCheckIdList==null){
					detailCheckIdList=new ArrayList<Map<String,Object>>();
					detailCheckIdList=superVouchMapper.queryCheckIdByDetailId(detailMap);
				}
				if(detailCheckIdList!=null && detailCheckIdList.size()>0){
					for(Map<String,Object> ckId:detailCheckIdList){
						if(ckId.get("vouch_detail_id").toString().equals(detailId)){
							//一条分录只有一条默认的辅助核算
							vouchCheckId=ckId.get("vouch_check_id").toString();
							break;
						}
					}
				}
				
				if(vouchCheckId==null){
					vouchCheckId=UUIDLong.absStringUUID();
				}

				isCheckJson=true;
				checkMap=new HashMap<String, Object>();
				checkMap.put("vouch_check_id", vouchCheckId);
				checkMap.put("vouch_id", vouchId);
				checkMap.put("vouch_detail_id", detailId);
				checkMap.put("group_id", group_id);
				checkMap.put("hos_id", hos_id);
				checkMap.put("copy_code", copy_code);
				checkMap.put("acc_year", acc_year);
				checkMap.put("summary", detail.getString("summary"));
				checkMap.put("subj_code", subjId);
				checkMap.put("debit_w", 0.00);
				checkMap.put("credit_w", 0.00);
				checkMap.put("sort_code", 1);//新增序列号
				if(!detailDebit.equals("") && Float.parseFloat(detailDebit)!=0){
					checkMap.put("debit", detailDebit);
					checkMap.put("credit", 0.00);
				}else{
					checkMap.put("debit", 0.00);
					checkMap.put("credit", detail.getString("credit"));
				}
				
				checkMap.put("occur_date", vouchDate);//没有发生日期，默认插入凭证日期
				//往来科目
				if(natureCode.equals("04") || natureCode.equals("05")){
									
					checkMap.put("column", "due_date,");//到期日期
					if(mapVo.get("p002").toString().equals("0")){
						//到期日期，默认月底
						calendar = new GregorianCalendar();  
						calendar.setTime(vouchDate);
						calendar.add(Calendar.MONTH, 1);  
				        calendar.set(Calendar.DAY_OF_MONTH, 0);
				    	String duDate=DateUtil.dateToString(calendar.getTime(),"yyyy-MM-dd");
						checkMap.put("column_value", "to_date('"+duDate+"','yyyy-MM-dd'),");
					}else{
						//到期日期，默认年底
						checkMap.put("column_value", "to_date('"+acc_year+"-12-31','yyyy-MM-dd'),");
					}
				}else{
					checkMap.put("column", "");
					checkMap.put("column_value", "");
				}
				
				checkDetailList.add(checkMap);
				checkList.add(checkDetailList);
				
			}
			
		
			/*********************组装现金流量数据，辅助核算和现金流量分开保存**********************************************/
			if(isCash.equals("1") && detail.getString("cash")!=null && !detail.getString("cash").equals("") && !isCashJson){
				JSONArray cashjson = JSONObject.parseArray(detail.getString("cash"));
				for(int j=0;j<cashjson.size();j++){
					JSONObject cash = JSONObject.parseObject(cashjson.getString(j));
					/*********************组装现金流量标注表数据**********************************************/
					if(cash.getString("cash_item_id")!=null && !cash.getString("cash_item_id").equals("")){
						isCashJson=true;
						cashMap=new HashMap<String, Object>();
						String cashId=UUIDLong.absStringUUID();
						cashMap.put("cash_id", cashId);
						cashMap.put("vouch_id", vouchId);
						cashMap.put("vouch_detail_id", detailId);
						cashMap.put("group_id", group_id);
						cashMap.put("hos_id", hos_id);
						cashMap.put("copy_code", copy_code);
						cashMap.put("cash_item_id", cash.getString("cash_item_id"));
						cashMap.put("cash_money", cash.getString("money").replace(",", ""));
						cashMap.put("create_user", createUserId);
						cashMap.put("create_date", createDate);
						if(cash.getString("summary")!=null && !cash.getString("summary").equals("")){
							cashMap.put("summary", cash.getString("summary"));
						}else{
							cashMap.put("summary", detailSummary);
						}
						cashMap.put("sort_code", j+1);//新增序列号
						cashList.add(cashMap);
					}
				}
				
			}
		
		}
		
		Map<String, Object> detailMap=new HashMap<String, Object>(); 
		detailMap.put("group_id",group_id);
		detailMap.put("hos_id", hos_id);
		detailMap.put("copy_code", copy_code);
		detailMap.put("copy_code", copy_code);
		detailMap.put("vouch_id", vouchId);
		
		if(!mapVo.get("vouchId").toString().equals("")){
			//根据vouch_id删除凭证辅助核算表
			superVouchMapper.deleteSuperVouchByCheckAndVouchId(group_id,hos_id,copy_code,vouchId);
			//根据vouch_id删除现金流量表
			superVouchMapper.deleteSuperVouchByCashAndVouchId(group_id,hos_id,copy_code,vouchId);
			//根据vouch_id删除凭证明细表 
			superVouchMapper.deleteSuperVouchByDetail(detailMap);
			//根据vouch_id删除差异标注表 
			superVouchMapper.deleteSuperVouchByDiff(group_id,hos_id,copy_code,vouchId);
		}
		
		
		if(detailList!=null && detailList.size()>0){
			
			//保存凭证明细表
			state=superVouchMapper.saveSuperVouchByDetail(detailList);
			
			if(state==0){
				//草稿可以不保存分录
				if(mapVo.get("state").equals("-1")){
					reJson="{\"msg\":\"草稿凭证，保存成功。\",\"state\":\"true\",\"vouchId\":"+vouchId+"}";
				}else{
					reJson="{\"msg\":\"保存分录失败。\",\"state\":\"false\"}";
				}
				return reJson;
			}	
				
			//保存辅助核算表,一条分录批量保存一次，因为不同而分录的表头是不一样的，这样做也是为了降低数据库的长时间操作，所以增加访问次数。
			if(checkList!=null && checkList.size()>0){
				for(List<Map<String,Object>> lm :checkList){
					if(lm!=null && lm.size()>0){
						String column=lm.get(0).get("column").toString();
						//保存辅助核算
						superVouchMapper.saveSuperVouchByCheck(column,lm);
					}
				}
				
			}
			
			//保存现金流量标注表
			if(cashList!=null && cashList.size()>0){
				superVouchMapper.saveSuperVouchByCash(cashList);
			}
			
			/*验证分录科目是否有辅助核算*/
			checkMap=new HashMap<String,Object>();
			checkMap.put("group_id", group_id);
			checkMap.put("hos_id", hos_id);
			checkMap.put("copy_code", copy_code);
			for(int i=0;i<detailjson.size();i++){
				JSONObject detail = JSONObject.parseObject(detailjson.getString(i));
				if(detail.getString("is_check").equals("0")){
					continue;
				}
				
				checkMap.put("acc_year", acc_year);
				checkMap.put("subj_code", detail.getString("sid"));
				checkTypeList=superVouchMapper.queryVouchCheckType(checkMap);
				if(checkTypeList!=null && checkTypeList.size()>0){
					
					checkMap.put("vouch_id", vouchId);
					StringBuffer exisSql=new StringBuffer();
					exisSql.append(" and ( ");
					int index=0;
					for(Map<String,Object> map:checkTypeList){
						String column_check=map.get("column_check").toString();
						
						if(map.get("is_sys").toString().equals("1")){
							column_check=column_check+"_id";
						}
						if(index==0){
							exisSql.append(column_check +" is null ");
						}else{
							exisSql.append(" or "+column_check +" is null ");
						}
					
						index++;
					}
					exisSql.append(" )");
					checkMap.put("exis_sql",exisSql);
					String subj=superVouchMapper.queryVouchCheckExists(checkMap);
					if(subj!=null && !subj.equals("")){
						throw new SysException("科目："+subj+"辅助核算为空");
					}
					
				}
				
			}
		}
		
		//验证分录金额是否与辅助核算金额一致
		validateList=superVouchMapper.queryVouchDedailAndCheckByMoney(group_id,hos_id,copy_code,vouchId);
		if(validateList!=null && validateList.size()>0){
			throw new SysException("分录金额与辅助核算不一致：<br/>"+validateList.toString()+"");
		}
		
		//验证分录金额是否与现金流量标注金额一致，后标注的也需要判断
		//if(mapVo.get("p026").toString().equals("1")){
			validateList=superVouchMapper.queryVouchDedailAndCashByMoney(group_id,hos_id,copy_code,vouchId);
			if(validateList!=null && validateList.size()>0){
				throw new SysException("分录金额与现金流量标注不一致：<br/>"+validateList.toString()+"");
			}	
		//}
		
		//验证金额方向是否与现金流量标注方向一致，后标注的也需要判断
		//if(mapVo.get("p026").toString().equals("1")){
			validateList=superVouchMapper.queryVouchDedailAndCashByDire(group_id,hos_id,copy_code,vouchId);
			if(validateList!=null && validateList.size()>0){
				throw new SysException("分录金额与现金流量标注方向不一致：<br/>"+validateList.toString()+"");
			}	
		//}
		
		//更新票据核销状态
		superVouchMapper.updateSuperVouchByCheckNo(vouchMap);
				
		/*添加凭证日志*/
		if(mapVo.get("vouchId").toString().equals("") && mapVo.get("busi_log_table")!=null && !mapVo.get("busi_log_table").equals("")){
			
			if(mapVo.get("auto_id")!=null && !mapVo.get("auto_id").toString().equals("")){
				//新的自动凭证处理方式
				Map<String,Object> logMap=new HashMap<String,Object>();
				
				if(mapVo.get("busi_type_code").toString().equalsIgnoreCase("Z011")){
					
					//出纳凭证回写出纳登记表
					String[] busiNo = mapVo.get("busi_no").toString().split(",");
					logMap.put("group_id", group_id);
					logMap.put("hos_id", hos_id);
					logMap.put("copy_code", copy_code);
					logMap.put("vouch_id", vouchId);
					logMap.put("busi_no", busiNo);
					superVouchMapper.updateSuperVouchByAccTell(logMap);
					
				}else{
					
					//业务系统自动凭证
					logMap.put("group_id", group_id);
					logMap.put("hos_id", hos_id);
					logMap.put("copy_code", copy_code);
					logMap.put("acc_year", acc_year);
					logMap.put("vouch_id", vouchId);
					logMap.put("create_user", createUserId);
					logMap.put("busi_log_table", mapVo.get("busi_log_table"));
					
					if(mapVo.get("busi_log_table").toString().equalsIgnoreCase("ACC_BUSI_LOG_ZZ")){
						//期末处理、工资转账、出纳等其他凭证业务类型，需要保存会计期间
						logMap.put("acc_month_col", "acc_month");
						logMap.put("acc_month_val", acc_month);
					}
					superVouchMapper.saveAutoVouchLog(logMap);
				}
				
				
			}else{
				
				//老的自动凭证处理方式			
				if(mapVo.get("busi_no")==null || mapVo.get("busi_no").equals("")){
					throw new SysException("业务编号为空，保存失败！");
				}
				
					
				//业务系统自动生成凭证
				Map<String,Object> logMap=new HashMap<String,Object>();
				String[] busiNo = mapVo.get("busi_no").toString().split(",");
				
				logMap.put("group_id", group_id);
				logMap.put("hos_id", hos_id);
				logMap.put("copy_code", copy_code);
				logMap.put("acc_year", acc_year);
				logMap.put("vouch_id", vouchId);
				logMap.put("busi_log_table", mapVo.get("busi_log_table"));
				logMap.put("busi_type_code", mapVo.get("busi_type_code"));
				logMap.put("template_code", mapVo.get("template_code"));
				logMap.put("create_date", createDate);
				logMap.put("create_user", createUserId);
				
				//批量处理，一次性批量添加，内存溢出
				if(busiNo.length>1000){
					List<String> busiNoArray=new  ArrayList<String>();
					for(int i=0;i<busiNo.length;i++){
						
						busiNoArray.add(busiNo[i]);
						if(i>0 && i%1000==0){
							logMap.remove("busi_no");
							logMap.put("busi_no", busiNoArray.toArray());
							superVouchMapper.saveSuperVouchByAutoLog(logMap);
							busiNoArray=new  ArrayList<String>();
							
						}
					}
					
					if(busiNoArray!=null &&busiNoArray.size()>0){
						logMap.remove("busi_no");
						logMap.put("busi_no", busiNoArray.toArray());
						superVouchMapper.saveSuperVouchByAutoLog(logMap);
					}
				}else{
					logMap.put("busi_no", busiNo);
					superVouchMapper.saveSuperVouchByAutoLog(logMap);
				}
					
				
			}
			
		}
		
		//保存差异标注
		String diffNote=null;
		JSONArray diffjson=null;
		if(mapVo.get("diff")!=null && !"".equals(mapVo.get("diff").toString()) && !"[]".equals(mapVo.get("diff").toString()) && !"{}".equals(mapVo.get("diff").toString())){
			diffjson = JSONObject.parseArray(mapVo.get("diff").toString());
		}
		
		if(mapVo.get("is_auto_diff").equals("false") && diffjson!=null && diffjson.size()>0){
			//手工标注
			List<Map<String,Object>> diffList=new ArrayList<Map<String,Object>>();
			for(int i=0;i<diffjson.size();i++){
				JSONObject detail = JSONObject.parseObject(diffjson.getString(i));
				Map<String,Object> diffMap=new HashMap<String, Object>();
				diffMap.put("diff_id", i+1);
				diffMap.put("summary", detail.getString("summary"));
				diffMap.put("diff_item_code", detail.getString("diff_item_code"));
				diffMap.put("diff_money", detail.getString("money").replace(",", ""));
				diffList.add(diffMap);
			}
			int reDiff=superVouchMapper.saveSuperVouchByDiff(vouchMap,diffList);
			if(reDiff>0){
				//判断差异标注
				superVouchMapper.updateAccVouchDiffNote(vouchMap);
				
				validateList=superVouchMapper.queryAccVouchDiffAutoTemp(vouchMap);
				if(validateList!=null && validateList.size()>0){
					throw new SysException("差异标注失败：<br/>"+validateList.toString()+"");
				}else{
					vouchMap.put("sign_flag", 1);
					superVouchMapper.updateSuperVouchBySignFlag(vouchMap);
				}
				
			}
			
		}
		
		if(mapVo.get("is_auto_diff").equals("true") && Integer.parseInt(mapVo.get("state").toString())>0){
			//自动标注
			superVouchMapper.updateAccVouchDiffAuto(vouchMap);
			
			vouchMap.put("error_level", 2);
			validateList=superVouchMapper.queryAccVouchDiffAutoTemp(vouchMap);
			if(validateList!=null && validateList.size()>0){
				diffNote=validateList.toString();
			}
		}
		
		
		if(mapVo.get("p008").toString().equals("0")){
			reJson="{\"state\":\"true\",\"vouchId\":"+vouchId+"}";
		}else{
			if(diffNote!=null && !diffNote.equals("")){
				reJson="{\"msg\":\"凭证保存成功。<br/>自动生成差异标注：<br/>"+diffNote+"\",\"state\":\"true\",\"vouchId\":"+vouchId+"}";
			}else{
				reJson="{\"msg\":\"保存成功。\",\"state\":\"true\",\"vouchId\":"+vouchId+"}";
			}
		}
		
		
		
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		return reJson;
	}	
	
	
	//删除凭证相关联的表
	public String deleteSuperVouchByVouchId(Map<String,Object> map)throws DataAccessException{		
		
		//查附件
		List<Map<String,Object>> list=superVouchMapper.queryFileDel(map);
		
		superVouchMapper.deleteSuperVouchByVouchId(map);
		
		
		if(list.size()>0){
			superVouchMapper.deleteBatchFile(list);
			for(Map<String,Object> mmp : list){
				String att_path=mmp.get("att_path").toString();
				String file_name = att_path.substring(att_path.lastIndexOf("/") + 1, att_path.length());
				String path = att_path.substring(0,att_path.lastIndexOf("/"));
				if(!FtpUtil.deleteFile(path, file_name)){
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return "error";
				}
			}
		}
		return map.get("reNote").toString();
	}

	//根据vouch_id查询凭证主表
	public SuperVouchMain querySuperVouchByMain(Map<String,Object> map)throws DataAccessException{
		return superVouchMapper.querySuperVouchByMain(map);
	}
	
	/**
	 * 此方法停用
	 * 凭证加载：根据vouch_id查询凭证明细表（修改凭证时有漏洞停用）
	 * 改用querySuperVouchByDetailCheckCash这个方法
	 */
	@Override
	public String querySuperVouchByDetail(Map<String, Object> mapVo) throws DataAccessException {
		List<Map<String,Object>> li=new ArrayList<Map<String,Object>>();
		li=superVouchMapper.querySuperVouchByDetail(mapVo);
		String json="{\"Rows\":["+vouchDetailListToJson(li)+"}";
		return json; 
	}
	
	//凭证一次性加载入口：根据vouch_id查询凭证明细表、辅助核算表、现金流量表
	public String querySuperVouchByDetailCheckCash(Map<String, Object> map) throws DataAccessException {
		
		try{
			
			String json=querySuperVouchByDetailCheckCashJson(map);
			
			String diff=querySuperVouchByDiff(map);
			return "{"+json+","+diff+"}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		
	}
	
	//根据vouch_id查询凭证明细表、辅助核算表、现金流量表
	private String querySuperVouchByDetailCheckCashJson(Map<String, Object> map) throws Exception {
		
		List<Map<String,Object>> detailList=new ArrayList<Map<String,Object>>();
		if(map.get("is_budg")!=null && map.get("is_budg").toString().equals("1")){
			//分栏式
			map.put("order_by", " d.vouch_row,s.kind_code ");
		}else{
			//分屏式
			map.put("order_by", " s.kind_code,d.vouch_row ");
		}
		detailList=superVouchMapper.querySuperVouchByDetail(map);
		
		
		//辅助核算页面显示现金流量
		int p004=Integer.parseInt(MyConfig.getSysPara("004"));
		//银行科目默认弹出辅助核算窗口
		int p032=Integer.parseInt(MyConfig.getSysPara("032"));
		boolean isShowCheck;
		 
		List<Map<String,Object>> checkList=null;
		List<Map<String,Object>> cashList=null;
		Map<String,List<Map<String,Object>>> checkMap=new HashMap<String,List<Map<String,Object>>>();
		Map<String,List<Map<String,Object>>> cashMap=new HashMap<String,List<Map<String,Object>>>();	
		
		if(detailList!=null && detailList.size()>0){
			for(Map<String,Object> detail:detailList){
				
				isShowCheck=false;
				//是否票据号核销
				int isBill=Integer.parseInt(detail.get("is_bill").toString());
				map.put("vouch_detail_id", detail.get("did"));
				
				//银行科目默认弹出辅助核算窗口
				if(detail.get("subj_nature_code").equals("03") && p032==1){
					isBill=1;
				}
				
				map.put("subj_code", detail.get("subj_code"));
				if(detail.get("is_check").toString().equals("1") || isBill==1){
					
					map=getVouchCheckSql(map);
					
					checkList=new ArrayList<Map<String,Object>>();
					if(p004==1){
						//辅助核算页面显示现金流
						checkList=superVouchMapper.querySuperVouchByCheckCash(map);//查询辅助核算、现金流量表
						if(checkList==null || checkList.size()==0){
							//没有辅助核算，sql关联不出来，单独取现金流量放在checkMap里面
							checkList=superVouchMapper.querySuperVouchByCash(map);//查询现金流量表
						}
						isShowCheck=true;
					}else{
						//辅助核算页面不显示现金流量
						checkList=superVouchMapper.querySuperVouchByCheck(map);//查询辅助核算
					}
					
					if(checkList!=null && checkList.size()>0){
						checkMap.put(detail.get("did").toString(), checkList);
					}
					
				}
				
				if(!isShowCheck && detail.get("is_cash").toString().equals("1")){
					cashList=new ArrayList<Map<String,Object>>();
					cashList=superVouchMapper.querySuperVouchByCash(map);//查询现金流量表
					if(cashList!=null && cashList.size()>0){
						cashMap.put(detail.get("did").toString(), cashList);
					}
				}
				
			}
		}
		
		return getSuperVouchJson(map,detailList,checkMap,cashMap);
		
	}
	
	
	//凭证跳转：根据凭证号、凭证日期，查询凭证明细表、辅助核算表、现金流量表
	@Override
	public String querySuperVouchByJump(Map<String, Object> mapVo)throws DataAccessException {
		
		// TODO Auto-generated method stub
		try{
			StringBuilder json=new StringBuilder();
			json.append("{");
			SuperVouchMain svm=new SuperVouchMain();
			if(mapVo.get("vouch_date")!=null && !mapVo.get("vouch_date").toString().equals("")){
				mapVo.put("acc_year",mapVo.get("vouch_date").toString().substring(0, 4));
				mapVo.put("acc_month",mapVo.get("vouch_date").toString().substring(5, 7));
			}
			
			//next_no=true直接取最新的凭证号,不查凭证表了
			if(mapVo.get("next_no")!=null && mapVo.get("next_no").equals("false")){
				//查询凭证主表
				String jump_sel1="";
				String jump_sel2="";
				String jump_fh="=";
				if(mapVo.get("jump_flag")!=null && !mapVo.get("jump_flag").equals("")){
					//上张
					if(mapVo.get("jump_flag").toString().equalsIgnoreCase("P")){
						jump_sel1="select t1.* from ( ";
						jump_sel2=" order by to_number(v.vouch_no) desc  ) t1 where rownum=1 ";
						jump_fh=" < ";
					}else if(mapVo.get("jump_flag").toString().equalsIgnoreCase("N")){
						jump_sel1="select t1.* from ( ";
						jump_sel2=" order by to_number(v.vouch_no) asc  ) t1 where rownum=1 ";
						jump_fh=" > ";
					}
				}
				
				mapVo.put("jump_sel1", jump_sel1);
				mapVo.put("jump_sel2", jump_sel2);
				mapVo.put("jump_fh", jump_fh);
				svm=superVouchMapper.querySuperVouchByMain(mapVo);
			}
		
			if(svm!=null && svm.getVouch_id()!=null && !svm.getVouch_id().equals("")){
				
				json.append("\"vouch_id\":\""+svm.getVouch_id()+"\",");
				json.append("\"vouch_no\":\""+svm.getVouch_no()+"\",");
				json.append("\"vouch_date\":\""+svm.getVouch_date()+"\",");
				json.append("\"vouch_type_code\":\""+svm.getVouch_type_code()+"\",");
				json.append("\"state\":\""+svm.getState()+"\",");
				json.append("\"att_num\":\""+svm.getVouch_att_num()+"\",");
				json.append("\"create_user\":\""+svm.getCreate_user()+"\",");
				json.append("\"create_name\":\""+svm.getCreate_name()+"\",");
				json.append("\"cash_user\":\""+svm.getCash_user()+"\",");
				if(svm.getCash_name()==null)json.append("\"cash_name\":\"\",");
				else json.append("\"cash_name\":\""+svm.getCash_name()+"\",");
				json.append("\"audit_user\":\""+svm.getAudit_user()+"\",");
				if(svm.getAudit_name()==null)json.append("\"audit_name\":\"\",");
				else json.append("\"audit_name\":\""+svm.getAudit_name()+"\",");
				json.append("\"acc_user\":\""+svm.getAcc_user()+"\",");
				if(svm.getAcc_name()==null)json.append("\"acc_name\":\"\",");
				else json.append("\"acc_name\":\""+svm.getAcc_name()+"\",");
				json.append("\"busi_type_code\":\""+(svm.getBusi_type_code()==null?"":svm.getBusi_type_code())+"\",");
				json.append("\"template_code\":\"\",");
				
				
				mapVo.put("vouch_id", svm.getVouch_id());
				/*List<Map<String,Object>> li=new ArrayList<Map<String,Object>>();
				//凭证明细表
				li=superVouchMapper.querySuperVouchByDetail(mapVo);
				json.append("\"Rows\":["+vouchDetailListToJson(li));*/
				
				json.append(querySuperVouchByDetailCheckCashJson(mapVo));
				
				json.append(","+querySuperVouchByDiff(mapVo));
				
			}else{
				
				//凭证主表
				json.append("\"vouch_id\":\"\",");
				json.append("\"vouch_date\":\""+mapVo.get("vouch_date")+"\",");
				json.append("\"vouch_type_code\":\""+mapVo.get("vouch_type_code")+"\",");
				String reMaxVouchNo=queryMaxVouchNo(mapVo);
				json.append("\"vouch_no\":\""+reMaxVouchNo+"\",");
				json.append("\"state\":\"1\",");
				json.append("\"att_num\":\"0\",");
				json.append("\"busi_type_code\":\"\",");
				json.append("\"create_user\":\""+SessionManager.getUserId()+"\",");
				json.append("\"create_name\":\""+SessionManager.getUserName()+"\",");
				json.append("\"cash_user\":\"\",");
				json.append("\"cash_name\":\"\",");
				json.append("\"audit_user\":\"\",");
				json.append("\"audit_name\":\"\",");
				json.append("\"acc_user\":\"\",");
				json.append("\"acc_name\":\"\",");
				json.append("\"template_code\":\"\",");
				
				json.append("\"Rows\":[],\"check\":[],\"cash\":[],\"diff\":[]");
				
			}
			json.append("}");
			//logger.debug(json.toString());
			return json.toString(); 
		}catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	
	/**
	 * 此方法停用
	 * 凭证分录转json，根据List<Map<String,Object>>转json
	 * **/
	private String vouchDetailListToJson(List<Map<String,Object>> li){
		StringBuilder json=new StringBuilder();
		int rowIndex=0;
		Double debitSum=0.00;
		Double creditSum=0.00;
		if(li!=null && li.size()>0){
			for(Map<String, Object> m : li){
				rowIndex++;
        		if(m!=null && m.size()>0){
        			json.append("{");
					for (Map.Entry<String, Object> entry : m.entrySet()) {
						if(entry.getKey().equalsIgnoreCase("debit")){
							//增加id
							json.append("\"id\"");  
							json.append(":"); 
							json.append("\""+rowIndex+"\"");
							json.append(",");
							
							//借方金额
							json.append("\"debit\""); 
							json.append(":");
							json.append("\""+df.format(entry.getValue())+"\"");
							json.append(",");
							
							//增加借方显示金额
							json.append("\"debit_name\"");  
							json.append(":"); 
							json.append("\""+df.format(entry.getValue()).replace(".", "").replace("-", "")+"\"");
							json.append(",");
							
							debitSum+=Double.parseDouble(entry.getValue().toString());
							
						}else if(entry.getKey().equalsIgnoreCase("credit")){
							//借方金额
							json.append("\"credit\""); 
							json.append(":");
							json.append("\""+df.format(entry.getValue())+"\"");
							json.append(",");
							
							//增加贷方显示金额
							json.append("\"credit_name\"");  
							json.append(":");
							json.append("\""+df.format(entry.getValue()).replace(".", "").replace("-", "")+"\"");
							json.append(",");
							
							creditSum+=Double.parseDouble(entry.getValue().toString());
							
						}else{
							json.append("\"").append(entry.getKey()).append("\"");  
							json.append(":");
							json.append(JsonListMapUtil.objectToJson(entry.getValue()));
							json.append(",");
						}
						
					}
					json.setCharAt(json.length() - 1, '}');
					json.append(",");
        		}
			}
			
//			if(debitSum.equals(creditSum)){
//				capital = NumberToCN.number2CNMontrayUnit(new BigDecimal(debitSum));
//			}
						
			json.setCharAt(json.length() - 1, ']'); 
			//json.append(",\"capital\":\""+capital+"\",\"debitSum\":\""+df.format(debitSum)+"\",\"creditSum\":\""+df.format(creditSum)+"\"");  
		}else{
			json.append("]");
		}
		//Rows:[{"is_check":"0","summary":"收科室材料费","is_cash":"1","subj_type_code":"01","subj_name":"100201 银行存款-齐商银行","subj_dire":"0","did":"894173979763195","id":"1","debit":"222.00","debit_name":"22200","credit":"0.00","credit_name":"000","subj_nature_code":"03","subj_id":"341"},{"is_check":"0","summary":"收科室材料费","is_cash":"0","subj_type_code":"05","subj_name":"5001010101 医疗业务成本-人员经费-工资福利支出-基本工资","subj_dire":"0","did":"193992979989006","id":"2","debit":"0.00","debit_name":"000","credit":"222.00","credit_name":"22200","subj_nature_code":"01","subj_id":"627"}],capital:"贰佰贰拾贰元整",debitSum:"222.00",creditSum:"222.00"}
		
		return json.toString();
	}
	
	
	/**********************************************辅助核算***********************************************************************/

	//凭证制单-辅助核算-核算类型加载
	@Override
	public String queryVouchCheckType(Map<String, Object> entityMap)throws DataAccessException {
		// TODO Auto-generated method stub
		
		return JSON.toJSONString(superVouchMapper.queryVouchCheckType(entityMap)).toLowerCase();
	}
	
	
	/**
	 * 凭证制单-辅助核算-下拉框字典加载（辅助核算页面所有下拉字典）
	 * 此方法勿动，查询列、条件的增加都会影响导入辅助核算的验证
	 */
	@Override
	public List<Map<String,Object>> queryVouchCheckItemDict(Map<String, Object> checkMap)throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String,Object>> dictList=new ArrayList<Map<String,Object>>();
		Map<String, Object> entityMap=new HashMap<String,Object>();
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		
		//现金流量
		if(checkMap.get("column_id").toString().equalsIgnoreCase("cash_item_id")){
			entityMap.put("copy_code", SessionManager.getCopyCode());
			entityMap.put("id","cash_item_id");
			entityMap.put("no","''");
			entityMap.put("code", "cash_item_code");
			entityMap.put("name","cash_item_name");
			entityMap.put("check_table", "acc_cash_item");
			entityMap.put("sort", "cash_item_code");
			//entityMap.put("other_where", " and cash_dire= "+checkMap.get("cash_dire"));//只查询当前方向的现金流量项目
			
			entityMap.remove("check_type_id");
			dictList=superVouchMapper.queryVouchCashItemDict(entityMap);
		}
		//结算方式
		else if(checkMap.get("column_id").toString().equalsIgnoreCase("pay_code")){
			entityMap.put("copy_code", SessionManager.getCopyCode());
			entityMap.put("id","pay_code");
			entityMap.put("no","''");
			entityMap.put("code", "pay_code");
			entityMap.put("name","pay_name");
			entityMap.put("check_table", "acc_pay_type");
			entityMap.put("sort", "pay_code");
			entityMap.put("other_where", " and SOURCE_CODE='HRP'");//只查询HRP的字典
			entityMap.remove("check_type_id");
			dictList=superVouchMapper.queryVouchCountItemDict(entityMap);
		}
		//票据类型
		else if(checkMap.get("column_id").toString().equalsIgnoreCase("paper_type_code")){
			entityMap.put("copy_code", SessionManager.getCopyCode());
			entityMap.put("id","TYPE_CODE");
			entityMap.put("no","''");
			entityMap.put("code", "TYPE_CODE");
			entityMap.put("name","TYPE_name");
			entityMap.put("check_table", "ACC_PAPER_TYPE");
			entityMap.put("sort", "TYPE_CODE");
			entityMap.remove("check_type_id");
			entityMap.put("type","is_wb is_wb,"); 
			dictList=superVouchMapper.queryVouchNoteItemDict(entityMap);
		}
		//票据号
		else if(checkMap.get("column_id").toString().equalsIgnoreCase("check_no")){
			entityMap.put("copy_code", SessionManager.getCopyCode());
			entityMap.put("id","PAPER_NUM");
			entityMap.put("no","''");
			entityMap.put("code", "PAPER_NUM");
			entityMap.put("paper_type_code", checkMap.get("paper_type_code"));
			entityMap.put("check_table", "ACC_PAPER_DETAIL");
			entityMap.put("sort", "PAPER_NUM");
			dictList=superVouchMapper.queryVouchCheckNoDict(entityMap);
			
		}//标注项目
		else if(checkMap.get("column_id").toString().equalsIgnoreCase("diff_item_code")){
			entityMap.put("copy_code", SessionManager.getCopyCode());
			/*entityMap.put("id","diff_item_code");
			entityMap.put("code", "diff_item_code");
			entityMap.put("name","diff_item_name");
			entityMap.put("check_table", "ACC_DIFF_ITEM");
			entityMap.put("sort", "DIFF_ITEM_CODE");*/
			dictList=superVouchMapper.queryVouchDiffItemDict(entityMap);
			
		}else{
			//辅助核算
			entityMap.put("id",checkMap.get("column_id"));
			entityMap.put("no",checkMap.get("column_no")==null?"''":checkMap.get("column_no"));
			entityMap.put("code",checkMap.get("column_code")==null?"''":checkMap.get("column_code"));
			entityMap.put("name",checkMap.get("column_name"));
			entityMap.put("check_table", checkMap.get("check_table"));
			entityMap.put("sort","a.sort_code,a."+checkMap.get("column_code"));
			
			//核算分类查询条件and ${check_type_code_filed} in (${check_type_code}) 
			if(checkMap.get("check_type_code")!=null && !checkMap.get("check_type_code").toString().equals("")){
				entityMap.put("check_type_code",checkMap.get("check_type_code").toString());
			}
			
			//系统核算
			if(checkMap.get("column_check").toString().toLowerCase().startsWith("check")){
				entityMap.remove("copy_code");
				entityMap.remove("check_type_id");
				
				//部门辅助核算
				if(checkMap.get("column_check").toString().equalsIgnoreCase("check1")){
					
					//不是导入
					if(!checkMap.containsKey("is_imp")){
						
						if(checkMap.get("out_code") != null && !checkMap.get("out_code").toString().equals("")){
							if(checkMap.get("check_type_code") == null || checkMap.get("check_type_code").toString().equals("")){
								entityMap.put("check_type_code","and a.dept_id in (select attr.dept_id from ACC_DEPT_ATTR attr where attr.group_id = a.group_id and attr.hos_id = a.hos_id and attr.out_code = "+checkMap.get("out_code").toString()+")");
							}
						}
					}
					
					if(checkMap.containsKey("out_code")){
						
						entityMap.put("out_code",checkMap.get("out_code").toString());
						
					}
					
					entityMap.put("is_last",1);
				}
				
				//职工辅助核算(职工-部门关联)
				if(checkMap.get("column_check").toString().equalsIgnoreCase("check2")){
					entityMap.put("is_show",checkMap.get("is_show"));
				}
				
				//项目辅助核算(项目-负责人关联)
				if(checkMap.get("column_check").toString().equalsIgnoreCase("check3")){
					entityMap.put("is_show",checkMap.get("is_show"));
				}
				
				//库房辅助核算
				if(checkMap.get("column_check").toString().equalsIgnoreCase("check4")){
					
				}
				
				//客户辅助核算
				if(checkMap.get("column_check").toString().equalsIgnoreCase("check5")){
					
				}
				
				//供应商辅助核算
				if(checkMap.get("column_check").toString().equalsIgnoreCase("check6")){
					
				}
				
				//资源来源辅助核算
				if(checkMap.get("column_check").toString().equalsIgnoreCase("check7")){
					entityMap.put("sort", "a."+checkMap.get("column_code"));//排序字段
					entityMap.remove("no");//没有变更号
				}
				//单位辅助核算-排序字段
				if(checkMap.get("column_check").toString().equalsIgnoreCase("check8")){
					entityMap.put("sort", "a.hos_sort,a."+checkMap.get("column_code"));
					entityMap.remove("check_type_code");
				}
			
				dictList = superVouchMapper.queryVouchCheckSysItemDict(entityMap);
				
				
			}else if(checkMap.get("column_check").toString().toLowerCase().startsWith("zcheck")){
				//自定义核算
				entityMap.remove("no");//没有变更号
				entityMap.put("copy_code", SessionManager.getCopyCode());
				if(checkMap.get("column_check_import")!=null && !checkMap.get("column_check_import").equals("")){
					//辅助核算导入传核算字段，不传check_type_id
					entityMap.put("column_check_import", checkMap.get("column_check_import"));
				}else{
					entityMap.put("check_type_id", checkMap.get("check_id"));
				}

				dictList = superVouchMapper.queryVouchCheckItemDict(entityMap);
				
			}
		}
		//logger.debug(reJson);
		return dictList;
		
	}

	//凭证制单-辅助核算导入
	@Override
	public String importCheck(Map<String, Object> map)throws DataAccessException {
		// TODO Auto-generated method stub
		try{
			String columns=map.get("columns").toString();
			JSONArray jsonColumns = JSONObject.parseArray(columns);	
			if(jsonColumns==null || jsonColumns.size()==0){
				return "{\"error\":\"表头为空！\",\"state\":\"false\"}";
			}
			
			String content=map.get("content").toString();
			List<Map<String,List<String>>> liData=SpreadTableJSUtil.toListMap(content,1);
			if(liData==null || liData.size()==0){
				return "{\"error\":\"没有数据！\",\"state\":\"false\"}";
			}
			
			//查询字典,根据名称反查id、no、code
			Map<String,List<Map<String,Object>>> mapDict=new HashMap<String,List<Map<String,Object>>>();
			List<Map<String,Object>> dictList=null;
			for(int j=0;j<jsonColumns.size();j++){
				
				JSONObject jsonObj = JSONObject.parseObject(jsonColumns.getString(j));
				if(jsonObj.getString("name").equalsIgnoreCase("paper_type_code")){
					//票据类型
					map.put("column_id","paper_type_code");
					dictList=queryVouchCheckItemDict(map);
					if(dictList!=null && dictList.size()>0){
						mapDict.put(jsonObj.getString("name"), dictList);
					}
				}else if(jsonObj.getString("hide")!=null && jsonObj.getString("hide").equals("true")){
					map.put("column_check",jsonObj.getString("name"));
					if(jsonObj.getString("name").equalsIgnoreCase("cash_item_id")){
						//现金流量
						map.put("column_id","cash_item_id");
						dictList=queryVouchCheckItemDict(map);
						if(dictList!=null && dictList.size()>0){
							mapDict.put(jsonObj.getString("name"), dictList);
						}
					}else if(jsonObj.getString("name").equalsIgnoreCase("pay_type_code")){
						//结算方式
						map.put("column_id","pay_code");
						dictList=queryVouchCheckItemDict(map);
						if(dictList!=null && dictList.size()>0){
							mapDict.put(jsonObj.getString("name"), dictList);
						}
					}else if(jsonObj.getString("name").equalsIgnoreCase("check1")){
						//部门
						map.put("column_id","dept_id");
						map.put("column_no","dept_no");
						map.put("column_code", "dept_code");
						map.put("column_name","dept_name");
						map.put("check_table", "hos_dept_dict");
						dictList=queryVouchCheckItemDict(map);
						if(dictList!=null && dictList.size()>0){
							mapDict.put(jsonObj.getString("name"), dictList);
						}
					}else if(jsonObj.getString("name").equalsIgnoreCase("check2")){
						//职工
						map.put("column_id","emp_id");
						map.put("column_no","emp_no");
						map.put("column_code", "emp_code");
						map.put("column_name","emp_name");
						map.put("check_table", "hos_emp_dict");
						dictList=queryVouchCheckItemDict(map);
						if(dictList!=null && dictList.size()>0){
							mapDict.put(jsonObj.getString("name"), dictList);
						}
					}else if(jsonObj.getString("name").equalsIgnoreCase("check3")){
						//项目
						map.put("column_id","proj_id");
						map.put("column_no","proj_no");
						map.put("column_code", "proj_code");
						map.put("column_name","proj_name");
						map.put("check_table", "hos_proj_dict");
						
						dictList=queryVouchCheckItemDict(map);
						if(dictList!=null && dictList.size()>0){
							mapDict.put(jsonObj.getString("name"), dictList);
						}
					}else if(jsonObj.getString("name").equalsIgnoreCase("check4")){
						//库房
						map.put("column_id","store_id");
						map.put("column_no","store_no");
						map.put("column_code", "store_code");
						map.put("column_name","store_name");
						map.put("check_table", "hos_store_dict");
						dictList=queryVouchCheckItemDict(map);
						if(dictList!=null && dictList.size()>0){
							mapDict.put(jsonObj.getString("name"), dictList);
						}
					}else if(jsonObj.getString("name").equalsIgnoreCase("check5")){
						//客户
						map.put("column_id","cus_id");
						map.put("column_no","cus_no");
						map.put("column_code", "cus_code");
						map.put("column_name","cus_name");
						map.put("check_table", "hos_cus_dict");
						dictList=queryVouchCheckItemDict(map);
						if(dictList!=null && dictList.size()>0){
							mapDict.put(jsonObj.getString("name"), dictList);
						}
					}else if(jsonObj.getString("name").equalsIgnoreCase("check6")){
						//供应商
						map.put("column_id","sup_id");
						map.put("column_no","sup_no");
						map.put("column_code", "sup_code");
						map.put("column_name","sup_name");
						map.put("check_table", "hos_sup_dict");
						dictList=queryVouchCheckItemDict(map);
						if(dictList!=null && dictList.size()>0){
							mapDict.put(jsonObj.getString("name"), dictList);
						}
					}else if(jsonObj.getString("name").equalsIgnoreCase("check7")){
						//资金来源
						map.put("column_id","source_id");
						map.put("column_no","''");
						map.put("column_code", "source_code");
						map.put("column_name","source_name");
						map.put("check_table", "hos_source");
						dictList=queryVouchCheckItemDict(map);
						if(dictList!=null && dictList.size()>0){
							mapDict.put(jsonObj.getString("name"), dictList);
						}
					}else if(jsonObj.getString("name").equalsIgnoreCase("check8")){
						//单位
						map.put("column_id","hos_id");
						map.put("column_no","''");
						map.put("column_code", "hos_code");
						map.put("column_name","hos_name");
						map.put("check_table", "v_hos_dict");
						dictList=queryVouchCheckItemDict(map);
						if(dictList!=null && dictList.size()>0){
							mapDict.put(jsonObj.getString("name"), dictList);
						}
					}else if(jsonObj.getString("name").startsWith("zcheck")){
						//自定义辅助核算
						map.put("check_id", "acc_check_item");
						map.put("column_id","check_item_id");
						map.put("column_no","''");
						map.put("column_code", "check_item_code");
						map.put("column_name","check_item_name");
						map.put("check_table", "acc_check_item");
						map.put("column_check_import", jsonObj.getString("name").toUpperCase());
						dictList=queryVouchCheckItemDict(map);
						if(dictList!=null && dictList.size()>0){
							mapDict.put(jsonObj.getString("name"), dictList);
						}
					}
				
				}
			}
			
			int rowIndex=Integer.parseInt(map.get("rowIndex").toString());
			List<String> rowList=null;
			
			
			StringBuffer jsonStr=new StringBuffer();
			jsonStr.append("{\"Rows\":[");
							
			
			for(Map<String,List<String>> mapData:liData){
				
				rowIndex++;
				jsonStr.append("{");				
				for(int j=0;j<jsonColumns.size();j++){
					
					JSONObject jsonObj = JSONObject.parseObject(jsonColumns.getString(j));					
										
					if(jsonObj.getString("display").equalsIgnoreCase("id")){
						jsonStr.append("\""+jsonObj.getString("name")+"\":\""+rowIndex+"\",");
						
					}else if(jsonObj.getString("display").equalsIgnoreCase("cid")){
						jsonStr.append("\""+jsonObj.getString("name")+"\":\"\",");
						
					}else if(jsonObj.getString("hide")!=null && jsonObj.getString("hide").equals("true")){
						
					}else {
						
						if((jsonObj.getString("name").equalsIgnoreCase("summary") || jsonObj.getString("name").indexOf("check")!=-1) && !jsonObj.getString("name").equalsIgnoreCase("check_no")){
							if(mapData.get(jsonObj.getString("display"))==null){
								return "{\"error\":\"请用系统模板导入！\",\"state\":\"false\"}";
							}
						}
						
						
						rowList=mapData.get(jsonObj.getString("display"));
						
						if(jsonObj.getString("name").equalsIgnoreCase("summary")){
							if(rowList==null || rowList.get(1)==null || rowList.get(1).equals("")){
								return "{\"msg\":\""+rowList.get(0)+"，摘要为空！\",\"state\":\"false\",\"row_cell\":\""+rowList.get(0)+"\"}";
							}
							jsonStr.append("\""+jsonObj.getString("name")+"\":\""+rowList.get(1)+"\",");
							
						}else if(jsonObj.getString("name").equalsIgnoreCase("money")){
							if(rowList==null || rowList.get(1)==null || !NumberUtil.isNumeric(rowList.get(1))){
								return "{\"msg\":\""+rowList.get(0)+"，金额非法！\",\"state\":\"false\",\"row_cell\":\""+rowList.get(0)+"\"}";
							}
							jsonStr.append("\""+jsonObj.getString("name")+"\":\""+rowList.get(1)+"\",");
							
						}else if(jsonObj.getString("name").equalsIgnoreCase("occur_date") || jsonObj.getString("name").equalsIgnoreCase("due_date")){
							if(rowList!=null && rowList.get(1)!=null && !rowList.get(1).equals("")){
								try{
									
									if(rowList.get(1).indexOf("OADate")!=-1){
										Date vouchDate = new Date();
										BigDecimal bigDecimalA = new BigDecimal(rowList.get(1).replace("OADate","").replace("/","").replace("\\","").replace("(","").replace(")",""));
										bigDecimalA=bigDecimalA.multiply(new BigDecimal(24)).multiply(new BigDecimal(60)).multiply(new BigDecimal(60)).multiply(new BigDecimal(1000));
										bigDecimalA=bigDecimalA.add(new BigDecimal(formatter.parse("1899-12-30").getTime()));
										vouchDate.setTime(bigDecimalA.longValue());
										jsonStr.append("\""+jsonObj.getString("name")+"\":\""+DateUtil.dateToString(vouchDate,"yyyy-MM-dd")+"\",");
									}else{
										Date vouchDate=formatter.parse(rowList.get(1));
										jsonStr.append("\""+jsonObj.getString("name")+"\":\""+DateUtil.dateToString(vouchDate,"yyyy-MM-dd")+"\",");
									}
									
									//DateUtil.stringToDate(rowList.get(1),"yyyy-MM-dd");
								 }catch(ParseException e){
									 return "{\"msg\":\""+rowList.get(0)+"，日期非法【yyyy-MM-dd】！\",\"state\":\"false\",\"row_cell\":\""+rowList.get(0)+"\"}";
								 }
								 
							}
						}else if(jsonObj.getString("name").equalsIgnoreCase("check_no") || jsonObj.getString("name").equalsIgnoreCase("con_no") || jsonObj.getString("name").equalsIgnoreCase("business_no")){
							//票据号、合同号、单据号
							if(rowList!=null && rowList.get(1)!=null && !rowList.get(1).equals("")){
								jsonStr.append("\""+jsonObj.getString("name").toLowerCase()+"\":\""+rowList.get(1)+"\",");
							}
						}else{
							
							if(jsonObj.getString("name").equalsIgnoreCase("pay_name")){
								//结算方式
								if(rowList!=null && rowList.get(1)!=null && !rowList.get(1).equals("")){
									String dictId=getCheckDictIdByName(mapDict.get("pay_type_code"),rowList.get(1));
									if(dictId==null || dictId.equals("")){
										return "{\"msg\":\""+rowList.get(0)+"，【"+rowList.get(1)+"】不存在！\",\"state\":\"false\",\"row_cell\":\""+rowList.get(0)+"\"}";
									}
									jsonStr.append("\"pay_type_code\":\""+dictId.split("〓")[0]+"\",");
									jsonStr.append("\"pay_name\":\""+dictId.split("〓")[1]+"\",");
								}
								
							}else if(jsonObj.getString("name").equalsIgnoreCase("cash_name")){
								//现金流量
								if(rowList!=null && rowList.get(1)!=null && !rowList.get(1).equals("")){
									//return "{\"msg\":\""+rowList.get(0)+"，单元格为空！\",\"state\":\"false\",\"row_cell\":\""+rowList.get(0)+"\"}";
									String dictId=getCheckDictIdByName(mapDict.get("cash_item_id"),rowList.get(1));
									if(dictId==null || dictId.equals("")){
										return "{\"msg\":\""+rowList.get(0)+"，【"+rowList.get(1)+"】不存在！\",\"state\":\"false\",\"row_cell\":\""+rowList.get(0)+"\"}";
									}
									jsonStr.append("\"cash_item_id\":\""+dictId.split("〓")[0]+"\",");
									jsonStr.append("\"cash_name\":\""+dictId.split("〓")[1]+"\",");
								}
								
							}else if(jsonObj.getString("name").equalsIgnoreCase("paper_type_code")){
								//票据类型
								if(rowList!=null && rowList.get(1)!=null && !rowList.get(1).equals("")){
									String dictId=getCheckDictIdByName(mapDict.get("paper_type_code"),rowList.get(1));
									if(dictId==null || dictId.equals("")){
										return "{\"msg\":\""+rowList.get(0)+"，【"+rowList.get(1)+"】不存在！\",\"state\":\"false\",\"row_cell\":\""+rowList.get(0)+"\"}";
									}
									jsonStr.append("\"paper_type_code\":\""+dictId.split("〓")[1]+"\",");
								}
								
							}else{
								//辅助核算
								if(rowList==null || rowList.get(1)==null || rowList.get(1).equals("")){
									return "{\"msg\":\""+rowList.get(0)+"，单元格为空！\",\"state\":\"false\",\"row_cell\":\""+rowList.get(0)+"\"}";
								}
								
								String dictId=getCheckDictIdByName(mapDict.get(jsonObj.getString("name").replace("_name","")),rowList.get(1));
								
								if(dictId==null || dictId.equals("")){
									return "{\"msg\":\""+rowList.get(0)+"，【"+rowList.get(1)+"】不存在！\",\"state\":\"false\",\"row_cell\":\""+rowList.get(0)+"\"}";
								}
								
								if(map.containsKey("out_code") && map.get("column_code").equals("dept_code")){
									
									if(!map.get("out_code").equals(dictId.split("〓")[2])){
										
										if(map.get("out_code").equals("01")){
											
											return "{\"msg\":\""+rowList.get(0)+"，【"+rowList.get(1)+"】不是医疗科室,无法导入！\",\"state\":\"false\",\"row_cell\":\""+rowList.get(0)+"\"}";
										
										}else if(map.get("out_code").equals("02")){
											
											return "{\"msg\":\""+rowList.get(0)+"，【"+rowList.get(1)+"】不是管理科室,无法导入！\",\"state\":\"false\",\"row_cell\":\""+rowList.get(0)+"\"}";
											
										}
										
									}
								}
								
								jsonStr.append("\""+jsonObj.getString("name").replace("_name","")+"\":\""+dictId.split("〓")[0]+"\",");
								jsonStr.append("\""+jsonObj.getString("name")+"\":\""+dictId.split("〓")[1]+"\",");
								
							}
							
						}
						
					}
					
				}
				jsonStr.setCharAt(jsonStr.length() - 1, '}');
				jsonStr.append(",");
			}
			
			jsonStr.setCharAt(jsonStr.length() - 1, ']'); 
			jsonStr.append(",\"state\":\"true\",\"rowIndex\":"+rowIndex+"}");  
			return jsonStr.toString();
		}catch(Exception e){
			e.printStackTrace();
			logger.debug(e.getMessage());
			throw new SysException(e.getMessage());
		}
		
	}
	
	//根据名称找ID、NO、CODE
	private String getCheckDictIdByName(List<Map<String,Object>> listDict,String name){
		if(listDict==null || listDict.size()==0){
			return null;
		}
		
		for(Map<String,Object> map :listDict){
			String dName=map.get("name").toString();
			if(dName.split(" ")[0].equals(name) || dName.split(" ")[1].equals(name)){
				if(map.get("no")!=null && !map.get("no").equals("")){
					String res=map.get("id").toString()+"@"+map.get("no").toString()+"〓"+map.get("name")+"〓"+map.get("out_code");
					return res;
				}else{
					String res=map.get("id").toString()+"〓"+map.get("name")+"〓"+map.get("out_code");
					return res;
				}
				
			}
		}
		return null;
	}
	
	//根据系统参数010取凭证类型
	@Override
	public String querySuperVouchTypeByPerm(Map<String, Object> mapVo)throws DataAccessException {
		// TODO Auto-generated method stub
		return JSON.toJSONString(superVouchMapper.querySuperVouchTypeByPerm(mapVo));
	}


	//凭证制单-取凭证审核流程
	@Override
	public String queryVouchFlow(Map<String, Object> map)throws DataAccessException {
		// TODO Auto-generated method stub
		return JSON.toJSONString(superVouchMapper.queryVouchFlow(map));
	}


	//根据凭证流程操作签字、审核、记账
	@Override
	public String updateVouchStateByFlow(Map<String, Object> map)throws DataAccessException {
		// TODO Auto-generated method stub
		String nexState=map.get("nex_state").toString();//要审批的状态
		String curState=map.get("cur_state").toString();//当前凭证状态
		String flag=map.get("flag").toString();
		map.put("user_id", SessionManager.getUserId());
		//出纳签字
		Date createDate=DateUtil.getCurrenDate();
		
		//审批操作
		if(flag.equals("next")){
			//出纳签字
			if(nexState.equals("2")){
				map.put("cash_user", SessionManager.getUserId());
				map.put("cashe_date", createDate);
				map.put("perm_code", "updateVouchStateByFlow2");
			//凭证审核	
			}else if(nexState.equals("3")){
				map.put("audit_user", SessionManager.getUserId());
				map.put("audit_date", createDate);
				map.put("perm_code", "updateVouchStateByFlow3");
			//凭证记账	
			}else if(nexState.equals("99")){
				map.put("acc_user", SessionManager.getUserId());
				map.put("acc_date", createDate);
				map.put("perm_code", "updateVouchStateByFlow99");
			}
		//取消操作	
		}else if(flag.equals("pre")){
			
			//不允许修改他人凭证，判断审核人、出纳人、记账人是否是当前用户
			if(MyConfig.getSysPara("005").equals("0")){
				if(superVouchMapper.queryAccVouchExistsUser(map)==0){
					return "{\"error\":\"只能操作自己审核的凭证！\",\"state\":\"false\"}";
				}
			}
			
			
			//取消签字
			if(curState.equals("2")){
				map.put("perm_code", "updateVouchStateByFlow2");
			}
			//取消审核
			else if(curState.equals("3")){
				map.put("perm_code", "updateVouchStateByFlow3");
			}
			//取消记账
			else if(curState.equals("99")){
				map.put("perm_code", "updateVouchStateByFlow99");
			}
		}
		
		//凭证审批流判断操作权限
		map.put("mod_code", "01");
		if(queryVouchFlowByPerm(map)==0){
			return "{\"error\":\"没有该操作权限！\",\"state\":\"false\"}";
		}
		
		int res=superVouchMapper.updateVouchStateByFlow(map);
		if(res>0){
				/*//凭证记账
				if(flag.equals("next") && nexState.equals("99")){
					//先不考虑单张凭证记账
					accVouchMapper.updateBatchAccountingAccVouch(map);
				}
				//取消记账
				else if(flag.equals("pre") && curState.equals("99")){
					//先不考虑单张凭证取消记账
					
					AccVouch accVouch =accVouchMapper.queryAccVouchDetailByVouchid(map);
					if(accVouch != null){
						map.put("subj_code", accVouch.getSubj_no());
						accLederMapper.deleteAccLeder(map);
						accVouchMapper.updateAccVouchUser(map);
					}
					
				}*/
			return "{\"msg\":\"操作成功。\",\"state\":\"true\",\"vouchState\":\""+nexState+"\",\"user_id\":\""+SessionManager.getUserId()+"\",\"user_name\":\""+SessionManager.getUserName()+"\"}";
		}else{
			return "{\"error\":\"操作失败。\",\"state\":\"false\"}";
		}
	}

	//凭证审批流判断操作权限
	@Override
	public int queryVouchFlowByPerm(Map<String, Object> map)throws DataAccessException {
		// TODO Auto-generated method stub
		return superVouchMapper.queryVouchFlowByPerm(map);
	}
	
	
	//根据参数编码取系统参数的通用方法
	@Override
	public List<Map<String,Object>> queryVouchParaListByParaCode(Map<String, Object> entityMap)throws DataAccessException {
		// TODO Auto-generated method stub
		return superVouchMapper.queryVouchParaList(entityMap);
	}

	//复制凭证	
	@Override
	public synchronized String copySuperVouch(Map<String, Object> map)throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> detailiList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> checkList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> cashList = new ArrayList<Map<String, Object>>();
		if(map.get("vouch_date")==null || map.get("vouch_date").toString().equals("")){
			return "{\"error\":\"凭证日期为空.\"}";
		}
		
		String vouch_date= map.get("vouch_date").toString();
		try {
			//凭证复制方式0蓝字，1红字、2正常复制
			if(map.get("copy_type").equals("0") || map.get("copy_type").equals("1")){
				Map<String, Object> stateMap=new HashMap<String, Object>();
				stateMap=superVouchMapper.queryCopyAccVouchByVouchId(map);
				if(stateMap.get("STATE").equals("-1") || stateMap.get("STATE").equals("0")){
					return "{\"error\":\"作废、草稿凭证不能冲销.\"}";
				}
				map.put("vouch_date", stateMap.get("VOUCH_DATE"));
				map.put("vouch_type_code", stateMap.get("VOUCH_TYPE_CODE"));
			}
			
			String acc_year= map.get("vouch_date").toString().substring(0, 4);
			String acc_month= map.get("vouch_date").toString().substring(5, 7);
			if(Integer.parseInt(acc_year+acc_month)<Integer.parseInt(MyConfig.getAccYearMonth().getCurYearMonthAcc())){
				//return "{\"error\":\"会计期间：【" + acc_year+acc_month + "】已结账.\"}";
				vouch_date=queryMaxVouchDate(map);
				acc_year= vouch_date.substring(0, 4);
				acc_month= vouch_date.substring(5, 7);
			}
			
			//取凭证日期
			String group_id= SessionManager.getGroupId();
			String hos_id= SessionManager.getHosId();
			String copy_code= SessionManager.getCopyCode();
			String createUserId=SessionManager.getUserId();
			//取凭证号
			map.put("acc_year", acc_year);
			map.put("acc_month", acc_month);
			map.put("vouch_date", vouch_date);
			String maxVouchNo=queryMaxVouchNo(map);
			if(maxVouchNo.indexOf("error")!=-1){
				return maxVouchNo;
			}
			
			String vouch_id_new=UUIDLong.absStringUUID();
			Date vouchDateNew=DateUtil.stringToDate(vouch_date,"yyyy-MM-dd");
			map.put("vouch_id_new", vouch_id_new);
			map.put("vouch_date", vouchDateNew);
			map.put("create_user",createUserId);
			Date createDate=DateUtil.getCurrenDate();
			map.put("create_date",createDate);
			map.put("vouch_no", maxVouchNo);
			if(!"".equals(vouch_date)){
				map.put("old_acc_year", vouch_date.substring(0, 4));
			} 
			//凭证复制方式0蓝字，1红字、2正常复制
			if(map.get("copy_type").equals("0") || map.get("copy_type").equals("1")){
				map.put("vouch_id_cx", map.get("vouch_id"));
				map.put("vouch_cx_state", map.get("copy_type"));
			}else{
				map.put("vouch_id_cx", "");
				map.put("vouch_cx_state", "");
			}
			
			
			//复制凭证主表
			int reState=superVouchMapper.copySuperVouchMain(map);
			if(reState<=0){
				return "{\"error\":\"复制失败.\"}";
			}
			
			
			
			//查询辅助核算列名
			StringBuffer sbf = new StringBuffer();
			Map<String, Object> tempMap = new HashMap<String, Object>();
			tempMap.put("group_id", group_id);
			tempMap.put("hos_id", hos_id);
			tempMap.put("copy_code", copy_code);
			List<Map<String, Object>> checkTypeList =superVouchMapper.queryAccCheckTypeColumnCheck(tempMap);
			for (Map<String, Object> accCheckType : checkTypeList) {
				
				if(accCheckType.get("is_sys").toString().equals("1")){
					//系统核算
					sbf.append(","+accCheckType.get("column_check")+"_ID");
					if(accCheckType.get("is_change").toString().equals("1")){
						//有变更
						sbf.append(","+accCheckType.get("column_check")+"_NO");
					}
				}else{
					//非系统核算
					sbf.append(","+accCheckType.get("column_check"));
				}
				
			}
			
			
			//到期日期
			Date due_date=null;
			if(MyConfig.getSysPara("029").equals("0")){
				//到期日期，默认月底
				Calendar calendar = new GregorianCalendar();  
				calendar.setTime(vouchDateNew);
				calendar.add(Calendar.MONTH, 1);  
		        calendar.set(Calendar.DAY_OF_MONTH, 0);
		        due_date=calendar.getTime();
		    	
			}else{
				//到期日期，默认年底
				due_date=DateUtil.stringToDate(acc_year+"-12-31","yyyy-MM-dd");
			}
			
			//查询要复制的凭证明细ID
			List<String> vouchIDList= superVouchMapper.queryCopyAccVouchDetailByVouchId(map);
			//查询要复制的辅助核算
			List<Map<String, Object>> checkIdList = superVouchMapper.queryCopyAccVouchCheckByVouchId(map);
			//查询要复制的现金流量
			List<Map<String, Object>> cashIdList = superVouchMapper.queryCopyAccVouchCashByVouchId(map);
			
			for (String did : vouchIDList) {
				
				Map<String, Object> detailMap = new HashMap<String, Object>();
				detailMap.put("group_id", group_id);
				detailMap.put("hos_id", hos_id);
				detailMap.put("copy_code", copy_code);
				detailMap.put("vouch_id", map.get("vouch_id"));
				detailMap.put("vouch_id_new", vouch_id_new);	
				detailMap.put("vouch_detail_id", did);
				String vouch_detail_id_new=UUIDLong.absStringUUID();
				detailMap.put("vouch_detail_id_new",vouch_detail_id_new);
				detailMap.put("acc_year",acc_year);
				detailMap.put("old_acc_year",vouch_date.substring(0, 4));
				detailMap.put("copy_type",map.get("copy_type"));//凭证复制方式0蓝字，1红字、2正常复制
				detailiList.add(detailMap);
				
				//查找对应明细的辅助核算
				if(checkIdList!=null && checkIdList.size()>0){
					for (Map<String, Object> cMap : checkIdList) {
						if(!cMap.get("VOUCH_DETAIL_ID").toString().equals(did)){
							continue;
						}
							
						Map<String, Object> checkMap = new HashMap<String, Object>();
						checkMap.put("group_id", group_id);
						checkMap.put("hos_id", hos_id);
						checkMap.put("copy_code", copy_code);
						checkMap.put("vouch_id", map.get("vouch_id"));
						checkMap.put("vouch_id_new", vouch_id_new);	
						checkMap.put("vouch_detail_id", did);
						checkMap.put("vouch_detail_id_new",detailMap.get("vouch_detail_id_new"));
						checkMap.put("acc_year",acc_year);
						checkMap.put("old_acc_year",vouch_date.substring(0, 4));
						checkMap.put("copy_type",map.get("copy_type"));
						checkMap.put("vouch_check_id",cMap.get("VOUCH_CHECK_ID"));
						checkMap.put("vouch_check_id_new", UUIDLong.absStringUUID());
						checkMap.put("occur_date",vouchDateNew);//发生日期
						checkMap.put("column_name", sbf==null?"":sbf.toString());
						if(cMap.get("SUBJ_NATURE_CODE").toString().equals("04") || cMap.get("SUBJ_NATURE_CODE").toString().equals("05")){
							checkMap.put("due_date",due_date);//到期日期
						}else{
							checkMap.put("due_date","");//到期日期
						}
						checkList.add(checkMap);
					}
				}
				
				
				//查找对应明细的现金流量标注
				if(cashIdList!=null && cashIdList.size()>0){
					for (Map<String, Object> cMap : cashIdList) {
						if(!cMap.get("VOUCH_DETAIL_ID").toString().equals(did)){
							continue;
						}
						
						Map<String, Object> cashMap = new HashMap<String, Object>();
						cashMap.put("group_id", group_id);
						cashMap.put("hos_id", hos_id);
						cashMap.put("copy_code", copy_code);
						cashMap.put("vouch_id", map.get("vouch_id"));
						cashMap.put("vouch_id_new", vouch_id_new);	
						cashMap.put("vouch_detail_id", did);
						cashMap.put("vouch_detail_id_new",detailMap.get("vouch_detail_id_new"));
						cashMap.put("acc_year",acc_year);
						cashMap.put("copy_type",map.get("copy_type"));
						cashMap.put("cash_id", cMap.get("CASH_ID"));
						cashMap.put("cash_id_new", UUIDLong.absStringUUID());
						cashMap.put("create_date",createDate);
						cashMap.put("create_user",createUserId);
						cashList.add(cashMap);
						
					}
				}
				
			}
		
			if(detailiList.size()>0){
				//复制凭证明细
				superVouchMapper.copySuperVouchDetail(detailiList);
			}
			
			if(checkList.size()>0){
				//复制辅助核算
				superVouchMapper.copySuperVouchCheck(checkList);
			}
			
			if(cashList.size()>0){
				//复制现金流量
				superVouchMapper.copySuperVouchCash(cashList);
			}
		
			//复制差异标注
			superVouchMapper.copySuperVouchDiff(map);
			
			if(map.get("copy_type").equals("2")){
				return "{\"msg\":\"复制成功。\",\"state\":\"true\",\"vouchId\":"+vouch_id_new+"}";
			}else{
				return "{\"state\":\"true\",\"vouchId\":"+vouch_id_new+"}";
			}
			
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("复制失败："+e.getMessage());

		}
	}

	/**
	 * 凭证差异标注加载：根据vouch_id查询差异标注表
	 */
	@Override
	public String querySuperVouchByDiff(Map<String, Object> map)throws DataAccessException {
		// TODO Auto-generated method stub
		
		//查询现金流量
		List<Map<String,Object>> liDiff=new ArrayList<Map<String,Object>>();
		//根据vouch_detail_id查询现金流量表
		Map<String,Object> cMap=new HashMap<String, Object>();
		cMap.put("group_id", SessionManager.getGroupId());
		cMap.put("hos_id", SessionManager.getHosId());
		cMap.put("copy_code", SessionManager.getCopyCode());
		cMap.put("vouch_id", map.get("vouch_id"));
		liDiff=superVouchMapper.querySuperVouchByDiff(cMap);
		
		//组装json
		StringBuilder json=new StringBuilder();
		json.append("\"diff\":[");
		if(liDiff!=null && liDiff.size()>0){
			int i=0;
			for(Map<String, Object> diffMap:liDiff){
				
				i++;
    			json.append("{");
				json.append("\"id\"");  
				json.append(":"); 
				json.append("\""+i+"\"");
				json.append(",");
    							
    			json.append("\"summary\"");  
				json.append(":");
				json.append("\""+diffMap.get("summary")+"\"");
				json.append(",");
				
				json.append("\"diff_item_name\"");  
				json.append(":");
				json.append("\""+diffMap.get("diff_item_name")+"\"");
				json.append(",");
					
				json.append("\"diff_item_code\"");  
				json.append(":");
				json.append("\""+diffMap.get("diff_item_code")+"\"");
				json.append(",");
				
				json.append("\"money\""); 
				json.append(":");
				json.append("\""+df.format(diffMap.get("money"))+"\"");
				json.append(",");
				
				json.append("\"is_auto\""); 
				json.append(":");
				json.append("\""+diffMap.get("is_auto")+"\"");
				json.append("},");
					
			}
			
			json.setCharAt(json.length() - 1, ']'); 
		}else{
			json.append("]");
		}
		
		//logger.debug(json.toString());
		return json.toString();
	}

	
	/**
	 * 此方法停用
	 * 凭证现金流量加载：根据vouch_detail_id查询现金流量标注表
	 */
	@Override
	public String querySuperVouchByCash(Map<String, Object> map)throws DataAccessException {
		// TODO Auto-generated method stub
		
		//查询现金流量
		List<Map<String,Object>> liCash=new ArrayList<Map<String,Object>>();
		//根据vouch_detail_id查询现金流量表
		Map<String,Object> cMap=new HashMap<String, Object>();
		cMap.put("group_id", SessionManager.getGroupId());
		cMap.put("hos_id", SessionManager.getHosId());
		cMap.put("copy_code", SessionManager.getCopyCode());
		cMap.put("vouch_id", map.get("vouch_id"));
		cMap.put("vouch_detail_id", map.get("vouch_detail_id"));
		liCash=superVouchMapper.querySuperVouchByCash(cMap);
		
		//组装json
		//Double moneySum=0.00;
		//int dire=0;//方向
		int rowIndex=0;
		StringBuilder json=new StringBuilder();
		json.append("{\"Rows\":[");
		Map<String, Object> cashMap=null;
		//没有辅助核算，只有现金流量
		if(liCash!=null && liCash.size()>0){
			for(int i=0;i<liCash.size();i++){
				cashMap=new HashMap<String, Object>();
				cashMap =liCash.get(i);
				
				rowIndex++;
        		if(cashMap!=null && cashMap.size()>0){
        			json.append("{");
        			
        			//增加id
					json.append("\"id\"");  
					json.append(":"); 
					json.append("\""+rowIndex+"\"");
					json.append(",");
        			
        			json.append("\"summary\"");  
					json.append(":");
					json.append(JsonListMapUtil.objectToJson(cashMap.get("SUMMARY")));
					json.append(",");
					
					json.append("\"cash_name\"");  
					json.append(":");
					json.append(JsonListMapUtil.objectToJson(cashMap.get("CASH_NAME")));
					json.append(",");
						
					json.append("\"cash_item_id\"");  
					json.append(":");
					json.append(JsonListMapUtil.objectToJson(cashMap.get("CASH_ITEM_ID")));
					json.append(",");
					
					json.append("\"cash_dire\"");  
					json.append(":");
					json.append(JsonListMapUtil.objectToJson(cashMap.get("CASH_DIRE")));
					json.append(",");
					
					json.append("\"money\""); 
					json.append(":");
					json.append("\""+df.format(cashMap.get("MONEY"))+"\"");
					json.append("},");
					
					//moneySum+=Double.parseDouble(cashMap.get("CASH_MONEY").toString());
        		}
			}
			
			json.setCharAt(json.length() - 1, ']'); 
			json.append(",\"state\":\"true\"}"); 
		}else{
			json.append("],\"state\":\"true\"}");
		}
		
		//logger.debug(json.toString());
		return json.toString();
	}

	//打印凭证-单张凭证打印
	@Override
	public String querySuperVouchPrint(Map<String, Object> map)throws DataAccessException {
		// TODO Auto-generated method stub
		
		try{
			
			//查询凭证主表
			Map<String, Object> vouchMap=new HashMap<String, Object>();
			vouchMap=superVouchMapper.queryVouchPrintMain(map);
		
			//查询凭证明细表
			List<Map<String, Object>> liDetail=new ArrayList<Map<String,Object>>();
			liDetail=superVouchMapper.queryVouchPrintDetail(map);
			
			return superPrintService.getMapListByPrintTemplateJson(map,vouchMap,liDetail);
			
			//物理分页打印
			//SysPage sysPage = new SysPage();
			//明细结束行-起始行+1
			//int pageSize=Integer.parseInt(superPrintService.getSuperPrintParaValue(paraList,"005"))-Integer.parseInt(superPrintService.getSuperPrintParaValue(paraList,"004"))+1;
			//sysPage.setPage(Integer.parseInt(map.get("page").toString()));
			//sysPage.setPagesize(pageSize);
			//RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			
			
			//主表：vouchMap，明细表：liDetail
			//vouchMap.put("print_user_id", map.get("print_user_id"));
			//vouchMap.put("print_template_code", map.get("print_template_code"));
			//String content=superPrintService.querySuperPrintfillBatch(vouchMap,liDetail);
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
		
	}
	
	
	//批量打印凭证-返回spreadjson
	@Override
	public String querySuperVouchPrintBatch(Map<String, Object> map)throws DataAccessException {
		
		try{
			
			//查询凭证主表
			List<Map<String,Object>> mainList=superVouchMapper.querySuperVouchPrintBatch(map);
					
			//查询凭证明细表
			List<Map<String,Object>> detailList=superVouchMapper.querySuperVouchPrintBatch_detail(map);
			
			return superPrintService.getBatchListByPrintTemplateJson(map,mainList,detailList);
			
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
		
	}
	
	//PageOffice单张打印凭证-返回Map
	@Override
	public Map<String,Object> querySuperVouchPrintPage(Map<String, Object> map)throws DataAccessException {
		// TODO Auto-generated method stub
		
		try{
			
			Map<String,Object> reMap=new HashMap<String,Object>();
//			WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
//			SuperVouchMapper superVouchMapper = (SuperVouchMapper)context.getBean("superVouchMapper");
			if((map.get("p020")!=null && map.get("p020").equals("是")) || map.get("template_code").toString().equals("01002")){
				//分栏打印
				//查询凭证主表
				Map<String, Object> vouchMap=new HashMap<String, Object>();
				vouchMap=superVouchMapper.queryVouchPrintMain(map);
				
				//查询凭证明细表
				List<Map<String, Object>> detailList=superVouchMapper.queryVouchPrintDetail(map);
							
				//打印辅助核算
				if(map.get("p019")!=null && map.get("p019").equals("是")){
					detailList=querySuperVouchCheckPrint(map,detailList);
				}
				
				if(map.get("p020")!=null && map.get("p020").equals("是") && detailList!=null && detailList.size()>0){
					 //分栏打印
					Map<String,Object> detailNew=null;
					Map<String,Object> addMap=new HashMap<String,Object>();
					List<Map<String,Object>> detailListTemp=new ArrayList<Map<String,Object>>();
					for(Map<String,Object> detail:detailList){
						
						if(addMap!=null && addMap.get(detail.get("DID").toString())!=null){
							continue;
						}
						
						detailNew=new HashMap<String,Object>();
						addMap.put(detail.get("DID").toString(), detail.get("DID").toString());
						detailNew.putAll(detail);
						String dfKindCode="01";
						if(detail.get("KIND_CODE").toString().equals("01")){
							dfKindCode="02";
						}
						
						for(Map<String,Object> df:detailList){
							if(df.get("ID").toString().equals(detail.get("ID").toString()) && df.get("KIND_CODE").toString().equals(dfKindCode)){
								//加上对方科目数据
								
								if(addMap!=null && addMap.get(df.get("DID").toString())!=null){
									continue;
								}
								
								addMap.put(df.get("DID").toString(), df.get("DID").toString());
								detailNew.put("BUDG_SUBJ_CODE", df.get("SUBJ_CODE"));
								detailNew.put("BUDG_SUBJ_NAME", df.get("SUBJ_NAME"));
								detailNew.put("BUDG_SUBJ_NAME_ALL", df.get("SUBJ_NAME_ALL"));
								detailNew.put("BUDG_DEBIT", df.get("DEBIT"));
								detailNew.put("BUDG_CREDIT", df.get("CREDIT"));
								if(detailNew.get("SUMMARY")==null){
									detailNew.put("SUMMARY", df.get("SUMMARY"));
								}
								break;
							}
						}
						
						
						detailListTemp.add(detailNew);
					}
					detailList=detailListTemp;
				}
				
				reMap.put("main", vouchMap);
				reMap.put("detail", detailList);
			}else{
				
				//按财务预算会计科目分凭证打印,需要调批量打印方法
				List<Map<String,Object>> mainList=superVouchMapper.querySuperVouchPrintBatch_kind(map);
				map.put("kind_code", "||'-'||s.kind_code");
				
				//查询凭证明细表
				List<Map<String,Object>> detailList=superVouchMapper.querySuperVouchPrintBatch_detail(map);
				
				//打印辅助核算
				if(map.get("p019")!=null && map.get("p019").equals("是")){
					detailList=querySuperVouchCheckPrint(map,detailList);
				}
				
				if(map.get("p020")!=null && map.get("p020").equals("是") && detailList!=null && detailList.size()>0){
					 //分栏打印
					Map<String,Object> detailNew=null;
					Map<String,Object> addMap=new HashMap<String,Object>();
					List<Map<String,Object>> detailListTemp=new ArrayList<Map<String,Object>>();
					for(Map<String,Object> detail:detailList){
						
						if(addMap!=null && addMap.get(detail.get("DID").toString())!=null){
							continue;
						}
						
						detailNew=new HashMap<String,Object>();
						addMap.put(detail.get("DID").toString(), detail.get("DID").toString());
						detailNew.putAll(detail);
						String dfKindCode="01";
						if(detail.get("KIND_CODE").toString().equals("01")){
							dfKindCode="02";
						}
						
						for(Map<String,Object> df:detailList){
							if(df.get("ID").toString().equals(detail.get("ID").toString()) && df.get("KIND_CODE").toString().equals(dfKindCode)){
								//加上对方科目数据
								
								if(addMap!=null && addMap.get(df.get("DID").toString())!=null){
									continue;
								}
								
								addMap.put(df.get("DID").toString(), df.get("DID").toString());
								detailNew.put("BUDG_SUBJ_CODE", df.get("SUBJ_CODE"));
								detailNew.put("BUDG_SUBJ_NAME", df.get("SUBJ_NAME"));
								detailNew.put("BUDG_SUBJ_NAME_ALL", df.get("SUBJ_NAME_ALL"));
								detailNew.put("BUDG_DEBIT", df.get("DEBIT"));
								detailNew.put("BUDG_CREDIT", df.get("CREDIT"));
								if(detailNew.get("SUMMARY")==null){
									detailNew.put("SUMMARY", df.get("SUMMARY"));
								}
								break;
							}
						}
						
						
						detailListTemp.add(detailNew);
					}
					detailList=detailListTemp;
				}
				
				reMap.put("main", mainList);
				reMap.put("detail", detailList);
			}
			
			return reMap;
		
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
		
	}
	
	
	
	//PageOffice批量打印凭证-返回Map
	@Override
	public Map<String,Object> querySuperVouchPrintBatchPage(Map<String, Object> map)throws DataAccessException {
		
		try{
			Map<String,Object> reMap=new HashMap<String,Object>();
//			WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
//			SuperVouchMapper superVouchMapper = (SuperVouchMapper)context.getBean("superVouchMapper");
		
			//查询凭证主表
			List<Map<String,Object>> mainList=null;
			if(map.get("p020")!=null && map.get("p020").equals("是")){
				//分栏打印
				mainList=superVouchMapper.querySuperVouchPrintBatch(map);
				map.put("kind_code", "");
			}else{
				//按财务预算会计科目分凭证打印
				mainList=superVouchMapper.querySuperVouchPrintBatch_kind(map);
				map.put("kind_code", "||'-'||s.kind_code");
			}
						
			//查询凭证明细表
			List<Map<String,Object>> detailList=superVouchMapper.querySuperVouchPrintBatch_detail(map);
			
			//打印辅助核算
			if(map.get("p019")!=null && map.get("p019").equals("是")){
				detailList=querySuperVouchCheckPrint(map,detailList);
			}
			
			if(map.get("p020")!=null && map.get("p020").equals("是") && detailList!=null && detailList.size()>0){
				 //分栏打印
				Map<String,Object> detailNew=null;
				Map<String,Object> addMap=new HashMap<String,Object>();
				List<Map<String,Object>> detailListTemp=new ArrayList<Map<String,Object>>();
				for(Map<String,Object> detail:detailList){
					
					if(addMap!=null && addMap.get(detail.get("DID").toString())!=null){
						continue;
					}
					
					detailNew=new HashMap<String,Object>();
					addMap.put(detail.get("DID").toString(), detail.get("DID").toString());
					detailNew.putAll(detail);
					String dfKindCode="01";
					if(detail.get("KIND_CODE").toString().equals("01")){
						dfKindCode="02";
					}
					
					for(Map<String,Object> df:detailList){
						if(df.get("ID").toString().equals(detail.get("ID").toString()) && df.get("KIND_CODE").toString().equals(dfKindCode)){
							//加上对方科目数据
							
							if(addMap!=null && addMap.get(df.get("DID").toString())!=null){
								continue;
							}
							
							addMap.put(df.get("DID").toString(), df.get("DID").toString());
							detailNew.put("BUDG_SUBJ_CODE", df.get("SUBJ_CODE"));
							detailNew.put("BUDG_SUBJ_NAME", df.get("SUBJ_NAME"));
							detailNew.put("BUDG_SUBJ_NAME_ALL", df.get("SUBJ_NAME_ALL"));
							detailNew.put("BUDG_DEBIT", df.get("DEBIT"));
							detailNew.put("BUDG_CREDIT", df.get("CREDIT"));
							if(detailNew.get("SUMMARY")==null){
								detailNew.put("SUMMARY", df.get("SUMMARY"));
							}
							break;
						}
					}
					
					
					detailListTemp.add(detailNew);
				}
				detailList=detailListTemp;
			}
			
			reMap.put("main", mainList);
			reMap.put("detail", detailList);
			
			return reMap;
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
		
	}
	
	//根据分录查询辅助核算，并且重新组合数据
	private List<Map<String,Object>> querySuperVouchCheckPrint(Map<String, Object> map,List<Map<String,Object>> detailList){
		
		if(detailList==null || detailList.size()==0){
			return detailList;
		}
		
		//打印辅助核算
		Map<String, Object> tempMap = new HashMap<String, Object>();
		tempMap.put("group_id", map.get("group_id"));
		tempMap.put("hos_id", map.get("hos_id"));
		tempMap.put("copy_code", map.get("copy_code"));
		//查询当前账套下所有的辅助核算信息
		List<Map<String, Object>> checkTypeList =superVouchMapper.queryAccCheckTypeColumnCheck(tempMap);
			
		
		List<Map<String,Object>> detailCheckList=new ArrayList<Map<String,Object>>();
		for(Map<String,Object> desMap:detailList){
			
			if(desMap.get("IS_CHECK").toString().equals("1")){
				
				//检查多辅助核算
				List<Map<String,Object>> typeList=new ArrayList<Map<String,Object>>();
				for(Map<String, Object> typeMap:checkTypeList){
					if(desMap.get("CHECK1")!=null && desMap.get("CHECK1").equals(typeMap.get("check_type_id"))){
						typeList.add(typeMap);
					}else if(desMap.get("CHECK2")!=null && desMap.get("CHECK2").equals(typeMap.get("check_type_id"))){
						typeList.add(typeMap);
					}else if(desMap.get("CHECK3")!=null && desMap.get("CHECK3").equals(typeMap.get("check_type_id"))){
						typeList.add(typeMap);
					}else if(desMap.get("CHECK4")!=null && desMap.get("CHECK4").equals(typeMap.get("check_type_id"))){
						typeList.add(typeMap);
					}
				}
				
				//拼装辅助核算SQL
				StringBuffer selSql=new StringBuffer();//查询字段列名
				StringBuffer leftSql=new StringBuffer();//关联语句
				int i=0;
				
				for(Map<String,Object> type:typeList){
					
					i++;
					String table=type.get("check_table").toString();
					String checkTable=type.get("check_table").toString()+i;//有多个自定义核算，会重复关联
					String columnCheck=type.get("column_check").toString();
					
					if(type.get("is_sys").toString().equals("1")){
						//系统核算不需要加账套
						leftSql.append(" left join "+table+" "+checkTable+" on "+checkTable+".group_id=a.group_id and "+checkTable+".hos_id=a.hos_id");
						
						if(type.get("is_change").toString().equals("1")){
							//有变更表
							leftSql.append(" and "+checkTable+"."+type.get("column_id")+"=a."+columnCheck+"_id and "+checkTable+".is_stop=0");//关联业务表数据
						}else{
							//没有变更表
							leftSql.append(" and "+checkTable+"."+type.get("column_id")+"=a."+columnCheck+"_id");//关联业务表数据
						}
						
					}else{
						//非系统核算需要加账套
						leftSql.append(" left join "+table+" "+checkTable+" on "+checkTable+".group_id=a.group_id and "+checkTable+".hos_id=a.hos_id and "+checkTable+".copy_code=a.copy_code");//关联业务表数据
						leftSql.append(" and "+checkTable+".check_type_id="+type.get("check_type_id")+" and "+checkTable+"."+type.get("column_id")+"=a."+columnCheck);//关联业务表数据
					}
				
					if(i>1){
						selSql.append("||' '||");//多辅助核算空格隔开
					}
					selSql.append(checkTable+"."+type.get("column_name"));
					
				}
				
				map.put("vouch_id", desMap.get("ID"));
				map.put("did", desMap.get("DID"));
				map.put("selSql", selSql.toString());
				map.put("leftSql", leftSql.toString());
				//查询辅助核算
				List<Map<String,Object>> checkList=superVouchMapper.querySuperVouchCheckPrint(map);
				if(checkList!=null && checkList.size()>0){
					for(Map<String,Object> cheMap:checkList){
						detailCheckList.add(cheMap);
					}
				}
				
			}else{
				detailCheckList.add(desMap);
			}
		}
		
		return detailCheckList;
	}
	
	/**
	 * 点击凭证分录，显示科目余额
	 */
	public String querySuperVouchSubjBalance(Map<String,Object> map) throws DataAccessException{
		Map<String,Object> reMap=superVouchMapper.querySuperVouchSubjBalance(map);
		StringBuilder res=new StringBuilder();
		res.append("{\"bal_os\":\""+reMap.get("bal_os")+"\",");
		res.append("\"this_od\":\""+reMap.get("this_od")+"\",");
		res.append("\"this_oc\":\""+reMap.get("this_oc")+"\",");
		res.append("\"end_os\":\""+reMap.get("end_os")+"\"}");
		return res.toString();
	}
	
	/**
	 * 辅助核算，查询余额
	 */
	@Override
	public String querySuperVouchCheckBalance(Map<String, Object> checkMap) {
		Map<String,Object> reMap=superVouchMapper.querySuperVouchCheckBalance(checkMap);
		StringBuilder res=new StringBuilder();
		res.append("{\"bal_os\":\""+reMap.get("bal_os")+"\",");
		res.append("\"this_od\":\""+reMap.get("this_od")+"\",");
		res.append("\"this_oc\":\""+reMap.get("this_oc")+"\",");
		res.append("\"end_os\":\""+reMap.get("end_os")+"\"}");
		return res.toString();
		
	}
	
	/*
	 * 自动凭证，检查科目是否有辅助核算，用于查询辅助核算数据的检索条件，适用于单辅助核算
	 * checkEl:辅助核算类绑定的字段
	 * 返回辅助核算{"HOS_DEPT_DICT","65,66,67"},{"HOS_EMP_DICT","65,66,67"}
	 */
	public String getCheckSubjId(List<Map<String, Object>> metaList,String checkEl){
		
		StringBuffer subjId=new StringBuffer();
		for(Map<String, Object> map:metaList){
			if(map!=null && map.size()>0){
				
				if(map.get("check1")!=null && map.get("check1").toString().equalsIgnoreCase(checkEl)){
					subjId.append(map.get("subj_code").toString()+",");
				}
				
				if(map.get("check2")!=null && map.get("check2").toString().equalsIgnoreCase(checkEl)){
					subjId.append(map.get("subj_code").toString()+",");
				}
				
				if(map.get("check3")!=null && map.get("check3").toString().equalsIgnoreCase(checkEl)){
					subjId.append(map.get("subj_code").toString()+",");
				}
				
				if(map.get("check4")!=null && map.get("check4").toString().equalsIgnoreCase(checkEl)){
					subjId.append(map.get("subj_code").toString()+",");
				}
			}
		}
		return subjId==null?"":subjId.toString();
	}
	
	
	/*
	 * 自动凭证，根据业务数据绑定的辅助核算，检查科目挂的辅助核算是否一致
	 * 返回辅助核算查询SQL字符串：[查询字段][分组字段]
	 * checkEl：{CHECK4_ID:V_MAT_IN_MAIN.STORE_ID}
	 * 返回格式：
	 * {,t1.dept_id||'@'||t1.dept_no "check1",t1.dept_code||' '||t1.dept_name "check1_name"},
	 * { left join hos_dept_dict d on a.group_id = d.group_id AND a.hos_id = d.hos_id and a.dept_id=d.dept_id and a.dept_no=d.dept_no },
	 * {,d.dept_id,d.dept_no,d.dept_code,d.dept_name}
	 */
	public List<String> getCheckSqlBySubjId(Map<String, Object> detailMap,Map<String,String> checkEl)throws SysException{
		if(checkEl==null || checkEl.size()==0){
			return null;
		}
		
		
		List<String> checkSqlList=new ArrayList<String>();
		StringBuilder selSql=new StringBuilder();
		StringBuilder leftSql=new StringBuilder();
		StringBuilder groupSql=new StringBuilder();
		StringBuilder leftAndSql=null;
		Map<String,Object> zcheck=null;
		boolean isZC=true;//只允许一个科目挂一个自定义辅助核算，同步字典数据
		
		String vouch_date=detailMap.get("vouch_date").toString();
		//应收应付科目:occur_date发生日期,due_date到期日期
		if(detailMap.get("subj_nature_code").toString().equals("04") || detailMap.get("subj_nature_code").toString().equals("05")){
			String due_date="";//到期日期
			
			if(MyConfig.getSysPara("002").equals("0")){
				//到期日期，默认月底
				Calendar calendar = new GregorianCalendar();  
				calendar.setTime(DateUtil.stringToDate(vouch_date,"yyyy-MM-dd"));
				calendar.add(Calendar.MONTH, 1);  
		        calendar.set(Calendar.DAY_OF_MONTH, 0);
		        due_date=DateUtil.dateToString(calendar.getTime(),"yyyy-MM-dd");
			}else{
				//到期日期，默认年底
				due_date=vouch_date.substring(0,4)+"-12-31";
			}
			
			//合同编号，付款单业务辅助核算表合同编号字段写单据号：模板明细摘要配置关键字段{v_mat_pay_main.business_no}
			String con_no=checkEl.get("CON_NO");
			if(con_no!=null && !con_no.equals("")){
				groupSql.append(","+con_no);
				con_no=con_no.substring(con_no.indexOf(".")+1,con_no.length());
				selSql.append(",t1."+con_no+" con_no");
				
			}
			
			selSql.append(",'"+vouch_date+"' occur_date,'"+due_date+"' due_date");
		}
		
		for(Map.Entry<String, Object> data:detailMap.entrySet()){
			
			if(data.getValue()==null){
				continue;
			}
			
			String key=data.getKey();
			
			//只能同时挂4个辅助核算
			if(key.equalsIgnoreCase("check1") || key.equalsIgnoreCase("check2")
					|| key.equalsIgnoreCase("check3") || key.equalsIgnoreCase("check4")){
			
				//自定义辅助核算
				if(isZC && data.getValue().toString().indexOf("ZCHECK")!=-1){		
					isZC=false;
					
					//根据核算类型column_check查找字典对应表，自动生成凭证自定义核算关联系统字典表 
					zcheck=new HashMap<String,Object>();
					zcheck=superVouchMapper.queryAccCheckTypeZCode(SessionManager.getGroupId(),SessionManager.getHosId(),SessionManager.getCopyCode(),data.getValue().toString(),"["+checkEl.get("busi_type_code").toString()+"]");
					if(zcheck!=null && zcheck.size()>0){
						//拼音码字段
						if(zcheck.get("z_spell_code")!=null && !zcheck.get("z_spell_code").toString().equals("")){
							zcheck.put("z_spell_code", ","+zcheck.get("z_spell_code"));//$
						}else{
							zcheck.put("z_spell_code", ",''");
						}
						//五笔码字段
						if(zcheck.get("z_wbx_code")!=null && !zcheck.get("z_wbx_code").toString().equals("")){
							zcheck.put("z_wbx_code", ","+zcheck.get("z_wbx_code"));//$
						}else{
							zcheck.put("z_wbx_code", ",''");
						}
						//排序字段
						if(zcheck.get("z_sort_code")!=null && !zcheck.get("z_sort_code").toString().equals("")){
							zcheck.put("z_sort_code", ","+zcheck.get("z_sort_code"));//$
						}else{
							
							zcheck.put("field_table","acc_check_item");
							zcheck.put("field_key1", "check_type_id");
							zcheck.put("field_value1", zcheck.get("check_type_id"));
							zcheck.put("field_sort", "sort_code");
							int count=sysFunUtilMapper.querySysMaxSort(zcheck);//取最大排序号
							zcheck.put("z_sort_code", ","+count+"+rownum*10");
						}
						
						//根据业务表的编码复制辅助核算项表
						superVouchMapper.copyAccCheckItemByBusi(zcheck);
						
						//拼接业务表与辅助核算项表SQL
						selSql.append(",t1.check_item_id "+data.getValue().toString()+",t1.check_item_code||' '||t1.check_item_name "+data.getValue().toString()+"_name");
						leftSql.append(" left join acc_check_item on acc_check_item.group_id=b.group_id and acc_check_item.hos_id=b.hos_id and acc_check_item.copy_code=b.copy_code ");
						leftSql.append(" and acc_check_item.check_type_id="+zcheck.get("check_type_id")+" and acc_check_item.check_item_code="+zcheck.get("z_busi_code") );
						groupSql.append(",acc_check_item.check_item_id,acc_check_item.check_item_code,acc_check_item.check_item_name");
					}
					
				}
				
				
				String checkTable=detailMap.get(key+"_table").toString();
				leftAndSql=new StringBuilder();
				for(Map.Entry<String, String> check:checkEl.entrySet()){
						
					//业务数据字段绑定的辅助核算字段，check.getKey()：CHECK4_ID；data.getValue()：check4
					if(check.getKey().indexOf(data.getValue().toString())!=-1){
						if(check.getValue().endsWith("_NO")){
							leftAndSql.append(" and "+checkTable+"."+detailMap.get(key+"_no")+"="+check.getValue());
						}else{
							leftAndSql.append(" and "+checkTable+"."+detailMap.get(key+"_id")+"="+check.getValue());
						}
							
					}
				}
					
				if(leftAndSql!=null && !leftAndSql.toString().equals("")){
						
					leftSql.append(" left join "+checkTable+" on "+checkTable+".group_id = b.group_id AND "+checkTable+".hos_id = b.hos_id "+leftAndSql+" ");
						
					if(detailMap.get(key+"_id")!=null && !detailMap.get(key+"_id").equals("")){
							
						selSql.append(",t1."+detailMap.get(key+"_id"));						
						groupSql.append(","+checkTable+"."+detailMap.get(key+"_id"));
						
					}
					if(detailMap.get(key+"_no")!=null && !detailMap.get(key+"_no").equals("")){
							
						selSql.append("||'@'||t1."+detailMap.get(key+"_no")+" \""+data.getValue().toString().toLowerCase()+"\"");//check1
						groupSql.append(","+checkTable+"."+detailMap.get(key+"_no"));
							
					}
					if(detailMap.get(key+"_code")!=null && !detailMap.get(key+"_code").equals("")){
							
						selSql.append(",t1."+detailMap.get(key+"_code"));
						groupSql.append(","+checkTable+"."+detailMap.get(key+"_code"));
							
					}
					if(detailMap.get(key+"_name")!=null && !detailMap.get(key+"_name").equals("")){
							
						selSql.append("||' '||t1."+detailMap.get(key+"_name")+" \""+data.getValue().toString().toLowerCase()+"_name\"");//check1_name
						groupSql.append(","+checkTable+"."+detailMap.get(key+"_name"));
							
					}
						
				}
			}
			
		}
		if(selSql!=null && !selSql.toString().equals("") && leftSql!=null && !leftSql.toString().equals("") && groupSql!=null && !groupSql.toString().equals("")){
			checkSqlList.add(selSql.toString());//查询字段
			checkSqlList.add(leftSql.toString());//关联语句
			checkSqlList.add(groupSql.toString());//分组字段
		}
		
		return checkSqlList;
	}
	
	
	/*
	 * 自动凭证-保存自动辅助核算表acc_auto_check
	 */
	public void saveAccAutoCheck(Map<String, Object> detailMap,Map<String,String> checkEl,String vouch_sql,String where_sql,String group_sql)throws SysException{
		
		if(checkEl==null || checkEl.size()==0){
			return;
		}
		
		StringBuffer groupSql=new StringBuffer();
		StringBuffer column=new StringBuffer();//insert字段
		StringBuffer selSql=new StringBuffer();//t1表查询字段
		StringBuffer vouchSelSql=new StringBuffer();//子查询字段
		StringBuffer leftSql=new StringBuffer();
		boolean isZC=true;//自动凭证只支持一个科目挂一个自定义辅助核算，同步系统字典数据
		
		String vouch_date=detailMap.get("vouch_date").toString();
		//应收应付科目:occur_date发生日期,due_date到期日期
		if(detailMap.get("subj_nature_code").toString().equals("04") || detailMap.get("subj_nature_code").toString().equals("05")){
			String due_date="";//到期日期
			
			if(MyConfig.getSysPara("002").equals("0")){
				//到期日期，默认月底
				Calendar calendar = new GregorianCalendar();  
				calendar.setTime(DateUtil.stringToDate(vouch_date,"yyyy-MM-dd"));
				calendar.add(Calendar.MONTH, 1);  
		        calendar.set(Calendar.DAY_OF_MONTH, 0);
		        due_date=DateUtil.dateToString(calendar.getTime(),"yyyy-MM-dd");
			}else{
				//到期日期，默认年底
				due_date=vouch_date.substring(0,4)+"-12-31";
			}
			
			//合同编号，付款单业务辅助核算表合同编号字段写单据号：模板明细摘要配置关键字段{v_mat_pay_main.business_no}
			String con_no=checkEl.get("CON_NO");
			if(con_no!=null && !con_no.equals("")){
				vouchSelSql.append(","+con_no+" con_no");
				groupSql.append(","+con_no);
				//con_no=con_no.substring(con_no.indexOf(".")+1,con_no.length());
				column.append(",con_no");
				selSql.append(",t1.con_no");
				
			}
			
			//单据号
			String business_no=checkEl.get("BUSINESS_NO");
			if(business_no!=null && !business_no.equals("")){
				vouchSelSql.append(","+business_no+" business_no");
				groupSql.append(","+business_no);
				//con_no=con_no.substring(con_no.indexOf(".")+1,con_no.length());
				column.append(",business_no");
				selSql.append(",t1.business_no");
				
			}
			
			column.append(",occur_date,due_date");
			selSql.append(",to_date('"+vouch_date+"','yyyy-MM-dd') occur_date,to_date('"+due_date+"','yyyy-MM-dd') due_date");
		}
		
		boolean isCheck6=false;//供应商辅助核算，付款业务040703，将第一条辅助核算的供应商名称更新到分录的摘要上
		String busiTypeCode=detailMap.get("busi_type_code").toString();//业务类型
		for(Map.Entry<String, Object> data:detailMap.entrySet()){
			
			if(data.getValue()==null){
				continue;
			}
			
			String key=data.getKey();
			
			//只能同时挂4个辅助核算
			if(key.equalsIgnoreCase("check1") || key.equalsIgnoreCase("check2")
					|| key.equalsIgnoreCase("check3") || key.equalsIgnoreCase("check4")){
			
				if(data.getValue().toString().equalsIgnoreCase("CHECK6") && busiTypeCode.equals("040703")){
					//供应商辅助核算
					isCheck6=true;
				}
				
				//自定义辅助核算
				if(isZC && data.getValue().toString().indexOf("ZCHECK")!=-1){
					isZC=false;
					
					//根据核算类型column_check查找字典对应表，自动生成凭证自定义核算关联系统字典表 
					Map<String,Object> zcheck=new HashMap<String,Object>();
					zcheck=superVouchMapper.queryAccCheckTypeZCode(SessionManager.getGroupId(),SessionManager.getHosId(),SessionManager.getCopyCode(),data.getValue().toString(),"["+checkEl.get("busi_type_code").toString()+"]");
					if(zcheck!=null && zcheck.size()>0){
						/*暂时不做字典与自定义辅助核算同步，业务字典的编码与自定义核算项的编码保持一致即可
						//拼音码字段
						if(zcheck.get("z_spell_code")!=null && !zcheck.get("z_spell_code").toString().equals("")){
							zcheck.put("z_spell_code", ","+zcheck.get("z_spell_code"));//$
						}else{
							zcheck.put("z_spell_code", ",''");
						}
						//五笔码字段
						if(zcheck.get("z_wbx_code")!=null && !zcheck.get("z_wbx_code").toString().equals("")){
							zcheck.put("z_wbx_code", ","+zcheck.get("z_wbx_code"));//$
						}else{
							zcheck.put("z_wbx_code", ",''");
						}
						//排序字段
						if(zcheck.get("z_sort_code")!=null && !zcheck.get("z_sort_code").toString().equals("")){
							zcheck.put("z_sort_code", ","+zcheck.get("z_sort_code"));//$
						}else{
							
							zcheck.put("field_table","acc_check_item");
							zcheck.put("field_key1", "check_type_id");
							zcheck.put("field_value1", zcheck.get("check_type_id"));
							zcheck.put("field_sort", "sort_code");
							int count=sysFunUtilMapper.querySysMaxSort(zcheck);//取最大排序号
							zcheck.put("z_sort_code", ","+count+"+rownum*10");
						}
						
						//根据业务表的编码复制辅助核算项表-预算支出项目,写死的
						superVouchMapper.copyAccCheckItemByBusi(zcheck);
						*/
						
						//拼接业务表与辅助核算项表SQL
						column.append(","+data.getValue());//zcheck1
						selSql.append(",t1."+data.getValue());//zcheck1
						vouchSelSql.append(",acc_check_item.check_item_id "+data.getValue());
						groupSql.append(",acc_check_item.check_item_id");
						leftSql.append(" left join acc_check_item on acc_check_item.group_id=b.group_id and acc_check_item.hos_id=b.hos_id and acc_check_item.copy_code=b.copy_code ");
						leftSql.append(" and acc_check_item.check_type_id="+zcheck.get("check_type_id")+" and acc_check_item.check_item_code="+zcheck.get("z_busi_code") );
					}
					
				}
				
				
				for(Map.Entry<String, String> check:checkEl.entrySet()){
						
					//业务数据字段绑定的辅助核算字段，check.getKey()：CHECK4_ID；data.getValue()：check4；check.getValue()：V_MAT_IN_MAIN.STORE_ID
					if(check.getKey().indexOf(data.getValue().toString())!=-1){
						
						if(column.indexOf(check.getKey())==-1 ) {
							column.append(","+check.getKey());
							selSql.append(",t1."+check.getKey());
							vouchSelSql.append(","+check.getValue()+" "+check.getKey());
							groupSql.append(","+check.getValue());
						}
						
					}
				}
				
			}
			
		}
		
		if(column!=null && !column.toString().equals("")){
						
			String checkSelSql="select b.subj_code"+vouchSelSql+",round(sum("+detailMap.get("amount_money")+"),2) amount_money from "+detailMap.get("from_table")+" ";
			detailMap.put("column", column);
			detailMap.put("sel_sql", selSql);
			detailMap.put("vouch_sql", checkSelSql+vouch_sql.toString()+" ");
			detailMap.put("left_sql", leftSql!=null?leftSql.toString():"");
			detailMap.put("where_sql", where_sql.toString()+" and b.subj_code="+detailMap.get("subj_code")+" ");
			detailMap.put("group_sql", group_sql+groupSql+" ");
			
			//保存自动凭证辅助核算
			superVouchMapper.saveAccAutoCheck(detailMap);
			detailMap.remove("column");
			detailMap.remove("sel_sql1");
			detailMap.remove("vouch_sql");
			detailMap.remove("left_sql");
			detailMap.remove("where_sql");
			detailMap.remove("group_sql");
			
			/*自动凭证辅助核算表金额不等于分录金额，将差额更新到辅助核算第一条数据上，将差额控制到1元钱***********begin***********/
			Double detailSum=0.00;
			if(Integer.parseInt(detailMap.get("direction").toString())==0){
				detailMap.put("debit_credit", "debit");
				detailSum=Double.parseDouble(detailMap.get("debit").toString());
			}else{
				detailMap.put("debit_credit", "credit");
				detailSum=Double.parseDouble(detailMap.get("credit").toString());
			}
			
			Double checkSum=superVouchMapper.queryAutoVouchCheckSum(detailMap);
			if(checkSum.compareTo(detailSum)!=0){
				
				BigDecimal chaMoney=new BigDecimal(0);
				//-1表示小于，0是等于，1是大于
				if(checkSum.compareTo(detailSum)>0){
					//如果辅助核算金额 > 分录金额
					
					chaMoney=BigDecimal.valueOf(checkSum).subtract(BigDecimal.valueOf(detailSum)).setScale(2,BigDecimal.ROUND_HALF_UP);
					if(chaMoney.compareTo(new BigDecimal(1))!=1){
						//辅助核算金额-分录金额   <= 1
						detailMap.put("cha_money", -chaMoney.doubleValue());
					}
					
				}else if(detailSum.compareTo(checkSum)>0){
					//如果分录金额 > 辅助核算金额 
					
					chaMoney=BigDecimal.valueOf(detailSum).subtract(BigDecimal.valueOf(checkSum)).setScale(2,BigDecimal.ROUND_HALF_UP);
					if(chaMoney.compareTo(new BigDecimal(1))!=1){
						//分录金额 - 辅助核算金额   <= 1
						detailMap.put("cha_money", chaMoney.doubleValue());
					}
				}
				
				if(detailMap.get("cha_money")!=null){
					//自动凭证辅助核算表金额不等于分录金额，将差额更新到辅助核算第一条数据上，将差额控制到1元钱
					superVouchMapper.updateAutoVouchCheckByOne(detailMap);
					detailMap.remove("debit_credit");
					detailMap.remove("cha_money");
				}
				
			}
			/*自动凭证辅助核算表金额不等于分录金额，将差额更新到辅助核算第一条数据上，将差额控制到1元钱***********end***********/
			
		}
		
		//供应商辅助核算，付款业务040703，将第一条辅助核算的供应商名称更新到分录的摘要上
		//停用通过自动凭证模板明细摘要实现，如：[供应商]摘要，通过这个方法处理：updateAutoVouchDetailSummary
/*		if(isCheck6){
			String supName=superVouchMapper.queryAutoVouchCheckByCheck6(detailMap);
			detailMap.put("summary", "["+supName+"]"+detailMap.get("summary").toString());
		}*/
		
		
		detailMap.remove("vouch_date");//凭证日期
		detailMap.remove("amount_money");//金额字段
		detailMap.remove("from_table");//子查询表名
		detailMap.remove("busi_type_code");//业务类型
		
	}
	
	
	/*******************************自動凭证處理********************begin*****************************************/
	/**
	 * 组装凭证json格式
	 * mainMap：主表数据
	 * 主表map格式：vouch_date,vouch_type_code
	 * 
	 * detailList分录数据，里面放多个结果集List<Map<String, Object>>
	 * 分录Map格式：SUBJ_ID, SUMMARY, DEBIT, CREDIT, SUBJ_NAME, SUBJ_DIRE, SUBJ_TYPE_CODE, SUBJ_NATURE_CODE, IS_CHECK, IS_CASH, CHECK1, CHECK2, CHECK3, CHECK4
	 * 
	 * checkMap辅助核算数据，根据分录结果集的下标存放List<Map<String, Object>>
	 * 辅助核算Map格式：SUBJ_ID, SUMMARY, MONEY, CHECK1(部门的ID和变更号/1@2), CHECK1_NAME(部门的编码和名称/10370102 肿瘤中医门诊)
	 * 
	 * cashMap辅助核算数据，根据分录结果集的下标存放List<Map<String, Object>>
	 * 现金流量标注Map格式：SUBJ_ID, SUMMARY, MONEY, CASH_ITEM_ID(现金流量项目ID)，CASH_NAME(现金流量项目名称/101 开展医疗服务活动收到的现金)
	 *
	 */
	@Override
	public String getVouchJon(Map<String,Object> mainMap,List<List<Map<String, Object>>> detailList,Map<Integer,List<Map<String, Object>>> checkMap,Map<Integer,List<Map<String, Object>>> cashMap) throws Exception{
		if(mainMap==null || mainMap.size()==0){
			return "{\"error\":\"没有主表数据！\",\"state\":\"false\"}";
		}
		if(mainMap.get("vouch_date")==null || mainMap.get("vouch_date").equals("")){
			return "{\"error\":\"没有凭证日期！\",\"state\":\"false\"}";
		}
		if(mainMap.get("vouch_type_code")==null || mainMap.get("vouch_type_code").equals("")){
			return "{\"error\":\"没有凭证类型！\",\"state\":\"false\"}";
		}
		
		if (mainMap.get("group_id") == null) {
			mainMap.put("group_id", SessionManager.getGroupId());
		}
		if (mainMap.get("hos_id") == null) {
			mainMap.put("hos_id", SessionManager.getHosId());
		}
		if (mainMap.get("copy_code") == null) {
			mainMap.put("copy_code", SessionManager.getCopyCode());
		}
		if(mainMap.get("acc_year")==null || mainMap.get("acc_year").equals("")){
			mainMap.put("acc_year", mainMap.get("vouch_date").toString().substring(0, 4));
		}
		if(mainMap.get("acc_month")==null || mainMap.get("acc_month").equals("")){
			mainMap.put("acc_month", mainMap.get("vouch_date").toString().substring(5, 7));
		}
		if(mainMap.get("att_num")==null || mainMap.get("att_num").equals("")){
			mainMap.put("att_num", 0);
		}
		
		if(detailList==null || detailList.size()==0){
			throw new SysException("没有明细表数据或者没有科目对应关系！");
		}
		
		StringBuffer json=new StringBuffer();
		StringBuffer checkJson=new StringBuffer();
		StringBuffer cashJson=new StringBuffer();
		json.append("{");
		
		//组装凭证主表数据
		json.append("\"vouch_id\":\"\",");
		json.append("\"vouch_date\":\""+mainMap.get("vouch_date")+"\",");
		json.append("\"vouch_type_code\":\""+mainMap.get("vouch_type_code")+"\",");
		json.append("\"att_num\":\""+mainMap.get("att_num")+"\",");
		String reMaxVouchNo=queryMaxVouchNo(mainMap);
		json.append("\"vouch_no\":\""+reMaxVouchNo+"\",");
		json.append("\"state\":\"1\",");
		json.append("\"create_user\":\""+SessionManager.getUserId()+"\",");
		json.append("\"create_name\":\""+SessionManager.getUserName()+"\",");
		json.append("\"cash_user\":\"\",");
		json.append("\"cash_name\":\"\",");
		json.append("\"audit_user\":\"\",");
		json.append("\"audit_name\":\"\",");
		json.append("\"acc_user\":\"\",");
		json.append("\"acc_name\":\"\",");
		json.append("\"busi_type_code\":\"\",");
		json.append("\"template_code\":\"\",");
		
		boolean isCheckData=false;
		boolean isCashData=false;
		if(checkMap!=null && checkMap.size()>0){
			isCheckData=true;
		}
		if(cashMap!=null && cashMap.size()>0){
			isCashData=true;
		}
		
		//组装凭证明细表数据,detailList里面放多个结果集List<Map<String, Object>>
		int detailSize=0;
		int row=0;
		BigDecimal debitSum = new BigDecimal(0);
		BigDecimal creditSum = new BigDecimal(0);
		BigDecimal cha = new BigDecimal(0);
		json.append("\"Rows\":[");
		
		for(int i=0;i<detailList.size();i++){
			row=0;
			List<Map<String, Object>> list=detailList.get(i);
			if(list!=null && list.size()>0){
				for(Map<String, Object> map:list){
					detailSize++;
					row++;
					json.append("{\"id\":\""+detailSize+"\",\"did\":\"\",");
					
					if(map!=null && map.size()>0){
						
						for(Map.Entry<String, Object> entry:map.entrySet()){
							if(entry.getKey().equalsIgnoreCase("subj_code") || entry.getKey().equalsIgnoreCase("summary") || entry.getKey().equalsIgnoreCase("debit")
									|| entry.getKey().equalsIgnoreCase("credit") || entry.getKey().equalsIgnoreCase("subj_name") || entry.getKey().equalsIgnoreCase("subj_dire")
									|| entry.getKey().equalsIgnoreCase("subj_type_code") || entry.getKey().equalsIgnoreCase("subj_nature_code") || entry.getKey().equalsIgnoreCase("is_check")
									|| entry.getKey().equalsIgnoreCase("is_cash")){
								
								//分录
								if(entry.getKey().equalsIgnoreCase("debit")){
									
									String debit="";
									
									if(null!=entry.getValue().toString()&& !"".equals(entry.getValue().toString())){
										
										debit=entry.getValue().toString();
									}else{
										
										debit="0";
										
									}
									
									if(mainMap.get("isBalance")!=null && mainMap.get("isBalance").equals("1")){
										
										//住院结算业务，最后一条分录取借贷平衡
										if(i==detailList.size()-1 && Double.parseDouble(debit)!=0){
											debit=creditSum.subtract(debitSum).toString();
										}else{
											debitSum=debitSum.add(new BigDecimal(debit));
										}
										
										
									}

									debit=NumberUtil.numberToString(Double.parseDouble(debit));
									
									if(mainMap.get("isBalance")!=null && mainMap.get("isBalance").equals("2")) {
										debitSum=debitSum.add(new BigDecimal(debit));
											
										if(!debitSum.equals(BigDecimal.ZERO) && !creditSum.equals(BigDecimal.ZERO) && i==detailList.size()-1 && row==list.size() && Double.parseDouble(debit)!=0){
											//物资自动凭证业务，四舍五入之后，最后一条分录差5分钱，取借贷平衡,-1小于0等于1大于
											
											//小于0.05
											if(creditSum.compareTo(debitSum)==1 &&  creditSum.subtract(debitSum).compareTo(new BigDecimal(0.05))==-1){
												cha=creditSum.subtract(debitSum);
												debit=cha.add(new BigDecimal(debit)).setScale(2,BigDecimal.ROUND_HALF_UP).toString();//保留2位小数，四舍五入
											}else if(debitSum.compareTo(creditSum)==1 && debitSum.subtract(creditSum).compareTo(new BigDecimal(0.05))==-1){
												cha=debitSum.subtract(creditSum);
												debit=cha.add(new BigDecimal(debit)).setScale(2,BigDecimal.ROUND_HALF_UP).toString();//保留2位小数，四舍五入
											}
											
										}
										
									} 
									
									map.put("debit", debit);
									json.append("\""+entry.getKey().toLowerCase()+"\":\""+debit+"\",");
									json.append("\"debit_name\":\""+debit.replace(".", "")+"\",");//增加借方显示金额
									
								}else if(entry.getKey().equalsIgnoreCase("credit")){
									
									String credit="";
									
									if(null!=entry.getValue().toString()&& !"".equals(entry.getValue().toString())){
										
										credit=entry.getValue().toString();
									}else{
										
										credit="0";
										
									}
									 
									if(mainMap.get("isBalance")!=null && mainMap.get("isBalance").equals("1")){
										
										//住院结算业务，最后一条分录取借贷平衡
										if(i==detailList.size()-1 && Double.parseDouble(credit)!=0){
											credit=debitSum.subtract(creditSum).toString();
										}else{
											creditSum=creditSum.add(new BigDecimal(credit));
										}
										
									}


									credit=NumberUtil.numberToString(Double.parseDouble(credit));
									
									if(mainMap.get("isBalance")!=null && mainMap.get("isBalance").equals("2")){
										creditSum=creditSum.add(new BigDecimal(credit));
										
										if(!debitSum.equals(BigDecimal.ZERO) && !creditSum.equals(BigDecimal.ZERO) && i==detailList.size()-1 && row==list.size() && Double.parseDouble(credit)!=0){
											
											//物资自动凭证业务，四舍五入之后，最后一条分录差5分钱，取借贷平衡,-1表示小于,0是等于,1是大于.	
											if(creditSum.compareTo(debitSum)==1 && creditSum.subtract(debitSum).compareTo(new BigDecimal(0.05))==-1){
												cha=creditSum.subtract(debitSum);
												credit=cha.add(new BigDecimal(credit)).setScale(2,BigDecimal.ROUND_HALF_UP).toString();//保留2位小数，四舍五入
											}else if(debitSum.compareTo(creditSum)==1 && debitSum.subtract(creditSum).compareTo(new BigDecimal(0.05))==-1){
												cha=debitSum.subtract(creditSum);
												credit=cha.add(new BigDecimal(credit)).setScale(2,BigDecimal.ROUND_HALF_UP).toString();//保留2位小数，四舍五入
											}
											
										}
									} 
									
									map.put("credit", credit);
									json.append("\""+entry.getKey().toLowerCase()+"\":\""+credit+"\",");
									json.append("\"credit_name\":\""+credit.replace(".", "")+"\",");//增加贷方显示金额
									
								}else{
									
									if("summary".equals(entry.getKey().toLowerCase())&&mainMap.containsKey("busiLogNo")){
										
										json.append("\""+entry.getKey().toLowerCase()+"\":\"["+mainMap.get("Dsummary")+"]"+entry.getValue()+"\",");
										
									}else{
										
										json.append("\""+entry.getKey().toLowerCase()+"\":\""+entry.getValue()+"\",");
										
									}
									
								}
							}
							
						}
						
						if(map.get("is_check")==null)map.put("is_check","0");
						if(map.get("is_cash")==null)map.put("is_cash","0");
						
						if((isCheckData && map.get("is_check").toString().equals("1") || (isCashData && map.get("is_cash").toString().equals("1") && MyConfig.getSysPara("004").equals("1")))){
							//辅助核算
							checkJson.append(getVouchCheckJson(detailSize,map,checkMap.get(i),isCashData?cashMap.get(i):null));
						}
						
						if(isCashData && map.get("is_cash").toString().equals("1") && MyConfig.getSysPara("004").equals("0")){
							//现金流量
							cashJson.append(getVouchCashJson(detailSize,map,cashMap.get(i)));
							
						}
												
						json.setCharAt(json.length() - 1, ' ');//替换最后一个逗号
					}
					json.append("},");
				}
				
			}
			
		}
		
		if(detailSize>0){
			json.setCharAt(json.length() - 1, ']'); //替换最后一个逗号
		}else{
			json.append("]");
		}
		
		//组装辅助核算
		if(checkJson!=null && !checkJson.toString().equals("")){
			json.append(",\"check\":{"+checkJson);
			json.setCharAt(json.length() - 1, '}'); //替换最后一个逗号
		}
		
		//组装现金流量标注，004现金流量不与辅助核算一起保存
		if(cashJson!=null && !cashJson.toString().equals("") && MyConfig.getSysPara("004").equals("0")){
			json.append(",\"cash\":{"+cashJson);
			json.setCharAt(json.length() - 1, '}'); //替换最后一个逗号
		}
				
		json.append("}");
		
		//logger.debug(json.toString());
		return json.toString();
	}

	//组装辅助核算
	private String getVouchCheckJson(int rowIndex,Map<String, Object> detailIndexMap,List<Map<String, Object>> checkList,List<Map<String, Object>> cashList){
		
		//分录金额
		String detailMoney=Float.parseFloat(detailIndexMap.get("debit").toString())!=0?detailIndexMap.get("debit").toString():detailIndexMap.get("credit").toString();
		
		if(checkList==null || checkList.size()==0){
			return getVouchCashJson(rowIndex,detailIndexMap,cashList);
		}
		
		String subjCode=detailIndexMap.get("subj_code").toString();//分录科目ID
		String summary=detailIndexMap.get("summary").toString();//分录摘要
		
		BigDecimal checkMoeny = new BigDecimal(0);
		boolean isCheckData=false;
		List<Map<String, Object>> checkListNew=new ArrayList<Map<String, Object>>();
		
		for(Map<String, Object> map:checkList){
			if(map.get("subj_code")!=null && map.get("subj_code").toString().equals(subjCode) && map.get("summary")!=null && map.get("summary").toString().equals(summary)){
				isCheckData=true;
				String money=NumberUtil.numberToString(Double.parseDouble(map.get("money").toString()));
				checkMoeny=checkMoeny.add(new BigDecimal(money)).setScale(2,BigDecimal.ROUND_HALF_UP);//累加辅助核算金额，保留2位小数，四舍五入
				checkListNew.add(map);
			}
		}
		
		//计算分录金额-辅助核算金额(统计维度不一样，四舍五入导致)
		checkMoeny=new BigDecimal(detailMoney).subtract(checkMoeny).setScale(2,BigDecimal.ROUND_HALF_UP);
		
		int checkSize=0;
		StringBuffer check=new StringBuffer();
		check.append("\""+rowIndex+"\":[");
		
		for(Map<String, Object> map:checkListNew){
			checkSize++;
			check.append("{\"id\":\""+checkSize+"\",\"cid\":\"\",");
			
			for(Map.Entry<String, Object> entry:map.entrySet()){
				
				if(entry.getKey().equalsIgnoreCase("money")){
					//辅助核算金额
					String money=NumberUtil.numberToString(Double.parseDouble(entry.getValue().toString()));
					/*if(!cha.equals(BigDecimal.ZERO)){
						money=cha.add(new BigDecimal(money)).setScale(2,BigDecimal.ROUND_HALF_UP).toString();//保留2位小数，四舍五入
						cha=new BigDecimal(0);
					}*/
					
					if(!checkMoeny.equals(BigDecimal.ZERO) && checkSize==checkListNew.size()){
						//最后一条记录取差额，辅助核算金额与分录金额保持一致，以分录金额为准
						money=checkMoeny.add(new BigDecimal(money)).setScale(2,BigDecimal.ROUND_HALF_UP).toString();//保留2位小数，四舍五入
					}
					
					check.append("\""+entry.getKey().toLowerCase()+"\":\""+money+"\",");
				}else{
					check.append("\""+entry.getKey().toLowerCase()+"\":\""+entry.getValue()+"\",");
				}
				
			}
			
			//组装现金流量标注，辅助核算与现金流量标注一起保存，有辅助核算
			if(cashList!=null && cashList.size()>0){
				for(Map<String, Object> cashMap:cashList){
					if(cashMap.get("subj_code")!=null && cashMap.get("subj_code").toString().equals(subjCode)){
						
						if(cashMap.get("money")!=null){
							//数据里面有传金额
							String money=NumberUtil.numberToString(Double.parseDouble(cashMap.get("money").toString()));
							check.append("\"money\":\""+money+"\",");
						}else{
							//数据里面没有传金额，取分录金额
							check.append("\"money\":\""+detailMoney+"\",");
						}
						
						for(Map.Entry<String, Object> entry:cashMap.entrySet()){
											
							//剔除金额
							if(!entry.getKey().equalsIgnoreCase("money")){
								check.append("\""+entry.getKey().toLowerCase()+"\":\""+entry.getValue()+"\",");
							}
							
						}
					}
				}
			}
			
			check.setCharAt(check.length() - 1, '}'); //替换最后一个逗号
			check.append(",");
		}
		
		if(!isCheckData){
			//组装现金流量标注，辅助核算与现金流量标注一起保存，没有辅助核算
			if(cashList!=null && cashList.size()>0){
				for(Map<String, Object> cashMap:cashList){
					if(cashMap.get("subj_code")!=null && cashMap.get("subj_code").toString().equals(subjCode)){
						
						checkSize++;
						check.append("{\"id\":\""+checkSize+"\",\"cid\":\"\"");
						
						if(cashMap.get("money")!=null){
							//数据里面有传金额
							String money=NumberUtil.numberToString(Double.parseDouble(cashMap.get("money").toString()));
							check.append(",\"money\":\""+money+"\"");
							
						}else{
							//数据里面没有传金额，取分录金额
							check.append(",\"money\":\""+detailMoney+"\"");
						}
						
						for(Map.Entry<String, Object> entry:cashMap.entrySet()){
							
							//剔除金额
							if(!entry.getKey().equalsIgnoreCase("money")){
								check.append(",\""+entry.getKey().toLowerCase()+"\":\""+entry.getValue()+"\"");
							}
						}
						
						check.append("},");
					}
				}
			}
		}
		
		if(checkSize>0){
			check.setCharAt(check.length() - 1, ']'); //替换最后一个逗号
		}else{
			check.append("]");
		}
		check.append(",");
		return check.toString();
	}
	
	//组装现金流量标注
	private String getVouchCashJson(int rowIndex,Map<String, Object> detailIndexMap,List<Map<String, Object>> cashList){
		
		//分录金额
		String detailMoney=Float.parseFloat(detailIndexMap.get("debit").toString())!=0?detailIndexMap.get("debit").toString():detailIndexMap.get("credit").toString();
		if(cashList==null || cashList.size()==0){
			return "";
		}
		
		String subjId=detailIndexMap.get("subj_id").toString();//分录科目ID
		String summary=detailIndexMap.get("summary").toString();//分录摘要
		int checkSize=0;
		StringBuffer check=new StringBuffer();
		check.append("\""+rowIndex+"\":[");
		
		for(Map<String, Object> map:cashList){
			
			if(map.get("subj_id")!=null && map.get("subj_id").toString().equals(subjId) && map.get("summary")!=null && map.get("summary").toString().equals(summary)){
				checkSize++;
				check.append("{\"id\":\""+checkSize+"\",\"cid\":\"\"");
				
				if(map.get("money")!=null){
					//数据里面有传金额
					String money=NumberUtil.numberToString(Double.parseDouble(map.get("money").toString()));
					check.append(",\"money\":\""+money+"\"");
					
				}else{
					//数据里面没有传金额，取分录金额
					check.append(",\"money\":\""+detailMoney+"\"");
				}
				
				for(Map.Entry<String, Object> entry:map.entrySet()){
					
					//现金流量标注金额不单独取
					if(!entry.getKey().equalsIgnoreCase("money")){
						check.append(",\""+entry.getKey().toLowerCase()+"\":\""+entry.getValue()+"\"");
					}
				}
				check.append("},");
			}
		}
		
		if(checkSize>0){
			check.setCharAt(check.length() - 1, ']'); //替换最后一个逗号
		}else{
			check.append("]");
		}
		check.append(",");
		return check.toString();
	}
	
	
	
	//查询自动凭证数据
	public String queryAutoVouch(Map<String,Object> map) throws DataAccessException{
		
		try {
			//查询自动凭证主表
			Map<String,Object> mainMap=new HashMap<String,Object>();
			mainMap=superVouchMapper.queryAutoVouch(map);
			
			mainMap.put("busi_no", map.get("busi_no"));
			mainMap.put("busi_log_table", map.get("busi_log_table"));
			mainMap.put("acc_year", mainMap.get("vouch_date").toString().substring(0, 4));
			
			map.put("acc_year", mainMap.get("acc_year"));
			//查询自动凭证明细表
			List<Map<String,Object>> detailList=new ArrayList<Map<String,Object>>();
			
			if(map.get("is_budg")!=null && map.get("is_budg").toString().equals("1")){
				//分栏式
				map.put("order_by", " a.vouch_row,s.kind_code ");
			}else{
				//分屏式
				map.put("order_by", " s.kind_code,a.vouch_row ");
			}
			detailList=superVouchMapper.queryAutoVouchDetail(map);
			
			//辅助核算页面显示现金流量
			int p004=Integer.parseInt(MyConfig.getSysPara("004"));
			//银行科目默认弹出辅助核算窗口
			int p032=Integer.parseInt(MyConfig.getSysPara("032"));
			boolean isShowCheck;
			
			List<Map<String,Object>> checkList=null;
			List<Map<String,Object>> cashList=null;
			Map<String,List<Map<String,Object>>> checkMap=new HashMap<String,List<Map<String,Object>>>();
			Map<String,List<Map<String,Object>>> cashMap=new HashMap<String,List<Map<String,Object>>>();	
			
			if(detailList!=null && detailList.size()>0){
				for(Map<String,Object> detail:detailList){
					
					detail.put("did", detail.get("vouch_row"));
					if(detail.get("subj_code")==null){
						detail.put("kind_code", "01");
						continue;
					}
					
					isShowCheck=false;
					//是否票据号核销
					int isBill=Integer.parseInt(detail.get("is_bill").toString());
					map.put("vouch_row", detail.get("vouch_row"));
					map.put("did", detail.get("vouch_row"));
					
					//银行科目默认弹出辅助核算窗口
					if(detail.get("subj_nature_code").equals("03") && p032==1){
						isBill=1;
					}
					
					map.put("subj_code", detail.get("subj_code"));
					if(detail.get("is_check").toString().equals("1") || isBill==1){
						
						map=getVouchCheckSql(map);
						
						checkList=new ArrayList<Map<String,Object>>();
						if(p004==1){
							//辅助核算页面显示现金流
							checkList=superVouchMapper.queryAutoCheckCash(map);//查询凭证模板辅助核算、现金流量表
							if(checkList==null || checkList.size()==0){
								//没有辅助核算，sql关联不出来，单独取现金流量放在checkMap里面
								checkList=superVouchMapper.queryAutoVouchCash(map);//查询凭证模板现金流量表
							}
							isShowCheck=true;
						}else{
							//辅助核算页面不显示现金流量
							checkList=superVouchMapper.queryAutoCheck(map);//查询凭证模板辅助核算
						}
						
						if(checkList!=null && checkList.size()>0){
							checkMap.put(detail.get("did").toString(), checkList);
						}
						
					}
					
					if(!isShowCheck && detail.get("is_cash").toString().equals("1")){
						cashList=new ArrayList<Map<String,Object>>();
						cashList=superVouchMapper.queryAutoVouchCash(map);//查询凭证模板现金流量表
						if(cashList!=null && cashList.size()>0){
							cashMap.put(detail.get("did").toString(), cashList);
						}
					}
					
				}
			}
			
			mainMap.put("is_money", 1);//是否包含金额
			mainMap.put("is_budg", map.get("is_budg"));
			return getSuperVouchJsonByMain(mainMap,detailList,checkMap,cashMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	/*******************************自動凭证處理********************end*****************************************/
	
	/*****************************模板处理**********************begin************************/
	//取模板页面-查询模板
	public String queryAccTemplateMain(Map<String,Object> map) throws DataAccessException{
		StringBuffer json=new StringBuffer();
		json.append("{\"Rows\":[");
		
		List<Map<String,Object>> tempList=new ArrayList<Map<String,Object>>();
		map.put("p009", MyConfig.getSysPara("009"));//取凭证模板按用户过滤
		tempList=superVouchMapper.queryAccTemplateMain(map);
		if(tempList!=null && tempList.size()>0){
			int rowIndex=0;
			for(Map<String,Object> temp:tempList){
				rowIndex++;
				json.append("{");
				
				//增加id
				json.append("\"id\":\""+rowIndex+"\"");
				json.append(",");
				
				for(Map.Entry<String, Object> entry:temp.entrySet()){
					
					json.append("\""+entry.getKey().toLowerCase()+"\":\""+entry.getValue()+"\",");
					
				}
				
				json.setCharAt(json.length() - 1, '}');
				json.append(",");
			}
			json.setCharAt(json.length() - 1, ']'); 
		}else{
			json.append("]");
		}
			
		json.append(",\"state\":\"true\"}");
		return json.toString();
	}
	
	//保存凭证模板	
	@Override
	public String insertAccVouchTemplate(Map<String, Object> map)throws DataAccessException {
		// TODO Auto-generated method stub
		
		String templateCode=map.get("template_code").toString();
		int state=0;
		map.put("field_table","ACC_TEMPLATE");
		try {
			
			//替换模板
			if(map.get("is_replace").toString().equals("1")){
				
				//修改凭证模板主表
				state=superVouchMapper.updateAccTemplateMain(map);
				if(state>0){
					//修改凭证模板成功，说明是做修改操作
					String tempArry="'"+templateCode.replace(",", "','")+"'";
					
					if(MyConfig.getSysPara("009").equals("1")){
						//凭证模板按用户过滤，是否有写入权限
						String permStr=existsAccTemplatebyWrite(tempArry);
						
						if(permStr!=null && !permStr.equals("")){
							return "{\"error\":\""+permStr+"您没有权限修改.\",\"state\":\"true\"}";
						}
					}
					
					
					map.put("field_key1", "template_code");
					map.put("field_value1",templateCode);
					map.put("field_id","template_code");
					map.put("field_id_value",templateCode);
					int count = sysFunUtilMapper.existsSysCodeNameByUpdate(map);
					if (count>0) {
						return "{\"error\":\"模板编码：[" + map.get("template_code").toString() + "]已使用.\"}";
					}
					
					map.put("field_key1", "template_name");
					map.put("field_value1", map.get("template_name"));
					count = sysFunUtilMapper.existsSysCodeNameByUpdate(map);
					if (count>0) {
						return "{\"error\":\"模板名称：[" + map.get("template_name").toString() + "]已使用.\"}";
					}
					
					map.put("template_code",tempArry);//考慮批量刪除，刪除用的in,所以要拼字符串
					//删除凭证模板现金流量表 
					superVouchMapper.deleteAccVouchTemplateCash(map);
					//删除凭证模板辅助核算表
					superVouchMapper.deleteAccVouchTemplateCheck(map);
					//删除凭证模板明细表
					superVouchMapper.deleteAccVouchTemplateDetail(map);
					
				}
			}
			
			map.put("template_code",templateCode);
			if(state==0){
				
				map.put("field_key1", "template_code");
				map.put("field_value1", map.get("template_code"));
				int count = sysFunUtilMapper.existsSysCodeNameByAdd(map);
				if (count>0) {
					return "{\"error\":\"模板编码：[" + map.get("template_code").toString() + "]已使用.\"}";
				}
				
				map.put("field_key1", "template_name");
				map.put("field_value1", map.get("template_name"));
				count = sysFunUtilMapper.existsSysCodeNameByAdd(map);
				if (count>0) {
					return "{\"error\":\"模板名称：[" + map.get("template_name").toString() + "]已使用.\"}";
				}
				
				//取最大排序号
				map.put("field_sort", "sort_code");
				map.remove("field_key1");
				map.remove("field_value1");
				int sortCode=sysFunUtilMapper.querySysMaxSort(map);
				map.put("sort_code",sortCode);
				
				//保存凭证模板主表
				superVouchMapper.insertAccVouchTemplate(map);
				
				//添加用户数据权限表-凭证模板
				superVouchMapper.insertUserPermbyTemplate(map);
			}
			
			
			
			if(map.get("vouch_id")!=null && !map.get("vouch_id").toString().equals("")){
				//根据凭证ID保存凭证模板明细、辅助核算、现金流量表
				
				//添加凭证模板明细表
				superVouchMapper.insertAccVouchTemplateDetail(map);
				
				//添加凭证模板现金流量表
				superVouchMapper.insertAccVouchTemplateCash(map);
				
				//查询辅助核算列名
				StringBuffer sbf = new StringBuffer();
				List<Map<String, Object>> checkTypeList =superVouchMapper.queryAccCheckTypeColumnCheck(map);
				for (Map<String, Object> accCheckType : checkTypeList) {
					
					if(accCheckType.get("is_sys").toString().equals("1")){
						//系统核算
						sbf.append(","+accCheckType.get("column_check")+"_ID");
						if(accCheckType.get("is_change").toString().equals("1")){
							//有变更
							sbf.append(","+accCheckType.get("column_check")+"_NO");
						}
					}else{
						//非系统核算
						sbf.append(","+accCheckType.get("column_check"));
					}
					
				}
				map.put("column_name", sbf==null?"":sbf.toString());
				
				//添加凭证模板辅助核算表
				superVouchMapper.insertAccVouchTemplateCheck(map);
			
			}else{
				//根据录入的内容保存凭证模板明细、辅助核算、现金流量表
				
				/*********************组装凭证明细表数据**********************************************/
				JSONArray detailjson = JSONObject.parseArray(map.get("detail").toString());
				List<Map<String,Object>> detailList=new ArrayList<Map<String,Object>>();
				List<List<Map<String,Object>>> checkList=new ArrayList<List<Map<String,Object>>>();//凭证的所有辅助核算
				List<Map<String,Object>> cashList=new ArrayList<Map<String,Object>>();
				
				for(int i=0;i<detailjson.size();i++){
					JSONObject detail = JSONObject.parseObject(detailjson.getString(i));
					Map<String,Object> detailMap=new HashMap<String, Object>();
					String detailId=null;
					if(detail.getString("did")!=null && !detail.getString("did").equals("")){
						detailId=detail.getString("did");
					}else{
						detailId=UUIDLong.absStringUUID();
					}
					detailMap.put("vouch_detail_id", detailId);
					detailMap.put("template_code", templateCode);
					detailMap.put("group_id", map.get("group_id"));
					detailMap.put("hos_id", map.get("hos_id"));
					detailMap.put("copy_code", map.get("copy_code"));
					detailMap.put("summary", detail.getString("summary"));
					String subjCode=detail.getString("sid");
					detailMap.put("subj_code", subjCode);
					String detailDebit=detail.getString("debit");
					detailMap.put("debit",detailDebit);
					detailMap.put("credit", detail.getString("credit"));
					detailMap.put("debit_w", 0.00);
					detailMap.put("credit_w", 0.00);
					detailMap.put("vouch_row", detail.getString("vouch_row"));
					detailList.add(detailMap);
							
					
					/*********************组装凭证辅助核算表数据**********************************************/
					List<Map<String,Object>> checkDetailList=new ArrayList<Map<String,Object>>();//分录的辅助核算
					boolean isCheckJson=false;
					if(detail.getString("check")!=null && !detail.getString("check").equals("")){
						
						JSONArray checkjson = JSONObject.parseArray(detail.getString("check"));
						for(int j=0;j<checkjson.size();j++){
							
							JSONObject check = JSONObject.parseObject(checkjson.getString(j));
							Map<String,Object> checkMap=new HashMap<String, Object>();
							checkMap.put("vouch_check_id", UUIDLong.absStringUUID());
							checkMap.put("template_code", templateCode);
							checkMap.put("vouch_detail_id", detailId);
							checkMap.put("group_id", map.get("group_id"));
							checkMap.put("hos_id", map.get("hos_id"));
							checkMap.put("copy_code", map.get("copy_code"));
							checkMap.put("summary", check.getString("summary"));
							checkMap.put("subj_code", subjCode);
							checkMap.put("debit_w", 0.00);
							checkMap.put("credit_w", 0.00);
							checkMap.put("sort_code", j+1);
							String money=check.getString("money")==null?"0.00":check.getString("money").replace(",", "");
							if(!detailDebit.equals("") && Float.parseFloat(detailDebit)!=0){
								checkMap.put("debit", money);
								checkMap.put("credit", 0.00);
							}else{
								checkMap.put("debit", 0.00);
								checkMap.put("credit", money);
							}
							
							//动态拼辅助核算字段，循环遍历键值对
							StringBuilder column=new StringBuilder();//字段
							StringBuilder columnValue=new StringBuilder();//值
							for (Map.Entry<String, Object> entry : check.entrySet()) {
								
									if(entry.getKey().equalsIgnoreCase("occur_date")){
										//发生日期
										isCheckJson=true;
										column.append(entry.getKey()+",");
										columnValue.append("to_date('"+entry.getValue()+"','yyyy-MM-dd'),");
										
									}else if(entry.getKey().equalsIgnoreCase("due_date")){
										//到期日期
										isCheckJson=true;
										column.append(entry.getKey()+",");
										columnValue.append("to_date('"+entry.getValue()+"','yyyy-MM-dd'),");
										
									}else if(entry.getKey().equalsIgnoreCase("paper_type_code")){
										//票据类型
										isCheckJson=true;
										column.append(entry.getKey()+",");
										if(entry.getValue()!=null && !entry.getValue().toString().replace(" ", "").equals("")){
											columnValue.append("'"+entry.getValue().toString().split(" ")[0]+"',");
										}else{
											columnValue.append("'',");
										}
										
									}else if(entry.getKey().equalsIgnoreCase("pay_type_code") || entry.getKey().equalsIgnoreCase("con_no") || entry.getKey().equalsIgnoreCase("check_no") || entry.getKey().toLowerCase().startsWith("zcheck")){
										//结算方式、合同号、票据号、自定义核算
										isCheckJson=true;
										column.append(entry.getKey()+",");
										columnValue.append("'"+entry.getValue()+"',");
										
									}else if(entry.getKey().toLowerCase().startsWith("check")){
										//系统核算
										isCheckJson=true;
										if(entry.getKey().equalsIgnoreCase("check7") || entry.getKey().equalsIgnoreCase("check8")){
											//资金来源、单位核算
											column.append(entry.getKey()+"_ID,");
											columnValue.append("'"+entry.getValue()+"',");
										}else{
											//系统核算check1到check6
											column.append(entry.getKey()+"_ID,");
											column.append(entry.getKey()+"_NO,");
											columnValue.append("'"+entry.getValue().toString().split("@")[0]+"',");
											columnValue.append("'"+entry.getValue().toString().split("@")[1]+"',");
										}
										
									}									
								
							}
							
							checkMap.put("column", (column==null || column.length()==0)?"":column.toString());
							checkMap.put("column_value", (columnValue==null || columnValue.length()==0)?"":columnValue.toString());
							
							if(isCheckJson){
								checkDetailList.add(checkMap);
							}
							
							/*********************组装现金流量标注表数据，辅助核算和现金流量一起保存**********************************************/
							if(check.getString("cash_item_id")!=null && !check.getString("cash_item_id").equals("")){
								Map<String,Object> cashMap=new HashMap<String, Object>();
								String cashId=UUIDLong.absStringUUID();
								cashMap.put("cash_id", cashId);
								cashMap.put("template_code", templateCode);
								cashMap.put("vouch_detail_id", detailId);
								cashMap.put("group_id", map.get("group_id"));
								cashMap.put("hos_id", map.get("hos_id"));
								cashMap.put("copy_code", map.get("copy_code"));
								cashMap.put("cash_item_id", check.getString("cash_item_id"));
								cashMap.put("cash_money", check.getString("money")==null?"0.00":check.getString("money").replace(",", ""));
								cashMap.put("summary", check.getString("summary"));
								cashMap.put("sort_code", j+1);
								cashList.add(cashMap);
							}
						}
						//保存辅助核算表,一条分录批量保存一次，因为不同而分录的表头是不一样的，这样做也是为了降低数据库的长时间操作，所以增加访问次数
						if(checkDetailList!=null && checkDetailList.size()>0)checkList.add(checkDetailList);
						
					}
										
					
					/*********************组装现金流量数据，辅助核算和现金流量分开保存**********************************************/
					if(detail.getString("cash")!=null && !detail.getString("cash").equals("") ){
						JSONArray cashjson = JSONObject.parseArray(detail.getString("cash"));
						for(int j=0;j<cashjson.size();j++){
							JSONObject cash = JSONObject.parseObject(cashjson.getString(j));
							/*********************组装现金流量标注表数据**********************************************/
							if(cash.getString("cash_item_id")!=null && !cash.getString("cash_item_id").equals("")){
								Map<String,Object> cashMap=new HashMap<String, Object>();
								String cashId=UUIDLong.absStringUUID();
								cashMap.put("cash_id", cashId);
								cashMap.put("template_code", templateCode);
								cashMap.put("vouch_detail_id", detailId);
								cashMap.put("group_id", map.get("group_id"));
								cashMap.put("hos_id", map.get("hos_id"));
								cashMap.put("copy_code", map.get("copy_code"));
								cashMap.put("cash_item_id", cash.getString("cash_item_id"));
								cashMap.put("cash_money", cash.getString("money").replace(",", ""));
								cashMap.put("summary", cash.getString("summary"));
								cashMap.put("sort_code", j+1);
								cashList.add(cashMap);
							}
						}
						
					}
				
				}
				
				if(detailList!=null && detailList.size()>0){
					//保存凭证模板明细表
					superVouchMapper.insertAccVouchTemplateDetailBatch(detailList);
					
					if(checkList!=null && checkList.size()>0){
						for(List<Map<String,Object>> lm :checkList){
							if(lm!=null && lm.size()>0){
								String column=lm.get(0).get("column").toString();
								//保存辅助核算
								superVouchMapper.insertAccVouchTemplateCheckBatch(column,lm);
							}
						}
						
					}
					
					//保存现金流量标注表
					if(cashList!=null && cashList.size()>0){
						superVouchMapper.insertAccVouchTemplateCashBatch(cashList);
					}
				}
				
			}
		
			return "{\"state\":\"true\"}";
			
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("保存失败.");

		}
	}
	
	//取模板页面-删除模板
	public String deleteAccVouchTemplate(Map<String,Object> map) throws DataAccessException{
		
		try {
			
			if(MyConfig.getSysPara("009").equals("1")){
				//凭证模板按用户过滤，是否有写入权限
				String permStr=existsAccTemplatebyWrite(map.get("template_code").toString());
				
				if(permStr!=null && !permStr.equals("")){
					return "{\"error\":\""+permStr+"没有数据权限.\",\"state\":\"true\"}";
				}
			}
			
			
			//删除凭证模板现金流量表 
			superVouchMapper.deleteAccVouchTemplateCash(map);
			//删除凭证模板辅助核算表
			superVouchMapper.deleteAccVouchTemplateCheck(map);
			//删除凭证模板明细表
			superVouchMapper.deleteAccVouchTemplateDetail(map);
			//删除用户数据表-凭证模板
			superVouchMapper.deleteUserPermbyTemplate(map);
			//删除凭证模板主表
			superVouchMapper.deleteAccVouchTemplate(map);
			
			return "{\"msg\":\"删除成功。\",\"state\":\"true\"}";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		
	}
	
	
	//取模板页面-修改模板主表
	public String updateAccTemplateMain(Map<String,Object> map) throws DataAccessException{
		
		try {
			
			if(MyConfig.getSysPara("009").equals("1")){
				//凭证模板按用户过滤，是否有写入权限
				String permStr=existsAccTemplatebyWrite("'"+map.get("template_code").toString()+"'");
				
				if(permStr!=null && !permStr.equals("")){
					return "{\"error\":\""+permStr+"没有数据权限.\",\"state\":\"true\"}";
				}
			}
			
			superVouchMapper.updateAccTemplateMain(map);
			return "{\"msg\":\"修改成功。\",\"state\":\"true\"}";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		
	}
	
	//判断凭证模板是否有写入权限
	private String existsAccTemplatebyWrite(String tempCode){
		
		StringBuffer resTemp=new StringBuffer();
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());
		map.put("user_id", SessionManager.getUserId());
		map.put("template_code",tempCode);
		
		List<Map<String,Object>> permList=superVouchMapper.queryAccTemplateByPerm(map);
		if(permList!=null && permList.size()>0){
			for(Map<String,Object> perm:permList){
				if(perm.get("is_write")==null || perm.get("is_write").toString().equals("0")){
					resTemp.append(perm.get("template_code")+"，");
				}
			}
		}
		
		return resTemp==null?"":resTemp.toString();
	}
	
	//取模板页面-置顶
	public String updateAccTemplateMainBySort(Map<String,Object> map) throws DataAccessException{
		
		try {
			superVouchMapper.updateAccTemplateMainBySort(map);
			return "{\"state\":\"true\"}";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		
	}
	
	//取模板
	public String queryAccTemplateVouch(Map<String,Object> map) throws DataAccessException{
		
		try {
			//查询凭证模板主表
			Map<String,Object> mainMap=new HashMap<String,Object>();
			mainMap=superVouchMapper.queryAccTemplateMainByCode(map);
			
			map.put("acc_year", map.get("vouch_date").toString().substring(0, 4));
			//查询凭证模板明细表
			List<Map<String,Object>> detailList=new ArrayList<Map<String,Object>>();
			if(map.get("is_budg")!=null && map.get("is_budg").toString().equals("1")){
				//分栏式
				map.put("order_by", " a.vouch_row,s.kind_code ");
			}else{
				//分屏式
				map.put("order_by", " s.kind_code,a.vouch_row ");
			}
			detailList=superVouchMapper.queryAccTemplateDetail(map);
			
			//辅助核算页面显示现金流量
			int p004=Integer.parseInt(MyConfig.getSysPara("004"));
			//银行科目默认弹出辅助核算窗口
			int p032=Integer.parseInt(MyConfig.getSysPara("032"));
			boolean isShowCheck;
			
			List<Map<String,Object>> checkList=null;
			List<Map<String,Object>> cashList=null;
			Map<String,List<Map<String,Object>>> checkMap=new HashMap<String,List<Map<String,Object>>>();
			Map<String,List<Map<String,Object>>> cashMap=new HashMap<String,List<Map<String,Object>>>();	
			
			if(detailList!=null && detailList.size()>0){
				for(Map<String,Object> detail:detailList){
					
					isShowCheck=false;
					//是否票据号核销
					int isBill=Integer.parseInt(detail.get("is_bill").toString());
					map.put("did", detail.get("did"));
					
					//银行科目默认弹出辅助核算窗口
					if(detail.get("subj_nature_code").equals("03") && p032==1){
						isBill=1;
					}
					
					map.put("subj_code", detail.get("subj_code"));
					if(detail.get("is_check").toString().equals("1") || isBill==1){
						
						map=getVouchCheckSql(map);
						
						checkList=new ArrayList<Map<String,Object>>();
						if(p004==1){
							//辅助核算页面显示现金流
							checkList=superVouchMapper.queryAccTemplateCheckCash(map);//查询凭证模板辅助核算、现金流量表
							if(checkList==null || checkList.size()==0){
								//没有辅助核算，sql关联不出来，单独取现金流量放在checkMap里面
								checkList=superVouchMapper.queryAccTemplateCash(map);//查询凭证模板现金流量表
							}
							isShowCheck=true;
						}else{
							//辅助核算页面不显示现金流量
							checkList=superVouchMapper.queryAccTemplateCheck(map);//查询凭证模板辅助核算
						}
						
						if(checkList!=null && checkList.size()>0){
							checkMap.put(detail.get("did").toString(), checkList);
						}
						
					}
					
					if(!isShowCheck && detail.get("is_cash").toString().equals("1")){
						cashList=new ArrayList<Map<String,Object>>();
						cashList=superVouchMapper.queryAccTemplateCash(map);//查询凭证模板现金流量表
						if(cashList!=null && cashList.size()>0){
							cashMap.put(detail.get("did").toString(), cashList);
						}
					}
					
				}
			}
			
			mainMap.put("vouch_date", map.get("vouch_date"));
			mainMap.put("is_money", map.get("is_money"));//是否包含金额
			mainMap.put("is_budg", map.get("is_budg"));
			return getSuperVouchJsonByMain(mainMap,detailList,checkMap,cashMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	
	//根据科目查询挂的辅助核算，并返回拼装好的sql
	private Map<String,Object> getVouchCheckSql(Map<String,Object> map)throws Exception{
		
		StringBuffer selSql=new StringBuffer();//查询字段列名
		StringBuffer leftSql=new StringBuffer();//关联语句
		List<Map<String,Object>> checkTypeList=superVouchMapper.queryVouchCheckType(map);//根据科目查询挂的辅助核算
		if(checkTypeList!=null && checkTypeList.size()>0){
			
			int i=0;
			for(Map<String,Object> type:checkTypeList){
				
				i++;
				String table=type.get("check_table").toString();
				String checkTable=type.get("check_table").toString()+i;//有多个自定义核算，会重复关联
				String columnCheck=type.get("column_check").toString();
				
				if(type.get("is_sys").toString().equals("1")){
					//系统核算不需要加账套
					leftSql.append(" left join "+table+" "+checkTable+" on "+checkTable+".group_id=a.group_id and "+checkTable+".hos_id=a.hos_id");
					
					if(type.get("is_change").toString().equals("1")){
						//有变更表
						selSql.append(",a."+columnCheck+"_id||'@'||a."+columnCheck+"_no \""+columnCheck+"\"");//id@no
						
						leftSql.append(" and "+checkTable+"."+type.get("column_id")+"=a."+columnCheck+"_id ");//关联业务表数据
						if(MyConfig.getSysPara("03001").toString().equals("0")){
							leftSql.append(" and "+checkTable+".is_stop=0");//最新
						}else{
							leftSql.append(" and a."+columnCheck+"_no = "+checkTable+"."+type.get("column_no"));//历史
						}
						
					}else{
						//没有变更表
						selSql.append(",a."+columnCheck+"_id \""+columnCheck+"\"");//id
						
						leftSql.append(" and "+checkTable+"."+type.get("column_id")+"=a."+columnCheck+"_id");//关联业务表数据
					}
					
				}else{
					//非系统核算需要加账套
					selSql.append(",a."+columnCheck);//id
					
					leftSql.append(" left join "+table+" "+checkTable+" on "+checkTable+".group_id=a.group_id and "+checkTable+".hos_id=a.hos_id and "+checkTable+".copy_code=a.copy_code");//关联业务表数据
					leftSql.append(" and "+checkTable+".check_type_id="+type.get("check_id")+" and "+checkTable+"."+type.get("column_id")+"=a."+columnCheck);//关联业务表数据
				}
			
				selSql.append(","+checkTable+"."+type.get("column_code")+"||' '||"+checkTable+"."+type.get("column_name")+" \""+columnCheck+"_name\"");//code name
			}
		}
		
		map.put("sel_sql",selSql==null?"":selSql.toString());//查询字段列名
		map.put("left_sql",leftSql==null?"":leftSql.toString());//关联语句
		
		return map;
	}
	
	//取模板、自动凭证，根据凭证表结构组装json，包括凭证主表、凭证明细表、辅助核算表、现金流量表
	private String getSuperVouchJsonByMain(Map<String,Object> mainMap,List<Map<String, Object>> detailList,Map<String,List<Map<String,Object>>> checkMap,Map<String,List<Map<String,Object>>> cashMap) throws Exception{
		
		if(mainMap==null || mainMap.size()==0){
			return "{\"error\":\"没有主表数据！\",\"state\":\"false\"}";
		}
		if(mainMap.get("vouch_date")==null || mainMap.get("vouch_date").equals("")){
			return "{\"error\":\"没有凭证日期！\",\"state\":\"false\"}";
		}
		if(mainMap.get("vouch_type_code")==null || mainMap.get("vouch_type_code").equals("")){
			return "{\"error\":\"没有凭证类型！\",\"state\":\"false\"}";
		}
		
		if (mainMap.get("group_id") == null) {
			mainMap.put("group_id", SessionManager.getGroupId());
		}
		if (mainMap.get("hos_id") == null) {
			mainMap.put("hos_id", SessionManager.getHosId());
		}
		if (mainMap.get("copy_code") == null) {
			mainMap.put("copy_code", SessionManager.getCopyCode());
		}
		if(mainMap.get("acc_year")==null || mainMap.get("acc_year").equals("")){
			mainMap.put("acc_year", mainMap.get("vouch_date").toString().substring(0, 4));
		}
		if(mainMap.get("acc_month")==null || mainMap.get("acc_month").equals("")){
			mainMap.put("acc_month", mainMap.get("vouch_date").toString().substring(5, 7));
		}
		if(mainMap.get("att_num")==null || mainMap.get("att_num").equals("")){
			mainMap.put("att_num", 0);
		}
		
		if(detailList==null || detailList.size()==0){
			throw new SysException("没有明细表数据！");
		}
		
		StringBuffer json=new StringBuffer();
		StringBuffer checkJson=new StringBuffer();
		StringBuffer cashJson=new StringBuffer();
		json.append("{");
		
		//组装凭证主表数据
		json.append("\"vouch_id\":\"\",");
		json.append("\"vouch_date\":\""+mainMap.get("vouch_date")+"\",");
		json.append("\"vouch_type_code\":\""+mainMap.get("vouch_type_code")+"\",");
		json.append("\"att_num\":\""+mainMap.get("att_num")+"\",");
		String reMaxVouchNo=queryMaxVouchNo(mainMap);
		json.append("\"vouch_no\":\""+reMaxVouchNo+"\",");
		json.append("\"state\":\"1\",");
		json.append("\"create_user\":\""+SessionManager.getUserId()+"\",");
		json.append("\"create_name\":\""+SessionManager.getUserName()+"\",");
		json.append("\"cash_user\":\"\",");
		json.append("\"cash_name\":\"\",");
		json.append("\"audit_user\":\"\",");
		json.append("\"audit_name\":\"\",");
		json.append("\"acc_user\":\"\",");
		json.append("\"acc_name\":\"\",");
		json.append("\"busi_type_code\":\""+(mainMap.get("busi_type_code")==null?"":mainMap.get("busi_type_code").toString())+"\",");
		json.append("\"auto_id\":\""+(mainMap.get("auto_id")==null?"":mainMap.get("auto_id").toString())+"\",");
		json.append("\"template_code\":\""+(mainMap.get("template_code")==null?"":mainMap.get("template_code").toString())+"\",");
		json.append("\"busi_no\":\""+(mainMap.get("busi_no")==null?"":mainMap.get("busi_no").toString())+"\",");
		json.append("\"busi_log_table\":\""+(mainMap.get("busi_log_table")==null?"":mainMap.get("busi_log_table").toString())+"\",");
		
		
		boolean isMoney=true;
		//不显示金额
		if(mainMap.get("is_money")!=null && mainMap.get("is_money").toString().equals("0")){
			isMoney=false;
		}
		
		
		boolean p038=false;
		if(MyConfig.getSysPara("038")!=null && MyConfig.getSysPara("038").equals("1") ){
			p038=true;
		}
		
		Map<String,String> addMap=new HashMap<String,String>();
		//检查财务和预算会计科目是否能匹配到对应的分录，如果分屏录的凭证，按分栏显示的话会错位，所以按参数038否不允许保留空行显示
		if(mainMap.get("is_budg")!=null && mainMap.get("is_budg").toString().equals("1") && p038){
			boolean isMatch=false;
			for(Map<String, Object> m:detailList){
				if(addMap!=null && addMap.get(m.get("vouch_row").toString())!=null){
					isMatch=true;
					break;
				}
				addMap.put(m.get("vouch_row").toString(),m.get("did").toString());
			}
			p038=isMatch;
		}
		
		String keyStr="";
		String dfKindCode="02";//对方科目类别属性
		addMap=new HashMap<String,String>(); 
		
		//组装凭证明细表数据
		int id=0;//前台页面行号
		json.append("\"Rows\":[");
		for(Map<String, Object> m:detailList){
			
			String did=m.get("did").toString();
			if(mainMap.get("is_budg")!=null && mainMap.get("is_budg").toString().equals("1")){
				//分栏式
				if(addMap==null || addMap.get(m.get("did").toString())==null){
					//判断是否已经拼接
					if(m.get("kind_code").toString().equals("02")){
						//预算会计科目的json串的key以budg_开头
						keyStr="budg_";
						dfKindCode="01";
					}else{
						keyStr="";
						dfKindCode="02";
					}
					id++;
					addMap.put(m.get("did").toString(), m.get("vouch_row").toString());
					json.append("{\""+keyStr+"id\":\""+id+"\",\""+keyStr+"did\":\""+did+"\",");
					for(Map.Entry<String, Object> entry:m.entrySet()){
						if(entry.getKey().equalsIgnoreCase("subj_code") || entry.getKey().equalsIgnoreCase("summary") || entry.getKey().equalsIgnoreCase("debit")
								|| entry.getKey().equalsIgnoreCase("credit") || entry.getKey().equalsIgnoreCase("subj_name")){
							
							if(entry.getKey().equalsIgnoreCase("debit") || entry.getKey().equalsIgnoreCase("credit")){
								//分录金额
								String money="0.00";
								if(isMoney){
									money=entry.getValue().toString();
								}
								
								money=NumberUtil.numberToString(Double.parseDouble(money));
								json.append("\""+keyStr+entry.getKey().toLowerCase()+"\":\""+money+"\",");
								json.append("\""+keyStr+entry.getKey().toLowerCase()+"_name\":\""+money.replace(".", "")+"\",");  //增加借贷方显示金额
								
							}else if(entry.getKey().equalsIgnoreCase("summary")){
								json.append("\""+keyStr+"summary\":\""+entry.getValue().toString().replaceAll("\"","\\\\\"")+"\",");
							}else{
								String keyVale=entry.getValue()==null?"":entry.getValue().toString();
								if(entry.getKey().equalsIgnoreCase("subj_name")){
									//科目显示优化
									keyVale=getSubjNameShow(Integer.parseInt(m.get("subj_level")==null?"0":m.get("subj_level").toString()),entry.getValue().toString());
								}
								
								json.append("\""+keyStr+entry.getKey().toLowerCase()+"\":\""+keyVale+"\",");
								
							}
						}
						
					}
					
					if(keyStr.equals("")){
						//找对方科目，预算会计科目的json串的key以budg_开头
						keyStr="budg_";
					}else{
						keyStr="";
					}
					
					for(Map<String, Object> mBudg:detailList){
						//038凭证制单，分栏式允许保留空行0否1是
						if(mBudg.get("kind_code").toString().equals(dfKindCode) && 
								(
								!p038 || (p038 && m.get("vouch_row").toString().equals(mBudg.get("vouch_row").toString()))
								)
						){
							if(addMap!=null && addMap.get(mBudg.get("did").toString())!=null){
								continue;
							}
							
							id++;
							addMap.put(mBudg.get("did").toString(), mBudg.get("vouch_row").toString());
							json.append("\""+keyStr+"id\":\""+id+"\",\""+keyStr+"did\":\""+mBudg.get("did").toString()+"\",");
							for(Map.Entry<String, Object> entry:mBudg.entrySet()){
								if(entry.getKey().equalsIgnoreCase("subj_code") || entry.getKey().equalsIgnoreCase("summary") || entry.getKey().equalsIgnoreCase("debit")
										|| entry.getKey().equalsIgnoreCase("credit") || entry.getKey().equalsIgnoreCase("subj_name")){
									
									if(entry.getKey().equalsIgnoreCase("debit") || entry.getKey().equalsIgnoreCase("credit")){
										//分录金额
										String money="0.00";
										if(isMoney){
											money=entry.getValue().toString();
										}
										
										money=NumberUtil.numberToString(Double.parseDouble(money));
										json.append("\""+keyStr+entry.getKey().toLowerCase()+"\":\""+money+"\",");
										json.append("\""+keyStr+entry.getKey().toLowerCase()+"_name\":\""+money.replace(".", "")+"\",");  //增加借贷方显示金额
										
									}else if(entry.getKey().equalsIgnoreCase("summary")){
										json.append("\""+keyStr+"summary\":\""+entry.getValue().toString().replaceAll("\"","\\\\\"")+"\",");
									}else{
										
										String keyVale=entry.getValue()==null?"":entry.getValue().toString();
										if(entry.getKey().equalsIgnoreCase("subj_name")){
											//科目显示优化
											keyVale=getSubjNameShow(Integer.parseInt(mBudg.get("subj_level")==null?"0":mBudg.get("subj_level").toString()),entry.getValue().toString());
										}
										
										json.append("\""+keyStr+entry.getKey().toLowerCase()+"\":\""+keyVale+"\",");
										
									}
								}
								
							}
							break;
						}
					}
					
					
					json.setCharAt(json.length() - 1, '}');//替换最后一个逗号
					json.append(",");
				}
				
			}else{
				//分屏式
				id++;
				json.append("{\"id\":\""+id+"\",\"did\":\""+did+"\",");
				for(Map.Entry<String, Object> entry:m.entrySet()){
					if(entry.getKey().equalsIgnoreCase("subj_code") || entry.getKey().equalsIgnoreCase("summary") || entry.getKey().equalsIgnoreCase("debit")
							|| entry.getKey().equalsIgnoreCase("credit") || entry.getKey().equalsIgnoreCase("subj_name")){
						
						if(entry.getKey().equalsIgnoreCase("debit") || entry.getKey().equalsIgnoreCase("credit")){
							//分录金额
							String money="0.00";
							if(isMoney){
								money=entry.getValue().toString();
							}
							
							money=NumberUtil.numberToString(Double.parseDouble(money));
							json.append("\""+entry.getKey().toLowerCase()+"\":\""+money+"\",");
							json.append("\""+entry.getKey().toLowerCase()+"_name\":\""+money.replace(".", "")+"\",");  //增加借贷方显示金额
							
						}else if(entry.getKey().equalsIgnoreCase("summary")){
							json.append("\""+entry.getKey().toLowerCase()+"\":\""+entry.getValue().toString().replaceAll("\"","\\\\\"")+"\",");
						}else{
							
							String keyVale=entry.getValue()==null?"":entry.getValue().toString();
							if(entry.getKey().equalsIgnoreCase("subj_name")){
								//科目显示优化
								keyVale=getSubjNameShow(Integer.parseInt(m.get("subj_level")==null?"0":m.get("subj_level").toString()),entry.getValue().toString());
							}
							
							json.append("\""+entry.getKey().toLowerCase()+"\":\""+keyVale+"\",");
						}
					}
					
				}
				
				json.setCharAt(json.length() - 1, '}');//替换最后一个逗号
				json.append(",");
			}
			
			
			//辅助核算
			if(checkMap!=null && checkMap.size()>0){
				List<Map<String,Object>> checkList=checkMap.get(did);
				if(checkList!=null && checkList.size()>0){
					int checkRow=0;
					
					checkJson.append("\""+did+"\":[");
					for(Map<String,Object> check:checkList){
						
						checkRow++;
						checkJson.append("{\"id\":\""+checkRow+"\",\"cid\":\"\"");
						for(Map.Entry<String, Object> entry:check.entrySet()){
							if(entry.getKey().equalsIgnoreCase("rownum1")){
								continue;
							}
							
							if(entry.getKey().equalsIgnoreCase("money")){
								//辅助核算金额
								String money="0.00";
								if(isMoney){
									money=entry.getValue().toString();
								}
								checkJson.append(",\"money\":\""+money+"\"");
							}else if(entry.getValue()!=null){
								checkJson.append(",\""+entry.getKey().toLowerCase()+"\":\""+entry.getValue().toString().replaceAll("\"","\\\\\"")+"\"");
								
							}else{
								checkJson.append(",\""+entry.getKey().toLowerCase()+"\":\"\"");
							}
							
						}
						checkJson.append("},");
							
					}
					checkJson.setCharAt(checkJson.length()-1, ']');
					checkJson.append(",");
				}
			}	
			
			
			//现金流量标注
			if(cashMap!=null && cashMap.size()>0){
				List<Map<String,Object>> cashList=cashMap.get(did);
				if(cashList!=null && cashList.size()>0){
					
					int checkRow=0;
					cashJson.append("\""+did+"\":[");
					for(Map<String, Object> cash:cashList){
						
						checkRow++;
						cashJson.append("{\"id\":\""+checkRow+"\",\"cid\":\"\"");
						for(Map.Entry<String, Object> entry:cash.entrySet()){
							if(entry.getKey().equalsIgnoreCase("rownum1")){
								continue;
							}
							
							if(entry.getKey().equalsIgnoreCase("money")){
								//现金流量金额
								String money="0.00";
								if(isMoney){
									money=entry.getValue().toString();
								}
								cashJson.append(",\"money\":\""+money+"\"");
							}else{
								cashJson.append(",\""+entry.getKey().toLowerCase()+"\":\""+entry.getValue().toString().replaceAll("\"","\\\\\"")+"\"");
							}
							
						}
						cashJson.append("},");
					}
					cashJson.setCharAt(cashJson.length()-1, ']');
					cashJson.append(",");
				}
				
			}
		}
		
		json.setCharAt(json.length() - 1, ']'); //替换最后一个逗号
		
		
		//组装辅助核算
		if(checkJson!=null && !checkJson.toString().equals("")){
			checkJson.setCharAt(checkJson.length() - 1, ' '); //替换最后一个逗号
			json.append(",\"check\":{"+checkJson+"}");
		}
		
		//组装现金流量标注
		if(cashJson!=null && !cashJson.toString().equals("")){
			cashJson.setCharAt(cashJson.length() - 1, ' '); //替换最后一个逗号
			json.append(",\"cash\":{"+cashJson+"}");
		}
				
		json.append("}");
		//logger.debug(json.toString());
		return json.toString();
	}
	
	
	//凭证加载，根据凭证表结构组装json，包括凭证明细表、辅助核算表、现金流量表
	private String getSuperVouchJson(Map<String, Object> map,List<Map<String, Object>> detailList,Map<String,List<Map<String,Object>>> checkMap,Map<String,List<Map<String,Object>>> cashMap) throws Exception{
		
		StringBuffer json=new StringBuffer();
		StringBuffer checkJson=new StringBuffer();
		StringBuffer cashJson=new StringBuffer();
		
		//组装凭证明细表数据
		int id=0;//前台页面行号
		json.append("\"Rows\":[");
		
		if(detailList!=null && detailList.size()>0){
		
			boolean p038=false;
			//凭证制单，分栏式是否允许保留空行
			if(MyConfig.getSysPara("038")!=null && MyConfig.getSysPara("038").equals("1") ){
				p038=true;
			}
			Map<String,String> addMap=new HashMap<String,String>();
			//检查分录行号是否有一样的来区分凭证是按分屏还是分栏保存的，如果分屏录的凭证，按分栏显示的话会错位，所以按参数038否不允许保留空行显示
			if(map.get("is_budg")!=null && map.get("is_budg").toString().equals("1") && p038){
				boolean isMatch=false;
				for(Map<String, Object> m:detailList){
					if(addMap!=null && addMap.get(m.get("vouch_row").toString())!=null){
						isMatch=true;
						break;
					}
					addMap.put(m.get("vouch_row").toString(),m.get("did").toString());
				}
				p038=isMatch;
			}
			String keyStr="";
			String dfKindCode="02";//对方科目类别属性
			addMap=new HashMap<String,String>(); 
			
			for(Map<String, Object> m:detailList){
				//拼凭证明细json
				
				String did=m.get("did").toString();
				
				if(map.get("is_budg")!=null && map.get("is_budg").toString().equals("1")){
					//分栏式
					
					if(addMap==null || addMap.get(m.get("did").toString())==null){
						//判断是否已经拼接
						if(m.get("kind_code").toString().equals("02")){
							//预算会计科目的json串的key以budg_开头
							keyStr="budg_";
							dfKindCode="01";
						}else{
							keyStr="";
							dfKindCode="02";
						}
						
						id++;
						addMap.put(m.get("did").toString(), m.get("vouch_row").toString());
						json.append("{\""+keyStr+"id\":\""+id+"\",\""+keyStr+"did\":\""+did+"\",");
						for(Map.Entry<String, Object> entry:m.entrySet()){
							if(entry.getKey().equalsIgnoreCase("subj_code") || entry.getKey().equalsIgnoreCase("summary") || entry.getKey().equalsIgnoreCase("debit")
									|| entry.getKey().equalsIgnoreCase("credit") || entry.getKey().equalsIgnoreCase("subj_name")){
								
								if(entry.getKey().equalsIgnoreCase("debit") || entry.getKey().equalsIgnoreCase("credit")){
									//分录金额
									String money=entry.getValue().toString();
									
									money=NumberUtil.numberToString(Double.parseDouble(money));
									json.append("\""+keyStr+entry.getKey().toLowerCase()+"\":\""+money+"\",");
									json.append("\""+keyStr+entry.getKey().toLowerCase()+"_name\":\""+money.replace(".", "")+"\",");  //增加借贷方显示金额
									
								}else if(entry.getKey().equalsIgnoreCase("summary")){
									json.append("\""+keyStr+"summary\":\""+entry.getValue().toString().replaceAll("\"","\\\\\"")+"\",");
								}else{
									String keyVale=entry.getValue().toString();
									if(entry.getKey().equalsIgnoreCase("subj_name")){
										//科目显示优化
										keyVale=getSubjNameShow(Integer.parseInt(m.get("subj_level")==null?"0":m.get("subj_level").toString()),entry.getValue().toString());
									}
									
									json.append("\""+keyStr+entry.getKey().toLowerCase()+"\":\""+keyVale+"\",");
								}
							}
							
						}
						
						if(keyStr.equals("")){
							//找对方科目，预算会计科目的json串的key以budg_开头
							keyStr="budg_";
						}else{
							keyStr="";
						}
						
						for(Map<String, Object> mBudg:detailList){
							//038凭证制单，分栏式允许保留空行0否1是
							if(mBudg.get("kind_code").toString().equals(dfKindCode) && 
									(
									!p038 || (p038 && m.get("vouch_row").toString().equals(mBudg.get("vouch_row").toString()))
									)
							){
								if(addMap!=null && addMap.get(mBudg.get("did").toString())!=null){
									continue;
								}
								
								id++;
								addMap.put(mBudg.get("did").toString(), mBudg.get("vouch_row").toString());
								json.append("\""+keyStr+"id\":\""+id+"\",\""+keyStr+"did\":\""+mBudg.get("did").toString()+"\",");
								for(Map.Entry<String, Object> entry:mBudg.entrySet()){
									if(entry.getKey().equalsIgnoreCase("subj_code") || entry.getKey().equalsIgnoreCase("summary") || entry.getKey().equalsIgnoreCase("debit")
											|| entry.getKey().equalsIgnoreCase("credit") || entry.getKey().equalsIgnoreCase("subj_name")){
										
										if(entry.getKey().equalsIgnoreCase("debit") || entry.getKey().equalsIgnoreCase("credit")){
											//分录金额
											String money=entry.getValue().toString();
											
											money=NumberUtil.numberToString(Double.parseDouble(money));
											json.append("\""+keyStr+entry.getKey().toLowerCase()+"\":\""+money+"\",");
											json.append("\""+keyStr+entry.getKey().toLowerCase()+"_name\":\""+money.replace(".", "")+"\",");  //增加借贷方显示金额
											
										}else if(entry.getKey().equalsIgnoreCase("summary")){
											json.append("\""+keyStr+"summary\":\""+entry.getValue().toString().replaceAll("\"","\\\\\"")+"\",");
										}else{
											String keyVale=entry.getValue().toString();
											if(entry.getKey().equalsIgnoreCase("subj_name")){
												//科目显示优化
												keyVale=getSubjNameShow(Integer.parseInt(mBudg.get("subj_level")==null?"0":mBudg.get("subj_level").toString()),entry.getValue().toString());
											}
											json.append("\""+keyStr+entry.getKey().toLowerCase()+"\":\""+keyVale+"\",");
											
										}
									}
									
								}
								break;
							}
						}
						
						
						json.setCharAt(json.length() - 1, '}');//替换最后一个逗号
						json.append(",");
					}
					
				}else{
					//分屏式
					id++;
					json.append("{\"id\":\""+id+"\",\"did\":\""+did+"\",");
					for(Map.Entry<String, Object> entry:m.entrySet()){
						if(entry.getKey().equalsIgnoreCase("subj_code") || entry.getKey().equalsIgnoreCase("summary") || entry.getKey().equalsIgnoreCase("debit")
								|| entry.getKey().equalsIgnoreCase("credit") || entry.getKey().equalsIgnoreCase("subj_name")){
							
							if(entry.getKey().equalsIgnoreCase("debit") || entry.getKey().equalsIgnoreCase("credit")){
								//分录金额
								String money=entry.getValue().toString();
								
								money=NumberUtil.numberToString(Double.parseDouble(money));
								json.append("\""+entry.getKey().toLowerCase()+"\":\""+money+"\",");
								json.append("\""+entry.getKey().toLowerCase()+"_name\":\""+money.replace(".", "")+"\",");  //增加借贷方显示金额
								
							}else if(entry.getKey().equalsIgnoreCase("summary")){
								json.append("\""+entry.getKey().toLowerCase()+"\":\""+entry.getValue().toString().replaceAll("\"","\\\\\"")+"\",");
							}else{
								String keyVale=entry.getValue().toString();
								if(entry.getKey().equalsIgnoreCase("subj_name")){
									//科目显示优化
									keyVale=getSubjNameShow(Integer.parseInt(m.get("subj_level")==null?"0":m.get("subj_level").toString()),entry.getValue().toString());
								}
								
								json.append("\""+entry.getKey().toLowerCase()+"\":\""+keyVale+"\",");
							}
						}
						
					}
					
					json.setCharAt(json.length() - 1, '}');//替换最后一个逗号
					json.append(",");
				}
				
				//辅助核算
				if(checkMap!=null && checkMap.size()>0){
					List<Map<String,Object>> checkList=checkMap.get(did);
					if(checkList!=null && checkList.size()>0){
						int checkRow=0;
						checkJson.append("\""+did+"\":[");
						for(Map<String,Object> check:checkList){
							
							checkRow++;
							String cid="";
							if(check.get("CID")!=null){
								cid=check.get("CID").toString();
							}else if(check.get("CASH_ID")!=null){
								cid=check.get("CASH_ID").toString();
							}
							checkJson.append("{\"id\":\""+checkRow+"\",\"cid\":\""+cid+"\"");
							for(Map.Entry<String, Object> entry:check.entrySet()){
								if(entry.getKey().equalsIgnoreCase("rownum1") || entry.getKey().equalsIgnoreCase("cid") || entry.getKey().equalsIgnoreCase("cash_id")){
									continue;
								}
															
								if(entry.getKey().equalsIgnoreCase("money")){
									//辅助核算金额
									String money=entry.getValue().toString();
									
									checkJson.append(",\"money\":\""+money+"\"");
								}else if(entry.getValue()!=null){
									checkJson.append(",\""+entry.getKey().toLowerCase()+"\":\""+entry.getValue().toString().replaceAll("\"","\\\\\"")+"\"");
									
								}else{
									checkJson.append(",\""+entry.getKey().toLowerCase()+"\":\"\"");
								}
								
							}
							checkJson.append("},");
								
						}
						checkJson.setCharAt(checkJson.length()-1, ']');
						checkJson.append(",");
					}
				}	
				
				
				//现金流量标注
				if(cashMap!=null && cashMap.size()>0){
					List<Map<String,Object>> cashList=cashMap.get(did);
					if(cashList!=null && cashList.size()>0){
						
						int checkRow=0;
						cashJson.append("\""+did+"\":[");
						for(Map<String, Object> cash:cashList){
							
							checkRow++;
							cashJson.append("{\"id\":\""+checkRow+"\",\"cid\":\""+cash.get("CASH_ID").toString()+"\"");
							for(Map.Entry<String, Object> entry:cash.entrySet()){
								if(entry.getKey().equalsIgnoreCase("rownum1")  || entry.getKey().equalsIgnoreCase("cash_id")){
									continue;
								}
								
								if(entry.getKey().equalsIgnoreCase("money")){
									//现金流量金额
									String money=entry.getValue().toString();
									
									cashJson.append(",\"money\":\""+money+"\"");
								}else{
									cashJson.append(",\""+entry.getKey().toLowerCase()+"\":\""+entry.getValue().toString().replaceAll("\"","&quot;")+"\"");
								}
								
							}
							cashJson.append("},");
						}
						cashJson.setCharAt(cashJson.length()-1, ']');
						cashJson.append(",");
					}
					
				}
			}
			json.setCharAt(json.length() - 1, ']'); //替换最后一个逗号
		}else{
			json.append("]"); 
		}
		
		
		//组装辅助核算
		if(checkJson!=null && !checkJson.toString().equals("")){
			checkJson.setCharAt(checkJson.length() - 1, ' '); //替换最后一个逗号
			json.append(",\"check\":{"+checkJson+"}");
		}
		
		//组装现金流量标注
		if(cashJson!=null && !cashJson.toString().equals("")){
			cashJson.setCharAt(cashJson.length() - 1, ' '); //替换最后一个逗号
			json.append(",\"cash\":{"+cashJson+"}");
		}
				
		//logger.debug(json.toString());
		return json.toString();
	}
	
	//摘要维护页面-查询摘要
	public String queryAccVouchSummary(Map<String,Object> map) throws DataAccessException{
		StringBuffer json=new StringBuffer();
		json.append("{\"Rows\":[");
		
		List<Map<String,Object>> summaryList=new ArrayList<Map<String,Object>>();
		summaryList=superVouchMapper.queryAccVouchSummary(map);
		if(summaryList!=null && summaryList.size()>0){
			int rowIndex=0;
			for(Map<String,Object> summary:summaryList){
				rowIndex++;
				json.append("{");
				
				//增加id
				json.append("\"id\":\""+rowIndex+"\"");
				json.append(",");
				
				for(Map.Entry<String, Object> entry:summary.entrySet()){
					
					json.append("\""+entry.getKey().toLowerCase()+"\":\""+entry.getValue().toString().replaceAll("\"","\\\\\"")+"\",");
					
				}
				
				json.setCharAt(json.length() - 1, '}');
				json.append(",");
			}
			json.setCharAt(json.length() - 1, ']'); 
		}else{
			json.append("]");
		}
			
		json.append(",\"state\":\"true\"}");
		return json.toString();
	}
	
	
	//删除凭证摘要模板
	public String deleteAccVouchSummary(Map<String,Object> map) throws DataAccessException{
		try {
			superVouchMapper.deleteAccVouchSummary(map);
			return "{\"msg\":\"删除成功。\",\"state\":\"true\"}";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	
	//保存凭证摘要模板
	public String saveAccVouchSummary(Map<String,Object> map) throws DataAccessException{
		
		try {
			if(map.get("summary")==null || map.get("summary").equals("")){
				return "{\"error\":\"摘要不能为空\",\"state\":\"false\"}";
			}
			
			String summary=map.get("summary").toString().trim();
			if(summary.length()>100){
				return "{\"error\":\"摘要不能大于100字\",\"state\":\"false\"}";
			}
			map.put("summary", summary);
			
			superVouchMapper.deleteAccVouchSummary(map);
			
			//取最大排序号
			map.put("field_table","ACC_VOUCH_SUMMARY");
			map.put("field_sort", "sort_code");
			map.put("field_key1", "user_id");
			map.put("field_value1", map.get("user_id"));
			int sortCode=sysFunUtilMapper.querySysMaxSort(map);
			map.put("sort_code",sortCode);
			
			map.put("sid", UUIDLong.absStringUUID());
			superVouchMapper.saveAccVouchSummary(map);
			return "{\"msg\":\"保存成功\",\"state\":\"true\"}";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	
	//摘要维护页面-修改摘要
	@Override
	public String updateAccVouchSummaryBySid(Map<String, Object> map)throws DataAccessException {
		// TODO Auto-generated method stub
		
		try {
			if(map.get("summary")==null || map.get("summary").equals("")){
				return "{\"error\":\"摘要不能为空。\",\"state\":\"false\"}";
			}
			
			String summary=map.get("summary").toString().trim();
			if(summary.length()>100){
				return "{\"error\":\"摘要不能大于100字。\",\"state\":\"false\"}";
			}
			map.put("summary", summary);
			
			if(map.get("sid").toString().equals("")){
				//添加
				superVouchMapper.deleteAccVouchSummary(map);
				
				//取最大排序号
				map.put("field_table","ACC_VOUCH_SUMMARY");
				map.put("field_sort", "sort_code");
				map.put("field_key1", "user_id");
				map.put("field_value1", map.get("user_id"));
				int sortCode=sysFunUtilMapper.querySysMaxSort(map);
				map.put("sort_code",sortCode);
				
				map.put("sid", UUIDLong.absStringUUID());
				superVouchMapper.saveAccVouchSummary(map);
			}else{
				//修改
				superVouchMapper.updateAccVouchSummaryBySid(map);
			}
			
			return "{\"state\":\"true\"}";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	//摘要维护页面-置顶
	@Override
	public String updateAccVouchSummaryBySort(Map<String, Object> map)throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			superVouchMapper.updateAccVouchSummaryBySort(map);
			return "{\"state\":\"true\"}";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	
	/*****************************模板处理**********************end************************/
	
	//查询
	@Override
	public String queryFile(Map<String, Object> entityMap)throws DataAccessException {
		// TODO Auto-generated method stub
		List<AccVouchAtt> list = (List<AccVouchAtt>)superVouchMapper.queryFile(entityMap);
		
		return ChdJson.toJson(list);
	}

	//删除附件数据
	@Override
	public String deleteBatch(List<Map<String, Object>> entityMap)throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			superVouchMapper.deleteBatchFile(entityMap);
			
			for(Map<String,Object> map : entityMap){
				String att_path=map.get("att_path").toString();
				String file_name = att_path.substring(att_path.lastIndexOf("/") + 1, att_path.length());
				String path = att_path.substring(0,att_path.lastIndexOf("/"));
				if(!FtpUtil.deleteFile(path, file_name)){
					logger.error("附件丢失："+path);
					//附件没有的情况下，也允许删除数据
					//TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				}
			}
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());
		}	
		
	}

	//添加附件数据
	@Override
	public String addAccFile(Map<String, Object> entityMap,HttpServletRequest request, HttpServletResponse response) throws DataAccessException {
		// TODO Auto-generated method stub
		
		try {
			
			Map<String,Object> acc = superVouchMapper.queryByCodeFile(entityMap);

			if (acc != null) {

				return "{\"error\":\"数据重复,请重新添加.\"}";
			}
			
			int state=superVouchMapper.addFile(entityMap);
			if(state>0){
				String accFileJson = importFile(entityMap,(MultipartFile)entityMap.get("file"),request,response,entityMap.get("filePath").toString(), entityMap.get("newFileName").toString());
				if(accFileJson.equals("error")){
					throw new SysException("上传文件错误");
				}
				return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			}else{
				return "{\"msg\":\"数据添加失败.\",\"state\":\"false\"}";
			}
			
			
		}catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
		
	}
	
	//验证
	@Override
	public String importFile(Map<String, Object> entityMap, MultipartFile uploadFile,HttpServletRequest request, HttpServletResponse response,String filePath,String fileName) throws Exception {
		try {
			
			if(entityMap.get("ord_file_url") != null && !entityMap.get("ord_file_url").equals("")){
				String ord_file_url = entityMap.get("ord_file_url").toString();
				String file_name = ord_file_url.substring(ord_file_url.lastIndexOf("/") + 1, ord_file_url.length());
				String path = ord_file_url.substring(0,ord_file_url.lastIndexOf("/"));
				if(!FtpUtil.deleteFile(path, file_name)){
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return "error";
				}
			}
			
			if (uploadFile != null ) {
				if (!FtpUtil.uploadFile(uploadFile, "", filePath,fileName,request,response)) {
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return "error";
				}
			}
			return "{\"msg\":\"上传成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public String downloadFile(HttpServletResponse response,Map<String, Object> entityMap) {
		// TODO Auto-generated method stub
		try {
			String att_path=entityMap.get("att_path").toString();
			String file_name = att_path.substring(att_path.lastIndexOf("/") + 1, att_path.length());
			String path = att_path.substring(0,att_path.lastIndexOf("/"));
			
			if(!FtpUtil.downloadFile64(response, file_name, path)){
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return "error";
			}
			return "{\"msg\":\"下载成功.\",\"state\":\"true\"}";
		} catch (NoTransactionException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//查询平行记账模板
	@Override
	public String queryAccBudgTpTree(Map<String, Object> map) throws DataAccessException {

		List<Map> subjList = JSONArray.parseArray(map.get("param").toString(),Map.class);
		if(map.get("is_dire").toString().equals("0")){
			if(subjList!=null && subjList.size()>0){
				StringBuilder subjCode=new StringBuilder();
				for(Map m: subjList){
					subjCode.append("'"+m.get("subj_code")+"',");
				}
				subjCode.setCharAt(subjCode.length()-1, ' ');
				map.put("subj_code_str", subjCode.toString());
			}
		}
		List<Map<String, Object>> list = superVouchMapper.queryAccBudgTpTree(map,subjList);
		List<Map<String, Object>> resList=new ArrayList<Map<String, Object>>(); 
		
		/*
		 * 平行记账模板里面的财务会计科目要与凭证录的财务会计科目一致（可以不按顺序）
		 */
		if(subjList!=null && subjList.size()>0 && list!=null && list.size()>0){
			//去掉重复
			for(int i = 0; i < subjList.size() - 1; i++ ){
			   for(int  j  =  subjList.size() - 1; j > i; j--){
		           if(map.get("is_dire").toString().equals("1") && 
		        		   subjList.get(j).get("subj_code").toString().equals(subjList.get(i).get("subj_code").toString()) &&
		        		   subjList.get(j).get("dire").toString().equals(subjList.get(i).get("dire").toString())){
		        	   subjList.remove(j);       
		           }else if(map.get("is_dire").toString().equals("0") && subjList.get(j).get("subj_code").toString().equals(subjList.get(i).get("subj_code").toString())){
		        	   subjList.remove(j);
		           }
			    }        
			}        
			
			String tpCode=null;
			int i=0;
			List<Map<String, Object>> tpList=new ArrayList<Map<String, Object>>(); 
			for (Map<String, Object> tpMap:list) {
				
				i++;
				if(list.size()==i){
					//list的最后一条记录
					tpList.add(tpMap);
				}
				
				if(list.size()==i || (tpCode!=null && !tpCode.equals(tpMap.get("tp_code").toString()))){
					//list的最后一条记录，或者，每个模板的最后一条记录
					boolean isAccurate=isAccBudgTpAccurate(tpList,subjList,map);
					if(isAccurate){
						//模板完全匹配拿出模板数据，主表数据都是一致的，取第一条即可
						resList.add(tpList.get(0));
					}
					tpList=new ArrayList<Map<String, Object>>();
				}
				
				if(list.size()!=i){
					tpList.add(tpMap);
				}
				tpCode=tpMap.get("tp_code").toString();
			}
		}
		
		StringBuilder json=new StringBuilder();
		json.append("{Rows:[");
		
		if(resList!=null && resList.size()>0){
			for (Map<String, Object> m:resList) {
				json.append("{");
				json.append("\"id\":\""+m.get("tp_code")+"\",");
				json.append("\"pId\":0,");
				json.append("\"title\":\""+m.get("user_name")+"\",");
				json.append("\"tp_note\":\""+(m.get("note")==null?"":m.get("note"))+"\",");
				json.append("\"name\":\""+m.get("tp_name")+"\"");
				json.append("},");
			}
			json.setCharAt(json.length()-1, ']');
		}else{
			json.append("]");
		}
		
		json.append("}");
		return json.toString();
	}
	
	private boolean isAccBudgTpAccurate(List<Map<String,Object>> tpList,List<Map> subjList,Map<String, Object> map){
		boolean isAccurate=false;
		
		//去掉重复
		for(int i = 0; i < tpList.size() - 1; i++ ){
		   for(int  j  =  tpList.size() - 1; j > i; j--){
	           if(map.get("is_dire").toString().equals("1") && 
	        		   tpList.get(j).get("subj_code").toString().equals(tpList.get(i).get("subj_code").toString()) &&
	        		   tpList.get(j).get("dire").toString().equals(tpList.get(i).get("dire").toString())){
	        	   tpList.remove(j);       
	           }else if(map.get("is_dire").toString().equals("0") && tpList.get(j).get("subj_code").toString().equals(tpList.get(i).get("subj_code").toString())){
	        	   tpList.remove(j);
	           }
		    }        
		}
		
		if(map.get("is_all").toString().equals("0") || subjList.size()>=tpList.size()){
			//不是完全匹配模板内容：只要凭证科目在模板里面即可 or 凭证科目>=模板科目
			for(Map subj: subjList){
				isAccurate=false;
				for(Map<String,Object> tp: tpList){
					if(map.get("is_dire").toString().equals("1") && subj.get("dire").toString().equals(tp.get("dire").toString()) && subj.get("subj_code").toString().equals(tp.get("subj_code").toString())){
						//按方向
						isAccurate=true;
					}else if(map.get("is_dire").toString().equals("0") && subj.get("subj_code").toString().equals(tp.get("subj_code").toString())){
						//不按方向
						isAccurate=true;
					}
				}
				
				if(!isAccurate){
					break;
				}
			}
		}else{
			
			for(Map tp: tpList){
				isAccurate=false;
				for(Map<String,Object> subj: subjList){
					if(map.get("is_dire").toString().equals("1") && subj.get("dire").toString().equals(tp.get("dire").toString()) && subj.get("subj_code").toString().equals(tp.get("subj_code").toString())){
						//按方向
						isAccurate=true;
					}else if(map.get("is_dire").toString().equals("0") && subj.get("subj_code").toString().equals(tp.get("subj_code").toString())){
						//不按方向
						isAccurate=true;
					}
				}
				
				if(!isAccurate){
					break;
				}
			}
		}
		
		return isAccurate;
	}
	
	//根据财务会计科目查询预算会计科目
	@Override
	public String queryBudgSubjByAcc(Map<String, Object> map) throws DataAccessException {
		String subjCode=map.get("subj_code").toString();
		subjCode="'"+subjCode.replace(",", "','")+"'";
		map.put("subj_code", subjCode);
		List<Map<String,Object>> reList=superVouchMapper.queryBudgSubjByAcc(map);
		String reJson="{Total: 0}"; 
		if(reList!=null && reList.size()>0){
			reJson=ChdJson.toJson(reList);
		}
		return reJson;
	}
	
	/**
	 * 科目显示优化，注意科目名称关键字‘-’
	 * 039：科目显示规则：科目大于5级时倒数第2-3级中间以...显示
	 * 如：6级：5级显示...，7级：5、6级显示...依次类推
	 */
	private String getSubjNameShow(int subjLevel,String subjName){
		
		if(MyConfig.getSysPara("039").equals("0")){
			return subjName;
		}
		
		int showLevel=5;
		if(subjLevel<=showLevel){
			return subjName;
		}
		
		String subjNameA[]=subjName.split(" ")[1].split("-");
		String subj_name_n="";
		boolean isDD=false;
		for(int i=0;i<subjNameA.length;i++){
			if(i>showLevel-2 && i<subjLevel-1){
				if(!isDD){
					isDD=true;//只拼一次
					subj_name_n=subj_name_n+"-...";
				}
			}else{
				subj_name_n=subj_name_n+"-"+subjNameA[i];
			}
		}
		subj_name_n=subjName.split(" ")[0]+" "+subj_name_n.substring(1,subj_name_n.length());
		return subj_name_n;
	}
	
	
	//更新科目对照
	@Override
	public String updateBudgSubj(Map<String,Object> map) throws DataAccessException{
		
		try {
			if(map.get("subj_code_str")==null || map.get("subj_code_str").equals("")){
				return "{\"error\":\"科目不能为空\",\"state\":\"false\"}";
			}
			
			superVouchMapper.updateBudgSubj(map);
			
			return "{\"msg\":\"保存成功\",\"state\":\"true\"}";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	
	//差异手工标注
	@Override
	public String updateAccVouchDiffSg(Map<String,Object> mapVo) throws DataAccessException{
		
		try {
		
			String acc_year= mapVo.get("vouch_date").toString().substring(0, 4);
			String acc_month= mapVo.get("vouch_date").toString().substring(5, 7);
			mapVo.put("acc_year", acc_year);
			mapVo.put("acc_month", acc_month);
			mapVo.put("vouch_date", DateUtil.stringToDate(mapVo.get("vouch_date").toString(),"yyyy-MM-dd"));
			//根据vouch_id删除差异标注表 
			superVouchMapper.deleteSuperVouchByDiff(mapVo.get("group_id").toString(),mapVo.get("hos_id").toString(),mapVo.get("copy_code").toString(),mapVo.get("vouch_id").toString());
			
			String reJson=null;
			JSONArray diffjson=null;
			if(mapVo.get("diff")!=null && !"".equals(mapVo.get("diff").toString()) && !"[]".equals(mapVo.get("diff").toString()) && !"{}".equals(mapVo.get("diff").toString())){
				diffjson = JSONObject.parseArray(mapVo.get("diff").toString());
			}
			
			if(diffjson!=null && diffjson.size()>0){
				//手工标注
				List<Map<String,Object>> diffList=new ArrayList<Map<String,Object>>();
				for(int i=0;i<diffjson.size();i++){
					JSONObject detail = JSONObject.parseObject(diffjson.getString(i));
					Map<String,Object> diffMap=new HashMap<String, Object>();
					diffMap.put("diff_id", i+1);
					diffMap.put("summary", detail.getString("summary"));
					diffMap.put("diff_item_code", detail.getString("diff_item_code"));
					diffMap.put("diff_money", detail.getString("money").replace(",", ""));
					diffList.add(diffMap);
				}
				
			
				int reDiff=superVouchMapper.saveSuperVouchByDiff(mapVo,diffList);
				if(reDiff>0){
					
					//判断差异标注
					Date createDate=DateUtil.getCurrenDate();
					mapVo.put("create_date",createDate);
					superVouchMapper.updateAccVouchDiffNote(mapVo);
					
					List<String> validateList=superVouchMapper.queryAccVouchDiffAutoTemp(mapVo);
					if(validateList!=null && validateList.size()>0){
						throw new SysException("差异标注失败：<br/>"+validateList.toString()+"");
					}else{
						mapVo.put("sign_flag", 1);
						superVouchMapper.updateSuperVouchBySignFlag(mapVo);
					}
					
				}
				
			}
			
			reJson="{\"msg\":\"保存成功。\",\"state\":\"true\"}";
			return reJson;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	
	//差异标注
	@Override
	public String updateAccVouchDiffAuto(Map<String,Object> map) throws DataAccessException{
		
		try {
			map.put("error_level", 1);
			Date createDate=DateUtil.getCurrenDate();
			map.put("create_date",createDate);
			superVouchMapper.updateAccVouchDiffAuto(map);
			
			List<Map<String,Object>> reList=new ArrayList<Map<String,Object>>();
			int Total=0;
			reList=superVouchMapper.queryAccVouchDiffAutoTempAll(map);
			StringBuilder reJson=new StringBuilder();
			reJson.append("{\"Rows\":[");
			
			if(reList!=null && reList.size()>0){
				for(Map<String,Object> m:reList){
					reJson.append("{\"vouch_id\":\""+m.get("vouch_id")+"\",");
					reJson.append("\"note\":\""+m.get("note")+"\"},");
				}
				reJson.setCharAt(reJson.length()-1, ']');
				Total=reList.size();
			}else{
				reJson.append("]");
			}
			
			reJson.append(",\"Total\":"+Total+",\"state\":\"true\"}");
			return reJson.toString();
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	
	
	//辅助核算页面，打印辅助核算
	@Override
	public List<Map<String,Object>> queryCheckPrintByDid(Map<String,Object> map) throws DataAccessException{
		
		if(map.get("data")==null || "".equals(map.get("data").toString())){
			return null;
		}
		JSONArray data = JSONObject.parseArray(map.get("data").toString());
		if(data==null || data.size()==0){
			return null;
		}
		
		List<Map<String,Object>> checkList=new ArrayList<Map<String,Object>>();
		Map<String,Object> checkMap=null;
		for(int i=0;i<data.size();i++){
			checkMap=new HashMap<String,Object>();
			JSONObject check = JSONObject.parseObject(data.getString(i));
			for (Map.Entry<String, Object> entry : check.entrySet()) {
				checkMap.put(entry.getKey(), entry.getValue());
			}
			checkList.add(checkMap);
		}
		
		return checkList;
	}
}

