<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">
	var grid;
	var gridManager = null;
	var upData = {}
	$(function(){
		loadHead();
	})
		//导航栏
	var toolbatData = {
			items: [
				{ text: '查询', id: 'query', icon: 'search', click: queryTarget },
				{ line: true },
				{ text: '添加', id: 'add', icon: 'add', click: addTarget },
				{ line: true },
				{ text: '删除', id: 'delete', icon: 'delete', click: deleteTarget },
				{ line: true },
				{ text: '打印', id: 'print', icon: 'print', click: printTarget }
			]
	}
	//表格内容
	var gridDataAly = {
		columns: [
			{ display: '因素指标编码', name: 'FAC_CODE', align: 'left', width: 100 ,
				render:function(rowdata){
					var cd = rowdata.FAC_CODE;
					upData[cd] = rowdata
					var ret = ' <a href=javascript:updataTargetService("'+ cd +'")>'+rowdata.FAC_CODE+'</a>'
              		return ret
       		}},
			{ display: '因素指标名称', name: 'FAC_NAME',align:'left'},
			{ display: '指标单位', name: 'ZB_UNIT',align:'left'} ,
			{ display: '状态', name: 'IS_STOP',align:'left' },
			{ display: '备注', name: 'NOTE' ,align:'left'}
		],  
		checkbox: true,
        selectRowButtonOnly : true,
        delayLoad:true,
        width: '100%', 
        height: '100%', 
        isChecked: f_isChecked,
        onCheckRow : f_checkRow_updata,
        toolbar: toolbatData,
        
        dataAction : 'server',
        dataType : 'server',
        usePager : false,
        isAddRow:false,
		url : 'queryElementAnalyze.do',
		rownumbers : true, 
		frozen : false ,
		heightDiff:30,
	}
		
	function loadHead(){
		//添加表格
		grid = $("#maingrid").ligerGrid(gridDataAly);
		gridManager = $("#maingrid").ligerGetGridManager();
	}
	//表格初始化选择
	function f_isChecked(rowdata){
		if (rowdata.FAC_CODE.indexOf('AN') == 0)
			return true;
		return false;
     }
	// 修改 行点击事件  updata
	function f_checkRow_updata(a,b,c,d,e){
	}
	
	
	//修改
	function updataTargetService(addData){
		upData[addData]
    	var vo = addData.split("|");
		var parm = "bank_number="+vo[0]   +"&group_id="+ vo[1]   +"&hos_id="+ vo[2];
		var FAC_CODE="FAC_CODE="+ vo[0]
		var url = 'hrp/acc/accanalyze/elementAnalyze/elementUpdataPage.do?isCheck=false&'+FAC_CODE
		add_open("updata",url,upData[addData])
	}
	
	//toolbar 增加 
	function addTarget(addIndex){
		//新增window 
		var url = 'hrp/acc/accanalyze/elementAnalyze/elementAddPage.do?isCheck=false'
		var data ={}
		add_open("add",url,{})
	}
		
	//toolbar 查询 
	function queryTarget(){
		grid.options.parms = [];
		grid.options.newPage = 1;
		grid.options.parms.push({name : 'FAC_CODE', value : $("#elementInfo").val() });
		//加载查询条件
		grid.loadData(grid.where);
	 }
		
	//toolbar 删除 	
	function deleteTarget(){
		var selectedArr = grid.selected
		var selectedDatas ={'selectedDatas':selectedArr} ;
		if(selectedArr.length>0){
			for(var i=0;i<selectedArr.length;i++){
				
				$.ajax({
					url: 'hrp/acc/accanalyze/elementAnalyze/deleteElementAnalyze.do?',
					data: selectedArr[i],
					type: "post",
					dataType: "json",
					async: false,
					success: function (data) {
						if(data.isSuc){
							$.ligerDialog.success(data.msg);
						}else{
							$.ligerDialog.error(data.msg);
						}
				      },
					error: function x(s) {
						$.ligerDialog.success('删除失败');
					}
				})
			}
			queryTarget()
		}else{
			$.ligerDialog.success('先选择数据');
		}
		
	}
	
	//toolbar 打印 
	function printTarget(){
		if(grid.getData().length==0){
			$.ligerDialog.error("请先查询数据！");
			return;
		}
		var heads={};
		var printPara={
			title: "因素指标",//标题
			columns: JSON.stringify(grid.getPrintColumns()),//表头
			class_name: "com.chd.hrp.acc.service.AccElementAnalyzeService",
			method_name: "queryAccElementPrint",
			bean_name: "accElementAnalyzeService",
			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
		};

		$.each(grid.options.parms,function(i,obj){
			printPara[obj.name]=obj.value;
		});

		officeGridPrint(printPara);
	}
	
	//修改增加调后台 
	function addUpdataAjaxService(addData,url,dialog){
		var isSuccess
		var result ={}
		$.ajax({
			url: url,
			data: addData,
			type: "post",
			dataType: "json",
			async: false,
			success: function (data) {
				result.isSuccess = true
				result.data = data
		      },
			error: function (error) {
				result.isSuccess = false
				result.error = error
			}
		})
		return result 	
	}
		

		
	//打开windowd，修改添加 
    function add_open(addUpdata,url,objdata){
		var titleName = '添加'
		if(addUpdata=="add"){
			titleName = '添加'
		}else if(addUpdata=="updata"){
			titleName= '修改'
		}
    	parent.$.ligerDialog.open({
    		
    		height: 480,width: 660, title:'添加',
    		modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
			url : url,data:objdata,		
			buttons : [{
				text : '保存',
				onclick : function (item, dialog) {
					var forms = dialog.frame.$("#acc_element_sf").serialize();
					var coderule = dialog.frame.$("#acc_element_sf").html();
					coderuleArr = coderule.split("-");
					//dialog.frame.addPaperIncome(forms);
					var dataObject ={}
					//编码规则 
					dataObject.CODE_RULER = dialog.frame.$("#target_ruler_input").val()
					//指标编码
					dataObject.FAC_CODE = dialog.frame.$("#target_code_input").val();
					//名称
					dataObject.FAC_NAME = dialog.frame.$("#target_name_input").val();
					//指标单位
					dataObject.ZB_UNIT = dialog.frame.$("#target_unit_input").val();
					//取数公式 
					dataObject.FMA_EN = dialog.frame.$("#fma_en").val();
					dataObject.FMA_CN = dialog.frame.$("#fma_cn").val();
					//备注 
					dataObject.NOTE = dialog.frame.$("#target_note_input").val();
					//状态 
					dataObject.IS_STOP = dialog.frame.$("#target_status_select").val()
					dataObject.IS_LAST = 1 ;
	
					if(dataObject.FAC_NAME == null || dataObject.FAC_NAME == ""){
						dialog.frame.$.ligerDialog.error("必填项不可为空!");
						return false;
					}else if(dataObject.FAC_NAME.length>50){
						dialog.frame.$.ligerDialog.error("名称字符过长!");
					}
					if(!(dataObject.NOTE == null || dataObject.NOTE == "")&&dataObject.NOTE.length>100){
						dialog.frame.$.ligerDialog.error("备注字符过长!");
						return false;
					}
					if(dataObject.IS_LAST == null || dataObject.FAC_NAME == ""){
						dialog.frame.$.ligerDialog.error("必填项不可为空!");
						return false;
					}
					if(dataObject.IS_STOP == null || dataObject.IS_STOP == ""){
						dialog.frame.$.ligerDialog.error("必填项不可为空!");
						return false;
					}
			
					//保存 新增或修改 
					if(addUpdata=="add"){
						
						if(dataObject.FAC_CODE == null || dataObject.FAC_CODE == ""){
							dialog.frame.$.ligerDialog.error("必填项不可为空!");
							return false;
						}else{
							var faccodeLength = dataObject.FAC_CODE.length
							if(faccodeLength>20){
								dialog.frame.$.ligerDialog.error("字符过长! ");
								return false;
							}
							//位数是否为偶数,否则不符合编码规则 
							if(faccodeLength>1 && faccodeLength%2==0){
								
								//判断当前FAC_CODE是否存在
								var url = 'hrp/acc/accanalyze/elementAnalyze/queryElementForUpdata.do?isCheck=false'
								var result = addUpdataAjaxService(dataObject,url,dialog)
								//查询成功且值为空 ,则说明不存在该fac_code
								if ( result.isSuccess && result.data.Total == 0){
									//大于2位 ， 则判断父级编码，父级不存在则不符合编码规则
									if(faccodeLength>2){
										var currentFacCode = dataObject.FAC_CODE
										var currentSuperCode = dataObject.FAC_CODE.substring(0,faccodeLength-2)
										var url = 'hrp/acc/accanalyze/elementAnalyze/queryElementForUpdata.do?isCheck=false'
										// 父级编码		
										dataObject.FAC_CODE = currentSuperCode
										var resulta = addUpdataAjaxService(dataObject,url,dialog)
										if (resulta.isSuccess && resulta.data.Total == 0){
											//父级不存在,则不符合编码规则
											dialog.frame.$.ligerDialog.error("父级不存在!");
											return false;
										}else{
											dataObject.SUPER_CODE = currentSuperCode
											// fac_code不存在且有父级  末级标识为0   0否 1是			
											//dataObject.IS_LAST = 0;
										}
										//恢复fac_code
										dataObject.FAC_CODE = currentFacCode
									}else{
									//fac_code位两位 且不存在 则直接作为末级添加 	
										dataObject.SUPER_CODE = "TOP"
										// isLast不能为空 ，当前是否为末级  0否 1是		
										//dataObject.IS_LAST = 1;
									}
									
								}else{
									dialog.frame.$.ligerDialog.error("该指标编码已存在!");
									return false;
								}
							
							}else{
								dialog.frame.$.ligerDialog.error("不符合编码规则!");
								return false;
							}
							
						}
						var url = 'hrp/acc/accanalyze/elementAnalyze/addElementAnalyze.do?'
						var result = addUpdataAjaxService(dataObject,url,dialog)
						if(result.isSuccess){
							//是否继续添加
							dialog.frame.$.ligerDialog.confirm('成功，是否继续 ', function (isYes){
								if(isYes){
									//清空表单数据
									dialog.frame.$("#target_code_input").val('');
									dialog.frame.$("#target_name_input").val('');
									dialog.frame.$("#target_unit_input").val('');
									dialog.frame.$("#fma_cn").html('');
									dialog.frame.$("#target_note_input").val('');
								}else{
									dialog.close();
								}
							})
							
						}else{
							dialog.frame.$.ligerDialog.success("失败!")
						}
					}else if(addUpdata=="updata"){
						
						var url = 'hrp/acc/accanalyze/elementAnalyze/updataElementAnalyze.do?'
						//是修改除成功		
						var result = addUpdataAjaxService(dataObject,url,dialog)
						if(result.isSuccess){
							dialog.frame.$.ligerDialog.success("成功!");
						}else{
							dialog.frame.$.ligerDialog.error("失败!")
						}
						//关闭当前弹窗
						//dialog.close();
					}
				},
				cls : 'l-dialog-btn-highlight'
			}, {
				text : '取消',
				onclick : function(item, dialog) {
					dialog.close();
				}
			} ]
		});
	}

	</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
		<table cellpadding="0" cellspacing="0" class="l-table-edit">
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">因素指标：</td>
				<td align="left" class="l-table-edit-td"><input  name="basic_index" type="text" id="elementInfo" ltype="text" /></td>
				<td align="left"></td>
			</tr>
		</table>
	<div id="maingrid"></div>
</body>

</html>