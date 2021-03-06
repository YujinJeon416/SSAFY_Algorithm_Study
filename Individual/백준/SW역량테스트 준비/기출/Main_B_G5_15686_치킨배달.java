import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

/*
 * -치킨 배달-
 * 1. 모든 치킨집과 집간의 거리를 미리 구하여 2차원 배열에 저장한다.
 * 2. 치킨집을 정해진 갯수만큼 뽑고 집과의 최소 거리를 반환한다.
 * 3. 미리 각 거리를 구해두어 계속 같은 연산을 하지 않도록 하였다.
 */

// 출처 : https://www.acmicpc.net/problem/15686
public class Main_B_G5_15686_치킨배달 {
	static int N, M;
	static char[][] board;
	static int result = Integer.MAX_VALUE;
	// 모든 치킨집 좌표 List
	static List<Integer[]> chickList = new ArrayList<Integer[]>();
	static Stack<Integer> chickenStack = new Stack<Integer>();
	static int chickSize;
	// 집 좌표 List
	static List<Integer[]> homeList = new ArrayList<Integer[]>();
	static int homeSize;

	static int[][] memo;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		board = new char[N][N];
		String tempString;
		for (int row = 0; row < N; ++row) {
			tempString = br.readLine();
			for (int col = 0; col < N; ++col) {
				// '0':빈칸 '1':집 '2':치킨집
				board[row][col] = tempString.charAt(col * 2);
				// 만약 치킨집이라면 해당 리스트에 좌표 추가
				switch (tempString.charAt(col * 2)) {
				case '1':
					homeList.add(new Integer[] { row, col });
					break;
				case '2':
					chickList.add(new Integer[] { row, col });
					break;
				// 0
				default:
					break;
				}
			}
		} // end for(row)

		// 치킨집 갯수 저장
		chickSize = chickList.size();
		homeSize = homeList.size();
		memo = new int[homeSize][chickSize];

		// 각 집 index와 치킨집 index 간의 최소 거리를 미리 구해놓기
		saveAllDistance();
		// 치킨집을 뽑는 재귀 함수 이용
		pickChicken(0, 0);

		System.out.println(result);
	}

	private static void saveAllDistance() {
		// 모든 home과 치킨집 간의 관계 작성
		for (int homeIndex = 0; homeIndex < homeSize; ++homeIndex) {
			for (int chickIndex = 0; chickIndex < chickSize; ++chickIndex) {
				memo[homeIndex][chickIndex] = getDistance(homeList.get(homeIndex)[0], homeList.get(homeIndex)[1],
						chickList.get(chickIndex)[0], chickList.get(chickIndex)[1]);
			}
		}

	}

	// 현재 뽑은 치킨집
	private static void pickChicken(int curCount, int curIndex) {
		// 만일 다 뽑았다면 뽑은 각 치킨집과 모든집List와의 최소값 구하기
		if (curCount == M) {
			int minResult = Integer.MAX_VALUE;
			int tempResult;

			int tempSum = 0;
			// 집 하나에 대해서 가장 거리 짧은 치킨집 넣기
			for (int homeIndex = 0; homeIndex < homeSize; ++homeIndex) {
				minResult = Integer.MAX_VALUE;
				for (Integer chickIndex : chickenStack) {
					tempResult = memo[homeIndex][chickIndex];
					minResult = minResult < tempResult ? minResult : tempResult;
				} // end for (chick)
				tempSum += minResult;
			} // end for(home)
			result = result < tempSum ? result : tempSum;
			return;
		} // end function

		// 다 뽑지 못했다면 다음 치킨집 get
		for (int index = curIndex; index <= chickSize - M + curCount; ++index) {
			chickenStack.push(index);
			pickChicken(curCount + 1, index + 1);
			chickenStack.pop();
		}

	}

	// 치킨집과 집과의 거리 구하기
	private static int getDistance(int row1, int col1, int row2, int col2) {
		return Math.abs(row1 - row2) + Math.abs(col1 - col2);
	}
}
