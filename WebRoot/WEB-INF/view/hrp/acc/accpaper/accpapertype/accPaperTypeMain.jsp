<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" /> 
<title></title>
<jsp:include page="${path}/inc.jsp"/>
<script src="<%=path%>/lib/SpreadJS/scripts/gcspread.sheets.all.9.40.20161.0.min.js" type="text/javascript"></script>
<script src="<%=path%>/lib/SpreadJS/scripts/pluggable/gcspread.sheets.print.9.40.20161.0.min.js" type="text/javascript"></script>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
	
    var grid;
    
    var gridManager = null;
    
    var userUpdateStr;
    
    $(function ()
    {
		loadDict();
		
    	loadHead(null);	//加载数据
    	
    	loadHotkeys();
    	
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'type_code',value:$("#type_code").val()}); 
    	grid.options.parms.push({name:'type_name',value:$("#type_name").val()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '类型编码', name: 'type_code', align: 'left',width:80,
   					     render : function(rowdata, rowindex,value) {
						  return "<a href=javascript:openUpdate('"+rowdata.group_id  + "|" + 
							rowdata.hos_id   + "|" + 
							rowdata.copy_code   + "|" + 
							rowdata.type_code  +"')>"+rowdata.type_code+"</a>";
	  				  }	
                    	 },
                     { display: '类型名称', name: 'type_name', align: 'left',width:200},
                     { display: '前缀', name: 'paper_prefix', align: 'left'},
                     { display: '票据号长度', name: 'paper_clen', align: 'left'},
                     { display: '每本张数', name: 'paper_zlen', align: 'left'},
                     { display: '管理方式', name: 'paper_way_type', align: 'left',
     					render : function(rowdata, rowindex,value) {
    	  					if(rowdata.paper_way_type == 1){
    	  						return "单张管理"
    	  					}else {
    	  						return "多张管理"
    	  					}
    	  				}	},
                     { display: '领用方式', name: 'paper_use_type', align: 'left',
     					render : function(rowdata, rowindex,value) {
    	  					if(rowdata.paper_use_type == 1){
    	  						return "一次领用"
    	  					}else {
    	  						return "二次领用"
    	  					}
    	  				}	},
                     /* { display: '手动核销', name: 'is_sd', align: 'left',
     					render : function(rowdata, rowindex,value) {
    	  					if(rowdata.is_sd == 0){
    	  						return "否"
    	  					}else {
    	  						return "是"
    	  					}
    	  				}	},
                     { display: '自动核销', name: 'is_auto', align: 'left',
     					render : function(rowdata, rowindex,value) {
    	  					if(rowdata.is_auto == 0){
    	  						return "否"
    	  					}else {
    	  						return "是"
    	  					}
    	  				}	
                     }, */
                     { display: '是否停用', name: 'is_stop', align: 'left',
     					render : function(rowdata, rowindex,value) {
    	  					if(rowdata.is_stop == 0){
    	  						return "否"
    	  					}else {
    	  						return "是"
    	  					}
    	  				}	
                     }, { display: '是否银行票据', name: 'is_wb', align: 'left',
     					render : function(rowdata, rowindex,value) {
    	  					if(rowdata.is_wb == 0){
    	  						return "否"
    	  					}else {
    	  						return "是"
    	  					}
    	  				}	
                     }, { display: '备注', name: 'note', align: 'left'},
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAccPaperType.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code  + "|" + 
								rowdata.type_code  
							);
    				} ,
    				lodop:{
    	         		title:"票据类型",
    	      			fn:{
    	          			debit:function(value){//借方
    	          				if(value == 0){return "";}
    	                 			else{return formatNumber(value, 2, 1);}
    	          			},
    	          			credit:function(value){//贷方
    	          				if(value == 0){return "";}
    	                			else{return formatNumber(value, 2, 1);}
    	         				},
    	         				end_os:function(value){//余额
    	      	   				 if(value==0){return "Q";}
    	      					 else{return formatNumber(value, 2, 1);}
    	        				}
    	          		}
    	      		}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }

    function add_open(){
    	
    	$.ligerDialog.open({url: 'accPaperTypeAddPage.do?isCheck=false', height: 450,width: 500, title:'添加',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveAccPayType(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
    	
    }
    function del_open(){
    	
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
					this.type_code   
					)
             });
             $.ligerDialog.confirm('确定删除?', function (yes){
             	if(yes){
                 	ajaxJsonObjectByUrl("deleteBatchAccPaperType.do",{ParamVo : ParamVo.toString()},function (responseData){
                 		if(responseData.state=="true"){
                 			query();
                 		}
                 	});
             	}
             }); 
         }
    }
    
    function openUpdate(obj){
    	
		var vo = obj.split("|");
		var parm = "&group_id="+ 
			vo[0]   +"&hos_id="+ 
			vo[1]   +"&copy_code="+ 
			vo[2]   +"&type_code="+ 
			vo[3]
		
    	$.ligerDialog.open({ url : 'updateAccPaperTypePage.do?isCheck=false' + parm,data:{}, height: 450,width: 500, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveAccPayType(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

    }
    
    function print_open(){
   	 
    	var columnInfos = [{
			 name : "type_code",
			 displayName : "类型编码",
			 size: 200
		 },{ 
			 name: 'type_name', 
			 displayName: '类型名称',
			 size: 200
		 },{ 
			 name: 'paper_prefix', 
			 displayName: '前缀',
			 size: 80
		 },{ 
			 name: 'paper_clen', 
			 displayName: '票据号长度', 
			 size: 80
		 },{ 
			 name: 'paper_zlen', 
			 displayName: '每本张数', 
			 size: 80
		 }
    	];

		var printPara = {
				
    			usePager : false,
    			
    			type_code:$("#type_code").val(),
    			
    			type_name:$("#type_name").val()
    		};

    	//公用部分
		viewPrintOneHead("queryAccPaperType.do?isCheck=false", printPara, columnInfos,
				"单位未达账",500);

    }

	//键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
		
		hotkeys('A', add_open);
		
		hotkeys('D', del_open);
		
		hotkeys('P', print_open);

	}

    
    function loadDict(){
            //字典下拉框
        $(':button').ligerButton({width:80});
            
    	$("#type_code").ligerTextBox({width:160});
    	
    	$("#type_name").ligerTextBox({width:160});
    	
         } 
    
    function printDate(){
		 if(grid.getData().length==0){
 		
			$.ligerDialog.error("请先查询数据！");
			
			return;
		}
 	var heads={
	      		  //"isAuto": true/false 默认true，页眉右上角默认显示页码
	      		  "rows": [
// 							{"cell":0,"value":"会计期间："+$("#acc_year_month").val(),"colSpan":"5"},
// 							{"cell":3,"value":"系统名称："+liger.get("mod_code").getText(),"from":"right","align":"right","colSpan":"4"}  
	      		  ]
	      	};
	   		
		var printPara={
			rowCount:1,
			title:'票据类型',
			columns: JSON.stringify(grid.getPrintColumns()),//表头
			class_name: "com.chd.hrp.acc.service.paper.AccPaperTypeService",
			method_name: "queryAccPaperTypePrint",
			bean_name: "accPaperTypeService",
			heads: JSON.stringify(heads)//表头需要打印的查询条件,可以为空
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

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">类型编码：</td>
            <td align="left" class="l-table-edit-td"><input name="type_code" type="text" id="type_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">类型名称：</td>
            <td align="left" class="l-table-edit-td"><input name="type_name" type="text" id="type_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
	
    </table>
    <div style="border:1px">
    <input  type="button" value=" 查询(Q)" onclick="query();"/>
	<input  type="button" value=" 添加(A)" onclick="add_open();"/>
	<input  type="button" value=" 删除(D)" onclick="del_open();"/>
	<input  type="button" value=" 打 印" onclick="printDate();"/>
	<!-- <input  type="button" value=" 打印(p)" onclick="print_open();"/> -->
	</div>
	<div id="maingrid"></div>

</body>
</html>
