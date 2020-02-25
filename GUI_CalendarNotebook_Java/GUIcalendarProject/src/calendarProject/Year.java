package calendarProject;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Year extends Box implements ActionListener
{
	int year;
	JTextField showYear=null;
	JButton nextYear, lastYear;
	myCalendar calendar;
	
	public Year(myCalendar calendar)
	{
		super(BoxLayout.X_AXIS);
		showYear=new JTextField(4);
		showYear.setForeground(Color.blue);
		showYear.setFont(new Font("Serif",Font.BOLD,14));
		this.calendar=calendar;
		year=calendar.getYear();
		nextYear=new JButton("next year");
		lastYear=new JButton("last year");
		add(lastYear);
		add(showYear);
		add(nextYear);
		showYear.addActionListener(this);
		lastYear.addActionListener(this);
		nextYear.addActionListener(this);
	}
	
	public void setYear(int year)
	{
		this.year=year;
		showYear.setText(""+year);
	}
	
	public int getYear()
	{
		return year;
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==lastYear)
		{
			year=year-1;
			showYear.setText(""+year);
			calendar.setYear(year);
			calendar.setCalendarBorder(year,calendar.getMonth());
		}
		else if(e.getSource()==nextYear)
		{
			year=year+1;
			//year=Integer.parseInt(showYear.getText());
			showYear.setText(""+year);
			calendar.setYear(year);
			calendar.setCalendarBorder(year,calendar.getMonth());
		}
		else if(e.getSource()==showYear)
		{
			try
			{
				year=Integer.parseInt(showYear.getText());
				showYear.setText(""+year);
				calendar.setYear(year);
				calendar.setCalendarBorder(year,calendar.getMonth());
			}
			catch(NumberFormatException ee)
			{
				showYear.setText(""+year);
				calendar.setYear(year);
				calendar.setCalendarBorder(year,calendar.getMonth());
			}
		}
	}
}
