package com.chd.hrp.hr.serviceImpl.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.hr.dao.base.HrSelectMapper;
import com.chd.hrp.hr.dao.sc.HrFiledTabStrucMapper;
import com.chd.hrp.hr.entity.sc.HrFiledTabStruc;
import com.chd.hrp.hr.service.base.HrSelectService;
import com.chd.hrp.hr.util.ParameterHandler;
import com.github.pagehelper.PageInfo;

@Service("hrSelectService")
public class HrSelectServiceImpl implements HrSelectService {

	private static Logger logger = Logger.getLogger(HrSelectServiceImpl.class);

	@Resource(name = "hrSelectMapper")
	private final HrSelectMapper hrSelectMapper = null;
	
	//引入DAO操作
	@Resource(name = "hrFiledTabStrucMapper")
	private final HrFiledTabStrucMapper hrFiledTabStrucMapper = null;

	// 默认显示20条数据
	RowBounds rowBoundsALL = new RowBounds(0, 20);

	@Override
	public String queryDicSexSelect(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list = hrSelectMapper.queryDicSexSelect(entityMap);
		return JSONArray.toJSONString(list);
	}

	@Override
	public String queryDicNationSelect(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String queryDicPoliticalSelect(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String queryDicMarriageSelect(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String queryDicResidenceSelect(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String queryHosDeptDictTree(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> treeList = hrSelectMapper.queryHosDeptDictTree(entityMap);
		Map<String, Object> top = new HashMap<String, Object>();
		top.put("id", 0);
		top.put("pId", "");
		top.put("DEPT_ID", 0);
		if(SessionManager.getTypeCode().equals("0")){
			top.put("name", SessionManager.getHosSimple());
		}else{
			top.put("name", SessionManager.getGroupSimple());
		}
		
		treeList.add(top);
		// return ChdJson.toJson(treeList);
		return JSONArray.toJSONString(treeList);
	}

	@Override
	public String queryHrStoreTypeSelect(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list = hrSelectMapper.queryHrStoreTypeSelect(entityMap);
		return JSONArray.toJSONString(list);
	}

	@Override
	public String queryHrStoreTabStrucSelect(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("store_type_code") == null || "".equals(entityMap.get("store_type_code").toString().trim())) {
			List<Map<String, Object>> list = hrSelectMapper.queryHrStoreTypeSelect(entityMap);
			if (list != null && list.size() > 0) {
				entityMap.put("store_type_code", list.get(0).get("id"));
			}

		}
		List<Map<String, Object>> list = hrSelectMapper.queryHrStoreTabStrucSelect(entityMap);
		return JSONArray.toJSONString(list);
	}

	@Override
	public String queryHrTabTypeSelect(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list = hrSelectMapper.queryHrTabTypeSelect(entityMap);
		return JSONArray.toJSONString(list);
	}
	
	@Override
	public String queryHrTableTypeSelect(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list = hrSelectMapper.queryHrTableTypeSelect(entityMap);
		return JSONArray.toJSONString(list);
	}

	@Override
	public String queryHrTabStrucSelect(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list = hrSelectMapper.queryHrTabStrucSelect(entityMap);
		return JSONArray.toJSONString(list);
	}

	@Override
	public String queryHrColStrucSelect(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list = hrSelectMapper.queryHrColStrucSelect(entityMap);
		return JSONArray.toJSONString(list);
	}

	@Override
	public String queryHrColStrucGrid(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list = hrSelectMapper.queryHrColStrucGrid(entityMap);
		return ChdJson.toJson(list);
	}

	@Override
	public String queryHrColDataTypeSelect(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list = hrSelectMapper.queryHrColDataTypeSelect(entityMap);
		return JSONArray.toJSONString(list);
	}

	@Override
	public String queryHrComTypeSelect(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list = hrSelectMapper.queryHrComTypeSelect(entityMap);
		return JSONArray.toJSONString(list);
	}

	@Override
	public String queryHrConSignSelect(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list = hrSelectMapper.queryHrConSignSelect(entityMap);
		return JSONArray.toJSONString(list);
	}

	@Override
	public String queryHrJoinSignSelect(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list = hrSelectMapper.queryHrJoinSignSelect(entityMap);
		return JSONArray.toJSONString(list);
	}

	@Override
	public String queryHrFiiedTabStrucDic(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list = hrSelectMapper.queryHrFiiedTabStrucDic(entityMap);
		return ChdJson.toJson(list);
	}

	@Override
	public String queryHrFiiedDataDicByTabCol(Map<String, Object> entityMap) throws DataAccessException {
		hrSelectMapper.queryHrFiiedDataDicByTabCol(entityMap);
		List<Map<String, Object>> list = (List<Map<String, Object>>) entityMap.get("resultlist");
		return ChdJson.toJson(list);
	}

	@Override
	public String queryHrFiiedDataSelect(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list = hrSelectMapper.queryHrFiiedDataSelect(entityMap);
		return JSONArray.toJSONString(list);
	}

	@Override
	public String queryTypeFiledTypeSelect(Map<String, Object> mapVo) throws DataAccessException {
		List<Map<String, Object>> list = hrSelectMapper.queryTypeFiledTypeSelect(mapVo);
		return JSONArray.toJSONString(list);
	}

	@Override
	public String queryEmp(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {
			List<Map<String, Object>> list = (List<Map<String, Object>>) hrSelectMapper.queryEmp(entityMap);
			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = (List<Map<String, Object>>) hrSelectMapper.queryEmp(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * 部门分类
	 * 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */

	
	@Override
	public String queryHosDeptSelect(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list = hrSelectMapper.queryHosDeptSelect(entityMap);
		return JSONArray.toJSONString(list);
	}
	
	/**
	 * 部门分类不带权限
	 * 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */

	
	@Override
	public String queryHosAftDeptSelect(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list = hrSelectMapper.queryHosAftDeptSelect(entityMap);
		return JSONArray.toJSONString(list);
	}
	/**
	 * 按部门分类查询人员
	 * 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryEmpSelect(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list = hrSelectMapper.queryEmpSelect(entityMap);
		return JSONArray.toJSONString(list);
	}
	
	/**
	 * 查询人员按部门下拉框(只查考勤是的人)
	 * 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryEmpSelectAttend(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list = hrSelectMapper.queryEmpSelectAttend(entityMap);
		return JSONArray.toJSONString(list);
	}

	/**
	 * 部门架构快速查询
	 * 
	 * @param mapVo
	 * @return
	 */
	@Override
	public String queryDeptTree(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list = hrSelectMapper.queryDeptTree(entityMap);
		Map<String, Object> top = new HashMap<String, Object>();
		top.put("id", 0);
		top.put("pId", "");
		top.put("DEPT_ID", "");
		if(SessionManager.getTypeCode().equals("0")){
			top.put("name", SessionManager.getHosSimple());
		}else{
			top.put("name", SessionManager.getGroupSimple());
		}
		
		list.add(top);
		return JSON.toJSONString(list);
	}
	

	@Override
	public String queryDuty(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list = hrSelectMapper.queryDuty(entityMap);
		return JSONArray.toJSONString(list);
	}

	/**
	 * 查询职称
	 */
	@Override
	public String queryHrTitle(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list = hrSelectMapper.queryHrTitle(entityMap);
		return JSONArray.toJSONString(list);
	}

	/**
	 * 查询护理等级
	 */
	@Override
	public String queryDicLevel(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list = hrSelectMapper.queryDicLevel(entityMap);
		return JSONArray.toJSONString(list);
	}

	/**
	 * 查询员工姓名 职称 职务 护理等级 科室
	 */
	@Override
	public String queryEmpDuty(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			
		List<Map<String, Object>> list = (List<Map<String, Object>>) hrSelectMapper.queryEmpDuty(entityMap);
		
		return ChdJson.toJson(list);
				
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) hrSelectMapper.queryEmpDuty(entityMap, rowBounds);
			
	        PageInfo page = new PageInfo(list); 
			
			return ChdJson.toJson(list, page.getTotal());
			
		}

	}

	/**
	 * 查询人员下拉表格
	 */
	@Override
	public String queryHrEmpData(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list = hrSelectMapper.queryHrEmpData(entityMap);
		return ChdJson.toJson(list);
	}
	
	@Override
	public String queryHosEmpGrid(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list = hrSelectMapper.queryHosEmpGrid(entityMap);
		return ChdJson.toJson(list);
	}

	@Override
	public String queryHpmOraclePkg(Map<String, Object> entityMap) throws DataAccessException {

		List<Map<String, Object>> list = hrSelectMapper.queryHpmOraclePkg(entityMap);
		return JSONArray.toJSONString(list);
	}
    /**
     * 函数分类
     */
	@Override
	public String queryHpmFunType(Map<String, Object> entityMap) throws DataAccessException {

		List<Map<String, Object>> list = hrSelectMapper.queryHpmFunType(entityMap);
		return JSONArray.toJSONString(list);
	}
   /**
    * 绩效函数参数取值
    */
	@Override
	public String queryHrFunParaMethod(Map<String, Object> entityMap) {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}

		return JSON.toJSONString(hrSelectMapper.queryHrFunParaMethod(entityMap, rowBounds));
	}
    /**
     * 岗位下拉框
     */
	@Override
	public String queryStation(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list = hrSelectMapper.queryStation(entityMap);
		return JSONArray.toJSONString(list);
	}
   /**
    * 职工类别
    */
	@Override
	public String queryEmpType(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list = hrSelectMapper.queryEmpType(entityMap);
		return JSONArray.toJSONString(list);
	}
	/**
	 * 查询人员详细信息
	 */
	@Override
	public String queryPersonnel(Map<String, Object> entityMap)
			throws DataAccessException {
		return JSONArray.toJSONString(hrSelectMapper.queryPersonnel(entityMap));
	}
   /**
    * 人员下拉框
    */
	@Override 
	public String queryPerson(Map<String, Object> entityMap)
			throws DataAccessException {
		List<Map<String, Object>> list = hrSelectMapper.queryPerson(entityMap);
		return JSONArray.toJSONString(list);
	}
    /**
     * 药品权限下拉框
     */
	@Override
	public String queryPermTypeSelect(Map<String, Object> entityMap)
			throws DataAccessException {
		List<Map<String, Object>> list = hrSelectMapper.queryPermTypeSelect(entityMap);
		return JSONArray.toJSONString(list);
	}
	   /**
     * 药品下拉框
     */
	@Override
	public String queryMedTypeSelect(Map<String, Object> entityMap)
			throws DataAccessException {
		List<Map<String, Object>> list = hrSelectMapper.queryMedTypeSelect(entityMap);
		return JSONArray.toJSONString(list);
	}

	@Override
	public String queryProbNature(Map<String, Object> entityMap)
			throws DataAccessException {
		List<Map<String, Object>> list = hrSelectMapper.queryProbNature(entityMap);
		return JSONArray.toJSONString(list);
	}

	@Override
	public String queryProbType(Map<String, Object> entityMap)
			throws DataAccessException {
		List<Map<String, Object>> list = hrSelectMapper.queryProbType(entityMap);
		return JSONArray.toJSONString(list);
	}

	@Override
	public String queryIsOrNot(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list = hrSelectMapper.queryIsOrNot(entityMap);
		return JSONArray.toJSONString(list);
	}

	@Override
	public String queryColorSelect(Map<String, Object> entityMap)throws DataAccessException {
		List<Map<String, Object>> list = hrSelectMapper.queryColorSelect(entityMap);
		return JSONArray.toJSONString(list);
	}

	@Override
	public String queryAttendTypesSelect(Map<String, Object> entityMap)
			throws DataAccessException {
		List<Map<String, Object>> list = hrSelectMapper.queryAttendTypesSelect(entityMap);
		return JSONArray.toJSONString(list);
	}

	@Override
	public String queryHosInfoSelect(Map<String, Object> entityMap)
			throws DataAccessException {
		List<Map<String, Object>> list = hrSelectMapper.queryHosInfoSelect(entityMap);
		return JSONArray.toJSONString(list);
	}

	@Override
	public String queryOvertimeKind(Map<String, Object> entityMap)
			throws DataAccessException {
		List<Map<String, Object>> list = hrSelectMapper.queryOvertimeKind(entityMap);
		return JSONArray.toJSONString(list);
	}

	@Override
	public String queryCalssCode(Map<String, Object> entityMap)
			throws DataAccessException {
		List<Map<String, Object>> list = hrSelectMapper.queryCalssCode(entityMap);
		return JSONArray.toJSONString(list);
	}

	@Override
	public String queryCalssDept(Map<String, Object> entityMap)
			throws DataAccessException {
		List<Map<String, Object>> list = hrSelectMapper.queryCalssDept(entityMap);
		return JSONArray.toJSONString(list);
	}

	@Override
	public String queryHrStoreTabStruc(Map<String, Object> entityMap)
			throws DataAccessException {
		if (entityMap.get("store_type_code") == null || "".equals(entityMap.get("store_type_code").toString().trim())) {
			List<Map<String, Object>> list = hrSelectMapper.queryHrStoreTypeSelect(entityMap);
			if (list != null && list.size() > 0) {
				entityMap.put("store_type_code", list.get(0).get("id"));
			}

		}
		List<Map<String, Object>> list = hrSelectMapper.queryHrStoreTabStruc(entityMap);
		return JSONArray.toJSONString(list);
	}

	@Override
	public String queryMods() throws DataAccessException {
		List<Map<String, Object>> list = hrSelectMapper.queryMod(null);
		return JSONArray.toJSONString(list);
	}
	
	/**
	 * 职务等级
	 */
	@Override
	public String queryDutyLevel(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list = hrSelectMapper.queryDutyLevel(entityMap);
		return JSONArray.toJSONString(list);
	}
	/**
	 * 职务类别
	 */
	@Override
	public String queryDutyKind(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list = hrSelectMapper.queryDutyKind(entityMap);
		return JSONArray.toJSONString(list);
	}
    /**
     * 班次分类
     */
	@Override
	public String queryAttendCalssType(Map<String, Object> entityMap)
			throws DataAccessException {
		List<Map<String, Object>> list = hrSelectMapper.queryAttendCalssType(entityMap);
		return JSONArray.toJSONString(list);
	}
	
	/**
	 * 职务名称
	 */
	@Override
	public String queryDutyCode(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list = hrSelectMapper.queryDutyCode(entityMap);
		return JSONArray.toJSONString(list);
	}
	/**
	 * 岗位等级
	 */
	@Override
	public String queryStationLevel(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list = hrSelectMapper.queryStationLevel(entityMap);
		return JSONArray.toJSONString(list);
	}
	@Override
	public String queryAttendCodeCla(Map<String, Object> entityMap)
			throws DataAccessException {
		List<Map<String, Object>> list = hrSelectMapper.queryAttendCodeCla(entityMap);
		return JSONArray.toJSONString(list);
	}
    /**
     * 考勤项目分组
     */
	@Override
	public String queryAttendFZ(Map<String, Object> entityMap)
			throws DataAccessException {
		List<Map<String, Object>> list = hrSelectMapper.queryAttendFZ(entityMap);
		return JSONArray.toJSONString(list);
	}

	/**
     * 根据员工查科室信息
     */
	@Override
	public String queryHosDeptByEmpSelect(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list = hrSelectMapper.queryHosDeptByEmpSelect(entityMap);
		return JSONArray.toJSONString(list);
	}

	/**
	 * 查获奖情况
	 * @autor yangyunfei
	 */
	@Override
	public String queryPrize(Map<String, Object> mapVo) throws DataAccessException {
		List<Map<String, Object>> list = hrSelectMapper.queryPrize(mapVo);
		return JSONArray.toJSONString(list);
	}

	/**
	 * 代码项下拉加载
	 * @autor Lcl
	 */
	@Override
	public String queryAttendFieldOption(Map<String, Object> mapVo) {
		List<Map<String, Object>> list = hrSelectMapper.queryAttendFieldOption(mapVo);
		return JSONArray.toJSONString(list);
	}


	/**
	 * 查询区域、排班、倒班规则、医护属性
	 */
	@Override
	public String queryDicAreaAttr(Map<String, Object> mapVo) throws DataAccessException {
		List<Map<String, Object>> list = hrSelectMapper.queryDicAreaAttr(mapVo);
		return JSONArray.toJSONString(list);
	}
	
	/**
	 * 考勤项目树
	 */
	@Override
	public String queryAttendItemTree(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list = hrSelectMapper.queryAttendItemTree(entityMap);
		Map<String, Object> top = new HashMap<String, Object>();
		top.put("id", 0);
		top.put("pId", "");
		if(SessionManager.getTypeCode().equals("0")){
			top.put("name", SessionManager.getHosSimple());
		}else{
			top.put("name", SessionManager.getGroupSimple());
		}
		
		list.add(top);
		return JSONArray.toJSONString(list);
	}
	/**
	 * 考勤项目
	 */
	@Override
	public String queryAttendTypes(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list = hrSelectMapper.queryAttendTypes(entityMap);
		
		return JSONArray.toJSONString(list);
	}

	@Override
	public String queryAllAttendCode(Map<String, Object> entityMap)
			throws DataAccessException {
		List<Map<String, Object>> list = hrSelectMapper.queryAllAttendCode(entityMap);
		
		return JSONArray.toJSONString(list);
	}

	@Override
	public String queryAppEmpSelect(Map<String, Object> entityMap)
			throws DataAccessException {
		List<Map<String, Object>> list = hrSelectMapper.queryAppEmpSelect(entityMap);
		return JSONArray.toJSONString(list);
	}

	@Override
	public String queryHrEmpKindSelect(Map<String, Object> mapVo) throws DataAccessException {
		List<Map<String, Object>> list = hrSelectMapper.queryHrEmpKindSelect(mapVo);
		return JSONArray.toJSONString(list);
	}

	@Override
	public String queryUserSelect(Map<String, Object> entityMap)
			throws DataAccessException {
		List<Map<String, Object>> list = hrSelectMapper.queryUserSelect(entityMap);
		return JSONArray.toJSONString(list);
	}

	@Override
	public String queryHosTrainTypeSelect(Map<String, Object> entityMap)
			throws DataAccessException {
		List<Map<String, Object>> list = hrSelectMapper.queryHosTrainTypeSelect(entityMap);
		return JSONArray.toJSONString(list);
	}

	@Override
	public String queryHosTrainWaySelect(Map<String, Object> entityMap)
			throws DataAccessException {
		List<Map<String, Object>> list = hrSelectMapper.queryHosTrainWaySelect(entityMap);
		return JSONArray.toJSONString(list);
	}

	@Override
	public String queryHrExamWaySelect(Map<String, Object> mapVo) throws DataAccessException {
		List<Map<String, Object>> list = hrSelectMapper.queryHrExamWaySelect(mapVo);
		return JSONArray.toJSONString(list);
	}

	@Override
	public String queryHrTrainSiteSelect(Map<String, Object> mapVo)
			throws DataAccessException {
		List<Map<String, Object>> list = hrSelectMapper.queryHrTrainSiteSelect(mapVo);
		return JSONArray.toJSONString(list);
	}

	@Override
	public String queryEmpSelectInfo(Map<String, Object> mapVo)
			throws DataAccessException {
		List<Map<String, Object>> list = hrSelectMapper
				.queryEmpSelectInfo(mapVo);
		return ChdJson.toJsonLower(list);
	}

	@Override
	public String baseSelect(Map<String, Object> entityMap)
			throws DataAccessException {
		HrFiledTabStruc		hrFiledTabStruc=hrFiledTabStrucMapper.queryByCode(entityMap);
		if(hrFiledTabStruc!=null){
			String sql= hrFiledTabStruc.getRelated_sql();
			
			   Map<String, Object> sqlMap=new HashMap<String, Object>();
	              sqlMap  = matchAndReplaceSql(sql,entityMap);//匹配替换
						
						
	              entityMap.put("sql",sqlMap.get("result").toString());
			
			
			List<Map<String, Object>> list  = hrSelectMapper.baseSelect(entityMap);
			return toJSONString(list);
		}else{
			
		
		return null;
		}
	}


private String toJSONString(List<Map<String, Object>> list) {
	String json="[";
		for (Map<String, Object> map : list) {
			String jsonString="{";
		for(String key:map.keySet()){//keySet获取map集合key的集合  然后在遍历key即可
				String value = map.get(key).toString();//
				
				jsonString+="\""+key+"\":\""+value+"\",";
			
		}
		jsonString=jsonString.substring(0, jsonString.length()-1);
		jsonString+="},";
		json+=jsonString;
		}
		json=json.substring(0, json.length()-1);

		json+="]";
		return json;
	}

@Override
public String queryStManTree(Map<String, Object> entityMap)
		throws DataAccessException {
	List<Map<String, Object>> list = hrSelectMapper.queryStManTree(entityMap);
	return JSONArray.toJSONString(list);
}

public Map<String, Object>  matchAndReplaceSql(String sql,Map<String,Object> paraMap){
	Map<String, Object>  map=new HashMap<String, Object>();
	String result = sql        					//将所有字符都转成小写 为分割处理做准备
		   	   .replaceAll("\n|\t|\r|\\s{1,}", " ");	//去掉所有换行符 制表符 多个空格转换为单个空格  
	Matcher matcher = Pattern.compile("\\[.*?\\]").matcher(result);//#\\{.*?\\}
	//(@)(.*?),
	String value=null;
	while (matcher.find()) {
		
		String key = matcher.group() ;//获取匹配到的参数
		
		String column = key.replaceAll("\\[", "").replaceAll("\\]","");
		
		if(paraMap.get(column.toLowerCase()) == null){
			value="";
		}else{
		
		 value = String.valueOf(paraMap.get(column.toLowerCase()));
		}
		result = result.replace(key, "'" + value + "'");
		
	}
	map.put("result", result);
	
	return map;
}

@Override
public String queryManNumData(Map<String, Object> entityMap)
		throws DataAccessException {
	List<Map<String, Object>> list = hrSelectMapper.queryManNumData(entityMap);
	return ChdJson.toJson(list);
}		

}
