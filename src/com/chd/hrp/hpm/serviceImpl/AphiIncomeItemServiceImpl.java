/** 
 * 2015-2-2  
 * author:alfred
 */

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
import com.chd.hrp.hpm.dao.AphiDeptDictMapper;
import com.chd.hrp.hpm.dao.AphiIncomeItemMapper;
import com.chd.hrp.hpm.dao.AphiIncomeitemConfMapper;
import com.chd.hrp.hpm.dao.AphiIncomeitemPercMapper;
import com.chd.hrp.hpm.dao.AphiIncomeitemSeqMapper;
import com.chd.hrp.hpm.entity.AphiDeptDict;
import com.chd.hrp.hpm.entity.AphiIncomeItem;
import com.chd.hrp.hpm.entity.AphiIncomeitemSeq;
import com.chd.hrp.hpm.service.AphiIncomeItemService;

@Service("aphiIncomeItemService")
public class AphiIncomeItemServiceImpl implements AphiIncomeItemService {

	private static Logger logger = Logger.getLogger(AphiIncomeItemServiceImpl.class);

	@Resource(name = "aphiIncomeItemMapper")
	private AphiIncomeItemMapper aphiIncomeItemMapper = null;

	@Resource(name = "aphiIncomeitemPercMapper")
	private AphiIncomeitemPercMapper aphiIncomeitemPercMapper = null;

	@Resource(name = "aphiIncomeitemConfMapper")
	private AphiIncomeitemConfMapper aphiIncomeitemConfMapper = null;
	
	@Resource(name = "aphiDeptDictMapper")
	private final AphiDeptDictMapper aphiDeptDictMapper = null;
	
	@Resource(name = "aphiIncomeitemSeqMapper")
	private final AphiIncomeitemSeqMapper aphiIncomeitemSeqMapper = null;

