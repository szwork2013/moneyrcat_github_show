package com.emperises.monercat.game;

import java.util.Random;

public class RandomNum {
	 //probability �� arr һһ��Ӧ�ı�ʾ arr �и�����ĸ��ʣ������� probability ��Ԫ�غͲ��ܳ��� 100��
    public static int getRandomNum(int[] arr, int[] probability){
        if(arr.length != probability.length) return Integer.MIN_VALUE;
        Random ran = new Random();
        int ran_num = ran.nextInt(100);
        int temp = 0;
        for (int i = 0; i < arr.length; i++) {
            temp += probability[i];
            if(ran_num < temp)
                return arr[i];
        }
        return Integer.MIN_VALUE;
    }
    public static int getRandomNum(){
    	return RandomNum.getRandomNum(new int[]{0,2,3,5,10,20}, new int[] {50,30,10,5,3,2});//ģ���н�����
    }
}
