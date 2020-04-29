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
    <script type="text/javascript">
	var grid;

	var gridManager = null;

	var userUpdateStr;
	
	$(function() {
		loadDict();

		loadHead(null); //加载数据

	});
	
	
	//查询
	function query() {
	
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({name : 'bas_type_code',value : liger.get("bas_type_code").getValue()});
		grid.options.parms.push({name : 'wx_type_code',value : liger.get("wx_type_code").getValue()});
		grid.options.parms.push({name : 'basic_index',value : $("#basic_index").val()});
		//加载查询条件
		grid.loadData(grid.where);
	}
	
	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [  
				{display : '基本指标编码',name : 'bas_code',align:'left',width:180,
					  render : function(rowdata, rowindex,value) {
						  return "<a href=javascript:openUpdate('"+rowdata.group_id  + "|" + 
							rowdata.hos_id   + "|" + 
							rowdata.copy_code   + "|" + 
							rowdata.bas_code  +"')>"+rowdata.bas_code+"</a>";
	  				  }	
			    	},
				{display : '基本指标名称',name : 'bas_name',align:'left'},
				{display : '指标单位',name:'bas_unit',align:'left',width:80},
				{display : '基本分析指标类别',name:'bas_type_name',align:'left'},
				{display : '五性分析指标类别',name:'wx_type_name',align:'left'},
				{display : '状态',name:'is_stop',align:'left',width:80,
					render : function(rowdata, rowindex,value) {
	  					if(rowdata.is_stop == 0){
	  						return "启用"
	  					}else {
	  						return "停用"
	  					}
	  				}	
				},
				{display : '备注',name : 'note',align : 'left',width:400},
			],
			dataAction : 'server',dataType : 'server',usePager : false,isAddRow:false,
			url : 'queryAccFundaAnalysis.do',width : '100%',height : '100%',
			checkbox : true,rownumbers : true, delayLoad:true,frozen : false ,
			selectRowButtonOnly : true ,heightDiff:30,
			toolbar: { items: [
            	{ text: '查询', id:'query', click: query, icon:'search' },
            	{ line:true },
            	{ text: '添加', id:'add', click: add, icon:'add' },
                { line:true },
                { text: '删除', id:'del', click: del,icon:'delete' },
				{ line:true },
				{ text: '打印', id:'print', click: print,icon:'print'}
	       	]}
		});

		gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	//更新方法
	function openUpdate(obj){
		
		var vo = obj.split("|");
		var parm = "group_id="+
			vo[0]   +"&hos_id="+ 
			vo[1]   +"&copy_code="+ 
			vo[2]   +"&bas_code="+ 
			vo[3];
		
		$.ligerDialog.open({ url : 'updateAccFundaAnalysisPage.do?' + parm, height: 480,width: 660, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
				buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.save(); query();},cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
		
	}
	
	//添加
	function add(){
		$.ligerDialog.open({url: 'addAccFundaAnalysisPage.do', height: 480,width: 660, title:'添加',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
				buttons: [ { text: '保存', onclick: function (item, dialog) { dialog.frame.save(); query();},cls:'l-dialog-btn-highlight' }, { text: '关闭', onclick: function (item, dialog) { dialog.close(); } } ] });
		
	}
	
	//删除
	function del(){
		
		 var data = gridManager.getCheckedRows();
		 if (data.length == 0){
         	$.ligerDialog.error('请选择行');
         }else{
             var ParamVo =[];
             $(data).each(function (){					
					ParamVo.push(
					//表的主键
					this.group_id   +"@"+ 
					this.hos_id   +"@"+ 
					this.copy_code +"@"+
					this.bas_code   
					)
             });
             $.ligerDialog.confirm('确定删除?', function (yes){
             	if(yes){
                 	ajaxJsonObjectByUrl("deleteBatchAccFunda.do",{ParamVo : ParamVo.toString()},function (responseData){
                 		if(responseData.state=="true"){
                 			query();
                 		}
                 	});
             	}
             }); 
         }
	}
	
	function loadDict() {
		//字典下拉框
		$("#basic_index").ligerTextBox({width:160});
		
		autocomplete("#bas_type_code","../queryAccBasType.do?isCheck=false","id","text",true,true);
		
		autocomplete("#wx_type_code","../queryAccWxType.do?isCheck=false","id","text",true,true);

	}
	
	//打印方法
	function print(){
    	
    	if(grid.getData().length==0){
    		
			$.ligerDialog.error("请先查询数据！");
			
			return;
		}
    	
    	/* var selPara={};
    	$.each(grid.options.parms,function(i,obj){
    		selPara[obj.name]=obj.value;
    	}); */
    	var heads={
      		  //"isAuto": true/false 默认true，页眉右上角默认显示页码
      		  //"rows": [
  	          //{"cell":0,"value":"凭证日期："+$("#create_date_b").val()+"至"+$("#create_date_e").val(),"colSpan":"5"}
      		  //]
      	};
   		
   		var printPara={
   			rowCount:1,
   			title:'基本分析指标',
   			columns: JSON.stringify(grid.getPrintColumns()),//表头
   			class_name: "com.chd.hrp.acc.service.AccFundaAnalysisService",
			method_name: "queryAccFundaPrint",
			bean_name: "accFundaAnalysisService",
			heads: JSON.stringify(heads)//表头需要打印的查询条件,可以为空
   			};
    	
   		//执行方法的查询条件
   		$.each(grid.options.parms,function(i,obj){
   			debugger
   			printPara[obj.name]=obj.value;
    	});
   		
    	officeGridPrint(printPara);
   		/* ajaxJsonObjectByUrl("queryAccVouch.do?isCheck=false", selPara, function (responseData) {
   			printGridView(responseData,printPara);
		}); */
    }
	
	
</script>

</head>
	<body style="padding: 0px; overflow: hidden;">
		<div id="pageloading" class="l-loading" style="display: none"></div>
		<table cellpadding="0" cellspacing="0" class="l-table-edit">
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">基本分析指标类别：</td>
				<td align="left" class="l-table-edit-td">
					<input  name="bas_type_code" type="text" id="bas_type_code" ltype="text" />
				</td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">五性分析指标类别：</td>
				<td align="left" class="l-table-edit-td">
					<input  name="wx_type_code" type="text" id="wx_type_code" ltype="text" />
				</td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">基本指标：</td>
				<td align="left" class="l-table-edit-td"><input  name="basic_index" type="text" id="basic_index" ltype="text" /></td>
				<td align="left"></td>
			</tr>
		</table>
	    <div id="maingrid"></div>
	</body>
</html>
