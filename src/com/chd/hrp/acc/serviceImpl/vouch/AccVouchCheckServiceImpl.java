/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.serviceImpl.vouch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.StringTool;
import com.chd.base.util.UUIDLong;
import com.chd.hrp.acc.dao.vouch.AccVouchCheckMapper;
import com.chd.hrp.acc.entity.AccVouchCheck;
import com.chd.hrp.acc.entity.PeopleReminder;
import com.chd.hrp.acc.service.vouch.AccVouchCheckService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 凭证辅助核算表<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("accVouchCheckService")
public class AccVouchCheckServiceImpl implements AccVouchCheckService {

	private static Logger logger = Logger.getLogger(AccVouchCheckServiceImpl.class);
	
	@Resource(name = "accVouchCheckMapper")
	private final AccVouchCheckMapper accVouchCheckMapper = null;
   
	/**
	 * @Description 
	 * 凭证辅助核算表<BR> 添加AccVouchCheck
	 * @param AccVouchCheck entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAccVouchCheck(Map<String,Object> entityMap)throws DataAccessException{
		
		AccVouchCheck accVouchCheck = queryAccVouchCheckByCode(entityMap);

		if (accVouchCheck != null) {

			return "{\"error\":\"编码：" + entityMap.get("item_code").toString() + "重复.\"}";

		}
		
		try {
			
			accVouchCheckMapper.addAccVouchCheck(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addAccVouchCheck\"}";

		}

	}
	
	/**
	 * @Description 
	 * 凭证辅助核算表<BR> 批量添加AccVouchCheck
	 * @param  AccVouchCheck entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAccVouchCheck(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			accVouchCheckMapper.addBatchAccVouchCheck(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchAccVouchCheck\"}";

		}
	}
	
	/**
	 * @Description 
	 * 凭证辅助核算表<BR> 查询AccVouchCheck分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAccVouchCheck(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		if (sysPage.getTotal()==-1){
			List<AccVouchCheck> list = accVouchCheckMapper.queryAccVouchCheck(entityMap);
			return ChdJson.toJson(list);
		}else{
			sysPage = (SysPage) entityMap.get("sysPage");
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<AccVouchCheck> list = accVouchCheckMapper.queryAccVouchCheck(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, sysPage.getTotal());
		}
	}

	/**
	 * @Description 
	 * 凭证辅助核算表<BR> 查询AccVouchCheckByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AccVouchCheck queryAccVouchCheckByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return accVouchCheckMapper.queryAccVouchCheckByCode(entityMap);
		
	}
	/**
	 * 看是否结账
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String getIsAccFlag(Map<String,Object> entityMap)throws DataAccessException{
		
		AccVouchCheck accVouchCheck = accVouchCheckMapper.getIsAccFlag(entityMap);
		String message="";
		if(accVouchCheck == null){
			return "{\"state\":\"true\"}";
		}else{
			return "{\"state\":\"\"}";
		}
		//return accVouchCheck; 
	}
	/**
	 * @Description 
	 * 凭证辅助核算表<BR> 批量删除AccVouchCheck
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAccVouchCheck(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			String err = "";
			
			for( Map<String,Object> item : entityList){
				
				Long map = accVouchCheckMapper.queryAccVouchVeri(item);
				
				if(map >0){
					return "{\"error\":\"已核销,删除失败\"}"  ;
					}
				
				}
				int state = accVouchCheckMapper.deleteBatchAccVouchCheck(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccVouchCheck\"}";
		}

		
		
	}
	
	/**
	 * @Description 
	 * 凭证辅助核算表<BR> 删除AccVouchCheck
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteAccVouchCheck(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				accVouchCheckMapper.deleteAccVouchCheck(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteAccVouchCheck\"}";

		}
    }
	
	/**
	 * @Description 
	 * 凭证辅助核算表<BR> 更新AccVouchCheck
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAccVouchCheck(Map<String,Object> entityMap)throws DataAccessException{

		try {

			accVouchCheckMapper.updateAccVouchCheck(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccVouchCheck\"}";

		}
	}
	
	/**
	 * @Description 
	 * 凭证辅助核算表<BR> 批量更新AccVouchCheck
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAccVouchCheck(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			accVouchCheckMapper.updateBatchAccVouchCheck(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccVouchCheck\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 凭证辅助核算表<BR> 导入AccVouchCheck
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importAccVouchCheck(Map<String,Object> entityMap)throws DataAccessException{

		try {

			accVouchCheckMapper.deleteAccVouchCheck(entityMap);
			
			accVouchCheckMapper.addBatchAccVouchCheckImport(entityMap);

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}

	@Override
	public boolean queryAccVouchCheckBySbujCode(Map<String, Object> entityMap)
			throws DataAccessException {
		List<AccVouchCheck> list = accVouchCheckMapper.queryAccVouchCheckBySubjCode(entityMap);
		if(list.size() == 0 || list == null){
			return true;
		}
		return false;
	}
	/**
	 * 应收账龄分析
	 */
	@Override
	public String queryReceivableAccount(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		if (sysPage.getTotal()==-1){
			List<AccVouchCheck> list = accVouchCheckMapper.queryAccVouchCheck(entityMap);
			return ChdJson.toJson(list);
		}else{
			sysPage = (SysPage) entityMap.get("sysPage");
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<AccVouchCheck> list = accVouchCheckMapper.queryReceivableAccount(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, sysPage.getTotal());
		}
	}

