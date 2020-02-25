package calendarProject;
import java.util.Calendar;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util. Hashtable;

public class myCalendar extends JFrame implements MouseListener
{
	int year, month, day;
	Hashtable<String, String> hashtable=new Hashtable<String,String>();//
	File file;
	JTextField showDay[];
	JLabel title[];
	Calendar calendar;
	int week;
	notePad notepad=null;
	Month monthChange;//
	Year yearChange;
	String weekDay[]= {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
	JPanel leftPanel, rightPanel;
	
	public myCalendar(int year, int month, int day)
	{
		leftPanel=new JPanel();
		JPanel leftCenter=new JPanel();
		JPanel leftNorth=new JPanel();
		leftCenter.setLayout(new GridLayout(7,7,3,3));
		
		rightPanel=new JPanel();
		this.year=year;
		this.month=month;
		this.day=day;
		yearChange=new Year(this);
		yearChange.setYear(year);
		monthChange=new Month(this);
		monthChange.setMonth(month);
		
		title=new JLabel[7];
		showDay=new JTextField[42];
		for(int j=0;j<7;j++)
		{
			title[j]=new JLabel();
			title[j].setText(weekDay[j]);
			//title[j].setBorder(BorderFactory.createRaisedBevelBorder());
			leftCenter.add(title[j]);
		}
		title[0].setForeground(Color.red);
		title[6].setForeground(Color.blue);
		
		for(int i=0;i<42;i++)
		{
			showDay[i]=new JTextField();
			showDay[i].addMouseListener(this);
			showDay[i].setEditable(false);
			leftCenter.add(showDay[i]);
		}
		
		calendar=Calendar.getInstance();
		Box box=Box.createHorizontalBox();
		box.add(yearChange);
		box.add(monthChange);
		leftNorth.add(box);
		leftPanel.setLayout(new BorderLayout());
		leftPanel.add(leftNorth, BorderLayout.NORTH);
		leftPanel.add(leftNorth, BorderLayout.CENTER);
		//leftPanel.add(new Label("please enter year "), BorderLayout.SOUTH);
		leftPanel.validate();//
		Container con=getContentPane();
		JSplitPane split=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
		con.add(split, BorderLayout.CENTER);
		con.validate();
		
		hashtable=new Hashtable<String,String>();//
		file= new File("calendarNote.txt");
		
		if(!file.exists())
		{
			try 
			{
				FileOutputStream out=new FileOutputStream(file);
				ObjectOutputStream objectOut=new ObjectOutputStream(out);
				objectOut.writeObject(hashtable);
				objectOut.close();
			}
			catch(IOException e)
			{
			}
		}
		
		notepad=new notePad(this);
		rightPanel.add(notepad);
		
		setCalendarBorder(year, month);
		addWindowListener(new WindowAdapter()
			{public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
			});//
		
		setVisible(true);
		setBounds(200,200,550,300);
		validate();
	}
	
	public void setCalendarBorder(int year, int month)
	{
		calendar.set(year, month-1, 1);//????
		week=calendar.get(Calendar.DAY_OF_WEEK)-1;
		if(month==1||month==3||month==5||month==7||month==8||month==10||month==12)
		{
			sortDate(week,31);
		}
		else if(month==4||month==6||month==9||month==11)
		{
			sortDate(week,30);
		}
		else if(month==2)
		{
			if((year%4==0 & year%100!=0)||(year%400==0))
			{
				sortDate(week,29);
			}
			else
			{
				sortDate(week,28);
			}
		}
	}
	
	public void sortDate(int week, int monthDayNum)
	{
		for(int i=week,n=1; i<week+monthDayNum; i++)
		{
			showDay[i].setText(""+n);
			if(n==day)
			{
				showDay[i].setForeground(Color.green);
				showDay[i].setFont(new Font("Serif",Font.BOLD,20));
			}
			else
			{
				showDay[i].setFont(new Font("Serif",Font.BOLD,20));
				showDay[i].setForeground(Color.black);
			}
			if(i%7==6)
			{
				showDay[i].setForeground(Color.blue);
			}
			n++;
		}
		
		for(int i=0; i<week; i++)
		{
			showDay[i].setText("");
		}
		for(int i=week+monthDayNum; i<42; i++)
		{
			showDay[i].setText("");
		}
	}
	
	public int getYear()
	{
		return year;
	}
	
	public void setYear(int y)
	{
		year = y;
		notepad.setYear(year);
	}
	
	public int getMonth()
	{
		return month;
	}
		
	public void setMonth(int m)
	{
		month = m;
		notepad.setMonth(month);
	}
	
	public int getDay()
	{
		return day;
	}

	public void setDay(int d)
	{
		day = d;
		notepad.setDay(day);
	}
	
	public Hashtable<String, String> getHashtable()//
	{
		return hashtable;
	}
	
	public File getFile()
	{
		return file;
	}
	
	public void mousePressed(MouseEvent e)
	{
		JTextField source=(JTextField)e.getSource();
		try
		{
			day=Integer.parseInt(source.getText());
			notepad.setDay(day);
			notepad.setMessage(year,month,day);
			notepad.setNote(null);
			notepad.getNote(year,month,day);
		}
		catch(Exception ee)
		{
		}
	}
	
	public void mouseClicked(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	
	public static void main(String args[])
	{
		Calendar calendar = Calendar.getInstance();
		int y = calendar.get(Calendar.YEAR);
		int m = calendar.get(Calendar.MONTH)+1;
		int d = calendar.get(Calendar.DAY_OF_MONTH);
		new myCalendar(y,m,d);
	}
}
