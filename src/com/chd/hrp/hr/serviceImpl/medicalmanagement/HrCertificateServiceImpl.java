package com.chd.hrp.hr.serviceImpl.medicalmanagement;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.druid.sql.visitor.functions.If;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.ReadFiles;
import com.chd.hrp.hr.dao.medicalmanagement.HrCertificateMapper;
import com.chd.hrp.hr.dao.medicalmanagement.HrEmpTechExecMapper;
import com.chd.hrp.hr.dao.medicalmanagement.HrEmpTechRefMapper;
import com.chd.hrp.hr.entity.medicalmanagement.HrCertificate;
import com.chd.hrp.hr.entity.medicalmanagement.HrInnovation;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.chd.hrp.hr.service.medicalmanagement.HrCertificateService;
import com.github.pagehelper.PageInfo;

/**
 * 证件管理
 * 
 * @author Administrator
 *
 */
@Service("hrCertificateService")
public class HrCertificateServiceImpl implements HrCertificateService {
	private static Logger logger = Logger
			.getLogger(HrCertificateServiceImpl.class);

	@Resource(name = "hrCertificateMapper")
	private final HrCertificateMapper hrCertificateMapper = null;

	@Resource(name = "hrEmpTechExecMapper")
	private final HrEmpTechExecMapper hrEmpTechExecMapper = null;
	
	//引入DAO操作
	@Resource(name = "hrEmpTechRefMapper")
	private final HrEmpTechRefMapper hrEmpTechRefMapper = null;
	
	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;
    
	// 默认显示20条数据
	RowBounds rowBoundsALL = new RowBounds(0, 20);

