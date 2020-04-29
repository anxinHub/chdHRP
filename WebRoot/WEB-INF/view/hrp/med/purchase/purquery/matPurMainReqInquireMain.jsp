
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"><html style="overflow:hidden;">
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
    	//加载数据
    	loadHead(null);	
		 loadHotkeys();
        $("#stateSelect").ligerTextBox({width:134});
        
        $("#store_id").ligerTextBox({width:160});
		
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	  grid.options.parms.push({name:'begin_date',value:$("#begin_date").val()});//日期范围：开始时间 
    	  grid.options.parms.push({name:'end_date',value:$("#end_date").val()});//日期范围：结束时间
    	  grid.options.parms.push({name:'dept_id',value:liger.get("dept_id").getValue().split(",")[0]});//编制部门
    	  grid.options.parms.push({name:'state',value:liger.get("planState").getValue()});//状态
    	  grid.options.parms.push({name:'brif',value:$("#brif").val()});//摘要
    	  grid.options.parms.push({name:'pur_type',value:liger.get("pur_type").getValue()});//计划类型

    	//加载查询条件
    	grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
     }
    //获取查询条件的数值
    function f_getWhere(){
    	if (!grid) return null;
        var clause = function (rowdata, rowindex){
                	if($("#begin_date").val()!=""){
                		return rowdata.type_code.indexOf($("#begin_date").val()) > -1;	
                	}
                	if($("#end_date").val()!=""){
                		return rowdata.type_name.indexOf($("#end_date").val()) > -1;	
                	}
                	if($("#dept_id").val()!=""){
                		return rowdata.type_name.indexOf($("#dept_id").val()) > -1;	
                	}
                	if($("#state").val()!=""){
                		return rowdata.type_name.indexOf($("#state").val()) > -1;	
                	}
                	if($("#brif").val()!=""){
                		return rowdata.type_name.indexOf($("#brif").val()) > -1;	
                	}
                	if($("#pur_type").val()!=""){
                		return rowdata.type_name.indexOf($("#pur_type").val()) > -1;	
                	}
                	
        };
        return clause; 
    }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '采购计划号', name: 'pur_code', align: 'left',render:
                    	 function(rowdata,rowindex,value){
	                    	 return '<a href=javascript:openUpdate("' 
								+ rowdata.pur_id
								+ '")>'+rowdata.pur_code+'</a>';

                     	 }
					 		},
                     { display: '编制科室', name: 'dept_name', align: 'left'
					 		},
					 { display: '药品编码', name: 'brif', align: 'left'
					 		},
					 { display: '药品名称', name: 'plan_type', align: 'left'
						 	},
                     { display: '规格型号', name: 'pur_hos_name', align: 'left'
					 		},
					 { display: '计量单位', name: 'pur_hos_name', align: 'left'
					 		},
					 { display: '采购数量', name: 'req_hos_name', align: 'left'
					 },
					 { display: '科室需求计划号', name: 'pay_hos_name', align: 'left'
					 },
					 { display: '需求科室', name: 'maker', align: 'left'
					 },
					 { display: '需求日期', name: 'make_date', align: 'left'
					 },
					 { display: '需求数量', name: 'checker', align: 'left'
					 },
					 { display: '计划单价', name: 'check_date', align: 'left'
					 },
					 { display: '金额', name: 'state', align: 'center'
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryMedPurMain.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
                     	{ text: '打印', id:'print', click: printDate,icon:'print' },
        				{ line:true }
                     	]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.pur_id
							);
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function itemclick(item){ 
        if(item.id)
        {
            switch (item.id)
            {
                case "check":
                    var data = gridManager.getCheckedRows();
                    if (data.length == 0){
                    	$.ligerDialog.error('请选择行');
                    }else{
                    	 var Param =[];
                    	 var flag;
                         $(data).each(function (){
                        	if(this.state != '1'){
                        		flag = false;
                        	}
 							Param.push(
 							//表的主键
 							this.group_id+"@"+
 							this.hos_id+"@"+
 							this.copy_code+"@"+
 							this.pur_id+"@"+
 							this.state
 							)
                         });
                         if(flag == false){
                        	 $.ligerDialog.error('只能审核状态为未审核的数据');
                        	 return ;
                         }
                       $.ligerDialog.confirm('确定审核?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("checkMedPurMain.do?paramVo="+Param,{},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        });  
                    }
                    return;
                    
                case "cancelCheck":
                    var data = gridManager.getCheckedRows();
                    if (data.length == 0){
                    	$.ligerDialog.error('请选择行');
                    }else{
                    	 var Param =[];
                    	 var flag;
                         $(data).each(function (){
                        	if(this.state != '2'){
                        		flag = false;
                        	}
 							Param.push(
 							//表的主键
 							this.group_id+"@"+
 							this.hos_id+"@"+
 							this.copy_code+"@"+
 							this.pur_id+"@"+
 							this.state
 							)
                         });
                         if(flag == false){
                        	 $.ligerDialog.error('只能取消已审核的数据');
                        	 return ;
                         }
                       $.ligerDialog.confirm('确定取消审核?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("cancelCheckMedPurMain.do?paramVo="+Param,{},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        });  
                    }
                    return;
            }   
        }
        
    }
    	
   
    function downTemplate(){
    	
    	location.href = "downTemplate.do?isCheck=false";
    
    }
    
    function openUpdate(obj){
    	
		$.ligerDialog.open({ url : 'medPurMainCheckDetailPage.do?isCheck=false&pur_id='+obj,data:{}, height: 500,width: 1100,top:1,title:'修改',modal:true,showToggle:false,showMax:true,showMin: false,isResize:true,buttons: [ { text: '打印', onclick: function (item, dialog) { dialog.frame.saveMedPurMain(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
    
    }
   
    //键盘事件
	  function loadHotkeys() {

		hotkeys('Q', query);

		hotkeys('A', add);
		hotkeys('D', remove);

		hotkeys('B', downTemplate);

		hotkeys('E', exportExcel);


	 }
  //打印数据
	 function printDate(){
		//有数据直接打印
		if($("#resultPrint > table > tbody").html()!=""){
			lodopPrinterTable("resultPrint","开始打印","08113 物资仓库配套表",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备打印数据,请稍候...');

		var printPara={
			usePager:false,
           type_code:$("#type_code").val(),
           type_name:$("#type_name").val()
         };
		ajaxJsonObjectByUrl("queryMedStoreMatch.do",printPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
					 trHtml+="<td>"+item.type_code+"</td>"; 
					 trHtml+="<td>"+item.type_name+"</td>"; 
				 trHtml+="</tr>";
				$("#resultPrint > table > tbody").empty();
				$("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			//alert($("#resultPrint").html())
			lodopPrinterTable("resultPrint","开始打印","08113 物资仓库配套表",true);
	    },true,manager);
		return;
	 }
	
	 
	 //导出数据
	 function exportExcel(){
		//有数据直接导出
		if($("#resultPrint > table > tbody").html()!=""){
			lodopExportExcel("resultPrint","导出Excel","08113 物资仓库配套表.xls",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');

		var exportPara={
			usePager:false,
           type_code:$("#type_code").val(),
           type_name:$("#type_name").val()
         };
		ajaxJsonObjectByUrl("queryMedStoreMatch.do",exportPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
					 trHtml+="<td>"+item.type_code+"</td>"; 
					 trHtml+="<td>"+item.type_name+"</td>"; 
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").empty();
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			lodopExportExcel("resultPrint","导出Excel","08113 物资仓库配套表.xls",true);
	    },true,manager);
		return;
	 }
	 
	 function loadDict(){
         //字典下拉框
		 autocomplete("#dept_id", "../../queryPurDept.do?isCheck=false", "id", "text", true, true);//编制部门下拉框
		 
		//返回当前年,当前月,当前日期,当前月第一天,当前月最后一天,上个月,上月第一天，上月最后一天
			var date = getCurrentDate();
	        var aa = date.split(';');
	       	thisDateB=aa[3];
	       	thisDateE=aa[4];
	       	lastDateB = aa[6];
	       	lastDateE = aa[7];
			$("#begin_date").val(aa[3]);
	   		$("#end_date").val(aa[4]);
		 
      }   
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

    <table cellpadding="0" cellspacing="0" class="l-table-edit">
        <tr>
        	<td align="right" class="l-table-edit-td"  >
				日期范围：
	        </td>
			<td align="left" class="l-table-edit-td">
				<input class="Wdate" name="begin_date" id="begin_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
			</td>
			<td align="left" class="l-table-edit-td"  >
				至：
			</td>
			<td align="left" class="l-table-edit-td"></td>
			<td align="right" class="l-table-edit-td">
				<input class="Wdate" name="end_date" id="end_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
			</td>
			
   <td align="left" class="l-table-edit-td"></td>
    <td align="right" class="l-table-edit-td"  >
	采购计划单号：
      </td>
			<td align="left" class="l-table-edit-td">
				<input name="brif" id="brif" type="text"/>
			</td>
			<td align="right" class="l-table-edit-td"  ></td>
			<td align="left" class="l-table-edit-td"></td>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">药品信息：</td>
            <td align="left" class="l-table-edit-td">
        		<input name="planState" type="text" id="planState" ltype="text" validate="{required:true,maxlength:20}" />
            </td>
            <td align="left"></td>
        </tr>
        
         <tr>
         	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">采购部门：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr>  
    </table>

	<div id="maingrid"></div>
	<div id="resultPrint" style="display:none">
	   <!-- 	<table width="100%">
			<thead>
				<tr>
	                <th width="200"></th>	
	                <th width="200"></th>	
	                <th width="200"></th>	
	                <th width="200"></th>	
				</tr>
			 </thead>
			<tbody></tbody>
	   	</table> -->
   	</div>
</body>
</html>
