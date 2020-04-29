<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;" xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />

<script type="text/javascript">

	var nature_code = '${nature_code}';
	
	var app_mod_code = '${app_mod_code}';
	
	
	var ligerData;
	
	var listbox1, listbox2;
	
	var width = 310;
	
	
	if("00" == app_mod_code && "56" == nature_code){
		 
 		nature_code = "'05','06'";
 		
 		app_mod_code = "";
 		
 	 }else{
 		 
 		nature_code = "'"+nature_code+"'";
 		
 		app_mod_code = "'"+app_mod_code+"','99'"
 		
 	 }
	
	$(function (){
		autocomplete("#formula_code","../queryFormula.do?isCheck=false","id","text",true,true);
		autocomplete("#dept_id", "../queryDeptDictByPerm.do?nature_code=" + nature_code + "&isCheck=false", "id", "text", true, true,'',false,'','258');
		
		$("#leftButton").ligerButton({width:70});
		$("#allLeftButton").ligerButton({width:70});
		$("#rightButton").ligerButton({width:70});
		$("#allRightButton").ligerButton({width:70});
        
		listbox1 = $("#listbox_ok").ligerListBox({
            isShowCheckBox: true,
            isMultiSelect : true,
            width: width,
            height : 200,
            url : "../queryDeptDictByPerm.do?nature_code=" + nature_code + "&isCheck=false",
			valueField : 'id',
			textField : 'text'
        });
         
		listbox2 = $("#listbox_no").ligerListBox({
            isShowCheckBox: true,
            width: width,
            height : 200,
            data: [
            ]
        });
        
     });  
	
	
	//左移
	function moveToLeft(){
         var box1 = liger.get("listbox_ok"), box2 = liger.get("listbox_no");
         var selecteds = box2.getSelectedItems();
         if (!selecteds || !selecteds.length) return;
         box2.removeItems(selecteds);
         box1.addItems(selecteds);
	}
	 
	//右移
	function moveToRight(){
         var box1 = liger.get("listbox_ok"), box2 = liger.get("listbox_no");
         var selecteds = box1.getSelectedItems();
         if (!selecteds || !selecteds.length) return;
         box1.removeItems(selecteds);
         box2.addItems(selecteds);
	}
	
	
	//全部左移
	function moveAllToLeft(){ 
		var box1 = liger.get("listbox_ok"), box2 = liger.get("listbox_no");
        var selecteds = box2.data;
        if (!selecteds || !selecteds.length) return;
        box1.addItems(selecteds);
        box2.removeItems(selecteds); 
	}
	
	
	//全部右移
	function moveAllToRight(){ 
         var box1 = liger.get("listbox_ok"), box2 = liger.get("listbox_no");
         
         console.log(box1)
         var selecteds = box1.data;
         if (!selecteds || !selecteds.length) return;
         box2.addItems(selecteds);
         box1.removeItems(selecteds);
         
	}
     
      
   	//保存
    function saveDeptScheme(){
   		
   		var src_dept = liger.get("dept_id").getValue();
   		if(src_dept == ''){
   			$.ligerDialog.warn('请选择源科室');
   			return ; 
   		}
   		
    	box2 = liger.get("listbox_no");
   	 	box2.selectAll(); 	
		var depts = liger.get("listbox_no").getValue();
		
		
		if(depts == ''){
			$.ligerDialog.warn('请选择目标科室');
   			return ; 
		}
		
		
		var formPara={
			dept_id : src_dept.split(",")[0],
			dept_no : src_dept.split(",")[1],
			depts : depts,
			nature_code :nature_code,
			app_mod_code : app_mod_code
		};
		
    	
		ajaxJsonObjectByUrl("fastHpmDeptScheme"+'${app_mod_code}'+'${nature_code}'+".do",formPara,function(responseData){
	            
			if(responseData.state=="true"){
				parent.query();
	        }
		});
   }
    </script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit"  >
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">源科室：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text"  /></td>
			<td align="left"></td>

		</tr>
	</table>
 
    <table cellpadding="0" cellspacing="0" class="l-table-edit"  >
    	
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
				<div style="margin: 4px; float: left;">
					<div id="listbox_ok"></div>
				</div>
			</td>
			<td align="left">
				<div>
					<input id="leftButton" type="button" onclick="moveToLeft()"  value="左      移" /> <br /><br />
					<input id="rightButton" type="button" onclick="moveToRight()" value="右      移" /><br /><br />
					<input id="allLeftButton" type="button" onclick="moveAllToLeft()" value="全部左移" /><br /><br />
					<input id="allRightButton" type="button" onclick="moveAllToRight()" value="全部右移" />
				</div>
			</td>
			<td align="left">
				<div style="margin: 4px; float: left;">
					<div id="listbox_no"></div>
				</div>
			</td>
		</tr>
	</table> 

</body>
</html>
