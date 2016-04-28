package Model;

import java.util.*;


public class Game
{
	private ArrayList<String> Ips;
	private ArrayList<Integer> Ports;
	private ArrayList<String> PNames;
	public Game()
	{
		Ips = new ArrayList<String>();
		Ports = new ArrayList<Integer>();
		PNames = new ArrayList<String>();
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

	public String GetIthName(int i)
	{
		return PNames.get(i);
	}
}