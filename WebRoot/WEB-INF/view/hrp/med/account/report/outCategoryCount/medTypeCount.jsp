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
			  
    		amount_money:function(value){//金额
				return formatNumber(value,'${p08005 }', 1);
			},
			
		 
	};
    
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
        
        <%--显示科室改变事件--%>
        $("#show_dept").bind("change",function(){
			query();
		});
        
        <%--显示仓库改变事件--%>
        $("#is_showStore").bind("change",function(){
			query();
		});
        
    });
    //查询
    function  query(){
		grid.options.parms=[];
		grid.options.newPage=1;
		
		var begin_date = $("#begin_date").val();
		var end_date = $("#end_date").val();
		$("#year_month_span").text(begin_date+"至"+end_date);
			
		if(begin_date == ''){
			$.ligerDialog.warn('开始期间不能为空');
			return ;
		}
		
		if(end_date == ''){
			$.ligerDialog.warn('结束期间不能为空');
			return ; 
		}
		if(begin_date > end_date){
			$.ligerDialog.warn('开始期间不能大于结束期间');
			return;
		}
		
		if(liger.get("bus_type_code").getValue() == ""){
			$.ligerDialog.warn('业务类型不能为空');
			return ; 
		}
		
		f_setColumns();
		
        //根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'begin_date',
			value : $("#begin_date").val()
		});
		grid.options.parms.push({
			name : 'end_date',
			value : $("#end_date").val()
		}); 
		
		grid.options.parms.push({
			name : 'store_id',
			value : liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[0]
		});
		
		grid.options.parms.push({
			name : 'set_id',
			value : liger.get("set_code").getValue() == null ? "" : liger.get("set_code").getValue()
		});
		
		grid.options.parms.push({
			name : 'dept_id',
			value : liger.get("dept_code").getValue() == null ? "" : liger.get("dept_code").getValue().split(",")[0]
		});
		
		grid.options.parms.push({
			name : 'is_showStore',
			value : $("#is_showStore").is(":checked") ? "1":""
		});
		
		grid.options.parms.push({
			name : 'is_showDept',
			value : $("#show_dept").is(":checked") ? "1":""
		});
		
		grid.options.parms.push({
			name:'bus_type_code',value:
				"("+liger.get("bus_type_code").getValue().replace(/;/g,',')+")" == "()"? "" : "("+liger.get("bus_type_code").getValue().replace(/;/g,',')+")"
		}); 
		
		//grid._setUrl("queryMedDeptCount.do");
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMedTypeCount.do',width: '100%', height: '100%',rownumbers:true,
			delayLoad: true,//初始化不加载，默认false  
			selectRowButtonOnly:true,
			toolbar: { items: [
	  			 { text: '查询（<u>Q</u>）', id:'search', click: query, icon:'search' },
				 { line:true },
			     { text: '打印', id:'print', click: print, icon:'print' },
				 { line:true }
	  		]}	
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    //动态设置表头
	function f_setColumns(){ 
		var show_dept = $("#show_dept").is(":checked") ? true:false;
		var show_store = $("#is_showStore").is(":checked") ? true:false;
		var columns = [];
		 
		columns.push(
			{display:'虚仓名称',name:"set_name",width:'20%',align : 'left',
				render:function(rowdata, rowindex, value){
					if(rowdata.set_name == null){
						return "";
					}
					return rowdata.set_name;
				}
			}
		);
		 
		if(show_store){
			columns.push(
				{display:'仓库名称',name:"store_name",width:'20%',align : 'left'}
			);
		}
		 
		if(show_dept){
			columns.push(
				{display:'科室名称',name:"dept_name",width:'20%',align : 'left'}
			);
		}
		 
		columns.push(
			{display:'药品类别',name:"med_type_name",width:'20%',align : 'left'}
		);
		 
		columns.push(
			{display:'金额',name:"amount_money",width:'20%',align : 'right',formatter:"###,##0.00",
				render : function(rowdata, rowindex, value) {
   					return value ==null ? "" : formatNumber(value,'${p08005 }', 1);
   				}
			}
		);
		grid.set('columns', columns); 
       	//grid.reRender();  
	} 
    
	 //打印回调方法
    function lodopPrint(){
    	var head="<table class='head' width='100%'><tr><td>单位：${sessionScope.hos_name}</td></tr>";
 		head=head+"<tr><td>确认日期："+$("#begin_date").val()+"至"+$("#end_date").val()+"</td></tr>";
 		head=head+"</table>";
 		grid.options.lodop.head=head; 
 		grid.options.lodop.fn=renderFunc;
 		grid.options.lodop.title=$("#begin_date").val()+"至"+$("#end_date").val()+"科室消耗药品分类统计表(财务)";
    }
	 
   /*  //键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
		hotkeys('A', add);
		hotkeys('C', copy_no);
		hotkeys('O', offset);
		hotkeys('D', remove);
		hotkeys('S', audit);
		hotkeys('U', unAudit);
		hotkeys('F', confirm);
		hotkeys('P', print);
	}
     */
     
    function loadDict(){
		//字典下拉框
		
		var bus_type_code_paras={sel_flag : "out"};
		autocompleteAsyncMulti("#bus_type_code", "../../../queryMedBusType.do?isCheck=false", "id", "text", true, true,bus_type_code_paras,true);
		autocomplete("#store_code", "../../../queryMedStoreByRead.do?isCheck=false", "id", "text", true, true);
		autocomplete("#dept_code", "../../../queryMedAppDept.do?isCheck=false", "id", "text", true, true,{is_last:1},'', false, 240);
		autocomplete("#set_code", "../../../queryMedVirStore.do?isCheck=false", "id", "text", true, true);<%-- 虚仓 --%>
        $("#begin_date").ligerTextBox({width:100});
        autodate("#begin_date", "yyyy-MM-dd");
        $("#end_date").ligerTextBox({width:100});
        autodate("#end_date", "yyyy-MM-dd");
        $("#dept_code").ligerTextBox({width:240});
	} 
    
  	//打印
	function print(){
    	
    	if(grid.getData().length==0){
    		
			$.ligerDialog.warn("请先查询数据！");
			
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
       			title:'药品分类统计',
       			head:[
    				{"cell":0,"value":"单位: ${sessionScope.hos_name}","colspan":colspan_num,"br":true},
    				{"cell":0,"value":"统计日期: " + $("#begin_date").val() +" 至  "+ $("#end_date").val(),"colspan":colspan_num,"br":true}
       			],
       			foot:[
    				{"cell":0,"value":"主管:","colspan":2,"br":false} ,
    				{"cell":2,"value":"复核人:","colspan":colspan_num-2,"br":true},
    				{"cell":0,"value":"制单人： ${sessionScope.user_name}","colspan":2,"br":false},
    				{"cell":2,"value":"打印日期: " + cur_date,"colspan":colspan_num-2,"br":true}
       			],
       			columns:grid.getColumns(1),
       			headCount:1,//列头行数
       			autoFile:true,
       			type:3
       	};
   		ajaxJsonObjectByUrl("queryMedTypeCount.do?isCheck=false", selPara, function (responseData) {
   			printGridView(responseData,printPara);
		});

   		
    }
	
	</script>
