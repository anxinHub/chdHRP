<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script src="<%=path%>/lib/hrp/mat/mat.js"	type="text/javascript"></script>
<script src="<%=path%>/lib/stringbuffer.js"></script>
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
    });
    //查询
    function  query(){
		grid.options.parms=[];
		grid.options.newPage=1;
        //根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'begin_out_date',
			value : $("#begin_out_date").val()
		});
		grid.options.parms.push({
			name : 'end_out_date',
			value : $("#end_out_date").val()
		}); 
		grid.options.parms.push({
			name : 'store_id',
			value : liger.get("store_id").getValue() == null ? "" : liger.get("store_id").getValue().split(",")[0]
		}); 
		grid.options.parms.push({
			name : 'store_no',
			value : liger.get("store_id").getValue() == null ? "" : liger.get("store_id").getValue().split(",")[1]
		}); 
		grid.options.parms.push({
			name : 'dept_id',
			value : liger.get("dept_id").getValue() == null ? "" : liger.get("dept_id").getValue().split(",")[0]
		}); 
		grid.options.parms.push({
			name : 'dept_no',
			value : liger.get("dept_id").getValue() == null ? "" : liger.get("dept_id").getValue().split(",")[1]
		}); 
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [{
					display: '出库单号', name: 'out_no', align: 'left',width:150,
					/* render : function(rowdata, rowindex, value) {
						return '<a href=javascript:update_open("' 
							+ rowdata.group_id 
							+ ',' + rowdata.hos_id 
							+ ',' + rowdata.copy_code 
							+ ',' + rowdata.out_id
							+ '")>'+rowdata.out_no+'</a>';
					} */
				}, { 
		 			display: '业务类型', name: 'bus_type_name', align: 'left',width:150,
		 		}, { 
		 			display: '仓库', name: 'store_name', align: 'left',width:180,
		 		}, { 
		 			display: '领料科室', name: 'dept_name', align: 'left',width:100,
		 		}, { 
		 			display: '领料人', name: 'dept_emp_name', align: 'left',width:90,
		 		}, { 
		 			display: '出库日期', name: 'out_date', align: 'left',width:90,
		 		}, { 
		 			display: '制单人', name: 'maker_name', align: 'left',width:90,
		 		}, { 
		 			display: '确认日期', name: 'confirm_date', align: 'left',width:90,
		 		},  { 
		 			display: '确认人', name: 'confirmer_name', align: 'left',width:90,
		 		},{ 
		 			display: '状态', name: 'state', align: 'center',width:90,
		 				render:function(rowdata,index,value){
		 					if(rowdata.state == 1){
								return "未审核";
							}else if(rowdata.state == 2){
								return "已审核";
							}else{
								return "已出库";
							}
		 				}
		 		} ],
		 		
		 		dataAction: 'server',dataType: 'server',usePager:true,url:'queryAffiOut.do?isCheck=false?state=3',
				width: '99%', height: '100%', checkbox: true,rownumbers:true,frozen:false,
				delayLoad: true,//初始化不加载，默认false
				selectRowButtonOnly:true,heightDiff: 0,
				showTitle: true,detail: { onShowDetail: showDetail, height:'auto',reload: true ,single:true },//代销出库单明细
				toolbar: { items: [
					{ text: '查询（<u>Q</u>）', id:'query', click: query, icon:'search' },
					{ line:true },
					{ text: '确定（<u>A</u>）', id:'add', click: comfirm, icon:'add' },
					{ line:true },
					{ text: '关闭（<u>C</u>）', id:'close', click: this_close, icon:'close' }
			]}
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    //键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
		hotkeys('A', add);
		hotkeys('C', this_close);
	}
    
	function showDetail(row, detailPanel,callback)
    {
       var affiGrid = document.createElement('div'); 
        $(detailPanel).append(affiGrid);
      	$(affiGrid).css('margin',10).ligerGrid({
            columns:
                     [
                     { display: '材料编码', name: 'inv_code',width:150},
                     { display: '材料名称', name: 'inv_name',width:180},
                     { display: '计量单位', name: 'unit_name',width:104 },
                     { display: '批号', name: 'batch_no',width:120 },
                     { display: '单价', name: 'price' ,width:100,align : 'right',
                    	 render : function(rowdata, rowindex, value) {
								return formatNumber(rowdata.price, '${p04006}', 1);
							}
                     },
                     { display: '数量', name: 'amount' ,width:100},
                     { display: '金额', name: 'amount_money',width:100,align : 'right',
                    	 render : function(rowdata, rowindex, value) {
								return formatNumber(rowdata.amount_money, '${p04005}', 1);
							}
                     },
			  	     { display: '有效日期', name: 'inva_date',width:80},
			  	   	 { display: '供应商', name: 'sup_name',width:240} ,
		 	  		 { display: '生产商', name: 'fac_name',width:240}
                     ], 
                     showToggleColBtn: false,width: '98%',dataAction : 'server',dataType : 'server', usePager:false,
                     showTitle: false,columnWidth: 80,url : 'queryMatAffiDetail.do?isCheck=false&out_id='+row.out_id+'&store_id='+row.store_id,
 					 onAfterShowData: callback,frozen:false
         });
    }
	/* //修改
	function update_open(obj){		
		var vo = obj.split(",");
		var paras = 
			"group_id="+vo[0] +"&"+ 
			"hos_id="+vo[1] +"&"+ 
			"copy_code="+vo[2] +"&"+ 
			"out_id="+vo[3] ;
		alert(paras);
		$.ligerDialog.open({
			title: '订单修改',
			height: 500,
			width: 1000,
			url: '../../order/init/matOrderInitUpdatePage.do?isCheck=false&' + paras.toString(),
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true, top : 1
		});   
    } */
    //确定 
  function comfirm(){
	  var data = gridManager.getCheckedRows();
	  if(data.length == 0){
		  $.ligerDialog.error('请选择行');
	  }else{
		  var ParamVo =[];
			$(data).each(function (){		
				ParamVo.push(
					this.group_id +"@"+
					this.hos_id +"@"+
					this.copy_code +"@"+
					this.out_id +"@"+
					this.year +"@"+
					this.month +"@"+
					this.bus_type_code +"@"+
					this.store_id +"@"+
					this.store_no +"@"+
					this.brief +"@"+
					this.dept_id +"@"+
					this.dept_no +"@"+
					(this.dept_emp ? this.dept_emp : "" ) + "@" + 
					this.store_id +"@"+ 
					this.out_no 
				) 
			});
	        ajaxJsonObjectByUrl("affiDateOld.do?isCheck=false",{ParamVo : ParamVo.toString()},function(responseData){
	            if(responseData.state=="true"){
	                parent.query();
	            }
	        });
	  }
	  
    }
	function this_close(){
		frameElement.dialog.close();
	}
   
    function loadDict(){
		//字典下拉框
		autocomplete("#store_id", "../../queryMatStore.do?isCheck=false", "id", "text", true, true, "", true);
		//采购部门
    	autocomplete("#dept_id", "../../queryMatDeptDict.do?isCheck=false", "id", "text", true, true, "", true);
    	 $("#begin_out_date").ligerTextBox({width:160});
    	 $("#end_out_date").ligerTextBox({width:160});
	}  
	
	</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" >
        <tr>
        	 <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>仓库<font color="red">*</font>:</b></td>
            <td align="left" class="l-table-edit-td"><input name="store_id" type="text" id="store_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  ><b>出库日期<font color="red">*</font>:</b></td>
            <td align="left" class="l-table-edit-td" width="160px;">
            	<input class="Wdate" name="begin_out_date" id="begin_out_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
            </td>
	        <td align="left"  class="l-table-edit-td" width="15px;">至：</td>
	        <td align="left"  class="l-table-edit-td" width="160px;">
	        	<input class="Wdate" name="end_out_date" id="end_out_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
	        </td>
			<td align="right" class="l-table-edit-td"  ><b>领料科室<font color="red">*</font>:</b></td>
            <td align="left" class="l-table-edit-td">
            	<input name="dept_id" type="text" id="dept_id" ltype="text"  validate="{required:true}" />
            </td>
            
        </tr> 
    </table>
	<div id="maingrid"></div>
</body>
</html>
