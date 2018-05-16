package osteam;

import java.io.*;
import java.util.*;

class RR_Scheduler implements Scheduler{
    Map<Integer, Integer[]> input;

    public RR_Scheduler(Map<Integer, Integer[]> input){
        this.input = input;
    }

	public Map<Integer, Integer[]> getResult(){
		Map<Integer, Integer[]> result = new LinkedHashMap<Integer, Integer[]>(); // 결과로 출력할 해시맵
		int quantum = 4;
		int totalCurrentTime = 0;
		Map<Integer, Integer> svcTime = new HashMap<Integer, Integer>(); // 프로세스의 누적 서비스시간을 관리하기 위한 해시맵
		for(int key : input.keySet()) svcTime.put(key, 0); // 서비스시간 관리맵 초기화
		Queue<Integer> queue = new LinkedList<Integer>(); // 스케줄링 순서를 관리하기 위한 큐
		queue.offer(1); // process1 부터 시작

		while(true){
			int key = queue.remove(); // 어떤 프로세스가 CPU를 선점할 것인지

			for(int q = 0 ; q < quantum ; q++){
				svcTime.put(key, svcTime.get(key) + 1); // 선점된 프로세스의 누적 서비스시간 증가

				for(int inputKey : input.keySet()) // 중간에 프로세스가 큐에 진입한 경우 탐색
					if((input.get(inputKey)[0] == totalCurrentTime) && (totalCurrentTime != 0)){
						queue.offer(inputKey);
						//System.out.println("process" + inputKey + " 진입");
					}

				totalCurrentTime++;

				if(svcTime.get(key) == input.get(key)[1]) // 필요 서비스 시간을 만족시킨 경우 -> 반복문 종료
					break;
			}

			if(svcTime.get(key) == input.get(key)[1]){ // 필요 서비스 시간을 만족하지 못했는지의 여부검사 -> 만족할 경우 result에 등록
				int prc_arrTime = input.get(key)[0]; // 도착시간
				int prc_svcTime = input.get(key)[1]; // 서비스시간
				int prc_endTime = totalCurrentTime; // 종료시간
				int prc_returnTime = prc_endTime - prc_arrTime; // 반환시간
				int prc_normalReturnTime = prc_returnTime / prc_svcTime; // 정규화된 반환시간
				Integer[] values = {prc_arrTime, prc_svcTime, prc_endTime, prc_returnTime, prc_normalReturnTime};
				result.put(key, values);
			}else{ // 필요 서비스 시간을 만족하지 못하였을 경우 다시 queue에 저장
				queue.offer(key);
			}

			if(queue.isEmpty()) // 남은 프로세스가 있는가? -> 없으면 반복문 종료
				break;
		}
		return result;
	}
}
