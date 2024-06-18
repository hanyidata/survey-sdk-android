"use strict";(self.webpackChunksurvey=self.webpackChunksurvey||[]).push([[468],{7694:function(e,n,i){i.r(n),i.d(n,{default:function(){return s}}),i(9653);n={components:{"hy-survey":i(6364).Z},data:function(){return{surveyId:null,channelId:null,token:null,parameters:{},options:{},isLoadSurvey:!1,show:!1,dMarginTop:"0px",survey:{},ready:!1}},computed:{halfscreen:function(){return null!==this.options&&void 0!==this.options&&this.options.halfscreen||!1},style:function(){return this.survey.style},configure:function(){return this.survey&&this.survey.channel&&this.survey.channel.configure||{}},channelType:function(){return this.survey&&this.survey.channel&&this.survey.channel.type||"APP"},injectType:function(){return Number(this.configure&&this.configure.injectType)||{}},embed:function(){return this.survey&&this.survey.embed||{}},embedHeightMode:function(){return this.configure&&this.configure.embedHeightMode||this.embed.embedHeightMode||"AUTO"},embedHeight:function(){return this.configure&&this.configure.embedHeight||this.embed.embedHeight||"50%"},embedPaddingWidth:function(){return this.configure&&this.configure.appPaddingWidth||this.embed.appPaddingWidth||0},embedVerticalAlign:function(){return this.configure&&this.configure.embedVerticalAlign||this.embed.embedVerticalAlign||"BOTTOM"},embedBackGround:function(){return this.survey?this.configure&&this.configure.embedBackGround&&this.isLoadSurvey||"":0},heightValue:function(){return this.isLoadSurvey?"AUTO"===this.embedHeightMode?"auto":"100%"===this.embedHeight?"calc(100% - "+this.dMarginTop+")":this.embedHeight:"0px"},maxHeightValue:function(){return"AUTO"===this.embedHeightMode?this.embedHeight:""},appBorderRadius:function(){return this.configure&&this.configure.appBorderRadius||this.embed.appBorderRadius||"10px"}},created:function(){var e,n;document||(e=(n=uni.getMenuButtonBoundingClientRect()).top,n=n.height,this.dMarginTop=e+n+"px"),getApp().globalData._hy_subscribe(this.handleEvent)},methods:{hideSurvey:function(){this.isLoadSurvey=!1,this.show=!1,this.survey={}},handleEvent:function(e,n){"show"===e&&(this.surveyId=n.surveyId,this.channelId=n.channelId,this.token=n.token,this.options=n.options,this.parameters=n.params,this.show=!0,this.ready=!0)},handleLoad:function(e){console.debug("load survey",e),this.survey=e,this.isLoadSurvey=!0,this.options.onLoad&&this.options.onLoad()},handleFailed:function(e){this.options.onFailed&&this.options.onFailed(e)},handleCancel:function(){this.isLoadSurvey=!1,this.show=!1},handleSubmit:function(){this.options.onSubmit&&this.options.onSubmit()},submitFailed:function(e){this.options.onSubmitFailed&&this.options.onSubmitFailed(e)},handleSize:function(e){console.debug("size",e)}}};var t=i(9453),o=(n=(0,t.Z)(n,(function(){var e=this,n=e.$createElement;n=e._self._c||n;return e.show?n("v-uni-view",{staticClass:"hy-wx-dialog_c",class:{"half-screen-monitor":e.halfscreen}},[e.embedBackGround?n("v-uni-view",{staticClass:"hy-wx-mask",attrs:{catchtouchmove:"true"}}):e._e(),n("v-uni-view",{staticClass:"hy-wx-dialog",class:{"show-border":!e.embedBackGround,dialog_top:"1"==e.injectType&&"TOP"===e.embedVerticalAlign,dialog_center:"1"==e.injectType&&"CENTER"===e.embedVerticalAlign,dialog_bottom:"1"==e.injectType&&"BOTTOM"===e.embedVerticalAlign},style:{width:"calc(100% - "+e.embedPaddingWidth+" - "+e.embedPaddingWidth+")",height:e.heightValue,maxHeight:e.maxHeightValue,marginTop:"AUTO"!=e.embedHeightMode&&"100%"==e.embedHeight||"TOP"===e.embedVerticalAlign?e.dMarginTop:"0px",borderRadius:""+e.appBorderRadius}},[e.show&&(e.token||e.surveyId&&e.channelId)?n("hy-survey",{ref:"hySurvey",staticClass:"hy-wx-dialog-ct",attrs:{show:e.ready,surveyId:e.surveyId,delay:3e3,channelId:e.channelId,token:e.token,options:e.options,heightValue:e.heightValue,maxHeightValue:e.maxHeightValue,resize:!0,parameters:e.parameters},on:{load:function(n){arguments[0]=n=e.$handleEvent(n),e.handleLoad.apply(void 0,arguments)},loadfailed:function(n){arguments[0]=n=e.$handleEvent(n),e.handleFailed.apply(void 0,arguments)},hide:function(n){arguments[0]=n=e.$handleEvent(n),e.hideSurvey.apply(void 0,arguments)},cancel:function(n){arguments[0]=n=e.$handleEvent(n),e.handleCancel.apply(void 0,arguments)},submitfailed:function(n){arguments[0]=n=e.$handleEvent(n),e.submitFailed.apply(void 0,arguments)},size:function(n){arguments[0]=n=e.$handleEvent(n),e.handleSize.apply(void 0,arguments)},submit:function(n){arguments[0]=n=e.$handleEvent(n),e.handleSubmit.apply(void 0,arguments)}}}):e._e()],1)],1):e._e()}),[],!1,null,null,null,!1,void 0,void 0).exports,i(2502)),s=(0,t.Z)({components:{"hy-survey-monitor":n},data:function(){return{survey:null,surveyId:"6314304133598208",channelId:"6314307031796736",token:{NODE_ENV:"production",VUE_APP_DARK_MODE:"false",VUE_APP_NAME:"survey-ui-next-uni2",VUE_APP_PLATFORM:"h5",VUE_APP_INDEX_CSS_HASH:"63b34199",VUE_APP_INDEX_DARK_CSS_HASH:"e6047db7",VUE_APP_SURVEY_ENV:"https://www.xmplus.cn/api/survey",VUE_APP_SURVEY_ID:"6314304133598208",VUE_APP_CHANNEL_ID:"6314307031796736",VUE_APP_RESET:"false",BASE_URL:"./"}.VUE_APP_TOKEN,surveyParams:{}}},methods:{handleShow:function(){var e={onSubmit:this.handleSubmit,onSubmitFailed:this.submitFailed,onCancel:this.handleCancel,onLoad:this.handleLoad,onFailed:this.handleFailed};(0,o.u)(this.surveyId,this.channelId,e,this.surveyParams,this.token)},handleCancel:function(){console.debug("已关闭问卷")},handleLoad:function(){console.debug("问卷加载成功")},handleFailed:function(e){console.error("问卷打开失败: ",e)},submitFailed:function(e){console.error("问卷提交失败: ",e)},handleSubmit:function(){console.debug("问卷已提交")}}},(function(){var e=this,n=e.$createElement;n=e._self._c||n;return n("v-uni-view",{staticClass:"hy-survey-index popup"},[n("v-uni-view",{staticStyle:{"padding-top":"20px"}},[n("v-uni-button",{on:{click:function(n){arguments[0]=n=e.$handleEvent(n),e.handleShow.apply(void 0,arguments)}}},[e._v("弹出问卷")])],1),n("hy-survey-monitor")],1)}),[],!1,null,null,null,!1,void 0,void 0).exports}}]);