	/**
	 * 证件管理增加
	 */
	@Override
	public String addCertificate(Map<String, Object> entityMap)
			throws DataAccessException {

		try {
			Calendar calendar = new GregorianCalendar();
			Date date = new Date();
			calendar.setTime(date); 
			calendar.add(calendar.YEAR, 10);
			date=calendar.getTime();
		
			JSONArray json = JSONArray.parseArray(String.valueOf(entityMap.get("paramVo")));
			Iterator it = json.iterator();
			while (it.hasNext()) {
				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("group_id", entityMap.get("group_id"));
				map.put("hos_id", entityMap.get("hos_id"));
				map.put("cert_name",  jsonObj.get("cert_name"));
				//map.put("cert_code", hrCertificateMapper.queryHrCertSeq());
				if(ChdJson.validateJSON(String.valueOf(jsonObj.get("begin_date")))){
					map.put("begin_date", DateUtil.stringToDate( jsonObj.get("begin_date").toString(), "yyyy-MM-dd"));//有效日期
				}
				if(ChdJson.validateJSON(String.valueOf(jsonObj.get("end_date")))){
					map.put("end_date", DateUtil.stringToDate( jsonObj.get("end_date").toString(), "yyyy-MM-dd"));//有效日期
				}else{
					map.put("end_date", date);
				}
				String max_cert = hrCertificateMapper.queryByCodeNO(map);
				if(max_cert == null || "".equals(max_cert)){
					max_cert =  "0001";
        		}else{
        			int i =Integer.parseInt(max_cert)+1;
        			max_cert = String.format("%04d",i) ;
        		}
				map.put("cert_code", max_cert);
        		int count = hrCertificateMapper.queryByCodeCount(map);
				if(count >0){
					hrCertificateMapper.update(map);
				}else{
					hrCertificateMapper.add(map);
				}
				
			}
			
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * 证件管理修改
	 */
	@Override
	public String updateCertificate(Map<String, Object> entityMap)
			throws DataAccessException {

		try {

			@SuppressWarnings("unused")
			int state = hrCertificateMapper.update(entityMap);

			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * 证件管理删除
	 */
	@Override
	public String deleteCertificate(List<HrCertificate> entityList)
			throws DataAccessException {

		try {
			
			Map<String,Object> mapVo=new HashMap<String,Object>();
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			
			hrCertificateMapper.deleteCertificate(entityList,mapVo);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	/**
	 * 证件管理查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String queryCertificate(Map<String, Object> entityMap)
			throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrCertificate> list = (List<HrCertificate>) hrCertificateMapper
					.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(),
					sysPage.getPagesize());

			List<HrCertificate> list = (List<HrCertificate>) hrCertificateMapper
					.query(entityMap, rowBounds);

			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * 跳转修改页面
	 */
	@Override
	public HrCertificate queryByCodeCertificate(
			Map<String, Object> entityMap) throws DataAccessException {
		return hrCertificateMapper.queryByCode(entityMap);
	}

   /**
    * 查询是否存在
    */
	@Override
	public HrCertificate queryByCode(HrCertificate hrNursingPromotion)
			throws DataAccessException {
		return hrCertificateMapper.queryByCode(hrNursingPromotion);
	}

	@Override
	public HrCertificate queryByCodeAdd(Map<String, Object> entityMap)
			throws DataAccessException {
		return null;
		}
	
	
	@Override
	public List<Map<String, Object>> queryCertificateByPrint(
			Map<String, Object> entityMap) throws DataAccessException {
	
		 List<Map<String,Object>> list = ChdJson.toListLower(hrCertificateMapper.queryCertificateByPrint(entityMap));
		 return list;
	}

	@Override
	public String auditHrTechRec(HrCertificate hrNursingPromotion)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String unauditHrHrTechRec(HrCertificate hrNursingPromotion)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String dispassHrHrTechRec(HrCertificate hrNursingPromotion)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String importCertificate(Map<String, Object> mapVo) throws DataAccessException {
		int successNum = 0;
		int failureNum = 0;
		StringBuilder failureMsg = new StringBuilder();
		List<Map<String,Object>> saveList = new ArrayList<Map<String,Object>>();
		
		boolean dateFlag=true;
		String max_cert=null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = new GregorianCalendar();
		Date date = new Date();
		calendar.setTime(date); 
		calendar.add(calendar.YEAR, 10);
		date=calendar.getTime();
		try {
			List<Map<String, List<String>>> list = ReadFiles.readFiles(mapVo);
			if(list != null && list.size() > 0){
				for (Map<String, List<String>> map : list) {
					
					Map<String,Object> saveMap = new HashMap<String,Object>();
					saveMap.put("group_id", SessionManager.getGroupId());
					saveMap.put("hos_id", SessionManager.getHosId());
					saveMap.put("cert_name", map.get("cert_name").get(1));
				
					if(map.get("begin_date").get(1)!=null || !"null".equals(map.get("begin_date").get(1))){
						dateFlag=  DateUtil.CheckDate(map.get("begin_date").get(1).toString());
				        if (!dateFlag) {
				        	failureMsg.append(map.get("begin_date").get(1)+"<br/> 日期格式不合法;日期格式举例：2018-01-01 ");
							failureNum++;
							continue;
				        }else{
				        	saveMap.put("begin_date", map.get("begin_date").get(1));
				        }	
					}else{
						saveMap.put("begin_date", map.get("begin_date").get(1));
					}
					
					if(map.get("end_date").get(1)==null || "null".equals(map.get("end_date").get(1))){
						saveMap.put("end_date", sdf.format(date));
					}else{
						dateFlag=  DateUtil.CheckDate(map.get("end_date").get(1).toString());
				        if (!dateFlag) {
				        	failureMsg.append(map.get("end_date").get(1)+"<br/> 日期格式不合法;日期格式举例：2018-01-01 ");
							failureNum++;
							continue;
				        }else{
				        	saveMap.put("end_date", map.get("end_date").get(1));
				        }	
						
					}
					
					if(max_cert==null || "".equals(max_cert)){
						max_cert = hrCertificateMapper.queryByCodeNO(saveMap);
					}
					
					if(max_cert == null || "".equals(max_cert)){
						max_cert =  "0001";
	        		}else{
	        			int i =Integer.parseInt(max_cert)+1;
	        			max_cert = String.format("%04d",i) ;
	        		}
					saveMap.put("cert_code", max_cert);
					
					HrCertificate type = hrCertificateMapper.queryByCode(saveMap);
					
					if(type != null){
						failureMsg.append("<br/> 已存在相同的记录; ");
						failureNum++;
						continue;
					}
					successNum++;
					saveList.add(saveMap);
				}
				if(saveList.size() > 0){
					hrCertificateMapper.addBatchImport(saveList);
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
		
	}}
