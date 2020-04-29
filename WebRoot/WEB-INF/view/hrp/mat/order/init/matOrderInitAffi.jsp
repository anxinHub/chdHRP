<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <!-- jsp:include page="${path}/inc.jsp"/-->
    <jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
    <script src="<%=path%>/lib/hrp/mat/mat.js"	type="text/javascript"></script>
    <script type="text/javascript">
     var dataFormat;
     var grid;
     var gridManager;
     
     $(function (){
		loadDict()//加载下拉框
		loadHead();
		loadHotkeys();
     });  
     
     function query(){
    	 grid.options.parms = [];
 		 grid.options.newPage = 1;
 			
 		 grid.options.parms.push({name : 'begin_date',value : $("#begin_date").val()}); 
    	 grid.options.parms.push({name : 'end_date',value : $("#end_date").val() }); 
    	 
    	 grid.options.parms.push({name : 'store_id',value : liger.get("store_code").getValue().split(',')[0]});
    	 grid.options.parms.push({name : 'store_no',value : liger.get("store_code").getValue().split(',')[1]});
    	 grid.options.parms.push({name : 'dept_id',value : liger.get("dept_code").getValue().split(',')[0]}); 
    	 grid.options.parms.push({name : 'dept_no',value : liger.get("dept_code").getValue().split(',')[1]});
    	 
    	 grid.options.parms.push({name : 'brief',value : $("#brief").val()});
    	
 		 //加载查询条件
 		 grid.loadData(grid.where);
     }
    function loadDict(){
    	//返回当前年,当前月,当前日期,当前月第一天,当前月最后一天,上个月,上月第一天，上月最后一天
    	var date = getCurrentDate();
        var aa = date.split(';');
        $("#begin_date").val(aa[3]);
        $("#end_date").val(aa[4]);
    	//字典下拉框
    	autocomplete("#store_code", "../../queryMatStore.do?isCheck=false", "id", "text", true, true);//仓库
		autocomplete("#dept_code", "../../queryMatDept.do?isCheck=false", "id", "text", true, true,{is_last : '1'}, false);//科室		
		
        $("#begin_date").ligerTextBox({width:100});
        $("#end_date").ligerTextBox({width:100});
		
        $("#store_code").ligerTextBox({width:160});
        $("#dept_code").ligerTextBox({width:160});
        
        $("#brief").ligerTextBox({width:240});
        
        $("#query").ligerButton({click: query, width:90});
		$("#gen").ligerButton({click: gen, width:90});
		//$("#print").ligerButton({click: printDate, width:90});
		$("#close").ligerButton({click: this_close, width:90});
     } 
	
    function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [{display : '代销出库单号',name : 'out_no', minWidth : 150 , align : 'left',
							render : function(rowdata, rowindex, value) {
								return '<a href=javascript:update_open("' 
									+ rowdata.group_id 
									+ ',' + rowdata.hos_id 
									+ ',' + rowdata.copy_code 
									+ ',' + rowdata.out_id
									+ '")>'+rowdata.out_no+'</a>';
							}
					   },
			           {display : '计划类型', name : 'bus_type_name',  align : 'left'}, 
			           {display : '摘要', name : 'brief',  align : 'left',width : 120}, 
			           {display : '仓库', name : 'store_name', align : 'left'}, 
			           {display : '领料科室', name : 'dept_name',  align : 'left'	}, 
			           {display : '领料人', name : 'dept_emp_name', align:'left'	}, 
			           {display : '制单人', name : 'maker_name',  align : 'left'}, 
			           {display : '出库日期', name : 'out_date', align : 'left'}, 
			           {display : '确认人', name : 'confirmer_name',  align : 'left'	}, 
			           {display : '确认日期', name : 'confirm_date',  align :'left',width : 90	}, 
			           {display : '状态', name : 'state',  align : 'left',
			        	   render:function(rowdata,rowindex,value){
			        	   if(rowdata.state == 3){
			        		   return "出库确认"
			        	   }
			           }}
			],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatOrderInitAffi.do?isCheck=false',
			width: '100%', height: '70%', checkbox: true, rownumbers:false,
			delayLoad: true,//初始化不加载，默认false
			selectRowButtonOnly:true
		});
		gridManager = $("#maingrid").ligerGetGridManager();
	}
    
  	//查看出库明细
	function update_open(obj){		
		var vo = obj.split(",");
		var paras = 
			"group_id="+vo[0] +"&"+ 
			"hos_id="+vo[1] +"&"+ 
			"copy_code="+vo[2] +"&"+ 
			"out_id="+vo[3] ;
		$.ligerDialog.open({
			title: '查看代销出库明细',
			height: 500,
			width: 900,
			url: 'matOrderInitAffiDetailPage.do?isCheck=false&' + paras.toString(),
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true, top : 1
		});   
    }
  	
	//键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('G', gen);
		hotkeys('C', this_close);
		hotkeys('P', printDate);
	}
	
  	//生成
  	function gen(){
  		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择出库订单！');
		}else{
			var ParamVo =[];
			$(data).each(function (){		
				ParamVo.push(
					this.group_id   +"@"+ 
					this.hos_id   +"@"+ 
					this.copy_code   +"@"+ 
					this.out_id 
				) 
			});
			//alert(ParamVo.toString());
			$.ligerDialog.confirm('确定要生成订单吗?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("genByAffiMatOrderInit.do?isCheck=true",{ParamVo : ParamVo.toString()},function (responseData){
						
						if(responseData.Rows.length > 0){
							var p_data = parent.gridManager.rows;//获取父页面数据
				    		parent.deleteRange(p_data);//删除父页面数据
							//订单材料
							parent.add_rows(responseData.Rows);
							parent.is_addRow();
						}
						this_close();
						
						/* if(responseData.state=="true"){
							parent.query();
						} */
					});
				}
			}); 
		}
  	}
  	//打印
	/* function printDate(){
  		
	} */
	//关闭
	function this_close(){
		frameElement.dialog.close();
	}
	
    </script>
  
