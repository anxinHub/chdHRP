<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">	
    var grid;
    var gridManager = null;
    $(function ()
    {
		loadDict();
    	loadHead(null);	//加载数据
    });
    //查询
    function  query(){
        
    	grid.options.parms=[];
    	grid.options.newPage=1;        		 
  
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'begin_year_month',value:$("#begin_year_month").val()}); 
    	grid.options.parms.push({name:'end_year_month',value:$("#end_year_month").val()}); 
    	grid.options.parms.push({name:'busi_data_source_code',value:liger.get("busi_data_source_code").getValue()}); 
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '统计年月', name: 'year_month', align: 'left',
					 },
					 { display: '开单科室编码', name: 'appl_dept_code', align: 'left'
					 },
                     { display: '开单科室', name: 'appl_dept_name', align: 'left'
					 },
					 { display: '执行科室编码', name: 'exec_dept_code', align: 'left'
					 },
                     { display: '执行科室', name: 'exec_dept_name', align: 'left'
					 },
					 { display: '收费编码', name: 'charge_kind_code', align: 'left'
					 },
                     { display: '收费类别', name: 'charge_kind_name', align: 'left'
					 },
					 { display: '数据来源', name: 'busi_data_source_name', align: 'left'
					 },
                     { display: '金额', name: 'money', align: 'right',
						 render : function(rowdata, rowindex,
									value) {
								 return formatNumber(rowdata.money,2,1);
							}
					   }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryCostIncomeMain.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad :true,
                     selectRowButtonOnly:true,checkBoxDisplay : f_checkBoxDisplay,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
    	                { text: '删除', id:'delete', click: remove,icon:'delete' },
						{ line:true }, 
						{ text: '按月删除', id:'remove', click: deleteMonthly,icon:'delete' },
						{ line:true }, 
		                { text: '导入', id:'import', click: imp,icon:'up' },
		                { line:true },
		                { text: '采集HIS数据', id:'impHis', click: impHis,icon:'up' }
    				]}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }

    function f_checkBoxDisplay(rowdata){
        
    	 if (rowdata.year_month == "合计")
 			    return false;
 		      return true;
 	       }
     
    function remove(){
    	var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.error('请选择行');
        }else{
            var ParamVo =[];
            $(data).each(function (){					
				ParamVo.push(
				  //表的主键
				  this.income_id 
				)
            });
            $.ligerDialog.confirm('确定删除?', function (yes){
            	if(yes){
                	ajaxJsonObjectByUrl("deleteCostIncomeMain.do",{ParamVo : ParamVo.toString()},function (responseData){
                		if(responseData.state=="true"){
                			query();
                		}
                	});
            	}
            }); 
        }
    }


	function impHis(){
		$.ligerDialog.open({
		    url: 'costIncomeMainCollectPage.do?isCheck=false',
		    height: 300,
		    width: 300,
		    title: '采集',
		    modal: true,
		    showToggle: false,
		    showMax: false,
		    showMin: true,
		    isResize: true,
		    buttons: [{
		        text: '确定',
		        onclick: function(item, dialog) {
		            dialog.frame.save();
		        },
		        cls: 'l-dialog-btn-highlight'
		    },
		    {
		        text: '取消',
		        onclick: function(item, dialog) {
		            dialog.close();
		        }
		    }]
		});
	}


	    function imp(){
	    	//公用import.jsp页面使用
			var para={
			    "column": [
			        {
			            "name": "acc_year",
			            "display": "统计年度",
			            "width": "200",
			            "require":true
			        },
			        {
			            "name": "acc_month",
			            "display": "统计月份",
			            "width": "200",
			            "require":true
			        },
			        {
			            "name": "appl_dept_code",
			            "display": "开单科室编码",
			            "width": "200",
			            "require":true
			        },
			        {
			            "name": "appl_dept_name",
			            "display": "开单科室名称",
			            "width": "200"
			        },
			        {
			            "name": "exec_dept_code",
			            "display": "执行科室编码",
			            "width": "200",
			            "require":true
			        },
			        {
			            "name": "exec_dept_name",
			            "display": "执行科室名称",
			            "width": "200"
			        },
			        {
			            "name": "charge_kind_code",
			            "display": "收费类别编码",
			            "width": "200",
			            "require":true
			        },
			        {
			            "name": "charge_kind_name",
			            "display": "收费类别名称",
			            "width": "200"
			        },{
			            "name": "busi_data_source_code",
			            "display": "收入数据来源编码",
			            "width": "200",
			            "require":true
			        },{
			            "name": "busi_data_source_name",
			            "display": "收入数据来源名称",
			            "width": "200",
			            "require":false
			        },
			        {
			            "name": "money",
			            "display": "金额",
			            "width": "200",
			            "require":true
			        }
			       
			        
			    ]/* ,
			    isUpdate:true */
			};
			importSpreadView("hrp/cost/costincomemain/impCostIncomeMain.do?isCheck=false",para); 
		 }


		 
    
		    function deleteMonthly(){
		
		    	if ($("#begin_year_month").val() == '' || $("#end_year_month").val() == '') {
		    		$.ligerDialog.question('统计年月不能为空')
		    		return false;
		    	}

		    	$.ligerDialog.confirm('是否确认删除', function(yes) {
		    		if (yes) {
		    			var formPara = {
	    					begin_year_month: $("#begin_year_month").val(),
	    					end_year_month: $("#end_year_month").val(),
		    			};
		    			ajaxJsonObjectByUrl("deleteMonthlyCostIncomeMain.do", formPara, function(responseData) {
		    				if (responseData.state == "true") {
		    					query();
		    				}
		    			});
		    		}
		    	});
        }
    
 
    function loadDict(){

        
 		   autocomplete("#busi_data_source_code","../queryDataSource.do?isCheck=false","id","text",true,true,{busi_data_source_type:1});

    	   $("#begin_year_month").ligerTextBox({ width:160 });
    	   $("#end_year_month").ligerTextBox({ width:160});
 		 
		  autodate("#begin_year_month","yyyyMM");
		 
		   autodate("#end_year_month","yyyyMM");
		 
         }

      
    </script>
</head>
<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">统计年月：</td>
            <td align="left" class="l-table-edit-td"><input name="begin_year_month" type="text" id="begin_year_month" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">至&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
            <td align="left" class="l-table-edit-td"><input name="end_year_month" type="text" id="end_year_month" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">数据来源：</td>
            <td align="left" class="l-table-edit-td"><input name="busi_data_source_code" type="text" id="busi_data_source_code" ltype="text"  /></td>
        </tr> 
    </table>
	<div id="maingrid"></div>
</body>
</html>
