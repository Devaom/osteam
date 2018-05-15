package osteam;

import java.io.*;
import java.util.*;
import osteam.*;

public class Tester{

    public static void main(String[] args) throws IOException{
        Map<Integer, Integer[]> input = readInputData();

        System.out.println("\n프로세스ID\t도착시간\t서비스시간");
		for(int key : input.keySet())
			System.out.print(key + "\t\t" + input.get(key)[0] + "\t\t" + input.get(key)[1] + "\n");

        Scheduler scheduler; // Scheduler 인터페이스 객체

        System.out.println("\n1. RR(q = 4)");
        System.out.println("\n2. HRRN");
        System.out.println("\n3. Feedback(q = 2^i)\n");
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
        
        System.out.println("\n프로세스ID\t도착시간\t서비스시간\t종료시간\t반환시간\t정규화된반환시간");
        for(int i : result.keySet()){
			System.out.print(i + "\t\t");
            for(int j = 0 ; j < 4 ; j++)
                System.out.print(result.get(i)[j] + "\t\t");
			System.out.print((double)result.get(i)[3] / (double)result.get(i)[1]);
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

    public Map<Integer, Integer[]> getResult();

}
