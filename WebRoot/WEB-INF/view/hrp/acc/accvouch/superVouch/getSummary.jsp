<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/lib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<script src="<%=path%>/lib/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js"></script>
<script src="<%=path%>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/core/base.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path%>/lib/layer-v2.3/layer/layer.js" type="text/javascript"></script>

 	<!-- CSS dependencies -->
    <link rel="stylesheet" type="text/css"  href="<%=path%>/lib/hrp/acc/superVouch/grid/bootstrap/dist/css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css"  href="<%=path%>/lib/hrp/acc/superVouch/grid/pickadate/lib/themes/classic.css"/>
    <link rel="stylesheet" type="text/css"  href="<%=path%>/lib/hrp/acc/superVouch/grid/pickadate/lib/themes/classic.date.css"/>
    <link rel="stylesheet" type="text/css"  href="<%=path%>/lib/hrp/acc/superVouch/grid/fontawesome/css/font-awesome.min.css"/>
    <link rel="stylesheet" type="text/css"  href="<%=path%>/lib/hrp/acc/superVouch/grid/summernote/dist/summernote.css"/>
	
    <!-- Sensei Grid CSS -->
    <link rel="stylesheet" type="text/css"  href="<%=path%>/lib/hrp/acc/superVouch/grid/sensei-grid.css"/>

	<script src="<%=path%>/lib/hrp/acc/superVouch/grid/lodash/lodash.js" type="text/javascript"></script>
    <script src="<%=path%>/lib/hrp/acc/superVouch/grid/pickadate/lib/picker.js"></script>
    <script src="<%=path%>/lib/hrp/acc/superVouch/grid/pickadate/lib/picker.date.js"></script>
    <script src="<%=path%>/lib/hrp/acc/superVouch/grid/bootstrap/dist/js/bootstrap.js"></script>
    <script src="<%=path%>/lib/hrp/acc/superVouch/grid/summernote/dist/summernote.js"></script>
    <script src="<%=path%>/lib/hrp/acc/superVouch/grid/isInViewport/lib/isInViewport.js"></script>
    <script src="<%=path%>/lib/hrp/acc/superVouch/grid/typeahead.js/dist/typeahead.jquery.js"></script>
	
	<!-- Sensei Grid JS -->
    <script src="<%=path%>/lib/hrp/acc/superVouch/grid/sensei-grid.js"></script>
    <script src="<%=path%>/lib/hrp/acc/superVouch/grid/sensei-editors.js"></script>
    <script src="<%=path%>/lib/hrp/acc/superVouch/grid/sensei-row-actions.js"></script>


