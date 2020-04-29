<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
     var dataFormat;
     var dataStack ;//记录 函数 参数栈数据
     $(function (){
        loadDict()//加载下拉框
        loadForm();
        
        $("#index_code").change(function(){
        	var data = liger.get("index_code").getValue();
        	
        	$("#index_nature").val(data.split(",")[1]);
        	$("#index_describe").val(data.split(",")[2]);
        })
        
        $("#get_value_way").change(function(){
         	if(liger.get("get_value_way").getValue()=='01'){
         		$("#selectFormula").ligerButton({click: selectFormula, width:90,disabled:true});
         		$("#selectFun").ligerButton({click: selectFun, width:100,disabled:true});
         		$("#formula_id").val("");
          		$("#formula_name").val("");
          		$("#formula_ca").val("");
          		$("#fun_id").val("");
          		$("#fun_name").val("");
          		$("#fun_method_chs").val("");
         	}
         	if(liger.get("get_value_way").getValue()=='02'){
         		$("#selectFormula").ligerButton({click: selectFormula, width:90,disabled:true});
         		$("#selectFun").ligerButton({click: selectFun, width:100,disabled:false});
          		$("#formula_id").val("");
          		$("#formula_name").val("");
          		$("#formula_ca").val("");
         	}
         	if(liger.get("get_value_way").getValue()=='03'){
         		$("#selectFormula").ligerButton({click: selectFormula, width:90,disabled:false});
         		$("#selectFun").ligerButton({click: selectFun, width:100,disabled:true});
         		$("#selectFofmula").prop("disabled", false);
         		$("#selectFun").prop("disabled", true);
         		$("#fun_id").val("");
          		$("#fun_name").val("");
          		$("#fun_method_chs").val("");
         	}
         	
         })
     });  
     
     function  save(){
        var formPara={   
       		 index_code:liger.get("index_code").getValue().split(",")[0],
       		 
       	     get_value_way:liger.get("get_value_way").getValue(),
       	     
       	     formula_id:$("#formula_id").val(),
       	     
       	     fun_id:$("#fun_id").val() ,
       	     
       	     dataStack : dataStack
            
        };
        
        ajaxJsonObjectByUrl("addBudgBasicIndexGetWay.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
				 $("input[name='index_code']").val('');
				 $("input[name='index_nature']").val('');
				 $("input[name='index_describe']").val('');
				 $("input[name='get_value_way']").val('');
				 $("input[name='formula_id']").val('');
				 $("input[name='formula_name']").val('');
				 $("input[name='formula_ca']").val('');
				 $("input[name='fun_id']").val('');
				 $("input[name='fun_name']").val('');
				 $("input[name='fun_method_chs']").val('');
				 
				 parentFrameUse().query();
            }
        });
    }
   //选择计算公式页面
  	function selectFormula(){
  		
  		parent.$.ligerDialog.open({
 			url : 'hrp/budg/common/budgformula/budgFormulaPage.do?isCheck=false',height : 500,width : 750,title : '计算公式选择',
 			modal : true,showToggle : false,showMax : true,showMin :false ,	isResize : true,
 			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
 		});
  		
  	}
  	
  	// 选择函数页面
 	function selectFun(){
		var index_code = liger.get("index_code").getValue().split(",")[0];
 		var index_type_code = "01" // 指标类型 01 基本指标 02 费用指标 03 预算指标
 		if(!index_code){
 			$.ligerDialog.error("指标编码不能为空");
 			return false ;
 		}
 		parent.$.ligerDialog.open({
 			url : 'hrp/budg/common/budgfun/budgFunPage.do?isCheck=false&index_code='+index_code+'&index_type_code='+index_type_code,height : 500,width : 750,title : '取值函数选择',
 			modal : true,showToggle : false,showMax : true,showMin :false ,	isResize : true,
 			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
 			buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveSelectFun(); },cls:'l-dialog-btn-highlight' },
			           { text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
			          ]
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
     //$("form").ligerForm();
 }       
   
    function saveBudgBasicIndexGetWay(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
            //字典下拉框		
    	 //指标名称
        autocomplete("#index_code","../../../queryBudgIndexCodeGetWay.do?isCheck=false","id","text",true,true,'',false,'',200);
        //取值方法
        autocomplete("#get_value_way","../../../queryBudgGetValueWay.do?isCheck=false","id","text",true,true,'',false,'',200);
     	//计算公式ID
     	//  autocomplete("#formula_id","../../../queryBudgFormula.do?isCheck=false","id","text",true,true);
     	//取值函数
     	//  autocomplete("#fun_id","../../../queryBudgFun.do?isCheck=false","id","text",true,true);
     
        $("#index_code").ligerTextBox({width:200});
    	$("#index_nature").ligerTextBox({width:200,disabled:true});
    	$("#index_describe").ligerTextBox({width:600,disabled:true});
    	$("#get_value_way").ligerTextBox({width:200});
    	$("#selectFormula").ligerButton({click: selectFormula, width:90,disabled:true});
		$("#selectFun").ligerButton({click: selectFun, width:90,disabled:true});
    	$("#formula_id").ligerTextBox({width:200,disabled:true});
    	$("#formula_name").ligerTextBox({width:200,disabled:true});
    	$("#formula_ca").ligerTextBox({width:600,disabled:true});
    	$("#fun_id").ligerTextBox({width:200,disabled:true});
    	$("#fun_name").ligerTextBox({width:200,disabled:true});
    	$("#fun_method_chs").ligerTextBox({width:600,disabled:true});
    } 
    </script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post" id="form1">
		<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
	        <tr>
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font>指标名称<span style="color:red">*</span>:</font></td>
	            <td align="left" class="l-table-edit-td"><input name="index_code" type="text" id="index_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
	            <td align="left"></td>
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font>指标性质:</font></td>
	            <td align="left" class="l-table-edit-td"><input name="index_nature" type="text" id="index_nature" ltype="text" disabled="disabled" validate="{maxlength:20}" /></td>
	            <td align="left"></td>
	        </tr> 
	        <tr>
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font>指标描述:</font></td>
	            <td align="left" class="l-table-edit-td" colspan="4"><input name="index_describe" type="text" id="index_describe" disabled="disabled" ltype="text" validate="{maxlength:40}" /></td>
	            <td align="left"></td>
	        </tr> 
	        <tr>
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font>取值方法<span style="color:red">*</span>:</font></td>
	            <td align="left" class="l-table-edit-td"><input name="get_value_way" type="text" id="get_value_way" ltype="text" validate="{required:true,maxlength:20}" /></td>
	            <td align="left"></td>
	            <td align="center" class="l-table-edit-td" colspan="3">
						<button id ="selectFormula" accessKey="A"><b>选择公式（<u>A</u>）</b></button>
						&nbsp;&nbsp;
						<button id ="selectFun" accessKey="B"><b>选择函数（<u>B</u>）</b></button>
				</td>
	        </tr> 
	        <tr>
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font>公式代码:</font></td>
	            <td align="left" class="l-table-edit-td"><input name="formula_id" type="text" id="formula_id"  ltype="text" disabled="disabled" validate="{maxlength:20}" /></td>
	            <td align="left"></td>
	             <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font>公式名称:</font></td>
	            <td align="left" class="l-table-edit-td"><input name="formula_name" type="text" id="formula_name"  ltype="text" disabled="disabled" validate="{maxlength:20}" /></td>
	            <td align="left"></td>
	        </tr> 
	        <tr>
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font>取值公式:</font></td>
	            <td align="left" class="l-table-edit-td" colspan="4"><input name="formula_ca" type="text" id="formula_ca" ltype="text" disabled="disabled" validate="{maxlength:40}" /></td>
	            <td align="left"></td>
	        </tr> 
	        <tr>
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font>函数代码:</font></td>
	            <td align="left" class="l-table-edit-td"><input name="fun_id" type="text" id="fun_id" ltype="text"  disabled="disabled" validate="{maxlength:20}" /></td>
	            <td align="left"></td>
	             <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font>函数名称:</font></td>
	            <td align="left" class="l-table-edit-td"><input name="fun_name" type="text" id="fun_name" ltype="text" disabled="disabled"  validate="{maxlength:20}" /></td>
	            <td align="left"></td>
	        </tr> 
	        <tr>
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font>取值函数:</font></td>
	            <td align="left" class="l-table-edit-td" colspan="4"><input name="fun_method_chs" type="text" id="fun_method_chs" disabled="disabled" ltype="text" validate="{maxlength:40}" /></td>
	            <td align="left"></td>
	        </tr> 
	    </table>
	</form>

</body>
</html>
