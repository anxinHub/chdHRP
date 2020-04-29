<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
        
		loadHotkeys();
    	
		query();
    	
    	toobar();
    	$("#layout1").ligerLayout({ leftWidth: 250,
    		heightDiff: -8,
    		//每展开左边树即刷新一次表格，以免结构混乱
    		onLeftToggle:function(){			
        		grid._onResize()
            },
        //每调整左边树宽度大小即刷新一次表格，以免结构混乱
        	onEndResize:function(a,b){    
				grid._onResize()
            }	 }); 
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	  grid.options.parms.push({name:'acc_year',value:$("#acc_year_month").val().substring(0,4)}); 
    	  grid.options.parms.push({name:'acc_month',value:$("#acc_year_month").val().substring(4,6)}); 
    	  grid.options.parms.push({name:'goal_code',value:liger.get("goal_code").getValue()}); 
    	  grid.options.parms.push({name:'hos_id',value:liger.get("hos_id").getValue()}); 
    	  grid.options.parms.push({name:'prem_data',value:"true"}); 
    	  grid.options.parms.push({name:'emp_id',value:liger.get("emp_no").getValue().split(".")[0]}); 
    	  grid.options.parms.push({name:'emp_no',value:liger.get("emp_no").getValue().split(".")[1] == null?'':liger.get("emp_no").getValue().split(".")[1]}); 
    	  //加载查询条件
    	  grid.loadData(grid.where);
    	  loadTree();
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
						{ display: '指标编码', name: 'kpi_code', align: 'left',width:80
						},
						{ display: '指标名称', name: 'kpi_name', align: 'left',width:200,
	                    	 render : function(rowdata, rowindex,
	    								value) {
	                    		 	var nbspNum = rowdata.kpi_level * 1;
	                    		 	var nbspStr = "";
	                    		 	for(var i = 1 ; i < nbspNum; i ++){
	                    		 		nbspStr += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
	                    		 	}
	                    		 	var str = nbspStr+rowdata.kpi_name;
	                    		 	if(str == null || str == 'undefined'){
	                    		 		str = "";
	                    		 	}
	   								return str;
	                    }},
						{ display: '指标性质', name: 'nature_name', align: 'center',width:80},
                     	{ display: '权重', name: 'ratio', align: 'right',width:80,editor: { type: 'float' ,
							precision : 4},
                     		render : function(rowdata, rowindex,
    								value) {
                     			if(value == 0){
                     				return '0';
                     			}else{
                     				return value;
                     			}
                     		}},
                     	{ display: '目标值', name: 'goal_value', align: 'right',width:80,editor: { type: 'int' },
	                     		render : function(rowdata, rowindex,
	    								value) {
	                     			var col = arguments[arguments.length -1];
	                     			if(rowdata.is_last == 0){
	                     				rowdata.notEidtColNames.push(col.columnname);
	                     				return '';
	                     			}
	                     			if(value == 0){
	                     				return '0';
	                     			}else{
	                     				return value;
	                     			}
	                     		}},
                     	{ display: '满分标准', name: 'full_score', align: 'right',width:80,editor: { type: 'float',precision : 2},
                     		render : function(rowdata, rowindex,
    								value) {
                     			var col = arguments[arguments.length -1];
                     			if(rowdata.is_last == 0){
                     				rowdata.notEidtColNames.push(col.columnname);
                     				return '';
                     			}
                     			if(value == 0){
                     				return '0';
                     			}else{
                     				return value;
                     			}
                     		}
							},
                     	{ display: '评分方法', name: 'grade_meth_code', textField: 'grade_meth_name',align: 'center',width:120,
                     		editor : {
								type : 'select',
								valueField: 'id', 
								textField: 'text',
								url : '../queryPrmGradePara.do?isCheck=false',
								keySupport:true,
						      	autocomplete: true
								},
	                     		render : function(rowdata, rowindex,
	    								value) {
	                     			var col = arguments[arguments.length -1];
	                     			if(rowdata.is_last == 0){
	                     				rowdata.notEidtColNames.push(col.columnname);
	                     			}
	                     			return rowdata.grade_meth_name;
	                     		}	
                     	},
						{ display: '评分标准', name: 'kpi', align: 'center',width:80,
                     		render : function(rowdata, rowindex,
    								value) {
                     			var col = arguments[arguments.length -1];
                     			if(rowdata.is_last == 0){
                     				rowdata.notEidtColNames.push(col.columnname);
                     				return '';
                     			}
                     			var str = JSON.stringify(rowdata).replace(new RegExp("'","gm"),"");
	   							return "<a href\"=javascript:void(0)\"  onclick='viewScoringEmpScheme("+str+")'>查看</a>";
	                    }},
                     	{ display: '取值方法', name: 'method_code', textField: 'method_name',align: 'left',width:120,
                     		editor : {
								type : 'select',
								valueField: 'id', 
								textField: 'text',
								url : '../queryPrmTargetMethodPara.do?isCheck=false',
								keySupport:true,
						      	autocomplete: true
								},
	                     		render : function(rowdata, rowindex,
	    								value) {
	                     			var col = arguments[arguments.length -1];
	                     			if(rowdata.is_last == 0){
	                     				rowdata.notEidtColNames.push(col.columnname);
	                     			}
	                     			return rowdata.method_name;
	                     		}		
                     	},
                    	{ display: '取值公式', name: 'formula_method_chs', align: 'left',width:480},
                     	{ display: '取值函数', name: 'fun_method_chs', align: 'left',width:300},
                     	{ display: '指示灯', name: 'fun_code', align: 'center',width:80,
                     		render : function(rowdata, rowindex,
    								value) {
                     			var str = JSON.stringify(rowdata).replace(new RegExp("'","gm"),"");
                     			return "<a href\"=javascript:void(0)\" onclick='viewLedEmpScheme("+str+")'>查看</a>";
                     		}
                     	}],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryPrmEmpScheme.do',
                     width: '100%', height: '87%', checkbox: true,rownumbers:true,enabledEdit : true,delayLoad:true,
                     selectRowButtonOnly:true,isSingleCheck:false
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    function toobar(){
    	var obj = [];
    	
    	obj.push({ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' });
    	obj.push({ line:true });
    	
    	obj.push({ text: '生成（<u>C</u>）', id:'create', click: createEmpScheme, icon:'add' });
    	obj.push({ line:true });
    	
    	obj.push({ text: '删除（<u>D</u>）', id:'delete', click: deleteEmpScheme,icon:'delete' });
    	obj.push({ line:true });
    	
    	obj.push({ text: '保存（<u>S</u>）', id:'save', click: saveEmpScheme,icon:'add' });
    	obj.push({ line:true });
    	
    	obj.push({ text: '评分方法设定（<u>B</u>）', id:'scoring', click: scoringEmpScheme,icon:'config' });
    	obj.push({ line:true });
    	
    	obj.push({ text: '取值方法设定（<u>F</u>）', id:'Method', click: MethodEmpScheme,icon:'settings' });
    	obj.push({ line:true });
    	if('${sessionScope.prm_para_map["0702"]}' == 1){
    		obj.push({ text: '指示灯设定（<u>G</u>）', id:'led', click: ledEmpScheme,icon:'settings' });
    		obj.push({ line:true });
    	}
		obj.push({ text: '引入（<u>O</u>）', id:'introduce', click: introduceEmpScheme,icon:'back' });
		obj.push({ line:true });
    	
    	//obj.push({ text: '打印（<u>P</u>）', id:'print', click: printDate,icon:'print' });
    	//obj.push({ line:true });
    	
    	$("#toptoolmod").ligerToolBar({ items: obj});
    }
	function saveEmpScheme() {
		gridManager.endEdit();
		var kpi_data = gridManager.rows;
		var validate_buf = "";
		if(kpi_data.length > 0){
			$.each(kpi_data, function(k_index, k_content){ 
			  if (typeof (k_content.kpi_code) == "undefined"
					|| k_content.kpi_code == "") {
					grid.deleteRow(k_content);//删除选择的行
					return true;
			  }
              if (k_content.ratio < 0) {
				validate_buf = "维护权重不能小于0";
			  }
              if (k_content.ratio > 1) {
  				validate_buf = "维护权重不能大于1";
  			  }
			}); 
		}

		if(validate_buf.toString()  != ""){
			$.ligerDialog.warn(validate_buf.toString());
			return false;
		}
		
		var formPara = {
				kpi_data : JSON.stringify(kpi_data)
				};

		ajaxJsonObjectByUrl("savePrmEmpScheme.do", formPara, function(responseData) {	tree.selectNode(id);});
	}
	
	
	function viewLedEmpScheme(rowdata){
		gridManager.endEdit();
		ledEmpScheme(rowdata.group_id, rowdata.hos_id ,rowdata.copy_code,
				rowdata.acc_year,
				rowdata.acc_month ,
				rowdata.nature_name,
				rowdata.goal_code ,
				 rowdata.emp_no,
				 rowdata.emp_id,
				 rowdata.kpi_code ,
				 rowdata.kpi_name,
				 rowdata.ratio ,
				 rowdata.goal_value,
				 rowdata.full_score);
	}
	
	function ledEmpScheme(group_id, hos_id ,copy_code,
			acc_year,
			acc_month ,
			nature_name,
			goal_code ,
			 emp_no,
			 emp_id,
			 kpi_code ,
			 kpi_name,
			 ratio ,
			 goal_value,
			 full_score){
		gridManager.endEdit();
    	var checkData = gridManager.getCheckedRows();
    	var data = gridManager.getSelectedRow();
    	if(hos_id == null || hos_id == ''){
    		if(checkData.length > 1){
        		$.ligerDialog.warn('请选择单行操作');
    			return false;
        	}
    		if (data == null) {
    			$.ligerDialog.warn('请选择行');
    			return false;
    		}
    	}
		
		var parm = "";
		
		if(hos_id == null){
			parm = "group_id=" + data.group_id + "&" + "hos_id="
			+ data.hos_id + "&" + "copy_code=" + data.copy_code
			+ "&" + "acc_year=" + data.acc_year + "&" + "acc_month="
			+ data.acc_month + "&" + "nature_name="
			+ data.nature_name + "&" + "goal_code="
			+ data.goal_code + "&" + "emp_no="
			+ data.emp_no +  "&"+ "emp_id="
			+ data.emp_id +  "&" + "kpi_code="
			+ data.kpi_code + "&" + "kpi_name=" + data.kpi_name
			+ "&" + "ratio=" + data.ratio + "&" + "goal_value="
			+ data.goal_value+"&full_score="+data.full_score;
		}else{
			parm = "group_id=" + group_id + "&" + "hos_id="
			+ hos_id + "&" + "copy_code=" + copy_code
			+ "&" + "acc_year=" + acc_year + "&" + "acc_month="
			+ acc_month + "&" + "nature_name="
			+ nature_name + "&" + "goal_code="
			+ goal_code + "&" + "emp_no="
			+ emp_no +  "&"+ "emp_id="
			+ emp_id +  "&" + "kpi_code="
			+ kpi_code + "&" + "kpi_name=" + kpi_name
			+ "&" + "ratio=" + ratio + "&" + "goal_value="
			+ goal_value+"&full_score="+full_score;
		}
		
			
			$.ligerDialog.open({
				url : 'prmEmpSchemeKpiLed.do?isCheck=false&' + parm,
				height : 500,
				width : 800,
				title : '指示灯设定',
				modal: true, showToggle: false, showMax: false, showMin: false, isResize: true,
				parentframename: window.name,
				data : {},
				buttons : [ {
					text : '确定',
					onclick : function(item, dialog) {
						dialog.frame.saveSchemeSection();
					},
					cls : 'l-dialog-btn-highlight'
				}, {
					text : '取消',
					onclick : function(item, dialog) {
						dialog.close();
					}
				} ]
			});
	}
	
	
	function createEmpScheme(){
		$.ligerDialog.open({url: 'prmEmpSchemeCreate.do?isCheck=false', height: 230,width: 400, title:'生成',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,
			data: {
				acc_year :$("#acc_year_month").val().substring(0,4),
				acc_month:$("#acc_year_month").val().substring(4,6),
				goal_code:liger.get("goal_code").getValue(),
				hos_id:liger.get("hos_id").getValue(),
				emp_id:liger.get("emp_no").getValue().split(".")[0],
				emp_no:liger.get("emp_no").getValue().split(".")[1] == null?'':liger.get("emp_no").getValue().split(".")[1]
			},buttons: [ 
			           { text: '确定', onclick: function (item, dialog) { dialog.frame.createHosScheme(); },cls:'l-dialog-btn-highlight' }, 
			           { text: '关闭', onclick: function (item, dialog) { dialog.close(); } } ] });
	}
	
	
    function deleteEmpScheme(){
    	var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.warn('请选择行');
        }else{
            var ParamVo =[];
            $(data).each(function (){	
            	
            	if(isnull(this.group_id)){
					gridManager.deleteSelectedRow();
				}else{
            	
            	
													ParamVo.push(
													this.group_id   +"@"+ 
													this.hos_id   +"@"+ 
													this.copy_code   +"@"+ 
													this.acc_year   +"@"+ 
													this.acc_month   +"@"+ 
													this.goal_code   +"@"+ 
													this.kpi_code   +"@"+ 
													this.emp_no   +"@"+ 
													this.emp_id 
													);
				}});
            if(ParamVo == ""){
				return;
			}
            $.ligerDialog.confirm('确定删除?', function (yes){
            	if(yes){
                	ajaxJsonObjectByUrl("deletePrmEmpScheme.do",{ParamVo : ParamVo.toString()},function (responseData){
                		if(responseData.state=="true"){
                			tree.selectNode(id);
                		}
                	});
            	}
            }); 
        }
    }
    
    function viewScoringEmpScheme(rowdata){
		gridManager.endEdit();
		scoringEmpScheme(rowdata.group_id, rowdata.hos_id ,rowdata.copy_code,
				rowdata.acc_year,
				rowdata.acc_month ,
				rowdata.nature_name,
				rowdata.goal_code ,
				 rowdata.emp_no,
				 rowdata.emp_id,
				 rowdata.kpi_code ,
				 rowdata.kpi_name,
				 rowdata.ratio ,
				 rowdata.goal_value,
				 rowdata.grade_meth_code,
				 rowdata.is_last,
				 rowdata.full_score);
	}
    
    function scoringEmpScheme(group_id, hos_id ,copy_code,
			acc_year,
			acc_month ,
			nature_name,
			goal_code ,
			emp_no,
			emp_id,
			 kpi_code ,
			 kpi_name,
			 ratio ,
			 goal_value,
			 grade_meth_code,
			 is_last,
			 full_score){
    	
    	gridManager.endEdit();
    	
    	var checkData = gridManager.getCheckedRows();
    	var data = gridManager.getSelectedRow();
    	if(hos_id == null || hos_id == ''){
    		if(checkData.length > 1){
        		$.ligerDialog.warn('请选择单行操作');
    			return false;
        	}
    		if (data == null) {
    			$.ligerDialog.warn('请选择行');
    			return false;
    		}
    		if (data.grade_meth_code == null) {
    			$.ligerDialog.warn('请选择行所对应的评分设定方法');
    			return false;
    		}
    		if (data.is_last == 0) {
    			$.ligerDialog.warn('父级指标不能设定评分方法');
    			return false;
    		}
    	}else{
    		if (grade_meth_code == null) {
    			$.ligerDialog.warn('请选择行所对应的评分设定方法');
    			return false;
    		}
    	}
		
		var parm = "";
		var grade_meth  = '';
		if(hos_id == null){
			parm = "group_id=" + data.group_id + "&" + "hos_id="
			+ data.hos_id + "&" + "copy_code=" + data.copy_code
			+ "&" + "acc_year=" + data.acc_year + "&" + "acc_month="
			+ data.acc_month + "&" + "nature_name="
			+ data.nature_name + "&" + "goal_code="
			+ data.goal_code + "&" + "emp_no="
			+ data.emp_no +  "&"+ "emp_id="
			+ data.emp_id +  "&" + "kpi_code="
			+ data.kpi_code + "&" + "kpi_name=" + data.kpi_name
			+ "&" + "ratio=" + data.ratio + "&" + "goal_value="
			+ data.goal_value+"&full_score="+data.full_score;
			grade_meth = data.grade_meth_code;
		}else{
			parm = "group_id=" + group_id + "&" + "hos_id="
			+ hos_id + "&" + "copy_code=" + copy_code
			+ "&" + "acc_year=" + acc_year + "&" + "acc_month="
			+ acc_month + "&" + "nature_name="
			+ nature_name + "&" + "goal_code="
			+ goal_code + "&" + "emp_no="
			+ emp_no +  "&"+ "emp_id="
			+ emp_id +  "&" + "kpi_code="
			+ kpi_code + "&" + "kpi_name=" + kpi_name
			+ "&" + "ratio=" + ratio + "&" + "goal_value="
			+ goal_value+"&full_score="+full_score;
			grade_meth = grade_meth_code
		}
		
		if (grade_meth == '01') {
			
			$.ligerDialog.open({
				url : 'prmEmpSchemeSection.do?isCheck=false&' + parm,
				height : 500,
				width : 800,
				title : '区间法评分标准设定',
				modal: true, showToggle: false, showMax: false, showMin: false, isResize: true,
				parentframename: window.name,
				data : {},
				buttons : [ {
					text : '确定',
					onclick : function(item, dialog) {
						dialog.frame.saveSchemeSection();
					},
					cls : 'l-dialog-btn-highlight'
				}, {
					text : '取消',
					onclick : function(item, dialog) {
						dialog.close();
					}
				} ]
			});
		}

		if (grade_meth == '02') {

			$.ligerDialog.open({
				url : 'prmEmpSchemesAd.do?isCheck=false' + parm,
				height : 500,
				width : 800,
				title : '加分法评分标准设定',
				modal: true, showToggle: false, showMax: false, showMin: false, isResize: true,
				parentframename: window.name,
				data : {
					acc_year :$("#acc_year_month").val().substring(0,4),
					acc_month:$("#acc_year_month").val().substring(4,6),
					goal_code : liger.get("goal_code").getValue(),
					hos_id : liger.get("hos_id").getValue(),
					emp_no : liger.get("emp_no").getValue().split(".")[1],
					emp_id : liger.get("emp_no").getValue().split(".")[0]
				},
				buttons : [ {
					text : '确定',
					onclick : function(item, dialog) {
						dialog.frame.createEmpScheme();
					},
					cls : 'l-dialog-btn-highlight'
				}, {
					text : '取消',
					onclick : function(item, dialog) {
						dialog.close();
					}
				} ]
			});
		}
		
		if (grade_meth == '03') {

			$.ligerDialog.open({
				url : 'prmEmpSchemesAd.do?isCheck=false' + parm,
				height : 500,
				width : 800,
				title : '扣分法评分标准设定',
				modal: true, showToggle: false, showMax: false, showMin: false, isResize: true,
				parentframename: window.name,
				data : {
					acc_year :$("#acc_year_month").val().substring(0,4),
					acc_month:$("#acc_year_month").val().substring(4,6),
					goal_code : liger.get("goal_code").getValue(),
					hos_id : liger.get("hos_id").getValue(),
					emp_no : liger.get("emp_no").getValue().split(".")[1],
					emp_id : liger.get("emp_no").getValue().split(".")[0]
				},
				buttons : [ {
					text : '确定',
					onclick : function(item, dialog) {
						dialog.frame.createEmpScheme();
					},
					cls : 'l-dialog-btn-highlight'
				}, {
					text : '取消',
					onclick : function(item, dialog) {
						dialog.close();
					}
				} ]
			});
		}
    }
    
    function MethodEmpScheme() {
    	gridManager.endEdit();
		var data = grid.getCheckedRows();

		if (data.length == 0) {
			$.ligerDialog.warn('请选择行');
			return false;
		}

		if (data.length > 1) {
			$.ligerDialog.warn('取值方法设定只能选择单行');
			return false;
		}

		if (data[0].method_code == null) {
			$.ligerDialog.warn('请选择行所对应的取值设定方法');
			return false;
		}
		if (data[0].method_code == '01') {

		}

		if (data[0].method_code == '02' || data[0].method_code == '03') {
			
			var parm = "group_id=" + data[0].group_id + "&" + "hos_id="
			+ data[0].hos_id + "&" + "copy_code=" + data[0].copy_code
			+ "&" + "acc_year=" + data[0].acc_year + "&" + "acc_month="
			+ data[0].acc_month + "&" + "nature_name="
			+ data[0].nature_name + "&" + "goal_code="
			+ data[0].goal_code + "&" + "emp_no="
			+ data[0].emp_no +  "&" + "emp_id="
			+ data[0].emp_id + "&" + "kpi_code="
			+ data[0].kpi_code + "&" + "kpi_name=" + data[0].kpi_name + "&fun_code=" + data[0].fun_code + "&fun_name="+data[0].fun_name + "&fun_method_chs="+data[0].fun_method_chs
			+ "&" + "ratio=" + data[0].ratio + "&" + "goal_value="
			+ data[0].goal_value+"&method_code="+data[0].method_code+"&method_name="+data[0].method_name+"&full_score="+data[0].full_score
			+ "&formula_code="+data[0].formula_code+"&formula_name="+data[0].formula_name + "&formula_method_chs="+data[0].formula_method_chs;
			parent.$.ligerDialog.open({
				url : 'hrp/prm/prmempscheme/prmFormulaMethodEmpScheme.do?isCheck=false&'+ parm,
				height: '500',
				width: '800',
				title : '计算方式设定',
				modal: true, showToggle: false, showMax: false, showMin: false, isResize: true,
				parentframename: window.name,
			    buttons : [ {
					text : '确定',
					onclick : function(item, dialog) {
						dialog.frame.saveEmpMethod();
					},
					cls : 'l-dialog-btn-highlight'
				}, {
					text : '取消',
					onclick : function(item, dialog) {
						dialog.close();
					}
				} ]
			})
		}

	}
    
    function introduceEmpScheme(){
    	if(liger.get("hos_id").getValue() == ""){
   			$.ligerDialog.warn('请选择单位');
			return;
   		}
    	
    	if(liger.get("goal_code").getValue() == ""){
   			$.ligerDialog.warn('请选择目标');
			return;
   		}
    	
   		if(liger.get("emp_no").getValue() == "" || liger.get("emp_no").getValue() == "."){
   			$.ligerDialog.warn('请选择考核单元');
			return;
   		}
    	
    	var parm = "&acc_month="
    		+ $("#acc_month").val() + "&"+ "emp_no="
    		+ liger.get("emp_no").getValue().split(".")[1] + "&"+ "emp_id="
            + liger.get("emp_no").getValue().split(".")[0] + "&" + "hos_id="
            + liger.get("hos_id").getValue()+"&goal_code="+liger.get("goal_code").getValue()+"&"+"acc_year="
            + $("#acc_year_month").val().substring(0,4);
    		$.ligerDialog.open({
    			url : 'introduceEmpScheme.do?isCheck=false'+ parm,
    			height : 300,
    			width : 770,
    			title : '引入',
    			modal: true, showToggle: false, showMax: false, showMin: false, isResize: true,
				parentframename: window.name,
    		    buttons : [{
    				text : '确定',
    				onclick : function(item, dialog) {
    					dialog.frame.saveEmpIntroduce();
    				},
    				cls : 'l-dialog-btn-highlight'
    			}, {
    				text : '取消',
    				onclick : function(item, dialog) {
    					dialog.close();
    				}
    			} ]
    		});
    }
    function loadDict(){
            //字典下拉框
    	//loadComboBox({id:"#goal_code",url:"../quertPrmGoalDict.do?isCheck=false&prem_data=true",value:"id",text:"text",autocomplete:true,hightLight:true,initValue:true,selectBoxWidth:'auto',maxWidth:'260',defaultSelect:true,async:false});
    	//autocompleteAsync("#hos_id","../quertSysHosInfoDict.do?isCheck=false","id","text",true,true,"",true);
    	//autocompleteAsync("#emp_no","../queryHosEmpDict.do?isCheck=false","id","text",true,true,"",true,null);
    	
    	autocomplete("#goal_code","../quertPrmGoalDict.do?isCheck=false","id","text",true,true,"",true,"",220,"",220);
    	autocomplete("#hos_id","../quertSysHosInfoDict.do?isCheck=false","id","text",true,true,"",true,"",220,"",220);
    	autocomplete("#emp_no","../queryHosEmpDict.do?isCheck=false","id","text",true,true,"",true,"",220,"",220);
    	
    	$("#acc_year_month").ligerTextBox({width : 160});
    	//$("#acc_month").ligerTextBox({width : 160});
    	autodate("#acc_year_month","yyyyMM");
    	
	}  
    
  //打印数据
	 function printDate(){
		//有数据直接打印
		if($("#resultPrint > table > tbody").html()!=""){
			lodopPrinterTable("resultPrint","开始打印","医院绩效方案表集",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备打印数据,请稍候...');
		
		var printPara={
				
			usePager:false,
			
           acc_year:$("#acc_year_month").val().substring(0,4),
            
           acc_month:$("#acc_year_month").val().substring(4,6),
            
           goal_code:liger.get("goal_code").getValue(),
            
           hos_id :liger.get("hos_id").getValue(),
           
           emp_no:liger.get("emp_no").getValue().split(".")[1],
           
           emp_id:liger.get("emp_no").getValue().split(".")[0],
        
            
         };
		
		ajaxJsonObjectByUrl("queryPrmEmpScheme.do",printPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
					 trHtml+="<td>"+item.kpi_code+"</td>"; 
					 trHtml+="<td>"+item.kpi_name+"</td>"; 
					 trHtml+="<td>"+item.nature_name+"</td>"; 
					 trHtml+="<td>"+item.ratio+"</td>"; 
					 trHtml+="<td>"+item.goal_value+"</td>"; 
					 trHtml+="<td>"+item.grade_meth_code+"</td>"; 
					 trHtml+="<td>"+item.method_code+"</td>"; 
					 trHtml+="<td>"+item.formula_code+"</td>"; 
					 trHtml+="<td>"+item.fun_code+"</td>"; 
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			//alert($("#resultPrint").html())
			lodopPrinterTable("resultPrint","开始打印","医院绩效方案表集",true);
	    },true,manager);
		return;
	 }
	
	 
	 //导出数据
	 function exportExcel(){
		//有数据直接导出
		if($("#resultPrint > table > tbody").html()!=""){
			lodopExportExcel("resultPrint","导出Excel","医院绩效方案表集.xls",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');
		
		var printPara={
				
			usePager:false,

				acc_year :$("#acc_year_month").val().substring(0,4),
				acc_month:$("#acc_year_month").val().substring(4,6),
	            
	           goal_code:liger.get("goal_code").getValue(),
	            
	           hos_id :liger.get("hos_id").getValue(),
	           
	           emp_no:liger.get("emp_no").getValue().split(".")[1],
	           
	           emp_id:liger.get("emp_no").getValue().split(".")[0],
            
            
         };
		ajaxJsonObjectByUrl("queryPrmHosScheme.do",exportPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
				 trHtml+="<td>"+item.kpi_code+"</td>"; 
				 trHtml+="<td>"+item.kpi_name+"</td>"; 
				 trHtml+="<td>"+item.nature_name+"</td>"; 
				 trHtml+="<td>"+item.ratio+"</td>"; 
				 trHtml+="<td>"+item.goal_value+"</td>"; 
				 trHtml+="<td>"+item.grade_meth_code+"</td>"; 
				 trHtml+="<td>"+item.method_code+"</td>"; 
				 trHtml+="<td>"+item.formula_code+"</td>"; 
				 trHtml+="<td>"+item.fun_code+"</td>"; 
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			lodopExportExcel("resultPrint","导出Excel","医院绩效方案表集.xls",true);
	    },true,manager);
		return;
	 }		  
	 
	 
	 
	 //键盘事件
		function loadHotkeys(){

			hotkeys('Q',query);
			hotkeys('C',createEmpScheme);
			hotkeys('D',deleteEmpScheme);
			hotkeys('S',saveEmpScheme);
			hotkeys('B',scoringEmpScheme);
			hotkeys('F',MethodEmpScheme);
			hotkeys('O',introduceEmpScheme);
			hotkeys('P',printDate);
		}
	 
	 
	 
	 /* 设置树形菜单 */
     
     function onSelect(note){
         grid.options.parms=[];
         grid.options.newPage=1;
         
         id = note.data.id;
         pid = note.data.pid;
         grid.options.parms.push({name:'acc_year',value:$("#acc_year_month").val().substring(0,4)}); 
   	     grid.options.parms.push({name:'acc_month',value:$("#acc_year_month").val().substring(4,6)}); 
	   	 grid.options.parms.push({name:'goal_code',value:liger.get("goal_code").getValue()}); 
	   	 grid.options.parms.push({name:'hos_id',value:liger.get("hos_id").getValue()}); 
	   	 grid.options.parms.push({name:'emp_id',value:liger.get("emp_no").getValue().split(".")[0]}); 
	   	 grid.options.parms.push({name:'emp_no',value:liger.get("emp_no").getValue().split(".")[1] == null?'':liger.get("emp_no").getValue().split(".")[1]});
         if(pid != -1){
           grid.options.parms.push({name:'kpi_code',value:id});
      	   grid.options.parms.push({name:'super_kpi_code',value:pid});
         }else{
      	   grid.options.parms.push({name:'goal_code',value:id.replace("goal","")});
         }
     	 //加载查询条件
     	 grid.loadData(grid.where);
     }

     
      function loadTree(){
    	  
    	  var param = {

    			  acc_year :$("#acc_year_month").val().substring(0,4),
    			  acc_month:$("#acc_year_month").val().substring(4,6),
    			  goal_code:liger.get("goal_code").getValue(),
    			  hos_id:liger.get("hos_id").getValue(),
    			  emp_id:liger.get("emp_no").getValue().split(".")[0],
    			  emp_no:liger.get("emp_no").getValue().split(".")[1] == null?'':liger.get("emp_no").getValue().split(".")[1]
    			  };
		ajaxJsonObjectByUrl("queryEmpSchemeTree.do?isCheck=false", param,
		
			function(responseData) {
			
					if (responseData != null) {

						tree = $("#tree").ligerTree({
							
							data : responseData.Rows,
							
							checkbox : false,
							
							idFieldName : 'id',
							
							parentIDFieldName : 'pid',
							
							onSelect: onSelect,
							
							isExpand: 3,
							
							nodeWidth:400

						});
						
						treeManager = $("#tree").ligerGetTreeManager();
						
						treeManager.collapseAll();
					}
					
				});
	}
    </script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar">
	   <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	 	  <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">单位信息：</td>
            <td align="left" class="l-table-edit-td"><input name="hos_id"" type="text" id="hos_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">目标编码：</td>
            <td align="left" class="l-table-edit-td"><input name="goal_code" type="text" id="goal_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">考核单元：</td>
            <td align="left" class="l-table-edit-td"><input name="emp_no" type="text" id="emp_no" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
	
        <tr>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">考核期间：</td>
            <td align="left" class="l-table-edit-td"><input name="acc_year_month" type="text" id="acc_year_month"  ltype="text" class="Wdate" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyyMM'})"/></td>
            <td align="left"></td>
        </tr> 
    </table>
	</div>
   	<div id="toptoolmod"></div>
   	
   	
   	<div id="layout1" style="height: 100%;">
            <div  position="left" style="left: 0px; top: 0px;  height: 99%;">
            	<div class="l-layout-content" position="left" style="height:100%;overflow: auto;">
					<div class="ztree" style="float: left">
						<ul id="tree"></ul>
					</div>
				</div>
            </div>
            <div position="center" style="left:width: 975px; height: 100%;">
            	<div title="" class="l-layout-content" style="height: 100%;"
				position="center">
				<div id="maingrid"></div>
			</div>
            </div>
        </div> 

	<div id="resultPrint" style="display:none">
	   	<table width="100%">
			   	<thead>
				<tr>
                <th width="200">指标编码</th>	
                <th width="200">指标名称</th>	
                <th width="200">指标性质</th>	
                <th width="200">权重</th>	
                <th width="200">目标值</th>	
                <th width="200">评分方法</th>	
                <th width="200">取值方法</th>	
                <th width="200">取值公式</th>	  
                <th width="200">取值函数</th>	         
				   	</tr>
			   	</thead>
			   	<tbody></tbody>
	   	</table>
   	</div>
</body>
</html>
