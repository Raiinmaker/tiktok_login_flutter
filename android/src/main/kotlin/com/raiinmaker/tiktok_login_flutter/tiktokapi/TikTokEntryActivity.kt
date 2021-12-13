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


internal class TikTokEntryActivity : Activity(), IApiEventHandler {

    val messageStreamHandler = MessageStreamHandler()

    companion object {
        val instance = TikTokEntryActivity()
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val ttOpenApi: TikTokOpenApi = TikTokOpenApiFactory.create(this)
        ttOpenApi.handleIntent(intent, this) // receive and parse callback
    }

    override fun onReq(req: BaseReq) {
        Log.i("INFO", "onReq called")
    }
    override fun onResp(resp: BaseResp) {
        Log.i("INFO", "Response recieved")
        if (resp is Authorization.Response) {
            val response = resp
            messageStreamHandler.send(resp.authCode)
        }
    }

    override fun onErrorIntent(intent: Intent?) {
        Log.i("INFO", "onError called")
    }
}

class MessageStreamHandler : EventChannel.StreamHandler {
    private var eventSink: EventChannel.EventSink? = null
    override fun onListen(arguments: Any?, sink: EventChannel.EventSink) {
        eventSink = sink
    }

    fun send(code: String) {
        eventSink?.success(code)
    }

    override fun onCancel(p0: Any?) {
        eventSink = null
    }
}