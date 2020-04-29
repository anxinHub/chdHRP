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
		grid.options.parms.push({name : 'project',value : $("#project").val()});
		grid.options.parms.push({name : 'coop_type',value : liger.get("coop_type").getValue()});
		
		//加载查询条件
		grid.loadData(grid.where);
	}
	
	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [  
				{display : '项目编码',name : 'proj_code',align:'left',
					  render : function(rowdata, rowindex,value) {
						  return "<a href=javascript:openUpdate('"+rowdata.group_id  + "|" + 
							rowdata.hos_id   + "|" + 
							rowdata.copy_code   + "|" + 
							rowdata.proj_code  +"')>"+rowdata.proj_code+"</a>";
	  				  }	
			    	},
				{display : '项目名称',name : 'proj_name',align:'left'},
				{display : '合作类型',name:'coop_type',align:'left'},
				{display : '状态',name:'is_stop',align:'left'},
				{display : '备注',name : 'note',align : 'left',width:180},
			],
			dataAction : 'server',dataType : 'server',usePager : false,isAddRow:false,
			url : 'queryAccCooProj.do',width : '100%',height : '100%',
			checkbox : true,rownumbers : true, delayLoad:true,frozen : false ,
			selectRowButtonOnly : true ,heightDiff:30,
			toolbar: { items: [
            	{ text: '查询', id:'query', click: query, icon:'search' },
            	{ line:true },
            	{ text: '添加', id:'add', click: add, icon:'add' },
                { line:true },
                { text: '删除', id:'del', click: del,icon:'delete' },
				{ line:true },
				{ text: '导入', id:'lead', click: lead,icon:'up' },
				{ line:true },
				{ text: '打印', id:'print', click: print,icon:'print'}
	       	]}
		});

		gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	function lead(){
		parent.$.ligerDialog.open({ 
       		url : 'hrp/acc/autovouch/acccoodeptcost/acccooproj/accProjImportPage.do',
			data:{
				columns : grid.columns, 
				grid : grid
			}, height: 300,width: 450,title:'合作项目导入',modal:true,showToggle:false,showMax:true,
			showMin: false,isResize:true,parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		}); 
	}
	
	//更新方法
	function openUpdate(obj){
		
		var vo = obj.split("|");
		var parm = "group_id="+
			vo[0]   +"&hos_id="+ 
			vo[1]   +"&copy_code="+ 
			vo[2]   +"&proj_code="+ 
			vo[3];
		
		parent.$.ligerDialog.open({ url : 'hrp/acc/autovouch/acccoodeptcost/acccooproj/updateAccCooProjPage.do?' + parm, height: 580,width: 1200, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,parentframename: window.name });
		
	}
	
	//添加
	function add(){
		parent.$.ligerDialog.open({ url : 'hrp/acc/autovouch/acccoodeptcost/acccooproj/addAccCooProjPage.do?', height: 580,width: 1200, title:'添加',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,parentframename: window.name});
	}
	
	//删除
	function del(){
		
		 var data = gridManager.getCheckedRows();
		 if (data.length == 0){
         	$.ligerDialog.error('请选择行');
         }else if(data.length > 1000){
        	$.ligerDialog.error('选择行数需小于1000行');
         }else{
             var ParamVo =[];
             $(data).each(function (){					
					ParamVo.push(
					//表的主键
					this.group_id   +"@"+ 
					this.hos_id   +"@"+ 
					this.copy_code +"@"+
					this.proj_code   
					)
             });
             $.ligerDialog.confirm('确定删除?', function (yes){
             	if(yes){
                 	ajaxJsonObjectByUrl("deleteAccCooProj.do",{ParamVo : ParamVo.toString()},function (responseData){
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
		$("#project").ligerTextBox({width:160});
		
		$("#coop_type").ligerComboBox({  
            data: [
                { text: '院内', id: '1' },
                { text: '院外', id: '2' },
            ],
            width:160
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
      		  //"rows": [
  	          //{"cell":0,"value":"凭证日期："+$("#create_date_b").val()+"至"+$("#create_date_e").val(),"colSpan":"5"}
      		  //]
      	};
   		
   		var printPara={
   			rowCount:1,
   			title:'合作项目',
   			columns: JSON.stringify(grid.getPrintColumns()),//表头
   			class_name: "com.chd.hrp.acc.service.autovouch.acccoodeptcost.AccCooProjService",
			method_name: "queryAccProjPrint",
			bean_name: "accCooProjService",
			heads: JSON.stringify(heads)//表头需要打印的查询条件,可以为空
   			};
    	
   		//执行方法的查询条件
   		$.each(grid.options.parms,function(i,obj){
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
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">项目：</td>
				<td align="left" class="l-table-edit-td"><input  name="project" type="text" id="project" ltype="text" /></td>
				<td align="left"></td>
				
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">合作类型：</td>
				<td align="left" class="l-table-edit-td">
					<input  name="coop_type" type="text" id="coop_type" ltype="text" />
				</td>
				<td align="left"></td>
				
			</tr>
		</table>
	    <div id="maingrid"></div>
	</body>
</html>
