<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<script src="<%=path%>/lib/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
	<jsp:include page="${path}/static_resource.jsp">
		 <jsp:param value="select,datepicker,select,ligerUI,checkbox,tree,pageOffice" name="plugins"/>
	</jsp:include>
	<style>
		.table-layout {font-size: 13px;width:100%}
		.tree
       {
           width: 230px;
           height: 200px;
           margin: 10px;
           border: 1px solid #ccc;
           overflow: auto;
       }
       .l-children{
       		position: relative;
  			left: -30px;
       }
	</style>
	<script type="text/javascript">
	var grid,tree;
	var gridManager = null;
	
	/*var str  = '&nbsp;&nbsp;<div class="l-toolbar-item" toolbarid="item-1"><span style="float:left">会计科目：<input type="text" id="subj_query" class="liger-textbox"  style="float:left"></span><input class="l-button l-button-submit" type="button" onClick="query()" value="查询">'
	+'</div>';*/
	$(function() {
		loadTree();
		loadHead(null); //加载数据
		loadCss();
		loadDict();
		
		var acc_month=getMonthDate('${acc_year}','${acc_month}');
		$("#create_date_b").val(acc_month.split(";")[0]);
		$("#create_date_e").val(acc_month.split(";")[1]);
		
		$('input:radio[name="pay_type_code"]').bind("change",function(){
			hideSubj();
		})
	});
	//查询
	function query() {
		var node = tree.getSelected();
		if(node == null){
			$.ligerDialog.error('请先选择差异标注项目');
			return;
		}
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
	     
		 if(node.data.pid == 'top'){
	       	grid.options.parms.push({ name : 'diff_type_code', value : node.data.id});
      	 }else if(node.data.id == 'top'){
      		 
      	 }else{
			grid.options.parms.push({ name : 'diff_item_code', value : node.data.id});
      	 }
		
		var pay_type=$("input[name='pay_type_code']:checked").attr('value');
		grid.options.parms.push({name : 'create_date_b', value : $("#create_date_b").val()});
		grid.options.parms.push({name : 'create_date_e', value : $("#create_date_e").val()});
		grid.options.parms.push({name : 'vouch_type_code', value : liger.get("vouch_type_code").getValue()});
		grid.options.parms.push({name : 'state', value : liger.get("state").getValue()});
		grid.options.parms.push({name : 'vouch_no_b', value : $("#vouch_no_b").val()});
		grid.options.parms.push({name : 'vouch_no_e', value : $("#vouch_no_e").val()});
		grid.options.parms.push({name : 'money', value : $("#money").val()});
		grid.options.parms.push({name : 'sumMoney', value : $("#sumMoney").val() });
		grid.options.parms.push({name : 'subj_code_b', value : liger.get("subj_code_b").getValue()});
		grid.options.parms.push({name : 'subj_code_e', value : liger.get("subj_code_e").getValue()});
		grid.options.parms.push({name : 'create_user', value : liger.get("create_user").getValue()});
		grid.options.parms.push({name : 'cash_user', value : liger.get("cash_user").getValue()});
		grid.options.parms.push({name : 'acc_user', value : liger.get("acc_user").getValue()});
		grid.options.parms.push({name : 'audit_user', value : liger.get("audit_user").getValue()});
		grid.options.parms.push({name : 'summary', value : $("#summary").val()});
		grid.options.parms.push({name : 'busi_type_code', value : liger.get("busi_type_code").getValue()});
		grid.options.parms.push({name : 'pay_type_code',value : pay_type});
		//加载查询条件
		grid.loadData(grid.where);
	}

	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [  
				{display : '凭证编号',name : 'vouch_no',align : 'left',width:80,
					render:function(rowdata){
						if(rowdata.state==0){
							return "<a href=javascript:openSuperVouch('"+rowdata.vouch_id+"')><div>"+rowdata.vouch_no+"<span style='color:red;font-weight: bold;'> 废</span></div></a>";
						}else if(rowdata.state==-1){
							return "<a href=javascript:openSuperVouch('"+rowdata.vouch_id+"')><div>"+rowdata.vouch_no+"<span style='color:red;font-weight: bold;'> 稿</span></div></a>";
						}else{
							if(rowdata.vouch_id ==null){
								return;
							}
							return "<a href=javascript:openSuperVouch('"+rowdata.vouch_id+"')>"+rowdata.vouch_no+"</a>";
						}
					}
				}, 
				{display : '凭证日期',name : 'vouch_date',align : 'left',width:80,formatter: "yyyy-MM-dd",},
				 {display : '科目',name : 'diff_item_name',align : 'left',width:200},  
				{display : '摘要',name : 'summary',align : 'left',width:200}, 
				{display : '金额', name : 'diff_money', align : 'right',width:120,formatter: "###,##0.00",
					render : function(rowdata, rowindex, value) {
		  				return formatNumber(rowdata.diff_money, 2, 1);
		  			}
				},
				{display : '制单人',name : 'create_name',width:80,align : 'left'}, 
				{display : '出纳签字人',name : 'cash_name',width:80,align : 'left'}, 
				{display : '审核人',name : 'audit_name',width:80,align : 'left'}, 
				{display : '记账人',name : 'acc_name',width:80,align : 'left'}, 
				{display : '凭证来源', name : 'busi_type_name',width:80, align : 'left'}, 
				{display : '备注', name : 'note',width:100, align : 'left'} 
			],
			dataAction : 'server',dataType : 'server',usePager : false,
			url : 'queryAccDifferQuery.do?isCheck=false',width : '100%',delayLoad : true,
			height : '100%',checkbox : true,rownumbers : true,selectRowButtonOnly : true,heightDiff: 28,
			toolbar : {
				items : [ 
					{ text: '查询', id:'search', click: query, icon:'search' },
                	{ line:true },
					{ text: '打印', id:'print', click: print,icon:'print'}
				]
			},
		});

		gridManager = $("#maingrid").ligerGetGridManager();
	}

	function openSuperVouch(vouch_id){
		parent.openFullDialog('hrp/acc/accvouch/superVouch/superVouchMainPage.do?vouch_id='+vouch_id,'会计凭证',0,0,true,true);
	}
	
	var setting = {      
	   		data: {simpleData: {enable: true}},
	   		treeNode:{open:true},
	   		callback:{onClick:groupDetail}
	}; 
	function loadTree(){
       	$.post('queryDifferItemTree.do?isCheck=false' ,null,function (responseData){
     	    tree= $("#tree").ligerTree({  
     	         data: responseData.Rows, 
     	         idFieldName :'id',
     	         parentIDFieldName :'pid',
     	         textFieldName : 'name',
     	         nodeWidth : 400,
     	         checkbox :false,
     	         single : true,
     	         onSelect : function(data){
     	        		 query();
     	         }
     	      });
       	},"json");
     }
	
	function groupDetail(){
		query();
	}
	
	var acc_month=getMonthDate('${acc_year}','${acc_month}');
	var date_b = acc_month.split(";")[0];
	var date_e = acc_month.split(";")[1];
	
	function loadDict() {
		$("#create_date_b").ligerTextBox({width:160});
		$("#create_date_e").ligerTextBox({width:160});
		
		autocomplete("#vouch_type_code", "../../queryVouchType.do?isCheck=false","id", "text", true, true);
		autocomplete("#state", "../../queryAccVouchState.do?isCheck=false","id", "text", true, true);
		$("#subj_code_b").ligerComboBox({
    		url: "../../querySubj.do?isCheck=false",
    		valueField: "id",
    		textField: "text",
    		selectBoxWidth: subjWidth,
    		selectBoxHeight:'260',
    		setTextBySource: true,
    		width: '160',
    		autocomplete: true,
    		highLight: true,
    		keySupport: true,
    		async: true,
    		alwayShowInDown: true,
    	});
    	$("#subj_code_e").ligerComboBox({
    		url: "../../querySubj.do?isCheck=false",
    		valueField: "id",
    		textField: "text",
    		selectBoxWidth: subjWidth,
    		selectBoxHeight:'260',
    		setTextBySource: true,
    		width: '160',
    		autocomplete: true,
    		highLight: true,
    		keySupport: true,
    		async: true,
    		alwayShowInDown: true,
    	});
    	autocomplete("#acc_user", "../../../sys/queryAccUserDict.do?isCheck=false&create_date_b='"+date_b+"'&create_date_e= '"+date_e+"' ","id", "text", true, true);
    	autocomplete("#audit_user", "../../../sys/queryAuditUserDict.do?isCheck=false&create_date_b='"+date_b+"'&create_date_e= '"+date_e+"' ","id", "text", true, true);
    	autocomplete("#create_user", "../../../sys/queryCreateUserDict.do?isCheck=false&create_date_b='"+date_b+"'&create_date_e='"+date_e+"' ","id", "text", true, true);
    	autocomplete("#cash_user", "../../../sys/queryCashUserDict.do?isCheck=false&create_date_b='"+date_b+"'&create_date_e= '"+date_e+"' ","id", "text", true, true);
    	autocomplete("#busi_type_code", "../../queryBusiTypeByVouch.do?isCheck=false&begin_date="+date_b+"&end_date="+date_e+"","id", "text", true, true);
	}
	
	function hideSubj(){
		var pay_type=$("input[name='pay_type_code']:checked").attr('value');
		
		if(pay_type=="1"){
			grid.toggleCol('diff_item_name', false);
			grid.usePager = false;
		}else{
			grid.toggleCol('diff_item_name', true);
			grid.usePager = true;
		}
		
		if(tree.getSelected() != null){
			query();
		}
	}
	
	function print(){
		if(grid.getData().length==0){
			$.ligerDialog.error("请先查询数据！");
			return;
		}
    	
    	var heads={
      		  "rows": [
  	          {"cell":0,"value":"凭证日期："+$("#create_date_b").val()+"至"+$("#create_date_e").val(),"colSpan":"5"}
      		  ]
      	};
   		
   		var printPara={
   			rowCount:1,
   			title:'凭证查询',
   			columns: JSON.stringify(grid.getPrintColumns()),//表头
   			class_name: "com.chd.hrp.acc.service.vouch.AccVouchDifferService",
			method_name: "queryAccDifferQueryPrint",
			bean_name: "accVouchDifferService",
			heads: JSON.stringify(heads)//表头需要打印的查询条件,可以为空
   		};
    	
   		//执行方法的查询条件
   		$.each(grid.options.parms,function(i,obj){
   			printPara[obj.name]=obj.value;
    	});
   		
    	officeGridPrint(printPara);
	}
	
	function loadCss(){
		$("#layout1").ligerLayout({ leftWidth: 280, allowLeftResize: true
			,onLeftToggle: function (isColl){
				grid._onResize();
	    	}
			,onEndResize: function(isColl) {
				grid._onResize();
	        }	 
		});
		grid.toggleCol('diff_item_name', false);
		$("#treeDiv").css("height", $(window).height() - 28);
		$(':button').ligerButton({width:80});
		$("#vouch_no_b").ligerTextBox({width:160 });
		$("#vouch_no_e").ligerTextBox({width:160 });
		$("#summary").ligerTextBox({width:160 });
		$("#att_num").ligerTextBox({width:160 });
		$("#money").ligerTextBox({width:160 });
		$("#sumMoney").ligerTextBox({width:160 });
		$("#business_no").ligerTextBox({width:160 });
		$("#occur_date").ligerTextBox({width:160 });
		$("#money").ligerTextBox({width:160 });
		$("#sumMoney").ligerTextBox({width:160 });
		$("#summary").ligerTextBox({width:160 });
	}
	</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div class="l-layout" id="layout1">
		<div position="left" title="差异项目" >
			<div style="overflow:auto;" id="treeDiv">
				<ul class="ztree" id="tree"></ul>
			</div>
		</div>
		<div position="center" title="会计凭证" >
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">凭证日期：</td>
			<td align="left" class="l-table-edit-td"><input class="Wdate"
				name="create_date_b" type="text" id="create_date_b" ltype="text"
				style="width: 90px;" /></td>
			<td align="left" class="l-table-edit-td">至</td>
			<td align="left" class="l-table-edit-td"><input class="Wdate" name="create_date_e"
				type="text" id="create_date_e" ltype="text"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"
				style="width: 90px;" /></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">凭证类型：</td>
			<td align="left" class="l-table-edit-td"><input
				name="vouch_type_code" type="text" id="vouch_type_code" ltype="text"
				validate="{required:true,maxlength:20}" /></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 18px;">状&nbsp;&nbsp;&nbsp;&nbsp;态：&nbsp;&nbsp;&nbsp;</td>
			<td align="left" class="l-table-edit-td"><input name="state"
				type="text" id="state" ltype="text"
				validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
		</tr>
		 <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left: 10px;">凭 证 号 ：</td>
                <td align="left" class="l-table-edit-td"><input name="vouch_no_b" type="text" id="vouch_no_b" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left" >&nbsp;至&nbsp;</td>
                <td align="left" class="l-table-edit-td"><input name="vouch_no_e" type="text" id="vouch_no_e" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="right" class="l-table-edit-td" style="padding-left:18px;">摘&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;要：</td>
                <td align="left" class="l-table-edit-td" ><input name="summary" type="text" id="summary" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="right" class="l-table-edit-td" style="padding-left:18px;" >凭证来源：</td>
                <td align="left" class="l-table-edit-td"><input name="busi_type_code" type="hidden" id="busi_type_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left: 10px;">会计科目：</td>
                <td align="left" class="l-table-edit-td"><input name="subj_code_b" type="hidden" id="subj_code_b" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="right" >&nbsp;至&nbsp;</td>
                <td align="left" class="l-table-edit-td" ><input name="subj_code_e" type="hidden" id="subj_code_e" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="right" class="l-table-edit-td" style="padding-left:18px;">制 单 人：</td>
                <td align="left" class="l-table-edit-td"><input name="create_user" type="hidden" id="create_user" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left" class="l-table-edit-td" style="padding-left:18px;">出纳人 ：</td>
                <td align="left" class="l-table-edit-td"><input name="cash_user" type="hidden" id="cash_user" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="left" class="l-table-edit-td"  style="padding-left:15px;">金&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;额：</td>
                <td align="left" class="l-table-edit-td" ><input name="money" type="text" id="money" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="right" >&nbsp;至&nbsp;</td>
                <td align="left" class="l-table-edit-td" ><input name="sumMoney" type="text" id="sumMoney" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:18px;">审 核 人：</td>
                <td align="left" class="l-table-edit-td"><input name="audit_user" type="hidden" id="audit_user" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left" class="l-table-edit-td" style="padding-left:18px;">记账人 ：</td>
                <td align="left" class="l-table-edit-td"><input name="acc_user" type="hidden" id="acc_user" ltype="text" validate="{required:true,maxlength:20}" /></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left: 10px;">展现方式：</td>
                <td align="left" class="l-table-edit-td" colspan="4">
            	 <input name="pay_type_code" type="radio" id="pay_type_code"  checked="checked" value="1"/>&nbsp;按凭证展示
                &nbsp;&nbsp; <input name="pay_type_code" type="radio" id="pay_type_id" value="2"/>&nbsp;按标注项目展示
                <td align="left"></td>
            </tr>
	</table>
			<div>
				<div id="maingrid"></div>
			</div>
		</div>
	</div>
	
<script type="text/javascript">

//改变时间日期时触发后台查询项目相关负责人 
var inputDateB = document.getElementById("create_date_b");
var inputDateE = document.getElementById("create_date_e");
inputDateB.onfocus = function(){
	 WdatePicker({
		isShowClear:true,
		readOnly:false,
		dateFmt:'yyyy-MM-dd',
		onpicked:function(dp){
			date_b = dp.cal.getNewDateStr();
	 		//发生日期不触发 
		}
	}) 
}
inputDateE.onfocus = function(){
	 WdatePicker({
		isShowClear:true,
		readOnly:false,
		dateFmt:'yyyy-MM-dd',
		onpicked:function(dp){
			date_e = dp.cal.getNewDateStr();
			//结束日期，触发重新加载
	 		loadDict();
		}
	}) 
}
 
</script>
</body>
</html>