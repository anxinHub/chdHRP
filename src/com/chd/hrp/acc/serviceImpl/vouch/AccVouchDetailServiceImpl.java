/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.serviceImpl.vouch;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.UUIDLong;
import com.chd.hrp.acc.dao.commonbuilder.AccCheckTypeMapper;
import com.chd.hrp.acc.dao.vouch.AccVouchCheckMapper;
import com.chd.hrp.acc.dao.vouch.AccVouchDetailMapper;
import com.chd.hrp.acc.dao.vouch.AccVouchMapper;
import com.chd.hrp.acc.dao.vouch.SuperVouchMapper;
import com.chd.hrp.acc.entity.AccCheckType;
import com.chd.hrp.acc.entity.AccVouch;
import com.chd.hrp.acc.entity.AccVouchCheck;
import com.chd.hrp.acc.entity.AccVouchDetail;
import com.chd.hrp.acc.service.vouch.AccVouchDetailService;
import com.chd.hrp.acc.service.vouch.SuperVouchService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 凭证明细表<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("accVouchDetailService")
public class AccVouchDetailServiceImpl implements AccVouchDetailService {

	private static Logger logger = Logger.getLogger(AccVouchDetailServiceImpl.class);
	
	@Resource(name = "accVouchDetailMapper")
	private final AccVouchDetailMapper accVouchDetailMapper = null;
	
	@Resource(name = "accVouchCheckMapper")
	private final AccVouchCheckMapper accVouchCheckMapper = null;
	
	@Resource(name = "accVouchMapper")
	private final AccVouchMapper accVouchMapper = null;
	
	@Resource(name = "accCheckTypeMapper")
	private final AccCheckTypeMapper accCheckTypeMapper = null;
	
	@Resource(name = "superVouchService")
	private final SuperVouchService superVouchService = null;
	
	@Resource(name = "superVouchMapper")
	private final SuperVouchMapper superVouchMapper = null;
	
	/**
	 * @Description 
	 * 凭证明细表<BR> 添加AccVouchDetail
	 * @param AccVouchDetail entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAccVouchDetail(Map<String,Object> entityMap)throws DataAccessException{
		
		AccVouchDetail accVouchDetail = queryAccVouchDetailByCode(entityMap);

		if (accVouchDetail != null) {

			return "{\"error\":\"编码：" + entityMap.get("item_code").toString() + "重复.\"}";

		}
		
		try {
			
			accVouchDetailMapper.addAccVouchDetail(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addAccVouchDetail\"}";

		}

	}

	
	/**
	 * @Description 
	 * 凭证明细表<BR> 批量添加AccVouchDetail
	 * @param  AccVouchDetail entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAccVouchDetail(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			accVouchDetailMapper.addBatchAccVouchDetail(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchAccVouchDetail\"}";

		}
	}
	
	/**
	 * @Description 
	 * 凭证明细表<BR> 查询AccVouchDetail分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAccVouchDetail(Map<String,Object> entityMap) throws DataAccessException{
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if(sysPage.getTotal()!=-1)
		{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<AccVouchDetail> list = accVouchDetailMapper.queryAccVouchDetail(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}else{
			return ChdJson.toJson(accVouchDetailMapper.queryAccVouchDetail(entityMap));
		}
	}

	
	/**
	 * @Description 
	 * 凭证明细表<BR> 查询AccVouchDetailByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AccVouchDetail queryAccVouchDetailByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return accVouchDetailMapper.queryAccVouchDetailByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 凭证明细表<BR> 批量删除AccVouchDetail
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAccVouchDetail(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = accVouchDetailMapper.deleteBatchAccVouchDetail(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccVouchDetail\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 凭证明细表<BR> 删除AccVouchDetail
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteAccVouchDetail(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				accVouchDetailMapper.deleteAccVouchDetail(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteAccVouchDetail\"}";

		}
    }
	
	/**
	 * @Description 
	 * 凭证明细表<BR> 更新AccVouchDetail
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAccVouchDetail(Map<String,Object> entityMap)throws DataAccessException{

		try {

			accVouchDetailMapper.updateAccVouchDetail(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccVouchDetail\"}";

		}
	}
	
	/**
	 * @Description 
	 * 凭证明细表<BR> 批量更新AccVouchDetail
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAccVouchDetail(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			accVouchDetailMapper.updateBatchAccVouchDetail(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccVouchDetail\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 凭证明细表<BR> 导入AccVouchDetail
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importAccVouchDetail(Map<String,Object> entityMap)throws DataAccessException{

		try {

			

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}

	@Override
	public boolean queryAccVouchDetailBySubjCode(Map<String, Object> entityMap)
			throws DataAccessException {
		List<AccVouchDetail> list = accVouchDetailMapper.queryAccVouchDetailBySubjCode(entityMap);
		if(list.size() == 0 || list == null){
			return true;
		}
		return false;
	}


	@Override
	public String queryAccVouchSummary(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String queryAccVouchCashItem(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String queryAccVouchPayType(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String queryAccVouchSubj(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String addAccVouchSummary(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String queryAccVouchCheck(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @Description 
	 * 查询序列
	*/
	@Override
	public int queryAccVouchDetailNextval(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return accVouchDetailMapper.queryAccVouchDetailNextval(entityMap);
	}
	
