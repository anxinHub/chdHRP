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
<script type="text/javascript">
    var grid;
    var gridManager = null;
    
    $(function () {
    	
    	//加载下拉框
        loadDict();
    	//加载数据
    	loadHead(null);	
    });
    //查询
    function  query(){
		grid.options.parms=[];
		grid.options.newPage=1;
		
        //根据表字段进行添加查询条件
     	grid.options.parms.push({name : 'startDate',value : $("#startDate").val()});
		grid.options.parms.push({name : 'endDate',value : $("#endDate").val()});
		grid.options.parms.push({name : 'store_id',value : liger.get("store_id").getValue().split("@")[0]}); 
		grid.options.parms.push({name : 'store_no',value : liger.get("store_id").getValue().split("@")[1]}); 
		grid.options.parms.push({name : 'ass_type_id',value : liger.get("ass_type_id").getValue()});
		grid.options.parms.push({name : 'ass_code',value : $("#ass_code").val()}); 
		grid.options.parms.push({name : 'batch_no',value : $("#batch_no").val()});
        grid.options.parms.push({name : 'sup_id',value : liger.get("ven_id").getValue().split("@")[0]}); 
		grid.options.parms.push({name : 'fac_id',value : liger.get("fac_id").getValue().split("@")[0]}); 
		
    	//加载查询条件
    	grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
     }

	function loadHead(){
    	grid = $("#maingrid").ligerGrid({
        	columns: [
        		{ display : '资产编码', name : 'ass_code', align : 'left',
					},
				{ display : '资产名称', name : 'ass_name', align : 'left',
					},	
				{ display : '规格', name : 'ASS_SPEC', align : 'left',
					},
				{ display : '型号', name : 'ass_model', align : 'left',
					},	
				{ display : '计量单位', name : 'unit_name', align : 'left',
					},	
				{ display : '供应商', name : 'sup_name', align : 'left',
					},		
				{ display : '生产厂商', name : 'fac_name', align : 'left',
					},	
				{ display : '单价', name : 'price', align : 'left',
					render : function(rowdata, rowindex,value) {
						return formatNumber(value,'${ass_05005 }',1);
						}
					},	
				{ display : '入库数量', name : 'in_amount', align : 'left',
					render : function(rowdata, rowindex, value) {
						return value ==null ? "" : "<a href=javascript:queryInDetail('"+
							"ass_id="+rowdata.ASS_ID+"&sup_id="+(rowdata.VEN_ID?rowdata.VEN_ID:"")+"&fac_id="+(rowdata.FAC_ID?rowdata.FAC_ID:"")
							+"&ass_spec="+rowdata.ASS_SPEC+"&ass_model="+rowdata.ass_model+"')>"+value+"</a>";
						}
					},	
				{ display : '入库金额', name : 'in_money', align : 'left',
					render : function(rowdata, rowindex,value) {
						return formatNumber(value,'${ass_05005 }',1);
					    }
					},
				{ display : '出库数量', name : 'out_amount', align : 'left',
					render : function(rowdata, rowindex, value) {
			 			return value == 0 ? 0 : "<a href=javascript:queryOutDetail('"+
			 				"ass_id="+rowdata.ASS_ID+"&sup_id="+(rowdata.VEN_ID?rowdata.VEN_ID:"")+"&fac_id="+(rowdata.FAC_ID?rowdata.FAC_ID:"")
							+"&ass_spec="+rowdata.ASS_SPEC+"&ass_model="+rowdata.ass_model+"')>"+value+"</a>";
			 			}
					},
				{ display : '出库金额', name : 'out_money', align : 'left',
					render : function(rowdata, rowindex,value) {
						return formatNumber(value,'${ass_05005 }',1);
						}
					},
			],
			dataAction : 'server',
			dataType : 'server',
			usePager : true,
			url : '../tongJiReports/queryAssInAndOutSummary.do', 
			width : '100%',
			height : '100%',
			rownumbers : true,
			delayLoad : true,
			selectRowButtonOnly : true,
			toolbar : {
				items : [ {
						text : '查询（<u>Q</u>）',
						id : 'search',
						click : query,
						icon : 'search'
					}, {
						line : true
					},  {
						text : '打印（<u>P</u>）',
						id : 'print',
						click : printDate,
						icon : 'print'
					}, {
						line : true
					},
				]
			},

		});

		gridManager = $("#maingrid").ligerGetGridManager();
	}

	//键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);

		hotkeys('E', exportExcel);

		hotkeys('P', printDate);

	}

	function loadDict() {
		
        var param = {
	           	query_key:''
	    };
		
		//字典下拉框 
		$("#startDate").ligerTextBox({width:90});
        autodate("#startDate", "yyyy-MM-dd", "month_first");
        $("#endDate").ligerTextBox({width:90});
        autodate("#endDate", "yyyy-MM-dd", "month_last");
          
	   	autocomplete("#store_id","../queryHosStoreDict.do?isCheck=false", "id","text", true, true,param,true);
    	autocomplete("#ass_type_id","../queryAssTypeDictIsLast.do?isCheck=false","id","text",true,true,null,null,null,160);
		
    	$("#ass_code").ligerTextBox({width:205});
        autocomplete("#fac_id","../queryHosFacDictNo.do?isCheck=false","id","text",true,true,null,null,null,160);
        autocomplete("#ven_id","../queryHosSupDict.do?isCheck=false","id","text",true,true,param,true,'',160); 
	}
	
	//查询入库材料数量明细
    function queryInDetail(para){
   	
   		var paras=para
   	          +"&store_id="+liger.get("store_id").getValue().split("@")[0]
   	          +"&store_no="+(liger.get("store_id").getValue().split("@")[1]?liger.get("store_id").getValue().split("@")[1]:"")
   	          +"&startDate="+$("#startDate").val()
   	          +"&endDate="+$("#endDate").val()
   	
		parent.$.ligerDialog.open({
			title:'资产入库详情',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/ass/tongJiReports/assInDetailPage.do?isCheck=false&' + paras.toString(),
			modal: true, showToggle: false, showMax: true, showMin: true, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});	 
   	}
	
  //查询入库材料数量明细
    function queryOutDetail(para){		
    	var paras=para
		        +"&store_id="+liger.get("store_id").getValue().split("@")[0]
		        +"&store_no="+(liger.get("store_id").getValue().split("@")[1]?liger.get("store_id").getValue().split("@")[1]:"")
		        +"&startDate="+$("#startDate").val()
		        +"&endDate="+$("#endDate").val()
		
		parent.$.ligerDialog.open({
			title:'资产出库详情',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/ass/tongJiReports/assOutDetailPage.do?isCheck=false&' + paras.toString(),
			modal: true, showToggle: false, showMax: true, showMin: true, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
        });
    }
	
  	function printDate(){
  		
  		if(grid.getData().length==0){
   			$.ligerDialog.error("请先查询数据！");
   			return;
   		}
 		
 		var time=new Date();
    	var date=time.getFullYear()+"年"+(time.getMonth()+1)+"月"+time.getDate()+"日";
    	var heads={
        		"isAuto":true,//系统默认，页眉显示页码
        		"rows": [
    	          {"cell":0,"value":"制表日期:"},
  				  {"cell":1,"value":date} ,
    	          
        	]}; 
    	//表尾
    	var foots = {
    			rows: [
    				{"cell":11,"value":"制表人:"},
    				{"cell":12,"value":"${sessionScope.user_name}"},
    			]
    		}; 
 		var printPara={
 				title: "资产入出库查询报表",//标题
 				columns: JSON.stringify(grid.getPrintColumns()),//表头
 				class_name: "com.chd.hrp.ass.service.tongJiReports.AssDepreExpireService",
 				method_name: "queryAssDepreExpirePrint",
 				bean_name: "assDepreExpireService" ,
 				heads: JSON.stringify(heads), //表头需要打印的查询条件,可以为空
 				foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 
 				};
 		
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
	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
		<tr>
			<td align="right" class="l-table-edit-td"  >查询期间：</td>
			<td align="left" class="l-table-edit-td"  >
				<table>
					<tr>
						<td><input class="Wdate" name="startDate" id="startDate" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
						<td>&nbsp;至&nbsp;</td>
						<td><input class="Wdate" name="endDate" id="endDate" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
					</tr>
				</table>
			</td>
			<td align="right" class="l-table-edit-td" >仓 库：</td>
			<td align="left" class="l-table-edit-td"  ><input name="store_id" type="text" id="store_id" ltype="text" validate="{required:false}" /></td>
			<td align="right" class="l-table-edit-td" >资产分类：</td>
			<td align="left" class="l-table-edit-td"  ><input name="ass_type_id" type="text" id="ass_type_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" >资产信息：</td>
			<td align="left" class="l-table-edit-td"  ><input name="ass_code" type="text" id="ass_code" ltype="text" validate="{required:false,maxlength:100}" /></td>
			<td align="right" class="l-table-edit-td">供应商：</td>
			<td align="left" class="l-table-edit-td"><input name="ven_id" type="text" id="ven_id" ltype="text" validate="{required:false}" /></td>
			<td align="right" class="l-table-edit-td">生产厂商：</td>
			<td align="left" class="l-table-edit-td"><input name="fac_id" type="text" id="fac_id" ltype="text" validate="{required:false}" /></td>		
		</tr>
	</table>

	<div id="maingrid"></div>
</body>
</html>
