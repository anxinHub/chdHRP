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
            }	
 }); 
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
    	  grid.options.parms.push({name:'dept_id',value:liger.get("dept_no").getValue().split(".")[0]}); 
    	  grid.options.parms.push({name:'dept_no',value:liger.get("dept_no").getValue().split(".")[1] == null?'':liger.get("dept_no").getValue().split(".")[1]}); 
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
	   							return "<a href\"=javascript:void(0)\"  onclick='viewScoringDeptScheme("+str+")'>查看</a>";
	                    }},
                     	{ display: '取值方法', name: 'method_code', textField: 'method_name',align: 'left',width:120,
	                     		render : function(rowdata, rowindex,
	    								value) {
	                     			var str = JSON.stringify(rowdata).replace(new RegExp("'","gm"),"");
		   							return "<a href\"=javascript:void(0)\"  onclick='MethodDeptScheme("+str+")'>"+rowdata.method_name+"</a>";
	                     		}		
                     	},
                    	{ display: '取值公式', name: 'formula_method_chs', align: 'left',width:480},
                     	{ display: '取值函数', name: 'fun_method_chs', align: 'left',width:300},
                     	{ display: '指示灯', name: 'fun_code', align: 'center',width:80,
                     		render : function(rowdata, rowindex,
    								value) {
                     			var str = JSON.stringify(rowdata).replace(new RegExp("'","gm"),"");
                     			return "<a href\"=javascript:void(0)\" onclick='viewLedDeptScheme("+str+")'>查看</a>";
                     		}
                     	}],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryPrmDeptSchemeAudit.do',
                     width: '100%', height: '87%', checkbox: true,rownumbers:true,enabledEdit : false,delayLoad:true,
                     selectRowButtonOnly:true,isSingleCheck:false
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    function toobar(){
    	var obj = [];
    	
    	obj.push({ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' });
    	obj.push({ line:true });
    	
    	obj.push({ text : '审核（<u>A</u>）',id : 'audit',click : auditPrmDeptScheme,icon : 'audit'});
    	obj.push({ line:true });
    	
    	obj.push({ text : '反审核（<u>B</u>）',id : 'reAudit',click : reAuditPrmDeptScheme,icon : 'back'});
    	obj.push({ line:true });
    	
    	$("#toptoolmod").ligerToolBar({ items: obj});
    }
	
	
	function viewLedDeptScheme(rowdata){
		gridManager.endEdit();
		ledDeptScheme(rowdata.group_id, rowdata.hos_id ,rowdata.copy_code,
				rowdata.acc_year,
				rowdata.acc_month ,
				rowdata.nature_name,
				rowdata.goal_code ,
				 rowdata.dept_no,
				 rowdata.dept_id,
				 rowdata.kpi_code ,
				 rowdata.kpi_name,
				 rowdata.ratio ,
				 rowdata.goal_value,
				 rowdata.full_score);
	}
	
	function ledDeptScheme(group_id, hos_id ,copy_code,
			acc_year,
			acc_month ,
			nature_name,
			goal_code ,
			 dept_no,
			 dept_id,
			 kpi_code ,
			 kpi_name,
			 ratio ,
			 goal_value,
			 full_score){
		
		var parm = "group_id=" + group_id + "&" + "hos_id="
			+ hos_id + "&" + "copy_code=" + copy_code
			+ "&" + "acc_year=" + acc_year + "&" + "acc_month="
			+ acc_month + "&" + "nature_name="
			+ nature_name + "&" + "goal_code="
			+ goal_code + "&" + "dept_no="
			+ dept_no +  "&"+ "dept_id="
			+ dept_id +  "&" + "kpi_code="
			+ kpi_code + "&" + "kpi_name=" + kpi_name
			+ "&" + "ratio=" + ratio + "&" + "goal_value="
			+ goal_value+"&full_score="+full_score;
		
			
			$.ligerDialog.open({
				url : 'prmDeptSchemeKpiLedAudit.do?isCheck=false&' + parm,
				height : 500,
				width : 800,
				title : '指示灯设定',
				modal: true, showToggle: false, showMax: false, showMin: false, isResize: true,
				parentframename: window.name,
				buttons : [{
					text : '关闭',
					onclick : function(item, dialog) {
						dialog.close();
					}
				} ]
			});
	}
	
    
    function viewScoringDeptScheme(rowdata){
		gridManager.endEdit();
		scoringDeptScheme(rowdata.group_id, rowdata.hos_id ,rowdata.copy_code,
				rowdata.acc_year,
				rowdata.acc_month ,
				rowdata.nature_name,
				rowdata.goal_code ,
				 rowdata.dept_no,
				 rowdata.dept_id,
				 rowdata.kpi_code ,
				 rowdata.kpi_name,
				 rowdata.ratio ,
				 rowdata.goal_value,
				 rowdata.grade_meth_code,
				 rowdata.is_last,
				 rowdata.full_score);
	}
    
    function scoringDeptScheme(group_id, hos_id ,copy_code,
			acc_year,
			acc_month ,
			nature_name,
			goal_code ,
			 dept_no,
			 dept_id,
			 kpi_code ,
			 kpi_name,
			 ratio ,
			 goal_value,
			 grade_meth_code,
			 is_last,
			 full_score){
    	
		
		var parm = "";
		var grade_meth  = '';
		parm = "group_id=" + group_id + "&" + "hos_id="
			+ hos_id + "&" + "copy_code=" + copy_code
			+ "&" + "acc_year=" + acc_year + "&" + "acc_month="
			+ acc_month + "&" + "nature_name="
			+ nature_name + "&" + "goal_code="
			+ goal_code + "&" + "dept_no="
			+ dept_no +  "&"+ "dept_id="
			+ dept_id +  "&" + "kpi_code="
			+ kpi_code + "&" + "kpi_name=" + kpi_name
			+ "&" + "ratio=" + ratio + "&" + "goal_value="
			+ goal_value+"&full_score="+full_score;
		grade_meth = grade_meth_code
		
		if (grade_meth == '01') {
			
			$.ligerDialog.open({
				url : 'prmDeptSchemeSectionAudit.do?isCheck=false&' + parm,
				height : 500,
				width : 800,
				title : '区间法评分标准设定',
				modal: true, showToggle: false, showMax: false, showMin: false, isResize: true,
				parentframename: window.name,
				buttons : [ {
					text : '关闭',
					onclick : function(item, dialog) {
						dialog.close();
					}
				} ]
			});
		}

		if (grade_meth == '02' || grade_meth == '03') {

			$.ligerDialog.open({
				url : 'prmDeptSchemesAdAudit.do?isCheck=false' + parm,
				height : 500,
				width : 800,
				title : '加扣分法评分标准设定',
				modal: true, showToggle: false, showMax: false, showMin: false, isResize: true,
				parentframename: window.name,
				data : {
					acc_year : $("#acc_year").val(),
					acc_month : $("#acc_month").val(),
					goal_code : liger.get("goal_code").getValue(),
					hos_id : liger.get("hos_id").getValue(),
					dept_no : liger.get("dept_no").getValue().split(".")[1],
				    dept_id : liger.get("dept_no").getValue().split(".")[0]
				},
				buttons : [ {
					text : '关闭',
					onclick : function(item, dialog) {
						dialog.close();
					}
				} ]
			});
		}
    }
    
    function MethodDeptScheme(rowdata) {
		
		if (rowdata.method_code == '01') {

		}

		if (rowdata.method_code == '02' || rowdata.method_code == '03') {
			
			var parm = "group_id=" + rowdata.group_id + "&" + "hos_id="
			+ rowdata.hos_id + "&" + "copy_code=" + rowdata.copy_code
			+ "&" + "acc_year=" + rowdata.acc_year + "&" + "acc_month="
			+ rowdata.acc_month + "&" + "nature_name="
			+ rowdata.nature_name + "&" + "goal_code="
			+ rowdata.goal_code + "&" + "dept_no="
			+ rowdata.dept_no +  "&" + "dept_id="
			+ rowdata.dept_id + "&" + "kpi_code="
			+ rowdata.kpi_code + "&" + "kpi_name=" + rowdata.kpi_name + "&fun_code=" + rowdata.fun_code + "&fun_name="+rowdata.fun_name + "&fun_method_chs="+rowdata.fun_method_chs
			+ "&" + "ratio=" + rowdata.ratio + "&" + "goal_value="
			+ rowdata.goal_value+"&method_code="+rowdata.method_code+"&method_name="+rowdata.method_name+"&full_score="+rowdata.full_score
			+ "&formula_code="+rowdata.formula_code+"&formula_name="+rowdata.formula_name + "&formula_method_chs="+rowdata.formula_method_chs;
			parent.$.ligerDialog.open({
				url : 'hrp/prm/prmdeptschemeaudit/prmFormulaMethodDeptSchemeAudit.do?isCheck=false&'+ parm,
				height: '500',
				width: '800',
				title : '计算方式设定',
				modal: true, showToggle: false, showMax: false, showMin: false, isResize: true,
				parentframename: window.name,
			    buttons : [ {
					text : '关闭',
					onclick : function(item, dialog) {
						dialog.close();
					}
				} ]
			})
		}

	}
    
   
    function loadDict(){
            //字典下拉框
            
            
    	loadComboBox({id:"#goal_code",url:"../quertPrmGoalDict.do?isCheck=false&prem_data=true",value:"id",text:"text",autocomplete:true,hightLight:true,initValue:true,selectBoxWidth:'auto',maxWidth:'260',defaultSelect:true,async:false});
        
    	autocompleteAsync("#hos_id","../quertSysHosInfoDict.do?isCheck=false","id","text",true,true,"",true);
    	
    	autocompleteAsync("#dept_no","../queryPrmDeptDict.do?isCheck=false","id","text",true,true,"",true,null);
    	
    	$("#acc_year_month").ligerTextBox({width : 160});
    	
    	autodate("#acc_year_month","yyyyMM");
    	
	}  
    
    function auditPrmDeptScheme(){
		var data = gridManager.getCheckedRows();
		var ParamVo = [];

					ParamVo.push($("#acc_year_month").val().substring(0,4) + "@" + $("#acc_year_month").val().substring(4,6) + "@"
							+ liger.get("hos_id").getValue() + "@" + liger.get("goal_code").getValue())
		$.ligerDialog.confirm('确定审核?', function(yes) {
			if (yes) {
				ajaxJsonObjectByUrl("auditPrmDeptScheme.do", {
					ParamVo : ParamVo.toString()
				}, function(responseData) {
					if (responseData.state == "true") {
						query();
					}
				});
			}
		});
	}
	function reAuditPrmDeptScheme(){
		var data = gridManager.getCheckedRows();
		var ParamVo = [];

					ParamVo.push($("#acc_year_month").val().substring(0,4) + "@" + $("#acc_year_month").val().substring(4,6) + "@"
							+ liger.get("hos_id").getValue() + "@" + liger.get("goal_code").getValue())
		$.ligerDialog.confirm('确定审核?', function(yes) {
			if (yes) {
				ajaxJsonObjectByUrl("reAuditPrmDeptScheme.do", {
					ParamVo : ParamVo.toString()
				}, function(responseData) {
					if (responseData.state == "true") {
						query();
					}
				});
			}
		});
	}
	 
	 //键盘事件
		function loadHotkeys(){

			hotkeys('Q',query);
			hotkeys('A',auditPrmDeptScheme);
			hotkeys('B',reAuditPrmDeptScheme);
		}
	 
	 
	 
	 /* 设置树形菜单 */
     
     function onSelect(note){
         grid.options.parms=[];
         grid.options.newPage=1;
         
         var id = note.data.id;
         var pid = note.data.pid;
         grid.options.parms.push({name:'acc_year',value:$("#acc_year_month").val().substring(0,4)}); 
   	     grid.options.parms.push({name:'acc_month',value:$("#acc_year_month").val().substring(4,6)}); 
	   	 grid.options.parms.push({name:'goal_code',value:liger.get("goal_code").getValue()}); 
	   	 grid.options.parms.push({name:'hos_id',value:liger.get("hos_id").getValue()}); 
	   	 grid.options.parms.push({name:'dept_id',value:liger.get("dept_no").getValue().split(".")[0]}); 
	   	 grid.options.parms.push({name:'dept_no',value:liger.get("dept_no").getValue().split(".")[1] == null?'':liger.get("dept_no").getValue().split(".")[1]});
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
    			  dept_id:liger.get("dept_no").getValue().split(".")[0],
    			  dept_no:liger.get("dept_no").getValue().split(".")[1] == null?'':liger.get("dept_no").getValue().split(".")[1]
    			  };
		ajaxJsonObjectByUrl("queryDeptSchemeTreeAudit.do?isCheck=false", param,
		
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
            <td align="left" class="l-table-edit-td"><input name="dept_no" type="text" id="dept_no" ltype="text" validate="{required:true,maxlength:20}" /></td>
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
   	</div>
</body>
</html>
