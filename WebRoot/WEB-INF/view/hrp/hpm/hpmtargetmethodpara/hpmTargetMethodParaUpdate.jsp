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
    	
    	var data = method_code_data.Rows;
   	
	   	$("#target_note").ligerTextBox({ disabled: true,width:545 });
	   	
	   	$("#nature_name").ligerTextBox({ disabled: true,width:160 });
	   	
		$("#formula_method_chs").ligerTextBox({ disabled: true,width:545 });
	   	
	   	$("#formula_name").ligerTextBox({ disabled: true,width:160 });
	   	
		$("#fun_note").ligerTextBox({ disabled: true,width:545 });
	   	
	   	$("#fun_name").ligerTextBox({ disabled: true,width:160 });
	   	
		$("#formula_code").ligerTextBox({width:160 });
    	
    	$("#fun_code").ligerTextBox({width:160 });
	   	
	
	   	//--指标编码
	   	$("#target_code").ligerComboBox({
	        	url: '../queryHpmTarget.do?isCheck=false',
	        	valueField: 'id',
	         	textField: 'text', 
	         	selectBoxWidth: 160,
	        	autocomplete: true,
	        	width: 160,
	         	onSelected: function (selectValue){
	          		
	          		ajaxJsonObjectByUrl("queryTarget.do?isCheck=false",{"target_code":selectValue},function (responseData){
	           		
	          			var data = responseData.Rows;
						
	          			$("input[name='target_note']").val(data[0]['target_note']);
	          			
	          			$("input[name='nature_name']").val(data[0]['nature_name']);
	          		
	           	});
	          		
	           }
	
			});
	
	   	//--取值方法
	   	//autoCompleteByData("#method_code",method_code_data.Rows,"method_code","method_name",true,true);
	   	$("#method_code").ligerComboBox({
				data : data,
				valueField : 'method_code',
				textField : 'method_name',
				selectBoxWidth: 160,
				width: 160,
				autocomplete : autocomplete,
				onSelected: function (selectValue){
	
					if(selectValue == '03'){

						//$("#formula_code").ligerComboBox({disabled: true});
						
						//$("#fun_code").ligerComboBox({disabled: false});

						//liger.get("formula_code").setValue("");
						
						//liger.get("formula_code").setText("");

						$("#formula_code").ligerTextBox({disabled: true});
						
						$("#fun_code").ligerTextBox({disabled: false});
						
						$("#formulaButton").ligerButton({disabled: true});
						$("#funButton").ligerButton({disabled: false});
						
						$("input[name='formula_code']").val('');
						
						$("input[name='formula_method_chs']").val('');
						
						$("input[name='formula_name']").val('');
						
						
	          		}else if(selectValue == '02'){

						//$("#formula_code").ligerComboBox({disabled: false});
						
						//$("#fun_code").ligerComboBox({disabled: true});
						
						//liger.get("fun_code").setValue("");
						
						//liger.get("fun_code").setText("");

						$("#formula_code").ligerTextBox({disabled: false});

						$("#fun_code").ligerTextBox({disabled: true});
						
						$("#funButton").ligerButton({disabled: true});
						$("#formulaButton").ligerButton({disabled: false});
						
						$("input[name='fun_code']").val('');
						
						$("input[name='fun_note']").val('');
						
						$("input[name='fun_name']").val('');

	          		}else{

						//$("#formula_code").ligerComboBox({disabled: true});
						$("#formula_code").ligerTextBox({disabled: true});
						
						//$("#fun_code").ligerComboBox({disabled: true});
						$("#fun_code").ligerTextBox({disabled: true});
						
						$("input[name='formula_code']").val('');
						
						$("input[name='fun_code']").val('');
						
						//liger.get("fun_code").setValue("");
						
						//liger.get("fun_code").setText("");
						
						//liger.get("formula_code").setValue("");
						
						//liger.get("formula_code").setText("");
						
						$("#funButton").ligerButton({disabled: true});
						$("#formulaButton").ligerButton({disabled: true});
						
						$("input[name='fun_note']").val('');
						
						$("input[name='fun_name']").val('');
						
						$("input[name='formula_method_chs']").val('');
						
						$("input[name='formula_name']").val('');
	          			
	          		}
	
	           }
			});
	   	
	   	/*--公式代码
	   	$("#formula_code").ligerComboBox({
	        	url: '../queryFormula.do?isCheck=false',
	        	valueField: 'id',
	         	textField: 'id', 
	         	selectBoxWidth: 160,
	        	autocomplete: true,
	        	width: 160,
	         	onSelected: function (selectValue){
	          		
	         		if(selectValue != ""){
	         			
	         			ajaxJsonObjectByUrl("../formula/queryHpmFormula.do?isCheck=false",{"formula_code":selectValue},function (responseData){
	    	           		
		          			var data = responseData.Rows;
							
		          			$("input[name='formula_name']").val(data[0]['formula_name']);
		          			
		          			$("input[name='formula_method_chs']").val(data[0]['formula_method_chs']);
		          		
		           		});
	         			
	         		}

	           }
	
			});
	   	
	   	//--函数代码
	   	$("#fun_code").ligerComboBox({
	        	url: '../queryFun.do?isCheck=false',
	        	valueField: 'id',
	         	textField: 'id', 
	         	selectBoxWidth: 160,
	        	autocomplete: true,
	        	width: 160,
	         	onSelected: function (selectValue){
	         		
	          		if(selectValue != ""){
	          			
	          			ajaxJsonObjectByUrl("../fun/queryHpmFun.do?isCheck=false",{"fun_code":selectValue},function (responseData){
	    	           		
		          			var data = responseData.Rows;
							
		          			$("input[name='fun_name']").val(data[0]['fun_name']);
		          			
		          			$("input[name='fun_note']").val(data[0]['fun_note']);
		          		
		           		});
	          			
	          		}
	          		
	          		
	           }
	
			});*/

		  	//--target_code
			liger.get("target_code").setValue("${target_code}");
		   	
			liger.get("target_code").setText("${target_name}");
			
			//--method_code
			liger.get("method_code").setValue("${method_code}");
   		 
   		 	for (var i = 0; i < data.length; i++){
   			 
                  if ("data[i]['method_code']" == "${method_code}"){

                       liger.get("method_code").setText(data[i]['method_name']);
                       
                  }
            }

			loadForm();
			
	       
	    });  
	    
	    function  save(){
	   	
	       var formPara={

	    		   target_code:liger.get("target_code").getValue(),
	               
	               method_code:liger.get("method_code").getValue(),
	                
	               formula_code:liger.get("formula_code").getValue(),
	                
	               fun_code:liger.get("fun_code").getValue(),
	               
	               f_p_v:$("#f_p_v").val()
	           
	        };
	       
	       ajaxJsonObjectByUrl("updateHpmTargetMethod.do",formPara,function(responseData){
	           
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
	    //$("form").ligerForm();
	}       
  
   function saveTargetMethod(){
       if($("form").valid()){
           save();
       }
  	}
   
   function openFun(){
	    $.ligerDialog.open({
			url: '../fun/hpmFunPage.do?isCheck=false', 
			height: 410,
			width: 930, 
			data: {
                fun_code: '${fun_code}',
                f_p_v: '${f_p_v}'
            },
			title:'奖金函数',
	        buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveFunStack(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ]
			
		});
   }
   
   function openFormula(){
	    $.ligerDialog.open({
			url: '../formula/hpmFormulaPage.do?isCheck=false', 
			height: 430,
			width: 930,
			title:'计算公式'
			//data: {},
	        //buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveComType(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ]
			
		});
   }
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
   
   
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
			
            <tr>
                <td align="right" class="l-table-edit-td" colspan="6" style="padding-left:100px;"></td>
            </tr>
            
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:100px;padding-top:20px;">指标编码：</td>
                <td align="left" class="l-table-edit-td" style="padding-top:20px;"><input name="target_code" type="text" id="target_code" ltype="text" validate="{required:true}" /></td>
                <td align="left"></td>
                
                <td align="right" class="l-table-edit-td"  style="padding-left:100px;padding-top:20px;">指标性质：</td>
                <td align="left" class="l-table-edit-td" style="padding-top:20px;">
                <input name="nature_name" type="text" id="nature_name" ltype="text" value="${nature_name}"/></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:100px;padding-top:20px;">指标描述：</td>
                <td align="left" class="l-table-edit-td" colspan="4" style="padding-top:20px;">
                	<input name="target_note" type="text" ltype="text" id="target_note"  value="${target_note}"/>
                </td>
                <td align="left"></td>
            </tr> 
            <tr>
             	<td align="right" class="l-table-edit-td" style="padding-left:100px;padding-top:20px;">取值方法：</td>
                <td align="left" class="l-table-edit-td" style="padding-top:20px;"><input name="method_code" type="text" id="method_code" ltype="text" validate="{required:true}" /></td>
                <td align="left"></td>
                
                <td align="left" style="padding-left:100px;padding-top:20px;">
                <font style="color:red"><-选择取值方法</font>
                </td>
                
                <td align="left" style="padding-left:10px;padding-top:20px;">
                	<input class="liger-button" id="formulaButton" name="formulaButton" onClick="openFormula()" value="选择公式"/>
                	<input class="liger-button" id="funButton" name="funButton" onClick="openFun()" value="选择函数"/>
                </td>
                <td align="left"></td>
                

            </tr> 
            
            <tr>

                <td align="right" class="l-table-edit-td"  style="padding-left:100px;padding-top:20px;padding-top:20px;">公式代码：</td>
                <td align="left" class="l-table-edit-td" style="padding-top:20px;"><input name="formula_code" type="text" id="formula_code" value="${formula_code}" ltype="text"/></td>
                <td align="left"></td>
                
                <td align="right" class="l-table-edit-td"  style="padding-left:100px;padding-top:20px;">公式名称：</td>
                <td align="left" class="l-table-edit-td" style="padding-top:20px;"><input name="formula_name" type="text" value="${formula_name}" id="formula_name" ltype="text" /></td>
                <td align="left"></td>
                
            </tr>
            
            <tr >
                <td align="right" class="l-table-edit-td"  style="padding-left:100px;padding-top:20px;">取值公式：</td>
                <td align="left" class="l-table-edit-td" colspan="4" style="padding-top:20px;">
                	<input name="formula_method_chs" type="text" ltype="text" id="formula_method_chs" value="${formula_method_chs}"  />
                </td>
                <td align="left"></td>
            </tr>
            
            <tr>

                <td align="right" class="l-table-edit-td"  style="padding-left:100px;padding-top:20px;">函数代码：</td>
                <td align="left" class="l-table-edit-td" style="padding-top:20px;">
                	<input name="fun_code" type="text" id="fun_code" ltype="text" value="${fun_code}" />
                	<input name="f_p_v" type="hidden" id="f_p_v" value="${f_p_v}"/>
                	
                </td>
                <td align="left"></td>
                
                <td align="right" class="l-table-edit-td"  style="padding-left:100px;padding-top:20px;">函数名称：</td>
                <td align="left" class="l-table-edit-td" style="padding-top:20px;"><input name="fun_name"  value="${fun_name}" type="text" id="fun_name" ltype="text" /></td>
                <td align="left"></td>
                
            </tr>
            
            <tr >
                <td align="right" class="l-table-edit-td"  style="padding-left:100px;padding-top:20px;">取值函数：</td>
                <td align="left" class="l-table-edit-td" colspan="4" style="padding-top:20px;">
                	<input name="fun_note" type="text" ltype="text" id="fun_note"   value="${fun_note}"/>
                </td>
                <td align="left"></td>
            </tr>

        </table>
    </form>
    </body>
</html>
