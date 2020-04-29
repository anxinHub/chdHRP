<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
	
    var dataFormat;
    
    $(function (){
        loadDict();
        loadForm();
        loadHead(null);
        query();
        $("#accordion_div").ligerAccordion(
                {
                	height: 400,
                	changeHeightOnResize:true
                });
    });  
    
  //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
    		 grid.options.parms.push({name:'ass_id',value:"${ass_id}"}); 
        //根据表字段进行添加查询条件
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '资产编码', name: 'ass_code', align: 'left',
					 },
                     { display: '资产名称', name: 'ass_name', align: 'left'
					 },
					 { display: '资产分类', name: 'ass_type_id', align: 'left'
					 },
					 { display: '财务分类编码', name: 'acc_type_code', align: 'left'
					 },
					 { display: '单位', name: 'ass_unit', align: 'left'
					 },
					 { display: '是否计量', name: 'is_measure', align: 'left',
						 render : function(rowdata, rowindex,
									value) {
								if(rowdata.is_measure == 0){
									return "是";
								}else{
									return "否";
								}
							}
					 },
					 { display: '是否折旧', name: 'is_depre', align: 'left',
						 render : function(rowdata, rowindex,
									value) {
								if(rowdata.is_depre == 0){
									return "是";
								}else{
									return "否";
								}
							}
					 },
					 { display: '折旧方法', name: 'ass_depre_code', align: 'left'
					 },
					 { display: '折旧年限', name: 'depre_years', align: 'left'
					 },
                     { display: '是否停用', name: 'is_stop', align: 'left',
						 render : function(rowdata, rowindex,
									value) {
								if(rowdata.is_stop == 0){
									return "启用";
								}else{
									return "停用";
								}
							}
					 },
					 { display: '规格', name: 'ass_spec', align: 'left'
					 },
					 { display: '型号', name: 'ass_model', align: 'left'
					 },
					 { display: '生产厂商', name: 'fac_id', align: 'left'
					 },
                     { display: '主要供应商', name: 'ven_id', align: 'left' , width:80
					 },
				    { display: '资产用途', name: 'usage_code', align: 'left'
					 },
					 { display: '国际码', name: 'gb_code', align: 'left'
					 },
                  
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'../../../../ass/assnodict/queryAssNoDict.do?isCheck=false', 
                     width: '100%', height: '100%', checkbox: false,rownumbers:false,delayLoad:true,//delayLoad:true,初始化是否不加载的属性
                     selectRowButtonOnly:true
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
     
    function save() {
    
		var formPara = {
			ass_id:"${ass_id}", 

			ass_code:$("#ass_code").val(),
			ass_name:$("#ass_name").val(),
			ass_type_id:liger.get("ass_type_id").getValue(),
		
			acc_type_code:liger.get("acc_type_code").getValue(),

			ass_unit:liger.get("ass_unit").getValue(),

			is_measure:$("#is_measure").val(),

			is_depre:$("#is_depre").val(),

			ass_depre_code:liger.get("ass_depre_code").getValue(),

			depre_years:$("#depre_years").val(),

			is_stop:$("#is_stop").val(),

			ass_spec:$("#ass_spec").val(),

			ass_model:$("#ass_model").val(),

			fac_id :liger.get("fac_id").getValue().split("@")[0],
			
			fac_no :liger.get("fac_id").getValue().split("@")[1],
		
			ven_id : liger.get("ven_id").getValue().split("@")[0],
			
			ven_no : liger.get("ven_id").getValue().split("@")[1],
	
			usage_code : liger.get("usage_code").getValue(),
		
			gb_code : $("#gb_code").val(),
		    history: liger.get("history").getValue()

		};
		ajaxJsonObjectByUrl("updateBudgAssDict.do?isCheck=false", formPara,function(responseData) {

					if (responseData.state == "true") {
						parent.query();
						  query();
					}
				});
	}

    function loadForm(){
    
    $.metadata.setType("attr", "validate");
     var v = $("form").validate({
         errorPlacement: function (lable, element)
         {
             if (element.hasClass("l-textarea"))
             {
                 element.ligerTip({ content: lable.html(), target: element[0] }); 
             }
             else if (element.hasClass("l-text-field"))
             {
                 element.parent().ligerTip({ content: lable.html(), target: element[0] });
             }
             else
             {
                 lable.appendTo(element.parents("td:first").next("td"));
             }
         },
         success: function (lable)
         {
             lable.ligerHideTip();
             lable.remove();
         },
         submitHandler: function ()
         {
             $("form .l-text,.l-textarea").ligerHideTip();
         }
     });
     //$("form").ligerForm();
    }       
   
    function saveAssDict(){
    	
        if($("form").valid()){
        	
            save();
        }
    }
    function loadDict(){
        //字典下拉框

		//字典下拉框
	    autocomplete("#ass_type_id", "../../../../ass/queryAssTypeDictIsLast.do?isCheck=false", "id", "text", true,true,"",false,"${ass_type_id}",200);
        
		autocomplete("#fac_id", "../../../../ass/queryHosFacDict.do?isCheck=false", "id", "text", true,true,"",false,"${fac_id}@${fac_no}",500);
		
		autocomplete("#ven_id", "../../../../ass/queryHosSupDict.do?isCheck=false", "id", "text",true,true,"",false,"${ven_id}@${ven_no}",500);
		
		autocomplete("#ass_depre_code", "../../../../ass/queryAssDepreMethodDict.do?isCheck=false", "id", "text", true,true,"",false,"${ass_depre_code}",200);
		
		autocomplete("#usage_code", "../../../../ass/queryAssUsageDict.do?isCheck=false", "id", "text", true,true,"",false,"${usage_code}",200);
		
    	autocomplete("#ass_unit","../../../../ass/queryHosUnitDict.do?isCheck=false","id","text",true,true,"",false,"${ass_unit}",200);
 
		autocomplete("#ass_unit", "../../../../ass/queryHosUnitDict.do?isCheck=false", "id", "text", true,true,"",false,"${ass_unit}",200);
		
		autocomplete("#acc_type_code", "../../../../ass/queryMatFinaTypeIsLast.do?isCheck=false", "id", "text", true,true,"",false,"${acc_type_code}",200);
		
		//是否计量	
    	$("#is_measure").val("${is_measure}"); 
		//是否折旧	
    	$("#is_depre").val("${is_depre}"); 
		//是否停用	
    	$("#is_stop").val("${is_stop}"); 
    	
    	$("#ass_type_id").ligerTextBox({width : 200});
		$("#fac_id").ligerTextBox({width : 500});
		$("#ven_id").ligerTextBox({width : 500});
		$("#ass_depre_code").ligerTextBox({width : 200});
		$("#usage_code").ligerTextBox({width : 200});
		$("#ass_unit").ligerTextBox({width : 200});
		$("#acc_type_code").ligerTextBox({width : 200});
		
		$("#ass_code").ligerTextBox({width:200,disabled:true,cancelable:false});
		$("#ass_name").ligerTextBox({width:200});
		$("#is_measure").ligerTextBox({width:200});
		$("#is_depre").ligerTextBox({width:200});
		$("#depre_years").ligerTextBox({width:200});
		$("#is_stop").ligerTextBox({width:200});
		$("#ass_spec").ligerTextBox({width:200});
		$("#ass_model").ligerTextBox({width:200});
		$("#gb_code").ligerTextBox({width:200});
    	
    	
	}   
    
  
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
    <div id="accordion_div"> 
         <div title="修改资产信息">
                <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
            <tr>
          
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>资产编码：<font color ="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td"><input name="ass_code" type="text"  disabled="disabled"  value="${ass_code }" id="ass_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>资产名称：<font color ="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td"><input name="ass_name" type="text"  id="ass_name"  value="${ass_name }" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
                 <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产分类：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_type_id" type="text" id="ass_type_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            </tr> 
             <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">财务分类：</td>
            <td align="left" class="l-table-edit-td"><input name="acc_type_code" type="text"   id="acc_type_code" ltype="text" validate="{maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">单位：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_unit" type="text" value="${unit_name}" id="ass_unit" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否计量：</td>
             <td align="left" class="l-table-edit-td">
                	<select id="is_measure" name="is_measure"  value="${is_measure }" >
                		<option value="1">是</option>
                		<option value="0">否</option>
                		
                	</select>
                </td>
            <td align="left"></td>
        </tr> 
           <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否折旧：</td>
             <td align="left" class="l-table-edit-td">
                	<select id="is_depre" name="is_depre"  value="${is_depre }" >
                		<option value="1">是</option>
                		<option value="0">否</option>
                		
                	</select>
                </td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">折旧方法：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_depre_code" type="text" id="ass_depre_code" ltype="text" value="${usage_code}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
             <td align="right" class="l-table-edit-td"  style="padding-left:20px;">折旧年限：</td>
            <td align="left" class="l-table-edit-td"><input name="depre_years" type="text" value="${depre_years}"  id="depre_years" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
           <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否停用：</td>
             <td align="left" class="l-table-edit-td">
                	<select id="is_stop" name="is_stop"  value="${is_stop}">
                		<option value="0">否</option>
                		<option value="1">是</option>
                	</select>
                </td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">规格：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_spec" type="text"   value="${ass_spec}" id="ass_spec" ltype="text" validate="{maxlength:20}" /></td>
            <td align="left"></td>
             <td align="right" class="l-table-edit-td"  style="padding-left:20px;">型号：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_model" type="text"   value="${ass_model}" id="ass_model" ltype="text" validate="{maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">国标码：</td>
            <td align="left" class="l-table-edit-td"><input name="gb_code" type="text"    value="${gb_code}"  id="gb_code" ltype="text" validate="{maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产用途：</td>
            <td align="left" class="l-table-edit-td"><input name="usage_code" type="text" id="usage_code" ltype="text" validate="{maxlength:20}" /></td>
            <td align="left"></td>
            <td align="left"></td>
		    <td align="left" class="l-table-edit-td"><input name ="history" id ="history" type="checkbox" />是否保留历史记录</td>
		    <td align="left"></td>
        </tr> 
        <tr>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">生产厂商：</td>
            <td align="left" class="l-table-edit-td" colspan="4"><input name="fac_id" type="text"  id="fac_id" ltype="text" validate="{maxlength:50}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">主要供应商：</td>
            <td align="left" class="l-table-edit-td" colspan="4"><input name="ven_id" type="text" id="ven_id" ltype="text"  validate="{maxlength:50}" /></td>
            <td align="left"></td>
        </tr> 
        </table>
         <table align="center" cellpadding="0" cellspacing="0" class="l-table-edit">
        	<tr align="center"> 
        		<td align="center" class="l-table-edit-td"  style="padding-left:20px;"><input class="l-button l-button-test" style="float: right;" type="button" value="保存" onclick="saveAssDict();"/></td>
        	</tr>
    </table>
    </form>
        </div>
         <div title="变更记录" id="maingrid">
        </div> 
        
    </div>  
    </body>
</html>
