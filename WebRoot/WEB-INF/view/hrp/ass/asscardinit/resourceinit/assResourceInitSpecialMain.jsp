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
    var ass_card_no = '${ass_card_no}';
  
    $(function ()
    {
        loadDict()//加载下拉框
    	loadHead(null);	
        query();
    });
    
    function setAssCardNo(no){
    	ass_card_no = no;
    	query();
    }
    
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
    		grid.options.parms.push({
    			name : 'ass_nature',
    			value : '${ass_nature}'
    		});
    		grid.options.parms.push({
    			name : 'ass_card_no',	
    			value : ass_card_no
    		});
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                    
                     { display: '资金来源', name: 'source_id', align: 'left',textField : 'source_name',
							editor : {
								type : 'select',
								valueField : 'id',
								textField : 'text',
								url : '../querySourceDict.do?isCheck=false',
								keySupport : true,
								autocomplete : true,
								onSuccess : function(data) {
									if (initvalue != undefined
											&& initvalue != "") {
										this.setValue(initvalue);
										initvalue = "";
									}
								}
							}
					 },
                     { display: '金额(元)', name: 'price', align: 'right',
								editor : {
									type : 'float',
									precision : 2
								},
								render : function(item) {
									return formatNumber(
											item.price, '${ass_05005}', 1);
								}
					 },
					 { display: '累计折旧',name: 'depre_money',align: 'right',
								editor : {
									type : 'float',
									precision : 2
								},
								render : function(item) {
									return formatNumber(
											item.depre_money, '${ass_05005}', 1);
								}
					},
		            { display: '累计分摊', name: 'manage_depre_money',align: 'right',
						editor : {
							type : 'float',
							precision : 2
						},
						render : function(item) {
							return formatNumber(
									item.manage_depre_money, '${ass_05005}', 1);
						}
					},
					{ display: '净值', name: 'cur_money',align: 'right',
						render : function(item) {
							return formatNumber(
									item.cur_money, '${ass_05005}', 1);
						}
					},
					{ display: '残值', name: 'fore_money',align: 'right',
						editor : {
							type : 'float',
							precision : 2
						},
						render : function(item) {
							return formatNumber(
									item.fore_money, '${ass_05005}', 1);
						}
					},
                     { display: '已付金额', name: 'pay_money', align: 'right',
								editor : {
									type : 'float',
									precision : 2
								},
								render : function(item) {
									return formatNumber(
											item.pay_money, '${ass_05005}', 1);
								}
					 },
                     { display: '未付金额', name: 'unpay_money', align: 'right',
							render : function(item) {
								return formatNumber(
										item.unpay_money, '${ass_05005}', 1);
							}
				 	 },
                     
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAssResourceInit.do',
                     width: '100%', height: '90%', checkbox: true,rownumbers:true,enabledEdit : true,delayLoad : true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     scroll: true,
                     frozen:true,
                     alternatingRow: false,
                     scrollToPage: true,
                     scrollToAppend: true,
                     onAfterEdit : f_onAfterEdit,
                     toolbar: { items: [
				    	{ text: '保存（<u>A</u>）', id:'save', click: save, icon:'save' },
				    	{ line:true },
				    	{ text: '删除（<u>D</u>）', id:'delete', click: remove,icon:'delete' },
				    	]} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
        is_addRow();
    }
    
    function f_onAfterEdit(e) {
    	var price = e.record.price == null || e.record.price == "" ? 0:e.record.price;
    	var pay_money = e.record.pay_money == null || e.record.pay_money == "" ? 0:e.record.pay_money;
    	var depre_money = e.record.depre_money  == null || e.record.depre_money == "" ? 0:e.record.depre_money;
    	var fore_money = e.record.fore_money  == null || e.record.fore_money == "" ? 0:e.record.fore_money;
		grid.updateCell('unpay_money', price
				- pay_money, e.record);
		
		grid.updateCell('cur_money', price
				- depre_money - fore_money, e.record);
		
		grid.updateTotalSummary();
		return true;
	}
    
    function save(){
    	gridManager.endEdit();
    	var data = grid.getData();
    	if(ass_card_no == '0'){
    		parent.$.ligerDialog.warn("请先保存卡片");
    		return;
    	}
    	
		var formPara = {
				ass_nature:'${ass_nature}',
			ass_card_no : ass_card_no,

			ParamVo : JSON.stringify(data)

		};
		if (validateGrid()) {
			ajaxJsonObjectByUrl("saveAssCardResourceInit.do", formPara, function(
					responseData) {
				if (responseData.state == "true") {
					query();
				}
			}, false);
		}
    	
    	}
    	
    function remove(){
    	
    	var data = gridManager.getCheckedRows();
                    if (data.length == 0){
                    	parent.$.ligerDialog.error('请选择行');
                    }else{
                        var ParamVo =[];
                        $(data).each(function (){	
                        	if (isnull(this.group_id)) {
								gridManager.deleteSelectedRow();
							} else {
								ParamVo.push(
								this.group_id   +"@"+ 
								this.hos_id   +"@"+ 
								this.copy_code   +"@"+ 
								this.ass_card_no   +"@"+ 
								this.source_id 
								) ;
							}
							});
                        if(ParamVo == ""){
                			return;
                		}
                        parent.$.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                        		
                            	ajaxJsonObjectByUrl("deleteAssResourceInit.do",{ParamVo : ParamVo.toString(),ass_nature:'${ass_nature}'},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
    	}
    
    function validateGrid() {
		var data = gridManager.getData();
		var msg = "";
		var targetMap = new HashMap();
		var msgMap = new HashMap();
		//删除空行
		$.each(data, function(i, v) {
			if (!isnull(v.source_id)) {
				var key = v.source_id;
				var value = "第" + (i + 1) + "行";

				if (v.source_id == '@' || isnull(v.source_id)) {
					msg += "[资金来源]、";
				}
				
				if(isnull(v.price) || v.price == 0 || v.price == 0.0){
					msg += "[金额]";
				}
				
				if(v.pay_money > 0 && v.pay_money != v.price){
					msg += "[金额和付款金额不相等]";
				}

				if (msg != "") {
					msgMap.put(value + msg + "不能为空或不能为零！\n\r", "");
				}
				if (isnull(targetMap.get(key))) {
					targetMap.put(key, value);
				} else {
					msg = targetMap.get(key) + "与" + value + "重复！\n\r",
					value;
					msgMap.put(targetMap.get(key) + "与" + value + "重复！\n\r",
							value);
				}
			}
		});

		if (msg != "") {
			parent.$.ligerDialog.warn(msgMap.keySet());
			return false;
		}
		if (data.length == 0) {
			parent.$.ligerDialog.warn("无数据保存");
			return false;
		}
		return true;
	}
   
    function loadDict(){
            //字典下拉框
            
         }  
  
	  function is_addRow() {
			setTimeout(function() { //当数据为空时 默认新增一行
				grid.addRow();
			}, 1000);
		}
	  
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>

	<div id="maingrid"></div>
	
</body>
</html>
