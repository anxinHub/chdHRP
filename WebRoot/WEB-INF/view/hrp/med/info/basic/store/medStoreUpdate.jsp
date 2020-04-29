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
<script src="<%=path%>/lib/hrp/med/med.js"	type="text/javascript"></script>
<script type="text/javascript">
	
    var dataFormat;
    
    $(function (){
        loadDict();
        
        loadForm();
        loadHead(null);
        
        
        
    });  
    
    function  query(){
		grid.options.parms=[];
		grid.options.newPage=1;
    //根据表字段进行添加查询条件
	//加载查询条件
	grid.loadData(grid.where);
 }

	function loadHead(){
		
	}
     
    function save(){
    	
	        
        var formPara={
	        store_id:'${store_id}',
	        is_purchase : liger.get("is_purchase").getValue(), 
	        alias : $("#alias").val(), 
	        is_location : liger.get("is_location").getValue(),      
	        is_com : liger.get("is_com").getValue(),               
	        dept_id : liger.get("dept_id").getValue().split(",")[0],            
	        acc_emp : liger.get("acc_emp").getValue().split(",")[0],   
	        manager : liger.get("manager").getValue().split(",")[0],
	        stock_emp : liger.get("stock_emp").getValue().split(",")[0],
	        telephone : $("#telephone").val(),
	        is_control : liger.get("is_control").getValue() 
        };
        ajaxJsonObjectByUrl("updateMedStore.do",formPara,function(responseData){
            
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
   
    function saveStore(){
        if($("form").valid()){
            save();
        }
    }
    function loadDict(){
    	
        //主表   库房是否停用
    	autocomplete("#is_stop","../../../queryMedYearOrNo.do?isCheck=false","id","text",true,true);
        liger.get("is_stop").setValue("${is_stop}");
    	if('${is_stop}'==0){
    		liger.get("is_stop").setText("否");
    	}else{
    		liger.get("is_stop").setText("是");
    	}
    	
    	
    	autocomplete("#type_code","../../../queryMedStoreType.do?isCheck=false","id","text",true,true);
        liger.get("type_code").setValue("${type_code}");
        liger.get("type_code").setText("${type_name}");
    	
    	
    	//从表  是否是采购库房
    	autocomplete("#is_purchase","../../../queryMedYearOrNo.do?isCheck=false","id","text",true,true);
    	liger.get("is_purchase").set("onSelected", function(){
    		if(liger.get("is_purchase").getValue() == 1){
    			$("#f_stock_emp").css("display", "inline");
    			$("#stock_emp").attr("validate", "{required:true,maxlength:20}");
    		}else{
    			$("#f_stock_emp").css("display", "none");
    			$("#stock_emp").attr("validate", "{required:false,maxlength:20}");
    		}
    	});
        liger.get("is_purchase").setValue("${is_purchase}");
    	if('${is_purchase}'==0){
    		liger.get("is_purchase").setText("否");
    	}else{
    		liger.get("is_purchase").setText("是");
    	}
    	
    	//是否代销库房
    	autocomplete("#is_com","../../../queryMedYearOrNo.do?isCheck=false","id","text",true,true);
        liger.get("is_com").setValue("${is_com}");
    	if('${is_com}'==0){
    		liger.get("is_com").setText("否");
    	}else{
    		liger.get("is_com").setText("是");
    	}
    	
    	//是否代销库房
    	autocomplete("#is_control","../../../queryMedYearOrNo.do?isCheck=false","id","text",true,true);
        liger.get("is_control").setValue("${is_control}");
    	if('${is_control}'==0){
    		liger.get("is_control").setText("否");
    	}else{
    		liger.get("is_control").setText("是");
    	}
    	
    	//是否是货位管理
    	autocomplete("#is_location","../../../queryMedYearOrNo.do?isCheck=false","id","text",true,true);
        liger.get("is_location").setValue("${is_purchase}");
    	if('${is_location}'==0){
    		liger.get("is_location").setText("否");
    	}else{
    		liger.get("is_location").setText("是");
    	}
    	
    	//主管部门
    	autocomplete("#dept_id","../../../queryMedDeptIsManager.do?isCheck=false","id","text",true,true);
        liger.get("dept_id").setValue("${dept_id}");
        liger.get("dept_id").setText("${dept_name}");
        
    	//会计
    	autocomplete("#acc_emp","../../../queryMedEmp.do?isCheck=false","id","text",true,true);  
        liger.get("acc_emp").setValue("${acc_emp}");
        liger.get("acc_emp").setText("${acc_emp_name}");
        
    	//采购人
    	autocomplete("#stock_emp","../../../queryMedStockEmp.do?isCheck=false","id","text",true,true);  
        liger.get("stock_emp").setValue("${stock_emp}");
        liger.get("stock_emp").setText("${stock_emp_name}");
        
    	//库房管理员
    	autocomplete("#manager","../../../queryMedManagerEmp.do?isCheck=false","id","text",true,true);  
        liger.get("manager").setValue("${manager}");
        liger.get("manager").setText("${manager_name}");
    	
        $("#store_code").ligerTextBox({width:160,disabled:true});
    	$("#store_name").ligerTextBox({width:160,disabled:true});
    	$("#is_stop").ligerTextBox({width:160,disabled:true}); 	
        $("#type_code").ligerTextBox({width:160,disabled:true});
        $("#spell_code").ligerTextBox({width:160,disabled:true});
        $("#wbx_code").ligerTextBox({width:160,disabled:true});
        $("#sort_code").ligerTextBox({width:160,disabled:true});
        $("#alias").ligerTextBox({width:160});
     }  
    
    //部门改变，采购员和库房管理员改变
    function deptChange(){
    	$("#manager").val("");
    	$("#stock_emp").val("");
		var dept_id = liger.get("dept_id").getValue();
		if(!dept_id){
			/* $("#manager").ligerComboBox({
			 	parms : param,
			 	url: '../../../queryMedEmp.do?isCheck=false',
		      	valueField: 'id',
		       	textField: 'text', 
		      	autocomplete: true,
		      	selectBoxWidth: 160,
		      	width: 160
			 }); */
			$("#stock_emp").ligerComboBox({
			 	parms : param,
			 	url: '../../../queryMedEmp.do?isCheck=false',
		      	valueField: 'id',
		       	textField: 'text', 
		      	autocomplete: true,
		      	selectBoxWidth: 160,
		      	width: 160
			 });
			
		}else{
			var param = {
					dept_id : dept_id.split(",")[0]
            	};
			
			/* $("#manager").ligerComboBox({
			 	parms : param,
			 	url: '../../../queryMedManagerEmp.do?isCheck=false',
		      	valueField: 'id',
		       	textField: 'text', 
		      	autocomplete: true,
		      	selectBoxWidth: 160,
		      	width: 160
			 }); */
			$("#stock_emp").ligerComboBox({
			 	parms : param,
			 	url: '../../../queryMedStockEmp.do?isCheck=false',
		      	valueField: 'id',
		       	textField: 'text', 
		      	autocomplete: true,
		      	selectBoxWidth: 160,
		      	width: 160
			 });
		}
    }
    	function openUpdate(){
    		var parm = "store_id="+"${store_id}";
    		
        	$.ligerDialog.open({ url : 'medStoreUpdateDictPage.do?isCheck=false&' + parm,data:{}, height: 350,width: 800, 
        			title:'查看变更记录',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
        			buttons: [ 
        			           
        			           { text: '关闭', onclick: function (item, dialog) { dialog.close(); } } 
        			           ] 
        	});
    	}
    
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" border="0">
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font>库房编码：</b></td>
                <td align="left" class="l-table-edit-td">
                	<input name="store_code"  type="text"  id="store_code" value="${store_code }" disabled="disabled" ltype="text" validate="{required:true,maxlength:20}" />
                </td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font>库房名称：</b></td>
                <td align="left" class="l-table-edit-td">
                	<input name="store_name"  type="text" id="store_name" value="${store_name}" disabled="disabled" ltype="text" validate="{required:true,maxlength:20}" />
                </td>
                <td align="left"></td>
            </tr> 
            <tr>
            	<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>是否停用：</b></td>
                <td align="left" class="l-table-edit-td">
                	<input name="is_stop"  type="text" id="is_stop" disabled="disabled" ltype="text" validate="{maxlength:20}" />
                </td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font>库房分类：</b></td>
                <td align="left" class="l-table-edit-td">
                	<input name="type_code"  type="text" id="type_code"  disabled="disabled" ltype="text" validate="{required:true,maxlength:20}" />
                	
                </td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font>拼音码：</b></td>
                <td align="left" class="l-table-edit-td">
                	<input name="spell_code"  type="text"  id="spell_code" value="${spell_code }" disabled="disabled" ltype="text" validate="{maxlength:20}" />
                </td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font>五笔码：</b></td>
                <td align="left" class="l-table-edit-td">
                	<input name="wbx_code"  type="text" id="wbx_code" value="${wbx_code}" disabled="disabled" ltype="text" validate="{maxlength:20}" />
                </td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font>排序号：</b></td>
                <td align="left" class="l-table-edit-td">
                	<input name="sort_code" type="text" id="sort_code" value="${sort_code}" disabled="disabled" ltype="text" validate="{required:true,digits:true,maxlength:20}" />
                </td>
                <td align="left"></td>
               
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font>备注：</b></td>
                <td align="left" class="l-table-edit-td" colspan="5">
                	<textarea rows="3"  id="note" name="note" style="width:470px;" ltype="text" disabled="disabled" validate="{maxlength:20}">"${note}"</textarea>
                </td>
                
            </tr> 
            <tr>
            	<td colspan="6"></td>
            </tr>
            <tr>
            	<td align="right" class="l-table-edit-td"  style="padding-left:20px;" >>>附属信息</td>
            	<td colspan="5"></td>
            </tr>
			
			<tr> 
				<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font>是否采购库房：</b></td>
                <td align="left" class="l-table-edit-td">
                	<input name="is_purchase"  type="text" id="is_purchase"  ltype="text" validate="{required:true,maxlength:20}" />
                </td>
                <td align="left"></td>
                
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font>库房代码：</b></td>
                <td align="left" class="l-table-edit-td">
                	<input name="alias"  type="text" id="alias" value="${alias}" ltype="text" validate="{required:true,maxlength:20}" />
                </td>
                <td align="left"><font color="red">(3位标识，与单据号有关)</font></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font>是否货位管理：</b></td>
                <td align="left" class="l-table-edit-td">
                	<select id="is_location" name="is_location" style="width: 160px;">
			               <option value="0">否</option>
			               <option value="1">是</option>
                	</select>
                </td>
                <td align="left"></td>
                
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font>主管部门：</b></td>
                <td align="left" class="l-table-edit-td">
                	<input name="dept_id" type="text" id="dept_id"   ltype="text"  onChange="deptChange();" validate="{required:true,maxlength:20}"/>
                </td>
                <td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font>是否代销库房：</b></td>
                <td align="left" class="l-table-edit-td">
                	<input name="is_com"  type="text" id="is_com"  ltype="text" validate="{required:true,maxlength:20}" />
                </td>
                <td align="left"></td>
                
				<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font id="f_stock_emp" color="red">*</font>采购员：</b></td>
                <td align="left" class="l-table-edit-td">
                	<input name="stock_emp" type="text" id="stock_emp"  ltype="text"  validate="{required:true,maxlength:20}"/>
                </td>
                <td align="left"></td>
			</tr>
			<tr>
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font>科室申请是否控制库存：</b></td>
                <td align="left" class="l-table-edit-td">
                	<input name="is_control"  type="text" id="is_control"  ltype="text" validate="{required:true,maxlength:20}" />
                </td>
                <td align="left"></td>
				<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>会计：</b></td>
                <td align="left" class="l-table-edit-td">
                	<input name="acc_emp" type="text" id="acc_emp"  ltype="text"  validate="{required:false,maxlength:20}"/>
                	
                </td>
                <td align="left"></td>
                
			</tr>
			<tr>
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>库房管理员：</b></td>
                <td align="left" class="l-table-edit-td">
                	<input name="manager" type="text" id="manager"  ltype="text"  validate="{required:false,maxlength:20}"/>
                </td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>电话：</b></td>
                <td align="left" class="l-table-edit-td">
                	<input name="telephone" type="text" id="telephone" value="${telephone}" ltype="text" validate="{required:false,maxlength:20}"/>
                </td>
                <td align="left"></td>
                <td></td>
                <td></td>
                <td></td>
			</tr>
			<br/>
			<tr>
				<td align="right" class="l-table-edit-td"  style="padding-left:20px;" ><a href=javascript:openUpdate()>变更记录</a></td>
            	<td colspan="5"></td>
			</tr>
        </table>
    </form>
    <div id="maingrid"></div>
    </body>
</html>
