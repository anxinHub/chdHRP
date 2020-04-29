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
		$("#begin_date").ligerTextBox({width:160});
		$("#end_date").ligerTextBox({width:160});
		$("#inv_code").ligerTextBox({width:160});
    	//加载数据
    	loadHead(null);	
		loadHotkeys();
		if("${inv_code}" != null && "${inv_code}" != ""){
			$("#inv_code").val("${inv_code}");	
		}
		query();
    });
    //查询
    function  query(){
		grid.options.parms=[];
		grid.options.newPage=1;
        //根据表字段进行添加查询条件
		grid.options.parms.push({name:'begin_date',value:$("#begin_date").val()}); 
		grid.options.parms.push({name:'end_date',value:$("#end_date").val()}); 
		grid.options.parms.push({name:'inv_code',value:$("#inv_code").val()}); 
    	
		//加载查询条件
    	grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '变更序号', name: 'inv_no', align: 'center', width: '8%',
                    	 render : function(rowdata, rowindex, value) {
         					return '<a href=javascript:update_open("' 
         							+ rowdata.group_id 
         							+ ',' + rowdata.hos_id 
         							+ ',' + rowdata.copy_code 
         							+ ',' + rowdata.inv_no
         							+ ',' + rowdata.inv_id
         							+ '")>'+rowdata.inv_no+'</a>';
         				}
					 },
                     { display: '材料编码', name: 'inv_code', align: 'left', width: '12%' 
					 		},
                     { display: '材料名称', name: 'inv_name', align: 'left', width: '20%'
					 		},
                     { display: '规格型号', name: 'inv_model', align: 'left', width: '12%'
					 		},
                     { display: '计量单位', name: 'unit_name', align: 'left', width: '8%'
					 		},
                     { display: '变更人', name: 'change_user_name', align: 'left', width: '10%'},
                     { display: '变更时间', name: 'change_date', align: 'left', width: '10%'},
                     { display: '变更原因', name: 'change_note', align: 'left', width: '10%'},
                     { display: '停用日期', name: 'edate', align: 'left', width: '10%'},
                     { display: '是否停用', name: 'is_stop', align: 'left', width: '8%',
					 			render : function(rowdata, rowindex,value) {
									if(rowdata.is_stop == 1){
										return "是";
									}else{
										return "否";
									}
								}	
					 }
                     ],
                     dataAction: 'server',dataType: 'server',
                     usePager:true,
                    
                     url:'queryMatInvDict.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
         			 delayLoad: true,
                     selectRowButtonOnly:true,//heightDiff: -20,
                     toolbar: { items: [
                     	{ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' },
                     	{ line:true }/* ,
				    	{ text: '打印（<u>P</u>）', id:'print', click: printDate,icon:'print' },
						{ line:true } */
					]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.inv_no   + "|" + 
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code   + "|" + 
								rowdata.inv_id 
							);
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    function update_open(obj){
    	var vo = obj.split(",");
		var paras = "group_id="+vo[0]  
			+"&"+ "hos_id="+vo[1]
			+"&"+ "copy_code="+vo[2] 
			+"&"+ "inv_no="+vo[3]
			+"&"+ "inv_id="+vo[4];
		
		$.ligerDialog.open({
			title: '物资变更明细',
			height: 500,
			width: 1000,
			url: 'matInvDictUpdatePage.do?isCheck=false&' + paras.toString(),
			modal: true, showToggle: false, showMax: true, showMin: true, isResize: true, top : 1,
			buttons: [
				{ text: '取消', onclick: function(item, dialog){dialog.close();}, cls:'l-dialog-btn-highlight'}
			]
		});   
    }
    //键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
		/* hotkeys('P', printDate); */
	 }
 /*  //打印数据
	 function printDate(){
		//有数据直接打印
		if($("#resultPrint > table > tbody").html()!=""){
			lodopPrinterTable("resultPrint","开始打印","04106 物资材料变更表",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备打印数据,请稍候...');

		var printPara={
			usePager:false,
           inv_no:$("#inv_no").val(),
           inv_id:$("#inv_id").val(),
           inv_code:$("#inv_code").val(),
           inv_name:$("#inv_name").val(),
           alias:$("#alias").val(),
           mat_type_no:$("#mat_type_no").val(),
           mat_type_id:$("#mat_type_id").val(),
           fina_type_code:$("#fina_type_code").val(),
           inv_model:$("#inv_model").val(),
           unit_code:$("#unit_code").val(),
           inv_attr_code:$("#inv_attr_code").val(),
           amortize_type:$("#amortize_type").val(),
           price_type:$("#price_type").val(),
           sup_id:$("#sup_id").val(),
           fac_no:$("#fac_no").val(),
           fac_id:$("#fac_id").val(),
           plan_price:$("#plan_price").val(),
           price_rate:$("#price_rate").val(),
           sell_price:$("#sell_price").val(),
           is_batch:$("#is_batch").val(),
           is_quality:$("#is_quality").val(),
           is_dura:$("#is_dura").val(),
           stay_time:$("#stay_time").val(),
           is_overstock:$("#is_overstock").val(),
           is_sec_whg:$("#is_sec_whg").val(),
           is_shel_make:$("#is_shel_make").val(),
           is_add_sale:$("#is_add_sale").val(),
           is_bar:$("#is_bar").val(),
           bar_code_new:$("#bar_code_new").val(),
           is_cert:$("#is_cert").val(),
           is_highvalue:$("#is_highvalue").val(),
           is_com:$("#is_com").val(),
           is_charge:$("#is_charge").val(),
           sdate:$("#sdate").val(),
           edate:$("#edate").val(),
           per_weight:$("#per_weight").val(),
           per_volum:$("#per_volum").val(),
           brand_name:$("#brand_name").val(),
           agent_name:$("#agent_name").val(),
           inv_structure:$("#inv_structure").val(),
           inv_usage:$("#inv_usage").val(),
           user_code:$("#user_code").val(),
           create_date:$("#create_date").val(),
           note:$("#note").val(),
           is_stop:$("#is_stop").val()
         };
		ajaxJsonObjectByUrl("queryMatInvDict.do",printPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
					 trHtml+="<td>"+item.inv_no+"</td>"; 
					 trHtml+="<td>"+item.inv_id+"</td>"; 
					 trHtml+="<td>"+item.inv_code+"</td>"; 
					 trHtml+="<td>"+item.inv_name+"</td>"; 
					 trHtml+="<td>"+item.alias+"</td>"; 
					 trHtml+="<td>"+item.mat_type_no+"</td>"; 
					 trHtml+="<td>"+item.mat_type_id+"</td>"; 
					 trHtml+="<td>"+item.fina_type_code+"</td>"; 
					 trHtml+="<td>"+item.inv_model+"</td>"; 
					 trHtml+="<td>"+item.unit_code+"</td>"; 
					 trHtml+="<td>"+item.inv_attr_code+"</td>"; 
					 trHtml+="<td>"+item.amortize_type+"</td>"; 
					 trHtml+="<td>"+item.price_type+"</td>"; 
					 trHtml+="<td>"+item.sup_id+"</td>"; 
					 trHtml+="<td>"+item.fac_no+"</td>"; 
					 trHtml+="<td>"+item.fac_id+"</td>"; 
					 trHtml+="<td>"+item.plan_price+"</td>"; 
					 trHtml+="<td>"+item.price_rate+"</td>"; 
					 trHtml+="<td>"+item.sell_price+"</td>"; 
					 trHtml+="<td>"+item.is_batch+"</td>"; 
					 trHtml+="<td>"+item.is_quality+"</td>"; 
					 trHtml+="<td>"+item.is_dura+"</td>"; 
					 trHtml+="<td>"+item.stay_time+"</td>"; 
					 trHtml+="<td>"+item.is_overstock+"</td>"; 
					 trHtml+="<td>"+item.is_sec_whg+"</td>"; 
					 trHtml+="<td>"+item.is_shel_make+"</td>"; 
					 trHtml+="<td>"+item.is_add_sale+"</td>"; 
					 trHtml+="<td>"+item.is_bar+"</td>"; 
					 trHtml+="<td>"+item.bar_code_new+"</td>"; 
					 trHtml+="<td>"+item.is_cert+"</td>"; 
					 trHtml+="<td>"+item.is_highvalue+"</td>"; 
					 trHtml+="<td>"+item.is_com+"</td>"; 
					 trHtml+="<td>"+item.is_charge+"</td>"; 
					 trHtml+="<td>"+item.sdate+"</td>"; 
					 trHtml+="<td>"+item.edate+"</td>"; 
					 trHtml+="<td>"+item.per_weight+"</td>"; 
					 trHtml+="<td>"+item.per_volum+"</td>"; 
					 trHtml+="<td>"+item.brand_name+"</td>"; 
					 trHtml+="<td>"+item.agent_name+"</td>"; 
					 trHtml+="<td>"+item.inv_structure+"</td>"; 
					 trHtml+="<td>"+item.inv_usage+"</td>"; 
					 trHtml+="<td>"+item.user_code+"</td>"; 
					 trHtml+="<td>"+item.create_date+"</td>"; 
					 trHtml+="<td>"+item.note+"</td>"; 
					 trHtml+="<td>"+item.is_stop+"</td>"; 
				 trHtml+="</tr>";
				$("#resultPrint > table > tbody").empty();
				$("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			//alert($("#resultPrint").html())
			lodopPrinterTable("resultPrint","开始打印","04106 物资材料变更表",true);
	    },true,manager);
		return;
	 }
	 */
	 
	 //导出数据
	 function exportExcel(){
		//有数据直接导出
		if($("#resultPrint > table > tbody").html()!=""){
			lodopExportExcel("resultPrint","导出Excel","04106 物资材料变更表.xls",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');

		var exportPara={
			usePager:false,
           inv_no:$("#inv_no").val(),
           inv_id:$("#inv_id").val(),
           inv_code:$("#inv_code").val(),
           inv_name:$("#inv_name").val(),
           alias:$("#alias").val(),
           mat_type_no:$("#mat_type_no").val(),
           mat_type_id:$("#mat_type_id").val(),
           fina_type_code:$("#fina_type_code").val(),
           inv_model:$("#inv_model").val(),
           unit_code:$("#unit_code").val(),
           inv_attr_code:$("#inv_attr_code").val(),
           amortize_type:$("#amortize_type").val(),
           price_type:$("#price_type").val(),
           sup_id:$("#sup_id").val(),
           fac_no:$("#fac_no").val(),
           fac_id:$("#fac_id").val(),
           plan_price:$("#plan_price").val(),
           price_rate:$("#price_rate").val(),
           sell_price:$("#sell_price").val(),
           is_batch:$("#is_batch").val(),
           is_quality:$("#is_quality").val(),
           is_dura:$("#is_dura").val(),
           stay_time:$("#stay_time").val(),
           is_overstock:$("#is_overstock").val(),
           is_sec_whg:$("#is_sec_whg").val(),
           is_shel_make:$("#is_shel_make").val(),
           is_add_sale:$("#is_add_sale").val(),
           is_bar:$("#is_bar").val(),
           bar_code_new:$("#bar_code_new").val(),
           is_cert:$("#is_cert").val(),
           is_highvalue:$("#is_highvalue").val(),
           is_com:$("#is_com").val(),
           is_charge:$("#is_charge").val(),
           sdate:$("#sdate").val(),
           edate:$("#edate").val(),
           per_weight:$("#per_weight").val(),
           per_volum:$("#per_volum").val(),
           brand_name:$("#brand_name").val(),
           agent_name:$("#agent_name").val(),
           inv_structure:$("#inv_structure").val(),
           inv_usage:$("#inv_usage").val(),
           user_code:$("#user_code").val(),
           create_date:$("#create_date").val(),
           note:$("#note").val(),
           is_stop:$("#is_stop").val()
         };
		ajaxJsonObjectByUrl("queryMatInvDict.do",exportPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
					 trHtml+="<td>"+item.inv_no+"</td>"; 
					 trHtml+="<td>"+item.inv_id+"</td>"; 
					 trHtml+="<td>"+item.inv_code+"</td>"; 
					 trHtml+="<td>"+item.inv_name+"</td>"; 
					 trHtml+="<td>"+item.alias+"</td>"; 
					 trHtml+="<td>"+item.mat_type_no+"</td>"; 
					 trHtml+="<td>"+item.mat_type_id+"</td>"; 
					 trHtml+="<td>"+item.fina_type_code+"</td>"; 
					 trHtml+="<td>"+item.inv_model+"</td>"; 
					 trHtml+="<td>"+item.unit_code+"</td>"; 
					 trHtml+="<td>"+item.inv_attr_code+"</td>"; 
					 trHtml+="<td>"+item.amortize_type+"</td>"; 
					 trHtml+="<td>"+item.price_type+"</td>"; 
					 trHtml+="<td>"+item.sup_id+"</td>"; 
					 trHtml+="<td>"+item.fac_no+"</td>"; 
					 trHtml+="<td>"+item.fac_id+"</td>"; 
					 trHtml+="<td>"+item.plan_price+"</td>"; 
					 trHtml+="<td>"+item.price_rate+"</td>"; 
					 trHtml+="<td>"+item.sell_price+"</td>"; 
					 trHtml+="<td>"+item.is_batch+"</td>"; 
					 trHtml+="<td>"+item.is_quality+"</td>"; 
					 trHtml+="<td>"+item.is_dura+"</td>"; 
					 trHtml+="<td>"+item.stay_time+"</td>"; 
					 trHtml+="<td>"+item.is_overstock+"</td>"; 
					 trHtml+="<td>"+item.is_sec_whg+"</td>"; 
					 trHtml+="<td>"+item.is_shel_make+"</td>"; 
					 trHtml+="<td>"+item.is_add_sale+"</td>"; 
					 trHtml+="<td>"+item.is_bar+"</td>"; 
					 trHtml+="<td>"+item.bar_code_new+"</td>"; 
					 trHtml+="<td>"+item.is_cert+"</td>"; 
					 trHtml+="<td>"+item.is_highvalue+"</td>"; 
					 trHtml+="<td>"+item.is_com+"</td>"; 
					 trHtml+="<td>"+item.is_charge+"</td>"; 
					 trHtml+="<td>"+item.sdate+"</td>"; 
					 trHtml+="<td>"+item.edate+"</td>"; 
					 trHtml+="<td>"+item.per_weight+"</td>"; 
					 trHtml+="<td>"+item.per_volum+"</td>"; 
					 trHtml+="<td>"+item.brand_name+"</td>"; 
					 trHtml+="<td>"+item.agent_name+"</td>"; 
					 trHtml+="<td>"+item.inv_structure+"</td>"; 
					 trHtml+="<td>"+item.inv_usage+"</td>"; 
					 trHtml+="<td>"+item.user_code+"</td>"; 
					 trHtml+="<td>"+item.create_date+"</td>"; 
					 trHtml+="<td>"+item.note+"</td>"; 
					 trHtml+="<td>"+item.is_stop+"</td>"; 
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").empty();
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			lodopExportExcel("resultPrint","导出Excel","04106 物资材料变更表.xls",true);
	    },true,manager);
		return;
	 }		  
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
        <tr>
            <td align="right" class="l-table-edit-td"  >
				启用日期：
	        </td>
			<td align="left" class="l-table-edit-td">
				<input class="Wdate" name="begin_date" id="begin_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
			</td>
			<td align="right" class="l-table-edit-td"  >
				停用日期：
			</td>
			<td align="left" class="l-table-edit-td">
				<input class="Wdate" name="end_date" id="end_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
			</td>
			<td align="right" class="l-table-edit-td"  >
				物资材料编码：
			</td>
            <td align="left" class="l-table-edit-td">
            	<input name="inv_code" type="text" id="inv_code" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
        </tr> 
	</table>

	<div id="maingrid"></div>
	<div id="resultPrint" style="display:none">
	   	<table width="100%">
			<thead>
		 
			<tr>
                <th width="200">变更ID</th>	
                <th width="200">物资材料ID</th>	
                <th width="200">物资材料编码</th>	
                <th width="200">物资材料名称</th>	
                <th width="200">别名</th>	
                <th width="200">物资类别变更ID</th>	
                <th width="200">物资类别ID</th>	
                <th width="200">财务类别编码</th>	
                <th width="200">规格型号</th>	
                <th width="200">计量单位</th>	
                <th width="200">物资属性</th>	
                <th width="200">摊销方式</th>	
                <th width="200">计价方法</th>	
                <th width="200">供应商ID</th>	
                <th width="200">生产厂商变更ID</th>	
                <th width="200">生产厂商ID</th>	
                <th width="200">计划价</th>	
                <th width="200">加价率</th>	
                <th width="200">零售价</th>	
                <th width="200">是否批次管理</th>	
                <th width="200">是否保质期管理</th>	
                <th width="200">是否为耐用品</th>	
                <th width="200">呆滞标准</th>	
                <th width="200">是否呆滞积压</th>	
                <th width="200">是否作二级库管理</th>	
                <th width="200">是否自制品</th>	
                <th width="200">是否为加价销售</th>	
                <th width="200">是否条码管理</th>	
                <th width="200">品种条码</th>	
                <th width="200">是否证件管理</th>	
                <th width="200">是否高值</th>	
                <th width="200">是否专购</th>	
                <th width="200">是否收费</th>	
                <th width="200">启用日期</th>	
                <th width="200">停用日期</th>	
                <th width="200">单位重量</th>	
                <th width="200">单位体积</th>	
                <th width="200">品牌</th>	
                <th width="200">代理商</th>	
                <th width="200">材料结构</th>	
                <th width="200">材料用途</th>	
                <th width="200">变更人</th>	
                <th width="200">变更时间</th>	
                <th width="200">变更原因</th>	
                <th width="200">是否停用</th>	
			</tr>
			   	</thead>
			   	<tbody></tbody>
	   	</table>
   	</div>
</body>
</html>
