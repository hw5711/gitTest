package calendarProject;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Month extends Box implements ActionListener
{
	int month;
	JTextField showMonth=null;
	JButton nextMonth, lastMonth;
	myCalendar calendar;
	
	public Month(myCalendar calendar)
	{
		super(BoxLayout.X_AXIS);
		this.calendar=calendar;
		showMonth=new JTextField(2);
		month=calendar.getMonth();
		showMonth.setEditable(false);
		showMonth.setForeground(Color.blue);
		showMonth.setFont(new Font("Serif",Font.BOLD,16));
		nextMonth=new JButton("next month");
		lastMonth=new JButton("last month");
		showMonth.setText(""+month);
	}
	
	public void setMonth(int month)
	{
		if(month<=12 && month>=1)
		{
			this.month=month;
		}
		else
		{
			this.month=1;
		}
		showMonth.setText(""+month);
	}
	
	public int getMonth()
	{
		return month;
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==lastMonth)
		{
			if(month>=2)
			{
				month=month-1;
				calendar.setMonth(month);
				calendar.setCalendarBorder(calendar.getYear(),month);
			}
			else if(month==1)
			{
				month=12;
				calendar.setMonth(month);
				calendar.setCalendarBorder(calendar.getYear(),month);
			}
			showMonth.setText(""+month);
		}
		else if(e.getSource()==nextMonth)
		{
			if(month<12)
			{
				month=month+1;
				calendar.setMonth(month);
				calendar.setCalendarBorder(calendar.getYear(),month);
			}
			else if(month==12)
			{
				month=1;
				calendar.setMonth(month);
				calendar.setCalendarBorder(calendar.getYear(),month);
			}
			showMonth.setText(""+month);
		}
	}
}
