/**
 * Author: Bill Lv<billcc.lv@hotmail.com>
 * Date: 2016-06-19
 */
var sidebar = {
    select: function (selection) {
        var li = $('#' + selection);
        li.parent().parent().addClass('start active open').children('a.nav-toggle').append('<span class="selected"></span>').children('.arrow').addClass('open');
        li.addClass('start active open').children('a').append('<span class="selected"></span>');
    }
};