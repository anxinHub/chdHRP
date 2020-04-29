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
			 
			amount_money:function(value){//汇总金额
				return formatNumber(value, 2, 1);
			},
		 
	};
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);
        
        $("#is_last").bind("change",function(){
			query();
		});
        
        $("#is_showStore").bind("change",function(){
			query();
		});
    });
    //查询
    function  query(){
		grid.options.parms=[];
		grid.options.newPage=1;
		var begin_year = $("#begin_year").val();
		var begin_month = $("#begin_month").val();
			
		if(begin_year == ''){
			$.ligerDialog.error('年度不能为空');
			return ;
		}
		if(begin_month == ''){
			$.ligerDialog.error('月度不能为空');
			return ; 
		}
		
        //根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'begin_year',
			value : $("#begin_year").val()
		});
		grid.options.parms.push({
			name : 'begin_month',
			value : $("#begin_month").val()
		}); 
		grid.options.parms.push({
			name : 'store_id',
			value : liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[0]
		});
		
		grid.options.parms.push({
			name : 'med_type_id',
			value : liger.get("med_type_code").getValue() == null ? "" : liger.get("med_type_code").getValue().split(",")[0]
		}); 
		
		grid.options.parms.push({
			name : 'dept_id',
			value : liger.get("dept_code").getValue() == null ? "" : liger.get("dept_code").getValue().split(",")[0]
		}); 
		
		grid.options.parms.push({
			name : 'is_last',
			value : $("#is_last").is(":checked") ? 1 : ''}
		);
		 
		
		grid.options.parms.push({
			name : 'set_id',
			value : liger.get("set_code").getValue() == null ? "" : liger.get("set_code").getValue()
		}); 
		
		grid.options.parms.push({
			name : 'is_showStore',
			value : $("#is_showStore").is(":checked") ? 1 : ''}
		);
		

		
		grid.options.parms.push({
			name:'is_charge', value : liger.get("is_charge").getValue()
		});
		
		//grid._setUrl("queryMedDeptCountToFim.do");
			var selPara={};
	    	
	    	$.each(grid.options.parms,function(i,obj){
	    		
	    		selPara[obj.name]=obj.value;
	    		
	    	});
    	ajaxJsonObjectByUrl("../../../../hip/queryALLMedFimTypeDict.do?isCheck=false", selPara, function (responseData) {
    		  abc=JSON.stringify(responseData);
    		 console.log(abc)
    		 columns=[
    	    			{display: '业务类型', name: 'BUS_TYPE_NAME', align: 'left', minWidth: '140', }, 
    	    			{display: '科室编码', name: 'DEPT_CODE', align: 'left', minWidth: '140', }, 
    	    			{display: '科室名称', name: 'DEPT_NAME', align: 'left', minWidth: '150'}, 
    	    				
    	    		];
    		var show_store = $("#is_showStore").is(":checked") ? true:false;
	    		var name='';
    			for(var i=0;i<responseData.length;i++){
    				columns.push({
    					display:responseData[i].text,
    					id:'',
    					name:responseData[i].id,
    					align:'left',
    					minWidth:'140', render:function(rowdata,rowindex,value){
        					return formatNumber(value,2,1);
        				}
    				});
    			}
    				columns.push({display:'汇总金额', id:'', name:'AMOUNT_MONEY', align:'right', minWidth:'80', type:'float',
    					render: function (rowdata, rowindex, value) {
							return formatNumber(value == null ? 0 : value, 2, 1);
						},
    				 
    				});
    			grid.set('columns', columns);  
    			//ajaxJsonObjectByUrl("../../../hip/queryALLMedTypeDict.do?isCheck=false", null, function (responseData) {});
    				 
		});
		
		
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [],
			dataAction: 'server',dataType: 'server',usePager:false,url:'queryMedSuoOutStoreDetailToFim.do',width: '100%', height: '100%',rownumbers:true,
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
     
	 //打印回调方法
     function lodopPrint(){
     	var head="<table class='head' width='100%'><tr><td>单位：${sessionScope.hos_name}</td></tr>";
  		head=head+"<tr><td>确认日期："+$("#begin_date").val()+"至"+$("#end_date").val()+"</td></tr>";
  		head=head+"</table>";
  		grid.options.lodop.head=head; 
  		grid.options.lodop.fn=renderFunc;
  		grid.options.lodop.title=$("#begin_date").val()+"至"+$("#end_date").val()+"科室消耗药品分类统计表(财务)";
     }
     
     
    function loadDict(){
    	var date = getCurrentDate();
        var aa = date.split(';');
        year = aa[0];
        month = aa[1];
		//字典下拉框
		var bus_type_code_paras={sel_flag : "out"};
		//autocompleteAsyncMulti("#bus_type_code", "../../../queryMedBusType.do?isCheck=false", "id", "text", true, true,{codes : '3, 21, 9,11,13,23,49,50'},true);
		autocomplete("#store_code", "../../../queryMedStoreByRead.do?isCheck=false", "id", "text", true, true);
		autocompleteAsync("#med_type_code", "../../../queryMedTypeDict.do?isCheck=false", "id", "text", true, true, {is_last : 1});
		autocomplete("#dept_code", "../../../queryMedAppDept.do?isCheck=false", "id", "text", true, true, {is_last : 1},'', false, 240);
		autocomplete("#set_code", "../../../queryMedVirStore.do?isCheck=false", "id", "text", true, true);<%-- 虚仓 --%>
		autocomplete("#is_charge", "../../../queryMedYearOrNo.do?isCheck=false", "id", "text", true, true);
        $("#begin_date").ligerTextBox({width:100});
        autodate("#begin_date", "yyyy-MM-dd");
        $("#end_date").ligerTextBox({width:100});
        autodate("#end_date", "yyyy-MM-dd");
        $("#dept_code").ligerTextBox({width:240});
        autocompleteAsync("#begin_year", "../../../queryMedYear.do?isCheck=false", "id", "text", true, true, "", false, year, "60");
        autocompleteAsync("#begin_month", "../../../queryMedMonth.do?isCheck=false", "id", "text", true, true, "", false, month, "50");
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
       			title:'科室统计',
       			head:[
    				{"cell":0,"value":"单位: ${sessionScope.hos_name}","colspan":colspan_num,"br":true},
    				{"cell":0,"value":"统计日期: " + $("#begin_date").val() +" 至  "+ $("#end_date").val(),"colspan":colspan_num,"br":true},
    				{"cell":0,"value":"仓库: " + $("#store_code").val() ,"colspan":colspan_num,"br":true}
       			
    				],
       			foot:[
    				{"cell":0,"value":"主管:","colspan":2,"br":false} ,
    				{"cell":2,"value":"复核人:","colspan":colspan_num-4,"br":false},
    				{"cell":colspan_num-2,"value":"制单人： ${sessionScope.user_name}","colspan":2,"br":true},
    				{"cell":0,"value":"打印日期: " + cur_date,"colspan":colspan_num,"br":true}
       			],
       			columns:grid.getColumns(1),
       			headCount:1,//列头行数
       			autoFile:true,
       			type:3
       	};
   		ajaxJsonObjectByUrl("queryMedDeptCountToFim.do?isCheck=false", selPara, function (responseData) {
   			printGridView(responseData,printPara);
		});

   		
    }
	</script>
