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
import io.flutter.plugin.common.MethodChannel



 class TikTokEntryActivity : Activity(), IApiEventHandler {

    companion object {
        var result: MethodChannel.Result? = null;
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val ttOpenApi: TikTokOpenApi = TikTokOpenApiFactory.create(this)
        ttOpenApi.handleIntent(intent, this) // receive and parse callback
    }

    override fun onReq(req: BaseReq) {

    }
    override fun onResp(resp: BaseResp) {

        if (resp is Authorization.Response) {

            result?.success(resp.authCode)
        }
        this.finish()
    }

    override fun onErrorIntent(intent: Intent?) {
        result?.error("AUTHORIZATION_ERROR", "Failed to get auth code", null);
        this.finish()
    }


}
