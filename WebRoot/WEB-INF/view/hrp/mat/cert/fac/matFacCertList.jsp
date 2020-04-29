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
<script src="<%=path%>/lib/hrp/mat/mat.js"	type="text/javascript"></script>
<script type="text/javascript">
	var grid; 
	var gridManager = null;
	
	$(function() {
		
		loadDict();//加载下拉框
		loadHead();
	});
	//查询
	function query() {
		grid.options.parms=[];
		grid.options.newPage=1;
		
		grid.options.parms.push({name:'type_code',value:liger.get("type_code").getValue()}); 
		grid.options.parms.push({name:'fac_name',value:$("#fac_name").val()}); 
		grid.options.parms.push({name:'is_disable',value:liger.get("is_disable").getValue()}); 
		grid.options.parms.push({name:'exist_cert',value:liger.get("exist_cert").getValue()}); 

		//加载查询条件
		grid.loadData(grid.where);
	}
	
	function loadHead(){
		
		grid = $("#maingrid").ligerGrid({
    		columns: [
				{display: '厂商ID', name: 'fac_id', align: 'left', width: '20%', hide:true},
    			{display: '厂商编码', name: 'fac_code', align: 'left', width: '20%'},
    			{display: '厂商名称', name: 'fac_name', align: 'left', width: '20%'},
    			{display: '存在证件信息', name: 'cert_num', align: 'center', width: '20%',
    				render: function (rowdata, rowindex, value) {
						if(value != null && value > 0){
							return "是";
						}else{
							return "否";
						}
					}	
    			},
    			{display: '操作', name: 'oper', align: 'center', width: '20%',
    				render : function(rowdata, rowindex, value) {
    					return "<a href=javascript:f_onEdit('"
    							+ rowdata.fac_id
    							+ "','"
    							+ escape(rowdata.fac_code
    							+ " "
    							+ rowdata.fac_name)
    							+ "')>编辑</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href=javascript:f_onAdd('"
    							+ rowdata.fac_id
    							+ "','"
    							+ escape(rowdata.fac_code
    							+ " "
    							+ rowdata.fac_name)
    							+ "')>追加</a>";
    				}	
    			}, 
    			{display: '旗下材料', name: 'inv_num', align: 'center', width: '20%', type: 'float',
    				render: function (rowdata, rowindex, value) {
						if(value == null || value == 0){
							return;
						}
						return "<a href=javascript:f_onQueryInv('"
						+ rowdata.fac_id
						+ "','"
						+ escape(rowdata.fac_code
						+ " "
						+ rowdata.fac_name) + "')>"+value+"</a>";
					}
    			}
    		],
    		dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatFacCert.do?isCheck=true',
    		width: '100%', height: '100%', checkbox: false, rownumbers:true,
    		enabledEdit: true, delayLoad : false,//初始化不加载，默认false
    		selectRowButtonOnly:true,//heightDiff: -10,
    		toolbar: { items: [
    			{ text: '查询', id:'search', click: query, icon:'search' }
    		]}
    	});
		gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	function loadDict() {
		
		autocomplete("#type_code","../../../sys/queryFacTypeDict.do?isCheck=false","id","text",true,true);
		
		$('#is_disable').ligerComboBox({
				data:[{id:1,text:'是'},{id:0,text:'否'}],
				valueField: 'id',
	            textField: 'text',
				cancelable:true,
				width:160
		});
		$('#exist_cert').ligerComboBox({
			data:[{id:1,text:'是'},{id:0,text:'否'}],
			valueField: 'id',
            textField: 'text',
			cancelable:true,
			width:160
		});
		
		$("#fac_name").ligerTextBox({ width : 160 });

	}
	
	function f_onEdit(fac_id, fac_name){
		var paras = "fac_id=" + fac_id + "&fac_name=" + unescape(fac_name) ;
		 
		parent.$.ligerDialog.open({
			title : '维护生产厂商证件',
			height : $(window).height(),
			width :  $(window).width(),
			url : 'hrp/mat/cert/fac/matFacCertEditPage.do?isCheck=false&'
					+ paras.toString(),
			modal:true,
			showToggle:false,
			isResize:true, 
			showMin: true,
			showMax: true, //开启最大化最小化按钮
			parentframename : window.name, //用于parent弹出层调用本页面的方法或变量
		});
	}
	
	function f_onAdd(fac_id, fac_name){
		var paras = "fac_id=" + fac_id + "&fac_name=" + unescape(fac_name) ;
		
		parent.$.ligerDialog.open({
			title : '添加供应商证件',
			height : $(window).height() - 50,
			width :  $(window).width() - 650,
			url : 'hrp/mat/cert/fac/matFacCertAddPage.do?isCheck=false&'
					+ paras.toString(),
			modal:true,
			showToggle:false,
			isResize:true, 
			showMin: true,
			showMax: false, //开启最大化最小化按钮
			parentframename : window.name, //用于parent弹出层调用本页面的方法或变量
		});
	}
	
	function f_onQueryInv(fac_id, fac_name){
		var paras = "fac_id=" + fac_id + "&fac_name=" + unescape(fac_name) ;
		
		parent.$.ligerDialog.open({
			title : '材料列表',
			height : $(window).height(),
			width :  $(window).width(),
			url : 'hrp/mat/cert/fac/matFacCertInvPage.do?isCheck=false&'
					+ paras.toString(),
			modal:true,
			showToggle:false,
			isResize:true, 
			showMin: true,
			showMax: false, //开启最大化最小化按钮
			parentframename : window.name, //用于parent弹出层调用本页面的方法或变量
		});
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" border="0" width="100%">
		<tr>
			<td align="right" class="l-table-edit-td" >厂商类别：</td>
            <td align="left" class="l-table-edit-td"><input name="type_code" type="text" id="type_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
           
			<td align="right" class="l-table-edit-td" >厂商信息：</td>
            <td align="left" class="l-table-edit-td"><input name="fac_name" type="text" id="fac_name" ltype="text" validate="{required:true}" /></td>
           
			
			<td align="right" class="l-table-edit-td" >是否停用：</td>
            <td align="left" class="l-table-edit-td"><input name="is_disable" type="text" id="is_disable"  /></td>
         
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;">存在证件信息：</td>
            <td align="left" class="l-table-edit-td"><input name="exist_cert" type="text" id="exist_cert" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
		</tr>
	</table>
	<div id="maingrid"></div>
</body>
</html>
