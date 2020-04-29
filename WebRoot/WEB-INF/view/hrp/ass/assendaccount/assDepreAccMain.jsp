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
<!-- 计提折旧 -->
<script type="text/javascript">

    var grid;
    var gridManager = null;
    
    $(function (){
        loadDict();//加载下拉框
        loadHotkeys();
    	loadHead(null);	//加载数据
    	
    });
    
    //查询
    function query(){
    	
   		grid.options.parms=[];
   		grid.options.newPage=1;
   		
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'ass_type_id',value:liger.get("ass_type_id").getValue()}); 
    	grid.options.parms.push({name:'ass_name',value:liger.get("ass_name").getValue()});
    	grid.options.parms.push({name:'ass_card_no',value:liger.get("ass_card_no").getValue()});
    	grid.options.parms.push({name:'use_dept_id',value:liger.get("use_dept_id").getValue().split("@")[0]});
    	grid.options.parms.push({name:'use_dept_no',value:liger.get("use_dept_id").getValue().split("@")[1]});
    	//grid.options.parms.push({name:'dept_id',value:liger.get("dept_id").getValue().split("@")[0]});
    	//grid.options.parms.push({name:'dept_no',value:liger.get("dept_id").getValue().split("@")[1]});
    	
    	grid.options.parms.push({name:'source_id',value:liger.get("source_id").getValue()});
    	
    	grid.options.parms.push({name:'depre_year_month',value:$('#depre_year_month').val()});
 	    	
    	//加载查询条件
    	grid.loadData(grid.where);
    	loadHead();	
		$("#resultPrint > table > tbody").empty();
		
	}
    
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
	        	     { display: '计提年月', name: 'depre_year_month', align: 'left',width: '120'
	                 },
	                 { display: '所在部门', name: 'use_dept_name', align: 'left',width: '120'
	                 },
	        	     { display: '资产卡片号', name: 'ass_card_no', align: 'left',width: '120'
	                 },
                     { display: '资产编码', name: 'ass_code', align: 'left',width: '120'
                     },
                     { display: '资产名称', name: 'ass_name', align: 'left',width: '120'
					 },
					 { display: '资产分类', name: 'ass_type_name', align: 'left', width: '120'
				     },
					 { display: '规格', name: 'ass_spec', align: 'left',width: '120'
					 },
					 { display: '型号', name: 'ass_mondl', align: 'left',width: '120'
					 },
					 { display: '品牌', name: 'ass_brand', align: 'left',width: '120'
					 },
					 { display: '入库日期', name: 'in_date', align: 'left',width: '100'
					 },
					 { display: '资产原值', name: 'prim_money', align: 'left',width: '120',
						 render : function(rowdata, rowindex,value) {
							return formatNumber(value,'${ass_05005}',1);
						 }
					 },
					 { display: '本月计提', name: 'now_depre_amount', align: 'left',width: '120',
						 render : function(rowdata, rowindex,value) {
								return formatNumber(value,'${ass_05005}',1);
							 }
					 },
					 { display: '累计折旧', name: 'add_depre_amount', align: 'left',width: '120',
						 render : function(rowdata, rowindex,value) {
								return formatNumber(value,'${ass_05005}',1);
							 }
					 },
					 { display: '资金来源', name: 'source_name', align: 'left',width: '100'
					 },
					 { display: '处置回冲', name: 'dispose_income', align: 'left',width: '100',
						 render : function(rowdata, rowindex,value) {
								return formatNumber(value,'${ass_05005}',1);
							 }
					 },
					 { display: '退货回冲', name: 'back_price', align: 'left',width: '100',
						 render : function(rowdata, rowindex,value) {
								return formatNumber(value,'${ass_05005}',1);
							 }
					 },
					 { display: '原值计提', name: 'change_price', align: 'left',width: '100',
						 render : function(rowdata, rowindex,value) {
								return formatNumber(value,'${ass_05005}',1);
							 }
					 },
					 { display: '累计折旧调整', name: 'change_depre', align: 'left',width: '100',
						 render : function(rowdata, rowindex,value) {
								return formatNumber(value,'${ass_05005}',1);
							 }
					 },					 
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAssDepreAccInfo.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad :true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     tree:{columnId:'ass_type_code'},
                     toolbar: { 
                    	 items: [
                            { text: '查询', id:'sum', click: query,icon:'search' },
                            { line:true },
						    { text: '打   印 （<u>P</u>）', id:'print', click: printDate,icon:'print' }
                         ]},
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    //字典下拉框
    function loadDict(){
    	autocomplete("#ass_type_id","../queryAssTypeDictIsLast.do?isCheck=false","id","text",true,true,null,null,null,"180");
    	autocomplete("#use_dept_id","../queryDeptDict.do?isCheck=false","id","text",true,true,null,null,null,"180");
    	//autocomplete("#dept_id","../queryDeptDict.do?isCheck=false","id","text",true,true,null,null,null,"180");
    	autocomplete("#source_id","../querySourceDict.do?isCheck=false","id","text",true,true,null,null,null,"180");
    	
    	$('#is_measure').ligerComboBox({
			data:[{id:1,text:'是'},{id:0,text:'否'}],
			valueField: 'id',
            textField: 'text',
            width:180,
			cancelable:true
		})
    	
    	$("#ass_name,#ass_card_no").ligerTextBox({width:180});
    	$("#depre_year_month").ligerTextBox({width : 180});//提取日期
        
    } 
    //键盘事件
	function loadHotkeys() {
	}
    
	function printDate(){
		if(grid.getData().length==0){
   			$.ligerDialog.error("请先查询数据！");
   			return;
   		}
 		
 		var time=new Date();
 		var date=$("#depre_year_month").val();
    	var heads={
        		"isAuto":true,//系统默认，页眉显示页码
        		"rows": [
    	          {"cell":0,"value":"会计期间："},
    	          {"cell":1,"value":date},
    	          
        	]}; 
    	//表尾
    	var foots = {
    			rows: [
    				{"cell":10,"value":"制表人:"},
    				{"cell":11,"value":"${user_name}"},
    			]
    		}; 
 		var printPara={
 				title: "资产计提折旧",//标题
 				columns: JSON.stringify(grid.getPrintColumns()),//表头
 				class_name: "com.chd.hrp.ass.service.guanLiReports.AssPropertyMonthMainService",
 				method_name: "queryAssPropertyMonthMainPrint",
 				bean_name: "assPropertyMonthMainService" ,
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
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;" width="60">提取年月：</td>
            <td align="left" class="l-table-edit-td" >
	          <input name="depre_year_month" class="Wdate" type="text" id="depre_year_month" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})" />
			</td>
			<td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产分类：</td>
		    <td align="left" class="l-table-edit-td"><input name="ass_type_id" type="text" id="ass_type_id"  /></td>
		    <td align="left"></td>
		    <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产名称：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_name" type="text" id="ass_name"  /></td>
            <td align="left"></td>
          
        </tr>
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">资金来源：</td>
		    <td align="left" class="l-table-edit-td"><input name="source_id" type="text" id="source_id"  /></td>
		    <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">使用科室：</td>
		    <td align="left" class="l-table-edit-td"><input name="use_dept_id" type="text" id="use_dept_id"  /></td>
		    <td align="left"></td>
		     <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产卡片号：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_card_no" type="text" id="ass_card_no"  /></td>
            <td align="left"></td>
        </tr> 
        <!-- 
        <tr>
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产归属：</td>
		    <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id"  /></td>
		    <td align="left"></td>
        </tr>
        -->
    </table>

	<div id="maingrid"></div>
	<div id="resultPrint" style="display:none">
	   	<table width="100%">
			<thead>
		 
			<tr>
				<th width="100" colspan="1" rowspan="2">类别编码</th>	
                <th width="100" colspan="1" rowspan="2">类别名称</th>	
                
                <th width="400" colspan="4" rowspan="1">原值</th> 
                <th width="400" colspan="4" rowspan="1">累计折旧</th>	 
                <th width="400" colspan="2" rowspan="1">净值</th> 
			</tr>
			<tr>
				<th width="100" >期初</th>	
                <th width="100" >借方</th>	
                <th width="100" >贷方</th>	
                <th width="100" >余额</th>
                <th width="200" >期初</th>	
                <th width="200" >借方</th>	
                <th width="200" >贷方</th>	
                <th width="200" >余额</th>
                <th width="200" >期初</th>	
                <th width="200" >期末</th>	
			</tr>
			   	</thead>
			   	<tbody></tbody>
	   	</table>
   	</div>
</body>
</html>
