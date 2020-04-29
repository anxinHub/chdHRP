/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.serviceImpl.base;

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
import com.chd.base.util.ChdJson;
import com.chd.base.util.ReadFiles;
import com.chd.base.util.StringTool;
import com.chd.hrp.hr.dao.base.HrTechnicalMapper;
import com.chd.hrp.hr.entity.base.HrTechnical;
import com.chd.hrp.hr.service.base.HrTechnicalService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 技术等级
 * @Table:
 * HR_TECHNICAL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("hrTechnicalService")
public class HrTechnicalServiceImpl implements HrTechnicalService {

	private static Logger logger = Logger.getLogger(HrTechnicalServiceImpl.class);
	//引入DAO操作
	@Resource(name = "hrTechnicalMapper")
	private final HrTechnicalMapper hrTechnicalMapper = null;
    
	/**
	 * @Description 
	 * 添加技术等级<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象技术等级
		HrTechnical deptKind = hrTechnicalMapper.queryByCode(entityMap);

		if (deptKind != null) {

			if (deptKind.getTechnical_code().equals(entityMap.get("technical_code"))) {
				return "{\"error\":\"编码：" + deptKind.getTechnical_code().toString() + "已存在.\"}";
			}
			if (deptKind.getTechnical_name().equals(entityMap.get("technical_name"))) {
				return "{\"error\":\"名称：" + deptKind.getTechnical_name().toString() + "已存在.\"}";
			}

		}
		try {
			
			int state = hrTechnicalMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	
	
		/**
	 * @Description 
	 * 更新技术等级<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		//获取对象技术等级
		List<HrTechnical> deptKind = hrTechnicalMapper.queryByCodeName(entityMap);

		if (deptKind != null) {
           for (HrTechnical hrTechnical : deptKind) {
        		if (hrTechnical.getTechnical_name().equals(entityMap.get("technical_name"))) {
    				return "{\"error\":\"名称：" + hrTechnical.getTechnical_name().toString() + "已存在.\"}";
    			}
		}
		

		}
		try {
			
		  int state = hrTechnicalMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}
	
	
    
	
	

	/**
	 * @Description 
	 * 查询结果集技术等级<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<HrTechnical> list = (List<HrTechnical>)hrTechnicalMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HrTechnical> list = (List<HrTechnical>)hrTechnicalMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象技术等级<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public HrTechnical queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return hrTechnicalMapper.queryByCode(entityMap);
	}
	
	
	
	/**
	 * 删除技术等级
	 */
	@Override
	public String deleteHrTechnical(List<HrTechnical> entityList)throws DataAccessException{
		
		try {
			
			hrTechnicalMapper.deleteHrTechnical(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
	}
	/**
	 * 查询树形
	 */
	@Override
	public String queryTechnicalLevelTree(Map<String, Object> entityMap)  throws DataAccessException {

		StringBuilder treeJson = new StringBuilder();

		treeJson.append("[");
		List<HrTechnical> storeTypeList = (List<HrTechnical>) hrTechnicalMapper.query(entityMap);
		for (HrTechnical storeType : storeTypeList) {
			treeJson.append(
					"{'id':'" + storeType.getTechnical_code() + "', 'pId':'0', 'name':'" + storeType.getTechnical_name() + "'},");

		}
		treeJson.append("]");
		return treeJson.toString();
	}
	/**
	 * 导入数据
	 */
	@Override
	public String importData(Map<String, Object> entityMap)
			throws DataAccessException {
		int successNum = 0;
		int failureNum = 0;
		StringBuilder failureMsg = new StringBuilder();
		List<Map<String,Object>> saveList = new ArrayList<Map<String,Object>>();
		
		Map<String, Object> whetherMap = new HashMap<String, Object>();
		whetherMap.put("是", "1");
		whetherMap.put("否", "0");
		whetherMap.put("1", "1");
		whetherMap.put("0", "0");
		
		try {
			List<Map<String, List<String>>> list = ReadFiles.readFiles(entityMap);
			if(list != null && list.size() > 0){
				for (Map<String, List<String>> map : list) {
					Map<String,Object> saveMap = new HashMap<String,Object>();
					saveMap.put("group_id", SessionManager.getGroupId());
					saveMap.put("hos_id", SessionManager.getHosId());
					saveMap.put("copy_code", SessionManager.getCopyCode());
					
					//过滤空行
					if("".equals(map.get("technical_code").get(1)) || "".equals(map.get("technical_name").get(1))){
						continue;
					}
					
					if("null".equals(map.get("technical_code").get(1)) || "null".equals(map.get("technical_name").get(1))){
						continue;
					}
					
					saveMap.put("technical_code", map.get("technical_code").get(1));
					saveMap.put("technical_name", map.get("technical_name").get(1));
					saveMap.put("is_stop", whetherMap.get(map.get("is_stop").get(1)));
					saveMap.put("spell_code",StringTool.toPinyinShouZiMu(map.get("technical_name").get(1)));
					saveMap.put("wbx_code", StringTool.toWuBi(map.get("technical_name").get(1)));
					saveMap.put("note", map.get("note").get(1));
					
					HrTechnical type = hrTechnicalMapper.queryByCode(saveMap);
					
					if(type != null){
						if(type.getTechnical_code().equals(map.get("technical_code").get(1).toString())){
							failureMsg.append("<br/>技术等级编码 "+map.get("technical_code")+" 已存在; ");
						}else{
							failureMsg.append("<br/>技术等级名称 "+map.get("technical_name")+" 已存在; ");
						}
						
						failureNum++;
						continue;
					}
					successNum++;
					saveList.add(saveMap);
				}
				if(saveList.size() > 0){
					hrTechnicalMapper.addByImport(saveList);
				}
				if (failureNum>0){
					failureMsg.insert(0, "，失败 "+failureNum+" 条，导入信息如下：");
				}
			}
			return "{\"msg\":\"已成功导入 "+successNum+"条"+failureMsg+"\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "{\"error\":\"导入失败！\"}";
		}
	}
}
