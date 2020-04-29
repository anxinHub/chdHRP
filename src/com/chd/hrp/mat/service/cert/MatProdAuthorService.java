/**
* @Copyright: Copyright (c) 2017-9-13 
* @Company: 杭州亦童科技有限公司
*/
package com.chd.hrp.mat.service.cert;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.dao.DataAccessException;

public interface MatProdAuthorService {

	//列表查询
	public String queryMatProdAuthorList(Map<String, Object> map) throws Exception;
	//查询
	public Map<String, Object> queryMatProdAuthorById(Map<String, Object> map) throws Exception;
	//目标企业字典加载
	public String queryHosFacSup(Map<String, Object> map) throws Exception;
	//保存
	public Map<String, Object> saveMatProdAuthor(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception;
	//删除
	public Map<String, Object> deleteMatProdAuthor(Map<String, Object> map) throws Exception;
	//认证
	public Map<String, Object> updateMatProdAuthorToAuthent(Map<String, Object> map) throws Exception;
	//取消认证
	public Map<String, Object> updateMatProdAuthorToUnAuthent(Map<String, Object> map) throws Exception;
	//审核
	public Map<String, Object> updateMatProdAuthorToCheck(Map<String, Object> map) throws Exception;
	//取消审核
	public Map<String, Object> updateMatProdAuthorToUnCheck(Map<String, Object> map) throws Exception;
	//导入
	public Map<String, Object> addMatProdAuthorByImp(Map<String, Object> map) throws Exception;
	
	//查询材料列表
	public String queryMatInvList(Map<String, Object> map) throws Exception;
	
	//查询关联材料
	public String queryMatProdAuthorInvList(Map<String, Object> map) throws Exception;
	//保存关联材料
	public Map<String, Object> saveMatProdAuthorInv(Map<String, Object> map) throws Exception;
	//选择新证查询
	public String queryMatProdAuthorChooseList(Map<String, Object> map) throws Exception;
	//选择新证
	public Map<String, Object> updateMatProdAuthorToNewCert(Map<String, Object> map) throws Exception;
	//取消新证
	public Map<String, Object> updateMatProdAuthorToUnNewCert(Map<String, Object> map) throws Exception;
}
