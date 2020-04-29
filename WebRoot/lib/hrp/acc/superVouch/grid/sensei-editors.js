(function ($) {
    var root = this;

    var Editor = function (grid) {
        this.grid = grid;
    };
    Editor.extend = function (props) {
        var parent = this;
        var child;

        child = function () {
            return parent.apply(this, arguments);
        };

        var Surrogate = function () {
            this.constructor = child;
        };
        Surrogate.prototype = parent.prototype;
        child.prototype = new Surrogate();

        if (props) {
            _.extend(child.prototype, props);
        }

        child.__super__ = parent.prototype;

        return child;
    };
    Editor.prototype.getElement = function () {
        return $(this.editor);
    };
    Editor.prototype.initialize = function () {
    };
    Editor.prototype.render = function () {
    };
    Editor.prototype.show = function () {
        this.getElement().show();
    };
    Editor.prototype.hide = function () {
        this.getElement().hide();
        this.grid.activeEditor.activeCell = null;
        this.grid.activeEditor = null;
    };
    Editor.prototype.setDimensions = function ($td) {
        this.getElement().css({
            width: $td.outerWidth() + 1,
            height: $td.outerHeight() + 1
        });
    };
    Editor.prototype.getValue = function () {
        throw Error("Editor.getValue not implemented");
    };
    Editor.prototype.setValue = function () {
        throw Error("Editor.setValue not implemented");
    };

    // export editor
    root.Editor = Editor;

    root.BasicEditor = Editor.extend({
        types: [],
        name: "BasicEditor",
        render: function () {
            if (!this.editor) {
                this.editor = document.createElement("div");
                this.editor.className = "sensei-grid-editor sensei-grid-basic-editor";
                var input = document.createElement("input");
                input.setAttribute("type", "text");
                this.editor.appendChild(input);
                this.grid.$el.append(this.editor);
            }
        },
        getValue: function () {
        	var val=$("input", this.editor).val().replace(/\//g,"").replace(/\\/g,"");
        	$("input", this.editor).val(val);
            return $("input", this.editor).val();
        },
        setValue: function (val) {
        	if(this.grid.activeEditor.activeCell.data("column")=="debit_name"){
        		var $row=grid.getCellRow(this.grid.activeEditor.activeCell);
        		var cell13=$($row[0].cells[13]).find("div").text();
        		if( cell13!="" && parseFloat(cell13)!=0 ) $("input", this.editor).val(cell13).focus().select();//借方金额
        		else  $("input", this.editor).val(val).focus().select();
        	}else if(this.grid.activeEditor.activeCell.data("column")=="credit_name"){
        		var $row=grid.getCellRow(this.grid.activeEditor.activeCell);
        		var cell14=$($row[0].cells[14]).find("div").text();
        		if( cell14!="" && parseFloat(cell14)!=0 ) $("input", this.editor).val(cell14).focus().select();//借方金额
        		else  $("input", this.editor).val(val).focus().select();
        	}else if(this.grid.activeEditor.activeCell.data("column")=="budg_debit_name"){
        		var $row=grid.getCellRow(this.grid.activeEditor.activeCell);
        		var cell21=$($row[0].cells[21]).find("div").text();
        		if( cell21!="" && parseFloat(cell21)!=0 ) $("input", this.editor).val(cell21).focus().select();//借方金额
        		else  $("input", this.editor).val(val).focus().select();
        	}else if(this.grid.activeEditor.activeCell.data("column")=="budg_credit_name"){
        		var $row=grid.getCellRow(this.grid.activeEditor.activeCell);
        		var cell22=$($row[0].cells[22]).find("div").text();
        		if( cell22!="" && parseFloat(cell22)!=0 ) $("input", this.editor).val(cell22).focus().select();//借方金额
        		else  $("input", this.editor).val(val).focus().select();
        	}else{
        		val=val.replace(/\//g,"").replace(/\\/g,"");
        		$("input", this.editor).val(val).focus().select();
        	}
        	
           
        }
    });

    root.TextareaEditor = Editor.extend({
        types: [],
        name: "TextareaEditor",
        render: function () {
            if (!this.editor) {
                this.editor = document.createElement("div");
                this.editor.className = "sensei-grid-editor sensei-grid-textarea-editor";
                var textarea = document.createElement("textarea");
                this.editor.appendChild(textarea);
                this.grid.$el.append(this.editor);
            }
        },
        setDimensions: function ($td) {
            this.getElement().find("textarea").css({
                width: $td.outerWidth() + 50,
                height: $td.outerHeight() + 50
            });
        },
        getValue: function () {
        	var val=$("textarea", this.editor).val().replace(/\//g,"").replace(/\\/g,"");
        	$("textarea", this.editor).val(val);
            return $("textarea", this.editor).val();
        },
        setValue: function (val) {
        	val=val.replace(/\//g,"").replace(/\\/g,"");
            $("textarea", this.editor).val(val).focus();
        }
    });

    root.BooleanEditor = Editor.extend({
        types: [],
        name: "BooleanEditor",
        render: function () {
            if (!this.editor) {
                this.editor = document.createElement("div");
                this.editor.className = "sensei-grid-editor sensei-grid-boolean-editor";
                var $wrapper = $("<div>", {class: "sensei-grid-checkbox-wrapper"});
                $wrapper.append($("<input>", {type: "checkbox"}));
                $(this.editor).append($wrapper);
                this.grid.$el.append(this.editor);
            }
        },
        setDimensions: function ($td) {
            var css = {
                width: $td.outerWidth() - 3,
                height: $td.outerHeight() - 3,
                background: "white"
            };
            this.getElement().find(".sensei-grid-checkbox-wrapper").css(css);
        },
        getValue: function () {
            return $("input", this.editor).is(":checked") ? "true" : "false";
        },
        setValue: function (val) {
            if (val.toLowerCase() === "true") {
                $("input", this.editor).prop("checked", true);
            } else {
                $("input", this.editor).prop("checked", false);
            }
            $("input", this.editor).focus();
        }
    });

    root.SelectEditor = Editor.extend({
        types: [],
        name: "SelectEditor",
        render: function () {
            if (!this.editor) {
                this.editor = document.createElement("div");
                this.editor.className = "sensei-grid-editor sensei-grid-custom-editor";
                var select = document.createElement("select");
                this.editor.appendChild(select);
                this.grid.$el.append(this.editor);
            }
        },
        renderValues: function () {
            if (_.has(this.props, "values")) {
                var $select = this.getElement().find("select");
                $select.html(null);

                _.each(this.props["values"], function (val) {
                    var option = document.createElement("option");
                    option.value = val;
                    option.innerHTML = val;
                    $select.append(option);
                });
            }
        },
        show: function () {
            this.renderValues();
            this.getElement().show();
        },
        getValue: function () {
            return $("select", this.editor).val();
        },
        setValue: function (val) {
            $("select>option", this.editor).filter(function () {
                return $(this).val() === val;
            }).attr("selected", "selected");
            $("select").focus();
        }
    });

    /**
     * Substring matcher for typeahead plugin
     * @param strs
     * @return
     */
    var substringMatcher = function (strs,column) {
    	if(column=="summary" || column=="budg_summary"){
    		
    		 return function (query, process) {
    			if(query.indexOf("\\")!=-1 || query.indexOf("/")!=-1){
    				alert("/\\为特殊字符！");
    				return;
    		 	}
    			if(query.length>98){
    				alert("摘要输入过长！");
    				return;
    		 	}
 	            var matches, substrRegex;
 	            var isException=false;
 	            // an array that will be populated with substring matches
 	            matches = [];

 	            // regex used to determine if a string contains the substring `q`
 	            try{
 	            	substrRegex = new RegExp(query, 'i');
 	            }catch(e){
 	            	isException=true;
 	            }

 	            // iterate through the pool of strings and for any string that
 	            // contains the substring `q`, add it to the `matches` array
 	            $.each(strs, function (i, str) {
 	            	if(isException){
 	            		matches.push(str);
 	            	}else{
 	            		if(substrRegex.test(str)) {
 	 	                    matches.push(str);
 	 	                }
 	            	}
 	                
 	                if(i==50 && query.length<2)return false;
 	            });
 	            process(matches);
    		 };
    	}else{
    		
    		 return function (query, process){
//    			 if(query.indexOf("\\")!=-1 || query.indexOf("/")!=-1){
//     				alert("/\\为特殊字符！");
//     				return;
//     		 	 }
    			 //实时从后台检索科目
    			 /*$.ajax({
                 url: '../../../../hrp/acc/accvouch/making/queryAccVouchSubj.do?isCheck=false',
                 type: 'post',
                 data: {topicName: query},
                 dataType: 'json',
                 async : false,
                 success: function (result) {        
                 	console.log("result",result);
                 	process(result);
                 }
             	});*/
    			var matches, substrRegex,likeReg,vegReg;
    			var isException=false;
 	            // an array that will be populated with substring matches
 	           matches = [];
 	           try{
 	            // regex used to determine if a string contains the substring `q`
	 	           substrRegex = new RegExp($.trim(query), 'i');
	 	           likeReg = new RegExp("^"+$.trim(query).toUpperCase());//右匹配
	 	           vegReg = new RegExp("^(0|[1-9]\d{0,9})$"+$.trim(query));
 	           }catch(e){
 	        	  isException=true;
 	           }
	 	  		//var regs = new RegExp("^[0-9]"+$.trim(query));
 	            // iterate through the pool of strings and for any string that
 	            // contains the substring `q`, add it to the `matches` array
 	          
 	            $.each(strs, function (i, str) {
 	            	if((page=="vouch" && is_budg==1) || page=="vouchKind"){
 	            		//分财务预算会计加载下拉框
 	            		if(column=="subj_name" && str.kind_code=="02"){
 	            			return true;
 	            		}else if(column=="budg_subj_name" && str.kind_code=="01"){
 	            			return true;
 	            		}
            		}
 	            	
 	            	if(isException){
 	            		 matches.push(str.name);
 	            	}else{
 	            		
 	            		if(page=="vouch" || page=="vouchKind"){
 	 	            	//凭证分录页面：query.split(" ")[0]：与系统参数039一起使用
 	            			if (query.split(" ")[0]==str.id || vegReg.test(str.id) || likeReg.test(str.name.toUpperCase()) || substrRegex.test(str.spell_code) 
 	 	            				|| substrRegex.test(str.wbx_code) || substrRegex.test(str.name.split(" ")[1])) {
 	 	 	                    matches.push(str.name);
 	 	 	                }
 	            		}else{
 	            			if (query==str.name || likeReg.test(str.name.toUpperCase()) || substrRegex.test(str.spell_code) 
 	 	            				|| substrRegex.test(str.wbx_code) || substrRegex.test(str.name.split(" ")[1])) {
 	 	 	                    matches.push(str.name);
 	 	 	                }
 	            		}
 	            		/*if (vegReg.test(str.id) || likeReg.test(str.name) || substrRegex.test(str.spell_code) || substrRegex.test(str.wbx_code) || substrRegex.test(str.name.split(" ")[1]) 
 	 	                		|| (str.type!=undefined && str.type.toString().split(" ").length>1  && (substrRegex.test(str.type.split(" ")[0]) || substrRegex.test(str.type.split(" ")[1])))) {
 	 	                    matches.push(str.name);
 	 	                }*/ 
 	            	}
 	                
 	                if(i==50 && query.length<1)return false;
 	            });
 	            process(matches);
    		 };
    		
    	}
      
    };
   
    root.AutocompleteEditor = Editor.extend({
        types: [],
        name: "AutocompleteEditor",
        render: function () {
            if (!this.editor) {
                this.editor = document.createElement("div");
                this.editor.className = "sensei-grid-editor sensei-grid-ac-editor";
                var input = document.createElement("input");
                input.setAttribute("type", "text");
                this.editor.appendChild(input);
                this.grid.$el.append(this.editor);
            }
        },
        show: function () {
            this.getElement().show(); 
            $("input", this.getElement()).typeahead(
                {
                    hint: false,
                    highlight: false,
                    minLength: 0
                },
                {
                    name: 'values',
                    limit: 100,
                    source: substringMatcher(this.props,this.grid.activeEditor.activeCell.data("column"))
                }
               
            );
            var width=100;
            if((page=="vouch" || page=="vouchKind") && (this.grid.activeEditor.activeCell.data("column")=="subj_name" || this.grid.activeEditor.activeCell.data("column")=="budg_subj_name")){
            	width=moenyWith*2;
        	}
            $('.tt-menu').css('width',this.grid.activeEditor.activeCell.width()+width);
        },
        hide: function () {

            // destroy typeahead
            $("input", this.getElement()).typeahead("close");
            $("input", this.getElement()).typeahead("destroy");

            this.getElement().hide();
            this.grid.activeEditor.activeCell = null;
            this.grid.activeEditor = null;
        },
        setDimensions: function ($td) {
            this.getElement().css({
                width: $td.outerWidth() + 1,
                height: $td.outerHeight() + 1
            });
        },
        getValue: function () {
        	var val=$("input", this.editor).val();
        	if(this.grid.activeEditor.activeCell.data("column")=="summary" || this.grid.activeEditor.activeCell.data("column")=="budg_summary"){
        		val=val.replace(/\//g,"").replace(/\\/g,"");
        		if(val.length>98){
        			val=val.substring(0,98);
        		}
        	}
        	$("input", this.editor).typeahead('val', val);
            return $("input", this.editor).typeahead('val');
        	
        },
        setValue: function (val) {
        	if(this.grid.activeEditor.activeCell.data("column")=="summary" || this.grid.activeEditor.activeCell.data("column")=="budg_summary"){
        		val.replace(/\//g,"").replace(/\\/g,"");
        	}
            $("input", this.editor).typeahead('val', val).focus().select();
        }
    });
    
    
    root.DateEditor = Editor.extend({
        types: [],
        name: "DateEditor",
        datepicker: null,
        render: function () {
            if (!this.editor) {

                // create editor elements
                this.editor = document.createElement("div");
                this.editor.className = "sensei-grid-editor sensei-grid-date-editor";
                var $wrapper = $("<div>", {class: "sensei-grid-date-wrapper"});
                $wrapper.append($("<input>", {
                    type: "text",
                    class: "datepicker"
                }));
                $(this.editor).append($wrapper);
                this.grid.$el.append(this.editor);

                // load the datepicker
                $('.datepicker').pickadate({
                    //format: 'ddd mmm dd yyyy',
                	format: 'yyyy-mm-dd',
                    editable: true,
                    today: false,
                    clear: false,
                    close: false,
                    changeMonth: true,
                    changeYear: true
                });

                // store datepicker instance
                this.datepicker = $(".datepicker").pickadate('picker');
            }
        },
        show: function () {
            this.getElement().show();
            // force open datepicker
            if (this.datepicker) {
                this.datepicker.open();
            }
        },
        getValue: function () {
        	var val=$("input", this.editor).val();
        	if(val!=""){
        		val=val.replace(/^(\d{4})(\d{2})(\d{2})$/, "$1-$2-$3")
        		var a = /^(\d{4})-(\d{2})-(\d{2})$/
    			if (!a.test(val)) {
    				val= "";
    			}
        	}
            return val;
        },
        setValue: function (val) {
            $("input", this.editor).val(val).focus().select();
        }
    });

    root.DisabledEditor = Editor.extend({
        types: [],
        name: "DisabledEditor",
        render: function () {
            if (!this.editor) {

                // create editor elements
                this.editor = document.createElement("div");
                this.editor.className = "sensei-grid-editor sensei-grid-disabled-editor";
                var $input = $("<input>", {
                    type: "text",
                    readOnly: true
                });
                $(this.editor).append($input);
                this.grid.$el.append(this.editor);
            }
        },
        getValue: function () {
            return $("input", this.editor).val();
        },
        setValue: function (val) {
            $("input", this.editor).val(val).focus();
        }
    });

    root.RichEditor = Editor.extend({
        types: [],
        name: "RichEditor",
        render: function () {
            if (!this.editor) {
                this.editor = $("<div>", {class: "sensei-grid-editor sensei-grid-rich-editor"});
                var summertime = $("<div>", {class: "summertime-wrapper"});
                this.editor.append(summertime);
                this.grid.$el.append(this.editor);
            }
        },
        setDimensions: function ($td) {
            this.getElement().css({width: $td.outerWidth() + 50});
        },
        getValue: function () {
            var htmlVal = $(".summertime-wrapper", this.editor).summernote("code");
            return ("" + htmlVal).trim();
        },
        setValue: function (val) {

            $(".summertime-wrapper", this.editor).summernote({
                focus: true,
                height: 100,
                disableResizeEditor: true,
                toolbar: [
                    ['style', ['bold', 'italic', 'underline', 'clear']],
                    ['font', ['strikethrough']],
                    ['color', ['color']],
                    ['fontsize', ['fontsize']]
                ],
                callbacks: {
                    onKeydown: function (e) {

                        // prevent enter + modifier keys in summernote
                        if (e.keyCode === 13 && (e.shiftKey || e.altKey || e.metaKey || e.ctrlKey)) {
                            e.preventDefault();
                            return true;
                        }

                        // allow only enter itself in summernote, prevent event to be triggered in grid
                        if (e.keyCode === 13) {
                            e.stopImmediatePropagation();
                        }

                        // prevent tab in summernote
                        if (e.keyCode === 9) {
                            e.preventDefault();
                            return false;
                        }

                    }
                }
            });

            $(".summertime-wrapper", this.editor).summernote("code", val);
        }
    });
   
})(jQuery);