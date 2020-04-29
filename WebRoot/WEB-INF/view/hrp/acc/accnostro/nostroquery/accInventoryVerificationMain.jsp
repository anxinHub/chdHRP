<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script>
    var grid;      
    var gridManager = null;  
    var userUpdateStr;
    var flag = false;
    var year_month = '${yearMonth}';
    $(function (){
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
			//alert(liger.get("subj_code").getValue().split(".")[2])
        	if(liger.get("subj_code").getValue().split(".")[2]=='1'){
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
    	debugger;
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
        	
        	
        	grid.options.parms.push({name:'vouch_no1',value:liger.get("vouch_no1").getValue()}); 
        	grid.options.parms.push({name:'vouch_no2',value:liger.get("vouch_no2").getValue()}); 
        	if(""==liger.get("proj1").getValue()||""==liger.get("proj2").getValue()){
        		grid.options.parms.push({name:'proj1',value:""}); 
            	grid.options.parms.push({name:'proj2',value:""});
        	}else{
        		if(liger.get("check_type").getValue()=="ACC_CHECK_ITEM"){
        			grid.options.parms.push({name:'proj1',value:liger.get("proj1").getValue()}); 
                	grid.options.parms.push({name:'proj2',value:liger.get("proj2").getValue()});
        		}else{
        			grid.options.parms.push({name:'proj1',value:liger.get("proj1").getText().split(" ")[0]}); 
                	grid.options.parms.push({name:'proj2',value:liger.get("proj2").getText().split(" ")[0]});
        		}
        	} 
        	
        	
        	grid.options.parms.push({name:'veriState',value:liger.get("veriState").getValue()});
        	var is_check = 0;
        	if($("#is_check").attr("checked") == true){
        		is_check = 1;
        	}
        	grid.options.parms.push({name:'is_check',value:is_check});
        	
        	//加载查询条件
        	grid.loadData(grid.where);
    	}
    	
     }
  

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [{ display: '日期', align: 'left',
						 columns:[{ display: '年', name: 'acc_year', align: 'left',width:60},
					              { display: '月', name: 'acc_month', align: 'left',width:60,
									 render : function(rowdata, rowindex, value) {
											if(rowdata.acc_month == '99'){
												return "";
											}else{
												return rowdata.acc_month;
											}
										}
					              },
					              { display: '日', name: 'acc_day', align: 'left',width:60}
					 			 ]
					 },
					 { display: '凭证号', name: 'vouch_no', align: 'left',width:100,
						 render : function(rowdata, rowindex, value) {
							 if(rowdata.vouch_no !=null && rowdata.vouch_no !="-"){
								 return "<a href=javascript:openSuperVouch('"+rowdata.vouch_id+"')>"+rowdata.vouch_no+"</a>"; 
							 }
						 }		 
					 },/* 添加凭证号链接 */
                     { display: '项目编码', name: 'check_code', align: 'left',width:100},
                     { display: '项目名称', name: 'check_name', align: 'left',width:180},
					 { display: '摘要', name: 'summary', align: 'left',width:100},
					 { display: '核销状态', name: 'state', align: 'left',width:100},
					 { display: '合同号', name: 'con_no', align: 'left',width:100},
					 { display: '单据号', name: 'business_no', align: 'left',width:100},
					 { display: '发生日期', name: 'occur_date', align: 'left',width:100},
					 { display: '到期日期', name: 'due_date', align: 'left',width:100},
					 { display: '借方', name: 'debit', align: 'right',width:100,
						 render:function(rowdata){
							 return formatNumber(rowdata.debit ==null ? 0 : rowdata.debit,2,1);
						 	 }	 
					 },
					 { display: '贷方', name: 'credit', align: 'right',width:100,
						 render:function(rowdata){
							 return formatNumber(rowdata.credit ==null ? 0 : rowdata.credit,2,1);
					 	 }
					 },
                     { display: '已核销金额', name: 'ybal_amt', align: 'right',width:100,
						 render:function(rowdata){
							 if(rowdata.check_id == '0'){
								 return formatNumber(rowdata.ybal_amt ==null ? 0 : rowdata.ybal_amt,2,1);
							}else{
								if(rowdata.ybal_amt != 0){
									var check_type = liger.get("check_type").getValue().split(" ")[0];
									var check_type_id = liger.get("check_type").getValue().split(" ")[1];
									return "<a href=javascript:openUpdate('"+rowdata.check_id+"|"+
											check_type+"|"+
											check_type_id+"|"+
											rowdata.subj_dire+"|"+
											rowdata.check_code+"|"+
											rowdata.veri_check_id+
									"')>"+formatNumber(rowdata.ybal_amt ==null ? 0 : rowdata.ybal_amt,2,1)+"</a>";	
								}else{
									return "0.00";
								}
								
							}
						 }
                     },/* 添加已核销链接 */
                     { display: '未核销金额', name: 'wbal_amt', align: 'right',width:100,
                    	 render:function(rowdata){
                    		 return formatNumber(rowdata.wbal_amt ==null ? 0 : rowdata.wbal_amt,2,1);
						 	 }	 
                     },{ display: '方向', name: 'subj_dire'},
                     { display: '核算ID', name: 'check_id'},
                     { display: '核销ID', name: 'veri_check_id'}
                   ],
                   dataAction: 'server',dataType: 'server',usePager:true,
                   url:'queryInventoryVerification.do?isCheck=true',
                   width: '100%', height: '100%', checkbox: false,rownumbers:true,delayLoad :true,isScroll:true,
                   selectRowButtonOnly:true,//heightDiff: -10,
                   toolbar: { items: [
                     	{ text: '查询', id:'search', click: itemclick,icon:'search' },
                     	{ line:true },
                     	{ text: '打印', id:'print', click: printData, icon:'print' },
    					{ line:true }
    					/* { text: '导出', id:'Excel', click: itemclick, icon:'' },
    	                { line:true } */
    				]}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
        grid.toggleCol("subj_dire", false);
        grid.toggleCol("check_id", false);
        grid.toggleCol("veri_check_id", false);
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
				title: "核销清册",//标题
				columns: JSON.stringify(grid.getPrintColumns()),//表头
				class_name: "com.chd.hrp.acc.service.verification.AccNostroService",
				method_name: "queryAccVerificationDetailPrint",
				bean_name: "accNostroService"/* ,
				heads: JSON.stringify(heads) *///表头需要打印的查询条件,可以为空
				/* foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 */
				};
		
		$.each(grid.options.parms,function(i,obj){
				printPara[obj.name]=obj.value;
		});
		
		officeGridPrint(printPara);
    }
    function openSuperVouch(vouch_id){
		parent.openFullDialog('hrp/acc/accvouch/superVouch/superVouchMainPage.do?vouch_id='+vouch_id,'会计凭证',0,0,true,true);
	}
    
    //打开已核销页面
    function openUpdate(obj){
    	var vo = obj.split("|");
		var parm = "check_id="+vo[0]+
			"&check_type="+vo[1]+
			"&check_type_id="+vo[2]+
			"&subj_dire="+vo[3]+
			"&check_code="+vo[4]+
			"&veri_check_id="+vo[5]+
			"&subj_code="+liger.get("subj_code").getValue().split(".")[1]; 
		
    	$.ligerDialog.open({ url : 'accInventoryVerificationQetail.do?isCheck=false&' + parm,data:{}, 
    			height: 500,width: 900, title:'已核销金额',modal:true,showToggle:false,showMax:false,
    			showMin: false,isResize:true,
    			buttons: [ 
    				{ text: '关闭', onclick: function (item, dialog) { dialog.close(); } } 
    			] 
    	});
    }
    
    
	function itemclick(item){ 
        if(item.id){
            switch (item.id){
                case "search":
                	query();
              		return;
                case "modify":
                    return;
                case "delete":
                case "Excel":
                case "Word":
                case "PDF":
                case "TXT":
                case "XML":
            }   
        }
        
    }
    function loadDict(){
    	$("#vouch_no1").ligerTextBox({width:160});
        $("#vouch_no2").ligerTextBox({width:160});
        
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
		$("#check_type").ligerComboBox({
		 	parms : {'subj_code':subj_code},
	      	url: '../queryCheckTypeBySubjId.do?isCheck=false',
	      	valueField: 'check_table',
	       	textField: 'check_type_name', 
	       	selectBoxWidth: 180,
	      	autocomplete: true,
	      	width: 180,
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
			      	valueField: 'id', 	textField: 'text', autocomplete: true
				 });
				$("#proj2").ligerComboBox({
			      	url: '../../sys/queryDeptDict.do?isCheck=false',
			      	valueField: 'id', 	textField: 'text', autocomplete: true
				 });
				return;
			case "HOS_EMP_DICT":
				$("#proj1").ligerComboBox({
			      	url: '../../sys/queryEmp.do?isCheck=false',
			      	valueField: 'id', 	textField: 'text', autocomplete: true
				 });
				$("#proj2").ligerComboBox({
			      	url: '../../sys/queryEmp.do?isCheck=false',
			      	valueField: 'id', 	textField: 'text', autocomplete: true
				 });
				return;
			case "HOS_PROJ_DICT":
				$("#proj1").ligerComboBox({
			      	url: '../../sys/queryProjDictDict.do?isCheck=false',
			      	valueField: 'id', 	textField: 'text', autocomplete: true
				 });
				$("#proj2").ligerComboBox({
			      	url: '../../sys/queryProjDictDict.do?isCheck=false',
			      	valueField: 'id', 	textField: 'text', autocomplete: true
				 });
				return;
			case "HOS_STORE_DICT":
				$("#proj1").ligerComboBox({
			      	url: '../../sys/queryStoreDictDict.do?isCheck=false',
			      	valueField: 'id', 	textField: 'text', autocomplete: true
				 });
				$("#proj2").ligerComboBox({
			      	url: '../../sys/queryStoreDictDict.do?isCheck=false',
			      	valueField: 'id', 	textField: 'text', autocomplete: true
				 });
				return;
			case "HOS_CUS_DICT":
				$("#proj1").ligerComboBox({
			      	url: '../../sys/queryCusDict.do?isCheck=false',
			      	valueField: 'id', 	textField: 'text', autocomplete: true
				 });
				$("#proj2").ligerComboBox({
			      	url: '../../sys/queryCusDict.do?isCheck=false',
			      	valueField: 'id', 	textField: 'text', autocomplete: true
				 });
				return;
			case "HOS_SUP_DICT":
				$("#proj1").ligerComboBox({
			      	url: '../../sys/querySupDictDict.do?isCheck=false',
			      	valueField: 'id', 	textField: 'text', autocomplete: true
				 });
				$("#proj2").ligerComboBox({
			      	url: '../../sys/querySupDictDict.do?isCheck=false',
			      	valueField: 'id', 	textField: 'text', autocomplete: true
				 });
				return;
			case "HOS_SOURCE":
				$("#proj1").ligerComboBox({ url: '../../sys/querySourceDict.do?isCheck=false',
					valueField: 'id', 	textField: 'text', autocomplete: true
				 });
				$("#proj2").ligerComboBox({ url: '../../sys/querySourceDict.do?isCheck=false',
			      	valueField: 'id', 	textField: 'text', autocomplete: true
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
	            <td align="left" class="l-table-edit-td" colspan="3"><input name="subj_code" type="text"  id="subj_code" onchange="subjChange();" /></td>
	            
	            <td align="right" class="l-table-edit-td"  style="padding-left:10px;">往来类型：</td>
	            <td align="left" class="l-table-edit-td" ><input name="check_type" type="text" id="check_type"   onchange="checkTypeChange();" /></td>
	            
	            <td align="right" class="l-table-edit-td"  style="padding-left:10px;" >往来项目：</td>
	            <td align="left" class="l-table-edit-td"><input name="proj1" type="text" id="proj1"   /></td>
	            <td align="left"  class="l-table-edit-td">至：</td>
	            <td align="left" class="l-table-edit-td"><input name="proj2" type="text" id="proj2"   /></td>
	        </tr> 
	         <tr>
	         	<td align="right" class="l-table-edit-td"  style="padding-left:10px;">会计期间：</td>
	            <td align="left" class="l-table-edit-td" ><input name="acc_year_month1" type="text" id="acc_year_month1"   /></td>
	            <td align="left" class="l-table-edit-td">至：</td>
	            <td align="left" class="l-table-edit-td"><input name="acc_year_month2" type="text" id="acc_year_month2"  /></td>
	            
	            <td align="right" class="l-table-edit-td"  style="padding-left:10px;">核销状态：</td>
	            <td align="left" class="l-table-edit-td" >
	            	<select id="veriState" name="veriState">
	            		<option value='0'>全部</option>
	            		<option value='1'>未核销</option>
	            		<!-- <option value='2'>部分核销</option> -->
	            		<option value='2'>已核销</option>
	            	</select>
	            </td>
	            
	            <td align="right" class="l-table-edit-td"  style="padding-left:10px;">凭证号：</td>
	            <td align="left" class="l-table-edit-td"  ><input name="vouch_no1" type="text" id="vouch_no1" ltype="text"   /></td>
	            <td align="left" class="l-table-edit-td">至：</td>
	            <td align="left" class="l-table-edit-td"><input name="vouch_no2" type="text" id="vouch_no2"  ltype="text"  /></td>
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
