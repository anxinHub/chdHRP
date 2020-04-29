/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.ass.serviceImpl.apply;

import java.util.*;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.hrp.ass.dao.apply.AssApplyDeptDetailMapper;
import com.chd.hrp.ass.dao.apply.AssApplyDeptMapper;
import com.chd.hrp.ass.dao.apply.AssApplyDeptProofMapper;
import com.chd.hrp.ass.entity.apply.AssApplyDept;
import com.chd.hrp.ass.service.apply.AssApplyDeptDetailService;
import com.chd.hrp.ass.service.apply.AssApplyDeptService;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.mat.dao.storage.check.MatCheckMainMapper;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 050201 购置申请单
 * @Table:
 * ASS_APPLY_DEPT
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

 
@Service("assApplyDeptService")
public class AssApplyDeptServiceImpl implements AssApplyDeptService {

	private static Logger logger = Logger.getLogger(AssApplyDeptServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assApplyDeptMapper")
	private final AssApplyDeptMapper assApplyDeptMapper = null;
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;
	
	@Resource(name = "assApplyDeptDetailMapper")
	private final AssApplyDeptDetailMapper assApplyDeptDetailMapper = null;
	
	
	@Resource(name = "assApplyDeptProofMapper")
	private final AssApplyDeptProofMapper assApplyDeptProofMapper = null;
	
	@Resource(name = "assApplyDeptDetailService")
	private final AssApplyDeptDetailService assApplyDeptDetailService = null;
	
	/**
	 * @Description 
	 * 添加050201 购置申请单<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAssApplyDept(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象050201 购置申请单
		AssApplyDept assApplyDept = queryAssApplyDeptByCode(entityMap);

		if (assApplyDept != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
	
			 assApplyDeptMapper.addAssApplyDept(entityMap);
			 
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	/**
	 * @Description 
	 * 批量添加050201 购置申请单<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAssApplyDept(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assApplyDeptMapper.addBatchAssApplyDept(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	
		/**
	 * @Description 
	 * 更新050201 购置申请单<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAssApplyDept(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = assApplyDeptMapper.updateAssApplyDept(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新050201 购置申请单<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAssApplyDept(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  assApplyDeptMapper.updateBatchAssApplyDept(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 删除050201 购置申请单<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deleteAssApplyDept(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = assApplyDeptMapper.deleteAssApplyDept(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除050201 购置申请单<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAssApplyDept(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assApplyDeptMapper.deleteBatchAssApplyDept(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
	}
	
	/**
	 * @Description 
	 * 添加050201 购置申请单<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdateAssApplyDept(Map<String,Object> entityMap)throws DataAccessException{
		
		String apply_id = entityMap.get("apply_id").toString();
		String apply_no = entityMap.get("apply_no").toString();
		
		/**
		* 备注 先判断是否存在 存在即更新不存在则添加<br>
		* 在判断是否存在时可根据实际情况进行修改传递的参数进行判断<br>
		* 判断是否名称相同 判断是否 编码相同 等一系列规则
		*/
		//判断是否存在对象050701 资产入库主表
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("apply_id", entityMap.get("apply_id"));
    	List<AssApplyDept> list=assApplyDeptMapper.queryAssApplyDeptExists(mapVo);
		
//		//获取对象050201 购置申请单
//		AssApplyDept assApplyDept = queryAssApplyDeptByCode(entityMap);

		try {
			if (list.size()>0) {

				int state = assApplyDeptMapper.updateAssApplyDept(entityMap);
				
			}else{
				if(entityMap.get("create_date") != null && !"".equals(entityMap.get("create_date"))){
					entityMap.put("year", entityMap.get("create_date").toString().substring(0,4));
					entityMap.put("month", entityMap.get("create_date").toString().substring(5,7));
				}
				entityMap.put("bill_table", "ASS_APPLY_DEPT");
				
				apply_no=assBaseService.getBillNOSeqNo(entityMap);
				entityMap.put("apply_no", apply_no);
				int state = assApplyDeptMapper.addAssApplyDept(entityMap);
				apply_id= String.valueOf(queryAssApplyDeptSequence());
				if(state>0){
					assBaseService.updateAssBillNoMaxNo(entityMap);
				}
			}
			//处理申请明细
			JSONArray json = JSONArray.parseArray(String.valueOf(entityMap.get("ParamVo")));
			
			Iterator it = json.iterator();
			while (it.hasNext()) {
				
				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
				
				Map<String,Object> detailVo = new HashMap<String,Object>();
				
				if (jsonObj.get("ass_id") == null || "".equals(jsonObj.get("ass_id"))) {
					continue;
				}

				detailVo.put("group_id", entityMap.get("group_id"));
				detailVo.put("hos_id", entityMap.get("hos_id"));
				detailVo.put("copy_code", entityMap.get("copy_code"));
				detailVo.put("apply_id", apply_id);
				detailVo.put("apply_no", apply_no);
	            detailVo.put("source_id", entityMap.get("source_id"));
	            
	            detailVo.put("ass_model", jsonObj.get("ass_model"));
	            detailVo.put("ass_spec", jsonObj.get("ass_spec"));
	            detailVo.put("ass_brand", jsonObj.get("ass_brand"));
	            detailVo.put("apply_amount", jsonObj.get("apply_amount"));
	            detailVo.put("advice_price", jsonObj.get("advice_price"));
	            detailVo.put("ass_usage_code", jsonObj.get("ass_usage_code"));
	            
	            detailVo.put("usage_note", jsonObj.get("usage_note"));
	            detailVo.put("note", jsonObj.get("note"));
	            detailVo.put("ass_name", jsonObj.get("ass_name"));
	            detailVo.put("proj_id", jsonObj.get("proj_id"));
	            detailVo.put("proj_no", jsonObj.get("proj_no"));
	            detailVo.put("budg_purchase_id", jsonObj.get("budg_purchase_id"));
	            detailVo.put("emp_dept_id", jsonObj.get("emp_dept_id"));
	            detailVo.put("emp_dept_no", jsonObj.get("emp_dept_no"));
	            
	            detailVo.put("purchase_type_id", jsonObj.get("purchase_type_id"));
	            detailVo.put("budg_amount", jsonObj.get("budg_amount"));
	            detailVo.put("budg_money", jsonObj.get("budg_money"));
	            detailVo.put("budg_price", jsonObj.get("budg_price"));
	            
	            detailVo.put("features_req", jsonObj.get("features_req"));
	            detailVo.put("proj_name", jsonObj.get("proj_name"));
	            detailVo.put("naturs_code", jsonObj.get("naturs_code"));
	            detailVo.put("ass_type_id", jsonObj.get("ass_type_id"));
	            
	            
				if (jsonObj.get("detail_id") == null || "".equals(String.valueOf(jsonObj.get("detail_id")))) {
					detailVo.put("detail_id", assApplyDeptDetailMapper.queryAssApplyDeptDetailSequence());
				}else{
					detailVo.put("detail_id", jsonObj.get("detail_id"));
				}
				if (StringUtils.isNotEmpty((String) jsonObj.get("need_date"))) {
					detailVo.put("need_date", DateUtil.stringToDate(jsonObj.get("need_date").toString(), "yyyy-MM-dd"));
				}

				if (StringUtils.isNotEmpty((String) jsonObj.get("budg_date"))) {
					detailVo.put("budg_date", DateUtil.stringToDate(jsonObj.get("budg_date").toString(), "yyyy-MM-dd"));
				}


				if (jsonObj.get("ass_id") != null && !"".equals(String.valueOf(jsonObj.get("ass_id")))) {

					String ass_id_no = jsonObj.get("ass_id").toString();

					detailVo.put("ass_id", ass_id_no.split("@")[0]);

					detailVo.put("ass_no", ass_id_no.split("@")[1]);

				}

				if (jsonObj.get("fac_id") != null && !"".equals(String.valueOf(jsonObj.get("fac_id")))) {

					String fac_id_no = jsonObj.get("fac_id").toString();
					if(fac_id_no != null && fac_id_no.split("@").length == 2){
						detailVo.put("fac_id", fac_id_no.split("@")[0]);
						detailVo.put("fac_no", fac_id_no.split("@")[1]);
					}else{
						detailVo.put("fac_id", null);
						detailVo.put("fac_no", null);
					}
				}
				/**
				 * 论证主表数据 处理
				 */
				if (jsonObj.get("bcost_year") != null && !"".equals(String.valueOf(jsonObj.get("ass_id")))) {
					detailVo.put("bcost_year", jsonObj.get("bcost_year"));
				}else{
					detailVo.put("bcost_year", null);
				}
				if (jsonObj.get("avg_cost") != null && !"".equals(String.valueOf(jsonObj.get("ass_id")))) {
					detailVo.put("avg_cost", jsonObj.get("avg_cost"));
				}else{
					detailVo.put("avg_cost", null);
				}
				if (jsonObj.get("avg_profit") != null && !"".equals(String.valueOf(jsonObj.get("ass_id")))) {
					detailVo.put("avg_profit", jsonObj.get("avg_profit"));
				}else{
					detailVo.put("avg_profit", null);
				}
				if (jsonObj.get("benefit_rate") != null && !"".equals(String.valueOf(jsonObj.get("ass_id")))) {
					detailVo.put("benefit_rate", jsonObj.get("benefit_rate"));
				}else{
					detailVo.put("benefit_rate", null);
				}
				
				if (jsonObj.get("create_user") != null && !"".equals(String.valueOf(jsonObj.get("ass_id"))) ) {
					detailVo.put("create_user", jsonObj.get("create_user"));
				}else{
					detailVo.put("create_user", null);
				}
				if (jsonObj.get("use_place") != null && !"".equals(String.valueOf(jsonObj.get("ass_id")))) {
					detailVo.put("use_place", jsonObj.get("use_place"));
				}else{
					detailVo.put("use_place", null);
				}
				if (jsonObj.get("apply_analyze") != null && !"".equals(String.valueOf(jsonObj.get("ass_id")))) {
					detailVo.put("apply_analyze", jsonObj.get("apply_analyze"));
				}else{
					detailVo.put("apply_analyze", null);
				}
				if (jsonObj.get("investigate_analyze") != null && !"".equals(String.valueOf(jsonObj.get("ass_id")))) {
					detailVo.put("investigate_analyze", jsonObj.get("investigate_analyze"));
				}else{
					detailVo.put("investigate_analyze", null);
				}
				if (jsonObj.get("describ") != null && !"".equals(String.valueOf(jsonObj.get("ass_id")))) {
					detailVo.put("describ", jsonObj.get("describ"));
				}else{
					detailVo.put("describ", null);
				}
				
				if(jsonObj.get("proof_id") == null){//申请明细不存在论证信息时添加
					if(jsonObj.get("bcost_year") != null){// 页面有 论证信息
						assApplyDeptProofMapper.addApplyDeptProof(detailVo);
						
						Long proof_id = assApplyDeptProofMapper.queryAssApplyProofSequence();
						
						detailVo.put("proof_id", proof_id);
					}else{
						detailVo.put("proof_id", null);
					}
						
				}else{//申请明细存在论证信息 时 修改 论证信息
					detailVo.put("proof_id", jsonObj.get("proof_id"));
					assApplyDeptProofMapper.updateApplyDeptProof(detailVo);
				}
				
				
				assApplyDeptDetailService.addOrUpdateAssApplyDeptDetail(detailVo);
				
				if(detailVo.get("proof_id") != null){ // 有论证数据
					
					//处理论证明细
					if(jsonObj.get("detailData") != null && !"".equals(String.valueOf(jsonObj.get("detailData")))){
						
						JSONArray jsonProof = JSONArray.parseArray(String.valueOf(JSONObject.parseObject(String.valueOf(jsonObj.get("detailData"))).get("Rows")));
					
						Map<String,Object> delProofDetailMap = new HashMap<String, Object>();
						
						delProofDetailMap.put("group_id", SessionManager.getGroupId());
						delProofDetailMap.put("hos_id", SessionManager.getHosId());
						delProofDetailMap.put("copy_code", SessionManager.getCopyCode());
						delProofDetailMap.put("proof_id", detailVo.get("proof_id"));
						// 论证明细数据 先删除  后添加
						assApplyDeptProofMapper.deleteApplyProofDetailm(delProofDetailMap);
						
						if(jsonProof.size() > 0){
							
							Iterator itProof = jsonProof.iterator();
							while (itProof.hasNext()) {
								
								JSONObject jsonObjProof = JSONObject.parseObject(itProof.next().toString());
								
								Map<String,Object> proofDetailMap = new HashMap<String,Object>();
						
						
								if(jsonObjProof.get("year_num")!= null){//排除 空行数据
									proofDetailMap.put("group_id", SessionManager.getGroupId());
									proofDetailMap.put("hos_id", SessionManager.getHosId());
									proofDetailMap.put("copy_code", SessionManager.getCopyCode());
									proofDetailMap.put("proof_id", detailVo.get("proof_id"));
									
									proofDetailMap.put("year_num", jsonObjProof.get("year_num"));
									proofDetailMap.put("project_income", jsonObjProof.get("project_income"));
									proofDetailMap.put("year_workload", jsonObjProof.get("year_workload"));
									proofDetailMap.put("charge_stand", jsonObjProof.get("charge_stand"));
									proofDetailMap.put("consumable_cost", jsonObjProof.get("consumable_cost"));
									proofDetailMap.put("employee_cost", jsonObjProof.get("employee_cost"));
									proofDetailMap.put("maintenance_cost", jsonObjProof.get("maintenance_cost"));
									proofDetailMap.put("waterele_cost", jsonObjProof.get("waterele_cost"));
									proofDetailMap.put("depreciation_cost", jsonObjProof.get("depreciation_cost"));
									proofDetailMap.put("other_cost", jsonObjProof.get("other_cost"));
									
									assApplyDeptProofMapper.addApplyDeptProofDetail(proofDetailMap);
								}
							}
						}
					}
				}
				
			}
			return "{\"msg\":\"操作成功.\",\"state\":\"true\",\"apply_id\":\""+apply_id+"\",\"apply_no\":\""+apply_no+"\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());
		}
		
	}
	/**
	 * @Description 
	 * 查询结果集05001 购置申请<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAssApplyDept(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage"); 
		
		if (sysPage.getTotal()==-1){
			
			List<AssApplyDept> list = assApplyDeptMapper.queryAssApplyDept(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssApplyDept> list = assApplyDeptMapper.queryAssApplyDept(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	///
	/**
	 * @Description 
	 * 查询结果集050201 购置申请单<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAssApplyDepts(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage"); 
		
		if (sysPage.getTotal()==-1){
			
			List<AssApplyDept> list = assApplyDeptMapper.queryAssApplyDepts(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssApplyDept> list = assApplyDeptMapper.queryAssApplyDepts(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象050201 购置申请单<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AssApplyDept queryAssApplyDeptByCode(Map<String,Object> entityMap)throws DataAccessException{
		return assApplyDeptMapper.queryAssApplyDeptByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取050201 购置申请单<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssApplyDept
	 * @throws DataAccessException
	*/
	@Override
	public AssApplyDept queryAssApplyDeptByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return assApplyDeptMapper.queryAssApplyDeptByUniqueness(entityMap);
	}
	
	@Override
    public Long queryAssApplyDeptSequence() throws DataAccessException {
		return assApplyDeptMapper.queryAssApplyDeptSequence();
    }
	@Override
	public String updateToExamine(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		try {
			
			  int state = assApplyDeptMapper.updateToExamine(entityMap);
				
				return "{\"msg\":\"审核成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				throw new SysException(e.getMessage());

			}
	}
	@Override
	public String updateNotToExamine(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		try {
			
			  int state = assApplyDeptMapper.updateNotToExamine(entityMap);
				
				return "{\"msg\":\"销审成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				throw new SysException(e.getMessage());

			}
	}
	/**
	 * @Description 
	 * 获取05001 购置申请单打印<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssApplyDept
	 * @throws DataAccessException
	*/
	@Override
	public Map<String,Object> queryAssApplyDeptDY(Map<String, Object> map)throws DataAccessException {
		
		try{
			Map<String,Object> reMap=new HashMap<String,Object>();
			WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
			AssApplyDeptMapper assApplyDeptMapper = (AssApplyDeptMapper)context.getBean("assApplyDeptMapper");
			if("1".equals(String.valueOf(map.get("p_num")))){
				//盘点打印模板主表
				List<Map<String,Object>> mainList=assApplyDeptMapper.queryMatCheckByPrintBatch(map);
						
				//盘点打印模板从表
				List<Map<String,Object>> detailList=assApplyDeptMapper.queryMatCheckByPrintBatch_detail(map);
				
				reMap.put("main", mainList);
				reMap.put("detail", detailList);
			}else{
				Map<String,Object> mainList=assApplyDeptMapper.queryMatCheckByPrint(map);
				
				//盘点打印模板从表
				List<Map<String,Object>> detailList=assApplyDeptMapper.queryMatCheckByPrintBatch_detail(map);
				reMap.put("main", mainList);
				reMap.put("detail", detailList);
			}
			
		
			
			return reMap;
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
		
	}
	
	
	
	@Override
	public List<AssApplyDept> queryAssApplyDeptExists(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return assApplyDeptMapper.queryAssApplyDeptExists(entityMap);
	}
	@Override
	public String queryAssApplyDeptByPlanDept(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage"); 
		
		if (sysPage.getTotal()==-1){
			
			List<AssApplyDept> list = assApplyDeptMapper.queryAssApplyDeptByPlanDept(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<AssApplyDept> list = assApplyDeptMapper.queryAssApplyDeptByPlanDept(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	@Override
	public List<String> queryAssApplyDeptState(Map<String, Object> mapVo)
			throws DataAccessException {
		return assApplyDeptMapper.queryAssApplyDeptState(mapVo);
	}
	
	
}
