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
				event.add("arrival");
				queuelength.add(queue);
				queue++;
				elapse += arrival;
				eventtime.add(elapse); //経過時間の登録はイベント後
				service -= arrival;
				arrival = this.getExponential(lambda);
			}
			else if(arrival >= service ){ //退去が発生
				total_queue += queue * service;
				if( queue > 0 ) total_queuelength += ( queue - 1 ) * service;
				else if ( queue == 0 ) total_queuelength += queue * service;
				event.add("departure");
				queuelength.add(queue);
				queue--;
				elapse += service;
				eventtime.add(elapse); //経過時間の登録はイベント後
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
	
	public double[] getEvaluation() {
		int maxLength = 0;
		double result[] = new double[3]; //平均系内時間、系内時間分散、最大待ち行列の長さ
		for(int i = 0; i < eventtime.size(); i++) {
			//System.out.println("Eventtime : "+eventtime.get(i)+" Event : "+ event.get(i)+" Queuelength : "+queuelength.get(i));
			if( maxLength < queuelength.get(i) ) maxLength = queuelength.get(i);
		}
		int arrival_number = 0, departure_number = 0, arrival_index = 0, departure_index = 0;
		double systemtime = 0, systemtime2 = 0;
		for(int i = 0; i < eventtime.size(); i++) { //同じ客の到着と退去を探す
			if(event.get(i) == "arrival") {
				arrival_number++;
				arrival_index = i;
				for(int j = departure_index + 1; j < eventtime.size(); j++) {
					if(event.get(j) == "departure") {
						departure_number++;
					}
					if( arrival_number == departure_number) {
						departure_index = j;
						systemtime += eventtime.get(departure_index) - eventtime.get(arrival_index);
						systemtime2 += Math.pow(eventtime.get(departure_index) - eventtime.get(arrival_index),2);
						break;
					}
				}
			}
		}
		result[0] = systemtime / departure_number;
		result[1] = systemtime2 / departure_number - Math.pow((systemtime / departure_number),2);
		result[2] = maxLength;
		return result;
	}
	
	//指数乱数発生
	public double getExponential(double param) {
		return - Math.log(1 - rnd.nextDouble()) / param;
	}

	public double getL() { //平均系内人数
		return rho / ( 1- rho );
	}
	
	public double getW() { //平均系内時間
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
		return 1 / ( Math.pow(mu * ( 1 - rho ),2));
	}
	public double getVWq() { //待ち時間分散
		return ( 2 * rho - Math.pow(rho, 2)) / Math.pow( mu * ( 1 - rho ), 2);
	}
}