	@Override
	public String queryIncomeitem(Map<String, Object> mapVo) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) mapVo.get("sysPage");

		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

		return JsonListBeanUtil.listToJson(aphiIncomeItemMapper.queryIncomeItem(mapVo, rowBounds), sysPage.getTotal());

	}

	@Override
	public String addIncomeitem(Map<String, Object> mapVo) throws DataAccessException {

		AphiIncomeItem ii = queryIncomeItemByCode(mapVo);

		if (ii != null) {

			return "{\"error\":\"收入项目编码：" + mapVo.get("income_item_code").toString() + "重复.\"}";

		}

		try {

			String income_item_name = mapVo.get("income_item_name").toString();

			mapVo.put("spell_code", StringTool.toPinyinShouZiMu(income_item_name));

			mapVo.put("wbx_code", StringTool.toWuBi(income_item_name));

			// for (Map.Entry<String, Object> entry : mapVo.entrySet()) {
			//
			// System.out.println("serviceKey= " + entry.getKey() +
			// " serviceValue= " + entry.getValue());
			//
			// }

			aphiIncomeItemMapper.addIncomeItem(mapVo);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addIncomeitem\"}";

		}

	}

	@Override
	public AphiIncomeItem queryIncomeItemByCode(Map<String, Object> mapVo) throws DataAccessException {

		AphiIncomeItem ii = aphiIncomeItemMapper.queryIncomeItemByCode(mapVo);

		return ii;
	}

	@Override
	public String deleteIncomeItem(Map<String, Object> mapVo, String income_item_codes) throws DataAccessException {

		/*
		 * 判断业务需求 勾选数据行，点删除按钮，进行数据删除，
		 * 在【收入项目配置Aphi_IncomeItem_Conf】或【收入项目表计提比例Aphi_IncomeItem_Perc】已存在该数据，
		 * 不能删除。并给予提示，
		 * 【收入项目配置Aphi_IncomeItem_Conf】或【收入项目表计提比例Aphi_IncomeItem_Perc
		 * 】中已引用此项目，不能删除！
		 */

		try {
			if (mapVo.get("user_id") == null) {
				mapVo.put("user_id", SessionManager.getUserId());
			}
			if (income_item_codes != null && !income_item_codes.equals("")) {

				String[] income_item_codeArray = income_item_codes.split(",");

				for (String code : income_item_codeArray) {

					mapVo.put("income_item_code", code);

					List listPerc = aphiIncomeitemPercMapper.queryIncomeitemPerc(mapVo);

					if (listPerc.size() > 0) {

						return "{\"msg\":\"删除失败【收入项目表计提比例Aphi_IncomeItem_Perc】中已引用此项目，不能删除！.\",\"state\":\"true\"}";

					}

					List listConf = aphiIncomeitemConfMapper.queryIncomeitemConf(mapVo);

					if (listConf.size() > 0) {

						return "{\"msg\":\"删除失败【收入项目配置Aphi_IncomeItem_Conf】中已引用此项目，不能删除！.\",\"state\":\"true\"}";

					}

					aphiIncomeItemMapper.deleteIncomeItem(mapVo);
				}

				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

			} else {

				return "{\"msg\":\"没有要删除的数据.\",\"state\":\"false\"}";

			}

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteIncomeItem\"}";

		}

	}

	@Override
	public String updateIncomeItem(Map<String, Object> mapVo) throws DataAccessException {

		try {

			String income_item_name = mapVo.get("income_item_name").toString();

			mapVo.put("spell_code", StringTool.toPinyinShouZiMu(income_item_name));

			mapVo.put("wbx_code", StringTool.toWuBi(income_item_name));

			aphiIncomeItemMapper.updateIncomeItem(mapVo);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateIncomeItem\"}";

		}

	}

	@Override
	public String addBatchIncomeitem(List<Map<String, Object>> mapVo) throws DataAccessException {
		int state = aphiIncomeItemMapper.addBatchIncomeItem(mapVo);
		if (state > 0) {
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} else {
			return "{\"msg\":\"添加失败.\",\"state\":\"false\"}";
		}

	}

	//导入
	@Override
	public String hpmIncomeItemImport(Map<String, Object> map)throws DataAccessException {
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
			
			//查询导入字典 List
			List<AphiIncomeItem> aphiIncomeItemList = aphiIncomeItemMapper.queryIncomeItem(entityMap);
			
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
					List<String> income_item_name = item.get("收入项目名称");
					List<String> is_stop = item.get("是否停用");
					
					if(income_item_code.get(1) == null){
						return "{\"warn\":\"收入项目编码为空！\",\"state\":\"false\",\"row_cell\":\"" + income_item_code.get(0) + "\"}";
					}
					
					if(income_item_name.get(1) == null){
						return "{\"warn\":\"收入项目名称为空！\",\"state\":\"false\",\"row_cell\":\"" + income_item_name.get(0) + "\"}";
					}
					
					for(AphiIncomeItem aphiIncomeItem:aphiIncomeItemList){
						if(income_item_code.get(1).equals(aphiIncomeItem.getIncome_item_code())){
							return "{\"warn\":\"收入项目编码已经存在！\",\"state\":\"false\",\"row_cell\":\"" + income_item_code.get(0) + "\"}";
						}else if(income_item_name.get(1).equals(aphiIncomeItem.getIncome_item_name())){
							return "{\"warn\":\"收入项目名称已经存在！\",\"state\":\"false\",\"row_cell\":\"" + income_item_name.get(0) + "\"}";
						}
					}
					
					//以年度+月份+指标编码+科室名称为键值,判断导入数据是否重复
					String key = income_item_code.get(1) + income_item_name.get(1);
					if(exitMap.get(key) != null ){
						err_sb.append(income_item_code.get(1)+"收入项目," +income_item_name.get(1)+"收入项目名称 ");
					}else{
						exitMap.put(key, key);
					}
					
					
					//添加数据Map中add到returnList
					Map<String,Object> returnMap = new HashMap<String,Object>();
					returnMap.put("group_id", SessionManager.getGroupId());
					returnMap.put("hos_id", SessionManager.getHosId());
					returnMap.put("copy_code", SessionManager.getCopyCode());
					returnMap.put("income_item_code", income_item_code.get(1));
					returnMap.put("income_item_name", income_item_name.get(1));
					returnMap.put("spell_code", StringTool.toPinyinShouZiMu(income_item_name.get(1)));
					returnMap.put("wbx_code", StringTool.toWuBi(income_item_name.get(1)));
					returnMap.put("is_stop", 0);
					returnMap.put("data_source", "");
					
					returnList.add(returnMap);
					break;
				}
			}
			
			if (err_sb.toString().length() > 0 ) {
				return "{\"warn\":\"以下数据【" +err_sb.toString() + "】数据重复！\",\"state\":\"false\"}";
			}else{
				aphiIncomeItemMapper.addBatchIncomeItem(returnList);
				return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
			}		
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

}
