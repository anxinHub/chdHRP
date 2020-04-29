package com.chd.task.ass;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.chd.base.util.UUIDLong;

@SuppressWarnings("serial")
public class UploadFileServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getParameter("filePath");
		downloadFileByOutputStream(path, response);// 下载文件，通过OutputStream流
	}

	/**
	 * 下载文件，通过OutputStream流
	 * 
	 * @param response
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void downloadFileByOutputStream(String filePath, HttpServletResponse response) throws FileNotFoundException, IOException {
		// 1.获取要下载的文件的绝对路径
		String realPath = this.getServletContext().getRealPath("/ass/download/" + filePath);
		// 2.获取要下载的文件名
		String fileName = realPath.substring(realPath.lastIndexOf("\\") + 1);
		// 设置content-disposition响应头控制浏览器以下载的形式打开文件，中文文件名要使用URLEncoder.encode方法进行编码，否则会出现文件名乱码
		response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
		//获取文件大小
		File file = new File(getServletContext().getRealPath("/ass/download/" + filePath));
		response.setContentLength((int)file.length());
		// 4.获取要下载的文件输入流
		InputStream in = new FileInputStream(realPath);
		int len = 0;
		// 5.创建数据缓冲区
		byte[] buffer = new byte[1024];
		// 6.通过response对象获取OutputStream流
		OutputStream out = response.getOutputStream();
		// 7.将FileInputStream流写入到buffer缓冲区
		while ((len = in.read(buffer)) > 0) {
			// 8.使用OutputStream将缓冲区的数据输出到客户端浏览器
			out.write(buffer, 0, len);
		}
		in.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String path = request.getParameter("paths");
		// 创建文件项目工厂对象
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// 设置文件上传路径
		String upload = this.getServletContext().getRealPath(path);
		//获取文件大小
		File file = new File(getServletContext().getRealPath(path));
		response.setContentLength((int)file.length());
		// 获取系统默认的临时文件保存路径，该路径为Tomcat根目录下的temp文件夹
		String temp = System.getProperty("java.io.tmpdir");
		// 设置缓冲区大小为 5M
		factory.setSizeThreshold(1024 * 1024 * 5);
		// 设置临时文件夹为temp
		factory.setRepository(new File(temp));
		// 用工厂实例化上传组件,ServletFileUpload 用来解析文件上传请求
		ServletFileUpload servletFileUpload = new ServletFileUpload(factory);

		// 解析结果放在List中
		try {
			List<FileItem> list = servletFileUpload.parseRequest(request);

			for (FileItem item : list) {
				String name = item.getFieldName();
				InputStream is = item.getInputStream();

				if (name.contains("content")) {
					System.out.println(inputStream2String(is));
				} else if (name.contains("file")) {
					try {
						inputStream2File(is, upload + "\\" + UUIDLong.getInstance().absStringUUID() + "_" + item.getName());
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

			out.write("success");
		}
		catch (FileUploadException e) {
			e.printStackTrace();
			out.write("failure");
		}

		out.flush();
		out.close();
	}

	// 流转化成字符串
	public static String inputStream2String(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int i = -1;
		while ((i = is.read()) != -1) {
			baos.write(i);
		}
		return baos.toString();
	}

	// 流转化成文件
	public static void inputStream2File(InputStream is, String savePath) throws Exception {
		System.out.println("文件保存路径为:" + savePath);
		File file = new File(savePath);
		InputStream inputSteam = is;
		BufferedInputStream fis = new BufferedInputStream(inputSteam);
		FileOutputStream fos = new FileOutputStream(file);
		int f;
		while ((f = fis.read()) != -1) {
			fos.write(f);
		}
		fos.flush();
		fos.close();
		fis.close();
		inputSteam.close();

	}

}
