(self.webpackChunksurvey=self.webpackChunksurvey||[]).push([[769],{11172:function(t,e,n){"use strict";n.r(e),n.d(e,{default:function(){return i}});var r=n(66407),o=n(4587),i=(e=(n(9653),{components:{"hy-survey":n(44851).Z},data:function(){return{show:!0,surveyId:"4445329530320896",channelId:"4446931357162496",surveyError:"",survey:{},parameters:{externalUserId:101},options:{reset:"true"}}},created:function(){return(0,o.Z)((0,r.Z)().mark((function t(){return(0,r.Z)().wrap((function(t){for(;;)switch(t.prev=t.next){case 0:case"end":return t.stop()}}),t)})))()},computed:{style:function(){return this.survey.style},configure:function(){return this.survey&&this.survey.channel&&this.survey.channel.configure&&this.survey.channel.configure||{}},channelType:function(){return this.survey&&this.survey.channel&&this.survey.channel.type||"APP"},injectType:function(){return Number(this.configure&&this.configure.injectType)||{}},embed:function(){return this.survey&&this.survey.embed||{}},embedPaddingWidth:function(){return this.configure&&this.configure.appPaddingWidth||this.embed.appPaddingWidth||0},embedPaddingHeight:function(){return this.configure&&this.configure.appPaddingHeight||this.embed.appPaddingHeight||0}},methods:{hideMonitor:function(){this.show=!1},hideSurvey:function(){this.show=!1},handleLoad:function(t){console.debug("问卷加载成功"),this.survey=t},handleFailed:function(t){console.error("问卷打开失败: ",t)},submitFailed:function(t){console.error("问卷提交失败: ",t)},handleSubmit:function(){console.debug("问卷已提交")}}}),(0,n(69453).Z)(e,(function(){var t=this,e=t.$createElement;e=t._self._c||e;return t.show?e("v-uni-view",{staticClass:"hy-survey-index",style:{padding:t.embedPaddingHeight+"  "+t.embedPaddingWidth}},[t.surveyId&&t.channelId?e("hy-survey",{attrs:{delay:3e3,resize:!0,show:!0,surveyId:t.surveyId,channelId:t.channelId,options:t.options,parameters:t.parameters},on:{hide:function(e){arguments[0]=e=t.$handleEvent(e),t.hideMonitor.apply(void 0,arguments)},load:function(e){arguments[0]=e=t.$handleEvent(e),t.handleLoad.apply(void 0,arguments)},loadfailed:function(e){arguments[0]=e=t.$handleEvent(e),t.handleFailed.apply(void 0,arguments)},submitfailed:function(e){arguments[0]=e=t.$handleEvent(e),t.submitFailed.apply(void 0,arguments)},submit:function(e){arguments[0]=e=t.$handleEvent(e),t.handleSubmit.apply(void 0,arguments)}}}):t._e()],1):t._e()}),[],!1,null,null,null,!1,void 0,void 0).exports)},72443:function(t,e,n){n(26800)("asyncIterator")},4587:function(t,e,n){"use strict";function r(t,e,n,r,o,i,a){try{var u=t[i](a),c=u.value}catch(t){return void n(t)}u.done?e(c):Promise.resolve(c).then(r,o)}function o(t){return function(){var e=this,n=arguments;return new Promise((function(o,i){var a=t.apply(e,n);function u(t){r(a,o,i,u,c,"next",t)}function c(t){r(a,o,i,u,c,"throw",t)}u(void 0)}))}}n.d(e,{Z:function(){return o}}),n(41539)},66407:function(t,e,n){"use strict";n.d(e,{Z:function(){return o}}),n(69070),n(82526),n(41817),n(41539),n(32165),n(78783),n(33948),n(72443),n(39341),n(73706),n(10408),n(30489),n(21703),n(96647),n(57658),n(54747),n(68304),n(65069),n(47042);var r=n(96257);function o(){
/*! regenerator-runtime -- Copyright (c) 2014-present, Facebook, Inc. -- license (MIT): https://github.com/facebook/regenerator/blob/main/LICENSE */
o=function(){return t};var t={},e=Object.prototype,n=e.hasOwnProperty,i=Object.defineProperty||function(t,e,n){t[e]=n.value},a="function"==typeof Symbol?Symbol:{},u=a.iterator||"@@iterator",c=a.asyncIterator||"@@asyncIterator",s=a.toStringTag||"@@toStringTag";function h(t,e,n){return Object.defineProperty(t,e,{value:n,enumerable:!0,configurable:!0,writable:!0}),t[e]}try{h({},"")}catch(e){h=function(t,e,n){return t[e]=n}}function l(t,e,n,r){var o,a,u,c;e=e&&e.prototype instanceof p?e:p,e=Object.create(e.prototype),r=new E(r||[]);return i(e,"_invoke",{value:(o=t,a=n,u=r,c="suspendedStart",function(t,e){if("executing"===c)throw new Error("Generator is already running");if("completed"===c){if("throw"===t)throw e;return _()}for(u.method=t,u.arg=e;;){var n=u.delegate;if(n&&(n=function t(e,n){var r=n.method,o=e.iterator[r];return void 0===o?(n.delegate=null,"throw"===r&&e.iterator.return&&(n.method="return",n.arg=void 0,t(e,n),"throw"===n.method)||"return"!==r&&(n.method="throw",n.arg=new TypeError("The iterator does not provide a '"+r+"' method")),d):(r=f(o,e.iterator,n.arg),"throw"===r.type?(n.method="throw",n.arg=r.arg,n.delegate=null,d):(o=r.arg,o?o.done?(n[e.resultName]=o.value,n.next=e.nextLoc,"return"!==n.method&&(n.method="next",n.arg=void 0),n.delegate=null,d):o:(n.method="throw",n.arg=new TypeError("iterator result is not an object"),n.delegate=null,d)))}(n,u),n)){if(n===d)continue;return n}if("next"===u.method)u.sent=u._sent=u.arg;else if("throw"===u.method){if("suspendedStart"===c)throw c="completed",u.arg;u.dispatchException(u.arg)}else"return"===u.method&&u.abrupt("return",u.arg);if(c="executing",n=f(o,a,u),"normal"===n.type){if(c=u.done?"completed":"suspendedYield",n.arg===d)continue;return{value:n.arg,done:u.done}}"throw"===n.type&&(c="completed",u.method="throw",u.arg=n.arg)}})}),e}function f(t,e,n){try{return{type:"normal",arg:t.call(e,n)}}catch(t){return{type:"throw",arg:t}}}t.wrap=l;var d={};function p(){}function y(){}function v(){}a={};var g=(h(a,u,(function(){return this})),Object.getPrototypeOf),m=(g=g&&g(g(P([]))),g&&g!==e&&n.call(g,u)&&(a=g),v.prototype=p.prototype=Object.create(a));function w(t){["next","throw","return"].forEach((function(e){h(t,e,(function(t){return this._invoke(e,t)}))}))}function b(t,e){var o;i(this,"_invoke",{value:function(i,a){function u(){return new e((function(o,u){!function o(i,a,u,c){var s;i=f(t[i],t,a);if("throw"!==i.type)return(a=(s=i.arg).value)&&"object"==(0,r.Z)(a)&&n.call(a,"__await")?e.resolve(a.__await).then((function(t){o("next",t,u,c)}),(function(t){o("throw",t,u,c)})):e.resolve(a).then((function(t){s.value=t,u(s)}),(function(t){return o("throw",t,u,c)}));c(i.arg)}(i,a,o,u)}))}return o=o?o.then(u,u):u()}})}function x(t){var e={tryLoc:t[0]};1 in t&&(e.catchLoc=t[1]),2 in t&&(e.finallyLoc=t[2],e.afterLoc=t[3]),this.tryEntries.push(e)}function L(t){var e=t.completion||{};e.type="normal",delete e.arg,t.completion=e}function E(t){this.tryEntries=[{tryLoc:"root"}],t.forEach(x,this),this.reset(!0)}function P(t){if(t){var e,r=t[u];if(r)return r.call(t);if("function"==typeof t.next)return t;if(!isNaN(t.length))return e=-1,(r=function r(){for(;++e<t.length;)if(n.call(t,e))return r.value=t[e],r.done=!1,r;return r.value=void 0,r.done=!0,r}).next=r}return{next:_}}function _(){return{value:void 0,done:!0}}return i(m,"constructor",{value:y.prototype=v,configurable:!0}),i(v,"constructor",{value:y,configurable:!0}),y.displayName=h(v,s,"GeneratorFunction"),t.isGeneratorFunction=function(t){return t="function"==typeof t&&t.constructor,!!t&&(t===y||"GeneratorFunction"===(t.displayName||t.name))},t.mark=function(t){return Object.setPrototypeOf?Object.setPrototypeOf(t,v):(t.__proto__=v,h(t,s,"GeneratorFunction")),t.prototype=Object.create(m),t},t.awrap=function(t){return{__await:t}},w(b.prototype),h(b.prototype,c,(function(){return this})),t.AsyncIterator=b,t.async=function(e,n,r,o,i){void 0===i&&(i=Promise);var a=new b(l(e,n,r,o),i);return t.isGeneratorFunction(n)?a:a.next().then((function(t){return t.done?t.value:a.next()}))},w(m),h(m,s,"Generator"),h(m,u,(function(){return this})),h(m,"toString",(function(){return"[object Generator]"})),t.keys=function(t){var e,n=Object(t),r=[];for(e in n)r.push(e);return r.reverse(),function t(){for(;r.length;){var e=r.pop();if(e in n)return t.value=e,t.done=!1,t}return t.done=!0,t}},t.values=P,E.prototype={constructor:E,reset:function(t){if(this.prev=0,this.next=0,this.sent=this._sent=void 0,this.done=!1,this.delegate=null,this.method="next",this.arg=void 0,this.tryEntries.forEach(L),!t)for(var e in this)"t"===e.charAt(0)&&n.call(this,e)&&!isNaN(+e.slice(1))&&(this[e]=void 0)},stop:function(){this.done=!0;var t=this.tryEntries[0].completion;if("throw"===t.type)throw t.arg;return this.rval},dispatchException:function(t){if(this.done)throw t;var e=this;function r(n,r){return a.type="throw",a.arg=t,e.next=n,r&&(e.method="next",e.arg=void 0),!!r}for(var o=this.tryEntries.length-1;0<=o;--o){var i=this.tryEntries[o],a=i.completion;if("root"===i.tryLoc)return r("end");if(i.tryLoc<=this.prev){var u=n.call(i,"catchLoc"),c=n.call(i,"finallyLoc");if(u&&c){if(this.prev<i.catchLoc)return r(i.catchLoc,!0);if(this.prev<i.finallyLoc)return r(i.finallyLoc)}else if(u){if(this.prev<i.catchLoc)return r(i.catchLoc,!0)}else{if(!c)throw new Error("try statement without catch or finally");if(this.prev<i.finallyLoc)return r(i.finallyLoc)}}}},abrupt:function(t,e){for(var r=this.tryEntries.length-1;0<=r;--r){var o=this.tryEntries[r];if(o.tryLoc<=this.prev&&n.call(o,"finallyLoc")&&this.prev<o.finallyLoc){var i=o;break}}var a=(i=i&&("break"===t||"continue"===t)&&i.tryLoc<=e&&e<=i.finallyLoc?null:i)?i.completion:{};return a.type=t,a.arg=e,i?(this.method="next",this.next=i.finallyLoc,d):this.complete(a)},complete:function(t,e){if("throw"===t.type)throw t.arg;return"break"===t.type||"continue"===t.type?this.next=t.arg:"return"===t.type?(this.rval=this.arg=t.arg,this.method="return",this.next="end"):"normal"===t.type&&e&&(this.next=e),d},finish:function(t){for(var e=this.tryEntries.length-1;0<=e;--e){var n=this.tryEntries[e];if(n.finallyLoc===t)return this.complete(n.completion,n.afterLoc),L(n),d}},catch:function(t){for(var e=this.tryEntries.length-1;0<=e;--e){var n,r,o=this.tryEntries[e];if(o.tryLoc===t)return"throw"===(n=o.completion).type&&(r=n.arg,L(o)),r}throw new Error("illegal catch attempt")},delegateYield:function(t,e,n){return this.delegate={iterator:P(t),resultName:e,nextLoc:n},"next"===this.method&&(this.arg=void 0),d}},t}}}]);