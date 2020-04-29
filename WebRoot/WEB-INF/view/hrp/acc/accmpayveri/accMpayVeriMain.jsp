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
    //根据表字段进行添加查询条件
      grid.options.parms.push({name:'beginDate',value:$("#beginDate").val()});
	  grid.options.parms.push({name:'endDate',value:$("#endDate").val()});
	  grid.options.parms.push({name:'pay_bill_type',value:$("#pay_bill_type").val()}); 
	  grid.options.parms.push({name:'state',value:$("#state").val()}); 
	  grid.options.parms.push({name:'sup_id',value:liger.get("sup_id").getValue().split(".")[0]});
	  grid.options.parms.push({name:'pay_bill_no',value:$("#pay_bill_no").val()});
	
	  //加载查询条件
	  grid.loadData(grid.where);
	
    }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
            columns: [ 
                      { display: '付款单号', name: 'pay_bill_no', align: 'left',width:120/* ,
 					 			render:function(rowdata,index,value){
 					 				if(rowdata.pay_bill_type == 0){
 					 					return "<a href=javascript:openUpdate('"+
 				 						rowdata.group_id+"|"+
 				 						rowdata.hos_id+"|"+
 				 						rowdata.copy_code+"|"+
 				 						rowdata.pay_id+"|"+
 				 						rowdata.state+"')>"+rowdata.pay_bill_no+"</a>";
 					 				}else{
 					 					return "<a href=javascript:openUpdate('"+
 				 						rowdata.group_id+"|"+
 				 						rowdata.hos_id+"|"+
 				 						rowdata.copy_code+"|"+
 				 						rowdata.pay_id+"|"+
 				 						rowdata.state+"')>"+rowdata.pay_bill_no+"<font color='red'>(退)</font></a>";
 					 				}
 					 			} */
 					 		},
 					 { display: '单据类型', name: 'pay_bill_type', align: 'left',width:100,
 					 			render : function(rowdata,rowindex,value){
 					 				if(rowdata.pay_bill_type == 0){
 					 					return "付款单";
 					 				}else if((rowdata.pay_bill_type == 1)){
 					 					return "退款单";
 					 				}
 					 			}
 					 		},
                      { display: '付款日期', name: 'pay_date', align: 'left',width:100
 					 		},
                      { display: '供应商', name: 'sup_name', align: 'left',width:200
 					 		},
 				 	 { display: '付款金额', name: 'pay_money', align: 'right',width:100,
 					 			render:function(rowdata,rowindex,value){
 					 				return formatNumber(rowdata.pay_money ,'4', 1);
 					 			}
 					 		},
 					 { display: '制单人', name: 'maker_name', align: 'left',width:96
 					 		},
 					 { display: '制单日期', name: 'make_date', align: 'left',width:100
 					 		},
                      { display: '审核人', name: 'checker_name', align: 'left',width:96
 					 		},
                      { display: '审核日期', name: 'chk_date', align: 'left',width:100
 					 		},
                      { display: '状态', name: 'state', align: 'left',width:80,
 					 			render:function(rowdata,index,value){
 					 				if(rowdata.state == 1){
 					 					return "未审核";
 					 				}else if (rowdata.state == 2){
 					 					return "已审核";
 					 				}else{
 					 					return "记账";
 					 				}
 					 			}
 					 		}
                      ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'../../mat/matpay/matpaymain/queryMatPayMain.do?isCheck=false',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,selectRowButtonOnly:true,delayLoad: true
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    /* function openUpdate(obj){
		
		var vo = obj.split("|");
		var parm = 
			"group_id="+vo[0]   +"&"+ 
			"hos_id="+vo[1]   +"&"+ 
			"copy_code="+vo[2]   +"&"+ 
			"pay_id="+vo[3] 
			$.ligerDialog.open({ url : '../../mat/matpay/matpaymain/matPayMainUpdatePage.do?isCheck=false&'+parm,data:{}, top: 0,
				height: 580,width: 1000, title:'修改',modal:true,showToggle:false,showMax:true,
				showMin: true,isResize:true,
				});
 		
    } */
    
    function loadDict(){
            //字典下拉框
    	//供应商下拉框
		autocomplete("#sup_id", "../../sys/querySupDictDict.do?isCheck=false", "id", "text", true, true,'',false,'',300);
		
		autodate("#beginDate", "yyyy-mm-dd", "month_first");
    	autodate("#endDate", "yyyy-mm-dd", "month_last");
		$("#beginDate").ligerTextBox({width:100});
        $("#endDate").ligerTextBox({width:100});
        $("#state").ligerTextBox({width:160});
		$("#sup_id").ligerTextBox({width:220});
        $("#pay_bill_type").ligerTextBox({width:160});
        $("#pay_bill_no").ligerTextBox({width:160});

    }   
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
    	<tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">付款日期：</td>
        	<td align="left" class="l-table-edit-td"  width="20%">
				<table>
					<tr>
						<td>
							<input class="Wdate" name="beginDate" id="beginDate" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
           				</td>
						<td align="right" class="l-table-edit-td"  > 至 </td>
						<td>
							<input class="Wdate" name="endDate" id="endDate" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
            			</td>
            		</tr>
				</table>
	        </td>  
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">付款单号：</td>
            <td align="left" class="l-table-edit-td"><input name="pay_bill_no" type="text" id="pay_bill_no" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">状态：</td>
             <td align="left" class="l-table-edit-td">
            	<select name="state" id="state"style="width: 135px;" >
            			<option value="">请选择</option>
                		<option value="1">未审核</option>
                		<option value="2">审核</option>
                		<option value="3">已确认</option>
            	</select>
            </td>
            <td align="left"></td>
        </tr> 
        <tr>
        	<td align="right" class="l-table-edit-td" style="padding-left:20px;">供应商：</td>
            <td align="left" class="l-table-edit-td" ><input name="sup_id" type="text" id="sup_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">单据类型：</td>
            <td align="left" class="l-table-edit-td">
	            <select name="pay_bill_type" id="pay_bill_type"style="width: 135px;" >
	            			<option value="">请选择</option>
	                		<option value="0">付款单</option>
	                		<option value="1">退款单</option>
	            </select>
	            </td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"></td>
            <td align="left" class="l-table-edit-td">
            <input class="liger-button" type="button" value=" 查询" onclick="query();" /> </td>
            <td align="left"></td>
        </tr> 
    </table>
    
	<div id="maingrid"></div>

</body>
</html>
