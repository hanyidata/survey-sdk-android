"use strict";(self.webpackChunksurvey=self.webpackChunksurvey||[]).push([[468],{13141:function(e,n,i){i.r(n),i.d(n,{default:function(){return s}}),i(9653);n={components:{"hy-survey":i(44851).Z},data:function(){return{surveyId:null,channelId:null,parameters:{},options:{},isLoadSurvey:!1,show:!1,marginTop:"30px",survey:{},ready:!1}},computed:{style:function(){return this.survey.style},configure:function(){return this.survey&&this.survey.channel&&this.survey.channel.configure&&this.survey.channel.configure||{}},channelType:function(){return this.survey&&this.survey.channel&&this.survey.channel.type||"APP"},injectType:function(){return Number(this.configure&&this.configure.injectType)||{}},embed:function(){return this.survey&&this.survey.embed||{}},embedHeightMode:function(){return this.configure&&this.configure.embedHeightMode||this.embed.embedHeightMode||"AUTO"},embedHeight:function(){return this.configure&&this.configure.embedHeight||this.embed.embedHeight||"50%"},embedPaddingWidth:function(){return this.configure&&this.configure.appPaddingWidth||this.embed.appPaddingWidth||0},embedVerticalAlign:function(){return this.configure&&this.configure.embedVerticalAlign||this.embed.embedVerticalAlign||"BOTTOM"},embedBackGround:function(){return this.survey?this.configure&&this.configure.embedBackGround||"":0},appBorderRadius:function(){return this.configure&&this.configure.appBorderRadius||this.embed.appBorderRadius||"10px"}},created:function(){var e,n;document||(e=(n=uni.getMenuButtonBoundingClientRect()).top,n=n.height,this.marginTop=e+n+10+"px"),getApp().globalData._hy_subscribe(this.handleEvent)},methods:{hideSurvey:function(){this.isLoadSurvey=!1,this.show=!1,this.survey={}},handleEvent:function(e,n){"show"===e&&(this.surveyId=n.surveyId,this.channelId=n.channelId,this.options=n.options,this.parameters=n.params,this.show=!0,this.ready=!0)},handleLoad:function(e){console.debug("load survey",e),this.survey=e,this.isLoadSurvey=!0,this.options.onLoad&&this.options.onLoad()},handleFailed:function(e){this.options.onFailed&&this.options.onFailed(e)},handleCancel:function(){this.isLoadSurvey=!1,this.show=!1,this.options.onCancel&&this.options.onCancel()},handleSubmit:function(){this.options.onSubmit&&this.options.onSubmit()},submitFailed:function(e){this.options.onSubmitFailed&&this.options.onSubmitFailed(e)},handleSize:function(e){console.debug("size",e)}}};var t=i(69453),o=(n=(0,t.Z)(n,(function(){var e=this,n=e.$createElement;n=e._self._c||n;return e.show?n("v-uni-view",{staticClass:"hy-wx-dialog_c"},[n("v-uni-view",{staticClass:"hy-wx-mask",class:{"show-back":e.embedBackGround},attrs:{catchtouchmove:"true"}}),n("v-uni-view",{staticClass:"hy-wx-dialog",class:{dialog_top:"1"==e.injectType&&"TOP"===e.embedVerticalAlign,dialog_center:"1"==e.injectType&&"CENTER"===e.embedVerticalAlign,dialog_bottom:"1"==e.injectType&&"BOTTOM"===e.embedVerticalAlign},style:{width:"calc(100% - "+e.embedPaddingWidth+" - "+e.embedPaddingWidth+")",height:e.isLoadSurvey?"AUTO"===e.embedHeightMode?"auto":"100%"===e.embedHeight?"calc(100% - "+e.marginTop+")":e.embedHeight:"0px",maxHeight:"AUTO"===e.embedHeightMode?e.embedHeight:"",borderWidth:e.survey.showBackground?"0px":"1px",marginTop:"AUTO"!=e.embedHeightMode&&"100%"==e.embedHeight?e.marginTop:"0px",borderRadius:""+e.appBorderRadius}},[n("v-uni-view",{staticClass:"hy-wx-close",on:{click:function(n){arguments[0]=n=e.$handleEvent(n),e.handleCancel.apply(void 0,arguments)}}}),e.show&&e.surveyId&&e.channelId?n("hy-survey",{staticClass:"hy-wx-dialog-ct",attrs:{show:e.ready,surveyId:e.surveyId,channelId:e.channelId,options:e.options,resize:!0,parameters:e.parameters},on:{load:function(n){arguments[0]=n=e.$handleEvent(n),e.handleLoad.apply(void 0,arguments)},loadfailed:function(n){arguments[0]=n=e.$handleEvent(n),e.handleFailed.apply(void 0,arguments)},hide:function(n){arguments[0]=n=e.$handleEvent(n),e.hideSurvey.apply(void 0,arguments)},submitfailed:function(n){arguments[0]=n=e.$handleEvent(n),e.submitFailed.apply(void 0,arguments)},size:function(n){arguments[0]=n=e.$handleEvent(n),e.handleSize.apply(void 0,arguments)},submit:function(n){arguments[0]=n=e.$handleEvent(n),e.handleSubmit.apply(void 0,arguments)}}}):e._e()],1)],1):e._e()}),[],!1,null,null,null,!1,void 0,void 0).exports,i(42502)),s=(0,t.Z)({components:{"hy-survey-monitor":n},data:function(){return{survey:null,surveyId:"4445329530320896",channelId:"4446931357162496",surveyParams:{externalUserId:"001"}}},methods:{handleShow:function(){var e={onSubmit:this.handleSubmit,onSubmitFailed:this.submitFailed,onCancel:this.handleCancel,onLoad:this.handleLoad,onFailed:this.handleFailed};(0,o.u)(this.surveyId,this.channelId,e,this.surveyParams)},handleCancel:function(){console.debug("已关闭问卷")},handleLoad:function(){console.debug("问卷加载成功")},handleFailed:function(e){console.error("问卷打开失败: ",e)},submitFailed:function(e){console.error("问卷提交失败: ",e)},handleSubmit:function(){console.debug("问卷已提交")}}},(function(){var e=this,n=e.$createElement;n=e._self._c||n;return n("v-uni-view",{staticClass:"hy-survey-index"},[n("v-uni-view",{staticStyle:{"padding-top":"20px"}},[n("v-uni-button",{on:{click:function(n){arguments[0]=n=e.$handleEvent(n),e.handleShow.apply(void 0,arguments)}}},[e._v("弹出问卷")])],1),n("hy-survey-monitor")],1)}),[],!1,null,null,null,!1,void 0,void 0).exports}}]);