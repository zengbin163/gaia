package test.jdk.nanana;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

class PrintMatrix {

    public ArrayList<Integer> printMatrix(int[][] matrix){
        ArrayList<Integer> res = new ArrayList<>();
        if(matrix.length == 1 && matrix[0].length == 1){
            res.add(matrix[0][0]);
            return res;
        }
        int left = 0, up = 0, right = matrix[0].length - 1, down = matrix.length - 1;
        boolean ltor = true, utod = false, rtol = false, dtou = false;
        int row = 0, col = 0;
        while(up < down){
            if(ltor == true){
                while(col < right){
                    res.add(matrix[row][col]);
                    col++;
                }
                ltor = false;
                utod = true;
            }
            if(utod == true){
                while(row < down){
                    res.add(matrix[row][right]);
                    row++;
                }
                utod = false;
                rtol = true;
            }
            if(rtol == true){
                while(col > left){
                    res.add(matrix[down][col]);
                    col--;
                }
                rtol = false;
                dtou = true;
            }
            if(dtou == true){
                while(row > up){
                    res.add(matrix[row][col]);
                    row--;
                }
                dtou = false;
                ltor = true;
            }
            left++;
            up++;
            right--;
            down--;
            col = left;
            row = up;
        }
        //解决奇数行列的矩阵的问题
        if(matrix.length >> 1 << 1 != matrix.length){
            res.add(matrix[row][col]);
        }
        return res;
    }

//    public static void main(String[] args){
//		int [][]a= {
//				{ 1,  2,  3,  4,  5 },
//				{ 6,  7,  8,  9,  10 },
//				{ 11, 12, 13, 14, 15 },
//				{ 16, 17, 18, 19, 20 },
//				{ 21, 22, 23, 24, 25 }
//		};
//    	int [][]a=
//    		{
//    				{1,2,3},
//    				{4,5,6},
//    				{7,8,9}
//    		};
//		int  [][]a=
//			{
//					{1,2},
//					{3,4}
//			};
//    	PrintMatrix print=new PrintMatrix();
//    	ArrayList<Integer>res=print.printMatrix(a);
//    	System.out.println(res);
//    }
}
//这是那个类


public class TestPrintMatrix {
    PrintMatrix print = new PrintMatrix();
    ArrayList<Integer> testRes;
    ArrayList<Integer> corRes = new ArrayList<>();

    @Test
    public void r1(){
        ArrayList<Integer> list1 = new ArrayList<>();
        ArrayList<Integer> list2 = new ArrayList<>();
        list1.add(1);
        list2.add(2);
        System.out.println(list1.equals(list2));
    }

    @Test
    public void test(){
        int[][] a =
                {
                        {1, 2},
                        {3, 4}
                };

        corRes.add(1);
        corRes.add(2);
        corRes.add(4);
        corRes.add(3);
        testRes = print.printMatrix(a);

        System.out.println(corRes);
        System.out.println(testRes);
        assertEquals(corRes, testRes);
    }

    @Test
    public void test1(){
        int[][] a =
                {
                        {1}
                };
        testRes = print.printMatrix(a);
        corRes.add(1);
        assertEquals(corRes, testRes);
    }

    @Test
    public void test2(){
        int[][] a =
                {
                        {1, 2, 3, 4},
                        {5, 6, 7, 8},
                        {9, 10, 11, 12},
                        {13, 14, 15, 16}
                };
        testRes = print.printMatrix(a);
        corRes.add(1);
        corRes.add(2);
        corRes.add(3);
        corRes.add(4);
        corRes.add(8);
        corRes.add(12);
        corRes.add(16);
        corRes.add(15);
        corRes.add(14);
        corRes.add(13);
        corRes.add(9);
        corRes.add(5);
        corRes.add(6);
        corRes.add(7);
        corRes.add(11);
        corRes.add(10);
        assertEquals(corRes, testRes);
    }

    @Test
    public void test3(){
        int[][] a = {
                {1, 2, 3, 4, 5},
                {6, 7, 8, 9, 10},
                {11, 12, 13, 14, 15},
                {16, 17, 18, 19, 20},
                {21, 22, 23, 24, 25}
        };
        testRes = print.printMatrix(a);
        corRes.add(1);
        corRes.add(2);
        corRes.add(3);
        corRes.add(4);
        corRes.add(5);
        corRes.add(10);
        corRes.add(15);
        corRes.add(20);
        corRes.add(25);
        corRes.add(24);
        corRes.add(23);
        corRes.add(22);
        corRes.add(21);
        corRes.add(16);
        corRes.add(11);
        corRes.add(6);
        corRes.add(7);
        corRes.add(8);
        corRes.add(9);
        corRes.add(14);
//		corRes.add(19);
//		corRes.add(18);
//		corRes.add(17);
//		corRes.add(12);
//		corRes.add(13);

        System.out.println(corRes);
        System.out.println(testRes);
        assertEquals(testRes, corRes);
    }
}
