package com.chd.hrp.pac.serviceImpl.template.pacttemplateset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.nutz.json.Json;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.StringTool;
import com.chd.hrp.budg.entity.BudgAccSubjShip;
import com.chd.hrp.pac.dao.template.pacttemplateset.PactTemplateButtonMapper;
import com.chd.hrp.pac.dao.template.pacttemplateset.PactTemplateGridetabMapper;
import com.chd.hrp.pac.dao.template.pacttemplateset.PactTemplateGridtabItemMapper;
import com.chd.hrp.pac.dao.template.pacttemplateset.PactTemplateMapper;
import com.chd.hrp.pac.dao.template.pacttemplateset.PactTemplateModularMapper;
import com.chd.hrp.pac.dao.template.pacttemplateset.PactTemplateSheetItemMapper;
import com.chd.hrp.pac.dao.template.pacttemplateset.PactTemplateSheetMapper;
import com.chd.hrp.pac.service.template.pacttemplateset.PactTemplateSetService;
import com.github.pagehelper.PageInfo;

@Service(value = "pactTemplateSetService")
public class PactTemplateSetServiceImpl implements PactTemplateSetService {

	private static Logger logger = Logger.getLogger(PactTemplateSetServiceImpl.class);
	
	@Resource(name = "pactTemplateMapper")
	private PactTemplateMapper pactTemplateMapper;


	@Resource(name = "pactTemplateModularMapper")
	private PactTemplateModularMapper pactTemplateModularMapper;
	
	
	@Resource(name = "pactTemplateGridetabMapper")
	private PactTemplateGridetabMapper pactTemplateGridetabMapper;

	@Resource(name = "pactTemplateGridtabItemMapper")
	private PactTemplateGridtabItemMapper pactTemplateGridtabItemMapper;
	
	@Resource(name = "pactTemplateSheetMapper")
	private PactTemplateSheetMapper pactTemplateSheetMapper;
	
	@Resource(name = "pactTemplateSheetItemMapper")
	private PactTemplateSheetItemMapper pactTemplateSheetItemMapper;
	