	@Override
	public String updateBatchAccVouchDetailOfMoney(Map<String, Object> entityMap)
			throws DataAccessException {
		
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		
		List<Map<String, Object>> addList = new ArrayList<Map<String, Object>>();
		
		List<Map<String, Object>> cashVouchList = new ArrayList<Map<String, Object>>();
		
		StringBuffer sbf = new StringBuffer();
		
		try {
			
			for (String id : entityMap.get("vouch_id").toString().split(",")) {
				
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
				String [] res = id.split("@");
				
				mapVo.put("vouch_id", res[0]);// 实际实体类变量
				
				mapVo.put("acc_year", res[1]);
				
				mapVo.put("acc_month", res[2]);
				
				mapVo.put("vouch_type_code", res[3]);
				
				mapVo.put("group_id", SessionManager.getGroupId());
				
				mapVo.put("hos_id", SessionManager.getHosId());
				
				mapVo.put("copy_code", SessionManager.getCopyCode());
				
				mapVo.put("new_vouch_id", UUIDLong.absStringUUID());
				
				Date createDate=DateUtil.getCurrenDate();
				
				mapVo.put("create_date",createDate);
				
				String maxVouchNo=superVouchService.queryMaxVouchNo(mapVo);
				
				if(maxVouchNo.indexOf("error")!=-1){
					
					return maxVouchNo;
					
				}
				
				mapVo.put("new_vouch_no", maxVouchNo);
				
				accVouchMapper.addBatchAccVouchOfRed(mapVo);
				
				List<AccVouch> vouchDetailList= accVouchMapper.queryAccVouchDetailCountByVouchId(mapVo);
				
				for (AccVouch accVouch : vouchDetailList) {
					
					Map<String, Object> map = new HashMap<String, Object>();
					
					map.put("vouch_detail_id", accVouch.getVouch_detail_id());
					
					map.put("new_vouch_detail_id", UUIDLong.absStringUUID());
					
					map.put("vouch_id", res[0]);
					
					map.put("acc_year", res[1]);
					
					map.put("acc_month", res[2]);
					
					map.put("group_id", SessionManager.getGroupId());
					
					map.put("hos_id", SessionManager.getHosId());
					
					map.put("copy_code", SessionManager.getCopyCode());
					
					map.put("new_vouch_id",mapVo.get("new_vouch_id"));
					
					map.put("create_date",createDate);
					
					listVo.add(map);
					
					List<AccCheckType> checkTypeList =accCheckTypeMapper.queryAccCheckType(entityMap);
					
					sbf.setLength(0);
					
					for (AccCheckType accCheckType : checkTypeList) {
						
						if(accCheckType.getColumn_check().toUpperCase().indexOf("ZCHECK")>-1){
							
							sbf.append(","+accCheckType.getColumn_check());
							
						}else if("CHECK7".equals(accCheckType.getColumn_check().toUpperCase())||"CHECK8".equals(accCheckType.getColumn_check().toUpperCase())){
							
							sbf.append(","+accCheckType.getColumn_check()+"_ID");
							
						}else{
							
							sbf.append(","+accCheckType.getColumn_check()+"_ID");
							
							sbf.append(","+accCheckType.getColumn_check()+"_NO");
							
						}
						
					}
					
					List<AccVouchCheck> checkList = accVouchCheckMapper.queryAccVouchCheckCountByVouchId(map);
					
					for (AccVouchCheck accVouchCheck : checkList) {
						
						Map<String, Object> checkMap = new HashMap<String, Object>();
						
						checkMap.put("vouch_detail_id", accVouch.getVouch_detail_id());
						
						checkMap.put("new_vouch_detail_id", map.get("new_vouch_detail_id"));
						
						checkMap.put("vouch_check_id", accVouchCheck.getVouch_check_id());
						
						checkMap.put("new_vouch_check_id", UUIDLong.absStringUUID());
						
						checkMap.put("vouch_id", res[0]);
						
						checkMap.put("acc_year", res[1]);
						
						checkMap.put("acc_month", res[2]);
						
						checkMap.put("group_id", SessionManager.getGroupId());
						
						checkMap.put("hos_id", SessionManager.getHosId());
						
						checkMap.put("copy_code", SessionManager.getCopyCode());
						
						checkMap.put("new_vouch_id",mapVo.get("new_vouch_id"));
						
						//checkMap.put("create_date",createDate);
						
						checkMap.put("column_name", sbf);
						
						addList.add(checkMap);
						
					}
					
					List<AccVouch> cashList = accVouchMapper.queryAccVouchCashByVouchDetailId(map);
					
					for (AccVouch cash : cashList) {
						
						Map<String, Object> cashMap = new HashMap<String, Object>();
						
						cashMap.put("vouch_detail_id", accVouch.getVouch_detail_id());
						//vouch_id 现金流量id，vouch_id为别名
						cashMap.put("cash_id", cash.getVouch_id());
						
						cashMap.put("new_vouch_detail_id", map.get("new_vouch_detail_id"));
						
						cashMap.put("new_cash_id", UUIDLong.absStringUUID());

						cashMap.put("group_id", SessionManager.getGroupId());
						
						cashMap.put("hos_id", SessionManager.getHosId());
						
						cashMap.put("copy_code", SessionManager.getCopyCode());
						
						cashMap.put("create_date",createDate);
						
						cashVouchList.add(cashMap);
						
					}
				}
			
				if(listVo.size()>0){
					
					accVouchDetailMapper.addBatchAccVouchDetailOfMoney(listVo);
					
				}
				
				if(addList.size()>0){
					
					accVouchCheckMapper.addBatchAccVouchCheckOfMoney(addList);
				}
				
				if(cashVouchList.size()>0){
					
					accVouchCheckMapper.addBatchAccCashVouchOfMoney(cashVouchList);
				}
				
			}
			
				return "{\"msg\":\"红字冲销成功.\",\"state\":\"true\"}";
			
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("冲销失败:"+e.getMessage());

		}
	}
	

	@Override
	public String queryAccMaxVouchNo(Map<String, Object> entityMap)
			throws DataAccessException {
		
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
			p001=MyConfig.getSysPara("001").toString();
		}
		
		int newVouchNo=0;
		if(entityMap.get("reMaxVouchNo")!=null && !entityMap.get("reMaxVouchNo").equals("")){
			//传入凭证号，直接取，修改凭证号用到
			reMaxVouchNo=entityMap.get("reMaxVouchNo").toString();
			newVouchNo=Integer.parseInt(reMaxVouchNo);
			
		}else{
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
				newVouchNo=Integer.parseInt(reMaxVouchNo);
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
}
