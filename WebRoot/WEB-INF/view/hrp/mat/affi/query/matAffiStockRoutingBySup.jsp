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
<script src="<%=path%>/lib/hrp/mat/mat.js"	type="text/javascript"></script>
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    var renderFunc = {
    }
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
		loadHotkeys();
		//query();
    }); 
    //查询
    function  query(){
    	if(!$("#begin_confirm_date").val()){
        	$.ligerDialog.warn("开始日期不能为空！");
        	return false;
        }
        if(!$("#end_confirm_date").val()){
        	$.ligerDialog.warn("结束日期不能为空");
        	return false;
        }
        if(!dateValid("begin_confirm_date","end_confirm_date")){
        	$.ligerDialog.warn("结束日期不能小于开始日期");
        	return false;
        }
        
        if(!liger.get("store_code").getValue()){
        	$.ligerDialog.warn("请选择仓库！");
        	return false;
        }
        /* if(!liger.get("sup_code").getValue()){
        	$.ligerDialog.warn("请选择供应商！");
        	return false;
        } */
        
		grid.options.parms=[];
		grid.options.newPage=1;
        //根据表字段进行添加查询条件
        grid.options.parms.push({name : 'begin_confirm_date',value : $("#begin_confirm_date").val()});
        grid.options.parms.push({name : 'end_confirm_date',value : $("#end_confirm_date").val()});
        grid.options.parms.push({name : 'store_id',value : liger.get("store_code").getValue().split(",")[0]}); 
        grid.options.parms.push({name : 'sup_id',value : liger.get("sup_code").getValue().split(",")[0]}); 
		grid.options.parms.push({name : 'inv_code',value : $("#inv_code").val()});
		grid.options.parms.push({name : 'batch_no',value : $("#batch_no").val()});
    	//加载查询条件
    	grid.loadData(grid.where);
     }
    //打印回调方法
    function lodopPrint(){
 		grid.options.lodop.title="库存分布查询(供应商)";
 		var head="<table class='head' width='100%'><tr><td>期间："+$("#begin_confirm_date").val().getValue()+"至"+$("#end_confirm_date").val()+"</td>";
  		//head=head+"<td align='left'>"+liger.get("store_code").getText()+"&nbsp;&nbsp;&nbsp;&nbsp;"+liger.get("sup_code").getText()+"</td></tr>";
  		head=head+"</table>";
  		grid.options.lodop.head=head;
    }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [
						{ display: '仓库名称', name: 'store_name', align: 'left',width: '140'
						}, { display: '供应商', name: 'sup_name', align: 'left', minWidth: '200'
			          	}, { display: '交易编码', name: 'bid_code', align: 'left',width: '110'
			          	}, { display: '材料编码', name: 'inv_code', align: 'left', minWidth: '150'
				 		}, { display: '材料名称', name: 'inv_name', align: 'left',width: '260'
				 		}, { display: '规格型号', name: 'inv_model', align: 'left', minWidth: '150'
				 		}, { display: '计量单位', name: 'unit_name', align: 'left', minWidth: '80'
				 		}, { display: '批号', name: 'batch_no', align: 'left', minWidth: '80'
				 		}, { display: '单价', name: 'price', align: 'right', minWidth: '80',
				 			 render : function(rowdata, rowindex, value) {
									return formatNumber(rowdata.price ==null ? 0 : rowdata.price, '${p04006}', 1);
								}
				 		}, { display: '上期结存量', name: 'last_hold', align: 'right', minWidth: '80'
				 		}, { display: '本期增加', name: 'in_amount', align: 'right', minWidth: '80'
						}, { display: '本期减少', name: 'out_amount', align: 'right', minWidth: '80'
				 		}, { display: '本期结存量', name: 'this_hold', align: 'right', minWidth: '80'
				 		}],
						dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatAffiStockRoutingBySup.do?isCheck=false',
						width: '100%', height: '100%',rownumbers:true,
						delayLoad: true,//初始化不加载，默认false
						selectRowButtonOnly:true,//heightDiff: -10,
						toolbar: { items: [
							{ text: '查询（<u>Q</u>）', id:'search', click: query, icon:'search' },
							{ line:true }
				]}
			});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    //键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
	}
	
    function loadDict(){
		 //字典下拉框
		/*var date = getCurrentDate();
        var aa = date.split(';');
        year = aa[0];
        month = aa[1];
		//返回当前年,当前月,当前日期,当前月第一天,当前月最后一天,上个月,上月第一天，上月最后一天
		autocompleteAsync("#year", "../../queryMatYear.do?isCheck=false", "id", "text", true, true, "", false, year, "60");
        autocompleteAsync("#month", "../../queryMatMonth.do?isCheck=false", "id", "text", true, true, "", false, month, "50"); */
		
		autocompleteAsync("#sup_code", "../../queryHosSupDict.do?isCheck=false", "id", "text", true, true, "", false, false, 260, false, 240);
/* 		autocompleteAsync("#store_code", "../../queryMatStoreByRead.do?isCheck=false", "id", "text", true, true,{is_com : 1},true); */
		autocompleteAsync("#store_code", "../../queryMatStoreDictDate.do?isCheck=false", "id", "text", true, true,{is_com : 1,read_or_write:1},true);
		autodate("#begin_confirm_date", "yyyy-MM-dd", "month_first") ;
		autodate("#end_confirm_date", "yyyy-MM-dd", "month_last") ;
		$("#begin_confirm_date").ligerTextBox({width:110});
		$("#end_confirm_date").ligerTextBox({width:110});
		$("#batch_no").ligerTextBox({width:160});
        $("#inv_model").ligerTextBox({width:160});
        $("#inv_code").ligerTextBox({width:240});
	}  
	
	</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
	<div class="search-block clearfix">
	
	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" >
        <tr>
        	<td align="right" class="l-table-edit-td"  width="10%"> 统计日期：</td>
			<td align="left" class="l-table-edit-td"  width="20%">
				<table>
					<tr>
						<td>
							<input class="Wdate" name="begin_confirm_date" id="begin_confirm_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
						<td align="right" class="l-table-edit-td"  > 至 </td>
						<td>
							<input class="Wdate" name="end_confirm_date" id="end_confirm_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
            		</tr>
				</table>
			</td>
			
	        <td align="right" class="l-table-edit-td"  width="10%"><font color="red">*</font>仓库：</td>
			<td align="left" class="l-table-edit-td" width="20%">
            	<input name="store_code" type="text" id="store_code" ltype="text" validate="{required:false}" />
            </td>
            
            <td align="right" class="l-table-edit-td"  width="10%">供应商：</td>
			<td align="left" class="l-table-edit-td" width="20%">
            	<input name="sup_code" type="text" id="sup_code" ltype="text" validate="{required:false}" />
            </td>
            
        </tr> 
        <tr>
        	
	        <td align="right" class="l-table-edit-td" width="10%">材料信息：</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="inv_code" type="text" id="inv_code" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
            
            <td align="right" class="l-table-edit-td"  width="10%">批号：</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="batch_no" type="text" id="batch_no" ltype="text" required="true" validate="{required:true}" />
            </td>
            
        </tr>
    </table>
	</div>
	<div id="maingrid"></div>
</body>
</html>