<script type="text/javascript">
var dialog = frameElement.dialog; //调用页面的dialog对象(ligerui对象)
var detailGrid=dialog.get("data").grid;
var grid;
var page="getSummary";

	$(function() {
		if(existsChromeVer42(44)){
			//谷歌43以下版本滚动条的宽度多1px
			$("#templateDiv").css("padding-right","1px");
		}
		
		$(':button').ligerButton({width:60});
		$("input[name='button120']").ligerButton({width:135});
		loadTemplateTable();
		
		//快捷键处理
		BindKeyBoard([
			{keyCode:112,fn:function(){}},//F1
			{keyCode:113,fn:function(){myDel()}},//删除F2
			{keyCode:114,fn:function(){}},//F3
			{keyCode:115,fn:function(){}},//F4
			{keyCode:116,fn:function(){}},//F5
			{keyCode:117,fn:function(){}},//F6
			{keyCode:118,fn:function(){}},//F7
			{keyCode:119,fn:function(){}},//F8
			{keyCode:120,fn:function(){}},//F9
			{keyCode:121,fn:function(){}},//F10
			{keyCode:122,fn:function(){}},//F11
			{keyCode:123,fn:function(){}},//F12
			{keyCode:27,fn:function(){myClose()}}//关闭ESC
		]);
	});

	
	function loadTemplateTable(){
		
			
		var tmpData=[];
		ajaxJsonObjectByUrl("queryAccVouchSummary.do?isCheck=false",{},function (responseData){
			tmpData=responseData.Rows;
		},false);
	
	    var columns = [
	    {
   	        name: "id",
   	        type: "int",editor:"DisabledEditor",hide:true,
   	        display:"ID",align : 'left'
	    },{
	        name: "sid",
	        type: "string",editor:"DisabledEditor",hide:true,
	        display:"SID",align : 'left'
	    },{
	        name: "summary",
	        type: "string",wrap:true,
	        display:"摘要",width:'363px',align : 'left'
	    },{
	        name: "sort_code",
	        type: "int",editor:"DisabledEditor",hide:true,
	        display:"排序",align : 'left'
	    },{
	        name: "user_id",
	        type: "string",editor:"DisabledEditor",hide:true,
	        display:"用户ID",align : 'left'
	    }];

	   
	    var options = {
	        // add an empty row at the end of grid
	        emptyRow: true,
	        // enable sortable callbacks
	        sortable: true,
	        // disable specific keys
	        disableKeys: [],
	        // move active cell when a row is removed
	        moveOnRowRemove: true,
	        // skip these cells on duplicate action
	        skipOnDuplicate: ["sid"],
	        // set the initial order of table
	        initialSort: {col: "sort_code", order: "asc"},
	        selectable: true,
	        readonly :false
	    };
	   
   
	    // initialize grid with data, column mapping and options
	    grid = $(".sensei-grid-default").grid(tmpData, columns, options);
	    
	    // register editors that are bundled with sensei grid
	     grid.registerEditor(BasicEditor);
	 //   grid.registerEditor(BooleanEditor);
	 //   grid.registerEditor(TextareaEditor);
	   grid.registerEditor(SelectEditor);   
	//   grid.registerEditor(AutocompleteEditor);
	    grid.registerEditor(DisabledEditor);
	    

	    // register row actions
	    // grid.registerRowAction(BasicRowActions);

	    // example listeners on grid events
	    grid.events.on("editor:save", function (data, $cell) {	  
	    });
	    grid.events.on("cell:select", function ($cell) {
	       // console.info("active cell:", $cell);
	    });
	    grid.events.on("cell:clear", function (oldValue, $cell) {
	       // console.info("clear cell:", oldValue, $cell);
	    });
	    grid.events.on("cell:deactivate", function ($cell) {
	      //  console.info("cell deactivate:", $cell);
	    });
	    grid.events.on("row:select", function ($row) {
	      //  console.info("row select:", $row);
	    });
	    grid.events.on("row:remove", function (data, row, $row) {
	       // console.info("row remove:", data, row, $row);
	    });
	    grid.events.on("row:mark", function ($row) {
	      //  console.info("row mark:", $row);
	    });
	    grid.events.on("row:unmark", function ($row) {
	      //  console.info("row unmark:", $row);
	    });
	    grid.events.on("row:save", function (data, $row, source) {
	     //   console.info("row save:", source, data);
	        // save row via ajax or any other way
	        // simulate delay caused by ajax and set row as saved
	        setTimeout(function () {
	            grid.setRowSaved($row);
	           // $('#vouchDiv').scrollTop( $('#vouchDiv')[0].scrollHeight );
	        }, 1000);
	    });

	    // implement basic sorting
	    grid.events.on("column:sort", function (col, order, $el) {
	      // console.info("column sort:", col, order, $el);
	        var sorted = _.sortBy(tmpData, col);
	        if (order === "desc") {
	            sorted = sorted.reverse();
	        }
	        grid.updateData(sorted);
	    }); 
	    
	    
	    grid.registerRowAction(SaveRowAction);//保存
	    grid.registerRowAction(SortRowAction);//置顶
	    
	    // render grid
	    grid.render();
	   
	}

	//修改摘要
	function saveRowAction($btn,$row){
		//console.log($row)
		var $rowData=grid.getRowData($row);
		if($rowData.summary==""){
			$btn.prop("disabled", false).text("保      存");
			return;
		}
		
		
		var parm={
			sid:$rowData.sid,
			summary:$rowData.summary
		};
		
		var jumpIndex = layer.load(1);
		ajaxJsonObjectBylayer("updateAccVouchSummaryBySid.do?isCheck=false",parm, function(responseData) {
			
			if($rowData.sid==""){
				//添加摘要时，需要重新查询一遍
				ajaxJsonObjectBylayer("queryAccVouchSummary.do?isCheck=false",{},function (responseData){
					grid.renderData(responseData.Rows);
					
				},layer,jumpIndex);
			}
			
			getSummaryArry();
			layer.close(jumpIndex);
			$btn.prop("disabled", false).addClass("btn-success").text("保      存");
		},layer,jumpIndex);	
		
	}
	
	
	//置顶
	function sortRowAction($btn,$row){
		//console.log($row)
		var $rowData=grid.getRowData($row);
		if($rowData.sid==""){
			return;
		}
		
		var parm={
			sid:$rowData.sid,
			sort_code:$rowData.sort_code
		};
		
		var jumpIndex = layer.load(1);
		ajaxJsonObjectBylayer("updateAccVouchSummaryBySort.do?isCheck=false",parm, function(responseData) {
			
			if(responseData.state=="true"){
			
				ajaxJsonObjectBylayer("queryAccVouchSummary.do?isCheck=false",{},function (responseData){
					grid.renderData(responseData.Rows);
					getSummaryArry();
					layer.close(jumpIndex);
				},layer,jumpIndex);
		     
			}
			
		},layer,jumpIndex);	
	}
	
	
	//删除
	function myDel(){
		if(grid.getSelectedRows().length==0){
			return;
		}
		
		$.ligerDialog.confirm("确定要删除吗？", function(yes) {
			if(yes) {
				
				var sidArry=[];
				$.each(grid.getSelectedRows(),function (i,obj) {
					if(grid.getRowData($(this)).sid!=""){
						sidArry.push(grid.getRowData($(this)).sid);	
					}
				});
				
				if(sidArry.length==0){
					return;	
				}
				
				var jumpIndex = layer.load(1);
				ajaxJsonObjectBylayer("deleteAccVouchSummary.do?isCheck=false",{sidArry:sidArry}, function(responseData) {
					
					if(responseData.state=="true"){
						grid.removeActiveRow();
						getSummaryArry();
					}
					layer.close(jumpIndex);
					
				},layer,jumpIndex);	
				
			}
		});
		
	}
	
	
	function getSummaryArry(){
		//非编辑状态
		/* if(detailGrid.config["readonly"]){
			return;
		}  */
		var summary=[];
		$.each(grid.getGridData(),function(i,obj){
			if(obj.summary!=""){
				summary.push(obj.summary);
			}
		});
		
		parent.summaryDict=summary;
		detailGrid.editorProps["summary"] = summary;	
		
	}
	
	function myClose(){
		setTimeout(function(){			
			//var pGrid=dialog.get("data").grid;
			//pGrid.editCell(); 			
			frameElement.dialog.close();
		},50);
	}	

	
</script>
</head>

<body style="padding: 0px;overflow-x:auto; overflow-y:scroll;"  onload="">
<div style="top:41px;left:377px;position:fixed;z-index:3;height:22px;width:147px;border-top:1px solid #DDDDDD;border-right:1px solid #DDDDDD;background:#FFFFDF;text-align:center;font-weight:bold;vertical-align:middle;">操作</div>

	<div style="margin:0;position:fixed;z-index:3;left:0px;top:0px;width:100%;background:white;">
		<table id="headTable" cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" style="height:40px;">
			<tr>
				<td align="left" width="80px">
					&nbsp;&nbsp;<input type="button" value=" 删除 F2" onclick="myDel();"/>
				</td>
				<td align="right">
					&nbsp;<input type="button" value=" 关闭 ESC" onclick="myClose();"/>&nbsp;
				</td>
			</tr>
		</table>
	</div>
	<div id="templateDiv" class="sensei-grid sensei-grid-default" style="position:absolute;top:40px;"></div>
	
</body>
</html>
