package osteam;

import java.io.*;
import java.util.*;

public class Tester{

    public static void main(String[] args) throws IOException{
        Map<Integer, Integer[]> input = readInputData();
        Scheduler scheduler; // Scheduler 인터페이스 객체
		while(true){
			System.out.println("= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =");
			System.out.println("\n\t\t\t\t< M E N U >\n");
			System.out.println("\n\t\t\t1. RR(q = 4)");
			System.out.println("\n\t\t\t2. HRRN");
			System.out.println("\n\t\t\t3. Feedback(q = 2^i)");
			System.out.println("\n\t\t\t0. 종료\n");
			System.out.print("\t\t테스트할 알고리즘을 선택해주세요 > ");
			Scanner sc = new Scanner(System.in);
			switch(sc.next()){
				case "1": // RR
					scheduler = new RR_Scheduler(input);
					System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
					System.out.println("\t\t * * * RR 스케줄링 알고리즘 테스트 결과입니다 * * *");
					break;
				case "2": // HRRN
					scheduler = new HRRN_Scheduler(input);
					System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
					System.out.println("\t\t * * * HRRN 스케줄링 알고리즘 테스트 결과입니다 * * *");
					break;
				case "3": // Feedback
					scheduler = new Feedback_Scheduler(input);
					System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
					System.out.println("\t\t * * * Feedback 스케줄링 알고리즘 테스트 결과입니다 * * *");
					break;
				case "0":
					System.out.println("\n\t\t스케줄러 테스터를 종료합니다");
					return;
				default:
					System.out.println("\n\t\t잘못된 입력입니다");
					continue;
			}

			System.out.println("\n\n\t\t\t\t< input.txt >");
			System.out.println("\t\t\t프로세스ID\t도착시간\t서비스시간\t");
			for(int pid : input.keySet())
				System.out.print("\t\t\t" + pid + "\t\t" + input.get(pid)[0] + "\t\t" + input.get(pid)[1] + "\n");
			System.out.println("\n");

			Map<Integer, Integer[]> result = scheduler.getResult();
			
			Map<Integer, Integer> trace = scheduler.getProcessTrace();
			System.out.println("\t\t\t\t< 간트 차트 >");
			System.out.println("p  0  1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25");
			for(int pid : input.keySet()){
				System.out.print(pid);
				for(int time : trace.keySet()){
					if(trace.get(time) == pid)
						System.out.print("  ■");
					else
						System.out.print("   ");
				}
				System.out.println("");
			}

			System.out.println("\n\n-\t-\t-\t-\t< 분석 결과 >\t-\t-\t-\t-\t-\t-");
			System.out.println("프로세스ID\t도착시간\t서비스시간\t종료시간\t반환시간\t정규화된반환시간");
			for(int pid : result.keySet()){
				System.out.print(pid + "\t\t");
				for(int i = 0 ; i < 4 ; i++)
					System.out.print(result.get(pid)[i] + "\t\t");
				System.out.print((double)result.get(pid)[3] / (double)result.get(pid)[1]);
				System.out.println("\n");
			}
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
	public Map<Integer, Integer> getProcessTrace();
}
