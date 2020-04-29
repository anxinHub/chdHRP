/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.acc.serviceImpl.vouch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.AccParaMapper;
import com.chd.hrp.acc.dao.commonbuilder.AccSubjMapper;
import com.chd.hrp.acc.dao.vouch.AccVouchCheckMapper;
import com.chd.hrp.acc.dao.vouch.AccVouchDetailMapper;
import com.chd.hrp.acc.dao.vouch.AccVouchMapper;
import com.chd.hrp.acc.entity.AccPara;
import com.chd.hrp.acc.entity.AccSubj;
import com.chd.hrp.acc.entity.AccVouch;
import com.chd.hrp.acc.service.vouch.AccVouchService;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description. 凭证主表<BR>
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("accVouchService")
public class AccVouchServiceImpl implements AccVouchService {

	private static Logger logger = Logger.getLogger(AccVouchServiceImpl.class);

	@Resource(name = "accVouchMapper")
	private final AccVouchMapper accVouchMapper = null;
	
	@Resource(name = "accVouchDetailMapper")
	private final AccVouchDetailMapper accVouchDetailMapper = null;
	
	@Resource(name = "accVouchCheckMapper")
	private final AccVouchCheckMapper accVouchCheckMapper = null;
	
	@Resource(name = "accParaMapper")
	private final AccParaMapper accParaMapper = null;
	
	@Resource(name = "accSubjMapper")
	private final AccSubjMapper accSubjMapper = null;
	
	

	/**
	 * @Description 凭证主表<BR>
	 *              添加AccVouch
	 * @param AccVouch
	 *            entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addAccVouch(Map<String, Object> entityMap) throws DataAccessException {


		try {

			List<Map<String,Object>> list_vouch_detail_batch = (List<Map<String, Object>>) entityMap.get("list_vouch_detail_batch");
			
			/*for(int i =0; i<list_vouch_detail_batch.size();i++){
				
				Map<String,Object> map =  list_vouch_detail_batch.get(i);
				
				for (String key : map.keySet()) {
					
					System.out.println("key = "+ key+" value = " + map.get(key));
					
				}
				
			}*/
			
			List<Map<String,Object>> list_vouch_check_batch = (List<Map<String, Object>>) entityMap.get("list_vouch_check_batch");
			
			accVouchMapper.addAccVouch(entityMap);//保存凭证
			
			accVouchDetailMapper.addBatchAccVouchDetail(list_vouch_detail_batch);//  保存明细
			
