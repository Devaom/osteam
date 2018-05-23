package osteam;

import java.io.*;
import java.util.*;
import java.util.stream.IntStream;

class Feedback_Scheduler implements Scheduler{
	Map<Integer, Integer[]> input;
    Map<Integer, Integer> trace; // 프로세스 수행 궤적 저장용

	public Feedback_Scheduler(Map<Integer, Integer[]> input){
		this.input = input;
	}

	public Map<Integer, Integer[]> getResult(){
		trace = new LinkedHashMap<Integer, Integer>(); // 간트차트를 그리기 위해 프로세스 수행 궤적을 저장하는 해시맵
		Map<Integer, Integer[]> result = new LinkedHashMap<>();
		int qSize = 4; // 준비 큐의 개수
		int quantum = 0; // 시간 할당량
		int totalCurrentTime = 0; // 현재 시간
      	boolean isReadyQueueEmpty = false; // 준비큐가 모두 비어있는지 확인
        int curReadyQueue = 0; // 현재큐가 몇번째 준비큐인지 (2^i)

		Map<Integer, Integer> svcTime = new HashMap<>(); // 프로세스의 누적 서비스시간을 관리하기 위한 해시맵 <id, 시간>
		input.keySet().forEach(pid->svcTime.put(pid,0)); // Service Time init

		LinkedList<Queue<Integer>> readyQueue = new LinkedList<>(); // 여러개의 준비큐를 담을 컬랙션 -> 프로세스 ID값을 가지고 있다

		IntStream.range(0,qSize)
			.forEach(i -> readyQueue.add(new LinkedList<>())); // 처음에 지정한 qSize 만큼 준비 큐 생성

		readyQueue.get(0).offer(1); // process1 부터 시작

		int processId = 0;
		while(true){
			for (int i = 0 ; i < qSize ; i++) // 준비큐들 중에서 제일 먼저 있는 큐를 가져온다.
			{
				if(!readyQueue.get(i).isEmpty()){
					curReadyQueue = i;
					quantum = (int)Math.pow(2,i); // 해당 준비큐의 시간 할당량(q) 계산
					processId = readyQueue.get(i).remove();
					break;
				}
			}
			for(int q = 0 ; q < quantum ; q++) // 프로세스 수행 과정
			{
				svcTime.put(processId, svcTime.get(processId) + 1); // 선점된 프로세스의 서비스 시간 증가
				trace.put(totalCurrentTime, processId); // 프로세스 수행궤적 저장

				for(int pid : input.keySet()){
					if(input.get(pid)[0] - 1 == totalCurrentTime && totalCurrentTime != 0) // 다른 프로세스의 진입 시간과 현재 시간이랑 같을 때 input.get(inputKey)[0] = 도착시간
						readyQueue.get(0).offer(pid); // 맨 처음 준비 큐에 해당 프로세스 넣기
				}
				totalCurrentTime++;
				if(svcTime.get(processId).equals(input.get(processId)[1])) // 필요 서비스 시간을 만족시킨 경우 -> 반복문 종료
				//if(svcTime.get(processId) == input.get(processId)[1]) // 필요 서비스 시간을 만족시킨 경우 -> 반복문 종료
					break;
			}
			//if(svcTime.get(processId) == input.get(processId)[1]) // Integer Caching에 의한 무한루프 버그를 일으킴
			if(svcTime.get(processId).equals(input.get(processId)[1])) // 필요 서비스 시간을 만족하지 못했는지의 여부검사 -> 만족할 경우 result에 등록
			{
				int prc_arrTime = input.get(processId)[0]; // 도착시간
				int prc_svcTime = input.get(processId)[1]; // 서비스시간
				int prc_endTime = totalCurrentTime; // 종료시간
				int prc_returnTime = prc_endTime - prc_arrTime; // 반환시간
				int prc_normalReturnTime = prc_returnTime / prc_svcTime; // 정규화된 반환시간
				Integer[] values = {prc_arrTime, prc_svcTime, prc_endTime, prc_returnTime, prc_normalReturnTime};
				result.put(processId, values);
			}else{
				isReadyQueueEmpty = readyQueue.stream() // 준비큐가 모두 비어 있는지 확인
					.allMatch(Queue::isEmpty);
				if(isReadyQueueEmpty){
					readyQueue.get(curReadyQueue).offer(processId);
				}else{  // 필요 서비스 시간을 만족하지 못하였을 경우 다음 준비큐에 저장
					if(curReadyQueue + 1 == qSize)
						readyQueue.get(curReadyQueue).offer(processId); // 제일 마지막 준비 큐에서 실행한 프로세스 였을경우 그대로 해당 큐에 집어넣는다.
					else
						readyQueue.get(curReadyQueue+1).offer(processId); // 아닐 경우 그 다음 준비 큐에 집어넣는다.
				}
			}
				isReadyQueueEmpty = readyQueue.stream().allMatch(Queue::isEmpty);
				if(isReadyQueueEmpty) break; // 준비큐가 모두 비어있을 경우, 종료
		}

		return result;
	}
	public Map<Integer, Integer> getProcessTrace(){
		// 간트차트를 그리고 싶음 -> 프로세스의 수행 궤적을 저장해야 함
		// 수행 궤적 정보의 규격: totalCurrentTime, pid
		return trace;
	}
}
