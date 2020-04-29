<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
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
		 loadHotkeys();
        $("#bill_id").ligerTextBox({width:160});
        $("#bill_detail_id").ligerTextBox({width:160});
        $("#in_id").ligerTextBox({width:160});
        $("#in_detail_id").ligerTextBox({width:160});
        $("#bill_money").ligerTextBox({width:160});
        $("#fav_money").ligerTextBox({width:160});
        $("#pay_money").ligerTextBox({width:160});
        $("#is_pay_all").ligerTextBox({width:160});
        $("#fav_money_temp").ligerTextBox({width:160});
        $("#pay_money_temp").ligerTextBox({width:160});
		
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	  grid.options.parms.push({name:'bill_id',value:$("#bill_id").val()}); 
    	  grid.options.parms.push({name:'bill_detail_id',value:$("#bill_detail_id").val()}); 
    	  grid.options.parms.push({name:'in_id',value:$("#in_id").val()}); 
    	  grid.options.parms.push({name:'in_detail_id',value:$("#in_detail_id").val()}); 
    	  grid.options.parms.push({name:'bill_money',value:$("#bill_money").val()}); 
    	  grid.options.parms.push({name:'fav_money',value:$("#fav_money").val()}); 
    	  grid.options.parms.push({name:'pay_money',value:$("#pay_money").val()}); 
    	  grid.options.parms.push({name:'is_pay_all',value:$("#is_pay_all").val()}); 
    	  grid.options.parms.push({name:'fav_money_temp',value:$("#fav_money_temp").val()}); 
    	  grid.options.parms.push({name:'pay_money_temp',value:$("#pay_money_temp").val()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
     }
    //获取查询条件的数值
    function f_getWhere(){
    	if (!grid) return null;
        var clause = function (rowdata, rowindex){
                	if($("#bill_id").val()!=""){
                		return rowdata.bill_id.indexOf($("#bill_id").val()) > -1;	
                	}
                	if($("#bill_detail_id").val()!=""){
                		return rowdata.bill_detail_id.indexOf($("#bill_detail_id").val()) > -1;	
                	}
                	if($("#in_id").val()!=""){
                		return rowdata.in_id.indexOf($("#in_id").val()) > -1;	
                	}
                	if($("#in_detail_id").val()!=""){
                		return rowdata.in_detail_id.indexOf($("#in_detail_id").val()) > -1;	
                	}
                	if($("#bill_money").val()!=""){
                		return rowdata.bill_money.indexOf($("#bill_money").val()) > -1;	
                	}
                	if($("#fav_money").val()!=""){
                		return rowdata.fav_money.indexOf($("#fav_money").val()) > -1;	
                	}
                	if($("#pay_money").val()!=""){
                		return rowdata.pay_money.indexOf($("#pay_money").val()) > -1;	
                	}
                	if($("#is_pay_all").val()!=""){
                		return rowdata.is_pay_all.indexOf($("#is_pay_all").val()) > -1;	
                	}
                	if($("#fav_money_temp").val()!=""){
                		return rowdata.fav_money_temp.indexOf($("#fav_money_temp").val()) > -1;	
                	}
                	if($("#pay_money_temp").val()!=""){
                		return rowdata.pay_money_temp.indexOf($("#pay_money_temp").val()) > -1;	
                	}
        };
        return clause; 
    }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '发票ID', name: 'bill_id', align: 'left'
					 		},
                     { display: '发票明细ID', name: 'bill_detail_id', align: 'left'
					 		},
                     { display: '入库单ID', name: 'in_id', align: 'left'
					 		},
                     { display: '入库明细ID', name: 'in_detail_id', align: 'left'
					 		},
                     { display: '发票金额', name: 'bill_money', align: 'left'
					 		},
                     { display: '优惠金额', name: 'fav_money', align: 'left'
					 		},
                     { display: '累计付款', name: 'pay_money', align: 'left'
					 		},
                     { display: '全部开票标志', name: 'is_pay_all', align: 'left'
					 		},
                     { display: '优惠金额临时', name: 'fav_money_temp', align: 'left'
					 		},
                     { display: '付款金额临时', name: 'pay_money_temp', align: 'left'
					 		}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryMedBillDetail.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询（<u>E</u>）', id:'search', click: query,icon:'search' },
                     	{ line:true },
				    					{ text: '添加（<u>A</u>）', id:'add', click: add_open, icon:'add' },
				    	                { line:true },
				    	                { text: '删除（<u>D</u>）', id:'delete', click: remove,icon:'delete' },
											{ line:true }, 
				    	                { text: '导出Excel（<u>E</u>）', id:'export', click: exportExcel,icon:'pager' },
						                { line:true },
						                { text: '打印（<u>P</u>）', id:'print', click: printDate,icon:'print' },
						                { line:true },
						                { text: '下载导入模板（<u>B</u>）', id:'downTemplate', click:downTemplate,icon:'down' },
						                { line:true },
						                { text: '导入（<u>I</u>）', id:'import', click: imp,icon:'up' }
				    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code   + "|" + 
								rowdata.bill_id 
							);
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function add_open(){
    	
    	var index = layer.open({
					type : 2,
					title : '科室申请资产明细',
					shadeClose : false,
					shade : false,
					maxmin : true, //开启最大化最小化按钮
					area : [ '893px', '500px' ],
					content : 'medBillDetailAddPage.do?isCheck=false'
				});
				layer.full(index);
    	}
    	
    function remove(){
    	
    	var data = gridManager.getCheckedRows();
                    if (data.length == 0){
                    	$.ligerDialog.error('请选择行');
                    }else{
                        var ParamVo =[];
                        $(data).each(function (){					
																ParamVo.push(
																this.group_id   +"@"+ 
																this.hos_id   +"@"+ 
																this.copy_code   +"@"+ 
																this.bill_id 
																) });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteMedBillDetail.do",{ParamVo : ParamVo},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
    	}
    function imp(){
    	
    	var index = layer.open({
					type : 2,
					title : '科室申请资产明细',
					shadeClose : false,
					shade : false,
					maxmin : true, //开启最大化最小化按钮
					area : [ '893px', '500px' ],
					content : 'medBillDetailImportPage.do?isCheck=false'
				});
				layer.full(index);
    	}	
    function downTemplate(){
    	
    	location.href = "downTemplate.do?isCheck=false";
    	}	
   
    function openUpdate(obj){
    		
		var vo = obj.split("|");
		var parm = 
			"group_id="+vo[0]   +"&"+ 
			"hos_id="+vo[1]   +"&"+ 
			"copy_code="+vo[2]   +"&"+ 
			"bill_id="+vo[3] 
		 
    var index = layer.open({
					type : 2,
					title : '科室申请资产明细',
					shadeClose : false,
					shade : false,
					maxmin : true, //开启最大化最小化按钮
					area : [ '893px', '500px' ],
					content : 'medBillDetailImportPage.do?isCheck=false&' + parm
				});
				layer.full(index);	
    
    }
    function loadDict(){
            //字典下拉框
            
         }  
    //键盘事件
	  function loadHotkeys() {

		hotkeys('Q', query);

		hotkeys('A', add);
		hotkeys('D', remove);

		hotkeys('B', downTemplate);

		hotkeys('E', exportExcel);

		hotkeys('P', printDate);
		hotkeys('I', imp);
		

	 }
  //打印数据
	 function printDate(){
		//有数据直接打印
		if($("#resultPrint > table > tbody").html()!=""){
			lodopPrinterTable("resultPrint","开始打印","保存一个发票对应的入库单，及金额",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备打印数据,请稍候...');

		var printPara={
			usePager:false,
           bill_id:$("#bill_id").val(),
           bill_detail_id:$("#bill_detail_id").val(),
           in_id:$("#in_id").val(),
           in_detail_id:$("#in_detail_id").val(),
           bill_money:$("#bill_money").val(),
           fav_money:$("#fav_money").val(),
           pay_money:$("#pay_money").val(),
           is_pay_all:$("#is_pay_all").val(),
           fav_money_temp:$("#fav_money_temp").val(),
           pay_money_temp:$("#pay_money_temp").val()
         };
		ajaxJsonObjectByUrl("queryMedBillDetail.do",printPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
					 trHtml+="<td>"+item.bill_id+"</td>"; 
					 trHtml+="<td>"+item.bill_detail_id+"</td>"; 
					 trHtml+="<td>"+item.in_id+"</td>"; 
					 trHtml+="<td>"+item.in_detail_id+"</td>"; 
					 trHtml+="<td>"+item.bill_money+"</td>"; 
					 trHtml+="<td>"+item.fav_money+"</td>"; 
					 trHtml+="<td>"+item.pay_money+"</td>"; 
					 trHtml+="<td>"+item.is_pay_all+"</td>"; 
					 trHtml+="<td>"+item.fav_money_temp+"</td>"; 
					 trHtml+="<td>"+item.pay_money_temp+"</td>"; 
				 trHtml+="</tr>";
				$("#resultPrint > table > tbody").empty();
				$("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			lodopPrinterTable("resultPrint","开始打印","保存一个发票对应的入库单，及金额",true);
	    },true,manager);
		return;
	 }
	
	 
	 //导出数据
	 function exportExcel(){
		//有数据直接导出
		if($("#resultPrint > table > tbody").html()!=""){
			lodopExportExcel("resultPrint","导出Excel","保存一个发票对应的入库单，及金额.xls",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');

		var exportPara={
			usePager:false,
           bill_id:$("#bill_id").val(),
           bill_detail_id:$("#bill_detail_id").val(),
           in_id:$("#in_id").val(),
           in_detail_id:$("#in_detail_id").val(),
           bill_money:$("#bill_money").val(),
           fav_money:$("#fav_money").val(),
           pay_money:$("#pay_money").val(),
           is_pay_all:$("#is_pay_all").val(),
           fav_money_temp:$("#fav_money_temp").val(),
           pay_money_temp:$("#pay_money_temp").val()
         };
		ajaxJsonObjectByUrl("queryMedBillDetail.do",exportPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
					 trHtml+="<td>"+item.bill_id+"</td>"; 
					 trHtml+="<td>"+item.bill_detail_id+"</td>"; 
					 trHtml+="<td>"+item.in_id+"</td>"; 
					 trHtml+="<td>"+item.in_detail_id+"</td>"; 
					 trHtml+="<td>"+item.bill_money+"</td>"; 
					 trHtml+="<td>"+item.fav_money+"</td>"; 
					 trHtml+="<td>"+item.pay_money+"</td>"; 
					 trHtml+="<td>"+item.is_pay_all+"</td>"; 
					 trHtml+="<td>"+item.fav_money_temp+"</td>"; 
					 trHtml+="<td>"+item.pay_money_temp+"</td>"; 
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").empty();
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			lodopExportExcel("resultPrint","导出Excel","保存一个发票对应的入库单，及金额.xls",true);
	    },true,manager);
		return;
	 }		  
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">发票ID：</td>
            <td align="left" class="l-table-edit-td"><input name="bill_id" type="text" id="bill_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">发票明细ID：</td>
            <td align="left" class="l-table-edit-td"><input name="bill_detail_id" type="text" id="bill_detail_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">入库单ID：</td>
            <td align="left" class="l-table-edit-td"><input name="in_id" type="text" id="in_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">入库明细ID：</td>
            <td align="left" class="l-table-edit-td"><input name="in_detail_id" type="text" id="in_detail_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">发票金额：</td>
            <td align="left" class="l-table-edit-td"><input name="bill_money" type="text" id="bill_money" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">优惠金额：</td>
            <td align="left" class="l-table-edit-td"><input name="fav_money" type="text" id="fav_money" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">累计付款：</td>
            <td align="left" class="l-table-edit-td"><input name="pay_money" type="text" id="pay_money" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">全部开票标志：</td>
            <td align="left" class="l-table-edit-td"><input name="is_pay_all" type="text" id="is_pay_all" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">优惠金额临时：</td>
            <td align="left" class="l-table-edit-td"><input name="fav_money_temp" type="text" id="fav_money_temp" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">付款金额临时：</td>
            <td align="left" class="l-table-edit-td"><input name="pay_money_temp" type="text" id="pay_money_temp" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
    </table>

	<div id="maingrid"></div>
	<div id="resultPrint" style="display:none">
	   	<table width="100%">
			<thead>
		 
			<tr>
                <th width="200">发票ID</th>	
                <th width="200">发票明细ID</th>	
                <th width="200">入库单ID</th>	
                <th width="200">入库明细ID</th>	
                <th width="200">发票金额</th>	
                <th width="200">优惠金额</th>	
                <th width="200">累计付款</th>	
                <th width="200">全部开票标志</th>	
                <th width="200">优惠金额临时</th>	
                <th width="200">付款金额临时</th>	
			</tr>
			   	</thead>
			   	<tbody></tbody>
	   	</table>
   	</div>
</body>
</html>
