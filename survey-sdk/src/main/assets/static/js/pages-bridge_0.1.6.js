(self.webpackChunksurvey=self.webpackChunksurvey||[]).push([[759],{43069:function(t,e,n){var r=n(86202);(r="string"==typeof(r=r.__esModule?r.default:r)?[[t.id,r,""]]:r).locals&&(t.exports=r.locals),(0,n(56).Z)("63f6b35c",r,!0,{sourceMap:!1,shadowMode:!1})},12941:function(t,e,n){"use strict";n.r(e),n.d(e,{default:function(){return a}});var r=n(66407),o=n(4587),i=(e=(n(38862),n(92222),n(32249)),n(42502)),a=(e={components:{"hy-survey":e.Z},data:function(){return{show:!0,surveyId:null,channelId:null,delay:3e3,surveyError:"",logger:window.console.log,survey:{},borderRadiusMode:"CENTER",parameters:{accessCode:111},closed:!1,options:{}}},created:function(){var t=this;return(0,o.Z)((0,r.Z)().mark((function e(){return(0,r.Z)().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:console.debug("setup bridge"),document.addEventListener("init",t.handleInit),document.addEventListener("show",t.handleShow),document.addEventListener("close",t.handleClose),t.injectLog();case 5:case"end":return e.stop()}}),e)})))()},methods:{injectLog:function(){window.console.log=this.forwardLog,window.console.info=this.forwardLog,window.console.warn=this.forwardLog,window.console.debug=this.forwardLog,this._callNative("surveyProxy",JSON.stringify({type:"init"}))},forwardLog:function(t){this._callNative("logger",t)},handleShow:function(t){console.debug("received show command"),this.show=!0},handleClose:function(t){console.debug("received close command")},handleInit:function(t){var e=t.detail||{},n=e.surveyId,r=e.channelId,o=e.delay,a=e.parameters,c=e.server;e=e.borderRadiusMode;this.borderRadiusMode=e||"CENTER",c&&(0,i.z)(null,{collectorMethod:"APP"},c),n&&r?(this.delay=o||3e3,this.surveyId=n,this.channelId=r,this.parameters=a||{},this.closed=!1,console.debug("init survey ".concat(n," ").concat(r))):console.warn("invalid command ".concat(JSON.stringify(t.detail||{})))},handleHide:function(t){this.show=!1,this.closed=!0,this._callNative("surveyProxy",JSON.stringify({type:"close",reason:t}))},handleCancel:function(){console.debug("cancel"),this.$refs.survey&&this.$refs.survey.distubUploadEve("CLOSE"),this.show=!1,this._callNative("surveyProxy",JSON.stringify({type:"cancel"}))},handleSubmit:function(){this._callNative("surveyProxy",JSON.stringify({type:"submit"}))},handleLoad:function(t){this._callNative("logger",JSON.stringify({type:"logger"})),this._callNative("surveyProxy",JSON.stringify({type:"load",configure:t&&t.channel&&t.channel.configure}))},handleSize:function(t){this.closed||this._callNative("surveyProxy",JSON.stringify({type:"size",value:t}))},_callNative:function(t,e){window.webkit&&window.webkit.messageHandlers&&window.webkit.messageHandlers[t]?window.webkit.messageHandlers[t].postMessage(e):window[t]?window[t].postMessage(e):this.logger("".concat(t,": "),e)}}},n(43069),(0,n(69453).Z)(e,(function(){var t=this,e=t.$createElement;e=t._self._c||e;return t.surveyId&&t.channelId?e("v-uni-view",{staticClass:"hy-survey-embed-index bridge"},[e("v-uni-view",{staticClass:"hy-wx-close",on:{click:function(e){arguments[0]=e=t.$handleEvent(e),t.handleCancel.apply(void 0,arguments)}}}),t.surveyId&&t.channelId?e("hy-survey",{ref:"survey",attrs:{borderRadiusMode:t.borderRadiusMode,delay:t.delay,resize:!0,show:t.show,surveyId:t.surveyId,channelId:t.channelId,options:t.options,parameters:t.parameters},on:{hide:function(e){arguments[0]=e=t.$handleEvent(e),t.handleHide.apply(void 0,arguments)},size:function(e){arguments[0]=e=t.$handleEvent(e),t.handleSize.apply(void 0,arguments)},submit:function(e){arguments[0]=e=t.$handleEvent(e),t.handleSubmit.apply(void 0,arguments)},load:function(e){arguments[0]=e=t.$handleEvent(e),t.handleLoad.apply(void 0,arguments)}}}):t._e()],1):t._e()}),[],!1,null,"ee42cc20",null,!1,void 0,void 0).exports)},72443:function(t,e,n){n(26800)("asyncIterator")},86202:function(t,e,n){"use strict";n.r(e);var r=n(8081),o=(r=n.n(r),n(23645)),i=(o=n.n(o),n(61667));i=n.n(i),n=new URL(n(87044),n.b),o=o()(r()),r=i()(n);o.push([t.id,".bridge[data-v-ee42cc20]{position:relative;height:100%}.hy-wx-close[data-v-ee42cc20]{position:fixed;z-index:1000;right:0;top:0;width:12px;height:12px;padding:15px;background:url("+r+");background-size:12px 12px;background-repeat:no-repeat;background-position:50%}",""]),e.default=o},4587:function(t,e,n){"use strict";function r(t,e,n,r,o,i,a){try{var c=t[i](a),s=c.value}catch(t){return void n(t)}c.done?e(s):Promise.resolve(s).then(r,o)}function o(t){return function(){var e=this,n=arguments;return new Promise((function(o,i){var a=t.apply(e,n);function c(t){r(a,o,i,c,s,"next",t)}function s(t){r(a,o,i,c,s,"throw",t)}c(void 0)}))}}n.d(e,{Z:function(){return o}}),n(41539)},66407:function(t,e,n){"use strict";n.d(e,{Z:function(){return o}}),n(69070),n(82526),n(41817),n(41539),n(32165),n(78783),n(33948),n(72443),n(39341),n(73706),n(10408),n(30489),n(21703),n(96647),n(57658),n(54747),n(68304),n(65069),n(47042);var r=n(96257);function o(){
/*! regenerator-runtime -- Copyright (c) 2014-present, Facebook, Inc. -- license (MIT): https://github.com/facebook/regenerator/blob/main/LICENSE */
o=function(){return t};var t={},e=Object.prototype,n=e.hasOwnProperty,i=Object.defineProperty||function(t,e,n){t[e]=n.value},a="function"==typeof Symbol?Symbol:{},c=a.iterator||"@@iterator",s=a.asyncIterator||"@@asyncIterator",u=a.toStringTag||"@@toStringTag";function l(t,e,n){return Object.defineProperty(t,e,{value:n,enumerable:!0,configurable:!0,writable:!0}),t[e]}try{l({},"")}catch(e){l=function(t,e,n){return t[e]=n}}function d(t,e,n,r){var o,a,c,s;e=e&&e.prototype instanceof v?e:v,e=Object.create(e.prototype),r=new E(r||[]);return i(e,"_invoke",{value:(o=t,a=n,c=r,s="suspendedStart",function(t,e){if("executing"===s)throw new Error("Generator is already running");if("completed"===s){if("throw"===t)throw e;return N()}for(c.method=t,c.arg=e;;){var n=c.delegate;if(n&&(n=function t(e,n){var r=n.method,o=e.iterator[r];return void 0===o?(n.delegate=null,"throw"===r&&e.iterator.return&&(n.method="return",n.arg=void 0,t(e,n),"throw"===n.method)||"return"!==r&&(n.method="throw",n.arg=new TypeError("The iterator does not provide a '"+r+"' method")),f):(r=h(o,e.iterator,n.arg),"throw"===r.type?(n.method="throw",n.arg=r.arg,n.delegate=null,f):(o=r.arg,o?o.done?(n[e.resultName]=o.value,n.next=e.nextLoc,"return"!==n.method&&(n.method="next",n.arg=void 0),n.delegate=null,f):o:(n.method="throw",n.arg=new TypeError("iterator result is not an object"),n.delegate=null,f)))}(n,c),n)){if(n===f)continue;return n}if("next"===c.method)c.sent=c._sent=c.arg;else if("throw"===c.method){if("suspendedStart"===s)throw s="completed",c.arg;c.dispatchException(c.arg)}else"return"===c.method&&c.abrupt("return",c.arg);if(s="executing",n=h(o,a,c),"normal"===n.type){if(s=c.done?"completed":"suspendedYield",n.arg===f)continue;return{value:n.arg,done:c.done}}"throw"===n.type&&(s="completed",c.method="throw",c.arg=n.arg)}})}),e}function h(t,e,n){try{return{type:"normal",arg:t.call(e,n)}}catch(t){return{type:"throw",arg:t}}}t.wrap=d;var f={};function v(){}function p(){}function y(){}a={};var g=(l(a,c,(function(){return this})),Object.getPrototypeOf),w=(g=g&&g(g(_([]))),g&&g!==e&&n.call(g,c)&&(a=g),y.prototype=v.prototype=Object.create(a));function m(t){["next","throw","return"].forEach((function(e){l(t,e,(function(t){return this._invoke(e,t)}))}))}function b(t,e){var o;i(this,"_invoke",{value:function(i,a){function c(){return new e((function(o,c){!function o(i,a,c,s){var u;i=h(t[i],t,a);if("throw"!==i.type)return(a=(u=i.arg).value)&&"object"==(0,r.Z)(a)&&n.call(a,"__await")?e.resolve(a.__await).then((function(t){o("next",t,c,s)}),(function(t){o("throw",t,c,s)})):e.resolve(a).then((function(t){u.value=t,c(u)}),(function(t){return o("throw",t,c,s)}));s(i.arg)}(i,a,o,c)}))}return o=o?o.then(c,c):c()}})}function x(t){var e={tryLoc:t[0]};1 in t&&(e.catchLoc=t[1]),2 in t&&(e.finallyLoc=t[2],e.afterLoc=t[3]),this.tryEntries.push(e)}function L(t){var e=t.completion||{};e.type="normal",delete e.arg,t.completion=e}function E(t){this.tryEntries=[{tryLoc:"root"}],t.forEach(x,this),this.reset(!0)}function _(t){if(t){var e,r=t[c];if(r)return r.call(t);if("function"==typeof t.next)return t;if(!isNaN(t.length))return e=-1,(r=function r(){for(;++e<t.length;)if(n.call(t,e))return r.value=t[e],r.done=!1,r;return r.value=void 0,r.done=!0,r}).next=r}return{next:N}}function N(){return{value:void 0,done:!0}}return i(w,"constructor",{value:p.prototype=y,configurable:!0}),i(y,"constructor",{value:p,configurable:!0}),p.displayName=l(y,u,"GeneratorFunction"),t.isGeneratorFunction=function(t){return t="function"==typeof t&&t.constructor,!!t&&(t===p||"GeneratorFunction"===(t.displayName||t.name))},t.mark=function(t){return Object.setPrototypeOf?Object.setPrototypeOf(t,y):(t.__proto__=y,l(t,u,"GeneratorFunction")),t.prototype=Object.create(w),t},t.awrap=function(t){return{__await:t}},m(b.prototype),l(b.prototype,s,(function(){return this})),t.AsyncIterator=b,t.async=function(e,n,r,o,i){void 0===i&&(i=Promise);var a=new b(d(e,n,r,o),i);return t.isGeneratorFunction(n)?a:a.next().then((function(t){return t.done?t.value:a.next()}))},m(w),l(w,u,"Generator"),l(w,c,(function(){return this})),l(w,"toString",(function(){return"[object Generator]"})),t.keys=function(t){var e,n=Object(t),r=[];for(e in n)r.push(e);return r.reverse(),function t(){for(;r.length;){var e=r.pop();if(e in n)return t.value=e,t.done=!1,t}return t.done=!0,t}},t.values=_,E.prototype={constructor:E,reset:function(t){if(this.prev=0,this.next=0,this.sent=this._sent=void 0,this.done=!1,this.delegate=null,this.method="next",this.arg=void 0,this.tryEntries.forEach(L),!t)for(var e in this)"t"===e.charAt(0)&&n.call(this,e)&&!isNaN(+e.slice(1))&&(this[e]=void 0)},stop:function(){this.done=!0;var t=this.tryEntries[0].completion;if("throw"===t.type)throw t.arg;return this.rval},dispatchException:function(t){if(this.done)throw t;var e=this;function r(n,r){return a.type="throw",a.arg=t,e.next=n,r&&(e.method="next",e.arg=void 0),!!r}for(var o=this.tryEntries.length-1;0<=o;--o){var i=this.tryEntries[o],a=i.completion;if("root"===i.tryLoc)return r("end");if(i.tryLoc<=this.prev){var c=n.call(i,"catchLoc"),s=n.call(i,"finallyLoc");if(c&&s){if(this.prev<i.catchLoc)return r(i.catchLoc,!0);if(this.prev<i.finallyLoc)return r(i.finallyLoc)}else if(c){if(this.prev<i.catchLoc)return r(i.catchLoc,!0)}else{if(!s)throw new Error("try statement without catch or finally");if(this.prev<i.finallyLoc)return r(i.finallyLoc)}}}},abrupt:function(t,e){for(var r=this.tryEntries.length-1;0<=r;--r){var o=this.tryEntries[r];if(o.tryLoc<=this.prev&&n.call(o,"finallyLoc")&&this.prev<o.finallyLoc){var i=o;break}}var a=(i=i&&("break"===t||"continue"===t)&&i.tryLoc<=e&&e<=i.finallyLoc?null:i)?i.completion:{};return a.type=t,a.arg=e,i?(this.method="next",this.next=i.finallyLoc,f):this.complete(a)},complete:function(t,e){if("throw"===t.type)throw t.arg;return"break"===t.type||"continue"===t.type?this.next=t.arg:"return"===t.type?(this.rval=this.arg=t.arg,this.method="return",this.next="end"):"normal"===t.type&&e&&(this.next=e),f},finish:function(t){for(var e=this.tryEntries.length-1;0<=e;--e){var n=this.tryEntries[e];if(n.finallyLoc===t)return this.complete(n.completion,n.afterLoc),L(n),f}},catch:function(t){for(var e=this.tryEntries.length-1;0<=e;--e){var n,r,o=this.tryEntries[e];if(o.tryLoc===t)return"throw"===(n=o.completion).type&&(r=n.arg,L(o)),r}throw new Error("illegal catch attempt")},delegateYield:function(t,e,n){return this.delegate={iterator:_(t),resultName:e,nextLoc:n},"next"===this.method&&(this.arg=void 0),f}},t}}}]);