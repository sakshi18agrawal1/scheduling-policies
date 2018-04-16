package os_a3_final;

public class multi_feedback
{
	int pid[];
	int burst[];
	int prior[];
	int arrival[];
	int extra[];
	int waiting[];
	int completion[];
	public void find_waiting()
	{
		completion= new int[pid.length];
		int time=arrival[0];
		int temp=0;
		int q=2;
		for(int i=0; i<2; i++)
		{
			temp=0;
			while(temp<pid.length)
			{
				if(arrival[temp]<=time)
				{	
					if(extra[temp]>q)
					{
						extra[temp]-=q;
						System.out.print("P"+pid[temp]+"("+time+"-"+(time+q)+") |\t");
						time+=q;
					}
					else if(extra[temp]>0&& extra[temp]<=q)
					{
						System.out.print("P"+pid[temp]+"("+time+"-"+(time+extra[temp])+") |\t");
						time+= extra[temp];
						completion[temp]=time;
						extra[temp]=0;
					}
					temp++;
				}
				else 
					time++;
			}
			q+=2;
		}
		temp=0;
		while(temp<pid.length)
		{
			if(extra[temp]>0)
			{
					System.out.print("P"+pid[temp]+"("+time+"-"+(time+extra[temp])+") |\t");
					time+= extra[temp];
					completion[temp]=time;
					extra[temp]=0;
			}
			temp++;
		}
		for(int i=0;i<pid.length; i++)
			waiting[i]= completion[i]-burst[i]-arrival[i];
	}
	
  
  	public void copy()
	{
		for(int i=0;i<pid.length;i++)
			extra[i]=burst[i];
	}
	public int[] get(int p[],int b[],int pri[], int a[])
	{
		pid=p;
		burst=b;
		prior=pri;
		arrival=a;
		waiting= new int[pid.length];
		extra=new int[pid.length];
		copy();
		find_waiting();
		return waiting;
	}
}

