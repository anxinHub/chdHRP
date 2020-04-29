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
    
    $(function ()
    {
		loadDict();
    	loadHead(null);
    });
    //查询
    function  query(){
    	grid.options.parms=[];
    	grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'ei_code',value:$("#ei_code").val()}); 
    	grid.options.parms.push({name:'check_code',value:$("#check_code").val()}); 
    	grid.options.parms.push({name:'begin_date',value:$("#begin_date").val()}); 
    	grid.options.parms.push({name:'end_date',value:$("#end_date").val()}); 
    	grid.options.parms.push({name:'bill_begin_date',value:$("#bill_begin_date").val()}); 
    	grid.options.parms.push({name:'bill_end_date',value:$("#bill_end_date").val()}); 
    	grid.options.parms.push({name:'reimburse_man',value:liger.get("reimburse_man").getValue().split(".")[0]}); 
    	//加载查询条件
    	grid.loadData(grid.where);
    }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
        	columns: [ 
           		{ display: '发票代码', name: 'ei_id', align: 'left', width: '15%', hide:true },
                { display: '发票号码', name: 'ei_code', align: 'left',width :'15%',
					render : function(rowdata, rowindex,value) {
						return "<a href=javascript:openUpdate('"+rowdata.ei_id+"|"+ rowdata.ei_code+"|"+rowdata.check_code+"')>"+rowdata.ei_code+"</a>";
					}
				},
                { display: '开票日期', name: 'ei_date', align: 'left',width :'15%'},
				{ display: '检验码', name: 'check_code', align: 'left',width :'15%' },
				{ display: '开据金额', name: 'ei_money', align: 'left',width :'15%' },
				{ display: '报销人', name: 'reimburse_man', align: 'left',width :'15%' },
				{ display: '报销日期', name: 'reimburse_date', align: 'left',width :'15%' }
			],
            dataAction: 'server',
            dataType: 'server',
            usePager:false,
            url:'queryAccEInvoice.do',
            width: '100%', 
            height: '100%',
            delayLoad : true, 
            checkbox: true,
            rownumbers:true,
            selectRowButtonOnly:true,//heightDiff: -10,
            toolbar: { items: [
                     	{ text: '查询', id:'search', click: query, icon:'search' },
                     	{ line:true },
                     	{ text: '添加', id:'add', click: add, icon:'add' },
                     	{ line:true },
                     	{ text: '删除', id:'delete', click: remove, icon:'delete' },
                     	{ line:true }
    				]},
    		onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.ei_id  + "|" + 
								rowdata.ei_code  + "|" + 
								rowdata.check_code  
						);
    				} 
        	}
    	);

        gridManager = $("#maingrid").ligerGetGridManager();
    }
	
    function add(){
    	
    	$.ligerDialog.open({url: 'accEInvoiceAddPage.do?isCheck=false', height: 300,width: 400, title:'添加',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
    				buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveAccEInvoice(); },cls:'l-dialog-btn-highlight' }, 
    				           { text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
    				] 
    	});
    }
    
    function remove(){
    	
    	var data = gridManager.getCheckedRows();
        
    	if (data.length == 0){
        	$.ligerDialog.error('请选择行');
        }else{
            var ParamVo =[];
            $(data).each(function (){					
				ParamVo.push(
					//表的主键
					this.ei_id	+"@"+ 
					this.ei_code +"@"+ 
					this.check_code   
				)
            });
            $.ligerDialog.confirm('确定删除?', function (yes){
            	if(yes){
                	ajaxJsonObjectByUrl("deleteBatchAccEInvoice.do",{ParamVo : ParamVo.toString()},function (responseData){
                		if(responseData.state=="true"){
                			query();
                		}
                	});
            	}
            }); 
        }
    }
    
    function openUpdate(obj){
    	
		var vo = obj.split("|");
		
		var parm = "ei_id=" +vo[0]+
				"&ei_code=" +vo[1]+
				"&check_code=" +vo[2];
		
    	$.ligerDialog.open({ url : 'accEInvoiceUpdatePage.do?isCheck=false&'+ parm,data:{}, height: 300,width: 400, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
    		buttons: [ 
    			{ text: '保存', onclick: function (item, dialog) { dialog.frame.saveAccEInvoice(); },cls:'l-dialog-btn-highlight' }, 
    			{ text: '关闭', onclick: function (item, dialog) { dialog.close(); } } 
    		] 
    	});

    }

    function loadDict(){
    	$("#ei_code").ligerTextBox({width:160});
    	$("#check_code").ligerTextBox({width:160});
    	autocomplete("#reimburse_man", "../../queryEmpDictById.do?isCheck=false",  "id", "text", true, true, "", false);
    	
    	$("#begin_date").ligerTextBox({width:90});
    	$("#end_date").ligerTextBox({width:90});
    	autodate("#begin_date","yyyy-MM-dd");
    	autodate("#end_date","yyyy-MM-dd");
    	
    	$("#bill_begin_date").ligerTextBox({width:90});
    	$("#bill_end_date").ligerTextBox({width:90});
    	autodate("#bill_begin_date","yyyy-MM-dd");
    	autodate("#bill_end_date","yyyy-MM-dd");
    }   
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
		<tr>
			<td align="right" class="l-table-edit-td"  width="10%">
            	发票日期：
            </td>
            <td align="left" class="l-table-edit-td"  width="20%">
				<table>
					<tr>
						<td>
							<input class="Wdate" name="begin_date" id="begin_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
						<td align="right" class="l-table-edit-td"  >
							至
						</td>
						<td>
							<input class="Wdate" name="end_date" id="end_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
            		</tr>
				</table>
	        </td>
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;">发票号码：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="ei_code" type="text" id="ei_code" ltype="text"  />
            </td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">检验码：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="check_code" type="text" id="check_code" ltype="text"  />
            </td>
            <td align="left"></td>
		</tr>
        <tr>
            <td align="right" class="l-table-edit-td"  width="10%">
            	报销日期：
            </td>
            <td align="left" class="l-table-edit-td"  width="20%">
				<table>
					<tr>
						<td>
							<input class="Wdate" name="begin_date" id="bill_begin_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
						<td align="right" class="l-table-edit-td"  >
							至
						</td>
						<td>
							<input class="Wdate" name="end_date" id="bill_end_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
            		</tr>
				</table>
	        </td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">报销人：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="reimburse_man" type="text" id="reimburse_man" ltype="text"  />
            </td>
            <td align="left"></td>
        </tr> 
    </table>

	<div id="maingrid"></div>

</body>
</html>
