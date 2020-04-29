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
    var grid;
    var gridManager = null;
    var userUpdateStr;
    var stateData = {Rows : [ {
		"id" : "01",
		"text" : "新建"
	}, {
		"id" : "02",
		"text" : "提交"
	}, {
		"id" : "03",
		"text" : "审核"
	} ],
	Total : 2}
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead();	
		loadHotkeys();
		
    });
    //查询
    function  query(){
		grid.options.parms=[];
		grid.options.newPage=1;
		//根据表字段进行添加查询条件
		grid.options.parms.push(
			{name:'bid_code',value:$("#bid_code").val()},
			{name:'bid_makertime',value:$("#bid_makertime").val()},
			{name:'bid_method',value:liger.get("bid_method").getValue()},
			{name:'ven_id',value:liger.get("ven_id").getValue().split(",")[0]},
			{name:'bid_ylwcode',value:$("#bid_ylwcode").val()},
			{name:'bid_state',value:liger.get("bid_state").getValue()},
		    			  
		); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
            columns: [ 
					{ display: '招标编号', name: 'bid_code', align: 'center', width:'8%',
						render : function(rowdata, rowindex,value) {
							return "<a href=javascript:openUpdate('"+rowdata.bid_id+"|"+rowdata.bid_state+"')>"+rowdata.bid_code+"</a>";
						}
					},
					{ display: '供应商', name: 'ven_id',
						textField : 'ven_name', align: 'left', width:'15%',
						editor : {
							type : 'select',
							valueField : 'id',
							textField : 'text',
							url : '../queryHosSupDict.do?isCheck=false',
							keySupport : true,
							autocomplete : true,
						}
					},
					{ display: '定标日期', name: 'bid_calibratedate', type : 'date',
						format : 'yyyy-MM-dd',
						editor : {
							type : 'date',showSelectBox:false
						},
						align: 'center', width:'5%'
					},
	                { display: '招标地址', name: 'bid_tenderaddress', align: 'left', width:'15%'},
                    { display: '招标人', name: 'bid_tenderee', align: 'center',width:'5%'},
                    { display: '申请金额', name: 'bid_value', align: 'right',width:'8%',
                    	render:function(rowdata,rowindex,value){
                    		 return formatNumber(value,'${ass_05055}',1)
			 			}
                    },
                    { display: '招标方式', name: 'bid_method', align: 'center', width:'5%',
                    	render:function(rowdata,rowindex,value){
                       		if(value == '01'){
   			 					return "公开招标";
   			 				}else if(rowdata.state == '02'){
   			 					return "邀请招标";
   			 				}
			 			}
                    },
				  	{ display: '公告媒介', name: 'bid_notice', align: 'left',width:'10%'},
				  	{ display: '公告日期', name: 'bid_noticedate', align: 'center',width:'5%',
				  		type : 'date',
						format : 'yyyy-MM-dd',
						editor : {
							type : 'date',showSelectBox:false
						}
				  	},
				  	{ display: '招标联系电话', name: 'bid_phone', align: 'left',width:'7%'},
				  	{ display: '一链网招标编号', name: 'bid_ylwcode', align: 'left',width:'8%'},
				 	{ display: '状态', name: 'bid_state', align: 'left',width:'5%',
			 			render:function(rowdata,rowindex,value){
							if (rowdata.bid_state !='01') {//不是新建  不允许 编辑 供应商/定标日期
							
								rowdata.notEidtColNames.push("ven_id");
								rowdata.notEidtColNames.push("bid_calibratedate");
							}
			 				if(rowdata.bid_state == '01'){
			 					return "新建";
			 				}else if(rowdata.bid_state == '02'){
			 					return "提交";
			 				}else if(rowdata.bid_state == '03'){
			 					return "审核";
			 				}else{
			 					return "";
			 				}
			 			}
			 		},
				  	{ display: '招标文件', name: 'file_path', align: 'left',width:'8%',
			 			render: function(rowdata,index,value){
		            		  if(!rowdata.file_path){
		            			  return "<a href=javascript:openFlie("+rowdata.bid_id+")><font><b>招标文件</b></font></a>";
		            		  }else{
		            			  var array = rowdata.file_path.split('/');
			            		  var name = array[array.length-1];
			                      return '<a href='+value+' download='+name+'>文件下载</a>';
		            		  }
	                 	  }
			 		}
				 	],
                    dataAction: 'server',dataType: 'server',usePager:true,url:'queryAssTrendCheck.do?isCheck=false',
                    width: '100%',  height: '100%', checkbox: true,rownumbers:true, allowAdjustColWidth:false,enabledEdit:true,isAddRow:false,
                    onToFirst: updateDateExist,//翻页之前判断当前页是否有变更数据 （若有变更数据，提示其保存）
    				onToPrev: updateDateExist,//翻页之前判断当前页是否有变更数据 （若有变更数据，提示其保存）
    				onToNext: updateDateExist,//翻页之前判断当前页是否有变更数据 （若有变更数据，提示其保存）
    				onToLast: updateDateExist,//翻页之前判断当前页是否有变更数据（若有变更数据，提示其保存）
                    heightDiff: 0,
                    toolbar: { items: [
                    	{ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' },
	                   	{ line:true },
	                   	{ text: '保存（<u>S</u>）', id:'save', click: save, icon:'save' },
	  	                { line:true },
	  	              	{ text: '删除（<u>D</u>）', id:'delete', click: deleteAssTend, icon:'delete' },
	  	                { line:true },
	                   	{ text: '提交（<u>T</u>）', id:'send', click: sendAssTrend, icon:'next' },
	  	                { line:true },
	  	              	{ text: '取消提交（<u>C</u>）', id:'unsend', click: unSendAssTrend, icon:'before' },
	  	                { line:true },
	                   	{ text: '审核（<u>A</u>）', id:'audit', click: auditAssTrend, icon:'audit' },
	  	                { line:true },
	  	             	{ text: '消审（<u>U</u>）', id: 'unAudit', click: unAuditAssTrend, icon: 'unaudit' }
  						]}
               });

         gridManager = $("#maingrid").ligerGetGridManager();
    }
    
  //翻页之前判断当前页是否有变更数据（若有变更数据，提示其保存）
	function updateDateExist() {
		var data = grid.changedCells;
		if (!isObjEmpty(data)) {

			$.ligerDialog.warn('数据有变更【<span style="color:red">不支持跨页保存数据,请先保存变更数据(若不想保存变更数据,请刷新页面确定后再操作)</span>】');

			return false;
		}
	}
	function isObjEmpty(obj) {      //判断对象是否为空对象
		for (var i in obj) {
			return false;
		}
		return true;
	}

    //保存
    function save(){
    	var changeData = grid.changedCells;

		if (isObjEmpty(changeData)) {

			$.ligerDialog.warn('数据没有变更,无需保存');

			return false;
		}

		var data = grid.getUpdated();
		if (data.length == 0) {

			$.ligerDialog.error('数据没有变更,无需保存');

		} else {
			var ParamVo = [];

			$(data).each(function () {
				ParamVo.push(
					this.bid_id + "|" +
					this.ven_id + "|" +
					this.bid_calibratedate 
				)
			});
			ajaxJsonObjectByUrl("saveAssTendCheckInfo.do?isCheck=false", { ParamVo: ParamVo.toString() }, function (responseData) {

				if (responseData.state == "true") {
					query();
				}
			});
		}
 
    }
    
    //删除中标信息
	function deleteAssTend(){
		var data = gridManager.getCheckedRows();
		var ids = "";
		var str = '';
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
			return false;
		}
		
		$.each(data, function (){
			if(this.bid_state != '01'){
				str += this.bid_code +",";
			}
			ids += this.bid_id +",";
		})
		
		if(str != ''){
			$.ligerDialog.error("【"+str.substr(0, str.length - 1)+"】不是新建状态不能删除！");
			return false;
		}
		$.ligerDialog.confirm('确定删除所选招标信息吗?', function (yes){
			if(yes){
				ajaxJsonObjectByUrl("deleteAssTrendCheckInfo.do?isCheck=false",{ParamVo: ids.substr(0, ids.length-1)},function (res){
					if(res.state=="true"){
						query();
					}
				});
			}
		}); 
	}
    
  //提交
	function sendAssTrend(){
		var data = gridManager.getCheckedRows();
		var ids = "";
		var str1 = '';//记录不是新建状态数据 返回
		var str2 = '';//记录未填写中标信息数据 返回
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
			return false;
		}
		
		$.each(data, function (){
			if(this.bid_state != '01'){
				str1 += this.bid_code +",";
			}
			if(this.bid_winflag != '1'){
				str2 += this.bid_code +",";
			}
			ids += this.bid_id + '@02'+",";
		})
		
		if(str1 != ''){
			$.ligerDialog.error("【"+str1.substr(0, str1.length - 1)+"】不是新建状态不能提交！");
			return false;
		}
		if(str2 != ''){
			$.ligerDialog.error("【"+str2.substr(0, str2.length - 1)+"】未保存中标信息不能提交！");
			return false;
		}
		$.ligerDialog.confirm('确定提交所选招标吗?', function (yes){
			if(yes){
				ajaxJsonObjectByUrl("sendAssTrend.do?isCheck=false",{ParamVo: ids.substr(0, ids.length-1)},function (res){
					if(res.state=="true"){
						$.each(data, function (){
							query();
						})
					}
				});
			}
		}); 
	}
	//取消提交
	function unSendAssTrend(){
		var data = gridManager.getCheckedRows();
		var ids = "";
		var str = '';
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
			return false;
		}
		
		$.each(data, function (){
			if(this.bid_state != '02'){
				str += this.bid_code +",";
			}
			ids += this.bid_id + '@01'+",";
		})
		
		if(str != ''){
			$.ligerDialog.error("【"+str.substr(0, str.length - 1)+"】不是提交状态不能取消提交！");
			return false;
		}
		$.ligerDialog.confirm('确定取消提交所选招标吗?', function (yes){
			if(yes){
				ajaxJsonObjectByUrl("unSendAssTrend.do?isCheck=false",{ParamVo: ids.substr(0, ids.length-1)},function (res){
					if(res.state=="true"){
						$.each(data, function (){
							query();
						})
					}
				});
			}
		}); 
	}
  //审核
	function auditAssTrend(){
		var data = gridManager.getCheckedRows();
		var ids = "";
		var str = '';
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
			return false;
		}
		
		$.each(data, function (){
			if(this.bid_state != '02'){
				str += this.bid_code+",";
			}
			ids += this.bid_id +'@03'+",";
		})
		
		if(str != ''){
			$.ligerDialog.error("【"+str.substr(0, str.length - 1)+"】不是提交状态不能审核！");
			return false;
		}
		$.ligerDialog.confirm('确定审核所选供招标吗?', function (yes){
			if(yes){
				ajaxJsonObjectByUrl("auditAssTrend.do?isCheck=false",{ParamVo: ids.substr(0, ids.length-1)},function (res){
					if(res.state=="true"){
						$.each(data, function (){
							query();
						})
					}
				});
			}
		}); 
	}
	
	//消审
	function unAuditAssTrend(){
		var data = gridManager.getCheckedRows();
		var ids = "";
		var str = '';
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
			return false;
		}
		
		$.each(data, function (){
			if(this.bid_state != '03'){
				str += this.bid_code +",";
			}
			ids += this.bid_id + '@02'+",";
		})
		
		if(str != ''){
			$.ligerDialog.error("【"+str.substr(0, str.length - 1)+"】未审核不能消审！");
			return false;
		}
		$.ligerDialog.confirm('确定消审所选招标吗?', function (yes){
			if(yes){
				ajaxJsonObjectByUrl("unAuditAssTrend.do?isCheck=false",{ParamVo: ids.substr(0, ids.length-1)},function (res){
					if(res.state=="true"){
						$.each(data, function (){
							query();
						})
					}
				});
			}
		}); 
	} 
	
	function openFlie(bid_id){
		parent.$.ligerDialog.open({
			title: '招标文件',
			height: 600,
			width: 1000,
			url: 'hrp/ass/asstend/assTendFilePage.do?isCheck=false&bid_id='+bid_id,
			modal: true, showToggle: false, showMax: false, showMin: false, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量

		}); 
	 
 	};
	
	function openUpdate(obj) {

		  var bid_id=obj.split("|")[0];
		  var bid_state=obj.split("|")[1];
			parent.$.ligerDialog.open({
				title: '设备招标单修改',
				height: $(window).height(),
				width: $(window).width(),
				url: 'hrp/ass/asstend/assTendDetailPage.do?isCheck=false&bid_id=' + bid_id+"&bid_state="+bid_state,
				modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
				parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量

			});
			
		}
    function loadDict(){
            //字典下拉框
            //供货单位
    		autocomplete("#ven_id", "../queryHosSupDict.do?isCheck=false", "id","text", true, true);
            
    		//autodate("#bid_makertime","yyyy-MM-dd");
    		
    		autoCompleteByData("#bid_state", stateData.Rows, "id", "text", true, true,'',false);
    		
    		$("#ven_id").ligerTextBox({width:180});
    		$("#bid_code").ligerTextBox({width:180});
            $("#bid_makertime").ligerTextBox({width:180});
            $("#bid_method").ligerTextBox({width:180});
            $("#bid_ylwcode").ligerTextBox({width:180});
            $("#bid_state").ligerTextBox({width:180});
         }  
    //键盘事件
	  function loadHotkeys() {

		hotkeys('Q', query);
		hotkeys('S', save);
		hotkeys('D', deleteAssTend);
		hotkeys('T', sendAssTrend);
		hotkeys('C', unSendAssTrend);
		hotkeys('A', auditAssTrend);
		hotkeys('U', unAuditAssTrend);
	 }
   
    </script> 

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">招标编号：</td>
            <td align="left" class="l-table-edit-td"><input name="bid_code" type="text" id="bid_code" ltype="text" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">制单日期：</td>
            <td align="left" class="l-table-edit-td"><input class="Wdate" name="bid_makertime" type="text" id="bid_makertime" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">招标方式：</td>
            <td align="left" class="l-table-edit-td"><input name="bid_method" type="text" id="bid_method" ltype="text" /></td>
            <td align="left"></td>
        </tr> 
         <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">供应商：</td>
            <td align="left" class="l-table-edit-td"><input name="ven_id" type="text" id="ven_id" ltype="text" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">一链网编号：</td>
            <td align="left" class="l-table-edit-td"><input name="bid_ylwcode" type="text" id="bid_ylwcode" ltype="text" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">招标方式：</td>
            <td align="left" class="l-table-edit-td"><input name="bid_state" type="text" id="bid_state" ltype="text" /></td>
            <td align="left"></td>
        </tr> 
    </table>

	<div id="maingrid"></div>
	
</body>
</html>
