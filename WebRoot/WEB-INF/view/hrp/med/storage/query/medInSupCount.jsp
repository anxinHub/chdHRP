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
			/* med_type_code:function(value){//汇总金额
				return formatNumber(value, 2, 1);
			}, */
		 
	};
    
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
		loadHotkeys();
		
		$("#is_showStore").bind("change",function(){
			
			query();
		});
    });
    //查询
    function  query(){
    	 console.log(grid.getColumns())
		grid.options.parms=[];
		grid.options.newPage=1;
        //根据表字段进行添加查询条件
		grid.options.parms.push({name : 'begin_confirm_date',value : $("#begin_confirm_date").val()});
		grid.options.parms.push({name : 'end_confirm_date',value : $("#end_confirm_date").val()});
		grid.options.parms.push({name : 'store_id',value : liger.get("store_code").getValue().split(",")[0]}); 
		grid.options.parms.push({name : 'store_no',value : liger.get("store_code").getValue().split(",")[1]}); 
		grid.options.parms.push({name : 'med_type_id',value : liger.get("med_type_code").getValue().split(",")[0]}); 
		//grid.options.parms.push({name : 'med_type_no',value : liger.get("med_type_code").getValue().split(",")[1]}); 
		grid.options.parms.push({name : 'med_type_code',value : liger.get("med_type_code").getText().split(" ")[0]});
		grid.options.parms.push({name : 'sup_id',value : liger.get("sup_code").getValue().split(",")[0]}); 
		grid.options.parms.push({name : 'sup_no',value : liger.get("sup_code").getValue().split(",")[1]});
    	grid.options.parms.push({name : 'is_charge',value : liger.get("is_charge").getValue()}); 
    	grid.options.parms.push({name : 'set_id',value : liger.get("set_code").getValue()});
    	grid.options.parms.push({name : 'type_level',value : liger.get("type_level").getValue()});
    	grid.options.parms.push({name : 'is_showStore',value : $("#is_showStore").is(":checked") ? "1":""}); 
    	//2017年3月28日14:42:44邓辉添加，加载动态表头
	  	var selPara={};
	    	
	    	$.each(grid.options.parms,function(i,obj){
	    		selPara[obj.name]=obj.value;
	    	});
    	ajaxJsonObjectByUrl("../../../hip/queryALLMedTypeDict.do?isCheck=false", selPara, function (responseData) {
    		  abc=JSON.stringify(responseData);
    		 columns=[
    				 
    	    			{display: '业务类型', name: 'bus_type_name', align: 'left', minWidth: '140' },
    	    			{display: '供应商编码', name: 'sup_code', align: 'left', minWidth: '140' },
    	    			{display: '供应商名称', name: 'sup_name', align: 'left', minWidth: '150'}, 
    	    		];
	    		var name='';
    			for(var i=0;i<responseData.length;i++){
    				name=responseData[i].text;
    				columns.push({
    					display:name,
    					id:'',
    					name:responseData[i].id,
    					align:'left',
    					minWidth:'140', render:function(rowddata,rowindex,value){
        					return formatNumber(value,2,1);
        				}
    				});
    				renderFunc[responseData[i].id] = function(value){
    					return formatNumber(value,2, 1);
    				}
    				
    			}
    				columns.push({display:'汇总金额', id:'', name:'amount_money', align:'right', minWidth:'80',type:'float',
    					render: function (rowdata, rowindex, value) {
							return formatNumber(value == null ? 0 : value, 2, 1);
						},
	    				 
    				});
    			grid.set('columns', columns);  
    			grid.loadData(grid.where);
		});
    	
    	//加载查询条件
    	//grid.loadData(grid.where);
    	 
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [],
			dataAction: 'server',dataType: 'server',usePager:false,url:'queryMedStorageQueryMedInSupCount.do',
			width: '100%', height: '100%',rownumbers:true,
			delayLoad: true,//初始化不加载，默认false
			selectRowButtonOnly:true,//heightDiff: -10,
			toolbar: { items: [
				{ text: '查询（<u>Q</u>）', id:'search', click: query, icon:'search' },
				{ line:true },
				{ text: '打印', id:'print', click: print, icon:'print' },
				{ line:true }
			]},
			onAfterShowData:function(data){
				console.log(data);
			}
		});
        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
     
    //打印回调方法
    function lodopPrint(){
    	var head="<table class='head' width='100%'><tr><td>单位：${sessionScope.hos_name}</td></tr>";
 		head=head+"<tr><td>入库日期："+$("#begin_confirm_date").val()+"至"+$("#end_confirm_date").val()+"</td></tr>";
 		head=head+"</table>";
 		grid.options.lodop.head=head; 
 		grid.options.lodop.fn=renderFunc;
 		grid.options.lodop.title="入库业务汇总表（药品分类）";
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
    	//alert(colspan_num);
   		var printPara={
   			title:'入库业务汇总表（药品分类）',
   			head:[
				{"cell":0,"value":"单位: ${sessionScope.hos_name}","colspan":colspan_num,"br":true},
				{"cell":0,"value":"统计日期: " + $("#begin_confirm_date").val() +" 至  "+ $("#end_confirm_date").val(),"colspan":colspan_num,"br":true},
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
   		//alert(JSON.stringify(printPara));
   		ajaxJsonObjectByUrl("queryMedStorageQueryMedInSupCount.do?isCheck=false", selPara, function (responseData) {
   			printGridView(responseData,printPara);
		});

   		
    }

    
    //键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
	}
   
    function loadDict(){
		//字典下拉框
		//autocomplete("#store_code", "../../queryMedStoreByRead.do?isCheck=false", "id", "text", true, true);
		autocomplete("#store_code", "../../queryMedStoreDictDate.do?isCheck=false", "id", "text", true, true,{read_or_write : 1});
//		autocomplete("#med_type_code", "../../queryMedTypeDict.do?isCheck=false", "id", "text", true, true,'',false,'',220);
		autocomplete("#med_type_code", "../../queryMedTypeDictDate.do?isCheck=false", "id", "text", true, true,{read_or_write : 1},false,'',220);
		autoCompleteByData("#state", medInMain_state.Rows, "id", "text", true, true);
		autocompleteAsync("#bus_type_code", "../../queryMedBusType.do?isCheck=false", "id", "text", true, true, {sel_flag : 'in'}, true);
		autocomplete("#sup_code", "../../queryHosSupDict.do?isCheck=false", "id", "text", true, true,'',false,'',280);
		autocomplete("#is_charge", "../../queryMedYearOrNo.do?isCheck=false", "id", "text", true, true);
		autocomplete("#set_code", "../../queryMedVirStore.do?isCheck=false", "id", "text", true, true);
		autocomplete("#is_showStore", "../../queryMedVirStore.do?isCheck=false", "id", "text", true, true);
		autocomplete("#type_level", "../../queryMedTypeLevel_2.do?isCheck=false", "id", "text", true, true,'',true);
        $("#begin_confirm_date").ligerTextBox({width:100});
        $("#end_confirm_date").ligerTextBox({width:100});
        autodate("#begin_confirm_date", "yyyy-mm-dd", "month_first");
        autodate("#end_confirm_date", "yyyy-mm-dd", "month_last");
        $("#bill_no").ligerTextBox({width:100});
        $("#in_no").ligerTextBox({width:100});
        $("#sup_code").ligerTextBox({width:220});
	}  
	</script>
</head>

<body style="padding: 0px; overflow: hidden;"  >
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
	<div class="search-block clearfix">
		 <table>
		<tr>
			<td align="right" class="l-table-edit-td">入库日期： </td>
			<td align="left" class="l-table-edit-td"  width="20%">
				<table>
					<tr>
						<td>
							<input class="Wdate" name="begin_confirm_date" id="begin_confirm_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
						<td align="right" class="l-table-edit-td"  > 至 </td>
						<td>
							<input class="Wdate" name="end_confirm_date" id="end_confirm_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
            		</tr>
				</table>
	        </td>
	         <td align="right" class="l-table-edit-td" width="10%">虚仓名称：</td>
            <td align="left" class="l-table-edit-td" width="20%"> 
            	<input name="set_code" type="text" id="set_code" ltype="text" validate="{required:false}" />
  			</td> 
	        <td align="right" class="l-table-edit-td"  width="10%"> 仓库： </td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="store_code" type="text" id="store_code" ltype="text" validate="{required:false}" />
            </td>
		</tr>
		<tr>
	         <td align="right" class="l-table-edit-td"  width="10%"> 药品类别： </td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="med_type_code" type="text" id="med_type_code" ltype="text"   validate="{required:true,maxlength:20}" />
            </td>
             <td align="right" class="l-table-edit-td"  width="10%">是否收费： </td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="is_charge" type="text" id="is_charge" ltype="text" validate="{required:true,maxlength:20}" />
            </td>
            <td align="right" class="l-table-edit-td"  width="10%"><font color="red" size="2">*</font>类别级次：
			 </td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<select name="type_level"  id="type_level"  >
            			<option value = "1">一级</option>
            			<option value = "2">末级</option>
            		</select>
            </td>
		</tr> 
		 <tr>
		 	 <td align="right" class="l-table-edit-td" width="10%">供&nbsp;应&nbsp;商： </td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="sup_code" type="text" id="sup_code" ltype="text" validate="{required:false}" />
            </td>
            
		 </tr>
	</table>
	</div>
	<div id="maingrid"></div>
</body>
</html>
