/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.serviceImpl.common;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.jdbc.ConnectionFactory;
import com.chd.base.util.ChdJson;
import com.chd.base.util.StringTool;
import com.chd.hrp.budg.dao.common.BudgBasicIndexStackMapper;
import com.chd.hrp.budg.dao.common.BudgChargeStandStackMapper;
import com.chd.hrp.budg.dao.common.BudgComTypeMapper;
import com.chd.hrp.budg.dao.common.BudgFunMapper;
import com.chd.hrp.budg.dao.common.BudgFunParaMapper;
import com.chd.hrp.budg.dao.common.BudgFunParaMethodMapper;
import com.chd.hrp.budg.dao.common.BudgFunTypeMapper;
import com.chd.hrp.budg.dao.common.BudgIncomeStackMapper;
import com.chd.hrp.budg.dao.common.BudgIndexStackMapper;
import com.chd.hrp.budg.entity.BudgFun;
import com.chd.hrp.budg.entity.BudgFunPara;
import com.chd.hrp.budg.entity.BudgFunParaMethod;
import com.chd.hrp.budg.service.common.BudgFunService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 预算相关函数表
 * @Table:
 * BUDG_FUN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("budgFunService")
public class BudgFunServiceImpl implements BudgFunService {

	private static Logger logger = Logger.getLogger(BudgFunServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgFunMapper")
	private final BudgFunMapper budgFunMapper = null;
	
	@Resource(name = "budgFunTypeMapper")
	private final BudgFunTypeMapper budgFunTypeMapper = null;
	
	@Resource(name = "budgComTypeMapper")
	private final BudgComTypeMapper budgComTypeMapper = null;
	
	@Resource(name = "budgFunParaMethodMapper")
	private final BudgFunParaMethodMapper budgFunParaMethodMapper = null;
	
	@Resource(name = "budgFunParaMapper")
	private final BudgFunParaMapper budgFunParaMapper = null;
	
	
	@Resource(name = "budgChargeStandStackMapper")
	private final BudgChargeStandStackMapper budgChargeStandStackMapper = null;
	
	@Resource(name = "budgBasicIndexStackMapper")
	private final BudgBasicIndexStackMapper budgBasicIndexStackMapper = null;
	
	@Resource(name = "budgIndexStackMapper")
	private final BudgIndexStackMapper budgIndexStackMapper = null;
	
	@Resource(name = "budgIncomeStackMapper")
	private final BudgIncomeStackMapper budgIncomeStackMapper = null;
	
    
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private SAXReader reader = new SAXReader();
	private Document doc = null;
	
	/**
	 * @Description 
	 * 添加预算相关函数表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		List<Map<String, Object>> funParaList = new ArrayList<Map<String, Object>>();// 存储参数
		
		// 查询  函数编码是否重复
		int funCode  = budgFunMapper.queryFunDataExist(entityMap);
		
		// 查询  函数名称是否重复
		int funName  = budgFunMapper.queryNameExist(entityMap);
		
		// 查询 函数 存储过程名称 是否重复
		int prcName = budgFunMapper.queryFunExistByPrcName(entityMap);
		
		if (funCode > 0 ) {// 检测函数是否重复
			return "{\"error\":\"函数编码：" + entityMap.get("fun_code").toString() + "重复.\"}";
		}
		
		if (funName > 0 ) {// 检测函数是否重复
			return "{\"error\":\"函数名称：" + entityMap.get("fun_name").toString() + "重复.\"}";
		}
		
		if (prcName > 0) {// 检测函数是否重复
			return "{\"error\":\"存储过程名称：" + entityMap.get("prc_name").toString() + "重复.\"}";
		}

		try {
			String fun_code = entityMap.get("fun_code").toString();// 函数编码

			String fun_name = entityMap.get("fun_name").toString();// 函数名称

			String pkg_name = entityMap.get("pkg_name").toString();// 存储过程包名

			String prc_name = entityMap.get("prc_name").toString();// 存储过程名称

			String fun_sql = entityMap.get("fun_sql").toString();//

			int count = 1;

			List<Map> detail = ChdJson.fromJsonAsList(Map.class, entityMap.get("para_fun_para_data").toString());// 参数列表

			String fun_method_chs = "";// 中午名称

			String fun_method_eng = "";// 英文名称

			fun_method_eng = pkg_name + "." + prc_name + "(";

			fun_method_chs = prc_name + "(";

			for (Map<String, Object> detailVo : detail) {

				Map<String, Object> funParaMap = new HashMap<String, Object>();

				if (!detailVo.containsKey("para_code")) {
					if (detail.size() == count) {
						fun_method_eng = fun_method_eng.substring(0, fun_method_eng.length() - 1);
						fun_method_chs = fun_method_chs.substring(0, fun_method_chs.length() - 1);
					}
					break;
				}

				String para_code = detailVo.get("para_code").toString();
				String para_name = detailVo.get("para_name").toString().split("\\s+")[1];

				if (detail.size() == count) {
					fun_method_eng = fun_method_eng + "" + para_code + "";
					fun_method_chs = fun_method_chs + "'" + para_name + "'";
				} else {
					fun_method_eng = fun_method_eng + "" + para_code + ",";
					fun_method_chs = fun_method_chs + "'" + para_name + "',";
				}

				funParaMap.put("group_id", entityMap.get("group_id"));
				funParaMap.put("hos_id", entityMap.get("hos_id"));
				funParaMap.put("copy_code", entityMap.get("copy_code"));
				funParaMap.put("fun_code", fun_code);
				funParaMap.put("para_code", para_code);
				funParaMap.put("para_name", para_name);
				funParaMap.put("stack_seq_no", detailVo.get("stack_seq_no"));
				funParaMap.put("com_type_code", detailVo.get("com_type_code"));
				funParaList.add(funParaMap);
				count++;
			}

			fun_method_eng = fun_method_eng + ")";

			fun_method_chs = fun_method_chs + ")";

			entityMap.put("spell_code", StringTool.toPinyinShouZiMu(fun_name));

			entityMap.put("wbx_code", StringTool.toWuBi(fun_name));

			entityMap.put("fun_method_eng", fun_method_eng);

			entityMap.put("fun_method_chs", fun_method_chs);

			if (!"".equals(entityMap.get("fun_sql").toString())) {
				byte[] bytes = entityMap.get("fun_sql").toString().getBytes("UTF-8");
				entityMap.put("fun_sql", bytes);
			} else {
				// entityMap.put("fun_sql", "");
			}

			budgFunMapper.add(entityMap);

			budgFunParaMapper.addBatch(funParaList);

			readXmlAndWriterXmlOrProc(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {


			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"添加失败\",\"state\":\"false\"}");

		}
		
	}
	
	public void readXmlAndWriterXmlOrProc(Map<String, Object> entityMap) {

		List<BudgFun> funList = budgFunMapper.queryBudgFunByFile(entityMap);//查询所有fun_sql不为空列表
		try {
			StringBuffer pkgTitle = new StringBuffer();
			StringBuffer sqlBody = new StringBuffer();
			if (funList.size() > 0) {
			

				String str1 = "";

				String str2 = "";

				pkgTitle.append(
						" CREATE OR REPLACE PACKAGE " + entityMap.get("pkg_name").toString().toUpperCase() + " AS \r\n");
				pkgTitle.append(" def_OK          CONSTANT NUMBER  := 0;      -- 成功\r\n  ");
				pkgTitle.append(" def_ERR         CONSTANT NUMBER  := -1 ;    -- 系统错误 \r\n");
				pkgTitle.append(" def_WARNING     CONSTANT NUMBER  := 100;    -- 系统警告\r\n ");

				sqlBody.append("CREATE OR REPLACE PACKAGE BODY " + entityMap.get("pkg_name").toString().toUpperCase()
						+ " AS \r\n");

				for (BudgFun item : funList) {
					if (item.getFun_sql() != null) {
						str1 = str1 + new String(item.getFun_sql(), "UTF-8").substring(0,
								new String(item.getFun_sql(), "UTF-8").indexOf(")") + 1) + ";";
						str2 = str2 + new String(item.getFun_sql(), "UTF-8");
					}
				}
				pkgTitle.append(str1);

				sqlBody.append(str2);

				pkgTitle.append(" END " + entityMap.get("pkg_name").toString().toUpperCase() + ";\r\n");

				sqlBody.append("  END " + entityMap.get("pkg_name").toString().toUpperCase() + ";\r\n");
				conn = ConnectionFactory.getSystemConnection();
				if (conn == null) {
					logger.debug("获取数据源连接失败！");
					return;
				}
				readFileContent(pkgTitle.toString());
				readFileContent(sqlBody.toString());
			} else {
				
				readFileContent("drop package pkg_prm_fun");
				
			}

		} catch (UnsupportedEncodingException e) {
			throw new SysException();
		}catch (Exception e) {
			throw new SysException();
		} finally {
			ConnectionFactory.closeAllConn(conn, "system", pstmt, null);
		}
	}

	private void readFileContent(String str) throws SQLException, DocumentException {
			pstmt = conn.prepareStatement(str);
			pstmt.execute();
	}

	/**
	 * @Description 暂不用（代码未调不可用）
	 * 批量添加预算相关函数表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgFunMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 更新预算相关函数表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		List<Map<String, Object>> funParaList = new ArrayList<Map<String, Object>>();// 存储参数
		try {

			int count = 1;

			String fun_code = entityMap.get("fun_code").toString();// 函数编码

			String fun_name = entityMap.get("fun_name").toString();// 函数名称

			String pkg_name = entityMap.get("pkg_name").toString();// 存储过程包名

			String prc_name = entityMap.get("prc_name").toString();// 存储过程名称

			List<Map> detail = ChdJson.fromJsonAsList(Map.class, entityMap.get("para_fun_para_data").toString());// 参数列表

			String fun_method_chs = "";// 中午名称

			String fun_method_eng = "";// 英文名称

			fun_method_eng = pkg_name + "." + prc_name + "(";

			fun_method_chs = prc_name + "(";

			for (Map<String, Object> detailVo : detail) {

				Map<String, Object> funParaMap = new HashMap<String, Object>();

				if (!detailVo.containsKey("para_code")) {
					if (detail.size() == count) {
						fun_method_eng = fun_method_eng.substring(0, fun_method_eng.length() - 1);// 去掉逗号
						fun_method_chs = fun_method_chs.substring(0, fun_method_chs.length() - 1);// 去掉逗号
					}
					break;
				}

				String para_code = detailVo.get("para_code").toString();
				String para_name = detailVo.get("para_name").toString().split("\\s+")[1];

				if (detail.size() == count) {
					fun_method_eng = fun_method_eng + "" + para_code + "";
					fun_method_chs = fun_method_chs + "'" + para_name + "'";
				} else {
					fun_method_eng = fun_method_eng + "" + para_code + ",";
					fun_method_chs = fun_method_chs + "'" + para_name + "',";
				}

				funParaMap.put("group_id", entityMap.get("group_id"));
				funParaMap.put("hos_id", entityMap.get("hos_id"));
				funParaMap.put("copy_code", entityMap.get("copy_code"));
				funParaMap.put("fun_code", fun_code);
				funParaMap.put("para_code", para_code);
				funParaMap.put("para_name", para_name);
				funParaMap.put("stack_seq_no", detailVo.get("stack_seq_no"));
				funParaMap.put("com_type_code", detailVo.get("com_type_code"));
				funParaList.add(funParaMap);
				count++;
			}

			fun_method_eng = fun_method_eng + ")";

			fun_method_chs = fun_method_chs + ")";

			entityMap.put("spell_code", StringTool.toPinyinShouZiMu(fun_name));

			entityMap.put("wbx_code", StringTool.toWuBi(fun_name));

			entityMap.put("fun_method_eng", fun_method_eng);

			entityMap.put("fun_method_chs", fun_method_chs);

			if (!"".equals(entityMap.get("fun_sql").toString())) {
				byte[] bytes = entityMap.get("fun_sql").toString().getBytes("UTF-8");
				entityMap.put("fun_sql", bytes);
			} else {
				// entityMap.put("fun_sql", "");
			}

			entityMap.put("spell_code", StringTool.toPinyinShouZiMu(fun_name));

			entityMap.put("wbx_code", StringTool.toWuBi(fun_name));

			budgFunMapper.update(entityMap);

			budgFunParaMapper.delete(entityMap);

			budgFunParaMapper.addBatch(funParaList);

			readXmlAndWriterXmlOrProc(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"更新失败\",\"state\":\"false\"}");
			
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBudgFun\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新预算相关函数表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  budgFunMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchBudgFun\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除预算相关函数表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = budgFunMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBudgFun\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除预算相关函数表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgFunParaMapper.deleteBatch(entityList);
			
			budgFunMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"删除失败\",\"state\":\"false\"}");

		}	
	}
	
	/**
	 * @Description 
	 * 添加预算相关函数表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdate(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象预算相关函数表
		Map<String,Object> budgFun = budgFunMapper.queryByCode(entityMap);

		if (budgFun != null) {

			int state = budgFunMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = budgFunMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdateBudgFun\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集预算相关函数表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<BudgFun> list = (List<BudgFun>) budgFunMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<BudgFun> list = (List<BudgFun>) budgFunMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 根据 函数编码 查询其参数
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryBudgFunParaByFunCode(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = (List<Map<String,Object>>) budgFunMapper.queryBudgFunParaByFunCode(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<BudgFunPara> list = (List<BudgFunPara>) budgFunMapper.queryBudgFunParaByFunCode(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象预算相关函数表<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return budgFunMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取预算相关函数表<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return BudgFun
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return budgFunMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * 函数类型树
	 * @param mapVo
	 * @return
	 */
	@Override
	public String queryFunTypeTree(Map<String, Object> mapVo) {
		
		List<Map<String,Object>> typeList = (List<Map<String, Object>>) budgFunTypeMapper.query(mapVo);
		
		// 拼接返回json
		StringBuilder json = new StringBuilder();
		
		json.append("{Rows:[");
		
		if(typeList.size()> 0){
			for(Map<String,Object> item : typeList){
				
				json.append("{");
				
				json.append("\"id\":\""+item.get("type_code")+"\","+"\"text\":"+"\""+item.get("type_code")+" "+item.get("type_name")+"\"");

				json.append("},");
			}
		}else{
			json.append(",");
		}

		json.setCharAt(json.length() - 1, ']');
		json.append("}");

        return json.toString();  
    
	}
	@Override
	public List<?> queryExists(Map<String, Object> entityMap)	throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 查询函数部件
	 */
	@Override
	public String queryComTypePara(Map<String, Object> mapVo) throws DataAccessException{
		
		
		List<Map<String,Object>> list = budgFunMapper.queryComTypePara(mapVo); 
		 
		if (list.size() >0){
	 
			return ChdJson.toJson(list);

		}
		else { 
			return "{\"error\":\"该函数编码没有配置部件类型\"}";
		}
		
		
	}
	
	/**
	 * 重新 加载 函数存储过程
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String initBudgFunProc(Map<String, Object> entityMap) throws DataAccessException {
		List<BudgFun> list = (List<BudgFun>) budgFunMapper.query(entityMap);

		for (BudgFun item : list) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("group_id", item.getGroup_id());
			map.put("hos_id", item.getHos_id());
			map.put("copy_code", item.getCopy_code());
			map.put("pkg_name", item.getPkg_name());
			readXmlAndWriterXmlOrProc(map);
		}
		return "{\"msg\":\"加载成功.\",\"state\":\"true\"}";
	}
	
	RowBounds rowBoundsALL = new RowBounds(0, 20);
	
	@Override
	public String queryBudgComType(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
			
		}else{
			
			rowBounds = rowBoundsALL;
			 
		}
		
		return JSON.toJSONString(budgFunMapper.queryBudgComType(entityMap, rowBounds));
	}
	
	/**
	 * 参数下拉框 查询
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryBudgFunParaByDict(Map<String, Object> map) throws DataAccessException {
		
		BudgFunParaMethod bfpm= budgFunParaMethodMapper.queryByCode(map);
		
		if (bfpm!=null){
			
			map.put("sql", bfpm.getPara_sql().replace("{", "#{"));
			 
			
			return JSON.toJSONString(budgFunParaMethodMapper.queryFunParaByDict(map));
		}
		else {
			return "{\"error\":\"该函数编码没有配置部件类型\"}";
			
		}
		
		
	}
	
	/**
	 * 指标 参数栈 保存
	 */
	@Override
	public String saveBudgIndexStack(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			//指标类型 01 基本指标 02费用标准 03 预算指标 04 收入科目
			// 01 基本指标 budg_basic_index_stack  02费用标准  budg_charge_stan_stack   03 预算指标 budg_index_stack
			String index_type_code = String.valueOf(entityList.get(0).get("index_type_code"));
			
			if("01".equals(index_type_code)){//基本指标
				
				budgBasicIndexStackMapper.deleteBatch(entityList);
				
				budgBasicIndexStackMapper.addBatch(entityList);
				
			}else if("02".equals(index_type_code)){//费用指标
				
				budgChargeStandStackMapper.deleteBatch(entityList);
				
				budgChargeStandStackMapper.addBatch(entityList);
				
			}else if("03".equals(index_type_code)){//预算指标
				
				budgIndexStackMapper.deleteBatch(entityList);
				
				budgIndexStackMapper.addBatch(entityList);
				
			}else if("04".equals(index_type_code)){
				
				budgIncomeStackMapper.deleteBatch(entityList);
				
				budgIncomeStackMapper.addBatch(entityList);
				
			}
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"添加失败！\",\"state\":\"true\"}");
			//return "{\"error\":\"添加失败\"}";

		}
		
	}
	
}
