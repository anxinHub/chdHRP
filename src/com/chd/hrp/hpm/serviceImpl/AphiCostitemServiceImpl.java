package com.chd.hrp.hpm.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.JsonListBeanUtil;
import com.chd.base.util.SpreadTableJSUtil;
import com.chd.base.util.StringTool;
import com.chd.hrp.hpm.dao.AphiCostitemConfMapper;
import com.chd.hrp.hpm.dao.AphiCostitemMapper;
import com.chd.hrp.hpm.dao.AphiCostitemPercMapper;
import com.chd.hrp.hpm.dao.AphiCostitemSeqMapper;
import com.chd.hrp.hpm.entity.AphiCostitem;
import com.chd.hrp.hpm.entity.AphiCostitemSeq;
import com.chd.hrp.hpm.entity.AphiDeptDict;
import com.chd.hrp.hpm.service.AphiCostitemService;

/**
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("aphiCostitemService")
public class AphiCostitemServiceImpl implements AphiCostitemService {

	private static Logger logger = Logger.getLogger(AphiCostitemServiceImpl.class);

	@Resource(name = "aphiCostitemMapper")
	private final AphiCostitemMapper aphiCostitemMapper = null;

	@Resource(name = "aphiCostitemPercMapper")
	private AphiCostitemPercMapper aphiCostitemPercMapper = null;

	@Resource(name = "aphiCostitemConfMapper")
	private AphiCostitemConfMapper aphiCostitemConfMapper = null;
	
	@Resource(name = "aphiCostitemSeqMapper")
	private final AphiCostitemSeqMapper aphiCostitemSeqMapper = null;

	/**
	 * 
	 */
	@Override
	public String addCostitem(Map<String, Object> entityMap) throws DataAccessException {

		try {

			AphiCostitem costitem = aphiCostitemMapper.queryCostitemByCode(entityMap);

			if (costitem != null) {

				return "{\"msg\":\"编码重复.\",\"state\":\"false\"}";
			}

			String cost_iitem_name = entityMap.get("cost_iitem_name").toString();

			entityMap.put("spell_code", StringTool.toPinyinShouZiMu(cost_iitem_name));

			entityMap.put("wbx_code", StringTool.toWuBi(cost_iitem_name));

			aphiCostitemMapper.addCostitem(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码  addCostitem\"}";

		}
	}

	/**
	 * 
	 */
	@Override
	public String queryCostitem(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

		return JsonListBeanUtil.listToJson(aphiCostitemMapper.queryCostitem(entityMap, rowBounds), sysPage.getTotal());

	}

	/**
	 * 
	 */
	@Override
	public AphiCostitem queryCostitemByCode(Map<String, Object> entityMap) throws DataAccessException {

		return aphiCostitemMapper.queryCostitemByCode(entityMap);

	}

	/**
	 * 
	 */
	@Override
	public String deleteCostitem(Map<String, Object> mapVo, String codes) throws DataAccessException {
		if (mapVo.get("user_id") == null) {
			mapVo.put("user_id", SessionManager.getUserId());
		}
		/*
		 * 删除：勾选数据行，点删除按钮，进行数据删除，
		 * 在【支出项目配置表Aphi_CostItem_Conf】或【支出项目计提比例Aphi_CostItem_Perc
		 * 】已存在该数据，不能删除。并给予提示，收入项目配置中已引用此项目，不能删除！
		 */

		try {

			if (codes != null && !codes.equals("")) {

				String[] code_codeArray = codes.split(",");

				for (String code : code_codeArray) {

					mapVo.put("cost_item_code", code);

					List listPerc = aphiCostitemPercMapper.queryCostitemPerc(mapVo);

					if (listPerc.size() > 0) {

						return "{\"msg\":\"删除失败【支出项目计提比例Aphi_CostItem_Perc】中已引用此项目，不能删除！.\",\"state\":\"true\"}";

					}

					List listConf = aphiCostitemConfMapper.queryCostitemConf(mapVo);

					if (listConf.size() > 0) {

						return "{\"msg\":\"删除失败【支出项目配置表Aphi_CostItem_Conf】中已引用此项目，不能删除！.\",\"state\":\"true\"}";

					}

					aphiCostitemMapper.deleteCostitem(mapVo);
				}

				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

			} else {

				return "{\"msg\":\"没有要删除的数据.\",\"state\":\"false\"}";

			}

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteCostitem\"}";

		}

	}

	/**
	 * 
	 */
	@Override
	public String updateCostitem(Map<String, Object> entityMap) throws DataAccessException {

		try {

			String cost_iitem_name = entityMap.get("cost_iitem_name").toString();

			entityMap.put("spell_code", StringTool.toPinyinShouZiMu(cost_iitem_name));

			entityMap.put("wbx_code", StringTool.toWuBi(cost_iitem_name));

			aphiCostitemMapper.updateCostitem(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostitem\"}";

		}
	}

	@Override
	public String addBatchCostitem(List<Map<String, Object>> entityMap) throws DataAccessException {
		int state = aphiCostitemMapper.addBatchCostitem(entityMap);
		if (state > 0) {
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}
		return "{\"msg\":\"添加失败.\",\"state\":\"false\"}";
	}

	//导入
	@Override
	public String hpmcostitemImport(Map<String, Object> map)throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			String content=map.get("content").toString();
			
			List<Map<String,List<String>>> liData=SpreadTableJSUtil.toListMap(content,1);
			
			if(liData==null || liData.size()==0){
				return "{\"error\":\"没有数据！\",\"state\":\"false\"}";
			}
			
			//new Map查询导入时对应数据信息
			Map<String, Object> entityMap=new HashMap<String,Object>();
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("copy_code", SessionManager.getCopyCode());
			entityMap.put("is_stop", "0");// 查询未停用
			
			//查询支出项目字典List
			List<AphiCostitemSeq> costitemSeqList = aphiCostitemSeqMapper.queryCostitemSeq(entityMap);
			
			List<AphiCostitem> aphiCostitemList  = aphiCostitemMapper.queryCostitem(entityMap);
			
			//以键值对的形式存储,用于判断是否存在支出项目
			Map<String,AphiCostitemSeq> costitemSeqMap = new HashMap<String, AphiCostitemSeq>();
			
			//用于存储传的数据值,判断数据是否重复
			Map<String,String> exitMap = new HashMap<String,String>();
			
			//存储要添加保存的数据List
			List<Map<String,Object>> returnList = new ArrayList<Map<String,Object>>();
			
			//用于记录重复数据
			StringBuffer err_sb = new StringBuffer();
			
			////收入项目List 放入Map 键 cost_item_code 值 AphiCostitemSeq
			for(AphiCostitemSeq costItemSeq:costitemSeqList){
				costitemSeqMap.put(costItemSeq.getCost_item_code(), costItemSeq);
				costitemSeqMap.put(costItemSeq.getCost_iitem_name(), costItemSeq);
			}
			
			for(Map<String, List<String>> item:liData){
				for(String st :item.keySet()){
					if(item.get(st).get(1) == null){
						break;
					}
					
					List<String> cost_item_code = item.get("支出项目编码");
					List<String> cost_iitem_name = item.get("支出项目名称");
					List<String> cost_type_code = item.get("支出项目分类编码");
					
					
					if (cost_item_code.get(1) == null) {
						return "{\"warn\":\"支出项目编码为空！\",\"state\":\"false\",\"row_cell\":\"" + cost_item_code.get(0) + "\"}";
					} 
					
					if(cost_iitem_name.get(1) == null){
						return "{\"warn\":\"支出项目名称为空！\",\"state\":\"false\",\"row_cell\":\"" + cost_iitem_name.get(0) + "\"}";
					}
					if(cost_type_code.get(1) == null) {
						return "{\"warn\":\"支出项目分类编码为空！\",\"state\":\"false\",\"row_cell\":\"" + cost_type_code.get(0) + "\"}";
					}
					
					for(AphiCostitem aphiCostitem :aphiCostitemList){
						if(cost_item_code.get(1).equals(aphiCostitem.getCost_item_code())){
							return "{\"warn\":\"支出项目编码已经存在！\",\"state\":\"false\",\"row_cell\":\"" + cost_item_code.get(0) + "\"}";
						}else if(cost_iitem_name.get(1).equals(aphiCostitem.getCost_iitem_name())){
							return "{\"warn\":\"支出项目名称已经存在！\",\"state\":\"false\",\"row_cell\":\"" + cost_iitem_name.get(0) + "\"}";
						}
					}
					/*
					if (is_stop.get(1) == null) {
						return "{\"warn\":\"是否停用为空！\",\"state\":\"false\",\"row_cell\":\"" + is_stop.get(0) + "\"}";
					}else if(Character.isDigit(is_stop.get(1).charAt(0))){
						is_stop.add(1,(is_stop.get(1).equals("1")?is_stop.get(1).replace("1","是"):is_stop.get(1).replace("0","否")));
					}
					
					//判断可为空字段
					List<String> data_source = new ArrayList<String>();
					if(item.get("数据来源") == null){
						data_source.add("");
						data_source.add(1,"");
					}else{
						data_source = item.get("数据来源");
					}*/
					
					//判断导入数据是否重复
					String key = cost_item_code.get(1)+cost_iitem_name.get(1);
					if(exitMap.get(key) != null ){
						err_sb.append(cost_item_code.get(1)+"支出项目编码," + cost_iitem_name.get(1)+"支出项目名称");
					}else{
						exitMap.put(key, key);
					}
					
					//添加数据Map中add到returnList
					Map<String,Object> returnMap = new HashMap<String,Object>();
					returnMap.put("group_id", SessionManager.getGroupId());
					returnMap.put("hos_id", SessionManager.getHosId());
					returnMap.put("copy_code", SessionManager.getCopyCode());
					returnMap.put("cost_item_code", cost_item_code.get(1));
					returnMap.put("cost_iitem_name", cost_iitem_name.get(1));
					returnMap.put("cost_type_code", cost_type_code.get(1));
					returnMap.put("data_source", "");
					returnMap.put("spell_code", StringTool.toPinyinShouZiMu(cost_iitem_name.get(1)));
					returnMap.put("wbx_code", StringTool.toWuBi(cost_iitem_name.get(1)));
					returnMap.put("is_stop", 0);
					returnList.add(returnMap);
					break;
				}
			}	
			
			if (err_sb.toString().length() > 0 ) {
				return "{\"warn\":\"以下数据【" +err_sb.toString() + "】数据重复！\",\"state\":\"false\"}";
			}else{
				aphiCostitemMapper.addBatchCostitem(returnList);
				return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
}
