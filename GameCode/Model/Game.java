package Model;

import java.util.*;


public class Game
{
	private ArrayList<String> Ips;
	private ArrayList<Integer> Ports;
	public Game()
	{
		Ips = new ArrayList<String>();
		Ports = new ArrayList<Integer>();
	}

	public Game(ArrayList<String> ip, ArrayList<Integer> port)
	{
		Ips = ip;
		Ports = port;
	}

	public String GetIthIP(int i)
	{
		return Ips.get(i);
	}

	public int GetIthPort(int i)
	{
		return Ports.get(i);
	}
}