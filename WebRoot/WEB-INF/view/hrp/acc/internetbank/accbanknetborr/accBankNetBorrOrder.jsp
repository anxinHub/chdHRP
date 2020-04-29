<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<jsp:include page="${path}/inc.jsp"/>
<script src="<%=path%>/lib/hrp/acc/internetbank/icbc/common.js" type="text/javascript"></script>
<script type="text/javascript">

    var grid;
    
    var gridManager = null;

    $(function ()
    {
    	loadDict(null);
    	
    	loadHead(null);	//加载数据
    	
		$("#check_column1").change(function () { 
			
			query();
			
		});
    });
    
    
    
    //查询
    function  query(){//根据表字段进行添加查询条件
    	
		grid.options.parms=[];grid.options.newPage=1;
		
		if(check_column1_manager.getValue()){
			
			grid.options.parms.push({name:'result',value:'6'});
			
		}
		
		grid.options.parms.push({name:'emp_id',value:liger.get("emp_id").getValue()});
		
		grid.options.parms.push({name:'apply_code',value:$("#apply_code").val()});
		
		grid.options.parms.push({name:'beginDate',value:$("#beginDate").val()});
		
		grid.options.parms.push({name:'endDate',value:$("#endDate").val()});
    	
    	//加载查询条件
    	grid.loadData(grid.where);
     
    }

    function loadHead(){
    	
    	grid = $("#maingrid").ligerGrid({
			columns : [
					{display : '付款日期',name : 'trandate',width: 140,align : 'left',
						render: function (rowdata)
	                    {
							return "<a href=javascript:openUpdate('"+rowdata.apply_code+"|"+rowdata.group_id+"|"+rowdata.hos_id+"|"+rowdata.copy_code+"|"+rowdata.acc_year+"')>"+rowdata.trandate+" "+rowdata.trantime+"</a>";
	                    }
					},
					
					{display : '付款单号',name : 'apply_code',width: 120,align : 'left'},
					
					 { display: '凭证号', name: 'vouch_no', align: 'left',width:100,
						render: function (rowdata){
							if(rowdata.vouch_no){
								if(rowdata.vouch_no=='-'){
									return rowdata.vouch_no;
								}else{
									return "<a href=javascript:openSuperVouch('"+rowdata.vouch_id+"')>"+rowdata.vouch_no+"</a>";
								}
							}else{
								return '';
							}
						}
				 	},
				   {display : '凭证状态',name : 'state_name',width: 90,align : 'left'},
				 	
                   { display: '项目', name: 'proj_name', align: 'left'},
                   
                   { display: '借款人', name: 'emp_name', align: 'left'},

					{display : '总金额',name : 'totalamt',width: 80,align : 'right',
						render:function(rowdata){ return rowdata.totalamt>0?formatNumber(rowdata.totalamt,2,1):formatNumber('0',2,1)}	
					},
					
					{display : '指令状态',name : 'result',width: 150,align : 'left',
						render: function (item)
	                    {
							for (var i = 0; i < resultData.length; i++)
	                        {
	                            if (resultData[i]['id'] == item.result)
	                                return resultData[i]['text']
	                        }
	                    }	
					},
					
					//{display : '交易返回码',name : 'iretcode',width: 100,align : 'left'},
					
					{display : '交易返回描述',name : 'iretmsg',width: 240,align : 'left',
						render: function (item)
	                    {
							if(item.retcode == 'error'){return item.retmsg} 
							else{return item.iretmsg}
	                    }	
					}
					],
					dataAction : 'server',dataType : 'server',usePager : true,url : 'queryAccBankNetBorr.do',pageSize:500,delayLoad:true,
					width : '100%',height : '100%',checkbox : true,selectRowButtonOnly : true,alternatingRow: false,//heightDiff: -10,
					toolbar : {
					items : [
				         {text : '查询',id : 'search',click : query,icon : 'search'},
				         {line : true},
				         {text : '网上支付',id : 'carry',click : pay,icon : 'communication'},
						 {line : true},
				         {text : '更新指令状态',id : 'search',click : updateAccBankNetBorr,icon : 'up'},
				         {line : true}
				         /* {text : '导出',id : 'search',click : exportData,icon : 'down'},
				         {line : true} */
				         ]
			}
		});

		gridManager = $("#maingrid").ligerGetGridManager();
    }
    
	function pay(){
    	
    	var data = gridManager.getCheckedRows();
    	var ParamVo =[];
    	var str_state = '';
    	var str_sup = '';
        if (data.length == 0){
        	$.ligerDialog.error('请选择行');
        }else{
        	 $(data).each(function (){
        		 
				ParamVo.push(this.apply_code+"@"+this.totalamt);
				
        	 })
        	 
        	 var paramVo = ParamVo.toString();
        	 $.ligerDialog.open({url: 'collectAccBankNetBorrPage.do?isCheck=false&payFlag=0&paramVo='+ParamVo,height: 450,width: 870,title:'补录网上银行信息',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
 				/* buttons: [ 
 				           { text: '确定支付', onclick: function (item, dialog) {dialog.frame.saveAccBankNetBuyer(); },cls:'l-dialog-btn-highlight' }, 
 				           { text: '关闭', onclick: function (item, dialog) { dialog.close(); } } ]  */
        	 }); 
        }	
    }

  	//查询
    function  updateAccBankNetBorr(){//根据表字段进行添加查询条件

    	var data = gridManager.getCheckedRows();
    	var ParamVo =[];
		if (data.length == 0){
         	$.ligerDialog.error('请选择行');
		}else{
        	 $(data).each(function (){
 				
 				ParamVo.push(this.group_id +"@"+ this.hos_id +"@"+ this.copy_code+"@"+ this.acc_year+"@" + this.fseqno+"@" + this.vouch_id+"@" + this.vouch_state);
 				
         	 })

        	 $.ligerDialog.confirm('确定更新指令状态?', function (yes){if(yes){
     			
     			ajaxJsonObjectByUrl("updateAccBankNetBorr.do?isCheck=false&ParamVo="+ParamVo.toString(),{}, function(responseData) {
     	
     				if (responseData.state == "true") {
     					
     					query();
     					
     				}
     			});
     		}});
         }	
    }
    
	function openUpdate(obj){
    	
    	var vo = obj.split("|");
		var parm = "apply_code="+vo[0]   
			+"&group_id="+ vo[1]   
			+"&hos_id="+ vo[2]   
			+"&copy_code="+ vo[3]
			+"&acc_year="+ vo[4];
    	$.ligerDialog.open({ url : 'queryAccBankNetBorrRdPage.do?isCheck=false&' + parm,data:{}, height: 520,width: 1180, title:'支付单明细',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true});

    }
    
    function loadDict(){
    	
		autodate("#beginDate", "yyyy-mm-dd", "month_first");autodate("#endDate", "yyyy-mm-dd", "month_last");
		
		$("#beginDate").ligerTextBox({width:120});
		
		$("#endDate").ligerTextBox({width:120});
		
    	$("#apply_code").ligerTextBox({width : 180});

    	autocomplete("#emp_id","../../queryEmpDict.do?isCheck=false", "id","text", true, true,null,false,null,"180");
    	
    	check_column1_manager = $("#check_column1").ligerCheckBox();check_column1_manager.setValue(false);

     } 

    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	
				<table cellpadding="0" cellspacing="0" class="l-table-edit">
			   	 	<tr>
			            <td align="right" class="l-table-edit-td" style="padding-left: 20px;">付款日期：</td>
						<td align="left" class="l-table-edit-td" style="width: 90px"><input class="Wdate" name="beginDate" id="beginDate" type="text"
							onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
						<td align="left" style="width: 15">至：</td>
						<td align="left" style="width: 90px"><input class="Wdate" name="endDate" id="endDate" type="text"
						onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
						<td align="right" class="l-table-edit-td"  style="padding-left:20px;">申请人：</td>
			            <td align="left" class="l-table-edit-td" colspan="3"><input name="emp_id" type="text" id="emp_id"  /></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">报销单号：</td>
			            <td align="left" class="l-table-edit-td"><input name="apply_code" type="text" id="apply_code"  /></td>
			            <td align="left" style="padding-left:20px;"><input name="check_column1" type="checkbox" id="check_column1" ltype="text"  />显示失败</td>
			            
			        </tr>
	   			  </table>
	   		<div id="maingrid"></div>
	
	
</body>
</html>
