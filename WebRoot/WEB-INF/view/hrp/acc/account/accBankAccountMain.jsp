<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">	
    var grid;
    var gridManager = null;
    var userUpdateStr;
    
    
    //初始化
    $(function (){
    	
		loadDict();
    	loadHead(null);	//加载数据
    	
    	
    });
    
    
    //查询
    function  query(){
   		grid.options.parms=[];
   		grid.options.newPage=1;
   		
   		//根据表字段进行添加查询条件
        var subj_code=liger.get("subj_code").getValue();
        var acc_time1 =$("#acc_date").val();
        var acc_time2 =$("#acct_date").val();
         
        if(subj_code==""){
        	$.ligerDialog.error('科目编码不能为空！');
        	return;
        }
        
        if($("#acc_date").val() == ''||$("#acct_date").val() == ''){
    		$.ligerDialog.error('起止日期为必填项');
    		return;
    	}
        grid.options.parms.push({name:'subj_code',value:subj_code}); 
        grid.options.parms.push({name:'acc_time1',value:acc_time1}); 
        grid.options.parms.push({name:'acc_time2',value:acc_time2}); 
    	grid.options.parms.push({name:'price1',value:$("#acc_money").val()}); 
    	grid.options.parms.push({name:'price2',value:$("#acct_money").val()}); 
    	grid.options.parms.push({name:'bank_id',value:''}); 
    	//加载查询条件
    	grid.loadData(grid.where);
	}
	
    
    //加载grid
    function loadHead(){
		grid = $("#maingrid").ligerGrid({
    		columns: [ 
				{ display: '业务日期', name: 'occur_date', align: 'left',render:
					function(rowdata,index,value){
						if(rowdata.occur_date == null || rowdata.occur_date == undefined){
	                  	  		return "";
						}
                  	  	return "<a href=javascript:openUpdate('"+rowdata.bank_id+"')>"+rowdata.occur_date+"</a>";
					}
				},
				
				{ display: '摘要', name: 'summary', align: 'left'},
				
				{ display: '结算方式', name: 'pay_name', align: 'left'},
				
				{ display: '票据号', name: 'check_no', align: 'left'},
				
				{ display: '借方金额', name: 'debit', align: 'right',render:
					function(rowdata){
						return formatNumber(rowdata.debit,2,1);
					}
				},
				
				{ display: '贷方金额', name: 'credit', align: 'right',render:
					function(rowdata){
						return formatNumber(rowdata.credit,2,1);
					}
				},
				
				{ display: '对账状态', name: 'is_checks', align: 'left'},
			],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryAccBankAccount.do',
			width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad:true,
			checkBoxDisplay:isCheckDisplay,selectRowButtonOnly:true,//heightDiff: -10,
			onDblClickRow : 
				function (rowdata, rowindex, value){	
					if(rowdata.bank_id == null || rowdata.bank_id == undefined || rowdata.bank_id == ''){
 						$.ligerDialog.error('请选择业务数据');
 						return;
 					}
					
					openUpdate(rowdata.bank_id);
				} ,
    				
				lodop:{
					title:"银行未达账",
					fn:{
   		    			debit:function(value){//借方
   		    				if(value == 0){return "";}
   		           			else{return formatNumber(value, 2, 1);}
   		    			},
   		    			credit:function(value){//贷方
   		    				if(value == 0){return "";}
   		          			else{return formatNumber(value, 2, 1);}
   		   				},
   		   				end_os:function(value){//余额
   			   				 if(value==0){return "Q";}
   							 else{return formatNumber(value, 2, 1);}
   		  				}
   		    		}
    			}
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    
  //打印回调方法
    function lodopPrint(){
    /* 	var accStr="不包含未记账"
       	if($("#is_state").prop("checked")){
       		accStr="包含未记账"
       	} */
    	var head="<table class='head' width='100%'><tr><td>日期："+$("#acc_date").val()+"至"+$("#acct_date").val()+"</td>";
 		grid.options.lodop.head=head;
    }
  
  	//是否复选框
    function isCheckDisplay(rowdata){
        //admin用户没有复选框
        // console.log(rowdata.checkboxDisplay ==false)
       	if(rowdata.occur_date == null) return false;
         return true;
    }
    
  	//添加页跳转
    function add_open(){
    	$.ligerDialog.open({
    		url: 'accBankAccountAddPage.do?isCheck=false', height: 373,width: 488, title:'添加',
    		modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
    		buttons: [ 
    			{ text: '确定', onclick: 
    				function (item, dialog) { 
    					dialog.frame.saveAccTell(); 
    				},cls:'l-dialog-btn-highlight' 
    			}, 
    			{ text: '取消', onclick: 
    				function (item, dialog) { 
    					dialog.close(); 
    				} 
    			} 
    		] 
    	});
    }
    
    //删除
    function delete_btn(){
    	
    	 var flag = true;
    	 var data = gridManager.getCheckedRows();
         if (data.length == 0){
         	$.ligerDialog.error('请选择行');
         	return ; 
         }
     
         var ParamVo =[];
         $(data).each(function (){
      		if(this.is_checks=="已对账"){
				$.ligerDialog.error('已对账的数据不能删除');
				return flag = false;
			}
        	 
			if(this.bank_id != null){
				//表的主键
				ParamVo.push(
					this.bank_id   +"@"+ 
					this.group_id   +"@"+ 
					this.hos_id   +"@"+ 
					this.copy_code 
				);
			}
		});
             
        if(!flag){
      	 	return;
        }
             
		if(ParamVo.length == 0){
			$.ligerDialog.error('请选择业务数据');
			return ; 
		}

        $.ligerDialog.confirm('确定删除?', function (yes){
        	if(yes){
            	ajaxJsonObjectByUrl("deleteAccBankAccount.do",{ParamVo : ParamVo.toString()},function (responseData){
            		if(responseData.state=="true"){
            			query();
            		}
            	});
        	}
        }); 
    }
    
    function downTemplate(){
    	location.href = "downTemplate.do?isCheck=false";
    }
    
    //导入页跳转
    function imp(){
    	$.ligerDialog.open({
    		url: 'accBankAccountImportPage.do?isCheck=false', height: 500,width: 1135, title:'导入',
    		modal:true,showToggle:false,showMax:false,showMin: true,isResize:true 
    	});
    }
    
    //修改页跳转
    function openUpdate(obj){
    	$.ligerDialog.open({ 
    		url : 'accBankAccountUpdatePage.do?isCheck=false&bank_id=' + obj,data:{}, height: 373,width: 488, title:'修改',
    		modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
    		buttons: [ 
    			{ text: '确定', onclick: 
    				function (item, dialog) { 
    					dialog.frame.saveAccTell(); 
    				},cls:'l-dialog-btn-highlight' 
    			}, 
    			{ text: '取消', onclick: 
    				function (item, dialog) { 
    					dialog.close(); 
    				} 
    			} 
    		] 
    	});
    }
    
    
   	
    //打印
    function print_btn(){
    	
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
			title: "银行未达账",//标题
			columns: JSON.stringify(grid.getPrintColumns()),//表头
			class_name: "com.chd.hrp.acc.service.tell.AccBankAccountService",
			method_name: "queryAccBankAccountPrint",
			bean_name: "accBankAccountService"/* ,
			heads: JSON.stringify(heads) *///表头需要打印的查询条件,可以为空
			/* foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 */
		};
  	
	  	$.each(grid.options.parms,function(i,obj){
	  			printPara[obj.name]=obj.value;
	  	});
	  	
	  	officeGridPrint(printPara);
	}
    
    
    
    //字典下拉框
	function loadDict(){
    	autocomplete("#subj_code","../querySubj.do?isCheck=false&subj_nature_code=03&is_last=1","id","text",true,true,'',true,'','','',subjWidth);
    	
    	$("#subj_code").bind("change",function() {
    		
    		var formPara = {
    			subj_code : liger.get("subj_code").getValue()
       		};
    		if(liger.get("subj_code").getValue()!=''){
    			ajaxJsonObjectByUrl("selectAccBankBalInit.do?isCheck=false",formPara, function(responseData) {
    				if (responseData.bal) {
    					$("#bal").val(formatNumber(responseData.bal,2, 1));
    				} else {
    					$("#bal").val("0.00");
    				}
    			});
    		}
		});
    	
    	var year_Month = '${yearMonth}';
 		if(year_Month.toString()=="000000"){
 			
 			var mydate=new Date;
			var year=mydate.getFullYear();
			var month=mydate.getMonth()+1;
			var tate =mydate.getDate();
			month =(month<10 ? "0"+month:month); 
			year_Month = (year.toString()+month.toString()+tate.toString());
 			
 		}
 		
 		var acc_date=getMonthDate(year_Month.substring(0,4),year_Month.substring(5,7).toString());
 		$("#acc_date").val(acc_date.split(";")[0]);
 		$("#acct_date").val(acc_date.split(";")[1]);
 		
        $("#acc_date").ligerTextBox({width:160});
    	$("#acct_date").ligerTextBox({width:160});
        $("#acc_money").ligerTextBox({width:80});
    	$("#acct_money").ligerTextBox({width:80});
    	$(':button').ligerButton({width:80});  
    	$("#bal").ligerTextBox({ width : 160,disabled:true});
    }   
    /* function install_btn(){
    	alert("^~^")
    } */
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar" ></div>
	<div id="topmenu"></div>
	
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">
            	<font color="red" size="2">*</font>科目编码：
            </td>
            <td align="left" class="l-table-edit-td">
            	<input name="subj_code" type="text" id="subj_code" ltype="text" validate="{required:true,maxlength:20}" />
            </td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">
            	<font color="red" size="2">*</font>日期：
            </td>
            
            <td align="left" class="l-table-edit-td">
            	<input class="Wdate" name="acc_date" type="text" id="acc_date" ltype="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" />
            </td>
            <td align="center" class="l-table-edit-td">至</td>
            <td align="left" class="l-table-edit-td">
            	<input class="Wdate" name="acct_date" type="text" id="acct_date" ltype="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" />
            </td>
            
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">金额：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="acc_money" type="text" id="acc_money" ltype="text" validate="{required:true,maxlength:20}" />
            </td>
            
            <td align="center" class="l-table-edit-td">至</td>
            <td align="left" class="l-table-edit-td">
            	<input name="acct_money" type="text" id="acct_money" ltype="text" validate="{required:true,maxlength:20}" />
            </td>
            <td align="left"></td>
        </tr>
    </table>
    
	<div style="border: 1px;width: 100%;">
		<input type="button" value=" 查询" onclick="query();" /> 
		<input type="button" value=" 添加" onclick="add_open();" /> 
		<input type="button" value=" 删除" onclick="delete_btn();" /> 
		<!-- <input type="button" value=" 初始化余额" onclick="install_btn();" /> --> 
		<input type="button" value=" 打印" onclick="print_btn();" /> 
		<input type="button" value=" 下载导入模板" onclick="downTemplate();" /> 
		<input type="button" value=" 导入" onclick="imp();" />
		<div style="display: block; float: right;width: 500px;">
			<div style="display: block; float: left;width: 15%;font-size:medium;font-weight: bold;">初始余额:</div>
			<div style="display: block; float: left;width: 84%"><input name="bal" type="text" id="bal" ltype="text" disabled="disabled" onchange="this.value=cc(this.value)"/></div>
		</div>

	</div>
	<div id="maingrid"></div>

</body>
</html>
