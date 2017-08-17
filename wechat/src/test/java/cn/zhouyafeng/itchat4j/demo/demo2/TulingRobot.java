package cn.zhouyafeng.itchat4j.demo.demo2;

import cn.zhouyafeng.itchat4j.Wechat;
import cn.zhouyafeng.itchat4j.core.Core;
import cn.zhouyafeng.itchat4j.face.IMsgHandlerFace;
import cn.zhouyafeng.itchat4j.utils.MyHttpClient;
import cn.zhouyafeng.itchat4j.utils.enums.MsgTypeEnum;
import cn.zhouyafeng.itchat4j.utils.tools.DownloadTools;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.*;
import java.util.logging.Logger;

/**
 * 图灵机器人示例
 * 
 *
 * @date 创建时间：2017年4月24日 上午12:13:26
 * @version 1.0
 *
 */
public class TulingRobot implements IMsgHandlerFace {
	MyHttpClient myHttpClient = Core.getInstance().getMyHttpClient();
	String bdApiKey = "CMBsrIRrpU8IN2HHE709SiN1";
	String bdSeKey = "f8gcbGc6lxUbf0AoGxmgYABxFuPeUqg4";
	String apiKey = "a7e95210cfe24325b94aa7851eb76d6d"; // 这里是我申请的图灵机器人API接口，每天只能5000次调用，建议自己去申请一个，免费的:)
	Logger logger = Logger.getLogger("TulingRobot");


	public String getBdAccessToken(){
		List<BasicNameValuePair> paramPair = new ArrayList<BasicNameValuePair>();
		paramPair.add(new BasicNameValuePair("grant_type","client_credentials"));
		paramPair.add(new BasicNameValuePair("client_id",bdApiKey));
		paramPair.add(new BasicNameValuePair("client_secret",bdSeKey));

		String result = "";
		try{
			HttpEntity entity = myHttpClient.doGet("https://openapi.baidu.com/oauth/2.0/token",paramPair,false,null);
			result = EntityUtils.toString(entity, "UTF-8");
			JSONObject obj = JSON.parseObject(result);
			System.out.print(result);
			return obj.getString("access_token");
		}catch (Exception e){
			System.out.print("get baidu access token error"+e.getMessage());
		}
 		return "";
	}

	String getVoiceFileUrl(String accessToken,String text){
		List<BasicNameValuePair> paramPair = new ArrayList<BasicNameValuePair>();
		paramPair.add(new BasicNameValuePair("tex",text));
		paramPair.add(new BasicNameValuePair("lan","zh"));
		paramPair.add(new BasicNameValuePair("tok",accessToken));
		paramPair.add(new BasicNameValuePair("ctp","1"));
		paramPair.add(new BasicNameValuePair("cuid","a0:99:9b:17:52:3b"));
		String uuid = UUID.randomUUID().toString();
		String result = "/tmp/"+uuid+".mp3";
		try{
			HttpEntity entity = myHttpClient.doGet("http://tsn.baidu.com/text2audio",paramPair,false,null);
			OutputStream out = new FileOutputStream(result);
			byte[] bytes = EntityUtils.toByteArray(entity);
			out.write(bytes);
			out.flush();
			out.close();
			return result;
		}catch (Exception e){
			System.out.print("get baidu mp3 error"+e.getMessage());
		}
		return "";
	}
	@Override
	public String textMsgHandle(JSONObject msg) {
		if (msg.getBoolean("groupMsg")) { // 群消息不处理
			return null;
		}
		String result = "";
		String text = msg.getString("Text");
		String url = "http://www.tuling123.com/openapi/api";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("key", apiKey);
		paramMap.put("info", text);
		paramMap.put("userid", "123456");
		String paramStr = JSON.toJSONString(paramMap);
		String token = getBdAccessToken();
		String path = "";
		try {
			HttpEntity entity = myHttpClient.doPost(url, paramStr);
			result = EntityUtils.toString(entity, "UTF-8");
			JSONObject obj = JSON.parseObject(result);
			System.out.println("turing result "+result);
			if (obj.getString("code").equals("100000")) {
				result = obj.getString("text");
				path = getVoiceFileUrl(token,result);
				System.out.println("path is "+path);
			} else {
				result = "处理有误";
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		return path;
	}

	@Override
	public String picMsgHandle(JSONObject msg) {
		if (msg.getBoolean("groupMsg")) { // 群消息不处理
			return null;
		}
		String accessToken = getBdAccessToken();
		return accessToken;
	}

	@Override
	public String voiceMsgHandle(JSONObject msg) {
		if (msg.getBoolean("groupMsg")) { // 群消息不处理
			return null;
		}
		String fileName = String.valueOf(new Date().getTime());
		String voicePath = "/tmp" + File.separator + fileName + ".mp3";
		DownloadTools.getDownloadFn(msg, MsgTypeEnum.VOICE.getType(), voicePath);
		return "收到语音";
	}

	@Override
	public String viedoMsgHandle(JSONObject msg) {
		if (msg.getBoolean("groupMsg")) { // 群消息不处理
			return null;
		}
		String fileName = String.valueOf(new Date().getTime());
		String viedoPath = "/tmp" + File.separator + fileName + ".mp4";
		DownloadTools.getDownloadFn(msg, MsgTypeEnum.VIEDO.getType(), viedoPath);
		return "收到视频";
	}

	public static void main(String[] args) {
		IMsgHandlerFace msgHandler = new TulingRobot();
		Wechat wechat = new Wechat(msgHandler, "/tmp");
		wechat.start();
	}

	@Override
	public String nameCardMsgHandle(JSONObject msg) {
		// TODO Auto-generated method stub
		return null;
	}

}
