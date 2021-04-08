/*******************************************************************************
 * 文件名称：plug-in.js
 * 文件说明：js一些自己封装的好用的插件
 * 创建者：dongmingdi
 * 创建时间：2015-10-16
 ******************************************************************************/
(function($, undef)
{
    $.fn.template = {
        symbol: {
            pattern: '#\\{([^\\}]+?)\\}',
            split: '.',
            span: ',',
            fn: '^#fn\\|(.+?)(?:&(.+?))?$',
            judge: '^#if\\|(.+?)\\?(.+?)\\:(.+)$',
            //judge: '^#if\\|(?:#fn\\|(.+?))\\?(.+?)\\:(.+)$',
            loop: '^#loop\\|(.+?)\\|(.+)$',
            sub: '^#sub\\|(.+?)(?:\\|(.+))?$',
            empty: '^#empty$',
            pre: '^#{#pre\\|(.+)}$',
            code: '^#{#code\\|(.+)}$',
            func: 'var tf = function() { return <code>; };'
        },

        getHandle: function(path, data)
        {
            var key = path.split(this.symbol.split), handle = data;
            for (var i = 0, count = key.length; i < count; i++)
            {
                handle = handle[key[i]];
                if (typeof handle === 'undefined')
                    return undef;
            }
            return handle;
        },

        wrap: function(symbol)
        {
            return '#{' + symbol + '}';
        },

//        formatList: function(options)
//        {
//            options = $.extend({}, $.fn.template.defaults, options);
//            var t = this;
//            var template = options.template;
//            var list = options.list;
//            if('' == template || '[object Array]' != Object.prototype.toString.call(list) || 0 == list.length)
//            {
//                return '';
//            }
//            var html = [];
//            for(var i = 0; i < list.length; i++)
//            {
//                var item = t.evaluate(template, list[i]);
//                html.push(item);
//            }
//            return html.join('');
//        },

        evaluate: function(template, data, attachment)
        {
            var t = this, empty = '';
            if ('[object Array]' == Object.prototype.toString.call(data))
            {
                var product = [], count = data.length, item;
                for (var i = 0; i < count; i++)
                {
                    item = data[i];
                    item.tIndex = i;
                    item.tClosest = data.tClosest;
                    product.push(this.evaluate(template, item, attachment));
                }
                return product.join('');
            }
            var symbol = this.symbol;
            var prePattern = new RegExp(symbol.pre, 'gi');
            template = template.replace(prePattern, function(a, b)
            {
                return t.evaluate(t.wrap(b), data, attachment);
            });
            var pattern = new RegExp(symbol.pattern, 'gi');
            return template.replace(pattern, function(a, b)
            {
                var trimer = new RegExp("(^[\\s\\t\\xa0\\u3000]+)|([\\u3000\\xa0\\s\\t]+\x24)", "g");
                b = String(b).replace(trimer, "");
                if ((new RegExp(symbol.fn, 'gi')).test(b))
                {
                    var methodName = RegExp.$1;
                    var method = (attachment && attachment.method && attachment.method[RegExp.$1]) || t.getHandle(methodName, window) || attachment[methodName] || attachment;
                    var parameters = [];
                    if (RegExp.$2)
                    {
                        var group = RegExp.$2.split(t.symbol.span);
                        $.each(group, function(i, item)
                        {
                            var value = t.getHandle(item, data);
                            if (typeof value === 'undefined')
                            //value = '';
                            //if (value !== '' && !value)
                                value = eval(item);
                            parameters.push(value);
                        });
                    }
                    parameters.push(attachment);
                    if (method)
                        return method.apply(data, parameters);
                    else
                        return empty;
                }
                else if ((new RegExp(symbol.judge, 'gi')).test(b))
                {
                    var pathA = RegExp.$2,
                        pathB = RegExp.$3,
                        judgement = eval('(' + t.evaluate(t.wrap(RegExp.$1), data, attachment) + ')');
                    //                        if (!term)
                    //                            return empty;
                    return t.evaluate(judgement ? t.wrap(pathA) : t.wrap(pathB), data, attachment);

                    //                        var term = attachment.method[RegExp.$1],
                    //                        pathA = RegExp.$2,
                    //                        pathB = RegExp.$3;
                    //                        if (!term)
                    //                            return empty;
                    //                        return t.evaluate(term.call(data, data.tClosest) ? t.wrap(pathA) : t.wrap(pathB), data, attachment);
                }
                else if ((new RegExp(symbol.loop, 'gi')).test(b))
                {
                    var action = t.wrap(RegExp.$2);
                    var range = t.getHandle(RegExp.$1, data);
                    //var range = t.evaluate(t.wrap(RegExp.$1), data, attachment);
                    if ('[object Array]' != Object.prototype.toString.call(range))
                        return empty;
                    range.tClosest = data;
                    return t.evaluate(action, range, attachment)
                }
                else if ((new RegExp(symbol.sub, 'gi')).test(b))
                {
                    var sub = attachment.template[RegExp.$1];
                    if (!sub)
                        return empty;
                    var host = data;
                    if (RegExp.$2)  //todo:fix
                        host = t.getHandle(RegExp.$2, host);
                    return t.evaluate(sub, host, attachment);
                }
                else if ((new RegExp(symbol.code, 'gi')).test(a))
                {
                    var code = RegExp.$1;
                    eval(symbol.func.replace('<code>', code));
                    return tf.call(data);
                }
                else if ((new RegExp(symbol.empty, 'gi')).test(a))
                {
                    return empty;
                }
                else
                {
                    var handle = t.getHandle(b, data);
                    if (typeof handle === 'undefined' || handle == null)
                        return empty;
                    else
                        return handle.toString();
                }
            });
        }
    };

//    $.fn.template.defaults = {
//        template : '', //列表渲染模板
//        list : [] //数据数组
//    }
})($);

