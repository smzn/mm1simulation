package mmsimulation;

import java.util.Random;

public class MMSimulation_lib {
	
	private double lambda, mu, rho;
	private int time;
	Random rnd = new Random();
	
	public MMSimulation_lib(double lambda, double mu, int time) {
		this.lambda = lambda;
		this.mu = mu;
		this.time = time;
		this.rho = lambda / mu;
	}
	
	
	public double getSimulation() { //イベントドリブン型
		double arrival = this.getExponential(lambda);
		double service = arrival + this.getExponential(mu);
		int queue = 0; //サービス中を含むキューの長さ
		double elapse = 0;
		double total_queue = 0;
		while(elapse < time) {
			if( arrival < service ) { //到着が発生
				total_queue += queue * arrival;
				queue++;
				elapse += arrival;
				service -= arrival;
				arrival = this.getExponential(lambda);
			}
			else if(arrival >= service ){ //退去が発生
				total_queue += queue * service;
				queue--;
				elapse += service;
				arrival -= service;
				if( queue == 0) //queueが0なら到着後サービス
					service = arrival + this.getExponential(mu);
				else if( queue > 0)
					service = this.getExponential(mu);
			}
		}
		return total_queue / time;
	}
	
	//指数乱数発生
	public double getExponential(double param) {
		return - Math.log(1 - rnd.nextDouble()) / param;
	}

	public double getL() { //平均系内人数
		return rho / ( 1- rho );
	}
	
	public double getW() { //平均平内時間
		return 1 / ( 1- rho ) / lambda;
	}
	
	public double getQ() { //平均待ち人数
		return Math.pow(rho, 2) / ( 1- rho ) ;
	}
	
	public double getWq() { //平均待ち時間
		return rho / ( 1- rho ) / lambda ;
	}

}
