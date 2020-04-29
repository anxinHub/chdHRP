<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html style="overflow:hidden;">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/static_resource.jsp">
	<jsp:param value="select,datepicker,grid,dialog" name="plugins" />
</jsp:include>
<script type="text/javascript">
	var budg_year;
	var grid;
	$(function () {
		loadHead(null);//加载数据
		loadHotkeys();
		init();
	});
	
	var year_input, month_input, /* dept_name_select, */ material_type_select;

	function init() {
		getData("../../../queryBudgYear.do?isCheck=false", function (data) {
			year_input = $("#year_input").etDatepicker({
				defaultDate: data[0].text,
				view: "years",
				minView: "years",
				dateFormat: "yyyy",
				minDate: data[data.length - 1].text,
				maxDate: data[0].text,
				todayButton: false,
				onSelect:function(value){
					materReload(value);
					setTimeout(function(){
						query()
					},10)
				}
			});
		});

		month_input = $("#month_input").etDatepicker({
			view: "months",
			minView: "months",
			dateFormat: "mm",
			showNav: false,
			todayButton: false,
			onSelect: query
		});

		/* dept_name_select = $("#dept_name_select").etSelect({
			url: "../../../queryDept.do?isCheck=false",
			defaultValue: "none",
			onChange: query
		}); */

		material_type_select = $("#material_type_select").etSelect({
			defaultValue: "none",
			onChange: query
		});
		
		function materReload(budg_year){
			material_type_select.reload({
				url:"queryBudgMedTypeSubj.do?isCheck=false",
				para:{
					budg_year:budg_year
				}
			})
		}
	}

	function getData(url, callback) {
		$.ajax({
			url: url,
			dataType: 'JSON',
			type: "POST",
			success: function (res) {
				if (typeof callback === "function") {
					callback(res);
				}
			}
		})
	}
	
	//查询
	function query() {
		var parms = [
			{ name: 'year', value: year_input.getValue() },
			{ name: 'month', value: month_input.getValue() },
			{ name: 'med_type_id', value: material_type_select.getValue().split(",")[0] }
		];
		//加载查询条件
		grid.loadData(parms,'queryBudgMedPurExe.do?isCheck=false');
	}

	function loadHead() {
		var flag;
		grid = $("#maingrid").etGrid({
			columns: [
				{ display: '预算年度', name: 'year', align: 'left',width:"10%",editable:setEdit ,
                    	 editor : {
								type : 'select',
								keyField : 'year',
								url : '../../../queryBudgYear.do?isCheck=false',
								keySupport : true,
								autocomplete : true,
                    	 }
					}, 
				{ display: '月份', name: 'month', align: 'left',width:"7%",editable:setEdit ,
                    	 editor : {
								type : 'select',
								keyField : 'id',
								textField : 'label',
								source:[ 
									{id : '01',label:'1月'},{id : '02',label:'2月'},{id : '03',label:'3月'},{id : '04',label:'4月'},
									{id : '05',label:'5月'},{id : '06',label:'6月'},{id : '07',label:'7月'},{id : '08',label:'8月'},
									{id : '09',label:'9月'},{id : '10',label:'10月'},{id : '11',label:'11月'},{id : '12',label:'12月'}
								],
								keySupport : true,
								autocomplete : true,
                    	 }
					}, 
				{ display: '药品类别(E)', name: 'med_type_name', align: 'left',width:"25%",editable:setEdit ,
			 			editor : {
							type : 'select',
							keyField : 'med_type_id',
							url:"queryBudgMedTypeSubj.do?isCheck=false&budg_year="+budg_year,
							keySupport : true,
							autocomplete : true,
							create:function(rowdata,celldata,setting){
	                	    	 if(rowdata.year){
	                	    		 setting.url = 'queryBudgMedTypeSubj.do?isCheck=false&budg_year='+rowdata.year;
	                	    	 }else{
	                	    		 $.etDialog.error('请先填写年度');
	                	    		 return false ;
	                	    	 }
	                	    }
						}
					},
				{ display: '采购金额(E)',name: 'pur_amount',align: 'right',dataType:"float",width:"25%",
						render:function(ui){
							return formatNumber(ui.rowData.pur_amount, 2, 1);
						}
					}, 
				{ display: '说明(E)',name: 'remark',align: 'left',dataType:"string",width:"30%"}
			],
			dataModel:{
	         	method:'POST',
	         	location:'remote',
	         	url:'',
	         	recIndx: 'year'
            }, 
            usePager:false,width: '100%', height: '100%',checkbox: true,editable: true,
            addRowByKey:true,
   	        toolbar: {
             	items: [
		           	{ type: "button", label: '查询',icon:'search',listeners: [{ click: query}] },
					{ type: "button", label: '添加行',icon:'plus',listeners: [{ click: addRow}] },
					{ type: "button", label: '保存',icon:'disk',listeners: [{ click: save}] },
					{ type: "button", label: '删除',icon:'minus',listeners: [{ click: remove}] },
					/* { type: "button",label:'下载导入模板', icon:'arrowthickstop-1-s',listeners: [{ click: downTemplate}]},
					{ type: "button", label: '导入',icon:'arrowthick-1-n',listeners: [{ click: imp}] }, */
           		]
 	        }
		});
	}
	
	// 根据 group_id 是否存在 返回 true 或 false  控制单元格可否编辑 用 
    function setEdit(ui){
   		 if(ui.rowData && ui.rowData.group_id){
   			 return false ;
   		 }else{
   			 return true ;
   		 }
    }
	
	function addRow(){
		grid.addRow();
	}
	
	function  save(){
		var data = grid.getChanges();
    	
    	var ParamVo =[];
    	
    	if(data.addList.length > 0 || data.updateList.length > 0){
    		
        	if(data.addList.length > 0){
        		
        		var addData = data.addList ;
        		
        		if(!validateGrid(addData)){
        			return  false ;
        		}
                $(addData).each(function (){	
                	ParamVo.push(
    					this.year   +"@"+ 
    					this.month   +"@"+ 
    					this.med_type_id +"@"+ 
    					(this.pur_amount ?this.pur_amount :"")   +"@"+ 
    					(this.remark?this.remark:"") +"@"+
    					this._rowIndx +"@"+ 
    					'1' //添加数据标识
    				) 
    			});
        	}
    		if(data.updateList.length > 0){
        		
        		var updateData = data.updateList ;
                $(updateData).each(function (){	
                	ParamVo.push(
    					this.year   +"@"+ 
    					this.month   +"@"+ 
    					this.med_type_id  +"@"+
    					(this.pur_amount ?this.pur_amount :"")   +"@"+
    					(this.remark?this.remark:"") +"@"+
    					this._rowIndx +"@"+ 
    					'2' //修改数据标识
    				) 
    			});
        	}
	  	    ajaxPostData({url: 'saveBudgMedPurExe.do?isCheck=false',
			    data: {ParamVo : ParamVo.toString()},
			    success: function (responseData) {
			    	query();
			    }
			})
	    }else{
	    	$.etDialog.warn('没有需要保存的数据!');
	    }
    }
  
    function validateGrid(data) {  
    	var msg="";
 		var rowm = "";
 		//判断grid 中的数据是否重复或者为空
 		var targetMap = new HashMap();
 		
 		$.each(data,function(i, v){
 			rowm = "";
			if (v.year == "" || v.year == null || v.year == 'undefined') {
				rowm+="[预算年度]、";
			}  
			if (v.month == "" || v.month == null || v.month == 'undefined') {
				rowm+="[月份]、";
			}  
			if (v.med_type_id == "" || v.med_type_id == null || v.med_type_id == 'undefined') {
				rowm+="[药品类别]、";
			}
			if (v.pur_amount == "" || v.pur_amount == null || v.pur_amount == 'undefined') {
				rowm+="[采购金额]、";
			}  
			if(rowm != ""){
				rowm = "第"+(Number(v._rowIndx)+1)+"行" + rowm.substring(0, rowm.length-1) + "不能为空" + "\n\r";
			}
			msg += rowm;
			var key= v.year + v.month + v.med_type_id
			var value="第"+(Number(v._rowIndx)+1)+"行";
			if(targetMap.get(key)== null || targetMap.get(key) == 'undefined' || targetMap.get(key) == ""){
				targetMap.put(key ,value);
			}else{
				msg += targetMap.get(key)+"与"+value+"数据重复!!" + "\n\r";
			}
 		});
 		if(msg != ""){
 			$.etDialog.warn(msg);  
			return false;  
 		}else{
 			return true;  
 		} 	
 	}
	
	function remove() {
		var data = grid.selectGet();
		if (data.length == 0) {
			$.etDialog.error('请选择行');
		} else {
			var ParamVo = [];
			var delData = [] ;//接受页面端删除数据
			$(data).each(function (){	
            	if (this.rowData.group_id) {
					ParamVo.push(
						this.rowData.group_id   +"@"+ 
						this.rowData.hos_id   +"@"+ 
						this.rowData.copy_code   +"@"+ 
						this.rowData.year   +"@"+ 
						this.rowData.month   +"@"+ 
						this.rowData.med_type_id 
					)
				}else{
	              	delData.push(data)
	            }
			});
			$.etDialog.confirm('确定删除?', function (yes) {
				if (yes) {
					if(ParamVo.length > 0){
						ajaxPostData({
	         			    url: 'deleteBudgMedPurExe.do?isCheck=false',
	         			    data: {ParamVo : ParamVo.toString()},
	         			    success: function (responseData) {
	         			    	query();
	         			    }
	         			})
					}else{
           				grid.deleteSelectedRows(delData);
           				$.etDialog.success("删除成功.");
           			}
				}
			});
		}
	}

	//键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('D', remove);
	}

</script>
</head>

<body style="padding: 0px; overflow: hidden;">
<div id="pageloading" class="l-loading" style="display: none"></div>

<div id="toptoolbar"></div>

<div class="main">
		<table class="table-layout">
			<tr>
				<td class="label">年度：</td>
				<td class="ipt">
					<input type="text" id="year_input" />
				</td>
				<td class="label">月份：</td>
				<td class="ipt">
					<input type="text" id="month_input" />
				</td>
				<td class="label">药品类别：</td>
				<td class="ipt">
					<select name="" id="material_type_select" style="width:180px"></select>
				</td>
			</tr>
		</table>
	</div>
	<div id="maingrid"></div>
</body>

</html>