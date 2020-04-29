package com.chd.base.util;


/**
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-7-6 下午4:52:10
 * @Company: 智慧云康（北京）数据科教有限公司
 * @Author: LiuYingDuo
 * @email:  bell@s-chd.com
 * @Version: 1.0 
 */
import javax.servlet.http.HttpServletRequest;  

import org.springframework.web.multipart.MultipartFile;  
  
/** 
 * @Description:Plupload是一个上传插件。 
 * 这是一个bean类,主要存储Plupload插件上传时需要的参数。 
 * 属性名不可随意改动. 
 * 这里主要使用MultipartFile文件上传方法 
 * @Copyright: Copyright (c) 2015-7-6 下午4:52:10
 * @Company: 智慧云康（北京）数据科教有限公司
 * @Author: LiuYingDuo
 * @email:  bell@s-chd.com
 * @Version: 1.0 
 */  
public class Plupload {  
      
    /**文件临时名(打文件被分解时)或原名*/  
    private String name;  
    /**总的块数*/  
    private int chunks = -1;  
    /**当前块数（从0开始计数）*/  
    private int chunk = -1;  
    /**HttpServletRequest对象，不能直接传入进来，需要手动传入*/  
    private HttpServletRequest request;  
    /**保存文件上传信息，不能直接传入进来，需要手动传入*/  
    private MultipartFile multipartFile;  
      
    public String getName() {  
        return name;  
    }  
  
    public void setName(String name) {  
        this.name = name;  
    }  
  
    public int getChunks() {  
        return chunks;  
    }  
  
    public void setChunks(int chunks) {  
        this.chunks = chunks;  
    }  
  
    public int getChunk() {  
        return chunk;  
    }  
  
    public void setChunk(int chunk) {  
        this.chunk = chunk;  
    }  
  
    public HttpServletRequest getRequest() {  
        return request;  
    }  
  
    public void setRequest(HttpServletRequest request) {  
        this.request = request;  
    }  
  
    public MultipartFile getMultipartFile() {  
        return multipartFile;  
    }  
  
    public void setMultipartFile(MultipartFile multipartFile) {  
        this.multipartFile = multipartFile;  
    }  
      
}  
