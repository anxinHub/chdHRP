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
<script type="text/javascript">
    var mainGrid;
    var data ;
    var gridManager = null;
    var grid ;
    var userUpdateStr;
    var  init=0 ;
    
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
    	mainGrid.options.parms=[];
    	mainGrid.options.newPage=1;
        //根据表字段进行添加查询条件
		mainGrid.options.parms.push({
			name : 'sup_id',
			value : liger.get("sup_id").getValue() == null ? "" : liger.get("sup_id").getValue().split(",")[0]
		});
		mainGrid.options.parms.push({
			name : 'dept_id',
			value : liger.get("dept_id").getValue() == null ? "" : liger.get("dept_id").getValue().split(",")[0]
		}); 
		mainGrid.options.parms.push({
			name : 'stocker',
			value : liger.get("stocker").getValue() == null ? "" : liger.get("stocker").getValue().split(",")[0]
		}); 
		mainGrid.options.parms.push({
			name : 'bus_type_code',
			value : liger.get("bus_type_code").getValue() == null ? "" : liger.get("bus_type_code").getValue().split(",")[0]
		}); 
		
		mainGrid.options.parms.push({
			name : 'store_id',
			value : liger.get("store_id").getValue() == null ? "" : liger.get("store_id").getValue().split(",")[0]
		}); 
		mainGrid.options.parms.push({
 			name : 'year',	value : $("#year").val()
 		});
		mainGrid.options.parms.push({
 			name : 'month',	value : $("#month").val()
 		});
		mainGrid.options.parms.push({
			name : 'in_no',
			value : $("#in_no").val()
		});
		mainGrid.options.parms.push({
			name : 'brief',
			value : $("#brief").val()
		});
		mainGrid.options.parms.push({
 			name : 'init',	value : init
 		});
    	//加载查询条件
    	mainGrid.loadData(mainGrid.where);
     }

    function loadHead(){
    	mainGrid = $("#maingrid").ligerGrid({
			columns: [
			       { display: '入库ID', name: 'in_id', align: 'left',frozen:true
		 				},
			       { display: '入库单号', name: 'in_no', align: 'left',width:140
		 				},
                   { display: '仓库', name: 'store_name', align: 'left',width:100
				 		},
				   { display: '供应商', name: 'sup_name', align: 'left',width:200
				 		},
                   { display: '申请科室', name: 'dept_name', align: 'left',width:80
				 		},
                   { display: '采购员', name: 'emp_name', align: 'left',width:80
				 		},
				   { display: '入库时间', name: 'confirm_date', align: 'left',width:80
				  		},
				   { display: '单据金额', name: 'payable_money', align: 'right',width:100,
				  			render:function(rowdata,rowindex,value){
	                    		return formatNumber(rowdata.payable_money, '${p08005 }', 1);
	                    	}
				  		},
				   { display: '制单人', name: 'maker_name', align: 'left',width:80
					  	}, 
			  	   { display: '审核人', name: 'checker_name', align: 'left',width:80
			  			}, 
			  	   { display: '确认人', name: 'confirmer_name', align: 'left',width:80
			  			}
			  	  
		 		 ],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMedCommonInBill.do?isCheck=false',
			width: '98%', height: '100%', checkbox: true,rownumbers:true,frozen:false,
			delayLoad: true,//初始化不加载，默认false
			selectRowButtonOnly:true,heightDiff: 0, allowAdjustColWidth : false ,
			showTitle: true,detail: { onShowDetail: inDetail,height:'auto', reload: true ,single:true },//入库单明细
			toolbar: { items: [
						{ text: '查询（<u>Q</u>）', id:'search', click: query, icon:'search' },
						{ line:true },
						{ text: '确认（<u>A</u>）', id:'add', click: add_open, icon:'add' },
						{ line:true },
						{ text: '<input type="checkbox" id="initOnly" />只显示期初送货单', id:'', click: setInit,icon:'' },
					]}
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    function inDetail(row, detailPanel,callback){
    	var sup_id = liger.get("sup_id").getValue() == null ? "" : liger.get("sup_id").getValue().split(",")[0];
    	data = row;
        var detailGrid = document.createElement('div'); 
        $(detailPanel).append(detailGrid);
        grid = $(detailGrid).css('margin',10).ligerGrid({
            columns:
              [
              { display: '药品编码', name: 'inv_code',width:100},
              { display: '药品名称', name: 'inv_name',width:150},
              { display: '计量单位', name: 'unit_name',width:70 },
              { display: '规格型号', name: 'inv_model',width:200,align: 'left'},
              { display: '批号', name: 'batch_no',width:100 },
              { display: '条形码', name: 'sn' ,width:180},
              { display: '单价', name: 'price',align: 'left',width:110 ,
            	 	render:function(rowdata,rowindex,value){
	              		return formatNumber(rowdata.price, '${p08006 }', 1);
	              	}
            	  },
              { display: '数量', name: 'amount',width:100},
              { display: '金额', name: 'payable_money',align: 'right', width:110,
	            	  	render:function(rowdata,rowindex,value){
		              		return formatNumber(rowdata.payable_money, '${p08006 }', 1);
		              	}
              		}
              ], 
              dataAction: 'server',dataType: 'server',checkbox: true, usePager:false,width: '98%',
              selectRowButtonOnly:true,delayLoad: false,//初始化不加载，默认false
              isScoll:true ,allowAdjustColWidth : false , frozen : false ,isAddRow:false,
              data:row.detail,fixedCellHeight:true,
              //url:'queryMedInDetail.do?isCheck=false&in_id='+row.in_id+'&bill_id='+row.bill_id+'&init='+init+'&sup_id='+sup_id,
  			  onAfterShowData: callback , onSelectRow  : detailData , onUnSelectRow :removeDetail
        });  
    } 
    //键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
		hotkeys('A', add_open);
	}
  
    
	function setInit(){
		if($("#initOnly").prop("checked") == true){
    		init = "1" ;
    	}else{
    		init = "0" ;
    	}
    	query();
    }
	 //勾选明细后  选中主表格记录
    function detailData(rowdata,rowid,rowobj){
    	var bill_money = 0;//存储  主表格发票金额
    	var detail =grid.getSelectedRows();
    	if(detail.length > 0){
   		$.each(detail,function(index,content){
   			 bill_money += content.bill_money;
	   		 })
	   	}
    	data.bill_money = bill_money;
    	data.detail={"Rows":detail,"Total":detail.length};
	   	if(!gridManager.isSelected(data)){
	   		 gridManager.select(data);
	   	}
    }
    // 取消勾选明细后  判断是否取消选中主表格记录
    function removeDetail(rowdata,rowid,rowobj){
    	 var bill_money = 0;//存储  主表格发票金额
	   	 var detail = grid.getSelectedRows();
	   	 if(detail.length > 0){
	   		$.each(detail,function(index,content){
	   			 bill_money += content.bill_money;
		   	})
		 }
		 data.bill_money = bill_money;
	   	 data.detail={"Rows":detail,"Total":detail.length};
	   	 if(detail.length == 0){
	   		 gridManager.unselect(data);
	   	 }
    }
    
    function add_open(){
    	
    	 var rows = mainGrid.getSelectedRows(); 
         if(rows.length == 0){
        	 $.ligerDialog.error('请选择行数据!');
             return;
         }else{
        	/*  var in_nos ='';
         	 for (var i = 0; i < rows.length; i++){
         		 if(rows[i].detail == null){
         			 in_nos += rows[i].in_no +"," ;
         		 }
              }
         	if(in_nos != ""){
				$.ligerDialog.error('添加失败！'+in_nos+'<span style="color:red">入库单未选择明细数据,请选择明细数据后再添加</span>');
				return;
			}else{ */
				parent.inMainGrid.addRows(rows);
				parent.$.ligerDialog.close();
	         	parent.$(".l-dialog,.l-window-mask").remove();
			//}
         }
	}
    
    function loadDict(){
		//字典下拉框
		
		//科室：当前用户有权限的职能科室列表（HOS_DEPT_DICT 中is_stop=0的职能科室列表）
		autocomplete("#dept_id", "../../querySignedDept.do?isCheck=false", "id", "text", true, true);
		//供应商下拉框
		autocomplete("#sup_id", "../../queryHosSupDict.do?isCheck=false", "id", "text", true, true,'',false,'',240);
		liger.get("sup_id").setValue('${sup_id},${sup_no}');
		liger.get("sup_id").setText('${sup_code} ${sup_name}');
		//采购员下拉框
		autocomplete("#stocker", "../../queryMedStoctEmpDict.do?isCheck=false", "id", "text", true, true);
		//业务类型下拉框
		autocomplete("#bus_type_code", "../../queryMedBusType.do?isCheck=false", "id", "text", true, true);
		//仓库下拉框
		autocomplete("#store_id", "../../queryMedStore.do?isCheck=false", "id", "text", true, true,'',false,'',240);
        $("#sup_id").ligerTextBox({width:240,disabled:true});
        $("#dept_id").ligerTextBox({width:160});
        $("#year").ligerTextBox({width:240});
        $("#month").ligerTextBox({width:160});
        $("#in_no").ligerTextBox({width:160});
        $("#brief").ligerTextBox({width:500});
	}  
	
	</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar" ></div>
	<form name="form1" method="post"  id="form1" >
		 <table cellpadding="0" cellspacing="0" class="l-table-edit" >
			<tr>
				<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>供应商：</b></td>
	            <td align="left" class="l-table-edit-td"><input name="sup_id" type="text" id="sup_id"  ltype="text" disabled="disabled" validate="{required:true,maxlength:20}" /></td>
	            <td align="left"></td>
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>科室：</b></td>
	            <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
	            <td align="left"></td>
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>采购员：</b></td>
	            <td align="left" class="l-table-edit-td"><input name="stocker" type="text" id="stocker" ltype="text" validate="{required:true,maxlength:20}" /></td>
	            <td align="left"></td>
	        </tr> 
	        <tr>
	        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>起始年月<font color="red">*</font>:</b></td>
	            <td align="left" class="l-table-edit-td">
	            	<input class="Wdate" name="year" type="text" id="year" ltype="text"  onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" validate="{required:true,maxlength:20}" />
	            </td>
	            <td align="left"></td>
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>起始年月<font color="red">*</font>:</b></td>
	            <td align="left" class="l-table-edit-td">
	            	<input class="Wdate" name="month" type="text" id="month" ltype="text"  onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" validate="{required:true,maxlength:20}" />
	            </td>
	            <td align="left"></td>
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>业务类型<font color="red">*</font>:</b></td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="bus_type_code" type="text" id="bus_type_code" ltype="text" validate="{required:true,maxlength:20}" />
	            </td>
	            <td align="left"></td>
	        </tr> 
	        <tr>
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>仓库：</b></td>
	            <td align="left" class="l-table-edit-td"><input name="store_id" type="text" id="store_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
	            <td align="left"></td>
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>入库单号：</b></td>
	            <td align="left" class="l-table-edit-td"><input name="in_no" type="text" id="in_no" ltype="text" validate="{required:true,maxlength:20}" /></td>
	            <td align="left"></td>
	            <td align="left"></td>
	            <td align="left"></td>
	            <td align="left"></td>
	        </tr> 
	         <tr>
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>摘要：</b></td>
	            <td align="left" class="l-table-edit-td" colspan="7"><input name="brief" type="text" id="brief" ltype="text" validate="{required:true,maxlength:20}" /></td>
	            <td align="left"></td>
	        </tr> 
	    </table>
    </form>
	<div id="maingrid"></div>
</body>
</html>