	@Override
	public List<PeopleReminder> queryVeriVouchCheck(
			Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int queryAccVouchCheckNextval(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return accVouchCheckMapper.queryAccVouchCheckNextval(entityMap);
	}
	
	/**
	 * @Description 
	 * 凭证辅助核算表<BR> 查询 银行日记账
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	 * Time 2016-06-06
	*/
	@Override
	public String queryAccBankJournal(Map<String, Object> entityMap) throws DataAccessException {
		
		/*if("".equals(entityMap.get("subj_code")) || entityMap.get("subj_code") == null){
			
			return "{\"error\":\"现金科目为必填项\"}";

		}*/
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		List<AccVouchCheck> list = accVouchCheckMapper.queryAccBankJournal(entityMap, rowBounds);
		
		PageInfo page = new PageInfo(list);
		
		return ChdJson.toJson(list, page.getTotal());
	}

	
	@Override
	public String addAccVouchCheckByNostro(Map<String, Object> entityMap)
			throws DataAccessException {
		
		StringBuffer sql= new StringBuffer();
		
		StringBuffer sqlValue= new StringBuffer();
		
		StringBuffer sqls= new StringBuffer();
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		List<Map<String,Object>> list_leder_add = new ArrayList<Map<String,Object>>();
		
		try {
			
			String [] data = entityMap.get("check_data").toString().split(",");
			
			String [] column = entityMap.get("para").toString().split(";");
			
			for(int i=0;i<data.length;i++){
				
				sqlValue.setLength(0);
					
			    sql.setLength(0);
			
				String [] res = data[i].split("@");
				
				Map<String, Object> mapDetailVo = new HashMap<String, Object>();
				
				mapDetailVo.put("group_id",entityMap.get("group_id").toString());
				
				mapDetailVo.put("hos_id",entityMap.get("hos_id").toString());
				
				mapDetailVo.put("copy_code",entityMap.get("copy_code").toString());
				
				mapDetailVo.put("acc_year",entityMap.get("acc_year").toString());
				
				mapDetailVo.put("subj_code",entityMap.get("subj_code").toString());
				
				//mapDetailVo.put("subj_id",entityMap.get("subj_id").toString());
				sql.append("bal_amt, ");
				sqlValue.append("0, ");

				if(!"".equals(entityMap.get("para").toString())){
					
					for (int j = 0; j < column.length; j++) {
						
						if("check7".equals(column[j])||"check8".equals(column[j])){
							
							if(!"".equals(res[j+1])){
								
								sql.append(column[j]+"_id"+",");
								
								if("check7".equals(column[j])){
									
									sqlValue.append(res[j+1].toString().split("\\.")+",");
								
								}else{
									
									sqlValue.append(res[j+1].toString()+",");
								
								}
							
							}
							
						}else if(column[j].indexOf("zcheck")!=-1){
				            
								if(!"".equals(res[j+1].toString())){
								
									sql.append(column[j]+",");
									
									sqlValue.append(res[j+1].toString()+",");
								
								}
							
						}else{
							
							if(!"".equals(res[j].toString())){
								
								sqlValue.append(res[j+1].toString().split("\\.")[0]+",");
								
								sqlValue.append(res[j+1].toString().split("\\.")[1]+",");
								
								sql.append(column[j]+"_id"+",");
								
								sql.append(column[j]+"_no"+",");
								
							}
							
						}
						
					}
					
				}
				
				mapDetailVo.put("vouch_id", ""); 
				
	            mapDetailVo.put("vouch_detail_id", "");
	            
	            mapDetailVo.put("vouch_check_ids", UUIDLong.absStringUUID());
				
				if(StringTool.isNotBlank(res[0])){	
					
					mapDetailVo.put("summary",  res[0]);
					
				}else{
					
					mapDetailVo.put("summary",  "");
				}
				
				if("0".equals(entityMap.get("subj_dire"))){						
						
					mapDetailVo.put("debit", res[9]);
						
					mapDetailVo.put("credit",0);
				
				}else{
						
					mapDetailVo.put("debit",0);
						
					mapDetailVo.put("credit",res[9]);
				}

				if(res[7] != null &&  !"undefined".equals(res[7]) && !"".equals(res[7])){	
					
					mapDetailVo.put("con_no",res[7]);
					
				}else{
					
					mapDetailVo.put("con_no","");
					
				}
				
				if(res[8] != null &&  !"undefined".equals(res[8]) && !"".equals(res[8])){	
					
					mapDetailVo.put("business_no",res[8]);
					
				}else{
					
					mapDetailVo.put("business_no","");
					
				}
				
				if(res[5] != null &&  !"undefined".equals(res[5]) && !"".equals(res[5])){	
					
					mapDetailVo.put("occur_date",DateUtil.jsDateToString(res[5], "yyyy-MM-dd"));
					
				}else{
					
					mapDetailVo.put("occur_date","");
					
				}
				  
				if( res[6].toString() != null &&  !"undefined".equals(res[6].toString()) && !"".equals(res[6].toString()) && res[6].trim().length()>0 ){	
					
					mapDetailVo.put("due_date",DateUtil.jsDateToString(res[6], "yyyy-MM-dd"));
					
				}else{
					
					mapDetailVo.put("due_date","");
					
				} 
 			
				entityMap.put("sql", sql.substring(0, sql.length()));
				
				mapDetailVo.put("sqlValue", sqlValue.toString().substring(0, sqlValue.length()));
				
				list_leder_add.add(mapDetailVo);
				
			}
			System.out.println(list_leder_add);
			
			entityMap.put("itemList", list_leder_add);
			
			accVouchCheckMapper.deleteAccVouchCheckByNostro(entityMap);
			
			accVouchCheckMapper.addBatchAccVouchCheckImport(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addAccVouchCheckByNostro\"}";

		}
		
	}
	
	@Override
	public List<Map<String, Object>> queryAccBankJournalPrint(Map<String, Object> entityMap) throws DataAccessException {
		
		List<Map<String, Object>> list = accVouchCheckMapper.queryAccBankJournalPrint(entityMap);
		
		return list;
	}
	
}
