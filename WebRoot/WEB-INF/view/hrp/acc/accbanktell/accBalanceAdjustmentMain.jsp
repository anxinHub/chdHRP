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
    var acc_date;
    
    $(function ()
    {
		loadDict();
    	
    	loadHead(null);	//加载数据
    	
    	/* //获取当前时间，根据年度、月份设置凭证起止日期
		var mydate = new Date();
		
		var vYear = mydate.getFullYear();
		
		var vMon = mydate.getMonth() + 1;
		
		var acc_month;
		
		if(vMon<10){
			
			acc_month = getMonthDate(vYear,"0"+vMon);
			
		}else{
			
			acc_month = getMonthDate(vYear,vMon);
			
		}
		
		$("#acc_date_b").val(acc_month.split(";")[0);
		
		$("#acc_date_e").val(acc_month.split(";")[1]); */
    });
    //查询
    function  query(){
    	
    	
   		grid.options.parms=[];
   		grid.options.newPage=1;
        //根据表字段进行添加查询条件
   		if($("#subj_code").val()==''){
   		 	$.ligerDialog.error('科目编码为必填项');
   		 	return;
   		}
   		if($("#acc_date").val()=='' ){
  		 	$.ligerDialog.error('截止期间为必填项'); 
   		 	return;
   		} 
   		var accDate=$("#acc_date").val();
   		var lastDay= new Date(accDate.substring(0,4),accDate.substring(5,7),0).getDate();
    	grid.options.parms.push({name:'subj_code',value:liger.get("subj_code").getValue()}); 
		grid.options.parms.push({name:'acc_date',value:accDate+"-"+lastDay}); 

    	grid.options.parms.push({name:'subj_select_flag',value:$('#is_state').is(':checked')?1:0});
    	
    	grid.options.parms.push({name:'is_account',value:$('#is_account').is(':checked')?1:0});
    	
    	//加载查询条件
    	grid.loadData(grid.where);
     } 	

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '银行账项目', name: 'unit_name', align: 'left',width:'350'
					 },
                     { display: '金额', name: 'debit', align: 'right',formatter:'###,##0.00',width:'110',render:
     					function(rowdata){
      					return formatNumber(rowdata.debit,2,1);
 					}
					 },
                     { display: '银行对账单项目', name: 'bank_name', align: 'left',width:'350'
					 },
                     { display: '金额', name: 'credit', align: 'right',formatter:'###,##0.00',width:'110',render:
     					function(rowdata){
      					return formatNumber(rowdata.credit,2,1);
 						}
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:false,url:'collectBalanceAdjust.do',
                     width: '100%', height: '100%',rownumbers:true,delayLoad:true,onAfterShowData:function (currentData){
                 		$.each(currentData.Rows, function(t_index, t_content){ 
						    if(t_content.unit_name=="单位-调节后余额" || t_content.bank_name =="银行-调节后余额"){
						    	if(t_content.debit == t_content.credit){
						    		$("#hid").html("余额调节表平衡!")
						    		$("#hid").css({color:"green"}) 
						    	}else {
						    		$("#hid").html("余额调节表不平衡!")
						    		$("#hid").css({color:"red"}) 
						    	}
						    }

		     			}); 
             },

                     selectRowButtonOnly:true,
                   
                     lodop:{
                  		title:"余额调节表",
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
              		}});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    function print_btn(){
    	
    	if(grid.getData().length==0){
  			$.ligerDialog.error("请先查询数据！");
  			return;
  		}
         	
  		var printPara={
			title: "余额调节表",//标题
			columns: JSON.stringify(grid.getPrintColumns()),//表头
			class_name: "com.chd.hrp.acc.service.AccInitialBalanceCalibrationService",
			method_name: "collectBalanceAdjustByPrint",
			bean_name: "accInitialBalanceCalibrationService"/* ,
			heads: JSON.stringify(heads) *///表头需要打印的查询条件,可以为空
			/* foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 */
			};
	
	$.each(grid.options.parms,function(i,obj){
			printPara[obj.name]=obj.value;
	});
	
	officeGridPrint(printPara);
        
    } 
    //打印回调方法
    function lodopPrint(){
    /* 	var accStr="不包含未记账"
       	if($("#is_state").prop("checked")){
       		accStr="包含未记账"
       	} */
    	var head="<table class='head' width='100%'><tr><td>日期："+$("#acc_date").val()+"</td>";
 		grid.options.lodop.head=head;
    }
      
    function loadDict(){
            //字典下拉框
    	autocomplete("#subj_code","../querySubj.do?isCheck=false&subj_nature_code=03&is_last=1","id","text",true,true,'',true);
            
       // $("#acc_date").ligerTextBox({ width:120});
        acc_date = $("#acc_date").etDatepicker({
			range: false,
			view: "months",
			minView: "months",
			dateFormat: "yyyy-mm",
			defaultDate: ['${acc_year}-${acc_month}']
		});
        
        //autodate("#acc_date", "yyyy-MM-dd");

    	 
    	$(':button').ligerButton({width:80});
         }   
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
	
		<div id="topmenu"></div>
	
    <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">*</font>科目编码：</td>
            <td align="left" class="l-table-edit-td"><input name="subj_code" type="text" id="subj_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">*</font>截止期间</td>
            <td align="left" class="l-table-edit-td">
            <!-- input  class="Wdate"  name="acc_date" type="text" id="acc_date" ltype="text" validate="{required:true,maxlength:20}"  onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /-->
            <input class="Wdate" name="acc_date" type="text" id="acc_date" ltype="text" style="width: 98px;" />
            </td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"></td>
            <td align="left" class="l-table-edit-td">
            	<input name="is_state" type="checkbox" id="is_state" class="liger-checkbox"/>含对账明细
            	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            	<input name="is_account" type="checkbox" id="is_account" class="liger-checkbox" checked="checked"/>含未记账
            </td>
            <td align="left"></td>
        </tr>
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;"></td>
            <td align="left" class="l-table-edit-td"><span id="hid" ></span></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;" colspan="5" ></td>
            <td align="left" class="l-table-edit-td" >
            	<input  type="button" value=" 校验" onclick="query();"/>
            	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            	<input  type="button" value="打印" onclick="print_btn();"/>
            </td>
         </tr> 
	
    </table> 

	<div id="maingrid"></div>

</body>
</html>
