<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"  xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script src="<%=path%>/lib/hrp/mat/mat.js" type="text/javascript"></script>
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
 
    
    $(function ()
    {
        loadDict();
    	loadHead(null);	
		loadHotkeys();
		query();
    });

    //键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('A', add);
		hotkeys('D', remove);
	}
    
    function loadDict() {
		//字典下拉框
		autocompleteAsync("#store_id", "../../queryMatStoreDictDate.do?isCheck=false", "id","text", true, true, {read_or_write:1}, false, false, '180');
	}
	
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
	                     { display: '养护单号', name: 'mt_no', align: 'left',width : 200,
	                    	 render : function(rowdata, rowindex, value) {
	     						return '<a href=javascript:openUpdate("' 
	     							+ rowdata.group_id
	     							+ ',' + rowdata.hos_id 
	     							+ ',' + rowdata.copy_code
	     							+ ',' + rowdata.mt_id
	     							+ '")>'+rowdata.mt_no+'</a>';
	     					 }	 
	                     },
	                     { display: '仓库', name: 'store_name', align: 'left',width : 200},
	                     { display: '物资类别', name: 'mat_type_name', align: 'left',width : 200},
	                     { display: '制单日期', name: 'make_date', align: 'left',width : 200},
	                     { display: '制单人', name: 'maker_name', align: 'left',width : 200}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatMaintainMain.do?isCheck=false',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     					{ text: '查询', id:'search', click: query,icon:'search' },
                     					{ line:true },
				    					{ text: '添加', id:'add', click: add_open, icon:'add' },
										{ line:true },
										{ text: '删除', id:'delete', click: remove,icon:'delete' },
				    	                { line:true }
							 ]},
    				onDblClickRow : function (rowdata, rowindex, value){
										openUpdate(
												rowdata.group_id   + "," + 
												rowdata.hos_id   + "," + 
												rowdata.copy_code   + "," + 
												rowdata.mt_id 
											);
    								} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    //查询
    function  query(){
		grid.options.parms=[];
    	grid.options.newPage=1;
        //根据表字段进行添加查询条件
		grid.options.parms.push({name:'store_id',value:liger.get("store_id").getValue().split(",")[0]}); 
    	//加载查询条件
    	grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
     }
    
    //添加
    function add_open(){
    	parent.$.ligerDialog.open({
			title: '养护记录-添加', height:$(window).height()*0.5,width:$(window).width()*0.4, 
			url: 'hrp/mat/storage/maintain/matMaintainMainAddPage.do?isCheck=false',
			modal: true, showToggle: false, isResize: true,//showMax: true, showMin: true, 
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
			buttons: [ 
			           { text: '确定', onclick: function (item, dialog) { dialog.frame.saveMatLocationType();},cls:'l-dialog-btn-highlight' }, 
			           { text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
			         ] 
		});   
	} 
    

    
    //修改
    function openUpdate(obj) {
		var voStr = obj.split(",");
		var paras = 
			"group_id=" + voStr[0].toString() + "&" 
			+ "hos_id=" + voStr[1].toString() + "&" 
			+ "copy_code=" + voStr[2].toString() + "&" 
			+ "mt_id=" + voStr[3].toString();
		parent.$.ligerDialog.open({
			title: '养护记录修改',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/mat/storage/maintain/matMaintainUpdatePage.do?isCheck=false&' + paras.toString(),
			modal: true, showToggle: false, showMax: true, showMin: true, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});   
	}
    
    function remove(){

		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择要删除的数据');
		} else {
			var ParamVo = [];
			var nos = "";
			$(data).each(
					function() {
						ParamVo.push(
								this.hos_id + "@" + 
								this.group_id + "@"+ 
								this.copy_code + "@" +
								this.mt_id
								)
			});
			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteMatMaintainMainAndDetail.do?isCheck=false", {ParamVo : ParamVo.toString()}, function(responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			});
		}
	}
 

	
	
	
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar" ></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit"  width="100%">
		<tr>
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;">仓  库：</td>
			<td align="left" class="l-table-edit-td">
				<input name="store_id" type="text" id="store_id" ltype="text" />
			</td>
		</tr> 
	</table>
	<div id="maingrid"></div>
</body>
</html>
