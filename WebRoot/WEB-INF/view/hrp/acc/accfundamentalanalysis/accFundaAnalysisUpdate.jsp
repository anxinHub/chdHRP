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
<script type="text/javascript">
	
     var dataFormat;
     
     $(function (){
    	 
    	 loadDict()
        
         loadForm();
         
         loadCss();
    	 
    	 //设置指标编码只读
    	 $("#bas_code").ligerGetTextBoxManager().setDisabled();
        
     }); 
     
     function loadCss(){
  		
  		$("#bas_code").ligerTextBox({
  			width : 400
  		});

  		$("#bas_name").ligerTextBox({
  			width : 400
  		});
  		
  		$("#bas_unit").ligerTextBox({
  			width : 400
  		});
  		
  		$("#bas_type_code").ligerTextBox({
  			width : 400
  		});
  		
  		$("#wx_type_code").ligerTextBox({
  			width : 400
  		});
  		
  		$("#std_val").ligerTextBox({
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
   
    	 if($("#bas_code").val()==""){
    		 
             $.ligerDialog.error("基本指标编码不能为空!");
    		 
    		 return;
    	 }
    	 
         if($("#bas_name").val()==""){
    		 
             $.ligerDialog.error("基本指标名称不能为空!");
    		 
    		 return;
    	 }
    	 
         if($("#bas_unit").val()==""){
    		 
    		 $.ligerDialog.error("指标单位不能为空!");
    		 
    		 return;
    	 }
         
		if(liger.get("bas_type_code").getValue()==""){
		    		 
    		 $.ligerDialog.error("基本分析指标类别不能为空!");
    		 
    		 return;
    	 }
    	 
		if(liger.get("wx_type_code").getValue()==""){
			 
			 $.ligerDialog.error("五性分析指标类别不能为空!");
			 
			 return;
		}
		
		if($("#std_val").val()==""){
   		 
	   		 $.ligerDialog.error("标准值不能为空!");
	   		 
	   		 return;
   	 	}
		
    	
        var formPara={
        		
        		bas_code:$("#bas_code").val(),
        		
        		bas_name:$("#bas_name").val(),
        		
        		bas_unit:$("#bas_unit").val(),

        		bas_type_code:liger.get("bas_type_code").getValue(),
        		
        		wx_type_code:liger.get("wx_type_code").getValue(),
        		
        		fma_en:$("#fma_en").val(),
        		
        		fma_cn:$("#fma_cn").val(),
        		
        		std_val:$("#std_val").val(),
        		
        		is_stop:liger.get("is_stop").getValue(),
        		
        		note:$("#note").val()
            
            
         };
        
        ajaxJsonObjectByUrl("updateAccFundaAnalysis.do?isCheck=false",formPara,function(responseData){
        	if(responseData.state=="true"){
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
		//字典下拉框
		
		autocomplete("#bas_type_code","../queryAccBasType.do?isCheck=false","id","text",true,true,null,false,null,400);
		
		autocomplete("#wx_type_code","../queryAccWxType.do?isCheck=false","id","text",true,true,null,false,null,400);
		
		liger.get("bas_type_code").setValue("${bas_type_code}");
		liger.get("bas_type_code").setText("${bas_type_name}");
		
		liger.get("wx_type_code").setValue("${wx_type_code}");
		liger.get("wx_type_code").setText("${wx_type_name}");
		
		$("#is_stop").ligerComboBox({  
            data: [
            	{ text: '启用', id: '0' },
                { text: '停用', id: '1' },
            ],
            initWidth: 400
        }); 
		
		liger.get("is_stop").setValue("${is_stop}");

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
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">基本指标编码:</td>
                <td align="left" class="l-table-edit-td">
                	<input name="bas_code" type="text" id="bas_code" ltype="text" value="${bas_code}" disabled="disabled" validate="{required:true}" />
                </td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">基本指标名称:</td>
                <td align="left" class="l-table-edit-td">
                	<input name="bas_name" type="text" id="bas_name" ltype="text" value="${bas_name}" maxlength="50" validate="{required:true}" />
                </td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">指标单位:</td>
                <td align="left" class="l-table-edit-td">
                	<input name="bas_unit" type="text" id="bas_unit" ltype="text" value="${bas_unit}" maxlength="20" validate="{required:true}" />
                </td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">基本分析指标类别:</td>
                <td align="left" class="l-table-edit-td">
                	<input  name="bas_type_code" type="text" id="bas_type_code" ltype="text" validate="{required:true}"/>
                </td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">五性分析指标类别:</td>
                <td align="left" class="l-table-edit-td">
                	<input  name="wx_type_code" type="text" id="wx_type_code" ltype="text" validate="{required:true}"/>
                </td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">取数公式:</td>
                <td align="left" class="l-table-edit-td">
                	<textarea id="fma_en" name="fma_en" cols="100" rows="3" hidden=true>${fma_en}</textarea>
                	<textarea name="fma_cn" id="fma_cn" validate="{required:true}" readonly disabled
                			style=" border: 1px solid #aecaf0;height: 100px;line-height: 15px;width: 400px;">${fma_cn}</textarea>
                </td>
                <td align="left">
                	<input  type="button" class="l-dialog-btn-highlight" style="width:50px;height:25px" value=" 设置" onclick="myCellSet();"/>
                </td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">标准值:</td>
                <td align="left" class="l-table-edit-td">
                	<input name="std_val" type="text" id="std_val" title="请填写数字" onkeyup="num(this)" size="10" value="${std_val}"
                			ltype="text" validate="{required:true}" />
                </td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">备注:</td>
                <td align="left" class="l-table-edit-td">
                	<textarea name="note" id="note" ltype="textarea" maxlength="100"
                			style=" border: 1px solid #aecaf0;height: 50px;line-height: 20px;width: 400px;background: #fff;">${note}</textarea>
                </td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">状态:</td>
                <td align="left" class="l-table-edit-td">
                	<input name="is_stop" type="text" id="is_stop" ltype="text" validate="{required:true}" />
                </td>
                <td align="left"></td>
            </tr>
        </table>
    </form>
   
    </body>
</html>
