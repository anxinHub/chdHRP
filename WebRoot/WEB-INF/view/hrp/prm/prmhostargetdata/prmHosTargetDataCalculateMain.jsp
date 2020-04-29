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
    	$('body').height("100%");  //   以免拉动左边树时结构混乱
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
   
        $("#acc_year").ligerTextBox({width:160});
        $("#target_code").ligerTextBox({width:160});
        $("#is_audit").ligerComboBox({width:160 });
        $("#check_hos_id").ligerComboBox({width:160 });
        
        toolbar();
        loadHotkeys();
		
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	  grid.options.parms.push({name:'acc_year',value:$("#acc_year").val()}); 
    	  grid.options.parms.push({name:'target_code',value:$("#target_code").val()}); 
    	  grid.options.parms.push({name:'check_hos_id',value:liger.get("check_hos_id").getValue()});
    	  grid.options.parms.push({name:'is_audit',value:$("#is_audit").val()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '考核单位', name: 'check_hos_name', align: 'left'
					 		},
                     { display: '指标编码', name: 'target_code', align: 'left'
					 		},
					 { display: '指标编码', name: 'target_name', align: 'left'
						 		},
					 { display: '取值方法', name: 'method_name', align: 'left'
					 		},
					 { display: '计算公式', name: 'formula_method_chs', align: 'left'
						 	},
					 { display: '取值函数', name: 'fun_name', align: 'left'
						    },
                     { display: '指标值', name: 'target_value', align: 'left',
						    	editor:{type:'text'},
	                     		render : function(rowdata, rowindex,value) {
	                     			return rowdata.target_value == null || rowdata.target_value == "" ? "" : formatNumber(rowdata.target_value,2,1);
	                     		}
					 		},
                     { display: '审核状态', name: 'is_audit', align: 'left',
                     	 render : function(rowdata, rowindex,
									value) {
								if(rowdata.is_audit == 0){
									return "未审核";
								}else{
									return "审核"
								}
							}
					 		},
                     { display: '审核人', name: 'user_code', align: 'left'
					 		},
                     { display: '审核日期', name: 'audit_date', align: 'left'
					 		}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryPrmHosTargetDataCalculate.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,enabledEdit: true,delayLoad:true,
                     selectRowButtonOnly:true
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
        
        $(document).bind('keydown.maingrid', function(event) {
			if (event.keyCode == 13) {// enter,也可以改成9:tab

				grid.endEditToPrmNext();
			}
		});
		
		$("#maingrid").on('focus', 'input', function() {
			if (clicked != 0)
				return;
			var curdom = $(this).parent();
			if (curdom.hasClass('l-text-combobox') && !$(this).attr('readonly')) {
				var clkbutton = curdom.find('.l-trigger-icon');
				clicked = 2;
				clkbutton[0].click();
			}
		});
    }
    
    
 	//工具栏
	function toolbar(){
		var obj = [];
		
		obj.push({ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '打印（<u>P</u>）', id:'print', click: print,icon:'print' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '生成（<u>C</u>）', id:'create', click: createTargetCalculate, icon:'bookpen' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '审核（<u>A</u>）', id:'audit', click:auditTargetCalculate,icon:'audit' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '反审核（<u>B</u>）', id:'reAudit', click:reAuditTargetCalculate,icon:'back' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '保存（<u>S</u>）', id:'save', click:saveHostargetCalculate,icon:'save' });
       	obj.push({ line:true });
       	
       	$("#toptoolbar").ligerToolBar({ items: obj});
	}
	
    
    
    //键盘事件
	function loadHotkeys(){
		
		hotkeys('Q',query);
		hotkeys('P',print);
		hotkeys('C',createTargetCalculate);
		hotkeys('D',collectTargetCalculate);
		hotkeys('A',auditTargetCalculate);
		hotkeys('B',reAuditTargetCalculate);
	    hotkeys('S',saveHostargetCalculate);
	}
    
    function saveHostargetCalculate(){
    	
    	var data = gridManager.getUpdated();
    	   
    	if(data.length==0){
    		
    		$.ligerDialog.warn('沒有数据更新');
    		
    	}else{
		var ParamVo = [];
		$(data).each(
				function() {
					ParamVo.push(this.group_id + "@" + this.hos_id + "@"
							+ this.copy_code + "@" + this.acc_year + "@"
							+ this.acc_month + "@" + this.target_code + "@"
							+ this.check_hos_id + "@" + this.target_value + "@")
				});

				ajaxJsonObjectByUrl("updateBatchPrmHosTargetDataCalculate.do", {
					ParamVo : ParamVo.toString()
				}, function(responseData) {
					if (responseData.state == "true") {
						query();
					}
				});
    	}
    	
    }
    
    function createTargetCalculate(){
    	
    	if($("#acc_year").val()==""){
     		$.ligerDialog.warn("核算年月不能为空");
     		return false;
    	}
    	
    	$.ligerDialog.confirm('确定生成吗?',function(yes){
    		if(yes){
    			var ParamVo =[];
    	    	   
				ParamVo.push(
					$("#acc_year").val() 
				)
           
		    	ajaxJsonObjectByUrl("createPrmHosTargetDataCalculate.do",{ParamVo : ParamVo.toString()},function (responseData){
		     		if(responseData.state=="true"){
		     			query();
		     		}
		     	});
    		}
    	});
    }
    //计算
    function collectTargetCalculate(){
    	
    	var acc_year_month = $("#acc_year").val()
    	
    	if (acc_year_month == '') {
        	$.ligerDialog.warn('请选择年月');
            return false;
        }
    	
	    $.ligerDialog.confirm('确定计算吗?',function(yes){
	    	if(yes){
	    		var param={
		       		acc_year_month:acc_year_month,
					check_hos_id:liger.get("check_hos_id").getValue()
			    }
		    	ajaxJsonObjectByUrl("collectPrmHosTargetCalculate.do",param, function(responseData){
	    			if(responseData.state=="true"){
	        			query();
	        		}
	    		});
	    	}
	    }); 	
    
    }
    
    function auditTargetCalculate(){
    	 var data = gridManager.getCheckedRows();
         if (data.length == 0){
         	$.ligerDialog.warn('请选择行');
         }else{
        	 
             var ParamVo =[];
             $(data).each(function (){			
          
            
														ParamVo.push(
														this.group_id   +"@"+ 
														this.hos_id   +"@"+ 
														this.copy_code   +"@"+ 
														this.acc_year   +"@"+ 
														this.acc_month   +"@"+ 
														this.target_code   +"@"+ 
														this.check_hos_id 
														) });
             $.ligerDialog.confirm('确定审核?', function (yes){
             	if(yes){
                 	ajaxJsonObjectByUrl("auditPrmHosTargetDataCalculate.do",{ParamVo : ParamVo.toString()},function (responseData){
                 		if(responseData.state=="true"){
                 			query();
                 		}
                 	});
             	}
             }); 
         }
    }
    
    function reAuditTargetCalculate(){
    	 var data = gridManager.getCheckedRows();
         if (data.length == 0){
         	$.ligerDialog.warn('请选择行');
         }else{
             var ParamVo =[];
             $(data).each(function (){					
   													ParamVo.push(
   													this.group_id   +"@"+ 
   													this.hos_id   +"@"+ 
   													this.copy_code   +"@"+ 
   													this.acc_year   +"@"+ 
   													this.acc_month   +"@"+ 
   													this.target_code   +"@"+ 
   													this.check_hos_id 
   													) });
             $.ligerDialog.confirm('确定取消审核?', function (yes){
             	if(yes){
                 	ajaxJsonObjectByUrl("reAuditPrmHosTargetDataCalculate.do",{ParamVo : ParamVo.toString()},function (responseData){
                 		if(responseData.state=="true"){
                 			query();
                 		}
                 	});
             	}
             }); 
         }
    }
    
    function loadDict(){
            //字典下拉框
    	autocomplete("#target_code", "../quertPrmTargetDict.do?isCheck=false",
				"id", "text", true, true, "", false, "", "300");
            
    	 autocomplete("#check_hos_id","../quertSysHosInfoDict.do?isCheck=false","id","text",true,true,"",false);
    	 
    	 autodate("#acc_year","yyyymm");
         }  
    
	
  	//打印
    function print() {

		if (grid.getData().length == 0) {
			$.ligerDialog.warn("请先查询数据！");
			return;
		}

		/* var heads={
		  //"isAuto": true/false 默认true，页眉右上角默认显示页码
		  "rows": [
	      {"cell":0,"value":"科室名称："+liger.get("dept_id").getText(),"colSpan":"5"},
	      {"cell":3,"value":"项目名称："+liger.get("proj_id").getText(),"from":"right","align":"right","colSpan":"4"}
			  ]
		}; */
	
		var printPara={
				title: "院级指标数据计算",//标题
				columns: JSON.stringify(grid.getPrintColumns()),//表头
				class_name: "com.chd.hrp.prm.service.PrmHosTargetDataCalculateService",
				method_name: "queryPrmHosTargetPrmTargetDataPrint",
				bean_name: "prmHosTargetDataCalculateService"/* ,
				heads: JSON.stringify(heads) *///表头需要打印的查询条件,可以为空
				/* foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 */
				};
		
		$.each(grid.options.parms,function(i,obj){
				printPara[obj.name]=obj.value;
		});
		
		officeGridPrint(printPara);
	}
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
            <td align="right" class="l-table-edit-td" style="padding-left: 20px;">核算年月：</td>
			<td align="left" class="l-table-edit-td"><input class="Wdate"
				name="acc_year" type="text" id="acc_year" ltype="text"
				 onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyyMM'})"
				style="width: 160px;" /></td>
			<td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">指标名称：</td>
            <td align="left" class="l-table-edit-td"><input name="target_code" type="text" id="target_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">考核单位：</td>
            <td align="left" class="l-table-edit-td"><input name="check_hos_id" type="text" id="check_hos_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>

        </tr> 
	      <tr>            
	       <td align="right" class="l-table-edit-td"  style="padding-left:20px;">审核状态：</td>
	              <td align="left" class="l-table-edit-td">
               	<select id="is_audit" name="is_audit" style="width: 135px;">
               						<option value="">全部</option>
			                		<option value="0">未审核</option>
			                		<option value="1">已审核</option>
                				</select></td>
            <td align="left"></td>
	      </tr>
      
    </table>
	
	<div id="toptoolbar" ></div>
	<div id="maingrid"></div>
	
</body>
</html>
