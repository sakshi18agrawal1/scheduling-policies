package os_a3_final;

import java.util.Scanner;
public class all_schedule 
{
	static int pid[];
	static int burst[];
	static int arrival[];
	static int priority[];
	static int waiting[];
	public static void main(String args[])
	{
		Scanner sc= new Scanner(System.in);
		System.out.println("Enter number of processes");
		int n=sc.nextInt();
		pid= new int[n];
		arrival=new int[n]; 
		burst=new int[n]; 
		priority=new int[n];
		pid[0]=0;
		arrival[0]=0;
		burst[0]=3+(int)(Math.random()*27);
		priority[0]=3;
		for(int i=1 ; i<n; i++)
		{
			pid[i]= i;
			burst[i]= 1+ (int)(Math.random()*29);
			priority[i]= 1+ (int)(Math.random()*9);
			arrival[i]= (int)(Math.random()*20); 
		}
		all_schedule obj= new all_schedule();
		int input;
		waiting= new int[n];
		int turn_around[]= new int[n];
		int total_turn, total_wait;
		float sd_turn, sd_wait;
		fifo fifo_obj= new fifo();
		sjf_preemption sjfp_obj= new sjf_preemption();
		sjf_nonpreemptive sjfnp_obj= new sjf_nonpreemptive();
		priority_preemptive priop_obj= new priority_preemptive();
		priority_nonpreemptive prionp_obj= new priority_nonpreemptive();
		round_robin rr_obj= new round_robin();
		multi_q multiq_obj= new multi_q();
		multi_feedback feedback_obj= new multi_feedback();
		do
		{
			obj.sort();
			System.out.println("\nSelect a scheduling algorithm:\n1-First come, first serve\n2-Shortest job first\n3-Priority based\n4-Shortest job first(preemptive)\n5-Priority base(preemptive)\n6-Round Robin\n7-Multilevel queue\n8-Multilevel feedback queue\n9-Exit");
			input= sc.nextInt();
			if(input==1)
			{
				System.out.println("\nDisplaying GANNT chart");
				waiting=fifo_obj.get(pid, burst, arrival,arrival[0]);
				System.out.println("\nFIFO scheduling:");
				
			}
			else if(input==2)
			{
				System.out.println("\nDisplaying GANNT chart");
				waiting=sjfnp_obj.get(pid, burst, arrival,arrival[0]);
				System.out.println("\nSJF Non-Preemptive scheduling:");
			}
			else if(input==3)
			{
				System.out.println("\nDisplaying GANNT chart");
				waiting=prionp_obj.get(pid, burst,priority, arrival,arrival[0]);
				System.out.println("\nPriority based Non-Preemptive scheduling:");
			}
			else if(input==4)
			{
				System.out.println("\nDisplaying GANNT chart");
				waiting=sjfp_obj.get(pid, burst, arrival,arrival[0]);
				System.out.println("\nSJF Preemptive scheduling:");
			}
			else if(input==5)
			{
				System.out.println("\nDisplaying GANNT chart");
				waiting=priop_obj.get(pid, burst,priority, arrival,arrival[0]);
				System.out.println("\nPriority based Preemptive scheduling:");
			}
			else if(input==6)
			{
				System.out.println("\nDisplaying GANNT chart");
				waiting=rr_obj.get(pid, burst, arrival,2,arrival[0]);
				System.out.println("\nRound Robin scheduling:");
			}
			else if(input==7)
			{
				System.out.println("\nDisplaying GANNT chart");
				waiting=multiq_obj.get(pid, burst,priority, arrival);
				System.out.println("\nMulti level queue scheduling:");
			}
			else if(input==8)
			{
				System.out.println("\nDisplaying GANNT chart");
				waiting=feedback_obj.get(pid, burst,priority, arrival);
				System.out.println("\nMulti level feedback queue scheduling:");
			}
			obj.sort_wait();
			if(input<9)
			{
				turn_around = find_turnaround(burst, waiting);
				total_turn= sum(turn_around);
				total_wait= sum(waiting);
				sd_turn= st_dev(turn_around);
				sd_wait= st_dev(waiting);
				display(pid,burst, arrival, priority, waiting, turn_around, total_wait, total_turn, sd_wait, sd_turn);
			}
		}
		while(input!=9);
	}
	public static void display(int pid[],int burst[], int arrival[], int priority[], int waiting[], int turn_around[], int wait, int turnaround, float sd_wait, float sd_turn)
	{
		float avg_wait= ((float)wait/ (float)pid.length);
		float avg_turn= ((float)turnaround/ (float)pid.length);
		System.out.println("Pid    Burst    Arrival    Priority    Waiting    Turnaround"); 
		for(int i=0; i<pid.length; i++)
			System.out.println(pid[i] +"\t"+burst[i]+"\t "+arrival[i]+"\t    "+priority[i]+"\t        "+ waiting[i]+"\t     "+ turn_around[i]);
		System.out.println("\n\nAverage waiting time= "+avg_wait);
		System.out.println("Average turn around time= "+avg_turn);
		System.out.println("Standard Deviation of waiting time= "+sd_wait);
		System.out.println("Standard Deviation of  turnaround time= "+sd_turn);
	}
	public static float st_dev(int arr[])
	{
		float mean=(float)sum(arr)/(float)arr.length;
		float st=0;
		for(int i=0;i<arr.length;i++)
			st=st+(float)Math.pow(((float)arr[i]-mean),2);
		float res= ((float)st/ (float)arr.length);
		res=(float)Math.sqrt(res);
		return res;
	}
	public static int[] find_turnaround(int burst[], int waiting[])
	{
		int []turn_around= new int[burst.length];
		for(int i=0; i<burst.length; i++)
			turn_around[i]=burst[i]+waiting[i];
		return turn_around;
	}
	public static int sum(int arr[])
	{
		int total=0;
		for(int i=0; i<pid.length; i++)
			total+= arr[i];
		return total;
	}
	public void sort()
    {
		int temp;
        int n = pid.length;
        for (int i = 0; i < n-1; i++)
            for (int j = 0; j < n-i-1; j++)
                if (arrival[j] > arrival[j+1])
                {
                    // swap temp and arr[i]
                    temp = arrival[j];
                    arrival[j] = arrival[j+1];
                    arrival[j+1] = temp;
					temp = burst[j];
                    burst[j] = burst[j+1];
                    burst[j+1] = temp;
					temp = pid[j];
                    pid[j] = pid[j+1];
                    pid[j+1] = temp;
                    temp = priority[j];
                    priority[j] = priority[j+1];
                    priority[j+1] = temp;
                }
    }
	public void sort_wait()
	  {
		  int temp;
	      int n = pid.length;
	      for (int i = 0; i < n-1; i++)
	          for (int j = 0; j < n-i-1; j++)
	              if (pid[j] > pid[j+1])
	              {
	            	  	temp = arrival[j];
	                    arrival[j] = arrival[j+1];
	                    arrival[j+1] = temp;
						temp = burst[j];
	                    burst[j] = burst[j+1];
	                    burst[j+1] = temp;
						temp = pid[j];
	                    pid[j] = pid[j+1];
	                    pid[j+1] = temp;
	                    temp = priority[j];
	                    priority[j] = priority[j+1];
	                    priority[j+1] = temp;
	                    temp = waiting[j];
	                    waiting[j] = waiting[j+1];
	                    waiting[j+1] = temp;
	              }
	  }
}
