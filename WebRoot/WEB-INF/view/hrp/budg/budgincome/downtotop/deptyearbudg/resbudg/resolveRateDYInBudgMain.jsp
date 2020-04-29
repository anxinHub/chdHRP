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
<script src="<%=path%>/lib/hrp/med/med.js"	type="text/javascript"></script>
<script src="/CHD-HRP/lib/et_assets/hr/common.js"></script>
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    
    var budg_year ;
    
    $(function (){
    	loadHead(null);
    	//加载数据
		loadHotkeys();
		init();
    });
    
    var year_input,subj_name_select,subj_level_select,dept_name_select

	function init(){
    	getData("../../../../queryBudgYear.do?isCheck=false", function (data) {
			year_input = $("#year_input").etDatepicker({
				defaultDate: data[0].text,
				view: "years",
				minView: "years",
				dateFormat: "yyyy",
				/* minDate: data[data.length - 1].text,
				maxDate: data[0].text, */
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
				url:"../../../../queryBudgSubj.do?isCheck=false",
				para:{
					subj_type:'04',
					budg_year:value
				}
			})
		}
		
		subj_level_select = $("#subj_level_select").etSelect({
			url: "../../../../queryBudgSubjLevel.do?isCheck=false&insurance_natrue='01'",
			defaultValue: "none",
			onChange: query
		});

		dept_name_select=$("#dept_name_select").etSelect({
			url: "../../../../queryBudgDeptDict.do?isCheck=false",
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
			{ name: 'subj_level', value: subj_level_select.getValue() },
			{ name: 'dept_id', value: dept_name_select.getValue() }
		]
		//加载查询条件
		grid.loadData(search,"queryResolveRateDYInBudgUp.do?isCheck=false");
	}    

    function loadHead(){
    	grid = $("#maingrid").etGrid({
           columns: [ 
                     { display: '科室编码', name: 'dept_code', align: 'left', width: 100,editable:false
			 				},
				 	 { display: '科室名称', name: 'dept_name', align: 'left', width: 150,editable:false
				 	 		},
                     { display: '科目编码', name: 'subj_code', align: 'left', width: 100,editable:false
					
					 		},
				 	 { display: '科目名称', name: 'subj_name', align: 'left', width: 150,editable:false
					 		},
				 	 { display: '上年收入 (元)', name: 'last_year_income', align: 'left', width: 120,editable:false,
					 			render:function(ui){
									if (ui.rowData.last_year_income) {
										return formatNumber(ui.rowData.last_year_income, 2, 1);
									}
								}
					 		},
	                 { display: '增长比例(E)', name: 'grow_rate', align: 'left',width:80,dataType:'float',
					 			render:function(ui){
									if (ui.rowData.grow_rate) {
										return formatNumber(ui.rowData.grow_rate, 2, 1);
									}
								}
					 		},
                     { display: '分解比例(E)', name: 'resolve_rate', align: 'left',width:80,dataType:'float',
					 			render:function(ui){
									if (ui.rowData.resolve_rate) {
										return formatNumber(ui.rowData.resolve_rate, 2, 1);
									}
								}
					 		},
                     { display: '说明(E)', name: 'remark', align: 'left',dataType: 'string',minWidth:170
					 		}
		         ],
	             dataModel:{
	         	 method:'POST',
	         	 location:'remote',
	         	 url:'',
	         	 recIndx: 'dept_name'
		         },
		         usePager:true,width: '100%', height: '100%',checkbox: true,editable: true,
		         addRowByKey:true,
				 toolbar: {
		            items: [
		          	{ type: "button", label: '查询',icon:'search',listeners: [{ click: query}] },
		        	{ type: "button", label: '生成',icon:'plus',listeners: [{ click: generate}] },
					{ type: "button", label: '保存',icon:'disk',listeners: [{ click: save}] },
					{ type: "button", label: '删除',icon:'minus',listeners: [{ click: remove}] },
		         ]}
          });
    }
    
    function save() {
		var data = grid.getChanges();
    	
    	var ParamVo =[];

		if(data.updateList.length > 0){
    		if(data.updateList.length > 0){
        		var updateData = data.updateList ;
                $(updateData).each(function (){	
                	ParamVo.push(
               			this.year   +"@"+ 
       					this.subj_code  +"@"+ 
       					this.dept_id  +"@"+ 
       					(this.last_year_income? this.last_year_income:"0.00") +"@"+
       					(this.resolve_rate? this.resolve_rate:"0.00")  +"@"+ 
       					(this.grow_rate? this.grow_rate:"0.00")  +"@"+ 
       					(this.remark?this.remark:"-1") 
    				) 
    			});
        	}
			ajaxJsonObjectByUrl("updateResolveRateDYInBudgUp.do?isCheck=false", { ParamVo: ParamVo.toString() }, function (responseData) {
				if (responseData.state == "true") {
					query();
           		}
           	});
			
		}else{
    		$.ligerDialog.warn('没有需要保存的数据!');
    	}
	}
  //增量生成
    function generate(){
    	var year = year_input.getValue();
    	if(year){
    		ajaxPostData({
				url: "generateResolveRateDept.do?isCheck=false&year="+year,
				data: {},
				success: function (res){
					if (res.state == "true") {
						query();
					}
				}
			})
    	}else{
    		$.etDialog.error("预算年度不能为空");
    	}
    }
	
    function remove(){
    	var data = grid.selectGet();
        if (data.length == 0){
        	$.ligerDialog.error('请选择行');
        }else{
            var ParamVo =[];
            $(data).each(function (){					
				ParamVo.push(
					this.rowData.year   +"@"+ 
					this.rowData.subj_code  +"@"+ 
					this.rowData.dept_id +"@"+
					(this.rowData.last_year_income? this.rowData.last_year_income:"0.00") +"@"+
					(this.rowData.resolve_rate="" )+"@"+ 
					(this.rowData.grow_rate="" )+"@"+ 
					(this.rowData.remark="-1") 
				) 
			});
            $.ligerDialog.confirm('确定删除?', function (yes){
            	if(yes){
                	ajaxJsonObjectByUrl("updateResolveRateDYInBudgUp.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
                		if(responseData.state=="true"){
                			parent.query();
                			query();
                		}
                	});
            	}
            }); 
        }
    }
	
    //键盘事件
	  function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('S', save);
		hotkeys('D', remove);
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
					<select name=" " id="subj_level_select" style="width:180px;"></select>
				</td>
			</tr>
			<tr>
				<td class="label">科室名称：</td>
				<td class="ipt">
					<select name="" id="dept_name_select" style="width:180px;"></select>
				</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
		</table>
	</div>
	<div id="maingrid"></div>
	
</body>
</html>
