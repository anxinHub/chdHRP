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
    
    var clicked = 0;
    
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
        
        toobar();
        
        loadHotkeys();

    });
    
    function  query(){
		grid.options.parms=[];
		grid.options.newPage=1;
		grid.options.parms.push({name:'acc_year',value:'${acc_year}'}); 
		grid.loadData(grid.where);
   }

    function loadHead(){
    	
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                { display: '等级', name: 'sec_code', align: 'center',textField: 'sec_name',
					editor : {
						type : 'select',
						valueField : 'sec_code',
						textField : 'sec_name',
						selectBoxWidth : 500,
						selectBoxHeight : 240,
						grid : {
							columns : [ {
								display : '等级代码',
								name : 'sec_code',
								align : 'center'
							}, {
								display : '等级名称',
								name : 'sec_name',
								align : 'center'
							},  {
								display : '指示灯',
								name : 'led_path',
								align : 'center',
		                    	 render : function(rowdata, rowindex, value) {
			 							return "<img style='margin-top: 7px;width:20px;' src='<%=path%>/"+rowdata.led_path+"' border='0' width ='50px' />";
			 						}
							} ],
							switchPageSizeApplyComboBox : false,
							url : 'queryPrmDeptLedByEditerGrid.do?isCheck=false',
							pageSize : 30
						},
						keySupport: true,
						alwayShowInDown : true,
						keySupport : true,
						autocomplete : true,
						onSuccess : function() {
							this.parent("tr").next(".l-grid-row").find(
									"td:first").focus();
						}
					}
				 		},
                { display: 'KPI起始分', name: 'kpi_beg_score', align: 'center',editor: { type: 'float' ,
					precision : 0},
             		render : function(rowdata, rowindex,
							value) {
             			if(value == 0 || value == '' || value == null){
             				return '0';
             			}else{
             				return value;
             			}
             		}
				 		},
                { display: 'KPI结束分', name: 'kpi_end_score', align: 'center',editor: { type: 'float' ,
					precision : 0},
             		render : function(rowdata, rowindex,
							value) {
             			if(value == 0 || value == '' || value == null){
             				return '0';
             			}else{
             				return value;
             			}
             		}
				 		},
				 { display: '指示灯', name: 'led', align: 'center',
	                    	 render : function(rowdata, rowindex, value) {
	                    		 
	                    		 if(rowdata.led_path == null || rowdata.led_path == ''){
	                    			 return '';
	                    		 }
	                    		 
	 							return "<img style='margin-top: 7px;width:20px;' src='<%=path%>/"+rowdata.led_path+"' border='0' width ='50px' />";
	 						}
					   }
                ],
                dataAction: 'server',dataType: 'server',usePager:true,
                url:'queryPrmDeptKpiLedAudit.do?group_id=${group_id}&hos_id=${hos_id}&copy_code=${copy_code}&acc_year=${acc_year}&acc_month=${acc_month}&goal_code=${goal_code}&kpi_code=${kpi_code}&dept_id=${dept_id}&dept_no=${dept_no}',
                width: '100%', height: '100%', checkbox: true,rownumbers:true,alternatingRow : true,
                selectRowButtonOnly:true,enabledEdit : false//heightDiff: -10,
                   });

        		gridManager = $("#maingrid").ligerGetGridManager();
    }

  
  //键盘事件
	function loadHotkeys(){
		
	}
	
	
	
	
	
    function loadDict(){
        //字典下拉框

    	autocomplete("#hos_id","../quertSysHosInfoDict.do?isCheck=false","id","text",true,true,"",false);
        
		liger.get("hos_id").setValue("${hos_id}");
        
        liger.get("hos_id").setText("${hos_code} ${hos_name}");
    	
    	autocomplete("#dept_no","../queryPrmDeptDict.do?isCheck=false","id","text",true,true,"",false);
    	
		liger.get("dept_no").setValue("${dept_id}.${dept_no}");
        
        liger.get("dept_no").setText("${dept_code} ${dept_name}");
        
		$("#hos_id").ligerTextBox({width : 160,disabled: true});
    	
    	$("#dept_no").ligerTextBox({width : 160,disabled: true});
    	
    	$("#acc_year").ligerTextBox({width : 70,disabled: true});
    	
    	$("#acc_month").ligerTextBox({width : 70,disabled: true});
    	
    	$("#kpi_code").ligerTextBox({width : 160,disabled: true});
    	
    	$("#nature_code").ligerTextBox({width : 160,disabled: true});
    	
    	$("#goal_value").ligerTextBox({width : 70,disabled: true});
    	
    	$("#ratio").ligerTextBox({width : 70,disabled: true});
	}  

    function toobar(){
    }
    
    </script>

</head>

<body style="padding: 0px; overflow: hidden;" >
	<div id="pageloading" class="l-loading" style="display: none"></div>

    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	 	  <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:10px;">单位信息：</td>
            <td align="left" class="l-table-edit-td"><input name="hos_id" type="text" id="hos_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:5px;">考核单元：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_no" type="text" id="dept_no" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            
             <td align="right" class="l-table-edit-td"  style="padding-left:5px;">考核年度：</td>
            <td align="left" class="l-table-edit-td"><input name="acc_year" type="text" id="acc_year"  ltype="text"  value="${acc_year}"  validate="{required:true,maxlength:20}"   class="Wdate" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy'})"/></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:5px;">考核期间：</td>
            <td align="left" class="l-table-edit-td"><input name="acc_month" type="text" id="acc_month"  value="${acc_month}"   ltype="text" validate="{required:true,maxlength:20}"   class="Wdate" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'MM'})"/></td>
            <td align="left"></td>
        </tr> 
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:10px;">指标名称：</td>
            <td align="left" class="l-table-edit-td"><input name="kpi_code" type="text" id="kpi_code" ltype="text"  value="${kpi_name}"  validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:5px;">指标性质：</td>
            <td align="left" class="l-table-edit-td"><input name="nature_code" type="text" id="nature_code" ltype="text"  value="${nature_name}"  validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            
             <td align="right" class="l-table-edit-td"  style="padding-left:5px;">目标值：</td>
            <td align="left" class="l-table-edit-td"><input name="goal_value" type="text" id="goal_value" value="${goal_value}"  ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:5px;">满分：</td>
            <td align="left" class="l-table-edit-td"><input name="ratio" type="text" id="ratio"  value="${full_score}" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr>
    </table>
	<div id="toptoolmod"></div>
	<div id="maingrid"></div>

</body>
</html>