</head>
  
<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post"  id="form1" >
		<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" border="0">
	        <tr>
	           
	            <td align="right" class="l-table-edit-td" style="padding-left: 10px;">日期范围：</td>
				<td align="left" class="l-table-edit-td" style="width: 100px;">
					<input class="Wdate" name="begin_date" id="begin_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
				</td>
				<td align="right" class="l-table-edit-td"  style="width: 10px;">至：</td>
				<td align="left" class="l-table-edit-td" style="width: 100px;">
					<input class="Wdate" name="end_date" id="end_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
				</td>
	            
	            <td align="right" class="l-table-edit-td">仓库名称：</td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="store_code" type="text" id="store_code" required="false" ltype="text" validate="{required:true}" />
	            </td>
	            
	            <td align="right" class="l-table-edit-td">领料科室：</td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="dept_code" type="text" id="dept_code" required="false" ltype="text" validate="{required:true}" />
	            </td>
	            
	        </tr> 
	        <tr>
	            <td align="right" class="l-table-edit-td" >摘要：</td>
	            <td align="left" class="l-table-edit-td" colspan="3">
					<input name="brief" type="text" requried="false" id="brief" />
				</td>
	           	
	           	
	           	<td></td>
	           	<td></td>
	        </tr> 
	        
	    </table>
    </form>
    <div style="width: 100%; height: 100%;">
		<div id="maingrid"></div>
		<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%"  style="margin-top: 5px;">
			<tr>	
				<td align="center" class="l-table-edit-td" >
					<button id ="query" accessKey="Q"><b>查询（<u>Q</u>）</b></button>&nbsp;&nbsp;
					<button id ="gen" accessKey="G"><b>生成（<u>G</u>）</b></button>&nbsp;&nbsp;
					<!-- <button id ="print" accessKey="P"><b>打印（<u>P</u>）</b></button>&nbsp;&nbsp; -->
					<button id ="close" accessKey="C"><b>关闭（<u>C</u>）</b></button>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>
