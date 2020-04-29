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
    var year_Month = '${yearMonth}';
    var is_check = 0;
	var showItem = 0; 
    $(function ()
    {
    	//loadForm();
		loadDict();
    	
    	loadHead(null);	//加载数据
    	
    	$("#subj_code").bind("change",function(){
    		subjChange();
    	});
    	
    });
    
    //验证
    function validate_check(){
		if(liger.get("subj_code").getValue()=="" || liger.get("subj_code").getValue() == null){
        	$.ligerDialog.error('请选择往来科目！');
        	return false;
        }else{
        	if(liger.get("subj_code").getValue().split(".")[2]=='1'){
        		if(liger.get("check_type").getValue() == null || liger.get("check_type").getValue() == ""){
        			$.ligerDialog.error('请选往来类型！');
                	return false;
        		}
        	}
        }
		
		if(liger.get("acc_year_month").getValue()=="" || liger.get("acc_year_month").getValue() == null){
        	$.ligerDialog.error('请选择会计期间！');
        	return false ;
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
        	if(liger.get("check_type").getValue()){
        		grid.options.parms.push({name:'check_type',value:liger.get("check_type").getValue().split(" ")[0]}); 
            	grid.options.parms.push({name:'check_type_id',value:liger.get("check_type").getValue().split(" ")[1]});
        		
            }else{
            	grid.options.parms.push({name:'check_type',value:""}); 
    			grid.options.parms.push({name:'check_type_id',value:""});
            }
        	grid.options.parms.push({name:'acc_year',value:liger.get("acc_year_month").getValue().split(".")[0]}); 
        	grid.options.parms.push({name:'acc_month',value:liger.get("acc_year_month").getValue().split(".")[1]});
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
        	
        	grid.options.parms.push({name:'month',value:year_Month.substring(4,6)});
        	
        	
        	if($("#is_check").attr("checked") == true){
        		is_check = 1;
        	}else{
        		is_check = 0;
        	}
        	if($("#showItem").attr("checked") == true){
        		showItem = 1;
        	}else{
        		showItem = 0;
        	}
        	
        	grid.options.parms.push({name:'is_check',value:is_check});
        	grid.options.parms.push({name:'showItem',value:showItem}); 
        	//加载查询条件
        	grid.loadData(grid.where);
    	}
     }
  

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '项目编码', name: 'check_code', align: 'left' ,width:100, 
		                    totalSummary:{
		                        type: 'sum',
		                        render: function (suminf, column, cell){
		                            return '<div>合计</div>';
		                        }
		                    }
					 },
                     { display: '项目名称', name: 'check_name', align: 'left', width:200},
					 { display: '期初余额', name: 'bal_os', align: 'right',
						 render:function(rowdata){
							 return formatNumber(rowdata.bal_os,2,1);
						 },
		                 totalSummary:{
		                 	type: 'sum',
		                    render: function (suminf, column, cell){
		                    	return '<div>' + formatNumber(suminf.sum ,2,1) + '</div>';
		                    }
		                 }
					 },
					 { display: '本期借方', name: 'this_od', align: 'right',
						 render:function(rowdata){
							 return formatNumber(rowdata.this_od,2,1);
						 },
		                 totalSummary:{
		                 	type: 'sum',
		                    render: function (suminf, column, cell){
		                    	return '<div>' + formatNumber(suminf.sum ,2,1) + '</div>';
		                    }
		                }
					 },
					 { display: '累计借方', name: 'sum_od', align: 'right',
						 render:function(rowdata){
							 return formatNumber(rowdata.sum_od,2,1);
						 },
		                 totalSummary:{
		                 	type: 'sum',
		                    render: function (suminf, column, cell){
		                    	return '<div>' + formatNumber(suminf.sum ,2,1) + '</div>';
		                    }
		                 }
					 },
					 { display: '本期贷方', name: 'this_oc', align: 'right',
						 render:function(rowdata){
							 return formatNumber(rowdata.this_oc,2,1);
						 },
		                 totalSummary:{
		                 	type: 'sum',
		                    render: function (suminf, column, cell){
		                    	return '<div>' + formatNumber(suminf.sum ,2,1) + '</div>';
		                    }
		                 }
					 },
					 { display: '累计贷方', name: 'sum_oc', align: 'right',
						 render:function(rowdata){
							 return formatNumber(rowdata.sum_oc,2,1);
						 },
		                 totalSummary:{
		                 	type: 'sum',
		                    render: function (suminf, column, cell){
		                    	return '<div>' + formatNumber(suminf.sum ,2,1) + '</div>';
		                    }
		                }
					 },
                     { display: '方向', name: 'subj_dire', align: 'left',
							render : function(rowdata, rowindex,value) {
								if(rowdata.subj_dire == 0){
									return "借";
								}else{
									return "贷"
								}
							}
					 },
                     { display: '余额', name: 'balance', align: 'right',
						 render:function(rowdata){
							 return formatNumber(rowdata.balance,2,1);
						 },
		                 totalSummary:{
		                 	type: 'sum',
		                    render: function (suminf, column, cell){
		                    	return '<div>' + formatNumber(suminf.sum ,2,1)+ '</div>';
		                        }
		                    }
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryBalance.do?isCheck=true',
                     width: '100%', height: '100%', checkbox: false,rownumbers:true,
                     delayLoad:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true } ,
                     	{ text: '打印', id:'print', click: print,icon:'print' },
                     	{ line:true } 
    				]}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }

    function print(){
    	
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
				title: "余额查询",//标题
				columns: JSON.stringify(grid.getPrintColumns()),//表头
				class_name: "com.chd.hrp.acc.service.verification.AccNostroService",
				method_name: "queryAccLederCheckBalanceOPrint",
				bean_name: "accNostroService"/* ,
				heads: JSON.stringify(heads) *///表头需要打印的查询条件,可以为空
				/* foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 */
				};
		
		$.each(grid.options.parms,function(i,obj){
				printPara[obj.name]=obj.value;
		});
		
		officeGridPrint(printPara);
    }
    
    function loadDict(){
            //字典下拉框
            var param = {
            		SUBJ_NATURE_CODE1:'04',
            		SUBJ_NATURE_CODE2:'05',
            		is_last : 1
            	};
    	 
    	 $("#acc_year_month").ligerComboBox({
          	url: '../queryYearMonth.do?isCheck=false',
          	valueField: 'id',
           	textField: 'text', 
           	selectBoxWidth: 180,
          	autocomplete: true,
          	width: 180
 		  });
    	 
    	 

    	 liger.get("acc_year_month").setValue(year_Month);
		 
    	 liger.get("acc_year_month").setText(year_Month);
    	 
    	 
    	 var defaultSelect = true;
    	 
    	 $("#subj_code").ligerComboBox({
    		 	parms : param,
    	      	url: 'querySubjIsCheck.do?isCheck=false',
    	      	valueField: 'id',
    	       	textField: 'text', 
    	       	selectBoxWidth: subjWidth,
        		selectBoxHeight:'260',
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
    
    
    function subjChange(){
    	$("#proj1").val("");
    	$("#proj2").val("");
    	$("#check_type").val("");
		var subj_code = liger.get("subj_code").getValue().split(".")[1];
		var defaultSelect = true;
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
    	$("#proj1").ligerComboBox({
	       	selectBoxWidth: 180,autocomplete: true,width: 180
		 });
    	$("#proj2").ligerComboBox({
    		selectBoxWidth: 180,autocomplete: true,width: 180
		 });
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
    <table cellpadding="0" cellspacing="0" class="l-table-edit"  border="0">
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:10px;">往来科目：</td>
            <td align="left" class="l-table-edit-td" >
            	<input id="subj_code" name="subj_code" />
            </td>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:10px;">往来类型：</td>
            <td align="left" class="l-table-edit-td" >
            	<input id="check_type" name="check_type" onchange="checkTypeChange();" validate="{required:true,maxlength:20}"/>
            </td>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:10px;" >往来项目：</td>
            <td align="left" class="l-table-edit-td" ><input id="proj1" name="proj1" /></td>
            <td align="left" class="l-table-edit-td">至:</td>
            <td><input id="proj2" name="proj2" /></td>
            <td></td>
            
        </tr> 
         <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:10px;">会计期间：</td>
            <td align="left" class="l-table-edit-td">
            	<input id="acc_year_month" name="acc_year_month" />
            </td>
            
            <td align="right" class="l-table-edit-td"  ><input name="is_check" type="checkbox" id="is_check"  /></td>
            <td align="left" class="l-table-edit-td">含未记账</td>
            
            <td align="right" class="l-table-edit-td" ><input name="showItem" type="checkbox" id="showItem" ltype="text"  /></td>
            <td align="left">不显示余额为零的项目</td>
           
        </tr> 
	 
    </table>
	</form>
	<div id="maingrid"></div>

</body>
</html>
