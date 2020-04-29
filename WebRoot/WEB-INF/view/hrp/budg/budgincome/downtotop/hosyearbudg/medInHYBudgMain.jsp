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
<script src="/CHD-HRP/lib/et_assets/hr/common.js"></script>
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    var budg_year ;
    $(function (){
    	//加载数据
    	loadHead(null);	
		loadHotkeys();
		init();
    });
	var year_input,subj_name_select,subj_level_select;

	function init(){
		year_input = $("#year_input").etDatepicker({
            view: "years",
            minView: "years",
            dateFormat: "yyyy",
            clearButton: false,
            onChange: function () {
                setTimeout(function () {
                	query();
                	reloadSubjName();
                }, 10);
            },
            defaultDate: true
        });

		subj_name_select = $("#subj_name_select").etSelect({
			url:"../../../queryBudgSubj.do?isCheck=false&subj_type=04&budg_year="+year_input.getValue(),
			defaultValue: "none",
			onChange: query
		});
		function reloadSubjName(value){
			subj_name_select.reload({
				url:"../../../queryBudgSubj.do?isCheck=false",
				para:{
					subj_type:'04',
					budg_year:year_input.getValue()
				}
			});
		}
		
		subj_level_select = $("#subj_level_select").etSelect({
			url: "../../../queryBudgSubjLevel.do?isCheck=false&insurance_natrue='01'",
			defaultValue: "none",
			onChange: query
		});
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
		});
	};

	function query(){
		var search = [
			{ name: 'year', value: year_input.getValue() },
			{ name: 'subj_code', value: subj_name_select.getValue() },
			{ name: 'subj_level', value: subj_level_select.getValue() }
		];
		//加载查询条件
		grid.loadData(search,"");
	}
	
    function loadHead(){
    	 var yearEditor = getRecentYearForSelect();	
    	grid = $("#maingrid").etGrid({
			columns: [
				{display: '预算年度', name: 'year', align: 'center',width:100,editable:setEdit ,
					editor: yearEditor,
                },
				{display: '科目编码', name: 'subj_code', align: 'left',width:120,editable:false},
				{display: '科目名称', name: 'subj_name', align: 'left',width:140,editable:setEdit ,
					editor: {
		 				 keyField:'subj_code',
                	     type: 'select',  //编辑框为下拉框时
                	     url: '../../../qureyResolveSubjIndexFromPlan.do?isCheck=false&is_single_count=0&budg_year='+budg_year,      //  动态数据接口
                	     change: queryLastYearIncomeload ,
                	     //与年度联动查询
                	     create:function(rowdata,celldata,setting){
                	    	 if(rowdata.year){
                	    		 setting.url = '../../../qureyResolveSubjIndexFromPlan.do?isCheck=false&is_single_count=0&budg_year='+rowdata.year;
                	    	 }else{
                	    		 $.etDialog.error('请先填写年度');
                	    		 return false ;
                	    	 }
                	     }
                	 }
				},
				{display: '上年收入(元)', name: 'last_year_income', align: 'right',editable:false,width:140,
					render:function(ui){
						if (ui.rowData.last_year_income) {
							return formatNumber(ui.rowData.last_year_income, 2, 1);
						}
					}
				},
				{display: '预算值(E)', name: 'budg_value', align: 'right', width:140,dataType:'float',
					render:function(ui){
						return formatNumber(ui.rowData.budg_value, 2, 1);
					}
				},
				{display: '说明(E)', name: 'remark', align: 'left', dataType: 'string',minWidth:240},
			],
			dataModel:{
	           	 method:'POST',
	           	 location:'remote',
	           	 url:'queryMedInHYBudgUp.do?isCheck=false',
	           	 recIndx: 'year'
            },
            usePager:true,width: '100%', height: '100%',checkbox: true,editable: true,
            addRowByKey:true,
			toolbar: {
              items: [
		           	{ type: "button", label: '查询',icon:'search',listeners: [{ click: query}] },
		           /* 	{ type: "button", label: '增量生成',icon:'plus',listeners: [{ click: generate}] }, */
					{ type: "button", label: '添加行',icon:'plus',listeners: [{ click: add_Row}] },
					{ type: "button", label: '保存',icon:'disk',listeners: [{ click: save}] },
					{ type: "button", label: '删除',icon:'minus',listeners: [{ click: remove}] },
					{ type: "button",label:'下载导入模板', icon:'arrowthickstop-1-s',listeners: [{ click: downTemplate}]},
					{ type: "button", label: '导入',icon:'arrowthick-1-n',listeners: [{ click: imp}] },
					{ type: "button", label: '汇总',icon:'plus',listeners: [{ click: collect}] },
          	]}
		});
    }
  //添加行
    function add_Row(){
    	grid.addRow() ;
    }
    //选择指标后 更新指标编码 
    function reloadDeptCode(rowdata,celldata){
    	setTimeout(function () {
    		grid.updateRow(celldata.rowIndx,{'dept_code':rowdata.dept_name.split(" ")[0]})
    	}, 10);
    }	
    // 根据 group_id 是否存在 返回 true 或 false  控制单元格可否编辑 用 
    function setEdit(ui){
   		 if(ui.rowData && ui.rowData.group_id){
   			 return false ;
   		 }else{
   			 return true ;
   		 }
    }
  
    //选择 科目后 查询其上年收入 并更新上年收入单元格
    function queryLastYearIncomeload(rowdata,celldata){
	   	budg_year = rowdata.year ;
	   	grid.updateRow(celldata.rowIndx,{"subj_code":rowdata.subj_code});
	   	if(rowdata.subj_code){
	   		$.post("queryLastYearIncome.do?isCheck=false&budg_year="+budg_year+"&subj_code="+rowdata.subj_code,null,function(responseData){
	  			if(!responseData){
	  				var para = eval("("+responseData+")") ;
	           	    if(para.last_year_income){
	           			grid.updateRow(celldata.rowIndx,{"last_year_income":para.last_year_income});
	           	 	}
	  			}else{
	  				grid.updateRow(celldata.rowIndx,{"last_year_income":0});
	       			$.etDialog.error('<span style="color: red">该指标上年收入数据不存在,</span>')
	       	 	}
	        });
	   	}else{
	   		$.etDialog.error('科目编码不能为空');
	   	}
    }
    
    function add_open(){
		$.ligerDialog.open({ url : 'medInHYBudgAddPage.do?isCheck=false',data:{}, height: 500,width: 800, 
				title:'医院年度医疗收入预算添加',modal:true,showToggle:false,showMax:true,showMin: false,isResize:true,
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
       					this.subj_code  +"@"+ 
       					(this.budg_value? this.budg_value:"")  +"@"+ 
       					(this.last_year_income? this.last_year_income:"")  +"@"+ 
       					(this.remark? this.remark:"")   	+"@"+ 
       					(this.hos_suggest? this.hos_suggest:"")  +"@"+
       					(this.count_value? this.count_value:"-1") + "@" +
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
       					this.subj_code  +"@"+ 
       					(this.budg_value? this.budg_value:"")  +"@"+ 
       					(this.last_year_income? this.last_year_income:"")  +"@"+ 
       					(this.remark? this.remark:"")   	+"@"+ 
       					(this.hos_suggest? this.hos_suggest:"")  +"@"+
       					(this.count_value? this.count_value:"-1") +"@"+
    					this._rowIndx +"@"+ 
    					'2' //修改数据标识
    				) 
    			});
        	}
    		ajaxPostData({
				url: "saveMedInHYBudgUp.do?isCheck=false",
				data: { ParamVo: ParamVo.toString() },
				success: function (res){
					if (res.state == "true") {
						query();
					}
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
			if (!v.subj_name) {
				rowm+="[科目名称]、";
			}
			if (!v.budg_value) {
				rowm+="[预算值]、";
			}
 			
			if(rowm != ""){
				rowm = "第"+(Number(v._rowIndx)+1)+"行" + rowm.substring(0, rowm.length-1) + "不能为空" + "\n\r";
			}
			msg += rowm;
			var key=v.year + v.month + v.subj_code
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
        				this.rowData.year   +"@"+ 
        				this.rowData.subj_code 
       				) 
            	}else{
            		deletePageRow.push(this);
            	}
			});
            if(ParamVo.length > 0){
            	$.etDialog.confirm('确定删除?',function (){
					ajaxPostData({
						url: "deleteMedInHYBudgUp.do?isCheck=false",
						data: { ParamVo: ParamVo.toString() },
						success: function (res){
							if (res.state == "true") {
								query();
							}
						}
					})
				});
            }else if(deletePageRow.length > 0 ){
            	grid.deleteRows(deletePageRow);
            	$.etDialog.success("删除成功!");
            }
		}
	}
    //汇总
    function collect(){
    	var year = year_input.getValue();
		if(!year){
			$.etDialog.error('预算年度不能为空');
			return false ;
		}
		ajaxPostData({
			url: "collectBudgMedIncomeHosYearDown.do?isCheck=false&year="+year,
			data: {},
			success: function (res){
				if (res.state == "true") {
					query();
				}
			}
		})
    }
    
    function imp(){
    	$.etDialog.open({
			url: 'medInHYBudgImportPage.do?isCheck=false',
			title: '导入',
			shadeClose: false,
			shade: false,
			maxmin: true, //开启最大化最小化按钮
			isMax: true
		});
    }	
    function downTemplate(){
   		location.href = "downTemplate.do?isCheck=false";
   	}	
   
 /*    function printDate(){
    	if(grid.getAllData().length==0){
			$.etDialog.error("无打印数据！");
			return;
		}
	    
    	grid.options.parms=[];
    	grid.options.newPage=1;
    	grid.options.parms.push({name:'year',value:year_input.getValue()}); 
  	  	grid.options.parms.push({name:'subj_code',value:subj_name_select.getValue()}); 
  	  	grid.options.parms.push({name:'subj_level',value:subj_level_select.getValue()});
        var selPara={};
    	$.each(grid.options.parms,function(i,obj){
    		selPara[obj.name]=obj.value;
    	});
   		var printPara={
   			headCount:2,
   			title:"医院年度医疗收入预算信息",
   			type:3,
   			columns:grid.getColumns(1)
   		};
   		ajaxJsonObjectByUrl("queryMedInHYBudgUp.do?isCheck=false", selPara, function(responseData) {
   			printGridView(responseData,printPara);
    	});
    } */
	var printDate = function () {
    	if(grid.getAllData()==null){
    		$.etDialog.error("请先查询数据！");
			return;
		}
    	var heads={
        		 /* "isAuto":true,//系统默认，页眉显示页码
        		"rows": [
    	          {"cell":0,"value":"表名："+tree.getSelectedNodes()[0].name},
        		]  */}; 
    	var printPara={
            title: "科室年度医疗收入预算编制方案",//标题
            columns: JSON.stringify(grid.getPrintColumns()),//表头
            class_name: "com.chd.hrp.budg.service.budgincome.toptodown.deptmonthinbudg.MedInDMBudgService",
            method_name: "queryMedInHYBudgUpData",
            bean_name: "medInDMBudgService",
            heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
            foots: '',//表尾需要打印的查询条件,可以为空 
        };
        $.each(grid.getUrlParms(),function(i,obj){
            printPara[obj.name]=obj.value;
        }); 
     	
        officeGridPrint(printPara);
    	
    	
    };
    //键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('A', add_Row);
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
				<td class="label">科目名称：</td>
				<td class="ipt">
					<select name="" id="subj_name_select" style="width:180px;"></select>
				</td>
				<td class="label">科目级次：</td>
				<td class="ipt">
					<select name="" id="subj_level_select" style="width:180px;"></select>
				</td>
			</tr>
		</table>
	</div>
	<div id="maingrid"></div>
	
</body>
</html>
