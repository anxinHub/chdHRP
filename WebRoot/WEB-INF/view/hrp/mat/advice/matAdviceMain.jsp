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
        loadDict();//加载下拉框
    	loadHead(null);	
    });
    //查询
    function  query(){
    	  grid.options.parms=[];
    	  grid.options.newPage=1;
          //根据表字段进行添加查询条件
    	  grid.options.parms.push({name:'charge_date_b',value:$("#charge_date_b").val()}); 
    	  grid.options.parms.push({name:'charge_date_e',value:$("#charge_date_e").val()}); 
    	  grid.options.parms.push({name:'hx_flag',value:$("#hx_flag").val()}); 
    	  grid.options.parms.push({name:'dept_id',value:liger.get("dept_id").getValue().split(",")[0]}); 
     	  grid.options.parms.push({name:'inv_id',value:liger.get("inv_id").getValue()}); 
    	  grid.options.parms.push({name:'charge_item_id',value:liger.get("charge_item_id").getValue()}); 
    	  grid.options.parms.push({name:'hospital_no',value:$("#hospital_no").val()}); 
    	  grid.options.parms.push({name:'patient_name',value:$("#patient_name").val()}); 
    	  grid.options.parms.push({name:'doctor_code',value:$("#doctor_code").val()}); 
    	  grid.options.parms.push({name:'out_no',value:$("#out_no").val()}); 
    	  grid.options.parms.push({name:'busi_code',value:$("#busi_code").val()}); 
    	  grid.options.parms.push({
  			name : 'inv_model',//规格型号
  			value : $("#inv_model").val()
  		});
    	  //加载查询条件
    	  grid.loadData(grid.where);
     }
    

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '流水号', name: 'receive_id', align: 'left',width: '100'
					 		},
                     { display: '医嘱日期', name: 'charge_date', align: 'left',width: '100'
					 		},
                     { display: '开单科室', name: 'order_dept_name', align: 'left',width: '100'
					 		},
                     { display: '执行科室', name: 'exec_dept_name', align: 'left',width: '100'
					 		},
                     { display: '收费项目编码', name: 'charge_detail_code', align: 'left',width: '100'
					 		},
					 { display: '收费项目名称', name: 'charge_item_name', align: 'left',width: '100'
							 },		
                     { display: '材料编码', name: 'inv_code', align: 'left',width: '100'
					 		},
                     { display: '材料名称', name: 'inv_name', align: 'left',width: '100'
					 		},
					 { display: '供应商编码', name: 'sup_code', align: 'left',width: '100'
					 		},
					 { display: '供应商名称', name: 'sup_name', align: 'left',width: '100'
					 		},
                     { display: '规格型号', name: 'inv_model', align: 'left',width: '100'
					 		},
                     { display: '计量单位', name: 'unit_name', align: 'left',width: '100'
					 		},
                     { display: '收费单价', name: 'charge_price', align: 'left',width: '100'
					 		},
                     { display: '数量', name: 'charge_num', align: 'left',width: '100'
					 		},
                     { display: '批号', name: 'batch_no', align: 'left',width: '100'
					 		},
                     { display: '条形码', name: 'bar_code', align: 'left',width: '100'
					 		},
                     { display: '医生工号', name: 'doctor_code', align: 'left',width: '100'
					 		},
		             { display: '病历号', name: 'hospital_no', align: 'left',width: '100'
						 	},
				     { display: '病人姓名', name: 'patient_name', align: 'left',width: '100'
							},
					 { display: '核销信息', name: 'message', align: 'left',width: '100'
							},
					 { display: '核销状态', name: 'hx_flag', align: 'left',width: '100',
					 			render: function(rowdata, rowindex, value){
					 				if(value == 0){
					 					return "未核销";
					 				}else if(value == 1){
					 					return "已审核";
					 				}
					 			}
							},
					 { display: '单据号', name: 'out_no', align: 'left',width: '120'
							}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatAdvice.do',
                     width: '100%', height: '94%', checkbox: true,rownumbers:true,delayLoad :true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                    	 {text : '查询',id : 'search',click : query,icon : 'search'},
                    	 {line : true},
                     	 { text: '生成', id:'init', click: initMatAdvice,icon:'save' }
				    				]}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    function initMatAdvice(){
		var data = gridManager.getCheckedRows();
		if(data.length == 0) {
			$.ligerDialog.error('请选择行');
		}else{
			 var formPara={
					 busi_code:$("#busi_code").val(),
				        ParamVo : JSON.stringify(data)
				  };
			 ajaxJsonObjectByUrl("initMatAdvice.do",formPara,function(responseData){
	 	           if(responseData.state=="true"){
	 	            	query();
	 	           }
	 	     });
		}
    }
    
    function loadDict(){
    	
		var param = {
            	query_key:''
        };
		
		autocomplete("#inv_id","../queryMatInv.do?isCheck=false","id","text",true,true,null,false);
		autocomplete("#charge_item_id","../../cost/queryChargeItemArrt.do?isCheck=false","id","text",true,true,null,false);
		autocomplete("#dept_id", "../queryMatDept.do?isCheck=false", "id", "text", true, true);
    	$("#charge_date_b").ligerTextBox({width:80});
    	
        $("#charge_date_e").ligerTextBox({width:80});
        
        $("#hospital_no").ligerTextBox({width:160}); 
        
        $("#patient_name").ligerTextBox({width:160}); 
        
        $("#doctor_code").ligerTextBox({width:160});
        
        $("#out_no").ligerTextBox({width:160}); 
        
        $("#inv_model").ligerTextBox({width:160});
    	//状态
    	$("#hx_flag").ligerComboBox({
			width : 160
		});    
    	$("#busi_code").ligerComboBox({
			width : 160
		});
    }
    </script>

