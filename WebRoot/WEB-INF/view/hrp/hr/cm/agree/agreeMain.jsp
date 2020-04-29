<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script src="<%=path%>/lib/hrp/hr/hr.js"></script> 
<script type="text/javascript">
    var grid;
    var gridManager = null;
        $(function () {
        	  loadDict()//加载下拉框
          	//加载数据
          	loadHead(null);	
          	 $("#agree_code").ligerTextBox({width:160});
        })
        //表格加载
            function loadHead(){
        	var columns=getGridColumns({ui:'liger',group_id:'${group_id}',hos_id:'${hos_id}',gridTables:['HR_AGREE'],design:'queryAgree.do'});
        	grid = $("#maingrid").ligerGrid({
                columns: columns,
                              dataAction: 'server',dataType: 'server',url:'queryAgree.do',delayLoad :true,
                              width: '100%', height: '100%', checkbox: true,rownumbers:true,usePager :true,
                              enabledEdit : true,
                        toolbar: {
                            items: [
                                {text: '查询', id:'search', click: query,icon:'search' },
                            	{text: '新增协议', id:'add',click: add,icon: 'add' },
                            	{text: '存档协议',id:'save',click: save,icon: 'save'},
                            	{text: '终止协议',id:'delete',click: del,icon: 'delete'} ,
                            	//{text: '上传附件',id:'import',click: ,icon: 'up'} ,
                            	//{text: '查看附件',id:'',click: u,icon: ''} 
            
                            ]
                        },
                    	onDblClickRow : function(rowdata, rowindex, value) {
            				openUpdate(rowdata.group_id + "|" + rowdata.hos_id
            						+ "|"
            						+ rowdata.agree_id);
            			}
                            });

                 gridManager = $("#maingrid").ligerGetGridManager();
          
        }
      	//查询方法
        function query() {
     			grid.options.parms=[];
     	  		grid.options.parms.push({name:'',value:$("#").val()}); 
     	  		grid.options.parms.push({name:'tab_code',value:'HR_AGREE'}); 
     	  		grid.options.parms.push({name:'',value:$("#").val()}); 
     	  		grid.options.parms.push({name:'rjt',value:"grid"}); 
     	  		grid.options.parms.push({name:'design_code',value:"queryAgree.do"}); 
     	  
         		grid.loadData(grid.where);
     	
     		}
        
		//新增协议
        function add() {
        	 parent.$.ligerDialog.open({
                 url: 'hrp/hr/cm/agree/agreeAddPage.do?isCheck=false',
                 title: '变更合同',
                 height : $(window).height()-200,
 				width : $(window).width(),
 				parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
                 buttons: [ { text: '确定', onclick: function (item, dialog) {dialog.frame.saveData();},cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ]
             
             });
        }
      //存档合同
		function save(){
			var data = grid.getCheckedRows();
            if (data.length == 0) {
            	$.ligerDialog.error('请选择行');
            } else {
            	
        		var param  = [];
             var err ="";
             
             $(data).each(function (){
             	
           	 	 if(this.status != '3'&&this.status!='4'){
						
								 var rowdata = this;
			                      rowdata.tab_code='HR_AGREE';
			                      rowdata.status='03';
			                      param.push(rowdata);
							
               	  }else{
						if(err == ""){
							err = this.row_id;
						}else{
							err += "、"+this.row_id;
						}
					}
             });
             if (err != "") {
   				$.ligerDialog.warn("第["+err+"]行已存档或已终止，请检查状态！");
   				return;
   			}
            	

                $.ligerDialog.confirm('确定存档?' , function (yes){
                    	if(yes){
                        	ajaxJsonObjectByUrl("updateAgree.do?isCheck=false",{ParamVo: JSON.stringify(param)},function (responseData){
                        		if(responseData.status=="true"){
                        			query();
                        		}
                        	});
                    	}
                });
            }
		}
		//终止协议
        function del() {
        	var data = grid.getCheckedRows();
        	//console.log(data);
        	
            if (data.length == 0) {$.ligerDialog.error('请选择行');return;}
            else if(data.length > 1){$.ligerDialog.error('只能选择一行');return;
            }
            else{
            
                	var param = [];
                 
                    var err ="";
                    
                    $(data).each(function (){
                    	
                  	 	 if(this.status!='4'){
       						var rowdata = this;
       			                      param.push(rowdata);
                      	  }else{
       						if(err == ""){
       							err = this.row_id;
       						}else{
       							err += "、"+this.row_id;
       						}
       					}
                    });
                    if (err != "") {
          				$.ligerDialog.warn("第["+err+"]行已终止，请检查状态！");
          				return;
          			}
              parent.$.ligerDialog.open({
                    url: 'hrp/hr/cm/agree/agreeStopAddPage.do?isCheck=false',
                title: '终止协议',
                width:800,
                height: 300,
                data:{
                	param
                },
            	parentframename: window.name, 
                buttons : [ {
					text : '保存',
					onclick : function(item, dialog) {
						dialog.frame.saveData();
					},
					cls : 'l-dialog-btn-highlight'
				}, {
					text : '取消',
					onclick : function(item, dialog) {
						dialog.close();
					}
				} ] });
            }
        }
		//变更协议
		var openUpdate = function (obj) {
        	var vo = obj.split("|");
			if("null"==vo[2]){
				return false;
				
			}
			var parm ="&group_id="+ 
			  vo[0] + "&hos_id=" + 
			  vo[1]+ "&agree_id="+
			  vo[2]+"&tab_code="+"HR_AGREE";
			
        	
			 parent.$.ligerDialog.open({
                 url: 'hrp/hr/cm/agree/agreeUpdatePage.do?isCheck=false' +parm,
                title: '变更协议',
                height : $(window).height()-200,
 				width : $(window).width(),
                buttons: [ { text: '确定', onclick: function (item, dialog) {dialog.frame.saveData();},cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ]
            
            })
        	
        }
		function loadDict(){
			autocomplete("#agree_type","../../baseSelect.do?isCheck=false&&field_tab_code=DIC_AGREE_TYPE","id","text",true,true,'','false','',160);
			autocomplete("#status","../../baseSelect.do?isCheck=false&&field_tab_code=DIC_AGREE_STATUS","id","text",true,true,'','false','',175);
			$("#start_date").ligerTextBox({width:80});
			$("#end_date").ligerTextBox({width:80});
		}
    </script>
</head>

<body>
  
<div id="pageloading" class="l-loading" style="display: none"></div>
<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" >
		<tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">协议编号：</td>
            <td align="left" class="l-table-edit-td"><input name="agree_code" type="text" id="agree_code" ltype="text" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">协议日期：</td>
            <td align="left" class="l-table-edit-td"><input name="start_date" type="text" id="start_date" ltype="text" class="Wdate"  id="resignation_date" ltype="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
            <td align="center">-</td>
            <td align="left" class="l-table-edit-td"><input name="end_date" type="text" id="end_date" ltype="text" class="Wdate"  id="resignation_date" ltype="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
            <td align="left"></td>
          
        </tr> 
        </table>
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
            <!-- <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text" /></td>
            <td align="left"></td> -->
          
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">协议类型：</td>
            <td align="left" class="l-table-edit-td"><input name="agree_type" type="text" id="agree_type" ltype="text"/></td>
            <td align="left"></td>
             <td align="right" class="l-table-edit-td"  style="padding-left:20px;">协议状态：</td>
            <td align="left" class="l-table-edit-td"><input name="status" type="text" id="status" ltype="text"/></td>
            <td align="left"></td>
        </tr> 
    </table>
            <div id="maingrid"></div>
     
</body>

</html>