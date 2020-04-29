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
	var date = year+"年"+month+"月"+day+"日";
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
    	var isAccByStore='${p04045}'; //是否按库房结账
    	if(isAccByStore==1 ){
    		if(liger.get("set_code").getValue()==''){
    			$.ligerDialog.error("请选择虚仓");
    			return;
    		}
    	}
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
			name : 'bus_type_code',
			value : liger.get("bus_type_code").getValue() == null ? "" : liger.get("bus_type_code").getValue()
		}); 

		
		
			var selPara={};
	    	
	    	$.each(grid.options.parms,function(i,obj){
	    		
	    		selPara[obj.name]=obj.value;
	    		
	    	});
    	ajaxJsonObjectByUrl("queryOccurOutMatFimTypeDictForHead.do?isCheck=false", selPara, function (responseData) {
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
    					display:responseData[i].fim_type_name,
    					id:'',
    					formatter:'###,##0.00',
    					name:responseData[i].fim_type_code,
    					align:'right',
    					minWidth:'140', render:function(rowdata,rowindex,value){
        					return formatNumber(value,2,1);
        				}
    				});
    			}
    				columns.push({display:'汇总金额', id:'', name:'AMOUNT_MONEY', align:'right', minWidth:'80', type:'float',
    					render: function (rowdata, rowindex, value) {
							return formatNumber(value == null ? 0 : value, 2, 1);
						},formatter:'###,##0.00'
    				 
    				}); 
    			grid.set('columns', columns);  
    			//ajaxJsonObjectByUrl("../../../hip/queryALLMatTypeDict.do?isCheck=false", null, function (responseData) {});
    				 
		});
		
		
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({  
			columns: [],
			dataAction: 'server',dataType: 'server',usePager:false,url:'queryMatSuoOutStoreDetailToFim.do',width: '100%', height: '100%',rownumbers:true,
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
     
	
     
     
    function loadDict(){
    	var date = getCurrentDate();
        var aa = date.split(';');
        year = aa[0];
        month = aa[1];
		//字典下拉框
		var bus_type_code_paras={sel_flag : "out"};
		autocompleteAsync("#bus_type_code", "../../../queryMatBusType.do?isCheck=false", "id", "text", true, true,{codes : "'3', '5','7','9', '11','13','17', '19', '21', '23'"},true);
		autocomplete("#store_code", "../../../queryMatStoreDictDate.do?isCheck=false", "id", "text", true, true,{read_or_write : 1});
		autocomplete("#dept_code", "../../../queryMatDeptDictDate.do?isCheck=false", "id", "text", true, true, {is_last : 1,read_or_write : 1},'', false, 240);
		autocomplete("#set_code", "../../../queryMatVirStore.do?isCheck=false", "id", "text", true, true);<%-- 虚仓 --%>
        $("#begin_date").ligerTextBox({width:100});
        autodate("#begin_date", "yyyy-MM-dd");
        $("#end_date").ligerTextBox({width:100});
        autodate("#end_date", "yyyy-MM-dd");
        $("#dept_code").ligerTextBox({width:240});
        autocompleteAsync("#begin_year", "../../../queryMatYear.do?isCheck=false", "id", "text", true, true, "", false, year, "60");
        autocompleteAsync("#begin_month", "../../../queryMatMonth.do?isCheck=false", "id", "text", true, true, "", false, month, "50");
	}  
	
  	//打印
	function print(){
    	if(grid.getData().length==0){
			$.ligerDialog.error("请先查询数据！");
			return;
		}
    	
    	if(liger.get("set_code").getValue()== "" && liger.get("store_code").getValue()== ""){ 
    		var heads={
            		"isAuto":true,//系统默认，页眉显示页码
            		"rows": [
        	          {"cell":0,"value":"统计年月："},
        	          {"cell":1,"value":$("#begin_year").val()+"-"+$("#begin_month").val()},
            	]}; 
    		
        	
    	}else {
    		if(liger.get("set_code").getValue()== ""){
    			var heads={
                		"isAuto":true,//系统默认，页眉显示页码
                		"rows": [
            	          {"cell":0,"value":"统计年月："},
            	          {"cell":1,"value":$("#begin_year").val()+"-"+$("#begin_month").val()},
            	          {"cell":3,"value":"仓库："},
            	          {"cell":4,"value":""+liger.get("store_code").getText()==''?' ':liger.get("store_code").getText().split(" ")[1]+""}
            	         
                	]}; 
        			 
    		}else{
    			var heads={
                		"isAuto":true,//系统默认，页眉显示页码
                		"rows": [
            	          {"cell":0,"value":"统计年月："},
            	          {"cell":1,"value":$("#begin_year").val()+"-"+$("#begin_month").val()},
            	          {"cell":3,"value":"虚仓："},
            	          {"cell":4,"value":""+liger.get("set_code").getText()==''?' ':liger.get("set_code").getText().split(" ")[1]+""}
                	]};  
    		}
    		 
    	}

    	//表尾
		var foots = {
			rows: [
				{"cell":0,"value":"制单日期:"} ,
				{"cell":1,"value":date} ,
			]
		}; 
    	var printPara={
          		title: "出库业务汇总表（财务分类）",//标题
          		columns: JSON.stringify(grid.getPrintColumns()),//表头
          		class_name: "com.chd.hrp.mat.service.account.report.outCategoryCount.MatSuoOutStoreDetailToFimService",
       			method_name: "queryMatSuoOutStoreDetailToFimPrint",
       			bean_name: "matSuoOutStoreDetailToFimService",
       			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
       			foots: JSON.stringify(foots),//表尾需要打印的查询条件,可以为空 
           	};
        	$.each(grid.options.parms,function(i,obj){
       			printPara[obj.name]=obj.value;
        	});
        	officeGridPrint(printPara);
   		
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
				${p04045=='1' ?'<font color="red" size="2">*</font>':'' }虚仓名称：
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
            <td align="right" class="l-table-edit-td"  width="10%"  >
				业务类别：
			</td>
        	<td align="left" class="l-table-edit-td" width="20%" >
            	<input name="bus_type_code" type="text" id="bus_type_code" ltype="text" validate="{required:false,maxlength:100}"/>
            	
            </td>
        
       		

        	
       </tr>
    </table>
    
    <div align="center">
    	<h2>
	    	<span id="year_month_span"></span>
	    	出库业务汇总表（财务分类）
    	</h2>
    </div>
	<div id="maingrid"></div>
</body>
</html>
