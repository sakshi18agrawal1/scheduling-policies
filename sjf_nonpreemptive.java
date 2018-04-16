package os_a3_final;

public class sjf_nonpreemptive 
{
	int pid[];
	int burst[];
	int waiting[];
	int arrival[];
	int extra[];
	
	public int find_min(int a)
	{
		int min=100;
		int index=0,flag=0;
		for(int i=0;i<=a;i++)
		{
			if(extra[i]<min&&extra[i]>0)
			{
				min=extra[i];
				index=i;
				flag=1;
			}
		}
		if(flag==0)
			return -1;
		return index;
	}
	public void find_waiting(int start)
	{
		int min=0;
		waiting= new int[burst.length+1];
		waiting[0]=0;
		int time=start,finish=0;
		int x=0,y=0,i=1;
		int completed_pid=0;
		while(completed_pid!=pid.length)
		{
			while(y<pid.length&&arrival[y]<=time)
			{
				y++;
			}
			//sort(x,y);
			
			min=find_min(y-1);
			if(min!=-1)
			{
				extra[min]=0;
				completed_pid++;
				waiting[min]=time-arrival[min];
				System.out.print("P"+pid[min]+"("+time+"-"+(time+burst[min])+") |\t");
				time+= burst[min];
			}
		}		
		waiting[pid.length]= time;
	}
	public void copy()
	{
		for(int i=0;i<pid.length;i++)
			extra[i]=burst[i];
	}
	public int[] get(int p[],int b[],int a[], int start)
	{
		pid=p;
		burst=b;
		arrival=a;
		extra=new int[pid.length];
		copy();
		find_waiting(start);
		return waiting;
	}
}
