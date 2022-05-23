import java.util.Arrays;
import java.util.Scanner;

public class Banker {
    static int PNum /*= 5*/, RNum /*= 3*/;
    static int [] ResMax;
    /*= {10,5,7}*/
    static int [] available;
    /*= {0,0,0}*/
    static int [][] maximum ;
    /*= {
            {10,5,7},
            {3,2,2},
            {9,0,2},
            {2,2,2},
            {4,3,3}
    };*/
    static int [][] allocation ;
    /*= {
            {3,4,2},
            {2,0,0},
            {3,0,2},
            {2,1,1},
            {0,0,2}
    };*/

    static int [][] need;
    static boolean [] finish;
    static ResCons temp = new ResCons();

    static void copyArray(int [] arr1, int [] arr2){
        for (int i =0; i < RNum ; i++){
            arr1[i] = arr2[i];
        }
    }

    static void copy2DArray(int [][] arr1, int [][] arr2){
        for (int i =0; i < PNum ; i++){
            for (int k =0; k < RNum; k++){
                arr1[i][k] = arr2[i][k];
            }
        }
    }

    static void calcNeed(){
        for (int i=0; i < PNum; i++){
            for (int j=0; j< RNum; j++){
                need[i][j] = maximum[i][j] - allocation[i][j];
            }
        }
    }

    static void canFinish(int num){
        for (int i =0; i< RNum; i++){
            if (need[num][i] > temp.available[i]) return;
        }
        for (int i =0; i< RNum; i++){
            temp.available[i] += temp.allocation[num][i];
            temp.allocation[num][i] = 0;
            finish[num] = true;
        }
        System.out.println(num + " :" +Arrays.toString(temp.available));
    }

    static boolean isSafe(){
        for (int i =0; i < PNum; i++){
            finish[i] = false;
        }
        for (int i = 0; i < PNum*PNum; i++){
            if (!finish[i%PNum]){
                canFinish(i%PNum);
            }

            int count = 0;
            for (int k = 0; k < PNum; k++){
                if (finish[k]){
                    count++;
                }
            }
            if (count == PNum){
                copy2DArray(temp.maximum, maximum);
                copy2DArray(temp.allocation, allocation);
                copyArray(temp.available, available);
                copy2DArray(temp.need, need);
                return true;
            }
        }
        copy2DArray(temp.maximum, maximum);
        copy2DArray(temp.allocation, allocation);
        copyArray(temp.available, available);
        copy2DArray(temp.need, need);
        return false;
    }

    static void request(int process, int [] resources){
        for (int i=0; i < RNum; i++){
            if (resources[i] > available[i]){
                System.out.println("Resources are unavailable");
                return;
            }
            if (resources[i] > need[process][i]){
                System.out.println("Resources exceeded process' maximum resource");
                return;
            }
        }

        for (int i=0; i< RNum; i++){
            allocation[process][i] += resources[i];
            need[process][i] -= resources[i];
            available[i] -= resources[i];
        }
        copy2DArray(temp.need, need);
        copy2DArray(temp.allocation, allocation);
        copyArray(temp.available, available);

        if (isSafe()){
            System.out.println("System is safe");
        }else{
            System.out.println("===========================================");
            System.out.println("System is unsafe\nStarting recovery");
            while (!isSafe()){
                int x;
                x = recovery();
                System.out.println("The victim is process: " + x);
            }
            System.out.println("System is recovered\nSystem is safe now");
        }
    }

    static void release(int process, int [] resources){
        for (int i=0; i < RNum; i++){
            if (resources[i] > allocation[process][i]){
                System.out.println("Resources are unavailable");
                return;
            }
        }
        for (int i=0; i< RNum; i++){
            allocation[process][i] -= resources[i];
            available[i] += resources[i];
            need[process][i] += resources[i];
        }
        copy2DArray(temp.allocation, allocation);
        copyArray(temp.available, available);
        copy2DArray(temp.need, need);

        if (isSafe()){
            System.out.println("System is safe");
        }else{
            System.out.println("System is unsafe");
        }

    }

