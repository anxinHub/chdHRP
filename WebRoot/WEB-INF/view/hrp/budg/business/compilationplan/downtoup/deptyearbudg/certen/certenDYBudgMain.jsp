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
<jsp:param value="select,datepicker,ligerUI,grid" name="plugins" />
</jsp:include>
<script type="text/javascript">
    var grid;
    var year_input;
    var index_code_select;
    var dept_id_select;
    var budg_year ;
    var index_code;
    var dept_id;
    var rowidex ;
    
    //打印 单元格格式化 用
    var renderFunc = {
    	count_value : function(value){ //计算值
   			return formatNumber(value, 2, 1);
   		},	 
     	budg_value : function(value){ //预算值
  			return formatNumber(value, 2, 1);
  		},
  		dept_suggest_sum : function(value){ //科室意见汇总
  			return formatNumber(value, 2, 1);
  		},
  	};
    $(function (){
    	loadHead(null);
    	init();
		loadHotkeys();
    });
    
    function init(){
    	year_input = $("#year_input").etDatepicker({
            view: "years",
            minView: "years",
            dateFormat: "yyyy",
            clearButton: false,
            onChange: function (value) {
            	index_code_select.setValue('');
            	reloadSubjName(value);
            },
            defaultDate: true
        });


		index_code_select = $("#index_code_select").etSelect({
			defaultValue: "none",
			onChange: function(value){
				index_code = value ;
				dept_id_select.setValue('');
				reloadDeptName(value);
				
			}
		});
		reloadSubjName(year_input.getValue());
		function reloadSubjName(value){
			index_code_select.reload({
				url:"../../../../../qureyBudgIndexFromPlan.do?isCheck=false&budg_level=03&edit_method=03",
				para:{
					budg_year:value
				}
			})
		};
		
		dept_id_select = $("#dept_id_select").etSelect({
			defaultValue: "none"
		});
		reloadDeptName(index_code_select.getValue());
		function reloadDeptName(value){
			dept_id_select.reload({
				url:"../../../../../queryBudgIndexDeptSet.do?isCheck=false",
				para:{
					index_code : value
				}
			})
		};
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
    	var params = [
			{ name: 'year', value: year_input.getValue() },
			{ name: 'index_code', value: index_code_select.getValue() },
			{ name: 'dept_id', value: dept_id_select.getValue() },
			{ name: 'budg_level', value: "03" },
			{ name: 'edit_method', value: "03" },
		]
		//加载查询条件
		grid.loadData(params,"queryCertenDYBudgDown.do?isCheck=false&budg_level=03&edit_method=03");
    }

    function loadHead(){
    	grid = $("#maingrid").etGrid({
           columns: [ 
		   	  { display: '预算年度', name:'year', align: 'center',width:90,editable:setEdit ,
		      	    editor : {
						type : 'select',
						keyField : 'year',
						url : '../../../../../queryBudgYear.do?isCheck=false',
						change:function(rowdata,celldata){
	             		 	grid.updateRow(celldata.rowIndx,{index_code:"",index_name:""})
	             	 	}
	     	 		}
				},
		      { display: '指标编码', name: 'index_code', align: 'left',editable:false ,width:120,
				},
			  { display: '指标名称', name: 'index_name', align: 'left',editable:setEdit ,width:150,
					editor : {
						type : 'select',
						keyField : 'index_code',
						url : 'queryBudgIndex.do?isCheck=false&budg_level=03&edit_method=03',
						change :function(rowdata,celldata){
	             		 	grid.updateRow(celldata.rowIndx,{dept_code:"",dept_name:""})
	             	 	} ,
				    	//与年度联动查询
	         	        create:function(rowdata,celldata,setting){
	         	    	 	if(rowdata.year){
	         	    		 	setting.url = 'queryBudgIndex.do?isCheck=false&budg_level=03&edit_method=03&budg_year='+rowdata.year;
	         	    	 	}else{
	         	    		 	$.etDialog.error('请先填写年度');
	         	    		 return false ;
	         	    	 	}
	         	     	}
					} 
				  },
				{ display: '科室编码', name: 'dept_code', align: 'left',width:80,editable:false,
				  },
				{ display: '科室名称', name: 'dept_name', align: 'left',width:150,editable:setEdit,
					  editor:{
						type:'select' ,
						keyField:'dept_id',
						url:'../../../../../queryBudgIndexDeptSet.do?isCheck=false&index_code=',
						change:reloadDeptCode,
		 				//与指标联动查询
	             	    create:function(rowdata,celldata,setting){
	             	    	 if(rowdata.index_code){
	             	    		 setting.url = '../../../../../queryBudgIndexDeptSet.do?isCheck=false&index_code='+rowdata.index_code;
	             	    	 }else{
	             	    		 $.etDialog.error('请先填写指标名称');
	             	    		 return false ;
	             	    	 }
	             	    }
					  }
				   },
				{ display: '计算值', name: 'count_value', align: 'right',width:120,dataType:'float',
					render:function(ui){
						if (ui.rowData.count_value) {
							return formatNumber(ui.rowData.count_value, 2, 1);
						}
					}
				 },
               { display: '预算值(E)', name: 'budg_value', align: 'right',width:120,dataType:'float',
					 render:function(ui){
						if (ui.rowData.fun_id){//取值函数链接
	                 		return "<a href=javascript:openFunProcess('"+ui.rowData.group_id+"|"+ 
	                 		ui.rowData.hos_id+"|"+ui.rowData.copy_code+"|"+ui.rowData.index_code+"|"+
	                 		ui.rowData.dept_id+"|"+ui.rowData.year+"|"+ui.rowData.fun_id+"')>"+formatNumber(ui.rowData.budg_value,2,1) +"</a>";
	                 	}else if(ui.rowData.formula_id){//计算公式链接
	                 		return "<a href=javascript:openFormulaProcess('"+ui.rowData.group_id+"|"+ 
	                 		ui.rowData.hos_id+"|"+ui.rowData.copy_code+"|"+ui.rowData.index_code+"|"+
	                 		ui.rowData.dept_id+"|"+ui.rowData.year+"|"+ui.rowData.formula_id+"')>"+formatNumber(ui.rowData.budg_value,2,1) +"</a>";
	                 	}else{
	                 		return formatNumber(ui.rowData.budg_value,2,1) ;
	                 	}
					 }
				  },
               { display: '说明(E)', name: 'remark', align: 'left',minWidth:120,dataType:'string'
				  }
            ],
            dataModel:{
	           	method:'POST',
	           	location:'remote',
	           	url:'',
	           	recIndx: 'year'
	         },
   	         usePager:true,width: '100%', height: '100%',checkbox: true,editable: true,
   	         addRowByKey:true,
   	     	 toolbar: {
	          	 items: [
		         	 { type: "button", label: '查询',icon:'search',listeners: [{ click: query}] },
		         	 { type: "button", label: '增量生成',icon:'plus',listeners: [{ click: generate}] },
					 { type: "button", label: '添加行',icon:'plus',listeners: [{ click: addRow}] },
				  	 { type: "button", label: '保存',icon:'disk',listeners: [{ click: save}] },
					 { type: "button", label: '删除',icon:'minus',listeners: [{ click: remove}] },
				  	 { type: "button",label:'下载导入模板', icon:'arrowthickstop-1-s',listeners: [{ click: downTemplate}]},
					 { type: "button", label: '导入',icon:'arrowthick-1-n',listeners: [{ click: imp}] },
					 { type: "button", label: '计算',icon:'calculator',listeners: [{ click: collect}] },
	       	 	 ]
	         }
        });
    }
    
    //选择科室后 更新科室编码 
    function reloadDeptCode(rowdata,celldata){
    	setTimeout(function () {
    		grid.updateRow(celldata.rowIndx,{'dept_code':rowdata.dept_name.split(" ")[0]})
    	}, 10);
    }
  //打印回调方法
    function lodopPrint(){
    	var head="";
 		grid.options.lodop.head=head; 
 		grid.options.lodop.fn=renderFunc;
 		grid.options.lodop.title="科室年度业务预算-確定预算";
    }
   	
  //添加可编辑行
    function addRow(){
    	grid.addRow();
    }
    
 	// 根据 group_id 是否存在 返回 true 或 false  控制单元格可否编辑 用 
    function setEdit(ui){
   		 if(ui.rowData && ui.rowData.group_id){
   			 return false ;
   		 }else{
   			 return true ;
   		 }
    }
	
    function add_open(){
		$.ligerDialog.open({ url : 'certenDYBudgAddPage.do?isCheck=false',data:{}, height: 500,width: 800, 
			title:'科室年度业务预算确定预算添加',modal:true,showToggle:false,showMax:true,showMin: false,isResize:true
    	}); 
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
               			this.year   +"@"+ 
       					this.index_code  +"@"+ 
       					this.dept_id  +"@"+ 
       					this.dept_code  +"@"+ 
       					(this.count_value? this.count_value:"")  +"@"+ 
       					(this.budg_value? this.budg_value:"")  +"@"+ 
      					(this.remark?this.remark:"")   	+"@"+ 
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
      					this.index_code  +"@"+ 
      					this.dept_id  +"@"+ 
      					this.dept_code  +"@"+ 
      					(this.count_value? this.count_value:"")  +"@"+ 
      					(this.budg_value? this.budg_value:"")  +"@"+ 
       					(this.remark?this.remark:"")   	+"@"+ 
    					this._rowIndx +"@"+ 
    					'2' //修改数据标识
    				) 
    			});
        	}
    		ajaxPostData({
 			    url: 'saveCertenDYBudgDown.do?isCheck=false',
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
			if (!v.year) {
				rowm+="[年度]、";
			}
			if (!v.index_name) {
				rowm+="[科目名称]、";
			}
			if (!v.dept_name) {
				rowm+="[科室名称]、";
			}
			if (!v.budg_value) {
				rowm+="[预算值]、";
			}
 			
			if(rowm != ""){
				rowm = "第"+(Number(v._rowIndx)+1)+"行" + rowm.substring(0, rowm.length-1) + "不能为空" + "\n\r";
			}
			msg += rowm;
			var key=v.year + v.month + v.index_code + v.dept_id 
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
    
    function remove(){
    	var data = grid.selectGet();
        if (data.length == 0){
        	$.etDialog.error('请选择行');
        }else{
            var ParamVo =[];
            var deleteDate = [];
            $(data).each(function (){	
            	if(!this.rowData.group_id){
            		deleteDate.push(this);
            	}else{
            		ParamVo.push(
						this.rowData.group_id   +"@"+ 
						this.rowData.hos_id   +"@"+ 
						this.rowData.copy_code   +"@"+ 
						this.rowData.year   +"@"+ 
						this.rowData.dept_id   +"@"+
						this.rowData.index_code 
					) 	
            	}
			});
            $.etDialog.confirm('确定删除?', function (yes){
            	if(yes){
            		if(ParamVo.length > 0){
            			ajaxPostData({
             			    url: 'deleteCertenDYBudgDown.do?isCheck=false',
             			    data: {ParamVo : ParamVo.toString()},
             			    success: function (responseData) {
             			    	query();
             			    }
             			})
            		}else{
            			grid.deleteRows(deleteDate);
                    	$.etDialog.success("删除成功!");
            		}
            	}
            }); 
        }
    }
    function imp(){
    	
    	var index = layer.open({
			type : 2,
			title : '导入',
			shadeClose : false,
			shade : false,
			maxmin : true, //开启最大化最小化按钮
			area : [ '893px', '500px' ],
			content : 'certenDYBudgImportPage.do?isCheck=false'
		});
		layer.full(index);
    }
    function downTemplate(){
    	location.href = "downTemplate.do?isCheck=false";
    }	
   
    //
    function openUpdate(obj){
		var vo = obj.split("|");
		var parm = "group_id="+vo[0] +"&hos_id="+vo[1] +"&copy_code="+vo[2] +"&year="+vo[3] +"@index_code="+vo[5] 
		$.ligerDialog.open({ url : 'certenHYBudgUpdatePage.do?isCheck=false&' + parm ,data:{}, height: 300,width: 450, title:'医院年度医疗收入独立核算科目预算',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
			buttons: [
				{ text: '确定', onclick: function (item, dialog) { dialog.frame.saveBudgWorkHosYear(); },cls:'l-dialog-btn-highlight' },
			    { text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
			]
    	}); 
    }
    
  //增量生成
    function generate(){
 	   	var year = year_input.getValue();
 	   	if(year){
 	   		ajaxPostData({
			    url: "generate.do?isCheck=false&year="+year,
			    data: {},
			    success: function (responseData) {
			    	query();
			    }
			})
 	   	}else{
 	   		$.etDialog.error("预算年度不能为空");
 	   	}
    }
  
  //计算
    function collect(){
		var year = year_input.getValue() ;
		if( !year){
			$.etDialog.error('预算年度不能为空');
			return false ;
		}
		ajaxPostData({
		    url: "collectCertenDYBudgData.do?isCheck=false&year="+year+"&budg_level="+"03"+"&edit_method="+"03",
		    data: {},
		    success: function (responseData) {
		    	query();
		    }
		})
   	}
    //计算过程查看  链接
    function openFormulaProcess(obj){
 	    var vo = obj.split("|");
		var parm = 
			"group_id="+vo[0]   +"&"+ 
			"hos_id="+vo[1]   +"&"+ 
			"copy_code="+vo[2]   +"&"+ 
			"index_code="+vo[3]   +"&"+ 
			"dept_id="+vo[4]   +"&"+
			"year="+vo[5]   +"&"+
			"formula_id="+vo[6] +"&"+
			"element_type_code="+'03' 
			"element_level="+'03' 
		parent.$.ligerDialog.open({ url : 'hrp/budg/common/budgformula/budgFormulaProcessPage.do?isCheck=false&'+parm,data:{}, 
			 height:600,width:800, title:'计算过程查看',modal:true,showToggle:false,showMax:true,showMin: false,
			 isResize:true,parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
   		});
    }
	
	//取值函数查看  链接 
    function openFunProcess(obj){
 	    var vo = obj.split("|");
 		var parm = 
 			"group_id="+vo[0]   +"&"+ 
 			"hos_id="+vo[1]   +"&"+ 
 			"copy_code="+vo[2]   +"&"+ 
 			"index_code="+vo[3]   +"&"+ 
 			"dept_id="+vo[4]   +"&"+
 			"year="+vo[5]   +"&"+
 			"fun_code="+vo[6] +"&"+
 			"budg_year="+vo[5]   +"&"+
 			"budg_level="+'03'   +"&"+
 			"index_type_code="+"03" 
 		 
		parent.$.ligerDialog.open({ url : 'hrp/budg/common/budgfun/budgFunProcessPage.do?isCheck=false&'+parm,data:{}, 
			 height:600,width:800, title:'函数取值过程查看',modal:true,showToggle:false,showMax:true,showMin: false,
			 isResize:true,parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
   		});
    }
    //键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('G', generate);
		//hotkeys('A', add_open);
		hotkeys('S', save);
		hotkeys('D', remove);
		hotkeys('B', downTemplate);
		hotkeys('I', imp);
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
				<td class="label">预算指标：</td>
				<td class="ipt">
					<select name="" id="index_code_select" style="width:180px;"></select>
				</td>
				<td class="label">科室名称：</td>
				<td class="ipt">
					<select name="" id="dept_id_select" style="width:180px;"></select>
				</td>
			</tr>
		</table>
	</div>
	<div id="maingrid"></div>
	
</body>
</html>
