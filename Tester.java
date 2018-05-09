package osteam;

import java.io.*;
import java.util.*;

public class Tester{

    public static void main(String[] args) throws IOException{
        Map<Integer, Integer[]> input = readInputData();
        Scheduler scheduler; // Scheduler 인터페이스 객체

        System.out.println("1. RR(q = 4)");
        System.out.println("2. HRRN");
        System.out.println("3. Feedback(q = 2^i)");
        System.out.print("테스트할 알고리즘을 선택해주세요 > ");
        Scanner sc = new Scanner(System.in);
        switch(sc.next()){
            case "1": // RR
                scheduler = new RR_Scheduler(input);
                break;
            case "2": // HRRN
                scheduler = new HRRN_Scheduler(input);
                break;
            case "3": // Feedback
                scheduler = new Feedback_Scheduler(input);
                break;
            default:
                System.out.println("잘못된 입력입니다");
                return;
        }

        Map<Integer, Integer[]> result = scheduler.getResult();
        
        System.out.println("프로세스ID\t도착시간\t서비스시간\t종료시간\t반환시간\t정규화된반환시간");
        for(int i : result.keySet()){
            for(int j = 0 ; j < 6 ; j++)
                System.out.print(result.get(i)[j] + "\t\t");
            System.out.println("\n");
        }
    }

    // input 데이터를 TreeMap 객체(Key를 기준으로 자동정렬)로 반환하는 메소드
    // <프로세스ID, {도착시간, 서비스시간}> 구조
    static Map<Integer, Integer[]> readInputData() throws IOException{
 	BufferedReader br = new BufferedReader(new FileReader("./input.txt"));
        Map<Integer, Integer[]> input = new TreeMap<Integer, Integer[]>();

        while(true){
            String line = br.readLine();
            if(line.equals("0")) break;
            String lineSplit[] = line.split(",");
            Integer[] values = {Integer.parseInt(lineSplit[1]), Integer.parseInt(lineSplit[2])};
            input.put(Integer.parseInt(lineSplit[0]), values);
        }
        br.close();
        return input;
    }
}


interface Scheduler{
    //public Scheduler(Map<Integer, Integer[]>);
    //public void setInputData(Map<Integer, Integer[]>);
    public Map<Integer, Integer[]> getResult();
}


class RR_Scheduler implements Scheduler{
    Map<Integer, Integer[]> input;

    public RR_Scheduler(Map<Integer, Integer[]> input){
        this.input = input;
    }

    public Map<Integer, Integer[]> getResult(){
        // RR 알고리즘의 로직은 이 메서드에서 구현하세요
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


class Feedback_Scheduler implements Scheduler{
    Map<Integer, Integer[]> input;

    public Feedback_Scheduler(Map<Integer, Integer[]> input){
        this.input = input;
    }

    public Map<Integer, Integer[]> getResult(){
        // Feedback 알고리즘의 로직은 이 메서드에서 구현하세요
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









