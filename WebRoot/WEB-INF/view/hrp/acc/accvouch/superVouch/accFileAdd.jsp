<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<% 
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
    <script type="text/javascript">
     var dataFormat;
     var vouch_id='${vouch_id}';
     $(function (){
         loadDict()//加载下拉框
        loadForm();
         
     });  
     
     function  save(){
        var att_type = $("#att_type").val();  
        var invo_num = $("#invo_num").val();
        var invo_money = $("#invo_money").val();
        var invo_date = $("#invo_date").val();
        var type="1";//展示图片  
        var f = $("#att_path").val();  
        if(att_type==2){
        	if(invo_num == null || invo_num == ""){
        		parent.$.ligerDialog.warn("发票号码不能为空！");  
      			return false;  
      		}
        	if(invo_money == null || invo_money == ""){
        		parent.$.ligerDialog.warn("发票金额不能为空！");  
      			return false;  
      		}
        	if(invo_date == null || invo_date == ""){
        		parent.$.ligerDialog.warn("发票日期不能为空！");  
      			return false;  
      		}
        }
        
        if(f==null||f==""){  
        	parent.$.ligerDialog.error("<span style='color:Red'>错误提示:上传文件不能为空,请重新选择文件</span>");
            return false;  
         }else{  
           var extname = f.substring(f.lastIndexOf(".")+1,f.length);  
           extname = extname.toLowerCase();//处理了大小写  
         }  
         var file = document.getElementById("att_path").files;    
        
        
        $.ajaxFileUpload({  
        	
            url : 'saveAccFile.do?isCheck=false', //用于文件上传的服务器端请求地址  
            secureuri : false, //一般设置为false  
            fileElementId : 'att_path', //文件上传空间的id属性  <input type="file" id="file" name="file" />  
            type : 'post',  
            data :{
            	att_type : att_type,
            	vouch_id : vouch_id,
            	invo_num : invo_num,
            	invo_money : invo_money,
            	invo_date : invo_date
            },
            dataType : 'json', //返回值类型 一般设置为json  
            success : function(data, status) //服务器成功响应处理函数  
            {  
            	if(data.state == "true"){
            		parent.$.ligerDialog.success(data.msg);
            		 $("input[name='att_type']").val('');
    				 $("input[name='att_path']").val('');
    				 $("input[name='invo_num']").val('');
    				 $("input[name='invo_money']").val('');
    				 $("input[name='invo_date']").val('');
    				 parent.query();
            	}else{
               		parent.$.ligerDialog.warn(data.error);
            	}
            	
            } , error : function(data, status, e){  //服务器响应失败处理函数  
            	
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
   
    function saveAccFile(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
            //字典下拉框
        $("#att_type").ligerComboBox({width:200});
        $("#invo_num").ligerTextBox({width:200});
        $("#invo_date").ligerTextBox({width:200});
        $("#invo_money").ligerTextBox({width:200}); 
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
                    	
                         // xml.responseText = io.contentWindow.document.body?io.contentWindow.document.body.innerHTML:null;  
                         // xml.responseXML = io.contentWindow.document.XMLDocument?io.contentWindow.document.XMLDocument:io.contentWindow.document.children[0].outerHTML;
						//console.log(io.contentWindow.document.children[0].outerHTML)
						 xml.responseText = io.contentWindow.document.children[0].outerHTML;
                         xml.responseXML = io.contentWindow.document.children[0].outerHTML;
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
            if ( type == "script" ) {
            	jQuery.globalEval( data );
            
            // Get the JavaScript object, if JSON is used.
            } else if ( type == "json" ) {
            	data = data.replace(/<\/\w{0,}>/g, "");
            	data = data.replace(/<JSONObject>/g, "");
            	data = data.replace(/</g, "\",\"");
            	data = data.replace(/\",\"/, "");
            	data = data.replace(/>/g, "\":\"");
            	data = "{\"" + data + "\"}";
            	data = JSON.parse(data);
            	
           	// evaluate scripts within html
            } else if ( type == "html" ) {
            	jQuery("<div>").html(data).evalScripts(); 
            }
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
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red">*</font>文件类型：</td>
                <td align="left" class="l-table-edit-td">
                	 <select name="att_type" id="att_type">
						<option value="1">文档</option>
						<option value="2">发票</option>
			   		 </select>
             	</td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">发票号码：</td>
                <td align="left" class="l-table-edit-td"><input name="invo_num" type="text" id="invo_num" ltype="text" validate="{maxlength:50}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">发票金额：</td>
                <td align="left" class="l-table-edit-td"><input name="invo_money" type="text" id="invo_money" ltype="text" validate="{maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">发票日期：</td>
                <td align="left" class="l-table-edit-td"><input name="invo_date" type="text" id="invo_date" ltype="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">文件：</td>
                <td align="left" class="l-table-edit-td">
                <input name="att_path" type="file" id="att_path"  />
                </td>
                <td align="left"></td>
            </tr> 

        </table>
    </form>
    </body>
</html>
