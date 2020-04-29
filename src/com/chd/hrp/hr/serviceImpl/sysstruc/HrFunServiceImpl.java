
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.hr.serviceImpl.sysstruc;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.jdbc.ConnectionFactory;
import com.chd.base.util.ChdJson;
import com.chd.base.util.StringTool;
import com.chd.hrp.hr.dao.sysstruc.HrComTypeMapper;
import com.chd.hrp.hr.dao.sysstruc.HrFunLinkMapper;
import com.chd.hrp.hr.dao.sysstruc.HrFunMapper;
import com.chd.hrp.hr.dao.sysstruc.HrFunParaMapper;
import com.chd.hrp.hr.dao.sysstruc.HrFunParaMethodMapper;
import com.chd.hrp.hr.entity.sysstruc.HrFun;
import com.chd.hrp.hr.service.sysstruc.HrFunService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 9908 绩效函数表
 * @Table: PRM_FUN
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("hrFunService")
public class HrFunServiceImpl implements HrFunService {

	private static Logger logger = Logger.getLogger(HrFunServiceImpl.class);

	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private SAXReader reader = new SAXReader();
	private Document doc = null;

	// 引入DAO操作
	@Resource(name = "hrFunMapper")
	private final HrFunMapper hrFunMapper = null;

	@Resource(name = "hrFunLinkMapper")
	private final HrFunLinkMapper hrFunLinkMapper = null;

	@Resource(name = "hrComTypeMapper")
	private final HrComTypeMapper hrComTypeMapper = null;

	@Resource(name = "hrFunParaMapper")
	private final HrFunParaMapper hrFunParaMapper = null;

	@Resource(name = "hrFunParaMethodMapper")
	private final HrFunParaMethodMapper hrFunParaMethodMapper = null;

	/**
	 * @Description 添加9908 绩效函数表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addPrmFun(Map<String, Object> entityMap) throws DataAccessException {

		List<Map<String, Object>> funParaList = new ArrayList<Map<String, Object>>();// 存储参数

		HrFun hf = queryPrmFunByCode(entityMap);
		HrFun hf2 = hrFunMapper.queryPrmFunByPrcName(entityMap);
		if (hf != null) {// 检测函数是否重复
			return "{\"error\":\"函数编码：" + entityMap.get("fun_code").toString() + "重复.\"}";
		}

		if (hf2 != null) {// 检测函数是否重复
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

			String fun_method_chs = "";// 中文名称

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
				String para_name = detailVo.get("para_name").toString()/*.split("\\s+")[1]*/;

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
				funParaMap.put("stack_seq_no", detailVo.get("stack_seq_no") == null ? "":detailVo.get("stack_seq_no"));
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

			hrFunMapper.addPrmFun(entityMap);

			hrFunParaMapper.addBatchPrmFunPara(funParaList);

			readXmlAndWriterXmlOrProc(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("添加失败 ");

			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addPrmFun\"}";

		}
	}

	public void readXmlAndWriterXmlOrProc(Map<String, Object> entityMap) {

		List<HrFun> funList = hrFunMapper.queryPrmFunByFile(entityMap);//查询所有fun_sql不为空列表
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

				for (HrFun prmFun : funList) {
					if (prmFun.getFun_sql() != null) {
						str1 = str1 + new String(prmFun.getFun_sql(), "UTF-8").substring(0,
								new String(prmFun.getFun_sql(), "UTF-8").indexOf(")") + 1) + ";";
						str2 = str2 + new String(prmFun.getFun_sql(), "UTF-8");
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
				
				readFileContent("drop package pkg_hpm_fun");
				
			}

		} catch (UnsupportedEncodingException e) {
			throw new SysException();
		}catch (Exception e) {
			logger.error(e.getMessage());
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
	 * @Description 批量添加9908 绩效函数表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatchPrmFun(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			hrFunMapper.addBatchPrmFun(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchPrmFun\"}";

		}

	}

	/**
	 * @Description 更新9908 绩效函数表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updatePrmFun(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> funParaList = new ArrayList<Map<String, Object>>();// 存储参数
		try {

			int count = 1;

			String fun_code = entityMap.get("fun_code").toString();// 函数编码

			String fun_name = entityMap.get("fun_name").toString();// 函数名称

			String pkg_name = entityMap.get("pkg_name").toString();// 存储过程包名

			String prc_name = entityMap.get("prc_name").toString();// 存储过程名称

			List<Map> detail = ChdJson.fromJsonAsList(Map.class, entityMap.get("para_fun_para_data").toString());// 参数列表

			String fun_method_chs = "";// 中文名称

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
				String para_name = detailVo.get("para_name").toString();

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
				funParaMap.put("stack_seq_no", detailVo.get("stack_seq_no") == null ? "":detailVo.get("stack_seq_no"));
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

			hrFunMapper.updatePrmFun(entityMap);

			hrFunParaMapper.deletePrmFunPara(entityMap);

			hrFunParaMapper.addBatchPrmFunPara(funParaList);

			readXmlAndWriterXmlOrProc(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateFun\"}";
		}
	}

	/**
	 * @Description 批量更新9908 绩效函数表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatchPrmFun(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			hrFunMapper.updateBatchPrmFun(entityList);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchPrmFun\"}";

		}

	}

	/**
	 * @Description 删除9908 绩效函数表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deletePrmFun(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = hrFunMapper.deletePrmFun(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deletePrmFun\"}";

		}

	}

	/**
	 * @Description 批量删除9908 绩效函数表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchPrmFun(List<HrFun> entityList) throws DataAccessException {

		try {
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			
			for(HrFun fun : entityList){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("group_id",fun.getGroup_id());
				map.put("hos_id",fun.getHos_id());
				map.put("copy_code",fun.getCopy_code());
				map.put("fun_code",fun.getFun_code());
				map.put("pkg_name", fun.getPkg_name());
				list.add(map);
				
			}
			hrFunParaMapper.deleteBatchPrmFunPara(list);
			hrFunMapper.deleteBatchPrmFun(list);

			for (Map<String, Object> map : list) {
				map.put("pkg_name", map.get("pkg_name").toString());
				readXmlAndWriterXmlOrProc(map);
			}

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchPrmFun\"}";

		}
	}

	/**
	 * @Description 查询结果集9908 绩效函数表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryPrmFun(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrFun> list = hrFunMapper.queryPrmFun(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HrFun> list = hrFunMapper.queryPrmFun(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * @Description 获取对象9908 绩效函数表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public HrFun queryPrmFunByCode(Map<String, Object> entityMap) throws DataAccessException {
		return hrFunMapper.queryPrmFunByCode(entityMap);
	}

	@Override
	public HrFun queryPrmFunComtype(Map<String, Object> entityMap) throws DataAccessException {
		return hrFunMapper.queryPrmFunComtype(entityMap);
	}

	@Override
	public String initPrmFunProc(Map<String, Object> entityMap) throws DataAccessException {
		List<HrFun> list = hrFunMapper.queryPrmFun(entityMap);

		for (HrFun prmFun : list) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("group_id", prmFun.getGroup_id());
			map.put("hos_id", prmFun.getHos_id());
			map.put("copy_code", prmFun.getCopy_code());
			map.put("pkg_name", prmFun.getPkg_name());
			readXmlAndWriterXmlOrProc(map);
		}
		return "{\"msg\":\"加载成功.\",\"state\":\"true\"}";
	}

}
