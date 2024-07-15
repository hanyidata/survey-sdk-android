"use strict";(self.webpackChunksurvey=self.webpackChunksurvey||[]).push([[759],{6924:function(e,n,s){s.r(n),s.d(n,{default:function(){return l}});var t=s(6407),i=s(4587),o=(n=(s(8862),s(2222),s(210)),s(2502)),l=(n={components:{"hy-survey":n.Z},data:function(){return{show:!0,surveyId:null,channelId:null,clientId:null,sendId:null,delay:1e3,surveyError:"",logger:window.console.log,survey:{},surveyJson:null,borderRadiusMode:null,parameters:{accessCode:111},closed:!1,options:{halfscreen:!1},isShowSurvey:!1}},computed:{style:function(){return this.survey.style}},created:function(){var e=this;return(0,i.Z)((0,t.Z)().mark((function n(){return(0,t.Z)().wrap((function(n){for(;;)switch(n.prev=n.next){case 0:console.debug("setup bridge"),document.addEventListener("init",e.handleInit),document.addEventListener("show",e.handleShow),document.addEventListener("close",e.handleClose),e.injectLog();case 5:case"end":return n.stop()}}),n)})))()},methods:{handleFocus:function(e){this._callNative("surveyProxy",JSON.stringify({type:"input-focus",question:e}))},handleBlur:function(e){console.debug("lost input focus"),this._callNative("surveyProxy",JSON.stringify({type:"input-blur",question:e}))},injectLog:function(){window.console.log=this.forwardLog,window.console.info=this.forwardLog,window.console.warn=this.forwardLog,window.console.debug=this.forwardLog,this._callNative("surveyProxy",JSON.stringify({type:"init",options:this.options}))},forwardLog:function(e){this._callNative("logger",e)},handleShow:function(e){console.debug("received show command"),this.show=!0},handleClose:function(e){console.debug("received close command")},handleInit:function(e){var n=e.detail||{},s=n.surveyId,t=n.channelId,i=n.clientId,l=n.sendId,a=n.delay,d=n.parameters,r=n.server;n=n.borderRadiusMode;this.borderRadiusMode=n,r&&(0,o.z)(null,{collectorMethod:"APP"},r),e.detail.survey&&(this.surveyJson=e.detail.survey),e.detail.halfscreen&&(this.options.halfscreen=!0),this.clientId=i,e.detail.language&&(this.options.language=e.detail.language),this.options.showType=e.detail.showType,this.options.project=e.detail.project,l||s&&t?(this.delay=a||1e3,this.surveyId=s||null,this.channelId=t||null,this.sendId=l||null,this.isShowSurvey=!0,this.parameters=d||{},this.closed=!1,console.debug("init survey surveyId:".concat(s,";  channelId:").concat(t,"; clientId:").concat(i))):console.warn("invalid command ".concat(JSON.stringify(e.detail||{})))},handleHide:function(e){this.show=!1,this.closed=!0,this.isShowSurvey=!1,this._callNative("surveyProxy",JSON.stringify({type:"close",reason:e}))},handleCancel:function(){console.debug("cancel"),this.show=!1,this.isShowSurvey=!1,this._callNative("surveyProxy",JSON.stringify({type:"cancel"}))},handleSubmit:function(){this._callNative("surveyProxy",JSON.stringify({type:"submit"}))},handleLoad:function(e){this._callNative("logger",JSON.stringify({type:"logger"})),this.survey=e,this._callNative("surveyProxy",JSON.stringify({type:"load",configure:e&&e.channel&&e.channel.configure,options:this.options}))},handleSize:function(e){this.closed||this._callNative("surveyProxy",JSON.stringify({type:"size",value:e}))},_callNative:function(e,n){window.webkit&&window.webkit.messageHandlers&&window.webkit.messageHandlers[e]?window.webkit.messageHandlers[e].postMessage(n):window[e]?window[e].postMessage(n):this.logger("".concat(e,": "),n)}}},(0,s(9453).Z)(n,(function(){var e=this,n=e.$createElement;n=e._self._c||n;return e.sendId||e.surveyId&&e.channelId?n("v-uni-view",{staticClass:"bridge"},[e.isShowSurvey?n("hy-survey",{ref:"survey",attrs:{borderRadiusMode:e.borderRadiusMode,delay:e.delay,resize:!0,show:e.show,surveyId:e.surveyId,channelId:e.channelId,sendId:e.sendId,clientId:e.clientId,options:e.options,parameters:e.parameters,surveyJson:e.surveyJson},on:{focus:function(n){arguments[0]=n=e.$handleEvent(n),e.handleFocus.apply(void 0,arguments)},blur:function(n){arguments[0]=n=e.$handleEvent(n),e.handleBlur.apply(void 0,arguments)},cancel:function(n){arguments[0]=n=e.$handleEvent(n),e.handleCancel.apply(void 0,arguments)},hide:function(n){arguments[0]=n=e.$handleEvent(n),e.handleHide.apply(void 0,arguments)},size:function(n){arguments[0]=n=e.$handleEvent(n),e.handleSize.apply(void 0,arguments)},submit:function(n){arguments[0]=n=e.$handleEvent(n),e.handleSubmit.apply(void 0,arguments)},load:function(n){arguments[0]=n=e.$handleEvent(n),e.handleLoad.apply(void 0,arguments)}}}):e._e()],1):e._e()}),[],!1,null,null,null,!1,void 0,void 0).exports)}}]);