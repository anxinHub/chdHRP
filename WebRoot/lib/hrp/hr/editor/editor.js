function loadEditor(tableWords){
	//根据DOM元素的id构造出一个编辑器
	editor=CodeMirror.fromTextArea(document.getElementById("code"),{
	    mode:"text/c-orcl", //实现sql代码高亮
	    lineNumbers:true,
	    theme:"default",
	    keyMap: "default",
	    extraKeys:{"Tab":"autocomplete"},
	    hint: CodeMirror.hint.sql,
	    lineWrapping: true,         //是否换行
	    foldGutter: true,           //是否折叠
	    gutters: ["CodeMirror-linenumbers", "CodeMirror-foldgutter"], //添加行号栏，折叠栏
	    hintOptions: {
	    	tables: tableWords
	    }
	
	});
	
	editor.on("keyup", function (cm, event) {
	    //所有的字母和'$','{','.'在键按下之后都将触发自动完成
	    if (!cm.state.completionActive &&
	        ((event.keyCode >= 65 && event.keyCode <= 90 ) || event.keyCode == 52 || event.keyCode == 219 || event.keyCode == 190)) {
	        CodeMirror.commands.autocomplete(cm, null, {completeSingle: false});
	    }
	});
}

function getSelectedRange() {
    return { from: editor.getCursor(true), to: editor.getCursor(false) };
}

 