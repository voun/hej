import TSim.*;
import java.util.concurrent.Semaphore;

public class Lab1 
{
	private TSimInterface tsim = TSimInterface.getInstance();
	private Semaphore s1 = new Semaphore(1);
	private Semaphore s2 = new Semaphore(1);
	private Semaphore s3 = new Semaphore(1);
	private Semaphore s4 = new Semaphore(1);
	private Semaphore s5 = new Semaphore(1);
	private Semaphore s6 = new Semaphore(1);
	
  public Lab1(Integer speed1, Integer speed2) throws InterruptedException 
  {
      Train train1 = new Train(speed1,true,1);
	  Train train2 = new Train(speed2,false,2);  
	  train1.start();train2.start();
  }
    
    class Train extends Thread // Inner class
    {
    	private int speed;
    	private boolean dir; //dir är true om rör sig från A till B, annars false.
    	private int id;
    	private int prevSensX;
    	private int prevSensY;
    	
    	public Train(int speed, boolean dir, int id) throws InterruptedException
    	{
    		this.speed = speed;
    		if (id == 1) s1.acquire();
    		else if (id == 2) s5.acquire();
    		this.id = id;
    		this.dir = dir;
    	}
    	public void run()
    	{
    		try{
    			tsim.setSpeed(id, speed);
    			while(true)
        		{
    				SensorEvent sensor = tsim.getSensor(id);
    				if (sensor.getStatus() == SensorEvent.INACTIVE)
    					continue;
    				if(sensor.getXpos() == 13 && sensor.getYpos() == 3) //T1
    				{
    					if(!dir)
    					{
    						tsim.setSpeed(id, 0);
    						sleep(1000+20*Math.abs(speed));
    						speed = -speed;
    						tsim.setSpeed(id, speed);
    						dir = !dir;	
    					}
    					prevSensX = 13;
    					prevSensY = 3;
    				}
    				else if(sensor.getXpos() == 13 && sensor.getYpos() == 5) //T2
    				{
    					if(!dir)
    					{
    						tsim.setSpeed(id, 0);
    						sleep(1000+20*Math.abs(speed));
    						speed = -speed;
    						tsim.setSpeed(id, speed);
    						dir = !dir;
    					}	
    					prevSensX = 13;
    					prevSensY = 5;
    				}
    				else if (sensor.getXpos() == 13 && sensor.getYpos() == 7) //T3
    				{
    					if(dir)
    					{
    						s6.release();
    						if(s3.tryAcquire()){
    							//System.out.println("T4if");
    							tsim.setSwitch(17, 7, TSimInterface.SWITCH_RIGHT);}
    						else
    						{
    							//System.out.println("T3else");
    							tsim.setSpeed(id, 0);
    							s3.acquire();
    							tsim.setSwitch(17, 7, TSimInterface.SWITCH_RIGHT);
    							tsim.setSpeed(id, speed);
    						}
    					}
    					else
    					{
    						s3.release();
    						if(s6.tryAcquire())
    						{}
    						else
    						{
    							tsim.setSpeed(id, 0);
    							s6.acquire();
    							tsim.setSpeed(id, speed);
    						}							
    					}
    						
    					prevSensX = 13;
    					prevSensY = 7;
    				}
    				else if (sensor.getXpos() == 19 && sensor.getYpos() == 9) //T4
    				{
    					if (dir)
    					{
    						if(prevSensX == 13 && prevSensY == 7)
    							s1.release();	
    						if(s2.tryAcquire())
    							tsim.setSwitch(15, 9, TSimInterface.SWITCH_RIGHT);
    						else
    							tsim.setSwitch(15, 9, TSimInterface.SWITCH_LEFT);
	
    					}
    					else
    					{
    						if(prevSensX == 12 && prevSensY == 9)
    							s2.release();
    						if(s1.tryAcquire())
    							tsim.setSwitch(17, 7, TSimInterface.SWITCH_RIGHT);
    						else
    							tsim.setSwitch(17, 7, TSimInterface.SWITCH_LEFT);
    					}
    					prevSensX = 19;
    					prevSensY = 9;
    				}
    				else if(sensor.getXpos() == 13 && sensor.getYpos() == 8) //T5
    				{
    					if(dir)
    					{
    						s6.release();
    						if(s3.tryAcquire())
    							tsim.setSwitch(17, 7, TSimInterface.SWITCH_LEFT);
    						else
    						{
    							tsim.setSpeed(id, 0);
    							s3.acquire();
    							tsim.setSwitch(17, 7, TSimInterface.SWITCH_LEFT);
    							tsim.setSpeed(id, speed);	
    						}	
    					}
    					else
    					{
    						s3.release();
    						if(s6.tryAcquire())
    						{}
    						else
    						{
    							tsim.setSpeed(id, 0);
    							s6.acquire();
    							tsim.setSpeed(id, speed);
    						}							
    					}
    					prevSensX = 13;
    					prevSensY = 8;
    				}
    				else if(sensor.getXpos() == 12 && sensor.getYpos() == 9) //T7
    				{
    					if(dir)
    						s3.release();
    					else
    					{
    						if(s3.tryAcquire())
    							tsim.setSwitch(15, 9, TSimInterface.SWITCH_RIGHT);
    						else
    						{
    							tsim.setSpeed(id, 0);
    							s3.acquire();
    							System.out.println("hej");
    							tsim.setSwitch(15, 9, TSimInterface.SWITCH_RIGHT);
    							tsim.setSpeed(id, speed);		
    						}
    						prevSensX = 12;
    						prevSensY = 9;											
    					}
    				}
    				else if(sensor.getXpos() == 7 && sensor.getYpos() == 9) //T8
    				{
    					if(dir)
    					{
    						if(s4.tryAcquire())
    							tsim.setSwitch(4, 9, TSimInterface.SWITCH_LEFT);
    						else
    						{
    							tsim.setSpeed(id, 0);
    							s4.acquire();
    							tsim.setSwitch(4, 9, TSimInterface.SWITCH_LEFT);
    							tsim.setSpeed(id, speed);	
    						}		
    					}
    					else
    						s4.release();
    					prevSensX = 7;
    					prevSensY = 9;
    				}
    				else if(sensor.getXpos() == 1 && sensor.getYpos() == 9) //T9
    				{
    					if(dir)
    					{
    						if(prevSensX == 7 && prevSensY == 9)
    							s2.release();
    						if(s5.tryAcquire())
    							tsim.setSwitch(3, 11, TSimInterface.SWITCH_LEFT);
    						else
    							tsim.setSwitch(3, 11, TSimInterface.SWITCH_RIGHT);			
    					}
    					else
    					{
    						if(prevSensX == 6 && prevSensY == 11)
    							s5.release();
    							
    						if(s2.tryAcquire())
    							tsim.setSwitch(4, 9, TSimInterface.SWITCH_LEFT);
    						else
    							tsim.setSwitch(4, 9, TSimInterface.SWITCH_RIGHT);		
    					}
    					prevSensX = 1;
    					prevSensY = 9;
    				}
   
    				else if(sensor.getXpos() == 6 && sensor.getYpos() == 11) //T11
    				{
    					if(dir)
    						s4.release();
    					else
    					{
    						if(s4.tryAcquire())
    							tsim.setSwitch(3, 11, TSimInterface.SWITCH_LEFT);
    						else
    						{
    							tsim.setSpeed(id, 0);
    							s4.acquire();
    							tsim.setSwitch(3, 11, TSimInterface.SWITCH_LEFT);
    							tsim.setSpeed(id, speed);
    						}	
    					}
    					prevSensX = 6;
    					prevSensY = 11;
    				}
    				else if(sensor.getXpos() == 5 && sensor.getYpos() == 13) //T12 
    				{
    					if(dir)
    						s4.release();
    					else
    					{
    						if(s4.tryAcquire())
    							tsim.setSwitch(3, 11, TSimInterface.SWITCH_RIGHT);
    						else
    						{
    							tsim.setSpeed(id, 0);
    							s4.acquire();
    							tsim.setSwitch(3, 11, TSimInterface.SWITCH_RIGHT);
    							tsim.setSpeed(id, speed);
    							
    						}		
    					}
    					prevSensX = 5;
    					prevSensY = 13;
    				}
    				else if(sensor.getXpos() == 13 && sensor.getYpos() == 13) //T13
    				{
    					if(dir)
    					{
    						tsim.setSpeed(id, 0);
    						sleep(1000+20*Math.abs(speed));
    						speed = -speed;
    						tsim.setSpeed(id, speed);
    						dir = !dir;
    					}
    					prevSensX = 13;
    					prevSensY = 13;
    				}
    		
    				else if(sensor.getXpos() == 13 && sensor.getYpos() == 11) //T14
    				{
    					if(dir)
    					{
    						tsim.setSpeed(id, 0);
    						sleep(1000+20*Math.abs(speed));
    						speed = -speed;
    						tsim.setSpeed(id, speed);
    						dir = !dir;
    					}	
    					prevSensX = 13;
    					prevSensY = 11;
    				}
    				else if(sensor.getXpos() == 6 && sensor.getYpos() == 10) //T15
    				{
    					if(dir)
    					{
    						if(s4.tryAcquire())
    							tsim.setSwitch(4, 9, TSimInterface.SWITCH_RIGHT);
    						else
    						{
    							tsim.setSpeed(id, 0);
    							s4.acquire();
    							tsim.setSwitch(4, 9, TSimInterface.SWITCH_RIGHT);
    							tsim.setSpeed(id, speed);
    							
    						}		
    					}
    					else
    						s4.release();
    					prevSensX = 6;
    					prevSensY = 10;
    				}
    				else if(sensor.getXpos() == 13 && sensor.getYpos() == 10) //T16
    				{
    					if(dir)
    						s3.release();
    					else
    					{
    						if(s3.tryAcquire())
    							tsim.setSwitch(15, 9, TSimInterface.SWITCH_LEFT);
    						else
    						{
    							tsim.setSpeed(id, 0);
    							s3.acquire();
    							tsim.setSwitch(15, 9, TSimInterface.SWITCH_LEFT);
    							tsim.setSpeed(id, speed);	
    						}		
    					}
    					prevSensX = 13;
    					prevSensY = 10;
    				}
    				else if(sensor.getXpos() == 6 && sensor.getYpos() == 5) //T17
    				{
    					if(dir)
    					{
    						if(s6.tryAcquire())
    						{}
    						else
    						{
    							tsim.setSpeed(id, 0);
    							s6.acquire();
    							tsim.setSpeed(id, speed);
    						}
    					}
    					else
    						s6.release();
    					prevSensX = 6;
    					prevSensY = 5;
    				}			
    				else if(sensor.getXpos() == 9 && sensor.getYpos() == 5) //T18
    				{
    					if(dir)
    					{
    						if(s6.tryAcquire())
    						{}
    						else
    						{
    							tsim.setSpeed(id, 0);
    							s6.acquire();
    							tsim.setSpeed(id, speed);
    						}
    					}
    					else
    						s6.release();
    					prevSensX = 9;
    					prevSensY = 5;
    				}	
        		} 		
    		}catch(InterruptedException e){
    			e.getMessage();
    		}catch (CommandException e){
    			e.getMessage();
    		}	   		
    	}
    }   
}