var CustomSelect = (function () {

    function CustomSelect(selectElement) {
        var self = this;
        self.selectElement = selectElement;
        self.liList = '';
        self.init();
    }

    CustomSelect.prototype.init = function () {
        var $selectElement = $(this.selectElement);
            selectedOption = $selectElement.find('option').filter(':selected'),
            selectDiv = $('<div>', {'class' : 'custom-selectDiv'}),
            selectUl = $('<ul>', {'class': 'custom-selectUl'}),
            zIndex = 999,
            this.selectDiv = selectDiv,
            this.selectUl = selectUl;
        this.createSelectList();
        //hide the old select
        selectDiv.css('z-index',zIndex);
        $selectElement.hide();
        selectUl.html(this.liList);
        selectDiv.html('<a href="#" rel="'+selectedOption.val()+'" class="selectedValue">'+selectedOption.text()+'</a>');
        selectUl.appendTo(selectDiv);
        $selectElement.after(selectDiv);
        this.initEvents(selectDiv, selectUl);
    };

    CustomSelect.prototype.createSelectList = function () {
        var $selectElement = $(this.selectElement),
            self = this;
        if($selectElement.has('optgroup').length) {
            $selectElement.find('optgroup').each(function(i,$selectElement){
                self.liList += '<li><span class="optionGroupTitle">'+$(this).attr('label')+'</span><ul>';
                $(this).children().each(function(i,$selectElement){
                    self.liList += '<li><a rel="'+$(this).val()+'" href="#">'+$(this).text()+'</a></li>';
                });
                self.liList += '</ul></li>';
            });
        } else {
            $selectElement.find('option').each(function(i,$selectElement){
                self.liList += '<li><a rel="'+$(this).val()+'" href="#">'+$(this).text()+'</a></li>';
            });
        }
    };

    CustomSelect.prototype.cancelDefault = function (e) {
        if (e.preventDefault) {
            e.preventDefault();
            e.stopPropagation();
        } else {
            e.cancelBubble = false;
            e.returnValue = false;
        }
        return false;
    };

    CustomSelect.prototype.initEvents = function (selectDiv, selectUl) {
        var $selectElement = $(this.selectElement),
            self = this;
        selectDiv.find('a.selectedValue').click(function(e){
            self.cancelDefault(e);
            self.closeList($('ul.custom-selectUl').not($(this).next()));
            self.spreadList(selectUl);
        });

        selectUl.find('a').click(function(e){
            self.cancelDefault(e);
            self.selectedValue = $(this).text();
            selectDiv.find('a.selectedValue').text($(this).text());
            $selectElement.val($(this).attr('rel'));
            $selectElement.trigger('change');
            self.closeList(selectUl);
        });
        $(document).click(function(e){
            self.closeList($('.custom-selectUl').not(':hidden'));
        });
    };

    CustomSelect.prototype.setSelected = function (name) {
        if (this.selectedValue !== name) {
            var selectDiv = this.selectDiv;
            selectDiv.find('a.selectedValue').text(name);
        }
    };

    CustomSelect.prototype.spreadList = function (ele) {
        if(!ele.is(':hidden')) {
            ele.stop(true,true).slideUp('fast');
        } else {
            ele.stop(true,true).slideDown('fast');
        }
    };

    CustomSelect.prototype.closeList = function (ele) {
        ele.stop(true,true).slideUp('fast');
    };

    return CustomSelect;
})();