    static int recovery(){
        int mx = 0;
        int victimProcess = 0;
        for (int i = 0; i< PNum; i++){
            int temp = 0;
            for (int k =0; k < RNum; k++){
                temp += allocation[i][k];
            }
            if (temp > mx){
                mx = temp;
                victimProcess = i;
            }
        }
        for (int i=0; i< RNum; i++){
            available[i] += allocation[victimProcess][i];
            need[victimProcess][i] += allocation[victimProcess][i];
            allocation[victimProcess][i] = 0;
        }
        copy2DArray(temp.allocation, allocation);
        copyArray(temp.available, available);
        copy2DArray(temp.need, need);
        return victimProcess;
    }

    static boolean calcAvailable(){
        int [] total = new int[RNum];
        for (int i=0; i< PNum; i++){
            for(int k=0; k < RNum; k++){
                total[k] += allocation[i][k];
            }
        }
        for (int i =0; i <RNum; i++){
            if (total[i] > ResMax[i]){
                return false;
            }
            available[i] = ResMax[i] - total[i];
        }
        return true;
    }

    public static void main(String[] args) {
        while (true) {
            Scanner input = new Scanner(System.in);
            System.out.println("Hello From Banker Algorithm");
            System.out.print("Enter Number of Processes: ");
            PNum = input.nextInt();
            System.out.print("Enter Number of Resources: ");
            RNum = input.nextInt();
            allocation = new int[PNum][RNum];
            maximum = new int[PNum][RNum];
            need = new int[PNum][RNum];
            ResMax = new int[RNum];
            available = new int[RNum];
            finish = new boolean[PNum];
            temp.allocation = new int[PNum][RNum];
            temp.maximum = new int[PNum][RNum];
            temp.need = new int[PNum][RNum];
            temp.available = new int[RNum];
            System.out.println("Please Enter Number of Each Resource Instance: ");
            for (int i = 0; i < RNum; i++) {
                System.out.print("\t" + (char) ('A' + i) + ": ");
                ResMax[i] = input.nextInt();
            }
            System.out.println("Please Enter Each Process Allocation Resources: ");
            for (int i = 0; i < PNum; i++) {
                System.out.println("P" + i + ": ");
                for (int k = 0; k < RNum; k++) {
                    System.out.print("\t" + (char) ('A' + k) + ": ");
                    allocation[i][k] = input.nextInt();
                }
            }
            System.out.println("Please Enter Each Process Maximum Resources: ");
            for (int i = 0; i < PNum; i++) {
                System.out.println("P" + i + ": ");
                for (int k = 0; k < RNum; k++) {
                    System.out.print("\t" + (char) ('A' + k) + ": ");
                    maximum[i][k] = input.nextInt();
                }
            }
            if (!calcAvailable()) {
                System.out.println("The Allocated Resources are bigger than the Resources Instances. :(");
                continue;
            }
            calcNeed();
            if (isSafe()) {
                System.out.println("System is safe");
            } else {
                System.out.println("System is unsafe");
            }
            while (true) {
                System.out.println("====================================");
                System.out.println("1- Request Resource.");
                System.out.println("2- Release Resource.");
                System.out.println("3- Quit.");
                short choose = input.nextShort();
                int processNum;
                int[] res = new int[RNum];
                switch (choose) {
                    case 1: {
                        System.out.print("Enter the Process Number: ");
                        processNum = input.nextInt();
                        for (int i = 0; i < RNum; i++) {
                            System.out.print("\t" + (char) ('A' + i) + ": ");
                            res[i] = input.nextInt();
                        }
                        request(processNum, res);
                        break;
                    }
                    case 2: {
                        System.out.print("Enter the Process Number: ");
                        processNum = input.nextInt();
                        for (int i = 0; i < RNum; i++) {
                            System.out.print("\t" + (char) ('A' + i) + ": ");
                            res[i] = input.nextInt();
                        }
                        release(processNum, res);
                        break;
                    }
                    case 3: {
                        System.exit(0);
                    }
                    default:
                        System.out.println("Wrong input.");
                }
            }
        }
    }
}
