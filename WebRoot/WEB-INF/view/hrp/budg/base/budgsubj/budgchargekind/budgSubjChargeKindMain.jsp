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
		<jsp:param value="select,datepicker,dialog,ligerUI,grid" name="plugins" />
	</jsp:include>
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    var budg_year;
    $(function (){
    	//加载数据
    	loadHead(null);	
		init();
    });
    
    var year_input,subj_name_select

    function init(){
    	getData("../../../queryBudgYear.do?isCheck=false", function (data) {
			year_input = $("#year_input").etDatepicker({
				defaultDate: data[0].text,
				view: "years",
				minView: "years",
				dateFormat: "yyyy",
				//minDate: data[data.length - 1].text,
				//maxDate: data[0].text,
				todayButton: false,
				onSelect: function (value) {
					reloadSubjName(value);
					setTimeout(function () {
						query();
					}, 10);
				}
			});
			reloadSubjName(data[0].text);
		});

    	subj_name_select = $("#subj_name_select").etSelect({
    		defaultValue: "none",
    		onChange: query
    	});
    	function reloadSubjName(value){
    		subj_name_select.reload({
    			url:"../../../queryBudgSubj.do?isCheck=false",
    			para:{
    				subj_type:'04',
    				budg_year:value
    			}
    		})
    	}
    	
    }

    //ajax获取数据
    function getData(url, callback) {
    	$.ajax({
    		url: url,
    		dataType: "JSON",
    		type: "post",
    		success: function (res) {
    			if (typeof callback === "function") {
    				callback(res);
    			}
    		}
    	})
    };
   
    //查询
    function  query(){
    	var charge_kind_name=$('#charge_kind_name').val();
    	var search = [
			{ name: 'budg_year', value: year_input.getValue() },
			{ name: 'budg_subj_code', value: subj_name_select.getValue() },
			{ name: 'charge_kind_name', value:charge_kind_name},
		]
    	//加载查询条件
    	grid.loadData(search,"queryBudgSubjChargeKind.do?isCheck=false");
     }
    
    function loadHead(){
    	
    	grid = $("#maingrid").etGrid({
           columns: [        	  
				{display: '预算科目编码', name: 'budg_subj_code', align: 'left',width:"20%",editable:false },
				{display: '预算科目名称', name: 'budg_subj_name', align: 'left',width:"20%",
					editor: {
		 				 keyField:'budg_subj_code',
	               	     type: 'select',  //编辑框为下拉框时
	               	     //source:[],   //  静态数据接口  也可以是回调函数返回值
	               	     url: '../../../queryBudgSubj.do?isCheck=false&subj_type=04&budg_year='+$('#year_input').val(),
	               	     change:function(rowdata,celldata){
	               	    	 grid.updateRow(celldata.rowIndx,{budg_subj_code:rowdata.budg_subj_code})
	               	     },
	               	     //与年度联动查询
	               	     create:function(rowdata,celldata,setting){
	               	    	 if($('#year_input').val()){
	               	    		 setting.url = '../../../queryBudgSubj.do?isCheck=false&subj_type=04&budg_year='+$('#year_input').val();
	               	    	 }else{
	               	    		 $.etDialog.error('请先填写年度');
	               	    		 return false ;
	               	    	 }
	               	     }
	               	 }
				 },
				 { display: '收费类别编码', name: 'charge_kind_code', align: 'left', width: "20%"},
				 { display: '收费类别名称', name: 'charge_kind_name', align: 'left', minWidth: "20%"},
	           ],
	           dataModel:{
		          method:'POST',
		          location:'remote',
		          url:'',
		          recIndx: ''
	   	       },
	   	       pageModel:{
	   	    	   type:'remote',
	   	       },
	   	       usePager:true,width: '100%', height: '100%',checkbox: true,editable: true,
	   	       addRowByKey:true,
	   		   toolbar: {
	              items: [
		           	{ type: "button", label: '查询',icon:'search',listeners: [{ click: query}] },
		         	/*{ type: "button", label: '继承', icon: 'plus', listeners: [{ click: extend }] },*/
					{ type: "button", label: '添加行',icon:'plus',listeners: [{ click: add_Row}] },
					{ type: "button", label: '保存',icon:'disk',listeners: [{ click: save}] },
					{ type: "button", label: '删除',icon:'minus',listeners: [{ click: remove}] },
					/* { type: "button", label: '打印',icon:'print',listeners: [{ click: printDate}] }, */
					{ type: "button", label: '下载导入模板',icon:'arrowthickstop-1-s',listeners: [{ click: downTemplate}] },
					{ type: "button", label: '导入',icon:'arrowthick-1-n',listeners: [{ click: imp}] },
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
		
    //添加行
    function add_Row(){
   		grid.addRow() ;
    }
	   
    //选择指标后 更新指标编码 
    function reloadAccSubjCode(rowdata,celldata){
	   	setTimeout(function () {
	   		grid.updateRow(celldata.rowIndx,{'acc_subj_code2':rowdata.acc_subj_name.split(" ")[0]})
	   	}, 10);
    }	
    
    function save() {
		var data = grid.getChanges();
    	var ParamVo =[];
		budg_year=$('#year_input').val();
		if(data.addList.length > 0 || data.updateList.length > 0){
        	if(data.addList.length > 0){
        		var addData = data.addList ;
        		if(!validateGrid(addData)){
        			return  false ;
        		}
                $(addData).each(function (){
                	ParamVo.push(
               			//表的主键
               			budg_year+"@"+
						this.budg_subj_code   +"@"+ 
						this.budg_subj_name   +"@"+ 
						this.charge_kind_code   +"@"+ 
						this.charge_kind_name   	+"@"+
						this.budg_subj_code_old   	+"@"+
						this.charge_kind_code_old  	+"@"+
						'1'
    				) 
    			});
        	}
        	
    		if(data.updateList.length > 0){
        		var updateData = data.updateList ;
        		if(!validateGrid(updateData)){
        			return false ;
        		}
                $(updateData).each(function (){	
                	ParamVo.push(
          				//表的主键
     					budg_year	 +"@"+
     					this.budg_subj_code   +"@"+ 
						this.budg_subj_name   +"@"+ 
						this.charge_kind_code   +"@"+ 
						this.charge_kind_name   	+"@"+
						this.budg_subj_code_old   	+"@"+
						this.charge_kind_code_old  	+"@"+
    					'2' //修改数据标识
    				) 
    			});
        	}
			ajaxPostData({
				url: "saveBudgSubjChargeKind.do?isCheck=false",
				data: { ParamVo: ParamVo.toString() },
				success: function (res) {
					query();
				}
			});
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
			if (!v.budg_subj_name) {
				rowm+="[预算科目名称]、";
			}
			if (!v.charge_kind_code) {
				rowm+="[收费类别编码]、";
			}
			if (!v.charge_kind_name) {
				rowm+="[收费类别名称]、";
			}
			if(rowm != ""){
				rowm = "第"+(Number(v._rowIndx)+1)+"行" + rowm.substring(0, rowm.length-1) + "不能为空" + "\n\r";
			}
			msg += rowm;
			var key=v.budg_subj_code + v.charge_kind_code
			
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
		budg_year=$('#input_year').val();
		var data = grid.selectGet();
        if (data.length == 0){
        	$.etDialog.error('请选择行');
        }else{
        	debugger;
            var ParamVo =[];//后台删除数据
            var deletePageRow = [];// 页面删除数据
            $(data).each(function (){	
            	if(this.rowData.group_id){
            		ParamVo.push(
           				this.rowData.group_id   +"@"+ 
						this.rowData.hos_id   +"@"+ 
						this.rowData.copy_code   +"@"+ 
						this.rowData.budg_year +"@"+
						this.rowData.budg_subj_code+"@"+
						this.rowData.charge_kind_code
       				) 
            	}else{
            		deletePageRow.push(this);
            	}
			});
            if(ParamVo.length > 0){
            	$.etDialog.confirm('确定删除?', function () {
					ajaxPostData({
						url: "deleteBudgSubjChargeKind.do?isCheck=false",
						data: { ParamVo: ParamVo.toString() },
						success: function () {
							query();
						}
					})
				});
            }else if(deletePageRow.length > 0 ){
            	grid.deleteRows(deletePageRow);
            	$.etDialog.success("删除成功!");
            }
		}
	}
    
	
    //继承
    function extend(){
 	   	var budg_year = year_input.getValue();
 	   	if(!budg_year){
 	   		$.etDialog.error("预算年度不能为空");
 	   		return
 	   	}
 	   	ajaxPostData({
		    url: "extend.do?isCheck=false&budg_year="+budg_year,
		    data: '',
		    success: function (responseData) {
		    	query();
		    },
		})
    }
	
    function imp() {
    	budg_year=$('#year_input').val();
		var index = layer.open({
			type: 2,
			title: '导入',
			shadeClose: false,
			shade: false,
			maxmin: true, //开启最大化最小化按钮
			area: ['893px', '500px'],
			content: 'budgSubjChargeKindImportPage.do?isCheck=false&budg_year='+budg_year
		});
		layer.full(index);
	}
	function downTemplate() {
		location.href = "downTemplate1.do?isCheck=false";
	}
    
    function openUpdate(obj){
		var vo = obj.split("|");
		var parm = "group_id="+vo[0]+"&hos_id="+ vo[1]+"&copy_code="+ vo[2]+"&budg_year="+ vo[3]+"&acc_subj_code2="+ vo[4]+"&subj_type="+ vo[5];
    	$.ligerDialog.open({ url :'budgAccSubjCostShipUpdatePage.do?isCheck=false&'+parm,data:{}, height: 300,width: 450, 
			title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
			buttons: [ 
				{ text: '确定', onclick: function (item, dialog) 
				{ dialog.frame.saveBudgAccRelationship(); },cls:'l-dialog-btn-highlight' }, 
				{ text: '取消', onclick: function (item, dialog) { dialog.close(); } }
			] 
    	});
    }
    
    //打印数据
	function printDate(){
		if(grid.getAllData().length==0){
			$.ligerDialog.error("无打印数据！");
			return;
		}
    	grid.options.parms=[];
    	grid.options.newPage=1;
		
    	grid.options.parms.push({name:'budg_year',value: liger.get("budg_year").getValue()});
    	grid.options.parms.push({name:'subj_code',value:liger.get("subj_code").getValue()});
    	grid.options.parms.push({name:'acc_subj_code2',value:liger.get("acc_subj_code2").getValue()});
        var selPara={};
    	$.each(grid.options.parms,function(i,obj){
    		selPara[obj.name]=obj.value;
    	});
   		var printPara={
   			headCount:2,
   			title:"支出预算科目与会计科目对应关系",
   			type:3,
   			columns:grid.getColumns(1)
   		};
   		ajaxJsonObjectByUrl("queryBudgAccSubjCostShip.do?isCheck=false", selPara, function(responseData) {
   			printGridView(responseData,printPara);
    	});
	}
    </script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
   <div class="main">
		<table class="table-layout">
			<tr>
				<td class="label">预算年度：</td>
				<td class="ipt">
					<input type="text" id="year_input" />
				</td>
				<td class="label" style="width:100px">收费类别名称：</td>
				<td class="ipt">
					<input type="text" name="" id="charge_kind_name" style="width:180px;"></select>
				</td>
				<td class="label" style="width:100px">预算科目名称：</td>
				<td class="ipt">
					<select name=" " id="subj_name_select" style="width:180px;"></select>
				</td>
			</tr>
		</table>
	</div>
	<div id="maingrid"></div>
</body>
</html>