	@Resource(name = "pactTemplateButtonMapper")
	private PactTemplateButtonMapper pactTemplateButtonMapper;

	
	
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addBatch(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String update(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateBatch(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String delete(Map<String, Object> entityMap)	throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteBatch(List<Map<String, Object>> listVo) throws DataAccessException {
		try {

			int state = pactTemplateMapper.deleteBatch(listVo);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {
	
			logger.error(e.getMessage(), e);
	
			throw new SysException(e.getMessage(), e);
	
		}
	}

	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String query(Map<String, Object> entityMap)	throws DataAccessException {
		
		try {
			SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");
			if (sysPage.getTotal() == -1) {
				List<Map<String,Object>> query = (List<Map<String, Object>>) pactTemplateMapper.query(entityMap);
				return ChdJson.toJson(query);
			} else {
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<Map<String, Object>> list = (List<Map<String, Object>>) pactTemplateMapper.query(entityMap,rowBounds);
				@SuppressWarnings("rawtypes")
				PageInfo page = new PageInfo(list);
				return ChdJson.toJson(list, page.getTotal());
			}
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public <T> T queryByCode(Map<String, Object> entityMap)	throws DataAccessException {
		try {
			
			return pactTemplateMapper.queryByCode(entityMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
		
	}

	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap)	throws DataAccessException {
		
		try {
			
			return pactTemplateMapper.queryByUniqueness(entityMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {

		try {
			return pactTemplateMapper.queryExists(entityMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String savePactTemplateSet(List<Map<String, Object>> listVo)	throws DataAccessException {
		try {
			for(Map<String,Object> item : listVo){
				
				String code = StringTool.toPinyinShouZiMu(String.valueOf(item.get("pt_name")));
				
				item.put("pt_code", setNewPtCode(code));
							
				if("-1".equals(item.get("pt_rowid"))){ //添加
					
					pactTemplateMapper.add(item) ;
					
				}else{ //修改
					pactTemplateMapper.update(item);
				}
			}
	
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"保存失败.\",\"state\":\"false\"}");
		}
	}
	/**
	 * 方案编码生成（方案名称 首拼 + 3位流水号）
	 * @param code 方案名称 首拼
	 * @return
	 * @throws DataAccessException
	 */
	private String setNewPtCode(String code) throws DataAccessException {
		
		Map<String,Object> codeMap = new HashMap<String,Object>();
		
		codeMap.put("group_id", SessionManager.getGroupId());
		codeMap.put("hos_id", SessionManager.getHosId());
		codeMap.put("copy_code", SessionManager.getCopyCode());
		codeMap.put("pt_code", code);
		
		int num = pactTemplateMapper.queryMaxCodeNum(codeMap);
		
		String newCode = String.valueOf(num==0?1:num);
		
		for (int i = newCode.length() ; i < 3; i++) {
			newCode = "0" + newCode;
		}
		return code+newCode ;
	}

	@Override
	public String queryTemplateModular(Map<String, Object> mapVo) throws DataAccessException {
		try {
			List<Map<String,Object>> modularMap = pactTemplateMapper.queryTemplateModular(mapVo);
			
			return ChdJson.toJson(modularMap);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
		
	}

	@Override
	public String queryPactTemplateItem(Map<String, Object> entityMap) throws DataAccessException {

		try {
			SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");
			if (sysPage.getTotal() == -1) {
				List<Map<String,Object>> list = (List<Map<String, Object>>) pactTemplateMapper.queryPactTemplateItem(entityMap);
				return ChdJson.toJsonLower(list);
			} else {
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<Map<String, Object>> list = (List<Map<String, Object>>) pactTemplateMapper.queryPactTemplateItem(entityMap,rowBounds);
				@SuppressWarnings("rawtypes")
				PageInfo page = new PageInfo(list);
				return ChdJson.toJsonLower(list, page.getTotal());
			}
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String queryPactTemplateItemSet(Map<String, Object> entityMap)	throws DataAccessException {
		try {
			SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");
			if (sysPage.getTotal() == -1) {
				List<Map<String,Object>> list = (List<Map<String, Object>>) pactTemplateMapper.queryPactTemplateItemSet(entityMap);
				return ChdJson.toJsonLower(list);
			} else {
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<Map<String, Object>> list = (List<Map<String, Object>>) pactTemplateMapper.queryPactTemplateItemSet(entityMap,rowBounds);
				@SuppressWarnings("rawtypes")
				PageInfo page = new PageInfo(list);
				return ChdJson.toJsonLower(list, page.getTotal());
			}
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String savePactTemplateModular(List<Map<String, Object>> listVo)	throws DataAccessException {
		try {
			List<Map<String,Object>> addList =  new ArrayList<Map<String,Object>>();
			List<Map<String,Object>> updateList =  new ArrayList<Map<String,Object>>();
			for(Map<String,Object> item : listVo){
				
				if("0".equals(item.get("ptm_rowid"))){ //添加
					
					addList.add(item) ;
					
				}else{ //修改
					updateList.add(item) ;
				}
			}
			if(addList.size() > 0){
				pactTemplateModularMapper.addBatch(addList);
			}
			if(updateList.size() > 0){
				pactTemplateModularMapper.updateBatch(updateList);
			}
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"保存失败.\",\"state\":\"false\"}");
		}
	}

	@Override
	public String queryPactTemplateSet(Map<String, Object> entityMap) throws DataAccessException {
		try {
			SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");
			if (sysPage.getTotal() == -1) {
				List<Map<String,Object>> list = (List<Map<String, Object>>) pactTemplateMapper.queryPactTemplateSet(entityMap);
				return ChdJson.toJsonLower(list);
			} else {
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<Map<String, Object>> list = (List<Map<String, Object>>) pactTemplateMapper.queryPactTemplateSet(entityMap,rowBounds);
				@SuppressWarnings("rawtypes")
				PageInfo page = new PageInfo(list);
				return ChdJson.toJsonLower(list, page.getTotal());
			}
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}
	
	/**
	 * 根据 合同模板配置方案模块 id 删除 模块数据及后续设置数据
	 */
	@Override
	public String deletePactTemplateModular(Map<String, Object> mapVo) throws DataAccessException {
		try {
			pactTemplateGridtabItemMapper.delete(mapVo);
			pactTemplateGridetabMapper.delete(mapVo);
			
			pactTemplateSheetItemMapper.delete(mapVo);
			pactTemplateSheetMapper.delete(mapVo);
			
			pactTemplateModularMapper.delete(mapVo);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {
	
			logger.error(e.getMessage(), e);
	
			throw new SysException(e.getMessage(), e);
	
		}
	}

	@Override
	public String savePactTemplateSheetItemSet(Map<String, Object> mapVo) throws DataAccessException {
		try {
			
			Map<String,Object> sheetMap = new HashMap<String,Object>();
			
			sheetMap.put("group_id", SessionManager.getGroupId());
			sheetMap.put("hos_id", SessionManager.getHosId());
			sheetMap.put("copy_code", SessionManager.getCopyCode());
			sheetMap.put("pt_rowid", mapVo.get("pt_rowid"));
			sheetMap.put("ptm_rowid", mapVo.get("ptm_rowid"));
			sheetMap.put("pts_columns", mapVo.get("pts_columns"));
			if("-1".equals(mapVo.get("et_rowid"))){
				// 查询 表单元素配置 id
				int et_rowid = pactTemplateSheetMapper.queryNextSeq();
				
				sheetMap.put("et_rowid", et_rowid);
				
				pactTemplateSheetMapper.add(sheetMap);
			}else{
				sheetMap.put("et_rowid",mapVo.get("et_rowid"));
				
				pactTemplateSheetMapper.update(sheetMap);
			}
			
			// 存储按钮信息用 
			List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
			
			for ( String item : mapVo.get("buttonData").toString().split(",")) {
				Map<String, Object> buttonMap=new HashMap<String, Object>();
				buttonMap.put("group_id", SessionManager.getGroupId());
				buttonMap.put("hos_id", SessionManager.getHosId());
				buttonMap.put("copy_code", SessionManager.getCopyCode());
				buttonMap.put("pt_rowid", mapVo.get("pt_rowid"));
				buttonMap.put("ptm_rowid", mapVo.get("ptm_rowid"));
				buttonMap.put("ptm_showmode", mapVo.get("ptm_showmode"));
				
				buttonMap.put("et_rowid", sheetMap.get("et_rowid"));
				buttonMap.put("bb_button", item);
				buttonMap.put("bb_buttoncode", StringTool.toPinyinShouZiMu(item));//按钮编码  采用 按钮名称 首字母
				buttonMap.put("bb_position", mapVo.get("ptm_position"));
				
				buttonMap.put("bb_isavailable", 1);
				buttonMap.put("bb_xposition", mapVo.get("ptm_position_x"));
				buttonMap.put("bb_yposition", mapVo.get("ptm_position_y"));
				buttonMap.put("be_funtionid", null);
				
				listVo.add(buttonMap);
			}
			if(listVo.size()>0){
				//先删除 后添加
				pactTemplateButtonMapper.delete(sheetMap);
				pactTemplateButtonMapper.addBatch(listVo);
			}
			
			if (mapVo.get("detail") != null) {
				JSONArray json = JSONArray.parseArray(mapVo.get("detail").toString());
				
				// 存新增 配置项明细数据
				List<Map<String,Object>> addList =  new ArrayList<Map<String,Object>>();
				
				// 存新增 配置项明细数据
				List<Map<String,Object>> updateList =  new ArrayList<Map<String,Object>>();
				
				Iterator itData = json.iterator();
				
				while (itData.hasNext()) {
					JSONObject jsonObj = JSONObject.parseObject(itData.next().toString());
					Map<String,Object> detailMap = new HashMap<String,Object>();
					
					detailMap.put("group_id", SessionManager.getGroupId());
					detailMap.put("hos_id", SessionManager.getHosId());
					detailMap.put("copy_code", SessionManager.getCopyCode());
					detailMap.put("pt_rowid", mapVo.get("pt_rowid"));
					detailMap.put("ptm_rowid", mapVo.get("ptm_rowid"));
					detailMap.put("ptm_showmode", mapVo.get("ptm_showmode"));
					detailMap.put("et_rowid", sheetMap.get("et_rowid"));
					detailMap.put("be_itemid", jsonObj.get("be_itemid"));
					detailMap.put("be_code", jsonObj.get("be_code"));
					
					detailMap.put("be_name", jsonObj.get("be_name"));
					detailMap.put("be_row", jsonObj.get("be_row"));
					detailMap.put("be_columnno", jsonObj.get("be_columnno"));
					detailMap.put("be_alias", jsonObj.get("be_alias"));
					detailMap.put("be_isestablish", jsonObj.get("be_isestablish"));
					detailMap.put("be_ishide", jsonObj.get("be_ishide"));
					detailMap.put("be_isdisabled", jsonObj.get("be_isdisabled"));
					detailMap.put("be_establishattribute", jsonObj.get("be_establishattribute"));
					detailMap.put("cs_rowid", jsonObj.get("cs_rowid"));
					detailMap.put("cs_code", jsonObj.get("cs_code"));
					detailMap.put("cs_name", jsonObj.get("cs_code"));
					
					detailMap.put("be_enter", jsonObj.get("be_enter"));
					detailMap.put("be_background", jsonObj.get("be_background"));
					detailMap.put("be_fontcolor", jsonObj.get("be_fontcolor"));
					detailMap.put("be_align", jsonObj.get("be_align"));
					
					detailMap.put("be_width", jsonObj.get("be_width"));
					detailMap.put("be_rowspan", jsonObj.get("be_rowspan"));
					detailMap.put("be_colspan", jsonObj.get("be_colspan"));
					
					if(jsonObj.get("ptsi_rowid") != null && !"".equals(jsonObj.get("ptsi_rowid"))){//修改
						detailMap.put("ptsi_rowid",jsonObj.get("ptsi_rowid"));
						
						updateList.add(detailMap);
						
					}else{//新增
						addList.add(detailMap);
					}
				}
				
				if(addList.size()>0){
					pactTemplateSheetItemMapper.addBatch(addList) ;
				}
				
				if(updateList.size()>0){
					pactTemplateSheetItemMapper.updateBatch(updateList) ;
				}
			}
				
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"保存失败.\",\"state\":\"false\"}");
		}
	}

	@Override
	public Map<String, Object> queryPactTemplateSheet(Map<String, Object> mapVo) throws DataAccessException {
		try {
			Map<String,Object> sheetMap = pactTemplateSheetMapper.queryByCode(mapVo);
			
			return sheetMap;
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public List<Map<String, Object>> queryPactTemplateButtonInfo(Map<String, Object> mapVo) throws DataAccessException {
		try {
			List<Map<String, Object>> buttonInfo = pactTemplateButtonMapper.queryPactTemplateButtonInfo(mapVo);
			
			return buttonInfo;
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public List<Map<String, Object>> queryPactTemplateGrid(Map<String, Object> mapVo)	throws DataAccessException {
		try {
			List<Map<String,Object>> gridMap = (List<Map<String, Object>>) pactTemplateGridetabMapper.query(mapVo);
			
			return gridMap;
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String savePactTemplateGridItemSet(Map<String, Object> mapVo) throws DataAccessException {
		try {
			
			Map<String,Object> tabMap = new HashMap<String,Object>();
			
			tabMap.put("group_id", SessionManager.getGroupId());
			tabMap.put("hos_id", SessionManager.getHosId());
			tabMap.put("copy_code", SessionManager.getCopyCode());
			tabMap.put("pt_rowid", mapVo.get("pt_rowid"));
			tabMap.put("ptm_rowid", mapVo.get("ptm_rowid"));
			tabMap.put("gt_tab", mapVo.get("gt_tab"));
			
			tabMap.put("gt_istotalrow", mapVo.get("gt_istotalrow"));
			if("-1".equals(mapVo.get("et_rowid"))){
				// 查询 表格模块标签页配置  id
				int et_rowid = pactTemplateGridetabMapper.queryNextSeq();
				
				tabMap.put("et_rowid", et_rowid);
				
				pactTemplateGridetabMapper.add(tabMap);
			}else{
				tabMap.put("et_rowid",mapVo.get("et_rowid"));
				
				pactTemplateGridetabMapper.update(tabMap);
			}
			
			// 存储按钮信息用 
			List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
			
			for ( String item : mapVo.get("buttonData").toString().split(",")) {
				Map<String, Object> buttonMap=new HashMap<String, Object>();
				buttonMap.put("group_id", SessionManager.getGroupId());
				buttonMap.put("hos_id", SessionManager.getHosId());
				buttonMap.put("copy_code", SessionManager.getCopyCode());
				buttonMap.put("pt_rowid", mapVo.get("pt_rowid"));
				buttonMap.put("ptm_rowid", mapVo.get("ptm_rowid"));
				buttonMap.put("ptm_showmode", mapVo.get("ptm_showmode"));
				
				buttonMap.put("et_rowid", tabMap.get("et_rowid"));
				buttonMap.put("bb_button", item);
				buttonMap.put("bb_buttoncode", StringTool.toPinyinShouZiMu(item));//按钮编码  采用 按钮名称 首字母
				buttonMap.put("bb_position", mapVo.get("ptm_position"));
				
				buttonMap.put("bb_isavailable", 1);
				buttonMap.put("bb_xposition", mapVo.get("ptm_position_x"));
				buttonMap.put("bb_yposition", mapVo.get("ptm_position_y"));
				buttonMap.put("be_funtionid", null);
				
				listVo.add(buttonMap);
			}
			if(listVo.size()>0){
				//先删除 后添加
				pactTemplateButtonMapper.delete(tabMap);
				pactTemplateButtonMapper.addBatch(listVo);
			}
			
			if (mapVo.get("detail") != null) {
				JSONArray json = JSONArray.parseArray(mapVo.get("detail").toString());
				
				// 存新增 配置项明细数据
				List<Map<String,Object>> addList =  new ArrayList<Map<String,Object>>();
				
				// 存新增 配置项明细数据
				List<Map<String,Object>> updateList =  new ArrayList<Map<String,Object>>();
				
				Iterator itData = json.iterator();
				
				while (itData.hasNext()) {
					JSONObject jsonObj = JSONObject.parseObject(itData.next().toString());
					Map<String,Object> detailMap = new HashMap<String,Object>();
					
					
					detailMap.put("group_id", SessionManager.getGroupId());
					detailMap.put("hos_id", SessionManager.getHosId());
					detailMap.put("copy_code", SessionManager.getCopyCode());
					detailMap.put("pt_rowid", mapVo.get("pt_rowid"));
					detailMap.put("ptm_rowid", mapVo.get("ptm_rowid"));
					detailMap.put("ptm_showmode", mapVo.get("ptm_showmode"));
					detailMap.put("et_rowid", tabMap.get("et_rowid"));
					detailMap.put("be_itemid", jsonObj.get("be_itemid"));
					detailMap.put("be_code", jsonObj.get("be_code"));
					
					detailMap.put("be_name", jsonObj.get("be_name"));
					detailMap.put("be_tittlerow", jsonObj.get("be_tittlerow"));
					detailMap.put("be_columnno", jsonObj.get("be_columnno"));
					detailMap.put("be_alias", jsonObj.get("be_alias"));
					detailMap.put("be_startcolumn", jsonObj.get("be_startcolumn"));
					detailMap.put("be_occupycolumn", jsonObj.get("be_occupycolumn"));
					detailMap.put("be_isquery", jsonObj.get("be_isquery"));
					detailMap.put("be_isestablish", jsonObj.get("be_isestablish"));
					detailMap.put("be_ishide", jsonObj.get("be_ishide"));
					
					detailMap.put("be_establishattribute", jsonObj.get("be_establishattribute"));
					detailMap.put("be_function", jsonObj.get("be_function"));
					detailMap.put("cs_code", jsonObj.get("cs_code"));
					detailMap.put("cs_name", jsonObj.get("cs_code"));
					
					detailMap.put("be_enter", jsonObj.get("be_enter"));
					detailMap.put("be_background", jsonObj.get("be_background"));
					detailMap.put("be_fontcolor", jsonObj.get("be_fontcolor"));
					detailMap.put("be_align", jsonObj.get("be_align"));
					
					if(jsonObj.get("ptsi_rowid") != null && !"".equals(jsonObj.get("ptsi_rowid"))){//修改
						detailMap.put("ptsi_rowid",jsonObj.get("ptsi_rowid"));
						
						updateList.add(detailMap);
						
					}else{//新增
						addList.add(detailMap);
					}
				}
				
				if(addList.size()>0){
					pactTemplateGridtabItemMapper.addBatch(addList) ;
				}
				
				if(updateList.size()>0){
					pactTemplateGridtabItemMapper.updateBatch(updateList) ;
				}
			}
				
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"保存失败.\",\"state\":\"false\"}");
		}
	}

	@Override
	public String deletePactTemplateGridtab(Map<String, Object> mapVo) throws DataAccessException {
		try {
			pactTemplateGridtabItemMapper.delete(mapVo);
			pactTemplateButtonMapper.delete(mapVo);
			pactTemplateGridetabMapper.delete(mapVo);
			
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {
	
			logger.error(e.getMessage(), e);
	
			throw new SysException(e.getMessage(), e);
	
		}
	}

	@Override
	public Map<String, Object> queryPactTemplateGridTab(Map<String, Object> mapVo) throws DataAccessException {
		try {
			Map<String,Object> tabMap = pactTemplateGridetabMapper.queryByCode(mapVo);
			
			return tabMap;
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String queryCsCode(Map<String, Object> mapVo)
			throws DataAccessException {
		List<Map<String, Object>>  cscode=pactTemplateGridetabMapper.queryCsCode(mapVo);
		return JSON.toJSONString(cscode);
	}
	
}
