/**
 * @license Copyright (c) 2003-2016, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here. For example:
	config.language = 'zh-cn';
	// config.uiColor = '#AADC6E';
	//config.toolbarCanCollapse = true;
	config.width = 'auto';
	config.height = $(window).height()-180;
	
	config.toolbar = 'Full';     
    
	config.toolbar_Full =     
	[     
	    ['Source','-','Save','NewPage','Preview','Print','-','Templates'],     
	    ['Cut','Copy','Paste','PasteText','PasteFromWord','-', 'SpellChecker'],     
	    ['Undo','Redo','-','Find','Replace','-','SelectAll', '-','Scayt'],     
	    ['Form', 'Checkbox', 'Radio', 'TextField', 'Textarea', 'Select', 'Button', 'ImageButton', 'HiddenField'],     
	    '/',     
	    ['Bold','Italic','Underline','Strike','-','Subscript','Superscript','-','RemoveFormat'],     
	    ['NumberedList','BulletedList','-','Outdent','Indent','Blockquote','CreateDiv'],     
	    ['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock','-','BidiLtr', 'BidiRtl'],     
	    ['Link','Unlink','Anchor'],     
	    ['Image','Flash','Table','HorizontalRule','Smiley','SpecialChar','PageBreak','Iframe'],     
	    '/',     
	    ['Styles','Format','Font','FontSize'],     
	    ['TextColor','BGColor'],     
	    ['Maximize', 'ShowBlocks']     
	];     
	    
	config.toolbar_Basic =     
	[     
	    ['Bold', 'Italic', '-', 'NumberedList', 'BulletedList', '-', 'Link', 'Unlink']     
	];
	//About,Language
};

