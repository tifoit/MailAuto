package com.stlz.util.config;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtil {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	//��ȡ����ʱ��
	public static String yesteday() {
		Calendar calendar = Calendar.getInstance();//��ʱ��ӡ����ȡ����ϵͳ��ǰʱ��
        calendar.add(Calendar.DATE, -1);    //�õ�ǰһ��
        String  yesteday = new SimpleDateFormat("yyyyMMdd").format(calendar.getTime());
        System.out.println("yesteday="+yesteday);
        return yesteday;

	}
}
