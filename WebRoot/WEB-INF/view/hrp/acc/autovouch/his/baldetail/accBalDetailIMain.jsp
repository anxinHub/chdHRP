<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    $(function ()
    {
         loadDict()//加载下拉框
    	//加载数据
    	 loadHead(null);	
		// loadHotkeys();
		 $(':button').ligerButton({width:80});
    });
    //查询
    function  query(){
		grid.options.parms=[];
		grid.options.newPage=1;
        //根据表字段进行添加查询条件
		grid.options.parms.push({name : 'charge_date_b',value : $("#charge_date_b").val()});
		grid.options.parms.push({name : 'charge_date_e',value : $("#charge_date_e").val()});
    	grid.options.parms.push({name : 'rep_no',value : $("#rep_no").val()});
    	grid.options.parms.push({name : 'charge_code',value : $("#charge_code").val()});
    	grid.options.parms.push({name : 'io_type',value : liger.get("io_type").getValue()});

    	//加载查询条件
    	grid.loadData(grid.where);
     }
	
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [{display : '日报序号',name : 'rep_no',width: 150,align : 'left'}, 
				{display : '收款员编码',name : 'charge_code',width: 150,align : 'left'},
				{display : '收款员名称',name : 'charge_name',align : 'left',width: 150,align : 'left'},
			/* 	{display:'凭证编号',name:"vouch_no",width: 100,align : 'left',render:function(rowdata){
					 if(rowdata.vouch_no=='-'){
						return rowdata.vouch_no;
					}else{
						return "<a href=javascript:openSuperVouch('"+rowdata.vouch_id+"')>"+rowdata.vouch_no+"</a>";
					} 
				}}, */
				//{display : '结算方式编码',name : 'pay_type_code',align : 'left',width: 100,align : 'left'},
				//{display : '结算方式名称',name : 'pay_name',align : 'left',width: 100,align : 'left'},
				{display : '病人类别编码',name : 'patient_type_code',align : 'left',width: 100,align : 'left'},
				{display : '病人类别名称',name : 'patient_name',align : 'left',width: 150,align : 'left'},
				{display : '金额',name : 'charge_money',align : 'left',width: 100,align : 'right', totalSummary: { 
					render: function (suminf, column, cell) {
                    	return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, '2', 1)+ '</div>';
                	} 
				}},
				{display : '是否退费',name : 'is_back',align : 'left',width: 100,align : 'left'}
				],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryAccBalDetailI.do',
			width: '100%', height: '100%', checkbox: false,rownumbers:true,
			delayLoad: true,//初始化加载，默认false
			selectRowButtonOnly:true,pageSize:500
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    //键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
		hotkeys('D', remove);
	}
    
    
	function openSuperVouch(vouch_id){
		parent.openFullDialog('hrp/acc/accvouch/superVouch/superVouchMainPage.do?vouch_id='+vouch_id,'会计凭证',0,0,true,true);
	}
  
    	
    function remove(){
    	
	}
   
    function loadDict(){
    	autocomplete("#template_code","queryAutoBusiTemplate.do?isCheck=false&busi_type_code=0104","id","text",true,true,"",true);
    	$("#charge_date_b").ligerTextBox({width:90});autodate("#charge_date_b", "yyyy-mm-dd", "month_first");
    	$("#charge_date_e").ligerTextBox({width:90});autodate("#charge_date_e", "yyyy-mm-dd", "month_last");
    	$("#rep_no").ligerTextBox({width:205});
    	$("#charge_code").ligerTextBox({width:150});
    	
    	var io_type_data = [{ id: 1, text: '1 住院' }];
    	if(${type}==1){
    		//温医个性化需求
    		io_type_data.push({ id: 2, text: '2 其它' });
    	}
    	$("#io_type").ligerComboBox({data: io_type_data,width:150,valueField:'id',textField:'text',autocomplete: false,cancelable: false}); 
		liger.get("io_type").setValue(1);
		liger.get("io_type").setText('1 住院');
	}  
    
    function printDate(){
		 if(grid.getData().length==0){
   		
			$.ligerDialog.error("请先查询数据！");
			
			return;
		}
   	var heads={
	      		  //"isAuto": true/false 默认true，页眉右上角默认显示页码
	      		  "rows": [
							{"cell":0,"value":"结算日期："+$("#charge_date_b").val()+"至"+$("#charge_date_e").val(),"colSpan":"5"},
	      		  ]
	      	};
	   		
  		var printPara={
  			rowCount:1,
  			title:'住院结算明细清单',
  			columns: JSON.stringify(grid.getPrintColumns()),//表头
  			class_name: "com.chd.hrp.acc.service.autovouch.his.AccBalDetailIService",
			method_name: "queryAccBalDetailIPrint",
			bean_name: "accBalDetailIService",
			heads: JSON.stringify(heads)//表头需要打印的查询条件,可以为空
  			};
   	
  		//执行方法的查询条件
  		$.each(grid.options.parms,function(i,obj){
  			printPara[obj.name]=obj.value;
   	});
  		
   	officeGridPrint(printPara); 
	
	}
	
	</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

    <table width="100%" cellpadding="0" cellspacing="0" class="l-table-edit" style="font-size:15px" >
         <tr>
            <td align="right" class="l-table-edit-td"  style=padding-left:20px;width:120px;"><b><font color="red">*</font></b>结算日期：</td>
            <td align="left" class="l-table-edit-td">
				<table>
					<tr>
						<td>
							<input type="text" id="charge_date_b" style="font-size:12px" ltype="text"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
						<td align="right" class="l-table-edit-td"  >
							至
						</td>
						<td>
							<input type="text" id="charge_date_e" style="font-size:12px" ltype="text"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
            		</tr>
				</table>
			</td>
            
            <td align="right" class="l-table-edit-td"  style=padding-left:20px;">医嘱类别：</td>
            <td align="left" class="l-table-edit-td"><input name="io_type" type="text" id="io_type" ltype="text" /></td>
            
           
        </tr> 
        <tr>
			
			<td align="right" class="l-table-edit-td"  style=padding-left:20px;">日报序号：</td>
            <td align="left" class="l-table-edit-td"><input name="rep_no" type="text" id="rep_no" ltype="text" /></td>
			
			<td align="right" class="l-table-edit-td"  style=padding-left:20px;">收款员：</td>
            <td align="left" class="l-table-edit-td"><input name="charge_code" type="text" id="charge_code" ltype="text" /></td>
			
			<td align="right">
            	<button accessKey="Q" onclick="query();"><b>查询（<u>Q</u>）</b></button>
            	&nbsp;&nbsp;&nbsp;&nbsp;
            	<button  onclick="printDate();"><b>打 印</b></button>
            	&nbsp;&nbsp;
            </td>
    </table>
	<div id="maingrid"></div>
</body>
</html>
