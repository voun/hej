import TSim.*;
import java.util.concurrent.Semaphore;

public class Lab1 
{
	private TSimInterface tsim = TSimInterface.getInstance();
	private Semaphore s1 = new Semaphore(0); //Train starts at this semaphore, so initially no available permits
	private Semaphore s2 = new Semaphore(1);
	private Semaphore s3 = new Semaphore(1);
	private Semaphore s4 = new Semaphore(1);
	private Semaphore s5 = new Semaphore(0); //Train starts at this semaphore, so initially no available permits
	private Semaphore s6 = new Semaphore(1);
	private int Coord[][] = {{13,3}, {13,5}, {13,7}, {19,9}, {13,8}, {12,9}, 
									{7,9}, {1,9}, {6,11}, {5,13}, {13,13}, {13,11}, 
									{6,10}, {13,10}, {6,5}, {9,5}}; //Coordinates of sensors
	
  public Lab1(Integer speed1, Integer speed2) throws InterruptedException 
  {
      Train train1 = new Train(speed1,true,1);
	  Train train2 = new Train(speed2,false,2);  
	  train1.start();train2.start();
  }
    
    class Train extends Thread // Inner class
    {
    	private int speed;
    	private boolean dir; //dir is true if the train is moving from A to B, and false otherwise.
    	private int id;
    	private int prevSensX;
    	private int prevSensY;
    	
    	public Train(int speed, boolean dir, int id) throws InterruptedException
    	{
    		this.speed = speed;
    		this.id = id;
    		this.dir = dir;
    	}
    	public void prevSens(SensorEvent sensor) {
    		prevSensX = sensor.getXpos();
    		prevSensY = sensor.getYpos();
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
    				if(sensor.getXpos() == Coord[0][0] && sensor.getYpos() == Coord[0][1]) //T1
    				{
    					if(!dir)
    					{
    						tsim.setSpeed(id, 0);
    						sleep(1000+20*Math.abs(speed));
    						speed = -speed;
    						tsim.setSpeed(id, speed);
    						dir = !dir;	
    					}
    					prevSens(sensor);
    				}
    				else if(sensor.getXpos() == Coord[1][0] && sensor.getYpos() == Coord[1][1]) //T2
    				{
    					if(!dir)
    					{
    						tsim.setSpeed(id, 0);
    						sleep(1000+20*Math.abs(speed));
    						speed = -speed;
    						tsim.setSpeed(id, speed);
    						dir = !dir;
    					}	
    					prevSens(sensor);
    				}
    				else if (sensor.getXpos() == Coord[2][0] && sensor.getYpos() == Coord[2][1]) //T3
    				{
    					if(dir)
    					{
    						s6.release();
    						if(s3.tryAcquire())
    							tsim.setSwitch(17, 7, TSimInterface.SWITCH_RIGHT);
    						else
    						{
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
    					prevSens(sensor);
    				}
    				else if (sensor.getXpos() == Coord[3][0] && sensor.getYpos() == Coord[3][1]) //T4
    				{
    					if (dir)
    					{
    						if(prevSensX == Coord[2][0] && prevSensY == Coord[2][1])
    							s1.release();	
    						if(s2.tryAcquire())
    							tsim.setSwitch(15, 9, TSimInterface.SWITCH_RIGHT);
    						else
    							tsim.setSwitch(15, 9, TSimInterface.SWITCH_LEFT);
    					}
    					else
    					{
    						if(prevSensX == Coord[5][0] && prevSensY == Coord[5][1])
    							s2.release();
    						if(s1.tryAcquire())
    							tsim.setSwitch(17, 7, TSimInterface.SWITCH_RIGHT);
    						else
    							tsim.setSwitch(17, 7, TSimInterface.SWITCH_LEFT);
    					}
    					prevSens(sensor);
    				}
    				else if(sensor.getXpos() == Coord[4][0] && sensor.getYpos() == Coord[4][1]) //T5
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
    					prevSens(sensor);
    				}
    				else if(sensor.getXpos() == Coord[5][0] && sensor.getYpos() == Coord[5][1]) //T6
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
    							tsim.setSwitch(15, 9, TSimInterface.SWITCH_RIGHT);
    							tsim.setSpeed(id, speed);		
    						}
    						prevSens(sensor);								
    					}
    				}
    				else if(sensor.getXpos() == Coord[6][0] && sensor.getYpos() == Coord[6][1]) //T7
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
    					prevSens(sensor);
    				}
    				else if(sensor.getXpos() == Coord[7][0] && sensor.getYpos() == Coord[7][1]) //T8
    				{
    					if(dir)
    					{
    						if(prevSensX == Coord[6][0] && prevSensY == Coord[6][1])
    							s2.release();
    						if(s5.tryAcquire())
    							tsim.setSwitch(3, 11, TSimInterface.SWITCH_LEFT);
    						else
    							tsim.setSwitch(3, 11, TSimInterface.SWITCH_RIGHT);			
    					}
    					else
    					{
    						if(prevSensX == Coord[8][0] && prevSensY == Coord[8][1])
    							s5.release();
    							
    						if(s2.tryAcquire())
    							tsim.setSwitch(4, 9, TSimInterface.SWITCH_LEFT);
    						else
    							tsim.setSwitch(4, 9, TSimInterface.SWITCH_RIGHT);		
    					}
    					prevSens(sensor);
    				}
   
    				else if(sensor.getXpos() == Coord[8][0] && sensor.getYpos() == Coord[8][1]) //T9
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
    					prevSens(sensor);
    				}
    				else if(sensor.getXpos() == Coord[9][0] && sensor.getYpos() == Coord[9][1]) //T10
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
    					prevSens(sensor);
    				}
    				else if(sensor.getXpos() == Coord[10][0] && sensor.getYpos() == Coord[10][1]) //T11
    				{
    					if(dir)
    					{
    						tsim.setSpeed(id, 0);
    						sleep(1000+20*Math.abs(speed));
    						speed = -speed;
    						tsim.setSpeed(id, speed);
    						dir = !dir;
    					}
    					prevSens(sensor);
    				}
    		
    				else if(sensor.getXpos() == Coord[11][0] && sensor.getYpos() == Coord[11][1]) //T12
    				{
    					if(dir)
    					{
    						tsim.setSpeed(id, 0);
    						sleep(1000+20*Math.abs(speed));
    						speed = -speed;
    						tsim.setSpeed(id, speed);
    						dir = !dir;
    					}	
    					prevSens(sensor);
    				}
    				else if(sensor.getXpos() == Coord[12][0] && sensor.getYpos() == Coord[12][1]) //T13
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
    					prevSens(sensor);
    				}
    				else if(sensor.getXpos() == Coord[13][0] && sensor.getYpos() == Coord[13][1]) //T14
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
    					prevSens(sensor);
    				}
    				else if(sensor.getXpos() == Coord[14][0] && sensor.getYpos() == Coord[14][1]) //T15
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
    					prevSens(sensor);
    				}			
    				else if(sensor.getXpos() == Coord[15][0] && sensor.getYpos() == Coord[15][1]) //T16
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
    					prevSens(sensor);
    				}	
        		} 		
    		}catch(InterruptedException e){
    			e.getMessage();
    			System.exit(1);
    		}catch (CommandException e){
    			e.getMessage();
    			System.exit(1);
    		}	   		
    	}
    }   
}