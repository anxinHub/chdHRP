<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="${path}/resource.jsp">
	<jsp:param value="tree,grid,select,dialog,validate,tab,datepicker,upload,checkbox" name="plugins" />
</jsp:include>
<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script type="text/javascript">
var parentFrameName = parent.$.etDialog.parentFrameName;
var parentWindow = parent.window[parentFrameName]
var etTab,grid,subGrid,sourceGrid,sup_no,is_money_c,pact_code,change_date,pay_date;
var attr_code ;// 合同属性    用于 关联卡片查询
var sourceGridData ; // 加载资金来源表格数据用 
var pact_11002 = '${pact_11002}';
var value_c_code = '';
	$(function(){
		 $("#layout1").ligerLayout({ 
			 rightWidth: 400,
			 isRightCollapse: true ,
			 height: 120,    // 下半部整体高度 20200420 CXD
			 heightDiff:150,
			 onRightToggle : function() {
				 subGrid._onResize();			 	 
				 sourceGrid._onResize();
				 },
			 //每调整左边树宽度大小即刷新一次表格，以免结构混乱
			 onEndResize : function(a, b) {subGrid._onResize();}
		});
		initfrom();
		initSelect();
		//setDivShow()
		$("#save").on("click", function () {
			save();
		})
		$("#cancle").on("click", function () {
			var curIndex = parent.$.etDialog.getFrameIndex(window.name);
	       parent.$.etDialog.close(curIndex); 
		})
		
	})
	
    var initSelect=  function(){
        sup_no = $("#sup_no").etSelect({
        			defaultValue: 'none',
					url: '../../basicset/select/queryHosSupSelectDict.do?isCheck=false',
					onChange:function(value){
						reloadPactFKHT(value);
						pact_code.setValue("");
					}
				});
        pay_type = $("#pay_type").etSelect({
      		defaultValue: "none",
      		options: [{ "id": "1", "text": "全款",label:"全款"}, 
      			       { "id": "2", "text": "预付款",label:"预付款"},
      			       { "id": "3", "text": "期款",label:"期款"},
      			       { "id": "4", "text": "尾款",label:"尾款"}
      			     ]
		  });
        pact_code = $("#pact_code").etSelect({
        	defaultValue: 'none',
			url: '../../basicset/select/queryPactFKHTSelectPerm.do?isCheck=false',
			onChange:function(value){
				// 选择合同名称 判断 金额变动勾选  如果勾选 则加载 合同明细数据
				if(is_money_c.status == 'checked'){
					subGrid._setUrl("queryPactMainChangeFKHTAfterDet.do?isCheck=false&pact_code="+value)
				}
				
			}
        })
        function reloadPactFKHT(value) {
        	pact_code.reload({
                url: "../../basicset/select/queryPactFKHTSelectPerm.do?isCheck=false",
                para: {
                    sup_id: value.split("@")[0]
                }
            });
        }
      }
    
	 var initSubGrid = function () {
		 grid = $("#subGrid").ligerGrid({
			 columns :[	{ display: '标的物编码', name: 'subject_code',width: '8%',editable: false},
						{ display: '标的物名称', name: 'subject_name',width: '12%',editable: false},
						{ display: '变动前数量', name: 'before_amount', width: '8%',align: "right",editable: false},
						{ display: '变动前单价', name: 'before_price', width: '10%',align: "right",editable: false},
						{ display: '变动前金额', name: 'before_money',width: '10%',align: "right",editable: false },
						{ display: '变动数量', name: 'change_amount', width: '8%',align: "right", type: 'int',editor: {type: 'int'},
							render : function(rowdata, rowindex,value){
								if(!value){
									rowdata.change_amount=0
									return '0';
								}else{
									return value ;
								}
							}
						},
						{ display: '变动单价', name: 'change_price', width: '10%',align: "right",editable: false},
						{ display: '变动金额', name: 'change_money',width: '10%',align: "right",editor: {type: 'float'}},  
						{ display: '变动后数量', name: 'amount', width: '8%',align: "right",editable: false},
						{ display: '变动后单价', name: 'price', width: '10%',align: "right",editable: false},
						{ display: '变动后金额', name: 'money',width: '10%',align: "right",editable: false }, 
						{ display: '关联卡片', name: 'card_amount',width: '10%'}
         				],
             height: '240', width:'100%', checkbox: true, usePager: false,
             enabledEdit : true,
             selectRowButtonOnly:true,
             isAddRow:false,
             onAfterEdit : editorEnd,
             onSelectRow: loadSourceGrid//选择行时，加载 资金来源表数据
         });
		 subGrid = $("#subGrid").ligerGetGridManager();
     };
     // 明细 新增资金来源
     function loadHeadItem() {
 		gridItem = $("#sourceGrid").ligerGrid({
 			columns : [{ display : '资金来源',	name : 'source_id',textField : 'source_name',width : 100,
	 				editor : {
	 					type : 'select',
	 					url : '../../basicset/select/queryHosSourceDictSelect.do?isCheck=false',
	 					keySupport : true,
	 					autocomplete : true,
	 				},
	 				render:function(rowdata,rowindex,value){
	 					if (rowdata.detail_id) {//数据库已存在数据 不允许修改 资金来源，只能修改 金额
							rowdata.notEidtColNames.push("source_id");
						}
	 					return rowdata.source_name;
	 				}
	 			},
	 			{	display : '变动前金额',	name : 'money',align : 'right',width : 100,	
	 				render : function(rowdata, rowindex,value) {
	 					 return formatNumber(value == null ? 0:value,2,1);
	 				},
	 			},
	 			{	display : '变动金额',	name : 'money_c',align : 'right',width : 100,	editor : {type : 'float'},
	 				render : function(rowdata, rowindex,value) {
	 					 return formatNumber(value == null ? 0:value,2,1);
	 				},
	 			},
	 			{	display : '变动后金额',	name : 'money_a',align : 'right',width : 100,
	 				render : function(rowdata, rowindex,value) {
	 					 return formatNumber(value == null ? 0:value,2,1);
	 				},
	 			}
	 		],
 			usePager : false,
 			width : '100%',	height : '240',checkbox : true,enabledEdit : true,
 			rownumbers : true,isAddRow:false,
 			data:sourceGridData,
 			selectRowButtonOnly : true,//heightDiff: -10,
 			onAfterEdit : editorSourceEnd,
 			toolbar : {
 				items : [ {text : '添加行',	id : 'saveItem',click : addSourceData,icon : 'save'}, 
 				          {line : true},
 				          {text : '确定',	id : 'saveItem',click : saveSourceData,icon : 'save'}, 
 				          {line : true},
 				          {text : '删除',	id : 'deleteItem',click : removeSourceData,icon : 'delete'}
 				         ]
 			}
 		});

 		sourceGrid = $("#sourceGrid").ligerGetGridManager();
 	}
   //选中明细数据  加载 资金来源数据 
    function loadSourceGrid(rowData,rowindex,rowobj){
    	 if(rowData.sourceData){
    		 sourceGridData = rowData.sourceData ;
    	 }else{
    		 sourceGridData  = {"Rows":[],"Total":0} ;
    	 }
    	loadHeadItem();
    	 
     } 
     //资金来源表格 添加行
     function addSourceData(){
    	 var data = subGrid.getCheckedRows();
         if (data.length != 1) {
             $.etDialog.error('请选择一行标的物明细');
         }else{
        	 sourceGrid.addRow();
         }
    	 
     }
  // 用于动态生成标的物中的单价变动
     function editorSourceEnd(e){
    	 if(e.column.name == 'money_c' ){
    		var data = e.record;
    		if(!data.money_c){
    			e.record.money_a = parseFloat(data.money);
    			e.record.money_c = 0.00;
    		}else{
    			var change_money = parseFloat(data.money_c);//变动金额
    			var money = 0.00;//变动后金额
    			
    			e.record.money_c = change_money;
    			e.record.money_a =change_money+parseFloat(data.money);
    		}
    		
    		sourceGrid.updateCell('money_c', e.record.money_c, e.record);
    		sourceGrid.updateCell('money_a', e.record.money_a, e.record);

    	 }
     }
     //保存资金来源数据 到明细数据中
     function saveSourceData(){
    	 var detailMoney = 0 ;//明细数据金额
    	 var sourceMoney = 0 ;//资金来源总金额
    	 var errStr = '';//表格数据校验错误信息
    	 var data = subGrid.getCheckedRows();
    	 if (data.length != 1) {
             $.etDialog.error('请选择一行标的物明细');
         }else{
        	 var sourceData = sourceGrid.getData();
        	 $(data).each(function () {
        		 detailMoney = this.money;
        		 $.each(sourceData,function(index,content){
        			 if(!content.source_id){errStr = "资金来源不能为空";return ;}
	                 if(!content.money_c){errStr = "变动金额不能为空";return ;}
	                 if(content.money_a<0){errStr = "变动后金额不能小于0";return ;}
	                 var money = parseFloat(content.money_a)
	                 if (!money) {money = 0;}
	                 content.money_a = money;
        			 sourceMoney += money;
        		 })
        		 if(errStr != ''){
        			 if(errStr.length != 0){$.etDialog.error(errStr) ;return ;}
        		 }else{
        			 if(detailMoney==sourceMoney){
            			 subGrid.updateRow(this,{sourceData:{"Rows":sourceData,"Total":sourceData.length}});
            			 $.etDialog.success('操作成功！');
            		 }else{
            			 $.etDialog.error('标的物明细数据金额与资金来源变动后总金额不一致,请核对！');
            		 }
        		 }
        	 })
        	 
         }
        
     }
	 //资金来源表格 删除行
     function removeSourceData(){
    	 var data = subGrid.getCheckedRows();
         if (data.length != 1) {
             $.etDialog.error('请选择一行标的物明细');
         } else {
        	 var dataSource = sourceGrid.getCheckedRows();
        	 if (data.length == 0) {
                 $.etDialog.error('请选择要删除的资金来源明细数据');
        	 }else{
        		 sourceGrid.deleteSelectedRow2();
        	 }
             
         }
     }
  // 用于动态生成标的物中的单价变动
     function editorEnd(e){
    	 if(e.column.name == 'change_amount' || e.column.name == 'change_money'){
    		var data = e.record;
    		var change_amount = 0;//变动数量
			var change_price =0.00;//变动单价
    		if(!data.change_money){
    			 e.record.change_price = 0;
    			 if(!data.change_amount){
    				 e.record.change_amount=0;
    			 }else{
    				 change_amount =data.change_amount;
    			 }
    		}else{
    			
    			var change_money = parseFloat(data.change_money);//变动金额
    			var amount = 0;//变动后数量
    			var price =0.00;//变动后单价
    			var money = 0.00;//变动后金额
    			if(data.change_amount){
    				change_amount = data.change_amount
    			}
    			money=change_money+data.before_money;
    			amount=change_amount+data.before_amount;
    			price=money/amount;
    			change_price =price- data.before_price;
    			e.record.change_amount = change_amount;
    			e.record.change_price = change_price.toFixed(2);// 四舍五入 保留两位小数 20200422  CXD
    			e.record.amount =amount
    			e.record.money =money;
    			e.record.price =price.toFixed(2);// 四舍五入 保留两位小数 20200422  CXD
    		}
    		subGrid.updateRow(e.record,{
    			change_amonut:e.record.change_amonut,
        		change_price:e.record.change_price,
        		amount:e.record.amount,
        		price:e.record.price,
        		money:e.record.money
    		});
    		
    		addMoney();
    	 }
     }
  // 计算  金额
     function addMoney(){
 		var money = 0;
  	 	var data = subGrid.getData();
		 if (data != null && data.length != 0) {
           $(data).each(function () {
          	 var rowdata = this;
          	 if (rowdata && rowdata.change_money) {
	                 money  += parseFloat(rowdata.change_money);
				}
           });
      	}
  		$("#plan_money").val(money);
	}
    function removeSub(){
    	 var data = subGrid.getCheckedRows();
         if (data.length == 0) {
             $.etDialog.error('请选择行');
         } else {
             subGrid.deleteSelectedRow2();
             // 清除资金来源表数据
             sourceGridData  = {"Rows":[],"Total":0} ;
             loadHeadItem();
             
         }
     }
    //保存
    function save() {
		var formValidate;
		if(is_money_c.status == 'check'){
			formValidate = $.etValidate({
				items : [ 
					{el : $("#sup_no"),required : true},
					{el : $("#pact_code"),required : true},
					{el : $("#change_date"),required : true},
					{el : $("#change_reason"),required : true}
					]
			});
		}else{
			formValidate = $.etValidate({
				items : [ 
					{el : $("#sup_no"),required : true},
					{el : $("#pact_code"),required : true},
					{el : $("#change_date"),required : true},
					{el : $("#change_reason"),required : true},
					
					{el : $("#pay_id"),required : true},
					{el : $("#pay_type"),required : true},
					{el : $("#pay_date"),required : true},
					{el : $("#plan_money"),required : true}
					]
			});
		}
		
		if(!formValidate.test()){return;};

		if(subGrid){
			debugger;
		    var err = "";
			 var sub = [];
			 var data = [];
			 data = subGrid.getData();
	         if (data.length > 0){
	        	 $(data).each(function(){
	        		 var rowdata = this;
	                  //if(rowdata.change_amount == "" || rowdata.change_amount == "undefined"){err = "变动数量不能为空";return ;}
	                  if(!rowdata.change_money){err = "变动金额不能为空";return ;}
	                  if(rowdata.money<0){err = "变动后金额不能小于0";return ;}
	                  if(rowdata.amount<0){err = "变动后数量不能小于0";return ;}
	                  sub.push(this);
	              })
	              if(err.length != 0){$.etDialog.error(err) ;return ;}
	         }
	 		$.each(sub,function(index,item){
				var sum_c=0
				var sum_a=0
				var Rows=item.sourceData.Rows
				for (i in Rows)
				{
					Rows[i].money_a=Rows[i].money
					if(!Rows[i].money_c) Rows[i].money_c=0;
					else sum_c=sum_a+Rows[i].money_c;
				}
	 			if(item.change_money!=sum_c)
	 			{
	 				item.sourceData.Rows[0].money_c=item.change_money;
	 				item.sourceData.Rows[0].money_a=item.sourceData.Rows[0].money+item.change_money;
	 			}
			});
		}
		
		ajaxPostData({
			url : 'addPactChangeFKHTAfter.do?isCheck=false',
			data : {
				sup_no : sup_no.getValue().split("@")[1],
				sup_id : sup_no.getValue().split("@")[0],
				pact_code : pact_code.getValue(),
				change_date : $("#change_date").val(),
				change_reason : $("#change_reason").val(),
				is_money_c : is_money_c.checked? 1 : 0,
				pay_id : $("#pay_id").val(),
				pay_type : pay_type.getValue(),
				summary : $("#summary").val(),
				
				pay_date : $("#pay_date").val(),
				plan_money : $("#plan_money").val(),
				pay_cond : $("#pay_cond").val(),
				sub : JSON.stringify(sub),
				is_exe : '1' ,
				value_c_code : value_c_code
			},
			success : function(data) {
				if(data.state = 'true'){
					parentWindow.query(); 
					var curIndex = parent.$.etDialog.getFrameIndex(window.name);
			        parent.$.etDialog.close(curIndex);
				}else{
					$.etDialog.error(data.error)
				}
			     
			},
			delayCallback : true
		})
	}

	  //日期
	var initfrom = function(){
		change_date = $("#change_date").etDatepicker({
			defaultDate: true, 
			view: "days" ,
			todayButton: false
		});
		pay_date = $("#pay_date").etDatepicker({
			defaultDate: true,
			todayButton: false
		});
		is_money_c = $('#is_money_c').etCheck({
			onChange: setDivShow
		});
	};
	
	function setDivShow(){
		if(is_money_c.checked){
			$("#planMod").show();
			$("#layout1").show();
			initSubGrid();
			loadHeadItem();
			// 勾选金额 变动  如果 合同名称 已选择则加载 合同明细数据
			if(pact_code.getValue()){
				subGrid._setUrl("queryPactMainChangeFKHTAfterDet.do?isCheck=false&pact_code="+pact_code.getValue());
				initSubGrid();
			}
			
		}else{
			$("#planMod").hide();
			$("#layout1").hide();
		}
	}
	