</head>

<body style="padding: 0px; overflow: hidden;" onload="f_setColumns()">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
        <tr>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  width="10%">
            	<font color="red" size="2">*</font>确认日期：
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
				仓库：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="store_code" type="text" id="store_code" ltype="text" />
            </td>
            
			<td align="right" class="l-table-edit-td" width="10%">
        		虚仓名称：
        	</td>
            <td align="left" class="l-table-edit-td" width="20%"> 
            	<input name="set_code" type="text" id="set_code" ltype="text" />
            </td>
        </tr>
        <tr>
        	<td align="right" class="l-table-edit-td" width="10%">
        		科室:
        	</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="dept_code" type="text" id="dept_code" ltype="text"/>
            </td>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><span style="color:red">*</span>业务类型：</td>
			<td align="left" class="l-table-edit-td">
				<input name="bus_type_code" type="text" id="bus_type_code" ltype="text" validate="{required:true,maxlength:20}" />
			</td>
			
			<td align="right" class="l-table-edit-td" width="10%">
        		<input name="show_dept" type="checkbox" id="show_dept" checked="checked" />显示科室
			</td>
        	
        	<td align="left" class="l-table-edit-td" width="20%">
        		<input name="is_showStore" type="checkbox" id="is_showStore" checked="checked" />是否显示仓库
        	</td>
        </tr>
    </table>
    
    <div align="center">
    	<h2>
	    	<span id="year_month_span"></span>
	    	科室消耗药品分类统计表
    	</h2>
    </div>
	<div id="maingrid"></div>
</body>
</html>
