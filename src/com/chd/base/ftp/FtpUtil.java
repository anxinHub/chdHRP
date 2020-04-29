package com.chd.base.ftp;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Hashtable;
import java.util.NoSuchElementException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.web.multipart.MultipartFile;

import com.chd.base.SessionManager;
import com.chd.base.jdbc.ConfigInit;
import com.chd.base.startup.LoadSystemInfo;
import com.chd.base.util.FileUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.UploadUtil;
import com.chd.base.util.qrcode.QRCodeFormat;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class FtpUtil {
	private static FTPClientPool fTPClientPool;
	private static final String CODE = "utf-8";
	private static final int BLACK = 0xff000000;
	private static final int WHITE = 0xFFFFFFFF;
	private static final String is_ftp = ConfigInit.getFtpConfig().getProperty("is_ftp");

	public static boolean uploadFile(MultipartFile uploadFile, String basePath, String filePath,
			HttpServletRequest request, HttpServletResponse response) {
		try {

			if (is_ftp.equals("true")) {
				getConntion();
				FTPClient client = fTPClientPool.borrowObject();
				client.setFileType(FTPClient.BINARY_FILE_TYPE);
				// 切换到上传目录 ,basepath需已存在
				if (!client.changeWorkingDirectory(basePath + filePath)) {
					// 如果目录不存在创建目录
					String[] dirs = filePath.split("/");
					String tempPath = basePath;
					for (String dir : dirs) {
						if (null == dir || "".equals(dir))
							continue;
						tempPath += "/" + dir;
						if (!client.changeWorkingDirectory(tempPath)) {
							if (!client.makeDirectory(tempPath)) {
								return false;
							} else {
								client.changeWorkingDirectory(tempPath);
							}
						}
					}
				}

				if (uploadFile != null) {
					saveFile(uploadFile, client);
				}
				fTPClientPool.returnObject(client);
			} else {
				Plupload plupload = new Plupload();
				plupload.setRequest(request);
				plupload.setMultipartFile(uploadFile);
				plupload.setName(uploadFile.getOriginalFilename());
				UploadUtil.upload(plupload, basePath + filePath, request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * 人力系统使用
	 * @param uploadFile
	 * @param basePath
	 * @param filePath
	 * @param request
	 * @param response
	 * @return
	 */
	public static boolean uploadFileExt(MultipartFile uploadFile, String basePath, String filePath,
			HttpServletRequest request, HttpServletResponse response) {
		try {

			if (is_ftp.equals("true")) {
				getConntion();
				FTPClient client = fTPClientPool.borrowObject();
				client.setFileType(FTPClient.BINARY_FILE_TYPE);
				// 切换到上传目录 ,basepath需已存在
				if (!client.changeWorkingDirectory(basePath + filePath)) {
					// 如果目录不存在创建目录
					String[] dirs = filePath.split("/");
					String tempPath = basePath;
					for (String dir : dirs) {
						if (null == dir || "".equals(dir))
							continue;
						tempPath += "/" + dir;
						if (!client.changeWorkingDirectory(tempPath)) {
							if (!client.makeDirectory(tempPath)) {
								return false;
							} else {
								client.changeWorkingDirectory(tempPath);
							}
						}
					}
				}

				if (uploadFile != null) {
					saveFile(uploadFile, client);
				}
				fTPClientPool.returnObject(client);
			} else {
				Plupload plupload = new Plupload();
				plupload.setRequest(request);
				plupload.setMultipartFile(uploadFile);
				plupload.setName(uploadFile.getOriginalFilename());
				UploadUtil.uploadExt(plupload, basePath + filePath, request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public static boolean uploadFile(MultipartFile uploadFile, String basePath, String filePath, String fileName,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			if (is_ftp.equals("true")) {
				getConntion();
				FTPClient client = fTPClientPool.borrowObject();
				client.setFileType(FTPClient.BINARY_FILE_TYPE);
				// 切换到上传目录 ,basepath需已存在
				if (!client.changeWorkingDirectory(basePath + filePath)) {
					// 如果目录不存在创建目录
					String[] dirs = filePath.split("/");
					String tempPath = basePath;
					for (String dir : dirs) {
						if (null == dir || "".equals(dir))
							continue;
						tempPath += "/" + dir;
						if (!client.changeWorkingDirectory(tempPath)) {
							if (!client.makeDirectory(tempPath)) {
								return false;
							} else {
								client.changeWorkingDirectory(tempPath);
							}
						}
					}
				}

				if (uploadFile != null) {
					saveFile(uploadFile, client, fileName);
				}
				fTPClientPool.returnObject(client);
			} else {
				Plupload plupload = new Plupload();
				plupload.setRequest(request);
				plupload.setMultipartFile(uploadFile);
				plupload.setName(fileName);
				UploadUtil.uploadMore(plupload,uploadFile, basePath + filePath, request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	private static boolean saveFile(MultipartFile file, FTPClient client) {
		boolean success = false;
		InputStream inStream = null;
		try {
			String fileName = new String(file.getOriginalFilename().getBytes("GBK"), "iso-8859-1");
			inStream = file.getInputStream();
			success = client.storeFile(fileName, inStream);
			if (success == true) {
				return success;
			}
		} catch (Exception e) {
		} finally {
			if (inStream != null) {
				try {
					inStream.close();
				} catch (IOException e) {
				}
			}
		}
		return success;
	}

	private static boolean saveFile(MultipartFile file, FTPClient client, String name) {
		boolean success = false;
		InputStream inStream = null;
		try {
			//String fileName = name + "."+ new String(file.getOriginalFilename().getBytes("GBK"), "iso-8859-1").split(".")[1];
			inStream = file.getInputStream();
			success = client.storeFile(name, inStream);
			if (success == true) {
				return success;
			}
		} catch (Exception e) {
		} finally {
			if (inStream != null) {
				try {
					inStream.close();
				} catch (IOException e) {
				}
			}
		}
		return success;
	}

	public static boolean copyFile(String ordPath, String newPath) throws IOException {
		InputStream inStream = null;
		OutputStream os = null;
		try {
			if (is_ftp.equals("true")) {
				getConntion();
				FTPClient client = fTPClientPool.borrowObject();
				client.setControlEncoding("gb2312");
				client.setFileType(FTPClient.BINARY_FILE_TYPE);
				String path = ordPath.substring(0, ordPath.lastIndexOf("/"));
				String fileName = ordPath.substring(ordPath.lastIndexOf("/") + 1, ordPath.length());
				client.changeWorkingDirectory(path);
				FTPFile[] fs = client.listFiles();
				String url = LoadSystemInfo.getProjectUrl();
				/**
				 * 下载文件到临时文件
				 */
				if (fs[0].getName().equals(fileName)) {
					newPath = newPath + "/";
					String dir = url + newPath.substring(0, newPath.lastIndexOf("/"));
					File fileMkdirs = new File(dir);
					if (!fileMkdirs.exists()) {
						fileMkdirs.mkdirs();
					}
					File localFile = new File(url + "/" + newPath + "/" + fileName);
					if (!localFile.exists()) {
						localFile.createNewFile();
					}
					os = new FileOutputStream(localFile);
					client.retrieveFile(new String(fileName.getBytes("GBK"), "iso-8859-1"), os);
				}

				/**
				 * 在FTP创建目录
				 */
				String[] dirs = newPath.split("/");
				String tempPath = "";
				for (String dir : dirs) {
					if (null == dir || "".equals(dir))
						continue;
					tempPath += "/" + dir;
					if (!client.changeWorkingDirectory(tempPath)) {
						if (!client.makeDirectory(tempPath)) {
							return false;
						} else {
							client.changeWorkingDirectory(tempPath);
						}
					}
				}

				
				//读取临时的文件
				File file = new File(url + "/" + newPath + "/" + fileName);
				inStream = new FileInputStream(file);
				
				//读取临时文件上传到FTP
				client.storeFile(new String(fileName.getBytes("GBK"), "iso-8859-1"), inStream);
				if(os != null){
					os.close();
				}
				if (inStream != null) {
					inStream.close();
				}
				
				//删除临时文件和文件夹
				FileUtil.deleteDirectory(url + "//" + SessionManager.getGroupId()+"//");

				fTPClientPool.returnObject(client);
			} else {
				String url = LoadSystemInfo.getProjectUrl();
				File oldFile = new File(url + ordPath);
				return oldFile.renameTo(new File(url + newPath));

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(os != null){
				os.close();
			}
			if (inStream != null) {
				inStream.close();
			}
		}
		return true;
	}
	

	public static boolean downloadFile(HttpServletResponse response, String fileName, String path) throws IOException {
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		response.setContentType("text/html;charset=GBK");
		try {
			if (is_ftp.equals("true")) {
				getConntion();
				FTPClient client = fTPClientPool.borrowObject();
				client.setControlEncoding("gb2312");
				client.setFileType(FTPClient.BINARY_FILE_TYPE);
				client.changeWorkingDirectory(path);
				FTPFile[] fs = client.listFiles();
				for (FTPFile ff : fs) {
					if (ff.getName().equals(fileName)) {

						response.setContentType("application/x-msdownload;");
						response.setHeader("Content-disposition",
								"attachment; filename=" + new String(fileName.getBytes("GBK"), "ISO8859-1"));
						bos = new BufferedOutputStream(response.getOutputStream());
						bis = new BufferedInputStream(client.retrieveFileStream(ff.getName()));
						byte[] buff = new byte[2048];
						int bytesRead;
						while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
							bos.write(buff, 0, bytesRead);
						}
						break;
					}
				}
				fTPClientPool.returnObject(client);
			} else {
				String ctxPath = LoadSystemInfo.getProjectUrl() + "/" + path + "/";
				String downLoadPath = ctxPath + fileName;
				long fileLength = new File(downLoadPath).length();
				response.setContentType("application/x-msdownload;");
				response.setHeader("Content-disposition",
						"attachment; filename=" + new String(fileName.getBytes("GBK"), "ISO8859-1"));
				response.setHeader("Content-Length", String.valueOf(fileLength));
				bis = new BufferedInputStream(new FileInputStream(downLoadPath));
				bos = new BufferedOutputStream(response.getOutputStream());
				byte[] buff = new byte[2048];
				int bytesRead;
				while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
					bos.write(buff, 0, bytesRead);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bos != null)
				bos.close();
		}
		return true;
	}
	
	public static boolean downloadFile64(HttpServletResponse response, String fileName, String path) throws IOException {
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		response.setContentType("text/html;charset=GBK");
		try {
			if (is_ftp.equals("true")) {
				getConntion();
				FTPClient client = fTPClientPool.borrowObject();
				client.setControlEncoding("gb2312");
				client.setFileType(FTPClient.BINARY_FILE_TYPE);
				client.changeWorkingDirectory(path);
				FTPFile[] fs = client.listFiles();
				for (FTPFile ff : fs) {
					if (ff.getName().equals(fileName)) {
						String fileN;
						fileN=fileName.replace(" ", "+");
						//Base64解密
						Base64 b6 = new Base64();  
					    String name=b6.getFromBase64(fileN);
					    
						response.setContentType("application/x-msdownload;");
						response.setHeader("Content-disposition",
								"attachment; filename=" + new String(name.getBytes("GBK"), "ISO8859-1"));
						bos = new BufferedOutputStream(response.getOutputStream());
						bis = new BufferedInputStream(client.retrieveFileStream(ff.getName()));
						byte[] buff = new byte[2048];
						int bytesRead;
						while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
							bos.write(buff, 0, bytesRead);
						}
						break;
					}
				}
				fTPClientPool.returnObject(client);
			} else {
				String ctxPath = LoadSystemInfo.getProjectUrl() + "/" + path + "/";
				String fileN;
				fileN=fileName.replace(" ", "+");
				
				String downLoadPath = ctxPath + fileN;
				//Base64解密
				Base64 b6 = new Base64();  
			    String name=b6.getFromBase64(fileN);
			   
				//String name=new String(b6.decodeBufferBase64(fileName));
				
				long fileLength = new File(downLoadPath).length();
				response.setContentType("application/x-msdownload;");
				response.setHeader("Content-disposition",
						"attachment; filename=" + new String(name.getBytes(), "ISO8859-1"));
				response.setHeader("Content-Length", String.valueOf(fileLength));
				bis = new BufferedInputStream(new FileInputStream(downLoadPath));
				bos = new BufferedOutputStream(response.getOutputStream());
				byte[] buff = new byte[2048];
				int bytesRead;
				while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
					bos.write(buff, 0, bytesRead);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bos != null)
				bos.close();
		}
		return true;
	}

	// 查看图片
	public static boolean viewImage(HttpServletResponse response, String fileName, String path) throws IOException {
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		response.setContentType("text/html;charset=GBK");
		try {
			if (is_ftp.equals("true")) {
				getConntion();
				FTPClient client = fTPClientPool.borrowObject();
				client.setControlEncoding("gb2312");
				client.setFileType(FTPClient.BINARY_FILE_TYPE);
				client.changeWorkingDirectory(path);
				FTPFile[] fs = client.listFiles();
				for (FTPFile ff : fs) {
					if (ff.getName().equals(fileName)) {
						response.setContentType("image/jpg");
						response.addHeader("pragma", "NO-cache");
						response.addHeader("Cache-Control", "no-cache");
						response.addDateHeader("Expries", 0);
						bos = new BufferedOutputStream(response.getOutputStream());
						bis = new BufferedInputStream(client.retrieveFileStream(ff.getName()));
						BufferedImage image = javax.imageio.ImageIO.read(bis);

						int tag_w = image.getWidth(); // 得到源图宽度
						int tag_h = image.getHeight(); // 得到源图高度

						BufferedImage tag = new BufferedImage(tag_w, tag_h, BufferedImage.TYPE_INT_RGB);
						tag.getGraphics().drawImage(image, 0, 0, tag_w, tag_h, null);
						JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
						encoder.encode(tag);
						break;
					}
				}
				fTPClientPool.returnObject(client);
			} else {
				String ctxPath = LoadSystemInfo.getProjectUrl() + "/" + path + "/";
				String downLoadPath = ctxPath + fileName;
				response.setContentType("image/jpg");
				response.addHeader("pragma", "NO-cache");
				response.addHeader("Cache-Control", "no-cache");
				response.addDateHeader("Expries", 0);
				bis = new BufferedInputStream(new FileInputStream(downLoadPath));
				bos = new BufferedOutputStream(response.getOutputStream());
				BufferedImage image = javax.imageio.ImageIO.read(bis);

				int tag_w = image.getWidth(); // 得到源图宽度
				int tag_h = image.getHeight(); // 得到源图高度

				BufferedImage tag = new BufferedImage(tag_w, tag_h, BufferedImage.TYPE_INT_RGB);
				tag.getGraphics().drawImage(image, 0, 0, tag_w, tag_h, null);
				JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
				encoder.encode(tag);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bos != null)
				bos.close();
			if (bis != null)
				bis.close();
		}
		return true;
	}

	private static void getConntion() throws Exception {
		FTPClientConfig config = new FTPClientConfig();
		config.getHost();
		config.getPort();
		config.getUsername();
		config.getPassword();
		config.getEncoding();
		config.getPassiveMode();
		config.getClientTimeout();

		FTPClientFactory factory = new FTPClientFactory(config);
		fTPClientPool = new FTPClientPool(factory);
	}

	/**
	 * 根据路径很文件名称删除FTP服务器文件
	 * 
	 * @param path
	 * @param file_name
	 * @return
	 */
	public static boolean deleteFile(String path, String file_name) {
		try {
			if (is_ftp.equals("true")) {
				file_name = new String(file_name.getBytes("GBK"), "iso-8859-1");
				getConntion();
				FTPClient client = fTPClientPool.borrowObject();
				client.changeWorkingDirectory(path);
				client.deleteFile(file_name);
				fTPClientPool.returnObject(client);
			} else {
				return FileUtil.deleteFile(LoadSystemInfo.getProjectUrl() + "/" + path + "/" + file_name);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	/*
	 * 根据路径很文件名称删除FTP服务器文件
	 * 
	 * @param path
	 * @param file_name
	 * @return
	 */
	public static boolean deleteFileTwo(String path, String file_name) {
		try {
			if (is_ftp.equals("true")) {
				file_name = new String(file_name.getBytes("GBK"), "iso-8859-1");
				getConntion();
				FTPClient client = fTPClientPool.borrowObject();
				client.changeWorkingDirectory(path);
				client.deleteFile(file_name);
				fTPClientPool.returnObject(client);
			} else {
				String projectUr=LoadSystemInfo.getProjectUrl();
				return FileUtil.deleteFile( projectUr.substring(0,projectUr.length()-1)+ path + "/" + file_name);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	// 删除文件夹
	public static boolean deleteDirectory(String pathname, String path) {
		try {
			if (is_ftp.equals("true")) {
				getConntion();
				FTPClient client = fTPClientPool.borrowObject();
				client.changeWorkingDirectory(path);
				client.removeDirectory(pathname);
				fTPClientPool.returnObject(client);
			} else {
				FileUtil.deleteFolder(LoadSystemInfo.getProjectUrl() + "/" + path + "/" + pathname + "/");
				return FileUtil.deleteDirectory(LoadSystemInfo.getProjectUrl() + "//" + SessionManager.getGroupId()+"//");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * 生成RQ二维码
	 * 
	 * @param str
	 *            内容
	 * @param height
	 *            高度（px）
	 * @author wuhongbo
	 */
	public static BufferedImage getRQ(String str, Integer height) {
		if (height == null || height < 100) {
			height = 200;
		}

		try {
			// 文字编码
			//Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
			//hints.put(EncodeHintType.CHARACTER_SET, CODE);
			
			QRCodeFormat qrFot = QRCodeFormat.NEW();

			BitMatrix bitMatrix = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, height, height, qrFot.getHints());//PDF_417
			//matrix = encode(content, bf, format.getSize(), format.getSize(), format.getHints());
			return toBufferedImage(bitMatrix);

			// 输出方式
			// 网页
			// ImageIO.write(image, "png", response.getOutputStream());

			// 文件
			// ImageIO.write(image, "png", file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 生成一维码（128）
	 * 
	 * @param str
	 * @param width
	 * @param height
	 * @return
	 * @author wuhongbo
	 */
	public static BufferedImage getBarcode(String str, Integer width, Integer height) {

		if (width == null || width < 200) {
			width = 200;
		}

		if (height == null || height < 50) {
			height = 50;
		}

		try {
			// 文字编码
			Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
			hints.put(EncodeHintType.CHARACTER_SET, CODE);

			BitMatrix bitMatrix = new MultiFormatWriter().encode(str, BarcodeFormat.CODE_128, width, height, hints);

			return toBufferedImage(bitMatrix);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 生成二维码，写到文件中
	 * 
	 * @param str
	 * @param sPath
	 * @param file_name
	 *            .png
	 * @throws IOException
	 * @author wuhongbo
	 */
	public static boolean getQRWriteFile(String str, String basePath, String filePath, String fileName)
			throws IOException {
		boolean success = false;
		InputStream inStream = null;
		try {
			BufferedImage image = getRQ(str, 200);
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			if (is_ftp.equals("true")) {
				getConntion();
				FTPClient client = fTPClientPool.borrowObject();
				client.setFileType(FTPClient.BINARY_FILE_TYPE);
				// 切换到上传目录 ,basepath需已存在
				if (!client.changeWorkingDirectory(basePath + filePath)) {
					// 如果目录不存在创建目录
					String[] dirs = filePath.split("/");
					String tempPath = basePath;
					for (String dir : dirs) {
						if (null == dir || "".equals(dir))
							continue;
						tempPath += "/" + dir;
						if (!client.changeWorkingDirectory(tempPath)) {
							if (!client.makeDirectory(tempPath)) {
								return false;
							} else {
								client.changeWorkingDirectory(tempPath);
							}
						}
					}
				}

				fileName = new String(fileName.getBytes("GBK"), "iso-8859-1");
				ImageIO.write(image, "png", os);
				inStream = new ByteArrayInputStream(os.toByteArray());
				success = client.storeFile(fileName, inStream);
				if (success == true) {
					return success;
				}
				fTPClientPool.returnObject(client);
			} else {
				File file = new File(LoadSystemInfo.getProjectUrl() + basePath + filePath + fileName);
				if (!file.exists())
					file.mkdirs();
				ImageIO.write(image, "png", file);
			}
		} catch (NoSuchElementException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (inStream != null) {
				try {
					inStream.close();
				} catch (IOException e) {
				}
			}
		}
		return true;
	}

	/**
	 * 生成一维码，写到文件中
	 * 
	 * @param str
	 * @param sPath
	 * @param file_name
	 *            .png
	 * @throws IOException
	 * @author wuhongbo
	 */
	public static boolean getBarcodeWriteFile(String str, String basePath, String filePath, String fileName)
			throws IOException {
		boolean success = false;
		InputStream inStream = null;
		try {
			if (is_ftp.equals("true")) {
				BufferedImage image = getBarcode(str, null, null);
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				getConntion();
				FTPClient client = fTPClientPool.borrowObject();
				client.setFileType(FTPClient.BINARY_FILE_TYPE);
				// 切换到上传目录 ,basepath需已存在
				if (!client.changeWorkingDirectory(basePath + filePath)) {
					// 如果目录不存在创建目录
					String[] dirs = filePath.split("/");
					String tempPath = basePath;
					for (String dir : dirs) {
						if (null == dir || "".equals(dir))
							continue;
						tempPath += "/" + dir;
						if (!client.changeWorkingDirectory(tempPath)) {
							if (!client.makeDirectory(tempPath)) {
								return false;
							} else {
								client.changeWorkingDirectory(tempPath);
							}
						}
					}
				}

				fileName = new String(fileName.getBytes("GBK"), "iso-8859-1");
				ImageIO.write(image, "png", os);
				inStream = new ByteArrayInputStream(os.toByteArray());
				success = client.storeFile(fileName, inStream);
				if (success == true) {
					return success;
				}
				fTPClientPool.returnObject(client);
			} else {
				File file = new File(LoadSystemInfo.getProjectUrl() + basePath + filePath + fileName);
				if (!file.exists())
					file.mkdirs();
				BufferedImage image = getBarcode(str, null, null);
				ImageIO.write(image, "png", file);
			}
		} catch (NoSuchElementException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (inStream != null) {
				try {
					inStream.close();
				} catch (IOException e) {
				}
			}
		}
		return true;
	}

	/**
	 * 转换成图片
	 * 
	 * @param matrix
	 * @return
	 * @author wuhongbo
	 */
	private static BufferedImage toBufferedImage(BitMatrix matrix) {
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
			}
		}
		return image;
	}

}
