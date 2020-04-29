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
    var year_month = '${yearMonth}';
    var flag = false;
    
    $(function (){
		loadDict(); 
		loadForm();
    	loadHead(null);	//加载数据  	
    });
    
    //查询
    function  query(){
    	grid.options.parms=[];
    	grid.options.newPage=1;
    	
		if(""==liger.get("subj_code").getValue()){
        	$.ligerDialog.error('请选择往来科目');
        	return;
        }
		
		if(""==liger.get("due_date").getValue()){
        	$.ligerDialog.error('请选择截止日期');
        	return;
        }
		
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'subj_code',value:liger.get("subj_code").getValue()});
    	grid.options.parms.push({name:'due_date',value:liger.get("due_date").getValue()}); 
    	
    	if(liger.get("dept_code").getValue() ==''){
    		grid.options.parms.push({name:'dept_id',value:''});
        	//grid.options.parms.push({name:'dept_no',value:''});
    	}else{
    		grid.options.parms.push({name:'dept_id',value:liger.get("dept_code").getValue().split(".")[0]});
        	//grid.options.parms.push({name:'dept_no',value:liger.get("dept_code").getValue().split(".")[1]});
    	}
    	
    	if(liger.get("emp_code").getValue() ==''){
    		grid.options.parms.push({name:'emp_id',value:''}); 
        	//grid.options.parms.push({name:'emp_no',value:''});
    	}else{
    		grid.options.parms.push({name:'emp_id',value:liger.get("emp_code").getValue().split(".")[0]}); 
        	//grid.options.parms.push({name:'emp_no',value:liger.get("emp_code").getValue().split(".")[1]});
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

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [
                     { display: '部门', name: 'dept_name', align: 'left',width:120},
                     { display: '职工编码', name: 'emp_code', align: 'left',width:120,
						 render:function(rowdata){
							 if(rowdata.check_id == '0'){
								 return '';
							 }else{
								 return rowdata.emp_code;
							 }
						 }
                     },
                     { display: '职工名称', name: 'emp_name', align: 'left',width:120,
                    	 render:function(rowdata){
							 if(rowdata.check_id == '0'){
								 return '';
							 }else{
								 return rowdata.emp_name;
							 }
						 }	 
                     },
					 { display: '日期', name: 'vouch_date', align: 'left',width:120},
					 { display: '凭证号', name: 'vouch_no', align: 'left',width:120,
						 render : function(rowdata, rowindex, value) {
   							 if(rowdata.vouch_no !=null){
   								 return "<a href=javascript:openSuperVouch('"+rowdata.vouch_no.split('-')[2]+"')><div>"+rowdata.vouch_no.split('-')[0]+"-"+rowdata.vouch_no.split('-')[1]+"</div></a>"; 
   							 }
						 	}	 
					 },/*点击凭证号链接*/
					 { display: '摘要', name: 'summary', align: 'left',width:180},
					 { display: '借款金额', name: 'debit', align: 'right',width:120,
						 render:function(rowdata){
 							return formatNumber(rowdata.debit ==null ? 0 : rowdata.debit,2,1);
 						 }	 
					 },
					 { display: '还款金额', name: 'ybal_amt', align: 'right',width:120,
						 render:function(rowdata){
	 							return formatNumber(rowdata.ybal_amt ==null ? 0 : rowdata.ybal_amt,2,1);
	 						 }		 
					 },
					 { display: '核销状态', name: 'state', align: 'left',width:120},
					 { display: '核算ID', name: 'check_id' }
                   ],
                   dataAction: 'server',dataType: 'server',usePager:true,url:'queryContactsReminderDetail.do?isCheck=true',
                   width: '100%', height: '100%', checkbox: false,rownumbers:true,delayLoad :true,isScroll:true,
                   selectRowButtonOnly:true,//heightDiff: -10,
                   toolbar: { items: [
                     	{ text: '查询', id:'search', click: search,icon:'search' },
                     	{ line:true },
                     	{ text: '打印', id:'print', click: printData, icon:'print' },
    					{ line:true }/* ,
    					{ text: '导出', id:'export', click: exportData, icon:'export' },
    	                { line:true } */
    				]}
                   });
        gridManager = $("#maingrid").ligerGetGridManager();
        grid.toggleCol("check_id", false);
        
    }
    
    function openSuperVouch(vouch_id){
		parent.openFullDialog('hrp/acc/accvouch/superVouch/superVouchMainPage.do?vouch_id='+vouch_id,'会计凭证',0,0,true,true);
	}
    
    //查询
    function search(){
    	validate_check();
    	if(flag){
    		query();
    	}else{
    		$.ligerDialog.error('请选择科目！');
        	return;
    	}
    }
    
    //打印
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
				title: "个人往来催款单",//标题
				columns: JSON.stringify(grid.getPrintColumns()),//表头
				class_name: "com.chd.hrp.acc.service.verification.AccNostroService",
				method_name: "queryContactsReminderDetailPrint",
				bean_name: "accNostroService"/* ,
				heads: JSON.stringify(heads) *///表头需要打印的查询条件,可以为空
				/* foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 */
				};
		
		$.each(grid.options.parms,function(i,obj){
				printPara[obj.name]=obj.value;
		});
		
		officeGridPrint(printPara);
           	
    }
    
    //导出
    function exportData(){
    	
    }
	
	function validate_check(){
    	var subj_code = liger.get("subj_code").getValue();
    	if(subj_code == null || subj_code==''){
    		flag = false;
    	}else{
    		flag = true;
    	}
    	
    } 
	
    function loadDict(){
            //字典下拉框    
            var param = {
        		SUBJ_NATURE_CODE1:'04',
        		SUBJ_NATURE_CODE2:'04',
        		is_last : 1
        		
        	};
            var defaultSelect = true;
            
            $("#subj_code").ligerComboBox({
            	parms : param,
    	      	url: '../querySubj.do?isCheck=false',
    	      	valueField: 'id',
    	       	textField: 'text', 
    	       	selectBoxWidth: subjWidth,
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
            autodate("#due_date");//默认当前日期
            $("#dept_code").ligerComboBox({
		      	url: '../../sys/queryDeptDict.do?isCheck=false',
		      	valueField: 'id',
		       	textField: 'text', 
		       	selectBoxWidth: 180,
		      	autocomplete: true,
		      	width: 180
			 });
           
         }  
    
    function subjChange(){
    	//alert(liger.get("dept_code").getValue())
		if(liger.get("dept_code").getValue()!= null && liger.get("dept_code").getValue() != ''){
			$("#emp_code").val("");
			var para = {
					dept_id : liger.get("dept_code").getValue().split(".")[0],
					dept_no : liger.get("dept_code").getValue().split(".")[1]
	        	};
			
			$("#emp_code").ligerComboBox({
			 	parms : para ,
			 	url: '../queryEmpDictById.do?isCheck=false',
		      	valueField: 'id',
		       	textField: 'text', 
		      	autocomplete: true,
		      	selectBoxWidth: 180,
		      	width: 180,
		      	initValue: 0
			 });
		}else{
		
			$("#emp_code").ligerComboBox({
		      	url: '../../sys/queryEmp.do?isCheck=false',
		      	valueField: 'id',
		       	textField: 'text', 
		       	selectBoxWidth: 180,
		      	autocomplete: true,
		      	width: 180
			 });
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
            <td align="right" class="l-table-edit-td"  style="padding-left:10px;">科目：</td>
            <td align="left" class="l-table-edit-td"><input name="subj_code" type="text"  id="subj_code"  ltype="text" /></td>
            <td align="left"></td>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:10px;">部门：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_code" type="text" id="dept_code"  ltype="text" onchange="subjChange();" /></td>
            <td align="left" ></td>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:10px;">职工：</td>
            <td align="left" class="l-table-edit-td"><input name="emp_code" type="text" id="emp_code" ltype="text"   /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">截止日期：</td>
            <td align="left" class="l-table-edit-td"><input class="Wdate" name="due_date" type="text" id="due_date"  onFocus="WdatePicker({isShowClear:false,readOnly:false,dateFmt:'yyyy-MM-dd'})" ltype="text" /></td>
            <td align="left"></td>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;" >核销状态：</td>
            <td align="left" class="l-table-edit-td">
            	<select id="veriState" name="veriState">
            		<option value="0">全部</option>
            		<option value="1">未核销</option>
            		<!-- <option value="2">部分核销</option> -->
            		<option value="2">已核销</option>
            	</select>
            </td>
            <td align="left"></td>
            
            <td align="right" class="l-table-edit-td"><input name="is_check" type="checkbox" id="is_check"   /></td>
            <td align="left">含未记账</td>
            <td align="left"></td>
        </tr> 
       
    </table>
	</form>
	<div id="maingrid"></div>
</body>
</html>
