package com.raiinmaker.tiktok_login_flutter.tiktokapi
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.bytedance.sdk.open.tiktok.TikTokOpenApiFactory
import com.bytedance.sdk.open.tiktok.api.TikTokOpenApi
import com.bytedance.sdk.open.tiktok.authorize.model.Authorization
import com.bytedance.sdk.open.tiktok.common.handler.IApiEventHandler
import com.bytedance.sdk.open.tiktok.common.model.BaseReq
import com.bytedance.sdk.open.tiktok.common.model.BaseResp
import io.flutter.plugin.common.EventChannel
import android.util.Log
import io.flutter.plugin.common.MethodChannel




 class TikTokEntryActivity : Activity(), IApiEventHandler {

//    val messageStreamHandler = MessageStreamHandler()


    companion object {
        var result: MethodChannel.Result? = null;
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val ttOpenApi: TikTokOpenApi = TikTokOpenApiFactory.create(this)
        ttOpenApi.handleIntent(intent, this) // receive and parse callback
//        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
//        getActionBar()?.hide();
    }

    override fun onReq(req: BaseReq) {
        Log.i("INFO", "onReq called")
    }
    override fun onResp(resp: BaseResp) {
        Log.i("INFO", "Response recieved")
        if (resp is Authorization.Response) {

            result?.success(resp.authCode)
        }
        this.finish()
    }

    override fun onErrorIntent(intent: Intent?) {
        Log.i("INFO", "onError called")
        result?.error("AUTHORIZATION_ERROR", "Failed to get auth code", "");
        this.finish()
    }


}
//
//class MessageStreamHandler : EventChannel.StreamHandler {
//    private var eventSink: EventChannel.EventSink? = null
//    override fun onListen(arguments: Any?, sink: EventChannel.EventSink) {
//        eventSink = sink
//    }
//
//    fun send(code: String) {
//        eventSink?.success(code)
//    }
//
//    override fun onCancel(p0: Any?) {
//        eventSink = null
//    }
//}