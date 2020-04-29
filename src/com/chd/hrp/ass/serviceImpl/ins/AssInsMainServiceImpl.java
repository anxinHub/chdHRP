/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.serviceImpl.ins;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.ins.AssInsMainMapper;
import com.chd.hrp.ass.dao.plan.AssPlanDeptMapper;
import com.chd.hrp.ass.entity.ins.AssInsMain;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.ins.AssInsDetailService;
import com.chd.hrp.ass.service.ins.AssInsMainService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 050601 资产安装主表
 * @Table:
 * ASS_INS_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

 
@Service("assInsMainService")
public class AssInsMainServiceImpl implements AssInsMainService {

	private static Logger logger = Logger.getLogger(AssInsMainServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assInsMainMapper")
	private final AssInsMainMapper assInsMainMapper = null;
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;
	
	@Resource(name = "assInsDetailService")
	private final AssInsDetailService assInsDetailService = null;
	/**
	 * @Description 
	 * 添加050601 资产安装主表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAssInsMain(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象050601 资产安装主表
		AssInsMain assInsMain = queryAssInsMainByCode(entityMap);

		if (assInsMain != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = assInsMainMapper.addAssInsMain(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	/**
	 * @Description 
	 * 批量添加050601 资产安装主表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAssInsMain(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assInsMainMapper.addBatchAssInsMain(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	
		/**
	 * @Description 
	 * 更新050601 资产安装主表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAssInsMain(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = assInsMainMapper.updateAssInsMain(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新050601 资产安装主表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAssInsMain(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  assInsMainMapper.updateBatchAssInsMain(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 删除050601 资产安装主表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deleteAssInsMain(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = assInsMainMapper.deleteAssInsMain(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除050601 资产安装主表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAssInsMain(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assInsMainMapper.deleteBatchAssInsMain(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
	}
	
	/**
	 * @Description 
	 * 添加050601 资产安装主表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdateAssInsMain(Map<String,Object> entityMap)throws DataAccessException{
		
		String ins_id = entityMap.get("ins_id").toString();
		String ins_no = entityMap.get("ins_no").toString();
		
		/**
		* 备注 先判断是否存在 存在即更新不存在则添加<br>
		* 在判断是否存在时可根据实际情况进行修改传递的参数进行判断<br>
		* 判断是否名称相同 判断是否 编码相同 等一系列规则
		*/
		//判断是否存在对象050601 资产安装主表
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("ins_id", entityMap.get("ins_id"));
		List<AssInsMain> list = assInsMainMapper.queryAssInsMainExists(mapVo);
		
		try {
			
			if (list.size() > 0) {
				int state = assInsMainMapper.updateAssInsMain(entityMap);
				//return "{\"msg\":\"更新成功.\",\"state\":\"true\",\"ins_id\":\""+entityMap.get("ins_id")+"\",\"ins_no\":\""+entityMap.get("ins_no")+"\"}";
			}else{
				if(entityMap.get("create_date") != null && !"".equals(entityMap.get("create_date"))){
					entityMap.put("year", entityMap.get("create_date").toString().substring(0,4));
					entityMap.put("month", entityMap.get("create_date").toString().substring(5,7));
				}
				entityMap.put("bill_table", "ASS_INS_MAIN");
				ins_no=assBaseService.getBillNOSeqNo(entityMap);
				entityMap.put("ins_no", ins_no);
				
				int state = assInsMainMapper.addAssInsMain(entityMap);
				ins_id= String.valueOf(queryAssInsMainSequence());
				if(state>0){
					assBaseService.updateAssBillNoMaxNo(entityMap);
				}
			}
			List<Map> detail = ChdJson.fromJsonAsList(Map.class, entityMap.get("ParamVo").toString());

			for (Map<String, Object> detailVo : detail) {

				detailVo.put("group_id", SessionManager.getGroupId());
				detailVo.put("hos_id", SessionManager.getHosId());
				detailVo.put("copy_code", SessionManager.getCopyCode());

				if (detailVo.get("ass_id") == null || "".equals(detailVo.get("ass_id"))) {
					continue;
				}

				detailVo.put("ins_id", ins_id);
				detailVo.put("ins_no", ins_no);

				detailVo.put("contract_detail_id", mapVo.get("contract_detail_id"));

				if (detailVo.get("ins_detail_id") == null) {
					detailVo.put("ins_detail_id", "0");
				}

				//String ins_money = detailVo.get("ins_money").toString().replaceAll(",", "");
				if(detailVo.get("ins_money") != null && !detailVo.get("ins_money").equals("")){
					String ins_money = detailVo.get("ins_money").toString();
					detailVo.put("ins_money", Double.parseDouble(ins_money));
				}

				String ass_id_no = detailVo.get("ass_id").toString();
				detailVo.put("ass_id", ass_id_no.split("@")[0]);
				detailVo.put("ass_no", ass_id_no.split("@")[1]);
				String fid="";
				 if (detailVo.get("fac_id") != null && !detailVo.get("fac_id").equals("@") && !detailVo.get("fac_id").equals("")) {
					 fid = detailVo.get("fac_id").toString();
						detailVo.put("fac_id", fid.split("@")[0]);
						detailVo.put("fac_no", fid.split("@")[1]);
				}
				
				/*if (fid!= null && fid.split("@").length == 2) {
				
				}*/ else {
					detailVo.put("fac_id", null);
					detailVo.put("fac_no", null);
				}

				assInsDetailService.addOrUpdateAssInsDetail(detailVo);
			}
			return "{\"msg\":\"操作成功.\",\"state\":\"true\",\"ins_id\":\""+ins_id+"\",\"ins_no\":\""+ins_no+"\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	
	/**
	 * @Description 
	 * 查询结果集050601 资产安装主表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAssInsMain(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssInsMain> list = assInsMainMapper.queryAssInsMain(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssInsMain> list = assInsMainMapper.queryAssInsMain(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象050601 资产安装主表<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AssInsMain queryAssInsMainByCode(Map<String,Object> entityMap)throws DataAccessException{
		return assInsMainMapper.queryAssInsMainByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取050601 资产安装主表<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssInsMain
	 * @throws DataAccessException
	*/
	@Override
	public AssInsMain queryAssInsMainByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return assInsMainMapper.queryAssInsMainByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取050601 资产安装主表<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssInsMain>
	 * @throws DataAccessException
	*/
	@Override
	public List<AssInsMain> queryAssInsMainExists(Map<String,Object> entityMap)throws DataAccessException{
		return assInsMainMapper.queryAssInsMainExists(entityMap);
	}
	@Override
	public String updateToExamine(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		try {
			
			  int state = assInsMainMapper.updateToExamine(entityMap);
				
				return "{\"msg\":\"审核成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				throw new SysException(e.getMessage());

			}
	}
	
	/**
	 * @Description 资产安装打印05012
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	@Override
	public Map<String,Object> queryAssInsMainDY(Map<String, Object> map)throws DataAccessException {
		
		try{
			Map<String,Object> reMap=new HashMap<String,Object>();
			WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
			AssInsMainMapper assInsMainMapper = (AssInsMainMapper)context.getBean("assInsMainMapper");
			if("1".equals(String.valueOf(map.get("p_num")))){
				// 资产安装打印模板主表
				List<Map<String,Object>> mainList=assInsMainMapper.queryassInsMainBatch(map);
						
				//资产安装打印模板从表
				List<Map<String,Object>> detailList=assInsMainMapper.queryassInsMain_detail(map);
				
				reMap.put("main", mainList);
				reMap.put("detail", detailList);
			}else{
				Map<String,Object> mainList=assInsMainMapper.querassInsMainByPrint(map);
				
				//资产安装打印模板从表
				List<Map<String,Object>> detailList=assInsMainMapper.queryassInsMain_detail(map);
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
	public String updateNotToExamine(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		try {
			
			  int state = assInsMainMapper.updateNotToExamine(entityMap);
				
				return "{\"msg\":\"销审成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				throw new SysException(e.getMessage());

			}
	}
	@Override
	public Long queryAssInsMainSequence() throws DataAccessException {
		return assInsMainMapper.queryAssInsMainSequence();
	}
	@Override
	public List<String> queryInsMainState(Map<String, Object> mapVo)
			throws DataAccessException {
		return assInsMainMapper.queryInsMainState(mapVo);
	}
	
}
