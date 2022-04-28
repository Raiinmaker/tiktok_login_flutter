import Flutter
import UIKit
import TikTokOpenSDK

public class SwiftTiktokLoginFlutterPlugin: NSObject, FlutterPlugin {
    
    let viewController: UIViewController =
        (UIApplication.shared.delegate?.window??.rootViewController)!;
    
    public static func register(with registrar: FlutterPluginRegistrar) {
        
        
        
        let channel = FlutterMethodChannel(name: "tiktok_login_flutter", binaryMessenger: registrar.messenger())
        let instance = SwiftTiktokLoginFlutterPlugin()
        registrar.addMethodCallDelegate(instance, channel: channel)
        registrar.addApplicationDelegate(instance)
        
    }
    
    public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
        switch call.method {
        case "initializeTiktokLogin":
            self.initializeTiktokLogin(call: call, result: result)
        case "authorize":
            self.authorize(call: call, result: result)
        case "share":
            self.share(call: call, result: result)
        default:
            result(FlutterMethodNotImplemented)
        }
    }
    
    private func initializeTiktokLogin(call: FlutterMethodCall, result: @escaping FlutterResult) {
        return result(true)
    }
    
    private func authorize(call: FlutterMethodCall, result: @escaping FlutterResult) {
        
        
        
        
        let args = call.arguments as! [String: Any]
        let scope = args["scope"] as! String
        
        // split by comma into a list
        let scopeList = scope.components(separatedBy: ",")
        
        let scopesSet = NSOrderedSet(array:scopeList)
        let request = TikTokOpenSDKAuthRequest()
        request.permissions = scopesSet
        
        /* STEP 2 */
        request.send(viewController, completion: { resp -> Void in
            /* STEP 3 */
            if resp.errCode == TikTokOpenSDKErrorCode.success  {
                /* STEP 3.a */
                
                result(resp.code)
                
                
            } else {
                result(FlutterError(code: "AUTHORIZATION_REQUEST_FAILED", message: resp.errString, details: nil))
            }
        })
    }
    
    let imagePickerController = UIImagePickerController()
    var videoURL: NSURL?
    
    private func share(call: FlutterMethodCall, result: @escaping FlutterResult) {
        
        guard let arguments = call.arguments as? [String:String],
        let file:String = arguments["file"] else {
            result(FlutterError(code: "INVALID ARGUMENTS", message: "Please provide right argumnets", details: nil))
            return
        }
        
    
        let request = TikTokOpenSDKShareRequest()
        request.mediaType = TikTokOpenSDKShareMediaType.video;
        
        print(file)
        let mediaLocalIdentifiers: [String] = [file]
        request.localIdentifiers = mediaLocalIdentifiers
        
        request.send(completionBlock: {resp -> Void in
            print("recieved")
            print(resp.isSucceed)
            print(resp.description)
            print(resp.errCode)
            result(resp.isSucceed)
        })
        
        
       
    }
    // app delegate functions
    //
    //
    
    public func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [AnyHashable : Any] = [:]) -> Bool {
        
        TikTokOpenSDKApplicationDelegate.sharedInstance().application(application, didFinishLaunchingWithOptions: launchOptions)
        
        return true
    }
    
    public func application(_ app: UIApplication, open url: URL, options: [UIApplication.OpenURLOptionsKey : Any] ) -> Bool {
        
        guard let sourceApplication = options[UIApplication.OpenURLOptionsKey.sourceApplication] as? String,
              let annotation = options[UIApplication.OpenURLOptionsKey.annotation] else {
            return false
        }
        
        if TikTokOpenSDKApplicationDelegate.sharedInstance().application(app, open: url, sourceApplication: sourceApplication, annotation: annotation) {
            return true
        }
        return false
    }
    
    public func application(_ application: UIApplication, open url: URL, sourceApplication: String, annotation: Any) -> Bool {
        if TikTokOpenSDKApplicationDelegate.sharedInstance().application(application, open: url, sourceApplication: sourceApplication, annotation: annotation) {
            return true
        }
        return false
    }
    
    public  func application(_ application: UIApplication, handleOpen url: URL) -> Bool {
        if TikTokOpenSDKApplicationDelegate.sharedInstance().application(application, open: url, sourceApplication: nil, annotation: "") {
            return true
        }
        return false
    }
}
