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
	
	var year_month
	
	$(function() {
		loadDict();

		loadHead(null); //加载数据

	});
	
	//查询
	function query() {
	
		grid.options.parms = [];
		grid.options.newPage = 1;
		var res = year_month.getValue();
		if(res==""){
			$.ligerDialog.error("请选择统计年月！");
			return;
		}
		
		var str = res.replace('.','').substring(0,6);
		var dept_code = liger.get("dept_item").getText().split(" ")[0];
		//根据表字段进行添加查询条件
		grid.options.parms.push({name : 'year_month',value : str});
		grid.options.parms.push({name : 'dept_item',value : dept_code});
		grid.options.parms.push({name : 'dept_kind',value : liger.get("dept_kind").getValue()});
		grid.options.parms.push({name : 'dept_out',value : liger.get("dept_out").getValue()});
		grid.options.parms.push({name : 'dept_natur',value : liger.get("dept_natur").getValue()});
		grid.options.parms.push({name : 'dept_type',value : liger.get("dept_type").getValue()});
		//加载查询条件
		grid.loadData(grid.where);
	}
	
	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [  
				{display : '统计年月',name : 'year_month',align:'left'},
				{display : '科室编码',name : 'dept_code',align:'left',
					render : function(rowdata, rowindex,value) {
						  return "<a href=javascript:openUpdate('"+rowdata.group_id  + "|" + 
							rowdata.hos_id   + "|" + 
							rowdata.copy_code   + "|" + 
							rowdata.year_month  + "|" +
							rowdata.dept_id +"')>"+rowdata.dept_code+"</a>";
	  				  }	
				},
				{display : '科室名称',name:'dept_name',align:'left'},
				{display : '科室分类',name:'kind_name',align:'left'},
				{display : '科室类型',name:'type_name',align:'left'},
				{display : '科室性质',name:'natur_name',align:'left'},
				{display : '支出性质',name:'out_name',align:'left'},
				{display : '科室人数',name:'emp_count',align:'right'},
				{display : '总人数',name:'total_num',align:'right'},
				{display : '占比(%)',name:'ft_bl',align:'right'},
				{display : '状态',name:'state',align:'left'},
			],
			dataAction : 'server',dataType : 'server',usePager : false,isAddRow:false,
			url : 'queryAccPersonCollect.do',width : '100%',height : '100%',
			checkbox : true,rownumbers : true, delayLoad:true,frozen : false ,
			selectRowButtonOnly : true ,heightDiff:30,
			toolbar: { items: [
            	{ text: '查询', id:'query', click: query, icon:'search' },
            	{ line:true },
            	{ text: '添加', id:'add', click: add, icon:'add' },
                { line:true },
                { text: '删除', id:'del', click: del,icon:'delete' },
				{ line:true },
				{ text: '导入', id:'lead', click: lead,icon:'prev' },
				{ line:true },
				{ text: '继承上月', id:'inherit', click: inherit,icon:'extend' },
				{ line:true },
				{ text: '采集系统数据', id:'collect', click: collect,icon:'account' },
				{ line:true },
				{ text: '打印', id:'print', click: print,icon:'print'},
				{ line:true },
				{ text: '审核', id:'check', click: check,icon:'cashier'},
				{ line:true },
				{ text: '取消审核', id:'unCheck', click: unCheck,icon:'uncashier'},
	       	]}
		});

		gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	//审核
	function check(){
		
		if(grid.getData().length==0){
			$.ligerDialog.error("请先查询数据！");
			return;
		}
		
		$.ligerDialog.confirm('是否审核'+year_month.getValue()+'的全部数据',
			function (yes) {
				if(yes){
					var parm = {
							year_month: (year_month.getValue()).replace('.','').substring(0,6),
							state: 2
					}
					
					ajaxJsonObjectByUrl("updateAccPersonState.do", parm ,function (responseData){
		         		if(responseData.state=="true"){
		         			query();
		         		}
		         	});
				}
			}
		);
	}
	
	//取消审核
	function unCheck(){
		if(grid.getData().length==0){
			$.ligerDialog.error("请先查询数据！");
			return;
		}
		
		
		$.ligerDialog.confirm('是否取消'+year_month.getValue()+'全部数据的审核状态',
			function (yes) {
				if(yes){
					var parm = {
						year_month: (year_month.getValue()).replace('.','').substring(0,6),
						state: 1
					}
				
					ajaxJsonObjectByUrl("updateAccPersonState.do", parm ,function (responseData){
		         		if(responseData.state=="true"){
		         			query();
		         		}
		         	});
				}
			}
		);
	}
	
	//导入
	function lead(){
		parent.$.ligerDialog.open({ 
       		url : 'hrp/acc/autovouch/accpubCost/accperson/accPersonCollectImportPage.do?'
       				+'&year_month=' + (year_month.getValue()).replace('.','').substring(0,6),
			data:{
				columns : grid.columns, 
				grid : grid
			}, height: 300,width: 450,title:'科室人员采集导入',modal:true,showToggle:false,showMax:true,
			showMin: false,isResize:true,parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		}); 
	}
	
	//继承上月
	function inherit(){
		$.ligerDialog.confirm('统计年月：'+year_month.getValue()+'，是否要继承上月数据？',
			function (yes) {
				if(yes){
					var this_month = (year_month.getValue()).replace('.','').substring(0,6);
					var last_month;
					if(this_month.substring(4,5) == "01"){
						var res = ((this_month.substring(0,3))*1)-1;
						last_month = res + "12";
					}else{
						last_month = ((this_month*1)-1).toString();
					}
					var parm = {
							year_month: this_month,
							last_month: last_month
					}
					
					ajaxJsonObjectByUrl("updateAccPersonDataFromLastMonth.do", parm ,function (responseData){
		         		if(responseData.state=="true"){
		         			query();
		         		}
		         	});
				}
			}
		);
	}
	
	//采集系统数据
	function collect(){
		$.ligerDialog.confirm('是否采集'+year_month.getValue()+'的系统科室数据',
			function (yes) {
				if(yes){
					var parm = {
						year_month: (year_month.getValue()).replace('.','').substring(0,6)
					}
				
					ajaxJsonObjectByUrl("insertAccSysPerson.do", parm ,function (responseData){
		         		if(responseData.state=="true"){
		         			query();
		         		}
		         	});
				}

			}
		);
	}
	
	//更新方法
	function openUpdate(obj){
		
		var vo = obj.split("|");
		var parm = "group_id="+
			vo[0]   +"&hos_id="+ 
			vo[1]   +"&copy_code="+ 
			vo[2]   +"&year_month="+ 
			vo[3]   +"&dept_id="+
			vo[4];
		
		$.ligerDialog.open({ url : 'updateAccPersonCollectPage.do?' + parm, height: 300,width: 400, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
				buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.save();},cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
		
	}
	
	//添加
	function add(){
		$.ligerDialog.open({url: 'addAccPersonCollectPage.do', data:{"year_month":year_month.getValue()},height: 300,width: 400, title:'添加',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
				buttons: [ { text: '保存', onclick: function (item, dialog) { dialog.frame.save();},cls:'l-dialog-btn-highlight' }, { text: '关闭', onclick: function (item, dialog) { dialog.close(); } } ] });
		
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
					this.year_month +"@"+
					this.dept_id
					)
             });
             $.ligerDialog.confirm('确定删除?', function (yes){
             	if(yes){
                 	ajaxJsonObjectByUrl("deletePersonCollect.do",{ParamVo : ParamVo.toString()},function (responseData){
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
		autocomplete("#dept_item","../../accpubcost/pubcostreg/queryDeptAllInfoDict.do?isCheck=false","dept_id","dept_name",true,true);
		
		autocomplete("#dept_kind","../../../../sys/queryDeptKindDict.do?isCheck=false","id","text",true,true);
		
		autocomplete("#dept_out","../../../queryDeptOut.do?isCheck=false","id","text",true,true);
		
		autocomplete("#dept_natur","../../../queryDeptNatur.do?isCheck=false","id","text",true,true);
		
		autocomplete("#dept_type","../../../queryDeptType.do?isCheck=false","id","text",true,true);
		
		year_month = $("#year_month").etDatepicker({
    		range: false,
            view: "months",
            minView: "months",
            dateFormat: "yyyy.mm",
            width:162,
            defaultDate: ['${yearMonth}']
   		});
		
		
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
      		  "rows": [
  	          {"cell":0,"value":"统计年月："+$("#year_month").val(),"colSpan":"5"}
      		  ]
      	};
   		
   		var printPara={
   			rowCount:1,
   			title:'科室人员采集',
   			columns: JSON.stringify(grid.getPrintColumns()),//表头
   			class_name: "com.chd.hrp.acc.service.autovouch.accpubCost.AccPersonCollectService",
			method_name: "queryAccPersonPrint",
			bean_name: "accPersonCollectService",
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
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
					<span style="color:red;">*&nbsp;</span>统计年月：
				</td>
				<td align="left" class="l-table-edit-td">
					<input  name="year_month" type="text" id="year_month" ltype="text" />
				</td>
				<td align="left"></td>
			
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室：</td>
				<td align="left" class="l-table-edit-td">
					<input  name="dept_item" type="text" id="dept_item" ltype="text" />
				</td>
				
				<td align="left"></td>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室分类：</td>
				<td align="left" class="l-table-edit-td">
					<input  name="dept_kind" type="text" id="dept_kind" ltype="text" />
				</td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">支出性质：</td>
				<td align="left" class="l-table-edit-td">
					<input  name="dept_out" type="text" id="dept_out" ltype="text" />
				</td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室性质：</td>
				<td align="left" class="l-table-edit-td">
					<input  name="dept_natur" type="text" id="dept_natur" ltype="text" />
				</td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室类型：</td>
				<td align="left" class="l-table-edit-td">
					<input  name="dept_type" type="text" id="dept_type" ltype="text" />
				</td>
			</tr>
		</table>
	    <div id="maingrid"></div>
	</body>
</html>
