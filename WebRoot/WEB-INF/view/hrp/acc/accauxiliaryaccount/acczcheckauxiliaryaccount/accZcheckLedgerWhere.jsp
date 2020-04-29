<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc.jsp"/>
<link rel="stylesheet" href="<%=path %>/lib/Z-tree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="<%=path %>/lib/Z-tree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=path %>/lib/Z-tree/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="<%=path %>/lib/Z-tree/js/jquery.ztree.exedit-3.5.js"></script>
<script type="text/javascript">
	var para = {};
	
	var ligerData;
	
	var listbox1, listbox2;
	
	var width = 250;
	
	 $(function (){
	 	
	    loadDict();//加载下拉框
	    
	    loadForm();
	    
	    loadTree();
	    
	    $("#listbox2").ligerListBox({
            isShowCheckBox: true,
            isMultiSelect: true,
            height: 300,
            width:200
        });
	 });  
	 
	 function  save(){
		 
		 var box1 = liger.get("listbox_ok"), box2 = liger.get("listbox_no");
		 
		 box2.selectAll();
		 
		 var wageData = liger.get("listbox_no").getValue();
	
		 if(wageData==""){
			 
			 $.ligerDialog.error('请选择工资项');
			 
		 }
	
	    var formPara={
	        
	       scheme_name:$("#scheme_name").val(),
	
	       emp_kind_code:liger.get("emp_kind_code").getValue(),
	        
	       wage_code :wageData,
	        
	       order_no:$("#order_no").val()
	
	     };
	    
	     ajaxJsonObjectByUrl("addCostWageSchemeSet.do",formPara,function(responseData){
	        
	        if(responseData.state=="true"){
				 $("input[name='scheme_id']").val('');
				 $("input[name='scheme_name']").val('');
				 $("input[name='emp_kind_code']").val('');
				 $("input[name='wage_item_code']").val('');
				 $("input[name='order_no']").val('');
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
	
	function saveCostWageSchemeSet(){
	    if($("form").valid()){
	        save();
	    }
	}
	
	function loadDict(){
	        //字典下拉框
		autocomplete("#emp_kind_code","../queryEmpTypeArrt.do?isCheck=false","id","text",true,true);
	        
	 } 
	
	
	function changeEmp(){
		listbox1.clear();
	    listbox1.clearContent();
		para="";//清空参数
		para = {
				emp_kind_code : liger.get("emp_kind_code").getValue()
			};
		
		 $("#listbox_ok").ligerListBox({
			parms : para,
			url : '',
			valueField : 'id',
			textField : 'text',
			isShowCheckBox : true,
			isMultiSelect : true,
			height : 160,
			width : 150
		}); 
	
	} 
	
	function moveToLeft()
    {
    	var box2 = liger.get("listbox2");
    	
        var selecteds = box2.getSelectedItems();
        
        box2.removeItems(selecteds); 
    }
    function moveToRight()
    {
    	moveAllToLeft();
    	
    	var nodes = tree.getCheckedNodes();
    	
    	var param = "";
    	
    	$.each(nodes,function(index,value){
    		
    		if((index+1) == nodes.length){
    			
    			param = param +"{'id':"+value.id+",'text':'"+value.name+"'}";
    		
    		}else if((index+1) < nodes.length && value.pId != null){
    			
    			param = param +"{'id':"+value.id+",'text':'"+value.name+"'},";
    		
    		}
    	
    	});
    	
    	var box2 = liger.get("listbox2");
    	
    	box2.removeItems(eval("["+param+"]"));
        
    	box2.addItems(eval("["+param+"]"));
    }
    function moveAllToLeft()
    { 
    	
    	var  box2 = liger.get("listbox2");
        
    	var selecteds = box2.data;
        
    	box2.removeItems(selecteds);
    
    }
    function moveAllToRight()
    { 
    	
    	moveAllToLeft();
    	
    	var treeObj = $.fn.zTree.getZTreeObj("tree");
    	
    	var nodes = treeObj.getCheckedNodes(false);
    	
    	var param = "";
    	
    	$.each(nodes,function(index,value){
    		
    		if((index+1) == nodes.length){
    			
    			param = param +"{'id':"+value.id+",'text':'"+value.name+"'}";
    		
    		}else if((index+1) < nodes.length && value.pId != null){
    		
    			param = param +"{'id':"+value.id+",'text':'"+value.name+"'},";
    		
    		}
    	
    	});
    	
    	var box2 = liger.get("listbox2");
        
    	box2.addItems(eval("["+param+"]")); 
    }

	var tree;
	
    var setting = {      
		data: {
			simpleData: {
				enable: true
			}
		},
		check:{
			enable: true
		},
		treeNode:{
			open:true
		}
   	};
	function loadTree(){
				
    	$.post("../accsubj/queryAccSubjByMenu.do",null,function (responseData){
    	      
    		tree=$.fn.zTree.init($("#tree"), setting, responseData.Rows);
    	       
    	},"json");
    }
	function searchTree(){
		
		var formPara={
		        
			select_code:$("#select_code").val(),
			
			select_text:$("#select_text").val()

			};
		
    	$.post("../accsubj/queryAccSubjByMenu.do",formPara,function (responseData){
    	    
    		tree=$.fn.zTree.init($("#tree"), setting, responseData.Rows);
    		
    	},"json");
    }
	//点击确定把选择的科目赋值给父页面隐藏的文本框
	function save(){

		var wageData = liger.get("listbox2");
		  
	    window.parent.setVal(wageData.data);
		
	}
    </script>
  <style type="text/css">
       .middle input {
           display: block;width:30px;
       }
   </style>
  </head>
  
   <body>
   <table cellpadding="0" cellspacing="0" class="l-table-edit"  border="0">
   		<tr>
   			<td align="center">
   				<select id="select_code" name="select_code">
   					<option value="1">拼音码</option>
   					<option value="2">五笔码</option>
   					<option value="3">编码</option>
   					<option value="4">名称</option>
   				</select>
   			</td>
            <td align="center" class="l-table-edit-td"   ><input id="select_text" name="select_text" type="text" /></td>
            <td align="center">
            	<input class="l-button l-button-test"  type="button" value="查询" onclick="searchTree();"/>
            </td>
   		</tr>
   </table>
   <div class="l-layout" id="layout1" style="height: 100%;" ligeruiid="layout1">
   <div class="l-layout-left" style="left: 0px; top: 0px; width: 200px; height: 350px;">
   <div class="l-layout-content" position="left">
	   <div style="float: left;">
	  		<div class="l-layout-header">列表</div>
	        <div style="width:200px; height:300px;overflow:auto;" >
			      <ul class="ztree" id="tree" ></ul>
			</div>
	    </div>
	   </div> 
	</div>    
	<div class="l-layout-center" style="left: 205px; top: 0px; width: 250px; height: 350px;"><div class="l-layout-header">已选</div>
	<div title="" class="l-layout-content" style="height: 350px;" position="center">
    <div class="middle" style="margin-top:50px; float: left;">
		 <input onclick="moveToRight()" class="l-button l-button-test" style="margin-top:20px;" type="button" value=">>">
         <input onclick="moveToLeft()" class="l-button l-button-test" style="margin-top:20px;" type="button" value="<<">
         <input onclick="moveAllToRight()" class="l-button l-button-test" style="margin-top:20px;" type="button" value="全选">
         <input onclick="moveAllToLeft()" class="l-button l-button-test" style="margin-top:20px;" type="button" value="全删">
    </div>
   <div style="margin: 4px; float: left;">
       <div id="listbox2"></div> 
   </div>
   <div style="margin-left:160px; float: left;" ><input type="checkbox"/>按范围查询</div>
	
   </div>
   </div>
	</div>
   
    </body>
</html>
