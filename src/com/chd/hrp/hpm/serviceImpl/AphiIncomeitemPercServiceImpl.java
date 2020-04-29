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
import com.chd.hrp.hpm.dao.AphiDeptDictMapper;
import com.chd.hrp.hpm.dao.AphiIncomeItemMapper;
import com.chd.hrp.hpm.dao.AphiIncomeitemPercMapper;
import com.chd.hrp.hpm.dao.AphiIncomeitemSeqMapper;
import com.chd.hrp.hpm.entity.AphiIncomeItem;
import com.chd.hrp.hpm.entity.AphiIncomeitemPerc;
import com.chd.hrp.hpm.entity.AphiIncomeitemSeq;
import com.chd.hrp.hpm.service.AphiIncomeitemPercService;

/**
 * alfred
 */

@Service("aphiIncomeitemPercService")
public class AphiIncomeitemPercServiceImpl implements AphiIncomeitemPercService {

	private static Logger logger = Logger.getLogger(AphiIncomeitemPercServiceImpl.class);

	@Resource(name = "aphiIncomeitemPercMapper")
	private final AphiIncomeitemPercMapper aphiIncomeitemPercMapper = null;
	
	@Resource(name = "aphiIncomeItemMapper")
	private final AphiIncomeItemMapper aphiIncomeItemMapper = null;
	
	@Resource(name = "aphiIncomeitemSeqMapper")
	private final AphiIncomeitemSeqMapper aphiIncomeitemSeqMapper = null;
	
	@Resource(name = "aphiDeptDictMapper")
	private final AphiDeptDictMapper aphiDeptDictMapper = null;

