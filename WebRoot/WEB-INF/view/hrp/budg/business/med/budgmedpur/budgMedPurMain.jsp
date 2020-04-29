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
<script src="<%=path%>/lib/hrp/budg/budg.js" type="text/javascript"></script>
<script type="text/javascript">
	var budg_year;
	var grid;
	$(function () {
		loadHead(null);//加载数据
		loadHotkeys();
		init();
	});
	
	var year_input, month_input, material_type_select;

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

		material_type_select = $("#material_type_select").etSelect({
			defaultValue: "none",
			onChange: query
		});
		
		function materReload(budg_year){
			material_type_select.reload({
				url:"queryBudgMedPurTypeSubj.do?isCheck=false",
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
		grid.loadData(parms,'queryBudgMedPur.do?isCheck=false');
	}

	function loadHead() {
		grid = $("#maingrid").etGrid({
			columns: [
				{ display: '预算年度', name: 'year', align: 'left',width:"10%",editable:setEdit ,
	                    	 editor : {
									type : 'select',
									keyField : 'year',
									url : '../../../queryBudgYear.do?isCheck=false',
									keySupport : true,
									autocomplete : true,
									change : queryCostBudg
	                    	 }
						}, 
				{ display: '月份', name: 'month', align: 'left',width:"7%",editable:setEdit ,
                    	 editor : {
								type : 'select',
								keyField : 'monthId',
								textField : 'label',
								source:[ 
									{id : '01',label:'1月'},{id : '02',label:'2月'},{id : '03',label:'3月'},{id : '04',label:'4月'},
									{id : '05',label:'5月'},{id : '06',label:'6月'},{id : '07',label:'7月'},{id : '08',label:'8月'},
									{id : '09',label:'9月'},{id : '10',label:'10月'},{id : '11',label:'11月'},{id : '12',label:'12月'}
								],
								keySupport : true,
								autocomplete : true,
								change : queryCostBudg
                    	 }
					}, 
				{ display: '药品类别(E)', name: 'med_type_name', align: 'left',width:"20%",editable:setEdit ,
			 			editor : {
							type : 'select',
							keyField : 'med_type_id',
							url:"queryBudgMedPurTypeSubj.do?isCheck=false&budg_year="+budg_year,
							keySupport : true,
							autocomplete : true,
							change : queryCostBudg ,
							create:function(rowdata,celldata,setting){
	                	    	 if(rowdata.year){
	                	    		 setting.url = 'queryBudgMedPurTypeSubj.do?isCheck=false&budg_year='+rowdata.year;
	                	    	 }else{
	                	    		 $.etDialog.error('请先填写年度');
	                	    		 return false ;
	                	    	 }
	                	    }
						}
					},
				{ display: '支出预算',name: 'cost_budg',align: 'right',width:"20%",editable:false,
						render:function(ui){
							return formatNumber(ui.rowData.cost_budg, 2, 1);
						}
					}, 
				{ display: '采购预算(E)',name: 'pur_budg',align: 'right',dataType:"float",width:"20%",
						render:function(ui){
							return formatNumber(ui.rowData.pur_budg, 2, 1);
						}
					}, 
				{ display: '说明(E)',name: 'remark',align: 'left',dataType:"string",width:"20%",}
			],
			
			dataModel:{
	           	method:'POST',
	           	location:'remote',
	           	url:'',
	           	recIndx: 'year'
            }, 
            pageModel:{
            	type:'remote',
            },
            usePager:true,width: '100%', height: '100%',checkbox: true,editable: true,
            addRowByKey:true,
         	toolbar: {
               items: [
		           	{ type: "button", label: '查询',icon:'search',listeners: [{ click: query}] },
		           	{ type: "button", label: '生成',icon:'plus',listeners: [{ click: collect}] },
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
	  // 预算年度 、 月份 、药品分类 变化后 查询其支出预算 并更采购预算   支出预算 = 采购预算 
    function queryCostBudg(rowdata,celldata){
		if(rowdata.year && rowdata.monthId && rowdata.med_type_id ){
  	   		$.post("queryCostBudg.do?isCheck=false&year="+rowdata.year+"&month="
  	   				+rowdata.monthId+"&med_type_id="+rowdata.med_type_id,null,function(responseData){
  	      			
  	              	var para = eval("("+responseData+")") ;
  	              	
  	              	if(!para){
  	              		$.etDialog.warn('所选年度 、 月份 、物资分类的支出预算没有维护');
  	              		return false ;
  	              	} ;
  	              	grid.updateRow(celldata.rowIndx,{"cost_budg":para.cost_budg,"pur_budg":para.cost_budg});
  	   		});
  	   	}
    }
	
	//汇总BUDG_MED_COST数据  生成采够预算编制数据
	function collect() {
		if(year_input.getValue()){
			var formPara = {
					year: year_input.getValue()
				};
				$.etDialog.confirm('生成将会覆盖已有数据,确定生成?', function (yes) {
					if (yes) {
						ajaxPostData({
		     			    url: 'generateBudgMedPur.do?isCheck=false',
		     			    data: formPara,
		     			    success: function (responseData) {
		     			    	query();
		     			    }
		     			})
					}
				});
		}else{
			$.etDialog.warn('年度不能为空!');
		}
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
    					this.monthId   +"@"+ 
    					this.med_type_id +"@"+ 
    					this.pur_budg +"@"+ 
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
    					this.pur_budg +"@"+ 
    					(this.remark?this.remark:"") +"@"+
    					this._rowIndx +"@"+ 
    					'2' //修改数据标识
    				) 
    			});
        	}
	  	    ajaxPostData({url: 'saveBudgMedPur.do?isCheck=false',
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
			if (v.pur_budg == "" || v.pur_budg == null || v.pur_budg == 'undefined') {
				rowm+="[采购预算]、";
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
	         			    url: 'deleteBudgMedPur.do?isCheck=false',
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
		hotkeys('C', collect);
		hotkeys('A', add);
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