<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">

    var dataFormat;
    var check_type ;
    var isSubj = '${98001}' == 1 ? true : false;
    var init_1 = 1;
    var init_2 = 1;
    var init_3 = 1;
    var init_4 = 1;
    $(function (){
    	
       $.post("getRules.do?isCheck=false",null,function(responseData){

         $("#rules").val(responseData);

         $("#font2").text(responseData);

       });
       loadDict();
       loadForm();
       
       $("#subj_name").change(function(){
    	   
    	   var name ='${subj_name}';
    	   var nameAll = '${subj_name_all}'
    	   $("subj_name_all").val(nameAll.replace(name,$("#subj_name").val()))
    	   
       })
       
       //辅助核算联动
       $("#check1").change(function(){
         if(this.value==""){
        	 liger.get("check_type1").setValue('');
             liger.get("check_type1").setText('');
        	 liger.get("check_type1").clearContent();
           	 $("#check2").ligerComboBox({ disabled: true,cancelable: false });
             liger.get("check2").setValue('');
             liger.get("check2").setText('');
             liger.get("check_type2").clearContent();
             $("#check2").prop("disabled","disabled");
             $("#check3").ligerComboBox({ disabled: true,cancelable: false });
             liger.get("check3").setValue('');
             liger.get("check3").setText('');
             liger.get("check_type3").clearContent();
             $("#check3").prop("disabled","disabled");
             $("#check4").ligerComboBox({ disabled: true,cancelable: false });
             liger.get("check4").setValue('');
             liger.get("check4").setText('');
             liger.get("check_type4").clearContent();
             $("#check4").prop("disabled","disabled");
             //console.log(2,liger.get("check_type1").getValue());
          }else{

 			$("#check2").ligerComboBox({ disabled: false,cancelable: false });
 			$("#check3").ligerComboBox({ disabled: true,cancelable: false });
 			$("#check3").prop("disabled","disabled");
 			$("#check4").ligerComboBox({ disabled: true,cancelable: false });
 			$("#check4").prop("disabled","disabled");
 			
 			$("#check_type2").ligerComboBox({ disabled: false,cancelable: false });
 			check_type=$("#check1").val();
 			chooseType(check_type,"#check_type1","check_type1");
          }
       });
       $("#check2").change(function(){
           if(this.value==""){
         	   liger.get("check_type2").setValue('');
               liger.get("check_type2").setText('');
               liger.get("check_type2").clearContent();
               $("#check3").ligerComboBox({ disabled: true,cancelable: false });
               liger.get("check3").setValue('');
               liger.get("check3").setText('');
               liger.get("check_type3").clearContent();
               $("#check3").prop("disabled","disabled");
               $("#check4").ligerComboBox({ disabled: true,cancelable: false });
               liger.get("check4").setValue('');
               liger.get("check4").setText('');
               liger.get("check_type4").clearContent();
               $("#check4").prop("disabled","disabled");
               }else{
                 $("#check3").ligerComboBox({ disabled: false,cancelable: false });
                 $("#check4").ligerComboBox({ disabled: true,cancelable: false });
                 $("#check_type3").ligerComboBox({ disabled: false,cancelable: false });
                 check_type=$("#check2").val();
                 chooseType(check_type,"#check_type2","check_type2");
                 $("#check4").prop("disabled","disabled");
               }
           });
       $("#check3").change(function(){
           if(this.value==""){
         	   liger.get("check_type3").setValue('');
               liger.get("check_type3").setText('');
               liger.get("check_type3").clearContent();
               $("#check4").ligerComboBox({ disabled: true,cancelable: false });
               liger.get("check4").setValue('');
               liger.get("check4").setText('');
               liger.get("check_type4").clearContent();
               $("#check4").prop("disabled","disabled");

             }else{

               $("#check4").ligerComboBox({ disabled: false,cancelable: false });

               $("#check_type4").ligerComboBox({ disabled: false,cancelable: false });
               check_type=$("#check3").val();
               chooseType(check_type,"#check_type3","check_type3");
             }
        });

        $("#check4").change(function(){
        	 if(this.value==""){
                 liger.get("check_type4").setValue('');
                 liger.get("check_type4").setText('');
                 liger.get("check_type4").clearContent();
        	 }else{
        		 
      			check_type=$("#check4").val();
      			chooseType(check_type,"#check_type4","check_type4");
        	 }
        	
        });
        
        if(${is_disable}==true){
        	$("#check1").ligerComboBox({ disabled: true,cancelable: false });
        }
    });

