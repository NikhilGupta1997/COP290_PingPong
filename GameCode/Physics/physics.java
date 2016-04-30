package Physics;

//import package Physics;
public class physics 
{
	public vel_b2b Colision_b2b(double ux1,double uyy1,double ux2,double uyy2,double angle)
	{
		velocity vel1=new velocity();
		velocity vel2=new velocity();
		double uy1 = uyy1;
		double uy2 = uyy2;
		double e = 1.0;
		double sin = Math.sin(angle);
		double cos = Math.cos(angle);
		vel2.vy1= (ux1*(e+1)/2 *cos*sin) + uy1*(e+1)/2 *sin*sin + ux2*(((1-e)/2)*cos*sin-sin*cos)+uy2*(((1-e)/2)*sin*sin +cos*cos); // correct.

		vel2.vx1= ux1*(e+1)/2*cos*cos+ uy1*(e+1)/2*sin*cos + ux2*((1-e)/2*cos*cos+ sin*sin) + uy2*((1-e)/2*sin*cos-cos*sin); // correct

		vel1.vy1= ux1*(((1-e)/2)*cos*sin-sin*cos) + uy1*(((1-e)/2)*sin*sin +cos*cos) + ux2*(e+1)/2 *cos*sin + uy2*(1+e)/2*sin*sin; // correct.

		vel1.vx1= ux1*((1-e)/2*cos*cos+sin*sin) + uy1*(((1-e)/2)*cos*sin-cos*sin) + ux2*(e+1)/2*cos*cos + uy2*(1+e)/2*cos*sin; // correct

		vel_b2b vel=new vel_b2b();

		vel1.vy1 = (vel1.vy1);
		vel2.vy1 = (vel2.vy1);

		vel.v1=vel1;vel.v2=vel2;
		return vel;
	}

	public velocity b_wall(double ux1,double uy1,int wall_no)
	{
		velocity vel1=new velocity();
		if(wall_no%2==1)
		{
			vel1.vx1=ux1;
	 		vel1.vy1= -uy1;
		}
		else if(wall_no%2==0)
		{
			vel1.vx1=-ux1;
	 		vel1.vy1= uy1;	
		}
		return vel1;
	}

	public int collision_wall(double posx,double posy,double radius,double x)     //x is the dimension of the square
	{int wall_no=0;

	if((posx+radius)>=x-20&&(posy<540&&posy>40)) wall_no=4;
	else if((posy+radius+24.0)>=x-20&&(posx<530&&posx>50)) wall_no=3;
	else if((posx-radius)<=20.0&&(posy<540&&posy>40)) wall_no=2;
	else if((posy-radius+12.5-20)<=0.0&&(posx<530&&posx>50)) wall_no=1;
	//if(wall_no!=0)
	//	System.out.print(posx+","+posy+","+x+"\n");
	return wall_no;
	}

	public int collision_corner(double posx,double posy,double radius,double x,double l ) //y is the dimension of the corner
	{  	

		int corner_no=0;
		double sqrt2=Math.sqrt(2);
		if((Math.abs(posx+posy-l)/sqrt2)<=radius) corner_no=1;//working well
		else if(Math.abs(posy+posx-2*x+l)/sqrt2<=radius+20.0)corner_no=3;//working well
		else if(Math.abs(posy-posx+x-l)/sqrt2<=radius) corner_no=2;// working well
		else if(Math.abs(posy-posx-x+l)/sqrt2<=radius+22.0) corner_no=4;
		// if(corner_no!=0)
		// {
		// 	System.out.println(posx+","+posy);

		// System.out.println("Corner 1--"+(Math.abs(posx+posy-l)/sqrt2));
		// System.out.println("Corner 3--"+(Math.abs(posy+posx-2*x-l)/sqrt2));
		// System.out.println("Corner 2--"+(Math.abs(posy-posx+x-l)/sqrt2));
		// System.out.println("Corner 4--"+(Math.abs(posy-posx-x+l)/sqrt2));
		// }
		return corner_no;
	}

	public boolean collision_paddle(double posx,double posy,double len,int X,double radius,double padd_x,double padd_y,double h,int paddle_no)
	{  // TODO: Synchronize the player no 
		if(paddle_no==1)
		{
			if(posx<=(padd_x+len/2) && posx>=(padd_x-len/2) && posy<=(h+radius-10.0)) return true; else return false;
		}
		else if(paddle_no==4)
		{
			if(posy<=(padd_y+len/2) && posy>=(padd_y-len/2) && posx >= X-(h+radius+7.5)) return true; else return false ;
		}
		else if(paddle_no==3)
		{
			if(posx<=(padd_x+len/2) && posx>=(padd_x-len/2) && posy >= X-(h+radius+22)) return true; else return false;
		}
	else
	{
		if(posy<=(padd_y+len/2) && posy>=(padd_y-len/2) && posx<=(h+radius)) return true; else return false ;
	}
	
	}	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
