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
<script src="<%=path%>/lib/hrp/med/med.js"	type="text/javascript"></script>
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    var renderFunc = {
			price:function(value){//单价
				return formatNumber(value, '${p08006 }', 1);
			},
			amount_money:function(value){//金额
				return formatNumber(value, '${p08005 }', 1);
			},
			amount:function(value){//数量
				return formatNumber(value, '${p08005 }', 1);
			}
	}; 
    
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
		 loadHotkeys();
		 
		 $("#set_code").bind("change",function(){
		    	if(liger.get("set_code").getValue()){
		    		liger.get("store_code").setValue("");
					liger.get("store_code").setText("");
		   	 		$("#store_code").ligerComboBox({disabled:true});
		   	 		grid.toggleCol('02', true);
		    	}else{
		    		$("#store_code").ligerComboBox({disabled:false});
		    		grid.toggleCol('02', false);
		    	}
		    	
			});
			$("#store_code").bind("change",function(){
		    	if(liger.get("store_code").getValue()){
		    		liger.get("set_code").setValue("");
					liger.get("set_code").setText("");
		   	 		$("#set_code").ligerComboBox({disabled:true});
		   	 		grid.toggleCol('03', true);
		    	}else{
		    		$("#set_code").ligerComboBox({disabled:false});
		    		grid.toggleCol('03', false);
		    	}
		    	
			});
		 
		 query();
    });
    //查询
    function  query(){
		grid.options.parms=[];
		grid.options.newPage=1;
        //根据表字段进行添加查询条件
        
        var  begin_date = $("#begin_date").val();
        var  end_date = $("#end_date").val();
        
        if( begin_date == '' ){
        	$.ligerDialog.warn('开始期间不能为空 ');
        	return ; 
        }
        
        if( end_date == '' ){
        	$.ligerDialog.warn('结束期间不能为空 ');
        	return ; 
        }
        
		grid.options.parms.push({
			name : 'begin_date',
			value : begin_date
		});
		grid.options.parms.push({
			name : 'end_date',
			value : end_date
		}); 
		grid.options.parms.push({
			name : 'store_id',
			value : liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[0]
		}); 
	/* 	grid.options.parms.push({
			name : 'med_type_id',
			value : liger.get("med_type_code").getValue() == null ? "" : liger.get("med_type_code").getValue()
		}); */
		grid.options.parms.push({name : 'med_type_code',value : liger.get("med_type_code").getText().split(" ")[0]});
		grid.options.parms.push({
			name : 'set_id',
			value : liger.get("set_code").getValue() == null ? "" : liger.get("set_code").getValue()
		}); 
		
		grid.options.parms.push({
			name : 'inv_id',
			value : liger.get("inv_code").getValue() == null ? "" : liger.get("inv_code").getValue().split(",")[0]
		}); 
		
		grid.options.parms.push({
			name : 'is_charge',
			value : liger.get("is_charge").getValue() == null ? "" : liger.get("is_charge").getValue()
		}); 
		grid.options.parms.push({
			name : 'inv_model',//规格型号
			value : $("#inv_model").val()
		});

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [{
				display: '虚仓名称', name: 'set_name', align: 'left', minWidth: '150', frozen: true
				}, { 
		 			display: '仓库名称', name: 'store_name', align: 'left', minWidth: '100', frozen: true
		 		},{
					display: '药品类别', name: 'med_type_name', textField: 'med_type_name', align: 'left', minWidth: '150', frozen: true
				}, { 
		 			display: '药品编码', name: 'inv_code', align: 'left', minWidth: '100', frozen: true
		 		}, { 
		 			display: '药品名称', name: 'inv_name', align: 'left', minWidth: '150', frozen: true
		 		}, { 
		 			display: '规格型号', name: 'inv_model', align: 'left', minWidth: '80'
		 		}, { 
		 			display: '计量单位', name: 'unit_name', align: 'left', minWidth: '80'
		 		}, { 
		 			display: '数量', name: 'amount', align: 'left', minWidth: '100'
		 		}, { 
		 			display: '单价', name: 'price', align: 'right', minWidth: '100',formatter:"###,##0.00",
		 			render : function(rowdata, rowindex, value) {
						return value ==null ? "" : formatNumber(value, '${p08006 }', 1);
					}
		 		}, { 
		 			display: '金额', name: 'amount_money', align: 'right', minWidth: '100',formatter:"###,##0.00",
		 			render : function(rowdata, rowindex, value) {
						return value ==null ? "" : formatNumber(value, '${p08005 }', 1);
					}
		 		}],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMedAccountReportDeptOut.do',
			width: '100%', height: '98%',rownumbers:true,
			delayLoad: true,//初始化不加载，默认false
			selectRowButtonOnly:true,//heightDiff: -10,
			//alternatingRow: false, //不知道什么意思，有待测试
			tree: { columnId: 'med_type_code' }, 
			toolbar: { items: [
				{ text: '查询（<u>Q</u>）', id:'search', click: query, icon:'search' },
				{ line:true },
				{ text: '打印', id:'print', click: print, icon:'print' },
				{ line:true }
			]}
		});
		console.log(324567,grid);
        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    //打印回调方法
    function lodopPrint(){
    	var head="<table class='head' width='100%'><tr><td>单位：${sessionScope.hos_name}</td></tr>";
 		head=head+"<tr><td>确认日期："+$("#begin_date").val()+"至"+$("#end_date").val()+"</td></tr>";
 		head=head+"</table>";
 		grid.options.lodop.head=head; 
 		grid.options.lodop.fn=renderFunc;
 		grid.options.lodop.title="出库明细汇总表";
    }
    
    //键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
		hotkeys('P', print);
	}
    
    function loadDict(){
		//字典下拉框
		autocomplete("#store_code", "../../queryMedStoreByRead.do?isCheck=false", "id", "text", true, true);
		autocomplete("#med_type_code", "../../queryMedType.do?isCheck=false", "id", "text", true, true,'',false,'',280);
		autocomplete("#set_code", "../../queryMedVirStore.do?isCheck=false", "id", "text", true, true);
		autocomplete("#is_charge", "../../queryMedYearOrNo.do?isCheck=false", "id", "text", true, true);
	//	autocomplete("#inv_code", "../../queryMedInvDict.do?isCheck=false", "id", "text", true, true);
        $("#begin_date").ligerTextBox({width:100});
        autodate("#begin_date", "yyyy-mm-dd", "month_first");
        $("#end_date").ligerTextBox({width:100});
        autodate("#end_date", "yyyy-mm-dd", "month_last");
        $("#inv_model").ligerTextBox({width:240});
        $("#inv_code").ligerTextBox({width:240});
        $("#med_type_code").ligerTextBox({width:240});
	}
    
  	//打印
	function print(){
    	
    	if(grid.getData().length==0){
    		
			$.ligerDialog.error("请先查询数据！");
			
			return;
		}
    	
    	var selPara={};
    	
    	$.each(grid.options.parms,function(i,obj){
    		
    		selPara[obj.name]=obj.value;
    		
    	});
   		
		var dates = getCurrentDate();
    	
    	var cur_date = dates.split(";")[2];
    	//跨所有列:计算列数
    	var colspan_num = grid.getColumns(1).length-1;
    	
   		var printPara={
   			title:'出库明细汇总表',
   			head:[
				{"cell":0,"value":"单位: ${sessionScope.hos_name}","colspan":colspan_num,"br":true},
				{"cell":0,"value":"统计日期: " + $("#begin_date").val() +" 至  "+ $("#end_date").val(),"colspan":colspan_num,"br":true}
   			],
   			foot:[
				{"cell":0,"value":"主管:","colspan":3,"br":false} ,
				{"cell":3,"value":"复核人:","colspan":3,"br":false},
				{"cell":colspan_num-2,"value":"制单人： ${sessionScope.user_name}","colspan":2,"br":true},
				{"cell":0,"value":"打印日期: " + cur_date,"colspan":colspan_num,"br":true}
   			],
   			columns:grid.getColumns(1),
   			headCount:1,//列头行数
   			autoFile:true,
   			type:3
   		};
   		console.log(123,printPara);
   		ajaxJsonObjectByUrl("queryMedAccountReportDeptOut.do?isCheck=false", selPara, function (responseData) {
   			console.log(345,responseData);
   			printGridView(responseData,printPara);
		});

   		
    }
	
	</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" >
        <tr>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  width="10%">
            		<span style="color: red; size: 2;">*</span>确认日期：
            </td>
            <td align="left" class="l-table-edit-td"  width="20%">
				<table>
					<tr>
						<td align="left" class="l-table-edit-td">
							<input class="Wdate" name="begin_date" id="begin_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
						<td align="right" class="l-table-edit-td"  >
							至：
						</td>
						<td align="left" class="l-table-edit-td">
							<input class="Wdate" name="end_date" id="end_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
            		</tr>
				</table>
	        </td>
	        
	        <td align="right" class="l-table-edit-td"  width="10%">
				虚仓名称：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="set_code" type="text" id="set_code" ltype="text" />
            </td>
	        
			<td align="right" class="l-table-edit-td"  width="10%">
				仓库：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="store_code" type="text" id="store_code" ltype="text" required="true" validate="{required:false}" />
            </td>
			
        </tr> 
        
        <tr>
        	<td align="right" class="l-table-edit-td"  width="10%">
				药品类别：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="med_type_code" type="text" id="med_type_code" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
            
            <td align="right" class="l-table-edit-td"  width="10%">
				药品信息：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="inv_code" type="text" id="inv_code" ltype="text" />
            </td>
            
            <td align="right" class="l-table-edit-td"  width="10%">
				是否收费：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="is_charge" type="text" id="is_charge" ltype="text" />
            </td>
        </tr>
         <tr>
       <td align="right" class="l-table-edit-td"  width="10%">
		规格型号:
		</td>
		  <td align="left" class="l-table-edit-td" width="20%">
			<input name="inv_model" type="text" id="inv_model" ltype="text"    validate="{required:true}" />
		  </td>
		
     </tr>
		
    </table>
	<div id="maingrid"></div>
</body>
</html>
