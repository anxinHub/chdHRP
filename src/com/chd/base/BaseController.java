/** 
 * 2015-1-2 
 * BaseController.java 
 * author:pengjin
 */
package com.chd.base;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;

import com.chd.base.SysPage;

/** 基于@ExceptionHandler异常处理 */

@Controller
public class BaseController {

	private static Logger logger = Logger.getLogger(BaseController.class);

	// 封装分页参数
	public Map<String, Object> getPage(Map<String, Object> mapVo) {
		SysPage sysPage = new SysPage();
		if (mapVo.get("page") == null || mapVo.get("pagesize") == null) {
			sysPage.setPage(0);
			sysPage.setPagesize(1);
			sysPage.setTotal(-1);
			logger.debug("进入不分页模式");
		} else {
			logger.debug("进入物理分页模式");
			// 重新查询
			if (mapVo.get("changepage") == null) {
				sysPage.setPage(0);
			} else {
				sysPage.setPage(Integer.parseInt(mapVo.get("page").toString()));
			}
			if (mapVo.get("total") == null) {
				sysPage.setTotal(0);
			} else {
				sysPage.setTotal(Integer
						.parseInt(mapVo.get("total").toString()));
			}
			sysPage.setPagesize(Integer.parseInt(mapVo.get("pagesize")
					.toString()));
			logger.debug("page：" + sysPage.getPage() + ",pagesize："
					+ sysPage.getPagesize() + ",totalCount："
					+ sysPage.getTotal());
		}
//		mapVo.remove("page");
//		mapVo.remove("pagesize");
//		mapVo.remove("total");
//		mapVo.remove("changepage");
		mapVo.put("sysPage", sysPage);

		return mapVo;
	}
	
	public Map<String, Object> getPage1(Map<String, Object> mapVo) {
		SysPage sysPage = new SysPage();
		if (mapVo.get("page") == null || mapVo.get("pagesize") == null) {
			sysPage.setPage(0);
			sysPage.setPagesize(1);
			sysPage.setTotal(-1);
			logger.debug("进入不分页模式");
		} else {
			logger.debug("进入物理分页模式");
			// 重新查询
			if (mapVo.get("changepage") == null) {
				sysPage.setPage(0);
			} else {
				sysPage.setPage(Integer.parseInt(mapVo.get("page").toString()));
			}
			if (mapVo.get("total") == null) {
				sysPage.setTotal(0);
			} else {
				sysPage.setTotal(Integer
						.parseInt(mapVo.get("total").toString()));
			}
			sysPage.setPagesize(Integer.parseInt(mapVo.get("pagesize")
					.toString()));
			logger.debug("page：" + mapVo.get("page") + ",pagesize："
					+ mapVo.get("pagesize") + ",totalCount："
					+ mapVo.get("total"));
		}

		return mapVo;
	}

	public void printTemplate(HttpServletRequest request,
			HttpServletResponse response, String modePath, String fileName)
			throws IOException {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("UTF-8");
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;

		String ctxPath = request.getSession().getServletContext()
				.getRealPath("/")
				+ "\\" + "excelTemplate\\" + "\\" + modePath + "\\";
		String downLoadPath = ctxPath + fileName;
		try {
			long fileLength = new File(downLoadPath).length();
			response.setContentType("application/x-msdownload;");
			response.setHeader("Content-disposition", "attachment; filename=" 
					+ new String(fileName.getBytes("utf-8"), "ISO8859-1"));
			
			response.setHeader("Content-Length", String.valueOf(fileLength));
			bis = new BufferedInputStream(new FileInputStream(downLoadPath));
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
		}
	}
}
