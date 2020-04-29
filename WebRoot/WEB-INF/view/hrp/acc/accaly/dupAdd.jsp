<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<style>
	element.style {
		width:300px;
	}

</style>
<script type="text/javascript">
	
     var dataFormat;
     
     $(function (){
    	 
    	 loadDict()
        
         loadForm();
    	 
    	 loadCss();
        
     });  
     
     function loadCss(){
 		
 		$("#dup_code").ligerTextBox({
 			width : 400
 		});

 		$("#dup_name").ligerTextBox({
 			width : 400
 		});
 		
 		$("#zb_unit").ligerTextBox({
 			width : 400
 		});
 		
 		$("#is_stop").ligerTextBox({
 			width : 400
 		});
 	}
     
     //标准值判断
     function num(obj){
    	 obj.value = obj.value.replace(/[^\d.]/g,""); //清除"数字"和"."以外的字符
    	 obj.value = obj.value.replace(/^\./g,""); //验证第一个字符是数字
    	 obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个, 清除多余的
    	 obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
    	 obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3'); //只能输入两个小数
   	 }
     
     function  save(){
   
    	 if($("#dup_code").val()==""){
    		 
             $.ligerDialog.error("杜邦指标编码不能为空!");
    		 
    		 return;
    	 }
    	 
         if($("#dup_name").val()==""){
    		 
             $.ligerDialog.error("杜邦指标名称不能为空!");
    		 
    		 return;
    	 }
    	 
         if($("#zb_unit").val()==""){
    		 
    		 $.ligerDialog.error("杜邦指标单位不能为空!");
    		 
    		 return;
    	 }
         

		
    	
         var formPara={
        		
        		dup_code:$("#dup_code").val(),
        		
        		dup_name:$("#dup_name").val(),
        		
        		zb_unit:$("#zb_unit").val(),
        		
        		fma_en:$("#fma_en").val(),
        		
        		fma_cn:$("#fma_cn").val(),
        		
        		is_stop:$("#is_stop").val(),
        		
        		note:$("#note").val()
            
            
         };
        
        ajaxJsonObjectByUrl("addAccAlyDup.do?isCheck=false",formPara,function(responseData){
        	if(responseData.state=="true"){
				 $("input[name='dup_code']").val('');
				 $("input[name='dup_name']").val('');
				 $("input[name='zb_unit']").val('');
				 $('#fma_cn').val('');
				 $('#fma_en').val('');
				 $('#note').val('');
				 $("input[name='is_stop']").val('');
               parent.query();
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
 
 	function loadDict() {
	}
 
    function saveAccSubjType(){
        if($("form").valid()){
            save();
        }
    } 
    
  //自定义函数
    function myCellSet(){
    	parent.parent.$.ligerDialog.open({
    		 url : 'hrp/acc/accfunda/cellSetPage.do?isCheck=false&mod_code=1',
    		 data:{"fma_en":$("#fma_en").val(),"fma_cn":$("#fma_cn").val()},
    		 height: 600,width: 1200, 
    		 title:'自定义函数',
    		 modal:true,showToggle:false,initShowMax:false,showMax:false,showMin: false,isResize:true,
    		 parentframename: window.name,
 			 buttons : [ {
 				text : '确定',
 				onclick : function(item, dialog) {
 					var obj = {};//js对象实现引用传递
 					dialog.frame.mySave(obj);
 					$("#fma_en").val(obj.fma_en);
 					$("#fma_cn").val(obj.fma_cn);
 					dialog.close();
 				},
 				    cls : 'l-dialog-btn-highlight'
 			    }, {
 				    text : '取消',
 				    onclick : function(item, dialog) {
 					    dialog.close();
 				    }
 			 } ]
    	});
    	
	 }
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
			<tr>
				<td align="right" class="l-table-edit-td"  style="padding-left:20px;" >编码规则:</td>
				<td align="left" class="l-table-edit-td"  colpan="2"><font color="red">2-2-2-2-2</font></td>
			</tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">杜邦指标编码:</td>
                <td align="left" class="l-table-edit-td">
                	<input name="dup_code" type="text" id="dup_code" ltype="text" maxlength="20" validate="{required:true}" />
                </td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">杜邦指标名称:</td>
                <td align="left" class="l-table-edit-td">
                	<input name="dup_name" type="text" id="dup_name" ltype="text" maxlength="50" validate="{required:true}" />
                </td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">指标单位:</td>
                <td align="left" class="l-table-edit-td">
                	<input name="zb_unit" type="text" id="zb_unit" ltype="text" maxlength="20" validate="{required:true}" />
                </td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">取数公式:</td>
                <td align="left" class="l-table-edit-td">
                	<textarea id="fma_en" name="fma_en" cols="100" rows="3" hidden=true></textarea>
                	<textarea name="fma_cn" id="fma_cn" validate="{required:true}" readonly disabled
                			style=" border: 1px solid #aecaf0;height: 100px;line-height: 15px;width: 400px;"></textarea>
                </td>
                <td align="left">
                	<input  type="button" class="l-dialog-btn-highlight" style="width:50px;height:25px" value=" 设置" onclick="myCellSet();"/>
                </td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">备注:</td>
                <td align="left" class="l-table-edit-td">
                	<textarea name="note" id="note" ltype="textarea" maxlength="100"
                			style=" border: 1px solid #aecaf0;height: 50px;line-height: 20px;width: 400px;background: #fff;"></textarea>
                </td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">状态:</td>
                <td align="left" class="l-table-edit-td">
                	<select name="is_stop" id="is_stop" style="width:180px;">
						<option value="0">启用</option>
						<option value="1">停用</option>
					</select>
                </td>
                <td align="left"></td>
            </tr>
        </table>
    </form>
   
    </body>
</html>
       
