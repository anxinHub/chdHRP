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
import com.chd.hrp.hpm.dao.AphiCostitemMapper;
import com.chd.hrp.hpm.dao.AphiCostitemPercMapper;
import com.chd.hrp.hpm.dao.AphiCostitemSeqMapper;
import com.chd.hrp.hpm.entity.AphiCostitem;
import com.chd.hrp.hpm.entity.AphiCostitemPerc;
import com.chd.hrp.hpm.entity.AphiCostitemSeq;
import com.chd.hrp.hpm.service.AphiCostitemPercService;

/**
 * alfred
 */

@Service("aphiCostitemPercService")
public class AphiCostitemPercServiceImpl implements AphiCostitemPercService {

	private static Logger logger = Logger.getLogger(AphiCostitemPercServiceImpl.class);

	@Resource(name = "aphiCostitemPercMapper")
	private final AphiCostitemPercMapper aphiCostitemPercMapper = null;
	
	@Resource(name = "aphiCostitemMapper")
	private final AphiCostitemMapper aphiCostitemMapper = null;

	@Resource(name = "aphiCostitemSeqMapper")
	private final AphiCostitemSeqMapper aphiCostitemSeqMapper = null;
	/**
	 * 
	 */
	@Override
	public String addCostitemPerc(Map<String, Object> entityMap) throws DataAccessException {

		AphiCostitemPerc cp = queryCostitemPercByCode(entityMap);

		if (cp != null) {

			return "{\"error\":\"支出项目编码：" + entityMap.get("cost_item_code").toString() + "重复.\"}";

		}

		try {

			aphiCostitemPercMapper.addCostitemPerc(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addCostitemPerc\"}");
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addCostitemPerc\"}";

		}
	}

	/**
	 * 
	 */
	@Override
	public String queryCostitemPerc(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

		return JsonListBeanUtil.listToJson(aphiCostitemPercMapper.queryCostitemPerc(entityMap, rowBounds), sysPage.getTotal());

	}

	/**
	 * 
	 */
	@Override
	public AphiCostitemPerc queryCostitemPercByCode(Map<String, Object> entityMap) throws DataAccessException {

		return aphiCostitemPercMapper.queryCostitemPercByCode(entityMap);

	}

