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
    
    var gridData;
    
    var gridParam;
    
    $(function ()
    {
		loadDict();
    	
    	loadHead(null);	//加载数据
		
var mydate = new Date();
		
		var vYear = mydate.getFullYear();
		
		var vMon = mydate.getMonth() + 1;
		
		var acc_month;
		
		if(vMon<10){
			
			acc_month = getMonthDate(vYear,"0"+vMon);
			
		}else{
			
			acc_month = getMonthDate(vYear,vMon);
			
		}
		
		$("#begin_date").val(acc_month.split(";")[0]);
		
		$("#end_date").val(acc_month.split(";")[1]);
    	
    });
    //查询
    function  query(){
    	var subj_code = liger.get("subj_code").getValue();
    	if(subj_code == ''){
    		$.ligerDialog.error('会计科目为必填项');
    		return;
    	}
    	
    	if($("#begin_date").val() == ''||$("#end_date").val() == ''){
    		$.ligerDialog.error('起止日期为必填项');
    		return;
    	}
    	

    	var start_year = $("#begin_date").val().split("-")[0];
    	var end_year = $("#end_date").val().split("-")[0];
    	
    	//if(start_year != end_year){
    	//	$.ligerDialog.error('不支持跨年查询');
    	//	return ; 
    	//}
    	
    	grid.options.parms=[];
    	grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'subj_code',value:subj_code});
    	grid.options.parms.push({name:'begin_date',value:$("#begin_date").val()});
    	grid.options.parms.push({name:'end_date',value:$("#end_date").val()});
    	grid.options.parms.push({name:'sumMoney',value:$("#sumMoney").val()});
    	grid.options.parms.push({name:'summary',value:$("#summary").val()});
    	grid.options.parms.push({name:'create_user',value:liger.get("create_user").getValue().split(".")[0]});
    	grid.options.parms.push({name:'vouch_type_code',value:liger.get("vouch_type_code").getValue().split(".")[0]}); 
    	grid.options.parms.push({name:'vouch_no_b',value:$("#vouch_no_b").val()});
    	grid.options.parms.push({name:'vouch_no_e',value:$("#vouch_no_e").val()});
    	grid.options.parms.push({name:'vouch_type_code',value:$("#vouch_type_code").val()});
    	grid.options.parms.push({name:'money',value:$("#money").val()});
		grid.options.parms.push({name:'state',value:$('#state').is(':checked')?1:99}); 
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	gridData =showJson();
    	gridParam = is_checkJson();
    	var columns = "";
    	columns = columns + "{display: '期间',align: 'center',columns:[{display: '年', name: 'v_year', align: 'left',width:'4%'},"
    	+"{display: '月', name: 'v_month', align: 'left',width:'3%'},"
    	+"{ display: '日', name: 'v_day', align: 'left',width:'3%'}]},"
    	+"{ display: '凭证号', name: 'vouch_no', align: 'left',width:'6%',render:function(rowdata){if(rowdata.vouch_id != 0 && rowdata.vouch_id != null){return'<a href=javascript:openSuperVouch('+rowdata.vouch_id+')>'+rowdata.vouch_no+'</a>'}}},"
    	+"{ display: '摘要', name: 'summary', align: 'left',width:'15%'},";
    	if(gridParam == true){
    		columns = columns+"{ display: '对账', name: 't_is_check', align: 'center'},";
    	};
    	columns = columns
    	+"{ display: '会计科目', name: 'subj_name', align: 'left'},"
    	+"";
    	if(gridData==true){ 
    		columns = columns+"{ display: '对方科目', name: 'other_subj_name', align: 'left'},"
    		;
    	}
    	columns = columns
    	+"{ display: '借方金额', name: 'debit', align: 'right',width:'10%',reg:'0.00= ,0= ',render:function(rowdata){return formatNumber(rowdata.debit,2,1);}},"
    	+"{ display: '贷方金额', name: 'credit', align: 'right',width:'10%',reg:'0.00= ,0= ',render:function(rowdata){return formatNumber(rowdata.credit,2,1); }},"
    	+"{ display: '方向', name: 'subj_dire', align: 'center',width:'2%',render:function(rowdata){if(rowdata.subj_dire == '0'){return '借';}if(rowdata.subj_dire == '1'){return '贷';}}},"
    	+"{ display: '余额', name: 'bal', align: 'right',width:'10%',render:function(rowdata){return formatNumber(rowdata.bal,2,1);}}"
    	grid = $("#maingrid").ligerGrid({
    					columns: eval("["+columns+"]"),	 
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAccBankJournal.do',
                     width: '100%', height: '100%',rownumbers:true,delayLoad:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     lodop:{
                 		title:"银行日记账",
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
    	var head="<table class='head' width='100%'><tr><td>日期："+$("#begin_date").val()+"至"+$("#end_date").val()+"</td>";
 		grid.options.lodop.head=head;
    }
    function itemclick(item){ 
        if(item.id)
        {
            switch (item.id)
            {
                case "add":
              		$.ligerDialog.open({url: 'accBankAccountAddPage.do?isCheck=false', height: 500,width: 500, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveAccTell(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
              		return;
                case "modify":
                    return;
                case "delete":
                    var data = gridManager.getCheckedRows();
                    if (data.length == 0){
                    	$.ligerDialog.error('请选择行');
                    }else{
                        var ParamVo =[];
                        $(data).each(function (){					
							ParamVo.push(
							//表的主键
							this.tell_id   +"@"+ 
							this.group_id   +"@"+ 
							this.hos_id   +"@"+ 
							this.copy_code 
							)
                        });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteAccBankAccount.do?isCheck=false",{ParamVo : ParamVo},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
                    return;
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
    
    function openSuperVouch(vouch_id){
		parent.openFullDialog('hrp/acc/accvouch/superVouch/superVouchMainPage.do?vouch_id='+vouch_id,'会计凭证',0,0,true,true);
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
           	
    	/* var heads={
		  //"isAuto": true/false 默认true，页眉右上角默认显示页码
		  "rows": [
	      {"cell":0,"value":"科室名称："+liger.get("dept_id").getText(),"colSpan":"5"},
	      {"cell":3,"value":"项目名称："+liger.get("proj_id").getText(),"from":"right","align":"right","colSpan":"4"}
			  ]
		}; */
	
		var printPara={
				title: "银行日记账",//标题
				columns: JSON.stringify(grid.getPrintColumns()),//表头
				class_name: "com.chd.hrp.acc.service.vouch.AccVouchService",
 				method_name: "queryAccCashJournalPrint",
 				bean_name: "accVouchService"/* ,
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
    	autocomplete("#other_subj_code","../querySubj.do?isCheck=false&is_last=1&extras=03","id","text",true,true);
    	autocomplete("#create_user", "../../sys/queryEmp.do?isCheck=false","id", "text", true, true);
    	autocomplete("#vouch_type_code", "../queryVouchType.do?isCheck=false","id", "text", true, true);
    	
        $("#begin_date").ligerTextBox({width:160});
    	$("#end_date").ligerTextBox({width:160});  
    	$(':button').ligerButton({width:80}); 
         } 
    
    var click = 0;
	function show(){
		click++;
		if(click%2 == 0){
			$("#vouch_table").hide();
		}else{
			$("#vouch_table").show();
		}
	}
	
	//根据选择核算类构建动态列
 	function showJson(){
    	var flag = false;
    	if($('#showSubj').is(':checked')) {
    		flag = true;
		}
   	return flag;
   	
    }
	
 	function is_checkJson(){
    	var flag = false;
    	if($('#is_check').is(':checked')) {
    		flag = true;
		}
   	return flag;
   	
    }
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
	
		<div id="topmenu"></div>
	
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	
        <tr>
            <td align="right" class="l-table-edit-td"  style=" width=100px;"><font color="red" size="2">*</font>会计科目：</td>
            <td align="left" class="l-table-edit-td" style="width=200px;"><input name="subj_code" type="text" id="subj_code" ltype="text"  /></td>
            <td align="right" class="l-table-edit-td"  style=" width=100px;" ><font color="red" size="2">*</font>日期：</td>
            <td align="left" class="l-table-edit-td" style="width=200px;"><input class="Wdate" name="begin_date" type="text" id="begin_date" ltype="text" validate="{required:true,maxlength:20}" value="${begin_date}"onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd',minDate:'${modStartTime}'})"/></td>
            <td align="center"class="l-table-edit-td" >至</td>
            <td align="left" class="l-table-edit-td"><input class="Wdate" name="end_date" type="text" id="end_date" ltype="text" validate="{required:true,maxlength:20}" value="${end_date}" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd',minDate:'${modStartTime}'})"/></td>
            <td align="left" class="l-table-edit-td"></td>
            <td><input  type="button" value=" 查询" onclick="query()" /> </td>
            <td align="left"></td>
            <td align="left" class="l-table-edit-td"></td>
            <td><input  type="button" value="打印" onclick="print_btn();"/></td>
            <td align="left"></td>
        </tr> 
		<tr>
            <td align="center" class="l-table-edit-td"  style="padding-left:20px; width=100px;">
            	<input type="checkbox" id="state" />含未记账凭证
            </td>
            <td align="center" class="l-table-edit-td"  style="padding-left:20px;width=200px;">
            	<input type="checkbox" id="showSubj"  onchange="loadHead();"/>显示对方科目
            </td><td align="left" class="l-table-edit-td"  style="padding-left:20px;width=200px;">
            	<input type="checkbox" id="is_check"  onchange="loadHead();"/>显示对账信息
            </td>
			<td align="center" class="l-table-edit-td"  style="padding-left:20px;width=100px;">
				<a href="javascript:show()">高级查询</a>
			</td>
			<td style="padding-left:20px;width=200px;"></td>
        </tr>
    </table>
	
	<table id="vouch_table" cellpadding="0" cellspacing="0" class="l-table-edit" style="display: none">
		 	<tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:40px;width: 65px " >凭证号：</td>
                <td align="left" class="l-table-edit-td" style="width: 200px;">
                	<input name="vouch_no_b" type="text" id="vouch_no_b" ltype="text" validate="{required:true,maxlength:20}" style="width: 72px"/>
                 -- <input name="vouch_no_e" type="text" id="vouch_no_e" ltype="text" validate="{required:true,maxlength:20}" style="width:72px"/>
                </td>
                <td align="right" class="l-table-edit-td"  style="width: 100px;">金额：</td>
                <td align="left" class="l-table-edit-td"  style="width: 200px;">
                	<input name="money" type="text" id="money" ltype="text" validate="{required:true,maxlength:20} " style="width:72px" />
            		--
                	<input name="sumMoney" type="text" id="sumMoney" ltype="text" validate="{required:true,maxlength:20}" style="width:72px" />
                </td>
                
                <td align="right" class="l-table-edit-td" style="padding-left: 20px; width=100px;">凭证类型：</td>
				<td align="left" class="l-table-edit-td" style="width: 200px;">
					<input name="vouch_type_code" type="hidden" id="vouch_type_code" ltype="text"
					validate="{required:true,maxlength:20}" />
				</td>
            </tr> 
            <tr>
            	
                <td align="right" class="l-table-edit-td"  style="width: 100px;">制单人： </td>
                <td align="left" class="l-table-edit-td"  style="width: 200px;">
                	<input name="create_user" type="hidden" id="create_user" ltype="text" validate="{required:true,maxlength:20}" />
                </td>
                
                <td align="right" class="l-table-edit-td"  style="width=100px;">摘要：</td>
                <td align="left" class="l-table-edit-td" colspan="2" style="width=300px;">
                	<input name="summary" type="text" id="summary" ltype="text" validate="{required:true,maxlength:20}" style="width: 283px;"/>
                </td>
                <td style="width=200px;"></td>
            </tr> 
            <tr>
                
            </tr> 
           
	</table>
	<div id="maingrid"></div>

</body>
</html>
