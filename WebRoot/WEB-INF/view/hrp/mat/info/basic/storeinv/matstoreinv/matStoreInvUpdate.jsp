<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
    <script type="text/javascript">
    var dataFormat;
    var isHide = '${p04035}' == 1 ? false : true;
    var dialog = frameElement.dialog; //调用页面的dialog对象(ligerui对象)
    $(function (){
        loadDict();
        loadForm(); 
    });  
     
    function save(){  
    	var selectRowIndex = dialog.get('selectedRow');
        var formPara={
       
        store_id:parent.liger.get("store_id").getValue().split(",")[0] ,
        inv_id:$("#inv_id").val(),
        old_location_id : liger.get("old_location_id").getValue(),
        location_id:liger.get("location_id").getValue()
        };   
        ajaxJsonObjectByUrl("addOrUpdateMatStoreInv.do?isCheck=false",formPara,function(responseData){
            
            if(responseData.state=="true"){
            	  //  设置数据加载完事件 使它渲染完页面后滚动条滚动到指定位置和选择该数据
            	parent.grid.set('onAfterShowData',afterShowData);
				var changepage = parent.grid.options.page;
				parent.query(changepage);
				
                //parent.query();
                parent.$.ligerDialog.close();
                parent.$(".l-dialog,.l-window-mask").remove();
                function afterShowData() {
					 this.gridbody.scrollTop(29 * selectRowIndex);
					 this.select(selectRowIndex);
					 this.unbind('AfterShowData');
				 }
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
     $("form").ligerForm();
    }       
   
    function addStoreInv(){
         save();
    }
    function loadDict(){
    	
        //字典下拉框
        var store_id=$("#store_id").val(); 
       /*  autocomplete("#store_id","../../../../queryMatStore.do?isCheck=false&","id","text",true,true );
		liger.get("store_id").setValue(parent.liger.get("store_id").getValue().split(",")[0]);
    	
    	liger.get("store_id").setText("${store_code} ${store_name}");
         */
        autocomplete("#location_id","../../../../queryMatLocation.do?isCheck=false&store_id="+store_id,"id","text",true,true,'',false,'${location_id}','220');
        liger.get("location_id").setText('${location_code} ${location_name}');
        autocomplete("#old_location_id","../../../../queryMatLocation.do?isCheck=false&store_id="+store_id,"id","text",true,true,'',false,'${location_id}','220');
        liger.get("old_location_id").setText('${location_code} ${location_name}');
        //由于页面当前字段是置灰的，则没有进行走后台 直接通过父页面的仓库取值
        $("#store_id").ligerComboBox({width:220,disabled:true});
 		var store_code = parent.liger.get("store_id").getText()  
        $("#store_id").val(store_code); 
        $("#inv_name").ligerTextBox({width:220,disabled:true});
        $("#inv_model").ligerTextBox({width:220,disabled:true});
        $("#fac_name").ligerTextBox({width:220,disabled:true});
     }   
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
		
        <tr style="display: none">
        <input name="old_location_id" type="hidden" id="old_location_id" value="${location_code}" />
        <input name="inv_id" type="hidden" id="inv_id"   value="${inv_id}" validate="{required:true,maxlength:20}" />
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:180px;"><b>仓库:</b></td>
            <td align="left" class="l-table-edit-td"><input name="store_id" type="text" id="store_id" disabled="disabled" ltype="text" value="${store_id }"  validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td> 
         </tr> 
         <tr>
         	 <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>物资材料名称:</b></td>
            <td align="left"  class="l-table-edit-td"  >
            	<input name="inv_name" type="text" id="inv_name" ltype="text" value="${inv_code } ${inv_name }"/>
            </td>
         </tr>
        <tr>    
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>规格型号:</b></td>
            <td align="left" class="l-table-edit-td"><input name="inv_model" type="text" id="inv_model" disabled="disabled" ltype="text" value="${inv_model }"  validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
       </tr> 
       <tr>
       	 <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>生产厂商:</b></td>
            <td align="left" class="l-table-edit-td"><input name="fac_name" type="text" id="fac_name" disabled="disabled" ltype="text" value="${fac_code } ${fac_name }"  validate="{required:true,maxlength:20}" /></td>
         
       </tr>
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>货位:</b></td>
            <td align="left" class="l-table-edit-td"><input name="location_id" type="text" hide:isHide id="location_id" ltype="text" value="${location_code}" /></td>
         </tr> 
			
        </table>
    </body>
</html>
