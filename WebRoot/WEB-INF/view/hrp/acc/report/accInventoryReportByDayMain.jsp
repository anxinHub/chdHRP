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
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
    		var end_time = $("#end_time").val();
    		if(end_time == ''){
    			$.ligerDialog.error('日期为必填项');
    			return;
    		} 
    grid.options.parms.push({name:'end_time',value:end_time}); 
        //根据表字段进行添加查询条件
    	/* grid.options.parms.push({name:'subj_code',value:liger.get("subj_code").getValue()}); 
    	grid.options.parms.push({name:'cash_subj_code',value:$("#cash_subj_code").val()}); 
    	grid.options.parms.push({name:'other_subj_code',value:$("#other_subj_code").val()}); 
    	grid.options.parms.push({name:'summary',value:$("#summary").val()});  */
    	//加载查询条件
    	grid.loadData(grid.where);
     }
   

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '科目编码', name: 'subj_code', align: 'left'
			 		 },
                     { display: '科目', name: 'subj_name', align: 'left'
					 },
                     { display: '昨日库存', name: 'pre_bal', align: 'right',formatter:'###,##0.00',render:
                    	 function(rowdata){
                 	 		return formatNumber(rowdata.pre_bal,2,1);
                  	 	 }
					 },
                     { display: '本日收入', name: 'debit', align: 'right',formatter:'###,##0.00',render:
                    	 function(rowdata){
                    	 	return formatNumber(rowdata.debit,2,1);
                     	 }
					 },
                     { display: '本日支出', name: 'credit', align: 'right',formatter:'###,##0.00',render:
                    	 function(rowdata){
                    	 	return formatNumber(rowdata.credit,2,1)
                     	 }
					 },
                     { display: '本日库存', name: 'cur_bal', align: 'right',formatter:'###,##0.00',render:
                    	 function(rowdata){
                    	 	return formatNumber(rowdata.cur_bal,2,1)
                     	 }
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAccInventoryReportByDay.do',
                     width: '100%', height: '100%',rownumbers:true,
                     selectRowButtonOnly:true,delayLoad:true, lodop:{
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
              		}
                   
    	});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function print_btn(){
    	
    	if(grid.getData().length==0){
  			$.ligerDialog.error("请先查询数据！");
  			return;
  		}
    	
    	var end_time = $("#end_time").val();
		if(end_time == ''){
			$.ligerDialog.error('日期为必填项');
			return;
		} 
    	
    	var heads={
        		"isAuto":true,//系统默认，页眉显示页码
        		"rows": [
    	          {"cell":0,"value":"会计期间："},
    	          {"cell":1,"value":""+end_time+""}
        ]}; 
         	
  		var printPara={
			title: "库存日报",//标题
			columns: JSON.stringify(grid.getPrintColumns()),//表头
			class_name: "com.chd.hrp.acc.service.tell.AccInventoryReportService",
			method_name: "queryAccInventoryReportByDayPrint",
			bean_name: "accInventoryReportService" ,
			heads: JSON.stringify(heads) /**///表头需要打印的查询条件,可以为空
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
    	var head="<table class='head' width='100%'><tr><td>日期："+$("#end_time").val()+"</td>";
 		grid.options.lodop.head=head;
    }
   
    function loadDict(){
            //字典下拉框
    	//autocomplete("#subj_code","../querySubj.do?isCheck=false","id","text",true,true);
            
    	var aa = getCurrentDate();
    	
    	var dateStr = aa.split(";")
        var curDate = dateStr[2];
    	$('#end_time').val(curDate);
    	
    	$("#end_time").ligerTextBox({ width:120});
      	$(':button').ligerButton({width:80});
    }   
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
	
		<div id="topmenu"></div>
	
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font size="3px" color="red">*</font>会计期间：</td>
            <td align="left" class="l-table-edit-td"><input  class="Wdate" name="end_time" type="text" id="end_time" ltype="text" validate="{required:true,maxlength:20}" 
            onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
            <td align="right" class="l-table-edit-td"  ></td>
            <td align="right" class="l-table-edit-td"  ><input  type="button" value=" 查询" onclick="query();"/></td>
            <td align="right" class="l-table-edit-td"  ></td>
            <td align="right" class="l-table-edit-td"  ><input  type="button" value=" 打印" onclick="print_btn();"/></td>
            <td align="right" class="l-table-edit-td"  ></td>
        </tr> 
	
    </table>

	<div id="maingrid"></div>

</body>
</html>
