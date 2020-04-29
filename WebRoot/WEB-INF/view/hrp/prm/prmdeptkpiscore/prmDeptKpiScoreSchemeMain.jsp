<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc.jsp"/>
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    var id;
    var pid;
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
        loadHotkeys();
        query();
        $("#acc_year").ligerTextBox({width:160});
        $("#acc_month").ligerTextBox({width:160});
        $("#kpi_code").ligerTextBox({width:160});
        $("#dept_id").ligerTextBox({width:160});
        $("#layout1").ligerLayout({ leftWidth: 200,
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
    	grid.loadData(grid.where);
    	loadTree();
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
        	   		 { display: '指标编码', name: 'kpi_code', align: 'left',width:60
					 },
					 { display: '指标名称', name: 'kpi_name', align: 'left',width:190,
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
		             	 }	
					 },
                     { display: '指标性质', name: 'nature_name', align: 'center',width:70
					 		},
                     { display: '权重', name: 'ratio', align: 'right',width:50
					 		},
                     { display: '目标值', name: 'goal_value', align: 'right',width:50
					 		},
                     { display: '评分方法', name: 'grade_meth_name', align: 'center',width:70
					 		},
                     { display: '评分标准', name: '', align: 'center',width:70,
	                     		render : function(rowdata, rowindex,
	    								value) {
	                     			var str = JSON.stringify(rowdata).replace(new RegExp("'","gm"),"");
		   							return "<a href\"=javascript:void(0)\"  onclick='viewScoringDeptScheme("+str+")'>查看</a>";
		                    }
					 		},
					 { display: '取值方法', name: 'method_name', align: 'center',width:70
					 		},
					 { display: '计算公式', name: 'formula_method_chs', align: 'left',width:130
					 },
					 { display: '取值函数', name: 'fun_method_chs', align: 'left',width:130
					 },
					 { display: '指标值', name: 'kpi_value', align: 'right',width:75,
                  		render : function(rowdata, rowindex,
								value) {
                  			if(rowdata.kpi_value != null){
                  				if(rowdata.method_code == "02"){
                  					return "<a href=javascript:openFormulaView('"+rowdata.group_id+ "|" + 
        							rowdata.hos_id   + "|" + 
        							rowdata.copy_code   + "|" + 
        							rowdata.dept_id  + "|" + 
        							rowdata.dept_no  + "|" + 
        							rowdata.formula_method_chs  + "|" + 
        							rowdata.kpi_name   + "|" + 
        							rowdata.kpi_value+ "|" + 
        							rowdata.formula_code+ "|" + 
        							rowdata.acc_year+ "|" + 
        							rowdata.acc_month+"')>"+rowdata.kpi_value+"</a>";
                  				}else{
                  					return rowdata.kpi_value == 0?"0":rowdata.kpi_value;
                  				}
                  			}
                  			return rowdata.kpi_value;
   							
                    	}
					 },
					 { display: '指标得分', name: 'kpi_score', align: 'right',width:75
					 },
					 { display: '指示灯', name: 'led', align: 'center',width:75,
                    	 render : function(rowdata, rowindex, value) {
                    		 
                    		 if(rowdata.led_path == null || rowdata.led_path == ''){
                    			 return '';
                    		 }
                    		 
 							return "<img style='margin-top: 7px;width:20px;' src='<%=path%>/"+rowdata.led_path+"' border='0' width ='50px' />";
 						}
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryPrmKpiScoreDeptByScheme.do?isCheck=false&group_id=${group_id}&hos_id=${hos_id}&copy_code=${copy_code}'
         				+ '&acc_year=${acc_year}&acc_month=${acc_month}&goal_code=${goal_code}&dept_id=${dept_id}&dept_no=${dept_no}',
                     width: '100%', height: '98%', checkbox: false,rownumbers:false,delayLoad:true,
                     selectRowButtonOnly:true
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function openFormulaView(obj){
    	
    	var vo = obj.split("|");
    	
    	var param = 
    	 "group_id=" + vo[0]
    	 +"&hos_id="  + vo[1]
    	 +"&copy_code=" + vo[2]
    	 +"&dept_id=" + vo[3]
    	 +"&dept_no=" + vo[4]
    	 +"&formula_method_chs=" + vo[5]
    	 +"&kpi_name="  + vo[6]
    	 +"&kpi_value=" + vo[7]
    	 +"&formula_code=" + vo[8]
    	 +"&acc_year=" + vo[9]
    	 +"&acc_month=" + vo[10];
    	
    	$.ligerDialog.open({
			url : 'prmDeptKpiScoreFormulaMain.do?isCheck=false&'+param,
			height : 500,
			width : 800,
			title : '公式查看',
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
		grade_meth = grade_meth_code;
		
		if (grade_meth == '01') {
			
			$.ligerDialog.open({
				url : 'prmDeptKpiScoreSectionMain.do?isCheck=false&' + parm,
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

		if (grade_meth == '02') {

			$.ligerDialog.open({
				url : 'prmDeptKpiScoreAdMain.do?isCheck=false' + parm,
				height : 500,
				width : 800,
				title : '加分法评分标准设定',
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
		
		if (grade_meth == '03') {

			$.ligerDialog.open({
				url : 'prmDeptKpiScoreAdMain.do?isCheck=false' + parm,
				height : 500,
				width : 800,
				title : '扣分法评分标准设定',
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
		
		
    }

 
	function loadHotkeys(){
		
		hotkeys('Q',query);
		hotkeys('P',printDeptKpiScore);

	}
   function printDeptKpiScore(){
		
		printDate();
	}
	
	
    function loadDict(){
            //字典下拉框
            
    	    autocompleteAsync("#hos_id","../quertSysHosInfoDict.do?isCheck=false","id","text",true,true,"",true,"${hos_id}",false);
            
            
	        autocompleteAsync("#dept_id","../queryPrmDeptDict.do?isCheck=false","id","text",true,true,"",false,"${dept_id}.${dept_no}");
	      
	        
	        $("#hos_id").ligerTextBox({width : 160,disabled: true});
	    	
	    	$("#dept_id").ligerTextBox({width : 160,disabled: true});
	        
	    	
	    	$("#acc_year").ligerTextBox({width : 160,disabled: true});
	    	
	    	$("#acc_month").ligerTextBox({width : 160,disabled: true});
         }  
    
  //打印数据
	 function printDate(){
		//有数据直接打印
		if($("#resultPrint > table > tbody").html()!=""){
			lodopPrinterTable("resultPrint","开始打印","科室KPI指标考评计算表",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备打印数据,请稍候...');
		
		var printPara={
				
			usePager:false,
            
           acc_year:$("#acc_year").val(),
            
           acc_month:$("#acc_month").val(),
            
           hos_id:liger.get("hos_id").getValue(),
            
           dept_id:liger.get("dept_id").getValue().split(".")[0]
            
          
          
         };
		ajaxJsonObjectByUrl("queryPrmDeptKpiScoreDept.do",printPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
					 trHtml+="<td>"+item.acc_year+"</td>"; 
					 trHtml+="<td>"+item.acc_month+"</td>"; 
					 trHtml+="<td>"+item.dept_kind_name+"</td>"; 
					 trHtml+="<td>"+item.dept_code+"</td>"; 
					 trHtml+="<td>"+item.dept_name+"</td>"; 
					 trHtml+="<td>"+item.kpi_score+"</td>"; 
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			lodopPrinterTable("resultPrint","开始打印","科室KPI指标考评计算表",true);
	    },true,manager);
		return;
	 }
	
	 
	 /* 设置树形菜单 */
     
     function onSelect(note){
         grid.options.parms=[];
         grid.options.newPage=1;
         
         id = note.data.id;
         pid = note.data.pid;
         grid.options.parms.push({name:'acc_year',value:$("#acc_year").val()}); 
   	     grid.options.parms.push({name:'acc_month',value:$("#acc_month").val()}); 
   	     grid.options.parms.push({name:'hos_id',value:liger.get("hos_id").getValue()}); 
   	     grid.options.parms.push({name:'dept_id',value:liger.get("dept_id").getValue().split(".")[0]}); 
	     grid.options.parms.push({name:'dept_no',value:liger.get("dept_id").getValue().split(".")[1] == null?'':liger.get("dept_id").getValue().split(".")[1]}); 
         if(pid != -1){
           grid.options.parms.push({name:'kpi_code',value:id});
      	   grid.options.parms.push({name:'super_kpi_code',value:pid.replace("goal","")});
         }else{
      	   grid.options.parms.push({name:'goal_code',value:id.replace("goal","")});
         }
     	 //加载查询条件
     	 grid.loadData(grid.where);
     }

     
      function loadTree(){
    	  
    	  var param = {

    			  acc_year :$("#acc_year").val(),
    			  acc_month:$("#acc_month").val(),
    			  dept_id:liger.get("dept_id").getValue().split(".")[0],
    			  hos_id:liger.get("hos_id").getValue()
    			  };
    	 
		ajaxJsonObjectByUrl("queryPrmKpiScoreDeptBySchemeTree.do?isCheck=false&group_id=${group_id}&hos_id=${hos_id}&copy_code=${copy_code}&acc_year=${acc_year}&acc_month=${acc_month}&goal_code=${goal_code}&dept_id=${dept_id}&dept_no=${dept_no}", param,
		
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

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">单位信息：</td>
            <td align="left" class="l-table-edit-td"><input name="hos_id" type="text" id="hos_id" ltype="text" /></td>
            <td align="left"></td>   
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室名称：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">年度：</td>
            <td align="left" class="l-table-edit-td"><input name="acc_year" class="Wdate"  type="text" id="acc_year" value="${acc_year }" ltype="text" validate="{required:true,maxlength:20}" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy'})"/></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">月份：</td>
            <td align="left" class="l-table-edit-td"><input name="acc_month" class="Wdate"  type="text" id="acc_month" value="${acc_month }" ltype="text" validate="{required:true,maxlength:20}" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'MM'})"/></td>
            <td align="left"></td>
        </tr> 
    </table>
    
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
                <th width="200">考核年度</th>	
                <th width="200">考核月份</th>	
                <th width="200">科室分类</th>	
                <th width="200">科室编码</th>	
                <th width="200">科室名称</th>	
                <th width="200">绩效得分</th>	       
				   	</tr>
			   	</thead>
			   	<tbody></tbody>
	   	</table>
   	</div>
</body>
</html>
