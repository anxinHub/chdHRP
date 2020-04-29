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
    <script src="<%=path%>/lib/stringbuffer.js"></script>
    <script src="<%=path%>/lib/map.js"></script>
    <script type="text/javascript">
	var grid;

	var gridManager = null;

	var userUpdateStr;
	
    var acc_year_Month = '${yearMonth}';
    
    var map = new HashMap();
    
	$(function() {
		loadDict();

		loadHead(null); //加载数据

	});
	//查询
	function query() {
		
	    var acc_year = $("#year_month").val().substring(0,4)
	    var acc_month = $("#year_month").val().substring(5,7)
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({name : 'year_month',value : acc_year + acc_month});
		grid.options.parms.push({name : 'target_code',value : liger.get("target_code").getValue().split(",")[0]});
		//加载查询条件
		grid.loadData(grid.where);
	}
	
	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [ {display : '基本指标编码',name : 'target_code',align:'left',width:'24%'
		    	},
			     {display : '基本指标名称',name : 'target_name',align : 'left',width:'24%'
				},
				{display : '单位',name : 'unit_name',align : 'right',width:'24%'},
				{display : '数字',name : 'target_num',align : 'right',width:'24%',editor : {type : 'float'},render:function(rowdata){
					return rowdata.target_num==""?"0":rowdata.target_num;
				}}
			],
			dataAction : 'server',dataType : 'server',usePager : false,enabledEdit : true,isAddRow:true,
			url : 'queryAccTargetData.do?isCheck=false',width : '100%',height : '100%',
			checkbox : true,rownumbers : true, delayLoad:true,frozen : false ,
			selectRowButtonOnly : false ,heightDiff:0,onBeforeEdit:f_onBeforeEdit
		});

		gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	/* // 跳转到下一个单元格之前事件
	function f_onAfterEdit(e) {
		if (e.column.columnname == "target_num") {
			
			var formPara = {

					acc_year : $("#year_month").val().substring(0,4).toString(),

					acc_month : $("#year_month").val().substring(5,7).toString(),
					
					target_code : e.record.target_code,

					target_num : e.record.target_num,

				};
			ajaxJsonObjectByUrl("saveAccTargetData.do?isCheck=false", formPara, function(
					responseData) {
				if (responseData.log == "1") {
					is_addRow();
				}
			});
			
			
		}

		 return false;
	} */
	var rowindex_id = "";
	function f_onBeforeEdit(e) {
		rowindex_id = e.rowindex;	
	}
	function save(){
		
		map.removeAll();
		
		 if($("#year_month").val() ==""){
			 
			 $.ligerDialog.error('请选择会计期间！');
			 
			 return false;
		 }
		
		 var data = grid.getData();
		 
		 var acc_target_data = gridManager.rows
		 
		 var validate_detail_buf = new StringBuffer();
		 
		 /* if(acc_target_data.length > 0){
			    
			 $.each(acc_target_data, function(d_index, d_content){
				 
					var indexNo = d_index+1; 
					
					if(typeof(d_content.target_code) != "undefined" && d_content.target_code != null && d_content.target_code != ""){
						
						if(typeof(d_content.target_num) == "undefined" || d_content.target_num == ""){
							
							 validate_detail_buf.append("第["+indexNo+"]行 数字为空 请输入\n");
						}				
						    
						 if(map.get(d_content.target_code.split(",")[0]) != null){
							 
							 validate_detail_buf.append("第["+indexNo+"]行 基本指标重复\n");
						 }
						 map.put(d_content.target_code.split(",")[0],d_content.target_code.split(",")[0]);
					}else{
						
						validate_detail_buf.append("第["+indexNo+"]行 基本指标 请输入\n");
					}
					
				}); 
		 } */
		 
	
		 if(validate_detail_buf.toString()  != ""){
				
				$.ligerDialog.warn(validate_detail_buf.toString());
				
				return false;
			}

		 var formPara = {
					
				    acc_year : $("#year_month").val().substring(0,4).toString(),

					acc_month : $("#year_month").val().substring(5,7).toString(),

					data : JSON.stringify(data)

			};
		 ajaxJsonObjectByUrl("saveBatchAccTargetData.do?isCheck=false", formPara, function(responseData) {

				if (responseData.state == "true") {
					      query();
				}
			});
	}
	function del_open(){
		
		gridManager.deleteSelectedRow();
		
		/*   var data = gridManager.getCheckedRows();
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
    							this.acc_year +"@"+
    							this.acc_month +"@"+
    							this.target_code   
    							)
      
             });
             $.ligerDialog.confirm('确定删除?', function (yes){
             	if(yes){
                 	ajaxJsonObjectByUrl("deleteBatchAccTargetData.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
                 		if(responseData.state=="true"){
                 			query();
                 		}
                 	});
             	}
             }); 
         }  */
	}
	
	function print(){
		
		if(grid.getData().length==0){
			$.ligerDialog.error("请先查询数据！");
			return;
		}
           	
        	var selPara={};
        	$.each(grid.options.parms,function(i,obj){
        		selPara[obj.name]=obj.value;
        	});
     
       		//console.log(grid)
       		var printPara={
       			headCount:2,
       			title:'基本数字',
       			type:3,
       			columns:grid.getColumns(1),
       			autoFile:true
       		};
       		
       		ajaxJsonObjectByUrl("queryAccTargetData.do?isCheck=false", selPara, function(responseData) {
       			printGridView(responseData,printPara);
        	});
	}
	
	function loadDict() {
		
		autocomplete("#target_code","../queryAccTarget.do?isCheck=false","id","text",true,true);
		
   	   $("#year_month").ligerComboBox({
       	url: '../queryYearMonth.do?isCheck=false',
       	valueField: 'id',
        	textField: 'text', 
        	selectBoxWidth: 180,
       	autocomplete: true,
       	width: 180
		  });
   	   
  	 liger.get("year_month").setValue(acc_year_Month.substring(0,4)+"."+acc_year_Month.substring(4,6).toString());
	 
	 liger.get("year_month").setText(acc_year_Month.substring(0,4)+"."+acc_year_Month.substring(4,6).toString());


		//字典下拉框
		  $(':button').ligerButton({width:80});
		
		  $("#year_month").ligerTextBox({width:160});
		
		  $("#target_code").ligerTextBox({width:160});
	}
	

	function is_addRow() {
		setTimeout(function() { //当数据为空时 默认新增一行
			grid.addRow();
		}, 100);
	}
	
	function inherit() {
		if($("#year_month").val() ==""){
			 $.ligerDialog.error('请选择会计期间！');
			 return false;
		 }
		
		$.ligerDialog.confirm('是否继承上月数据?', 
				function (yes) 
				{ 

				if(yes==true)
					{
					
					 var formPara = {
								
							    acc_year : $("#year_month").val().substring(0,4).toString(),

								acc_month : $("#year_month").val().substring(5,7).toString(),
		

						};
					 ajaxJsonObjectByUrl("inheritAccTargetData.do?isCheck=false", formPara, function(responseData) {

							if (responseData.state == "true") {
								      query();
							}
						});
					
					}
				
				});
		 
	}

function imp_open(){
		
		$.ligerDialog.open({url: 'accTargetDataImportPage.do?isCheck=false', height: 500,width: 790, title:'导入',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true });
	}
function downTemplate(){
	
	location.href = "downTemplateTargetDate.do?isCheck=false";
}

</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">会计期间：</td>
			<td align="left" class="l-table-edit-td"><input  name="year_month" type="text" id="year_month" ltype="text" style="width: 160px;" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">基本指标：</td>
			<td align="left" class="l-table-edit-td"><input  name="target_code" type="text" id="target_code" ltype="text" /></td>
			<td align="left"></td>
		</tr>
	</table>
	<div style="border:1px">
	<input  type="button" value=" 查询" onclick="query();"/>
	<input  type="button" value=" 保存" onclick="save();"/>
	<input  type="button" value=" 打印" onclick="print();"/>
	<input  type="button" value=" 继承" onclick="inherit();"/>
	<input  type="button" value=" 下载导入模板" onclick="downTemplate();"/>
	<input  type="button" value=" 导入" onclick="imp_open();"/>
	</div>
	<div id="maingrid"></div>

</body>
</html>
