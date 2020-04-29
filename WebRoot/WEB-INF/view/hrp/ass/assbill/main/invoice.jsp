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
	var data;
	var detailGrid;
    var gridManager = null;
    var userUpdateStr;
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
        $("#ass_in_no").ligerTextBox({width:160});
        $("#in_date_beg").ligerTextBox({width:90});
        $("#in_date_end").ligerTextBox({width:90});
        $("#begin_date_beg").ligerTextBox({width:90});
        $("#end_date_beg").ligerTextBox({width:90});
        $("#ass_in_no").ligerTextBox({width:160});
        $("#invoice_no").ligerTextBox({width:160});
        $("#ven_id").ligerTextBox({width:160});
        $("#store_id").ligerTextBox({width:160});
        query();
    });
    //查询
    function  query(){
    	
    	if (liger.get("ven_id").getValue() == "") {
			$.ligerDialog.error('供应商不能为空');
			return;
		}
    	if (liger.get("store_id").getValue() == "") {
			$.ligerDialog.error('仓库不能为空');
			return;
		}
    	if ($("#create_date").val() == "") {
			$.ligerDialog.error('制单日期不能为空');
			return;
		}
    	
   		 grid.options.parms=[];
   		 grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	  grid.options.parms.push({name:'ass_in_no',value:$("#ass_in_no").val()}); 
    	  grid.options.parms.push({name:'in_date_beg',value:$("#in_date_beg").val()}); 
    	  grid.options.parms.push({name:'in_date_end',value:$("#in_date_end").val()}); 
    	  grid.options.parms.push({name:'ven_id',value:liger.get("ven_id").getValue().split("@")[0]}); 
    	  grid.options.parms.push({name:'store_id',value:liger.get("store_id").getValue().split("@")[0]}); 
    	  grid.options.parms.push({name:'begin_date_beg',value:$("#begin_date_beg").val()}); 
    	  grid.options.parms.push({name:'end_date_beg',value:$("#end_date_beg").val()}); 
    	  grid.options.parms.push({name:'invoice_no',value:$("#invoice_no").val() }); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
            columns: [ 
                      { display: '入库单号', name: 'ass_in_no', align: 'left',width:130 },
                      { display : '摘要',name : 'note',align : 'left',width : 150,frozen: true},
                      { display : '仓库',name : 'store_name',align : 'left',width : 140, frozen: true},
                      { display : '供应商',name : 'ven_name',align : 'left',width : 260}, 
                      { display : '入库金额',name : 'in_money',align : 'right',width : 80,
							render: function(item)
				            {
				                    return formatNumber(item.in_money,'${ass_05005}',1);
				            }
					  },
                      { display: '发票号',name: 'invoice_no', align: 'left',width : 150},
					  { display : '采购员',name : 'purc_emp_name',align : 'left',width : 110}, 
					  { display : '制单人',name : 'create_emp_name',align : 'left',width : 100}, 
					  { display : '制单日期',name : 'create_date',align : 'left',width : 100},
					  { display : '确认人',name : 'confirm_emp_name',align : 'left',width : 100}, 
					  { display : '入库确认日期',name : 'in_date',align : 'left',width : 100}, 
					  { display : '状态',name : 'state_name',align : 'left',width : 100},
					  { display : '仓库',name : 'store_name',align : 'left',width : 260}
                      ],
                      dataAction: 'server',dataType: 'server',usePager:true,width:'100%',height:'100%',
                      url:'queryInMainBill.do?isCheck=false', 
                      checkbox: true,rownumbers:true,enabledEdit:true, frozen : false ,
                      delayLoad: true,//初始化不加载，默认false
                      selectRowButtonOnly:true,heightDiff: 0,
                      /* onCheckRow: function (checked) {
                    	  if (checked) {
                    		  console.log
                    		  detailGrid.select();
                    	  }
                      }, */
                      showTitle: true,detail: { onShowDetail: showDetail , height:'auto',reload: true ,single:true },//入库单明细
                      toolbar: { items: [
     					{ text: '查询', id:'query', click: query, icon:'search' },
     	                { line:true },
     	                { text: '保存', id:'save', click: save,icon:'add' },
						{ line:true },
						{ text : '关闭', id : 'close', click : this_close, icon : 'candle' } 
     				]},
                  });

         gridManager = $("#maingrid").ligerGetGridManager();
     }
  
    
    //明细
    function showDetail(row, detailPanel,callback)
    {
   	 	data= row;
        var detailGridHtml = document.createElement('div'); 
        $(detailPanel).append(detailGridHtml);
        detailGrid =$(detailPanel).css('margin',10).ligerGrid({
            columns:
                   [
                   { display: '卡片编码', name: 'ass_card_no',width:100},
                   { display: '资产名称', name: 'ass_name',width:190},
                   { display: '规格', name: 'ass_spec',width:200, align:'left' },
                   { display: '型号', name: 'ass_mondl',width:90 },
                   { display: '品牌', name: 'ass_brand' ,width:100},
                   { display: '生成厂商', name: 'fac_name',width:98 , align: 'right'},
                   { display: '数量', name: 'ass_amount',width:98 , align: 'right'},
                   { display: '单价', name: 'price', align: 'right',width:100 },
                   { display: '状态', name: 'state_name', align: 'right',width:100 },
                   { display: '发票号', name: 'invoice_no', align: 'right',width:100 }
                   ], 
                   dataAction: 'server',
                   dataType: 'server',
                   usePager: false,
                   checkbox: true,
                   selectRowButtonOnly: true,
                   delayLoad: false, //初始化不加载，默认false
                   width: '99%',
                   height: 'auto',
                   isScoll: true,
                   allowAdjustColWidth: false,
                   frozen: false,
                   isAddRow: false,
                   data: row.detail,
                   enabledEdit: true,
                   fixedCellHeight: true,
                   checkBoxDisplay: function (rowdata) {
                	   if (rowdata.use_state === 7) {
                		   return false;
                	   } 
                   }
        });
    }
    
    function getPayMoney(e) {}
    //保存
    function save(){
    	if (liger.get("ven_id").getValue() == "") {
			$.ligerDialog.error('供应商不能为空');
			return;
		}
    	if (liger.get("store_id").getValue() == "") {
			$.ligerDialog.error('仓库不能为空');
			return;
		}
    	if ($("#create_date").val() == "") {
			$.ligerDialog.error('制单日期不能为空');
			return;
		}
    	var gridData = grid.getSelectedRows();
    	
    	var detailData = []
    	
    	if (detailGrid) {
    		detailData = detailGrid.getSelectedRows();
    	}
		if (gridData.length === 0 && detailData.length === 0) {
			$.ligerDialog.error('请选择行');
			return;
		}
		
		var dataParam = [];
		var invoice_nos = [];
		gridData.forEach(function (item) {
			dataParam = dataParam.concat(item.detail.Rows)
		})
		dataParam = dataParam.concat(detailData)

		for (var i = 0; i < dataParam.length; i++) {
			var item = dataParam[i];
			
			for (var j = i + 1; j < dataParam.length; j++) {
				var nextItem = dataParam[j];
				
				if (item.ass_card_no === nextItem.ass_card_no) {
					dataParam.splice(i, 1)
					i--
				}
			}
		}
		
		var params = [];
		$.each(dataParam,function(index,item){
			if(item.use_state !== 7 && item.use_state !== 6 ){
				params.push(item.ass_card_no);
				
			}
			//console.log(item);
			invoice_nos.push(item.invoice_no);
		}); 
		var falg = true;
		var nary = invoice_nos.sort();
		for(var i = 0; i < invoice_nos.length; i++){
			if(nary[i+1] == null){
				break;
			}
			if(invoice_nos[i] != nary[i+1]){
				falg = false;
				break;
			}
		}
		
		
		if(!falg){
			$.ligerDialog.warn('本次所生成发票单,发票号必须一致');
			return;
		}
		
		ajaxJsonObjectByUrl("importInAndMap.do?isCheck=false",
				{
				 'ven_id':liger.get("ven_id").getValue().split("@")[0],
				 'ven_no':liger.get("ven_id").getValue().split("@")[1],
				 'store_id':liger.get("store_id").getValue().split("@")[0],
				 'store_no':liger.get("store_id").getValue().split("@")[1],
				 'store_code':liger.get("store_id").getText().split(" ")[0],
				 'create_date':$("#create_date").val(),
			 	 'invoice_num':invoice_nos[0],
				 'params':params.toString()
				 },
				 function(data){
					parent.parentFrameUse().query();
					parent.parentFrameUse().openUpdate(data.update_para);
					parent.this_close();
					this_close();
		},"json"); 
		
    }
    	
    //关闭按钮
    function this_close() {
		frameElement.dialog.close();
	}
    
	
   //下拉框
    function loadDict(){
    	
    	
    	var param = {query_key:''};
    	
    	autocomplete("#ven_id", "../../queryHosSupDictNo.do?isCheck=false","id", "text",true,true,param,true,null,"350",null,null,null,function(){
    		
    		query();  
    	});
    	
    	autocomplete("#store_id", "../../queryHosStoreDict.do?isCheck=false","id", "text",true,true,param,true,null,"300");
    	
    	autodate("#in_date_beg","YYYY-mm-dd","month_first");

		autodate("#in_date_end","YYYY-mm-dd","month_last");
		
		
		autodate("#begin_date_beg","YYYY-mm-dd","month_first");

		autodate("#end_date_beg","YYYY-mm-dd","month_last");
		$("#create_date").ligerTextBox({
			width : 160
		});
    }  
 	  
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">入库日期：</td>
            <td align="left" class="l-table-edit-td" width="5%"><input name="in_date_beg" type="text" id="in_date_beg"  class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
            <td align="left" width="2%">至：</td>
            <td align="left" class="l-table-edit-td"><input name="in_date_end" type="text" id="in_date_end"  class="Wdate"
				onFocus="WdatePicker({minDate:'#F{$dp.$D(\'in_date_beg\');}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">入库单号：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_in_no" type="text" id="ass_in_no"   /></td>
            <td align="left"></td>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>供应商：</td>
            <td align="left" class="l-table-edit-td"><input name="ven_id" type="text" id="ven_id" /></td>
            <td align="left"></td>
            
            <td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>制单日期：</td>
			<td align="left" class="l-table-edit-td"><input
				name="create_date" type="text" id="create_date" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="left"></td>
            
        </tr> 
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">制单日期：</td>
            <td align="left" class="l-table-edit-td"><input name="begin_date_beg" type="text" id="begin_date_beg"  class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"  /></td>
            <td align="left">至：</td>
            <td align="left" class="l-table-edit-td"><input name="end_date_beg" type="text" id="end_date_beg"  class="Wdate"
				onFocus="WdatePicker({minDate:'#F{$dp.$D(\'change_date_beg\');}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">发票号：</td>
            <td align="left" class="l-table-edit-td"><input name="invoice_no" type="text" id="invoice_no" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>仓库：</td>
            <td align="left" class="l-table-edit-td"><input name="store_id" type="text" id="store_id"   /></td>
            <td align="left"></td>
        </tr> 
    </table>

	<div id="maingrid"></div>
	
</body>
</html>
