<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"><html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
    var grid;
    var gridManager = null;
    $(function ()
    {
    	//加载数据
    	loadHead(null);	
    	loadDict();
		
    });
    //查询
    function  query(){
    	grid.options.parms=[];
    	grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'type_code',value:$("#type_code").val()});        
    	grid.options.parms.push({name:'type_name',value:$("#type_name").val()});
    	//加载查询条件
    	grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
     }
    
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '需求计划单号', name: 'req_code', align: 'left'},
                     { display: '药品编码', name: 'inv_code', align: 'left'},
					 { display: '药品名称', name: 'inv_name', align: 'left'},
                     { display: '规格型号', name: 'inv_model', align: 'left'},
		             { display: '计量单位', name: 'unit_name', align: 'left'},
					 { display: '单价', name: 'price', align: 'right',
								render : function(rowdata, rowindex, value) {
									rowdata.price = value == null ? "" : formatNumber(value, '${p08006 }', 0);
									return value == null ? "" : formatNumber(value, '${p08006 }', 1);
								}
						 	},
					 { display: '计划数量', name: 'req_amount', align: 'left'},	
				     { display: '采购数量', name: 'pur_amount', align: 'left',type: 'int', editor: { type: 'int'}},	
					 { display: '金额', name: 'amount_money', align: 'right',render:
							function(rowdata){
							 	return formatNumber(rowdata.price * rowdata.pur_amount,2,1);
						 	}
					}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,
                     url:'queryMedRequireAmountDetail.do?isCheck=false&req_rela='+'${req_rela}',
                     width: '100%', height: '100%', rownumbers:true,enabledEdit : true,
                     selectRowButtonOnly:true,
                     toolbar: { items: [
                             { text: '确认', id:'confirm', click: saveReqPurRela,icon:'' },{ line:true }
          				]}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function loadDict(){    	
    	autocomplete("#inv_code", "../../queryMedInvDict.do?isCheck=false", "id", "text", true, true,{inv_id:'${inv_id}'},true);//状态 下拉框
    	$("#inv_code").ligerTextBox({width:300,disabled: true });
    }
    
   //保存 
   function saveReqPurRela(){	   
	   var allData = gridManager.getData();	   
	   var pur_amount = null;	   
	   var req_amount = null;	   
	   var flag = true;	   
	   $(allData).each(function(i,v){
		   //过滤空行
		    if(v.req_code != '' && v.req_code != undefined && v.req_code != null){
			   	if(v.pur_amount == undefined || v.pur_amount == ''){ 			
	   				$.ligerDialog.error('采购数量不能为零');  			
	   				return flag = false;
	   			}
			   	pur_amount = v.pur_amount;		   
			   	req_amount = v.amount;
		    }
	   });
	   
	   if(flag == false){  
		   return;
	   }
	   
	   var inv_id = '${inv_id}';	   
	   var req_id = '${req_id}';	   
	   var rowindex = '${rowindex}';	   
	   var req_rela = '['+'{"req_id":' + req_id + ',"req_amount":' + req_amount + ',"pur_amount":' + pur_amount +',"inv_id":' + inv_id + '}'+']';	   
	   parent.updatePurAmount(rowindex,req_rela,pur_amount);	   
	   this_close();
   }
   	
    //关闭窗口
    function this_close(){		
		 frameElement.dialog.close();
	}    
    </script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit">		 
	        <tr>
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">药品信息：</td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="inv_code" type="text" id="inv_code" ltype="text" validate="{required:true,maxlength:20}"  disabled="disabled"/>
	            </td>
	            <td align="left"></td>
	        </tr>      
    	</table>
    	<div id="maingrid"></div>
    </form>
</body>
</html>
