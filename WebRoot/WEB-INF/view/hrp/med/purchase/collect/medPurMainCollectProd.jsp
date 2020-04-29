
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

    });
    //查询
    function  query(){
    	grid.options.parms=[];
    	grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'make_date',value:$("#make_date").val()});//日期范围：开始时间 
    	
    	grid.options.parms.push({name:'pur_hos_id', value : liger.get("pur_hos_id").getValue().split(",")[0]});//采购单位
    	grid.options.parms.push({name:'req_hos_id', value : liger.get("req_hos_id").getValue().split(",")[0]});//请购单位
    	grid.options.parms.push({name:'pur_code',   value : $("#pur_code").val()});
    	//加载查询条件
    	grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '医院采购计划编号', name: 'pur_code', align: 'left',render:
                    	 function(rowdata,rowindex,value){
	                    	 return '<a href=javascript:openUpdate("' 
								+ rowdata.pur_id
								+','
								+ rowdata.state
								+ '")>'+rowdata.pur_code+'</a>';
                     	 }
					 },
                     { display: '申请年月', name: 'apply_year_month', align: 'left'},
					 { display: '申请医院', name: 'req_hos_name', align: 'left'},
					 { display: '采购单位', name: 'pur_hos_name', align: 'left'},
                     { display: '付款单位', name: 'pay_hos_name', align: 'left'},
					 { display: '摘要', name: 'brif', align: 'left'},
					 { display: '制单人', name: 'maker', align: 'left'},
					 { display: '制单日期', name: 'make_date', align: 'left'},
					 { display: '审核人', name: 'checker', align: 'left'},
					 { display: '审核日期', name: 'check_date', align: 'left'}
                    ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryMedPurMainByCollect.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
        				{ text: '汇总生成', id:'produce', click: itemclick,icon:'' },
        				{ line:true }
                     	]}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function itemclick(item){ 
        if(item.id)
        {
            switch (item.id)
            {
                case "produce":
                	var data = gridManager.getCheckedRows();
                    if (data.length == 0){
                    	$.ligerDialog.error('请选择行');
                    	return ;
                    }else{
                    	 var Param =[];
                         $(data).each(function (){
        						Param.push(
        						//表的主键
        						this.group_id+"@"+
        						this.hos_id+"@"+
        						this.copy_code+"@"+
        						this.pur_id+"@"+
        						this.pur_hos_id+"@"+
        						this.req_hos_id+"@"+
        						this.pay_hos_id
        						)
                         });
                        
                       $.ligerDialog.confirm('计划汇总后，医院采购计划将被执行，确定汇总吗?', function (yes){
                        	if(yes){
                        		ajaxJsonObjectByUrl("collectMedPurMain.do?Param="+Param,{},function (responseData){
                            		if(responseData.state=="true"){
                            			parent.query();
                            			this_close();
                            		}
                            	});
                        	}
                        });  
                    }
            }   
        }
        
    }
    
    //关闭窗口
    function this_close(){
		frameElement.dialog.close();
	}
   
    function downTemplate(){
    	
    	location.href = "downTemplate.do?isCheck=false";
    
    }
    
    function openUpdate(obj){
    	
    	var pur_id = obj.split(",")[0];
    	
    	var state = obj.split(",")[1];
    	
    	
		$.ligerDialog.open({ url : 'medPurReqDetailPage.do?isCheck=false&pur_id='+pur_id,data:{}, height: 380,width: 1100, top:1,title:'请购计划单明细',modal:true,showToggle:false,showMax:true,showMin: false,isResize:true,buttons: [ { text: '关闭', onclick: function (item, dialog) { dialog.close(); } } ] });
    
    }
   
	 function loadDict(){
         //字典下拉框
        autocomplete("#pur_hos_id","../../queryMedHosInfoDict.do?isCheck=false","id","text",true,true,"",false,'${pur_hos_id}');//采购单位下拉框 
        liger.get("pur_hos_id").setValue("${pur_hos_id}");
		liger.get("pur_hos_id").setText("${pur_hos_name}");
		
        autocomplete("#req_hos_id","../../queryMedHosInfoDict.do?isCheck=false","id","text",true,true,"",false);//请购单位下拉框 		
		//返回当前年,当前月,当前日期,当前月第一天,当前月最后一天,上个月,上月第一天，上月最后一天
		var date = getCurrentDate();
	    var aa = date.split(';');
		$("#make_date").val(aa[0]+'-'+aa[1]);
		$("#pur_code").ligerTextBox({width:160});
		$("#pur_hos_id").ligerTextBox({width:160,disabled:true});
		
      }   
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
        	<td align="right" class="l-table-edit-td"  >
				<font color="red" size="2">*</font>编制年月：
	        </td>
			
			<td align="left" class="l-table-edit-td">
				<input class="Wdate" name="make_date" id="make_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM'})"/>
			</td>
			
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">*</font>采购单位：</td>
            <td align="left" class="l-table-edit-td"><input name="pur_hos_id" type="text" id="pur_hos_id" ltype="text" validate="{required:true,maxlength:20}" readonly="readonly"/></td>
            <td align="left"></td>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">请购单位：</td>
            <td align="left" class="l-table-edit-td">
        		<input name="req_hos_id" type="text" id="req_hos_id" ltype="text" validate="{required:true,maxlength:20}" />
            </td>
            <td align="left"></td>
              <td align="right" class="l-table-edit-td" style="padding-left: 10px;">单据号：</td>
			<td align="left" class="l-table-edit-td" colspan="3">
				<input name="pur_code" type="text" requried="false" id="pur_code" />
			</td>
			<td align="left"></td>
        </tr>
        
    </table>

	<div id="maingrid"></div>
</body>
</html>