</script>
</head>

<body style="overflow: scroll; ">
	<table class="table-layout">
		<tr>
			<td class="label" style="width: 100px;">变更号编号：</td>
			<td class="ipt"><input id="change_code" type="text" disabled="disabled" style="background-color: #EAEAEA" placeholder="系统生成"/></td>
			<td class="label no-empty" style="width: 100px;">供应商：</td>
			<td class="ipt"><select id="sup_no" style="width: 180px;"></select></td>
			<td class="label no-empty" style="width: 100px;">合同名称：</td>
			<td class="ipt" ><input id="pact_code" type="text" style="width: 180px;" /></td>
			<td class="label no-empty" style="width: 100px;">变更日期：</td>
			<td class="ipt"><input id="change_date" type="text"/></td>
		</tr>
		<tr>
			<td class="label no-empty" style="width: 100px;">变更原因：</td>
			<td class="ipt" colspan="7"><textarea id="change_reason" style="resize:none;width: 95.5%;"></textarea></td>
		</tr>
		<tr>
			<td class="label"><input id="is_money_c" type="checkbox" />金额变动</td>
		</tr>
	</table>
	<div id ="planMod" style="height: 130px;display: none">
		<table  class="table-layout">
			<tr>
				<td class="label no-empty" style="width: 100px;">付款期号：</td>
				<td class="ipt"><input id="pay_id" type="text" style="width: 180px;"/></td>
				<td class="label no-empty" style="width: 100px;">付款类型：</td>
				<td class="ipt"><select id="pay_type" style="width: 180px;"></select></td>
				<td class="label" style="width: 120px;">付款摘要：</td>
				<td class="ipt"><input id="summary" type="text" /></td>
			</tr>
			<tr>
				<td class="label" style="width: 100px;">付款期限：</td>
				<td class="ipt"><input id="pay_date" type="text" /></td>
				<td class="label no-empty" style="width: 100px;">金额：</td>
				<td class="ipt"><input id="plan_money" type="text" disabled="disabled"/></td>
			</tr>
			<tr>
				<td class="label no-empty" style="width: 100px;">付款条件：</td>
				<td class="ipt" colspan="7"><textarea id="pay_cond" style="resize:none;width: 95.5%;"></textarea></td>
			</tr>
		
		</table>
	</div>
	<div id="layout1" style=" width:100%; display: none">
	 	<div id="subGrid" position="center" title='明细'></div>
	 	<div id="sourceGrid" position="right" title='资金来源'></div>
	</div>
	<div class="button-group">
	  <button id="save">保存</button>
	  <button id="cancle">取消</button>
	</div>
	
</body>

</html>

