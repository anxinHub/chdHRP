
package com.chd.hrp.hpm.serviceImpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.hrp.hpm.dao.AphiDeptKindBonusDataMapper;
import com.chd.hrp.hpm.dao.AphiItemMapper;
import com.chd.hrp.hpm.dao.AphiTargetMapper;
import com.chd.hrp.hpm.entity.AphiDeptKindBonusData;
import com.chd.hrp.hpm.entity.AphiItem;
import com.chd.hrp.hpm.entity.AphiTarget;
import com.chd.hrp.hpm.service.AphiDeptKindBonusDataService;

/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */


@Service("aphiDeptKindBonusDataService")
public class AphiDeptKindBonusDataServiceImpl implements AphiDeptKindBonusDataService {

	private static Logger logger = Logger.getLogger(AphiDeptKindBonusDataServiceImpl.class);
	
	@Resource(name = "aphiDeptKindBonusDataMapper")
	private final AphiDeptKindBonusDataMapper aphiDeptKindBonusDataMapper = null;
	@Resource(name = "aphiItemMapper")
	private final AphiItemMapper aphiItemMapper = null;
	@Resource(name="aphiTargetMapper")
	private final AphiTargetMapper aphiTargetMapper = null;
    
	/**
	 * 
	 */
	@Override
	public String addDeptKindBonusData(Map<String,Object> entityMap)throws DataAccessException{
	    int state = aphiDeptKindBonusDataMapper.addDeptKindBonusData(entityMap);
		if (state > 0) {
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} else {
			return "{\"msg\":\"添加失败.\",\"state\":\"false\"}";
		}	
	}
	
	/**
	 * 
	 */
	@Override
	public String queryDeptKindBonusData(Map<String,Object> entityMap) throws DataAccessException{
		double sum_money = 0;
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		StringBuffer sql = new StringBuffer();
		String bonus_money="";
		double result = 0;
		List<AphiDeptKindBonusData> deptBonusDataList =aphiDeptKindBonusDataMapper.queryDeptKindBonusData(entityMap, rowBounds);
		StringBuilder deptSql = new StringBuilder();
		deptSql.append("{Rows:[");
		if(deptBonusDataList!=null &&deptBonusDataList.size()>0){
			for (int i = 0; i < deptBonusDataList.size(); i++) {
				Map<String, Object> map = (Map) deptBonusDataList.get(i);
				for (int j = 0; j <deptBonusDataList.size(); j++) {
					Map<String, Object> maps = (Map) deptBonusDataList.get(j);
					if(map.get("acct_year").equals(maps.get("acct_year"))&&map.get("acct_month").equals(maps.get("acct_month"))||map.get("item_code")!=maps.get("item_code")||map.get("dept_code")!=maps.get("dept_code")){
						bonus_money+="\"bonus_money" + j+ "\":" + "\"" +maps.get("bonus_money") + "\",";
					 result =result+ Double.parseDouble(maps.get("bonus_money").toString());
					}
				}
				bonus_money+="\"sum_money\":" + "\"" +result + "\",";
				deptBonusDataList.remove(i);
				deptSql.append("{");
				for (Map.Entry<String, Object> entry : map.entrySet()) {

					if (!"rownum".equals(entry.getKey())) {

						deptSql.append("\"" + entry.getKey() + "\":" + "\"" + entry.getValue() + "\",");
						deptSql.append(bonus_money);
					}

				}
				deptSql.append("},");
			}
			deptSql.setCharAt(deptSql.length() - 1, ']');
			if (sysPage.getTotal() == -1)
				deptSql.append(",Total:" + deptBonusDataList.size() + "}");

			else
				deptSql.append(",Total:" + sysPage.getTotal() + "}");

		} else {

			deptSql.append("],Total:0}");

		}
		return deptSql.toString();
	}
	
	/**
	 * 
	 */
	@Override
	public AphiDeptKindBonusData queryDeptKindBonusDataByCode(Map<String,Object> entityMap)throws DataAccessException{
		return aphiDeptKindBonusDataMapper.queryDeptKindBonusDataByCode(entityMap);
	}
	
	
	/**
	 * 
	 */
	
