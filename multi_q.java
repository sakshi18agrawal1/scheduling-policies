package os_a3_final;

public class multi_q 
{
	int pid[];
	int burst[];
	int prior[];
	int arrival[];
	int q_num[];
	int waiting[];
	int n[]= new int[3];
	
	/*public int find_max(int p[])
	{
		int max=p[0];
		for(int i=1; i<p.length; i++)
		{
			if(max<p[i])
				max=p[i];
		}
		return max;
	}*/
	public void assign_q()
	{
		int max_p= 10;
		int q1= max_p/3;
		int q2= 2*max_p/3;
		for(int i=0; i<3;i++)
			n[i]=0;
		for(int i=0; i<pid.length; i++)
		{
			if(prior[i]<=q1)
			{
				q_num[i]=0;
				n[0]++;
			}
			else if(prior[i]<=q2)
			{
				q_num[i]=1;
				n[1]++;
			}
			else
			{
				q_num[i]=2;
				n[2]++;
			}
		}
	}
	public void find_waiting()
	{
		fifo fifo_obj= new fifo();
		sjf_preemption sjfp_obj= new sjf_preemption();
		sjf_nonpreemptive sjfnp_obj= new sjf_nonpreemptive();
		priority_preemptive priop_obj= new priority_preemptive();
		priority_nonpreemptive prionp_obj= new priority_nonpreemptive();
		round_robin rr_obj= new round_robin();
		int wait=0;
		int q_sched[]= new int[3];
		for(int i=0; i<3; i++)
		{
			if(n[i]==0)
				continue;
			q_sched[i]= 1 + (int)(Math.random()*5);
			int p[]= new int[n[i]];
			int b[]= new int[n[i]];
			int pri[]= new int[n[i]];
			int a[]= new int[n[i]];
			int w[]= new int[n[i]];
			int temp=0;
			for(int j=0; j<pid.length; j++)
			{
				if(q_num[j]==i)
				{
					p[temp]=pid[j];
					b[temp]= burst[j];
					pri[temp]= prior[j];
					a[temp]= arrival[j];
					temp++;
				}			
			}
			if(q_sched[i]==1)
			{
				System.out.println("\nQueue "+(i+1)+" scheduling: First Come First Serve");
				w=fifo_obj.get(p,b,a,wait);
			}
			else if(q_sched[i]==2)
			{
				System.out.println("\nQueue "+(i+1)+" scheduling: SJF Non-preemptive");
				w=sjfnp_obj.get(p,b,a,wait);
			}
			else if(q_sched[i]==3)
			{
				System.out.println("\nQueue "+(i+1)+" scheduling: Priority based non-preemptive");
				w=prionp_obj.get(p,b,pri,a,wait);
			}
			else if(q_sched[i]==4)
			{
				System.out.println("\nQueue "+(i+1)+" scheduling: SJF preemptive");
				w=sjfp_obj.get(p,b,a,wait);
			}
			else if(q_sched[i]==5)
			{
				System.out.println("\nQueue "+(i+1)+" scheduling: Priority based preemptive");
				w=priop_obj.get(p,b,pri,a,wait);
			}
			else if(q_sched[i]==6)
			{
				System.out.println("\nQueue "+(i+1)+" scheduling: Round Robin");
				w=rr_obj.get(p,b,a,2,wait);
			}
			temp=0;
			for(int j=0; j<pid.length; j++)
			{
				if(q_num[j]==i)
				{
					waiting[j]= w[temp];
					//System.out.println(j);
					temp++;
				}
			}
			wait=w[p.length];
			//System.out.println("wait" +wait);
		}
	}
	public int[] get(int p[],int b[],int pri[], int a[])
	{
		pid=p;
		burst=b;
		prior=pri;
		arrival=a;
		q_num= new int[pid.length];
		waiting= new int[pid.length];
		assign_q();
		find_waiting();
		return waiting;
	}
}

