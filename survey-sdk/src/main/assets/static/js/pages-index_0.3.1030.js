"use strict";(self.webpackChunksurvey=self.webpackChunksurvey||[]).push([[769],{7672:function(e,n,t){t.r(n),t.d(n,{default:function(){return i}});var s=t(6407),a=t(4587),i=(n=(t(2222),{components:{"hy-survey":t(884).Z},data:function(){return{show:!0,surveyId:"6625464840488960",server:"https://test.xmplus.cn/api/survey",channelId:"6625466922633216",sendId:{NODE_ENV:"production",VUE_APP_DARK_MODE:"false",VUE_APP_NAME:"survey-ui-next-uni2",VUE_APP_PLATFORM:"h5",VUE_APP_INDEX_CSS_HASH:"63b34199",VUE_APP_INDEX_DARK_CSS_HASH:"e6047db7",VUE_APP_SURVEY_ENV:"https://test.xmplus.cn/api/survey",VUE_APP_SURVEY_ID:"6625464840488960",VUE_APP_CHANNEL_ID:"6625466922633216",VUE_APP_RESET:"false",BASE_URL:"./"}.VUE_APP_TOKEN,surveyError:"",survey:{},clientId:null,surveyJson:null,parameters:{},options:{reset:"false",language:{NODE_ENV:"production",VUE_APP_DARK_MODE:"false",VUE_APP_NAME:"survey-ui-next-uni2",VUE_APP_PLATFORM:"h5",VUE_APP_INDEX_CSS_HASH:"63b34199",VUE_APP_INDEX_DARK_CSS_HASH:"e6047db7",VUE_APP_SURVEY_ENV:"https://test.xmplus.cn/api/survey",VUE_APP_SURVEY_ID:"6625464840488960",VUE_APP_CHANNEL_ID:"6625466922633216",VUE_APP_RESET:"false",BASE_URL:"./"}.VUE_APP_DEFAULT_LANGUAGE,halfscreen:!1,showType:"embedded"}}},created:function(){var e=this;return(0,a.Z)((0,s.Z)().mark((function n(){var t;return(0,s.Z)().wrap((function(n){for(;;)switch(n.prev=n.next){case 0:return e.clientId="".concat(e.surveyId,"-").concat((new Date).getTime()),n.next=3,uni.request({url:"".concat(e.server,"/surveys/union-start"),method:"POST",data:{surveyId:e.surveyId,channelId:e.channelId,clientId:e.clientId}});case 3:t=n.sent,e.surveyJson=t.data.data,t=t.data.data.responseId,"union start with sid ".concat(e.surveyId," cid ").concat(e.channelId," clientId ").concat(e.clientId," rid: ").concat(t);case 7:case"end":return n.stop()}}),n)})))()},methods:{hideMonitor:function(){this.show=!1},hideSurvey:function(){this.show=!1},handleLoad:function(e){console.debug("问卷加载成功"),this.survey=e},handleFailed:function(e){console.error("问卷打开失败: ",e)},submitFailed:function(e){console.error("问卷提交失败: ",e)},handleSubmit:function(){console.debug("问卷已提交")},handleCancel:function(){console.debug("问卷已关闭"),this.isShowSurvey=!1,this.show=!1},handleSize:function(e){console.debug("size",e)}}}),(0,t(9453).Z)(n,(function(){var e=this,n=e.$createElement;n=e._self._c||n;return e.show?n("v-uni-view",{staticClass:"hy-app"},[e.surveyJson?n("hy-survey",{attrs:{delay:3e3,resize:!0,show:!0,surveyJson:e.surveyJson,surveyId:e.surveyId,channelId:e.channelId,clientId:e.clientId,sendId:e.sendId,options:e.options,parameters:e.parameters},on:{hide:function(n){arguments[0]=n=e.$handleEvent(n),e.hideMonitor.apply(void 0,arguments)},load:function(n){arguments[0]=n=e.$handleEvent(n),e.handleLoad.apply(void 0,arguments)},loadfailed:function(n){arguments[0]=n=e.$handleEvent(n),e.handleFailed.apply(void 0,arguments)},submitfailed:function(n){arguments[0]=n=e.$handleEvent(n),e.submitFailed.apply(void 0,arguments)},submit:function(n){arguments[0]=n=e.$handleEvent(n),e.handleSubmit.apply(void 0,arguments)},size:function(n){arguments[0]=n=e.$handleEvent(n),e.handleSize.apply(void 0,arguments)}}}):e._e()],1):e._e()}),[],!1,null,null,null,!1,void 0,void 0).exports)}}]);