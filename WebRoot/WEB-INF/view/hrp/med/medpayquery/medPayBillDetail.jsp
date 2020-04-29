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

    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
    });
    //查询
    function  query(){
		grid.options.parms=[];
		grid.options.newPage=1;
        //根据表字段进行添加查询条件
       	grid.options.parms.push({
			name : 'begin_date',
			value : $('#begin_date').val()
		}); 
        
       	grid.options.parms.push({
			name : 'end_date',
			value : $('#end_date').val()
		}); 
        
		grid.options.parms.push({
			name : 'sup_id',
			value : liger.get("sup_code").getValue() == null ? "" : liger.get("sup_code").getValue().split(",")[0]
		});
		
		grid.options.parms.push({
			name : 'bill_no',
			value : $('#bill_no').val()
		}); 
		
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [{ 
		 			display: '供货单位', name: 'sup_name', align: 'left', minWidth: '30%'
		 		},  { 
		 			display: '金额', name: 'bill_money', align: 'right', width: '15%',
		 			render : function(rowdata, rowindex, value) {
						return formatNumber(rowdata.bill_money ==null ? 0 : rowdata.bill_money, '${p08005 }', 1);
					}
		 		},  { 
		 			display: '发票号', name: 'bill_no', align: 'left', width: '40%'
		 		},  { 
		 			display: '发票张数', name: 'countnum', align: 'right', width: '10%'
		 		}
		 		],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMedPayBillDetail.do',
			width: '100%', height: '100%', rownumbers:true,
			delayLoad: true,//初始化不加载，默认false
			selectRowButtonOnly:true,//heightDiff: -10,
			toolbar: { items: [
				{ text: '查询（<u>Q</u>）', id:'search', click: query, icon:'search' },
				{ line:true },
			    { text: '打印', id:'print', click: print, icon:'print' },
				{ line:true }
			]}, 
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    //打印回调方法
    function lodopPrint(){
    	var head="<table class='head' width='100%'><tr><td>单位：${sessionScope.hos_name}</td></tr>";
 		head=head+"<tr><td>开票日期："+$("#begin_date").val() +" 至  "+ $("#end_date").val()+"</td></tr>";
 		head=head+"</table>";
 		grid.options.lodop.head=head; 
 		grid.options.lodop.title="发票报表";
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
    			title:'发票报表',
    			head:[
 				{"cell":0,"value":"单位: ${sessionScope.hos_name}","colspan":colspan_num,"br":true},
 				{"cell":0,"value":"统计日期: " + $("#begin_date").val() +" 至  "+ $("#end_date").val(),"colspan":colspan_num,"br":true}
    			],
    			foot:[
 				{"cell":0,"value":"主管:","colspan":3,"br":false} ,
 				{"cell":3,"value":"复核人:","colspan":colspan_num-5,"br":false},
 				{"cell":colspan_num-2,"value":"制单人： ${sessionScope.user_name}","colspan":2,"br":true},
 				{"cell":0,"value":"打印日期: " + cur_date,"colspan":colspan_num,"br":true}
    			],
    			columns:grid.getColumns(1),
    			headCount:1,//列头行数
    			autoFile:true,
    			type:3
    		};
    		ajaxJsonObjectByUrl("queryMedPayBillDetail.do?isCheck=false", selPara, function (responseData) {
    			printGridView(responseData,printPara);
 		});

    		
     }
	 
	//字典下拉框
    function loadDict(){
    	autodate("#begin_date", "yyyy-mm-dd", "month_first");
        autodate("#end_date", "yyyy-mm-dd", "month_last");
  	  	$("#begin_date").ligerTextBox({width:100});
      	$("#end_date").ligerTextBox({width:100});
		//供应商
		autocomplete("#sup_code", "../queryHosSupDict.do?isCheck=false", "id", "text", true, true,'',false,'',280);
		$("#bill_no").ligerTextBox({width:180});
	}
	
	
	</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar" ></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
        <tr>
        	<td align="right" class="l-table-edit-td"  width="10%">
            	开票日期：
            </td>
            <td align="left" class="l-table-edit-td"  width="20%">
				<table>
					<tr>
						<td>
							<input class="Wdate" name="begin_date" id="begin_date" type="text" onFocus="WdatePicker({isShowClear:false,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
						<td align="right" class="l-table-edit-td"  >
							至
						</td>
						<td>
							<input class="Wdate" name="end_date" id="end_date" type="text" onFocus="WdatePicker({isShowClear:false,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
            		</tr>
				</table>
	        </td>
	        
			<td align="right" class="l-table-edit-td" width="10%">
				供应商：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="sup_code" type="text" id="sup_code" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
            
            <td align="right" class="l-table-edit-td" width="10%" >
				发&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;票：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="bill_no" type="text" id="bill_no" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
        </tr> 
        
    </table>
	<div id="maingrid"></div>
</body>
</html>