	/**
	 * 
	 */
	@Override
	public String deleteCostitemPerc(Map<String, Object> mapVo, String codes) throws DataAccessException {

		try {

			if (codes != null && !codes.equals("")) {

				String[] code_array = codes.split(",");

				for (String code : code_array) {

					mapVo.put("cost_item_code", code);

					aphiCostitemPercMapper.deleteCostitemPerc(mapVo);
				}

				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

			} else {

				return "{\"msg\":\"没有要删除的数据.\",\"state\":\"false\"}";

			}

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteCostitemPerc\"}");
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteCostitemPerc\"}";

		}
	}

	/**
	 * 
	 */
	@Override
	public String updateCostitemPerc(Map<String, Object> entityMap) throws DataAccessException {

		try {

			aphiCostitemPercMapper.updateCostitemPerc(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateIncomeitemPerc\"}");
			//return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateIncomeitemPerc\"}";

		}
	}

	@Override
	public String createCostitemPerc(Map<String, Object> entityMap) throws DataAccessException {

		// 先清空数据库
		aphiCostitemPercMapper.clearCostitemPerc(entityMap);

		// 生成数据
		List<AphiCostitem> allCostitem = aphiCostitemMapper.queryCostitem(entityMap);
		try {

			for (int i = 0; i < allCostitem.size(); i++) {

				AphiCostitem costitem = allCostitem.get(i);

				entityMap.put("cost_item_code", costitem.getCost_item_code());

				entityMap.put("is_acc", "1");

				entityMap.put("cost_percent", 0);

				aphiCostitemPercMapper.addCostitemPerc(entityMap);

			}

			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"生成操作失败 数据库异常\",\"state\":\"false\"}");
			//return "{\"msg\":\"生成失败.\",\"state\":\"false\"}";

		}

	}

	@Override
	public String fastCostitemPerc(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		String rbtnl = (String) entityMap.get("rbtnl");

		if ("all".equals(rbtnl)) {
			// 生成数据
			List<AphiCostitemPerc> allCostitemPerc = aphiCostitemPercMapper.queryCostitemPerc(entityMap);

			try {

				for (int i = 0; i < allCostitemPerc.size(); i++) {

					AphiCostitemPerc costitemPerc = allCostitemPerc.get(i);

					entityMap.put("cost_item_code", costitemPerc.getCost_item_code());

					// entityMap.put("imcome_percent",
					// entityMap.get("imcome_percent"));

					aphiCostitemPercMapper.updateCostitemPerc(entityMap);
				}

				return "{\"msg\":\"快速填充成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {
				logger.error(e.getMessage(), e);
				
				throw new SysException("{\"error\":\"快速填充操作失败 数据库异常\",\"state\":\"false\"}");
				//return "{\"msg\":\"快速填充失败.\",\"state\":\"false\"}";

			}
		} else {

			String codes = (String) entityMap.get("checkIds");

			String[] code_array = codes.split(",");

			try {

				for (int i = 0; i < code_array.length; i++) {

					entityMap.put("cost_item_code", code_array[i]);

					// entityMap.put("imcome_percent",
					// entityMap.get("imcome_percent"));

					aphiCostitemPercMapper.updateCostitemPerc(entityMap);

				}
				return "{\"msg\":\"快速填充成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {
				logger.error(e.getMessage(), e);
				
				throw new SysException("{\"error\":\"快速填充操作失败 数据库异常\",\"state\":\"false\"}");
				//return "{\"msg\":\"快速填充失败.\",\"state\":\"false\"}";

			}

		}

	}

	@Override
	public String addBatchCostitemPerc(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		int  state = aphiCostitemPercMapper.addBatchCostitemPerc(entityMap);
		 if(state>0){
			 return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
		 }
		 	return "{\"msg\":\"导入失败.\",\"state\":\"false\"}";
	}

	@Override
	public String hpmCostitemConfImport(Map<String, Object> map)throws DataAccessException {
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
					List<String> is_acc = item.get("是否参与核算");
					List<String> cost_percent = item.get("计提比例");
					
					if (cost_item_code.get(1) == null) {
						return "{\"warn\":\"支出项目编码单元格为空！\",\"state\":\"false\",\"row_cell\":\"" + cost_item_code.get(0) + "\"}";
					} else if(Character.isDigit(cost_item_code.get(1).charAt(0))){
						if(costitemSeqMap.get(cost_item_code.get(1)) == null){
							return "{\"warn\":\"支出项目编码不存在！\",\"state\":\"false\",\"row_cell\":\"" + cost_item_code.get(0) + "\"}";
						}
					}else if(costitemSeqMap.get(cost_item_code.get(1)) == null){
						return "{\"warn\":\"支出项目名称不存在！\",\"state\":\"false\",\"row_cell\":\"" + cost_item_code.get(0) + "\"}";
					}
					
					if(is_acc.get(1) == null){
						return "{\"warn\":\"是否参与核算为空！\",\"state\":\"false\",\"row_cell\":\"" + is_acc.get(0) + "\"}";
					}else if(Character.isDigit(is_acc.get(1).charAt(0))){
						is_acc.add(1,(is_acc.get(1).equals("1")?is_acc.get(1).replace("1","是"):is_acc.get(1).replace("0","否")));
					}
					
					if (cost_percent.get(1) == null) {
						return "{\"warn\":\"计提比例为空！\",\"state\":\"false\",\"row_cell\":\"" + cost_percent.get(0) + "\"}";
					}else{
						try{
							 Double.parseDouble((cost_percent.get(1)));
						 }catch(NumberFormatException e){
							 return "{\"warn\":\"" + cost_percent.get(1) + ",计提比例输入不合法,只能输入数字！\",\"state\":\"false\",\"row_cell\":\"" + cost_percent.get(0) + "\"}";
						  }
					}
					
					//,判断导入数据是否重复
					String key = cost_item_code.get(1);
					if(exitMap.get(key) != null ){
						err_sb.append(cost_item_code.get(1)+"支出项目,");
					}else{
						exitMap.put(key, key);
					}
					
					//添加数据Map中add到returnList
					Map<String,Object> returnMap = new HashMap<String,Object>();
					returnMap.put("group_id", SessionManager.getGroupId());
					returnMap.put("hos_id", SessionManager.getHosId());
					returnMap.put("copy_code", SessionManager.getCopyCode());
					returnMap.put("cost_item_code", cost_item_code.get(1));
					returnMap.put("is_acc", (is_acc.get(1).equals("是")?1:0));
					returnMap.put("cost_percent", cost_percent.get(1));
					
					returnList.add(returnMap);
					break;
				}
			}
			
			if (err_sb.toString().length() > 0 ) {
				return "{\"warn\":\"以下数据【" +err_sb.toString() + "】数据重复！\",\"state\":\"false\"}";
			}else{
				aphiCostitemPercMapper.addBatchCostitemPerc(returnList);
				return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
			}

		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		
	}
}