// var dialog = frameElement.dialog; //调用页面的dialog对象(ligerui对象)
    function save(){
      var is_remit = 0;
     var is_cash = 0;
     var is_check = 0;
     var is_bill = 0;
     var is_jrsz = 0;
     var is_wlhx = 0;
     var check1="",check2="",check3="",check4="";
     
     if(liger.get("check1").getText()!= "" && liger.get("check1").getText() != null){
    	 
    	 check1= liger.get("check1").getValue();
    	 
     }
     
	if(liger.get("check2").getText()!= "" && liger.get("check2").getText() != null){
	    	 
		check2= liger.get("check2").getValue();
		
	}
	     
	if(liger.get("check3").getText()!= "" && liger.get("check3").getText() != null){
		 
		check3= liger.get("check3").getValue();
		
	}
	
	if(liger.get("check4").getText()!= "" && liger.get("check4").getText() != null){
		 
		check4= liger.get("check4").getValue();
		
	}
     
     if($("#is_remit").prop("checked") == true){
       is_remit = 1;
     }
     if($("#is_cash").prop("checked") == true){
       is_cash = 1;
     }
     if(liger.get("check1").getValue() != null && liger.get("check1").getValue() != ""){
       is_check = 1;
     }
     if($("#is_bill").prop("checked") == true){
    	 is_bill = 1;
     }
     if($("#is_jrsz").prop("checked") == true){
    	 is_jrsz = 1;
     } 
     if($("#is_wlhx").prop("checked") == true){
    	 is_wlhx = 1;
     }
     
       var formPara={
          subj_id:'${subj_id}',

          group_id:'${group_id}',

          hos_id:'${hos_id}',

          copy_code:'${copy_code}',

          acc_year:'${acc_year}',

          subj_code:$("#subj_code").val(),

          cur_code:liger.get("cur_code").getValue(),

          subj_type_code:liger.get("subj_type_code").getValue().split(",")[0],
          
          kind_code:liger.get("subj_type_code").getValue().split(",")[1],

          subj_nature_code:liger.get("subj_nature_code").getValue(),

          vouch_type_code:liger.get("vouch_type_code").getValue(),

          subj_name:$("#subj_name").val(),

          subj_name_all:$("#subj_name_all").val(),

          subj_name_en:$("#subj_name_en").val(),

          is_remit:is_remit,

          is_cash:is_cash,
          
          is_bill:is_bill,
          
          is_jrsz:is_jrsz,
          is_wlhx : is_wlhx,
          subj_dire:$("#subj_dire").val(),
          is_stop : $("#is_stop").val(),

          is_check:is_check,

          check1:check1,

          check2:check2,

          check3:check3,

          check4:check4,

          check5:'',

          check6:'',

          check7:'',

          check8:'',

          check9:'',

          check10:'',

          check_type_code1:liger.get("check_type1").getValue(),

          check_type_code2:liger.get("check_type2").getValue(),

          check_type_code3:liger.get("check_type3").getValue(),

          check_type_code4:liger.get("check_type4").getValue(),

          out_code:liger.get("out_code").getValue(),

          rules:$("#rules").val(),

          subj_level:'${subj_level}',

          super_code:'${super_code}'

        };
       
       ajaxJsonObjectByUrl("updateAccSubj.do?isCheck=false",formPara,function(responseData){
            if(responseData.state=="true"){
                parent.query();
                // parent.query1();
                // parent.updateRow(formPara);
                frameElement.dialog.close();
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

    function saveAccSubj(){
        if($("form").valid()){
        	if(isSubj==true){
				$.ligerDialog.warn("无功能权限！");
				return false;
			}else{
				 save();
			}
           
        }
    }
    function loadDict(){
        //字典下拉框

        if('${isSubjNote}'=='' && '${is_last}'=='1'){
          //科目没有使用,同时为末级科目，可以编辑
          autocomplete("#subj_type_code","../querySubjTypeKind.do?isCheck=false","id","text",true,true);
          autocomplete("#subj_nature_code","../querySubjNature.do?isCheck=false","id","text",true,true);
          autocomplete("#cur_code","../queryCur.do?isCheck=false","id","text",true,true);
          $("#subjUseTipId").attr("style","display:none");

        }else{
          $("#subj_code").ligerTextBox({ disabled: true });
          $("#subj_code").prop("disabled","disabled");
          $("#subj_name_all").ligerTextBox({ disabled: true });
          $("#subj_name_all").prop("disabled","disabled");
          $("#subj_type_code").ligerComboBox({ disabled: true,cancelable: false });
          $("#subj_nature_code").ligerComboBox({ disabled: true,cancelable: false });
          $("#subj_dire").val("${subj_dire}");
          $("#subj_dire").ligerTextBox({disabled:true,cancelable: false});
          $("#subj_dire").prop("disabled",true);
          $("#cur_code").ligerComboBox({disabled:true,cancelable: false});
          
          if("${is_cash}" == 1){
          	$("#is_cash").prop("checked",true);
          }
          $("#is_cash").ligerCheckBox({readonly:true});
          if("${is_remit}" == 1){
            	$("#is_remit").prop("checked",true);
            }
            $("#is_remit").ligerCheckBox({readonly:true});
          if("${is_bill}" == 1){
              	$("#is_bill").prop("checked",true);
              }
              $("#is_bill").ligerCheckBox({readonly:true});
         if("${is_jrsz}" == 1){
           	$("#is_jrsz").prop("checked",true);
           }
           $("#is_jrsz").ligerCheckBox({readonly:true});
           if("${is_wlhx}" == 1){
              	$("#is_wlhx").prop("checked",true);
              }
              $("#is_wlhx").ligerCheckBox({readonly:true});
           
        }

        autocomplete("#out_code","../queryDeptOutSelect.do?isCheck=false","id","text",true,true,'',false,'',178);

        autocomplete("#vouch_type_code","../queryVouchType.do?isCheck=false","id","text",true,true,'',false,'',178);

        liger.get("subj_type_code").setValue("${subj_type_code},${kind_code}");
        
        liger.get("subj_type_code").setText("${subj_type_name}");

        liger.get("subj_nature_code").setValue("${subj_nature_code}");

        liger.get("subj_nature_code").setText("${subj_nature_name}");

        liger.get("vouch_type_code").setValue("${vouch_type_code}");

        liger.get("vouch_type_code").setText("${vouch_type_name}");

        liger.get("cur_code").setValue("${cur_code}");

        liger.get("cur_code").setText("${cur_name}");

    	liger.get("out_code").setValue("${out_code}");

        liger.get("out_code").setText("${out_name}");
        
        autocomplete("#check1","../queryCheckType.do?isCheck=false","id","text",true,true,'',false,'',178,120);
        autocomplete("#check2","../queryCheckType.do?isCheck=false","id","text",true,true,'',false,'',178);
        autocomplete("#check3","../queryCheckType.do?isCheck=false","id","text",true,true,'',false,'',178);
        autocomplete("#check4","../queryCheckType.do?isCheck=false","id","text",true,true,'',false,'',178);

        $("#check1").ligerComboBox({ cancelable: false });
        
        if('${isCheck1Use}'=='0' && '${isCheck2Use}'=='0' && '${isCheck3Use}'=='0' && '${isCheck4Use}'=='0' && '${is_last}'=='1'){
        	//辅助核算 没有使用，可以编辑
        	liger.get("check1").setValue("${check1}");

            liger.get("check1").setText("${check1_name}");
            
            check_type=$("#check1").val();
   	      	chooseType(check_type,"#check_type1","check_type1");
   	      	
   	      	//判断 是否 挂辅助核算1（如果挂了 check2 可以编辑 否则不能编辑）
   	      	if("${check1}" != ''){
	   	      	$("#check2").ligerComboBox({ disabled: false,cancelable: false });
				$("#check3").ligerComboBox({ disabled: true,cancelable: false });
				$("#check3").prop("disabled","disabled");
				$("#check4").ligerComboBox({ disabled: true,cancelable: false });
				$("#check4").prop("disabled","disabled");
   	      	}else{
	   	      	$("#check2").ligerComboBox({ disabled: true,cancelable: false });
	   	     	$("#check2").prop("disabled","disabled");
				$("#check3").ligerComboBox({ disabled: true,cancelable: false });
				$("#check3").prop("disabled","disabled");
				$("#check4").ligerComboBox({ disabled: true,cancelable: false });
				$("#check4").prop("disabled","disabled");
   	      	}
   	     	
     		liger.get("check2").setValue("${check2}");

         	liger.get("check2").setText("${check2_name}");
         
         	check_type=$("#check2").val();
	      	chooseType(check_type,"#check_type2","check_type2");
	      	
	      //判断 是否 挂辅助核算2（如果挂了 check3可以编辑 否则不能编辑）
   	      	if("${check2}" != ''){
	   	      	$("#check3").ligerComboBox({ disabled: false,cancelable: false });
				$("#check4").ligerComboBox({ disabled: true,cancelable: false });
				$("#check4").prop("disabled","disabled");
   	      	}else{
				$("#check3").ligerComboBox({ disabled: true,cancelable: false });
				$("#check3").prop("disabled","disabled");
				$("#check4").ligerComboBox({ disabled: true,cancelable: false });
				$("#check4").prop("disabled","disabled");
   	      	}
	      	
        	liger.get("check3").setValue("${check3}");

            liger.get("check3").setText("${check3_name}");
            
            check_type=$("#check3").val();
   	      	chooseType(check_type,"#check_type3","check_type3");
   	      	
   	   		//判断 是否 挂辅助核算3（如果挂了 check4可以编辑 否则不能编辑）
   	      	if("${check3}" != ''){
	   	      	$("#check4").ligerComboBox({ disabled: false,cancelable: false });
   	      	}else{
				$("#check4").ligerComboBox({ disabled: true,cancelable: false });
				$("#check4").prop("disabled","disabled");
   	      	}
     	
     		liger.get("check4").setValue("${check4}");

         	liger.get("check4").setText("${check4_name}");
         
         	check_type=$("#check4").val();
	      	chooseType(check_type,"#check_type4","check_type4");
        }else{
        	if("${check1}" !=''){
        		$("#check1").ligerComboBox({ disabled: true,cancelable: false });
        		$("#check1").prop("disabled","disabled");
        		liger.get("check1").setValue('${check1}');
    			liger.get("check1").setText('${check1_name}');
    			check_type=$("#check1").val();
    			   
    			chooseType(check_type,"#check_type1","check_type1");
    			$("#check2").ligerComboBox({ disabled: false,cancelable: false });
    			$("#check3").ligerComboBox({ disabled: true,cancelable: false });
    			$("#check3").prop("disabled","disabled");
    			$("#check4").ligerComboBox({ disabled: true,cancelable: false });
    			$("#check4").prop("disabled","disabled");
        	}else{
        		$("#check1").ligerComboBox({ disabled: false,cancelable: false });
        		$("#check2").ligerComboBox({ disabled: true,cancelable: false });
        		$("#check2").prop("disabled","disabled");
    			$("#check3").ligerComboBox({ disabled: true,cancelable: false });
    			$("#check3").prop("disabled","disabled");
    			$("#check4").ligerComboBox({ disabled: true,cancelable: false });
    			$("#check4").prop("disabled","disabled");
        	}
			
			if("${check2}" !=''){
        		$("#check2").ligerComboBox({ disabled: true,cancelable: false });
        		$("#check2").prop("disabled","disabled");
        		liger.get("check2").setValue("${check2}");
    			liger.get("check2").setText("${check2_name}");
    			check_type=$("#check2").val();
    			chooseType(check_type,"#check_type2","check_type2");
    			
    			$("#check3").ligerComboBox({ disabled: false,cancelable: false });
    			$("#check4").ligerComboBox({ disabled: true,cancelable: false });
    			$("#check4").prop("disabled","disabled");
        	}else{
        		if("${check1}" !=''){
        			$("#check2").ligerComboBox({ disabled: false,cancelable: false });
        		}else{
        			$("#check2").ligerComboBox({ disabled: true,cancelable: false });
        			$("#check2").prop("disabled","disabled");
        		}
        		
        		$("#check3").ligerComboBox({ disabled: true,cancelable: false });
        		$("#check3").prop("disabled","disabled");
    			$("#check4").ligerComboBox({ disabled: true,cancelable: false });
    			$("#check4").prop("disabled","disabled");
        	}
			
			if("${check3}" !=''){
        		$("#check3").ligerComboBox({ disabled: true,cancelable: false });
        		$("#check3").prop("disabled","disabled");
        		liger.get("check3").setValue("${check3}");
    			liger.get("check3").setText("${check3_name}");
    			check_type=$("#check3").val();
    			chooseType(check_type,"#check_type3","check_type3");
    			$("#check4").ligerComboBox({ disabled: false,cancelable: false });
        	}else{
        		if("${check2}" !=''){
        			$("#check3").ligerComboBox({ disabled: false,cancelable: false });
        		}else{
        			$("#check3").ligerComboBox({ disabled: true,cancelable: false });
        			$("#check3").prop("disabled","disabled");
        		}
        		
        		$("#check4").ligerComboBox({ disabled: true,cancelable: false });
        		$("#check4").prop("disabled","disabled");
        	}
			
       		
			if("${check4}" !=''){
        		$("#check4").ligerComboBox({ disabled: true,cancelable: false });
        		$("#check4").prop("disabled","disabled");
        		liger.get("check4").setValue("${check4}");
    			liger.get("check4").setText("${check4_name}");
    			check_type=$("#check4").val();
    			chooseType(check_type,"#check_type4","check_type4");
        	}else{
        		if("${check3}" !=''){
        			$("#check4").ligerComboBox({ disabled: false,cancelable: false });
        		}else{
        			$("#check4").ligerComboBox({ disabled: true,cancelable: false });
        			$("#check4").prop("disabled","disabled");
        		}
        		
        	}
			
        }
        

        $("#subj_dire").val("${subj_dire}");
        $("#is_stop").val("${is_stop}");

        if("${is_remit}" == 1){
        	$("#is_remit").prop("checked",true);
        }
        if("${is_cash}" == 1){
        	$("#is_cash").prop("checked",true);
        }
        
        if("${is_bill}" == 1){
        	$("#is_bill").prop("checked",true);
        }
        
        if("${is_jrsz}" == 1){
        	$("#is_jrsz").prop("checked",true);
        }
        
        if("${is_wlhx}" == 1){
        	$("#is_wlhx").prop("checked",true);
        }
        
      //初始化下拉框
      $("#check_type1").ligerComboBox({selectBoxWidth: 178,isShowCheckBox: true,isMultiSelect: true,cancelable: false});

      $("#check_type2").ligerComboBox({selectBoxWidth: 178,isShowCheckBox: true,isMultiSelect: true,cancelable: false});

      $("#check_type3").ligerComboBox({selectBoxWidth: 178,isShowCheckBox: true,isMultiSelect: true,cancelable: false});

      $("#check_type4").ligerComboBox({selectBoxWidth: 178,isShowCheckBox: true,isMultiSelect: true,cancelable: false});

      //disabledCheck();
      
      $("#subj_code").ligerTextBox({  disabled: true });

     }

    function subjUseTip(){
      var s='${isSubjNote}';
      if(s==""){
        s="不是末级科目，某些字段不能修改。";
      }else{
        s='科目被以下业务使用：【'+s.substring(0, s.length-1)+'】，某些字段不能修改。';
      }
      $.ligerDialog.question(s);
    }

    function disabledCheck(){

     
    }
    //传入选择的辅助核算值，进行判断核算类型下拉框
    function chooseType(obj,type,name){
		if("部门"==obj){
			
		      if(""!=$(type).val()){
		
		          liger.get(name).setValue('');
		
		          liger.get(name).setText('');
		      }
		      	//部门分类下拉框
		        initSelect(type,"../queryDeptType.do?isCheck=false");
	
	
	      }else if("职工"==obj){
	
		      if(""!=$(type).val()){
		
		          liger.get(name).setValue('');
		
		          liger.get(name).setText('');
		      }
		    	//职工分类下拉框
		        initSelect(type,"../../sys/queryEmpKindDict.do?isCheck=false");
	
	
	      }else if("项目"==obj){
	
	
		      if(""!=$(type).val()){
		
		          liger.get(name).setValue('');
		
		          liger.get(name).setText('');
		      }
		    	//项目分类下拉框
		        initSelect(type,"../../sys/queryProjTypeDict.do?isCheck=false");
	
	      }else if("库房"==obj){
	
		      if(""!=$(type).val()){
		
		          liger.get(name).setValue('');
		
		          liger.get(name).setText('');
		      }
		    	//库房分类下拉框
		        initSelect(type,"../../sys/queryStoreTypeDict.do?isCheck=false");
	
	
	      }else if("客户"==obj){
	
	
		      if(""!=$(type).val()){
		
		          liger.get(name).setValue('');
		
		          liger.get(name).setText('');
		      }
		    	//客户分类下拉框
		        initSelect(type,"../../sys/queryCusTypeDict.do?isCheck=false");
	
	
	      }else if("供应商"==obj){
	
	
		      if(""!=$(type).val()){
		
		          liger.get(name).setValue('');
		
		          liger.get(name).setText('');
		      }
		    	//供应商分类下拉框
		      initSelect(type,"../../sys/querySupTypeDict.do?isCheck=false");
	
	      }else if("资金来源"==obj){
	  		
			if(""!=$(type).val()){
				
	    		liger.get(name).setValue('');
	    		
	 	     	liger.get(name).setText('');
			}
			//资金分类下拉框
	  		initSelect(type,"../../sys/querySysSourceNature.do?isCheck=false");
	  		 	
	  	  }else if("单位"==obj){
	  		
				if(""!=$(type).val()){
					
		    		liger.get(name).setValue('');
		    		
		 	     	liger.get(name).setText('');
				}
				$(type).ligerComboBox({
		    		data : {},
		    		valueField : "id",
		    		textField : "text",
		    		selectBoxWidth: 180,
		    		width: 180,
		    		keySupport:true,
		    		setTextBySource:true,
		    		autocomplete : true/* ,
		    		selectBoxHeight: 1 */
		    	});
	  		 	
	  	  }else{
	  		  
			if(obj == ''){
			return; 
			}else{
				if(""!=$(type).val()){
				
				    liger.get(name).setValue('');
				
				    liger.get(name).setText('');
				 // 自定义核算  核算分类下拉框
				}
				if(type =='#check_type1'){
				 initSelect(type,"../queryCheckItemType.do?isCheck=false&check_type_id=${check1}");
				}
				if(type =='#check_type2'){
				 initSelect(type,"../queryCheckItemType.do?isCheck=false&check_type_id=${check2}");
				}
				if(type =='#check_type3'){
				 initSelect(type,"../queryCheckItemType.do?isCheck=false&check_type_id=${check3}");
				}
				if(type =='#check_type4'){
				 initSelect(type,"../queryCheckItemType.do?isCheck=false&check_type_id=${check4}");
				} 
			}
	     
	   }
    }
    
	//初始化 分类下拉框数据
	function initSelect(type,data){
		
		$(type).ligerComboBox({
		       url : data,
		       valueField : "id",
		       textField : "text",
		       width: 178,
		       autocomplete : true,
		       isShowCheckBox : true ,
		       isMultiSelect :true ,
		       //async: false,
		       onSuccess:function(){
			       if(init_1 == 1 || init_2 == 1 || init_3 == 1 || init_4 == 1){
			    	   if(type =='#check_type1'){
			    		   this.setValue("${check_type_code1}");
				           this.setText("${check_type_name1}");
				           
				           init_1 = 0 ;
			    	   }
			    	   if(type =='#check_type2'){
			    		   this.setValue("${check_type_code2}");
				           this.setText("${check_type_name2}");
				           init_2 = 0 ;
			    	   }
			    	   if(type =='#check_type3'){
			    		   this.setValue("${check_type_code3}");
				           this.setText("${check_type_name3}");
				           init_3 = 0 ;
			    	   }
			    	   if(type =='#check_type4'){
			    		   this.setValue("${check_type_code4}");
				           this.setText("${check_type_name4}");
				           init_4 = 0 ;
			    	   }
			           this.updateStyle();
			       }
		        } 
			})
		
		/* if(type =='#check_type1' && flag == 1){
			liger.get("check_type1").setValue('${check_type_code1}');
			
	        liger.get("check_type1").setText('${check_type_name1}');
	        
	        liger.get("check_type1").updateStyle() ;
		}
		if(type =='#check_type2' && flag == 1){
			liger.get("check_type2").setValue('${check_type_code2}');
			
	        liger.get("check_type2").setText('${check_type_name2}');
	        
	        liger.get("check_type2").updateStyle() ;
	        
		}
		
		if(type =='#check_type3' && flag == 1){
			liger.get("check_type3").setValue('${check_type_code3}');
			
	        liger.get("check_type3").setText('${check_type_name3}');
	        
	        liger.get("check_type3").updateStyle() ;
	        
		}
		if(type =='#check_type4' && flag == 1){
			liger.get("check_type4").setValue('${check_type_code4}');
			
	        liger.get("check_type4").setText('${check_type_name4}');
	        
	        liger.get("check_type4").updateStyle() ;
	        
		} */
		
	}

  function initSelectValue(id,data,name,param){

      $(id).ligerComboBox({
        parms : param,
        url : data,
        valueField : "id",
        textField : "text",
        width: 178 ,
        autocomplete : true,
        onSuccess:function(data){

        liger.get(name).setValue(data[0].id);

          liger.get(name).setText(data[0].text);

           }
      });
    }
    </script>

  </head>

   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
   <font id="font1">编码规则：<font id="font2" color="red"></font></font>&nbsp;&nbsp;<a id="subjUseTipId" onclick="subjUseTip();" style="cursor:hand;">科目修改说明</a><hr/>
   <input type="hidden" id="rules" name="rules" />
   <div id="panel1-1" >
         <table cellpadding="0" cellspacing="0" class="l-table-edit" >
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:5px;"><b><font color="red">*</font></b>科目编码：</td>
                <td align="left" class="l-table-edit-td" style="padding-top:5px;"><input name="subj_code" type="text" value="${subj_code }" id="subj_code" disabled="disabled" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:5px;"><b><font color="red">*</font></b>科目名称：</td>
                <td align="left" class="l-table-edit-td" style="padding-top:5px;"><input name="subj_name" type="text" value="${subj_name }" id="subj_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr>
              <tr>
              <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:10px;"><b><font color="red">*</font></b>科目全称：</td>
                <td align="left" class="l-table-edit-td"  style="padding-top:10px;"><input name="subj_name_all" type="text" value="${subj_name_all }"  id="subj_name_all" ltype="text" validate="{required:true,maxlength:200}" /></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:10px;">英文名称：</td>
                <td align="left" class="l-table-edit-td" style="padding-top:10px;"><input name="subj_name_en" type="text" value="${subj_name_en }" id="subj_name_en" ltype="text"  /></td>
                <td align="left"></td>
            </tr>
            <tr>
              <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:10px;"><b><font color="red">*</font></b>科目类别：</td>
                <td align="left" class="l-table-edit-td"  style="padding-top:10px;"><input name="subj_type_code" type="text" id="subj_type_code"  ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:10px;"><b><font color="red">*</font></b>科目性质：</td>
                <td align="left" class="l-table-edit-td" style="padding-top:10px;"><input name="subj_nature_code" type="text" id="subj_nature_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr>
             <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:10px;"><b><font color="red">*</font></b>币种：</td>
                <td align="left" class="l-table-edit-td" style="padding-top:10px;"><input name="cur_code" type="text" id="cur_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:10px;"><b><font color="red">*</font></b>余额方向：</td>
                <td align="left" class="l-table-edit-td" style="padding-top:10px;">
                  <select id="subj_dire" name="subj_dire">
                    <option value="0">借方</option>
                    <option value="1">贷方</option>
                  </select>
                </td>
                 <td align="left"></td>
            </tr>
            <tr>
        <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:10px;">凭证类型：</td>
                <td align="left" class="l-table-edit-td" style="padding-top:10px;"><input name="vouch_type_code" type="text" id="vouch_type_code" ltype="text"  /></td>
                <td align="left"></td>
               <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:10px;">支出性质：</td>
                <td align="left" class="l-table-edit-td" style="padding-top:10px;"><input name="out_code" type="text" id="out_code" ltype="text"/></td>
                <td align="left"></td>
            </tr>
            <tr style="display: none">
            	<td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:10px;"><b><font color="red">*</font></b>是否停用：</td>
            	<td align="left" class="l-table-edit-td" style="padding-top:10px;">
                  <select id="is_stop" name="is_stop">
                    <option value="0">否</option>
                    <option value="1">是</option>
                  </select>
                </td>
                 <td align="left"></td>
            </tr>
            <tr>
              <td align="right" class="l-table-edit-td" colspan="4" style="padding-left:20px;padding-top:10px;">
                  <input name="is_remit" type="checkbox" id="is_remit" />期末调汇&nbsp;&nbsp;
                  <input name="is_cash" type="checkbox" id="is_cash"  />核算现金流
                  <input name="is_bill" type="checkbox" id="is_bill"  />是否票据核销
                 <input name="is_jrsz" type="checkbox" id="is_jrsz"  />是否记入收支
                  <input name="is_wlhx" type="checkbox" id="is_wlhx"  />是否往来核销
                </td>
            </tr>
           </table>
   </div>
   辅助核算<hr/>
   <div id="panel1-2" >
      <table cellpadding="0" cellspacing="0" class="l-table-edit" >
      <!--  <tr>
          <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:2px;">
            <div class="l-button" style="width: 60px; " ligeruiid="Button1002" onclick="">
                    <span>辅助核算</span></div>
          </td>
              <td align="left" class="l-table-edit-td" colspan="4">

              </td>
              <td align="left" ></td>
        </tr>  -->
          <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:5px;">辅助核算1：</td>
                <td align="left" class="l-table-edit-td" style="padding-top:5px;"><input name="check1" type="text" id="check1" ltype="text" /></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:5px;">辅助核算2：</td>
                <td align="left" class="l-table-edit-td" style="padding-top:5px;"><input name="check2" type="text" id="check2" ltype="text"  /></td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:10px;">辅助核算3：</td>
                <td align="left" class="l-table-edit-td" style="padding-top:10px;"><input name="check3" type="text" id="check3" ltype="text"  /></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:10px;">辅助核算4：</td>
                <td align="left" class="l-table-edit-td" style="padding-top:10px;"><input name="check4" type="text" id="check4" ltype="text"  /></td>
                <td align="left"></td>
            </tr>
              <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:10px;">核算分类1：</td>
                <td align="left" class="l-table-edit-td" style="padding-top:10px;"><input name="check_type1" type="text" id="check_type1" ltype="text" /></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:10px;">核算分类2：</td>
                <td align="left" class="l-table-edit-td" style="padding-top:10px;"><input name="check_type2" type="text" id="check_type2" ltype="text" /></td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:10px;">核算分类3：</td>
                <td align="left" class="l-table-edit-td" style="padding-top:10px;"><input name="check_type3" type="text" id="check_type3" ltype="text"  /></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:10px;">核算分类4：</td>
                <td align="left" class="l-table-edit-td" style="padding-top:10px;"><input name="check_type4" type="text" id="check_type4" ltype="text"  /></td>
                <td align="left"></td>
            </tr>
            <!--<tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:10px;">辅助核算9：</td>
                <td align="left" class="l-table-edit-td" style="padding-top:10px;"><input name="check9" type="text" id="check9" ltype="text" /></td>
                <td align="left"></td>
                 <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:10px;">辅助核算10：</td>
                <td align="left" class="l-table-edit-td" style="padding-top:10px;"><input name="check10" type="text" id="check10" ltype="text" /></td>
                <td align="left"></td>
            </tr>
      -->
        </table>
   </div>

    </form>
    </body>
</html>
