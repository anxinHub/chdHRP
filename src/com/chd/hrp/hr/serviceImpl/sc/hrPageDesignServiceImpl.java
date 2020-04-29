package com.chd.hrp.hr.serviceImpl.sc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.chd.base.SysPage;
import com.chd.base.startup.LoadSystemInfo;
import com.chd.hrp.hr.dao.sc.HrPageDesignMapper;
import com.chd.hrp.hr.dao.sc.HrPageTemplateMapper;
import com.chd.hrp.hr.entity.sc.HrPageDesign;
import com.chd.hrp.hr.entity.sc.HrPageTemplate;
import com.chd.hrp.hr.service.sc.HrPageDesignService;


@Service("hrPageDesignService")
public class hrPageDesignServiceImpl implements HrPageDesignService{
	
	private static Logger logger = Logger.getLogger(hrPageDesignServiceImpl.class);
	
	// 引入DAO操作
	@Resource(name = "hrPageDesignMapper")
	private final HrPageDesignMapper hrPageDesignMapper = null;
	
	// 引入DAO操作
	@Resource(name = "hrPageTemplateMapper")
	private final HrPageTemplateMapper hrPageTemplateMapper = null;
	
	@Override
	public String queryHrPageDesignTree(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String,Object>> tree = hrPageDesignMapper.queryHrPageDesignTree(entityMap);
		return JSONArray.toJSONString(tree);
	}
	
	@Override
	public String addHrPageDesign(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			HrPageDesign hrPageDesign = hrPageDesignMapper.queryHrPageDesignByCode(entityMap);
			
			if(null != hrPageDesign){
				return "{\"error\":\"数据表编码已存在!\"}";
			}
			
			int pageSort = hrPageDesignMapper.queryHrPageDesignMaxSortNo(entityMap);
			entityMap.put("page_sort", pageSort);
			hrPageDesignMapper.addHrPageDesign(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败\"}";
		}
	}

	@Override
	public String updateHrPageDesign(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		  try {
				
			    hrPageDesignMapper.updateHrPageDesign(entityMap);
				return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);
	
				return "{\"error\":\"修改失败\"}";
			}
	}
	
	@Override
	public String deleteHrPageDesign(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		   try {
				
			    hrPageDesignMapper.deleteHrPageDesign(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
				
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);
	
				return "{\"error\":\"删除失败\"}";
			}
	}

	@Override
	public HrPageDesign queryHrPageDesignByCode(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return hrPageDesignMapper.queryHrPageDesignByCode(entityMap);
	}

	@Override
	public String queryHrPageDesignExport(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		List<HrPageDesign> list = hrPageDesignMapper.queryHrPageDesignExport(entityMap);
		return JSONArray.toJSONString(list, true);
	}
	
	@Override
	public String saveHrPageDesign(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		System.out.println(entityMap);
		try {
			if("".equals(entityMap.get("page_code"))){
				return "{\"error\":\"请选择数据表信息\"}";
			}
			
			hrPageDesignMapper.updateHrPageDesign(entityMap);//模板编码添加到pagedesign表里
			
			HrPageTemplate hrPageTemplate = hrPageTemplateMapper.queryHrPageTemplateByCode(entityMap);//查询模板信息
			
			if(null == hrPageTemplate){return "{\"error\":\"请选择模板!\",\"state\":\"false\"}";}//模板不能为空
			
			String template_path = LoadSystemInfo.getProjectUrl()+hrPageTemplate.getTemplate_path();
			
			File file = new File(template_path);//模板路径
			
			if(!file.exists()){return "{\"error\":\"模板地址不存在,请检查!\",\"state\":\"false\"}";}//模板路径是否存在
			
			BufferedReader  in = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
			
			String readTemplate = null;
			
			 int result=0;
			 while((result=in.read())!=-1)
			    {
				  readTemplate = readTemplate + (char)result;
			    }
			
	         in.close();
	         
			/*
			
            BufferedReader  in = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
            
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("C:/Users/lenovo/Desktop/template010001temp.jsp"),"UTF-8"));
          
            		
            String template_jsp = null;
            while ((template_jsp = in.readLine()) != null) {
            	template_jsp = createHrTemplateColumns(out,template_jsp);
                out.write(template_jsp);
                out.newLine();
            }
            //清楚缓存
            out.flush();
            //关闭流
            in.close();
            out.close();*/
			
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);

			return "{\"error\":\"生成失败\"}";
		}
	}

}
