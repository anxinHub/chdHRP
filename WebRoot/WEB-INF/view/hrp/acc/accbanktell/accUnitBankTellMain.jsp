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
    
    $(function ()
    {
		loadDict();
    	
    	loadHead(null);	//加载数据
    	//获取当前时间，根据年度、月份设置凭证起止日期
		var mydate = new Date();

		var vYear = mydate.getFullYear();

		var vMon = mydate.getMonth() + 1;

		var acc_month;

		if (vMon < 10) {

			acc_month = getMonthDate(vYear, "0" + vMon);

		} else {

			acc_month = getMonthDate(vYear, vMon);

		}

		$("#begin_date").val(acc_month.split(";")[0]);

		$("#end_date").val(acc_month.split(";")[1]);

		
    	
    });
    //查询
    function  query(){
    	//alert(liger.get("subj_code").getValue());
    	if(liger.get("subj_code").getValue()==''){
    		 $.ligerDialog.error('科目编码为必填项');
    		 return;
    	}
    	if($("#begin_date").val()=='' || $("#end_date").val()==''){
   		 $.ligerDialog.error('起始时间为必填项');
   		 return;
   		}
//     	var begin_date = $("#begin_date").val().replace(".","");
//     	var end_date = $("#end_date").val().replace(".","")
    	var begin_date = $("#begin_date").val();
    	var end_date = $("#end_date").val();
    	
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'subj_code',value:liger.get("subj_code").getValue()}); 
    	grid.options.parms.push({name:'begin_date',value:begin_date}); 
    	grid.options.parms.push({name:'end_date',value:end_date});
    	
    	grid.options.parms.push({name:'vouch_no',value:$('#vouch_no').val()}); 
    	grid.options.parms.push({name:'pay_code',value:liger.get("pay_type_code").getValue()});
    	grid.options.parms.push({name:'check_no',value:$('#check_no').val()}); 
    	
    	
    	if($('#state').is(':checked')) {
    		grid.options.parms.push({name:'state',value:1}); 
		}else{
			grid.options.parms.push({name:'state',value:99}); 
		}
    	if($('#is_check').val() !='' && $('#is_check').val() != null){
    		if($('#is_check').val() == '1'){
    			grid.options.parms.push({name:'isY_check',value:$('#is_check').val()});
    		}
    		
    		if($('#is_check').val() == '0'){
        		grid.options.parms.push({name:'isN_check',value:$('#is_check').val()});
        	}
    		
    	}
    	
    	//加载查询条件
    	grid.loadData(grid.where);
    	gridManager = $("#maingrid").ligerGetGridManager();
     }
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
    		  columns: [ 
                    { display: '业务日期', name: 'vouch_date', align: 'left'
   					 },
                     { display: '凭证号', name: 'vouch_no', align: 'left'
   					 },
                    { display: '摘要', name: 'summary', align: 'left'
   					 },
                     { display: '结算方式', name: 'pay_name', align: 'left'
   					 },
   					 { display: '票据号', name: 'check_no', align: 'left'
   					 },
   					 { display: '借方金额', name: 'debit', align: 'right',formatter:'###,##0.00',
   						 render:function(rowdata){
   	   						 return formatNumber(rowdata.debit, 2, 1);
   						 }
   					 },
   					 { display: '贷方金额', name: 'credit', align: 'right',formatter:'###,##0.00',
   						 render:function(rowdata){
   						 return formatNumber(rowdata.credit, 2, 1);
   					 	 }
   					 },
   					 { display: '余额', name: 'bal', align: 'right',formatter:'###,##0.00',
   						 render:function(rowdata){
   	   						 return formatNumber(rowdata.bal, 2, 1);
   					     }
   					 },
   					 { display: '是否对账', name: 'is_check', align: 'left'
   					 }
                        ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAccUnitBankTell.do',
                     width: '100%', height: '100%',rownumbers:true,delayLoad:true,
                     selectRowButtonOnly:true,
                      
                   });

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
		
    	$.ligerDialog.open({ url : 'deptIncomeUpdatePage.do?isCheck=false' + parm,data:{}, height: 500,width: 500, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveAccTell(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

    }
    
    function print_btn(){
    	
  	  if(grid.getData().length==0){
  			$.ligerDialog.error("请先查询数据！");
  			return;
  		}
         	
  		var printPara={
			title: "单位银行账",//标题
			columns: JSON.stringify(grid.getPrintColumns()),//表头
			class_name: "com.chd.hrp.acc.service.tell.AccUnitBankTellService",
			method_name: "queryAccUnitBankTellPrint",
			bean_name: "accUnitBankTellService"/* ,
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
    	autocomplete("#subj_code","../querySubj.do?isCheck=false&subj_nature_code=03&is_last=1","id","text",true,true,'',true);
            
    	autocomplete("#pay_type_code","../queryPayType.do?isCheck=false","id","text",true,true,false,false,false,"120");

        
        $("#begin_date").ligerTextBox({ width:120});
        
    	$("#end_date").ligerTextBox({ width:120});
    	 
    	$("#vouch_no").ligerTextBox({ width:140});
    	
    	$("#is_check").ligerTextBox({ width:140});
    	
    	$("#check_no").ligerTextBox({ width:120});
    	 
    	$(':button').ligerButton({width:80});
    

    }   
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
	
		<div id="topmenu"></div>
	
    <table cellpadding="0" cellspacing="0" class="l-table-edit" border ="0">
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font size="3px" color="red">*</font>科目编码：</td>
            <td align="left" class="l-table-edit-td"><input name="subj_code" type="text" id="subj_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font size="3px" color="red">*</font>会计期间：</td>
            <td align="left" class="l-table-edit-td"><input class="Wdate" name="begin_date" type="text" id="begin_date" ltype="text" value="${begin_date}" validate="{required:true,maxlength:20}" 
            onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
            <td align="center" class="l-table-edit-td"  style="padding-left:20px;">至</td>
            <td align="left" class="l-table-edit-td"><input class="Wdate" name="end_date" type="text" id="end_date" ltype="text" value="${end_date}" validate="{required:true,maxlength:20}" 
            onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
            <td align="left"></td>
        </tr> 
        <tr>
        <td align="left"  class="l-table-edit-td"  style="padding-left:30px;"><font size="3px" color="red"></font>凭证号:</td>
        <td align="left" class="l-table-edit-td"><input name="vouch_no" type="text" id="vouch_no" ltype="text" validate="{required:true,maxlength:20}" /></td>
        <td align="left"></td>
        <td align="left" class="l-table-edit-td"  style="padding-left:30px;">结算方式:</td>
        <td align="left" class="l-table-edit-td"><input name="pay_type_code" type="text" id="pay_type_code" ltype="text"  validate="{maxlength:20}" /></td>
        <td align="right" class="l-table-edit-td"  style="padding-left:10px;">票据号</td>
        <td align="left" class="l-table-edit-td"><input name="check_no" type="text" id="check_no" ltype="text" validate="{required:true,maxlength:20}" /></td>
        <td align="left"></td>
        </tr>
		<tr>
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font size="3px" color="red">*</font>是否对账：</td>
            <td align="left" class="l-table-edit-td">
            	<select  id="is_check">
            		<option>全部</option>
	            	<option value="1" >是</option>
	            	<option value="0">否</option>
            	</select>
            </td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><input type="checkbox" id="state" checked="checked"/>包含未记账</td>        
		</tr>
    </table>
    <div style="border:1px">
    <input  type="button" value=" 查询" onclick="query();"/>
    <input  type="button" value="打印" onclick="print_btn();"/>
	</div>
	<div id="maingrid"></div>

</body>
</html>
