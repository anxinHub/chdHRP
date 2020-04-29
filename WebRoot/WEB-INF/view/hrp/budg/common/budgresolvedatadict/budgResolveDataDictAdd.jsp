<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html style="overflow:hidden;">
  <head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/static_resource.jsp">
<jsp:param value="grid,select,datepicker,ligerUI,validate,dialog" name="plugins" />
</jsp:include>
<script type="text/javascript">
     var grid;
     var budg_level = "${budg_level}";
     $(function (){
        loadDict();//加载下拉框
        loadForm();
        loadHead();
        
     });  
   
     function loadHead(){
    	 if(budg_level == '02'){
    		 grid = $("#maingrid").etGrid({
            	 columns: [ 
    				{ display: '月份', name: 'month', align: 'center',width:'40%', 
    					 
    					 },
    			    { display: '数值', name: 'value', align: 'right',width:'57%',
    						 render:function(ui){
    							 if(ui.rowData.value){
    								 return formatNumber(ui.rowData.value,2,1);
    							 }
    						 }
    				}
                     ],  	
                     dataModel:{
                    	 method:'POST',
                    	 location:'remote',
                    	 url:'',
                    	 recIndx: 'month' //必填 且该列不能为空  
                    },
                    usePager:true,width: '100%',inWindowHeight:true, height: '100%',checkbox: true,editable: true,
                    toolbar: {
                        items: [
         				{ type: "button", label: '保存',icon:'disk',listeners: [{ click: saveBudgResolveDataDict}] },
         				{ type: "button", label: '添加行',icon:'plus',listeners: [{ click: add_row}] },
         				{ type: "button", label: '下载导入模板', icon:'arrowthickstop-1-s',listeners: [{ click: downTemplate}]},
         				{ type: "button", label: '导入',icon:'arrowthick-1-n',listeners: [{ click: imp}] },
         				{ type: "button", label: '删除',icon:'minus',listeners: [{ click: remove}] },
         				{ type: "button", label: '关闭',icon:'closethick',listeners: [{ click: this_close}] },
         				]
         			}
             });
    	 }else if(budg_level == '03'){
    		 grid = $("#maingrid").etGrid({
            	 columns: [ 
    				{ display: '科室编码', name: 'dept_code', align: 'center',width:'20%', editable:false,
    					 },
    				{ display: '科室名称', name: 'dept_name', align: 'center',width:'40%',
							editor:{
				 				type:'select' ,
				 				keyField:'dept_id',
				 				url:'../../queryDept.do?isCheck=false',
				 				change:reloadDeptCode,
							} 
    					 },
    			    { display: '数值', name: 'value', align: 'right',width:'37%',
    						 render:function(ui){
    							 if(ui.rowData.value){
    								 return formatNumber(ui.rowData.value,2,1);
    							 }
    						 }
    				}
                     ],  	
                     dataModel:{
                    	 method:'POST',
                    	 location:'remote',
                    	 url:'',
                    	 recIndx: 'dept_code' //必填 且该列不能为空  
                    },
                    usePager:true,width: '100%',inWindowHeight:true, height: '100%',checkbox: true,editable: true,
                    toolbar: {
                        items: [
         				{ type: "button", label: '保存',icon:'disk',listeners: [{ click: saveBudgResolveDataDict}] },
         				{ type: "button", label: '添加行',icon:'plus',listeners: [{ click: add_row}] },
         				{ type: "button", label: '下载导入模板', icon:'arrowthickstop-1-s',listeners: [{ click: downTemplate}]},
         				{ type: "button", label: '导入',icon:'arrowthick-1-n',listeners: [{ click: imp}] },
         				{ type: "button", label: '删除',icon:'minus',listeners: [{ click: remove}] },
         				{ type: "button", label: '关闭',icon:'closethick',listeners: [{ click: this_close}] },
         				]
         			}
             });
    	 }else if(budg_level == '04'){
    		 grid = $("#maingrid").etGrid({
            	 columns: [ 
    				{ display: '月份', name: 'month', align: 'center',width:'20%',},
    				{ display: '科室名称', name: 'dept_name', align: 'center',width:'40%',
						editor:{
			 				type:'select' ,
			 				keyField:'dept_id',
			 				url:'../../queryDept.do?isCheck=false',
			 				change:reloadDeptCode,
						} 
					 },
    				{ display: '数值', name: 'value', align: 'right',width:'37%',
    						 render:function(ui){
    							 if(ui.rowData.value){
    								 return formatNumber(ui.rowData.value,2,1);
    							 }
    						 }
    				}
                     ],  	
                     dataModel:{
                    	 method:'POST',
                    	 location:'remote',
                    	 url:'',
                    	 recIndx: 'month' //必填 且该列不能为空  
                    },
                    usePager:true,width: '100%',inWindowHeight:true, height: '100%',checkbox: true,editable: true,
                    toolbar: {
                        items: [
         				{ type: "button", label: '保存',icon:'disk',listeners: [{ click: saveBudgResolveDataDict}] },
         				{ type: "button", label: '添加行',icon:'plus',listeners: [{ click: add_row}] },
         				{ type: "button", label: '下载导入模板', icon:'arrowthickstop-1-s',listeners: [{ click: downTemplate}]},
         				{ type: "button", label: '导入',icon:'arrowthick-1-n',listeners: [{ click: imp}] },
         				{ type: "button", label: '删除',icon:'minus',listeners: [{ click: remove}] },
         				{ type: "button", label: '关闭',icon:'closethick',listeners: [{ click: this_close}] },
         				]
         			}
             });
    	 }
	 }
     
     function save(){
    	var data = grid.getAllData();
    	
    	if(data.length == 0){
			
    		$.etDialog.error('请添加明细数据');
    		
    		return false;
    	}
   	 	
    	if(!validateGrid(data)){
			return  false ;
		}
    	
        var formPara = {
            
           budg_level:liger.get("budg_level").getValue(),
            
           resolve_data_code:$("#resolve_data_code").val(),
            
           resolve_data_name:$("#resolve_data_name").val(),
            
           detail:JSON.stringify(data)
            
         };
        ajaxPostData({
            url: "addBudgResolveDataDict.do?isCheck=false",
            data: formPara,
            success: function(responseData) {
             	// 子页面代码 -- 父级查询
             	var parentFrameName = parent.$.etDialog.getFrameName('index');
                var parentWindow = parent.window[parentFrameName];
                parentWindow.query();
                
                this_close();
            }
        });
    }
   //添加行
		function add_row(){
			grid.addRow();
		}
		//选择指标后 更新指标编码 
	    function reloadDeptCode(rowdata,celldata){
	    	setTimeout(function () {
	    		grid.updateRow(celldata.rowIndx,{'dept_code':rowdata.dept_name.split(" ")[0]})
	    	}, 10);
	    }
	function validateGrid(data) {  
		var msg="";
		var rowm = "";
		//判断grid 中的数据是否重复或者为空
		var targetMap = new HashMap();
		$.each(data,function(i, v){
			rowm = "";
			if(budg_level == '02' || budg_level =='04'){
				if (v.month == "" || v.month == null || v.month == 'undefined') {
					rowm+="[月份]、";
				} 
			}
			if(budg_level == '03'){
				if (v.dept_code == "" || v.dept_code == null || v.dept_code == 'undefined') {
					rowm+="[科室编码]、";
				} 
			}
			if(budg_level == '03' || budg_level =='04'){
				if (v.dept_name == "" || v.dept_name == null || v.dept_name == 'undefined') {
					rowm+="[科室名称]、";
				} 
			}
			if (v.value == "" || v.value == null || v.value == 'undefined') {
				rowm+="[数值]、";
			} 
			if(rowm != ""){
				rowm = "第"+(i+1)+"行" + rowm.substring(0, rowm.length-1) + "不能为空" + "\n\r";
			}
			msg += rowm;
			var key ;
			if(budg_level == '02'){
				key = v.month ;
			}else if(budg_level == '03'){
				key = v.dept_id ;
			}else if(budg_level == '04'){
				key = v.month + v.dept_id ;
			}
			 
			var value="第"+(Number(v._rowIndx)+1)+"行";
			if(targetMap.get(key)== null || targetMap.get(key) == 'undefined' || targetMap.get(key) == ""){
				targetMap.put(key ,value);
			}else{
				msg += targetMap.get(key)+"与"+value+"数据重复!!" + "\n\r";
			}
		});
		if(msg != ""){
			$.etDialog.warn(msg);  
			return false;  
		}else{
			return true;  
		} 	
	}
	 //删除;
    function remove(){
    	
    	var data = grid.selectGetChecked();
        if (data.length == 0){
        	$.etDialog.error('请选择行');
        }else{
			grid.deleteRows(data) ;
        }
    }
   //关闭当前页面
 	function this_close(){
 	  
 		 // 子页面代码 -- 关闭
 	     var curIndex = parent.$.etDialog.getFrameIndex(window.name);
 	     parent.$.etDialog.close(curIndex);
 	}
 	 //下载临时模板
    function downTemplate(){
 		 
    	location.href = "downTemplate.do?isCheck=false&budg_level="+budg_level;
    }	
  //导入;
    function imp(){
    	parent.$.etDialog.open({ 
			url : '../../../../../../hrp/budg/common/budgresolvedatadict/budgResolveDataDictImportPage.do?isCheck=false&budg_level='+budg_level,
			title:'导入',
			isMax: true,
			frameNameObj: {key:window.name} //用于parent弹出层调用本页面的方法或变量
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
	           //提示信息  显示2秒后消失
	             setTimeout(function(){
	            	  lable.ligerHideTip();
	                  lable.remove();
	             },2000)
	         },
	         success: function (lable)
	         {
	             lable.ligerHideTip();
	             lable.remove();
	         },
	         submitHandler: function ()
	         {
	             $("form .l-text,.l-textarea").ligerHideTip();
	         },
	         ignore: ".notValid"
	     });
	    
	    //$("form").ligerForm();
	}       
   
    function saveBudgResolveDataDict(){
        if($("form").valid()){
            save();
        }
   	}
    function loadDict(){
        //字典下拉框
		//预算层次 
	    autocomplete("#budg_level","../../queryBudgLevel.do?isCheck=false","id","text",true,true,'',false,'${budg_level}',180);  
	    
	    $("#budg_level").ligerTextBox({width:180,disabled:true});
        
        $("#resolve_data_code").ligerTextBox({width:180});
        
        $("#resolve_data_name").ligerTextBox({width:300});
	} 
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	        <tr>
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font>预算层次<span style="color:red">*</span>:</font></td>
	            <td align="left" class="l-table-edit-td"><input name="budg_level" type="text" id="budg_level" ltype="text" validate="{required:true,maxlength:20}" /></td>
	            <td align="left"></td>
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font>分解系数编码<span style="color:red">*</span>:</font></td>
	            <td align="left" class="l-table-edit-td"><input name="resolve_data_code" type="text" id="resolve_data_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
	            <td align="left"></td>
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font>分解系数名称<span style="color:red">*</span>:</font></td>
	            <td align="left" class="l-table-edit-td"><input name="resolve_data_name" type="text" id="resolve_data_name" ltype="text" validate="{required:true,maxlength:40}" /></td>
	            <td align="left"></td>
	        </tr> 
    	</table>
    </form>
	<div id="maingrid" ></div> 
    </body>
</html>

  
   	
