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
	var paraMoney = '${p04005}';
	var paraPrice = '${p04006}';
	
    var renderFunc = {

			price: function(value){//单价
				return formatNumber(value, paraPrice, 1); 
			},
			begin_amount: function(value){//期初数量
				return formatNumber(value, 2, 1); 
			},
			begin_money: function(value){//期初金额
				return formatNumber(value, paraMoney, 1);
			}, 
			in_amount: function(value){//移入数量
	   			 return formatNumber(value, 2, 1); 
			},
			in_money: function(value){//移入金额
				return formatNumber(value, paraMoney, 1);
			}, 
			out_amount: function(value){//移出数量
		   		return formatNumber(value, 2, 1); 
			},
			out_money: function(value){//移出金额
				return formatNumber(value, paraMoney, 1);
			}, 
			end_amount: function(value){//期末数量
   				return formatNumber(value, 2, 1); 
			},
			end_money: function(value){//期末金额
				return formatNumber(value, paraMoney, 1);
			} 
	};
    
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
		// query();
    });
    //查询
    function  query(){
    	/* $("#year_month_span").text(liger.get("year").getValue()+"年"+
    			liger.get("month").getValue()+"月"); */
    	
		grid.options.parms=[];
		grid.options.newPage=1;
        //根据表字段进行添加查询条件
	
		/* grid.options.parms.push({
			name : 'store_id',
			value : liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[0]
		}); 
		 */
		grid.options.parms.push({
			name : 'inv_code',
			value : $("#inv_code").val()
		}); 
		grid.options.parms.push({name: 'begin_make_date', value: $("#begin_make_date").val()}); 
		grid.options.parms.push({name: 'end_make_date', value: $("#end_make_date").val()}); 
		grid.options.parms.push({
			name : 'dept_id',
			value : liger.get("dept_code").getValue() == null ? "" : liger.get("dept_code").getValue().split(",")[0]
		}); 
		grid.options.parms.push({name: 'state', value: liger.get("state").getValue()}); 
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [ {
					display: '制单日期', name: 'make_date', align: 'left', width: '80',
						},
			{
					display: '单据号', name: 'scrap_no', align: 'left', width: '80',	
					render : function(rowdata, rowindex, value) {
						return '<a href=javascript:openUpdate("' 
								 + rowdata.scrap_id
								+ '")>'+rowdata.scrap_no+'</a>';
					}	
					
			}, {
					display: '科室', name: 'dept_name', align: 'left', width: '80',
			},
			        /* {
					display: '库房编码', name: 'store_code', align: 'left', width: '80',
				}, { 
					display: '库房名称', name: 'store_name', align: 'left', width: '100',
				}, */ { 
					display: '材料编码', name: 'inv_code', align: 'left', width: '100',
				}, { 
					display: '材料名称', name: 'inv_name', align: 'left', width: '180',
				},/*  { 
					display: '规格型号', name: 'inv_model', align: 'left', width: '140',
				},  */ 
				{ 
					
					display: '单价', name: 'price', align: 'left', width: '80',
		 			render : function(rowdata, rowindex, value) {
						return value ==null ? "" : formatNumber(value, paraPrice, 1);
					}
				}, { 
		 			display: '数量', name: 'amount', align: 'right', width: '80',
		 			render : function(rowdata, rowindex, value) {
						return value ==null ? "" : formatNumber(value, 2, 1);
					}
		 		}, { 
		 			display: '金额', name: 'money', align: 'right', width: '100',
		 			render : function(rowdata, rowindex, value) {
						return value ==null ? "" : formatNumber(value, paraMoney, 1);
					}
		 		}],
			dataAction: 'server', dataType: 'server', usePager: true, url: 'queryMatDuraQueryScrapDetail.do',
			width: '100%', height: '97%', checkbox: false, rownumbers: true,
			delayLoad: true,//初始化不加载，默认false
			selectRowButtonOnly: true//heightDiff: -10,
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
   
    function loadDict(){
		//字典下拉框
    	//返回当前年,当前月,
		var date = getCurrentDate();
        var aa = date.split(';');
        year = aa[0];
        month = aa[1];
		//返回当前年,当前月,当前日期,当前月第一天,当前月最后一天,上个月,上月第一天，上月最后一天
    //    autocompleteAsync("#store_code", "../../queryMatStore.do?isCheck=false", "id", "text", true, true, "", true);
        autocomplete("#mat_type_code", "../../queryMatType.do?isCheck=false", "id", "text", true, true);
        autocompleteAsync("#dept_code", "../../queryHosDeptDictByPerm.do?isCheck=false", "id", "text", true, true);
        autoCompleteByData("#state", matDuraScrap_state.Rows, "id", "text",true, true);
		$("#inv_code").ligerTextBox({width:220});
        //格式化按钮
		$("#query").ligerButton({click: query, width:70});
		$("#print").ligerButton({click: print, width:70});
		$("#begin_make_date").ligerTextBox({width : 100});
        autodate("#begin_make_date", "yyyy-mm-dd", "month_first");
		$("#end_make_date").ligerTextBox({width : 100});
        autodate("#end_make_date", "yyyy-mm-dd", "month_last");
	}
    
	 //打印回调方法
    function lodopPrint(){
    	var head="<table class='head' width='100%'><tr><td>单位：${sessionScope.hos_name}</td></tr>";
    	head=head+"<tr><td>统计年月："+liger.get("year").getText()+liger.get("month").getText()+"</td></tr>";
 		head=head+"</table>";
 		grid.options.lodop.head=head; 
 		grid.options.lodop.fn=renderFunc;
 		grid.options.lodop.title=liger.get("year").getText()+liger.get("month").getText()+"耐用品报废明细表";
    }
    function openUpdate(obj) {

		var voStr = obj.split(",");
		var paras =  "scrap_id=" + voStr[0];
		
		parent.$.ligerDialog.open({
			title: '科室报废查询',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/mat/dura/scrap/dept/updatePage.do?isCheck=false&' + paras.toString(),
			modal: true, showToggle: false, showMax: true, showMin: true, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});   
	}
  	//打印
	function print(){

		if(grid.getData().length==0){
		    		
					$.ligerDialog.error("请先查询数据！");
					
					return;
				}
		    	
		    	
		    	var heads={
		        		"isAuto":true,//系统默认，页眉显示页码
		        		"rows": [
		    	          {"cell":0,"value":"统计年月："},
		    	          {"cell":1,"value":date} , /*          {"cell":1,"value":""+liger.get("begin_confirm_date").getValue()+"至"+liger.get("end_confirm_date").getValue()}	, */
		    	        /*   {"cell":3,"value":"仓库："},
		    	          {"cell":4,"value":""+liger.get("store_code").getText()==''?'空':liger.get("store_code").getText().split(" ")[1]+""} */
		        	]}; 
		    	//表尾
				var foots = {
					rows: [
						{"cell":0,"value":"制单日期:"} ,
						{"cell":1,"value":date} ,
					]
				}; 
		    	var printPara={
		          		title: "耐用品报废明细表(科室)",//标题
		          		columns: JSON.stringify(grid.getPrintColumns()),//表头
		          		class_name: "com.chd.hrp.mat.service.dura.query.MatDuraQueryService",
		       			method_name: "queryMatDuraQueryScrapDetailPrint",
		       			bean_name: "matDuraQueryService",
		       			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
		       			foots: JSON.stringify(foots),//表尾需要打印的查询条件,可以为空 
		           	};
		        	$.each(grid.options.parms,function(i,obj){
		       			printPara[obj.name]=obj.value;
		        	});
		       		
		        	officeGridPrint(printPara);
    /* 	if(grid.getData().length==0){
    		
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
   			title:'耐用品报废明细表',
   			head:[
				{"cell":0,"value":"单位: ${sessionScope.hos_name}","colspan":colspan_num,"br":true},
				{"cell":0,"value":
					"统计日期: " + $("#year").val()+ "年"+ $("#month").val()+ "月" +
					" 至  "+ $("#end_year").val() +"年"+ $("#end_month").val() + "月","colspan":colspan_num,"br":true}
   			],
   			foot:[
				{"cell":0,"value":"主管:","br":false} ,
				{"cell":1,"value":"复核人:","br":false},
				{"cell":2,"value":"制单人： ${sessionScope.user_name}","br":true},
				{"cell":0,"value":"打印日期: " + cur_date,"colspan":colspan_num,"br":true}
   			],
   			columns:grid.getColumns(1),
   			headCount:1,//列头行数
   			autoFile:true,
   			type:3
   		};
   		ajaxJsonObjectByUrl("queryMatDuraQueryStoreStock.do?isCheck=false", selPara, function (responseData) {
   			printGridView(responseData,printPara);
		});
 */
   		
    }
	
	</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" >	</div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" >
        <tr>
          
			<td align="right" class="l-table-edit-td">
				制单日期：
			</td>
			<td align="left" class="l-table-edit-td">
				<table cellpadding="0" cellspacing="0" class="l-table-edit" >
					<tr>
			            <td align="left"><input name="begin_make_date" type="text" id="begin_make_date" ltype="text"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
			            <td align="left" class="l-table-edit-td">至：</td>
			            <td align="left" class="l-table-edit-td"><input name="end_make_date" type="text" id="end_make_date" ltype="text"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
					</tr>
				</table>
			</td>
       <!--      <td align="right" class="l-table-edit-td" >
	            仓库名称：
			</td>
			<td align="left" class="l-table-edit-td" >
				<input name="store_code" id="store_code" type="text" required="true" validate="{required:true}" />
			</td> -->
           <td align="right" class="l-table-edit-td" >
	            科室名称：
			</td>
			<td align="left" class="l-table-edit-td" >
				<input name="dept_code" id="dept_code" type="text" required="true" validate="{required:true}" />
			</td>
		</tr>
		<tr>
            <td align="right" class="l-table-edit-td"  >
	           耐用品：
			</td>
			<td align="left" class="l-table-edit-td"  >
				<input name="inv_code" id="inv_code" type="text" required="true" validate="{required:true}" />
			</td>
				<td align="right" class="l-table-edit-td">
				状   态：
			</td>
			<td align="left" class="l-table-edit-td" >
				<input name="state" type="text" id="state" ltype="text" />
			</td>
			<td colspan="4" align="right" class="l-table-edit-td">
				<button id ="query" accessKey="Q"><b>查询（<u>Q</u>）</b></button>
				&nbsp;&nbsp;
				<button id ="print" accessKey="P"><b>打印（<u>P</u>）</b></button>
				&nbsp;&nbsp;
			</td>
        </tr> 
    </table>
    <div align="center">
    	<h2>
	    	<span id="year_month_span"></span>耐用品报废明细表(科室)
    	</h2>
    </div>
	<div id="maingrid"></div>
</body>
</html>
