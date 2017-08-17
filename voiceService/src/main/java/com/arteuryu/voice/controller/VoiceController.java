package com.arteuryu.voice.controller;
import com.arteuryu.voice.xfvoice.XfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by yuerte969 on 28/7/2017.
 */
@RestController
public class VoiceController extends AbstractController {
    private XfService xfService = new XfService();
    private static final Logger log = Logger.getLogger(VoiceController.class.getName());

    @GetMapping("/testVoice/{text}")
    public String testVoice(@PathVariable String text){
        try{
            log.log(Level.INFO,"text is "+text);
            xfService.combileVoice(text);
        }catch (Exception e){
            e.printStackTrace();
            log.log(Level.INFO,e.getMessage());
            return "fail";
        }
        return "success";
    }

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        return null;
    }
}
