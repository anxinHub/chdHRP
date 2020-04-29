/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.serviceImpl.accept;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.chd.hrp.ass.dao.accept.AssAcceptDetailMapper;
import com.chd.hrp.ass.dao.accept.AssAcceptMainMapper;
import com.chd.hrp.ass.dao.accept.AssAcceptPhotoMapper;
import com.chd.hrp.ass.dao.dict.AssAcceptItemAffiMapper;
import com.chd.hrp.ass.dao.dict.AssAcceptItemDictMapper;
import com.chd.hrp.ass.dao.dict.AssAcceptItemMapper;
import com.chd.hrp.ass.dao.ins.AssInsMainMapper;
import com.chd.hrp.ass.entity.accept.AssAcceptDetail;
import com.chd.hrp.ass.entity.accept.AssAcceptMain;
import com.chd.hrp.ass.entity.dict.AssAcceptItem;
import com.chd.hrp.ass.entity.dict.AssAcceptItemAffi;
import com.chd.hrp.ass.entity.dict.AssAcceptItemDict;
import com.chd.hrp.ass.service.accept.AssAcceptDetailService;
import com.chd.hrp.ass.service.accept.AssAcceptMainService;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.github.pagehelper.PageInfo;
 
/**
 * 
 * @Description: 050601 资产验收主表
 * @Table: ASS_ACCEPT_MAIN
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("assAcceptMainService")
public class AssAcceptMainServiceImpl implements AssAcceptMainService {

	private static Logger logger = Logger.getLogger(AssAcceptMainServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "assAcceptMainMapper")
	private final AssAcceptMainMapper assAcceptMainMapper = null;
	
	@Resource(name = "assAcceptDetailMapper")
	private final AssAcceptDetailMapper assAcceptDetailMapper = null;
	
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;

	@Resource(name = "assAcceptItemDictMapper")
	private final AssAcceptItemDictMapper assAcceptItemDictMapper = null;

	@Resource(name = "assAcceptItemAffiMapper")
	private final AssAcceptItemAffiMapper assAcceptItemAffiMapper = null;

	@Resource(name = "assAcceptItemMapper")
	private final AssAcceptItemMapper assAcceptItemMapper = null;
	
	@Resource(name = "assAcceptDetailService")
	private final AssAcceptDetailService assAcceptDetailService = null;
	
	@Resource(name = "assAcceptPhotoMapper")
	private final AssAcceptPhotoMapper assAcceptPhotoMapper = null;
    
	/**
	 * @Description 添加050601 资产验收主表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addAssAcceptMain(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象050601 资产验收主表
		AssAcceptMain assAcceptMain = queryAssAcceptMainByCode(entityMap);

		if (assAcceptMain != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}

		try {
			int state = assAcceptMainMapper.addAssAcceptMain(entityMap);
			Long sequence = assAcceptMainMapper.queryCurrentSequence();
			return "{\"msg\":\"添加成功.\",\"state\":\"true\",\"sequence\":\"" + sequence + "\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 批量添加050601 资产验收主表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatchAssAcceptMain(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assAcceptMainMapper.addBatchAssAcceptMain(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 更新050601 资产验收主表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateAssAcceptMain(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = assAcceptMainMapper.updateAssAcceptMain(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 批量更新050601 资产验收主表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatchAssAcceptMain(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assAcceptMainMapper.updateBatchAssAcceptMain(entityList);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 删除050601 资产验收主表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteAssAcceptMain(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = assAcceptMainMapper.deleteAssAcceptMain(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 批量删除050601 资产验收主表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchAssAcceptMain(List<Map<String, Object>> entityList) throws DataAccessException {

		try {
			assAcceptDetailMapper.deleteBatchAssAcceptContractMap(entityList);
			assAcceptDetailMapper.deleteBatchAssAcceptInsMap(entityList);
			assAcceptItemMapper.deleteBatchAssAcceptItem(entityList);
			assAcceptDetailMapper.deleteBatchAssAcceptDetail(entityList);
			assAcceptMainMapper.deleteBatchAssAcceptMain(entityList);
			

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	/**
	 * @Description  资产验收打印<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public Map<String,Object> queryAssAcceptDY(Map<String, Object> map)throws DataAccessException {
		
		try{
			Map<String,Object> reMap=new HashMap<String,Object>();
			WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
			AssAcceptMainMapper assAcceptMainMapper = (AssAcceptMainMapper)context.getBean("assAcceptMainMapper");
			if("1".equals(String.valueOf(map.get("p_num")))){
				// 资产验收打印模板主表
				List<Map<String,Object>> mainList=assAcceptMainMapper.queryAssAcceptBatch(map);
						
				//资产验收打印模板从表
				List<Map<String,Object>> detailList=assAcceptMainMapper.queryAssAccept_detail(map);
				
				reMap.put("main", mainList);
				reMap.put("detail", detailList);
			}else{
				Map<String,Object> mainList=assAcceptMainMapper.querAssAcceptByPrint(map);
				
				//资产验收打印模板从表
				List<Map<String,Object>> detailList=assAcceptMainMapper.queryAssAccept_detail(map);
				reMap.put("main", mainList);
				reMap.put("detail", detailList);
			}
			
		
			
			return reMap;
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
		
	}
	
	
	
	
	/**
	 * @Description 添加050601 资产验收主表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addOrUpdateAssAcceptMain(Map<String, Object> entityMap) throws DataAccessException {
		
		String accept_id = entityMap.get("accept_id").toString();
		String accept_no = entityMap.get("accept_no").toString();
		
		Map<String, Object> buildMapVo1 = new HashMap<String, Object>();
		Map<String, Object> buildMap = new HashMap<String, Object>();
		
		String assBuildAcceptMainJson = "";
		String assAcceptDetail = "";

		// 获取对象050601 资产验收主表
		// AssAcceptMain assAcceptMain = queryAssAcceptMainByCode(entityMap);
		// 判断是否存在对象050601 资产安装主表
		Map<String, Object> mapVo = new HashMap<String, Object>();
		mapVo.put("group_id", entityMap.get("group_id"));
		mapVo.put("hos_id", entityMap.get("hos_id"));
		mapVo.put("copy_code", entityMap.get("copy_code"));
		mapVo.put("accept_id", entityMap.get("accept_id"));
		List<AssAcceptMain> assAcceptList = assAcceptMainMapper.queryAssAcceptMainExists(mapVo);

		try {
			
			if (assAcceptList.size() > 0) {
				assAcceptMainMapper.updateAssAcceptMain(entityMap);
			}else{

				
				if(entityMap.get("create_date") != null && !"".equals(entityMap.get("create_date"))){
					entityMap.put("year", entityMap.get("create_date").toString().substring(0,4));
					entityMap.put("month", entityMap.get("create_date").toString().substring(5,7));
				}
				
				entityMap.put("bill_table", "ASS_ACCEPT_MAIN");
				accept_no = assBaseService.getBillNOSeqNo(entityMap);

				entityMap.put("accept_no", accept_no);
				int state = assAcceptMainMapper.addAssAcceptMain(entityMap);
				accept_id = String.valueOf(queryCurrentSequence());
				if (state > 0) {
					assBaseService.updateAssBillNoMaxNo(entityMap);
				}
			}
			
			buildMapVo1.put("group_id", SessionManager.getGroupId());

			buildMapVo1.put("hos_id", SessionManager.getHosId());

			buildMapVo1.put("copy_code", SessionManager.getCopyCode());
			
			buildMapVo1.put("accept_id",accept_id);

			List<Map> detail = ChdJson.fromJsonAsList(Map.class, entityMap.get("ParamVo").toString());

			for (Map<String, Object> detailVo : detail) {

				detailVo.put("group_id", SessionManager.getGroupId());

				detailVo.put("hos_id", SessionManager.getHosId());

				detailVo.put("copy_code", SessionManager.getCopyCode());

				if (detailVo.get("ass_id") == null || "".equals(detailVo.get("ass_id"))) {
					continue;
				}
				
				detailVo.put("accept_id", accept_id);

				detailVo.put("accept_no", accept_no);

				if (detailVo.get("accept_detail_id") == null) {

					detailVo.put("accept_detail_id", "0");

				}

				detailVo.put("contract_detail_id", mapVo.get("contract_detail_id"));

				String ass_id_no = detailVo.get("ass_id").toString();

				detailVo.put("ass_id", ass_id_no.split("@")[0]);

				detailVo.put("ass_no", ass_id_no.split("@")[1]);

				String fac_id_no = String.valueOf(detailVo.get("fac_id"));

				if(fac_id_no != null && fac_id_no.split("@").length == 2){
					detailVo.put("fac_id", fac_id_no.split("@")[0]);
					detailVo.put("fac_no", fac_id_no.split("@")[1]);
				}else{
					detailVo.put("fac_id", null);
					detailVo.put("fac_no", null);
				}
				
				assAcceptDetailService.addOrUpdateAssAcceptDetail(detailVo);
				
			}
			
			//生成验收项目列表
			List<Map<String, Object>> listVoBuild = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> detailList = assAcceptDetailMapper.queryAssAcceptDetailByUpdate(buildMapVo1);
			
			for (Map<String, Object> map : detailList) {
				//buildMap.put("accept_detail_id", map.get("accept_detail_id"));
				buildMapVo1.put("ass_id", String.valueOf(map.get("ass_id")).split("@")[0]);
				assBuildAcceptMainJson = buildAssAcceptItem(buildMapVo1);
			    Map<String,Object> assBuildAcceptMainMap = JSONObject.parseObject(assBuildAcceptMainJson);
			    if(assBuildAcceptMainMap.get("warn") != null){
			    	//return ;
			    	throw new SysException("没有可生成的验收项目！");
			    }else {
				List<Map> list = JSONArray.parseArray(assBuildAcceptMainMap.get("Rows").toString(), Map.class);
				for (Map<String,Object> map2 : list) {
					map2.put("group_id", SessionManager.getGroupId());

					map2.put("hos_id", SessionManager.getHosId());

					map2.put("copy_code", SessionManager.getCopyCode());
					map2.put("accept_detail_id", map.get("accept_detail_id"));
					map2.put("accept_id", accept_id);
					
					map2.put("is_normal", "2");
					map2.put("item_code", map2.get("accept_item_code"));
					listVoBuild.add(map2);
				}
				
			}
			
			assAcceptItemMapper.deleteAssAcceptItem(buildMapVo1);

			assAcceptItemMapper.addBatchAssAcceptItem(listVoBuild);
			}
			return "{\"msg\":\"操作成功.\",\"state\":\"true\",\"accept_id\":\"" + accept_id + "\",\"accept_no\":\""
					+ accept_no + "\"}";

		} catch (Exception e) {

			logger.error(e.getMessage());

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 查询结果集050601 资产验收主表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryAssAcceptMain(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {
			List<AssAcceptMain> list = assAcceptMainMapper.queryAssAcceptMain(entityMap);
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<AssAcceptMain> list = assAcceptMainMapper.queryAssAcceptMain(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	/**
	 * @Description 获取对象050601 资产验收主表<BR>
	 * @param entityMap<BR>
	 *            参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public AssAcceptMain queryAssAcceptMainByCode(Map<String, Object> entityMap) throws DataAccessException {
		return assAcceptMainMapper.queryAssAcceptMainByCode(entityMap);
	}

	/**
	 * @Description 获取050601 资产验收主表<BR>
	 * @param entityMap<BR>
	 *            参数为要检索的字段
	 * @return AssAcceptMain
	 * @throws DataAccessException
	 */
	@Override
	public AssAcceptMain queryAssAcceptMainByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return assAcceptMainMapper.queryAssAcceptMainByUniqueness(entityMap);
	}

	@Override
	public Long queryCurrentSequence() throws DataAccessException {
		// TODO Auto-generated method stub
		return assAcceptMainMapper.queryCurrentSequence();
	}

	@Override
	public List<AssAcceptMain> queryAssAcceptMainExists(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return assAcceptMainMapper.queryAssAcceptMainExists(entityMap);
	}

	@Override
	public String updateToExamine(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {

			int state = assAcceptMainMapper.updateToExamine(entityMap);

			return "{\"msg\":\"审核成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	@Override
	public String updateNotToExamine(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {

			int state = assAcceptMainMapper.updateNotToExamine(entityMap);

			return "{\"msg\":\"销审成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	/**
	 * @Description 生成050601 资产验收主表<BR>
	 * @param entityMap<BR>
	 * @return AssAcceptMain
	 * @throws DataAccessException
	 */
	@Override
	public String initContract(Map<String, Object> entityMap) throws DataAccessException {
		try {

			int state = assAcceptMainMapper.initContract(entityMap);

			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage());

			throw new SysException(e.getMessage());

		}
	}

	@Override
	public AssAcceptMain queryAssAcceptMainUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return assAcceptMainMapper.queryAssAcceptMainUniqueness(entityMap);
	}

	
	//生成验收项目
	@Override
	public String buildAssAcceptItem(Map<String, Object> entityMap) throws DataAccessException {

		List<AssAcceptItemAffi> affiList = assAcceptItemAffiMapper.queryAssAcceptItemAffi(entityMap);
		if (affiList.size() > 0) {
			return ChdJson.toJson(affiList);
		}
		List<AssAcceptItemDict> dictList = assAcceptItemDictMapper.queryAssAcceptItemDict(entityMap);
		if (dictList.size() > 0) {
			return ChdJson.toJson(dictList);
		}
		return "{\"warn\":\"没有可生成验收项目.\"}";
	}

	
	//保存验收项目
	@Override
	public String saveAssAcceptItem(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			mapVo.put("group_id", entityMap.get(0).get("group_id"));
			mapVo.put("hos_id", entityMap.get(0).get("hos_id"));
			mapVo.put("copy_code", entityMap.get(0).get("copy_code"));
			mapVo.put("accept_id", entityMap.get(0).get("accept_id"));
			mapVo.put("accept_detail_id", entityMap.get(0).get("accept_detail_id"));
			assAcceptItemMapper.deleteAssAcceptItem(mapVo);

			assAcceptItemMapper.addBatchAssAcceptItem(entityMap);

			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	
	//删除验收项目
	@Override
	public String deleteAssAcceptItem(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			
			
			assAcceptItemMapper.deleteBatchAssAcceptItem(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	//查询验收项目
	@Override
	public String queryAssAcceptItem(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {
			List<AssAcceptItem> list = assAcceptItemMapper.queryAssAcceptItem(entityMap);
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<AssAcceptItem> list = assAcceptItemMapper.queryAssAcceptItem(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}
//查询资产是否存在
	@Override
	public List<AssAcceptItem> queryByAcceptId(Map<String, Object> entityMap) throws DataAccessException {
		List<AssAcceptItem> list=assAcceptItemMapper.queryByAcceptId(entityMap);
		return list;
	}

	@Override
	public String deleteAssAcceptItemByAssAcceptDetail(AssAcceptDetail entityMap) throws DataAccessException {
		try {
			assAcceptItemMapper.deleteAssAcceptItemByAssAcceptDetail(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		
	}

	@Override
	public List<String> queryAcceptMainState(Map<String, Object> mapVo)
			throws DataAccessException {
		return assAcceptMainMapper.queryAcceptMainState(mapVo);
	}

	@Override
	public String updateAcceptMainInState(Map<String, Object> mapVo)throws DataAccessException {
		// TODO Auto-generated method stub 
		try {

			int state = assAcceptMainMapper.updateAcceptMainInState(mapVo);
				
				return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

}
