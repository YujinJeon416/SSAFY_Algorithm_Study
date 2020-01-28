import java.io.BufferedReader;
import java.io.InputStreamReader;

// ��ó : https://www.acmicpc.net/problem/14503
public class Solution_14503 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String[] tempStringArr;
		String tempString;
		
		char[][] board;
		// board�� ����, ���� Size
		int rowSize;
		int colSize;
		
		// �κ��� ���� ��ġ, ����
		int curRow;	int curCol;
		// ������ �ϴ� ��ġ
		int postRow; int postCol;
		// 0:��, 2:��, 1:��, 3:��
		int curDir; // 
		// curDir�� ���� �̵��Լ�
		int[] dRow = {-1, 0, 1, 0};
		int[] dCol = {0, 1, 0, -1};
		
		// û���� ����
		int result=1;
		
		// ����, ���� ũ�� �ޱ�
		tempStringArr = br.readLine().split(" ");
		rowSize = Integer.parseInt(tempStringArr[0]);
		colSize = Integer.parseInt(tempStringArr[1]);
		
		board = new char[rowSize][colSize];

		tempStringArr = br.readLine().split(" ");
		curRow = Integer.parseInt(tempStringArr[0]);
		curCol = Integer.parseInt(tempStringArr[1]);
		curDir = Integer.parseInt(tempStringArr[2]);
		
		// �迭 �ʱ�ȭ
		for(int row = 0; row < rowSize; ++row) {
			tempString = br.readLine();
			for(int col = 0; col < colSize; ++col) {
				board[row][col] = tempString.charAt(col*2);
			}
		}
		
		//
		boolean needBack = false;
		boolean flag = true;
		
		// �⺻ ��ġ û��
		board[curRow][curCol] = '2';
		while(flag) {
			// ���� ��ġ û���ϱ�
			for(int i = 0; i < 4; ++i) {
				// �ϴ� ������ȯ �ϱ�
				curDir = (curDir+3)%4;
				postRow = curRow+dRow[curDir];
				postCol = curCol+dCol[curDir];
				// ���ʹ����� û�Ұ� �Ǿ����� �ʴٸ� ��ġ �̵��ϰ� û���ϱ�
				if(board[postRow][postCol] == '0') {
					curRow = postRow;
					curCol = postCol;
					++result;
					//û�� �ߴٸ� 2�� ä���
					board[curRow][curCol] = '2';
					
					break;
				}
				// �� ���� ��� û�Ұ� �Ǿ��ִٸ� needBack = true;
				if(i == 3) {
					needBack = true;
				}
			}
			// end for

			// �� ���� ��� û�Ұ� �Ǿ��ִٸ� ���� �������� �� ĭ ����
			if(needBack) {
				postRow = curRow-dRow[curDir];
				postCol = curCol-dCol[curDir];
				
				// ���� ���� ��쿡�� �۵� ���߱�
				// �ش� ��ǥ ���ϰ��
				if(board[postRow][postCol] == '1') {
					System.out.println(result);
					flag = false;
				}
				// '0' or '2' �ϰ�� �̵�
				else {
					needBack = false;
					curRow = postRow;
					curCol = postCol;
				}
			}
			// end if(needBack)
		}
	}
}
