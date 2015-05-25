package com.stlz.util.config;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtil {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	//获取昨天时间
	public static String yesteday() {
		Calendar calendar = Calendar.getInstance();//此时打印它获取的是系统当前时间
        calendar.add(Calendar.DATE, -1);    //得到前一天
        String  yesteday = new SimpleDateFormat("yyyyMMdd").format(calendar.getTime());
        System.out.println("yesteday="+yesteday);
        return yesteday;

	}
}
