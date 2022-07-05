package com.example.lit.domain.vo;

import javax.servlet.http.HttpSession;

public class AdminSession {
    private static final String SESSION_ID = "admin1356";

    public static void setSession(HttpSession session){
        session.setAttribute(SESSION_ID, "info");
        session.setAttribute("userNumber", 0L);
    }


    public static String checkSession(HttpSession session, String url){
        if(session.getAttribute(SESSION_ID) == null){
            url = "/admin/login";
        }
        return url;
    }
}
