	var formManager=null;
	var commJson=null;
	var eleSelParaArray;
	var mainform=null;
	
    var isInitComBox=true;
    var fieldJosn=[];
	var filedJsonValue={};
	
    function setQuery(jsonData){
    	var s=0;
    	var nl=false;
    	fieldJosn=[];
    	filedJsonValue={};
    	mainform = $("form");
    	formManager=mainform.ligerForm({
            inputWidth: 170, labelWidth: 80, space: 40,
            fields:fieldJosn
    	});
    	$.each(jsonData,function(i,obj){
    		var para={
    	    		 para_code:obj.para_code,
    	    		 ds_code:obj.ds_code
    	    };
    		
    		if(s%3==0)
    			nl=true;
    		else
    			nl=false;
    		s=s+1;
    	    ajaxJsonObjectByUrl("../querySuperReportParaByDs.do?isCheck=false",para,function(responseData){
    	    	loadForm(responseData.Rows,obj.ds_code,nl);
    		});
    	});
    	
    	
    }
    

    function loadForm(json,dscode,isNewline){
    	 //创建表单结构

    	isInitComBox=true;
    	$.metadata.setType("attr", "validate");
    	mainform.validate({
            //调试状态，不会提交数据的
            debug: false,
            errorPlacement: function (lable, element)
            {

                if (element.hasClass("l-textarea"))
                {
                    element.addClass("l-textarea-invalid");
                }
                else if (element.hasClass("l-text-field"))
                {
                    element.parent().addClass("l-text-invalid");
                }
                $(element).removeAttr("title").ligerHideTip();
                $(element).attr("title", lable.html()).ligerTip();
            },
            success: function (lable)
            {
                var element = $("#" + lable.attr("for"));
                if (element.hasClass("l-textarea"))
                {
                    element.removeClass("l-textarea-invalid");
                }
                else if (element.hasClass("l-text-field"))
                {
                    element.parent().removeClass("l-text-invalid");
                }
                $(element).removeAttr("title").ligerHideTip();
            },
            submitHandler: function ()
            {
                alert("Submitted!");
            }
        }); 
    	var isNewline=false;
    	
        $.each(json,function(i,obj){
    		
    		var group="";

			var paraJson=JSON.parse(obj.para_json);
			var inputWidth=170; 
			var isRequired=false;
			i=i+1;
			//定义宽度
			if(paraJson!=null && paraJson.width!=null && paraJson.width!=""){
				inputWidth=paraJson.width;
			}
			//是否必填
			if(paraJson!=null && paraJson.required!=null && paraJson.required=="true"){
				isRequired=false;
			}
    		
    		
    		if(obj.para_type==3 || obj.para_type==4){
    			//下拉框、检索框
    			var key="";
    			var paras="";
    			
    			if(eleSelParaArray && obj.para_type==4){
    				key=eleSelParaArray[i];
    			}
    			if(paraJson!=null && paraJson.para!=null && paraJson.para!=""){
    				$.each(paraJson.para.split(","),function(i,p){
    					paras=paras+p+"@"+(filedJsonValue[p+"#"+dscode+"@@"+dscode]==undefined?"":filedJsonValue[p+"#"+dscode+"@@"+dscode])+",";
    			    });
    				
    				paras=paras.substring(0,paras.length-1)
    			}
    			fieldJosn.push({display:obj.para_name,name:obj.para_code,comboboxName:obj.para_code+"_name",type: "combobox",width:inputWidth,validate: {required:isRequired},
    				editor: {
    					parms : {dict_code:obj.dict_code,key:key,paras:paras},
    					url : "../querySuperReportParaSelectData.do?isCheck=false",
    					valueField : "id",
    					textField : "text",
    					selectBoxWidth: inputWidth,
    					setTextBySource:true,
    					//width: 300,
    					autocomplete : true,
    					highLight : true,
    					keySupport:true,
    					keySupport:true,
    					cancelable:false,
    					delayLoad: false,      //是否延时加载
    				    triggerToLoad : false, //是否在点击下拉按钮时加载
    				    async:false,
    					onSuccess: function (data) {
    						//没有公式的情况下，默认回写参数值，所以不需要默认加载第一个值了
    						if (data.length > 0 && isInitComBox ) {
    							if (data[0].id != undefined && data[0].id != "" && data[0].id != null) {
    								this.setValue(obj.para_value);
    								filedJsonValue[obj.para_code+"@@"+dscode]=data[0].id;
    							}
    						}else {
    							this.setValue(obj.para_value);
    							this.setText(obj.para_value);
    						}
						},
    					onBeforeOpen: function (selectValue){
    		    			if(paraJson!=null && paraJson.para!=null && paraJson.para!=""){
    		    				var $combox=this;
    		    				$.each(paraJson.para.split(","),function(i,p){
    		    					$combox.setParm(p,$("[name="+p+"#"+dscode+"]").val());
    		    			    });
    		    				$combox.reload();
    		    			}   						
    						
    					},onBeforeSelect: function (selectValue){
    						if(selectValue==$("[name="+obj.para_code+"]").val()){
    							return;
    						}
    						filedJsonValue[obj.para_code+"@@"+dscode]=selectValue;
    						if(!isInitComBox)
    							getParaObj(json,obj);
    						
    					},onSelected: function (selectValue){
    					
	    					
    					}},newline: isNewline,group:group});
    			
    			
    		}else if(obj.para_type==5){
    			//复选框
        		fieldJosn.push({display:obj.para_name,name:obj.para_code,type: "checkbox",width:20,validate: {required:isRequired},newline: isNewline,group:group});
    		}else{
    			//文本框
    			fieldJosn.push({display:obj.para_name,name:obj.para_code,type: "text",width:inputWidth,validate: {required:isRequired},newline: isNewline,group:group});
    		}
    		
      });
       	
      formManager=mainform.ligerForm({
            inputWidth: 170, labelWidth: 50, space: 20,
            fields:fieldJosn
	});
      
      isInitComBox=false;
    }
    
    //根据当前参数编码，查找影响的级联，并且清空数据。
    function getParaObj(json,obj){
    	$.each(json,function(i,j){
    		var paraJson=JSON.parse(j.para_json);
    		if(paraJson!=null && paraJson.para!=null && paraJson.para!=""){
    			$.each(paraJson.para.split(","),function(z,p){
    				if(p==obj.para_code){
    					if($("[name="+j.para_code+"]").val()=="本医院" || $("[name="+j.para_code+"]").val()=="本账套" 
    							|| $("[name="+j.para_code+"]").val()=="本年度" || $("[name="+j.para_code+"]").val()=="RMB"){
    						return true;
    					}
    			
    					$("[name="+j.para_code+"]").val("");
    					$("[name="+j.para_code+"_name]").val("");
    				}
    		    });
    		}
    	});
    }
