package os_a3_final;

import java.util.LinkedList;
import java.util.Queue;

public class round_robin
{
	int pid[];
	int burst[];
	int waiting[];
	int turn_around[];
	int extra[];
	int arrival[];
	int completion[];
	int q;
	
	Queue<Integer> queue = new LinkedList<>();
	
	public int check(int y, int time)
	{
		while(y<pid.length&&arrival[y]<=time)
		{
			queue.add(pid[y]);
			y++;
		}
		return y;
	}
	public int find(int x)
	{
		int i=0;
		for(i=0;i<pid.length;i++)
		{
			if(pid[i]==x)
				break;
		}
		return i;
	}
	public void find_waiting(int start)
	{
		int j=0,t=start,completed_pid=0;
		j=check(j,t);
		int x=0,i=0,total_wait=0;
		waiting= new int[burst.length+1];
		completion= new int[burst.length];
		
		while(completed_pid!=pid.length)
		{
		if(queue.peek()!=null)
		{
			x=queue.remove();
			i=find(x);
			//System.out.println("i= "+i);
			//print();
			if(extra[i]>q)
			{
				extra[i]= extra[i]-q;
				System.out.print("P"+pid[i]+"("+t+"-"+(t+q)+") |\t");
				if(t%22==0)
					System.out.println();
				t+=q;
			}
			else if(extra[i]>0)
			{
				System.out.print("P"+pid[i]+"("+t+"-"+(t+extra[i])+") |\t");
				if(t%20==0)
					System.out.println();
				t+=extra[i];
				extra[i] = 0;
			}
			if(extra[i]==0)
			{
				completion[i]=t;
				completed_pid++;
			}
			
		}
		else
			t++;
		j=check(j,t);
		if(extra[i]>0)
			queue.add(pid[i]);
		}
		for(int k=0;k<pid.length;k++)
			waiting[k]=completion[k]-arrival[k]-burst[k];
		waiting[pid.length]=t;
	}
	
	public void print()
	{
		for(int i=0;i<pid.length;i++)
			System.out.println(pid[i]+" "+arrival[i]+" "+burst[i]+" "+extra[i]);
	}
	public void copy()
	{
		for(int i=0;i<pid.length;i++)
			extra[i]=burst[i];
	}
	public int[] get(int p[],int b[], int a[], int quan, int start)
	{
		q=quan;
		pid=p;
		burst=b;
		arrival=a;	
		extra=new int[pid.length];
		copy();
		find_waiting(start);
		return waiting;
	}
}