<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script src="<%=path%>/lib/et_components/et_plugins/etDatepicker.min.js"></script>
<script type="text/javascript">	
    var grid;
    
    var gridManager = null;
    
    var userUpdateStr;
    
    $(function ()
    {
		loadDict();
    	
    	loadHead(null);	//加载数据
    	
    });
    //查询
    function  query(){
    	grid.options.parms=[];
    	grid.options.newPage=1;
        //根据表字段进行添加查询条件
        var begin_date=$("#acc_year_month1").val().replace(".","/");
        var end_date=$("#acc_year_month2").val().replace(".","/");
	
        var tell_data=$('#tell_data').is(':checked');
        if(tell_data==true){
        	tell_data=1;
        }else{
        	tell_data=0;
        }
    	grid.options.parms.push({name:'begin_date',value:begin_date}); 
    	grid.options.parms.push({name:'end_date',value:end_date}); 
    	
    	grid.options.parms.push({name:'is_account',value:tell_data}); 
    	
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
    		  columns: [ 
    		            { display: '科目编码', name: 'SUBJ_CODE', align: 'left'
      					 },
                        { display: '科目名称', name: 'SUBJ_NAME', align: 'left'
   					 },
                        { display: '期初余额',  align: 'left',columns:[{
                        	display: '会计', name: 'ACC_BAL_OS', align: 'right',
      						 render:function(rowdata){
       	   						 return formatNumber(rowdata.ACC_BAL_OS, 2, 1);
       						 }
                        },{
                        	display: '出纳', name: 'TELL_BAL_OS', align: 'right',
     						 render:function(rowdata){
      	   						 return formatNumber(rowdata.TELL_BAL_OS, 2, 1);
      						 }
                       }]
   					 },
                     { display: '借方发生额', align: 'left',columns:[{
                     	display: '会计', name: 'ACCT_DEBIT', align: 'right',
   						 render:function(rowdata){
    	   						 return formatNumber(rowdata.ACCT_DEBIT, 2, 1);
    						 }
                     },{
                      	display: '出纳', name: 'TELL_DEBIT', align: 'right', 
  						 render:function(rowdata){
   	   						 return formatNumber(rowdata.TELL_DEBIT, 2, 1);
   						 }
                    }]
					 },
                        { display: '贷方发生额', align: 'left',columns:[{
                        	display: '会计', name: 'ACCT_CREDIT', align: 'right',
      						 render:function(rowdata){
       	   						 return formatNumber(rowdata.ACCT_CREDIT, 2, 1);
       						 }
                        },{
                        	display: '出纳', name: 'TELL_CREDIT', align: 'right', 
     						 render:function(rowdata){
      	   						 return formatNumber(rowdata.TELL_CREDIT, 2, 1);
      						 }
                       }]
   					 },
                        { display: '期末余额',align: 'left',columns:[{
                        	display: '会计', name: 'ACCT_END_OS', align: 'right',
      						 render:function(rowdata){
       	   						 return formatNumber((rowdata.ACC_BAL_OS+rowdata.ACCT_DEBIT)-rowdata.ACCT_CREDIT, 2, 1);
       						 }
                        },{
                        	display: '出纳', name: 'TELL_END_OS', align: 'right',
     						 render:function(rowdata){
      	   						 return formatNumber((rowdata.TELL_BAL_OS+rowdata.TELL_DEBIT)-rowdata.TELL_CREDIT, 2, 1);
      						 }
                       }]
   					 },
   					{ display: '差额', name: 'END_OS', align: 'right',render:function(rowdata){
	   						 return formatNumber((rowdata.ACC_BAL_OS+rowdata.ACCT_DEBIT-rowdata.ACCT_CREDIT)-(rowdata.TELL_BAL_OS+rowdata.TELL_DEBIT-rowdata.TELL_CREDIT), 2, 1);
						 }
  					 }],
                     dataAction: 'server',dataType: 'server',/* usePager:true, */url:'queryAllCheckQuery.do',
                     width: '100%', height: '100%',rownumbers:true,delayLoad:true,
                     selectRowButtonOnly:true
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }

    function itemclick(item){ 
        if(item.id)
        {
            switch (item.id)
            {
               
                case"install":
                	$.ligerDialog.open({url: 'accBankAccountInstallPage.do?isCheck=false', height: 300,width: 500, title:'初始余额',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.installAccTell(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
              		return;
                case "Excel":
                case "Word":
                case "PDF":
                case "TXT":
                case "XML":
                    $.ligerDialog.waitting('导出中，请稍候...');
                    setTimeout(function ()
                    {
                        $.ligerDialog.closeWaitting();
                        if (item.id == "Excel")
                            $.ligerDialog.success('导出成功');
                        else
                            $.ligerDialog.error('导出失败');
                    }, 1000);
                    return;
            }   
        }
        
    }
    function openUpdate(obj){
    	
		var vo = obj.split("|");
		var parm = 
			vo[0]   +"&"+ 
			vo[1]   +"&"+ 
			vo[2]   +"&"+ 
			vo[3] 
		
    	$.ligerDialog.open({ url : 'deptIncomeUpdatePage.do?isCheck=false&' + parm,data:{}, height: 500,width: 500, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveAccTell(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

    }
    
    function print_btn(){
    	
    	 if(grid.getData().length==0){
  			$.ligerDialog.error("请先查询数据！");
  			return;
  		}
         	
    	
     	 var heads={
 		  //"isAuto": true/false 默认true，页眉右上角默认显示页码
 		  "rows": [
 	      {"cell":0,"value":"出纳日期："+$("#acc_year_month1").val().replace(".","-")+"至"+$("#acc_year_month2").val().replace(".","-"),"colSpan":"5"}
 	      
 			  ]
 		}; 
 	
 		var printPara={
 				title: "总账对账查询",//标题
 				columns: JSON.stringify(grid.getPrintColumns()),//表头
 				class_name: "com.chd.hrp.acc.service.tell.AccTellService",
 				method_name: "queryAllCheckQueryPrint",
 				bean_name: "accTellService" ,
 				heads: JSON.stringify(heads) //表头需要打印的查询条件,可以为空
 				/* foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 */
 				};
 		
 		$.each(grid.options.parms,function(i,obj){
 				printPara[obj.name]=obj.value;
 		});
 		console.log(printPara);
 		officeGridPrint(printPara);

    }
    
    function loadDict(){
		// 字典下拉框
    	<%--autocomplete("#subj_code","../querySubj.do?isCheck=false","id","text",true,true,'',true);--%>
	
            
var aa = getCurrentDate();
		
		var dateStr = aa.split(";")
	    var curDate = dateStr[0]+'.'+dateStr[1];

         
         $("#acc_year_month1").val(curDate);
         $("#acc_year_month2").val(curDate); 
         
         $("#acc_year_month1").ligerTextBox({ width:120});
         
     	 $("#acc_year_month2").ligerTextBox({ width:120}); 
     	 $(':button').ligerButton({width:80});

     /* 	acc_year_month1 = $("#acc_year_month1").etDatepicker({
     		range: false,
            view: "months",
            minView: "months",
            dateFormat: "yyyy.mm",
            defaultDate: ['${yearMonth}']
   		});
     	acc_year_month2 = $("#acc_year_month2").etDatepicker({
     		range: false,
            view: "months",
            minView: "months",
            dateFormat: "yyyy.mm",
            defaultDate: ['${yearMonth}']
   		}); */

         }   
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
	
		<div id="topmenu"></div>
	
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">*</font>出纳日期：</td>
            <td align="left" class="l-table-edit-td"><input class="Wdate" name="acc_year_month1" type="text" id="acc_year_month1" ltype="text" validate="{required:true,maxlength:20}"  onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy.MM'})"/></td>
            <td align="center" class="l-table-edit-td" >至</td>
            <td align="left" class="l-table-edit-td"><input class="Wdate" name="acc_year_month2" type="text" id="acc_year_month2" ltype="text" validate="{required:true,maxlength:20}" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy.MM'})"/></td>
            <td align="left"></td>
        <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><input name="tell_data" type="checkbox" id="tell_data" /></td>
            <td align="left" class="l-table-edit-td">含未记账凭证</td>
           
        </tr>
	
    </table>
    <div style="border:1px">
    <input  type="button" value=" 查询" onclick="query();"/>
    <input  type="button" value="打印" onclick="print_btn();"/>
	</div>
	<div id="maingrid"></div>
    
</body>
</html>