			accVouchCheckMapper.addBatchAccVouchCheck(list_vouch_check_batch);//  保存辅助核算

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addAccVouch\"}";

		}

	}

	/**
	 * @Description 凭证主表<BR>
	 *              批量添加AccVouch
	 * @param AccVouch
	 *            entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatchAccVouch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			accVouchMapper.addBatchAccVouch(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchAccVouch\"}";

		}
	}

	/**
	 * @Description 凭证主表<BR>
	 *              查询AccVouch分页
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryAccVouch(Map<String, Object> entityMap) throws DataAccessException {
		
		/*
		 * 允许查询他人凭证功能没实际意义
		 * entityMap.put("para_code","006");
		
		AccPara accPara = accParaMapper.queryAccParaByCode(entityMap);
		
		if("0".equals(accPara.getPara_value())){
			
			entityMap.put("create_user",SessionManager.getUserId());
			
		}*/
		
		try{
			SysPage sysPage = new SysPage();
			
			sysPage = (SysPage)entityMap.get("sysPage");
			
			if(sysPage.getTotal() == -1){
				
				List<Map<String, Object>> list = accVouchMapper.queryAccVouch(entityMap);
				
				return ChdJson.toJson(list);
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<Map<String, Object>> list = accVouchMapper.queryAccVouch(entityMap, rowBounds);
				
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list,page.getTotal());
			}
		}catch(Exception e){
			throw new SysException(e.getMessage(),e);
		}
		
	}
	/**
	 * @Description 凭证主表<BR>
	 *              查询AccVouchByCode
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public AccVouch queryAccVouchByCode(Map<String, Object> entityMap) throws DataAccessException {

		return accVouchMapper.queryAccVouchByCode(entityMap);

	}

	/**
	 * @Description 凭证主表<BR>
	 *              批量删除AccVouch
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchAccVouch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {
			
				Map<String, Object> map = entityList.get(0);
				
				map.put("para_code","005");
				
				
				if("1".equals(MyConfig.getSysPara("005"))){//等于1 允许删除他人凭证 否则不允许删除他人凭证
					
					map.put("para_code","013");
					
					if("1".equals(MyConfig.getSysPara("013"))){//等于1删除中间凭证修改状态为作废;
						
						accVouchMapper.updateBatchAccVouch(entityList);
						
						return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
						
					} else if("2".equals(MyConfig.getSysPara("013"))){//等于2删除中间凭证后面的凭证号要往前移
						
						accVouchCheckMapper.deleteBatchAccVouchCheck(entityList);
						
						accVouchDetailMapper.deleteBatchAccVouchDetail(entityList);
						
						accVouchMapper.deleteBatchAccVouch(entityList);
						
						return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
						
					}else{//返回值等于0 不做任何处理;
						
						accVouchCheckMapper.deleteBatchAccVouchCheck(entityList);
						
						accVouchDetailMapper.deleteBatchAccVouchDetail(entityList);
						
						accVouchMapper.deleteBatchAccVouch(entityList);
						
						return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
						
					}
					
				}else{
					
					map.put("para_code","013");
					
					if("1".equals(MyConfig.getSysPara("013"))){//等于1删除中间凭证修改状态为作废;
						
						accVouchMapper.updateBatchAccVouch(entityList);
						
						return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
						
					} else if("2".equals(MyConfig.getSysPara("013"))){//等于2删除中间凭证后面的凭证号要往前移
						
						accVouchCheckMapper.deleteBatchAccVouchCheck(entityList);
						
						accVouchDetailMapper.deleteBatchAccVouchDetail(entityList);
						
						accVouchMapper.deleteBatchAccVouch(entityList);
						
						return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
						
					}else{//返回值等于0 不做任何处理;
						
						accVouchCheckMapper.deleteBatchAccVouchCheck(entityList);
						
						accVouchDetailMapper.deleteBatchAccVouchDetail(entityList);
						
						accVouchMapper.deleteBatchAccVouch(entityList);
						
						return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
						
					}
					
				}

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccVouch\"}";

		}

	}

	/**
	 * @Description 凭证主表<BR>
	 *              删除AccVouch
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteAccVouch(Map<String, Object> entityMap) throws DataAccessException {

		try {
			
			accVouchMapper.deleteAccVouch(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteAccVouch\"}";

		}
	}

	/**
	 * @Description 凭证主表<BR>
	 *              更新AccVouch
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateAccVouch(Map<String, Object> entityMap) throws DataAccessException {

		try {

			accVouchMapper.updateAccVouch(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccVouch\"}";

		}
	}

	/**
	 * @Description 凭证主表<BR>
	 *              批量更新AccVouch
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatchAccVouch(List<Map<String, Object>> entityList) throws DataAccessException {

		accVouchMapper.updateBatchAccVouch(entityList);
		
		//作废、取消作废，更新票据号状态
		accVouchMapper.updateAccPaperDetailState(entityList);	
		
		if("0".equals(entityList.get(0).get("state"))){
			
			//作废删除差异标注
			accVouchMapper.deleteBatchAccVouchByDiff(entityList);
			
			return "{\"msg\":\"作废成功.\",\"state\":\"true\"}";
				
		}
		
		return "{\"msg\":\"取消作废成功.\",\"state\":\"true\"}";
	}

	/**
	 * @Description 凭证主表<BR>
	 *              导入AccVouch
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String importAccVouch(Map<String, Object> entityMap) throws DataAccessException {

		try {

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}

	@Override
	public String queryAccCashJournal(Map<String, Object> entityMap) throws DataAccessException {
		
		if("".equals(entityMap.get("subj_code")) || entityMap.get("subj_code") == null){
			
			return "{\"error\":\"现金科目为必填项\"}";

		}
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		List<AccVouch> list = accVouchMapper.queryAccCashJournal(entityMap, rowBounds);
		
		PageInfo page = new PageInfo(list);
		
		return ChdJson.toJson(list, page.getTotal());
	}
	
	/**
	 * @Description 
	 * 查询序列
	*/
	@Override
	public int queryAccVouchNextval(Map<String, Object> entityMap) throws DataAccessException {

		return accVouchMapper.queryAccVouchNextval(entityMap);
		
	}
	

	/**
	 * @Description 
	 *  查询 返回科目挂的辅助核算信息 add by alfred    调用AccSubjMapper 中queryAccSubjWithCheckType方法 返回JSP页面辅助核算coloum名称JSON
	*/
	@Override
	public Map<String,String> queryAccSubjWithCheckType(Map<String, Object> entityMap) throws DataAccessException {
		
		List<AccSubj> list =  accSubjMapper.queryAccSubjWithCheckType(entityMap);
		
		StringBuffer sb_columns_check = new StringBuffer();
		
		StringBuffer check_type_temp = new StringBuffer();
		
		Map<String,String> map = new HashMap<String,String>();
		
		if(list.size() > 0){
			
			for( int i= 0;  i < list.size(); i++){

				AccSubj as = list.get(i);
				
				if(StringUtils.isNotEmpty(as.getColumn_id())){
					
					String column_id = as.getColumn_id().toLowerCase() ;
					
					String url = null;
					
					if("dept_id".equals(column_id)){
						
						url = "../../../sys/queryDeptDict.do?isCheck=false";
						
					}else if("emp_id".equals(column_id)){
						
						url = "../../../sys/queryEmp.do?isCheck=false";
						
					}else if("proj_id".equals(column_id)){
						
						url = "../../../sys/queryProjDictDict.do?isCheck=false";
						
					}else if("store_id".equals(column_id)){
						
						url = "../../../sys/queryStoreDictDict.do?isCheck=false";
						
					}else if("cus_id".equals(column_id)){
						
						url = "../../../sys/queryCusDict.do?isCheck=false";
						
					}else if("sup_id".equals(column_id)){
						
						url = "../../../sys/querySupDictDict.do?isCheck=false";
						
					}else if("source_id".equals(column_id)){
						
						url = "../../../sys/querySourceDict.do?isCheck=false";
						
					}
					
					String column_code = as.getColumn_code();
					
					String column_name = as.getColumn_name();
					
					String column_no = as.getColumn_no();
					
					String column_check = as.getColumn_check();
					
					String check_type_name  = as.getCheck_type_name();
					
					sb_columns_check.append("{display : \'"+check_type_name+"\',name : \'"+column_id+"\',valueField: \'"+column_id+"\',textField: \'"+column_name+"\',align : \'left\',isSort : false,");
			        sb_columns_check.append("		editor : {");
			        sb_columns_check.append("			type : \'select\',");
			        sb_columns_check.append("			valueField: \'id\', ");
			        sb_columns_check.append("			textField: \'text\',");
			        sb_columns_check.append("			url : \'"+url+"\',");
			        sb_columns_check.append("			keySupport:true,");
			        sb_columns_check.append("			autocomplete: true,");
			        sb_columns_check.append("			}");
			        sb_columns_check.append("},");

			        check_type_temp.append(column_id+"."+column_check+"@");
			        
				}
	
			}

			String check_type = check_type_temp.toString();
			
			if(check_type.length()>0){
				
				check_type = check_type.substring(0, check_type.length()-1);
				
			}
			
			map.put("columns", sb_columns_check.toString());
			
			map.put("check_type", check_type);

		}else{

	        map.put("check_type", "");
	        
		}
		
		return map;
	}

	@Override
	public String queryAccState(Map<String, Object> entityMap)
			throws DataAccessException {
		
		return ChdJson.toJson(accVouchMapper.queryVouchState(entityMap));
	}

	@Override
	public String queryAccVouchNeaten(Map<String, Object> entityMap)
			throws DataAccessException {
		
	 return ChdJson.toJson(accVouchMapper.queryAccVouchNeaten(entityMap));
	}

	@Override
	public String updateBatchAccVouchLen(Map<String, Object> entityMap)
			throws DataAccessException {
		
		try {

			accVouchMapper.updateBatchAccVouchLen(entityMap);

			return "{\"msg\":\"断号处理成功.\",\"state\":\"true\"}";
			
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"操作失败 数据库异常 请联系管理员! 错误编码  updateBatchAccVouchLen\"}";

		}
		
	}

	@Override
	public String queryAccVouchPayTypeCode(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
        /*  
         * 允许查询他人凭证功能没实际意义
         * entityMap.put("para_code","006");
		
		AccPara accPara = accParaMapper.queryAccParaByCode(entityMap);
		
		if("0".equals(accPara.getPara_value())){
			
			entityMap.put("create_user",SessionManager.getUserId());
			
		}*/
		
		try{
			SysPage sysPage = new SysPage();
			
			sysPage = (SysPage)entityMap.get("sysPage");
			
			if(sysPage.getTotal() == -1){
				
				List<Map<String, Object>> list = accVouchMapper.queryAccVouchPayTypeCode(entityMap);
				
				return ChdJson.toJson(list);
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<Map<String, Object>> list = accVouchMapper.queryAccVouchPayTypeCode(entityMap, rowBounds);
				
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list,page.getTotal());
			}
		}catch(Exception e){
			throw new SysException(e.getMessage(),e);
		}
	}

	@Override
	public String queryAccVouchFlowByNodeId(Map<String, Object> entityMap)
			throws DataAccessException {
		
		int count =accVouchMapper.queryAccVouchFlowByNodeId(entityMap);
		
		if(count>0){
			
			return "{\"result\":\"true\"}";
			
		}
		
		return "{\"result\":\"false\"}";
		
	}

	@Override
	public List<String> queryAccVouchDetailByVouchIdList(Map<String, Object> entityMap) throws DataAccessException {
		return accVouchMapper.queryAccVouchDetailByVouchIdList(entityMap);
	}
	
	@Override
	public List<String> queryAccVouchListByBank(Map<String, Object> entityMap) throws DataAccessException {
		return accVouchMapper.queryAccVouchListByBank(entityMap);
	}

	@Override
	public List<Map<String, Object>> queryAccVouchPrint(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		
		if(	"1".equals(entityMap.get("pay_type_code"))){
			
			List<Map<String, Object>> list = accVouchMapper.queryAccVouch(entityMap);
			
			return list;
		
		}else{
			
			List<Map<String, Object>> list = accVouchMapper.queryAccVouchPayTypeCode(entityMap);
			
			return list;
			
		}
		
	}

	@Override
	public List<Map<String, Object>> queryAccVouchPayTypeCodePrint(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = accVouchMapper.queryAccVouchPayTypeCode(entityMap);
		
		return list;
	}

	@Override
	public List<Map<String, Object>> queryAccVouchNeatenPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = accVouchMapper.queryAccVouchNeaten(entityMap);
		
		return list;
	}
	
	@Override
	public List<Map<String, Object>> queryAccCashJournalPrint(Map<String, Object> entityMap) throws DataAccessException {
		
		if(entityMap.get("group_id") == null){
			entityMap.put("group_id", SessionManager.getGroupId());
		}
	
		if(entityMap.get("hos_id") == null){
			entityMap.put("hos_id", SessionManager.getHosId());
		}
		
		if(entityMap.get("copy_code") == null){
			entityMap.put("copy_code", SessionManager.getCopyCode());
		}
		
		if(entityMap.get("begin_date") != null && !"".equals(entityMap.get("begin_date"))){
			entityMap.put("begin_date", entityMap.get("begin_date").toString().replace("-", "/"));
			entityMap.put("acc_year", entityMap.get("begin_date").toString().substring(0, 4));
		}
		
		if(entityMap.get("end_date") != null && !"".equals(entityMap.get("end_date"))){
			entityMap.put("end_date", entityMap.get("end_date").toString().replace("-", "/"));
		}
		
		List<Map<String, Object>> list = accVouchMapper.queryAccCashJournalPrint(entityMap);
		
		return list;
	}
	
}
