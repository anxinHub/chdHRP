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
import com.chd.hrp.hpm.dao.AphiIncomeItemMapper;
import com.chd.hrp.hpm.dao.AphiIncomeitemPointMapper;
import com.chd.hrp.hpm.dao.AphiIncomeitemSeqMapper;
import com.chd.hrp.hpm.dao.AphiPointValueMapper;
import com.chd.hrp.hpm.entity.AphiIncomeItem;
import com.chd.hrp.hpm.entity.AphiIncomeitemPoint;
import com.chd.hrp.hpm.entity.AphiIncomeitemSeq;
import com.chd.hrp.hpm.service.AphiIncomeitemPointService;

/**
 * alfred
 */

@Service("aphiIncomeitemPointService")
public class AphiIncomeitemPointServiceImpl implements AphiIncomeitemPointService {

	private static Logger logger = Logger.getLogger(AphiIncomeitemPointServiceImpl.class);

	@Resource(name = "aphiIncomeitemPointMapper")
	private final AphiIncomeitemPointMapper aphiIncomeitemPointMapper = null;
	
	@Resource(name = "aphiIncomeItemMapper")
	private final AphiIncomeItemMapper aphiIncomeItemMapper = null;
	
	@Resource(name = "aphiPointValueMapper")
	private final AphiPointValueMapper aphiPointValueMapper = null;
	
	@Resource(name = "aphiIncomeitemSeqMapper")
	private final AphiIncomeitemSeqMapper aphiIncomeitemSeqMapper = null;
	
