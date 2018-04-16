package os_a3_final;


public class fifo 
{
	int pid[];
	int burst[];
	int waiting[];
	int arrival[];
	int service[];
	
	
	public void find_waiting()
	{
		//int total_wait=0;
		waiting= new int[burst.length+1];
		waiting[0]=0;
		for(int i=1; i<burst.length; i++)
		{
			waiting[i]= service[i]-arrival[i];
			//total_wait+= waiting[i];
		}
		//return waiting;
	}
	
	public int[] get(int p[],int b[],int a[],int start)
	{
		pid=p;
		burst=b;
		arrival=a;
		service=new int[pid.length];
		service[0]=arrival[0];
		for(int i=1;i<burst.length;i++)
		{
			service[i]=service[i-1]+burst[i-1];
			if(service[i]<arrival[i])
			{
				service[i]=service[i]+(arrival[i]-service[i]);
			}
		}
		int time= start;
		for(int i=0; i<pid.length;i++)
		{
			System.out.print("P"+pid[i]+"("+time+"-"+(time+burst[i])+") |\t");
			time+=burst[i];
		}
		find_waiting();
		waiting[pid.length]=time;
		return waiting;
	}
}