package osteam;

import java.io.*;
import java.util.*;

class HRRN_Scheduler implements Scheduler{
    Map<Integer, Integer[]> input;

    public HRRN_Scheduler(Map<Integer, Integer[]> input){
        this.input = input;
    }

    public Map<Integer, Integer[]> getResult(){
        // HRRN 알고리즘의 로직은 이 메서드에서 구현하세요
        // 하나의 프로세스가 종료될 때마다 새로운 줄에 다음 6가지 내용을 한줄에 표시
        // (1) 프로세스 ID, (2) 도착시간, (3) 서비스시간, (4) 종료시간, (5) 반환시간, (6) 정규화된 반환시간
        // <프로세스 ID, {도착시간, 서비스시간, 종료시간, 반환시간, 정규화된 반환시간}>의 구조입니다.
        // LinkedHashMap은 입력 순서대로 출력할 수 있는 맵
        Map<Integer, Integer[]> result = new LinkedHashMap<Integer, Integer[]>();

        /* 종료되는 프로세스 순서대로 result에 입력하면 됩니다(아래는 샘플임) */
        Integer[] val1 = {3, 5, 6, 7, 8, 9};
        Integer[] val2 = {4, 5, 6, 7, 8, 9};
        Integer[] val3 = {5, 5, 6, 7, 8, 9};

        result.put(3, val1);
        result.put(4, val2);
        result.put(5, val3);

        return result;
    }
}
