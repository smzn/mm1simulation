package mmsimulation;

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
		System.out.println("Simulation : 系内人数 = "+msim.getSimulation());
		System.out.println("Solution : 系内人数 = "+msim.getL());
		System.out.println("Solution : 系内時間 = "+msim.getW());
		System.out.println("Solution : 待ち人数 = "+msim.getQ());
		System.out.println("Solution : 待ち時間 = "+msim.getWq());
		
	}

}
