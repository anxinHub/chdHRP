package com.chd.hrp.acc.serviceImpl;

import com.alibaba.fastjson.JSONArray;
import com.chd.base.SessionManager;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.AccElementAnalyzeMapper;
import com.chd.hrp.acc.service.AccElementAnalyzeService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.math.BigDecimal;

@Service("accElementAnalyzeService")
public class AccElementAnalyzeServiceImpl implements AccElementAnalyzeService {

	private static Logger logger = Logger.getLogger(AccElementAnalyzeServiceImpl.class);
	
	@Resource(name = "accElementAnalyzeMapper")
	private final AccElementAnalyzeMapper accElementAnalyzeMapper = null;



	@Override
	public String queryElements(Map<String, Object> mapVo) {
			List<Map<String, Object>> list = accElementAnalyzeMapper.queryElements(mapVo);
			for(Map<String,Object> map:list) {
				System.out.print("11111 into foreach");
				if(map.get("IS_STOP").toString().equals("1")) {
					map.put("IS_STOP","停用");
				}else {
					map.put("IS_STOP","启用");
				}
				System.out.print("11111"+map.get("IS_STOP"));
			}
			String querydata = ChdJson.toJson(list);
			return querydata;
	}
	@Override
	public List queryElementForUpdata(Map<String, Object> mapVo) {
		List<Map<String, Object>> list = accElementAnalyzeMapper.queryElementForUpdata(mapVo);
		//return ChdJson.toJson(list);
		return list;
	}
	@Override
	public List queryAccElementPrint(Map<String, Object> mapVo) {
		Map<String, Object> updataFather = new HashMap<String, Object>();
		updataFather.put("GROUP_ID", SessionManager.getGroupId());
		updataFather.put("HOS_ID", SessionManager.getHosId());
		updataFather.put("COPY_CODE", SessionManager.getCopyCode());
		List<Map<String, Object>> list = accElementAnalyzeMapper.queryAccElementAnalysis(updataFather);
		for(Map<String,Object> map:list) {
			if(map.get("IS_STOP").toString().equals("1")) {
				map.put("IS_STOP","停用");
			}else {
				map.put("IS_STOP","启用");
			}
		}
		return list;
	}

	@Override
	public String addElements(Map<String, Object> mapVo) {
		try {
			int addCount=0;
			mapVo.put("GROUP_ID", SessionManager.getGroupId());
			mapVo.put("HOS_ID", SessionManager.getHosId());
			mapVo.put("COPY_CODE", SessionManager.getCopyCode());

			String currentFacCode = (String)mapVo.get("FAC_CODE");
			String currentSuperCode = (String)mapVo.get("SUPER_CODE");

			//如果父级SUPER_CODE不为TOP ，则需修改父级
			if(mapVo.get("SUPER_CODE").equals("TOP")) {
				//无父级修改，直接添加
				addCount = accElementAnalyzeMapper.addElements(mapVo);
			}else {
				addCount = accElementAnalyzeMapper.addElements(mapVo);

				Map<String, Object> updataFather = new HashMap<String, Object>();
				updataFather.put("GROUP_ID", SessionManager.getGroupId());
				updataFather.put("HOS_ID", SessionManager.getHosId());
				updataFather.put("COPY_CODE", SessionManager.getCopyCode());
				updataFather.put("FAC_CODE",currentSuperCode);
				updataFather.put("IS_LAST",0);
				//addCount = accElementAnalyzeMapper.updataElements(updataFather);
			}
			return "{\"msg\":\"保存成功!\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SysException();
		}
	}

	@Override
	public String deleteElements(Map<String, Object> mapVo) {
		try {
			//List<Map> list = JSONArray.parseArray(mapVo.get("parmarr").toString(),Map.class);
			int addCount = 0;
			//查询当前删除完整数据，精确查询
			List<Map<String, Object>> list = accElementAnalyzeMapper.queryElementForUpdata(mapVo);
			String currentSuperCode = (String)list.get(0).get("SUPER_CODE");
			int currentIsLast = ( (BigDecimal)list.get(0).get("IS_LAST") ).intValue();
			//如果为末级__删除前判断父级是否有其他子级__若果没有则将该父级标记为末级
			if(currentIsLast==1){
				addCount = accElementAnalyzeMapper.deleteElements(list.get(0));
				//父级为top，则不修改父级islast标识
				if(currentSuperCode.equals("TOP")) {
					return  "{\"msg\":\"删除成功\",\"isSuc\":true}";
				}else {
					//为末级有父级，且父级没有其他子级时_修改父级的islast标识
					List<Map<String, Object>> sonCode = accElementAnalyzeMapper.querySonElements(mapVo);
					//查询suoer_code为当前currentSuperCode，为空则说明该父级没有子级，修改末级标识
					if(sonCode.isEmpty() ) {
						Map<String, Object> seniorMap = list.get(0);
						seniorMap.put("IS_LAST", 1);
						seniorMap.put("FAC_CODE", currentSuperCode);
						seniorMap.put("GROUP_ID", mapVo.get("GROUP_ID"));
						seniorMap.put("HOS_ID", mapVo.get("HOS_ID"));
						seniorMap.put("COPY_CODE", mapVo.get("COPY_CODE"));
						addCount=accElementAnalyzeMapper.updataElements(mapVo);
					}
					
					//if (addCount != 0) {
					return  "{\"msg\":\"删除成功\",\"isSuc\":true}";
					//}
				}
				
			}else{
				//如果不为末级__不能删除_?_删除所有子级	
				//throw new SysException("删除失败,不为末级!");
				return  "{\"msg\":\"删除失败,不为末级\",\"isSuc\":false}";
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SysException();
		}
	}


	@Override
	public String updataElements(Map<String, Object> mapVo) {
		try {
			int addCount = accElementAnalyzeMapper.updataElements(mapVo);
			if (addCount == 0) {
				throw new SysException("保存失败,请刷新尝试!");
			}
			return "{\"msg\":\"删除成功\",\"isSuc\":true}";
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SysException();
		}

	}
}
