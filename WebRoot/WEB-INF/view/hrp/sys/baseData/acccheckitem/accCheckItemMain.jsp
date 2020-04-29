<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">
	
    var grid;
    
    var gridManager = null;
    
    var userUpdateStr;

    $(function (){
    	
		loadDict();
    	
    	loadHead("maingrid");	//加载数据
    	
    	loadHotkeys();
    	$(':button').ligerButton({width:80});
    	$("#item_tab").ligerTab({ onAfterSelectTabItem: function(tabid){
    		selectTab(tabid);
    	}});
    });
    //查询
    function  query(){ 
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'check_item_code',value:$("#check_item_code").val()}); 
    	grid.options.parms.push({name:'check_item_name',value:$("#check_item_name").val()}); 
    	grid.options.parms.push({name:'is_stop',value:$("#is_stop").val()});
    	//加载查询条件
    	grid.loadData(grid.where);
     }
    function loadHead(obj){
    	if('maingrid' == obj){
    		grid = $("#maingrid").ligerGrid({
    	           columns: [
    						{ display: '核算类型', name: 'check_type_name', align: 'left',width:205},
    	                    { display: '核算项编码', name: 'check_item_code', align: 'left',width:160,
    								render : function(rowdata, rowindex,
    										value) {
    										return "<a href=javascript:openUpdate('"+rowdata.check_item_id   + "|" + 
    										rowdata.check_type_id   + "|" + 
    										rowdata.group_id   + "|" + 
    										rowdata.hos_id   + "|" + 
    										rowdata.copy_code   +"')>"+rowdata.check_item_code+"</a>";
    								}
    						 },
    	                     { display: '核算项名称', name: 'check_item_name', align: 'left',width:205},
    	                     { display: '核算分类名称', name: 'type_name', align: 'left',width:206},
    	                     { display: '是否停用', name: 'is_stop', align: 'left',width:100,
    								render : function(rowdata, rowindex,
    										value) {
    									if(rowdata.is_stop == 0){
    										return "否";
    									}else{
    										return "是"
    									}
    								}
    						 },
    						 { display: '核算字段', name: 'column_check', align: 'left',hide: true}
    	                     ],
    	                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAccCheckItem.do?isCheck=false&check_type_id=${check_type_id}',
    	                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
    	                     selectRowButtonOnly:true,heightDiff: 0,
    	    				onDblClickRow : function (rowdata, rowindex, value)
    	    				{
    							openUpdate(
    									rowdata.check_item_id   + "|" + 
    									rowdata.check_type_id   + "|" + 
    									rowdata.group_id   + "|" + 
    									rowdata.hos_id   + "|" + 
    									rowdata.copy_code 
    								);
    	    				} 
    	                   });

    	        gridManager = $("#maingrid").ligerGetGridManager();
    	}
    	if('typegrid' == obj ){
    		grid = $("#typegrid").ligerGrid({
    	           columns: [
    						{ display: '分类编码', name: 'type_code', align: 'left',width:240,
    							render : function(rowdata, rowindex,value) {
										return "<a href=javascript:openTypeUpdate('"+rowdata.check_type_id   + "|" + 
										rowdata.type_code   + "|" + 
										rowdata.group_id   + "|" + 
										rowdata.hos_id   + "|" + 
										rowdata.copy_code   +"')>"+rowdata.type_code+"</a>";
								}
    						},
    						{ display: '分类名称', name: 'type_name', align: 'left',width:240},
    	                    { display: '核算类名称', name: 'check_type_name', align: 'left',width:240},
    	                    { display: '是否停用', name: 'is_stop', align: 'left',width:200,
    								render : function(rowdata, rowindex,
    										value) {
    									if(rowdata.is_stop == 0){
    										return "启用";
    									}else{
    										return "停用"
    									}
    								}
    						 }
    	                     ],
    	                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAccCheckItemType.do?isCheck=false&check_type_id=${check_type_id}',
    	                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
    	                     selectRowButtonOnly:true,heightDiff:0,
    	    				onDblClickRow : function (rowdata, rowindex, value)
    	    				{
    	    					openTypeUpdate(
    									rowdata.check_type_id   + "|" + 
    									rowdata.type_code   + "|" + 
    									rowdata.group_id   + "|" + 
    									rowdata.hos_id   + "|" + 
    									rowdata.copy_code 
    								);
    	    				} 
    	                   });

    		typeManager = $("#typegrid").ligerGetGridManager();
    	}
    }
	    
  //键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
 
		hotkeys('A', add_checkitem);
		hotkeys('D', deleteCheckItem);

		/*hotkeys('E', exportExcel);

		hotkeys('P', printDate);
		hotkeys('K', printBarcode); */

	}
  	//核算项添加页面
	 function add_checkitem(){
		  
		  parent.$.ligerDialog.open({url: '../baseData/accCheckItemAddPage.do?isCheck=false&check_type_id=${check_type_id}&check_type_name=${check_type_name}', 
				  height: 310,width: 420, title:'核算项添加',modal:true,showToggle:false,showMax:false,showMin: false,
				  parentframename:window.name,
				  isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveAccCheckItem(); },
				  cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
	  }
  	//核算分类 添加页面
	 function add_checkItemType(){
		  
		 parent.$.ligerDialog.open({url: 'accCheckItemTypeAddPage.do?isCheck=false&check_type_id=${check_type_id}&check_type_name=${check_type_name}', 
				  height: 280,width: 420, title:'核算分类添加',modal:true,showToggle:false,showMax:false,showMin: false,
				  parentframename:window.name,
				  isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveAccCheckItemType(); },
				  cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
	  }
  	//核算项 删除
	 function deleteCheckItem(){
		 var data = gridManager.getCheckedRows();
         if (data.length == 0){
         	$.ligerDialog.error('请选择行');
         }else{
             var ParamVo =[];
             $(data).each(function (){					
					ParamVo.push(
					//表的主键
					this.check_item_id   +"@"+ 
					this.check_type_id   +"@"+ 
					this.group_id   +"@"+ 
					this.hos_id   +"@"+ 
					this.copy_code+"@"+
					this.column_check
					)
             });
             $.ligerDialog.confirm('确定删除?', function (yes){
             	if(yes){
                 	ajaxJsonObjectByUrl("deleteAccCheckItem.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
                 		if(responseData.state=="true"){
                 			query();
                 		}
                 	});
             	}
             }); 
         }
         
	  }
	//核算分类 删除
	 function deleteCheckItemType(){
		 var data = typeManager.getCheckedRows();
         if (data.length == 0){
         	$.ligerDialog.error('请选择行');
         }else{
             var ParamVo =[];
             $(data).each(function (){					
					ParamVo.push(
					//表的主键
					this.group_id   +"@"+ 
					this.hos_id   +"@"+ 
					this.copy_code+"@"+
					this.type_code   +"@"+ 
					this.check_type_id  
					)
             });
             $.ligerDialog.confirm('确定删除?', function (yes){
             	if(yes){
                 	ajaxJsonObjectByUrl("deleteAccCheckItemType.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
                 		if(responseData.state=="true"){
                 			query();
                 		}
                 	});
             	}
             }); 
         }
         
	  }
	function selectTab(item){ 
		switch (item)
		{
			case "tabitem1":
				$("#typeButton").hide();
				$("#itemButton").show();
				loadHead("maingrid");
	  	    	return;
			case "tabitem2":
				$("#typeButton").show();
				$("#itemButton").hide();
				loadHead("typegrid");
	  	    	return;
			
		} 
	}
    //核算项修改
    function openUpdate(obj){
		var vo = obj.split("|");
		var parm = "check_item_id="+
			vo[0]   +"&check_type_id="+ 
			vo[1]   +"&group_id="+ 
			vo[2]   +"&hos_id="+ 
			vo[3]   +"&copy_code="+ 
			vo[4]   +"&check_type_name=${check_type_name}"; 
    	parent.$.ligerDialog.open({ url : 'accCheckItemUpdatePage.do?isCheck=false&' + parm,data:{}, height: 400,width: 500, 
    			title:'核算项修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
    			parentframename:window.name,
    			buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveAccCheckItem(); },
    			cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
    }
    //核算分类修改
    function openTypeUpdate(obj){
		var vo = obj.split("|");
		var parm = "check_type_id="+
			vo[0]   +"&type_code="+ 
			vo[1]   +"&group_id="+ 
			vo[2]   +"&hos_id="+ 
			vo[3]   +"&copy_code="+ 
			vo[4]   ; 
		parent.$.ligerDialog.open({ url : 'accCheckItemTypeUpdatePage.do?isCheck=false&' + parm,data:{}, height: 280,width: 420, 
    			title:'核算分类修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
    			parentframename:window.name,
    			buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveAccCheckItemType(); },
    			cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
    }
    function loadDict(){
            //字典下拉框
		$("#check_item_code").ligerTextBox({width:160});
    	
    	$("#check_item_name").ligerTextBox({width:160});
    	
    	$("#is_stop").ligerTextBox({width:160});
    } 
    
    /*继承自定义核算项数据*/
    function extend(obj){
    	
    	parent.$.ligerDialog.open({ url : 'accCheckItemExtendPage.do?isCheck=false&check_type_id=${check_type_id}&check_type_name=${check_type_name}' ,
    			data:{}, height: 350,width: 400, title:'继承',modal:true,showToggle:false,showMax:false,showMin: false,
    			isResize:true, parentframename:window.name,
    			buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveAccCheckItem(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
    		
      } 
    //导入页面跳转
    function accCheckItemImportPage(){
    	$.ligerDialog.open({url: 'accCheckItemImportPage.do?isCheck=false&check_type_id=${check_type_id}&check_type_name=${check_type_name}', height: 500,width:900, 
    			title:'核算项导入',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true });
    }
    //下载导入模板
    function downTemplate(){
    	location.href = "downTemplate.do?isCheck=false";
    	return;
    }
    //核算项打印
    function printDate(){
		 if(grid.getData().length==0){
		
			$.ligerDialog.error("请先查询数据！");
			
			return;
		}
	var heads={
	      		  //"isAuto": true/false 默认true，页眉右上角默认显示页码
	      		  "rows": [
	      		  ]
	      	};
	   		
		var printPara={
			rowCount:1,
			title:'核算项',
			columns: JSON.stringify(grid.getPrintColumns()),//表头
			class_name: "com.chd.hrp.sys.service.baseData.SysAccCheckItemService",
			method_name: "queryAccCheckItemPrint",
			bean_name: "sysAccCheckItemService",
			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
			check_type_id : ${check_type_id}
			};
	
		//执行方法的查询条件
		$.each(grid.options.parms,function(i,obj){
			printPara[obj.name]=obj.value;
	});
		
	officeGridPrint(printPara); 
	
	}
    
    //核算分类
    function printDateType(){
			 if(grid.getData().length==0){
			
				$.ligerDialog.error("请先查询数据！");
				
				return;
			}
		var heads={
		      		  //"isAuto": true/false 默认true，页眉右上角默认显示页码
		      		  "rows": [
		      		  ]
		      	}; 
			var printPara={
				rowCount:1,
				title:'核算分类',
				columns: JSON.stringify(grid.getPrintColumns()),//表头
				class_name: "com.chd.hrp.sys.service.baseData.SysAccCheckItemTypeService",
				method_name: "queryAccCheckItemTypePrint",
				bean_name: "sysAccCheckItemTypeService",
				heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
				check_type_id : ${check_type_id}
				};
		
			//执行方法的查询条件
			$.each(grid.options.parms,function(i,obj){
				printPara[obj.name]=obj.value;
		});
			
		officeGridPrint(printPara); 
		
		}
    
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">核算项编码：</td>
            <td align="left" class="l-table-edit-td"><input name="check_item_code" type="text" id="check_item_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">核算项名称：</td>
            <td align="left" class="l-table-edit-td"><input name="check_item_name" type="text" id="check_item_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
             <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否停用：</td>
            <td align="left" class="l-table-edit-td">
            	<select id="is_stop" name="is_stop" style="width: 135px;">
            		<option value=""> </option>
			        <option value="0">启用</option>
			        <option value="1">停用</option>
                </select>
            </td>
            <td align="left"></td>
        </tr> 
    </table>
    <div style="width: 100%;height:100%;border:1px">
    	<div id="itemButton" style="border:1px">
	    <input  type="button" value=" 查询(Q)" onclick="query();"/>
		<input  type="button" value=" 添加（A）" onclick="add_checkitem();"/>
		<input  type="button" value=" 删除（D）" onclick="deleteCheckItem();"/>
		<input  type="button" value="继承" onclick="extend();" />
		<input  type="button" value="下载导入模板" onclick="downTemplate();" />
		<input  type="button" value="导入" onclick="accCheckItemImportPage();" /> 
		<input  type="button" value="打 印" onclick="printDate();" /> 
		</div>
	    <div id="typeButton" style="border:1px ; display:none; ">
	    <input  type="button" value=" 查询(Q)" onclick="query();"/>
		<input  type="button" value=" 添加(A)" onclick="add_checkItemType();"/>
		<input  type="button" value=" 删除(D)" onclick="deleteCheckItemType();"/>
		<input  type="button" value="打 印" onclick="printDateType();" /> 
		</div>
		<div id="item_tab" style="width: 100%;height:100%;overflow:hidden; border:1px solid #A3C0E8; ">
			<div id="item"  title ="核算项">
	    		<div id="maingrid"></div>
	    	</div>
			<div id="itemType"  title ="核算分类" >
	    		<div id="typegrid"></div>
	    	</div>
		</div>
   </div>
   
  <!--  <div style="width: 100%;height:495px;border:1px">
	    
		
   </div> -->
</body>
</html>
