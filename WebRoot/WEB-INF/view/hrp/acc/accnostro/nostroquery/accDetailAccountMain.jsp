<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
	<style >
	input::-webkit-outer-spin-button,
	input::-webkit-inner-spin-button {
	    -webkit-appearance: none;
	}
	 
	input[type="number"] {
	    -moz-appearance: textfield;
	
	}
	</style>
	<script>
    var grid;
    var gridManager = null;  
    var userUpdateStr;
    var year_month = '${yearMonth}';
    $(function ()
    {
		loadDict();
		loadForm(); 
    	loadHead(null);	//加载数据
    	
    });

  //验证
    function validate_check(){
		if(liger.get("subj_code").getValue()==""){
        	$.ligerDialog.error('请选择往来科目！');
        	return false;
        }
		
		if(liger.get("acc_year_month1").getValue()=="" || liger.get("acc_year_month2").getValue()==""){
        	$.ligerDialog.error('请选择会计期间！');
        	return false ;
        }
		
		if(liger.get("subj_code").getValue()!=""){
			//alert(liger.get("subj_code").getValue().split(".")[2]);
			
        	if(liger.get("subj_code").getValue().split(".")[2]=='1'){
        		//alert(liger.get("check_type").getValue());
        		if(liger.get("check_type").getValue()==""){
        			$.ligerDialog.error('请选往来类型！');
                	return false;
        		}
        	}
        }
		return true;
    }
    
    //查询
    function  query(){
    	if(validate_check()){
    		grid.options.parms=[];
        	grid.options.newPage=1;
    		
        	//根据表字段进行添加查询条件
        	grid.options.parms.push({name:'subj_code',value:liger.get("subj_code").getValue().split(".")[1]}); 
        	if(""==liger.get("check_type").getValue()){
    			grid.options.parms.push({name:'check_type',value:""}); 
    			grid.options.parms.push({name:'check_type_id',value:""});
            }else{
            	grid.options.parms.push({name:'check_type',value:liger.get("check_type").getValue().split(" ")[0]}); 
            	grid.options.parms.push({name:'check_type_id',value:liger.get("check_type").getValue().split(" ")[1]});
            }
        	
        	grid.options.parms.push({name:'acc_year_b',value:liger.get("acc_year_month1").getValue().split(".")[0]}); 
			grid.options.parms.push({name:'acc_month_b',value:liger.get("acc_year_month1").getValue().split(".")[1]}); 
			grid.options.parms.push({name:'acc_year_e',value:liger.get("acc_year_month2").getValue().split(".")[0]}); 
			grid.options.parms.push({name:'acc_month_e',value:liger.get("acc_year_month2").getValue().split(".")[1]});
        	if(""==liger.get("proj1").getValue()||""==liger.get("proj2").getValue()){
        		grid.options.parms.push({name:'proj1',value:""}); 
            	grid.options.parms.push({name:'proj2',value:""});
        	}else{
        		if(liger.get("check_type").getValue()=="ACC_CHECK_ITEM"){
        			grid.options.parms.push({name:'proj1',value:liger.get("proj1").getValue()}); 
                	grid.options.parms.push({name:'proj2',value:liger.get("proj2").getValue()});
        		}else{
        			grid.options.parms.push({name:'proj1',value:liger.get("proj1").getValue().split(".")[0]}); 
                	grid.options.parms.push({name:'proj2',value:liger.get("proj2").getValue().split(".")[0]});
        		}
        	} 
            
        	grid.options.parms.push({name:'vouch_no1',value:liger.get("vouch_no1").getValue()}); 
        	grid.options.parms.push({name:'vouch_no2',value:liger.get("vouch_no2").getValue()}); 
        	
        	grid.options.parms.push({name:'veriState',value:liger.get("veriState").getValue().split(".")[0]});
        	var is_check = 0;
        	if($("#is_check").is(":checked")){
        		is_check = 1;
        	}
        	grid.options.parms.push({name:'is_check',value:is_check});	
        	//加载查询条件
        	grid.loadData(grid.where);	
    	}
     }
  

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
					{ display: '日期',  align: 'left',
						columns:[
								{ display: '年', name: 'acc_year', align: 'left',width:60},
					            { display: '月', name: 'acc_month', align: 'left',width:60},
					            { display: '日', name: 'acc_day', align: 'left',width:60}
						]
					},
					{ display: '凭证号', name: 'vouch_no', align: 'left',width:100,
						render : function(rowdata, rowindex, value) {
							 if(rowdata.vouch_no !=null && rowdata.vouch_no !="-"){
								 return "<a href=javascript:openSuperVouch('"+rowdata.vouch_id+"')>"+rowdata.vouch_no+"</a>"; 
							 }
						 }
					},
					{ display: '项目编码', name: 'check_code', align: 'left',width:100},
					{ display: '项目名称', name: 'check_name', align: 'left',width:180},
					{ display: '摘要', name: 'summary', align: 'left',width:200},
					{ display: '核销状态', name: 'state', align: 'left',width:100},
					{ display: '合同号', name: 'con_no', align: 'left',width:100},
					{ display: '单据号', name: 'business_no', align: 'left',width:100},
					{ display: '发生日期', name: 'occur_date', align: 'left',width:100},
					{ display: '到期日期', name: 'due_date', align: 'left',width:100},
					{ display: '借方', name: 'debit', align: 'right',width:100,
						render:function(rowdata){
							var debit=(rowdata.debit ==null ? 0 : rowdata.debit);
							 return formatNumber(debit,2,1);
						 }
					},
					{ display: '贷方', name: 'credit', align: 'right',width:100,
						render:function(rowdata){
							var credit =(rowdata.credit ==null ? 0 : rowdata.credit);
							 return formatNumber(credit,2,1);
						 }
					},
					{ display: '方向', name: 'subj_dire', align: 'left',width:100,
					  render : function(rowdata, rowindex,value) {
									if(rowdata.subj_dire == 0){
										return "借";
									}else if(rowdata.subj_dire == 1){
										return "贷"
									}else{
										return "平"
									}
								}
					 },
					{ display: '余额', name: 'end_os', align: 'right',width:100,
						 render:function(rowdata){
								var end_os =(rowdata.end_os ==null ? 0 : rowdata.end_os);
								 return formatNumber(end_os,2,1);
							 }
					 }
           ],
           dataAction: 'server',dataType: 'server',usePager:true,url:'queryAccDetailAccountMain.do?isCheck=true',
           width: '100%', height: '100%', checkbox: false,rownumbers:true,delayLoad :true,
           selectRowButtonOnly:true,pageSizeOptions:[100, 200, 500],pageSize: 100,//heightDiff: -10,
           toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
    					{ text: '打印', id:'print', click: printData, icon:'print' },
    					{ line:true }/* ,
    					{ text: '导出', id:'export', click: exportData, icon:'export' },
    	                { line:true } */
    				]}
        });
        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function printData(){
    	
    	if(grid.getData().length==0){
			$.ligerDialog.error("请先查询数据！");
			return;
		 }
           	
    	/* var heads={
		  //"isAuto": true/false 默认true，页眉右上角默认显示页码
		  "rows": [
	      {"cell":0,"value":"科室名称："+liger.get("dept_id").getText(),"colSpan":"5"},
	      {"cell":3,"value":"项目名称："+liger.get("proj_id").getText(),"from":"right","align":"right","colSpan":"4"}
			  ]
		}; */
	
		var printPara={
				title: "明细账查询",//标题
				columns: JSON.stringify(grid.getPrintColumns()),//表头
				class_name: "com.chd.hrp.acc.service.verification.AccNostroService",
				method_name: "collectQueryAccDetailAccountPrint",
				bean_name: "accNostroService"/* ,
				heads: JSON.stringify(heads) *///表头需要打印的查询条件,可以为空
				/* foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 */
				};
		
		$.each(grid.options.parms,function(i,obj){
				printPara[obj.name]=obj.value;
		});
		
		officeGridPrint(printPara);
    }
    
    function exportData(){
    	
    }
    
    function openSuperVouch(vouch_id){
		parent.openFullDialog('hrp/acc/accvouch/superVouch/superVouchMainPage.do?vouch_id='+vouch_id,'会计凭证',0,0,true,true);
	}
    
    function loadDict(){
    	$("#vouch_no1").ligerTextBox({width:160});
        $("#vouch_no2").ligerTextBox({width:160});
        $("#proj1").ligerComboBox({ selectBoxWidth: 180, autocomplete: true, width: 180});
    	$("#proj2").ligerComboBox({ selectBoxWidth: 180, autocomplete: true, width: 180});
            //字典下拉框
            var param = {
            		SUBJ_NATURE_CODE1:'04',
            		SUBJ_NATURE_CODE2:'05',
            		is_last : 1
            	};
            var defaultSelect = true;
            $("#subj_code").ligerComboBox({
    		 	parms : param,
    	      	url: 'querySubjIsCheck.do?isCheck=false',
    	      	valueField: 'id',
    	       	textField: 'text', 
    	       	selectBoxWidth: subjWidth,
        		selectBoxHeight:'260',
    	      	autocomplete: true,
    	      	width: 245,
    	      	onSuccess : function (data){
    				var count = 0;
    				if(defaultSelect == true){
    					if(data.length >0 ){
    						if(data[0].id != undefined && data[0].id != "" && data[0].id != null){
    							defaultSelectValue = data[0].id;
    							if( count==0){
    								this.setValue(data[0].id);
    							}
    						}
    					}
    				}
    				count++;
    	       }

    		 });
    	 
    	 $("#acc_year_month1").ligerComboBox({
           	url: '../queryYearMonth.do?isCheck=false',
           	valueField: 'id',
            textField: 'text', 
            selectBoxWidth: 100,
           	autocomplete: true,
           	width: 100
  		  });
    	 
    	 liger.get("acc_year_month1").setValue(year_month);
 		 liger.get("acc_year_month1").setText(year_month);

    	 $("#acc_year_month2").ligerComboBox({
            	url: '../queryYearMonth.do?isCheck=false',
            	valueField: 'id',
             	textField: 'text', 
             	selectBoxWidth: 100,
            	autocomplete: true,
            	width: 100
   		  });
    	 liger.get("acc_year_month2").setValue(year_month);
 		 liger.get("acc_year_month2").setText(year_month);

    }  
    
    function subjChange(){
    	$("#proj1").val("");
    	$("#proj2").val("");
    	$("#check_type").val("");
		var subj_code = liger.get("subj_code").getValue();
		subj_code = subj_code.split(".")[1];
		var defaultSelect = true;
		if(!subj_code || subj_code == ""){
			return ;
		}
		$("#check_type").ligerComboBox({
		 	parms : {'subj_code':subj_code},
	      	url: '../queryCheckTypeBySubjId.do?isCheck=false',
	      	valueField: 'check_table',
	       	textField: 'check_type_name', 
	       	selectBoxWidth: 180,
	      	autocomplete: true,
	      	width: 180
		 });
		
	}
    
    function checkTypeChange(){
    	$("#proj1").val("");
    	$("#proj2").val("");
    	var check_type_code = liger.get("check_type").getValue().split(" ")[0];
    	var check_type_id = liger.get("check_type").getValue().split(" ")[1];
    	$("#proj1").ligerComboBox({selectBoxWidth: 180,autocomplete: true,width: 180});
    	$("#proj2").ligerComboBox({selectBoxWidth: 180,autocomplete: true,width: 180 });
    	switch (check_type_code){
			case "HOS_DEPT_DICT":
				$("#proj1").ligerComboBox({
			      	url: '../../sys/queryDeptDict.do?isCheck=false',
			      	valueField: 'id',textField: 'text'
				 });
				$("#proj2").ligerComboBox({
			      	url: '../../sys/queryDeptDict.do?isCheck=false',
			      	valueField: 'id',textField: 'text'
				 });
				return;
			case "HOS_EMP_DICT":
				$("#proj1").ligerComboBox({
			      	url: '../../sys/queryEmp.do?isCheck=false',
			      	valueField: 'id',textField: 'text'
				 });
				$("#proj2").ligerComboBox({
			      	url: '../../sys/queryEmp.do?isCheck=false',
			      	valueField: 'id',textField: 'text'
				 });
				return;
			case "HOS_PROJ_DICT":
				$("#proj1").ligerComboBox({
			      	url: '../../sys/queryProjDictDict.do?isCheck=false',
			      	valueField: 'id',textField: 'text'
				 });
				$("#proj2").ligerComboBox({
			      	url: '../../sys/queryProjDictDict.do?isCheck=false',
			      	valueField: 'id',textField: 'text'
				 });
				return;
			case "HOS_STORE_DICT":
				$("#proj1").ligerComboBox({
			      	url: '../../sys/queryStoreDictDict.do?isCheck=false',
			      	valueField: 'id',textField: 'text'
				 });
				$("#proj2").ligerComboBox({
			      	url: '../../sys/queryStoreDictDict.do?isCheck=false',
			      	valueField: 'id',textField: 'text'
				 });
				return;
			case "HOS_CUS_DICT":
				$("#proj1").ligerComboBox({
			      	url: '../../sys/queryCusDict.do?isCheck=false',
			      	valueField: 'id',textField: 'text'
				 });
				$("#proj2").ligerComboBox({
			      	url: '../../sys/queryCusDict.do?isCheck=false',
			      	valueField: 'id',textField: 'text'
				 });
				return;
			case "HOS_SUP_DICT":
				$("#proj1").ligerComboBox({
			      	url: '../../sys/querySupDictDict.do?isCheck=false',
			      	valueField: 'id',textField: 'text'
				 });
				$("#proj2").ligerComboBox({
			      	url: '../../sys/querySupDictDict.do?isCheck=false',
			      	valueField: 'id',textField: 'text'
				 });
				return;
			case "HOS_SOURCE":
				$("#proj1").ligerComboBox({
			      	url: '../../sys/querySourceDict.do?isCheck=false',
			      	valueField: 'id',textField: 'text'
				 });
				$("#proj2").ligerComboBox({
			      	url: '../../sys/querySourceDict.do?isCheck=false',
			      	valueField: 'id',textField: 'text'
				 });
				return;
			case "V_HOS_DICT":
				$("#proj1").ligerComboBox({
			      	url: 'queryVHosDict.do?isCheck=false',
			      	valueField: 'id',textField: 'text'
				 });
				$("#proj2").ligerComboBox({
			      	url: 'queryVHosDict.do?isCheck=false',
			      	valueField: 'id',textField: 'text'
				 });
				return;
			case "ACC_CHECK_ITEM"://自定义核算
				$("#proj1").ligerComboBox({
			      	url: '../queryCheckItem.do?isCheck=false',
			      	valueField: 'id',textField: 'text',parms : {'check_type_id':check_type_id},
				 });
				$("#proj2").ligerComboBox({
			      	url: '../queryCheckItem.do?isCheck=false',
			      	valueField: 'id',textField: 'text',parms : {'check_type_id':check_type_id},
				 });
				return;
		}
    }
    
    function loadForm(){
   	 	$("form").ligerForm();      
    }  
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar" ></div>
	<form name="form1" method="post"  id="form1" >
    <table cellpadding="0" cellspacing="0" class="l-table-edit" border="0">
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:10px;">往来科目：</td>
            <td align="left" class="l-table-edit-td" colspan="3">
            	<input name="subj_code" type="text"  id="subj_code" onchange="subjChange();" />
            </td>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:10px;">往来类型：</td>
            <td align="left" class="l-table-edit-td" >
            	<input name="check_type" type="text" id="check_type"   onchange="checkTypeChange();" />
            </td>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:10px;" >往来项目：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="proj1" type="text" id="proj1"   /></td>
            <td align="right"  class="l-table-edit-td" style="width：10px;">至：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="proj2" type="text" id="proj2"   />
            </td>
            
        </tr> 
         <tr>
         	<td align="right" class="l-table-edit-td"  style="padding-left:10px;">会计期间：</td>
            <td align="left" class="l-table-edit-td" ><input name="acc_year_month1" type="text" id="acc_year_month1"   /></td>
            <td align="left" class="l-table-edit-td">至：</td>
            <td align="left" class="l-table-edit-td"><input name="acc_year_month2" type="text" id="acc_year_month2"  /></td>
            
            <td align="left" class="l-table-edit-td"  style="padding-left:10px;">核销状态：</td>
            <td align="left" class="l-table-edit-td" >
            	<select id="veriState" name="veriState">
            		<option value='0'>全部</option>
            		<option value='1'>未核销</option>
            		<!-- <option value='2'>部分核销</option> -->
            		<option value='2'>已核销</option>
            	</select>
            </td>
            
            <td align="left" class="l-table-edit-td"  style="padding-left:10px;">凭证号：</td>
            <td align="left" class="l-table-edit-td"  ><input name="vouch_no1" type="number" id="vouch_no1" ltype="text"  onkeypress="return (/[\d]/.test(String.fromCharCode(event.keyCode)))"  /></td>
            <td align="left" class="l-table-edit-td">至：</td>
            <td align="left" class="l-table-edit-td"><input name="vouch_no2" type="number" id="vouch_no2"  ltype="text"  onkeypress="return (/[\d]/.test(String.fromCharCode(event.keyCode)))" /></td>
            
            
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td" ><input name="is_check" type="checkbox" id="is_check"   /></td>
            <td align="left" class="l-table-edit-td">含未记账</td>
        </tr>
	 
    </table>
	</form>
	<div id="maingrid"></div>

</body>
</html>