	/**
	 * 
	 */
	@Override
	public String addIncomeitemPerc(Map<String, Object> entityMap) throws DataAccessException {

		AphiIncomeitemPerc ip = queryIncomeitemPercByCode(entityMap);

		if (ip != null) {

			return "{\"error\":\"收入项目编码：" + entityMap.get("income_item_code").toString() + "重复.\"}";

		}

		try {

			aphiIncomeitemPercMapper.addIncomeitemPerc(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addIncomeitemPerc\"}";

		}
	}

	/**
	 * 
	 */
	@Override
	public String queryIncomeitemPerc(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

		return JsonListBeanUtil.listToJson(aphiIncomeitemPercMapper.queryIncomeitemPerc(entityMap, rowBounds), sysPage.getTotal());

	}

	/**
	 * 
	 */
	@Override
	public AphiIncomeitemPerc queryIncomeitemPercByCode(Map<String, Object> entityMap) throws DataAccessException {

		return aphiIncomeitemPercMapper.queryIncomeitemPercByCode(entityMap);

	}

	/**
	 * 
	 */
	@Override
	public String deleteIncomeitemPerc(Map<String, Object> mapVo, String codes) throws DataAccessException {

		try {

			if (codes != null && !codes.equals("")) {

				String[] code_array = codes.split(",");

				for (String code : code_array) {

					mapVo.put("income_item_code", code);

					aphiIncomeitemPercMapper.deleteIncomeitemPerc(mapVo);
				}

				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

			} else {

				return "{\"msg\":\"没有要删除的数据.\",\"state\":\"false\"}";

			}

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteIncomeitemPerc\"}";

		}
	}

	/**
	 * 
	 */
	@Override
	public String updateIncomeitemPerc(Map<String, Object> entityMap) throws DataAccessException {

		try {

			aphiIncomeitemPercMapper.updateIncomeitemPerc(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateIncomeitemPerc\"}";

		}
	}

	@Override
	public String createIncomeitemPerc(Map<String, Object> entityMap) throws DataAccessException {

		// 先清空数据库
		aphiIncomeitemPercMapper.clearIncomeitemPerc(entityMap);

		// 生成数据
		List<AphiIncomeItem> allIncomeItem = aphiIncomeItemMapper.queryIncomeItem(entityMap);
		try {

			for (int i = 0; i < allIncomeItem.size(); i++) {

				AphiIncomeItem incomeItem = allIncomeItem.get(i);

				entityMap.put("income_item_code", incomeItem.getIncome_item_code());
				
				entityMap.put("is_acc", "1");

				entityMap.put("imcome_percent", "");

				aphiIncomeitemPercMapper.addIncomeitemPerc(entityMap);
				
			}

			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			return "{\"msg\":\"生成失败.\",\"state\":\"false\"}";

		}

	}

	@Override
	public String fastIncomeitemPerc(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		String rbtnl = (String) entityMap.get("rbtnl");

		if ("all".equals(rbtnl)) {
			// 生成数据
			List<AphiIncomeitemPerc> allIncomeitemPerc = aphiIncomeitemPercMapper.queryIncomeitemPerc(entityMap);
			
			try {

				for (int i = 0; i < allIncomeitemPerc.size(); i++) {

					AphiIncomeitemPerc incomeitemPerc = allIncomeitemPerc.get(i);

					entityMap.put("income_item_code",incomeitemPerc.getIncome_item_code());

					//entityMap.put("imcome_percent", entityMap.get("imcome_percent"));

					aphiIncomeitemPercMapper.updateIncomeitemPerc(entityMap);
				}

				return "{\"msg\":\"快速填充成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				return "{\"msg\":\"快速填充失败.\",\"state\":\"false\"}";

			}
		} else {

			String codes = (String) entityMap.get("checkIds");

			String[] code_array = codes.split(",");

			try {

				for (int i = 0; i < code_array.length; i++) {

					entityMap.put("income_item_code", code_array[i]);

					//entityMap.put("imcome_percent", entityMap.get("imcome_percent"));

					aphiIncomeitemPercMapper.updateIncomeitemPerc(entityMap);

				}
				return "{\"msg\":\"快速填充成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				return "{\"msg\":\"快速填充失败.\",\"state\":\"false\"}";

			}

		}

	}

	@Override
	public String addBatchIncomeitemPerc(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		 int  state = aphiIncomeitemPercMapper.addBatchIncomeitemPerc(entityMap);
		 if(state>0){
			 return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
		 }
		 	return "{\"msg\":\"导入失败.\",\"state\":\"false\"}";
	}
	
	//导入
	@Override
	public String hpmIncomeitemPercImport(Map<String, Object> map)throws DataAccessException {
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
			
			//查询收入项目字典 List
			List<AphiIncomeitemSeq> incomeItemSeqList = aphiIncomeitemSeqMapper.queryIncomeitemSeq(entityMap);
			
			//以键值对的形式存储,用于判断是否存在收入项目
			Map<String,AphiIncomeitemSeq> incomeItemSeqMap = new HashMap<String, AphiIncomeitemSeq>();
			
			//用于存储传的数据值,判断数据是否重复
			Map<String,String> exitMap = new HashMap<String,String>();
			
			//存储要添加保存的数据List
			List<Map<String,Object>> returnList = new ArrayList<Map<String,Object>>();
			
			//用于记录重复数据
			StringBuffer err_sb = new StringBuffer();
			
			//收入项目List 放入Map 键 income_item_code 值 AphiIncomeItemSeq
			for(AphiIncomeitemSeq incomeItemSeq :incomeItemSeqList){
				incomeItemSeqMap.put(incomeItemSeq.getIncome_item_code(), incomeItemSeq);
				incomeItemSeqMap.put(incomeItemSeq.getIncome_item_name(), incomeItemSeq);
			}
			
			
			for(Map<String, List<String>> item:liData){
				for(String st :item.keySet()){
					if(item.get(st).get(1) == null){
						break;
					}
					
					List<String> income_item_code = item.get("收入项目编码");
					List<String> is_acc = item.get("是否参与核算");
					List<String> income_percent = item.get("计提比例");
					
					
					if (income_item_code.get(1) == null) {
						return "{\"warn\":\"收入项目编码单元格为空！\",\"state\":\"false\",\"row_cell\":\"" + income_item_code.get(0) + "\"}";
					} else if(Character.isDigit(income_item_code.get(1).charAt(0))){
						if(incomeItemSeqMap.get(income_item_code.get(1)) == null){
							return "{\"warn\":\"收入项目编码不存在！\",\"state\":\"false\",\"row_cell\":\"" + income_item_code.get(0) + "\"}";
						}
					}else if(incomeItemSeqMap.get(income_item_code.get(1)) == null){
						return "{\"warn\":\"收入项目名称不存在！\",\"state\":\"false\",\"row_cell\":\"" + income_item_code.get(0) + "\"}";
					}
					
					if(is_acc.get(1) == null){
						return "{\"warn\":\"是否参与核算为空！\",\"state\":\"false\",\"row_cell\":\"" + is_acc.get(0) + "\"}";
					}else if(Character.isDigit(is_acc.get(1).charAt(0))){
						is_acc.add(1,(is_acc.get(1).equals("1")?is_acc.get(1).replace("1","是"):is_acc.get(1).replace("0","否")));
					}
					
					if (income_percent.get(1) == null) {
						return "{\"warn\":\"计提比例为空！\",\"state\":\"false\",\"row_cell\":\"" + income_percent.get(0) + "\"}";
					}else{
						try{
							 Double.parseDouble((income_percent.get(1)));
						 }catch(NumberFormatException e){
							 return "{\"warn\":\"" + income_percent.get(1) + ",计提比例输入不合法,只能输入数字！\",\"state\":\"false\",\"row_cell\":\"" + income_percent.get(0) + "\"}";
						  }
					}
					
					//
					String key = income_item_code.get(1);
					if(exitMap.get(key) != null ){
						err_sb.append(income_item_code.get(1)+"收入项目");
					}else{
						exitMap.put(key, key);
					}
					
					//添加数据Map中add到returnList
					Map<String,Object> returnMap = new HashMap<String,Object>();
					returnMap.put("group_id", SessionManager.getGroupId());
					returnMap.put("hos_id", SessionManager.getHosId());
					returnMap.put("copy_code", SessionManager.getCopyCode());
					returnMap.put("income_item_code", income_item_code.get(1));
					returnMap.put("is_acc", (is_acc.get(1).equals("是")?1:0));
					returnMap.put("income_percent", income_percent.get(1));
					
					returnList.add(returnMap);
					break;
				}
			}
			
			
			if (err_sb.toString().length() > 0 ) {
				return "{\"warn\":\"以下数据【" +err_sb.toString() + "】数据重复！\",\"state\":\"false\"}";
			}else{
				aphiIncomeitemPercMapper.addBatchIncomeitemPerc(returnList);
				return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
}
