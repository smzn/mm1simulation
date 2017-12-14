package mmsimulation;

import java.util.Arrays;
import java.util.Scanner;

public class MMSimulation_main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);
		
		System.out.print("Input Lambda > ");
		double lambda = Double.parseDouble(scan.next());
		System.out.print("Input mu > ");
		double mu = Double.parseDouble(scan.next());
		System.out.print("Input Time > ");
		int time = Integer.parseInt(scan.next());
		
		MMSimulation_lib msim = new MMSimulation_lib(lambda, mu, time);
		System.out.println("Simulation : (系内人数,待ち人数) = "+Arrays.toString(msim.getSimulation()));
		System.out.println("Simulation : (系内時間,系内時間分散,最大待ち人数) = "+Arrays.toString(msim.getEvaluation()));
		System.out.println("Simulation : (時間割合) = "+Arrays.toString(msim.getTimerate()));
		System.out.println("Solution : 系内人数 = "+msim.getL());
		System.out.println("Solution : 系内時間 = "+msim.getW());
		System.out.println("Solution : 待ち人数 = "+msim.getQ());
		System.out.println("Solution : 待ち時間 = "+msim.getWq());
		System.out.println("Solution : 系内人数分散 = "+msim.getVL());
		System.out.println("Solution : 系内時間分散 = "+msim.getVW());
		System.out.println("Solution : 待ち人数分散 = "+msim.getVQ());
		System.out.println("Solution : 待ち時間分散 = "+msim.getVWq());		
	}

}
