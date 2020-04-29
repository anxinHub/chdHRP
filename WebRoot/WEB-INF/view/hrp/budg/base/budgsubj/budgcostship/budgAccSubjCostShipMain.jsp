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
    
    var year_input,subj_name_select,acc_subj_code2

    function init(){
    	year_input = $("#year_input").etDatepicker({
            view: "years",
            minView: "years",
            dateFormat: "yyyy",
            clearButton: false,
            onChange: function () {
                setTimeout(function () {
                	query();
                }, 10);
            },
            defaultDate: true
        });


    	subj_name_select = $("#subj_name_select").etSelect({
    		url:"../../../queryBudgSubj.do?isCheck=false&subj_type=05",
    		defaultValue: "none",
    		onChange: query
    	});
    	function reloadSubjName(value){
    		subj_name_select.reload({
    			url:"../../../queryBudgSubj.do?isCheck=false",
    			para:{
    				subj_type:'05',
    				budg_year:value
    			}
    		})
    	}
    	
    	acc_subj_code2 = $("#acc_subj_code2").etSelect({
    		url:"../../../queryAccSubj.do?isCheck=false&subj_type=05",
    		defaultValue: "none",
    		onChange: query
    	});
    	function reloadAccSubjName(value){
    		acc_subj_code2.reload({
    			url:"../../../queryAccSubj.do?isCheck=false",
    			para:{
    				subj_type:'05',
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
    	var search = [
			{ name: 'budg_year', value: year_input.getValue() },
			{ name: 'subj_code', value: subj_name_select.getValue() },
			{ name: 'acc_subj_code2', value: acc_subj_code2.getValue() },
		]
    	//加载查询条件
    	grid.loadData(search,"queryBudgAccSubjCostShip.do?isCheck=false");
     }
    
    function loadHead(){
    	grid = $("#maingrid").etGrid({
           columns: [ 
        	   {display: '预算年度', name: 'budg_year', align: 'center',width:"18%",editable:setEdit,
					 editor: {
		 				 keyField:'budg_year',
	               	     type: 'select',  //编辑框为下拉框时
	               	     url: '../../../queryBudgYearTen.do?isCheck=false',      //  动态数据接口
	               	     change:function(rowdata,celldata){
	                   		 grid.updateRow(celldata.rowIndx,{subj_code:"",subj_name:""})
	                   		 grid.updateRow(celldata.rowIndx,{acc_subj_code2:"",acc_subj_name:""})
	                   	 }
               	 	 },
				},
				{display: '预算科目编码', name: 'subj_code', align: 'left',width:"20%",editable:false },
				{display: '预算科目名称', name: 'subj_name', align: 'left',width:"20%",
					editor: {
		 				 keyField:'subj_code',
	               	     type: 'select',  //编辑框为下拉框时
	               	     //source:[],   //  静态数据接口  也可以是回调函数返回值
	               	     url: 'queryBudgCostTypeSet.do?isCheck=false&budg_year='+budg_year,
	               	     change:function(rowdata,celldata){
	               	    	 grid.updateRow(celldata.rowIndx,{subj_code:rowdata.subj_code})
	               	     },
	               	     //与年度联动查询
	               	     create:function(rowdata,celldata,setting){
	               	    	 if(rowdata.budg_year){
	               	    		 setting.url = 'queryBudgCostTypeSet.do?isCheck=false&budg_year='+rowdata.budg_year;
	               	    	 }else{
	               	    		 $.etDialog.error('请先填写年度');
	               	    		 return false ;
	               	    	 }
	               	     }
	               	 }
				 },
				 { display: '会计科目编码', name: 'acc_subj_code2', align: 'left', width: "20%",editable:false},
				 { display: '会计科目名称', name: 'acc_subj_name', align: 'left', minWidth: "20%",
						editor:{
			 				type:'select' ,
			 				keyField:'acc_subj_code2',
			 				url:'queryAccSubj.do?isCheck=false&acc_year='+budg_year+'&subj_type='+ '05',
			 				change:reloadAccSubjCode,
							//与年度联动查询
			           	    create:function(rowdata,celldata,setting){
			           	    	 if(rowdata.budg_year){
			           	    		 setting.url = 'queryAccSubj.do?isCheck=false&subj_type='+ '05'+'&acc_year='+rowdata.budg_year;
			           	    	 }else{
			           	    		 $.etDialog.error('请先填写年度');
			           	    		 return false ;
			           	    	 }
			           	    }
						}
					},
	           ],
	           dataModel:{
		          method:'POST',
		          location:'remote',
		          url:'',
		          recIndx: 'budg_year'
	   	       },
	   	       pageModel:{
	   	    	   type:'remote',
	   	       },
	   	       usePager:true,width: '100%', height: '100%',checkbox: true,editable: true,
	   	       addRowByKey:true,
	   		   toolbar: {
	              items: [
		           	{ type: "button", label: '查询',icon:'search',listeners: [{ click: query}] },
		         	//{ type: "button", label: '增量生成', icon: 'plus', listeners: [{ click: generate }] },
		         	{ type: "button", label: '继承', icon: 'plus', listeners: [{ click: extend }] },
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

		if(data.addList.length > 0 || data.updateList.length > 0){
        	if(data.addList.length > 0){
        		var addData = data.addList ;
        		if(!validateGrid(addData)){
        			return  false ;
        		}
                $(addData).each(function (){	
                	ParamVo.push(
               			//表的主键
               			this.budg_year	 +"@"+
						this.acc_subj_code2   +"@"+ 
						this.acc_subj_name   +"@"+ 
						this.subj_code   +"@"+ 
						this.subj_name   	+"@"+ 
						'05'   +"@"+ 
						this.acc_subj_code2_old   +"@"+ 
    					this._rowIndx +"@"+ 
    					'1' //添加数据标识
    				) 
    			});
        	}
        	
    		if(data.updateList.length > 0){
        		var updateData = data.updateList ;
                $(updateData).each(function (){	
                	ParamVo.push(
          				//表的主键
     					this.budg_year	 +"@"+
						this.acc_subj_code2   +"@"+ 
						this.acc_subj_name   +"@"+ 
						this.subj_code   +"@"+ 
						this.subj_name   	+"@"+ 
						'05'   +"@"+ 
						this.acc_subj_code2_old   +"@"+ 
    					this._rowIndx +"@"+ 
    					'2' //修改数据标识
    				) 
    			});
        	}
			ajaxPostData({
				url: "saveBudgAccSubjCostShip.do?isCheck=false",
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
			if (!v.budg_year) {
				rowm+="[年度]、";
			}
			if (!v.subj_name) {
				rowm+="[预算科目名称]、";
			}
			if (!v.acc_subj_name) {
				rowm+="[会计科目]、";
			}
			if(rowm != ""){
				rowm = "第"+(Number(v._rowIndx)+1)+"行" + rowm.substring(0, rowm.length-1) + "不能为空" + "\n\r";
			}
			msg += rowm;
			var key=v.budg_year + v.subj_code + v.acc_subj_code2
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
        if (data.length == 0){
        	$.etDialog.error('请选择行');
        }else{
            var ParamVo =[];//后台删除数据
            var deletePageRow = [];// 页面删除数据
            $(data).each(function (){	
            	if(this.rowData.group_id){
            		ParamVo.push(
           				this.rowData.group_id   +"@"+ 
						this.rowData.hos_id   +"@"+ 
						this.rowData.copy_code   +"@"+ 
						this.rowData.budg_year +"@"+
						this.rowData.acc_subj_code2
       				) 
            	}else{
            		deletePageRow.push(this);
            	}
			});
            if(ParamVo.length > 0){
            	$.etDialog.confirm('确定删除?', function () {
					ajaxPostData({
						url: "deleteBudgAccSubjCostShip.do?isCheck=false",
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
    
	//增量生成
    function generate(){
 	   	var budg_year = year_input.getValue();
 	   	if(!budg_year){
 	   		$.etDialog.error("预算年度不能为空");
 	   		return
 	   	}
 	   	$.etDialog.confirm('生成数据将覆盖原有数据,是否确定生成?', function () {
	 	   	ajaxPostData({
			    url: "generate.do?isCheck=false&budg_year="+budg_year,
			    data: '',
			    success: function (responseData) {
			    	query();
			    },
			})
 	   	})
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
		var index = layer.open({
			type: 2,
			title: '导入',
			shadeClose: false,
			shade: false,
			maxmin: true, //开启最大化最小化按钮
			area: ['893px', '500px'],
			content: 'budgAccSubjCostShipImportPage.do?isCheck=false'
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
				<td class="label" style="width:100px">会计科目：</td>
				<td class="ipt">
					<select name="" id="acc_subj_code2" style="width:180px;"></select>
				</td>
				<td class="label" style="width:100px">支出预算科目：</td>
				<td class="ipt">
					<select name=" " id="subj_name_select" style="width:180px;"></select>
				</td>
			</tr>
		</table>
	</div>
	<div id="maingrid"></div>
</body>
</html>
