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
        
        
    });
    //查询
    function  query(){
		grid.options.parms=[];
		grid.options.newPage=1;
		var begin_date = $("#begin_date").val();
		var end_date = $("#end_date").val();
		$("#year_month_span").text(begin_date+"至"+end_date);
		if(begin_date == ''){
			$.ligerDialog.error('开始期间不能为空');
			return ;
		}
		
		if(end_date == ''){
			$.ligerDialog.error('结束期间不能为空');
			return ; 
		}
		if(begin_date > end_date){
			$.ligerDialog.error('开始期间不能大于结束期间');
			return;
		}
		
		if(liger.get("type_level").getValue() == ''){
			$.ligerDialog.error('类别级次不能为空');
			return ; 
		}
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
			name : 'bus_type_code',
			value : liger.get("bus_type_code").getValue() == null ? "" : liger.get("bus_type_code").getValue()
		}); 
		
		
		grid.options.parms.push({
			name : 'dept_id',
			value : liger.get("dept_code").getValue() == null ? "" : liger.get("dept_code").getValue().split(",")[0]
		}); 
		
		
		grid.options.parms.push({
			name : 'type_level',
			value : liger.get("type_level").getValue() == null ? "" : liger.get("type_level").getValue()
		});
		
		grid.options.parms.push({
			name : 'set_id',
			value : liger.get("set_code").getValue() == null ? "" : liger.get("set_code").getValue()
		}); 
		var selPara={};
    	
    	$.each(grid.options.parms,function(i,obj){
    		
    		selPara[obj.name]=obj.value;
    		
    	});
    	ajaxJsonObjectByUrl("queryOccurOutMatTypeDictForHead.do?isCheck=false", selPara, function (responseData) {
    		 columns=[
    	    			{display: '业务类型', name: 'bus_type_name', align: 'left', minWidth: '140'}, 
    	    			{display: '科室编码', name: 'dept_code', align: 'left', minWidth: '140'}, 
    	    			{display: '科室名称', name: 'dept_name', align: 'left', minWidth: '150'}, 
    	    		];
	    		var name='';
    			for(var i=0;i<responseData.length;i++){
    				name=responseData[i].mat_type_name;
    				columns.push({
    					display:name,
    					id:'',
    					formatter:'###,##0.00',
    					name:responseData[i].mat_type_code,
    					align:'right',
    					minWidth:'140', render:function(rowdata,rowindex,value){
        					return formatNumber(value,2,1);
        				}
    				});
    				renderFunc[responseData[i].id] = function(value){
    					return formatNumber(value,2, 1);
    				}
    			}
    				columns.push({display:'汇总金额', id:'', name:'amount_money', align:'right', minWidth:'80', type:'float',
    					render: function (rowdata, rowindex, value) {
							return formatNumber(value == null ? 0 : value, 2, 1);
						},formatter:'###,##0.00'
    				}); 
    			grid.set('columns', columns);  
    			//加载查询条件
    	    	grid.loadData(grid.where);
		}); 
    	
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [],
			dataAction: 'server',dataType: 'server',usePager:false,url:'queryMatSuoOutStoreDetail.do',
			width: '100%', height: '100%',rownumbers:true,
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
    
    
    
	 //打印回调方法
    function lodopPrint(){
    	var head="<table class='head' width='100%'><tr><td>单位：${hos_name}</td></tr>";
 		head=head+"<tr><td>确认日期："+$("#begin_date").val()+"至"+$("#end_date").val()+"</td></tr>";
 		head=head+"</table>";
 		grid.options.lodop.head=head; 
 		grid.options.lodop.fn=renderFunc;
 		//grid.options.lodop.title=$("#begin_date").val()+"至"+$("#end_date").val()+"科室消耗物资分类统计表";
 		//由于第二行有确认日期的范围故即墨要求大表头不要日期范围
 		grid.options.lodop.title="出库业务汇总表（物资分类）";
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
	    autocomplete("#bus_type_code", "../../../queryMatBusType.do?isCheck=false", "id", "text", true, true,{codes : "'3', '5','7','9', '11','13','17', '19', '21', '23'"});
		autocomplete("#store_code", "../../../queryMatStoreDictDate.do?isCheck=false", "id", "text", true, true,{read_or_write : 1});
		autocomplete("#dept_code", "../../../queryMatDeptDictDate.do?isCheck=false", "id", "text", true, true, {is_last : 1,read_or_write : 1},'', false, 280);
		autocomplete("#type_level", "../../../queryMatTypeLevel_2.do?isCheck=false", "id", "text", true, true,'',true);
		autocomplete("#set_code", "../../../queryMatVirStore.do?isCheck=false", "id", "text", true, true);<%-- 虚仓 --%>
        $("#begin_date").ligerTextBox({width:120});
        autodate("#begin_date", "yyyy-MM-dd", "month_first");
        $("#end_date").ligerTextBox({width:120});
        autodate("#end_date", "yyyy-MM-dd", "month_last");
        $("#dept_code").ligerTextBox({width:280});
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
        	          {"cell":1,"value":""+$("#begin_date").val()+"至"+$("#end_date").val()},
        	          
            	]}; 
    	}else {
    		if(liger.get("set_code").getValue()== ""){
    			var heads={
                		"isAuto":true,//系统默认，页眉显示页码
                		"rows": [
            	          {"cell":0,"value":"统计年月："},
            	          {"cell":1,"value":""+$("#begin_date").val()+"至"+$("#end_date").val()},
            	          {"cell":3,"value":"仓库："},
            	          {"cell":4,"value":""+liger.get("store_code").getText()==''?' ':liger.get("store_code").getText().split(" ")[1]+""}
            	         
                	]}; 
        		
    		}else{
    			var heads={
                		"isAuto":true,//系统默认，页眉显示页码
                		"rows": [
            	          {"cell":0,"value":"统计年月："},
            	          {"cell":1,"value":""+$("#begin_date").val()+"至"+$("#end_date").val()},
            	          {"cell":3,"value":"虚仓："},
            	          {"cell":4,"value":""+liger.get("set_code").getText()==''?' ':liger.get("set_code").getText().split(" ")[1]+""}
                	]};  
    		}
    		
    		
    	}


    /* 	var heads={
        		"isAuto":true,//系统默认，页眉显示页码
        		"rows": [
    	          {"cell":0,"value":"统计年月："},
    	          {"cell":1,"value":""+$("#begin_date").val()+"至"+$("#end_date").val()},
    	          {"cell":3,"value":"仓库："},
    	          {"cell":4,"value":""+liger.get("store_code").getText()==''?'空':liger.get("store_code").getText().split(" ")[1]+""},
    	          {"cell":6,"value":"虚仓："},
    	          {"cell":7,"value":""+liger.get("set_code").getText()==''?'空':liger.get("set_code").getText().split(" ")[1]+""}
        	]};  */
    	//表尾
		var foots = {
			rows: [
				{"cell":0,"value":"制单日期:"} ,
				{"cell":1,"value":date} ,
			]
		}; 
    	var printPara={
          		title: "出库业务汇总表（物资分类）",//标题
          		columns: JSON.stringify(grid.getPrintColumns()),//表头
          		class_name: "com.chd.hrp.mat.service.account.report.outCategoryCount.MatSuoOutStoreDetailService",
       			method_name: "queryMatSuoOutStoreDetailPrint",
       			bean_name: "matSuoOutStoreDetailService",
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

<body style="padding: 0px; overflow: hidden;"  >
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" >
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
            	<input name="dept_code" type="text" id="dept_code" ltype="text"  />
            </td>
            <td align="right" class="l-table-edit-td"  width="10%">
				<font color="red" size="2">*</font>类别级次：
			</td>
        	<td align="left" class="l-table-edit-td" width="20%">
            	<!-- <input name="type_level" type="text" id="type_level" ltype="text" validate="{required:false,maxlength:100}" /> -->
            	<select name="type_level"  id="type_level" >
            			<option value = "1">一级</option>
            			<option value = "2">末级</option>
            		</select>
            </td>
       	    <td align="right" class="l-table-edit-td"  width="10%">业务类型：</td>
        	<td align="left" class="l-table-edit-td">
				<input name="bus_type_code" type="text" id="bus_type_code" ltype="text" validate="{required:true,maxlength:20}" />
			</td>
       </tr>
    </table>
    
    <div align="center">
    	<h2>
	    	<span id="year_month_span"></span>
	    	出库业务汇总表（物资分类）
    	</h2>
    </div>
	<div id="maingrid"></div>
</body>
</html>
