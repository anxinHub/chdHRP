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
<script src="<%=path%>/lib/hrp/mat/mat.js"	type="text/javascript"></script>
<script type="text/javascript">
	var time = new Date(); //获得当前时间
	var year = time.getFullYear();//获得年、月、日
	var month = time.getMonth()+1;
	var day = time.getDate(); 
	var date = year+"年"+month+"月"+day;
    var grid;
    var gridManager = null;
    var userUpdateStr;
    
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
		loadHotkeys();
    });
    //查询
    function  query(){
   		grid.options.parms=[];
		grid.options.newPage=1;
        //根据表字段进行添加查询条件
        grid.options.parms.push({name:'ass_type_id',value:liger.get("ass_type_id").getValue()}); 
    	grid.options.parms.push({name:'ass_name',value:liger.get("ass_name").getValue()});
    	grid.options.parms.push({name:'use_dept_id',value:liger.get("use_dept_id").getValue().split("@")[0]});
    	grid.options.parms.push({name:'store_id',value:liger.get("store_id").getValue().split("@")[0]});
    	grid.options.parms.push({name:'is_measure',value:liger.get("is_measure").getValue()});
    	grid.options.parms.push({name:'ass_naturs',value:liger.get("ass_naturs").getValue()});
    	grid.options.parms.push({name:'price_beg',value:$('#price_beg').val()});
    	grid.options.parms.push({name:'price_end',value:$('#price_end').val()});
    	
    	grid.options.parms.push({name:'in_date_beg',value:$('#in_date_beg').val()});
    	grid.options.parms.push({name:'in_date_end',value:$('#in_date_end').val()});
    	grid.options.parms.push({name:'run_date_beg',value:$('#run_date_beg').val()});
    	grid.options.parms.push({name:'run_date_end',value:$('#run_date_end').val()});
    	grid.options.parms.push({name:'ass_in_no',value:$('#ass_in_no').val()});
    	grid.options.parms.push({name : 'ven_id',value : liger.get("ven_id").getValue().split("@")[0]});
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
				columns: [{ 
			 				display: '供应商编码', name: 'sup_code', align: 'left', minWidth: '150'
				 		}, { 
				 			display: '供应商名称', name: 'sup_name', align: 'left', minWidth: '80'
				 		}, { 
				 			display: '生产厂商编码', name: 'fac_code', align: 'left', minWidth: '80'
				 		}, { 
				 			display: '生产厂商名称', name: 'fac_name', align: 'left', minWidth: '80'
				 		},  { 
				 			display: '入库日期', name: 'in_date', align: 'left', minWidth: '80'
				 		},  { 
				 			display: '资产编码', name: 'ass_code', align: 'left', minWidth: '80'
				 		},  { 
				 			display: '资产名称', name: 'ass_name', align: 'left', minWidth: '80'
				 		},  { 
				 			display: '规格型号', name: 'ass_spec', align: 'left', minWidth: '80'
				 		},  { 
				 			display: '计量单位', name: 'unit_name', align: 'left', minWidth: '80'
				 		},  { 
				 			display: '进价', name: 'price', align: 'left', minWidth: '80'
				 			
				 		},  { 
				 			display: '数量', name: 'ass_amount', align: 'left', minWidth: '80'
				 		},  { 
				 			display: '金额', name: 'sum_price', align: 'right', minWidth: '100',
				 			render : function(rowdata, rowindex,value) {
								return formatNumber(value,'${ass_05005 }',1);
							 }
				 		},{ 
				 			display: '资产分类', name: 'ass_type_name', align: 'left', minWidth: '90'
				 		}],
					dataAction: 'server',dataType: 'server',usePager:true,url:'queryAssSupplier.do',
					width: '100%', height: '100%',rownumbers:true,
					delayLoad: true,//初始化不加载，默认false
					selectRowButtonOnly:true,//heightDiff: -10,
					toolbar: { items: [
						       			{ text: '查询（<u>Q</u>）', id:'search', click: query, icon:'search' },
										{ line:true },
										{ text: '打印', id:'print', click: printDate, icon:'print' },
						   				{ line:true }
					]}
				});
	
	        gridManager = $("#maingrid").ligerGetGridManager();
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
    	          {"cell":0,"value":"报表日期:"},
  				  {"cell":1,"value":date} ,
    	          
        	]}; 
    	//表尾
    	var foots = {
    			rows: [
    				{"cell":3,"value":"制表人:"},
    				{"cell":4,"value":"${sessionScope.user_name}"},
    			]
    		}; 
 		var printPara={
 				title: "供应商采购明细表",//标题
 				columns: JSON.stringify(grid.getPrintColumns()),//表头
 				class_name: "com.chd.hrp.ass.service.tongJiReports.AssSupplierService",
 				method_name: "queryAssSupplierPrint",
 				bean_name: "assSupplierService" ,
 				heads: JSON.stringify(heads), //表头需要打印的查询条件,可以为空
 				foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 
 				};
 		
 		$.each(grid.options.parms,function(i,obj){
 				printPara[obj.name]=obj.value;
 		});
 		
 		officeGridPrint(printPara);
 	   		
 	}
   
    function loadDict(){
    	autocomplete("#ass_type_id","../queryAssTypeDictIsLast.do?isCheck=false","id","text",true,true,null,null,null,"180");
    	autocomplete("#use_dept_id","../queryDeptDict.do?isCheck=false","id","text",true,true,null,null,null,"180");
    	autocomplete("#store_id","../queryHosStoreDict.do?isCheck=false","id","text",true,true,null,null,null,"180");
    	autocomplete("#use_state","../queryAssCardUseStateDict.do?isCheck=false","id","text",true,true,null,null,null,"180");
    	autocomplete("#ven_id", "../queryHosSupDictNo.do?isCheck=false","id", "text",true,true,null,false,null,"180");
    	
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
    	
    	$("#ass_name,#ass_in_no").ligerTextBox({width:180});
    	$("#in_date_beg,#in_date_end,#run_date_beg,#run_date_end,#price_beg,#price_end").ligerTextBox({width : 80});
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
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">入库单号：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_in_no" type="text" id="ass_in_no"  /></td>
            <td align="left"></td>
        </tr>
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室：</td>
		    <td align="left" class="l-table-edit-td"><input name="use_dept_id" type="text" id="use_dept_id"  /></td>
		    <td align="left"></td>
		    <td align="right" class="l-table-edit-td"  style="padding-left:20px;">库房：</td>
		    <td align="left" class="l-table-edit-td"><input name="store_id" type="text" id="store_id"  /></td>
		    <td align="left"></td>
		    <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否计量：</td>
		    <td align="left" class="l-table-edit-td"><input name="is_measure" type="text" id="is_measure"  /></td>
		    <td align="left"></td>
        </tr> 
        <tr>
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
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;" width="60">制单日期：</td>
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
			<td align="right" class="l-table-edit-td" style="padding-left:20px;" width="60">单价：</td>
            <td align="left" class="l-table-edit-td" >
            	<div style="float:left;">
	            	<input name="price_beg" type="text" id="price_beg" />
	            </div>
	            <span style="float:left;margin: 0 3px;">至  </span>
	            <div style="float:left;">
	            	<input name="price_end"  type="text" id="price_end"  />
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
            <td align="right"  class="l-table-edit-td" style="padding-left: 20px;">供应商：</td>
			<td align="left"  class="l-table-edit-td"><input name="ven_id" type="text" id="ven_id" /></td>		
        </tr>
       
    </table>
	<div id="maingrid"></div>
</body>
</html>