</head>

<body style="padding: 0px; overflow: hidden;" >
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" >
        <tr>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  width="10%">
            	<font color="red" size="2">*</font>结账月度：
            </td>
            <td align="left" class="l-table-edit-td"  width="200px">
				<table >
					<tr>
						<td align="right" class="l-table-edit-td">
							<input name="begin_year" id="begin_year" type="text" required="true" validate="{required:true}" />
						</td>
						<td align="right" class="l-table-edit-td"  >
							年
						</td>
						<td align="right" class="l-table-edit-td">
							<input name="begin_month" id="begin_month" type="text" required="true" validate="{required:true}" />
						</td>
						
	            	</tr>
				</table>
			</td>
			<td align="right" class="l-table-edit-td"  width="10%">
				虚仓名称：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="set_code" type="text" id="set_code" ltype="text"/>
            </td>
            
            <td align="right" class="l-table-edit-td"  width="10%">
				仓库名称：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="store_code" type="text" id="store_code" ltype="text"/>
            </td>
            
        </tr>
        <tr>
        	<td align="right" class="l-table-edit-td" width="10%">
        		科&nbsp;&nbsp;&nbsp;室：
        	</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="dept_code" type="text" id="dept_code" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
            
        	<td align="right" class="l-table-edit-td"  width="10%"  style="display: none;">
				药品类别：
			</td>
        	<td align="left" class="l-table-edit-td" width="20%" style="display: none;">
            	<input name="med_type_code" type="text" id="med_type_code" ltype="text" validate="{required:false,maxlength:100}"/>
            </td>
             
        
       		

        	<td align="right" class="l-table-edit-td"  width="10%">是否收费：</td>
        	<td align="left" class="l-table-edit-td">
				<input name="is_charge" type="text" id="is_charge" ltype="text" validate="{required:true,maxlength:20}" />
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
