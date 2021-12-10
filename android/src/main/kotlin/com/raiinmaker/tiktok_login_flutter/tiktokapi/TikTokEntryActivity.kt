package com.raiinmaker.tiktok_login_flutter.tiktokapi
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.bytedance.sdk.open.tiktok.TikTokOpenApiFactory
import com.bytedance.sdk.open.tiktok.api.TikTokOpenApi
import com.bytedance.sdk.open.tiktok.authorize.model.Authorization
import com.bytedance.sdk.open.tiktok.common.handler.IApiEventHandler
import com.bytedance.sdk.open.tiktok.common.model.BaseReq
import com.bytedance.sdk.open.tiktok.common.model.BaseResp


internal class TikTokEntryActivity : Activity(), IApiEventHandler {
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val ttOpenApi: TikTokOpenApi = TikTokOpenApiFactory.create(this)
        ttOpenApi.handleIntent(intent, this) // receive and parse callback
    }

    override fun onReq(req: BaseReq) {}
    override fun onResp(resp: BaseResp) {
        if (resp is Authorization.Response) {
            val response = resp
//            Toast.makeText(this, " code：" + response.errorCode + " errorMessage：" + response.errorMsg, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onErrorIntent(intent: Intent?) {
//        Toast.makeText(this, "Intent Error", Toast.LENGTH_LONG).show()
    }
}