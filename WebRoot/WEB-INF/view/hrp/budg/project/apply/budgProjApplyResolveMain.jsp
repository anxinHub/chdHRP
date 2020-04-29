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
	var apply_amount = 0;
	var source_id = '${source_id}'; ;
	var apply_amount ='${apply_amount}';
	var detailData = parentFrameUse().grid.getRow('${rowindex}').detail ;
	var selectData = '' ;
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
		loadHotkeys();
		
    });

  //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	  grid.options.parms.push({name:'budg_year',value:'${budg_year}'}); 
    	  grid.options.parms.push({name:'proj_id',value:liger.get("proj_id").getValue().split(",")[0]}); 
    	  grid.options.parms.push({name:'apply_code',value:$("#apply_code").val()}); 
    	  grid.options.parms.push({name:'source_id',value:liger.get("source_id").getValue().split(",")[0]}); 

    	//加载查询条件
    	grid.loadData(grid.where);
    	
     }
    
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
					 { display: '支出项目ID', name: 'payment_item_id', align: 'left',width:100,hide :true},
					 { display: '支出项目变更ID', name: 'payment_item_no', align: 'left',width:100,hide :true},
                     { display: '支出项目编码', name: 'payment_item_code', align: 'left',width:150,
					 	editor: {
							type: 'select',
							valueField : 'payment_item_code',
							textField : 'payment_item_code',
							selectBoxWidth: '60%',
							selectBoxHeight: 240,
							grid: {
								columns: [
									{ display: '支出项目ID', name: 'payment_item_id', align: 'left',hide :true,width:'5'},
									{ display: '支出项目变更ID', name: 'payment_item_no', align: 'left',hide :true,width:'5'},
				                    { display: '支出项目编码', name: 'payment_item_code', align: 'left',width:'15%'},
									{ display: '支出项目名称', name: 'payment_item_name', align: 'left',width:'30%'},
									{ display: '支出项目全称', name: 'item_name_all', align: 'left',width:'30%'},
							 		{ display: '支出项目性质', name: 'payment_item_nature_name', align: 'left',width:'15%',}
								],
								switchPageSizeApplyComboBox: false,
								onSelectRow: function (data) {
									var e = window.event;
									if (e && e.which == 1) {
										f_onSelectRow_detail(data);
									}
								},
								url: 'queryBudgPaymentItem.do?isCheck=false',
								usePager:false,
								hight:'100%',
								width:'60%',
								//pageSize: 100,
								onSuccess: function (data, g) { //加载完成时默认选中
									if (grid.editor.editParm) {
										var editor = grid.editor.editParm.record;
										var item = data.Rows.map(function (v, i) {
											return v.payment_item_code;
										});
										var index = item.indexOf(editor.payment_item_code) == -1 ? 0 : item.indexOf(editor.payment_item_code);
										//加载完执行
										setTimeout(function () {
											g.select(data.Rows[index]);
										}, 80);
									}
								}
							},
							delayLoad: false, keySupport: true, autocomplete: true,// rownumbers : true,
							onSuccess: function (data, grid) {
								this.parent("tr").next(".l-grid-row").find("td:first").focus();
							},
							ontextBoxKeyEnter: function (data) {
								f_onSelectRow_detail(data.rowdata);
							}
						},
						 totalSummary: {
								align: 'left',
								render: function (suminf, column, cell) {
									return '<div>' + '合计' + '</div>';
								}
                  		}
					 },
                     { display: '支出项目名称', name: 'payment_item_name', align: 'left',width:300
					 		},
					 { display: '预算比例(%)(E)', name: 'rate', align: 'center',editor:{type:'float',onChanged : setBudgAmount},width:100,
					 			render : function(rowdata,rowindex,value){
					 				if(value){
					 					return formatNumber(value,2,1)+"%";
					 				}
					 			},
								totalSummary: {
										align: 'left',
										render: function (suminf, column, cell) {
											return '<div>' + formatNumber(suminf.sum == null ? 0 : suminf.sum,2,0)+"%" + '</div>';
										}
			              		}
					 		},
                     { display: '预算金额(元)(E)', name: 'budg_amount', align: 'right',editor:{type:'float', onChanged : setRate},width:120,
					 			render : function(rowdata, rowindex,value) {
									if(value){
										return formatNumber(value,2,1);
									}
					 			},
	                     		totalSummary: {
									align: 'left',
									render: function (suminf, column, cell) {
										return '<div>' + formatNumber(suminf.sum == null ? 0 : suminf.sum,2,0) + '</div>';
									}
	                     		}
					 		}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:false,width: '100%', height: '100%', 
                     checkbox: true,rownumbers:true, enabledEdit :true ,isAddRow:false ,
                     data:detailData,
                     onBeforeEdit: f_onBeforeEdit,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
						{ text: '保存（<u>S</u>）', id:'save', click: save,icon:'save' },
						{ line:true },
						{ text: '添加行（<u>A</u>）', id:'add', click: addRow, icon:'add' },
    	                { line:true },
    	                { text: '删除行（<u>D</u>）', id:'delete', click: deleteRow,icon:'delete' },
		                { line:true },
                     	{ text: '重置（<u>R</u>）', id:'reset', click: reset,icon:'setting' },
                     	{ line:true },
		                { text: '导入（<u>I</u>）', id:'import', click: impPaymentItem,icon:'up' },
		                { line:true },
                     	{ text: '关闭（<u>C</u>）', id:'close', click: this_close,icon:'close' }
				    	]},
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    //编辑前记录  rowindex 、column_name
    var rowindex_id = "";
	var column_name = "";
	function f_onBeforeEdit(e) {
		rowindex_id = e.rowindex;
		column_name = e.column.name;
	}
    
  	//选中回充数据
	function f_onSelectRow_detail(data, rowindex, rowobj) {
		selectData = "";
		selectData = data;
		
		//回充数据 
		if (selectData != "" || selectData != null) {
			if (column_name == "payment_item_code") {
				grid.updateRow(rowindex_id, {
					payment_item_id : data.payment_item_id,
					payment_item_no : data.payment_item_no,
					payment_item_code : data.payment_item_code,
					payment_item_name : data.payment_item_name
				});
			}
		}
		return true;
	}
    
    //编辑预算比例  计算预算金额
    function setBudgAmount(rowdata){
    	
		if(!liger.get("source_id").getValue()){
    		
    		$.ligerDialog.error('资金来源不能为空！');
    		return false ;
    	}
    	if(rowdata.record.rate){
    		if(rowdata.record.rate >= 0 && rowdata.record.rate <=100){
    			 
               	
    			var budg_amount = Number(apply_amount)*Number(rowdata.record.rate)/100;
    			
               	gridManager.updateCell('budg_amount', budg_amount , rowdata.record);
               	
               	gridManager.updateTotalSummary() ;
    			
    		}else{
    			
    			$.ligerDialog.error('预算比例必须0-100之间');
    		}
    		
    	}
    		
    }
    
    //编辑预算金额  计算预算比例
  	function setRate(rowdata){
    	
		if(!liger.get("source_id").getValue()){
    		
    		$.ligerDialog.error('资金来源不能为空,请选择资金来源后再编辑！');
    		return false ;
    	}
    	
  		if(rowdata.record.budg_amount){
  			
  			var rate =  Number(rowdata.record.budg_amount)/Number(apply_amount)*100
  	    	
  	    	gridManager.updateCell('rate',rate,rowdata.record);
  			
  			gridManager.updateTotalSummary() ;
  			
  		}
		
    }
    
    //重置
    function reset(){
    	//刷新页面
    	
    	var data = gridManager.getData();
    	
    	$.each(data,function(i, item){
    		
    		item.rate = "" ;
    		item.budg_amount = "" ;
    	})
    	
    	grid.deleteAllRows();
    	
    	grid.addRows(data);
    }
    
    //保存
    function save(){
    	
    	var data = gridManager.getData();
    	
    	var sourceData = parentFrameUse().grid.getData() ;
    	
    	if(data.length > 0){
    		if(!validateGrid(data)){
        		return false ;
        	}
    		$.each(sourceData,function(i, item){
    			if(item.source_id == source_id ){
    				
    				item.detail = {"Rows":data,"Total":data.length}
    				
    			}
    			
    		})
    		parentFrameUse().grid.deleteAllRows();
            parentFrameUse().grid.addRows(sourceData); 
        	
      		// 组装 预算项目分解表格  信息
      		var itemMap = new HashMap();
      		 
      		var cellName = '' ; 
      		
     	 	$(sourceData).each(function(){
     	 		
   	 			cellName = this.text.split(" ")[0]
     	 		
   	 			if(this.detail){
   	 				
   	 				$.each(this.detail.Rows,function(i, v){
   	 					
   	 					if(!itemMap.get(v.payment_item_id)){
   	 						
   	 						v[cellName] = v.budg_amount ;
   	 						
   	 						itemMap.put(v.payment_item_id,v);
   	 						
   	 					}else{
   	 						
   	 						itemMap.get(v.payment_item_id)[cellName] = v.budg_amount;
	 						
   	 					}
   	 					
   	 				})
   	 			}
   	 			
   	 		})
     	 		
 	 			
     	 	var addData=[];
     	 		
     	 	for(var i in itemMap.keySet()){
     	 			
  	 			addData.push(itemMap.get(itemMap.keySet()[i]));
  	 			
     	 	}
      		
      		parentFrameUse().detailGrid.deleteAllRows();
          	parentFrameUse().detailGrid.addRows(addData);
      	
      		this_close();
			
    	}else{
    		
    		$.ligerDialog.error('无保存数据！');
    	}
    }
    
  //关闭当前页面
	function this_close(){
	  
		frameElement.dialog.close();
	}
    //表格数据校验
    function validateGrid(data) {  
     	var msg="";
  		var rowm = "";
  		var rateSum = 0 ;
  		//判断grid 中的数据是否重复或者为空
  		var targetMap = new HashMap();
  		$.each(data,function(i, v){
  			rowm = "";
  			rateSum += Number(v.rate) ;
  			if (v.payment_item_code == "" || v.payment_item_code == null || v.payment_item_code == 'undefined') {
 				rowm+="[支出项目]、";
 			}
 			if (v.rate == "" || v.rate == null || v.rate == 'undefined') {
 				rowm+="[预算比例]、";
 			}
 			if (v.budg_amount == "" || v.budg_amount == null || v.budg_amount == 'undefined') {
 				rowm+="[预算金额]、";
 			}
 			
 			if(rowm != ""){
 				rowm = "第"+(i+1)+"行" + rowm.substring(0, rowm.length-1) + "不能为空" + "\n\r";
 			}
 			msg += rowm;
 			
 			var key=v.payment_item_code 
 			var value="第"+(i+1)+"行";
 			if(targetMap.get(key)== null || targetMap.get(key) == 'undefined' || targetMap.get(key) == ""){
 				targetMap.put(key ,value);
 			}else{
 				msg += targetMap.get(key)+"与"+value+"数据重复!!" + "\n\r";
 			}
  		});
  		if(rateSum != 100){
				msg += "预算比例总和不是100%";
		}
  		if(msg != ""){
  			$.ligerDialog.warn(msg);  
 			return false;  
  		}else{
  			return true;  
  		} 	
	}
    
    //添加行
    function addRow(){
     	//添加 可编辑行
     	grid.addRowEdited({
     		payment_item_id : '',
			payment_item_no : '',
			payment_item_code : '',
			payment_item_name : '',
			rate:'',
			budg_amount:''
         	
   		});
     }
     
     //删除
 	function deleteRow(){
 		var data = grid.getCheckedRows();
  		if(data.length == 0){
  			$.ligerDialog.error('请选择要删除的行!');
               return;
         }else{
         	 for (var i = 0; i < data.length; i++){
         		 grid.remove(data[i]);
              } 
         }
     }
    //导入
 	 function impPaymentItem(){
 		var para = {
 				url : 'hrp/budg/project/apply/budgPaymentItemImportPage.do?isCheck=false',
 				title : '导入项目预算分解数据',
 				width : 0,
 				height : 0,
 				isShowMin : false,
 				isModal : true,
 				data : {
 					columns : grid.columns,
 					grid : grid
 				}
 			};
 			parent.openDialog(para);
     }	
    function loadDict(){
            //字典下拉框
        //项目 下拉框
        autocompleteAsync("#proj_id","../../queryProjName.do?isCheck=false","id","text",true,true,'',false,'${proj_id},${proj_no}',380);
 		//资金来源
 		
        autocompleteAsync("#source_id","../../queryBudgSource.do?isCheck=false","id","text",true,true,'',false,'${source_id}',160);  
            
        $("#apply_code").ligerTextBox({width:160,disabled:true});
        $("#proj_id").ligerTextBox({width:380,disabled:true,cancelable:true});
        $("#source_id").ligerTextBox({width:160,disabled:true,cancelable:true});
    }  
    //键盘事件
	  function loadHotkeys() {

		hotkeys('C', reset);

		hotkeys('S', save);
		
		hotkeys('A', addRow);

		hotkeys('D', deleteRow);
		
		hotkeys('I', impPaymentItem);
		
	 }
 	  
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" >
		<tr>
		  <td align="right" class="l-table-edit-td"  style="padding-left:20px;">预算申报单号：</td>
          <td align="left" class="l-table-edit-td"><input name="apply_code" type="text" id="apply_code" value="${apply_code}" disabled="disabled" ltype="text" validate="{required:true,maxlength:20}" /></td>
          <td align="left"></td>
          <td align="right" class="l-table-edit-td"  style="padding-left:20px;">项目名称：</td>
          <td align="left" class="l-table-edit-td"><input name="proj_id" type="text" id="proj_id" disabled="disabled" disabled="disabled" ltype="text"  validate="{required:true,maxlength:20}" /></td>
          <td align="left"></td>
          <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资金来源：</td>
          <td align="left" class="l-table-edit-td"><input name="source_id" type="text" id="source_id" disabled="disabled" ltype="text" validate="{required:true,maxlength:20}" /></td>
          <td align="left"></td>
     	</tr> 
	</table>
	<div id="toptoolbar" ></div>

	<div id="maingrid"></div>
	
</body>
</html>