	public String deleteDeptKindBonusData(List<Map<String,Object>> entityList)throws DataAccessException{
		String request="";
		for (Map<String,Object> entityMap : entityList) {
	        int state = aphiDeptKindBonusDataMapper.deleteDeptKindBonusData(entityMap);
        	if (state > 0) {
			request="{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			} else {
				request="{\"msg\":\"没有要删除的数据.\",\"state\":\"false\"}";
			}
        }
		
		return request;
	}
	@Override
    public String deleteDeptKindBonusData(Map<String, Object> entityMap) throws DataAccessException {
		String request="";
		int state = aphiDeptKindBonusDataMapper.deleteDeptKindBonusData(entityMap);
		if (state > 0) {
			request= "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} else {
			request= "{\"msg\":\"没有要删除的数据.\",\"state\":\"false\"}";
		}
		return request;
    }
	/**
	 * 
	 */
	@Override
	public String deleteDeptKindBonusDataById(String[] ids)throws DataAccessException{
		String request="";
		if (ids != null && ids.length>0) {
					for (String s : ids) {
						aphiDeptKindBonusDataMapper.deleteDeptKindBonusDataById(s);
					}
					request= "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
				} else {
					request= "{\"error\":\"没有要删除的数据.\",\"state\":\"false\"}";
				}
		return request;
		
	}
	/**
	 * 
	 */
	@Override
	public String updateDeptKindBonusData(Map<String,Object> entityMap)throws DataAccessException{
		int state = aphiDeptKindBonusDataMapper.updateDeptKindBonusData(entityMap);
		if (state > 0) {
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} else {
			return "{\"msg\":\"修改失败.\",\"state\":\"false\"}";
		}
	}

	@Override
	public String initDeptKindBonusData(Map<String, Object> entityMap)
			throws DataAccessException {
		int state = aphiDeptKindBonusDataMapper.initDeptKindBonusData(entityMap);
		if (state > 0) {
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
		} else {
			return "{\"msg\":\"生成失败.\",\"state\":\"false\"}";
		}
	}

	@Override
	public  String queryDeptKindBonusDataGrid(Map<String, Object> entityMap) throws DataAccessException{
		StringBuilder json = new StringBuilder();
		List<AphiItem> queryItemDict = aphiItemMapper.qeuryItemMap(entityMap);
		json.append("[");
		for (int i = 0; i < queryItemDict.size(); i++) {
			AphiItem item = queryItemDict.get(i);
			json.append("{");
			json.append("\"id\":\"" + item.getItem_code() + "\"," + "\"text\":" + "\"" + item.getItem_name()+ "\"," + "\"Sort\":" + "\""
			        + i + "\"");
			json.append("},");
		}
		json.setCharAt(json.length() - 1, ']');
		
		return json.toString();
	}
	public  String queryTargetGrid(Map<String, Object> entityMap) throws DataAccessException{
		StringBuilder json = new StringBuilder();
		//List<AphiTarget> queryTargetDict =aphiTargetMapper.queryTargetMap(entityMap);
		List<AphiTarget> queryTargetDict =null;
		json.append("[");
		for (int i = 0; i < queryTargetDict.size(); i++) {
			AphiTarget target = queryTargetDict.get(i);
			json.append("{");
			json.append("\"id\":\"" + target.getTarget_code()+ "\"," + "\"text\":" + "\"" + target.getTarget_name()+ "\"," + "\"Sort\":" + "\""
			        + i + "\"");
			json.append("},");
		}
		json.setCharAt(json.length() - 1, ']');
		
		return json.toString();
	}
	@Override
	public String queryTarget(Map<String, Object> entityMap)
			throws DataAccessException {
		double sum_money = 0;
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		StringBuffer sql = new StringBuffer();
		String bonus_money="";
		double result = 0;
		List<AphiDeptKindBonusData> deptkindBonusDataList =aphiDeptKindBonusDataMapper.queryTarget(entityMap, rowBounds);
		StringBuilder deptSql = new StringBuilder();
		deptSql.append("{Rows:[");
		if(deptkindBonusDataList!=null &&deptkindBonusDataList.size()>0){
			for (int i = 0; i < deptkindBonusDataList.size(); i++) {
				Map<String, Object> map = (Map) deptkindBonusDataList.get(i);
				for (int j = 0; j <deptkindBonusDataList.size(); j++) {
					Map<String, Object> maps = (Map) deptkindBonusDataList.get(j);
					if(map.get("acct_year").equals(maps.get("acct_year"))&&map.get("acct_month").equals(maps.get("acct_month"))||map.get("item_code")!=maps.get("item_code")||map.get("dept_code")!=maps.get("dept_code")){
						bonus_money+="\"bonus_money" + j+ "\":" + "\"" +maps.get("bonus_money") + "\",";
					 result =result+ Double.parseDouble(maps.get("bonus_money").toString());
					}
				}
				bonus_money+="\"sum_money\":" + "\"" +result + "\",";
				deptkindBonusDataList.remove(i);
				deptSql.append("{");
				for (Map.Entry<String, Object> entry : map.entrySet()) {

					if (!"rownum".equals(entry.getKey())) {

						deptSql.append("\"" + entry.getKey() + "\":" + "\"" + entry.getValue() + "\",");
						deptSql.append(bonus_money);
					}

				}
				deptSql.append("},");
			}
			deptSql.setCharAt(deptSql.length() - 1, ']');
			if (sysPage.getTotal() == -1)
				deptSql.append(",Total:" + deptkindBonusDataList.size() + "}");

			else
				deptSql.append(",Total:" + sysPage.getTotal() + "}");

		} else {

			deptSql.append("],Total:0}");

		}
		return deptSql.toString();
	}
}
