package com.arteuryu.voice.xfvoice;
import com.iflytek.cloud.speech.*;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by yuerte969 on 27/7/2017.
 */
public class XfService {
    {
        SpeechUtility.createUtility( SpeechConstant.APPID +"=5976e925" + "," + "server_url = http://sdk.openspeech.cn/msp.do");
    }
    private static final Logger log = Logger.getLogger(XfService.class.getName());

    SynthesizeToUriListener synthesizeToUriListener = new SynthesizeToUriListener() {
        //progress为合成进度0~100
        public void onBufferProgress(int progress) {} //会话合成完成回调接口 //uri为合成保存地址，error为错误信息，为null时表示合成会话成功 public void onSynthesizeCompleted(String uri, SpeechError error) {}


        public void onSynthesizeCompleted(String s, SpeechError speechError) {
            if(null == speechError) {
                log.log(Level.INFO, "combile success ,url is " + s);
            }else{
                log.log(Level.INFO,"combile error:"+speechError.getErrorCode()+speechError.getErrorDesc());
            }
        }


        public void onEvent(int i, int i1, int i2, int i3, Object o, Object o1) {

        }
    };
    public void combileVoice(String text){
        log.log(Level.INFO,"combile text is "+text);
        try {
            SpeechSynthesizer mTts = SpeechSynthesizer.createSynthesizer();
            mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");//设置发音人
            mTts.setParameter(SpeechConstant.SPEED, "50");//设置语速，范围0~100
            mTts.setParameter(SpeechConstant.PITCH, "50");//设置语调，范围0~100
            mTts.setParameter(SpeechConstant.VOLUME, "50");//设置音量，范围0~100
//3.开始合成
//设置合成音频保存位置（可自定义保存位置），默认保存在“./tts_test.pcm”
            mTts.synthesizeToUri(text, "/tmp/test.mp3", synthesizeToUriListener);
        }catch (Exception e){
            log.log(Level.INFO,"error is "+e.getMessage());
        }
//合成监听器
    }
}
