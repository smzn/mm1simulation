package mmsimulation;

import java.util.ArrayList;
import java.util.Random;

public class MMSimulation_lib {
	
	private double lambda, mu, rho;
	private int time;
	Random rnd = new Random();
	ArrayList<Double> eventtime; 
	ArrayList<String> event;
	ArrayList<Integer> queuelength;
	private double eventrate[][];
	
	public MMSimulation_lib(double lambda, double mu, int time) {
		this.lambda = lambda;
		this.mu = mu;
		this.time = time;
		this.rho = lambda / mu;
		eventtime = new ArrayList<Double>();
		event = new ArrayList<String>();
		queuelength = new ArrayList<Integer>();
	}
	
	
	public double[] getSimulation() { //イベントドリブン型
		double arrival = this.getExponential(lambda);
		double service = arrival + this.getExponential(mu);
		int queue = 0; //サービス中を含むキューの長さ
		double elapse = 0;
		double total_queue = 0; //系内人数
		double total_queuelength = 0; //待ち人数
		double result[] = new double[2]; 
		while(elapse < time) {
			if( arrival < service ) { //到着が発生
				total_queue += queue * arrival;
				if( queue > 0 ) total_queuelength += ( queue - 1 ) * arrival; //待ち人数のみ
				else if ( queue == 0 ) total_queuelength += queue * arrival;
				eventtime.add(elapse);
				event.add("arrival");
				queuelength.add(queue);
				queue++;
				elapse += arrival;
				service -= arrival;
				arrival = this.getExponential(lambda);
			}
			else if(arrival >= service ){ //退去が発生
				total_queue += queue * service;
				if( queue > 0 ) total_queuelength += ( queue - 1 ) * service;
				else if ( queue == 0 ) total_queuelength += queue * service;
				eventtime.add(elapse);
				event.add("departure");
				queuelength.add(queue);
				queue--;
				elapse += service;
				arrival -= service;
				if( queue == 0) //queueが0なら到着後サービス
					service = arrival + this.getExponential(mu);
				else if( queue > 0)
					service = this.getExponential(mu);
			}
		}
		result[0] = total_queue / time;
		result[1] = total_queuelength / time;
		return result;
	}
	
	public void getEvaluation() {
		int maxLength = 0;
		for(int i = 0; i < eventtime.size(); i++) {
			System.out.println("Eventtime : "+eventtime.get(i)+" Event : "+ event.get(i)+" Queuelength : "+queuelength.get(i));
			if( maxLength < queuelength.get(i) ) maxLength = queuelength.get(i);
		}
		System.out.println("MaxQueueLength = "+maxLength);
	}
	
	//指数乱数発生
	public double getExponential(double param) {
		return - Math.log(1 - rnd.nextDouble()) / param;
	}

	public double getL() { //平均系内人数
		return rho / ( 1- rho );
	}
	
	public double getW() { //平均平内時間
		return 1 / ( 1- rho ) / mu;
	}
	
	public double getQ() { //平均待ち人数
		return Math.pow(rho, 2) / ( 1- rho ) ;
	}
	
	public double getWq() { //平均待ち時間
		return rho / ( 1- rho ) / mu ;
	}
	
	public double getVL() { //系内人数分散
		return rho / Math.pow( 1 - rho, 2 );
	}
	public double getVQ() {  //待ち人数分散
		return Math.pow(rho, 2) * ( 1 + rho - Math.pow(rho, 2)) / Math.pow( 1 - rho , 2);
	}
	public double getVW() { //系内時間分散
		return 1 / ( mu * ( 1 - rho ));
	}
	public double getVWq() { //待ち時間分散
		return ( 2 * rho - Math.pow(rho, 2)) / Math.pow( mu * ( 1 - rho ), 2);
	}
}