	/**
	 * 
	 */
	@Override
	public String addIncomeitemPoint(Map<String, Object> entityMap) throws DataAccessException {
		
		AphiIncomeitemPoint iip = queryIncomeitemPointByCode(entityMap);
		
		if(iip !=null){
			
			return "{\"error\":\"收入项目编码：" + entityMap.get("income_item_code").toString() + "重复.\"}";
			
		}
		
		try{
		
			aphiIncomeitemPointMapper.addIncomeitemPoint(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			
		}catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addIncomeitemPoint\"}");
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addIncomeitemPoint\"}";
						
		}

	}

	/**
	 * 
	 */
	@Override
	public String queryIncomeitemPoint(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		return JsonListBeanUtil.listToJson(aphiIncomeitemPointMapper.queryIncomeitemPoint(entityMap, rowBounds), sysPage.getTotal());
		
	}

	/**
	 * 
	 */
	@Override
	public AphiIncomeitemPoint queryIncomeitemPointByCode(Map<String, Object> entityMap) throws DataAccessException {
		
		return aphiIncomeitemPointMapper.queryIncomeitemPointByCode(entityMap);
		
	}

	/**
	 * 
	 */
	@Override
	public String deleteIncomeitemPoint(Map<String,Object> mapVo,String codes) throws DataAccessException {

		
		try{
			
			if (codes != null && !codes.equals("")) {
				
				String[] code_array = codes.split(",");
				
				for (String code : code_array) {
					
					mapVo.put("income_item_code", code);
					
					aphiIncomeitemPointMapper.deleteIncomeitemPoint(mapVo);
				}
				
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
				
			} else {
				
				return "{\"msg\":\"没有要删除的数据.\",\"state\":\"false\"}";
				
			}
			
		}catch(Exception e){
			
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteIncomeitemPoint\"}");
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteIncomeitemPoint\"}";
			
		}
	}

	/**
	 * 
	 */
	@Override
	public String updateIncomeitemPoint(Map<String, Object> entityMap) throws DataAccessException {
		
		try{
			
			aphiIncomeitemPointMapper.updateIncomeitemPoint(entityMap);
			
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
			
		}catch(Exception e){
			
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateIncomeitemPoint\"}");
			//return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateIncomeitemPoint\"}";
			
		}
	}
	
	@Override
	public String createIncomeitemPoint(Map<String, Object> entityMap) throws DataAccessException {

		// 先清空数据库
		aphiIncomeitemPointMapper.clearIncomeitemPoint(entityMap);

		// 生成数据
		List<AphiIncomeItem> allIncomeItem = aphiIncomeItemMapper.queryIncomeItem(entityMap);
		try {

			for (int i = 0; i < allIncomeItem.size(); i++) {

				AphiIncomeItem incomeItem = allIncomeItem.get(i);

				entityMap.put("income_item_code", incomeItem.getIncome_item_code());

				entityMap.put("is_acc", "1");

				//entityMap.put("cost_percent", "");

				aphiIncomeitemPointMapper.addIncomeitemPoint(entityMap);

			}

			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"生成失败 数据库异常\",\"state\":\"false\"}");
			//return "{\"msg\":\"生成失败.\",\"state\":\"false\"}";

		}

	}

	@Override
	public String fastIncomeitemPoint(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		String rbtnl = (String) entityMap.get("rbtnl");

		if ("all".equals(rbtnl)) {
			// 生成数据
			List<AphiIncomeitemPoint> allIncomeitemPoint = aphiIncomeitemPointMapper.queryIncomeitemPoint(entityMap);

			try {

				for (int i = 0; i < allIncomeitemPoint.size(); i++) {

					AphiIncomeitemPoint incomeitemPoint = allIncomeitemPoint.get(i);

					entityMap.put("income_item_code", incomeitemPoint.getIncome_item_code());

					// entityMap.put("imcome_percent",
					// entityMap.get("imcome_percent"));

					aphiIncomeitemPointMapper.updateIncomeitemPoint(entityMap);
				}

				return "{\"msg\":\"快速填充成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {
				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\"快速填充失败 数据库异常\",\"state\":\"false\"}");
				//return "{\"msg\":\"快速填充失败.\",\"state\":\"false\"}";

			}
		} else {

			String codes = (String) entityMap.get("checkIds");

			String[] code_array = codes.split(",");

			try {

				for (int i = 0; i < code_array.length; i++) {

					entityMap.put("income_item_code", code_array[i]);

					// entityMap.put("imcome_percent",
					// entityMap.get("imcome_percent"));

					aphiIncomeitemPointMapper.updateIncomeitemPoint(entityMap);

				}
				return "{\"msg\":\"快速填充成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {
				logger.error(e.getMessage(), e);
				return "{\"msg\":\"快速填充失败.\",\"state\":\"false\"}";

			}

		}

	}

	@Override
	public String addBatchIncomeitemPoint(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		int state = aphiIncomeitemPointMapper.addBatchIncomeitemPoint(entityMap);
		if(state>0){
			return  "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
		}
		return  "{\"msg\":\"导入失败.\",\"state\":\"false\"}";
	}

	@Override
	public String addIncomeitemPointValue(Map<String, Object> entityMap) throws DataAccessException {
		
		try{
			
			aphiPointValueMapper.deletePointValue(entityMap);
			
			aphiPointValueMapper.addPointValue(entityMap);
			
			return  "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
					
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败 数据库异常\",\"state\":\"false\"}");
			//return  "{\"msg\":\"保存失败.\",\"state\":\"false\"}";
			
		}
		
	}

	@Override
	public String hpmIncomeitemPointImport(Map<String, Object> map)throws DataAccessException {
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
					List<String> imcome_point = item.get("计提点数");
					
					
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
					
					
					if (imcome_point.get(1) == null) {
						return "{\"warn\":\"计提点数为空！\",\"state\":\"false\",\"row_cell\":\"" + imcome_point.get(0) + "\"}";
					}else{
						try{
							 Double.parseDouble((imcome_point.get(1)));
						 }catch(NumberFormatException e){
							 return "{\"warn\":\"" + imcome_point.get(1) + ",计提点数输入不合法,只能输入数字！\",\"state\":\"false\",\"row_cell\":\"" + imcome_point.get(0) + "\"}";
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
					returnMap.put("imcome_point", imcome_point.get(1));
					
					returnList.add(returnMap);
					break;
				}
			}
			
			if (err_sb.toString().length() > 0 ) {
				return "{\"warn\":\"以下数据【" +err_sb.toString() + "】数据重复！\",\"state\":\"false\"}";
			}else{
				aphiIncomeitemPointMapper.addBatchIncomeitemPoint(returnList);
				return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
}