</head>

<body style="padding: 0px; ">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">医嘱日期：</td>
            <td align="left" class="l-table-edit-td"><input name="charge_date_b" type="text" id="charge_date_b"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
            <td align="left">至</td>
            <td align="left" class="l-table-edit-td" ><input name="charge_date_e" type="text" id="charge_date_e"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">状态：</td>
            <td align="left" class="l-table-edit-td" >
            	<select id="hx_flag" name="hx_flag">
            		<option value=""></option>
            		<option value="0">未审核</option>
            		<option value="1">已审核</option>
            	</select>
            </td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id"  /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">业务类型：</td>
            <td align="left" class="l-table-edit-td">
            	<select id="busi_code" name="busi_code">
            		<option value="01" selected="selected">代销出库</option>
            		<option value="02">材料出库</option>
            		<option value="03">专购品</option>
            	</select>
            </td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">材料信息：</td>
            <td align="left" class="l-table-edit-td" colspan="3"><input name="inv_id" type="text" id="inv_id"  /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">收费项目：</td>
            <td align="left" class="l-table-edit-td"><input name="charge_item_id" type="text" id="charge_item_id"  /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">病历号：</td>
            <td align="left" class="l-table-edit-td"><input name="hospital_no" type="text" id="hospital_no"  /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">病人名称：</td>
            <td align="left" class="l-table-edit-td" colspan="3">
            	<input name="patient_name" type="text" id="patient_name"  />
            </td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">医生工号：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="doctor_code" type="text" id="doctor_code"  />
            </td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">单据号：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="out_no" type="text" id="out_no"  />
			</td>
         <td align="left"></td>
           	 <td align="right" class="l-table-edit-td" >规格型号:</td>
				 <td align="left" class="l-table-edit-td" >
					<input name="inv_model" type="text" id="inv_model" ltype="text"    validate="{required:true}" />
		</td>
		
            <td align="left"></td>
        </tr> 
    </table>

	<div id="maingrid"></div>
</body>
</html>
