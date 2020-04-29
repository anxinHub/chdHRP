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
	<script>
		var grid;
		var gridManager = null;
		$(function () {
			init();
			loadGrid();
		});
		function init() {
		 
		}
		//查询
		function query() {		
			grid.options.parms = [];
			grid.options.newPage = 1;
			//根据表字段进行添加查询条件
			grid.options.parms.push({name : 'keywords',value : $("#keywords").val()});
			//加载查询条件
			grid.loadData(grid.where);
		}
		function loadGrid() {
			grid = $("#maingrid").ligerGrid({
				columns : [  
					{display : '因素指标编码',name : 'fac_code',align:'left',
						  render : function(rowdata, rowindex,value) {
							  return "<a href=javascript:openUpdate('"+rowdata.fac_code  +"')>"+rowdata.fac_code+"</a>";
		  				  }	
				    	},
					{display : '因素指标名称',name : 'fac_name',align:'left'},
					{display : '指标单位',name:'zb_unit',align:'left'},
					{display : '状态',name:'is_stop',align:'left',
						render : function(rowdata, rowindex,value) {
		  					if(rowdata.is_stop == 1){
		  						return "停用"
		  					}else {
		  						return "启用"
		  					}
		  				}	
					},
					{display : '备注',name : 'note',align : 'left',width:400},
				],
				dataAction : 'server',dataType : 'server',usePager : false,isAddRow:false,
				url : 'queryAccAlyFac.do',width : '100%',height : '100%',
				checkbox : true,rownumbers : true, delayLoad:true,frozen : false ,
				selectRowButtonOnly : true ,heightDiff:30,
				toolbar: { items: [
	            	{ text: '查询', id:'query', click: query, icon:'search' },
	            	{ line:true },
	            	{ text: '添加', id:'add', click: add_open, icon:'add' },
	                { line:true },
	                { text: '删除', id:'del', click: remove,icon:'delete' },
					{ line:true },
					{ text: '打印', id:'print', click: printDate,icon:'print'}
		       	]}
			});

			gridManager = $("#maingrid").ligerGetGridManager();			
		}


		function openUpdate(fac_code) {
			$.ligerDialog.open({ url : 'facUpdatePage.do?fac_code='+fac_code, height: 480,width: 660, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
					buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.save(); query();},cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
		}

		function remove() {
			 var data = gridManager.getCheckedRows();
			 if (data.length == 0){
	         	$.ligerDialog.error('请选择行');
	         }else{
	             var ParamVo =[];
	             $(data).each(function (){					
						ParamVo.push(
						this.fac_code +"@"+
						this.super_code   
						)
	             });
	             $.ligerDialog.confirm('确定删除?', function (yes){
	             	if(yes){
	                 	ajaxJsonObjectByUrl("deleteAccAlyFac.do",{ParamVo : ParamVo.toString()},function (responseData){
	                 		if(responseData.state=="true"){
	                 			query();
	                 		}
	                 	});
	             	}
	             }); 
	         }
			
		}
		
		//添加
		function add_open(){
			$.ligerDialog.open({url: 'facAddPage.do', height: 480,width: 660, title:'因素分析指标添加',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
					buttons: [ { text: '保存', onclick: function (item, dialog) { dialog.frame.save(); query();},cls:'l-dialog-btn-highlight' }, { text: '关闭', onclick: function (item, dialog) { dialog.close(); } } ] });
			
		}

		
		var printDate = function () {
        	if(grid.getData()==null){
        		$.ligerDialog.warn("请先查询数据！");
    			return;
    		}
        	var heads={}; 
        	var printPara={
                title: "因素分析设置",//标题
                columns: JSON.stringify(grid.getPrintColumns()),//表头
                class_name: "com.chd.hrp.acc.serviceImpl.accAly.AccAlyFacServiceImpl",
                method_name: "queryFacPrintDate",
                bean_name: "accAlyFacService",
                heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
                foots: '',//表尾需要打印的查询条件,可以为空 
            };
          //执行方法的查询条件
       		$.each(grid.options.parms,function(i,obj){
       			printPara[obj.name]=obj.value;
        	});
            officeGridPrint(printPara);
        	
        	
        }
		//键盘事件
		function loadHotkeys() {
			hotkeys('Q', queryNew);
			hotkeys('A', add_open);
			hotkeys('D', remove);
			hotkeys('P', lodopPrint);
			
		}
	</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
		<table cellpadding="0" cellspacing="0" class="l-table-edit">
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">因素指标：</td>
				<td align="left" class="l-table-edit-td"><input  name="keywords" type="text" id="keywords" ltype="text" /></td>
				<td align="left"></td>
			</tr>
		</table>
	</div>
	<div id="maingrid"></div>

</body>

</html>