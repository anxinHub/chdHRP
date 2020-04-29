<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">
	
    var grid;
    
    var gridManager = null;
    
    var selectOption;  //occ_date,aff_date,
    
    var initColumu,initColumus;
    
    var userUpdateStr;
    
    $(function ()
    {
		loadDict();
    	
    	loadHead(null);	//加载数据
    	
    	$("#occ_date_state").ligerTextBox({width:120});
    	$("#occ_date_end").ligerTextBox({width:120});
		var acc_month=getMonthDate('${acc_year}','${acc_month}');
		$("#occ_date_state").val(acc_month.split(";")[0]);
		$("#occ_date_end").val(acc_month.split(";")[1]); 
		$("#aff_date_state").ligerTextBox({width:120});
    	$("#aff_date_end").ligerTextBox({width:120});
		//var acc_month=getMonthDate('${acc_year}','${acc_month}');
    	
    });
    //查询
    function  query(){
   		grid.options.parms=[];
   		grid.options.newPage=1;

        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'occ_date_state',value:$("#occ_date_state").val()}); 
    	grid.options.parms.push({name:'occ_date_end',value:$("#occ_date_end").val()}); 
    	grid.options.parms.push({name:'aff_date_state',value:$("#aff_date_state").val()}); 
    	grid.options.parms.push({name:'aff_date_end',value:$("#aff_date_end").val()}); 
    	grid.options.parms.push({name:'debt_comp',value:liger.get("debt_comp").getValue().split(".")[0]}); 
    	grid.options.parms.push({name:'resp_dept',value:liger.get("resp_dept").getValue()}); 
    	grid.options.parms.push({name:'debt_money_state',value:liger.get("debt_money_state").getValue()}); 
    	grid.options.parms.push({name:'debt_money_end',value:liger.get("debt_money_end").getValue()}); 
    	grid.options.parms.push({name:'aff_no',value:liger.get("aff_no").getValue()}); 
    	
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '序号', name: 'SEQNO', align: 'left'
					 },
                     { display: '债务单位', name: 'DEBT_COMP', align: 'left',
						 editor: {
								type: 'text'
						 }
					 },
                     { display: '原列科目', name: 'ACC_SUBJ_CODE', align: 'left',width: 350,isSort: false,
						 editor: { type: 'select', data: selectOption, valueField: 'ACC_SUBJ_CODE', textField: 'ACC_SUBJ_NAME' }, render: function (item)
		                    {
		                        for (var i = 0; i < selectOption.length; i++)
		                        {
		                            if (selectOption[i]['ACC_SUBJ_CODE'] == item.ACC_SUBJ_CODE)
		                                return selectOption[i]['ACC_SUBJ_NAME']
		                        }
		                        return item.selectOption;
		                    }   
					 },
                     { display: '清欠责任人部门', name: 'RESP_DEPT', align: 'right',
						 editor: {
								type: 'text'
						 }
					 },
					 { display: '债务金额', name: 'DEBT_MONEY', align: 'right',
						 editor: {
								type: 'numberbox',
								precision: 2
							},
							render: function(item)
				            {
				                    return formatNumber(item.DEBT_MONEY,2,2);
				            },
					 },
					 { display: '发生日期', name: 'OCC_DATE', align: 'right',format: 'yyyy-MM-dd', type:"date",
						 editor: {
								type: 'date',showSelectBox:false
						 },
					 },
					 { display: '核销日期', name: 'AFF_DATE', align: 'right',format: 'yyyy-MM-dd', type:"date",
						 editor: {
								type: 'date',showSelectBox:false
						 },
					 },
					 { display: '核销金额', name: 'AFF_MONEY', align: 'right',
						 editor: {
								type: 'numberbox',
								precision: 2
							},
							render: function(item)
				            {
				                    return formatNumber(item.AFF_MONEY,2,2);
				            },
					 },
					 { display: '收回金额', name: 'REGET_MONEY', align: 'right',
						 editor: {
								type: 'numberbox',
								precision: 2
							},
							render: function(item)
				            {
				                    return formatNumber(item.REGET_MONEY,2,2);
				            },
					 },
					 { display: '未收金额', name: 'NOGET_MONEY', align: 'right',
							editor: {
								type: 'numberbox',
								precision: 2
							},
							render: function(item)
				            {
				                    return formatNumber(item.NOGET_MONEY,2,2);
				            },
					 },
					 { display: '批准文号', name: 'AFF_NO', align: 'right',
						 editor: {
								type: 'text'
							}
					 },
					 { display: '备注', name: 'NOTE', align: 'right',
						 editor: {
								type: 'text',showSelectBox:false
						 },
					 },
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:"queryBadDebts.do",
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad:true,enabledEdit:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
    	                { line:true },
    	                { text: '添加', id:'search', click: add,icon:'add' },
    	                { line:true },
    	                { text: '删除', id:'delete', click: deletes,icon:'delete' },
    	                { line:true },
    					{ text: '保存', id:'add', click: save, icon:'save' },
                     	{ line:true },
    	                { text: '导入', id:'print', click: imports,icon:'print' },
    	                { line:true },
    	                { text: '打印', id:'print', click: print,icon:'print' },
    	                { line:true }
    				]}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function save(){
    	
    	var paramarr = grid.getData();
    	var param = [];
    	var code = false;
    	
    	if(paramarr == null || paramarr.length == 0){
    		$.ligerDialog.error("当前没有数据可以保存!");
    		return false;
    	}
    	
    	$.each(paramarr,function(){
    		
    		if(!this.DEBT_COMP){
    			$.ligerDialog.error("债务单位不能为空!");
    			code = true;
    			return false;
    		}else if(!this.ACC_SUBJ_CODE){
    			$.ligerDialog.error("原列科目不能为空!");
    			code = true;
    			return false;
    		}else if(!this.RESP_DEPT){
    			$.ligerDialog.error("清欠责任人不能为空!");
    			code = true;
    			return false;
    		}else if(!this.DEBT_MONEY){
    			$.ligerDialog.error("债务金额不能为空!");
    			code = true;
    			return false;
    		}else if(!this.OCC_DATE){
    			$.ligerDialog.error("发生日期不能为空!");
    			code = true;
    			return false;
    		}
    		param.push({"DEBT_COMP":this.DEBT_COMP},
    				   {"ACC_SUBJ_CODE":this.DEBT_COMP},
    				   {"RESP_DEPT":this.DEBT_COMP},
    				   {"DEBT_MONEY":this.DEBT_COMP},
    				   {"OCC_DATE":this.DEBT_COMP},
    				   {"AFF_DATE":this.DEBT_COMP},
    				   {"AFF_MONEY":this.DEBT_COMP},
    				   {"REGET_MONEY":this.DEBT_COMP},
    				   {"NOGET_MONEY":this.DEBT_COMP},
    				   {"AFF_NO":this.DEBT_COMP},
    				   {"NOTE":this.DEBT_COMP}
    		)		
    	})
    	
    	if(code){
    		return;
    	}
    	
    	ajaxJsonObjectByUrl("addBadDebts.do",{"parmarr":JSON.stringify(grid.getData())},function(){
	    });
    }
    
    function add(){
    	grid.addRow({"SEQNO":"自动生成"});
    }

    function deletes(){
    	var getrow = grid.getCheckedRows();
    	if(getrow != null && getrow.length != 0){
        	$.ligerDialog.confirm('确定要删除吗?', 
    	    		function (yes){
    	    			if(yes){
    	    				grid.deleteRange(getrow)
    	    			}						
    	    		}				
    	    );
    	}else{
    		$.ligerDialog.error('请选择行!')
    	}
    }
    
    function loadDict(){
    	
    	ajaxJsonObjectByUrl("queryAccSubjSelect.do?isCheck=false",null,function(responseJson){
    		selectOption = responseJson;
	    },false);
    	
        //会计期间
     	/* occ_date = $("#occ_date").etDatepicker({
     		range: true,
        	defaultDate: ['yyyy-mm-fd', 'yyyy-mm-ed']
   		});
        
     	//会计期间
     	aff_date = $("#aff_date").etDatepicker({
     		range: true,
        	defaultDate: ['yyyy-mm-fd', 'yyyy-mm-ed']
   		}); */
   		
     	$("#debt_comp").ligerTextBox({width : 180});
     	$("#resp_dept").ligerTextBox({width : 256});
     	$("#debt_money_state").ligerTextBox({width : 73});
     	$("#debt_money_end").ligerTextBox({width : 73});
     	$("#aff_no").ligerTextBox({width : 256});
    }   
    
    //导入所需表头
    function imports(){
    	
    	var paraLedger = {
    			"column" : [
    				{ "name" : "DEBT_COMP", "display" : "债务单位", "width" : "", "require" : true },
    				{ "name" : "ACC_SUBJ_CODE", "display" : "原科目编码", "width" : "", "require" : true },
    				{ "name" : "ACC_SUBJ_NAME", "display" : "原科目名称", "width" : "", "require" : true },
    				{ "name" : "RESP_DEPT", "display" : "清欠责任人及部门", "width" : "", "require" : true },
    				{ "name" : "DEBT_MONEY", "display" : "债务金额", "width" : "", "require" : true },
    				{ "name" : "OCC_DATE", "display" : "发生日期", "width" : "", "require" : true},
    				{ "name" : "AFF_DATE", "display" : "核销日期", "width" : "", "require" : false},
    				{ "name" : "AFF_MONEY", "display" : "核销金额", "width" : "", "require" : false },
    				{ "name" : "REGET_MONEY", "display" : "收回金额", "width" : "", "require" : false },
    				{ "name" : "NOGET_MONEY", "display" : "未收金额", "width" : "", "require" : false },
    				{ "name" : "AFF_NO", "display" : "批准文号", "width" : "", "require" : false },
    				{ "name" : "NOTE", "display" : "备注", "width" : "", "require" : false }] }
    				
    	importSpreadView("/hrp/acc/accBadDebts/importBadDebts.do", paraLedger, query());

	}

	function print() {

		if(grid.getData().length==0){
			$.ligerDialog.error("请先查询数据！");
			return;
		}
    	
    	var heads={};
           	
    	var printPara={
       			title: "期初借款",//标题
       			columns: JSON.stringify(grid.getPrintColumns()),//表头
       			class_name: "com.chd.hrp.acc.service.AccBadDebtsService",
    			method_name: "queryBadDebtsPrint",
    			bean_name: "accBadDebtsService",
    			heads: JSON.stringify(heads)//表头需要打印的查询条件,可以为空
    			/* foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 */
       		};
    	
    	$.each(grid.options.parms,function(i,obj){
   			printPara[obj.name]=obj.value;
    	});
    	
    	officeGridPrint(printPara)

	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit">
	
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">发生日期：</td>
        	<td>
				<table>
					<tr>
		      		<td align="left" class="l-table-edit-td l-table-edit"><input class="Wdate" name="occ_date_state" type="text" id="occ_date_state" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" ltype="text"/></td>
					<td align="left" >-</td>
				    <td align="left" class="l-table-edit-td l-table-edit"><input class="Wdate" name="occ_date_end" type="text" id="occ_date_end" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" ltype="text"/></td>
		            <td align="left"></td> 
            		</tr>
				</table>
			</td>	
			<td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">核销日期：</td>
           	<td>
				<table>
					<tr>
		            <td align="left" class="l-table-edit-td l-table-edit"><input class="Wdate" name="aff_date_state" type="text" id="aff_date_state" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" ltype="text"/></td>
					<td align="left" >-</td>
				    <td align="left" class="l-table-edit-td l-table-edit"><input class="Wdate" name="aff_date_end" type="text" id="aff_date_end" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" ltype="text"/></td>
		            <td align="left"></td>
		            </tr>
				</table>
			</td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">债务单位：</td>
            <td align="left" class="l-table-edit-td"><input name="debt_comp" type="text" id="debt_comp" ltype="text" validate="{required:true,maxlength:20}" style="width: 180px;"/></td>
        </tr> 
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;" >责任人/部门：</td>
            <td align="left" class="l-table-edit-td" ><input name="resp_dept" type="text" id="resp_dept" ltype="text" validate="{required:true,maxlength:20}" style="width: 180px;"/></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">批准文号：</td>
            <td align="left" class="l-table-edit-td" ><input name="aff_no" type="text" id="aff_no" ltype="text" validate="{required:true,maxlength:20}" style="width: 180px;"/></td>
            <td align="right" class="l-table-edit-td" style="padding-left: 20px;">债务金额</td>
			<td>
				<table>
					<tr>
						<td align="left" class="l-table-edit-td">
							<input name="debt_money_state" type="text" id="debt_money_state" validate="{required:true,maxlength:20}" />
						</td>
						<td align="left" class="l-table-edit-td">至</td>
						<td align="left" class="l-table-edit-td">
							<input name="debt_money_end" type="text"  id="debt_money_end" validate="{required:true,maxlength:20}"  />
						</td>
						<td align="left"></td>
					</tr>
				</table>
			</td>	
        </tr> 
	
    </table>

	<div id="maingrid"></div>

</body>
</html>
