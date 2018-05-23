package osteam;

import java.io.*;
import java.util.*;

class HRRN_Scheduler implements Scheduler{
    Map<Integer, Integer[]> input;
    Map<Integer, Integer> trace; // 프로세스 수행 궤적 저장용

    public HRRN_Scheduler(Map<Integer, Integer[]> input){
        this.input = input;
    }

    public Map<Integer, Integer[]> getResult(){
        // HRRN 알고리즘의 로직은 이 메서드에서 구현하세요
        // 하나의 프로세스가 종료될 때마다 새로운 줄에 다음 6가지 내용을 한줄에 표시
        // (1) 프로세스 ID, (2) 도착시간, (3) 서비스시간, (4) 종료시간, (5) 반환시간, (6) 정규화된 반환시간
        // <프로세스 ID, {도착시간, 서비스시간, 종료시간, 반환시간, 정규화된 반환시간}>의 구조입니다.
        // LinkedHashMap은 입력 순서대로 출력할 수 있는 맵
		trace = new LinkedHashMap<Integer, Integer>(); // 간트차트를 그리기 위해 프로세스 수행 궤적을 저장하는 해시맵
		Map<Integer, Integer[]> result = new LinkedHashMap<Integer, Integer[]>(); // 결과로 출력할 해시맵
		int totalCurrentTime = 0;
		Map<Integer, Integer> svcTime = new HashMap<Integer, Integer>(); // 프로세스의 누적 서비스시간을 관리하기 위한 해시맵
		ArrayList<Integer> queue = new ArrayList<Integer>(); // 진입된 프로세스들을 관리하기 위한 큐
		for(int processId : input.keySet()) svcTime.put(processId, 0); // 서비스시간 관리맵 초기화
		// 응답비율(R) = (w + s) / s	
		// w = 처리기를 기다리며 대기한 시간 =  totalCurrentTime - 도착시간
		// s = 예상 서비스시간 = 서비스시간
		
		int processId = 1; // process 1 부터 시작
		while(true){

			// 선택된 프로세스의 서비스시간만큼 루프
			do{
				svcTime.put(processId, svcTime.get(processId) + 1); // 선점된 프로세스의 누적 서비스시간 증가
				trace.put(totalCurrentTime, processId); // 프로세스 수행궤적 저장

				for(int inputKey : input.keySet()) // 중간에 프로세스가 큐에 진입한 경우 탐색
					//if((input.get(inputKey)[0] == totalCurrentTime) && (totalCurrentTime != 0)){ // Integer Caching에 의한 무한루프 버그를 일으킴
					if((input.get(inputKey)[0].equals(totalCurrentTime)) && (totalCurrentTime != 0)){
						queue.add(inputKey);
					}

				totalCurrentTime++;
			}while(!svcTime.get(processId).equals(input.get(processId)[1]));
			//}while(svcTime.get(processId) != input.get(processId)[1]); // Integer Caching에 의한 무한루프 버그를 일으킴

			// 종료된 프로세스 result에 반영 및 key 초기화
			int prc_arrTime = input.get(processId)[0]; // 도착시간
			int prc_svcTime = input.get(processId)[1]; // 서비스시간
			int prc_endTime = totalCurrentTime; // 종료시간
			int prc_returnTime = prc_endTime - prc_arrTime; // 반환시간
			int prc_normalReturnTime = prc_returnTime / prc_svcTime; // 정규화된 반환시간
			Integer[] values = {prc_arrTime, prc_svcTime, prc_endTime, prc_returnTime, prc_normalReturnTime};
			result.put(processId, values);
			queue.remove(new Integer(processId)); // ArrayList 타입은 삭제할때 Object 형태로 넘겨줘야함 -_-;;

			if(queue.isEmpty()) // 남은 프로세스가 있는지? -> 없으면 종료
				break;

			double maxResponseRate = 0;
			// 응답비율이 큰 순서대로 큐에 삽입하는 정렬 알고리즘?
			Iterator<Integer> iterableQueue = queue.iterator();
			while(iterableQueue.hasNext()){
				int k = iterableQueue.next();
				double w = totalCurrentTime - input.get(k)[0];
				double s = input.get(k)[1];
				double responseRate = (w + s) / s;
				if(responseRate > maxResponseRate){
					maxResponseRate = responseRate;
					processId = k;
				}
			}
		}


        return result;
    }
	public Map<Integer, Integer> getProcessTrace(){
		// 간트차트를 그리고 싶음 -> 프로세스의 수행 궤적을 저장해야 함
		// 수행 궤적 정보의 규격: totalCurrentTime, pid
		return trace;
	}
}
