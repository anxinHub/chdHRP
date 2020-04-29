<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <jsp:include page="${path}/inc.jsp"/>
    <script type="text/javascript">
     var dataFormat;
     $(function (){
         loadDict()//加载下拉框
        loadForm();
         $("#sec_code").ligerTextBox({width:200});
         $("#sec_name").ligerTextBox({width:200});
         $("#kpi_beg_score").ligerTextBox({width:200});
         $("#kpi_end_score").ligerTextBox({width:200});
         $("#led_path").ligerTextBox({width:280});
         $("#led_path").change(function(e){
             var file  = e.target.files[0];
             if(file){
                 var reader = new FileReader();
                 reader.onload = function(){
                        document.getElementById("image").src = this.result;
                 }
                reader.readAsDataURL(file);
            }
      	});
     });  
     
     function  save(){
        
        
        var sec_code = $("#sec_code").val();  
        var sec_name=$("#sec_name").val();  
        var kpi_beg_score = $("#kpi_beg_score").val();  
        var kpi_end_score=$("#kpi_end_score").val(); 
        var type="1";//展示图片  
        var f = $("#led_path").val();  
        if(f==null||f==""){  
        	$.ligerDialog.error("<span style='color:Red'>错误提示:上传文件不能为空,请重新选择文件</span>");
            return false;  
          }else{  
           var extname = f.substring(f.lastIndexOf(".")+1,f.length);  
           extname = extname.toLowerCase();//处理了大小写  
           if(extname!= "jpeg"&&extname!= "jpg"&&extname!= "gif"&&extname!= "png"){  
        	 $.ligerDialog.error("<span style='color:Red'>错误提示:格式不正确,支持的图片格式为：JPEG、GIF、PNG！</span>");
             return false;  
            }  
          }  
         var file = document.getElementById("led_path").files;    
         var size = file[0].size;  
         if(size>2097152){  
        	  $.ligerDialog.error("<span style='color:Red'>错误提示:所选择的图片太大，图片大小最多支持2M!</span>");
              return false;  
          }  
        
        
        $.ajaxFileUpload({  
            url : 'addPrmLed.do?sec_code='+sec_code+'&sec_name='+sec_name+'&kpi_beg_score='+kpi_beg_score+'&kpi_end_score='+kpi_end_score, //用于文件上传的服务器端请求地址  
            secureuri : false, //一般设置为false  
            fileElementId : 'led_path', //文件上传空间的id属性  <input type="file" id="file" name="file" />  
            type : 'post',  
            dataType : 'json', //返回值类型 一般设置为json  
            success : function(data, status) //服务器成功响应处理函数  
            {  
            	 $("input[name='sec_code']").val('');
				 $("input[name='sec_name']").val('');
				 $("input[name='kpi_beg_score']").val('');
				 $("input[name='kpi_end_score']").val('');
				 $("input[name='led_path']").val('');
                parent.query();
                
                // alert(data.msg);  
            },  
            error : function(data, status, e)//服务器响应失败处理函数  
            {  
                  
                // alert(data.msg);  
            }  
        });  
        
    }
     
 function loadForm(){
    
    $.metadata.setType("attr", "validate");
     var v = $("form").validate({
         errorPlacement: function (lable, element)
         {
             if (element.hasClass("l-textarea"))
             {
                 element.ligerTip({ content: lable.html(), target: element[0] }); 
             }
             else if (element.hasClass("l-text-field"))
             {
                 element.parent().ligerTip({ content: lable.html(), target: element[0] });
             }
             else
             {
                 lable.appendTo(element.parents("td:first").next("td"));
             }
         },
         success: function (lable)
         {
             lable.ligerHideTip();
             lable.remove();
         },
         submitHandler: function ()
         {
             $("form .l-text,.l-textarea").ligerHideTip();
         }
     });
     $("form").ligerForm();
 }       
   
    function savePrmLed(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
            //字典下拉框
           
     } 
    
    
    </script>
    
    <script>
    
    jQuery.extend({  
        
        createUploadIframe: function(id, uri)  
        {  
                //create frame  
                var frameId = 'jUploadFrame' + id;  
                var iframeHtml = '<iframe id="' + frameId + '" name="' + frameId + '" style="position:absolute; top:-9999px; left:-9999px"';  
                if(window.ActiveXObject)  
                {  
                    if(typeof uri== 'boolean'){  
                        iframeHtml += ' src="' + 'javascript:false' + '"';  
      
                    }  
                    else if(typeof uri== 'string'){  
                        iframeHtml += ' src="' + uri + '"';  
      
                    }     
                }  
                iframeHtml += ' />';  
                jQuery(iframeHtml).appendTo(document.body);  
      
                return jQuery('#' + frameId).get(0);              
        },  
        createUploadForm: function(id, fileElementId, data)  
        {  
            //create form     
            var formId = 'jUploadForm' + id;  
            var fileId = 'jUploadFile' + id;  
            var form = jQuery('<form  action="" method="POST" name="' + formId + '" id="' + formId + '" enctype="multipart/form-data"></form>');      
            if(data)  
            {  
                for(var i in data)  
                {  
                    jQuery('<input type="hidden" name="' + i + '" value="' + data[i] + '" />').appendTo(form);  
                }             
            }         
            var oldElement = jQuery('#' + fileElementId);  
            var newElement = jQuery(oldElement).clone();  
            jQuery(oldElement).attr('id', fileId);  
            jQuery(oldElement).before(newElement);  
            jQuery(oldElement).appendTo(form);  
      
      
              
            //set attributes  
            jQuery(form).css('position', 'absolute');  
            jQuery(form).css('top', '-1200px');  
            jQuery(form).css('left', '-1200px');  
            jQuery(form).appendTo('body');        
            return form;  
        },  
      
        ajaxFileUpload: function(s) {  
            // TODO introduce global settings, allowing the client to modify them for all requests, not only timeout          
            s = jQuery.extend({}, jQuery.ajaxSettings, s);  
            var id = new Date().getTime()          
            var form = jQuery.createUploadForm(id, s.fileElementId, (typeof(s.data)=='undefined'?false:s.data));  
            var io = jQuery.createUploadIframe(id, s.secureuri);  
            var frameId = 'jUploadFrame' + id;  
            var formId = 'jUploadForm' + id;          
            // Watch for a new set of requests  
            if ( s.global && ! jQuery.active++ )  
            {  
                jQuery.event.trigger( "ajaxStart" );  
            }              
            var requestDone = false;  
            // Create the request object  
            var xml = {}     
            if ( s.global )  
                jQuery.event.trigger("ajaxSend", [xml, s]);  
            // Wait for a response to come back  
            var uploadCallback = function(isTimeout)  
            {             
                var io = document.getElementById(frameId);  
                try   
                {                 
                    if(io.contentWindow)  
                    {  
                         xml.responseText = io.contentWindow.document.body?io.contentWindow.document.body.innerHTML:null;  
                         xml.responseXML = io.contentWindow.document.XMLDocument?io.contentWindow.document.XMLDocument:io.contentWindow.document;  
                           
                    }else if(io.contentDocument)  
                    {  
                         xml.responseText = io.contentDocument.document.body?io.contentDocument.document.body.innerHTML:null;  
                        xml.responseXML = io.contentDocument.document.XMLDocument?io.contentDocument.document.XMLDocument:io.contentDocument.document;  
                    }                         
                }catch(e)  
                {  
                    jQuery.handleError(s, xml, null, e);  
                }  
                if ( xml || isTimeout == "timeout")   
                {                 
                    requestDone = true;  
                    var status;  
                    try {  
                        status = isTimeout != "timeout" ? "success" : "error";  
                        // Make sure that the request was successful or notmodified  
                        if ( status != "error" )  
                        {  
                            // process the data (runs the xml through httpData regardless of callback)  
                            var data = jQuery.uploadHttpData( xml, s.dataType );      
                            // If a local callback was specified, fire it and pass it the data  
                            if ( s.success )  
                                s.success( data, status );  
          
                            // Fire the global callback  
                            if( s.global )  
                                jQuery.event.trigger( "ajaxSuccess", [xml, s] );  
                        } else  
                            jQuery.handleError(s, xml, status);  
                    } catch(e)   
                    {  
                        status = "error";  
                        jQuery.handleError(s, xml, status, e);  
                    }  
      
                    // The request was completed  
                    if( s.global )  
                        jQuery.event.trigger( "ajaxComplete", [xml, s] );  
      
                    // Handle the global AJAX counter  
                    if ( s.global && ! --jQuery.active )  
                        jQuery.event.trigger( "ajaxStop" );  
      
                    // Process result  
                    if ( s.complete )  
                        s.complete(xml, status);  
      
                    jQuery(io).unbind()  
      
                    setTimeout(function()  
                                        {   try   
                                            {  
                                                jQuery(io).remove();  
                                                jQuery(form).remove();    
                                                  
                                            } catch(e)   
                                            {  
                                                jQuery.handleError(s, xml, null, e);  
                                            }                                     
      
                                        }, 100)  
      
                    xml = null  
      
                }  
            }  
            // Timeout checker  
            if ( s.timeout > 0 )   
            {  
                setTimeout(function(){  
                    // Check to see if the request is still happening  
                    if( !requestDone ) uploadCallback( "timeout" );  
                }, s.timeout);  
            }  
            try   
            {  
      
                var form = jQuery('#' + formId);  
                jQuery(form).attr('action', s.url);  
                jQuery(form).attr('method', 'POST');  
                jQuery(form).attr('target', frameId);  
                if(form.encoding)  
                {  
                    jQuery(form).attr('encoding', 'multipart/form-data');                 
                }  
                else  
                {     
                    jQuery(form).attr('enctype', 'multipart/form-data');              
                }             
                jQuery(form).submit();  
      
            } catch(e)   
            {             
                jQuery.handleError(s, xml, null, e);  
            }  
              
            jQuery('#' + frameId).load(uploadCallback   );  
            return {abort: function () {}};   
      
        },  
      
        uploadHttpData: function( r, type ) {  
            var data = !type;  
            data = type == "xml" || data ? r.responseXML : r.responseText;  
            // If the type is "script", eval it in global context  
            if ( type == "script" )  
                jQuery.globalEval( data );  
            // Get the JavaScript object, if JSON is used.  
            if ( type == "json" )  
                eval( "data = " + data );  
            // evaluate scripts within html  
            if ( type == "html" )  
                jQuery("<div>").html(data).evalScripts();  
      
            return data;  
        },  
        handleError: function( s, xhr, status, e )      {  
            // If a local callback was specified, fire it  
                    if ( s.error ) {  
                        s.error.call( s.context || s, xhr, status, e );  
                    }  
      
                    // Fire the global callback  
                    if ( s.global ) {  
                        (s.context ? jQuery(s.context) : jQuery.event).trigger( "ajaxError", [xhr, s, e] );  
                    }  
        }  
       
    })  ;
    
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none" ></div>
   <form name="form1" method="post"  id="form1" action="upload" method="post" enctype="multipart/form-data">
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >

            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">等级代码：</td>
                <td align="left" class="l-table-edit-td"><input name="sec_code" type="text" id="sec_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">等级名称：</td>
                <td align="left" class="l-table-edit-td"><input name="sec_name" type="text" id="sec_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">KPI起始分：</td>
                <td align="left" class="l-table-edit-td"><input name="kpi_beg_score" type="text" id="kpi_beg_score" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">KPI结束分：</td>
                <td align="left" class="l-table-edit-td"><input name="kpi_end_score" type="text" id="kpi_end_score" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">指示灯路径：</td>
                <td align="left" class="l-table-edit-td">
                <input name="led_path" type="file" id="led_path"  />
                </td>
                <td align="left"><img id="image" width="" ></td>
            </tr> 

        </table>
    </form>
    </body>
</html>
