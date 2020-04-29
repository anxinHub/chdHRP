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
<!-- 全院固定资产分布 -->
<script type="text/javascript">

    var grid;
    var gridManager = null;
    var searchType = {id:'sum'};
    
    $(function (){
        loadDict();//加载下拉框
        loadHotkeys();
    	loadHead(null);	//加载数据
    	
    	//在库checkbox
        $('input:checkbox').ligerCheckBox();
        
       // $("#show_store").change(function () {
        	query(searchType);
       // });
        
        $("#store_id").change(function () {
        	if(liger.get("store_id").getValue()){
        		//liger.get("show_store").setValue(true)
        	}else{
        		//liger.get("show_store").setValue(false)
        	}
        }); 
    });
    
    //查询
    function query(item){
    	searchType.id = item.id;
    	
   		grid.options.parms=[];
   		grid.options.newPage=1;
   		
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'ass_type_id',value:liger.get("ass_type_id").getValue()}); 
    	grid.options.parms.push({name:'ass_name',value:liger.get("ass_name").getValue()});
    	grid.options.parms.push({name:'use_dept_id',value:liger.get("use_dept_id").getValue().split("@")[0]});
    	grid.options.parms.push({name:'use_dept_no',value:liger.get("use_dept_id").getValue().split("@")[1]});
    	grid.options.parms.push({name:'store_id',value:liger.get("store_id").getValue().split("@")[0]});
    	grid.options.parms.push({name:'store_no',value:liger.get("store_id").getValue().split("@")[1]});
    	grid.options.parms.push({name:'use_state',value:liger.get("use_state").getValue()});
    	grid.options.parms.push({name:'is_measure',value:liger.get("is_measure").getValue()});
    	grid.options.parms.push({name:'ass_naturs',value:liger.get("ass_naturs").getValue()});
    	grid.options.parms.push({name:'price_beg',value:$('#price_beg').val()});
    	grid.options.parms.push({name:'price_end',value:$('#price_end').val()});
    	
    	grid.options.parms.push({name:'in_date_beg',value:$('#in_date_beg').val()});
    	grid.options.parms.push({name:'in_date_end',value:$('#in_date_end').val()});
    	grid.options.parms.push({name:'run_date_beg',value:$('#run_date_beg').val()});
    	grid.options.parms.push({name:'run_date_end',value:$('#run_date_end').val()});
    	grid.options.parms.push({name:'ass_card_no',value:$('#ass_card_no').val()});
    	grid.options.parms.push({name:'bar_code',value:$('#bar_code').val()});
    	grid.options.parms.push({name:'searchType',value:item.id});
    	grid.options.parms.push({name:'is_dept',value:liger.get("is_dept").getValue()});
     	 
    	//加载查询条件 
    	grid.loadData(grid.where);
    	loadHead();	
		$("#resultPrint > table > tbody").empty();
		
		switch(item.id)
		{
		case 'detail':
		  	detialQuery()
		  break;
		case 'check':
			checkQuery()
		  break;
		}
	}
    
    function detialQuery(){
    	var columns = [
            { display: '资产卡片号', name: 'ass_card_no', align: 'left', width: 120 },
            { display: '原始卡片号', name: 'ass_ori_card_no', align: 'left', width: 120 },
            { display: '资产编码', name: 'ass_code', align: 'left', width: '120'},
            { display: '资产名称', name: 'ass_name', align: 'left', width: '120'},
            { display: '资产分类', name: 'ass_type_name', align: 'left', width: '120'},
			{ display: '条形码', name: 'bar_code', align: 'left',width: '120' },
            { display: '规格', name: 'ass_spec', align: 'left', width: '120'},
            { display: '型号', name: 'ass_mondl', align: 'left', width: '120'},
            { display: '品牌', name: 'ass_brand', align: 'left', width: '120'},
            { display: '生产厂商', name: 'fac_name', align: 'left', width: '120'},
            { display: '计量单位', name: 'unit_name', align: 'left', width: '120'},
            { display: '原值', name: 'price', align: 'right',width: '120',
				 render : function(rowdata, rowindex,value) {
					return formatNumber(value,'${ass_05005}',1);
				 }
			},
			{ display: '累计折旧', name: 'depre_money', align: 'right',width: '120',
				 render : function(rowdata, rowindex,value) {
					return formatNumber(value,'${ass_05005}',1);
				 }
			},
			{ display: '净值', name: 'cur_money', align: 'right',width: '120',
				 render : function(rowdata, rowindex,value) {
					return formatNumber(value,'${ass_05005}',1);
				 }
			},
			{ display: '数量', name: 'ass_amount', align: 'left',width: '100'
			},
			{ display: '供应商', name: 'ven_name', align: 'left',width: '160'
			},
			{ display: '入库日期', name: 'in_date', align: 'left',width: '100'
			},
			{ display: '启用日期', name: 'run_date', align: 'left',width: '100'
			},
			{ display: '在用状态', name: 'is_dept', align: 'left',width: '100'
			},
			{ display: '所在部门', name: 'dept_name', align: 'left',width: '160'
			},
			{ display: '所在库房', name: 'store_name', align: 'left',width: '160'
			},
			{ display: '业务类型', name: 'bus_type_name', align: 'left',width: '160'
			},
			{ display: '序列号', name: 'ass_seq_no', align: 'left',width: '160'
			},
			{ display: '所在位置', name: 'location', align: 'left',width: '160'
			},
			{ display: '国标码', name: 'gb_name', align: 'left',width: '160'
			},
			{ display: '档案号', name: 'file_number', align: 'left',width: '160'
			},
			{ display: '使用状态', name: 'state_name', align: 'left',width: '160'
			},
			{ display: '使用状态', name: 'state_name', align: 'left',width: '160'
			},
			{ display: '出厂日期', name: 'production_date', align: 'left',width: '160'
			}
            ];
    	grid.set('columns', columns); 
    	grid.reRender();

    }
    
    function checkQuery(){
    	var columns = [
            { display: '使用部门', name: 'use_dept_code', align: 'left', width: 120 },
            { display: '归属部门', name: 'dept_name', align: 'left', width: 120},
            ];
    	grid.set('columns', columns); 
    	grid.reRender();
    }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '资产编码', name: 'ass_code', align: 'left',width: '120',frozen: true
                     },
                     { display: '资产名称', name: 'ass_name', align: 'left',width: '120',frozen: true
					 },
					 { display: '规格', name: 'ass_spec', align: 'left',width: '120'
					 },
					 { display: '型号', name: 'ass_mondl', align: 'left',width: '120'
					 },
					 { display: '品牌', name: 'ass_brand', align: 'left',width: '120'
					 },
					 { display: '计量单位', name: 'unit_name', align: 'left',width: '120'
					 },
					 { display: '单价', name: 'price', align: 'left',width: '120',
						 render : function(rowdata, rowindex,value) {
							return formatNumber(value,'${ass_05005}',1);
						 }
					 },
					 { display: '数量', name: 'ass_amount', align: 'left',width: '100'
					 },
					 { display: '原值', name: 'total_price', align: 'left',width: '120',
						 render : function(rowdata, rowindex,value) {
							return formatNumber(value,'${ass_05005}',1);
						 }
					 },
					 { display: '所在部门', name: 'dept_name', align: 'left',width: '160'
					 },
					 { display: '国标码', name: 'gb_name', align: 'left',width: '120'
					 },
					 { display: '财务分类名称', name: 'fina_type_name', align: 'left',width: '120'
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAssDistribution.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad :true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     tree:{columnId:'ass_type_code'},
                     toolbar: { 
                    	 items: [
                            { text: '汇总查询', id:'sum', click: query,icon:'search' },
                            { line:true },
                            { text: '明细查询', id:'detail', click: query,icon:'search' },
                            { line:true },
                            /* { text: '盘点汇总查询', id:'check', click: query,icon:'search' },
                            { line:true }, */
						    { text: '汇总打   印 （<u>P</u>）', id:'print', click: printDate,icon:'print' },
						    { line:true },
						    { text: '明细打   印 （<u>I</u>）', id:'print', click: printDatee,icon:'print' },
                         ]},
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    //字典下拉框
    function loadDict(){
    	autocomplete("#ass_type_id","../queryAssTypeDictIsLast.do?isCheck=false","id","text",true,true,null,null,null,"180");
    	autocomplete("#use_dept_id","../queryDeptDict.do?isCheck=false","id","text",true,true,null,null,null,"180");
    	autocomplete("#store_id","../queryHosStoreDict.do?isCheck=false","id","text",true,true,null,null,null,"180");
    	autocomplete("#use_state","../queryAssCardUseStateDict.do?isCheck=false","id","text",true,true,null,null,null,"180");
		
    	
    	$('#is_measure').ligerComboBox({
			data:[{id:1,text:'是'},{id:0,text:'否'}],
			valueField: 'id',
            textField: 'text',
            width:180,
			cancelable:true
		})
    	
		$("#ass_naturs").ligerComboBox({
			url : '../queryAssNaturs.do?isCheck=false',
			valueField : 'id',
			textField : 'text',
			selectBoxWidth : 180,
			autocomplete : true,
			width : 180,
			onSelected : function(value, text) {

			}
		});
    	
    	$("#ass_name,#ass_card_no,#bar_code").ligerTextBox({width:180});
    	$("#in_date_beg,#in_date_end,#run_date_beg,#run_date_end,#price_beg,#price_end").ligerTextBox({width : 80});
    	
    	
    	$('#is_dept').ligerComboBox({
			data:[{id:1,text:'在用'},{id:0,text:'在库'}],
			valueField: 'id',
            textField: 'text',
			cancelable:true,
			width : 180
		});
        
    } 
    //键盘事件
	function loadHotkeys() {
	}
    
	//打印数据
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
    	          {"cell":0,"value":"使用科室："},
    	          {"cell":1,"value":liger.get("use_dept_id").getText().split(" ")[1]},
    	          {"cell":10,"value":"制表日期:"},
  				  {"cell":11,"value":date} ,
    	          
        	]}; 
    	//表尾
    	var foots = {
    			rows: [
    				{"cell":10,"value":"制表人:"},
    				{"cell":11,"value":"${user_name}"},
    			]
    		}; 
 		var printPara={
 				title: "全院固定资产分布",//标题
 				columns: JSON.stringify(grid.getPrintColumns()),//表头
 				class_name: "com.chd.hrp.ass.service.assquery.AssDistributionService",
 				method_name: "queryAssbuttonPrint",
 				bean_name: "assDistributionService" ,
 				heads: JSON.stringify(heads), //表头需要打印的查询条件,可以为空
 				foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 
 				};
 		
 		$.each(grid.options.parms,function(i,obj){
 				printPara[obj.name]=obj.value;
 		});
 		
 		officeGridPrint(printPara);
 	   		
 	}
	  
	
 	//打印数据
 	function printDatee(){
 		if(grid.getData().length==0){
   			$.ligerDialog.error("请先查询数据！");
   			return;
   		}
 		
 		var time=new Date();
    	var date=time.getFullYear()+"年"+(time.getMonth()+1)+"月"+time.getDate()+"日";
    	var heads={
        		"isAuto":true,//系统默认，页眉显示页码
        		"rows": [
    	          {"cell":0,"value":"使用科室："},
    	          {"cell":3,"value":liger.get("use_dept_id").getText().split(" ")[1]},
    	          {"cell":20,"value":"制表日期:"},
  				  {"cell":21,"value":date} , 
    	          
        	]}; 
    	//表尾
    	var foots = {
    			rows: [
    				{"cell":20,"value":"制表人:"},
    				{"cell":21,"value":"${user_name}"},
    			]
    		}; 
    	//console.log(grid);
 		var printPara={
 				title: "全院固定资产分布",//标题
 				columns: JSON.stringify(grid.getPrintColumns()),//表头
 				class_name: "com.chd.hrp.ass.service.assquery.AssDistributionService",
 				method_name: "queryAssbuttonsPrint",
 				bean_name: "assDistributionService" ,
 				heads: JSON.stringify(heads), //表头需要打印的查询条件,可以为空
 				foots: JSON.stringify(foots),//表尾需要打印的查询条件,可以为空 
 				//page:grid.options.page,
 				//pageSize:grid.options.pageSize,
 				//total:grid.options.total,
 				//isPreview:true
 				
 				};
 		
 		$.each(grid.options.parms,function(i,obj){
 				printPara[obj.name]=obj.value;
 		});
 		//Print({
			//page:grid.options.pageCount,
 			//printPara:printPara,
 			//callBack:officeGridPrint
 		//});
 		officeGridPrint(printPara);
 	   		
 	} 

 	
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
     <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产分类：</td>
		    <td align="left" class="l-table-edit-td"><input name="ass_type_id" type="text" id="ass_type_id"  /></td>
		    <td align="left"></td>
		    <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产名称：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_name" type="text" id="ass_name"  /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">卡片号：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_card_no" type="text" id="ass_card_no"  /></td>
            <td align="left"></td>
        </tr>
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">使用科室：</td>
		    <td align="left" class="l-table-edit-td"><input name="use_dept_id" type="text" id="use_dept_id"  /></td>
		    <td align="left"></td>
		    <td align="right" class="l-table-edit-td"  style="padding-left:20px;">库&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;房：</td>
		    <td align="left" class="l-table-edit-td"><input name="store_id" type="text" id="store_id"  /></td>
		    <td align="left"></td>
		    <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否计量：</td>
		    <td align="left" class="l-table-edit-td"><input name="is_measure" type="text" id="is_measure"  /></td>
		    <td align="left"></td>
        </tr> 
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">使用状态：</td>
		    <td align="left" class="l-table-edit-td"><input name="use_state" type="text" id="use_state"  /></td>
		    <td align="left"></td>
		    <td align="right" class="l-table-edit-td"  style="padding-left:20px;" width="60">入库日期：</td>
            <td align="left" class="l-table-edit-td" >
	            <div style="float:left;">
	            	<input name="in_date_beg" class="Wdate" type="text" id="in_date_beg" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMMdd'})" />
	            </div>
	            <span style="float:left;margin: 0 3px;">至</span>
	            <div style="float:left;">
	            	<input name="in_date_end" class="Wdate" type="text" id="in_date_end" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMMdd'})" />
				</div>
			</td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;" width="60">启用日期：</td>
            <td align="left" class="l-table-edit-td" >
	            <div style="float:left;">
	            	<input name="run_date_beg" class="Wdate" type="text" id="run_date_beg" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMMdd'})" />
	            </div>
	            <span style="float:left;margin: 0 3px;">至</span>
	            <div style="float:left;">
	            	<input name="run_date_end" class="Wdate" type="text" id="run_date_end" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMMdd'})" />
				</div>
			</td>
			<td align="left"></td>
        </tr>
         
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;" width="60">资产性质：</td>
        	<td align="left" class="l-table-edit-td">
                <input name="ass_naturs" type="text" id="ass_naturs"   />
            </td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">条形码：</td>
            <td align="left" class="l-table-edit-td"><input name="bar_code" type="text" id="bar_code"  /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">在用状态：</td>
            <td align="left" class="l-table-edit-td"><input name="is_dept" type="text" id="is_dept"  /></td>
            <td align="left"></td>
        </tr>
        <tr>
        	<td align="right" class="l-table-edit-td" style="padding-left:20px;" width="60">金额：</td>
            <td align="left" class="l-table-edit-td" >
            	<div style="float:left;">
	            	<input name="price_beg" type="text" id="price_beg" />
	            </div>
	            <span style="float:left;margin: 0 3px;">至  </span>
	            <div style="float:left;">
	            	<input name="price_end"  type="text" id="price_end"  />
				</div>
            </td>
        </tr>
       
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
