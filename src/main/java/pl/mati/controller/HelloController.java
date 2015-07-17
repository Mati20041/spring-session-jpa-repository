package pl.mati.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class HelloController {

    @RequestMapping("/")
    public String hello(HttpSession session) {
        Object atr = session.getAttribute("atr");
        if(atr != null) {
            return "Huray! " + atr.toString();
        } else {
            session.setAttribute("atr", "You made it!");
            return "Huray! Rerfresh page!";
        }
    }

}
